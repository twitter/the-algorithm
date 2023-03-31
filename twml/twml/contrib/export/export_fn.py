'\nFunctions for exporting models for different modes.\n'
_E='predict'
_D='eval'
_C='train'
_B=False
_A=None
from collections import OrderedDict
import os,tensorflow.compat.v1 as tf
from tensorflow.python.estimator.export import export
import twml,yaml
def get_sparse_batch_supervised_input_receiver_fn(feature_config,keep_fields=_A):
	"Gets supervised_input_receiver_fn that decodes a BatchPredictionRequest as sparse tensors\n  with labels and weights as defined in feature_config.\n  This input_receiver_fn is required for exporting models with 'train' mode to be trained with\n  Java API\n\n  Args:\n    feature_config (FeatureConfig): deepbird v2 feature config object\n    keep_fields (list): list of fields to keep\n\n  Returns:\n    supervised_input_receiver_fn: input_receiver_fn used for train mode\n  ";D=keep_fields
	def A():E='request';F=tf.placeholder(dtype=tf.uint8,name=E);G={E:F};A=twml.contrib.readers.HashedBatchPredictionRequest(F,feature_config);B=A.get_sparse_features()if D is _A else A.get_features(D);B['weights']=A.weights;C=A.labels;B,C=A.apply_filter(B,C);return export.SupervisedInputReceiver(B,C,G)
	return A
def update_build_graph_fn_for_train(build_graph_fn):
	"Updates a build_graph_fn by inserting in graph output a serialized BatchPredictionResponse\n  similar to the export_output_fns for serving.\n  The key difference here is that\n  1. We insert serialized BatchPredictionResponse in graph output with key 'prediction' instead of\n     creating an export_output object. This is because of the way estimators export model in 'train'\n     mode doesn't take custom export_output\n  2. We only do it when `mode == 'train'` to avoid altering the graph when exporting\n     for 'infer' mode\n\n  Args:\n    build_graph_fn (Callable): deepbird v2 build graph function\n\n  Returns:\n    new_build_graph_fn: An updated build_graph_fn that inserts serialized BatchPredictResponse\n                        to graph output when in 'train' mode\n  "
	def A(features,label,mode,params,config=_A):
		A=build_graph_fn(features,label,mode,params,config)
		if mode==tf.estimator.ModeKeys.TRAIN:A.update(twml.export_output_fns.batch_prediction_continuous_output_fn(A)[tf.saved_model.signature_constants.DEFAULT_SERVING_SIGNATURE_DEF_KEY].outputs)
		return A
	return A
def export_model_for_train_and_infer(trainer,feature_config,keep_fields,export_dir,as_text=_B):"Function for exporting model with both 'train' and 'infer' mode.\n\n  This means the exported saved_model.pb will contain two meta graphs, one with tag 'train'\n  and the other with tag 'serve', and it can be loaded in Java API with either tag depending on\n  the use case\n\n  Args:\n    trainer (DataRecordTrainer): deepbird v2 DataRecordTrainer\n    feature_config (FeatureConfig): deepbird v2 feature config\n    keep_fields (list of string): list of field keys, e.g.\n                                  ('ids', 'keys', 'values', 'batch_size', 'total_size', 'codes')\n    export_dir (str): a directory (local or hdfs) to export model to\n    as_text (bool): if True, write 'saved_model.pb' as binary file, else write\n                    'saved_model.pbtxt' as human readable text file. Default False\n  ";B=export_dir;C=keep_fields;D=feature_config;A=trainer;E=get_sparse_batch_supervised_input_receiver_fn(D,C);F=twml.parsers.get_sparse_serving_input_receiver_fn(D,C);A._export_output_fn=twml.export_output_fns.batch_prediction_continuous_output_fn;A._build_graph_fn=update_build_graph_fn_for_train(A._build_graph_fn);A._estimator._export_all_saved_models(export_dir_base=B,input_receiver_fn_map={tf.estimator.ModeKeys.TRAIN:E,tf.estimator.ModeKeys.PREDICT:F},as_text=as_text);A.export_model_effects(B)
def export_all_models_with_receivers(estimator,export_dir,train_input_receiver_fn,eval_input_receiver_fn,predict_input_receiver_fn,export_output_fn,export_modes=(_C,_D,_E),register_model_fn=_A,feature_spec=_A,checkpoint_path=_A,log_features=True):
	'\n  Function for exporting a model with train, eval, and infer modes.\n\n  Args:\n    estimator:\n      Should be of type tf.estimator.Estimator.\n      You can get this from trainer using trainer.estimator\n    export_dir:\n      Directory to export the model.\n    train_input_receiver_fn:\n      Input receiver for train interface.\n    eval_input_receiver_fn:\n      Input receiver for eval interface.\n    predict_input_receiver_fn:\n      Input receiver for predict interface.\n    export_output_fn:\n      export_output_fn to be used for serving.\n    export_modes:\n      A list to Specify what modes to export. Can be "train", "eval", "predict".\n      Defaults to ["train", "eval", "predict"]\n    register_model_fn:\n      An optional function which is called with export_dir after models are exported.\n      Defaults to None.\n  Returns:\n     The timestamped directory the models are exported to.\n  ';D=register_model_fn;C=export_modes;A=export_dir
	if A is _A:raise ValueError('export_dir can not be None')
	A=twml.util.sanitize_hdfs_path(A);B={}
	if _C in C:B[tf.estimator.ModeKeys.TRAIN]=train_input_receiver_fn
	if _D in C:B[tf.estimator.ModeKeys.EVAL]=eval_input_receiver_fn
	if _E in C:B[tf.estimator.ModeKeys.PREDICT]=predict_input_receiver_fn
	A=estimator._export_all_saved_models(export_dir_base=A,input_receiver_fn_map=B,checkpoint_path=checkpoint_path)
	if D is not _A:D(A,feature_spec,log_features)
	return A
def export_all_models(trainer,export_dir,parse_fn,serving_input_receiver_fn,export_output_fn=_A,export_modes=(_C,_D,_E),feature_spec=_A,checkpoint=_A,log_features=True):
	'\n  Function for exporting a model with train, eval, and infer modes.\n\n  Args:\n    trainer:\n      An object of type twml.trainers.Trainer.\n    export_dir:\n      Directory to export the model.\n    parse_fn:\n      The parse function used parse the inputs for train and eval.\n    serving_input_receiver_fn:\n      The input receiver function used during serving.\n    export_output_fn:\n      export_output_fn to be used for serving.\n    export_modes:\n      A list to Specify what modes to export. Can be "train", "eval", "predict".\n      Defaults to ["train", "eval", "predict"]\n    feature_spec:\n      A dictionary obtained from FeatureConfig.get_feature_spec() to serialize\n      as feature_spec.yaml in export_dir.\n      Defaults to None\n  Returns:\n     The timestamped directory the models are exported to.\n  ';E=export_output_fn;C=checkpoint;D=feature_spec;B=export_dir;A=trainer
	if A.params.get('distributed',_B)and not A.estimator.config.is_chief:tf.logging.info('Trainer.export_model ignored due to instance not being chief.');return
	if D is _A:
		if getattr(A,'_feature_config')is _A:raise ValueError('feature_spec is set to None.Please pass feature_spec=feature_config.get_feature_spec() to the export_all_model function')
		else:D=A._feature_config.get_feature_spec()
	B=twml.util.sanitize_hdfs_path(B);G=A._export_output_fn;A._export_output_fn=E;F=twml.parsers.convert_to_supervised_input_receiver_fn(parse_fn)
	if not C:C=A.best_or_latest_checkpoint
	B=export_all_models_with_receivers(estimator=A.estimator,export_dir=B,train_input_receiver_fn=F,eval_input_receiver_fn=F,predict_input_receiver_fn=serving_input_receiver_fn,export_output_fn=E,export_modes=export_modes,register_model_fn=A.export_model_effects,feature_spec=D,checkpoint_path=C,log_features=log_features);A._export_output_fn=G;return B
def export_feature_spec(dir_path,feature_spec_dict):
	'\n  Exports a FeatureConfig.get_feature_spec() dict to <dir_path>/feature_spec.yaml.\n  ';B=dir_path
	def D(dumper,data):return dumper.represent_mapping('tag:yaml.org,2002:map',data.items())
	try:yaml.add_representer(str,yaml.representer.SafeRepresenter.represent_str);yaml.add_representer(unicode,yaml.representer.SafeRepresenter.represent_unicode)
	except NameError:pass
	yaml.add_representer(OrderedDict,D);C='feature_spec.yaml';E=C.encode('utf-8')if type(B)!=str else C;A=os.path.join(B,E)
	with tf.io.gfile.GFile(A,mode='w')as F:yaml.dump(feature_spec_dict,F,default_flow_style=_B,allow_unicode=True)
	tf.logging.info('Exported feature spec to %s'%A);return A
get_supervised_input_receiver_fn=twml.parsers.convert_to_supervised_input_receiver_fn