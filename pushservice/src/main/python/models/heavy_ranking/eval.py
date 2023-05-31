"""
Evaluation job for the heavy ranker of the push notification service.
"""

from datetime import datetime

import twml

from ..libs.metric_fn_utils import get_metric_fn
from ..libs.model_utils import read_config
from .features import get_feature_config
from .model_pools import ALL_MODELS
from .params import load_graph_params
from .run_args import get_eval_arg_parser

from tensorflow.compat.v1 import logging


def main():
  args, _ = get_eval_arg_parser().parse_known_args()
  logging.info(f"Parsed args: {args}")

  params = load_graph_params(args)
  logging.info(f"Loaded graph params: {params}")

  logging.info(f"Get Feature Config: {args.feature_list}")
  feature_list = read_config(args.feature_list).items()
  feature_config = get_feature_config(
    data_spec_path=args.data_spec,
    params=params,
    feature_list_provided=feature_list,
  )

  logging.info("Build DataRecordTrainer.")
  metric_fn = get_metric_fn("OONC_Engagement" if len(params.tasks) == 2 else "OONC", False)

  trainer = twml.trainers.DataRecordTrainer(
    name="magic_recs",
    params=args,
    build_graph_fn=lambda *args: ALL_MODELS[params.model.name](params=params)(*args),
    save_dir=args.save_dir,
    run_config=None,
    feature_config=feature_config,
    metric_fn=metric_fn,
  )

  logging.info("Run the evaluation.")
  start = datetime.now()
  trainer._estimator.evaluate(
    input_fn=trainer.get_eval_input_fn(repeat=False, shuffle=False),
    steps=None if (args.eval_steps is not None and args.eval_steps < 0) else args.eval_steps,
    checkpoint_path=args.eval_checkpoint,
  )
  logging.info(f"Evaluating time: {datetime.now() - start}.")


if __name__ == "__main__":
  main()
  logging.info("Job done.")
