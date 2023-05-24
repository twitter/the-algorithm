from datetime import datetime
from functools import partial
import os

from ..libs.group_metrics import (
  run_group_metrics_light_ranking,
  run_group_metrics_light_ranking_in_bq,
)
from ..libs.metric_fn_utils import get_metric_fn
from ..libs.model_args import get_arg_parser_light_ranking
from ..libs.model_utils import read_config
from .deep_norm import build_graph, DataRecordTrainer, get_config_func, logging


# checkstyle: noqa

if __name__ == "__main__":
  parser = get_arg_parser_light_ranking()
  parser.add_argument(
    "--eval_checkpoint",
    default=None,
    type=str,
    help="Which checkpoint to use for evaluation",
  )
  parser.add_argument(
    "--saved_model_path",
    default=None,
    type=str,
    help="Path to saved model for evaluation",
  )
  parser.add_argument(
    "--run_binary_metrics",
    default=False,
    action="store_true",
    help="Whether to compute the basic binary metrics for Light Ranking.",
  )

  opt = parser.parse_args()
  logging.info("parse is: ")
  logging.info(opt)

  feature_list = read_config(opt.feature_list).items()
  feature_config = get_config_func(opt.feat_config_type)(
    data_spec_path=opt.data_spec,
    feature_list_provided=feature_list,
    opt=opt,
    add_gbdt=opt.use_gbdt_features,
    run_light_ranking_group_metrics_in_bq=opt.run_light_ranking_group_metrics_in_bq,
  )

  # -----------------------------------------------
  #        Create Trainer
  # -----------------------------------------------
  trainer = DataRecordTrainer(
    name=opt.model_trainer_name,
    params=opt,
    build_graph_fn=partial(build_graph, run_light_ranking_group_metrics_in_bq=True),
    save_dir=opt.save_dir,
    run_config=None,
    feature_config=feature_config,
    metric_fn=get_metric_fn(opt.task_name, use_stratify_metrics=False),
  )

  # -----------------------------------------------
  #         Model Evaluation
  # -----------------------------------------------
  logging.info("Evaluating...")
  start = datetime.now()

  if opt.run_binary_metrics:
    eval_input_fn = trainer.get_eval_input_fn(repeat=False, shuffle=False)
    eval_steps = None if (opt.eval_steps is not None and opt.eval_steps < 0) else opt.eval_steps
    trainer.estimator.evaluate(eval_input_fn, steps=eval_steps, checkpoint_path=opt.eval_checkpoint)

  if opt.run_light_ranking_group_metrics_in_bq:
    run_group_metrics_light_ranking_in_bq(
      trainer=trainer, params=opt, checkpoint_path=opt.eval_checkpoint
    )

  if opt.run_light_ranking_group_metrics:
    run_group_metrics_light_ranking(
      trainer=trainer,
      data_dir=os.path.join(opt.eval_data_dir, opt.eval_start_datetime),
      model_path=opt.saved_model_path,
      parse_fn=feature_config.get_parse_fn(),
    )

  end = datetime.now()
  logging.info("Evaluating time: " + str(end - start))
