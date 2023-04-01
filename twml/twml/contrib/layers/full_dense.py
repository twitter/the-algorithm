# pylint: disable=no-member,arguments-differ, attribute-defined-outside-init
"""
Implementing Full Dense Layer
"""
from twml.layers import Layer

import tensorflow.compat.v1 as tf
from tensorflow.python.layers import core


class FullDense(Layer):
  """
  Full-connected, Dense input layer class.
  This layer implements the operation:

  .. code-block:: python

    outputs = activation(inputs.weight + bias)

  Where ``activation`` is the activation function passed as the ``activation``
  argument (if not ``None``), ``weight`` is a weights matrix created by the layer,
  and ``bias`` is a bias vector created by the layer.

  However, this layer breaks up ``weight`` into ``num_partitions`` parts,
  for the purpose of even disribution of weights across parameter servers
  for distributed training.

  Note - This layer is created to allow distributed training optimizations,
  but can also be used for single node training (e.g. hogwild) without
  code modification

  Arguments:
    output_size:
      Integer or Long, dimensionality of the output space.
    weight_initializer:
      Initializer function for the weight matrix.
    weight_regularizer:
      Regularizer function for the weight matrix.
      Ensure to add tf.losses.get_regularization_loss() to your loss for this to take effect.
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
    num_partitions:
      Number of pieces to partition the weights into. This layer does
      column partitioning of the weights, which is equivalent to
      processing the input tensor with multiple fully connected layers
      of smaller output size, and then concatenating these outputs
    activation:
      Activation function (callable). Set it to None to maintain a linear activation.
    use_bias:
      Boolean whether to include a bias parameter in the layer
    bias_initializer:
      Initializer function for the bias.
    bias_regularizer:
      Regularizer function for the bias.
      Ensure to add tf.losses.get_regularization_loss() to your loss for this to take effect.
    activity_regularizer:
      Regularizer function for the output.
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
    weights:
      list of underlying weight and bias matrix components. no guarantee on order of elements
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
               num_partitions=3,
               activation=None,
               use_bias=True,
               bias_initializer=tf.zeros_initializer(),
               bias_regularizer=None,
               activity_regularizer=None,
               trainable=True,
               name=None,
               **kwargs):
    super(FullDense, self).__init__(trainable=trainable, name=name, **kwargs)
    self._output_sizes = self._get_output_partition_sizes(output_size, num_partitions)
    self._units = output_size
    self._activation = activation
    self._weight_initializer = weight_initializer
    self._bias_initializer = bias_initializer
    self._weight_regularizer = weight_regularizer
    self._bias_regularizer = bias_regularizer
    self._weight_constraint = weight_constraint
    self._bias_constraint = bias_constraint
    self._use_bias = use_bias
    # NOTE - many initializers depend on fan_in and fan_out
    #      - as such, initialization here may be different than
    #      - for a non-partitioned FullDense
    self._parts = [core.Dense(units=out_size,
                              activation=activation,
                              use_bias=use_bias,
                              kernel_initializer=weight_initializer,
                              bias_initializer=bias_initializer,
                              kernel_regularizer=weight_regularizer,
                              bias_regularizer=bias_regularizer,
                              activity_regularizer=activity_regularizer,
                              kernel_constraint=weight_constraint,
                              bias_constraint=bias_constraint,
                              trainable=trainable,
                              name=name,
                              **kwargs) for out_size in self._output_sizes]

  @staticmethod
  def _get_output_partition_sizes(out_size, num_parts):
    """ Returns the appropriate output sizes of the partitions """
    boundaries = [out_size * n // num_parts for n in range(num_parts + 1)]
    return [k - j for j, k in zip(boundaries[:], boundaries[1:])]

  def build(self, input_shapes):
    """ Create the appropriately sized weights and biases in each layer partition """
    if isinstance(input_shapes, (list, tuple)):
      input_shape = input_shapes[0]
      is_compatible = True
      for other_shape in input_shapes[1:]:
        is_compatible &= input_shape.is_compatible_with(other_shape)
      if not is_compatible:
        raise ValueError("Input shapes %s are not compatible." % input_shapes)
    else:
      input_shape = input_shapes

    for part in self._parts:
      part.build(input_shape)

    self.built = True

  @property
  def units(self):
    """ Returns the number of output units of the layer """
    return self._units

  @property
  def output_size(self):
    """ Returns the number of output units of the layer """
    return self._units

  @property
  def activation(self):
    """ Returns the activation function """
    return self._activation

  @property
  def weight_initializer(self):
    """ Returns the weight_initializer """
    return self._weight_initializer

  @property
  def weight_regularizer(self):
    """ Returns the weight_regularizer """
    return self._weight_regularizer

  @property
  def weight_constraint(self):
    """ Returns the weight_constraint """
    return self._weight_constraint

  @property
  def bias_initializer(self):
    """ Returns the bias_initializer """
    return self._bias_initializer

  @property
  def bias_regularizer(self):
    """ Returns the bias_regularizer """
    return self._bias_regularizer

  @property
  def bias_constraint(self):
    """ Returns the bias_constraint """
    return self._bias_constraint

  @property
  def use_bias(self):
    """ Returns whether a bias is used in the layer """
    return self._use_bias

  @property
  def trainable_variables(self):
    """ Returns the trainable variables of the layer """
    trainable_vars = []
    for pt in self._parts:
      trainable_vars += pt.trainable_variables
    return trainable_vars

  @property
  def trainable_weights(self):
    """ Returns the trainable variables of the layer """
    return self.trainable_variables

  @property
  def non_trainable_variables(self):
    """ Returns the non-trainable variables of the layer """
    non_trainable_vars = []
    for pt in self._parts:
      non_trainable_vars += pt.non_trainable_variables
    return non_trainable_vars

  @property
  def non_trainable_weights(self):
    """ Returns the non-trainable variables of the layer """
    return self.non_trainable_variables

  @property
  def variables(self):
    """ Returns a list of all weights and biases in this layer """
    layer_vars = []
    for pt in self._parts:
      layer_vars += pt.weights
    return layer_vars

  @property
  def weights(self):
    """ Returns a list of all weights and biases in this layer """
    return self.variables

  @property
  def dtype(self):
    """ Returns the dtype of the layers weights """
    return self._parts[0].dtype

  def call(self, inputs, **kwargs):  # pylint: disable=unused-argument
    """The logic of the layer lives here.

    Arguments:
      inputs:
        A dense Tensor or a list of such.
        If `inputs` is a list, all tensors must have same `dense_shape`.

    Returns:
      - If `inputs` is `SparseTensor`, then returns `bias + inputs * dense_b`.
      - If `inputs` is a `list[SparseTensor`, then returns
       `bias + accumulate_n([sp_a * dense_b for sp_a in inputs])`.
    """
    if not isinstance(inputs, (list, tuple)):
      inputs = [inputs]

    outputs = []
    for inp in inputs:
      part_outputs = [part(inp) for part in self._parts]
      outputs.append(tf.concat(part_outputs, axis=-1))

    return tf.accumulate_n(outputs)


def full_dense(inputs, output_size,
               weight_initializer=None,
               weight_regularizer=None,
               weight_constraint=None,
               bias_constraint=None,
               num_partitions=3,
               activation=None,
               use_bias=True,
               bias_initializer=tf.zeros_initializer(),
               bias_regularizer=None,
               activity_regularizer=None,
               trainable=True,
               name=None,
               reuse=None,
               **kwargs):
  """Functional interface for the fully-connected dense-input layer.
  This layer implements the operation:
  `outputs = activation(inputs.weight + bias)`
  Where `activation` is the activation function passed as the `activation`
  argument (if not `None`), `weight` is a weights matrix created by the layer,
  and `bias` is a bias vector created by the layer
  (only if `use_bias` is `True`).

  However, this layer breaks up ``weight`` into ``num_partitions`` parts,
  for the purpose of even disribution of weights across parameter servers
  for distributed training.

  Note - This layer is created to allow distributed training optimizations,
  but can also be used for single node training (e.g. hogwild) without
  code modification

  Arguments:
    inputs: Tensor input.
    output_size: Integer or Long, dimensionality of the output space.
    weight_initializer: Initializer function for the weight matrix.
      If `None` (default), weights are initialized using the default
      initializer used by `tf.get_variable`.
    weight_regularizer:
      Regularizer function for the weight matrix.
      Ensure to add tf.losses.get_regularization_loss() to your loss for this to take effect.
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
    num_partitions:
      Number of pieces to partition the weights into. This layer does
      column partitioning of the weights, which is equivalent to
      processing the input tensor with multiple fully connected layers
      of smaller output size, and then concatenating these outputs
    activation: Activation function (callable). Set it to None to maintain a
      linear activation.
    use_bias: Boolean, whether the layer uses a bias.
    bias_initializer:
      Initializer function for the bias.
    bias_regularizer:
      Regularizer function for the bias.
      Ensure to add tf.losses.get_regularization_loss() to your loss for this to take effect.
    activity_regularizer:
      Regularizer function for the output.
    trainable:
      Boolean, if `True` also add variables to the graph collection
      `GraphKeys.TRAINABLE_VARIABLES` (see `tf.Variable`).
    name:
      String, the name of the layer.
    reuse:
      Boolean, whether to reuse the weights of a previous layer
      by the same name.

  Returns:
    Output tensor with shape `inputs.shape[:-1] + [output_size]`.
  """
  if not isinstance(inputs, (list, tuple)):
    inputs = [inputs]

  dtype = inputs[0].dtype.base_dtype

  layer = FullDense(output_size=output_size,
                    weight_initializer=weight_initializer,
                    weight_regularizer=weight_regularizer,
                    weight_constraint=weight_constraint,
                    bias_constraint=bias_constraint,
                    num_partitions=num_partitions,
                    activation=activation,
                    use_bias=use_bias,
                    bias_initializer=bias_initializer,
                    bias_regularizer=bias_regularizer,
                    activity_regularizer=activity_regularizer,
                    trainable=trainable,
                    name=name,
                    dtype=dtype,
                    _scope=name,
                    _reuse=reuse,
                    **kwargs)

  return layer(inputs)
