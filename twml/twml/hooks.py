""" This file contains tf.train.SessionRunHooks defined by TWML """
from datetime import datetime
import json
import operator
import os

from absl import logging
import numpy as np
import tensorflow.compat.v1 as tf
from tensorflow.python.training.basic_session_run_hooks import NeverTriggerTimer, SecondOrStepTimer
import twml


class StepProgressHook(tf.train.SessionRunHook):
  """Hook that displays a progress bar to monitor global step progress """

  def __init__(self, max_step):
    """
    Initializes a `StepProgressHook`.
    This hook displays a progress bar for max_steps.

    Note that this hook only works for training and calibration.

    Args:
      max_steps:
        maximum steps to monitor in progress bar.
        When this many steps is reached, the progress bar will be full.
    """
    self._max_step = max_step
    self._start_step = 0
    self._global_step_tensor = None
    self._progress_bar = None

  def begin(self):
    """ sets the global_step_tensor """
    self._global_step_tensor = tf.train.get_or_create_global_step()
    if self._global_step_tensor is None:
      raise RuntimeError("Global step should be created to use StepProgressHook.")

  def after_create_session(self, session, coord):
    """ creates the progress bar and keeps track of the first global step upon session creation """
    global_step = session.run(self._global_step_tensor)
    self._start_step = global_step
    self._progress_bar = tf.keras.utils.Progbar(self._max_step)

  def before_run(self, run_context):  # pylint: disable=unused-argument
    """ invoked before calling session.run """
    return tf.train.SessionRunArgs(self._global_step_tensor)

  def after_run(self, run_context, run_values):
    """ invoked after run is called. Updates the progress bar. """
    step = run_context.session.run(self._global_step_tensor)
    self._progress_bar.update(step - self._start_step)


class GetMetricsHook(tf.train.SessionRunHook):
  """
  Hook used to obtain evaluation metrics.
  Typically used for early-stopping by obtaining the value of a
  metric at the end of an epoch.
  Note that the metric tensor and its commensurate update Op
  are responsible for aggregating the metric during the session
  (one session per epoch). Used for evaluation.
  """

  def __init__(self, get_metrics_fn):
    """GetMetricsHook constructor.

    Args:
      get_metrics_fn:
        Function that returns a dict mapping metric keys to
        tensors as a tf.Tensor.
        See Trainer.learn for an example use-case.
    """

    self._get_metrics_fn = get_metrics_fn
    self._metric_tensors = None
    self.metric_values = None

  def begin(self):
    """ sets the global_step_tensor and metric tensor"""
    self._metric_tensors = self._get_metrics_fn()
    assert isinstance(self._metric_tensors, dict)

  def end(self, session):
    self.metric_values = session.run(self._metric_tensors)


class EarlyStopHook(GetMetricsHook):
  """
  A GetMetricsHook augmented with early-stopping logic for use
  within the Trainer.learn method.
  """

  def __init__(self,
               metric,
               patience,
               minimize,
               get_estimator_spec_fn,
               checkpoint_dir,
               file_path=None,
               exit_on_end=True,
               start_epoch=0,
               tolerance=0):
    """
    Prepare early-stopping hook and variables.

    Args:
      metric:
        String specifying the metric to early-stop on. Required with positive
        ``early_stop_patience``. For example, 'accuracy', 'accuracy_0', 'loss', etc.
        The string is used to extract the relevant tensor Op from the dict returned by
        the get_eval_metric_ops method. For ``metrics`` pass to the constructor,
        the string is one of those. For multi-class (that is, multi-metric)
        metrics, the string may be appended with a ``_0``, ``_1``, etc. or one
        of the ``multi_metric_names`` (one per class).
      patience:
        Maximum number of epochs to wait for an improvement in the early_stop_metric
        before breaking off training. For example, a patience of 10 means that
        training will have 10 epochs to improve the metric before it is killed.
        Whenever the metric is improved before running out of patience,
        patience is reset to ``early_stop_patience``.
      minimize:
        Set this to True for metrics that need to be minimized
        (like ``loss``). Metrics like ``accuracy`` that need to be maximized
        should set this to False.
      tolerance:
        A non-negative tolerance for comparing early_stop_metric.
        e.g. when maximizing the condition is current_metric > best_metric + tolerance."
        Defaults to 0.
      get_estimator_spec_fn:
        function that returns the current EstimatorSpec.
        The EstimatorSpec is used to obtain the current eval_metric_ops.
      checkpoint_dir:
        path to directory containing the Estimator checkpoints.
      file_path:
        path to file that is used by this hook to communicate early-stopping
        to StopIfExistsHook. This hook would be used for evaluation, while
        the StopIfExistsHooks (the listeners) would be used for training.
        When the file is created, the StopIfExistsHooks detect and terminate training.
        This argument is used by ``Trainer.train_and_evaluate``.
      exit_on_end:
        when the end() method is called to indicate that the session is terminating,
        and exit_on_end is True, twml.errors.EarlyStopError() is triggered to stop the evaluation job.
        This is set to False by the trainer for non distributed jobs.
      start_epoch:
        Specifies the starting epoch number. This is used for logging purposes only.
    """
    if not isinstance(metric, str):
      raise ValueError("Expecting string for metric arg")
    if not isinstance(patience, int):
      raise ValueError("Expecting positive number for metric arg")

    self.should_stop = False
    self._metric = metric
    self._patience = patience
    self._current_patience = patience
    self._checkpoint_dir = checkpoint_dir
    self._exit_on_end = exit_on_end
    self._latest_checkpoint_path = None
    # used for distributed training (tf.estimator.train_and_evaluate)
    self._file_path = file_path
    self._epoch = start_epoch
    if self._file_path is not None:
      # TODO try to read epoch from a file that we create
      if tf.io.gfile.exists(self._file_path):
        # delete the file if it exists (not sure this makes sense)
        logging.info("EarlyStopHook: Removing existing file: %s.", self._file_path)
        tf.io.gfile.remove(self._file_path)

    # best_checkpoint dir will contain the best checkpoint
    self._best_checkpoint_path = os.path.join(checkpoint_dir, 'best_checkpoint')
    self._eval_checkpoint_path = os.path.join(checkpoint_dir, 'eval_checkpoint')
    self._best_metric_path = os.path.join(self._best_checkpoint_path, self._metric)

    if tf.io.gfile.exists(self._best_metric_path):
      with tf.io.gfile.GFile(self._best_metric_path, mode="r") as f:
        best_metric_from_file = float(f.read())
    else:
      best_metric_from_file = None

    if minimize:
      # current < best : is better
      self._is_better_than = operator.lt
      # worse metric possible
      if best_metric_from_file is None:
        self._best_metric = np.inf
      else:
        self._best_metric = best_metric_from_file - tolerance
      # used for printing
      self._early_stop_name = "minimum"
    else:
      # current > best : is better
      self._is_better_than = operator.gt
      # worse metric possible
      if best_metric_from_file is None:
        self._best_metric = -np.inf
      else:
        self._best_metric = best_metric_from_file + tolerance
      # used for printing
      self._early_stop_name = "maximum"

    def get_metrics_fn():
      """ function to get metric tensors to early-stopping """
      estimator_spec = get_estimator_spec_fn()
      eval_metric_ops = estimator_spec.eval_metric_ops
      if metric not in eval_metric_ops:
        raise ValueError(
          "Expecting early_stop_metric '%s' key in eval_metric_ops dict"
          % (metric))
      # get the value_op from the (value_op, update_op) value
      return {k: v[0] for k, v in eval_metric_ops.items()}

    # initialize GetMetricsHook to get current value of metric from session
    super(EarlyStopHook, self).__init__(get_metrics_fn=get_metrics_fn)

  def early_stop(self, epoch):
    """
    Looks at the current value of the early stopping metric.
    Decrements current patience. If metric improves, patience is reset
    and latest checkpoint is moved to checkpoint_dir/best_checkpoint.
    If current patience reaches zero, returns True.

    Args:
      epoch:
        The current epoch number.

    Returns:
      True when early-stopped. False otherwise.
    """
    # decrement patience
    self._current_patience -= 1

    # get the current metric value
    current_metric = self.metric_values[self._metric]

    if self._is_better_than(current_metric, self._best_metric):
      # save best version of model
      self._best_metric = current_metric
      logging.info(
        "Found new %s %s=%f @ epoch %d",
        self._early_stop_name, self._metric, self._best_metric, epoch)
      # backup the file to checkpoint_dir/best_checkpoint
      assert self._latest_checkpoint_path, "expecting latest checkpoint"
      logging.info("Backing up " + self._latest_checkpoint_path)

      try:
        eval_checkpoint = tf.train.latest_checkpoint(self._eval_checkpoint_path)
        twml.util.backup_checkpoint(
          checkpoint_path_prefix=eval_checkpoint,
          backup_path=self._best_checkpoint_path)
      except twml.errors.CheckpointNotFoundError as ex:
        msg = "Consider increasing 'keep_checkpoint_max' or 'save_checkpoint_secs'"
        raise twml.errors.CheckpointNotFoundError(str(ex) + "\n" + msg)

      tf.io.gfile.makedirs(os.path.dirname(self._best_metric_path))
      with tf.io.gfile.GFile(self._best_metric_path, mode="w") as f:
        # Write with enough precision
        f.write("%.8f" % self._best_metric)

      # reset patience
      self._current_patience = self._patience

    elif self._current_patience > 0:
      logging.info("No new %s found after %d epochs",
                   self._early_stop_name, self._patience - self._current_patience)
    elif self._current_patience == 0:
      logging.info(
        "No new %s found after %d epochs. Early-stopping experiment.",
        self._early_stop_name, self._patience)
      return True

    return False

  def cleanup_checkpoints(self):
    """
    makes it so that the best checkpoint is the only checkpoint
    in checkpoint_dir.
    """
    raise NotImplementedError("cleanup_checkpoints is no longer supported")

  def end(self, session):
    """
    This method is called at the end of an evaluation/epoch.
    When file_path constructor argument is provided, this
    will call ``early_stop()``.
    When ``early_stop()`` returns True, it creates the file_path,
    which will be detected by StopIfExistsHooks
    and stop training for all workers and the chief. It will
    also call ``cleanup_checkpoints()``.
    """
    super(EarlyStopHook, self).end(session)

    # Checks for early stopping criteria and makes a backup
    self.should_stop = self.early_stop(self._epoch)

    if self._file_path is not None:
      if self.should_stop:
        # create a file to inform workers
        with tf.io.gfile.GFile(self._file_path, "wb") as gfile:
          gfile.write("early-stop\n")
        # makes the best checkpoint the only checkpoint in save_dir.
        msg = "early-stopping evaluation at epoch %d" % self._epoch
        logging.info(msg)
        if self._exit_on_end:
          raise twml.errors.EarlyStopError(msg)
      else:
        self._latest_checkpoint_path = None

    self._epoch += 1

  def begin(self):
    """
    Saves the latest_checkpoint in case it gets superseded by another checkpoint.
    Remember that when used with train_and_evaluate, the chief saves checkpoints
    continuouly. The chief could save a checkpoint after evaluation started.
    So saving the checkpoint at the beginning of evaluation ensures that we
    later save the correct best checkpoint.
    """
    super(EarlyStopHook, self).begin()
    self._latest_checkpoint_path = tf.train.latest_checkpoint(self._checkpoint_dir)

    assert self._latest_checkpoint_path, "expecting latest checkpoint"
    # Backup to temporary directory
    try:
      twml.util.backup_checkpoint(
        checkpoint_path_prefix=self._latest_checkpoint_path,
        backup_path=self._eval_checkpoint_path)
    except twml.errors.CheckpointNotFoundError as ex:
      msg = "Consider increasing 'keep_checkpoint_max' or 'save_checkpoint_secs'"
      raise twml.errors.CheckpointNotFoundError(str(ex) + "\n" + msg)


class MetricsUpdateHook(GetMetricsHook):
  """
  A GetMetricsHook augmented with logic to map SessionRun events to metrics updates.
  It is mainly used by `TrackRun` to persist model metrics via Model Repo.
  """

  def __init__(self,
               get_estimator_spec_fn,
               add_metrics_fn,
               every_n_iter=None,
               every_n_secs=None
               ):
    """
    Args:
      get_estimator_spec_fn:
        function that returns the current EstimatorSpec.
        The EstimatorSpec is used to obtain the current eval_metric_ops.
      add_metrics_fn: `function` callback used to report metrics, called automatically
        at the end of every epoch.
      every_n_iter: `int`, log the metrics once every N local
        steps taken in the current epoch.
      every_n_secs: `int` or `float`, log the metrics once every N
        seconds passed in the current epoch. Exactly one of `every_n_iter` and `every_n_secs`
        should be provided.
    Raises:
      ValueError: if `every_n_iter` is non-positive or if not exactly one of `every_n_iter` and
        `every_n_secs` is set when `add_progress_metrics_fn` is provided.
    """
    only_log_at_end = (every_n_iter is None) and (every_n_secs is None)

    if (not only_log_at_end and every_n_iter and every_n_secs):
      raise ValueError(
        'exactly one of every_n_iter and every_n_secs must be provided'
      )

    # TODO: should have a minimum to avoid too many calls to ModelRepo?
    if every_n_iter is not None and every_n_iter <= 0:
      raise ValueError("invalid every_n_iter=%s." % every_n_iter)

    self._timer = (
      NeverTriggerTimer() if only_log_at_end else
      SecondOrStepTimer(every_secs=every_n_secs, every_steps=every_n_iter)
    )

    self._should_trigger = False
    self._iter_count = 0

    self._add_metrics_fn = add_metrics_fn

    def get_metrics_fn():
      """
      Function that returns the current EstimatorSpec.
        The EstimatorSpec is used to obtain the current eval_metric_ops.
      """
      estimator_spec = get_estimator_spec_fn()
      eval_metric_ops = estimator_spec.eval_metric_ops
      # get the value_op from the (value_op, update_op) value
      return {k: v[0] for k, v in eval_metric_ops.items()}
    super(MetricsUpdateHook, self).__init__(get_metrics_fn=get_metrics_fn)

  def report_metrics(self):
    """
    Triggers a metrics report.
    """
    self._timer.update_last_triggered_step(self._iter_count)
    if self.metric_values is not None:
      self._add_metrics_fn(self.metric_values)

  def begin(self):
    """
    Triggered before each epoch.
    """
    self._timer.reset()
    self._iter_count = 0
    return super(MetricsUpdateHook, self).begin()

  def before_run(self, run_context):
    """
    Triggered before each step.
    """
    self._should_trigger = self._timer.should_trigger_for_step(self._iter_count)
    return super(MetricsUpdateHook, self).before_run(run_context)

  def after_run(self, run_context, run_values):
    """
    Triggered after each step.
    """
    if self._should_trigger:
      self.report_metrics()
    self._iter_count += 1
    return super(MetricsUpdateHook, self).after_run(run_context, run_values)

  def end(self, session):
    """
    Triggered after each epoch.
    """
    self.report_metrics()
    return super(MetricsUpdateHook, self).end(session)


class EarlyStopDuration(tf.train.SessionRunHook):
  """
  Hook that can be used to terminate a job (training or validation) after a certain duration.
  The hook is fault tolerant, i.e., if a job is allotted 1 hour to run and fails after 45 minutes,
  then it will only run for 15 minutes once restarted.

  Args:
    max_duration: 
      A float. When this argument is defined, the job will automatically terminate after
      `max_duration` seconds if it has not already compeleted. 
    
    overwrite:
      A boolean. If set to True, this hook will overwrite the file containing the elapsed time
      since the beginning of the job. In a distributed setting, this will be used so only one 
      job writes to the file while all others will have read access. In a distributed setting,
      if all executors have this parameter set to False, then it just means that the hook will 
      not be fault tolerant. When restarted, the job will restart the clock from 0.
      
    save_dir:
      String. A directory (located on a file system that is Tensorflow compatible) where 
      we can store the file which contains the record of the elapsed time. This file is what makes 
      the hook faul tolerant.

    exit_on_end:
      when exit_on_end is True, twml.errors.EarlyStopError() is triggered to stop the job.
      This is usually set to True to kill a validation job in a distributed setting.
   """

  def __init__(self, max_duration: float, exit_on_end: bool, save_dir: str, overwrite: bool):
    self._overwrite = overwrite
    self._save_dir = save_dir
    self._exit_on_end = exit_on_end
    self._max_duration = max_duration
    self._last_time_check = datetime.now()

    # Initialize elapse time file
    if overwrite:
      self.elapsed_time()

  @property
  def elapsed_file_path(self):
    return os.path.join(self._save_dir, "early_stop_duration.txt")

  def early_stop(self) -> bool:
    return self.elapsed_time() > self._max_duration

  def elapsed_time(self) -> float:
    # Recorded elapsed time is 0 unless it's been recorded in a file already
    recorded_elapsed_time = 0
    if tf.io.gfile.exists(self.elapsed_file_path):
      with tf.io.gfile.GFile(self.elapsed_file_path, mode="r") as file:
        recorded_elapsed_time = json.loads(file.read())["elapsed_time"]

    elapsed_time = recorded_elapsed_time + (datetime.now() - self._last_time_check).total_seconds()
    self._last_time_check = datetime.now()

    if self._overwrite:
      # Record the actualized new elapsed time to the file
      tf.io.gfile.makedirs(os.path.dirname(self.elapsed_file_path))
      with tf.io.gfile.GFile(self.elapsed_file_path, mode="w") as file:
        record = {
          "elapsed_time": elapsed_time,
          "max_duration": self._max_duration
        }
        file.write(json.dumps(record, indent=2))

    return elapsed_time

  def before_run(self, run_context: tf.estimator.SessionRunContext) -> None:
    if self.early_stop():
      message = f"""
        Stopping job which now exceeded the maximum duration of {self._max_duration} seconds. 
      """
      logging.info(message)
      run_context.request_stop()

      if self._exit_on_end:
        raise twml.errors.EarlyStopError(message)


class StopAtStepHook(tf.train.StopAtStepHook):
  """
  Overrides ``tf.train.StopAtStepHook`` so that
  a ``stop_requested`` property can be accessed to determine
  if this hook requested a stop.
  """

  def __init__(self, *args, **kwargs):
    super(StopAtStepHook, self).__init__(*args, **kwargs)
    self._stop_requested = False

  @property
  def stop_requested(self):
    """ true if this hook requested a stop """
    return self._stop_requested

  def after_run(self, run_context, run_values):
    """ sets self.stop_requested to true when requesting a stop """
    super(StopAtStepHook, self).after_run(run_context, run_values)
    self._stop_requested = run_context.stop_requested


class StopIfExistsHook(tf.train.SessionRunHook):
  """
  Hook that requests stop if a file exists.
  This hook is used with the EarlyStopHook to implement
  early-stopping for distributed training (tf.estimator.train_and_evaluate).
  """

  def __init__(self, file_path):
    """
    Arguments:
      file_path:
        path to file. When this hook detects that the file exists,
        it requests a stop, which effectively kills this worker.
    """
    self._file_path = file_path
    self._stop_requested = False

  def after_run(self, run_context, run_values):
    if tf.io.gfile.exists(self._file_path):
      logging.info("Early-stopping file detected; requesting stop")
      run_context.request_stop()
      self._stop_requested = True

  @property
  def stop_requested(self):
    """ true if this hook requested a stop """
    return self._stop_requested
