# pylint: disable=no-member, attribute-defined-outside-init, duplicate-code
"""
Contains the twml.layers.SparseMaxNorm layer.
"""
from .layer import Layer

from libtwml import OPLIB
import tensorflow.compat.v1 as tf
import twml


class SparseMaxNorm(Layer):
  """
  Computes a max-normalization and adds bias to the sparse_input,
  forwards that through a sparse affine transform followed
  by an non-linear activation on the resulting dense representation.

  This layer has two parameters, one of which learns through gradient descent:
    bias_x (optional):
      vector of shape [input_size]. Learned through gradient descent.
    max_x:
      vector of shape [input_size]. Holds the maximas of input ``x`` for normalization.
      Either calibrated through SparseMaxNorm calibrator, or calibrated online, or both.

  The pseudo-code for this layer looks like:

  .. code-block:: python

    abs_x = abs(x)
    normed_x = clip_by_value(x / max_x, -1, 1)
    biased_x = normed_x + bias_x
    return biased


  Args:
    max_x_initializer:
      initializer vector of shape [input_size] used by variable `max_x`
    bias_x_initializer:
      initializer vector of shape [input_size] used by parameter `bias_x`
    is_training:
      Are we training the layer to learn the normalization maximas.
      If set to True, max_x will be able to learn. This is independent of bias_x
    epsilon:
      The minimum value used for max_x. Defaults to 1E-5.
    use_bias:
      Default True. Set to False to not use a bias term.

  Returns:
    A layer representing the output of the sparse_max_norm transformation.
   """

  def __init__(
          self,
          input_size=None,
          max_x_initializer=None,
          bias_x_initializer=None,
          is_training=True,
          epsilon=1E-5,
          use_bias=True,
          **kwargs):

    super(SparseMaxNorm, self).__init__(**kwargs)
    if input_size:
      raise ValueError('input_size is deprecated - it is now automatically \
                       inferred from your input.')
    if max_x_initializer is None:
      max_x_initializer = tf.zeros_initializer()
    self.max_x_initializer = max_x_initializer

    self._use_bias = use_bias
    if use_bias:
      if bias_x_initializer is None:
        bias_x_initializer = tf.zeros_initializer()
      self.bias_x_initializer = bias_x_initializer

    self.epsilon = epsilon
    self.is_training = is_training

  def build(self, input_shape):  # pylint: disable=unused-argument
    """Creates the max_x and bias_x tf.Variables of the layer."""

    self.max_x = self.add_variable(
      'max_x',
      initializer=self.max_x_initializer,
      shape=[input_shape[1]],
      dtype=tf.float32,
      trainable=False)

    if self._use_bias:
      self.bias_x = self.add_variable(
        'bias_x',
        initializer=self.bias_x_initializer,
        shape=[input_shape[1]],
        dtype=tf.float32,
        trainable=True)

    self.built = True

  def compute_output_shape(self, input_shape):
    """Computes the output shape of the layer given the input shape.

    Args:
      input_shape: A (possibly nested tuple of) `TensorShape`.  It need not
        be fully defined (e.g. the batch size may be unknown).

    Raises NotImplementedError.

    """
    raise NotImplementedError

  def _call(self, inputs, **kwargs):  # pylint: disable=unused-argument
    """
    The forward propagation logic of the layer lives here.

    Arguments:
      sparse_input:
        A 2D ``tf.SparseTensor`` of dense_shape ``[batch_size, input_size]``
    Returns:
       A ``tf.SparseTensor`` representing the output of the max_norm transformation, this can
       be fed into twml.layers.FullSparse in order to be transformed into a ``tf.Tensor``.
    """

    if isinstance(inputs, twml.SparseTensor):
      inputs = inputs.to_tf()
    elif not isinstance(inputs, tf.SparseTensor):
      raise TypeError("The inputs must be of type tf.SparseTensor or twml.SparseTensor")

    indices_x = inputs.indices[:, 1]
    values_x = inputs.values

    if self.is_training is False:
      normalized_x = OPLIB.sparse_max_norm_inference(self.max_x,
                                                     indices_x,
                                                     values_x,
                                                     self.epsilon)

      update_op = tf.no_op()
    else:
      max_x, normalized_x = OPLIB.sparse_max_norm_training(self.max_x,
                                                           indices_x,
                                                           values_x,
                                                           self.epsilon)

      update_op = tf.assign(self.max_x, max_x)

    with tf.control_dependencies([update_op]):
      normalized_x = tf.stop_gradient(normalized_x)

    # add input bias
    if self._use_bias:
      normalized_x = normalized_x + tf.gather(self.bias_x, indices_x)

    # convert back to sparse tensor
    return tf.SparseTensor(inputs.indices, normalized_x, inputs.dense_shape)

  def call(self, inputs, **kwargs):  # pylint: disable=unused-argument
    """
    The forward propagation logic of the layer lives here.

    Arguments:
      sparse_input:
        A 2D ``tf.SparseTensor`` of dense_shape ``[batch_size, input_size]``
    Returns:
       A ``tf.SparseTensor`` representing the output of the max_norm transformation, this can
       be fed into twml.layers.FullSparse in order to be transformed into a ``tf.Tensor``.
    """
    with tf.device(self.max_x.device):
      return self._call(inputs, **kwargs)

# For backwards compatiblity and also because I don't want to change all the tests.
MaxNorm = SparseMaxNorm


def sparse_max_norm(inputs,
                    input_size=None,
                    max_x_initializer=None,
                    bias_x_initializer=None,
                    is_training=True,
                    epsilon=1E-5,
                    use_bias=True,
                    name=None,
                    reuse=None):
  """
  Functional inteface to SparseMaxNorm.

  Args:
    inputs:
      A sparse tensor (can be twml.SparseTensor or tf.SparseTensor)
    input_size:
      number of input units
    max_x_initializer:
      initializer vector of shape [input_size] used by variable `max_x`
    bias_x_initializer:
      initializer vector of shape [input_size] used by parameter `bias_x`
    is_training:
      Are we training the layer to learn the normalization maximas.
      If set to True, max_x will be able to learn. This is independent of bias_x
    epsilon:
      The minimum value used for max_x. Defaults to 1E-5.
    use_bias:
      Default True. Set to False to not use a bias term.

  Returns:
    Output after normalizing with the max value.
   """
  if input_size:
    raise ValueError('input_size is deprecated - it is now automatically \
                     inferred from your input.')

  if isinstance(inputs, twml.SparseTensor):
    inputs = inputs.to_tf()

  layer = SparseMaxNorm(max_x_initializer=max_x_initializer,
                        bias_x_initializer=bias_x_initializer,
                        is_training=is_training,
                        epsilon=epsilon,
                        use_bias=use_bias,
                        name=name,
                        _scope=name,
                        _reuse=reuse)
  return layer(inputs)
