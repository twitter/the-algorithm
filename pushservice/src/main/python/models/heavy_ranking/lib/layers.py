"""
Different type of convolution layers to be used in the ClemNet.
"""
from typing import Any

import tensorflow as tf


class KerasConv1D(tf.keras.layers.Layer):
  """
  Basic Conv1D layer in a wrapper to be compatible with ClemNet.
  """

  def __init__(
    self,
    kernel_size: int,
    filters: int,
    strides: int,
    padding: str,
    use_bias: bool = True,
    kernel_initializer: str = "glorot_uniform",
    bias_initializer: str = "zeros",
    **kwargs: Any,
  ):
    super(KerasConv1D, self).__init__(**kwargs)
    self.kernel_size = kernel_size
    self.filters = filters
    self.use_bias = use_bias
    self.kernel_initializer = kernel_initializer
    self.bias_initializer = bias_initializer
    self.strides = strides
    self.padding = padding

  def build(self, input_shape: tf.TensorShape) -> None:
    assert (
      len(input_shape) == 3
    ), f"Tensor shape must be of length 3. Passed tensor of shape {input_shape}."

    self.features = input_shape[1]

    self.w = tf.keras.layers.Conv1D(
      kernel_size=self.kernel_size,
      filters=self.filters,
      strides=self.strides,
      padding=self.padding,
      use_bias=self.use_bias,
      kernel_initializer=self.kernel_initializer,
      bias_initializer=self.bias_initializer,
      name=self.name,
    )

  def call(self, inputs: tf.Tensor, **kwargs: Any) -> tf.Tensor:
    return self.w(inputs)


class ChannelWiseDense(tf.keras.layers.Layer):
  """
  Dense layer is applied to each channel separately. This is more memory and computationally
  efficient than flattening the channels and performing single dense layers over it which is the
  default behavior in tf1.
  """

  def __init__(
    self,
    output_size: int,
    use_bias: bool,
    kernel_initializer: str = "uniform_glorot",
    bias_initializer: str = "zeros",
    **kwargs: Any,
  ):
    super(ChannelWiseDense, self).__init__(**kwargs)
    self.output_size = output_size
    self.use_bias = use_bias
    self.kernel_initializer = kernel_initializer
    self.bias_initializer = bias_initializer

  def build(self, input_shape: tf.TensorShape) -> None:
    assert (
      len(input_shape) == 3
    ), f"Tensor shape must be of length 3. Passed tensor of shape {input_shape}."

    input_size = input_shape[1]
    channels = input_shape[2]

    self.kernel = self.add_weight(
      name="kernel",
      shape=(channels, input_size, self.output_size),
      initializer=self.kernel_initializer,
      trainable=True,
    )

    self.bias = self.add_weight(
      name="bias",
      shape=(channels, self.output_size),
      initializer=self.bias_initializer,
      trainable=self.use_bias,
    )

  def call(self, inputs: tf.Tensor, **kwargs: Any) -> tf.Tensor:
    x = inputs

    transposed_x = tf.transpose(x, perm=[2, 0, 1])
    transposed_residual = (
      tf.transpose(tf.matmul(transposed_x, self.kernel), perm=[1, 0, 2]) + self.bias
    )
    output = tf.transpose(transposed_residual, perm=[0, 2, 1])

    return output


class ResidualLayer(tf.keras.layers.Layer):
  """
  Layer implementing a 3D-residual connection.
  """

  def build(self, input_shape: tf.TensorShape) -> None:
    assert (
      len(input_shape) == 3
    ), f"Tensor shape must be of length 3. Passed tensor of shape {input_shape}."

  def call(self, inputs: tf.Tensor, residual: tf.Tensor, **kwargs: Any) -> tf.Tensor:
    shortcut = tf.keras.layers.Conv1D(
      filters=int(residual.shape[2]), strides=1, kernel_size=1, padding="SAME", use_bias=False
    )(inputs)

    output = tf.add(shortcut, residual)

    return output
