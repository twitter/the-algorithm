from twml.trainers import DataRecordTrainer

from .features import FEATURE_LIST_DEFAULT_PATH


def get_training_arg_parser():
  parser = DataRecordTrainer.add_parser_arguments()

  parser.add_argument(
    "--feature_list",
    default=FEATURE_LIST_DEFAULT_PATH,
    type=str,
    help="Which features to use for training",
  )

  parser.add_argument(
    "--param_file",
    default=None,
    type=str,
    help="Path to JSON file containing the graph parameters. If None, model will load default parameters.",
  )

  parser.add_argument(
    "--directly_export_best",
    default=False,
    action="store_true",
    help="whether to directly_export best_checkpoint",
  )

  parser.add_argument(
    "--warm_start_from", default=None, type=str, help="model dir to warm start from"
  )

  parser.add_argument(
    "--warm_start_base_dir",
    default=None,
    type=str,
    help="latest ckpt in this folder will be used to ",
  )

  parser.add_argument(
    "--model_type",
    default=None,
    type=str,
    help="Which type of model to train.",
  )
  return parser


def get_eval_arg_parser():
  parser = get_training_arg_parser()
  parser.add_argument(
    "--eval_checkpoint",
    default=None,
    type=str,
    help="Which checkpoint to use for evaluation",
  )

  return parser
