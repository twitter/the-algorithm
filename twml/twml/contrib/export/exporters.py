"""
Wrappers around tf.estimator.Exporters to export models and save checkpoints.
"""
import os

import tensorflow.compat.v1 as tf
from tensorflow.python.estimator import exporter
import twml


class _AllSavedModelsExporter(tf.estimator.Exporter):
  """Internal exporter class to be used for exporting models for different modes."""

  def __init__(self,
               name,
               input_receiver_fn_map,
               backup_checkpoints,
               assets_extra=None,
               as_text=False):
    """
    Args:
      name: A unique name to be used for the exporter. This is used in the export path.
      input_receiver_fn_map: A map of tf.estimator.ModeKeys to input_receiver_fns.
      backup_checkpoints: A flag to specify if backups of checkpoints need to be made.
      assets_extra: Additional assets to be included in the exported model.
      as_text: Specifies if the exported model should be in a human readable text format.
    """
    self._name = name
    self._input_receiver_fn_map = input_receiver_fn_map
    self._backup_checkpoints = backup_checkpoints
    self._assets_extra = assets_extra
    self._as_text = as_text

  @property
  def name(self):
    return self._name

  def export(self, estimator, export_path, checkpoint_path, eval_result,
             is_the_final_export):
    del is_the_final_export

    export_path = twml.util.sanitize_hdfs_path(export_path)
    checkpoint_path = twml.util.sanitize_hdfs_path(checkpoint_path)

    if self._backup_checkpoints:
      backup_path = os.path.join(export_path, "checkpoints")
      # Ensure backup_path is created. makedirs passes if dir already exists.
      tf.io.gfile.makedirs(backup_path)
      twml.util.backup_checkpoint(checkpoint_path, backup_path, empty_backup=False)

    export_result = estimator.experimental_export_all_saved_models(
      export_path,
      self._input_receiver_fn_map,
      assets_extra=self._assets_extra,
      as_text=self._as_text,
      checkpoint_path=checkpoint_path)

    return export_result


class BestExporter(tf.estimator.BestExporter):
  """
  This class inherits from tf.estimator.BestExporter with the following differences:
    - It also creates a backup of the best checkpoint.
    - It can export the model for multiple modes.

  A backup / export is performed everytime the evaluated metric is better
  than previous models.
  """

  def __init__(self,
               name='best_exporter',
               input_receiver_fn_map=None,
               backup_checkpoints=True,
               event_file_pattern='eval/*.tfevents.*',
               compare_fn=exporter._loss_smaller,
               assets_extra=None,
               as_text=False,
               exports_to_keep=5):
    """
    Args:
      name: A unique name to be used for the exporter. This is used in the export path.
      input_receiver_fn_map: A map of tf.estimator.ModeKeys to input_receiver_fns.
      backup_checkpoints: A flag to specify if backups of checkpoints need to be made.

    Note:
      Check the following documentation for more information about the remaining args:
      https://www.tensorflow.org/api_docs/python/tf/estimator/BestExporter
    """
    serving_input_receiver_fn = input_receiver_fn_map.get(tf.estimator.ModeKeys.PREDICT)

    super(BestExporter, self).__init__(
      name, serving_input_receiver_fn, event_file_pattern, compare_fn,
      assets_extra, as_text, exports_to_keep)

    if not hasattr(self, "_saved_model_exporter"):
      raise AttributeError(
        "_saved_model_exporter needs to exist for this exporter to work."
        " This is potentially broken because of an internal change in Tensorflow")

    # Override the saved_model_exporter with SaveAllmodelsexporter
    self._saved_model_exporter = _AllSavedModelsExporter(
      name, input_receiver_fn_map, backup_checkpoints, assets_extra, as_text)


class LatestExporter(tf.estimator.LatestExporter):
  """
  This class inherits from tf.estimator.LatestExporter with the following differences:
    - It also creates a backup of the latest checkpoint.
    - It can export the model for multiple modes.

  A backup / export is performed everytime the evaluated metric is better
  than previous models.
  """

  def __init__(self,
               name='latest_exporter',
               input_receiver_fn_map=None,
               backup_checkpoints=True,
               assets_extra=None,
               as_text=False,
               exports_to_keep=5):
    """
    Args:
      name: A unique name to be used for the exporter. This is used in the export path.
      input_receiver_fn_map: A map of tf.estimator.ModeKeys to input_receiver_fns.
      backup_checkpoints: A flag to specify if backups of checkpoints need to be made.

    Note:
      Check the following documentation for more information about the remaining args:
      https://www.tensorflow.org/api_docs/python/tf/estimator/LatestExporter
    """
    serving_input_receiver_fn = input_receiver_fn_map.get(tf.estimator.ModeKeys.PREDICT)

    super(LatestExporter, self).__init__(
      name, serving_input_receiver_fn, assets_extra, as_text, exports_to_keep)

    if not hasattr(self, "_saved_model_exporter"):
      raise AttributeError(
        "_saved_model_exporter needs to exist for this exporter to work."
        " This is potentially broken because of an internal change in Tensorflow")

    # Override the saved_model_exporter with SaveAllmodelsexporter
    self._saved_model_exporter = _AllSavedModelsExporter(
      name, input_receiver_fn_map, backup_checkpoints, assets_extra, as_text)
