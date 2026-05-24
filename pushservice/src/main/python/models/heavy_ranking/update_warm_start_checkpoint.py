"""
Model for modifying the checkpoints of the magic recs cnn Model with addition, deletion, and reordering
of continuous and binary features.
"""

import os

from twitter.deepbird.projects.magic_recs.libs.get_feat_config import FEATURE_LIST_DEFAULT_PATH
from twitter.deepbird.projects.magic_recs.libs.warm_start_utils_v11 import (
  get_feature_list_for_heavy_ranking,
  mkdirp,
  rename_dir,
  rmdir,
  warm_start_checkpoint,
)
import twml
from twml.trainers import DataRecordTrainer

import tensorflow.compat.v1 as tf
from tensorflow.compat.v1 import logging


def get_arg_parser():
  parser = DataRecordTrainer.add_parser_arguments()
  parser.add_argument(
    "--model_type",
    default="deepnorm_gbdt_inputdrop2_rescale",
    type=str,
    help="specify the model type to use.",
  )

  parser.add_argument(
    "--model_trainer_name",
    default="None",
    type=str,
    help="deprecated, added here just for api compatibility.",
  )

  parser.add_argument(
    "--warm_start_base_dir",
    default="none",
    type=str,
    help="latest ckpt in this folder will be used.",
  )

  parser.add_argument(
    "--output_checkpoint_dir",
    default="none",
    type=str,
    help="Output folder for warm started ckpt. If none, it will move warm_start_base_dir to backup, and overwrite it",
  )

  parser.add_argument(
    "--feature_list",
    default="none",
    type=str,
    help="Which features to use for training",
  )

  parser.add_argument(
    "--old_feature_list",
    default="none",
    type=str,
    help="Which features to use for training",
  )

  return parser


def get_params(args=None):
  parser = get_arg_parser()
  if args is None:
    return parser.parse_args()
  else:
    return parser.parse_args(args)


def _main():
  opt = get_params()
  logging.info("parse is: ")
  logging.info(opt)

  if opt.feature_list == "none":
    feature_list_path = FEATURE_LIST_DEFAULT_PATH
  else:
    feature_list_path = opt.feature_list

  if opt.warm_start_base_dir != "none" and tf.io.gfile.exists(opt.warm_start_base_dir):
    if opt.output_checkpoint_dir == "none" or opt.output_checkpoint_dir == opt.warm_start_base_dir:
      _warm_start_base_dir = os.path.normpath(opt.warm_start_base_dir) + "_backup_warm_start"
      _output_folder_dir = opt.warm_start_base_dir

      rename_dir(opt.warm_start_base_dir, _warm_start_base_dir)
      tf.logging.info(f"moved {opt.warm_start_base_dir} to {_warm_start_base_dir}")
    else:
      _warm_start_base_dir = opt.warm_start_base_dir
      _output_folder_dir = opt.output_checkpoint_dir

    continuous_binary_feat_list_save_path = os.path.join(
      _warm_start_base_dir, "continuous_binary_feat_list.json"
    )

    if opt.old_feature_list != "none":
      tf.logging.info("getting old continuous_binary_feat_list")
      continuous_binary_feat_list = get_feature_list_for_heavy_ranking(
        opt.old_feature_list, opt.data_spec
      )
      rmdir(continuous_binary_feat_list_save_path)
      twml.util.write_file(
        continuous_binary_feat_list_save_path, continuous_binary_feat_list, encode="json"
      )
      tf.logging.info(f"Finish writting files to {continuous_binary_feat_list_save_path}")

    warm_start_folder = os.path.join(_warm_start_base_dir, "best_checkpoint")
    if not tf.io.gfile.exists(warm_start_folder):
      warm_start_folder = _warm_start_base_dir

    rmdir(_output_folder_dir)
    mkdirp(_output_folder_dir)

    new_ckpt = warm_start_checkpoint(
      warm_start_folder,
      continuous_binary_feat_list_save_path,
      feature_list_path,
      opt.data_spec,
      _output_folder_dir,
      opt.model_type,
    )
    logging.info(f"Created new ckpt {new_ckpt} from {warm_start_folder}")

    tf.logging.info("getting new continuous_binary_feat_list")
    new_continuous_binary_feat_list_save_path = os.path.join(
      _output_folder_dir, "continuous_binary_feat_list.json"
    )
    continuous_binary_feat_list = get_feature_list_for_heavy_ranking(
      feature_list_path, opt.data_spec
    )
    rmdir(new_continuous_binary_feat_list_save_path)
    twml.util.write_file(
      new_continuous_binary_feat_list_save_path, continuous_binary_feat_list, encode="json"
    )
    tf.logging.info(f"Finish writting files to {new_continuous_binary_feat_list_save_path}")


if __name__ == "__main__":
  _main()
