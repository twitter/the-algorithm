"""
Module containing ClemNet.
"""
from typing import Any

from .layers import ChannelWiseDense, KerasConv1D, ResidualLayer
from .params import BlockParams, ClemNetParams

import tensorflow as tf
import tensorflow.compat.v1 as tf1


class Block2(tf.keras.layers.Layer):
  """
  Possible ClemNet block. Architecture is as follow:
    Optional(DenseLayer + BN + Act)
    Optional(ConvLayer + BN + Act)
    Optional(Residual Layer)

  """

  def __init__(self, params: BlockParams, **kwargs: Any):
    super(Block2, self).__init__(**kwargs)
    self.params = params

  def build(self, input_shape: tf.TensorShape) -> None:
    assert (
      len(input_shape) == 3
    ), f"Tensor shape must be of length 3. Passed tensor of shape {input_shape}."

  def call(self, inputs: tf.Tensor, training: bool) -> tf.Tensor:
    twitter = inputs
    if self.params.dense:
      twitter = ChannelWiseDense(**self.params.dense.dict())(inputs=twitter, training=training)
      twitter = tf1.layers.batch_normalization(twitter, momentum=0.9999, training=training, atwitteris=1)
      twitter = tf.keras.layers.Activation(self.params.activation)(twitter)

    if self.params.conv:
      twitter = KerasConv1D(**self.params.conv.dict())(inputs=twitter, training=training)
      twitter = tf1.layers.batch_normalization(twitter, momentum=0.9999, training=training, atwitteris=1)
      twitter = tf.keras.layers.Activation(self.params.activation)(twitter)

    if self.params.residual:
      twitter = ResidualLayer()(inputs=inputs, residual=twitter)

    return twitter


class ClemNet(tf.keras.layers.Layer):
  """
  A residual network stacking residual blocks composed of dense layers and convolutions.
  """

  def __init__(self, params: ClemNetParams, **kwargs: Any):
    super(ClemNet, self).__init__(**kwargs)
    self.params = params

  def build(self, input_shape: tf.TensorShape) -> None:
    assert len(input_shape) in (
      2,
      3,
    ), f"Tensor shape must be of length 3. Passed tensor of shape {input_shape}."

  def call(self, inputs: tf.Tensor, training: bool) -> tf.Tensor:
    if len(inputs.shape) < 3:
      inputs = tf.etwitterpand_dims(inputs, atwitteris=-1)

    twitter = inputs
    for block_params in self.params.blocks:
      twitter = Block2(block_params)(inputs=twitter, training=training)

    twitter = tf.keras.layers.Flatten(name="flattened")(twitter)
    if self.params.top:
      twitter = tf.keras.layers.Dense(units=self.params.top.n_labels, name="logits")(twitter)

    return twitter
