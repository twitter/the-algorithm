"\n``twml.trainers.Trainer`` is a wrapper around `tf.estimator.Estimator\n<https://www.tensorflow.org/versions/master/api_docs/python/tf/estimator/Estimator>`_\nto expose an easier to use API by\nhiding rarely used config knobs and supplying default values.\n\nThe `Trainer` facilitates multi-phase training commonly used at Twitter: e.g.\nMDL calibration -> MLP training -> Isotonic calibration.\nThe `Trainer` also facilitates hyperparameters tuning,\nwith its simple `add_parser_arguments()` method.\n\nLearning rate decay functions\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\nPlease note that we have four learning rate decay functions to choose from.\nAdditionally, each trainer can only take one learning rate decay function and its parameters.\nIf that is not the case, it will throw an error.\nAlso, please note that the learning rate decay is a positional argument and should be placed as\nthe last argument to the trainer, as you can see in the example above.\nThe four learning decays options are:\n\n1. inverse_learning_rate_decay:\n\n  The function returns the decayed learning rate. It is computed as:\n\n  ::\n\n    decayed_learning_rate = learning_rate / (1 + decay_rate * global_step /decay_step)\n    final_decayed_learning_rate = max(decayed_learning_rate, min_learning_rate)\n\n\n2. polynomial_learning_rate_decay:\n\n  The function returns the decayed learning rate. It is computed as:\n\n  ::\n\n    global_step = min(global_step, decay_steps)\n    decayed_learning_rate = (learning_rate - end_learning_rate) *\n                            (1 - global_step / decay_steps) ^ (power) +\n                            end_learning_rate\n\n\n3. piecewise_constant_learning_rate_decay:\n\n  Piecewise constant from boundaries and interval values.\n\n  Example: use a learning rate that's 1.0 for the first 100001 steps, 0.5 for\n  the next 10000 steps, and 0.1 for any additional steps.\n\n  ::\n\n    global_step = tf.Variable(0, trainable=False)\n    boundaries = [100000, 110000]\n    values = [1.0, 0.5, 0.1]\n    learning_rate = tf.train.piecewise_constant(global_step, boundaries, values)\n\n4. exponential_learning_rate_decay:\n\n  The function returns the decayed learning rate. It is computed as:\n\n  ::\n\n    decayed_learning_rate = learning_rate * decay_rate ^ (global_step / decay_steps)\n\n"
_T='eval_name'
_S='train_max_steps'
_R='Expecting callable eval_input_fn function'
_Q='Expecting callable train_input_fn function'
_P='TWML_JOB_NAME'
_O='TWML_JOB_ENV'
_N='TWML_JOB_ROLE'
_M='distributed_training_cleanup'
_L='TWML_HOGWILD_TASK_ID'
_K='TWML_HOGWILD_TASK_TYPE'
_J='tensorboard_port'
_I='Expecting callable input_fn function'
_H='loss'
_G='_SUCCESS'
_F='TWML_HOGWILD_PORTS'
_E='TF_CONFIG'
_D='distributed'
_C=False
_B=True
_A=None
import datetime,functools,math
from operator import itemgetter
import os,pprint as pp,random
from string import Template
import subprocess,sys,time
from threading import Thread
from twitter.common.metrics import AtomicGauge
from twitter.deepbird.stats_server import utils as stats_server_utils
from twitter.deepbird.stats_server.stats_exporter import StatsExporter
from twitter.ml.common import metrics
from twitter.ml.common.kubernetes import kubectl_delete_by_name,Resource
from twitter.ml.twml.status import get_distributed_training_job_status,TrainingJobStatus
from absl import logging
from twml.optimizers import LazyAdamOptimizer,optimize_loss,OPTIMIZER_SUMMARIES
from twml.contrib.optimizers import DeepGradientCompressionOptimizer
from twml.tracking import ExperimentTracker
from twml.util import delete_file_or_dir,get_distributed_training_job_path,sanitize_hdfs_path
try:from urllib import quote as encode_url
except ImportError:from urllib.parse import quote as encode_url
import tensorflow.compat.v1 as tf,tensorflow,tensorflow_hub as hub,twitter.ml.twml.kubernetes.status as k8s_status,twml,twml.export_output_fns,twml.learning_rate_decay,twml.metrics
_CLUSTER_TEMPLATE=Template('{\n  "cluster": {\n    "ps": [$PS],\n    "chief": [$CHIEF],\n    "worker": [$WORKER]\n  },\n  "task": {"type": "$TYPE", "index": $INDEX}\n}\n')
def init_from_checkpoint(init_dir,init_map):
	'\n  Wrapper around tf.train.init_from_checkpoint\n  ';A=init_dir
	if A:A=sanitize_hdfs_path(A);tf.train.init_from_checkpoint(A,init_map)
class Trainer:
	"\n  This class wraps ``tf.estimator.Estimator`` to make construction, saving, and loading easier.\n  Supports multi-phase training (for example, use a Trainer for MDL calibration, then\n  another for training the rest of the model, then another for isotonic calibration).\n  The Trainer also implements a training and evaluation loop via the ``learn()`` method.\n  Each Trainer is associated to a fixed set of hyper parameters (params), and a single model\n  specified by ``build_graph``. Given these constraints, a single Trainer can be called\n  multiple times for training and evaluation over multiple epochs.\n\n  However, if you intend to try different sets of hyper-parameters, we recommend you instantiate\n  a different Trainer for each such experiment. That way, each experiment can be tracked\n  in a different ``save_dir``. Indeed, after calling ``learn``, a Trainer's save_dir will contain\n  checkpoints of the model (its graph, and variables), and the history of metrics (for example,\n  evaluation accuracy at each epoch), and other store observations like the average time per step.\n  The latter metrics can be viewed by pointing\n  TensorBoard to the save_dir and accessing TensorBoard via your browser.\n  "
	def __init__(A,name,params,build_graph_fn,metric_fn=_A,optimize_loss_fn=_A,run_config=_A,save_dir=_A,init_from_dir=_A,init_map=_A,warm_start_from=_A,profiler_steps=_A,**H):
		"\n\n    Args:\n      name (String):\n        string name of this estimator; used as scope names for variables and tensors.\n      params (HParams, Namespace, or Dict):\n        hyper-parameters to be passed to Estimator constructor.\n        Must include params.train_batch_size and params.eval_batch_size.\n        Note that params is passed to twml.util.convert_to_hparams() to produce an HParams.\n      build_graph_fn:\n        A function for building tensorflow graphs.\n        This matches TensorFlow Estimator's model_fn signature.\n        For example,\n\n        .. code-block:: python\n\n          def build_graph(features, label, mode, params, config=None):\n            # Implements a simple binary logistic regression model\n            sparse_tf = twml.util.convert_to_sparse(features, params.input_size_bits)\n\n            logits = twml.layers.full_sparse(sparse_tf, 1 << params.input_size_bits, 1)\n\n            if mode == 'infer':\n              loss = None\n            else:\n              loss = tf.nn.sigmoid_cross_entropy_with_logits(labels=label, logits=logits)\n              loss = twml.util.weighted_average(loss, features['weights'])\n\n            output = tf.nn.sigmoid(logits)\n\n            return {'output': output, 'loss': loss}\n\n        Args:\n          features (dict of Tensor keyed by a string name):\n            input tensors.\n          mode (tf.estimator.ModeKeys / String):\n            one of 'train', 'eval', 'infer'.\n          label (Tensor):\n            if in ``mode == 'train'`` mode, these contain the corresponding labels for input.\n          params (HParams):\n            hyper parameters that control how to build a graph.\n          config:\n            the RunConfig object passed to Estimator constructor.\n\n        This function is expected to return a dictionary containing the following keys:\n\n        * 'output': a node representing model output; required.\n        * 'loss': (required) a loss node used for optimization; required for training and\n          evaluation.\n        * 'train_op': (optional) an operation that minimizes the loss (as output by\n          `tf.train.Optimizer.minimize`). If train_op is specified, train_op is used\n          for optimization as opposed to loss. Loss is always logged to tensorboard.\n\n        Notes:\n\n        * any tf.summary written inside build graph are logged to tensorboard during training.\n        * the ``build_graph_fn`` is called once or twice per epoch (once per training,\n          once per evaluation). All data loading (and preprocessing) logic not required\n          for serving should be in the ``input_fn`` passed to ``learn``, ``train``,\n          ``evalulate``, etc.\n\n      optimize_loss_fn:\n        Defaults to Trainer.get_train_op. A function that takes params and loss as arguments\n        and returns a training op. The training op is used to update parameters (that is, to learn).\n      metric_fn:\n        A function that returns the eval_metric_ops dict given graph_output, labels and weights.\n        Defaults to None.\n        Use ``twml.metrics.get_binary_class_metric_fn()`` to return a ``metric_fn``\n        which implements many binary classification metrics.\n      run_config (RunConfig):\n        optional configuration to be passed to Estimator constructor. Defaults to None.\n      save_dir (String):\n        optional directory where to save model checkpoints,\n        tensorboard event files and trained parameters.\n        Overwrites and defaults to run_config.model_dir.\n      init_from_dir (String):\n        optional directory to load weights from.\n        if set to None (the default), do not init from any directory.\n      init_map (map from String to String):\n        Must be specified if init_from_dir is specified.\n        Defines which scopes and variables to load.\n        Keys are the variables and scopes to load from the directory.\n        Values are the destinations (in the current graph) to load into.\n        See tf.init_from_checkpoint for more information.\n        Note that the the trainer prepends name_scope of the form `name`/model/ to the name_scope\n        of any variable defined inside `build_graph_fn` and this should be taken into account when\n        defining the values.\n      warm_start_from:\n        Optional string filepath to a checkpoint to warm-start from,\n        or a tf.estimator.WarmStartSettings object to fully configure warm-starting.\n        If the string filepath is provided instead of a WarmStartSettings,\n        then all variables are warm-started, and it is assumed that\n        vocabularies and Tensor names are unchanged.\n      profiler_steps (Integer):\n        Defaults to None. If set defines the number of steps in the\n        `tf.train.ProfileHook <https://www.tensorflow.org/api_docs/python/tf/train/ProfilerHook>`_.\n        Captures CPU/GPU profiling information every ``profiler_steps`` steps or seconds.\n        When executing ``learn``, ``train`` or ``predict`` methods,\n        with ``profiler_steps`` set to a number,\n        a ``timeline_X.json`` file is created in the save_dir. This file contains profiling data\n        storedin Chrome trace format. To view stored data, use the Chrome browser to follow\n        these steps:\n\n        1) Go to the page chrome://tracing.\n        2) In the upper left corner, you will find Load button.\n        3) Press it and load our JSON file, which can be found in the ``save_dir``\n\n        *Warning*: This could create too many these json files which can be a potential problem,\n        e.g. for  HDFS there is normally quota forfile count, so use with caution.\n\n        Note: this argument is ignored when a non-None ``hooks`` argument is pasesd to\n        ``train``, ``learn``, or ``predict`` methods. The hook can be added manually by passing\n        ``trainer.train(..., hooks=myhooks.extend(trainer.get_train_hooks()))``, for example.\n    ";J='stats_port';K=init_map;L=optimize_loss_fn;I='gke_state_files';E=warm_start_from;F=init_from_dir;C=save_dir;D=params;B=run_config
		if tensorflow.__version__>='2.0':RuntimeError('Trainer not yet supported for Tensorflow >= 2.0')
		A._name=name;A._build_graph_fn=build_graph_fn;A._metric_fn=metric_fn;A._tensorboard_handle=_A;A._current_estimator_spec=_A;A._profiler_steps=profiler_steps;A._export_output_fn=_A;A._is_early_stopping=_C;C=sanitize_hdfs_path(C);F=sanitize_hdfs_path(F)
		if isinstance(E,str):E=sanitize_hdfs_path(E)
		D=twml.util.convert_to_hparams(D);A._params=D;A.check_params();A._using_hogwild=_B if os.environ.get(_F)else _C;A._hogwild_setup()
		if not B:
			G=tf.ConfigProto();G.gpu_options.allow_growth=_B
			if'TWML_NUM_CPUS'in os.environ:
				N=int(os.environ.get('TWML_MESOS_CPU','8'))
				if D.num_mkl_threads>1:os.environ['OMP_NUM_THREADS']=str(D.num_mkl_threads);os.environ['MKL_NUM_THREADS']=str(D.num_mkl_threads);G.inter_op_parallelism_threads=N//D.num_mkl_threads;G.intra_op_parallelism_threads=D.num_mkl_threads
			B=tf.estimator.RunConfig(session_config=G,keep_checkpoint_max=A._params.get('keep_checkpoint_max',20),log_step_count_steps=10000,save_checkpoints_secs=A._params.get('save_checkpoints_secs',600),tf_random_seed=A._tf_random_seed())
		elif not isinstance(B,tf.estimator.RunConfig):raise ValueError('Expecting run_config argument of type None or tf.estimator.RunConfigGot %s instead.'%type(B).__name__)
		elif os.environ.get(_F):raise ValueError('Custom RunConfig not supported with Hogwild')
		if B.model_dir is _A and C is _A:raise ValueError('Expecting either save_dir or run_config.model_dir to be specified. Got None for each.')
		elif B.model_dir is _A:B=B.replace(model_dir=C)
		elif C is _A:C=B.model_dir
		A._save_dir=C;A.experiment_tracker=ExperimentTracker(A._params,B,A._save_dir);H[I]=H.get(I,[_G]);A._maybe_del_tsd_exit(H[I]);logging.info('Checkpoint and event files will be saved at save_dir=%s',C);A._optimize_loss_fn=A.get_train_op if L is _A else L
		if A._params.get('overwrite_save_dir')and tf.io.gfile.exists(A._save_dir):
			logging.info('Trainer overwriting existing save directory: %s (params.overwrite_save_dir)'%A._save_dir)
			if A._params.get(_D,_C):
				time.sleep(30)
				if B.is_chief:logging.info('Chief deleting the save_dir now');delete_file_or_dir(A._save_dir)
				time.sleep(30)
			else:delete_file_or_dir(A._save_dir)
		if A._params.get(J):
			try:stats_server_utils.start_stats_server(A._params.get(J),A._save_dir)
			except Exception as O:logging.error('Failed to start the stats server. Error: %s',str(O))
		M=os.path.join(A._save_dir,'checkpoint')
		if tf.io.gfile.exists(M):logging.info('The provided save_dir directory %s already exists. Training will be resumed.'%M)
		A._maybe_restore_checkpoint=lambda:init_from_checkpoint(F,K)
		if F is not _A and K is _A:raise ValueError('Need to provide init_map when init_from_dir is provided.')
		if not tf.io.gfile.exists(A._save_dir):tf.io.gfile.mkdir(A._save_dir)
		A._estimator=tf.estimator.Estimator(model_fn=A._model_fn,params=A._params,config=B,warm_start_from=E,model_dir=A._save_dir);logging.info('Trainer constructed using the following parameters: ');P=pp.pformat(A._params.values());logging.info(P)
		if A._params.get('disable_tensorboard',_C):logging.info('Skipping launching TensorBoard [--disable_tensorboard is set]')
		elif _J in A._params.values()and A._params.tensorboard_port is not _A:A.start_tensorboard(A._params.tensorboard_port)
		A.stats_exporter=StatsExporter('twml.trainer');A.export_gauge=AtomicGauge('export_model');A.stats_exporter.register_metrics(A.export_gauge)
	def _hogwild_setup(A):
		'\n    Setup the parameters required for hogwild.\n    ';B='num_threads';A._num_workers=A._params.get('num_workers')or 1;logging.info('NUM_WORKERS: %d',A._num_workers)
		if A._num_workers<=1:A._ports=_A;return
		if _D in A._params:A._params.set_hparam(_D,_B)
		else:A._params.add_hparam(_D,_B)
		C=os.environ.get(_F)
		if C:
			A._ports=[int(A)for A in C.strip().split(',')]
			if A._num_workers+1!=len(A._ports):raise ValueError('Number of (workers + PS) and ports need to match')
		elif A._num_workers>1:raise ValueError('TWML_HOGWILD_PORTS needs to be set to use hogwild training')
		D=A._params.get(B);E=int(math.ceil(float(D)/A._num_workers));A._params.set_hparam(B,E);F=os.environ.get(_K);G=int(os.environ.get(_L));os.environ[_E]=A._get_cluster_config(F,G)
	def _tf_random_seed(B):
		' Returns user set seed and deal with Hogwild multiple seeds ';A=B._params.get('tf_random_seed',_A)
		if A is _A:return _A
		elif B.using_hogwild and os.environ.get(_K)=='worker':return A+1+int(os.environ.get(_L))
		else:return A
	def check_params(A):
		' Verify that params has the correct key,values ';B='eval_batch_size';C=A._params.values()
		if'train_batch_size'in C:
			if not isinstance(A._params.train_batch_size,int):raise ValueError('Expecting params.train_batch_size to be an integer.')
			if A._params.train_batch_size<=0:raise ValueError('train_batch_size needs to be positive')
		else:raise ValueError('train_batch_size needs to be present in params')
		if B in C:
			if not isinstance(A._params.eval_batch_size,int):raise ValueError('Expecting params.eval_batch_size to be an integer.')
			if A._params.eval_batch_size<=0:raise ValueError('eval_batch_size needs to be positive.')
		else:A._params.add_hparam(B,A._params.train_batch_size)
		if A._params.get(_M)and not A._params.get(_D):raise ValueError('Expecting params.distributed to be set if params.distributed_training_cleanup is set.')
	def _get_cluster_config(A,name,index):'Create a tensorflow cluster config from ports, name and index';B='"localhost:%d"';C=B%A._ports[0];D=B%A._ports[1];E=', '.join([B%A for A in A._ports[2:]]);F=_CLUSTER_TEMPLATE.substitute(PS=C,CHIEF=D,WORKER=E,TYPE=name,INDEX=index);return F
	@property
	def current_estimator_spec(self):'\n    returns the current estimator (warning: often reset)\n    ';return self._current_estimator_spec
	@property
	def estimator(self):' returns estimator encapsulated by Trainer ';return self._estimator
	@property
	def num_workers(self):' returns number of workers ';return self._estimator.config.num_worker_replicas
	@property
	def worker_index(self):'\n    returns index of worker in the cluster\n    chief has index 0\n    non-chief workers have indices 1 through (num_workers - 1)\n    ';return self._estimator.config.global_id_in_cluster
	@property
	def using_hogwild(self):' returns a bool indicating whether hogwild is being used ';return self._using_hogwild
	def set_estimator(A,estimator):
		' sets the estimator used internally by Trainer ';B=estimator
		if not isinstance(B,tf.estimator.Estimator):raise ValueError('Expecting tf.estimator.Estimator')
		A._estimator=B;A._params=A.estimator.params
	@property
	def params(self):'\n    returns the hyper-parameters passed to the constructor.\n    ';return self._params
	@staticmethod
	def add_parser_arguments():'\n    Add common commandline args to parse for the Trainer class.\n    Typically, the user calls this function and then parses cmd-line arguments\n    into an argparse.Namespace object which is then passed to the Trainer constructor\n    via the params argument.\n\n    See the `code <_modules/twml/argument_parser.html#get_trainer_parser>`_\n    for a list and description of all cmd-line arguments.\n\n    Returns:\n      argparse.ArgumentParser instance with some useful args already added.\n    ';return twml.argument_parser.get_trainer_parser()
	@staticmethod
	def get_train_op(params,loss):
		'\n    Return a training Op, that is, a `twml.optimizers.optimize_loss\n    <https://www.tensorflow.org/api_docs/python/tf/contrib/layers/optimize_loss>`_\n    instance given params and loss.\n    This method can be overwritten by passing the optimize_loss_fn to the Trainer\n    constructor.\n\n    Args:\n      params:\n        tensorflow.contrib.training.HParams instance. Recognizes the optimizer, optimizer_summaries,\n        gradient_noise_scale, clip_gradients and learning_rate_decay (including\n        other learning rate decay arguments).\n      loss:\n        scalar Op returned by the build_graph that specifies the training loss to\n        be minimized.\n    ';A=params;B=A.get('optimizer')
		if not B:B='SGD'
		if B=='LazyAdam':B=LazyAdamOptimizer
		if B=='DGC':B=DeepGradientCompressionOptimizer(learning_rate=A.learning_rate,use_locking=_C,name='Sparse',density=A.get('dgc_density'),density_decay=A.get('dgc_density_decay'),density_decay_steps=A.get('dgc_density_decay_steps'),density_decay_rate=A.get('dgc_density_decay_rate'),min_density=A.get('dgc_min_density'),accumulation=A.get('dgc_accumulation'))
		C=[_H]
		if A.get('show_optimizer_summaries'):C=OPTIMIZER_SUMMARIES
		D=optimize_loss(loss=loss,global_step=tf.train.get_global_step(),optimizer=B,learning_rate=A.learning_rate,summaries=C,colocate_gradients_with_ops=_B,gradient_noise_scale=A.get('gradient_noise_scale'),clip_gradients=A.get('clip_gradients'),learning_rate_decay_fn=twml.learning_rate_decay.get_learning_rate_decay_fn(A));return D
	def export_model_effects(A,export_path,feature_spec=_A,log_features=_B):
		C=export_path;B=feature_spec
		if B:
			if log_features:
				D=B['features'];E=['.'.join(D[A]['featureName'].split('.')[1:])for A in D.keys()];F=','.join(E)
				try:G=A.experiment_tracker.compute_model_hash(C);metrics.log_usage('dbv2','export_model_effects','v1',custom_attrs=[G,'feature config present',F])
				except:logging.info('Failed to log Feature Config features')
			twml.contrib.export.export_fn.export_feature_spec(C,B);H=time.time();A.experiment_tracker.export_feature_spec(B);logging.info('Exported feature spec to ML Metastore in %s seconds.',time.time()-H)
		A.experiment_tracker.register_model(str(C));A.export_gauge.increment()
	@property
	def best_or_latest_checkpoint(self):
		A=self
		if A._is_early_stopping:
			B=os.path.join(A._save_dir,'best_checkpoint');C=tf.train.latest_checkpoint(B)
			if C:return C
			else:raise ValueError('Best checkpoint not found at %s.'%B)
		else:return A.latest_checkpoint
	@property
	def latest_checkpoint(self):return self.estimator.latest_checkpoint()
	def export_model(A,serving_input_receiver_fn,export_output_fn=_A,export_dir=_A,checkpoint_path=_A,feature_spec=_A,log_features=_B):
		'\n    Export the model for prediction. Typically, the exported model\n    will later be run in production servers. This method is called\n    by the user to export the PREDICTgraph to disk.\n\n    Internally, this method calls `tf.estimator.Estimator.export_savedmodel\n    <https://www.tensorflow.org/api_docs/python/tf/estimator/Estimator#export_savedmodel>`_.\n\n    Note that a valid self._export_output_fn is required.\n    If export_ouput_fn is provided, it is used to set the self._export_output_fn.\n\n    Args:\n      serving_input_receiver_fn:\n        function preparing the model for inference requests.\n        This funtion returns the ``features`` dict passed to ``build_graph``.\n      export_dir:\n        directory to export a SavedModel for prediction servers.\n        Defaults to ``[save_dir]/exported_models``.\n      checkpoint_path:\n        the checkpoint path to export. If None (the default), the most recent checkpoint\n        found within the model directory is chosen.\n      export_output_fn:\n        Function to export the graph_output (output of build_graph) for\n        prediction. Takes a graph_output dict as sole argument and returns\n        the export_output_fns dict.\n        Defaults to `twml.export_output_fns.default_output_fn`.\n\n    Return:\n      returns a string path to exported directory.\n\n    # set the export output function\n    ';C=export_dir;B=checkpoint_path
		if not A.is_chief():logging.info('Trainer.export_model ignored due to the process not being chief.');return
		A._export_output_fn=export_output_fn or twml.export_output_fns.default_output_fn
		if not callable(A._export_output_fn):raise RuntimeError('Expecting export_output_fn function. Got %s.'%type(A._export_output_fn).__name__)
		if C:C=sanitize_hdfs_path(C)
		if B:B=sanitize_hdfs_path(B)
		else:B=A.best_or_latest_checkpoint
		D=A._estimator.export_savedmodel(export_dir_base=C or os.path.join(A._save_dir,'exported_models'),serving_input_receiver_fn=serving_input_receiver_fn,checkpoint_path=B);logging.info('The exported model path is: '+str(D));A.export_model_effects(D,feature_spec,log_features);return D
	def _model_fn(A,features,labels,mode,params,config=_A):
		'\n    returns tf.estimator.EstimatorSpec that can be used with tf.estimator.Estimators.\n    You would probably never need to modify this method.\n    Instead, you should override build_graph, which this method calls.\n\n    Args:\n      features:\n        Dict of input tensors.\n      labels:\n        Tensor of target labels.\n      mode:\n        an instance of tf.estimator.ModeKeys.\n        Typically used to toggle TRAINing or EVALuation.\n      params:\n        HParams object containing hyper-parameters.\n    ';D='train_op';E=params;F=labels;G=features;C=mode
		if isinstance(G,dict):H=G.get('weights',_A)
		else:H=_A
		with tf.variable_scope(A._name+'/model'):B=A._build_graph_fn(G,F,C,E,config);I=B[_H]if _H in B else _A
		A._maybe_restore_checkpoint()
		with tf.variable_scope(A._name+'/optim'):
			J=_A
			if C==tf.estimator.ModeKeys.TRAIN:
				if D in B:J=B[D];B[D]=_A
				elif I is not _A:J=A._optimize_loss_fn(E,I)
				if E.get('train_log_metrics')and A._metric_fn:
					K=A._metric_fn(graph_output=B,labels=F,weights=H)
					for L in K:tf.summary.scalar(name='training_metric_'+L,tensor=K[L][1])
		if C==tf.estimator.ModeKeys.PREDICT and A._export_output_fn is not _A:M=A._export_output_fn(B)
		else:M=_A
		if C==tf.estimator.ModeKeys.EVAL and A._metric_fn:N=A._metric_fn(graph_output=B,labels=F,weights=H)
		else:N=_A
		O={A:B[A]for A in B if B[A]is not _A and A is not _H};P=twml.contrib.initializers.get_init_feed_dict();Q=tf.train.Scaffold(init_feed_dict=P);twml.contrib.initializers.clear_init_feed_collection();A._current_estimator_spec=tf.estimator.EstimatorSpec(mode=C,predictions=O,export_outputs=M,loss=I,train_op=J,eval_metric_ops=N,scaffold=Q);return A._current_estimator_spec
	def get_train_hooks(A):
		'Return SessionRunHooks used during training.\n\n    By default training uses one hooks `tf.train.StepCounterHook` for monitoring step speed.\n\n    If self._profiler_steps is set then we also use the ProfilerHook `tf.train.ProfilerHook`\n    for monitoring the profile.\n\n    ';C=2048*100//A._params.train_batch_size;D=tf.train.StepCounterHook(every_n_steps=C,output_dir=A._save_dir);B=[D]
		if A._profiler_steps is not _A:
			if not A._params.get(_D)or A._estimator.config.is_chief:E=tf.train.ProfilerHook(save_steps=A._profiler_steps,output_dir=A._save_dir);B.append(E)
		return B
	def is_task_type(A,name):
		'\n    Helper function to specify if the current process is of the given worker type.\n    Note: This an only be called *after* self._hogwild_setup() is called in __init__()\n    '
		if os.environ.get(_E):
			if A._estimator.config.task_type==name:return _B
			else:return _C
		return _B
	def is_evaluator(A):'\n    Helper function to let you know if the worker is evaluator.\n    Note: This an only be called *after* self._hogwild_setup() is called in __init__()\n    ';return A.is_task_type('evaluator')
	def is_chief(A):'\n    Helper function to let you know if the worker is chief.\n    Note: This an only be called *after* self._hogwild_setup() is called in __init__()\n    ';return A.is_task_type('chief')or A.is_task_type('master')
	def is_ps(A):
		'\n    Helper function to let you know if the task is parameter server.\n    '
		if os.environ.get(_E)and A._estimator.config.task_type=='ps':return _B
		return _C
	def _exit_ps_after_training_complete(A):
		'\n    Helper function to shutdown parameter server after training job complete (either succeed or failed).\n    '
		if not A.is_ps():return
		if os.environ.get(_F):return
		if A._params.get('disable_auto_ps_shutdown',_C):logging.info('Skip shutting down parameter server after training complete [--disable_auto_ps_shutdown is set]');return
		if A._is_on_gke():D=functools.partial(k8s_status.get_training_job_status,cluster=_A,namespace=os.environ[_N],environment=os.environ[_O],job_name=os.environ[_P],using_tsd=_B)
		else:D=functools.partial(get_distributed_training_job_path,base_job_path=get_distributed_training_job_path())
		def B():
			E=60;A=0
			while _B:
				try:
					C=D()
					if C==TrainingJobStatus.FINISHED:logging.info('Distributed training job succeed, shutting down parameter server.');os._exit(0)
					elif C==TrainingJobStatus.FAILED:logging.info('Distributed training job failed, shutting down parameter server.');os._exit(0)
					elif C==TrainingJobStatus.NOT_FOUND:raise Exception('Distributed training job status not found.')
					else:B=random.randrange(60,90);time.sleep(B);A=0
				except Exception as F:
					if A>=E:raise F
					A+=1;B=random.randrange(60,90)+A*10;logging.warn('Error getting distributed training job status, will retry after %s seconds.'%B);time.sleep(B)
		Thread(target=B).start()
	def get_eval_hooks(A):' Return SessionRunHooks used during evaluation.';return _A
	def get_predict_hooks(A):
		" Return hooks used during prediction.\n    If profiler_steps is set in the constructor to the Trainer,\n    we pass a tf.Train.ProfilerHook to the estimator's predict function.\n    ";B=[]
		if A._profiler_steps is not _A:C=tf.train.ProfilerHook(save_steps=A._profiler_steps,output_dir=A._save_dir);B.append(C)
		return B
	def learn(A,train_input_fn=_A,eval_input_fn=_A,train_max_steps=_A,train_steps=_A,eval_steps=_A,train_hooks=_A,eval_hooks=_A,early_stop_metric=_A,early_stop_patience=-1,early_stop_minimize=_B,early_stop_tolerance=0,start_epoch=0,exporters=_A,export_output_fn=_A,max_duration=_A):
		"\n    Train and evaluate the estimator for ``train_max_steps`` steps.\n    Each epoch involves ``train_steps`` training steps followed\n    by ``eval_steps`` evaluation steps. Note that each step\n    is a ``session.run()``, that is, each batch is a step.\n\n    Args:\n      train_max_steps:\n        maximum number of global steps of training to run.\n        Defaults to params.train_max_steps.\n        None-values cause learn() to terminate after *one* call to train() and evaluate(),\n        which is usually useful when using train_steps=-1\n        Non-positive values trains indefinitely in a loop (use with caution),\n        which is usually useful when used with early stopping.\n      train_steps:\n        number of training steps per epoch. For example, 100 means each\n        training epoch will end after processing 100 batches.\n        Defaults to params.train_steps.\n        Non-positive values and None-values go through the entire training set each epoch.\n      eval_steps:\n        number of evaluation steps per epoch.\n        Defaults to params.eval_steps.\n        Non-positive values and None-values go through the entire evaluation set each epoch.\n      train_input_fn:\n        Function to iterate through training set. It is passed to estimator.train.\n      eval_input_fn:\n        Function to iterate through evaluation set. It is passed to estimator.evaluate.\n      train_hooks:\n        List of SessionRunHooks uses for training. Defaults to self.get_train_hooks().\n      eval_hooks:\n        List of SessionRunHooks uses for evaluation. Defaults to self.get_eval_hooks()\n      start_epoch:\n        The epoch from which to start learn. If you want to do training and evaluation\n        for N epochs, you can call ``learn()`` in a loop as follows:\n      exporters:\n        List of exporters called at the end of each evaluation run.\n        Defaults to none.\n      export_output_fn:\n        The output format to use for exported models.\n        Only used if exporters is not None.\n\n        .. code-block:: python\n\n          for epoch in range(1,max_epoch):\n            trainer.learn(start_epoch=epoch)\n\n    Early-stopping arguments:\n      early_stop_metric:\n        String specifying the metric to early-stop on. Required with positive\n        ``early_stop_patience``. For example, 'accuracy', 'accuracy_0', 'loss', etc.\n        The string is used to extract the relevant tensor Op from the dict returned by\n        the get_eval_metric_ops method. For ``metrics`` pass to the constructor,\n        the string is one of those. For multi-class (that is, multi-metric)\n        metrics, the string may be appended with a ``_0``, ``_1``, etc. or one\n        of the ``multi_metric_names`` (one per class).\n      early_stop_patience:\n        Maximum number of epochs to wait for an improvement in the early_stop_metric\n        before breaking off training. For example, a patience of 10 means that\n        training will have 10 epochs to improve the metric before it is killed.\n        Whenever the metric is improved before running out of patience,\n        patience is reset to ``early_stop_patience``.\n        Defaults to -1 (that is, no early-stopping).\n      early_stop_minimize:\n        Set this to True (the default) for metrics that need to be minimized\n        (like ``loss``). Metrics like ``accuracy`` that need to be maximized\n        should set this to False.\n      early_stop_tolerance:\n        A non-negative tolerance for comparing early_stop_metric.\n        E.g. when maximizing the condition is current_metric > best_metric + tolerance.\n        Defaults to 0.\n      max_duration:\n        A float. When this argument is defined, the job will automatically terminate after\n        `max_duration` seconds if it has not already compeleted. \n\n    Returns:\n      The directory where the checkpoints were saved.\n      That is, save_dir.\n      You can point TensorBoard to this directory to get metrics,\n      or pass it to another Trainer via ``init_from_dir`` when doing\n      multi-phase training.\n    ";K=export_output_fn;L=start_epoch;M=eval_input_fn;N=train_input_fn;H=exporters;I=early_stop_patience;F=eval_steps;G=train_steps;D=max_duration;E=train_hooks;B=eval_hooks;C=train_max_steps
		if not callable(N):raise ValueError(_Q)
		if not callable(M):raise ValueError(_R)
		if os.environ.get(_E):raise ValueError('trainer.learn() can not be used with distributed / hogwild setups')
		if H and K:A._export_output_fn=K
		E=A.get_train_hooks()if E is _A else E;B=A.get_eval_hooks()if B is _A else B;B=[]if B is _A else B
		if C is _A:C=A.params.get(_S)
		if G is _A:G=A.params.train_steps
		if G<=0:G=_A
		if F is _A:F=A.params.eval_steps
		if F<=0:F=_A
		if I>0:assert C is not _A,'Early stopping and max_steps=None are not compatible.';A._is_early_stopping=_B;O=twml.hooks.EarlyStopHook(metric=early_stop_metric,checkpoint_dir=A._save_dir,patience=I,minimize=early_stop_minimize,tolerance=early_stop_tolerance,get_estimator_spec_fn=lambda:A.current_estimator_spec,start_epoch=L);B.append(O)
		if D is not _A:R=twml.hooks.EarlyStopDuration(max_duration=D,exit_on_end=_C,save_dir=A._save_dir,overwrite=_B);E.append(R);S=twml.hooks.EarlyStopDuration(max_duration=D,exit_on_end=_C,save_dir=A._save_dir,overwrite=_B);B.append(S)
		if not A._is_early_stopping:
			if C is not _A and C<=0:
				if D is not _A and D<0 or D is _A:logging.warn('train.max_steps is non-positive, and no early or duration stopping is configured. Training job will loop forever.')
		if C is not _A and C>0:P=twml.hooks.StopAtStepHook(last_step=C);E.append(P)
		with A.experiment_tracker.track_experiment(B,lambda:A.current_estimator_spec):
			J=L
			while _B:
				logging.info('Training epoch %d',J);A._estimator.train(N,steps=G,hooks=E);logging.info('Evaluating epoch %d',J);T=A._estimator.evaluate(M,steps=F,hooks=B)
				if H:
					U=A.estimator.latest_checkpoint()
					for Q in H:V=os.path.join(A._save_dir,'export',Q.name);Q.export(estimator=A.estimator,export_path=V,checkpoint_path=U,eval_result=T,is_the_final_export=_C)
				if C is _A:break
				if C>0 and P.stop_requested:break
				if I>0 and O.should_stop:break
				J+=1
			A.write_state_to_disk(save_dir=A._save_dir,filename=_G)
		return A._save_dir
	def get_train_spec(C,input_fn,max_steps=_A,hooks=_A):
		'Get the TrainSpec used by ``tf.train.train_and_evaluate``.';D=input_fn;B=hooks;A=max_steps
		if not callable(D):raise ValueError('Expecting callable train_input_fn')
		if A is _A:A=C.params.train_max_steps
		if A is not _A and A<=0:A=_A
		B=C.get_train_hooks()if B is _A else B;return tf.estimator.TrainSpec(input_fn=D,max_steps=A,hooks=B)
	def get_eval_spec(A,input_fn,steps=_A,delay=_A,period=_A,hooks=_A,exporters=_A):
		'Get the EvalSpec used by ``tf.train.train_and_evaluate``.';F=input_fn;D=period;E=delay;C=hooks;B=steps
		if not callable(F):raise ValueError('Expecting callable eval_input_fn')
		if B is _A:B=A.params.eval_steps
		if B<=0:B=_A
		if E is _A:E=A.params.eval_delay
		if D is _A:D=A.params.eval_period
		C=A.get_eval_hooks()if C is _A else C;G=A.params.get(_T,_A);return tf.estimator.EvalSpec(input_fn=F,steps=B,name=G,start_delay_secs=E,throttle_secs=D,hooks=C,exporters=exporters)
	def train_and_evaluate(A,train_input_fn=_A,eval_input_fn=_A,train_max_steps=_A,eval_steps=_A,eval_delay=_A,eval_period=_A,train_hooks=_A,eval_hooks=_A,early_stop_metric=_A,early_stop_patience=-1,early_stop_minimize=_B,early_stop_tolerance=0,exporters=_A,export_output_fn=_A,max_duration=_A):
		"\n    Train and evaluate the estimator for ``train_max_steps``\n    using ``tf.estimator.train_and_evaluate``.\n    With a cluster configuration provided in the ``TF_CONFIG`` environment variable, this method\n    can be used for distributed training (multi-node or multi-process).\n    Unlike the ``learn`` method, training is continuous with ``train_max_steps``.\n    For distributed use case, evaluation happens periodically.\n    That is, after ``eval_delay`` seconds, an evaluation epoch of ``eval_step`` steps\n    occurs every ``eval_period`` seconds. Evaluation happens on the most recent checkpoint.\n    TF defaults to saving checkpoints every 10 mins.\n    For local use case, training occurs for train_max_steps epochs followed by a\n    single evaluation. For local use case we therefore recommend using learn() instead\n    as it provides early-stopping and multiple evaluations.\n\n    ``train_and_evaluate`` will evaluate for ``eval_steps`` every ``eval_period`` seconds.\n    It will stop after ``train_steps`` is reached.\n\n    You must ensure that all workers/servers are assigned the same `save_dir`.\n\n    .. Note::\n\n      If the TF_CONFIG environment variable is set, this function assumes its running a distribute job.\n\n    Args:\n      train_input_fn:\n        Function to iterate through training set. It is passed to estimator.train_and_evalute\n      eval_input_fn:\n        Function to iterate through evaluation set. It is passed to estimator.train_and_evalute.\n      train_max_steps:\n        maximum number of global steps of training to run.\n        Defaults to params.train_max_steps.\n        Non-positive values and None-values train indefinitely (use with caution).\n      eval_steps:\n        number of steps per evaluation.\n        Defaults to params.eval_steps.\n        Non-positive values and None-values go through\n        the entire evaluation set for each evaluation.\n        Note that the number of eval_steps should be high enough to minimize noise.\n        This is especially true for early-stopping.\n      eval_delay:\n        Start the first evaluation after eval_delay. Defaults to params.eval_delay or 2*60s.\n      eval_period:\n        Run an evaluation every eval_period seconds. Defaults to params.eval_period or 10*60s.\n      exporters:\n        List of exporters called at the end of each evaluation run.\n        Defaults to none.\n      export_output_fn:\n        The output format to use for exported models.\n        Only used if exporters is not None.\n\n    Early-stopping arguments:\n      early_stop_metric:\n        String specifying the metric to early-stop on. Required with positive\n        ``early_stop_patience``. For example, 'accuracy', 'accuracy_0', 'loss', etc.\n        The string is used to extract the relevant tensor Op from the dict returned by\n        the get_eval_metric_ops method. For ``metrics`` pass to the constructor,\n        the string is one of those. For multi-class (that is, multi-metric)\n        metrics, the string may be appended with a ``_0``, ``_1``, etc. or one\n        of the ``multi_metric_names`` (one per class).\n      early_stop_patience:\n        Maximum number of epochs to wait for an improvement in the early_stop_metric\n        before breaking off training. For example, a patience of 10 means that\n        training will have 10 epochs to improve the metric before it is killed.\n        Whenever the metric is improved before running out of patience,\n        patience is reset to ``early_stop_patience``.\n        Defaults to -1 (that is, no early-stopping).\n      early_stop_minimize:\n        Set this to True (the default) for metrics that need to be minimized\n        (like ``loss``). Metrics like ``accuracy`` that need to be maximized\n        should set this to False.\n      early_stop_tolerance:\n        A non-negative tolerance for comparing early_stop_metric.\n        E.g. when maximizing the condition is current_metric > best_metric + tolerance.\n        Defaults to 0.\n      max_duration:\n        A float. When this argument is defined, the job will automatically terminate after\n        `max_duration` seconds if it has not already compeleted. \n\n    Returns:\n      The directory where the checkpoints were saved.\n    ";K=early_stop_patience;L=eval_input_fn;M=train_input_fn;E=max_duration;F=export_output_fn;G=exporters;H=eval_period;I=eval_delay;J=train_max_steps;D=eval_steps;C=train_hooks;B=eval_hooks;logging.info('WARNING: Trainer.train_and_evaluate is an EXPERIMENTAL API.');logging.info('Trainer.train_and_evaluate may change or be removed in future versions.')
		if not callable(M):raise ValueError(_Q)
		if not callable(L):raise ValueError(_R)
		A._exit_ps_after_training_complete()
		if A.is_evaluator():
			if A.params.get(_T)is not _A:G=_A;F=_A
			elif G and F:A._export_output_fn=F
			else:A._export_output_fn=_A
		C=A.get_train_hooks()if C is _A else C;C=[]if C is _A else C;B=A.get_eval_hooks()if B is _A else B;B=[]if B is _A else B
		if J is _A:J=A.params.get(_S)
		if D is _A:D=A.params.eval_steps
		if D<=0:D=_A
		if I is _A:I=A.params.eval_delay
		if H is _A:H=A.params.eval_period
		if K>0:N=os.path.join(A._save_dir,'earlystop_now.txt');A._is_early_stopping=_B;O=twml.hooks.EarlyStopHook(metric=early_stop_metric,checkpoint_dir=A._save_dir,patience=K,minimize=early_stop_minimize,tolerance=early_stop_tolerance,get_estimator_spec_fn=lambda:A.current_estimator_spec,file_path=N,exit_on_end=os.environ.get(_E)is not _A);B.append(O);P=twml.hooks.StopIfExistsHook(N);C.append(P)
		if E is not _A:Q=twml.hooks.EarlyStopDuration(max_duration=E,exit_on_end=_C,save_dir=A._save_dir,overwrite=A.is_chief());R=twml.hooks.EarlyStopDuration(max_duration=E,exit_on_end=os.environ.get(_E)is not _A,save_dir=A._save_dir,overwrite=_C);C.append(Q);B.append(R)
		with A.experiment_tracker.track_experiment(B,lambda:A.current_estimator_spec):S=A.get_train_spec(M,J,C);T=A.get_eval_spec(L,D,I,H,B,G);A._train_and_evaluate(S,T)
		if A.is_chief():A.write_state_to_disk(save_dir=A._save_dir,filename=_G)
		return A._save_dir
	def _train_and_evaluate(A,train_spec,eval_spec):
		'\n    Private method that calls\n    ``tf.estimator.train_and_evaluate(self._estimator, train_spec, eval_spec)``.\n    '
		try:tf.estimator.train_and_evaluate(A._estimator,train_spec,eval_spec)
		except twml.errors.EarlyStopError:
			if A.is_evaluator():0
			else:raise
	def train(A,input_fn=_A,steps=_A,hooks=_A):
		'\n    Train the estimator for `steps` training steps.\n\n    Args:\n      steps:\n        number of steps for which to perform training. For example, 100 means each\n        evaluation will end after processing 100 batches.\n        Defaults to None. i.e. trains on the entire dataset a single time.\n        Non-positive values and None-values go through the entire training set each epoch.\n      input_fn:\n        Function to iterate through training set. It is passed to estimator.train.\n      hooks:\n        List of SessionRunHooks uses for training. Defaults to self.get_train_hooks().\n    ';C=input_fn;B=hooks
		if os.environ.get(_E)and'is_calibrating'not in A.params:raise ValueError('trainer.train() can not be used with distributed / hogwild setups')
		if not callable(C):raise ValueError(_I)
		if A._is_early_stopping:raise ValueError('Can not call train() after learn() when using early stopping.')
		B=A.get_train_hooks()if B is _A else B;A._estimator.train(C,steps=steps,hooks=B);return A
	def evaluate(B,input_fn=_A,steps=_A,hooks=_A,name=_A):
		"\n    Evaluate the estimator for `steps` evaluation steps.\n\n    Args:\n      steps:\n        number of steps for which to perform evaluation. For example, 100 means each\n        evaluation will end after processing 100 batches.\n        Defaults to None. i.e. evaluates on the entire dataset a single time.\n        Negative values and None-values go through the entire training set each epoch.\n      input_fn:\n        Function to iterate through evaluation set. It is passed to estimator.evaluate.\n      hooks:\n        List of SessionRunHooks used for evaluation. Defaults to None.\n        Note that, unlike learn(), hooks defaults to None instead of self.get_eval_hooks()\n        as the latter may implement early-stopping, which isn't necessarilty the desired\n        behavior when calling evaluate() on its own.\n      name:\n        Name of the evaluation if user needs to run multiple evaluations on different data sets.\n        Metrics for different evaluations are saved in separate folders,\n        and appear separately in tensorboard.\n\n    Returns:\n      If `is_evaluator()`, returns a dict containing the evaluation metrics specified\n      in `metric_fn` keyed by name, as well as an entry `global_step` that contains\n      the value of the global step for which this evaluation was performed.\n      Otherwise (i.e. `is_evaluator() == False`), returns None.\n    ";D=input_fn;C=steps;A=hooks
		if not B.is_evaluator():return _A
		if not callable(D):raise ValueError(_I)
		A=B.get_eval_hooks()if A is _A else A;A=[]if A is _A else A;E=_A if C is not _A and C<0 else C
		with B.experiment_tracker.track_experiment(A,lambda:B.current_estimator_spec,name=name):F=B.best_or_latest_checkpoint;G=B._estimator.evaluate(D,steps=E,hooks=A,checkpoint_path=F,name=name)
		return G
	def start_tensorboard(A,port=_A):
		'\n    Start tensorboard process to visualize logs in save_dir.\n    ';B=port;logging.info('Starting tensorboard.')
		if A._tensorboard_handle:logging.warn('Tensorboard already running. Nothing done.');return
		if B is _A:
			if _J not in A.params.values():raise ValueError('You must specify a port for tensorboard to run on.')
			elif A.params.tensorboard_port is _A:return
			else:B=A.params.tensorboard_port
		E='experiments'
		if A.experiment_tracker.path:E+='/%s'%encode_url(A.experiment_tracker.experiment_id)
		C=['--logdir=%s'%A._save_dir,'--port=%d'%B]
		try:D=['email_and_launch_tensorboard',E,'--']+C;A._tensorboard_handle=subprocess.Popen(D)
		except OSError:
			try:A._tensorboard_handle=subprocess.Popen(['tensorboard']+C)
			except OSError:
				try:D=['./pants','run','twml:tensorboard','--']+C;A._tensorboard_handle=subprocess.Popen(D)
				except OSError:logging.error("No tensorboard installed, won't able to visualize training in tensorboard.")
	def stop_tensorboard(A):
		"\n    Shutdown this Trainer's associated Tensorboard.\n    "
		if A._tensorboard_handle:logging.info('Shutting down tensorboard.');A._tensorboard_handle.kill()
		else:logging.warn('No known tensorboard process. Nothing done.')
	def calibrate(B,calibrator,steps=_A,input_fn=_A,save_calibrator=_B,hooks=_A):
		'\n    Calibrate the calibrator for `steps` calibration steps using the estimator.train method.\n    The build_graph passed to the Trainer constructor should\n    call calibrator.accumulate using something like tf.py_func.\n    That way, when this method calls estimator.train the calibrator will\n    accumulate one epoch of samples. After which, this method calls calibrator.calibrate().\n    It is up to the user to then call calibrator.save() to save the calibrated Layer\n    and other information to disk for multi-phase training.\n\n    Args:\n      calibrator:\n        a twml.Calibrator instance or a dict of the form {name(str): twml.Calibrator}.\n      steps:\n        Maximum steps to accumulate examples for calibration. Optional.\n        If not specified, examples will be accumulated until all downsampled parts are processed.\n      input_fn:\n        Function to iterate through training set. It is passed to estimator.train.\n      hooks:\n        List of SessionRunHooks uses for training. Defaults to self.get_train_hooks().\n      save_calibrator:\n        Boolean (default: True). If set to True it will save the calibrator layer.\n    ';E='default';F=steps;D=input_fn;A=calibrator
		if not callable(D):raise ValueError(_I)
		if isinstance(A,twml.contrib.calibrators.Calibrator):A={E:A}
		B._estimator.train(D,steps=1);G=F if F is not _A else-1
		for (H,C) in sorted(A.items(),key=itemgetter(0)):
			I=0
			for K in B._estimator.predict(D,hooks=hooks,yield_single_examples=_C):
				if G>0 and I>G:break
				C.accumulate_feature(K);I+=1
			C.calibrate()
		for J in tf.io.gfile.listdir(B._save_dir):
			if not J.startswith('events'):tf.io.gfile.remove(os.path.join(B._save_dir,J))
		if save_calibrator:
			if len(A)==1:A=A[E];A.save(B.params.save_dir,name=A.name,verbose=_B)
			else:
				for (H,C) in A.items():C.save(B.params.save_dir,name=C.name+str(H),verbose=_B)
	def predict(B,*C,**A):
		'\n    Wrapper over the tensorflow `Estimator.predict\n    <https://www.tensorflow.org/api_docs/python/tf/estimator/Estimator#predict>`_.\n    method. See that documentation for description of arguments accepted.\n\n    If hooks is passed as an argument, the specified hooks are used.\n    Else when profiler_steps is specified in the constructor of the Trainer, a\n    tf.train.ProfilerHook is passed to the predict interface.\n    Otherwise, hooks is set to an empty list.\n    ';D='hooks'
		if D not in A and len(C)<3:A[D]=B.get_predict_hooks()
		return B.estimator.predict(*(C),**A)
	def hub_export(B,name,serving_input_receiver_fn,export_dir=_A,checkpoint_path=_A,export_task_type_overrider=_A):
		'\n    Exports registered modules into a save directory.\n\n    This method creates a directory under export_path with the save TF Hub.\n    One sub-directory (named export_name) per module registered via register_module_for_export.\n\n    Arguments:\n      name:\n        unique name of the module to export.\n      serving_input_receiver_fn:\n        A function with no arguments that returns a ServingInputReceiver.\n        This is used with the estimator passed to export() to build the graph (in PREDICT mode)\n        that registers the modules for export. The model in that graph is never run,\n        so the actual data provided by this input fn does not matter.\n      export_dir:\n        A string containing a directory where to write the export directories.\n        Defaults to the save_dir.\n      checkpoint_path:\n        The checkpoint path to export. Defaults to the latest.\n      export_task_type_overrider:\n        Specifies the task type that will override the default task type used for export\n        (hogwild training defaults to evaluator, otherwise, defaults to chief)\n    ';E=export_task_type_overrider;D=checkpoint_path;A=export_dir
		if E:
			if not B.is_task_type(E):logging.info(f"Trainer.hub_export ignored due to process not being {E}");return
		elif B._using_hogwild:
			if not B.is_evaluator():logging.info('Trainer.hub_export ignored due to the process not being evaluator.');return
		elif not B.is_chief():logging.info('Trainer.hub_export ignored due to the process not being chief.');return
		if A:A=sanitize_hdfs_path(A)
		if D:D=sanitize_hdfs_path(D)
		else:D=B.best_or_latest_checkpoint
		A=A if A is not _A else B._save_dir;I=hub.LatestModuleExporter(name,serving_input_receiver_fn);C=I.export(estimator=B.estimator,export_path=A,checkpoint_path=D)
		if isinstance(C,bytes):C=C.decode()
		J=tf.io.gfile.listdir(C);H=os.path.join(A,'backups',datetime.datetime.now().strftime('%Y-%m-%d_%H-%M-%S'))
		for F in J:
			K=os.path.join(C,F);G=os.path.join(A,F)
			if tf.io.gfile.exists(G):tf.io.gfile.makedirs(H);L=os.path.join(H,F);tf.io.gfile.rename(G,L)
			tf.io.gfile.rename(K,G)
		tf.io.gfile.rmtree(C)
	def _is_on_gke(B):
		'Returns True if running on gke.';A=os.environ.get('TWML_JOB_CLUSTER')
		if not A or A in{'smf1','atla'}:return _C
		return _B
	def _maybe_del_tsd_exit(A,state_files):
		'Handle potential early exit and TwitterSetDeployment deletion.\n\n      If:\n        - distributed training\n        - running GKE\n        - training is finished (all state_files exists)\n      we will exit early and not restart work\n\n      If --distributed_training_cleanup = True then we will also handle\n      cleaning up the TwitterSetDeployments.\n\n      Args:\n        state_files: A python list indicate state files to determine the finish \n        state of the job.\n    '
		if A.experiment_tracker._env_eligible_for_recording_experiment:
			B=A.experiment_tracker.get_run_status()
			if B and B not in{'Success','Failed'}:logging.info(f"Not exiting early because experiment is still {B}.");return
		if not A._is_on_gke():logging.info('No need to exit early because running on prem.');return
		D=[twml.util.file_exist_in_dir(A._save_dir,B)for B in state_files];E=A._params.get(_D)and all(D)
		if not E:return
		logging.info(f"Exiting early because a _SUCCESS file already exists in {A._save_dir}")
		if A._params.get(_M):C='-'.join([os.environ[_P],os.environ['TWML_DISTRIBUTED_JOB_TYPE'],os.environ[_O]]);logging.info(f"Deleting TwitterSetDeployment {C}");kubectl_delete_by_name(zone=_A,namespace=os.environ[_N],resource_type=Resource.TWITTERSETDEPLOYMENTS.value,resource_name=C,wait=_C)
		sys.exit(0)
	def write_state_to_disk(C,save_dir,filename=_G):
		'Write state file to disk to indicate the state of training process. This is usually used \n      to mark the state of training progress and determine the start when job restarts/resumes.\n    Args:\n      save_dir: A str of local/gcs/hdfs dir to write the state file.\n      file_name: A str indicate the state file. Default to `_SUCCESS`.\n    ';A=os.path.join(save_dir,filename)
		if tf.io.gfile.exists(A):tf.logging.warn(f"{A} already exist.");return
		with tf.io.gfile.GFile(A,'w')as B:B.write('')