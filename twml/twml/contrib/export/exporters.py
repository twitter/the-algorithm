'\nWrappers around tf.estimator.Exporters to export models and save checkpoints.\n'
_D='_saved_model_exporter needs to exist for this exporter to work. This is potentially broken because of an internal change in Tensorflow'
_C='_saved_model_exporter'
_B=False
_A=None
import os,tensorflow.compat.v1 as tf
from tensorflow.python.estimator import exporter
import twml
class _AllSavedModelsExporter(tf.estimator.Exporter):
	'Internal exporter class to be used for exporting models for different modes.'
	def __init__(A,name,input_receiver_fn_map,backup_checkpoints,assets_extra=_A,as_text=_B):'\n    Args:\n      name: A unique name to be used for the exporter. This is used in the export path.\n      input_receiver_fn_map: A map of tf.estimator.ModeKeys to input_receiver_fns.\n      backup_checkpoints: A flag to specify if backups of checkpoints need to be made.\n      assets_extra: Additional assets to be included in the exported model.\n      as_text: Specifies if the exported model should be in a human readable text format.\n    ';A._name=name;A._input_receiver_fn_map=input_receiver_fn_map;A._backup_checkpoints=backup_checkpoints;A._assets_extra=assets_extra;A._as_text=as_text
	@property
	def name(self):return self._name
	def export(A,estimator,export_path,checkpoint_path,eval_result,is_the_final_export):
		B=checkpoint_path;C=export_path;del is_the_final_export;C=twml.util.sanitize_hdfs_path(C);B=twml.util.sanitize_hdfs_path(B)
		if A._backup_checkpoints:D=os.path.join(C,'checkpoints');tf.io.gfile.makedirs(D);twml.util.backup_checkpoint(B,D,empty_backup=_B)
		E=estimator.experimental_export_all_saved_models(C,A._input_receiver_fn_map,assets_extra=A._assets_extra,as_text=A._as_text,checkpoint_path=B);return E
class BestExporter(tf.estimator.BestExporter):
	'\n  This class inherits from tf.estimator.BestExporter with the following differences:\n    - It also creates a backup of the best checkpoint.\n    - It can export the model for multiple modes.\n\n  A backup / export is performed everytime the evaluated metric is better\n  than previous models.\n  '
	def __init__(A,name='best_exporter',input_receiver_fn_map=_A,backup_checkpoints=True,event_file_pattern='eval/*.tfevents.*',compare_fn=exporter._loss_smaller,assets_extra=_A,as_text=_B,exports_to_keep=5):
		'\n    Args:\n      name: A unique name to be used for the exporter. This is used in the export path.\n      input_receiver_fn_map: A map of tf.estimator.ModeKeys to input_receiver_fns.\n      backup_checkpoints: A flag to specify if backups of checkpoints need to be made.\n\n    Note:\n      Check the following documentation for more information about the remaining args:\n      https://www.tensorflow.org/api_docs/python/tf/estimator/BestExporter\n    ';B=as_text;C=assets_extra;D=input_receiver_fn_map;E=D.get(tf.estimator.ModeKeys.PREDICT);super(BestExporter,A).__init__(name,E,event_file_pattern,compare_fn,C,B,exports_to_keep)
		if not hasattr(A,_C):raise AttributeError(_D)
		A._saved_model_exporter=_AllSavedModelsExporter(name,D,backup_checkpoints,C,B)
class LatestExporter(tf.estimator.LatestExporter):
	'\n  This class inherits from tf.estimator.LatestExporter with the following differences:\n    - It also creates a backup of the latest checkpoint.\n    - It can export the model for multiple modes.\n\n  A backup / export is performed everytime the evaluated metric is better\n  than previous models.\n  '
	def __init__(A,name='latest_exporter',input_receiver_fn_map=_A,backup_checkpoints=True,assets_extra=_A,as_text=_B,exports_to_keep=5):
		'\n    Args:\n      name: A unique name to be used for the exporter. This is used in the export path.\n      input_receiver_fn_map: A map of tf.estimator.ModeKeys to input_receiver_fns.\n      backup_checkpoints: A flag to specify if backups of checkpoints need to be made.\n\n    Note:\n      Check the following documentation for more information about the remaining args:\n      https://www.tensorflow.org/api_docs/python/tf/estimator/LatestExporter\n    ';B=as_text;C=assets_extra;D=input_receiver_fn_map;E=D.get(tf.estimator.ModeKeys.PREDICT);super(LatestExporter,A).__init__(name,E,C,B,exports_to_keep)
		if not hasattr(A,_C):raise AttributeError(_D)
		A._saved_model_exporter=_AllSavedModelsExporter(name,D,backup_checkpoints,C,B)