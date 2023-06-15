"""
Training job for the heavy ranker of the push notification service.
"""
from datetime import datetime
import json
import os

import twml

from ..libs.metric_fn_utils import flip_disliked_labels, get_metric_fn
from ..libs.model_utils import read_config
from ..libs.warm_start_utils import get_feature_list_for_heavy_ranking, warm_start_checkpoint
from .features import get_feature_config
from .model_pools import ALL_MODELS
from .params import load_graph_params
from .run_args import get_training_arg_parser

import tensorflow.compat.v1 as tf
from tensorflow.compat.v1 import logging


def main() -> None:
  args, _ = get_training_arg_parser().parse_known_args()
  logging.info(f"Parsed args: {args}")

  params = load_graph_params(args)
  logging.info(f"Loaded graph params: {params}")

  param_file = os.path.join(args.save_dir, "params.json")
  logging.info(f"Saving graph params to: {param_file}")
  with tf.io.gfile.GFile(param_file, mode="w") as file:
    json.dump(params.json(), file, ensure_ascii=False, indent=4)

  logging.info(f"Get Feature Config: {args.feature_list}")
  feature_list = read_config(args.feature_list).items()
  feature_config = get_feature_config(
    data_spec_path=args.data_spec,
    params=params,
    feature_list_provided=feature_list,
  )
  feature_list_path = args.feature_list

  warm_start_from = args.warm_start_from
  if args.warm_start_base_dir:
    logging.info(f"Get warm started model from: {args.warm_start_base_dir}.")

    continuous_binary_feat_list_save_path = os.path.join(
      args.warm_start_base_dir, "continuous_binary_feat_list.json"
    )
    warm_start_folder = os.path.join(args.warm_start_base_dir, "best_checkpoint")
    job_name = os.path.basename(args.save_dir)
    ws_output_ckpt_folder = os.path.join(args.warm_start_base_dir, f"warm_start_for_{job_name}")
    if tf.io.gfile.exists(ws_output_ckpt_folder):
      tf.io.gfile.rmtree(ws_output_ckpt_folder)

    tf.io.gfile.mkdir(ws_output_ckpt_folder)

    warm_start_from = warm_start_checkpoint(
      warm_start_folder,
      continuous_binary_feat_list_save_path,
      feature_list_path,
      args.data_spec,
      ws_output_ckpt_folder,
    )
    logging.info(f"Created warm_start_from_ckpt {warm_start_from}.")

  logging.info("Build Trainer.")
  metric_fn = get_metric_fn("OONC_Engagement" if len(params.tasks) == 2 else "OONC", False)

  trainer = twml.trainers.DataRecordTrainer(
    name="magic_recs",
    params=args,
    build_graph_fn=lambda *args: ALL_MODELS[params.model.name](params=params)(*args),
    save_dir=args.save_dir,
    run_config=None,
    feature_config=feature_config,
    metric_fn=flip_disliked_labels(metric_fn),
    warm_start_from=warm_start_from,
  )

  logging.info("Build train and eval input functions.")
  train_input_fn = trainer.get_train_input_fn(shuffle=True)
  eval_input_fn = trainer.get_eval_input_fn(repeat=False, shuffle=False)

  learn = trainer.learn
  if args.distributed or args.num_workers is not None:
    learn = trainer.train_and_evaluate

  if not args.directly_export_best:
    logging.info("Starting training")
    start = datetime.now()
    learn(
      early_stop_minimize=False,
      early_stop_metric="pr_auc_unweighted_OONC",
      early_stop_patience=args.early_stop_patience,
      early_stop_tolerance=args.early_stop_tolerance,
      eval_input_fn=eval_input_fn,
      train_input_fn=train_input_fn,
    )
    logging.info(f"Total training time: {datetime.now() - start}")
  else:
    logging.info("Directly exporting the model")

  if not args.export_dir:
    args.export_dir = os.path.join(args.save_dir, "exported_models")

  logging.info(f"Exporting the model to {args.export_dir}.")
  start = datetime.now()
  twml.contrib.export.export_fn.export_all_models(
    trainer=trainer,
    export_dir=args.export_dir,
    parse_fn=feature_config.get_parse_fn(),
    serving_input_receiver_fn=feature_config.get_serving_input_receiver_fn(),
    export_output_fn=twml.export_output_fns.batch_prediction_continuous_output_fn,
  )

  logging.info(f"Total model export time: {datetime.now() - start}")
  logging.info(f"The MLP directory is: {args.save_dir}")

  continuous_binary_feat_list_save_path = os.path.join(
    args.save_dir, "continuous_binary_feat_list.json"
  )
  logging.info(
    f"Saving the list of continuous and binary features to {continuous_binary_feat_list_save_path}."
  )
  continuous_binary_feat_list = get_feature_list_for_heavy_ranking(
    feature_list_path, args.data_spec
  )
  twml.util.write_file(
    continuous_binary_feat_list_save_path, continuous_binary_feat_list, encode="json"
  )


if __name__ == "__main__":
  main()
  logging.info("Done.")
