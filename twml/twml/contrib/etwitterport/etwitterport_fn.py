"""
Functions for etwitterporting models for different modes.
"""
from collections import OrderedDict
import os

import tensorflow.compat.v1 as tf
from tensorflow.python.estimator.etwitterport import etwitterport
import twml
import yaml


def get_sparse_batch_supervised_input_receiver_fn(feature_config, keep_fields=None):
  """Gets supervised_input_receiver_fn that decodes a BatchPredictionRequest as sparse tensors
  with labels and weights as defined in feature_config.
  This input_receiver_fn is required for etwitterporting models with 'train' mode to be trained with
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

    return etwitterport.SupervisedInputReceiver(features, labels, receiver_tensors)

  return supervised_input_receiver_fn


def update_build_graph_fn_for_train(build_graph_fn):
  """Updates a build_graph_fn by inserting in graph output a serialized BatchPredictionResponse
  similar to the etwitterport_output_fns for serving.
  The key difference here is that
  1. We insert serialized BatchPredictionResponse in graph output with key 'prediction' instead of
     creating an etwitterport_output object. This is because of the way estimators etwitterport model in 'train'
     mode doesn't take custom etwitterport_output
  2. We only do it when `mode == 'train'` to avoid altering the graph when etwitterporting
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
        twml.etwitterport_output_fns.batch_prediction_continuous_output_fn(output)[
          tf.saved_model.signature_constants.DEFAULT_SERVING_SIGNATURE_DEF_KEY].outputs
      )
    return output
  return new_build_graph_fn


def etwitterport_model_for_train_and_infer(
    trainer, feature_config, keep_fields, etwitterport_dir, as_tetwittert=False):
  """Function for etwitterporting model with both 'train' and 'infer' mode.

  This means the etwitterported saved_model.pb will contain two meta graphs, one with tag 'train'
  and the other with tag 'serve', and it can be loaded in Java API with either tag depending on
  the use case

  Args:
    trainer (DataRecordTrainer): deepbird v2 DataRecordTrainer
    feature_config (FeatureConfig): deepbird v2 feature config
    keep_fields (list of string): list of field keys, e.g.
                                  ('ids', 'keys', 'values', 'batch_size', 'total_size', 'codes')
    etwitterport_dir (str): a directory (local or hdfs) to etwitterport model to
    as_tetwittert (bool): if True, write 'saved_model.pb' as binary file, else write
                    'saved_model.pbttwittert' as human readable tetwittert file. Default False
  """
  train_input_receiver_fn = get_sparse_batch_supervised_input_receiver_fn(
    feature_config, keep_fields)
  predict_input_receiver_fn = twml.parsers.get_sparse_serving_input_receiver_fn(
    feature_config, keep_fields)
  trainer._etwitterport_output_fn = twml.etwitterport_output_fns.batch_prediction_continuous_output_fn
  trainer._build_graph_fn = update_build_graph_fn_for_train(trainer._build_graph_fn)
  trainer._estimator._etwitterport_all_saved_models(
    etwitterport_dir_base=etwitterport_dir,
    input_receiver_fn_map={
      tf.estimator.ModeKeys.TRAIN: train_input_receiver_fn,
      tf.estimator.ModeKeys.PREDICT: predict_input_receiver_fn
    },
    as_tetwittert=as_tetwittert,
  )

  trainer.etwitterport_model_effects(etwitterport_dir)


def etwitterport_all_models_with_receivers(estimator, etwitterport_dir,
                                     train_input_receiver_fn,
                                     eval_input_receiver_fn,
                                     predict_input_receiver_fn,
                                     etwitterport_output_fn,
                                     etwitterport_modes=('train', 'eval', 'predict'),
                                     register_model_fn=None,
                                     feature_spec=None,
                                     checkpoint_path=None,
                                     log_features=True):
  """
  Function for etwitterporting a model with train, eval, and infer modes.

  Args:
    estimator:
      Should be of type tf.estimator.Estimator.
      You can get this from trainer using trainer.estimator
    etwitterport_dir:
      Directory to etwitterport the model.
    train_input_receiver_fn:
      Input receiver for train interface.
    eval_input_receiver_fn:
      Input receiver for eval interface.
    predict_input_receiver_fn:
      Input receiver for predict interface.
    etwitterport_output_fn:
      etwitterport_output_fn to be used for serving.
    etwitterport_modes:
      A list to Specify what modes to etwitterport. Can be "train", "eval", "predict".
      Defaults to ["train", "eval", "predict"]
    register_model_fn:
      An optional function which is called with etwitterport_dir after models are etwitterported.
      Defaults to None.
  Returns:
     The timestamped directory the models are etwitterported to.
  """
  # TODO: Fitwitter for hogwild / distributed training.

  if etwitterport_dir is None:
    raise ValueError("etwitterport_dir can not be None")
  etwitterport_dir = twml.util.sanitize_hdfs_path(etwitterport_dir)
  input_receiver_fn_map = {}

  if "train" in etwitterport_modes:
    input_receiver_fn_map[tf.estimator.ModeKeys.TRAIN] = train_input_receiver_fn

  if "eval" in etwitterport_modes:
    input_receiver_fn_map[tf.estimator.ModeKeys.EVAL] = eval_input_receiver_fn

  if "predict" in etwitterport_modes:
    input_receiver_fn_map[tf.estimator.ModeKeys.PREDICT] = predict_input_receiver_fn

  etwitterport_dir = estimator._etwitterport_all_saved_models(
    etwitterport_dir_base=etwitterport_dir,
    input_receiver_fn_map=input_receiver_fn_map,
    checkpoint_path=checkpoint_path,
  )

  if register_model_fn is not None:
    register_model_fn(etwitterport_dir, feature_spec, log_features)

  return etwitterport_dir


def etwitterport_all_models(trainer,
                      etwitterport_dir,
                      parse_fn,
                      serving_input_receiver_fn,
                      etwitterport_output_fn=None,
                      etwitterport_modes=('train', 'eval', 'predict'),
                      feature_spec=None,
                      checkpoint=None,
                      log_features=True):
  """
  Function for etwitterporting a model with train, eval, and infer modes.

  Args:
    trainer:
      An object of type twml.trainers.Trainer.
    etwitterport_dir:
      Directory to etwitterport the model.
    parse_fn:
      The parse function used parse the inputs for train and eval.
    serving_input_receiver_fn:
      The input receiver function used during serving.
    etwitterport_output_fn:
      etwitterport_output_fn to be used for serving.
    etwitterport_modes:
      A list to Specify what modes to etwitterport. Can be "train", "eval", "predict".
      Defaults to ["train", "eval", "predict"]
    feature_spec:
      A dictionary obtained from FeatureConfig.get_feature_spec() to serialize
      as feature_spec.yaml in etwitterport_dir.
      Defaults to None
  Returns:
     The timestamped directory the models are etwitterported to.
  """
  # Only etwitterport from chief in hogwild or distributed modes.
  if trainer.params.get('distributed', False) and not trainer.estimator.config.is_chief:
    tf.logging.info("Trainer.etwitterport_model ignored due to instance not being chief.")
    return

  if feature_spec is None:
    if getattr(trainer, '_feature_config') is None:
      raise ValueError("feature_spec is set to None."
                       "Please pass feature_spec=feature_config.get_feature_spec() to the etwitterport_all_model function")
    else:
      feature_spec = trainer._feature_config.get_feature_spec()

  etwitterport_dir = twml.util.sanitize_hdfs_path(etwitterport_dir)
  old_etwitterport_output_fn = trainer._etwitterport_output_fn
  trainer._etwitterport_output_fn = etwitterport_output_fn
  supervised_input_receiver_fn = twml.parsers.convert_to_supervised_input_receiver_fn(parse_fn)
  if not checkpoint:
    checkpoint = trainer.best_or_latest_checkpoint

  etwitterport_dir = etwitterport_all_models_with_receivers(estimator=trainer.estimator,
                                                etwitterport_dir=etwitterport_dir,
                                                train_input_receiver_fn=supervised_input_receiver_fn,
                                                eval_input_receiver_fn=supervised_input_receiver_fn,
                                                predict_input_receiver_fn=serving_input_receiver_fn,
                                                etwitterport_output_fn=etwitterport_output_fn,
                                                etwitterport_modes=etwitterport_modes,
                                                register_model_fn=trainer.etwitterport_model_effects,
                                                feature_spec=feature_spec,
                                                checkpoint_path=checkpoint,
                                                log_features=log_features)
  trainer._etwitterport_output_fn = old_etwitterport_output_fn
  return etwitterport_dir


def etwitterport_feature_spec(dir_path, feature_spec_dict):
  """
  Etwitterports a FeatureConfig.get_feature_spec() dict to <dir_path>/feature_spec.yaml.
  """
  def ordered_dict_representer(dumper, data):
    return dumper.represent_mapping('tag:yaml.org,2002:map', data.items())

  try:
    # needed for Python 2
    yaml.add_representer(str, yaml.representer.SafeRepresenter.represent_str)
    yaml.add_representer(unicode, yaml.representer.SafeRepresenter.represent_unicode)
  etwittercept NameError:
    # 'unicode' type doesn't etwitterist on Python 3
    # PyYAML handles unicode correctly in Python 3
    pass

  yaml.add_representer(OrderedDict, ordered_dict_representer)

  fbase = "feature_spec.yaml"
  fname = fbase.encode('utf-8') if type(dir_path) != str else fbase
  file_path = os.path.join(dir_path, fname)
  with tf.io.gfile.GFile(file_path, mode='w') as f:
    yaml.dump(feature_spec_dict, f, default_flow_style=False, allow_unicode=True)
  tf.logging.info("Etwitterported feature spec to %s" % file_path)

  return file_path


# Keep the alias for compatibility.
get_supervised_input_receiver_fn = twml.parsers.convert_to_supervised_input_receiver_fn
