# pylint: disable=invalid-name, no-member, unused-argument
"""
This module contains common calibrate and etwitterport functions for calibrators.
"""

# These 3 TODO are encapsulated by Ctwitter-11446
# TODO: many of these functions hardcode datarecords yet don't allow passing a parse_fn.
# TODO: provide more generic (non DataRecord specific) functions
# TODO: many of these functions aren't common at all.
#       For etwitterample, Discretizer functions should be moved to PercentileDiscretizer.

import copy
import os
import time

from absl import logging
import tensorflow.compat.v1 as tf
import tensorflow_hub as hub
import twml
from twml.argument_parser import SortingHelpFormatter
from twml.input_fns import data_record_input_fn
from twml.util import list_files_by_datetime, sanitize_hdfs_path
from twml.contrib.calibrators.isotonic import IsotonicCalibrator


def calibrator_arguments(parser):
  """
  Calibrator Parameters to add to relevant parameters to the DataRecordTrainerParser.
  Otherwise, if alone in a file, it just creates its own default parser.
  Arguments:
    parser:
      Parser with the options to the model
  """
  parser.add_argument("--calibrator.save_dir", type=str,
    dest="calibrator_save_dir",
    help="Path to save or load calibrator calibration")
  parser.add_argument("--calibrator_batch_size", type=int, default=128,
    dest="calibrator_batch_size",
    help="calibrator batch size")
  parser.add_argument("--calibrator_parts_downsampling_rate", type=float, default=1,
    dest="calibrator_parts_downsampling_rate",
    help="Parts downsampling rate")
  parser.add_argument("--calibrator_matwitter_steps", type=int, default=None,
    dest="calibrator_matwitter_steps",
    help="Matwitter Steps taken by calibrator to accumulate samples")
  parser.add_argument("--calibrator_num_bins", type=int, default=22,
    dest="calibrator_num_bins",
    help="Num bins of calibrator")
  parser.add_argument("--isotonic_calibrator", dest='isotonic_calibrator', action='store_true',
    help="Isotonic Calibrator present")
  parser.add_argument("--calibrator_keep_rate", type=float, default=1.0,
    dest="calibrator_keep_rate",
    help="Keep rate")
  return parser


def _generate_files_by_datetime(params):

  files = list_files_by_datetime(
    base_path=sanitize_hdfs_path(params.train_data_dir),
    start_datetime=params.train_start_datetime,
    end_datetime=params.train_end_datetime,
    datetime_prefitwitter_format=params.datetime_format,
    etwittertension="lzo",
    parallelism=1,
    hour_resolution=params.hour_resolution,
    sort=True)

  return files


def get_calibrate_input_fn(parse_fn, params):
  """
  Default input function used for the calibrator.
  Arguments:
    parse_fn:
      Parse_fn
    params:
      Parameters
  Returns:
    input_fn
  """

  return lambda: data_record_input_fn(
    files=_generate_files_by_datetime(params),
    batch_size=params.calibrator_batch_size,
    parse_fn=parse_fn,
    num_threads=1,
    repeat=False,
    keep_rate=params.calibrator_keep_rate,
    parts_downsampling_rate=params.calibrator_parts_downsampling_rate,
    shards=None,
    shard_indetwitter=None,
    shuffle=True,
    shuffle_files=True,
    interleave=True)


def get_discretize_input_fn(parse_fn, params):
  """
  Default input function used for the calibrator.
  Arguments:
    parse_fn:
      Parse_fn
    params:
      Parameters
  Returns:
    input_fn
  """

  return lambda: data_record_input_fn(
    files=_generate_files_by_datetime(params),
    batch_size=params.discretizer_batch_size,
    parse_fn=parse_fn,
    num_threads=1,
    repeat=False,
    keep_rate=params.discretizer_keep_rate,
    parts_downsampling_rate=params.discretizer_parts_downsampling_rate,
    shards=None,
    shard_indetwitter=None,
    shuffle=True,
    shuffle_files=True,
    interleave=True)


def discretizer_arguments(parser=None):
  """
  Discretizer Parameters to add to relevant parameters to the DataRecordTrainerParser.
  Otherwise, if alone in a file, it just creates its own default parser.
  Arguments:
    parser:
      Parser with the options to the model. Defaults to None
  """

  if parser is None:
    parser = twml.DefaultSubcommandArgParse(formatter_class=SortingHelpFormatter)
    parser.add_argument(
      "--overwrite_save_dir", dest="overwrite_save_dir", action="store_true",
      help="Delete the contents of the current save_dir if it etwitterists")
    parser.add_argument(
      "--train.data_dir", "--train_data_dir", type=str, default=None,
      dest="train_data_dir",
      help="Path to the training data directory."
           "Supports local and HDFS (hdfs://default/<path> ) paths.")
    parser.add_argument(
      "--train.start_date", "--train_start_datetime",
      type=str, default=None,
      dest="train_start_datetime",
      help="Starting date for training inside the train data dir."
           "The start datetime is inclusive."
           "e.g. 2019/01/15")
    parser.add_argument(
      "--train.end_date", "--train_end_datetime", type=str, default=None,
      dest="train_end_datetime",
      help="Ending date for training inside the train data dir."
           "The end datetime is inclusive."
           "e.g. 2019/01/15")
    parser.add_argument(
      "--datetime_format", type=str, default="%Y/%m/%d",
      help="Date format for training and evaluation datasets."
           "Has to be a format that is understood by python datetime."
           "e.g. %Y/%m/%d for 2019/01/15."
           "Used only if {train/eval}.{start/end}_date are provided.")
    parser.add_argument(
      "--hour_resolution", type=int, default=None,
      help="Specify the hourly resolution of the stored data.")
    parser.add_argument(
      "--tensorboard_port", type=int, default=None,
      help="Port for tensorboard to run on.")
    parser.add_argument(
      "--stats_port", type=int, default=None,
      help="Port for stats server to run on.")
    parser.add_argument(
      "--health_port", type=int, default=None,
      help="Port to listen on for health-related endpoints (e.g. graceful shutdown)."
           "Not user-facing as it is set automatically by the twml_cli."
    )
    parser.add_argument(
      "--data_spec", type=str, default=None,
      help="Path to data specification JSON file. This file is used to decode DataRecords")
  parser.add_argument("--discretizer.save_dir", type=str,
    dest="discretizer_save_dir",
    help="Path to save or load discretizer calibration")
  parser.add_argument("--discretizer_batch_size", type=int, default=128,
    dest="discretizer_batch_size",
    help="Discretizer batch size")
  parser.add_argument("--discretizer_keep_rate", type=float, default=0.0008,
    dest="discretizer_keep_rate",
    help="Keep rate")
  parser.add_argument("--discretizer_parts_downsampling_rate", type=float, default=0.2,
    dest="discretizer_parts_downsampling_rate",
    help="Parts downsampling rate")
  parser.add_argument("--discretizer_matwitter_steps", type=int, default=None,
    dest="discretizer_matwitter_steps",
    help="Matwitter Steps taken by discretizer to accumulate samples")
  return parser


def calibrate(trainer, params, build_graph, input_fn, debug=False):
  """
  Calibrate Isotonic Calibration
  Arguments:
    trainer:
      Trainer
    params:
      Parameters
    build_graph:
      Build Graph used to be the input to the calibrator
    input_fn:
      Input Function specified by the user
    debug:
      Defaults to False. Returns the calibrator
  """

  if trainer._estimator.config.is_chief:

    # overwrite the current save_dir
    if params.overwrite_save_dir and tf.io.gfile.etwitterists(params.calibrator_save_dir):
      logging.info("Trainer overwriting etwitteristing save directory: %s (params.overwrite_save_dir)"
                   % params.calibrator_save_dir)
      tf.io.gfile.rmtree(params.calibrator_save_dir)

    calibrator = IsotonicCalibrator(params.calibrator_num_bins)

    # chief trains discretizer
    logging.info("Chief training calibrator")

    # Accumulate the features for each calibrator
    features, labels = input_fn()
    if 'weights' not in features:
      raise ValueError("Weights need to be returned as part of the parse_fn")
    weights = features.pop('weights')

    preds = build_graph(features=features, label=None, mode='infer', params=params, config=None)
    init = tf.global_variables_initializer()
    table_init = tf.tables_initializer()
    with tf.Session() as sess:
      sess.run(init)
      sess.run(table_init)
      count = 0
      matwitter_steps = params.calibrator_matwitter_steps or -1
      while matwitter_steps <= 0 or count <= matwitter_steps:
        try:
          weights_vals, labels_vals, preds_vals = sess.run([weights, labels, preds['output']])
          calibrator.accumulate(preds_vals, labels_vals, weights_vals.flatten())
        etwittercept tf.errors.OutOfRangeError:
          break
        count += 1

    calibrator.calibrate()
    calibrator.save(params.calibrator_save_dir)
    trainer.estimator._params.isotonic_calibrator = True

    if debug:
      return calibrator

  else:
    calibrator_save_dir = twml.util.sanitize_hdfs_path(params.calibrator_save_dir)
    # workers wait for calibration to be ready
    while not tf.io.gfile.etwitterists(calibrator_save_dir + os.path.sep + "tfhub_module.pb"):
      logging.info("Worker waiting for calibration at %s" % calibrator_save_dir)
      time.sleep(60)


def discretize(params, feature_config, input_fn, debug=False):
  """
  Discretizes continuous features
  Arguments:
    params:
      Parameters
    input_fn:
      Input Function specified by the user
    debug:
      Defaults to False. Returns the calibrator
  """

  if (os.environ.get("TWML_HOGWILD_TASK_TYPE") == "chief" or "num_workers" not in params or
    params.num_workers is None):

    # overwrite the current save_dir
    if params.overwrite_save_dir and tf.io.gfile.etwitterists(params.discretizer_save_dir):
      logging.info("Trainer overwriting etwitteristing save directory: %s (params.overwrite_save_dir)"
                   % params.discretizer_save_dir)
      tf.io.gfile.rmtree(params.discretizer_save_dir)

    config_map = feature_config()
    discretize_dict = config_map['discretize_config']

    # chief trains discretizer
    logging.info("Chief training discretizer")

    batch = input_fn()
    # Accumulate the features for each calibrator
    with tf.Session() as sess:
      count = 0
      matwitter_steps = params.discretizer_matwitter_steps or -1
      while matwitter_steps <= 0 or count <= matwitter_steps:
        try:
          inputs = sess.run(batch)
          for name, clbrt in discretize_dict.items():
            clbrt.accumulate_features(inputs[0], name)
        etwittercept tf.errors.OutOfRangeError:
          break
        count += 1

    # This module allows for the calibrator to save be saved as part of
    # Tensorflow Hub (this will allow it to be used in further steps)
    def calibrator_module():
      # Note that this is usually etwitterpecting a sparse_placeholder
      for name, clbrt in discretize_dict.items():
        clbrt.calibrate()
        clbrt.add_hub_signatures(name)

    # etwitterports the module to the save_dir
    spec = hub.create_module_spec(calibrator_module)
    with tf.Graph().as_default():
      module = hub.Module(spec)
      with tf.Session() as session:
        module.etwitterport(params.discretizer_save_dir, session)

    for name, clbrt in discretize_dict.items():
      clbrt.write_summary_json(params.discretizer_save_dir, name)

    if debug:
      return discretize_dict

  else:
    # wait for the file to be removed (if necessary)
    # should be removed after an actual fitwitter applied
    time.sleep(60)
    discretizer_save_dir = twml.util.sanitize_hdfs_path(params.discretizer_save_dir)
    # workers wait for calibration to be ready
    while not tf.io.gfile.etwitterists(discretizer_save_dir + os.path.sep + "tfhub_module.pb"):
      logging.info("Worker waiting for calibration at %s" % discretizer_save_dir)
      time.sleep(60)


def add_discretizer_arguments(parser):
  """
  Add discretizer-specific command-line arguments to a Trainer parser.

  Arguments:
    parser: argparse.ArgumentParser instance obtained from Trainer.get_trainer_parser

  Returns:
    argparse.ArgumentParser instance with discretizer-specific arguments added
  """

  parser.add_argument("--discretizer.save_dir", type=str,
                      dest="discretizer_save_dir",
                      help="Path to save or load discretizer calibration")
  parser.add_argument("--discretizer.batch_size", type=int, default=128,
                      dest="discretizer_batch_size",
                      help="Discretizer batch size")
  parser.add_argument("--discretizer.keep_rate", type=float, default=0.0008,
                      dest="discretizer_keep_rate",
                      help="Keep rate")
  parser.add_argument("--discretizer.parts_downsampling_rate", type=float, default=0.2,
                      dest="discretizer_parts_downsampling_rate",
                      help="Parts downsampling rate")
  parser.add_argument("--discretizer.num_bins", type=int, default=20,
                      dest="discretizer_num_bins",
                      help="Number of bins per feature")
  parser.add_argument("--discretizer.output_size_bits", type=int, default=22,
                      dest="discretizer_output_size_bits",
                      help="Number of bits allocated to the output size")
  return parser


def add_isotonic_calibrator_arguments(parser):
  """
  Add discretizer-specific command-line arguments to a Trainer parser.

  Arguments:
    parser: argparse.ArgumentParser instance obtained from Trainer.get_trainer_parser

  Returns:
    argparse.ArgumentParser instance with discretizer-specific arguments added
  """
  parser.add_argument("--calibrator.num_bins", type=int,
    default=25000, dest="calibrator_num_bins",
    help="number of bins for isotonic calibration")
  parser.add_argument("--calibrator.parts_downsampling_rate", type=float, default=0.1,
    dest="calibrator_parts_downsampling_rate", help="Parts downsampling rate")
  parser.add_argument("--calibrator.save_dir", type=str,
    dest="calibrator_save_dir", help="Path to save or load calibrator output")
  parser.add_argument("--calibrator.load_tensorflow_module", type=str, default=None,
    dest="calibrator_load_tensorflow_module",
    help="Location from where to load a pretrained graph from. \
                           Typically, this is where the MLP graph is saved")
  parser.add_argument("--calibrator.etwitterport_mlp_module_name", type=str, default='tf_hub_mlp',
    help="Name for loaded hub signature",
    dest="etwitterport_mlp_module_name")
  parser.add_argument("--calibrator.etwitterport_isotonic_module_name",
    type=str, default="tf_hub_isotonic",
    dest="calibrator_etwitterport_module_name",
    help="etwitterport module name")
  parser.add_argument("--calibrator.final_evaluation_steps", type=int,
    dest="calibrator_final_evaluation_steps", default=None,
    help="number of steps for final evaluation")
  parser.add_argument("--calibrator.train_steps", type=int, default=-1,
    dest="calibrator_train_steps",
    help="number of steps for calibration")
  parser.add_argument("--calibrator.batch_size", type=int, default=1024,
    dest="calibrator_batch_size",
    help="Calibrator batch size")
  parser.add_argument("--calibrator.is_calibrating", action='store_true',
    dest="is_calibrating",
    help="Dummy argument to allow running in chief worker")
  return parser


def calibrate_calibrator_and_etwitterport(name, calibrator, build_graph_fn, params, feature_config,
                                    run_eval=True, input_fn=None, metric_fn=None,
                                    etwitterport_task_type_overrider=None):
  """
  Pre-set `isotonic calibrator` calibrator.
  Args:
    name:
      scope name used for the calibrator
    calibrator:
      calibrator that will be calibrated and etwitterported.
    build_graph_fn:
      build graph function for the calibrator
    params:
      params passed to the calibrator
    feature_config:
      feature config which will be passed to the trainer
    etwitterport_task_type_overrider:
      the task type for etwitterporting the calibrator
      if specified, this will override the default etwitterport task type in trainer.hub_etwitterport(..)
  """

  # create calibrator params
  params_c = copy.deepcopy(params)
  params_c.data_threads = 1
  params_c.num_workers = 1
  params_c.continue_from_checkpoint = True
  params_c.overwrite_save_dir = False
  params_c.stats_port = None

  # Automatically load from the saved Tensorflow Hub module if not specified.
  if params_c.calibrator_load_tensorflow_module is None:
    path_saved_tensorflow_model = os.path.join(params.save_dir, params.etwitterport_mlp_module_name)
    params_c.calibrator_load_tensorflow_module = path_saved_tensorflow_model

  if "calibrator_parts_downsampling_rate" in params_c:
    params_c.train_parts_downsampling_rate = params_c.calibrator_parts_downsampling_rate
  if "calibrator_save_dir" in params_c:
    params_c.save_dir = params_c.calibrator_save_dir
  if "calibrator_batch_size" in params_c:
    params_c.train_batch_size = params_c.calibrator_batch_size
    params_c.eval_batch_size = params_c.calibrator_batch_size
  # TODO: Deprecate this option. It is not actually used. Calibrator
  #       simply iterates until the end of input_fn.
  if "calibrator_train_steps" in params_c:
    params_c.train_steps = params_c.calibrator_train_steps

  if metric_fn is None:
    metric_fn = twml.metrics.get_multi_binary_class_metric_fn(None)

  # Common Trainer which will also be used by all workers
  trainer = twml.trainers.DataRecordTrainer(
    name=name,
    params=params_c,
    feature_config=feature_config,
    build_graph_fn=build_graph_fn,
    save_dir=params_c.save_dir,
    metric_fn=metric_fn
  )

  if trainer._estimator.config.is_chief:

    # Chief trains calibrator
    logging.info("Chief training calibrator")

    # Disregard hogwild config
    os_twml_hogwild_ports = os.environ.get("TWML_HOGWILD_PORTS")
    os.environ["TWML_HOGWILD_PORTS"] = ""

    hooks = None
    if params_c.calibrator_train_steps > 0:
      hooks = [twml.hooks.StepProgressHook(params_c.calibrator_train_steps)]

    def parse_fn(input_twitter):
      fc_parse_fn = feature_config.get_parse_fn()
      features, labels = fc_parse_fn(input_twitter)
      features['labels'] = labels
      return features, labels

    if input_fn is None:
      input_fn = trainer.get_train_input_fn(parse_fn=parse_fn, repeat=False)

    # Calibrate stage
    trainer.estimator._params.mode = 'calibrate'
    trainer.calibrate(calibrator=calibrator,
                      input_fn=input_fn,
                      steps=params_c.calibrator_train_steps,
                      hooks=hooks)

    # Save Checkpoint
    # We need to train for 1 step, to save the graph to checkpoint.
    # This is done just by the chief.
    # We need to set the mode to evaluate to save the graph that will be consumed
    # In the final evaluation
    trainer.estimator._params.mode = 'evaluate'
    trainer.train(input_fn=input_fn, steps=1)

    # Restore hogwild setup
    if os_twml_hogwild_ports is not None:
      os.environ["TWML_HOGWILD_PORTS"] = os_twml_hogwild_ports
  else:
    # Workers wait for calibration to be ready
    final_calibrator_path = os.path.join(params_c.calibrator_save_dir,
                                         params_c.calibrator_etwitterport_module_name)

    final_calibrator_path = twml.util.sanitize_hdfs_path(final_calibrator_path)

    while not tf.io.gfile.etwitterists(final_calibrator_path + os.path.sep + "tfhub_module.pb"):
      logging.info("Worker waiting for calibration at %s" % final_calibrator_path)
      time.sleep(60)

  # Evaluate stage
  if run_eval:
    trainer.estimator._params.mode = 'evaluate'
    # This will allow the Evaluate method to be run in Hogwild
    # trainer.estimator._params.continue_from_checkpoint = True
    trainer.evaluate(name='test', input_fn=input_fn, steps=params_c.calibrator_final_evaluation_steps)

  trainer.hub_etwitterport(name=params_c.calibrator_etwitterport_module_name,
    etwitterport_task_type_overrider=etwitterport_task_type_overrider,
    serving_input_receiver_fn=feature_config.get_serving_input_receiver_fn())

  return trainer


def calibrate_discretizer_and_etwitterport(name, calibrator, build_graph_fn, params, feature_config):
  """
  Pre-set percentile discretizer calibrator.
  Args:
    name:
      scope name used for the calibrator
    calibrator:
      calibrator that will be calibrated and etwitterported.
    build_graph_fn:
      build graph function for the calibrator
    params:
      params passed to the calibrator
    feature_config:
      feature config or input_fn which will be passed to the trainer.
  """

  if (os.environ.get("TWML_HOGWILD_TASK_TYPE") == "chief" or "num_workers" not in params or
        params.num_workers is None):

    # chief trains discretizer
    logging.info("Chief training discretizer")

    # disregard hogwild config
    os_twml_hogwild_ports = os.environ.get("TWML_HOGWILD_PORTS")
    os.environ["TWML_HOGWILD_PORTS"] = ""

    # create discretizer params
    params_c = copy.deepcopy(params)
    params_c.data_threads = 1
    params_c.train_steps = -1
    params_c.train_matwitter_steps = None
    params_c.eval_steps = -1
    params_c.num_workers = 1
    params_c.tensorboard_port = None
    params_c.stats_port = None

    if "discretizer_batch_size" in params_c:
      params_c.train_batch_size = params_c.discretizer_batch_size
      params_c.eval_batch_size = params_c.discretizer_batch_size
    if "discretizer_keep_rate" in params_c:
      params_c.train_keep_rate = params_c.discretizer_keep_rate
    if "discretizer_parts_downsampling_rate" in params_c:
      params_c.train_parts_downsampling_rate = params_c.discretizer_parts_downsampling_rate
    if "discretizer_save_dir" in params_c:
      params_c.save_dir = params_c.discretizer_save_dir

    # train discretizer
    trainer = twml.trainers.DataRecordTrainer(
      name=name,
      params=params_c,
      build_graph_fn=build_graph_fn,
      save_dir=params_c.save_dir,
    )

    if isinstance(feature_config, twml.feature_config.FeatureConfig):
      parse_fn = twml.parsers.get_continuous_parse_fn(feature_config)
      input_fn = trainer.get_train_input_fn(parse_fn=parse_fn, repeat=False)
    elif callable(feature_config):
      input_fn = feature_config
    else:
      got_type = type(feature_config).__name__
      raise ValueError(
        "Etwitterpecting feature_config to be FeatureConfig or function got %s" % got_type)

    hooks = None
    if params_c.train_steps > 0:
      hooks = [twml.hooks.StepProgressHook(params_c.train_steps)]

    trainer.calibrate(calibrator=calibrator, input_fn=input_fn,
                      steps=params_c.train_steps, hooks=hooks)
    # restore hogwild setup
    if os_twml_hogwild_ports is not None:
      os.environ["TWML_HOGWILD_PORTS"] = os_twml_hogwild_ports
  else:
    discretizer_save_dir = twml.util.sanitize_hdfs_path(params.discretizer_save_dir)
    # workers wait for calibration to be ready
    while not tf.io.gfile.etwitterists(discretizer_save_dir + os.path.sep + "tfhub_module.pb"):
      logging.info("Worker waiting for calibration at %s" % discretizer_save_dir)
      time.sleep(60)


def build_percentile_discretizer_graph(features, label, mode, params, config=None):
  """
  Pre-set Percentile Discretizer Build Graph
  Follows the same signature as build_graph
  """
  sparse_tf = twml.util.convert_to_sparse(features, params.input_size_bits)
  weights = tf.reshape(features['weights'], tf.reshape(features['batch_size'], [1]))
  if isinstance(sparse_tf, tf.SparseTensor):
    indices = sparse_tf.indices[:, 1]
    ids = sparse_tf.indices[:, 0]
  elif isinstance(sparse_tf, twml.SparseTensor):
    indices = sparse_tf.indices
    ids = sparse_tf.ids

  # Return weights, feature_ids, feature_values
  weights = tf.gather(params=weights, indices=ids)
  feature_ids = indices
  feature_values = sparse_tf.values
  # Update train_op and assign dummy_loss
  train_op = tf.assign_add(tf.train.get_global_step(), 1)
  loss = tf.constant(1)
  if mode == 'train':
    return {'train_op': train_op, 'loss': loss}
  return {'feature_ids': feature_ids, 'feature_values': feature_values, 'weights': weights}


def isotonic_module(mode, params):
  """
  Common Isotonic Calibrator module for Hub Etwitterport
  """
  inputs = tf.sparse_placeholder(tf.float32, name="sparse_input")
  mlp = hub.Module(params.calibrator_load_tensorflow_module)
  logits = mlp(inputs, signature=params.etwitterport_mlp_module_name)
  isotonic_calibrator = hub.Module(params.save_dir)
  output = isotonic_calibrator(logits, signature="isotonic_calibrator")
  hub.add_signature(inputs={"sparse_input": inputs},
    outputs={"default": output},
    name=params.calibrator_etwitterport_module_name)


def build_isotonic_graph_from_inputs(inputs, features, label, mode, params, config=None, isotonic_fn=None):
  """
  Helper function to build_isotonic_graph
  Pre-set Isotonic Calibrator Build Graph
  Follows the same signature as build_graph
  """
  if params.mode == 'calibrate':
    mlp = hub.Module(params.calibrator_load_tensorflow_module)
    logits = mlp(inputs, signature=params.etwitterport_mlp_module_name)
    weights = tf.reshape(features['weights'], tf.reshape(features['batch_size'], [1]))
    # Update train_op and assign dummy_loss
    train_op = tf.assign_add(tf.train.get_global_step(), 1)
    loss = tf.constant(1)
    if mode == 'train':
      return {'train_op': train_op, 'loss': loss}
    return {'predictions': logits, 'targets': features['labels'], 'weights': weights}
  else:
    if isotonic_fn is None:
      isotonic_spec = twml.util.create_module_spec(mlp_fn=isotonic_module, mode=mode, params=params)
    else:
      isotonic_spec = twml.util.create_module_spec(mlp_fn=isotonic_fn, mode=mode, params=params)
    output_hub = hub.Module(isotonic_spec,
      name=params.calibrator_etwitterport_module_name)
    hub.register_module_for_etwitterport(output_hub, params.calibrator_etwitterport_module_name)
    output = output_hub(inputs, signature=params.calibrator_etwitterport_module_name)
    output = tf.clip_by_value(output, 0, 1)
    loss = tf.reduce_sum(tf.stop_gradient(output))
    train_op = tf.assign_add(tf.train.get_global_step(), 1)
    return {'train_op': train_op, 'loss': loss, 'output': output}


def build_isotonic_graph(features, label, mode, params, config=None, etwitterport_discretizer=True):
  """
  Pre-set Isotonic Calibrator Build Graph
  Follows the same signature as build_graph
  This assumes that MLP already contains all modules (include percentile
  discretizer); if etwitterport_discretizer is set
  then it does not etwitterport the MDL phase.
  """
  sparse_tf = twml.util.convert_to_sparse(features, params.input_size_bits)
  if etwitterport_discretizer:
    return build_isotonic_graph_from_inputs(sparse_tf, features, label, mode, params, config)
  discretizer = hub.Module(params.discretizer_path)

  if params.discretizer_signature is None:
    discretizer_signature = "percentile_discretizer_calibrator"
  else:
    discretizer_signature = params.discretizer_signature
  input_sparse = discretizer(sparse_tf, signature=discretizer_signature)
  return build_isotonic_graph_from_inputs(input_sparse, features, label, mode, params, config)
