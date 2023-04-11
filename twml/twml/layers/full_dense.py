# pylint: disable=no-member,arguments-differ, attribute-defined-outside-init
"""
Implementing Full Dense Layer
"""
from tensorflow.python.layers import core as core_layers
from tensorflow.python.ops import init_ops
from tensorflow.python.framework import tensor_shape
from tensorflow.python.keras.engine.base_layer import InputSpec
import tensorflow.compat.v1 as tf


class FullDense(core_layers.Dense):
  """
  Densely-connected layer class.
  This is wrapping tensorflow.python.layers.core.Dense
  This layer implements the operation:

  .. code-block:: python

    outputs = activation(inputs.weight + bias)

  Where ``activation`` is the activation function passed as the ``activation``
  argument (if not ``None``), ``weight`` is a weights matrix created by the layer,
  and ``bias`` is a bias vector created by the layer.

  Arguments:
    output_size:
      Integer or Long, dimensionality of the output space.
    activation:
      Activation function (callable). Set it to None to maintain a linear activation.
    weight_initializer:
      Initializer function for the weight matrix.
    bias_initializer:
      Initializer function for the bias.
    weight_regularizer:
      Regularizer function for the weight matrix.
      Ensure to add tf.losses.get_regularization_loss() to your loss for this to take effect.
    bias_regularizer:
      Regularizer function for the bias.
      Ensure to add tf.losses.get_regularization_loss() to your loss for this to take effect.
    activity_regularizer:
      Regularizer function for the output.
    weight_constraint:
      An optional projection function to be applied to the
      weight after being updated by an `Optimizer` (e.g. used to implement
      norm constraints or value constraints for layer weights). The function
      must take as input the unprojected variable and must return the
      projected variable (which must have the same shape). Constraints are
      not safe to use when doing asynchronous distributed training.
    bias_constraint:
      An optional projection function to be applied to the
      bias after being updated by an `Optimizer`.
    trainable:
      Boolean, if `True` also add variables to the graph collection
      ``GraphKeys.TRAINABLE_VARIABLES`` (see `tf.Variable
      <https://www.tensorflow.org/versions/master/api_docs/python/tf/Variable>`_).
    name:
      String, the name of the layer. Layers with the same name will
      share weights, but to avoid mistakes we require ``reuse=True`` in such cases.

  Properties:
    output_size:
      Python integer, dimensionality of the output space.
    activation:
      Activation function (callable).
    weight_initializer:
      Initializer instance (or name) for the weight matrix.
    bias_initializer:
      Initializer instance (or name) for the bias.
    weight:
      Weight matrix (TensorFlow variable or tensor). (weight)
    bias:
      Bias vector, if applicable (TensorFlow variable or tensor).
    weight_regularizer:
      Regularizer instance for the weight matrix (callable)
    bias_regularizer:
      Regularizer instance for the bias (callable).
    activity_regularizer:
      Regularizer instance for the output (callable)
    weight_constraint:
      Constraint function for the weight matrix.
    bias_constraint:
      Constraint function for the bias.

  """

  def __init__(self, output_size,
               weight_initializer=None,
               weight_regularizer=None,
               weight_constraint=None,
               bias_constraint=None,
               num_partitions=None,
               **kwargs):
    super(FullDense, self).__init__(units=output_size,
                                    kernel_initializer=weight_initializer,
                                    kernel_regularizer=weight_regularizer,
                                    kernel_constraint=weight_constraint,
                                    **kwargs)
    self._num_partitions = num_partitions

  def build(self, input_shape):
    '''
    code adapted from TF 1.12 Keras Dense layer:
    https://github.com/tensorflow/tensorflow/blob/r1.12/tensorflow/python/keras/layers/core.py#L930-L956
    '''
    input_shape = tensor_shape.TensorShape(input_shape)
    if input_shape[-1] is None:
      raise ValueError('The last dimension of the inputs to `Dense` '
                       'should be defined. Found `None`.')
    self.input_spec = InputSpec(min_ndim=2,
                                axes={-1: input_shape[-1]})

    partitioner = None
    if self._num_partitions:
      partitioner = tf.fixed_size_partitioner(self._num_partitions)

    self.kernel = self.add_weight(
        'kernel',
        shape=[input_shape[-1], self.units],
        initializer=self.kernel_initializer,
        regularizer=self.kernel_regularizer,
        constraint=self.kernel_constraint,
        dtype=self.dtype,
        partitioner=partitioner,
        trainable=True)

    if self.use_bias:
      self.bias = self.add_weight(
          'bias',
          shape=[self.units, ],
          initializer=self.bias_initializer,
          regularizer=self.bias_regularizer,
          constraint=self.bias_constraint,
          dtype=self.dtype,
          trainable=True)
    else:
      self.bias = None
    self.built = True

  @property
  def output_size(self):
    """
    Returns output_size
    """
    return self.units

  @property
  def weight(self):
    """
    Returns weight
    """
    return self.kernel

  @property
  def weight_regularizer(self):
    """
    Returns weight_regularizer
    """
    return self.kernel_regularizer

  @property
  def weight_initializer(self):
    """
    Returns weight_initializer
    """
    return self.kernel_initializer

  @property
  def weight_constraint(self):
    """
    Returns weight_constraint
    """
    return self.kernel_constraint


def full_dense(inputs, output_size,
               activation=None,
               use_bias=True,
               weight_initializer=None,
               bias_initializer=init_ops.zeros_initializer(),
               weight_regularizer=None,
               bias_regularizer=None,
               activity_regularizer=None,
               weight_constraint=None,
               bias_constraint=None,
               trainable=True,
               name=None,
               num_partitions=None,
               reuse=None):
  """Functional interface for the densely-connected layer.
  This layer implements the operation:
  `outputs = activation(inputs.weight + bias)`
  Where `activation` is the activation function passed as the `activation`
  argument (if not `None`), `weight` is a weights matrix created by the layer,
  and `bias` is a bias vector created by the layer
  (only if `use_bias` is `True`).

  Arguments:
    inputs: Tensor input.
    units: Integer or Long, dimensionality of the output space.
    activation: Activation function (callable). Set it to None to maintain a
      linear activation.
    use_bias: Boolean, whether the layer uses a bias.
    weight_initializer: Initializer function for the weight matrix.
      If `None` (default), weights are initialized using the default
      initializer used by `tf.get_variable`.
    bias_initializer:
      Initializer function for the bias.
    weight_regularizer:
      Regularizer function for the weight matrix.
      Ensure to add tf.losses.get_regularization_loss() to your loss for this to take effect.
    bias_regularizer:
      Regularizer function for the bias.
      Ensure to add tf.losses.get_regularization_loss() to your loss for this to take effect.
    activity_regularizer:
      Regularizer function for the output.
    weight_constraint:
      An optional projection function to be applied to the
      weight after being updated by an `Optimizer` (e.g. used to implement
      norm constraints or value constraints for layer weights). The function
      must take as input the unprojected variable and must return the
      projected variable (which must have the same shape). Constraints are
      not safe to use when doing asynchronous distributed training.
    bias_constraint:
      An optional projection function to be applied to the
      bias after being updated by an `Optimizer`.
    trainable:
      Boolean, if `True` also add variables to the graph collection
      `GraphKeys.TRAINABLE_VARIABLES` (see `tf.Variable`).
    name:
      String, the name of the layer.
    reuse:
      Boolean, whether to reuse the weights of a previous layer
      by the same name.

  Returns:
    Output tensor the same shape as `inputs` except the last dimension is of
    size `units`.

  Raises:
    ValueError: if eager execution is enabled.
  """
  layer = FullDense(output_size,
                    activation=activation,
                    use_bias=use_bias,
                    weight_initializer=weight_initializer,
                    bias_initializer=bias_initializer,
                    weight_regularizer=weight_regularizer,
                    bias_regularizer=bias_regularizer,
                    activity_regularizer=activity_regularizer,
                    weight_constraint=weight_constraint,
                    bias_constraint=bias_constraint,
                    trainable=trainable,
                    name=name,
                    dtype=inputs.dtype.base_dtype,
                    num_partitions=num_partitions,
                    _scope=name,
                    _reuse=reuse)
  return layer.apply(inputs)
