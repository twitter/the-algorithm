import os
from typing import Dict

from twitter.deepbird.projects.magic_recs.libs.model_utils import filter_nans_and_infs
import twml
from twml.layers import full_sparse, sparse_max_norm

from .params import FeaturesParams, GraphParams, SparseFeaturesParams

import tensorflow as tf
from tensorflow import Tensor
import tensorflow.compat.v1 as tf1


FEAT_CONFIG_DEFAULT_VAL = 0
DEFAULT_FEATURE_LIST_PATH = "./feature_list_default.yaml"
FEATURE_LIST_DEFAULT_PATH = os.path.join(
  os.path.dirname(os.path.realpath(__file__)), DEFAULT_FEATURE_LIST_PATH
)


def get_feature_config(data_spec_path=None, feature_list_provided=[], params: GraphParams = None):

  a_string_feat_list = [feat for feat, feat_type in feature_list_provided if feat_type != "S"]

  builder = twml.contrib.feature_config.FeatureConfigBuilder(
    data_spec_path=data_spec_path, debug=False
  )

  builder = builder.extract_feature_group(
    feature_regexes=a_string_feat_list,
    group_name="continuous_features",
    default_value=FEAT_CONFIG_DEFAULT_VAL,
    type_filter=["CONTINUOUS"],
  )

  builder = builder.extract_feature_group(
    feature_regexes=a_string_feat_list,
    group_name="binary_features",
    type_filter=["BINARY"],
  )

  if params.model.features.sparse_features:
    builder = builder.extract_features_as_hashed_sparse(
      feature_regexes=a_string_feat_list,
      hash_space_size_bits=params.model.features.sparse_features.bits,
      type_filter=["DISCRETE", "STRING", "SPARSE_BINARY"],
      output_tensor_name="sparse_not_continuous",
    )

    builder = builder.extract_features_as_hashed_sparse(
      feature_regexes=[feat for feat, feat_type in feature_list_provided if feat_type == "S"],
      hash_space_size_bits=params.model.features.sparse_features.bits,
      type_filter=["SPARSE_CONTINUOUS"],
      output_tensor_name="sparse_continuous",
    )

  builder = builder.add_labels([task.label for task in params.tasks] + ["label.ntabDislike"])

  if params.weight:
    builder = builder.define_weight(params.weight)

  return builder.build()


def dense_features(features: Dict[str, Tensor], training: bool) -> Tensor:
  """
  Performs feature transformations on the raw dense features (continuous and binary).
  """
  with tf.name_scope("dense_features"):
    x = filter_nans_and_infs(features["continuous_features"])

    x = tf.sign(x) * tf.math.log(tf.abs(x) + 1)
    x = tf1.layers.batch_normalization(
      x, momentum=0.9999, training=training, renorm=training, axis=1
    )
    x = tf.clip_by_value(x, -5, 5)

    transformed_continous_features = tf.where(tf.math.is_nan(x), tf.zeros_like(x), x)

    binary_features = filter_nans_and_infs(features["binary_features"])
    binary_features = tf.dtypes.cast(binary_features, tf.float32)

    output = tf.concat([transformed_continous_features, binary_features], axis=1)

  return output


def sparse_features(
  features: Dict[str, Tensor], training: bool, params: SparseFeaturesParams
) -> Tensor:
  """
  Performs feature transformations on the raw sparse features.
  """

  with tf.name_scope("sparse_features"):
    with tf.name_scope("sparse_not_continuous"):
      sparse_not_continuous = full_sparse(
        inputs=features["sparse_not_continuous"],
        output_size=params.embedding_size,
        use_sparse_grads=training,
        use_binary_values=False,
      )

    with tf.name_scope("sparse_continuous"):
      shape_enforced_input = twml.util.limit_sparse_tensor_size(
        sparse_tf=features["sparse_continuous"], input_size_bits=params.bits, mask_indices=False
      )

      normalized_continuous_sparse = sparse_max_norm(
        inputs=shape_enforced_input, is_training=training
      )

      sparse_continuous = full_sparse(
        inputs=normalized_continuous_sparse,
        output_size=params.embedding_size,
        use_sparse_grads=training,
        use_binary_values=False,
      )

    output = tf.concat([sparse_not_continuous, sparse_continuous], axis=1)

  return output


def get_features(features: Dict[str, Tensor], training: bool, params: FeaturesParams) -> Tensor:
  """
  Performs feature transformations on the dense and sparse features and combine the resulting
  tensors into a single one.
  """
  with tf.name_scope("features"):
    x = dense_features(features, training)
    tf1.logging.info(f"Dense features: {x.shape}")

    if params.sparse_features:
      x = tf.concat([x, sparse_features(features, training, params.sparse_features)], axis=1)

  return x
