' This file contains tf.train.SessionRunHooks defined by TWML '
_D="Consider increasing 'keep_checkpoint_max' or 'save_checkpoint_secs'"
_C='expecting latest checkpoint'
_B=False
_A=None
from datetime import datetime
import json,operator,os
from absl import logging
import numpy as np,tensorflow.compat.v1 as tf
from tensorflow.python.training.basic_session_run_hooks import NeverTriggerTimer,SecondOrStepTimer
import twml
class StepProgressHook(tf.train.SessionRunHook):
	'Hook that displays a progress bar to monitor global step progress '
	def __init__(A,max_step):'\n    Initializes a `StepProgressHook`.\n    This hook displays a progress bar for max_steps.\n\n    Note that this hook only works for training and calibration.\n\n    Args:\n      max_steps:\n        maximum steps to monitor in progress bar.\n        When this many steps is reached, the progress bar will be full.\n    ';A._max_step=max_step;A._start_step=0;A._global_step_tensor=_A;A._progress_bar=_A
	def begin(A):
		' sets the global_step_tensor ';A._global_step_tensor=tf.train.get_or_create_global_step()
		if A._global_step_tensor is _A:raise RuntimeError('Global step should be created to use StepProgressHook.')
	def after_create_session(A,session,coord):' creates the progress bar and keeps track of the first global step upon session creation ';B=session.run(A._global_step_tensor);A._start_step=B;A._progress_bar=tf.keras.utils.Progbar(A._max_step)
	def before_run(A,run_context):' invoked before calling session.run ';return tf.train.SessionRunArgs(A._global_step_tensor)
	def after_run(A,run_context,run_values):' invoked after run is called. Updates the progress bar. ';B=run_context.session.run(A._global_step_tensor);A._progress_bar.update(B-A._start_step)
class GetMetricsHook(tf.train.SessionRunHook):
	'\n  Hook used to obtain evaluation metrics.\n  Typically used for early-stopping by obtaining the value of a\n  metric at the end of an epoch.\n  Note that the metric tensor and its commensurate update Op\n  are responsible for aggregating the metric during the session\n  (one session per epoch). Used for evaluation.\n  '
	def __init__(A,get_metrics_fn):'GetMetricsHook constructor.\n\n    Args:\n      get_metrics_fn:\n        Function that returns a dict mapping metric keys to\n        tensors as a tf.Tensor.\n        See Trainer.learn for an example use-case.\n    ';A._get_metrics_fn=get_metrics_fn;A._metric_tensors=_A;A.metric_values=_A
	def begin(A):' sets the global_step_tensor and metric tensor';A._metric_tensors=A._get_metrics_fn();assert isinstance(A._metric_tensors,dict)
	def end(A,session):A.metric_values=session.run(A._metric_tensors)
class EarlyStopHook(GetMetricsHook):
	'\n  A GetMetricsHook augmented with early-stopping logic for use\n  within the Trainer.learn method.\n  '
	def __init__(A,metric,patience,minimize,get_estimator_spec_fn,checkpoint_dir,file_path=_A,exit_on_end=True,start_epoch=0,tolerance=0):
		'\n    Prepare early-stopping hook and variables.\n\n    Args:\n      metric:\n        String specifying the metric to early-stop on. Required with positive\n        ``early_stop_patience``. For example, \'accuracy\', \'accuracy_0\', \'loss\', etc.\n        The string is used to extract the relevant tensor Op from the dict returned by\n        the get_eval_metric_ops method. For ``metrics`` pass to the constructor,\n        the string is one of those. For multi-class (that is, multi-metric)\n        metrics, the string may be appended with a ``_0``, ``_1``, etc. or one\n        of the ``multi_metric_names`` (one per class).\n      patience:\n        Maximum number of epochs to wait for an improvement in the early_stop_metric\n        before breaking off training. For example, a patience of 10 means that\n        training will have 10 epochs to improve the metric before it is killed.\n        Whenever the metric is improved before running out of patience,\n        patience is reset to ``early_stop_patience``.\n      minimize:\n        Set this to True for metrics that need to be minimized\n        (like ``loss``). Metrics like ``accuracy`` that need to be maximized\n        should set this to False.\n      tolerance:\n        A non-negative tolerance for comparing early_stop_metric.\n        e.g. when maximizing the condition is current_metric > best_metric + tolerance."\n        Defaults to 0.\n      get_estimator_spec_fn:\n        function that returns the current EstimatorSpec.\n        The EstimatorSpec is used to obtain the current eval_metric_ops.\n      checkpoint_dir:\n        path to directory containing the Estimator checkpoints.\n      file_path:\n        path to file that is used by this hook to communicate early-stopping\n        to StopIfExistsHook. This hook would be used for evaluation, while\n        the StopIfExistsHooks (the listeners) would be used for training.\n        When the file is created, the StopIfExistsHooks detect and terminate training.\n        This argument is used by ``Trainer.train_and_evaluate``.\n      exit_on_end:\n        when the end() method is called to indicate that the session is terminating,\n        and exit_on_end is True, twml.errors.EarlyStopError() is triggered to stop the evaluation job.\n        This is set to False by the trainer for non distributed jobs.\n      start_epoch:\n        Specifies the starting epoch number. This is used for logging purposes only.\n    ';F=tolerance;D=checkpoint_dir;E=patience;C=metric
		if not isinstance(C,str):raise ValueError('Expecting string for metric arg')
		if not isinstance(E,int):raise ValueError('Expecting positive number for metric arg')
		A.should_stop=_B;A._metric=C;A._patience=E;A._current_patience=E;A._checkpoint_dir=D;A._exit_on_end=exit_on_end;A._latest_checkpoint_path=_A;A._file_path=file_path;A._epoch=start_epoch
		if A._file_path is not _A:
			if tf.io.gfile.exists(A._file_path):logging.info('EarlyStopHook: Removing existing file: %s.',A._file_path);tf.io.gfile.remove(A._file_path)
		A._best_checkpoint_path=os.path.join(D,'best_checkpoint');A._eval_checkpoint_path=os.path.join(D,'eval_checkpoint');A._best_metric_path=os.path.join(A._best_checkpoint_path,A._metric)
		if tf.io.gfile.exists(A._best_metric_path):
			with tf.io.gfile.GFile(A._best_metric_path,mode='r')as G:B=float(G.read())
		else:B=_A
		if minimize:
			A._is_better_than=operator.lt
			if B is _A:A._best_metric=np.inf
			else:A._best_metric=B-F
			A._early_stop_name='minimum'
		else:
			A._is_better_than=operator.gt
			if B is _A:A._best_metric=-np.inf
			else:A._best_metric=B+F
			A._early_stop_name='maximum'
		def H():
			' function to get metric tensors to early-stopping ';B=get_estimator_spec_fn();A=B.eval_metric_ops
			if C not in A:raise ValueError("Expecting early_stop_metric '%s' key in eval_metric_ops dict"%C)
			return{A:B[0]for(A,B)in A.items()}
		super(EarlyStopHook,A).__init__(get_metrics_fn=H)
	def early_stop(A,epoch):
		'\n    Looks at the current value of the early stopping metric.\n    Decrements current patience. If metric improves, patience is reset\n    and latest checkpoint is moved to checkpoint_dir/best_checkpoint.\n    If current patience reaches zero, returns True.\n\n    Args:\n      epoch:\n        The current epoch number.\n\n    Returns:\n      True when early-stopped. False otherwise.\n    ';A._current_patience-=1;B=A.metric_values[A._metric]
		if A._is_better_than(B,A._best_metric):
			A._best_metric=B;logging.info('Found new %s %s=%f @ epoch %d',A._early_stop_name,A._metric,A._best_metric,epoch);assert A._latest_checkpoint_path,_C;logging.info('Backing up '+A._latest_checkpoint_path)
			try:C=tf.train.latest_checkpoint(A._eval_checkpoint_path);twml.util.backup_checkpoint(checkpoint_path_prefix=C,backup_path=A._best_checkpoint_path)
			except twml.errors.CheckpointNotFoundError as D:E=_D;raise twml.errors.CheckpointNotFoundError(str(D)+'\n'+E)
			tf.io.gfile.makedirs(os.path.dirname(A._best_metric_path))
			with tf.io.gfile.GFile(A._best_metric_path,mode='w')as F:F.write('%.8f'%A._best_metric)
			A._current_patience=A._patience
		elif A._current_patience>0:logging.info('No new %s found after %d epochs',A._early_stop_name,A._patience-A._current_patience)
		elif A._current_patience==0:logging.info('No new %s found after %d epochs. Early-stopping experiment.',A._early_stop_name,A._patience);return True
		return _B
	def cleanup_checkpoints(A):'\n    makes it so that the best checkpoint is the only checkpoint\n    in checkpoint_dir.\n    ';raise NotImplementedError('cleanup_checkpoints is no longer supported')
	def end(A,session):
		'\n    This method is called at the end of an evaluation/epoch.\n    When file_path constructor argument is provided, this\n    will call ``early_stop()``.\n    When ``early_stop()`` returns True, it creates the file_path,\n    which will be detected by StopIfExistsHooks\n    and stop training for all workers and the chief. It will\n    also call ``cleanup_checkpoints()``.\n    ';super(EarlyStopHook,A).end(session);A.should_stop=A.early_stop(A._epoch)
		if A._file_path is not _A:
			if A.should_stop:
				with tf.io.gfile.GFile(A._file_path,'wb')as C:C.write('early-stop\n')
				B='early-stopping evaluation at epoch %d'%A._epoch;logging.info(B)
				if A._exit_on_end:raise twml.errors.EarlyStopError(B)
			else:A._latest_checkpoint_path=_A
		A._epoch+=1
	def begin(A):
		'\n    Saves the latest_checkpoint in case it gets superseded by another checkpoint.\n    Remember that when used with train_and_evaluate, the chief saves checkpoints\n    continuouly. The chief could save a checkpoint after evaluation started.\n    So saving the checkpoint at the beginning of evaluation ensures that we\n    later save the correct best checkpoint.\n    ';super(EarlyStopHook,A).begin();A._latest_checkpoint_path=tf.train.latest_checkpoint(A._checkpoint_dir);assert A._latest_checkpoint_path,_C
		try:twml.util.backup_checkpoint(checkpoint_path_prefix=A._latest_checkpoint_path,backup_path=A._eval_checkpoint_path)
		except twml.errors.CheckpointNotFoundError as B:C=_D;raise twml.errors.CheckpointNotFoundError(str(B)+'\n'+C)
class MetricsUpdateHook(GetMetricsHook):
	'\n  A GetMetricsHook augmented with logic to map SessionRun events to metrics updates.\n  It is mainly used by `TrackRun` to persist model metrics via Model Repo.\n  '
	def __init__(B,get_estimator_spec_fn,add_metrics_fn,every_n_iter=_A,every_n_secs=_A):
		'\n    Args:\n      get_estimator_spec_fn:\n        function that returns the current EstimatorSpec.\n        The EstimatorSpec is used to obtain the current eval_metric_ops.\n      add_metrics_fn: `function` callback used to report metrics, called automatically\n        at the end of every epoch.\n      every_n_iter: `int`, log the metrics once every N local\n        steps taken in the current epoch.\n      every_n_secs: `int` or `float`, log the metrics once every N\n        seconds passed in the current epoch. Exactly one of `every_n_iter` and `every_n_secs`\n        should be provided.\n    Raises:\n      ValueError: if `every_n_iter` is non-positive or if not exactly one of `every_n_iter` and\n        `every_n_secs` is set when `add_progress_metrics_fn` is provided.\n    ';C=every_n_secs;A=every_n_iter;D=A is _A and C is _A
		if not D and A and C:raise ValueError('exactly one of every_n_iter and every_n_secs must be provided')
		if A is not _A and A<=0:raise ValueError('invalid every_n_iter=%s.'%A)
		B._timer=NeverTriggerTimer()if D else SecondOrStepTimer(every_secs=C,every_steps=A);B._should_trigger=_B;B._iter_count=0;B._add_metrics_fn=add_metrics_fn
		def E():'\n      Function that returns the current EstimatorSpec.\n        The EstimatorSpec is used to obtain the current eval_metric_ops.\n      ';A=get_estimator_spec_fn();B=A.eval_metric_ops;return{A:B[0]for(A,B)in B.items()}
		super(MetricsUpdateHook,B).__init__(get_metrics_fn=E)
	def report_metrics(A):
		'\n    Triggers a metrics report.\n    ';A._timer.update_last_triggered_step(A._iter_count)
		if A.metric_values is not _A:A._add_metrics_fn(A.metric_values)
	def begin(A):'\n    Triggered before each epoch.\n    ';A._timer.reset();A._iter_count=0;return super(MetricsUpdateHook,A).begin()
	def before_run(A,run_context):'\n    Triggered before each step.\n    ';A._should_trigger=A._timer.should_trigger_for_step(A._iter_count);return super(MetricsUpdateHook,A).before_run(run_context)
	def after_run(A,run_context,run_values):
		'\n    Triggered after each step.\n    '
		if A._should_trigger:A.report_metrics()
		A._iter_count+=1;return super(MetricsUpdateHook,A).after_run(run_context,run_values)
	def end(A,session):'\n    Triggered after each epoch.\n    ';A.report_metrics();return super(MetricsUpdateHook,A).end(session)
class EarlyStopDuration(tf.train.SessionRunHook):
	'\n  Hook that can be used to terminate a job (training or validation) after a certain duration.\n  The hook is fault tolerant, i.e., if a job is allotted 1 hour to run and fails after 45 minutes,\n  then it will only run for 15 minutes once restarted.\n\n  Args:\n    max_duration: \n      A float. When this argument is defined, the job will automatically terminate after\n      `max_duration` seconds if it has not already compeleted. \n    \n    overwrite:\n      A boolean. If set to True, this hook will overwrite the file containing the elapsed time\n      since the beginning of the job. In a distributed setting, this will be used so only one \n      job writes to the file while all others will have read access. In a distributed setting,\n      if all executors have this parameter set to False, then it just means that the hook will \n      not be fault tolerant. When restarted, the job will restart the clock from 0.\n      \n    save_dir:\n      String. A directory (located on a file system that is Tensorflow compatible) where \n      we can store the file which contains the record of the elapsed time. This file is what makes \n      the hook faul tolerant.\n\n    exit_on_end:\n      when exit_on_end is True, twml.errors.EarlyStopError() is triggered to stop the job.\n      This is usually set to True to kill a validation job in a distributed setting.\n   '
	def __init__(A,max_duration,exit_on_end,save_dir,overwrite):
		B=overwrite;A._overwrite=B;A._save_dir=save_dir;A._exit_on_end=exit_on_end;A._max_duration=max_duration;A._last_time_check=datetime.now()
		if B:A.elapsed_time()
	@property
	def elapsed_file_path(self):return os.path.join(self._save_dir,'early_stop_duration.txt')
	def early_stop(A):return A.elapsed_time()>A._max_duration
	def elapsed_time(A):
		C='elapsed_time';D=0
		if tf.io.gfile.exists(A.elapsed_file_path):
			with tf.io.gfile.GFile(A.elapsed_file_path,mode='r')as B:D=json.loads(B.read())[C]
		E=D+(datetime.now()-A._last_time_check).total_seconds();A._last_time_check=datetime.now()
		if A._overwrite:
			tf.io.gfile.makedirs(os.path.dirname(A.elapsed_file_path))
			with tf.io.gfile.GFile(A.elapsed_file_path,mode='w')as B:F={C:E,'max_duration':A._max_duration};B.write(json.dumps(F,indent=2))
		return E
	def before_run(A,run_context):
		if A.early_stop():
			B=f"\n        Stopping job which now exceeded the maximum duration of {A._max_duration} seconds. \n      ";logging.info(B);run_context.request_stop()
			if A._exit_on_end:raise twml.errors.EarlyStopError(B)
class StopAtStepHook(tf.train.StopAtStepHook):
	'\n  Overrides ``tf.train.StopAtStepHook`` so that\n  a ``stop_requested`` property can be accessed to determine\n  if this hook requested a stop.\n  '
	def __init__(A,*B,**C):super(StopAtStepHook,A).__init__(*(B),**C);A._stop_requested=_B
	@property
	def stop_requested(self):' true if this hook requested a stop ';return self._stop_requested
	def after_run(A,run_context,run_values):' sets self.stop_requested to true when requesting a stop ';B=run_context;super(StopAtStepHook,A).after_run(B,run_values);A._stop_requested=B.stop_requested
class StopIfExistsHook(tf.train.SessionRunHook):
	'\n  Hook that requests stop if a file exists.\n  This hook is used with the EarlyStopHook to implement\n  early-stopping for distributed training (tf.estimator.train_and_evaluate).\n  '
	def __init__(A,file_path):'\n    Arguments:\n      file_path:\n        path to file. When this hook detects that the file exists,\n        it requests a stop, which effectively kills this worker.\n    ';A._file_path=file_path;A._stop_requested=_B
	def after_run(A,run_context,run_values):
		if tf.io.gfile.exists(A._file_path):logging.info('Early-stopping file detected; requesting stop');run_context.request_stop();A._stop_requested=True
	@property
	def stop_requested(self):' true if this hook requested a stop ';return self._stop_requested