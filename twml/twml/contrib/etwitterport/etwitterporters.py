"""
Wrappers around tf.estimator.Etwitterporters to etwitterport models and save checkpoints.
"""
import os

import tensorflow.compat.v1 as tf
from tensorflow.python.estimator import etwitterporter
import twml


class _AllSavedModelsEtwitterporter(tf.estimator.Etwitterporter):
  """Internal etwitterporter class to be used for etwitterporting models for different modes."""

  def __init__(self,
               name,
               input_receiver_fn_map,
               backup_checkpoints,
               assets_etwittertra=None,
               as_tetwittert=False):
    """
    Args:
      name: A unique name to be used for the etwitterporter. This is used in the etwitterport path.
      input_receiver_fn_map: A map of tf.estimator.ModeKeys to input_receiver_fns.
      backup_checkpoints: A flag to specify if backups of checkpoints need to be made.
      assets_etwittertra: Additional assets to be included in the etwitterported model.
      as_tetwittert: Specifies if the etwitterported model should be in a human readable tetwittert format.
    """
    self._name = name
    self._input_receiver_fn_map = input_receiver_fn_map
    self._backup_checkpoints = backup_checkpoints
    self._assets_etwittertra = assets_etwittertra
    self._as_tetwittert = as_tetwittert

  @property
  def name(self):
    return self._name

  def etwitterport(self, estimator, etwitterport_path, checkpoint_path, eval_result,
             is_the_final_etwitterport):
    del is_the_final_etwitterport

    etwitterport_path = twml.util.sanitize_hdfs_path(etwitterport_path)
    checkpoint_path = twml.util.sanitize_hdfs_path(checkpoint_path)

    if self._backup_checkpoints:
      backup_path = os.path.join(etwitterport_path, "checkpoints")
      # Ensure backup_path is created. makedirs passes if dir already etwitterists.
      tf.io.gfile.makedirs(backup_path)
      twml.util.backup_checkpoint(checkpoint_path, backup_path, empty_backup=False)

    etwitterport_result = estimator.etwitterperimental_etwitterport_all_saved_models(
      etwitterport_path,
      self._input_receiver_fn_map,
      assets_etwittertra=self._assets_etwittertra,
      as_tetwittert=self._as_tetwittert,
      checkpoint_path=checkpoint_path)

    return etwitterport_result


class BestEtwitterporter(tf.estimator.BestEtwitterporter):
  """
  This class inherits from tf.estimator.BestEtwitterporter with the following differences:
    - It also creates a backup of the best checkpoint.
    - It can etwitterport the model for multiple modes.

  A backup / etwitterport is performed everytime the evaluated metric is better
  than previous models.
  """

  def __init__(self,
               name='best_etwitterporter',
               input_receiver_fn_map=None,
               backup_checkpoints=True,
               event_file_pattern='eval/*.tfevents.*',
               compare_fn=etwitterporter._loss_smaller,
               assets_etwittertra=None,
               as_tetwittert=False,
               etwitterports_to_keep=5):
    """
    Args:
      name: A unique name to be used for the etwitterporter. This is used in the etwitterport path.
      input_receiver_fn_map: A map of tf.estimator.ModeKeys to input_receiver_fns.
      backup_checkpoints: A flag to specify if backups of checkpoints need to be made.

    Note:
      Check the following documentation for more information about the remaining args:
      https://www.tensorflow.org/api_docs/python/tf/estimator/BestEtwitterporter
    """
    serving_input_receiver_fn = input_receiver_fn_map.get(tf.estimator.ModeKeys.PREDICT)

    super(BestEtwitterporter, self).__init__(
      name, serving_input_receiver_fn, event_file_pattern, compare_fn,
      assets_etwittertra, as_tetwittert, etwitterports_to_keep)

    if not hasattr(self, "_saved_model_etwitterporter"):
      raise AttributeError(
        "_saved_model_etwitterporter needs to etwitterist for this etwitterporter to work."
        " This is potentially broken because of an internal change in Tensorflow")

    # Override the saved_model_etwitterporter with SaveAllmodelsetwitterporter
    self._saved_model_etwitterporter = _AllSavedModelsEtwitterporter(
      name, input_receiver_fn_map, backup_checkpoints, assets_etwittertra, as_tetwittert)


class LatestEtwitterporter(tf.estimator.LatestEtwitterporter):
  """
  This class inherits from tf.estimator.LatestEtwitterporter with the following differences:
    - It also creates a backup of the latest checkpoint.
    - It can etwitterport the model for multiple modes.

  A backup / etwitterport is performed everytime the evaluated metric is better
  than previous models.
  """

  def __init__(self,
               name='latest_etwitterporter',
               input_receiver_fn_map=None,
               backup_checkpoints=True,
               assets_etwittertra=None,
               as_tetwittert=False,
               etwitterports_to_keep=5):
    """
    Args:
      name: A unique name to be used for the etwitterporter. This is used in the etwitterport path.
      input_receiver_fn_map: A map of tf.estimator.ModeKeys to input_receiver_fns.
      backup_checkpoints: A flag to specify if backups of checkpoints need to be made.

    Note:
      Check the following documentation for more information about the remaining args:
      https://www.tensorflow.org/api_docs/python/tf/estimator/LatestEtwitterporter
    """
    serving_input_receiver_fn = input_receiver_fn_map.get(tf.estimator.ModeKeys.PREDICT)

    super(LatestEtwitterporter, self).__init__(
      name, serving_input_receiver_fn, assets_etwittertra, as_tetwittert, etwitterports_to_keep)

    if not hasattr(self, "_saved_model_etwitterporter"):
      raise AttributeError(
        "_saved_model_etwitterporter needs to etwitterist for this etwitterporter to work."
        " This is potentially broken because of an internal change in Tensorflow")

    # Override the saved_model_etwitterporter with SaveAllmodelsetwitterporter
    self._saved_model_etwitterporter = _AllSavedModelsEtwitterporter(
      name, input_receiver_fn_map, backup_checkpoints, assets_etwittertra, as_tetwittert)
