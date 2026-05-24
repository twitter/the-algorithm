"""
Functions for exporting models for different modes.
"""
from collections import OrderedDict
import os

import tensorflow.compat.v1 as tf
from tensorflow.python.estimator.export import export
import twml
import yaml


def get_sparse_batch_supervised_input_receiver_fn(feature_config, keep_fields=None):
  """Gets supervised_input_receiver_fn that decodes a BatchPredictionRequest as sparse tensors
  with labels and weights as defined in feature_config.
  This input_receiver_fn is required for exporting models with 'train' mode to be trained with
  Java API

  Args:
    feature_config (FeatureConfig): deepbird v2 feature config object
    keep_fields (list): list of fields to keep

  Returns:
    supervised_input_receiver_fn: input_receiver_fn used for train mode
  """
  def supervised_input_receiver_fn():
    serialized_request = tf.placeholder(dtype=tf.uint8, name='request')
    receiver_tensors = {'request': serialized_request}

    bpr = twml.contrib.readers.HashedBatchPredictionRequest(serialized_request, feature_config)
    features = bpr.get_sparse_features() if keep_fields is None else bpr.get_features(keep_fields)
    features['weights'] = bpr.weights
    labels = bpr.labels
    features, labels = bpr.apply_filter(features, labels)

    return export.SupervisedInputReceiver(features, labels, receiver_tensors)

  return supervised_input_receiver_fn


def update_build_graph_fn_for_train(build_graph_fn):
  """Updates a build_graph_fn by inserting in graph output a serialized BatchPredictionResponse
  similar to the export_output_fns for serving.
  The key difference here is that
  1. We insert serialized BatchPredictionResponse in graph output with key 'prediction' instead of
     creating an export_output object. This is because of the way estimators export model in 'train'
     mode doesn't take custom export_output
  2. We only do it when `mode == 'train'` to avoid altering the graph when exporting
     for 'infer' mode

  Args:
    build_graph_fn (Callable): deepbird v2 build graph function

  Returns:
    new_build_graph_fn: An updated build_graph_fn that inserts serialized BatchPredictResponse
                        to graph output when in 'train' mode
  """
  def new_build_graph_fn(features, label, mode, params, config=None):
    output = build_graph_fn(features, label, mode, params, config)
    if mode == tf.estimator.ModeKeys.TRAIN:
      output.update(
        twml.export_output_fns.batch_prediction_continuous_output_fn(output)[
          tf.saved_model.signature_constants.DEFAULT_SERVING_SIGNATURE_DEF_KEY].outputs
      )
    return output
  return new_build_graph_fn


def export_model_for_train_and_infer(
    trainer, feature_config, keep_fields, export_dir, as_text=False):
  """Function for exporting model with both 'train' and 'infer' mode.

  This means the exported saved_model.pb will contain two meta graphs, one with tag 'train'
  and the other with tag 'serve', and it can be loaded in Java API with either tag depending on
  the use case

  Args:
    trainer (DataRecordTrainer): deepbird v2 DataRecordTrainer
    feature_config (FeatureConfig): deepbird v2 feature config
    keep_fields (list of string): list of field keys, e.g.
                                  ('ids', 'keys', 'values', 'batch_size', 'total_size', 'codes')
    export_dir (str): a directory (local or hdfs) to export model to
    as_text (bool): if True, write 'saved_model.pb' as binary file, else write
                    'saved_model.pbtxt' as human readable text file. Default False
  """
  train_input_receiver_fn = get_sparse_batch_supervised_input_receiver_fn(
    feature_config, keep_fields)
  predict_input_receiver_fn = twml.parsers.get_sparse_serving_input_receiver_fn(
    feature_config, keep_fields)
  trainer._export_output_fn = twml.export_output_fns.batch_prediction_continuous_output_fn
  trainer._build_graph_fn = update_build_graph_fn_for_train(trainer._build_graph_fn)
  trainer._estimator._export_all_saved_models(
    export_dir_base=export_dir,
    input_receiver_fn_map={
      tf.estimator.ModeKeys.TRAIN: train_input_receiver_fn,
      tf.estimator.ModeKeys.PREDICT: predict_input_receiver_fn
    },
    as_text=as_text,
  )

  trainer.export_model_effects(export_dir)


def export_all_models_with_receivers(estimator, export_dir,
                                     train_input_receiver_fn,
                                     eval_input_receiver_fn,
                                     predict_input_receiver_fn,
                                     export_output_fn,
                                     export_modes=('train', 'eval', 'predict'),
                                     register_model_fn=None,
                                     feature_spec=None,
                                     checkpoint_path=None,
                                     log_features=True):
  """
  Function for exporting a model with train, eval, and infer modes.

  Args:
    estimator:
      Should be of type tf.estimator.Estimator.
      You can get this from trainer using trainer.estimator
    export_dir:
      Directory to export the model.
    train_input_receiver_fn:
      Input receiver for train interface.
    eval_input_receiver_fn:
      Input receiver for eval interface.
    predict_input_receiver_fn:
      Input receiver for predict interface.
    export_output_fn:
      export_output_fn to be used for serving.
    export_modes:
      A list to Specify what modes to export. Can be "train", "eval", "predict".
      Defaults to ["train", "eval", "predict"]
    register_model_fn:
      An optional function which is called with export_dir after models are exported.
      Defaults to None.
  Returns:
     The timestamped directory the models are exported to.
  """
  # TODO: Fix for hogwild / distributed training.

  if export_dir is None:
    raise ValueError("export_dir can not be None")
  export_dir = twml.util.sanitize_hdfs_path(export_dir)
  input_receiver_fn_map = {}

  if "train" in export_modes:
    input_receiver_fn_map[tf.estimator.ModeKeys.TRAIN] = train_input_receiver_fn

  if "eval" in export_modes:
    input_receiver_fn_map[tf.estimator.ModeKeys.EVAL] = eval_input_receiver_fn

  if "predict" in export_modes:
    input_receiver_fn_map[tf.estimator.ModeKeys.PREDICT] = predict_input_receiver_fn

  export_dir = estimator._export_all_saved_models(
    export_dir_base=export_dir,
    input_receiver_fn_map=input_receiver_fn_map,
    checkpoint_path=checkpoint_path,
  )

  if register_model_fn is not None:
    register_model_fn(export_dir, feature_spec, log_features)

  return export_dir


def export_all_models(trainer,
                      export_dir,
                      parse_fn,
                      serving_input_receiver_fn,
                      export_output_fn=None,
                      export_modes=('train', 'eval', 'predict'),
                      feature_spec=None,
                      checkpoint=None,
                      log_features=True):
  """
  Function for exporting a model with train, eval, and infer modes.

  Args:
    trainer:
      An object of type twml.trainers.Trainer.
    export_dir:
      Directory to export the model.
    parse_fn:
      The parse function used parse the inputs for train and eval.
    serving_input_receiver_fn:
      The input receiver function used during serving.
    export_output_fn:
      export_output_fn to be used for serving.
    export_modes:
      A list to Specify what modes to export. Can be "train", "eval", "predict".
      Defaults to ["train", "eval", "predict"]
    feature_spec:
      A dictionary obtained from FeatureConfig.get_feature_spec() to serialize
      as feature_spec.yaml in export_dir.
      Defaults to None
  Returns:
     The timestamped directory the models are exported to.
  """
  # Only export from chief in hogwild or distributed modes.
  if trainer.params.get('distributed', False) and not trainer.estimator.config.is_chief:
    tf.logging.info("Trainer.export_model ignored due to instance not being chief.")
    return

  if feature_spec is None:
    if getattr(trainer, '_feature_config') is None:
      raise ValueError("feature_spec is set to None."
                       "Please pass feature_spec=feature_config.get_feature_spec() to the export_all_model function")
    else:
      feature_spec = trainer._feature_config.get_feature_spec()

  export_dir = twml.util.sanitize_hdfs_path(export_dir)
  old_export_output_fn = trainer._export_output_fn
  trainer._export_output_fn = export_output_fn
  supervised_input_receiver_fn = twml.parsers.convert_to_supervised_input_receiver_fn(parse_fn)
  if not checkpoint:
    checkpoint = trainer.best_or_latest_checkpoint

  export_dir = export_all_models_with_receivers(estimator=trainer.estimator,
                                                export_dir=export_dir,
                                                train_input_receiver_fn=supervised_input_receiver_fn,
                                                eval_input_receiver_fn=supervised_input_receiver_fn,
                                                predict_input_receiver_fn=serving_input_receiver_fn,
                                                export_output_fn=export_output_fn,
                                                export_modes=export_modes,
                                                register_model_fn=trainer.export_model_effects,
                                                feature_spec=feature_spec,
                                                checkpoint_path=checkpoint,
                                                log_features=log_features)
  trainer._export_output_fn = old_export_output_fn
  return export_dir


def export_feature_spec(dir_path, feature_spec_dict):
  """
  Exports a FeatureConfig.get_feature_spec() dict to <dir_path>/feature_spec.yaml.
  """
  def ordered_dict_representer(dumper, data):
    return dumper.represent_mapping('tag:yaml.org,2002:map', data.items())

  try:
    # needed for Python 2
    yaml.add_representer(str, yaml.representer.SafeRepresenter.represent_str)
    yaml.add_representer(unicode, yaml.representer.SafeRepresenter.represent_unicode)
  except NameError:
    # 'unicode' type doesn't exist on Python 3
    # PyYAML handles unicode correctly in Python 3
    pass

  yaml.add_representer(OrderedDict, ordered_dict_representer)

  fbase = "feature_spec.yaml"
  fname = fbase.encode('utf-8') if type(dir_path) != str else fbase
  file_path = os.path.join(dir_path, fname)
  with tf.io.gfile.GFile(file_path, mode='w') as f:
    yaml.dump(feature_spec_dict, f, default_flow_style=False, allow_unicode=True)
  tf.logging.info("Exported feature spec to %s" % file_path)

  return file_path


# Keep the alias for compatibility.
get_supervised_input_receiver_fn = twml.parsers.convert_to_supervised_input_receiver_fn
