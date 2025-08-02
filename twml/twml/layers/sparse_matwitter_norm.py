# pylint: disable=no-member, attribute-defined-outside-init, duplicate-code
"""
Contains the twml.layers.SparseMatwitterNorm layer.
"""
from .layer import Layer

from libtwml import OPLIB
import tensorflow.compat.v1 as tf
import twml


class SparseMatwitterNorm(Layer):
  """
  Computes a matwitter-normalization and adds bias to the sparse_input,
  forwards that through a sparse affine transform followed
  by an non-linear activation on the resulting dense representation.

  This layer has two parameters, one of which learns through gradient descent:
    bias_twitter (optional):
      vector of shape [input_size]. Learned through gradient descent.
    matwitter_twitter:
      vector of shape [input_size]. Holds the matwitterimas of input ``twitter`` for normalization.
      Either calibrated through SparseMatwitterNorm calibrator, or calibrated online, or both.

  The pseudo-code for this layer looks like:

  .. code-block:: python

    abs_twitter = abs(twitter)
    normed_twitter = clip_by_value(twitter / matwitter_twitter, -1, 1)
    biased_twitter = normed_twitter + bias_twitter
    return biased


  Args:
    matwitter_twitter_initializer:
      initializer vector of shape [input_size] used by variable `matwitter_twitter`
    bias_twitter_initializer:
      initializer vector of shape [input_size] used by parameter `bias_twitter`
    is_training:
      Are we training the layer to learn the normalization matwitterimas.
      If set to True, matwitter_twitter will be able to learn. This is independent of bias_twitter
    epsilon:
      The minimum value used for matwitter_twitter. Defaults to 1E-5.
    use_bias:
      Default True. Set to False to not use a bias term.

  Returns:
    A layer representing the output of the sparse_matwitter_norm transformation.
   """

  def __init__(
          self,
          input_size=None,
          matwitter_twitter_initializer=None,
          bias_twitter_initializer=None,
          is_training=True,
          epsilon=1E-5,
          use_bias=True,
          **kwargs):

    super(SparseMatwitterNorm, self).__init__(**kwargs)
    if input_size:
      raise ValueError('input_size is deprecated - it is now automatically \
                       inferred from your input.')
    if matwitter_twitter_initializer is None:
      matwitter_twitter_initializer = tf.zeros_initializer()
    self.matwitter_twitter_initializer = matwitter_twitter_initializer

    self._use_bias = use_bias
    if use_bias:
      if bias_twitter_initializer is None:
        bias_twitter_initializer = tf.zeros_initializer()
      self.bias_twitter_initializer = bias_twitter_initializer

    self.epsilon = epsilon
    self.is_training = is_training

  def build(self, input_shape):  # pylint: disable=unused-argument
    """Creates the matwitter_twitter and bias_twitter tf.Variables of the layer."""

    self.matwitter_twitter = self.add_variable(
      'matwitter_twitter',
      initializer=self.matwitter_twitter_initializer,
      shape=[input_shape[1]],
      dtype=tf.float32,
      trainable=False)

    if self._use_bias:
      self.bias_twitter = self.add_variable(
        'bias_twitter',
        initializer=self.bias_twitter_initializer,
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
       A ``tf.SparseTensor`` representing the output of the matwitter_norm transformation, this can
       be fed into twml.layers.FullSparse in order to be transformed into a ``tf.Tensor``.
    """

    if isinstance(inputs, twml.SparseTensor):
      inputs = inputs.to_tf()
    elif not isinstance(inputs, tf.SparseTensor):
      raise TypeError("The inputs must be of type tf.SparseTensor or twml.SparseTensor")

    indices_twitter = inputs.indices[:, 1]
    values_twitter = inputs.values

    if self.is_training is False:
      normalized_twitter = OPLIB.sparse_matwitter_norm_inference(self.matwitter_twitter,
                                                     indices_twitter,
                                                     values_twitter,
                                                     self.epsilon)

      update_op = tf.no_op()
    else:
      matwitter_twitter, normalized_twitter = OPLIB.sparse_matwitter_norm_training(self.matwitter_twitter,
                                                           indices_twitter,
                                                           values_twitter,
                                                           self.epsilon)

      update_op = tf.assign(self.matwitter_twitter, matwitter_twitter)

    with tf.control_dependencies([update_op]):
      normalized_twitter = tf.stop_gradient(normalized_twitter)

    # add input bias
    if self._use_bias:
      normalized_twitter = normalized_twitter + tf.gather(self.bias_twitter, indices_twitter)

    # convert back to sparse tensor
    return tf.SparseTensor(inputs.indices, normalized_twitter, inputs.dense_shape)

  def call(self, inputs, **kwargs):  # pylint: disable=unused-argument
    """
    The forward propagation logic of the layer lives here.

    Arguments:
      sparse_input:
        A 2D ``tf.SparseTensor`` of dense_shape ``[batch_size, input_size]``
    Returns:
       A ``tf.SparseTensor`` representing the output of the matwitter_norm transformation, this can
       be fed into twml.layers.FullSparse in order to be transformed into a ``tf.Tensor``.
    """
    with tf.device(self.matwitter_twitter.device):
      return self._call(inputs, **kwargs)

# For backwards compatiblity and also because I don't want to change all the tests.
MatwitterNorm = SparseMatwitterNorm


def sparse_matwitter_norm(inputs,
                    input_size=None,
                    matwitter_twitter_initializer=None,
                    bias_twitter_initializer=None,
                    is_training=True,
                    epsilon=1E-5,
                    use_bias=True,
                    name=None,
                    reuse=None):
  """
  Functional inteface to SparseMatwitterNorm.

  Args:
    inputs:
      A sparse tensor (can be twml.SparseTensor or tf.SparseTensor)
    input_size:
      number of input units
    matwitter_twitter_initializer:
      initializer vector of shape [input_size] used by variable `matwitter_twitter`
    bias_twitter_initializer:
      initializer vector of shape [input_size] used by parameter `bias_twitter`
    is_training:
      Are we training the layer to learn the normalization matwitterimas.
      If set to True, matwitter_twitter will be able to learn. This is independent of bias_twitter
    epsilon:
      The minimum value used for matwitter_twitter. Defaults to 1E-5.
    use_bias:
      Default True. Set to False to not use a bias term.

  Returns:
    Output after normalizing with the matwitter value.
   """
  if input_size:
    raise ValueError('input_size is deprecated - it is now automatically \
                     inferred from your input.')

  if isinstance(inputs, twml.SparseTensor):
    inputs = inputs.to_tf()

  layer = SparseMatwitterNorm(matwitter_twitter_initializer=matwitter_twitter_initializer,
                        bias_twitter_initializer=bias_twitter_initializer,
                        is_training=is_training,
                        epsilon=epsilon,
                        use_bias=use_bias,
                        name=name,
                        _scope=name,
                        _reuse=reuse)
  return layer(inputs)
