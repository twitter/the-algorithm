"""
This module contains the etwitterperiment tracker for tracking training in ML Metastore
"""
from contetwittertlib import contetwittertmanager
from datetime import datetime
import getpass
import hashlib
import os
import re
import sys
import time

from absl import logging
import tensorflow.compat.v1 as tf
from twml.hooks import MetricsUpdateHook


try:
  from urllib import quote as encode_url
etwittercept ImportError:
  from urllib.parse import quote as encode_url


try:
  # ML Metastore packages might not be available on GCP.
  # If they are not found, tracking is disabled
  import requests
  from com.twitter.mlmetastore.modelrepo.client import ModelRepoClient
  from com.twitter.mlmetastore.modelrepo.core.path import (
    check_valid_id, get_components_from_id, generate_id)
  from com.twitter.mlmetastore.modelrepo.core import (
    DeepbirdRun, Etwitterperiment, FeatureConfig, FeatureConfigFeature, Model, ProgressReport, Project, StatusUpdate)
etwittercept ImportError:
  ModelRepoClient = None


class EtwitterperimentTracker(object):
  """
  A tracker that records twml runs in ML Metastore.
  """

  def __init__(self, params, run_config, save_dir):
    """

    Args:
      params (python dict):
        The trainer params. EtwitterperimentTracker uses `params.etwitterperiment_tracking_path` (String) and
        `params.disable_etwitterperiment_tracking`.
        If `etwitterperiment_tracking_path` is set to None, the tracker tries to guess a path with
        save_dir.
        If `disable_etwitterperiment_tracking` is True, the tracker is disabled.
      run_config (tf.estimator.RunConfig):
        The run config used by the estimator.
      save_dir (str):
        save_dir of the trainer
    """
    if isinstance(params, dict):
      self._params = params
    else:
      # preserving backward compatibility for people still using HParams
      logging.warning("Please stop using HParams and use python dicts. HParams are removed in TF 2")
      self._params = dict((k, v) for k, v in params.values().items() if v != 'null')
    self._run_config = run_config
    self._graceful_shutdown_port = self._params.get('health_port')

    self.tracking_path = self._params.get('etwitterperiment_tracking_path')
    is_tracking_path_too_long = self.tracking_path is not None and len(self.tracking_path) > 256

    if is_tracking_path_too_long:
      raise ValueError("Etwitterperiment Tracking Path longer than 256 characters")

    self.disabled = (
      self._params.get('disable_etwitterperiment_tracking', False) or
      not self._is_env_eligible_for_tracking() or
      ModelRepoClient is None
    )

    self._is_hogwild = bool(os.environ.get('TWML_HOGWILD_PORTS'))

    self._is_distributed = bool(os.environ.get('TF_CONFIG'))

    self._client = None if self.disabled else ModelRepoClient()

    run_name_from_environ = self.run_name_from_environ()
    run_name_can_be_inferred = (
      self.tracking_path is not None or run_name_from_environ is not None)

    # Turn the flags off as needed in hogwild / distributed
    if self._is_hogwild or self._is_distributed:
      self._env_eligible_for_recording_etwitterperiment = (
        self._run_config.task_type == "evaluator")
      if run_name_can_be_inferred:
        self._env_eligible_for_recording_etwitterport_metadata = (
          self._run_config.task_type == "chief")
      else:
        logging.info(
          'etwitterperiment_tracking_path is not set and can not be inferred. '
          'Recording etwitterport metadata is disabled because the chief node and eval node '
          'are setting different etwitterperiment tracking paths.')
        self._env_eligible_for_recording_etwitterport_metadata = False
    else:
      # Defaults to True
      self._env_eligible_for_recording_etwitterperiment = True
      self._env_eligible_for_recording_etwitterport_metadata = True

    if not self.disabled:
      # Sanitize passed in etwitterperiment tracking paths. e.g. own:proJ:etwitterp:Run.Name
      # -> own:proj:etwitterp:Run_Name
      if self.tracking_path:
        try:
          check_valid_id(self.tracking_path)
        etwittercept ValueError as err:
          logging.error(f'Invalid etwitterperiment tracking path provided. Sanitizing: {self.tracking_path}\nError: {err}')
          self.tracking_path = generate_id(
            owner=self.path['owner'],
            project_name=self.path['project_name'],
            etwitterperiment_name=self.path['etwitterperiment_name'],
            run_name=self.path['run_name']
          )
          logging.error(f'Generated sanitized etwitterperiment tracking path: {self.tracking_path}')
      else:
        logging.info(
          'No etwitterperiment_tracking_path set. Etwitterperiment Tracker will try to guess a path')
        self.tracking_path = self.guess_path(save_dir, run_name_from_environ)
        logging.info('Guessed path: %s', self.tracking_path)

      # additional check to see if generated path is valid
      try:
        check_valid_id(self.tracking_path)
      etwittercept ValueError as err:
        logging.error(
          'Could not generate valid etwitterperiment tracking path. Disabling tracking. ' +
          'Error:\n{}'.format(err)
        )
        self.disabled = True

    self.project_id = None if self.disabled else '{}:{}'.format(
      self.path['owner'], self.path['project_name'])
    self.base_run_id = None if self.disabled else self.tracking_path
    self._current_run_name_suffitwitter = None

    self._current_tracker_hook = None

    if self.disabled:
      logging.info('Etwitterperiment Tracker is disabled')
    else:
      logging.info('Etwitterperiment Tracker initialized with base run id: %s', self.base_run_id)

  @contetwittertmanager
  def track_etwitterperiment(self, eval_hooks, get_estimator_spec_fn, name=None):
    """
    A contetwittert manager for tracking etwitterperiment. It should wrap the training loop.
    An etwitterperiment tracker eval hook is appended to eval_hooks to collect metrics.

    Args:
      eval_hooks (list):
        The list of eval_hooks to be used. When it's not None, and does not contain any ,
        MetricsUpdateHook an etwitterperiment tracker eval hook is appended to it. When it contains
        any MetricsUpdateHook, this tracker is disabled to avoid conflict with legacy Model Repo
        tracker (`TrackRun`).
      get_estimator_spec_fn (func):
        A function to get the current EstimatorSpec of the trainer, used by the eval hook.
      name (str);
        Name of this training or evaluation. Used as a suffitwitter of the run_id.

    Returns:
      The tracker's eval hook which is appended to eval_hooks.
    """

    # disable this tracker if legacy TrackRun hook is present
    # TODO: remove this once we completely deprecate the old TrackRun interface
    if eval_hooks is not None:
      self.disabled = self.disabled or any(isinstance(twitter, MetricsUpdateHook) for twitter in eval_hooks)

    logging.info('Is environment eligible for recording etwitterperiment: %s',
                 self._env_eligible_for_recording_etwitterperiment)

    if self._env_eligible_for_recording_etwitterperiment and self._graceful_shutdown_port:
      requests.post('http://localhost:{}/track_training_start'.format(
        self._graceful_shutdown_port
      ))

    if self.disabled or eval_hooks is None:
      yield None
    else:
      assert self._current_tracker_hook is None, 'etwitterperiment tracking has been started already'

      if name is not None:
        self._current_run_name_suffitwitter = '_' + name

      logging.info('Starting etwitterperiment tracking. Path: %s', self._current_run_id)
      logging.info('Is environment eligible for recording etwitterport metadata: %s',
                   self._env_eligible_for_recording_etwitterport_metadata)
      logging.info('This run will be available at: http://go/mldash/etwitterperiments/%s',
                   encode_url(self.etwitterperiment_id))

      try:
        self._record_run()
        self._add_run_status(StatusUpdate(self._current_run_id, status='RUNNING'))
        self._register_for_graceful_shutdown()

        self._current_tracker_hook = self.create_eval_hook(get_estimator_spec_fn)
      etwittercept Etwitterception as err:
        logging.error(
          'Failed to record run. This etwitterperiment will not be tracked. Error: %s', str(err))
        self._current_tracker_hook = None

      if self._current_tracker_hook is None:
        yield None
      else:
        try:
          eval_hooks.append(self._current_tracker_hook)
          yield self._current_tracker_hook
        etwittercept Etwitterception as err:
          self._add_run_status(
            StatusUpdate(self._current_run_id, status='FAILED', description=str(err)))
          self._deregister_for_graceful_shutdown()
          self._current_tracker_hook = None
          self._current_run_name_suffitwitter = None
          logging.error('Etwitterperiment tracking done. Etwitterperiment failed.')
          raise

        try:
          if self._current_tracker_hook.metric_values:
            self._record_update(self._current_tracker_hook.metric_values)
          self._add_run_status(StatusUpdate(self._current_run_id, status='SUCCESS'))
          logging.info('Etwitterperiment tracking done. Etwitterperiment succeeded.')
        etwittercept Etwitterception as err:
          logging.error(
            'Failed to update mark run as successful. Error: %s', str(err))
        finally:
          self._deregister_for_graceful_shutdown()
          self._current_tracker_hook = None
          self._current_run_name_suffitwitter = None

  def create_eval_hook(self, get_estimator_spec_fn):
    """
    Create an eval_hook to track eval metrics

    Args:
      get_estimator_spec_fn (func):
        A function that returns the current EstimatorSpec of the trainer.
    """
    return MetricsUpdateHook(
      get_estimator_spec_fn=get_estimator_spec_fn,
      add_metrics_fn=self._record_update)

  def register_model(self, etwitterport_path):
    """
    Record the etwitterported model.

    Args:
      etwitterport_path (str):
        The path to the etwitterported model.
    """
    if self.disabled:
      return None

    try:
      logging.info('Model is etwitterported to %s. Computing hash of the model.', etwitterport_path)
      model_hash = self.compute_model_hash(etwitterport_path)
      logging.info('Model hash: %s. Registering it in ML Metastore.', model_hash)
      self._client.register_model(Model(model_hash, self.path['owner'], self.base_run_id))
    etwittercept Etwitterception as err:
      logging.error('Failed to register model. Error: %s', str(err))

  def etwitterport_feature_spec(self, feature_spec_dict):
    """
    Etwitterport feature spec to ML Metastore (go/ml-metastore).

    Please note that the feature list in FeatureConfig only keeps the list of feature hash ids due
    to the 1mb upper limit for values in manhattan, and more specific information (feature type,
    feature name) for each feature config feature is stored separately in FeatureConfigFeature dataset.

    Args:
       feature_spec_dict (dict): A dictionary obtained from FeatureConfig.get_feature_spec()
    """
    if self.disabled or not self._env_eligible_for_recording_etwitterport_metadata:
      return None

    try:
      logging.info('Etwitterporting feature spec to ML Metastore.')
      feature_list = feature_spec_dict['features']
      label_list = feature_spec_dict['labels']
      weight_list = feature_spec_dict['weight']
      self._client.add_feature_config(FeatureConfig(self._current_run_id, list(feature_list.keys()),
                                                    list(label_list.keys()), list(weight_list.keys())))

      feature_config_features = [
        FeatureConfigFeature(
          hash_id=_feature_hash_id,
          feature_name=_feature['featureName'],
          feature_type=_feature['featureType']
        )
        for _feature_hash_id, _feature in zip(feature_list.keys(), feature_list.values())
      ]
      self._client.add_feature_config_features(list(feature_list.keys()), feature_config_features)

      feature_config_labels = [
        FeatureConfigFeature(
          hash_id=_label_hash_id,
          feature_name=_label['featureName']
        )
        for _label_hash_id, _label in zip(label_list.keys(), label_list.values())
      ]
      self._client.add_feature_config_features(list(label_list.keys()), feature_config_labels)

      feature_config_weights = [
        FeatureConfigFeature(
          hash_id=_weight_hash_id,
          feature_name=_weight['featureName'],
          feature_type=_weight['featureType']
        )
        for _weight_hash_id, _weight in zip(weight_list.keys(), weight_list.values())
      ]
      self._client.add_feature_config_features(list(weight_list.keys()), feature_config_weights)

    etwittercept Etwitterception as err:
      logging.error('Failed to etwitterport feature spec. Error: %s', str(err))

  @property
  def path(self):
    if self.disabled:
      return None
    return get_components_from_id(self.tracking_path, ensure_valid_id=False)

  @property
  def etwitterperiment_id(self):
    if self.disabled:
      return None
    return '%s:%s:%s' % (self.path['owner'], self.path['project_name'],
                         self.path['etwitterperiment_name'])

  @property
  def _current_run_name(self):
    """
    Return the current run name.
    """
    if self._current_run_name_suffitwitter is not None:
      return self.path['run_name'] + self._current_run_name_suffitwitter
    else:
      return self.path['run_name']

  @property
  def _current_run_id(self):
    """
    Return the current run id.
    """
    if self._current_run_name_suffitwitter is not None:
      return self.base_run_id + self._current_run_name_suffitwitter
    else:
      return self.base_run_id

  def get_run_status(self) -> str:
    if not self.disabled:
      return self._client.get_latest_dbv2_status(self._current_run_id)

  def _add_run_status(self, status):
    """
    Add run status with underlying client.

    Args:
      status (StatusUpdate):
        The status update to add.
    """
    if not self.disabled and self._env_eligible_for_recording_etwitterperiment:
      self._client.add_run_status(status)

  def _record_run(self):
    """
    Record the run in ML Metastore.
    """
    if self.disabled or not self._env_eligible_for_recording_etwitterperiment:
      return None

    if not self._client.project_etwitterists(self.project_id):
      self._client.add_project(Project(self.path['project_name'], self.path['owner']))
      time.sleep(1)

    if not self._client.etwitterperiment_etwitterists(self.etwitterperiment_id):
      self._client.add_etwitterperiment(Etwitterperiment(
        self.path['etwitterperiment_name'], self.path['owner'], self.project_id, ''))
      time.sleep(1)

    run = DeepbirdRun(self.etwitterperiment_id, self._current_run_name, '',
                      {'raw_command': ' '.join(sys.argv)}, self._params)
    self._client.add_deepbird_run(run, force=True)
    time.sleep(1)

  def _record_update(self, metrics):
    """
    Record metrics update in ML Metastore.

    Args:
      metrics (dict):
        The dict of the metrics and their values.
    """

    if self.disabled or not self._env_eligible_for_recording_etwitterperiment:
      return None

    reported_metrics = {}
    for k, v in metrics.items():

      if hasattr(v, 'item'):
        reported_metrics[k] = v.item() if v.size == 1 else str(v.tolist())
      else:
        logging.warning("Ignoring %s because the value (%s) is not valid" % (k, str(v)))

    report = ProgressReport(self._current_run_id, reported_metrics)

    try:
      self._client.add_progress_report(report)
    etwittercept Etwitterception as err:
      logging.error('Failed to record metrics in ML Metastore. Error: {}'.format(err))
      logging.error('Run ID: {}'.format(self._current_run_id))
      logging.error('Progress Report: {}'.format(report.to_json_string()))

  def _register_for_graceful_shutdown(self):
    """
    Register the tracker with the health server, enabling graceful shutdown.

    Returns:
      (Response) health server response
    """
    if self._graceful_shutdown_port and not self.disabled and self._env_eligible_for_recording_etwitterperiment:
      return requests.post('http://localhost:{}/register_id/{}'.format(
        self._graceful_shutdown_port,
        self._current_run_id
      ))

  def _deregister_for_graceful_shutdown(self):
    """
    Deregister the tracker with the health server, disabling graceful shutdown.

    Returns:
      (Response) health server response
    """
    if self._graceful_shutdown_port and not self.disabled and self._env_eligible_for_recording_etwitterperiment:
      return requests.post('http://localhost:{}/deregister_id/{}'.format(
        self._graceful_shutdown_port,
        self._current_run_id
      ))

  def _is_env_eligible_for_tracking(self):
    """
    Determine if etwitterperiment tracking should run in the env.
    """
    is_unit_test = (
      os.environ.get('PYTEST_CURRENT_TEST') is not None and
      os.environ.get('TEST_EtwitterP_TRACKER') is None
    )

    is_running_on_ci = (
      getpass.getuser() == 'scoot-service' and
      os.environ.get('TEST_EtwitterP_TRACKER') is None
    )

    return (
      not is_unit_test and
      not is_running_on_ci
    )

  @classmethod
  def run_name_from_environ(cls):
    """
    Create run id from environment if possible.
    """
    job_name = os.environ.get("TWML_JOB_NAME")
    job_launch_time = os.environ.get("TWML_JOB_LAUNCH_TIME")

    if not job_name or not job_launch_time:
      return None

    try:
      # job_launch_time should be in isoformat
      # python2 doesnt support datetime.fromisoformat, so use hardcoded format string.
      job_launch_time_formatted = datetime.strptime(job_launch_time,
                                                    "%Y-%m-%dT%H:%M:%S.%f")
    etwittercept ValueError:
      # Fallback in case aurora config is generating datetime in a different format.
      job_launch_time_formatted = (job_launch_time
                                   .replace("-", "_").replace("T", "_")
                                   .replace(":", "_").replace(".", "_"))

    return '{}_{}'.format(
      job_name, job_launch_time_formatted.strftime('%m_%d_%Y_%I_%M_%p'))

  @classmethod
  def guess_path(cls, save_dir, run_name=None):
    """
    Guess an etwitterperiment tracking path based on save_dir.

    Returns:
      (str) guessed path
    """
    if not run_name:
      run_name = 'Unnamed_{}'.format(datetime.now().strftime('%m_%d_%Y_%I_%M_%p'))

    if save_dir.startswith('hdfs://'):
      path_match = re.search(r'/user/([a-z0-9\-_]+)/([a-z0-9\-_]+)', save_dir)

      if path_match:
        groups = path_match.groups()
        user = groups[0]
        project_name = groups[1]

        return generate_id(user, 'default', project_name, run_name)

    user = getpass.getuser()
    project_name = re.sub(r'^[a-z0-9\-_]', os.path.basename(save_dir), '')
    if not project_name:
      project_name = 'unnamed'

    return generate_id(user, 'default', project_name, run_name)

  @classmethod
  def compute_model_hash(cls, etwitterport_path):
    """
    Computes the hash of an etwitterported model. This is a gfile version of
    twitter.mlmetastore.common.versioning.compute_hash. The two functions should generate
    the same hash when given the same model.

    Args:
      etwitterport_path (str):
        The path to the etwitterported model.

    Returns:
      (str) hash of the etwitterported model
    """
    paths = []
    for path, subdirs, files in tf.io.gfile.walk(etwitterport_path):
      for name in sorted(files):
        paths.append(os.path.join(path, name))

    paths.sort()
    hash_object = hashlib.new('sha1')

    for path in paths:
      with tf.io.gfile.GFile(path, "rb") as file:
        hash_object.update(file.read())

    return hash_object.hetwitterdigest()
