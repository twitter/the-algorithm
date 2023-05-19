import sys

import twml

from .initializer import customized_glorot_uniform

import tensorflow.compat.v1 as tf
import yaml


# checkstyle: noqa


def read_config(whitelist_yaml_file):
  with tf.gfile.FastGFile(whitelist_yaml_file) as f:
    try:
      return yaml.safe_load(f)
    except yaml.YAMLError as exc:
      print(exc)
      sys.exit(1)


def _sparse_feature_fixup(features, input_size_bits):
  """Rebuild a sparse tensor feature so that its dense shape attribute is present.

  Arguments:
    features (SparseTensor): Sparse feature tensor of shape ``(B, sparse_feature_dim)``.
    input_size_bits (int): Number of columns in ``log2`` scale. Must be positive.

  Returns:
    SparseTensor: Rebuilt and non-faulty version of `features`."""
  sparse_feature_dim = tf.constant(2**input_size_bits, dtype=tf.int64)
  sparse_shape = tf.stack([features.dense_shape[0], sparse_feature_dim])
  sparse_tf = tf.SparseTensor(features.indices, features.values, sparse_shape)
  return sparse_tf


def self_atten_dense(input, out_dim, activation=None, use_bias=True, name=None):
  def safe_concat(base, suffix):
    """Concats variables name components if base is given."""
    if not base:
      return base
    return f"{base}:{suffix}"

  input_dim = input.shape.as_list()[1]

  sigmoid_out = twml.layers.FullDense(
    input_dim, dtype=tf.float32, activation=tf.nn.sigmoid, name=safe_concat(name, "sigmoid_out")
  )(input)
  atten_input = sigmoid_out * input
  mlp_out = twml.layers.FullDense(
    out_dim,
    dtype=tf.float32,
    activation=activation,
    use_bias=use_bias,
    name=safe_concat(name, "mlp_out"),
  )(atten_input)
  return mlp_out


def get_dense_out(input, out_dim, activation, dense_type):
  if dense_type == "full_dense":
    out = twml.layers.FullDense(out_dim, dtype=tf.float32, activation=activation)(input)
  elif dense_type == "self_atten_dense":
    out = self_atten_dense(input, out_dim, activation=activation)
  return out


def get_input_trans_func(bn_normalized_dense, is_training):
  gw_normalized_dense = tf.expand_dims(bn_normalized_dense, -1)
  group_num = bn_normalized_dense.shape.as_list()[1]

  gw_normalized_dense = GroupWiseTrans(group_num, 1, 8, name="groupwise_1", activation=tf.tanh)(
    gw_normalized_dense
  )
  gw_normalized_dense = GroupWiseTrans(group_num, 8, 4, name="groupwise_2", activation=tf.tanh)(
    gw_normalized_dense
  )
  gw_normalized_dense = GroupWiseTrans(group_num, 4, 1, name="groupwise_3", activation=tf.tanh)(
    gw_normalized_dense
  )

  gw_normalized_dense = tf.squeeze(gw_normalized_dense, [-1])

  bn_gw_normalized_dense = tf.layers.batch_normalization(
    gw_normalized_dense,
    training=is_training,
    renorm_momentum=0.9999,
    momentum=0.9999,
    renorm=is_training,
    trainable=True,
  )

  return bn_gw_normalized_dense


def tensor_dropout(
  input_tensor,
  rate,
  is_training,
  sparse_tensor=None,
):
  """
  Implements dropout layer for both dense and sparse input_tensor

  Arguments:
    input_tensor:
      B x D dense tensor, or a sparse tensor
    rate (float32):
      dropout rate
    is_training (bool):
      training stage or not.
    sparse_tensor (bool):
      whether the input_tensor is sparse tensor or not. Default to be None, this value has to be passed explicitly.
    rescale_sparse_dropout (bool):
      Do we need to do rescaling or not.
  Returns:
    tensor dropped out"""
  if sparse_tensor == True:
    if is_training:
      with tf.variable_scope("sparse_dropout"):
        values = input_tensor.values
        keep_mask = tf.keras.backend.random_binomial(
          tf.shape(values), p=1 - rate, dtype=tf.float32, seed=None
        )
        keep_mask.set_shape([None])
        keep_mask = tf.cast(keep_mask, tf.bool)

        keep_indices = tf.boolean_mask(input_tensor.indices, keep_mask, axis=0)
        keep_values = tf.boolean_mask(values, keep_mask, axis=0)

        dropped_tensor = tf.SparseTensor(keep_indices, keep_values, input_tensor.dense_shape)
        return dropped_tensor
    else:
      return input_tensor
  elif sparse_tensor == False:
    return tf.layers.dropout(input_tensor, rate=rate, training=is_training)


def adaptive_transformation(bn_normalized_dense, is_training, func_type="default"):
  assert func_type in [
    "default",
    "tiny",
  ], f"fun_type can only be one of default and tiny, but get {func_type}"

  gw_normalized_dense = tf.expand_dims(bn_normalized_dense, -1)
  group_num = bn_normalized_dense.shape.as_list()[1]

  if func_type == "default":
    gw_normalized_dense = FastGroupWiseTrans(
      group_num, 1, 8, name="groupwise_1", activation=tf.tanh, init_multiplier=8
    )(gw_normalized_dense)

    gw_normalized_dense = FastGroupWiseTrans(
      group_num, 8, 4, name="groupwise_2", activation=tf.tanh, init_multiplier=8
    )(gw_normalized_dense)

    gw_normalized_dense = FastGroupWiseTrans(
      group_num, 4, 1, name="groupwise_3", activation=tf.tanh, init_multiplier=8
    )(gw_normalized_dense)
  elif func_type == "tiny":
    gw_normalized_dense = FastGroupWiseTrans(
      group_num, 1, 2, name="groupwise_1", activation=tf.tanh, init_multiplier=8
    )(gw_normalized_dense)

    gw_normalized_dense = FastGroupWiseTrans(
      group_num, 2, 1, name="groupwise_2", activation=tf.tanh, init_multiplier=8
    )(gw_normalized_dense)

    gw_normalized_dense = FastGroupWiseTrans(
      group_num, 1, 1, name="groupwise_3", activation=tf.tanh, init_multiplier=8
    )(gw_normalized_dense)

  gw_normalized_dense = tf.squeeze(gw_normalized_dense, [-1])
  bn_gw_normalized_dense = tf.layers.batch_normalization(
    gw_normalized_dense,
    training=is_training,
    renorm_momentum=0.9999,
    momentum=0.9999,
    renorm=is_training,
    trainable=True,
  )

  return bn_gw_normalized_dense


class FastGroupWiseTrans(object):
  """
  used to apply group-wise fully connected layers to the input.
  it applies a tiny, unique MLP to each individual feature."""

  def __init__(self, group_num, input_dim, out_dim, name, activation=None, init_multiplier=1):
    self.group_num = group_num
    self.input_dim = input_dim
    self.out_dim = out_dim
    self.activation = activation
    self.init_multiplier = init_multiplier

    self.w = tf.get_variable(
      name + "_group_weight",
      [1, group_num, input_dim, out_dim],
      initializer=customized_glorot_uniform(
        fan_in=input_dim * init_multiplier, fan_out=out_dim * init_multiplier
      ),
      trainable=True,
    )
    self.b = tf.get_variable(
      name + "_group_bias",
      [1, group_num, out_dim],
      initializer=tf.constant_initializer(0.0),
      trainable=True,
    )

  def __call__(self, input_tensor):
    """
    input_tensor: batch_size x group_num x input_dim
    output_tensor:  batch_size x group_num x out_dim"""
    input_tensor_expand = tf.expand_dims(input_tensor, axis=-1)

    output_tensor = tf.add(
      tf.reduce_sum(tf.multiply(input_tensor_expand, self.w), axis=-2, keepdims=False),
      self.b,
    )

    if self.activation is not None:
      output_tensor = self.activation(output_tensor)
    return output_tensor


class GroupWiseTrans(object):
  """
  Used to apply group fully connected layers to the input.
  """

  def __init__(self, group_num, input_dim, out_dim, name, activation=None):
    self.group_num = group_num
    self.input_dim = input_dim
    self.out_dim = out_dim
    self.activation = activation

    w_list, b_list = [], []
    for idx in range(out_dim):
      this_w = tf.get_variable(
        name + f"_group_weight_{idx}",
        [1, group_num, input_dim],
        initializer=tf.keras.initializers.glorot_uniform(),
        trainable=True,
      )
      this_b = tf.get_variable(
        name + f"_group_bias_{idx}",
        [1, group_num, 1],
        initializer=tf.constant_initializer(0.0),
        trainable=True,
      )
      w_list.append(this_w)
      b_list.append(this_b)
    self.w_list = w_list
    self.b_list = b_list

  def __call__(self, input_tensor):
    """
    input_tensor: batch_size x group_num x input_dim
    output_tensor: batch_size x group_num x out_dim
    """
    out_tensor_list = []
    for idx in range(self.out_dim):
      this_res = (
        tf.reduce_sum(input_tensor * self.w_list[idx], axis=-1, keepdims=True) + self.b_list[idx]
      )
      out_tensor_list.append(this_res)
    output_tensor = tf.concat(out_tensor_list, axis=-1)

    if self.activation is not None:
      output_tensor = self.activation(output_tensor)
    return output_tensor


def add_scalar_summary(var, name, name_scope="hist_dense_feature/"):
  with tf.name_scope("summaries/"):
    with tf.name_scope(name_scope):
      tf.summary.scalar(name, var)


def add_histogram_summary(var, name, name_scope="hist_dense_feature/"):
  with tf.name_scope("summaries/"):
    with tf.name_scope(name_scope):
      tf.summary.histogram(name, tf.reshape(var, [-1]))


def sparse_clip_by_value(sparse_tf, min_val, max_val):
  new_vals = tf.clip_by_value(sparse_tf.values, min_val, max_val)
  return tf.SparseTensor(sparse_tf.indices, new_vals, sparse_tf.dense_shape)


def check_numerics_with_msg(tensor, message="", sparse_tensor=False):
  if sparse_tensor:
    values = tf.debugging.check_numerics(tensor.values, message=message)
    return tf.SparseTensor(tensor.indices, values, tensor.dense_shape)
  else:
    return tf.debugging.check_numerics(tensor, message=message)


def pad_empty_sparse_tensor(tensor):
  dummy_tensor = tf.SparseTensor(
    indices=[[0, 0]],
    values=[0.00001],
    dense_shape=tensor.dense_shape,
  )
  result = tf.cond(
    tf.equal(tf.size(tensor.values), 0),
    lambda: dummy_tensor,
    lambda: tensor,
  )
  return result


def filter_nans_and_infs(tensor, sparse_tensor=False):
  if sparse_tensor:
    sparse_values = tensor.values
    filtered_val = tf.where(
      tf.logical_or(tf.is_nan(sparse_values), tf.is_inf(sparse_values)),
      tf.zeros_like(sparse_values),
      sparse_values,
    )
    return tf.SparseTensor(tensor.indices, filtered_val, tensor.dense_shape)
  else:
    return tf.where(
      tf.logical_or(tf.is_nan(tensor), tf.is_inf(tensor)), tf.zeros_like(tensor), tensor
    )


def generate_disliked_mask(labels):
  """Generate a disliked mask where only samples with dislike labels are set to 1 otherwise set to 0.
  Args:
    labels: labels of training samples, which is a 2D tensor of shape batch_size x 3: [OONCs, engagements, dislikes]
  Returns:
    1D tensor of shape batch_size x 1: [dislikes (booleans)]
  """
  return tf.equal(tf.reshape(labels[:, 2], shape=[-1, 1]), 1)
