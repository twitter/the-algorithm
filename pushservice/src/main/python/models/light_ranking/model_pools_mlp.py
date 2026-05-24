import warnings

from twml.contrib.layers import ZscoreNormalization

from ...libs.customized_full_sparse import FullSparse
from ...libs.get_feat_config import FEAT_CONFIG_DEFAULT_VAL as MISSING_VALUE_MARKER
from ...libs.model_utils import (
  _sparse_feature_fixup,
  adaptive_transformation,
  filter_nans_and_infs,
  get_dense_out,
  tensor_dropout,
)

import tensorflow.compat.v1 as tf
# checkstyle: noqa

def light_ranking_mlp_ngbdt(features, is_training, params, label=None):
  return deepnorm_light_ranking(
    features,
    is_training,
    params,
    label=label,
    decay=params.momentum,
    dense_emb_size=params.dense_embedding_size,
    base_activation=tf.keras.layers.LeakyReLU(),
    input_dropout_rate=params.dropout,
    use_gbdt=False,
  )


def deepnorm_light_ranking(
  features,
  is_training,
  params,
  label=None,
  decay=0.99999,
  dense_emb_size=128,
  base_activation=None,
  input_dropout_rate=None,
  input_dense_type="self_atten_dense",
  emb_dense_type="self_atten_dense",
  mlp_dense_type="self_atten_dense",
  use_gbdt=False,
):
  # --------------------------------------------------------
  #            Initial Parameter Checking
  # --------------------------------------------------------
  if base_activation is None:
    base_activation = tf.keras.layers.LeakyReLU()

  if label is not None:
    warnings.warn(
      "Label is unused in deepnorm_gbdt. Stop using this argument.",
      DeprecationWarning,
    )

  with tf.variable_scope("helper_layers"):
    full_sparse_layer = FullSparse(
      output_size=params.sparse_embedding_size,
      activation=base_activation,
      use_sparse_grads=is_training,
      use_binary_values=False,
      dtype=tf.float32,
    )
    input_normalizing_layer = ZscoreNormalization(decay=decay, name="input_normalizing_layer")

  # --------------------------------------------------------
  #            Feature Selection & Embedding
  # --------------------------------------------------------
  if use_gbdt:
    sparse_gbdt_features = _sparse_feature_fixup(features["gbdt_sparse"], params.input_size_bits)
    if input_dropout_rate is not None:
      sparse_gbdt_features = tensor_dropout(
        sparse_gbdt_features, input_dropout_rate, is_training, sparse_tensor=True
      )

    total_embed = full_sparse_layer(sparse_gbdt_features, use_binary_values=True)

    if (input_dropout_rate is not None) and is_training:
      total_embed = total_embed / (1 - input_dropout_rate)

  else:
    with tf.variable_scope("dense_branch"):
      dense_continuous_features = filter_nans_and_infs(features["continuous"])

      if params.use_missing_sub_branch:
        is_missing = tf.equal(dense_continuous_features, MISSING_VALUE_MARKER)
        continuous_features_filled = tf.where(
          is_missing,
          tf.zeros_like(dense_continuous_features),
          dense_continuous_features,
        )
        normalized_features = input_normalizing_layer(
          continuous_features_filled, is_training, tf.math.logical_not(is_missing)
        )

        with tf.variable_scope("missing_sub_branch"):
          missing_feature_embed = get_dense_out(
            tf.cast(is_missing, tf.float32),
            dense_emb_size,
            activation=base_activation,
            dense_type=input_dense_type,
          )

      else:
        continuous_features_filled = dense_continuous_features
        normalized_features = input_normalizing_layer(continuous_features_filled, is_training)

      with tf.variable_scope("continuous_sub_branch"):
        normalized_features = adaptive_transformation(
          normalized_features, is_training, func_type="tiny"
        )

        if input_dropout_rate is not None:
          normalized_features = tensor_dropout(
            normalized_features,
            input_dropout_rate,
            is_training,
            sparse_tensor=False,
          )
        filled_feature_embed = get_dense_out(
          normalized_features,
          dense_emb_size,
          activation=base_activation,
          dense_type=input_dense_type,
        )

      if params.use_missing_sub_branch:
        dense_embed = tf.concat(
          [filled_feature_embed, missing_feature_embed], axis=1, name="merge_dense_emb"
        )
      else:
        dense_embed = filled_feature_embed

    with tf.variable_scope("sparse_branch"):
      sparse_discrete_features = _sparse_feature_fixup(
        features["sparse_no_continuous"], params.input_size_bits
      )
      if input_dropout_rate is not None:
        sparse_discrete_features = tensor_dropout(
          sparse_discrete_features, input_dropout_rate, is_training, sparse_tensor=True
        )

      discrete_features_embed = full_sparse_layer(sparse_discrete_features, use_binary_values=True)

      if (input_dropout_rate is not None) and is_training:
        discrete_features_embed = discrete_features_embed / (1 - input_dropout_rate)

    total_embed = tf.concat(
      [dense_embed, discrete_features_embed],
      axis=1,
      name="total_embed",
    )

  total_embed = tf.layers.batch_normalization(
    total_embed,
    training=is_training,
    renorm_momentum=decay,
    momentum=decay,
    renorm=is_training,
    trainable=True,
  )

  # --------------------------------------------------------
  #                MLP Layers
  # --------------------------------------------------------
  with tf.variable_scope("MLP_branch"):

    assert params.num_mlp_layers >= 0
    embed_list = [total_embed] + [None for _ in range(params.num_mlp_layers)]
    dense_types = [emb_dense_type] + [mlp_dense_type for _ in range(params.num_mlp_layers - 1)]

    for xl in range(1, params.num_mlp_layers + 1):
      neurons = params.mlp_neuron_scale ** (params.num_mlp_layers + 1 - xl)
      embed_list[xl] = get_dense_out(
        embed_list[xl - 1], neurons, activation=base_activation, dense_type=dense_types[xl - 1]
      )

    if params.task_name in ["Sent", "HeavyRankPosition", "HeavyRankProbability"]:
      logits = get_dense_out(embed_list[-1], 1, activation=None, dense_type=mlp_dense_type)

    else:
      raise ValueError("Invalid Task Name !")

  output_dict = {"output": logits}
  return output_dict
