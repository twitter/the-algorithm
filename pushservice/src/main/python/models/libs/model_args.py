from twml.trainers import DataRecordTrainer


# checkstyle: noqa


def get_arg_parser():
  parser = DataRecordTrainer.add_parser_arguments()

  parser.add_argument(
    "--input_size_bits",
    type=int,
    default=18,
    help="number of bits allocated to the input size",
  )
  parser.add_argument(
    "--model_trainer_name",
    default="magic_recs_mlp_calibration_MTL_OONC_Engagement",
    type=str,
    help="specify the model trainer name.",
  )

  parser.add_argument(
    "--model_type",
    default="deepnorm_gbdt_inputdrop2_rescale",
    type=str,
    help="specify the model type to use.",
  )
  parser.add_argument(
    "--feat_config_type",
    default="get_feature_config_with_sparse_continuous",
    type=str,
    help="specify the feature configure function to use.",
  )

  parser.add_argument(
    "--directly_export_best",
    default=False,
    action="store_true",
    help="whether to directly_export best_checkpoint",
  )

  parser.add_argument(
    "--warm_start_base_dir",
    default="none",
    type=str,
    help="latest ckpt in this folder will be used to ",
  )

  parser.add_argument(
    "--feature_list",
    default="none",
    type=str,
    help="Which features to use for training",
  )
  parser.add_argument(
    "--warm_start_from", default=None, type=str, help="model dir to warm start from"
  )

  parser.add_argument(
    "--momentum", default=0.99999, type=float, help="Momentum term for batch normalization"
  )
  parser.add_argument(
    "--dropout",
    default=0.2,
    type=float,
    help="input_dropout_rate to rescale output by (1 - input_dropout_rate)",
  )
  parser.add_argument(
    "--out_layer_1_size", default=256, type=int, help="Size of MLP_branch layer 1"
  )
  parser.add_argument(
    "--out_layer_2_size", default=128, type=int, help="Size of MLP_branch layer 2"
  )
  parser.add_argument("--out_layer_3_size", default=64, type=int, help="Size of MLP_branch layer 3")
  parser.add_argument(
    "--sparse_embedding_size", default=50, type=int, help="Dimensionality of sparse embedding layer"
  )
  parser.add_argument(
    "--dense_embedding_size", default=128, type=int, help="Dimensionality of dense embedding layer"
  )

  parser.add_argument(
    "--use_uam_label",
    default=False,
    type=str,
    help="Whether to use uam_label or not",
  )

  parser.add_argument(
    "--task_name",
    default="OONC_Engagement",
    type=str,
    help="specify the task name to use: OONC or OONC_Engagement.",
  )
  parser.add_argument(
    "--init_weight",
    default=0.9,
    type=float,
    help="Initial OONC Task Weight MTL: OONC+Engagement.",
  )
  parser.add_argument(
    "--use_engagement_weight",
    default=False,
    action="store_true",
    help="whether to use engagement weight for base model.",
  )
  parser.add_argument(
    "--mtl_num_extra_layers",
    type=int,
    default=1,
    help="Number of Hidden Layers for each TaskBranch.",
  )
  parser.add_argument(
    "--mtl_neuron_scale", type=int, default=4, help="Scaling Factor of Neurons in MTL Extra Layers."
  )
  parser.add_argument(
    "--use_oonc_score",
    default=False,
    action="store_true",
    help="whether to use oonc score only or combined score.",
  )
  parser.add_argument(
    "--use_stratified_metrics",
    default=False,
    action="store_true",
    help="Use stratified metrics: Break out new-user metrics.",
  )
  parser.add_argument(
    "--run_group_metrics",
    default=False,
    action="store_true",
    help="Will run evaluation metrics grouped by user.",
  )
  parser.add_argument(
    "--use_full_scope",
    default=False,
    action="store_true",
    help="Will add extra scope and naming to graph.",
  )
  parser.add_argument(
    "--trainable_regexes",
    default=None,
    nargs="*",
    help="The union of variables specified by the list of regexes will be considered trainable.",
  )
  parser.add_argument(
    "--fine_tuning.ckpt_to_initialize_from",
    dest="fine_tuning_ckpt_to_initialize_from",
    type=str,
    default=None,
    help="Checkpoint path from which to warm start. Indicates the pre-trained model.",
  )
  parser.add_argument(
    "--fine_tuning.warm_start_scope_regex",
    dest="fine_tuning_warm_start_scope_regex",
    type=str,
    default=None,
    help="All variables matching this will be restored.",
  )

  return parser


def get_params(args=None):
  parser = get_arg_parser()
  if args is None:
    return parser.parse_args()
  else:
    return parser.parse_args(args)


def get_arg_parser_light_ranking():
  parser = get_arg_parser()

  parser.add_argument(
    "--use_record_weight",
    default=False,
    action="store_true",
    help="whether to use record weight for base model.",
  )
  parser.add_argument(
    "--min_record_weight", default=0.0, type=float, help="Minimum record weight to use."
  )
  parser.add_argument(
    "--smooth_weight", default=0.0, type=float, help="Factor to smooth Rank Position Weight."
  )

  parser.add_argument(
    "--num_mlp_layers", type=int, default=3, help="Number of Hidden Layers for MLP model."
  )
  parser.add_argument(
    "--mlp_neuron_scale", type=int, default=4, help="Scaling Factor of Neurons in MLP Layers."
  )
  parser.add_argument(
    "--run_light_ranking_group_metrics",
    default=False,
    action="store_true",
    help="Will run evaluation metrics grouped by user for Light Ranking.",
  )
  parser.add_argument(
    "--use_missing_sub_branch",
    default=False,
    action="store_true",
    help="Whether to use missing value sub-branch for Light Ranking.",
  )
  parser.add_argument(
    "--use_gbdt_features",
    default=False,
    action="store_true",
    help="Whether to use GBDT features for Light Ranking.",
  )
  parser.add_argument(
    "--run_light_ranking_group_metrics_in_bq",
    default=False,
    action="store_true",
    help="Whether to get_predictions for Light Ranking to compute group metrics in BigQuery.",
  )
  parser.add_argument(
    "--pred_file_path",
    default=None,
    type=str,
    help="path",
  )
  parser.add_argument(
    "--pred_file_name",
    default=None,
    type=str,
    help="path",
  )
  return parser
