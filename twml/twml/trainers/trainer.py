# pylint: disable=too-many-lines
"""
``twml.trainers.Trainer`` is a wrapper around `tf.estimator.Estimator
<https://www.tensorflow.org/versions/master/api_docs/python/tf/estimator/Estimator>`_
to expose an easier to use API by
hiding rarely used config knobs and supplying default values.

The `Trainer` facilitates multi-phase training commonly used at Twitter: e.g.
MDL calibration -> MLP training -> Isotonic calibration.
The `Trainer` also facilitates hyperparameters tuning,
with its simple `add_parser_arguments()` method.

Learning rate decay functions
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Please note that we have four learning rate decay functions to choose from.
Additionally, each trainer can only take one learning rate decay function and its parameters.
If that is not the case, it will throw an error.
Also, please note that the learning rate decay is a positional argument and should be placed as
the last argument to the trainer, as you can see in the example above.
The four learning decays options are:

1. inverse_learning_rate_decay:

  The function returns the decayed learning rate. It is computed as:

  ::

    decayed_learning_rate = learning_rate / (1 + decay_rate * global_step /decay_step)
    final_decayed_learning_rate = max(decayed_learning_rate, min_learning_rate)


2. polynomial_learning_rate_decay:

  The function returns the decayed learning rate. It is computed as:

  ::

    global_step = min(global_step, decay_steps)
    decayed_learning_rate = (learning_rate - end_learning_rate) *
                            (1 - global_step / decay_steps) ^ (power) +
                            end_learning_rate


3. piecewise_constant_learning_rate_decay:

  Piecewise constant from boundaries and interval values.

  Example: use a learning rate that's 1.0 for the first 100001 steps, 0.5 for
  the next 10000 steps, and 0.1 for any additional steps.

  ::

    global_step = tf.Variable(0, trainable=False)
    boundaries = [100000, 110000]
    values = [1.0, 0.5, 0.1]
    learning_rate = tf.train.piecewise_constant(global_step, boundaries, values)

4. exponential_learning_rate_decay:

  The function returns the decayed learning rate. It is computed as:

  ::

    decayed_learning_rate = learning_rate * decay_rate ^ (global_step / decay_steps)

"""

import datetime
import functools
import math
from operator import itemgetter
import os
import pprint as pp
import random
from string import Template
import subprocess
import sys
import time
from threading import Thread

from twitter.common.metrics import AtomicGauge
from twitter.deepbird.stats_server import utils as stats_server_utils
from twitter.deepbird.stats_server.stats_exporter import StatsExporter
from twitter.ml.common import metrics
from twitter.ml.common.kubernetes import kubectl_delete_by_name, Resource
from twitter.ml.twml.status import get_distributed_training_job_status, TrainingJobStatus

from absl import logging
from twml.optimizers import LazyAdamOptimizer, optimize_loss, OPTIMIZER_SUMMARIES
from twml.contrib.optimizers import DeepGradientCompressionOptimizer
from twml.tracking import ExperimentTracker
from twml.util import (delete_file_or_dir,
                       get_distributed_training_job_path,
                       sanitize_hdfs_path)
try:
  from urllib import quote as encode_url
except ImportError:
  from urllib.parse import quote as encode_url
import tensorflow.compat.v1 as tf
import tensorflow
import tensorflow_hub as hub

import twitter.ml.twml.kubernetes.status as k8s_status
import twml
import twml.export_output_fns
import twml.learning_rate_decay
import twml.metrics


_CLUSTER_TEMPLATE = Template('''{
  "cluster": {
    "ps": [$PS],
    "chief": [$CHIEF],
    "worker": [$WORKER]
  },
  "task": {"type": "$TYPE", "index": $INDEX}
}
''')


def init_from_checkpoint(init_dir, init_map):
  """
  Wrapper around tf.train.init_from_checkpoint
  """
  if init_dir:
    init_dir = sanitize_hdfs_path(init_dir)
    tf.train.init_from_checkpoint(init_dir, init_map)


class Trainer(object):
  """
  This class wraps ``tf.estimator.Estimator`` to make construction, saving, and loading easier.
  Supports multi-phase training (for example, use a Trainer for MDL calibration, then
  another for training the rest of the model, then another for isotonic calibration).
  The Trainer also implements a training and evaluation loop via the ``learn()`` method.
  Each Trainer is associated to a fixed set of hyper parameters (params), and a single model
  specified by ``build_graph``. Given these constraints, a single Trainer can be called
  multiple times for training and evaluation over multiple epochs.

  However, if you intend to try different sets of hyper-parameters, we recommend you instantiate
  a different Trainer for each such experiment. That way, each experiment can be tracked
  in a different ``save_dir``. Indeed, after calling ``learn``, a Trainer's save_dir will contain
  checkpoints of the model (its graph, and variables), and the history of metrics (for example,
  evaluation accuracy at each epoch), and other store observations like the average time per step.
  The latter metrics can be viewed by pointing
  TensorBoard to the save_dir and accessing TensorBoard via your browser.
  """

  def __init__(self, name, params, build_graph_fn,
               metric_fn=None,
               optimize_loss_fn=None,
               run_config=None,
               save_dir=None,
               init_from_dir=None,
               init_map=None,
               warm_start_from=None,
               profiler_steps=None,
               **kwargs):
    """

    Args:
      name (String):
        string name of this estimator; used as scope names for variables and tensors.
      params (HParams, Namespace, or Dict):
        hyper-parameters to be passed to Estimator constructor.
        Must include params.train_batch_size and params.eval_batch_size.
        Note that params is passed to twml.util.convert_to_hparams() to produce an HParams.
      build_graph_fn:
        A function for building tensorflow graphs.
        This matches TensorFlow Estimator's model_fn signature.
        For example,

        .. code-block:: python

          def build_graph(features, label, mode, params, config=None):
            # Implements a simple binary logistic regression model
            sparse_tf = twml.util.convert_to_sparse(features, params.input_size_bits)

            logits = twml.layers.full_sparse(sparse_tf, 1 << params.input_size_bits, 1)

            if mode == 'infer':
              loss = None
            else:
              loss = tf.nn.sigmoid_cross_entropy_with_logits(labels=label, logits=logits)
              loss = twml.util.weighted_average(loss, features['weights'])

            output = tf.nn.sigmoid(logits)

            return {'output': output, 'loss': loss}

        Args:
          features (dict of Tensor keyed by a string name):
            input tensors.
          mode (tf.estimator.ModeKeys / String):
            one of 'train', 'eval', 'infer'.
          label (Tensor):
            if in ``mode == 'train'`` mode, these contain the corresponding labels for input.
          params (HParams):
            hyper parameters that control how to build a graph.
          config:
            the RunConfig object passed to Estimator constructor.

        This function is expected to return a dictionary containing the following keys:

        * 'output': a node representing model output; required.
        * 'loss': (required) a loss node used for optimization; required for training and
          evaluation.
        * 'train_op': (optional) an operation that minimizes the loss (as output by
          `tf.train.Optimizer.minimize`). If train_op is specified, train_op is used
          for optimization as opposed to loss. Loss is always logged to tensorboard.

        Notes:

        * any tf.summary written inside build graph are logged to tensorboard during training.
        * the ``build_graph_fn`` is called once or twice per epoch (once per training,
          once per evaluation). All data loading (and preprocessing) logic not required
          for serving should be in the ``input_fn`` passed to ``learn``, ``train``,
          ``evalulate``, etc.

      optimize_loss_fn:
        Defaults to Trainer.get_train_op. A function that takes params and loss as arguments
        and returns a training op. The training op is used to update parameters (that is, to learn).
      metric_fn:
        A function that returns the eval_metric_ops dict given graph_output, labels and weights.
        Defaults to None.
        Use ``twml.metrics.get_binary_class_metric_fn()`` to return a ``metric_fn``
        which implements many binary classification metrics.
      run_config (RunConfig):
        optional configuration to be passed to Estimator constructor. Defaults to None.
      save_dir (String):
        optional directory where to save model checkpoints,
        tensorboard event files and trained parameters.
        Overwrites and defaults to run_config.model_dir.
      init_from_dir (String):
        optional directory to load weights from.
        if set to None (the default), do not init from any directory.
      init_map (map from String to String):
        Must be specified if init_from_dir is specified.
        Defines which scopes and variables to load.
        Keys are the variables and scopes to load from the directory.
        Values are the destinations (in the current graph) to load into.
        See tf.init_from_checkpoint for more information.
        Note that the the trainer prepends name_scope of the form `name`/model/ to the name_scope
        of any variable defined inside `build_graph_fn` and this should be taken into account when
        defining the values.
      warm_start_from:
        Optional string filepath to a checkpoint to warm-start from,
        or a tf.estimator.WarmStartSettings object to fully configure warm-starting.
        If the string filepath is provided instead of a WarmStartSettings,
        then all variables are warm-started, and it is assumed that
        vocabularies and Tensor names are unchanged.
      profiler_steps (Integer):
        Defaults to None. If set defines the number of steps in the
        `tf.train.ProfileHook <https://www.tensorflow.org/api_docs/python/tf/train/ProfilerHook>`_.
        Captures CPU/GPU profiling information every ``profiler_steps`` steps or seconds.
        When executing ``learn``, ``train`` or ``predict`` methods,
        with ``profiler_steps`` set to a number,
        a ``timeline_X.json`` file is created in the save_dir. This file contains profiling data
        storedin Chrome trace format. To view stored data, use the Chrome browser to follow
        these steps:

        1) Go to the page chrome://tracing.
        2) In the upper left corner, you will find Load button.
        3) Press it and load our JSON file, which can be found in the ``save_dir``

        *Warning*: This could create too many these json files which can be a potential problem,
        e.g. for  HDFS there is normally quota forfile count, so use with caution.

        Note: this argument is ignored when a non-None ``hooks`` argument is pasesd to
        ``train``, ``learn``, or ``predict`` methods. The hook can be added manually by passing
        ``trainer.train(..., hooks=myhooks.extend(trainer.get_train_hooks()))``, for example.
    """

    if tensorflow.__version__ >= "2.0":
      RuntimeError("Trainer not yet supported for Tensorflow >= 2.0")

    self._name = name
    self._build_graph_fn = build_graph_fn
    self._metric_fn = metric_fn
    self._tensorboard_handle = None
    self._current_estimator_spec = None  # holds the current estimator spec
    self._profiler_steps = profiler_steps
    self._export_output_fn = None
    self._is_early_stopping = False

    # NOTE: Sanitize all HDFS paths first.
    save_dir = sanitize_hdfs_path(save_dir)
    init_from_dir = sanitize_hdfs_path(init_from_dir)

    # warm_start_from can be of type tf.estimator.WarmStartSettings.
    if isinstance(warm_start_from, str):
      warm_start_from = sanitize_hdfs_path(warm_start_from)

    # convert to twitter.deepbird.hparam.hparam.HParams object
    params = twml.util.convert_to_hparams(params)

    # keep a copy of the params because calling self._estimator.params creates a deepcopy
    self._params = params
    self.check_params()

    self._using_hogwild = True if os.environ.get('TWML_HOGWILD_PORTS') else False
    # configure Hogwild (needs to be called before RunConfig is created)
    self._hogwild_setup()

    if not run_config:
      session_config = tf.ConfigProto()
      # By default each process tries to allocate (almost) all of the memory.
      # This option ensures the gpu memory grows dynamically instead.
      session_config.gpu_options.allow_growth = True  # pylint: disable=no-member

      if 'TWML_NUM_CPUS' in os.environ:
        num_available_cpus = int(os.environ.get("TWML_MESOS_CPU", "8"))
        if params.num_mkl_threads > 1:
          os.environ["OMP_NUM_THREADS"] = str(params.num_mkl_threads)
          os.environ["MKL_NUM_THREADS"] = str(params.num_mkl_threads)
          session_config.inter_op_parallelism_threads = num_available_cpus // params.num_mkl_threads
          session_config.intra_op_parallelism_threads = params.num_mkl_threads

      run_config = tf.estimator.RunConfig(
        session_config=session_config,
        keep_checkpoint_max=self._params.get('keep_checkpoint_max', 20),
        log_step_count_steps=10000,
        save_checkpoints_secs=self._params.get('save_checkpoints_secs', 600),
        tf_random_seed=self._tf_random_seed())
    elif not isinstance(run_config, tf.estimator.RunConfig):
      raise ValueError("Expecting run_config argument of type None or tf.estimator.RunConfig"
        "Got %s instead." % type(run_config).__name__)
    elif os.environ.get('TWML_HOGWILD_PORTS'):
      raise ValueError("Custom RunConfig not supported with Hogwild")

    if run_config.model_dir is None and save_dir is None:
      raise ValueError(
          "Expecting either save_dir or run_config.model_dir to be specified. Got None for each.")
    elif run_config.model_dir is None:
      run_config = run_config.replace(model_dir=save_dir)
    elif save_dir is None:
      save_dir = run_config.model_dir

    self._save_dir = save_dir
    self.experiment_tracker = ExperimentTracker(self._params, run_config, self._save_dir)

    # Check if should delete the tsd running this training job. In certain use case when 
    # there are other tf operations following trainer.train_and_evaluate (or trainer.learn),
    # additional state files need to be specified to ensure those steps are executed after job restart.
    kwargs['gke_state_files'] = kwargs.get('gke_state_files', ['_SUCCESS'])
    self._maybe_del_tsd_exit(kwargs['gke_state_files'])
    logging.info("Checkpoint and event files will be saved at save_dir=%s", save_dir)
    self._optimize_loss_fn = self.get_train_op if optimize_loss_fn is None else optimize_loss_fn

    # overwrite the current save_dir
    if self._params.get('overwrite_save_dir') and tf.io.gfile.exists(self._save_dir):
      logging.info("Trainer overwriting existing save directory: %s (params.overwrite_save_dir)"
                   % self._save_dir)
      # if distributed or hogwild:
      if self._params.get('distributed', False):
        # sleep for 30 seconds to allow each worker to get to this point.
        time.sleep(30)
        if run_config.is_chief:
          logging.info("Chief deleting the save_dir now")
          delete_file_or_dir(self._save_dir)
        # sleep for 30 seconds to allow each worker to get to this point.
        time.sleep(30)
      else:
        delete_file_or_dir(self._save_dir)

    # Exposing stats to a /vars.json endpoint that will be collected
    # by the absorber
    if self._params.get('stats_port'):
      try:
        stats_server_utils.start_stats_server(self._params.get('stats_port'), self._save_dir)
      except Exception as err:
        logging.error('Failed to start the stats server. Error: %s', str(err))

    checkpoint = os.path.join(self._save_dir, 'checkpoint')
    if tf.io.gfile.exists(checkpoint):
      logging.info("The provided save_dir directory %s already exists."
                   " Training will be resumed."
                   % checkpoint)

    self._maybe_restore_checkpoint = lambda: init_from_checkpoint(init_from_dir, init_map)

    if init_from_dir is not None and init_map is None:
      raise ValueError("Need to provide init_map when init_from_dir is provided.")

    if not tf.io.gfile.exists(self._save_dir):
      # so tensorboard can point to a directory that exists
      tf.io.gfile.mkdir(self._save_dir)

    self._estimator = tf.estimator.Estimator(
      model_fn=self._model_fn,
      params=self._params,  # HParams
      config=run_config,  # RunConfig
      warm_start_from=warm_start_from,
      model_dir=self._save_dir,  # By this point it is same as run_config.model_dir
    )

    # Log parameters that are used to construct trainer. This allows people to see default values.
    logging.info("Trainer constructed using the following parameters: ")
    pp_params = pp.pformat(self._params.values())
    logging.info(pp_params)

    # Start TensorBoard
    if self._params.get('disable_tensorboard', False):
      logging.info("Skipping launching TensorBoard [--disable_tensorboard is set]")
    elif "tensorboard_port" in self._params.values() and self._params.tensorboard_port is not None:
      self.start_tensorboard(self._params.tensorboard_port)

    # Export gauge that will track whether a model was exported
    self.stats_exporter = StatsExporter("twml.trainer")
    self.export_gauge = AtomicGauge('export_model')
    self.stats_exporter.register_metrics(self.export_gauge)

  def _hogwild_setup(self):
    """
    Setup the parameters required for hogwild.
    """
    self._num_workers = self._params.get('num_workers') or 1
    logging.info("NUM_WORKERS: %d", self._num_workers)
    if self._num_workers <= 1:
      self._ports = None
      return

    # a hogwild job is considered distributed
    if 'distributed' in self._params:
      self._params.set_hparam('distributed', True)
    else:
      self._params.add_hparam('distributed', True)

    ports = os.environ.get('TWML_HOGWILD_PORTS')
    if ports:
      self._ports = [int(port) for port in ports.strip().split(",")]
      if (self._num_workers + 1!= len(self._ports)):
        raise ValueError("Number of (workers + PS) and ports need to match")
    else:
      if self._num_workers > 1:
        raise ValueError("TWML_HOGWILD_PORTS needs to be set to use hogwild training")

    # Split the number of data threads across multiple workers
    num_threads = self._params.get('num_threads')
    num_threads_per_worker = int(math.ceil(float(num_threads) / self._num_workers))
    self._params.set_hparam('num_threads', num_threads_per_worker)

    hogwild_task_type = os.environ.get('TWML_HOGWILD_TASK_TYPE')
    hogwild_task_id = int(os.environ.get('TWML_HOGWILD_TASK_ID'))
    os.environ['TF_CONFIG'] = self._get_cluster_config(hogwild_task_type, hogwild_task_id)

  def _tf_random_seed(self):
    """ Returns user set seed and deal with Hogwild multiple seeds """
    tf_random_seed = self._params.get('tf_random_seed', None)
    if tf_random_seed is None:
      return None
    elif self.using_hogwild and os.environ.get('TWML_HOGWILD_TASK_TYPE') == 'worker':
      # chief (tf_random_seed), worker_0 (tf_random_seed + 1), worker_1 (tf_random_seed + 2)...
      return tf_random_seed + 1 + int(os.environ.get('TWML_HOGWILD_TASK_ID'))
    else:
      return tf_random_seed

  def check_params(self):
    """ Verify that params has the correct key,values """
    param_values = self._params.values()

    if 'train_batch_size' in param_values:
      if not isinstance(self._params.train_batch_size, int):
        raise ValueError("Expecting params.train_batch_size to be an integer.")
      if self._params.train_batch_size <= 0:
        raise ValueError("train_batch_size needs to be positive")
    else:
      raise ValueError("train_batch_size needs to be present in params")

    if 'eval_batch_size' in param_values:
      if not isinstance(self._params.eval_batch_size, int):
        raise ValueError("Expecting params.eval_batch_size to be an integer.")
      if self._params.eval_batch_size <= 0:
        raise ValueError("eval_batch_size needs to be positive.")
    else:
      self._params.add_hparam('eval_batch_size', self._params.train_batch_size)

    if (self._params.get('distributed_training_cleanup') and
      not self._params.get('distributed')):
      # we only need to support training discontinuation for distributed training
      # bc we are still using TSDs on GKE for distributed training
      raise ValueError(
        "Expecting params.distributed to be set if "
        "params.distributed_training_cleanup is set."
      )

  def _get_cluster_config(self, name, index):
    """Create a tensorflow cluster config from ports, name and index"""
    host = '"localhost:%d"'
    ps = host % self._ports[0]
    chief = host % self._ports[1]
    workers = ", ".join([host % port for port in self._ports[2:]])
    config = _CLUSTER_TEMPLATE.substitute(
      PS=ps,
      CHIEF=chief,
      WORKER=workers,
      TYPE=name,
      INDEX=index,
    )
    return config

  @property
  def current_estimator_spec(self):
    """
    returns the current estimator (warning: often reset)
    """
    return self._current_estimator_spec

  @property
  def estimator(self):
    """ returns estimator encapsulated by Trainer """
    return self._estimator

  @property
  def num_workers(self):
    """ returns number of workers """
    return self._estimator.config.num_worker_replicas

  @property
  def worker_index(self):
    """
    returns index of worker in the cluster
    chief has index 0
    non-chief workers have indices 1 through (num_workers - 1)
    """
    return self._estimator.config.global_id_in_cluster

  @property
  def using_hogwild(self):
    """ returns a bool indicating whether hogwild is being used """
    return self._using_hogwild

  def set_estimator(self, estimator):
    """ sets the estimator used internally by Trainer """
    if not isinstance(estimator, tf.estimator.Estimator):
      raise ValueError("Expecting tf.estimator.Estimator")
    self._estimator = estimator
    self._params = self.estimator.params

  @property
  def params(self):
    """
    returns the hyper-parameters passed to the constructor.
    """
    return self._params

  @staticmethod
  def add_parser_arguments():
    """
    Add common commandline args to parse for the Trainer class.
    Typically, the user calls this function and then parses cmd-line arguments
    into an argparse.Namespace object which is then passed to the Trainer constructor
    via the params argument.

    See the `code <_modules/twml/argument_parser.html#get_trainer_parser>`_
    for a list and description of all cmd-line arguments.

    Returns:
      argparse.ArgumentParser instance with some useful args already added.
    """
    return twml.argument_parser.get_trainer_parser()

  @staticmethod
  def get_train_op(params, loss):
    """
    Return a training Op, that is, a `twml.optimizers.optimize_loss
    <https://www.tensorflow.org/api_docs/python/tf/contrib/layers/optimize_loss>`_
    instance given params and loss.
    This method can be overwritten by passing the optimize_loss_fn to the Trainer
    constructor.

    Args:
      params:
        tensorflow.contrib.training.HParams instance. Recognizes the optimizer, optimizer_summaries,
        gradient_noise_scale, clip_gradients and learning_rate_decay (including
        other learning rate decay arguments).
      loss:
        scalar Op returned by the build_graph that specifies the training loss to
        be minimized.
    """
    optimizer = params.get('optimizer')

    if not optimizer:
      optimizer = 'SGD'

    if optimizer == 'LazyAdam':
      optimizer = LazyAdamOptimizer

    if optimizer == 'DGC':
      optimizer = DeepGradientCompressionOptimizer(
          learning_rate=params.learning_rate,
          use_locking=False,
          name="Sparse",
          density=params.get('dgc_density'),
          density_decay=params.get('dgc_density_decay'),
          density_decay_steps=params.get('dgc_density_decay_steps'),
          density_decay_rate=params.get('dgc_density_decay_rate'),
          min_density=params.get('dgc_min_density'),
          accumulation=params.get('dgc_accumulation')
      )

    summaries = ['loss']
    if params.get('show_optimizer_summaries'):
      summaries = OPTIMIZER_SUMMARIES

    train_op = optimize_loss(
      loss=loss,
      global_step=tf.train.get_global_step(),
      optimizer=optimizer,
      learning_rate=params.learning_rate,
      summaries=summaries,
      colocate_gradients_with_ops=True,
      gradient_noise_scale=params.get('gradient_noise_scale'),
      clip_gradients=params.get('clip_gradients'),
      learning_rate_decay_fn=twml.learning_rate_decay.get_learning_rate_decay_fn(params)
    )
    return train_op

  def export_model_effects(self, export_path, feature_spec=None, log_features=True):

    # DO NOT CHANGE THE ORDER.
    # This needs to be done before registering the model.
    if feature_spec:
      if log_features:
        features = feature_spec['features']
        feature_names = ['.'.join(features[fid]['featureName'].split('.')[1:]) for fid in features.keys()]
        features_to_log = ','.join(feature_names)
        try:
          model_hash = self.experiment_tracker.compute_model_hash(export_path)
          metrics.log_usage('dbv2', 'export_model_effects', 'v1', custom_attrs=[model_hash, "feature config present", features_to_log])
        except:  # noqa: T803
          logging.info("Failed to log Feature Config features")

      twml.contrib.export.export_fn.export_feature_spec(export_path, feature_spec)
      export_start_time = time.time()
      self.experiment_tracker.export_feature_spec(feature_spec)
      logging.info("Exported feature spec to ML Metastore in %s seconds.", time.time() - export_start_time)

    self.experiment_tracker.register_model(str(export_path))
    self.export_gauge.increment()

  @property
  def best_or_latest_checkpoint(self):
    if self._is_early_stopping:
      best_checkpoint_path = os.path.join(self._save_dir, "best_checkpoint")
      checkpoint_path = tf.train.latest_checkpoint(best_checkpoint_path)
      # Return best checkpoint if necessary
      if checkpoint_path:
        return checkpoint_path
      else:
        raise ValueError("Best checkpoint not found at %s." % best_checkpoint_path)
    else:  # Fallback to latest checkpoint from save directory
      return self.latest_checkpoint

  @property
  def latest_checkpoint(self):
    return self.estimator.latest_checkpoint()

  def export_model(self, serving_input_receiver_fn,
                   export_output_fn=None,
                   export_dir=None, checkpoint_path=None,
                   feature_spec=None,
                   log_features=True):
    """
    Export the model for prediction. Typically, the exported model
    will later be run in production servers. This method is called
    by the user to export the PREDICTgraph to disk.

    Internally, this method calls `tf.estimator.Estimator.export_savedmodel
    <https://www.tensorflow.org/api_docs/python/tf/estimator/Estimator#export_savedmodel>`_.

    Note that a valid self._export_output_fn is required.
    If export_ouput_fn is provided, it is used to set the self._export_output_fn.

    Args:
      serving_input_receiver_fn:
        function preparing the model for inference requests.
        This funtion returns the ``features`` dict passed to ``build_graph``.
      export_dir:
        directory to export a SavedModel for prediction servers.
        Defaults to ``[save_dir]/exported_models``.
      checkpoint_path:
        the checkpoint path to export. If None (the default), the most recent checkpoint
        found within the model directory is chosen.
      export_output_fn:
        Function to export the graph_output (output of build_graph) for
        prediction. Takes a graph_output dict as sole argument and returns
        the export_output_fns dict.
        Defaults to `twml.export_output_fns.default_output_fn`.

    Return:
      returns a string path to exported directory.

    # set the export output function
    """
    if not self.is_chief():
      logging.info("Trainer.export_model ignored due to the process not being chief.")
      return

    self._export_output_fn = export_output_fn or twml.export_output_fns.default_output_fn

    if not callable(self._export_output_fn):
      raise RuntimeError(
        "Expecting export_output_fn function. Got %s."
        % type(self._export_output_fn).__name__)

    if export_dir:
      export_dir = sanitize_hdfs_path(export_dir)

    if checkpoint_path:
      checkpoint_path = sanitize_hdfs_path(checkpoint_path)
    else:
      checkpoint_path = self.best_or_latest_checkpoint

    # actually export the model using the Estimator API
    export_path = self._estimator.export_savedmodel(
      export_dir_base=export_dir or os.path.join(self._save_dir, 'exported_models'),
      serving_input_receiver_fn=serving_input_receiver_fn,
      checkpoint_path=checkpoint_path)

    # export_path is bytes, need to convert to string for python3 to work.
    logging.info("The exported model path is: " + str(export_path))

    self.export_model_effects(export_path, feature_spec, log_features)

    return export_path

  def _model_fn(self, features, labels, mode, params, config=None):
    """
    returns tf.estimator.EstimatorSpec that can be used with tf.estimator.Estimators.
    You would probably never need to modify this method.
    Instead, you should override build_graph, which this method calls.

    Args:
      features:
        Dict of input tensors.
      labels:
        Tensor of target labels.
      mode:
        an instance of tf.estimator.ModeKeys.
        Typically used to toggle TRAINing or EVALuation.
      params:
        HParams object containing hyper-parameters.
    """
    # pylint: disable=too-many-branches
    if isinstance(features, dict):
      weights = features.get('weights', None)
    else:
      weights = None

    with tf.variable_scope(self._name + '/model'):
      graph_output = self._build_graph_fn(features, labels, mode, params, config)
      loss = graph_output['loss'] if 'loss' in graph_output else None

    self._maybe_restore_checkpoint()

    with tf.variable_scope(self._name + '/optim'):
      train_op = None
      if mode == tf.estimator.ModeKeys.TRAIN:
        if 'train_op' in graph_output:
          train_op = graph_output['train_op']
          graph_output['train_op'] = None  # remove from preds to prevent error
        elif loss is not None:
          train_op = self._optimize_loss_fn(params, loss)

        if params.get('train_log_metrics') and self._metric_fn:
          metric_ops = self._metric_fn(graph_output=graph_output, labels=labels, weights=weights)
          for metric_name in metric_ops:
            tf.summary.scalar(
              name="training_metric_" + metric_name,
              tensor=metric_ops[metric_name][1])  # index 0 contains value_op, 1 contains update_op

    if mode == tf.estimator.ModeKeys.PREDICT and self._export_output_fn is not None:
      # note that this is ignored by the predict method.
      # Estimator only uses export_output_fn for export_model.
      export_outputs = self._export_output_fn(graph_output)
    else:
      export_outputs = None

    if mode == tf.estimator.ModeKeys.EVAL and self._metric_fn:
      eval_metric_ops = self._metric_fn(graph_output=graph_output, labels=labels, weights=weights)
    else:
      eval_metric_ops = None

    # None and loss (scalar, not sliceable by TFMA) should be removed from the graph_output
    preds = {key: graph_output[key] for key in graph_output if (graph_output[key] is not None) and (key is not 'loss')}

    init_feed_dict = twml.contrib.initializers.get_init_feed_dict()
    scaffold = tf.train.Scaffold(init_feed_dict=init_feed_dict)

    # Clear the init feed collection to avoid serializing the initializers.
    twml.contrib.initializers.clear_init_feed_collection()

    # save estimator for use by later methods and hooks (warning: often reset)
    self._current_estimator_spec = tf.estimator.EstimatorSpec(
      mode=mode,
      predictions=preds,
      export_outputs=export_outputs,
      loss=loss,
      train_op=train_op,
      eval_metric_ops=eval_metric_ops,
      scaffold=scaffold,
    )

    return self._current_estimator_spec

  def get_train_hooks(self):
    """Return SessionRunHooks used during training.

    By default training uses one hooks `tf.train.StepCounterHook` for monitoring step speed.

    If self._profiler_steps is set then we also use the ProfilerHook `tf.train.ProfilerHook`
    for monitoring the profile.

    """
    # Instead of having every_n_steps be a constant number,
    # change it dynamically based on batch size.
    # Ideally we should be using every_n_secs, but that seems buggy as of 1.7.
    # The every_n_steps = 20K / batch_size
    every_n_steps = ((2048 * 100) // self._params.train_batch_size)
    step_counter = tf.train.StepCounterHook(
      every_n_steps=every_n_steps, output_dir=self._save_dir
    )
    train_hooks = [step_counter]

    if self._profiler_steps is not None:
      if not self._params.get('distributed') or self._estimator.config.is_chief:
        profiler = tf.train.ProfilerHook(
          save_steps=self._profiler_steps,
          output_dir=self._save_dir
        )
        train_hooks.append(profiler)

    return train_hooks

  def is_task_type(self, name):
    """
    Helper function to specify if the current process is of the given worker type.
    Note: This an only be called *after* self._hogwild_setup() is called in __init__()
    """
    if os.environ.get('TF_CONFIG'):
      if self._estimator.config.task_type == name:
        return True
      else:
        return False
    return True

  def is_evaluator(self):
    """
    Helper function to let you know if the worker is evaluator.
    Note: This an only be called *after* self._hogwild_setup() is called in __init__()
    """
    return self.is_task_type("evaluator")

  def is_chief(self):
    """
    Helper function to let you know if the worker is chief.
    Note: This an only be called *after* self._hogwild_setup() is called in __init__()
    """
    return self.is_task_type("chief") or self.is_task_type("master")

  def is_ps(self):
    """
    Helper function to let you know if the task is parameter server.
    """
    if os.environ.get('TF_CONFIG') and self._estimator.config.task_type == 'ps':
      return True
    return False

  def _exit_ps_after_training_complete(self):
    """
    Helper function to shutdown parameter server after training job complete (either succeed or failed).
    """
    if not self.is_ps():
      return

    # No need to exit ps if on the same machine
    if os.environ.get('TWML_HOGWILD_PORTS'):
      return

    if self._params.get('disable_auto_ps_shutdown', False):
      logging.info("Skip shutting down parameter server after training complete [--disable_auto_ps_shutdown is set]")
      return

    # checking job status is different on gke vs aurora
    if self._is_on_gke():
      get_job_status = functools.partial(
        k8s_status.get_training_job_status,
        cluster=None,
        namespace=os.environ['TWML_JOB_ROLE'],
        environment=os.environ['TWML_JOB_ENV'],
        job_name=os.environ['TWML_JOB_NAME'],
        using_tsd=True)
    else:
      get_job_status = functools.partial(
        get_distributed_training_job_path,
        base_job_path=get_distributed_training_job_path()
      )

    def wait_complete_then_exit():
      retry_max = 60
      retry = 0
      while True:
        try:
          training_status = get_job_status()
          if training_status == TrainingJobStatus.FINISHED:
            logging.info("Distributed training job succeed, shutting down parameter server.")
            os._exit(0)
          elif training_status == TrainingJobStatus.FAILED:
            logging.info("Distributed training job failed, shutting down parameter server.")
            os._exit(0)
          elif training_status == TrainingJobStatus.NOT_FOUND:
            raise Exception("Distributed training job status not found.")
          else:
            poke_interval = random.randrange(60, 90)  # prevent spike QPS to aurora endpoint
            time.sleep(poke_interval)
            retry = 0
        except Exception as e:
          if retry >= retry_max:
            raise e  # only exception in this thread, won't fail parameter server thread
          retry += 1
          poke_interval = random.randrange(60, 90) + retry * 10
          logging.warn("Error getting distributed training job status, will retry after %s seconds." % poke_interval)
          time.sleep(poke_interval)
    Thread(target=wait_complete_then_exit).start()

  def get_eval_hooks(self):  # pylint: disable=no-self-use
    """ Return SessionRunHooks used during evaluation."""
    return None

  def get_predict_hooks(self):
    """ Return hooks used during prediction.
    If profiler_steps is set in the constructor to the Trainer,
    we pass a tf.Train.ProfilerHook to the estimator's predict function.
    """
    hooks = []
    if self._profiler_steps is not None:
      profiler = tf.train.ProfilerHook(
        save_steps=self._profiler_steps,
        output_dir=self._save_dir
      )
      hooks.append(profiler)
    return hooks

  def learn(self, train_input_fn=None, eval_input_fn=None,
            train_max_steps=None,
            train_steps=None, eval_steps=None,
            train_hooks=None, eval_hooks=None,
            early_stop_metric=None, early_stop_patience=-1,
            early_stop_minimize=True, early_stop_tolerance=0, start_epoch=0,
            exporters=None, export_output_fn=None, max_duration=None):
    """
    Train and evaluate the estimator for ``train_max_steps`` steps.
    Each epoch involves ``train_steps`` training steps followed
    by ``eval_steps`` evaluation steps. Note that each step
    is a ``session.run()``, that is, each batch is a step.

    Args:
      train_max_steps:
        maximum number of global steps of training to run.
        Defaults to params.train_max_steps.
        None-values cause learn() to terminate after *one* call to train() and evaluate(),
        which is usually useful when using train_steps=-1
        Non-positive values trains indefinitely in a loop (use with caution),
        which is usually useful when used with early stopping.
      train_steps:
        number of training steps per epoch. For example, 100 means each
        training epoch will end after processing 100 batches.
        Defaults to params.train_steps.
        Non-positive values and None-values go through the entire training set each epoch.
      eval_steps:
        number of evaluation steps per epoch.
        Defaults to params.eval_steps.
        Non-positive values and None-values go through the entire evaluation set each epoch.
      train_input_fn:
        Function to iterate through training set. It is passed to estimator.train.
      eval_input_fn:
        Function to iterate through evaluation set. It is passed to estimator.evaluate.
      train_hooks:
        List of SessionRunHooks uses for training. Defaults to self.get_train_hooks().
      eval_hooks:
        List of SessionRunHooks uses for evaluation. Defaults to self.get_eval_hooks()
      start_epoch:
        The epoch from which to start learn. If you want to do training and evaluation
        for N epochs, you can call ``learn()`` in a loop as follows:
      exporters:
        List of exporters called at the end of each evaluation run.
        Defaults to none.
      export_output_fn:
        The output format to use for exported models.
        Only used if exporters is not None.

        .. code-block:: python

          for epoch in range(1,max_epoch):
            trainer.learn(start_epoch=epoch)

    Early-stopping arguments:
      early_stop_metric:
        String specifying the metric to early-stop on. Required with positive
        ``early_stop_patience``. For example, 'accuracy', 'accuracy_0', 'loss', etc.
        The string is used to extract the relevant tensor Op from the dict returned by
        the get_eval_metric_ops method. For ``metrics`` pass to the constructor,
        the string is one of those. For multi-class (that is, multi-metric)
        metrics, the string may be appended with a ``_0``, ``_1``, etc. or one
        of the ``multi_metric_names`` (one per class).
      early_stop_patience:
        Maximum number of epochs to wait for an improvement in the early_stop_metric
        before breaking off training. For example, a patience of 10 means that
        training will have 10 epochs to improve the metric before it is killed.
        Whenever the metric is improved before running out of patience,
        patience is reset to ``early_stop_patience``.
        Defaults to -1 (that is, no early-stopping).
      early_stop_minimize:
        Set this to True (the default) for metrics that need to be minimized
        (like ``loss``). Metrics like ``accuracy`` that need to be maximized
        should set this to False.
      early_stop_tolerance:
        A non-negative tolerance for comparing early_stop_metric.
        E.g. when maximizing the condition is current_metric > best_metric + tolerance.
        Defaults to 0.
      max_duration:
        A float. When this argument is defined, the job will automatically terminate after
        `max_duration` seconds if it has not already compeleted. 

    Returns:
      The directory where the checkpoints were saved.
      That is, save_dir.
      You can point TensorBoard to this directory to get metrics,
      or pass it to another Trainer via ``init_from_dir`` when doing
      multi-phase training.
    """
    # pylint: disable=too-many-branches

    if not callable(train_input_fn):
      raise ValueError("Expecting callable train_input_fn function")
    if not callable(eval_input_fn):
      raise ValueError("Expecting callable eval_input_fn function")

    if os.environ.get('TF_CONFIG'):
      raise ValueError("trainer.learn() can not be used with distributed / hogwild setups")

    if exporters and export_output_fn:
      self._export_output_fn = export_output_fn

    train_hooks = self.get_train_hooks() if train_hooks is None else train_hooks
    eval_hooks = self.get_eval_hooks() if eval_hooks is None else eval_hooks
    eval_hooks = [] if eval_hooks is None else eval_hooks

    if train_max_steps is None:
      train_max_steps = self.params.get('train_max_steps')

    if train_steps is None:
      train_steps = self.params.train_steps
    if train_steps <= 0:
      train_steps = None

    if eval_steps is None:
      eval_steps = self.params.eval_steps
    if eval_steps <= 0:
      eval_steps = None

    if early_stop_patience > 0:
      assert train_max_steps is not None, "Early stopping and max_steps=None are not compatible."
      # prepare early stopping hook (which also handles logic here)
      self._is_early_stopping = True
      early_stop_hook = twml.hooks.EarlyStopHook(
        metric=early_stop_metric,
        checkpoint_dir=self._save_dir,
        patience=early_stop_patience,
        minimize=early_stop_minimize,
        tolerance=early_stop_tolerance,
        get_estimator_spec_fn=lambda: self.current_estimator_spec,
        start_epoch=start_epoch)
      # add early stop hook to eval hooks
      eval_hooks.append(early_stop_hook)

    if max_duration is not None:
      train_early_stop_duration_hook = twml.hooks.EarlyStopDuration(
        max_duration=max_duration,
        exit_on_end=False,
        save_dir=self._save_dir,
        overwrite=True,
      )
      train_hooks.append(train_early_stop_duration_hook)

      eval_early_stop_duration_hook = twml.hooks.EarlyStopDuration(
        max_duration=max_duration,
        exit_on_end=False,
        save_dir=self._save_dir,
        overwrite=True,
      )
      eval_hooks.append(eval_early_stop_duration_hook)

    if not self._is_early_stopping:
      if (train_max_steps is not None) and (train_max_steps <= 0):
        if ((max_duration is not None) and (max_duration < 0)) or (max_duration is None):
          logging.warn("train.max_steps is non-positive, and no early or duration stopping is configured. "
                      "Training job will loop forever.")

    if train_max_steps is not None and train_max_steps > 0:
      # we can't pass max_steps AND steps to estimator.train.
      # so we pass steps to estimator.train and max_steps to this hook instead...
      stop_at_step_hook = twml.hooks.StopAtStepHook(last_step=train_max_steps)
      train_hooks.append(stop_at_step_hook)

    with self.experiment_tracker.track_experiment(eval_hooks,
                                                  lambda: self.current_estimator_spec):
      # alternate training and evaluation epochs
      epoch = start_epoch
      while True:
        logging.info("Training epoch %d", epoch)
        self._estimator.train(train_input_fn, steps=train_steps, hooks=train_hooks)

        logging.info("Evaluating epoch %d", epoch)
        eval_result = self._estimator.evaluate(
          eval_input_fn, steps=eval_steps, hooks=eval_hooks)

        if exporters:
          checkpoint_path = self.estimator.latest_checkpoint()
          for exporter in exporters:
            export_path = os.path.join(self._save_dir, "export", exporter.name)
            exporter.export(
              estimator=self.estimator, export_path=export_path,
              checkpoint_path=checkpoint_path, eval_result=eval_result,
              is_the_final_export=False)

        # If train_max_step is none. Terminate after one loop.
        if train_max_steps is None:
          break

        # If stop_at_step_hook requested a stop, break
        if train_max_steps > 0 and stop_at_step_hook.stop_requested:
          break

        # early-stopping logic is handled internally by the hook
        if early_stop_patience > 0 and early_stop_hook.should_stop:
          # but we still need to break here
          break
        epoch += 1

      self.write_state_to_disk(save_dir=self._save_dir, filename='_SUCCESS')

    return self._save_dir

  def get_train_spec(self, input_fn, max_steps=None, hooks=None):
    """Get the TrainSpec used by ``tf.train.train_and_evaluate``."""
    if not callable(input_fn):
      raise ValueError("Expecting callable train_input_fn")

    if max_steps is None:
      max_steps = self.params.train_max_steps

    if max_steps is not None and max_steps <= 0:
      max_steps = None

    hooks = self.get_train_hooks() if hooks is None else hooks

    return tf.estimator.TrainSpec(input_fn=input_fn,
                                  max_steps=max_steps,
                                  hooks=hooks)

  def get_eval_spec(self, input_fn, steps=None, delay=None, period=None,
                    hooks=None, exporters=None):
    """Get the EvalSpec used by ``tf.train.train_and_evaluate``."""
    if not callable(input_fn):
      raise ValueError("Expecting callable eval_input_fn")

    if steps is None:
      steps = self.params.eval_steps

    if steps <= 0:
      steps = None

    if delay is None:
      delay = self.params.eval_delay

    if period is None:
      period = self.params.eval_period

    hooks = self.get_eval_hooks() if hooks is None else hooks

    eval_name = self.params.get("eval_name", None)

    return tf.estimator.EvalSpec(input_fn=input_fn,
                                 steps=steps,
                                 name=eval_name,
                                 start_delay_secs=delay,
                                 throttle_secs=period,
                                 hooks=hooks,
                                 exporters=exporters)

  def train_and_evaluate(self, train_input_fn=None, eval_input_fn=None,
                         train_max_steps=None, eval_steps=None,
                         eval_delay=None, eval_period=None,
                         train_hooks=None, eval_hooks=None,
                         early_stop_metric=None, early_stop_patience=-1,
                         early_stop_minimize=True, early_stop_tolerance=0, exporters=None,
                         export_output_fn=None, max_duration=None):
    """
    Train and evaluate the estimator for ``train_max_steps``
    using ``tf.estimator.train_and_evaluate``.
    With a cluster configuration provided in the ``TF_CONFIG`` environment variable, this method
    can be used for distributed training (multi-node or multi-process).
    Unlike the ``learn`` method, training is continuous with ``train_max_steps``.
    For distributed use case, evaluation happens periodically.
    That is, after ``eval_delay`` seconds, an evaluation epoch of ``eval_step`` steps
    occurs every ``eval_period`` seconds. Evaluation happens on the most recent checkpoint.
    TF defaults to saving checkpoints every 10 mins.
    For local use case, training occurs for train_max_steps epochs followed by a
    single evaluation. For local use case we therefore recommend using learn() instead
    as it provides early-stopping and multiple evaluations.

    ``train_and_evaluate`` will evaluate for ``eval_steps`` every ``eval_period`` seconds.
    It will stop after ``train_steps`` is reached.

    You must ensure that all workers/servers are assigned the same `save_dir`.

    .. Note::

      If the TF_CONFIG environment variable is set, this function assumes its running a distribute job.

    Args:
      train_input_fn:
        Function to iterate through training set. It is passed to estimator.train_and_evalute
      eval_input_fn:
        Function to iterate through evaluation set. It is passed to estimator.train_and_evalute.
      train_max_steps:
        maximum number of global steps of training to run.
        Defaults to params.train_max_steps.
        Non-positive values and None-values train indefinitely (use with caution).
      eval_steps:
        number of steps per evaluation.
        Defaults to params.eval_steps.
        Non-positive values and None-values go through
        the entire evaluation set for each evaluation.
        Note that the number of eval_steps should be high enough to minimize noise.
        This is especially true for early-stopping.
      eval_delay:
        Start the first evaluation after eval_delay. Defaults to params.eval_delay or 2*60s.
      eval_period:
        Run an evaluation every eval_period seconds. Defaults to params.eval_period or 10*60s.
      exporters:
        List of exporters called at the end of each evaluation run.
        Defaults to none.
      export_output_fn:
        The output format to use for exported models.
        Only used if exporters is not None.

    Early-stopping arguments:
      early_stop_metric:
        String specifying the metric to early-stop on. Required with positive
        ``early_stop_patience``. For example, 'accuracy', 'accuracy_0', 'loss', etc.
        The string is used to extract the relevant tensor Op from the dict returned by
        the get_eval_metric_ops method. For ``metrics`` pass to the constructor,
        the string is one of those. For multi-class (that is, multi-metric)
        metrics, the string may be appended with a ``_0``, ``_1``, etc. or one
        of the ``multi_metric_names`` (one per class).
      early_stop_patience:
        Maximum number of epochs to wait for an improvement in the early_stop_metric
        before breaking off training. For example, a patience of 10 means that
        training will have 10 epochs to improve the metric before it is killed.
        Whenever the metric is improved before running out of patience,
        patience is reset to ``early_stop_patience``.
        Defaults to -1 (that is, no early-stopping).
      early_stop_minimize:
        Set this to True (the default) for metrics that need to be minimized
        (like ``loss``). Metrics like ``accuracy`` that need to be maximized
        should set this to False.
      early_stop_tolerance:
        A non-negative tolerance for comparing early_stop_metric.
        E.g. when maximizing the condition is current_metric > best_metric + tolerance.
        Defaults to 0.
      max_duration:
        A float. When this argument is defined, the job will automatically terminate after
        `max_duration` seconds if it has not already compeleted. 

    Returns:
      The directory where the checkpoints were saved.
    """

    logging.info("WARNING: Trainer.train_and_evaluate is an EXPERIMENTAL API.")
    logging.info("Trainer.train_and_evaluate may change or be removed in future versions.")

    if not callable(train_input_fn):
      raise ValueError("Expecting callable train_input_fn function")
    if not callable(eval_input_fn):
      raise ValueError("Expecting callable eval_input_fn function")

    self._exit_ps_after_training_complete()

    # Maybe export in eval processes.
    if self.is_evaluator():
      if self.params.get("eval_name") is not None:
        # Do not export if running special eval.
        exporters = None
        export_output_fn = None
      elif exporters and export_output_fn:
        self._export_output_fn = export_output_fn
      else:
        # Default option.
        self._export_output_fn = None

    train_hooks = self.get_train_hooks() if train_hooks is None else train_hooks
    train_hooks = [] if train_hooks is None else train_hooks

    eval_hooks = self.get_eval_hooks() if eval_hooks is None else eval_hooks
    eval_hooks = [] if eval_hooks is None else eval_hooks

    if train_max_steps is None:
      train_max_steps = self.params.get('train_max_steps')

    if eval_steps is None:
      eval_steps = self.params.eval_steps
    if eval_steps <= 0:
      eval_steps = None

    if eval_delay is None:
      eval_delay = self.params.eval_delay
    if eval_period is None:
      eval_period = self.params.eval_period

    if early_stop_patience > 0:
      # when training hooks detect this file, they request a stop to training
      early_stop_path = os.path.join(self._save_dir, 'earlystop_now.txt')
      # prepare early stopping hook (which also handles logic here)

      self._is_early_stopping = True

      eval_early_stop_hook = twml.hooks.EarlyStopHook(
        metric=early_stop_metric,
        checkpoint_dir=self._save_dir,
        patience=early_stop_patience,
        minimize=early_stop_minimize,
        tolerance=early_stop_tolerance,
        get_estimator_spec_fn=lambda: self.current_estimator_spec,
        file_path=early_stop_path,
        exit_on_end=os.environ.get('TF_CONFIG') is not None)  # only exit for distributed jobs
      # add early stop hook to eval hooks
      eval_hooks.append(eval_early_stop_hook)

      # prepare the commensurate training hook
      train_early_stop_hook = twml.hooks.StopIfExistsHook(early_stop_path)
      train_hooks.append(train_early_stop_hook)

    if max_duration is not None:
      train_early_stop_duration_hook = twml.hooks.EarlyStopDuration(
        max_duration=max_duration,
        exit_on_end=False,
        save_dir=self._save_dir,
        overwrite=self.is_chief()
      )
      eval_early_stop_duration_hook = twml.hooks.EarlyStopDuration(
        max_duration=max_duration,
        exit_on_end=os.environ.get('TF_CONFIG') is not None,
        save_dir=self._save_dir,
        overwrite=False
      )  # only exit for distributed jobs

      train_hooks.append(train_early_stop_duration_hook)
      eval_hooks.append(eval_early_stop_duration_hook)

    with self.experiment_tracker.track_experiment(eval_hooks, lambda: self.current_estimator_spec):
      train_spec = self.get_train_spec(train_input_fn, train_max_steps, train_hooks)
      eval_spec = self.get_eval_spec(eval_input_fn, eval_steps,
                                     eval_delay, eval_period,
                                     eval_hooks, exporters)
      self._train_and_evaluate(train_spec, eval_spec)

    if self.is_chief():
      self.write_state_to_disk(save_dir=self._save_dir, filename='_SUCCESS')

    return self._save_dir

  def _train_and_evaluate(self, train_spec, eval_spec):
    """
    Private method that calls
    ``tf.estimator.train_and_evaluate(self._estimator, train_spec, eval_spec)``.
    """
    try:
      tf.estimator.train_and_evaluate(self._estimator, train_spec, eval_spec)
    except twml.errors.EarlyStopError:
      # Ignore the exception if on evaluator.
      if self.is_evaluator():
        pass
      else:
        raise

  def train(self, input_fn=None, steps=None, hooks=None):
    """
    Train the estimator for `steps` training steps.

    Args:
      steps:
        number of steps for which to perform training. For example, 100 means each
        evaluation will end after processing 100 batches.
        Defaults to None. i.e. trains on the entire dataset a single time.
        Non-positive values and None-values go through the entire training set each epoch.
      input_fn:
        Function to iterate through training set. It is passed to estimator.train.
      hooks:
        List of SessionRunHooks uses for training. Defaults to self.get_train_hooks().
    """
    if os.environ.get('TF_CONFIG') and "is_calibrating" not in self.params:
      raise ValueError("trainer.train() can not be used with distributed / hogwild setups")

    if not callable(input_fn):
      raise ValueError("Expecting callable input_fn function")

    if self._is_early_stopping:
      raise ValueError("Can not call train() after learn() when using early stopping.")

    hooks = self.get_train_hooks() if hooks is None else hooks
    self._estimator.train(input_fn, steps=steps, hooks=hooks)
    return self

  def evaluate(self, input_fn=None, steps=None, hooks=None, name=None):
    """
    Evaluate the estimator for `steps` evaluation steps.

    Args:
      steps:
        number of steps for which to perform evaluation. For example, 100 means each
        evaluation will end after processing 100 batches.
        Defaults to None. i.e. evaluates on the entire dataset a single time.
        Negative values and None-values go through the entire training set each epoch.
      input_fn:
        Function to iterate through evaluation set. It is passed to estimator.evaluate.
      hooks:
        List of SessionRunHooks used for evaluation. Defaults to None.
        Note that, unlike learn(), hooks defaults to None instead of self.get_eval_hooks()
        as the latter may implement early-stopping, which isn't necessarilty the desired
        behavior when calling evaluate() on its own.
      name:
        Name of the evaluation if user needs to run multiple evaluations on different data sets.
        Metrics for different evaluations are saved in separate folders,
        and appear separately in tensorboard.

    Returns:
      If `is_evaluator()`, returns a dict containing the evaluation metrics specified
      in `metric_fn` keyed by name, as well as an entry `global_step` that contains
      the value of the global step for which this evaluation was performed.
      Otherwise (i.e. `is_evaluator() == False`), returns None.
    """
    if not self.is_evaluator():
      return None

    if not callable(input_fn):
      raise ValueError("Expecting callable input_fn function")

    hooks = self.get_eval_hooks() if hooks is None else hooks
    hooks = [] if hooks is None else hooks

    # for consistency with train/learn
    eval_steps = None if steps is not None and steps < 0 else steps

    with self.experiment_tracker.track_experiment(hooks, lambda: self.current_estimator_spec, name=name):
      checkpoint = self.best_or_latest_checkpoint
      computed_metrics = self._estimator.evaluate(
        input_fn,
        steps=eval_steps,
        hooks=hooks,
        checkpoint_path=checkpoint,
        name=name
      )

    return computed_metrics

  def start_tensorboard(self, port=None):
    """
    Start tensorboard process to visualize logs in save_dir.
    """
    logging.info("Starting tensorboard.")
    if self._tensorboard_handle:
      logging.warn("Tensorboard already running. Nothing done.")
      return

    if port is None:
      if 'tensorboard_port' not in self.params.values():
        raise ValueError('You must specify a port for tensorboard to run on.')
      elif self.params.tensorboard_port is None:
        return
      else:
        port = self.params.tensorboard_port

    mldash_path = 'experiments'
    if self.experiment_tracker.path:
      mldash_path += '/%s' % encode_url(self.experiment_tracker.experiment_id)
    tensorboard_args = ['--logdir=%s' % self._save_dir, '--port=%d' % port]

    try:
      args = ['email_and_launch_tensorboard', mldash_path, '--'] + tensorboard_args
      self._tensorboard_handle = subprocess.Popen(args)
    except OSError:
      try:
        self._tensorboard_handle = subprocess.Popen(['tensorboard'] + tensorboard_args)
      except OSError:
        try:
          # this will work with Twitter internal pants build when run locally
          args = ['./pants', 'run', 'twml:tensorboard', '--'] + tensorboard_args
          self._tensorboard_handle = subprocess.Popen(args)
        except OSError:
          logging.error("No tensorboard installed, won't able to visualize training in tensorboard.")

  def stop_tensorboard(self):
    """
    Shutdown this Trainer's associated Tensorboard.
    """
    if self._tensorboard_handle:
      logging.info("Shutting down tensorboard.")
      self._tensorboard_handle.kill()
    else:
      logging.warn("No known tensorboard process. Nothing done.")

  def calibrate(self,
                calibrator,
                steps=None,
                input_fn=None,
                save_calibrator=True,
                hooks=None):
    """
    Calibrate the calibrator for `steps` calibration steps using the estimator.train method.
    The build_graph passed to the Trainer constructor should
    call calibrator.accumulate using something like tf.py_func.
    That way, when this method calls estimator.train the calibrator will
    accumulate one epoch of samples. After which, this method calls calibrator.calibrate().
    It is up to the user to then call calibrator.save() to save the calibrated Layer
    and other information to disk for multi-phase training.

    Args:
      calibrator:
        a twml.Calibrator instance or a dict of the form {name(str): twml.Calibrator}.
      steps:
        Maximum steps to accumulate examples for calibration. Optional.
        If not specified, examples will be accumulated until all downsampled parts are processed.
      input_fn:
        Function to iterate through training set. It is passed to estimator.train.
      hooks:
        List of SessionRunHooks uses for training. Defaults to self.get_train_hooks().
      save_calibrator:
        Boolean (default: True). If set to True it will save the calibrator layer.
    """

    if not callable(input_fn):
      raise ValueError("Expecting callable input_fn function")

    # making everything a dict to avoid multiple ifs
    if isinstance(calibrator, twml.contrib.calibrators.Calibrator):
      calibrator = {"default": calibrator}

    # This is a dummy call to train, since we cannot predict without training
    # from the Estimator API
    self._estimator.train(input_fn, steps=1)
    max_steps = steps if steps is not None else -1
    for name, clbrt in sorted(calibrator.items(), key=itemgetter(0)):
      count = 0
      for out in self._estimator.predict(input_fn, hooks=hooks, yield_single_examples=False):
        if max_steps > 0 and count > max_steps:
          break
        clbrt.accumulate_feature(out)
        count += 1
      clbrt.calibrate()

    # this step is done to allow us to keep the current phases event file for
    # visualization on Tensorboard. It removes all files that
    # are not event files. This piece of code should be deprecated when
    # we deprecate the MDL calibrator (CX-12329)
    for fname in tf.io.gfile.listdir(self._save_dir):
      if not fname.startswith("events"):
        tf.io.gfile.remove(os.path.join(self._save_dir, fname))

    if save_calibrator:
      # If we only have one calibrator, the calibrator signature
      # will be set to default
      if len(calibrator) == 1:
        calibrator = calibrator['default']
        calibrator.save(
          self.params.save_dir,
          name=calibrator.name,
          verbose=True
        )
      else:
        for name, clbrt in calibrator.items():
          clbrt.save(
            self.params.save_dir,
            name=clbrt.name + str(name),
            verbose=True
          )

  def predict(self, *args, **kwargs):
    """
    Wrapper over the tensorflow `Estimator.predict
    <https://www.tensorflow.org/api_docs/python/tf/estimator/Estimator#predict>`_.
    method. See that documentation for description of arguments accepted.

    If hooks is passed as an argument, the specified hooks are used.
    Else when profiler_steps is specified in the constructor of the Trainer, a
    tf.train.ProfilerHook is passed to the predict interface.
    Otherwise, hooks is set to an empty list.
    """
    if 'hooks' not in kwargs and len(args) < 3:
      # If hooks is not specified as a keyword argument, nor as a positional argument
      # add hooks as a keyword argument.
      kwargs['hooks'] = self.get_predict_hooks()

    return self.estimator.predict(*args, **kwargs)

  def hub_export(self,
                 name,
                 serving_input_receiver_fn,
                 export_dir=None,
                 checkpoint_path=None,
                 export_task_type_overrider=None):
    """
    Exports registered modules into a save directory.

    This method creates a directory under export_path with the save TF Hub.
    One sub-directory (named export_name) per module registered via register_module_for_export.

    Arguments:
      name:
        unique name of the module to export.
      serving_input_receiver_fn:
        A function with no arguments that returns a ServingInputReceiver.
        This is used with the estimator passed to export() to build the graph (in PREDICT mode)
        that registers the modules for export. The model in that graph is never run,
        so the actual data provided by this input fn does not matter.
      export_dir:
        A string containing a directory where to write the export directories.
        Defaults to the save_dir.
      checkpoint_path:
        The checkpoint path to export. Defaults to the latest.
      export_task_type_overrider:
        Specifies the task type that will override the default task type used for export
        (hogwild training defaults to evaluator, otherwise, defaults to chief)
    """
    if export_task_type_overrider:
      if not self.is_task_type(export_task_type_overrider):
        logging.info(
          f"Trainer.hub_export ignored due to process not being {export_task_type_overrider}")
        return
    else:
      if self._using_hogwild:
        if not self.is_evaluator():
          logging.info("Trainer.hub_export ignored due to the process not being evaluator.")
          return
      else:
        if not self.is_chief():
          logging.info("Trainer.hub_export ignored due to the process not being chief.")
          return

    if export_dir:
      export_dir = sanitize_hdfs_path(export_dir)

    if checkpoint_path:
      checkpoint_path = sanitize_hdfs_path(checkpoint_path)
    else:
      checkpoint_path = self.best_or_latest_checkpoint

    export_dir = export_dir if export_dir is not None else self._save_dir
    exporter = hub.LatestModuleExporter(name, serving_input_receiver_fn)
    # The path_exporter by default contains a timestamp directory in its path.
    path_exporter = exporter.export(estimator=self.estimator,
                                    export_path=export_dir,
                                    checkpoint_path=checkpoint_path)

    # LatestModuleExporter.export() returns a binary string on Cloud ML Engine
    # but tf.io.gfile.listdir() does not; this is an issue when joining paths
    if isinstance(path_exporter, bytes):
      path_exporter = path_exporter.decode()

    # Copying the saved hub module to export_dir so we don't need to specify
    # the timestamp when loading the module.
    # This is a workaround due to the current implementation of hub.LatestModuleExporter.
    # This works for multiple hub modules.
    hub_exported_modules = tf.io.gfile.listdir(path_exporter)

    backup_dir = os.path.join(export_dir, "backups",
                              datetime.datetime.now().strftime('%Y-%m-%d_%H-%M-%S'))

    for folder in hub_exported_modules:
      hub_module_oldpath = os.path.join(path_exporter, folder)
      hub_module_newpath = os.path.join(export_dir, folder)

      # If the destination already exists, move to backup
      if tf.io.gfile.exists(hub_module_newpath):
        # Ensure backup_dir exists
        tf.io.gfile.makedirs(backup_dir)
        hub_module_backup = os.path.join(backup_dir, folder)
        tf.io.gfile.rename(hub_module_newpath, hub_module_backup)

      tf.io.gfile.rename(hub_module_oldpath, hub_module_newpath)

    # Since the timestamped folder exists but is empty, we can delete it.
    tf.io.gfile.rmtree(path_exporter)

  def _is_on_gke(self) -> bool:
    """Returns True if running on gke."""
    cluster = os.environ.get('TWML_JOB_CLUSTER')
    if not cluster or cluster in {'smf1', 'atla'}:
      return False
    return True

  def _maybe_del_tsd_exit(self, state_files) -> None:
    """Handle potential early exit and TwitterSetDeployment deletion.

      If:
        - distributed training
        - running GKE
        - training is finished (all state_files exists)
      we will exit early and not restart work

      If --distributed_training_cleanup = True then we will also handle
      cleaning up the TwitterSetDeployments.

      Args:
        state_files: A python list indicate state files to determine the finish 
        state of the job.
    """
    # job type that is responsible for experiment tracking will remain alive
    # until it marks the experiment as finished.
    if self.experiment_tracker._env_eligible_for_recording_experiment:
      exp_status = self.experiment_tracker.get_run_status()
      if exp_status and exp_status not in {'Success', 'Failed'}:
        logging.info(
          f"Not exiting early because experiment is still {exp_status}."
        )
        return

    # do not bother if we are on prem
    if not self._is_on_gke():
      logging.info("No need to exit early because running on prem.")
      return

    states = [
      twml.util.file_exist_in_dir(self._save_dir, state_file) for state_file in state_files]
    do_not_restart = (self._params.get('distributed') and all(states))
    if not do_not_restart:
      return

    logging.info(
      f"Exiting early because a _SUCCESS file already exists in {self._save_dir}")
    if self._params.get('distributed_training_cleanup'):
      resource_name = '-'.join([
        os.environ['TWML_JOB_NAME'],
        os.environ['TWML_DISTRIBUTED_JOB_TYPE'],
        os.environ['TWML_JOB_ENV'],
      ])
      logging.info(f"Deleting TwitterSetDeployment {resource_name}")
      # each job type will manage its own deletion so that deletion happens
      # in the trainer init call for every job type
      # otherwise we may kill another job type during an important
      # process like experiment tracking management (handled by the evaluator
      kubectl_delete_by_name(
        zone=None,
        namespace=os.environ['TWML_JOB_ROLE'],
        resource_type=Resource.TWITTERSETDEPLOYMENTS.value,
        resource_name=resource_name,
        wait=False,
      )
    sys.exit(0)

  def write_state_to_disk(self, save_dir, filename='_SUCCESS') -> None:
    """Write state file to disk to indicate the state of training process. This is usually used 
      to mark the state of training progress and determine the start when job restarts/resumes.
    Args:
      save_dir: A str of local/gcs/hdfs dir to write the state file.
      file_name: A str indicate the state file. Default to `_SUCCESS`.
    """
    file_path = os.path.join(save_dir, filename)
    if tf.io.gfile.exists(file_path):
      tf.logging.warn(f'{file_path} already exist.')
      return

    with tf.io.gfile.GFile(file_path, 'w') as f:
      f.write('')