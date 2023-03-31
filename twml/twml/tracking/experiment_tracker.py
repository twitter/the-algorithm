'\nThis module contains the experiment tracker for tracking training in ML Metastore\n'
_H='%m_%d_%Y_%I_%M_%p'
_G='run_name'
_F='experiment_name'
_E=False
_D='project_name'
_C=True
_B='owner'
_A=None
from contextlib import contextmanager
from datetime import datetime
import getpass,hashlib,os,re,sys,time
from absl import logging
import tensorflow.compat.v1 as tf
from twml.hooks import MetricsUpdateHook
try:from urllib import quote as encode_url
except ImportError:from urllib.parse import quote as encode_url
try:import requests;from com.twitter.mlmetastore.modelrepo.client import ModelRepoClient;from com.twitter.mlmetastore.modelrepo.core.path import check_valid_id,get_components_from_id,generate_id;from com.twitter.mlmetastore.modelrepo.core import DeepbirdRun,Experiment,FeatureConfig,FeatureConfigFeature,Model,ProgressReport,Project,StatusUpdate
except ImportError:ModelRepoClient=_A
class ExperimentTracker:
	'\n  A tracker that records twml runs in ML Metastore.\n  '
	def __init__(A,params,run_config,save_dir):
		'\n\n    Args:\n      params (python dict):\n        The trainer params. ExperimentTracker uses `params.experiment_tracking_path` (String) and\n        `params.disable_experiment_tracking`.\n        If `experiment_tracking_path` is set to None, the tracker tries to guess a path with\n        save_dir.\n        If `disable_experiment_tracking` is True, the tracker is disabled.\n      run_config (tf.estimator.RunConfig):\n        The run config used by the estimator.\n      save_dir (str):\n        save_dir of the trainer\n    ';B=params
		if isinstance(B,dict):A._params=B
		else:logging.warning('Please stop using HParams and use python dicts. HParams are removed in TF 2');A._params=dict(((B,A)for(B,A)in B.values().items()if A!='null'))
		A._run_config=run_config;A._graceful_shutdown_port=A._params.get('health_port');A.tracking_path=A._params.get('experiment_tracking_path');E=A.tracking_path is not _A and len(A.tracking_path)>256
		if E:raise ValueError('Experiment Tracking Path longer than 256 characters')
		A.disabled=A._params.get('disable_experiment_tracking',_E)or not A._is_env_eligible_for_tracking()or ModelRepoClient is _A;A._is_hogwild=bool(os.environ.get('TWML_HOGWILD_PORTS'));A._is_distributed=bool(os.environ.get('TF_CONFIG'));A._client=_A if A.disabled else ModelRepoClient();D=A.run_name_from_environ();F=A.tracking_path is not _A or D is not _A
		if A._is_hogwild or A._is_distributed:
			A._env_eligible_for_recording_experiment=A._run_config.task_type=='evaluator'
			if F:A._env_eligible_for_recording_export_metadata=A._run_config.task_type=='chief'
			else:logging.info('experiment_tracking_path is not set and can not be inferred. Recording export metadata is disabled because the chief node and eval node are setting different experiment tracking paths.');A._env_eligible_for_recording_export_metadata=_E
		else:A._env_eligible_for_recording_experiment=_C;A._env_eligible_for_recording_export_metadata=_C
		if not A.disabled:
			if A.tracking_path:
				try:check_valid_id(A.tracking_path)
				except ValueError as C:logging.error(f"Invalid experiment tracking path provided. Sanitizing: {A.tracking_path}\nError: {C}");A.tracking_path=generate_id(owner=A.path[_B],project_name=A.path[_D],experiment_name=A.path[_F],run_name=A.path[_G]);logging.error(f"Generated sanitized experiment tracking path: {A.tracking_path}")
			else:logging.info('No experiment_tracking_path set. Experiment Tracker will try to guess a path');A.tracking_path=A.guess_path(save_dir,D);logging.info('Guessed path: %s',A.tracking_path)
			try:check_valid_id(A.tracking_path)
			except ValueError as C:logging.error('Could not generate valid experiment tracking path. Disabling tracking. '+'Error:\n{}'.format(C));A.disabled=_C
		A.project_id=_A if A.disabled else '{}:{}'.format(A.path[_B],A.path[_D]);A.base_run_id=_A if A.disabled else A.tracking_path;A._current_run_name_suffix=_A;A._current_tracker_hook=_A
		if A.disabled:logging.info('Experiment Tracker is disabled')
		else:logging.info('Experiment Tracker initialized with base run id: %s',A.base_run_id)
	@contextmanager
	def track_experiment(self,eval_hooks,get_estimator_spec_fn,name=_A):
		"\n    A context manager for tracking experiment. It should wrap the training loop.\n    An experiment tracker eval hook is appended to eval_hooks to collect metrics.\n\n    Args:\n      eval_hooks (list):\n        The list of eval_hooks to be used. When it's not None, and does not contain any ,\n        MetricsUpdateHook an experiment tracker eval hook is appended to it. When it contains\n        any MetricsUpdateHook, this tracker is disabled to avoid conflict with legacy Model Repo\n        tracker (`TrackRun`).\n      get_estimator_spec_fn (func):\n        A function to get the current EstimatorSpec of the trainer, used by the eval hook.\n      name (str);\n        Name of this training or evaluation. Used as a suffix of the run_id.\n\n    Returns:\n      The tracker's eval hook which is appended to eval_hooks.\n    ";C=eval_hooks;A=self
		if C is not _A:A.disabled=A.disabled or any((isinstance(A,MetricsUpdateHook)for A in C))
		logging.info('Is environment eligible for recording experiment: %s',A._env_eligible_for_recording_experiment)
		if A._env_eligible_for_recording_experiment and A._graceful_shutdown_port:requests.post('http://localhost:{}/track_training_start'.format(A._graceful_shutdown_port))
		if A.disabled or C is _A:yield _A
		else:
			assert A._current_tracker_hook is _A,'experiment tracking has been started already'
			if name is not _A:A._current_run_name_suffix='_'+name
			logging.info('Starting experiment tracking. Path: %s',A._current_run_id);logging.info('Is environment eligible for recording export metadata: %s',A._env_eligible_for_recording_export_metadata);logging.info('This run will be available at: http://go/mldash/experiments/%s',encode_url(A.experiment_id))
			try:A._record_run();A._add_run_status(StatusUpdate(A._current_run_id,status='RUNNING'));A._register_for_graceful_shutdown();A._current_tracker_hook=A.create_eval_hook(get_estimator_spec_fn)
			except Exception as B:logging.error('Failed to record run. This experiment will not be tracked. Error: %s',str(B));A._current_tracker_hook=_A
			if A._current_tracker_hook is _A:yield _A
			else:
				try:C.append(A._current_tracker_hook);yield A._current_tracker_hook
				except Exception as B:A._add_run_status(StatusUpdate(A._current_run_id,status='FAILED',description=str(B)));A._deregister_for_graceful_shutdown();A._current_tracker_hook=_A;A._current_run_name_suffix=_A;logging.error('Experiment tracking done. Experiment failed.');raise
				try:
					if A._current_tracker_hook.metric_values:A._record_update(A._current_tracker_hook.metric_values)
					A._add_run_status(StatusUpdate(A._current_run_id,status='SUCCESS'));logging.info('Experiment tracking done. Experiment succeeded.')
				except Exception as B:logging.error('Failed to update mark run as successful. Error: %s',str(B))
				finally:A._deregister_for_graceful_shutdown();A._current_tracker_hook=_A;A._current_run_name_suffix=_A
	def create_eval_hook(A,get_estimator_spec_fn):'\n    Create an eval_hook to track eval metrics\n\n    Args:\n      get_estimator_spec_fn (func):\n        A function that returns the current EstimatorSpec of the trainer.\n    ';return MetricsUpdateHook(get_estimator_spec_fn=get_estimator_spec_fn,add_metrics_fn=A._record_update)
	def register_model(A,export_path):
		'\n    Record the exported model.\n\n    Args:\n      export_path (str):\n        The path to the exported model.\n    ';B=export_path
		if A.disabled:return _A
		try:logging.info('Model is exported to %s. Computing hash of the model.',B);C=A.compute_model_hash(B);logging.info('Model hash: %s. Registering it in ML Metastore.',C);A._client.register_model(Model(C,A.path[_B],A.base_run_id))
		except Exception as D:logging.error('Failed to register model. Error: %s',str(D))
	def export_feature_spec(A,feature_spec_dict):
		'\n    Export feature spec to ML Metastore (go/ml-metastore).\n\n    Please note that the feature list in FeatureConfig only keeps the list of feature hash ids due\n    to the 1mb upper limit for values in manhattan, and more specific information (feature type,\n    feature name) for each feature config feature is stored separately in FeatureConfigFeature dataset.\n\n    Args:\n       feature_spec_dict (dict): A dictionary obtained from FeatureConfig.get_feature_spec()\n    ';G='featureType';E='featureName';F=feature_spec_dict
		if A.disabled or not A._env_eligible_for_recording_export_metadata:return _A
		try:logging.info('Exporting feature spec to ML Metastore.');B=F['features'];C=F['labels'];D=F['weight'];A._client.add_feature_config(FeatureConfig(A._current_run_id,list(B.keys()),list(C.keys()),list(D.keys())));H=[FeatureConfigFeature(hash_id=B,feature_name=A[E],feature_type=A[G])for(B,A)in zip(B.keys(),B.values())];A._client.add_feature_config_features(list(B.keys()),H);I=[FeatureConfigFeature(hash_id=A,feature_name=B[E])for(A,B)in zip(C.keys(),C.values())];A._client.add_feature_config_features(list(C.keys()),I);J=[FeatureConfigFeature(hash_id=B,feature_name=A[E],feature_type=A[G])for(B,A)in zip(D.keys(),D.values())];A._client.add_feature_config_features(list(D.keys()),J)
		except Exception as K:logging.error('Failed to export feature spec. Error: %s',str(K))
	@property
	def path(self):
		if self.disabled:return _A
		return get_components_from_id(self.tracking_path,ensure_valid_id=_E)
	@property
	def experiment_id(self):
		A=self
		if A.disabled:return _A
		return'%s:%s:%s'%(A.path[_B],A.path[_D],A.path[_F])
	@property
	def _current_run_name(self):
		'\n    Return the current run name.\n    ';A=self
		if A._current_run_name_suffix is not _A:return A.path[_G]+A._current_run_name_suffix
		else:return A.path[_G]
	@property
	def _current_run_id(self):
		'\n    Return the current run id.\n    ';A=self
		if A._current_run_name_suffix is not _A:return A.base_run_id+A._current_run_name_suffix
		else:return A.base_run_id
	def get_run_status(A):
		if not A.disabled:return A._client.get_latest_dbv2_status(A._current_run_id)
	def _add_run_status(A,status):
		'\n    Add run status with underlying client.\n\n    Args:\n      status (StatusUpdate):\n        The status update to add.\n    '
		if not A.disabled and A._env_eligible_for_recording_experiment:A._client.add_run_status(status)
	def _record_run(A):
		'\n    Record the run in ML Metastore.\n    '
		if A.disabled or not A._env_eligible_for_recording_experiment:return _A
		if not A._client.project_exists(A.project_id):A._client.add_project(Project(A.path[_D],A.path[_B]));time.sleep(1)
		if not A._client.experiment_exists(A.experiment_id):A._client.add_experiment(Experiment(A.path[_F],A.path[_B],A.project_id,''));time.sleep(1)
		B=DeepbirdRun(A.experiment_id,A._current_run_name,'',{'raw_command':' '.join(sys.argv)},A._params);A._client.add_deepbird_run(B,force=_C);time.sleep(1)
	def _record_update(A,metrics):
		'\n    Record metrics update in ML Metastore.\n\n    Args:\n      metrics (dict):\n        The dict of the metrics and their values.\n    '
		if A.disabled or not A._env_eligible_for_recording_experiment:return _A
		C={}
		for (D,B) in metrics.items():
			if hasattr(B,'item'):C[D]=B.item()if B.size==1 else str(B.tolist())
			else:logging.warning('Ignoring %s because the value (%s) is not valid'%(D,str(B)))
		E=ProgressReport(A._current_run_id,C)
		try:A._client.add_progress_report(E)
		except Exception as F:logging.error('Failed to record metrics in ML Metastore. Error: {}'.format(F));logging.error('Run ID: {}'.format(A._current_run_id));logging.error('Progress Report: {}'.format(E.to_json_string()))
	def _register_for_graceful_shutdown(A):
		'\n    Register the tracker with the health server, enabling graceful shutdown.\n\n    Returns:\n      (Response) health server response\n    '
		if A._graceful_shutdown_port and not A.disabled and A._env_eligible_for_recording_experiment:return requests.post('http://localhost:{}/register_id/{}'.format(A._graceful_shutdown_port,A._current_run_id))
	def _deregister_for_graceful_shutdown(A):
		'\n    Deregister the tracker with the health server, disabling graceful shutdown.\n\n    Returns:\n      (Response) health server response\n    '
		if A._graceful_shutdown_port and not A.disabled and A._env_eligible_for_recording_experiment:return requests.post('http://localhost:{}/deregister_id/{}'.format(A._graceful_shutdown_port,A._current_run_id))
	def _is_env_eligible_for_tracking(D):'\n    Determine if experiment tracking should run in the env.\n    ';A='TEST_EXP_TRACKER';B=os.environ.get('PYTEST_CURRENT_TEST')is not _A and os.environ.get(A)is _A;C=getpass.getuser()=='scoot-service'and os.environ.get(A)is _A;return not B and not C
	@classmethod
	def run_name_from_environ(D):
		'\n    Create run id from environment if possible.\n    ';B=os.environ.get('TWML_JOB_NAME');A=os.environ.get('TWML_JOB_LAUNCH_TIME')
		if not B or not A:return _A
		try:C=datetime.strptime(A,'%Y-%m-%dT%H:%M:%S.%f')
		except ValueError:C=A.replace('-','_').replace('T','_').replace(':','_').replace('.','_')
		return '{}_{}'.format(B,C.strftime(_H))
	@classmethod
	def guess_path(H,save_dir,run_name=_A):
		'\n    Guess an experiment tracking path based on save_dir.\n\n    Returns:\n      (str) guessed path\n    ';E='default';C=save_dir;B=run_name
		if not B:B='Unnamed_{}'.format(datetime.now().strftime(_H))
		if C.startswith('hdfs://'):
			F=re.search('/user/([a-z0-9\\-_]+)/([a-z0-9\\-_]+)',C)
			if F:G=F.groups();D=G[0];A=G[1];return generate_id(D,E,A,B)
		D=getpass.getuser();A=re.sub('^[a-z0-9\\-_]',os.path.basename(C),'')
		if not A:A='unnamed'
		return generate_id(D,E,A,B)
	@classmethod
	def compute_model_hash(G,export_path):
		'\n    Computes the hash of an exported model. This is a gfile version of\n    twitter.mlmetastore.common.versioning.compute_hash. The two functions should generate\n    the same hash when given the same model.\n\n    Args:\n      export_path (str):\n        The path to the exported model.\n\n    Returns:\n      (str) hash of the exported model\n    ';A=[]
		for (B,H,D) in tf.io.gfile.walk(export_path):
			for E in sorted(D):A.append(os.path.join(B,E))
		A.sort();C=hashlib.new('sha1')
		for B in A:
			with tf.io.gfile.GFile(B,'rb')as F:C.update(F.read())
		return C.hexdigest()