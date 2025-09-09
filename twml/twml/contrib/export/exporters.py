"""
Wrappers around tf.estimator.Exporters to export models and save checkpoints.
"""
import os
from typing import List

import tensorflow.compat.v1 as tf
from tensorflow.python.estimator import exporter

import twml


class _AllSavedModelsExporter(tf.estimator.Exporter):
    """Internal exporter class to be used for exporting models for different modes."""

    def __init__(
        self,
        name: str,
        input_receiver_fn_map: dict,
        backup_checkpoints: bool,
        assets_extra: List[str] = None,
        as_text: bool = False,
    ):
        """
        Args:
            name (str):
                A unique name to be used for the exporter. This is used in the export path.
            input_receiver_fn_map (dict):
                A map of tf.estimator.ModeKeys to input_receiver_fns.
            backup_checkpoints (bool):
                A flag to specify if backups of checkpoints need to be made.
            assets_extra (list):
                Additional assets to be included in the exported model.
            as_text (bool):
                Specifies if the exported model should be in a human readable text format.
        """
        self._name = name
        self._input_receiver_fn_map = input_receiver_fn_map
        self._backup_checkpoints = backup_checkpoints
        self._assets_extra = assets_extra
        self._as_text = as_text

    @property
    def name(self) -> str:
        return self._name

    def export(
        self,
        estimator: tf.estimator.Estimator,
        export_path: str,
        checkpoint_path: str,
        eval_result: dict,
        is_the_final_export: bool = True,
    ):  # pylint: disable=unused-argument
        del is_the_final_export

        export_path = twml.util.sanitize_hdfs_path(export_path)
        checkpoint_path = twml.util.sanitize_hdfs_path(checkpoint_path)

        if self._backup_checkpoints:
            backup_path = os.path.join(export_path, "checkpoints")
            # Ensure backup_path is created. makedirs passes if dir already exists.
            tf.io.gfile.makedirs(backup_path)
            twml.util.backup_checkpoint(
                checkpoint_path, backup_path, empty_backup=False
            )

        export_result = estimator.experimental_export_all_saved_models(
            export_path,
            self._input_receiver_fn_map,
            assets_extra=self._assets_extra,
            as_text=self._as_text,
            checkpoint_path=checkpoint_path,
        )

        return export_result


class BestExporter(tf.estimator.BestExporter):
    """
    This class inherits from tf.estimator.BestExporter with the following differences:
        - It also creates a backup of the best checkpoint.
        - It can export the model for multiple modes.

    A backup / export is performed every time the evaluated metric is better
    than previous models.
    """

    def __init__(
        self,
        name: str = "best_exporter",
        input_receiver_fn_map: dict = None,
        backup_checkpoints: bool = True,
        event_file_pattern: str = "eval/*.tfevents.*",
        compare_fn: callable = exporter._loss_smaller,
        assets_extra: List[str] = None,
        as_text: bool = False,
        exports_to_keep: int = 5,
    ):
        """
        Args:
            name (str):
                A unique name to be used for the exporter. This is used in the export path.
            input_receiver_fn_map (dict):
                A map of tf.estimator.ModeKeys to input_receiver_fns.
            backup_checkpoints (bool):
                A flag to specify if backups of checkpoints need to be made.
            event_file_pattern (str):
                A glob pattern for the event files in the evaluation directory.
            compare_fn (callable):
                A function that takes two evaluation results and returns True if the first
                one is better than the second one.
            assets_extra (list):
                Additional assets to be included in the exported model.
            as_text (bool):
                Specifies if the exported model should be in a human readable text format.
            exports_to_keep (int):
                The maximum number of exports to keep. Older exports are deleted.
        Note:
            Check the following documentation for more information about the remaining args:
            https://www.tensorflow.org/api_docs/python/tf/estimator/BestExporter
        """
        serving_input_receiver_fn = input_receiver_fn_map.get(
            tf.estimator.ModeKeys.PREDICT
        )

        super(BestExporter, self).__init__(
            name,
            serving_input_receiver_fn,
            event_file_pattern,
            compare_fn,
            assets_extra,
            as_text,
            exports_to_keep,
        )

        if not hasattr(self, "_saved_model_exporter"):
            raise AttributeError(
                "_saved_model_exporter needs to exist for this exporter to work."
                " This is potentially broken because of an internal change in Tensorflow"
            )

        # Override the saved_model_exporter with SaveAllmodelsexporter
        self._saved_model_exporter = _AllSavedModelsExporter(
            name, input_receiver_fn_map, backup_checkpoints, assets_extra, as_text
        )


class LatestExporter(tf.estimator.LatestExporter):
    """
    This class inherits from tf.estimator.LatestExporter with the following differences:
        - It also creates a backup of the latest checkpoint.
        - It can export the model for multiple modes.

    A backup / export is performed every time the evaluated metric is better
    than previous models.
    """

    def __init__(
        self,
        name: str = "latest_exporter",
        input_receiver_fn_map: dict = None,
        backup_checkpoints: bool = True,
        assets_extra: List[str] = None,
        as_text: bool = False,
        exports_to_keep: int = 5,
    ):
        """
        Args:
            name (str):
                A unique name to be used for the exporter. This is used in the export path.
            input_receiver_fn_map (dict):
                A map of tf.estimator.ModeKeys to input_receiver_fns.
            backup_checkpoints (bool):
                A flag to specify if backups of checkpoints need to be made.
            assets_extra (list[str]):
                Additional assets to be included in the exported model.
            as_text (bool):
                Specifies if the exported model should be in a human readable text format.
            exports_to_keep (int):
                The number of exports to keep.
        Note:
            Check the following documentation for more information about the remaining args:
            https://www.tensorflow.org/api_docs/python/tf/estimator/LatestExporter
        """
        serving_input_receiver_fn = input_receiver_fn_map.get(
            tf.estimator.ModeKeys.PREDICT
        )

        super(LatestExporter, self).__init__(
            name, serving_input_receiver_fn, assets_extra, as_text, exports_to_keep
        )

        if not hasattr(self, "_saved_model_exporter"):
            raise AttributeError(
                "_saved_model_exporter needs to exist for this exporter to work."
                " This is potentially broken because of an internal change in Tensorflow"
            )

        # Override the saved_model_exporter with SaveAllmodelsexporter
        self._saved_model_exporter = _AllSavedModelsExporter(
            name, input_receiver_fn_map, backup_checkpoints, assets_extra, as_text
        )
