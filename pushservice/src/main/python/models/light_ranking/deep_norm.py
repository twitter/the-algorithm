from datetime import datetime
from functools import partial
import os

from twitter.cortex.ml.embeddings.common.helpers import decode_str_or_unicode
import twml
from twml.trainers import DataRecordTrainer

from ..libs.get_feat_config import get_feature_config_light_ranking, LABELS_LR
from ..libs.graph_utils import get_trainable_variables
from ..libs.group_metrics import (
  run_group_metrics_light_ranking,
  run_group_metrics_light_ranking_in_bq,
)
from ..libs.metric_fn_utils import get_metric_fn
from ..libs.model_args import get_arg_parser_light_ranking
from ..libs.model_utils import read_config
from ..libs.warm_start_utils import get_feature_list_for_light_ranking
from .model_pools_mlp import light_ranking_mlp_ngbdt

import tensorflow.compat.v1 as tf
from tensorflow.compat.v1 import logging


# checkstyle: noqa


def build_graph(
  features, label, mode, params, config=None, run_light_ranking_group_metrics_in_bq=False
):
  is_training = mode == tf.estimator.ModeKeys.TRAIN
  this_model_func = light_ranking_mlp_ngbdt
  model_output = this_model_func(features, is_training, params, label)

  logits = model_output["output"]
  graph_output = {}
  # --------------------------------------------------------
  #            define graph output dict
  # --------------------------------------------------------
  if mode == tf.estimator.ModeKeys.PREDICT:
    loss = None
    output_label = "prediction"
    if params.task_name in LABELS_LR:
      output = tf.nn.sigmoid(logits)
      output = tf.clip_by_value(output, 0, 1)

      if run_light_ranking_group_metrics_in_bq:
        graph_output["trace_id"] = features["meta.trace_id"]
        graph_output["target"] = features["meta.ranking.weighted_oonc_model_score"]

    else:
      raise ValueError("Invalid Task Name !")

  else:
    output_label = "output"
    weights = tf.cast(features["weights"], dtype=tf.float32, name="RecordWeights")

    if params.task_name in LABELS_LR:
      if params.use_record_weight:
        weights = tf.clip_by_value(
          1.0 / (1.0 + weights + params.smooth_weight), params.min_record_weight, 1.0
        )

        loss = tf.reduce_sum(
          tf.nn.sigmoid_cross_entropy_with_logits(labels=label, logits=logits) * weights
        ) / (tf.reduce_sum(weights))
      else:
        loss = tf.reduce_mean(tf.nn.sigmoid_cross_entropy_with_logits(labels=label, logits=logits))
      output = tf.nn.sigmoid(logits)

    else:
      raise ValueError("Invalid Task Name !")

  train_op = None
  if mode == tf.estimator.ModeKeys.TRAIN:
    # --------------------------------------------------------
    #                get train_op
    # --------------------------------------------------------
    optimizer = tf.train.GradientDescentOptimizer(learning_rate=params.learning_rate)
    update_ops = set(tf.get_collection(tf.GraphKeys.UPDATE_OPS))
    variables = get_trainable_variables(
      all_trainable_variables=tf.trainable_variables(), trainable_regexes=params.trainable_regexes
    )
    with tf.control_dependencies(update_ops):
      train_op = twml.optimizers.optimize_loss(
        loss=loss,
        variables=variables,
        global_step=tf.train.get_global_step(),
        optimizer=optimizer,
        learning_rate=params.learning_rate,
        learning_rate_decay_fn=twml.learning_rate_decay.get_learning_rate_decay_fn(params),
      )

  graph_output[output_label] = output
  graph_output["loss"] = loss
  graph_output["train_op"] = train_op
  return graph_output


def get_params(args=None):
  parser = get_arg_parser_light_ranking()
  if args is None:
    return parser.parse_args()
  else:
    return parser.parse_args(args)


def _main():
  opt = get_params()
  logging.info("parse is: ")
  logging.info(opt)

  feature_list = read_config(opt.feature_list).items()
  feature_config = get_feature_config_light_ranking(
    data_spec_path=opt.data_spec,
    feature_list_provided=feature_list,
    opt=opt,
    add_gbdt=opt.use_gbdt_features,
    run_light_ranking_group_metrics_in_bq=opt.run_light_ranking_group_metrics_in_bq,
  )
  feature_list_path = opt.feature_list

  # --------------------------------------------------------
  #               Create Trainer
  # --------------------------------------------------------
  trainer = DataRecordTrainer(
    name=opt.model_trainer_name,
    params=opt,
    build_graph_fn=build_graph,
    save_dir=opt.save_dir,
    run_config=None,
    feature_config=feature_config,
    metric_fn=get_metric_fn(opt.task_name, use_stratify_metrics=False),
  )
  if opt.directly_export_best:
    logging.info("Directly exporting the model without training")
  else:
    # ----------------------------------------------------
    #        Model Training & Evaluation
    # ----------------------------------------------------
    eval_input_fn = trainer.get_eval_input_fn(repeat=False, shuffle=False)
    train_input_fn = trainer.get_train_input_fn(shuffle=True)

    if opt.distributed or opt.num_workers is not None:
      learn = trainer.train_and_evaluate
    else:
      learn = trainer.learn
    logging.info("Training...")
    start = datetime.now()

    early_stop_metric = "rce_unweighted_" + opt.task_name
    learn(
      early_stop_minimize=False,
      early_stop_metric=early_stop_metric,
      early_stop_patience=opt.early_stop_patience,
      early_stop_tolerance=opt.early_stop_tolerance,
      eval_input_fn=eval_input_fn,
      train_input_fn=train_input_fn,
    )

    end = datetime.now()
    logging.info("Training time: " + str(end - start))

    logging.info("Exporting the models...")

  # --------------------------------------------------------
  #      Do the model exporting
  # --------------------------------------------------------
  start = datetime.now()
  if not opt.export_dir:
    opt.export_dir = os.path.join(opt.save_dir, "exported_models")

  raw_model_path = twml.contrib.export.export_fn.export_all_models(
    trainer=trainer,
    export_dir=opt.export_dir,
    parse_fn=feature_config.get_parse_fn(),
    serving_input_receiver_fn=feature_config.get_serving_input_receiver_fn(),
    export_output_fn=twml.export_output_fns.batch_prediction_continuous_output_fn,
  )
  export_model_dir = decode_str_or_unicode(raw_model_path)

  logging.info("Model export time: " + str(datetime.now() - start))
  logging.info("The saved model directory is: " + opt.save_dir)

  tf.logging.info("getting default continuous_feature_list")
  continuous_feature_list = get_feature_list_for_light_ranking(feature_list_path, opt.data_spec)
  continous_feature_list_save_path = os.path.join(opt.save_dir, "continuous_feature_list.json")
  twml.util.write_file(continous_feature_list_save_path, continuous_feature_list, encode="json")
  tf.logging.info(f"Finish writting files to {continous_feature_list_save_path}")

  if opt.run_light_ranking_group_metrics:
    # --------------------------------------------
    # Run Light Ranking Group Metrics
    # --------------------------------------------
    run_group_metrics_light_ranking(
      trainer=trainer,
      data_dir=os.path.join(opt.eval_data_dir, opt.eval_start_datetime),
      model_path=export_model_dir,
      parse_fn=feature_config.get_parse_fn(),
    )

  if opt.run_light_ranking_group_metrics_in_bq:
    # ----------------------------------------------------------------------------------------
    # Get Light/Heavy Ranker Predictions for Light Ranking Group Metrics in BigQuery
    # ----------------------------------------------------------------------------------------
    trainer_pred = DataRecordTrainer(
      name=opt.model_trainer_name,
      params=opt,
      build_graph_fn=partial(build_graph, run_light_ranking_group_metrics_in_bq=True),
      save_dir=opt.save_dir + "/tmp/",
      run_config=None,
      feature_config=feature_config,
      metric_fn=get_metric_fn(opt.task_name, use_stratify_metrics=False),
    )
    checkpoint_folder = os.path.join(opt.save_dir, "best_checkpoint")
    checkpoint = tf.train.latest_checkpoint(checkpoint_folder, latest_filename=None)
    tf.logging.info("\n\nPrediction from Checkpoint: {:}.\n\n".format(checkpoint))
    run_group_metrics_light_ranking_in_bq(
      trainer=trainer_pred, params=opt, checkpoint_path=checkpoint
    )

  tf.logging.info("Done Training & Prediction.")


if __name__ == "__main__":
  _main()
