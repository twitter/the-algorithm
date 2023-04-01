# checkstyle: noqa
import tensorflow.compat.v1 as tf
from tensorflow.python.estimator.export.export import build_raw_serving_input_receiver_fn
from tensorflow.python.framework import dtypes
from tensorflow.python.ops import array_ops
import tensorflow_hub as hub

from datetime import datetime
from tensorflow.compat.v1 import logging
from twitter.deepbird.projects.timelines.configs import all_configs
from twml.trainers import DataRecordTrainer
from twml.contrib.calibrators.common_calibrators import build_percentile_discretizer_graph
from twml.contrib.calibrators.common_calibrators import calibrate_discretizer_and_export
from .metrics import get_multi_binary_class_metric_fn
from .constants import TARGET_LABEL_IDX, PREDICTED_CLASSES
from .example_weights import add_weight_arguments, make_weights_tensor
from .lolly.data_helpers import get_lolly_logits
from .lolly.tf_model_initializer_builder import TFModelInitializerBuilder
from .lolly.reader import LollyModelReader
from .tf_model.discretizer_builder import TFModelDiscretizerBuilder
from .tf_model.weights_initializer_builder import TFModelWeightsInitializerBuilder

import twml

def get_feature_values(features_values, params):
  if params.lolly_model_tsv:
    # The default DBv2 HashingDiscretizer bin membership interval is (a, b]
    #
    # The Earlybird Lolly prediction engine discretizer bin membership interval is [a, b)
    #
    # TFModelInitializerBuilder converts (a, b] to [a, b) by inverting the bin boundaries.
    #
    # Thus, invert the feature values, so that HashingDiscretizer can to find the correct bucket.
    return tf.multiply(features_values, -1.0)
  else:
    return features_values

def build_graph(features, label, mode, params, config=None):
  weights = None
  if "weights" in features:
    weights = make_weights_tensor(features["weights"], label, params)

  num_bits = params.input_size_bits

  if mode == "infer":
    indices = twml.limit_bits(features["input_sparse_tensor_indices"], num_bits)
    dense_shape = tf.stack([features["input_sparse_tensor_shape"][0], 1 << num_bits])
    sparse_tf = tf.SparseTensor(
      indices=indices,
      values=get_feature_values(features["input_sparse_tensor_values"], params),
      dense_shape=dense_shape
    )
  else:
    features["values"] = get_feature_values(features["values"], params)
    sparse_tf = twml.util.convert_to_sparse(features, num_bits)

  if params.lolly_model_tsv:
    tf_model_initializer = TFModelInitializerBuilder().build(LollyModelReader(params.lolly_model_tsv))
    bias_initializer, weight_initializer = TFModelWeightsInitializerBuilder(num_bits).build(tf_model_initializer)
    discretizer = TFModelDiscretizerBuilder(num_bits).build(tf_model_initializer)
  else:
    discretizer = hub.Module(params.discretizer_save_dir)
    bias_initializer, weight_initializer = None, None

  input_sparse = discretizer(sparse_tf, signature="hashing_discretizer_calibrator")

  logits = twml.layers.full_sparse(
    inputs=input_sparse,
    output_size=1,
    bias_initializer=bias_initializer,
    weight_initializer=weight_initializer,
    use_sparse_grads=(mode == "train"),
    use_binary_values=True,
    name="full_sparse_1"
  )

  loss = None

  if mode != "infer":
    lolly_activations = get_lolly_logits(label)

    if opt.print_data_examples:
      logits = print_data_example(logits, lolly_activations, features)

    if params.replicate_lolly:
      loss = tf.reduce_mean(tf.math.squared_difference(logits, lolly_activations))
    else:
      batch_size = tf.shape(label)[0]
      target_label = tf.reshape(tensor=label[:, TARGET_LABEL_IDX], shape=(batch_size, 1))
      loss = tf.nn.sigmoid_cross_entropy_with_logits(labels=target_label, logits=logits)
      loss = twml.util.weighted_average(loss, weights)

    num_labels = tf.shape(label)[1]
    eb_scores = tf.tile(lolly_activations, [1, num_labels])
    logits = tf.tile(logits, [1, num_labels])
    logits = tf.concat([logits, eb_scores], axis=1)

  output = tf.nn.sigmoid(logits)

  return {"output": output, "loss": loss, "weights": weights}

def print_data_example(logits, lolly_activations, features):
  return tf.Print(
    logits,
    [logits, lolly_activations, tf.reshape(features['keys'], (1, -1)), tf.reshape(tf.multiply(features['values'], -1.0), (1, -1))],
    message="DATA EXAMPLE = ",
    summarize=10000
  )

def earlybird_output_fn(graph_output):
  export_outputs = {
    tf.saved_model.signature_constants.DEFAULT_SERVING_SIGNATURE_DEF_KEY:
      tf.estimator.export.PredictOutput(
        {"prediction": tf.identity(graph_output["output"], name="output_scores")}
      )
  }
  return export_outputs

if __name__ == "__main__":
  parser = DataRecordTrainer.add_parser_arguments()

  parser = twml.contrib.calibrators.add_discretizer_arguments(parser)

  parser.add_argument("--label", type=str, help="label for the engagement")
  parser.add_argument("--model.use_existing_discretizer", action="store_true",
                      dest="model_use_existing_discretizer",
                      help="Load a pre-trained calibration or train a new one")
  parser.add_argument("--input_size_bits", type=int)
  parser.add_argument("--export_module_name", type=str, default="base_mlp", dest="export_module_name")
  parser.add_argument("--feature_config", type=str)
  parser.add_argument("--replicate_lolly", type=bool, default=False, dest="replicate_lolly",
                      help="Train a regression model with MSE loss and the logged Earlybird score as a label")
  parser.add_argument("--lolly_model_tsv", type=str, required=False, dest="lolly_model_tsv",
                      help="Initialize with weights and discretizer bins available in the given Lolly model tsv file"
                      "No discretizer gets trained or loaded if set.")
  parser.add_argument("--print_data_examples", type=bool, default=False, dest="print_data_examples",
                      help="Prints 'DATA EXAMPLE = [[tf logit]][[logged lolly logit]][[feature ids][feature values]]'")
  add_weight_arguments(parser)

  opt = parser.parse_args()

  feature_config_module = all_configs.select_feature_config(opt.feature_config)

  feature_config = feature_config_module.get_feature_config(data_spec_path=opt.data_spec, label=opt.label)

  parse_fn = twml.parsers.get_sparse_parse_fn(
    feature_config,
    keep_fields=("ids", "keys", "values", "batch_size", "total_size", "codes"))

  if not opt.lolly_model_tsv:
    if opt.model_use_existing_discretizer:
      logging.info("Skipping discretizer calibration [model.use_existing_discretizer=True]")
      logging.info(f"Using calibration at {opt.discretizer_save_dir}")
    else:
      logging.info("Calibrating new discretizer [model.use_existing_discretizer=False]")
      calibrator = twml.contrib.calibrators.HashingDiscretizerCalibrator(
        opt.discretizer_num_bins,
        opt.discretizer_output_size_bits
      )
      calibrate_discretizer_and_export(name="recap_earlybird_hashing_discretizer",
                                       params=opt,
                                       calibrator=calibrator,
                                       build_graph_fn=build_percentile_discretizer_graph,
                                       feature_config=feature_config)

  trainer = DataRecordTrainer(
    name="earlybird",
    params=opt,
    build_graph_fn=build_graph,
    save_dir=opt.save_dir,
    feature_config=feature_config,
    metric_fn=get_multi_binary_class_metric_fn(
      metrics=["roc_auc"],
      classes=PREDICTED_CLASSES
    ),
    warm_start_from=None
  )

  train_input_fn = trainer.get_train_input_fn(parse_fn=parse_fn)
  eval_input_fn = trainer.get_eval_input_fn(parse_fn=parse_fn)

  logging.info("Training and Evaluation ...")
  trainingStartTime = datetime.now()
  trainer.train_and_evaluate(train_input_fn=train_input_fn, eval_input_fn=eval_input_fn)
  trainingEndTime = datetime.now()
  logging.info("Training and Evaluation time: " + str(trainingEndTime - trainingStartTime))

  if trainer._estimator.config.is_chief:
    serving_input_in_earlybird = {
      "input_sparse_tensor_indices": array_ops.placeholder(
        name="input_sparse_tensor_indices",
        shape=[None, 2],
        dtype=dtypes.int64),
      "input_sparse_tensor_values": array_ops.placeholder(
        name="input_sparse_tensor_values",
        shape=[None],
        dtype=dtypes.float32),
      "input_sparse_tensor_shape": array_ops.placeholder(
        name="input_sparse_tensor_shape",
        shape=[2],
        dtype=dtypes.int64)
    }
    serving_input_receiver_fn = build_raw_serving_input_receiver_fn(serving_input_in_earlybird)
    twml.contrib.export.export_fn.export_all_models(
      trainer=trainer,
      export_dir=opt.export_dir,
      parse_fn=parse_fn,
      serving_input_receiver_fn=serving_input_receiver_fn,
      export_output_fn=earlybird_output_fn,
      feature_spec=feature_config.get_feature_spec()
    )
    logging.info("The export model path is: " + opt.export_dir)
