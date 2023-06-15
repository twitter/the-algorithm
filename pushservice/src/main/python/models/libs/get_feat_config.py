import os

from twitter.deepbird.projects.magic_recs.libs.metric_fn_utils import USER_AGE_FEATURE_NAME
from twitter.deepbird.projects.magic_recs.libs.model_utils import read_config
from twml.contrib import feature_config as contrib_feature_config


# checkstyle: noqa

FEAT_CONFIG_DEFAULT_VAL = -1.23456789

DEFAULT_INPUT_SIZE_BITS = 18

DEFAULT_FEATURE_LIST_PATH = "./feature_list_default.yaml"
FEATURE_LIST_DEFAULT_PATH = os.path.join(
  os.path.dirname(os.path.realpath(__file__)), DEFAULT_FEATURE_LIST_PATH
)

DEFAULT_FEATURE_LIST_LIGHT_RANKING_PATH = "./feature_list_light_ranking.yaml"
FEATURE_LIST_DEFAULT_LIGHT_RANKING_PATH = os.path.join(
  os.path.dirname(os.path.realpath(__file__)), DEFAULT_FEATURE_LIST_LIGHT_RANKING_PATH
)

FEATURE_LIST_DEFAULT = read_config(FEATURE_LIST_DEFAULT_PATH).items()
FEATURE_LIST_LIGHT_RANKING_DEFAULT = read_config(FEATURE_LIST_DEFAULT_LIGHT_RANKING_PATH).items()


LABELS = ["label"]
LABELS_MTL = {"OONC": ["label"], "OONC_Engagement": ["label", "label.engagement"]}
LABELS_LR = {
  "Sent": ["label.sent"],
  "HeavyRankPosition": ["meta.ranking.is_top3"],
  "HeavyRankProbability": ["meta.ranking.weighted_oonc_model_score"],
}


def _get_new_feature_config_base(
  data_spec_path,
  labels,
  add_sparse_continous=True,
  add_gbdt=True,
  add_user_id=False,
  add_timestamp=False,
  add_user_age=False,
  feature_list_provided=[],
  opt=None,
  run_light_ranking_group_metrics_in_bq=False,
):
  """
  Getter of the feature config based on specification.

  Args:
    data_spec_path: A string indicating the path of the data_spec.json file, which could be
      either a local path or a hdfs path.
    labels: A list of strings indicating the name of the label in the data spec.
    add_sparse_continous: A bool indicating if sparse_continuous feature needs to be included.
    add_gbdt: A bool indicating if gbdt feature needs to be included.
    add_user_id: A bool indicating if user_id feature needs to be included.
    add_timestamp: A bool indicating if timestamp feature needs to be included. This will be useful
      for sequential models and meta learning models.
    add_user_age: A bool indicating if the user age feature needs to be included.
    feature_list_provided: A list of features thats need to be included. If not specified, will use
      FEATURE_LIST_DEFAULT by default.
    opt: A namespace of arguments indicating the hyparameters.
    run_light_ranking_group_metrics_in_bq: A bool indicating if heavy ranker score info needs to be included to compute group metrics in BigQuery.

  Returns:
    A twml feature config object.
  """

  input_size_bits = DEFAULT_INPUT_SIZE_BITS if opt is None else opt.input_size_bits

  feature_list = feature_list_provided if feature_list_provided != [] else FEATURE_LIST_DEFAULT
  a_string_feat_list = [f[0] for f in feature_list if f[1] != "S"]

  builder = contrib_feature_config.FeatureConfigBuilder(data_spec_path=data_spec_path)

  builder = builder.extract_feature_group(
    feature_regexes=a_string_feat_list,
    group_name="continuous",
    default_value=FEAT_CONFIG_DEFAULT_VAL,
    type_filter=["CONTINUOUS"],
  )

  builder = builder.extract_features_as_hashed_sparse(
    feature_regexes=a_string_feat_list,
    output_tensor_name="sparse_no_continuous",
    hash_space_size_bits=input_size_bits,
    type_filter=["BINARY", "DISCRETE", "STRING", "SPARSE_BINARY"],
  )

  if add_gbdt:
    builder = builder.extract_features_as_hashed_sparse(
      feature_regexes=["ads\..*"],
      output_tensor_name="gbdt_sparse",
      hash_space_size_bits=input_size_bits,
    )

  if add_sparse_continous:
    s_string_feat_list = [f[0] for f in feature_list if f[1] == "S"]

    builder = builder.extract_features_as_hashed_sparse(
      feature_regexes=s_string_feat_list,
      output_tensor_name="sparse_continuous",
      hash_space_size_bits=input_size_bits,
      type_filter=["SPARSE_CONTINUOUS"],
    )

  if add_user_id:
    builder = builder.extract_feature("meta.user_id")
  if add_timestamp:
    builder = builder.extract_feature("meta.timestamp")
  if add_user_age:
    builder = builder.extract_feature(USER_AGE_FEATURE_NAME)

  if run_light_ranking_group_metrics_in_bq:
    builder = builder.extract_feature("meta.trace_id")
    builder = builder.extract_feature("meta.ranking.weighted_oonc_model_score")

  builder = builder.add_labels(labels).define_weight("meta.weight")

  return builder.build()


def get_feature_config_with_sparse_continuous(
  data_spec_path,
  feature_list_provided=[],
  opt=None,
  add_user_id=False,
  add_timestamp=False,
  add_user_age=False,
):
  task_name = opt.task_name if getattr(opt, "task_name", None) is not None else "OONC"
  if task_name not in LABELS_MTL:
    raise ValueError("Invalid Task Name !")

  return _get_new_feature_config_base(
    data_spec_path=data_spec_path,
    labels=LABELS_MTL[task_name],
    add_sparse_continous=True,
    add_user_id=add_user_id,
    add_timestamp=add_timestamp,
    add_user_age=add_user_age,
    feature_list_provided=feature_list_provided,
    opt=opt,
  )


def get_feature_config_light_ranking(
  data_spec_path,
  feature_list_provided=[],
  opt=None,
  add_user_id=True,
  add_timestamp=False,
  add_user_age=False,
  add_gbdt=False,
  run_light_ranking_group_metrics_in_bq=False,
):
  task_name = opt.task_name if getattr(opt, "task_name", None) is not None else "HeavyRankPosition"
  if task_name not in LABELS_LR:
    raise ValueError("Invalid Task Name !")
  if not feature_list_provided:
    feature_list_provided = FEATURE_LIST_LIGHT_RANKING_DEFAULT

  return _get_new_feature_config_base(
    data_spec_path=data_spec_path,
    labels=LABELS_LR[task_name],
    add_sparse_continous=False,
    add_gbdt=add_gbdt,
    add_user_id=add_user_id,
    add_timestamp=add_timestamp,
    add_user_age=add_user_age,
    feature_list_provided=feature_list_provided,
    opt=opt,
    run_light_ranking_group_metrics_in_bq=run_light_ranking_group_metrics_in_bq,
  )
