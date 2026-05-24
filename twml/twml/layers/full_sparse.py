# pylint: disable=no-member, arguments-differ, attribute-defined-outside-init, unused-argument
"""
Implementing Full Sparse Layer
"""

import math

from twitter.deepbird.sparse import sparse_dense_matmul

from .layer import Layer

import tensorflow.compat.v1 as tf
import twml


class FullSparse(Layer):
  """Fully-sparse layer class.
  This layer implements the operation:

  .. code-block:: python

    outputs = activation(inputs.weight + bias)

  Arguments:
    output_size:
      Long or Integer, dimensionality of the output space.
    input_size:
      The number of input units. (Deprecated)
    weight_initializer:
      Initializer function for the weight matrix.
      This argument defaults to zeros_initializer().
      This is valid when the FullSparse is the first layer of
      parameters but should be changed otherwise.
    weight_regularizer:
      Regularizer function for the weight matrix.
      Ensure to add tf.losses.get_regularization_loss() to your loss for this to take effect.
    bias_regularizer:
      Regularizer function for the bias.
      Ensure to add tf.losses.get_regularization_loss() to your loss for this to take effect
    activation:
      Activation function (callable). Set it to None to maintain a linear activation.
    bias_initializer:
      Initializer function for the bias.
      This argument defaults to tf.constant_initializer(1/output_size)
    trainable:
      Boolean, if `True` also add variables to the graph collection
      ``GraphKeys.TRAINABLE_VARIABLES`` (see `tf.Variable
      <https://www.tensorflow.org/versions/master/api_docs/python/tf/Variable>`_).
    name:
      String, the name of the layer. Layers with the same name will
      share weights, but to avoid mistakes we require ``reuse=True`` in such cases.
    use_sparse_grads:
      Boolean, if `True` do sparse mat mul with `embedding_lookup_sparse`, which will
      make gradients to weight matrix also sparse in backward pass. This can lead to non-trivial
      speed up at training time when input_size is large and optimizer handles sparse gradients
      correctly (eg. with SGD or LazyAdamOptimizer). If weight matrix is small, it's recommended
      to set this flag to `False`; for most use cases of FullSparse, however, weight matrix will
      be large, so it's better to set it to `True`
    num_partitions:
      Number of partitions to use for the weight variable. Defaults to 1.
    partition_axis:
      If num_partitions is specified, the partition axis for the weight variable
      Defaults to 0 (partition by row).
      Must be 0 (row) or 1 (column)
    use_binary_values:
      Assume all non zero values are 1. Defaults to False.
      This can improve training if used in conjunction with MDL.
      This parameter can also be a list of binary values if `inputs` passed to `call` a list.
    use_compression:
      Default False. Set True to enable data compression techniques for
      optimization of network traffic for distributed training.
    use_binary_sparse_dense_matmul:
      If binary sparse dense matmul op is to be used. It will only be enabled if
      `use_binary_values` is set true. It only should be used for inference, best practice is
      to set `use_binary_sparse_dense_matmul = not is_training`.
  """

  def __init__(self,
               output_size,
               input_size=None,
               weight_initializer=None,
               activation=None,
               bias_initializer=None,
               trainable=True,
               name=None,
               use_sparse_grads=True,
               num_partitions=None,
               partition_axis=0,
               use_binary_values=False,
               bias_regularizer=None,
               weight_regularizer=None,
               use_compression=False,
               use_binary_sparse_dense_matmul=False,
               **kwargs):
    super(FullSparse, self).__init__(trainable=trainable, name=name, **kwargs)
    # TODO - remove input_size warning.
    if input_size:
      raise ValueError('input_size is deprecated - it is now automatically \
                       inferred from your input.')

    # The bias initialization and weights initialization is set to match v1's implementation.
    if bias_initializer is None:
      bias_initializer = tf.constant_initializer(1 / output_size)
    # Weights initialization is set to 0s. This is safe for full sparse layers because
    # you are supposed to learn your embedding from the label.
    if weight_initializer is None:
      weight_initializer = tf.zeros_initializer()
    self.weight_initializer = weight_initializer
    self.bias_initializer = bias_initializer
    self.output_size = output_size
    self.activation = activation
    self.use_sparse_grads = use_sparse_grads
    self.num_partitions = num_partitions
    if partition_axis != 0 and partition_axis != 1:
      raise ValueError('partition_axis must be 0 or 1')
    self.partition_axis = partition_axis
    self.use_binary_values = use_binary_values
    self.weight_regularizer = weight_regularizer
    self.bias_regularizer = bias_regularizer
    self._use_compression = use_compression
    self._cast_indices_dtype = tf.int32 if self._use_compression else None
    self.use_binary_sparse_dense_matmul = use_binary_sparse_dense_matmul

  def _make_weight_var(self, shape, partitioner):
    self.weight = self.add_variable(
      'weight',
      initializer=self.weight_initializer,
      regularizer=self.weight_regularizer,
      shape=shape,
      dtype=self.dtype,
      trainable=True,
      partitioner=partitioner,
    )

  def build(self, input_shapes):
    """
    creates the ``bias`` and ``weight`` Variables
    of shape ``[output_size]`` and ``[input_size, output_size]`` respectively.
    """

    if isinstance(input_shapes, (list, tuple)):
      input_shape = input_shapes[0]
      is_compatible = True
      for other_shape in input_shapes[1:]:
        is_compatible &= input_shape.is_compatible_with(other_shape)
      if not is_compatible:
        raise ValueError("Input shapes %s are not compatible." % input_shapes)
    else:
      input_shape = input_shapes

    self.bias = self.add_variable(
      'bias',
      initializer=self.bias_initializer,
      regularizer=self.bias_regularizer,
      shape=[self.output_size, ],
      dtype=self.dtype,
      trainable=True
    )

    partitioner = None
    shape = [input_shape[1], self.output_size]

    # There is a 2gb limitation for each tensor because of protobuf.
    # 2**30 is 1GB. 2 * (2**30) is 2GB.
    dtype = tf.as_dtype(self.dtype)
    num_partitions = 1 if self.num_partitions is None else self.num_partitions
    in_shape = input_shape[1]
    out_shape = self.output_size

    # when v2 behavior is disabled, in_shape is tf.Dimension. otherwise it is int.
    if isinstance(in_shape, tf.Dimension):
      in_shape = in_shape.value

    if in_shape is None:
      raise ValueError("Input tensor should have shape."
                       " You can set it using twml.util.limit_sparse_tensor_size")

    (split_dim, other_dim) = (in_shape, out_shape) if self.partition_axis == 0 else (out_shape, in_shape)
    requested_size = math.ceil(float(split_dim) / num_partitions) * other_dim * dtype.size
    if (requested_size >= 2**31):
      raise ValueError("Weight tensor partitions cannot be larger than 2GB.\n"
                       "Requested Dimensions(%d, %d) of type %s (%d bytes total) over %d partitions.\n"
                       "Possible solutions:\n"
                       "- reduce the params.output_size_bits\n"
                       "- reduce the output_size of the sparse_layer\n"
                       "- specify a larger num_partitions argument\n"
                       "- reduce input_size_bits" %
                       (in_shape, self.output_size, dtype.name, requested_size, num_partitions))

    if self.num_partitions:
      partition_axis = int(self.partition_axis)
      partitioner = tf.fixed_size_partitioner(self.num_partitions, axis=partition_axis)
    else:
      # Regular variables do not like it when you pass both constant tensors and shape
      if not callable(self.weight_initializer):
        shape = None

    self._make_weight_var(shape, partitioner)

    self.built = True

  def compute_output_shape(self, input_shape):
    """Computes the output shape of the layer given the input shape.

    Args:
      input_shape: A (possibly nested tuple of) `TensorShape`.  It need not
        be fully defined (e.g. the batch size may be unknown).

    Raises NotImplementedError.

    """
    raise NotImplementedError

  def call(self, inputs, **kwargs):  # pylint: disable=unused-argument
    """The logic of the layer lives here.

    Arguments:
      inputs:
        A SparseTensor or a list of SparseTensors.
        If `inputs` is a list, all tensors must have same `dense_shape`.

    Returns:
      - If `inputs` is `SparseTensor`, then returns `bias + inputs * dense_b`.
      - If `inputs` is a `list[SparseTensor`, then returns
        `bias + add_n([sp_a * dense_b for sp_a in inputs])`.

    """
    if isinstance(inputs, (list, tuple)):

      if isinstance(self.use_binary_values, (list, tuple)):
        use_binary_values = self.use_binary_values
      else:
        use_binary_values = [self.use_binary_values] * len(inputs)

      num_inputs = len(inputs)
      if num_inputs != len(use_binary_values):
        raise ValueError("#inputs is %d while #use_binary_values is %d"
                         % (num_inputs, len(use_binary_values)))

      outputs = []
      for n in range(num_inputs):
        outputs.append(sparse_dense_matmul(inputs[n], self.weight,
                                           self.use_sparse_grads,
                                           use_binary_values[n],
                                           name='sparse_mm_' + str(n),
                                           partition_axis=self.partition_axis,
                                           num_partitions=self.num_partitions,
                                           compress_ids=self._use_compression,
                                           cast_indices_dtype=self._cast_indices_dtype,
                                           use_binary_sparse_dense_matmul=self.use_binary_sparse_dense_matmul))
      outputs = tf.accumulate_n(outputs)
    else:

      if isinstance(self.use_binary_values, (list, tuple)):
        raise ValueError("use_binary_values can not be %s when inputs is %s" %
                         (type(self.use_binary_values), type(inputs)))

      outputs = sparse_dense_matmul(inputs, self.weight,
                                    self.use_sparse_grads,
                                    self.use_binary_values,
                                    name='sparse_mm',
                                    partition_axis=self.partition_axis,
                                    num_partitions=self.num_partitions,
                                    compress_ids=self._use_compression,
                                    cast_indices_dtype=self._cast_indices_dtype,
                                    use_binary_sparse_dense_matmul=self.use_binary_sparse_dense_matmul)

    if self.bias is not None:
      outputs = tf.nn.bias_add(outputs, self.bias)

    if self.activation is not None:
      return self.activation(outputs)  # pylint: disable=not-callable
    return outputs


def full_sparse(
        inputs, output_size,
        input_size=None,
        activation=None,
        bias_regularizer=None,
        weight_regularizer=None,
        bias_initializer=None,
        weight_initializer=None,
        trainable=True,
        name=None,
        reuse=None,
        use_sparse_grads=True,
        num_partitions=None,
        partition_axis=0,
        use_binary_values=False,
        use_compression=False):
  """Functional interface for the sparsely-connected layer.

  Arguments:
    inputs:
      A sparse tensor (can be twml.SparseTensor or tf.SparseTensor)
    output_size:
      Long or Integer, dimensionality of the output space.
    weight_initializer:
      Initializer function for the weight matrix.
    activation:
      Activation function (callable). Set it to None to maintain a linear activation.
    bias_initializer:
      Initializer function for the bias.
    weight_regularizer:
      Regularizer function for the weight matrix.
      Ensure to add tf.losses.get_regularization_loss() to your loss for this to take effect.
    bias_regularizer:
      Regularizer function for the bias.
      Ensure to add tf.losses.get_regularization_loss() to your loss for this to take effect.
    trainable:
      Boolean, if `True` also add variables to the graph collection
      ``GraphKeys.TRAINABLE_VARIABLES`` (see `tf.Variable
      <https://www.tensorflow.org/versions/master/api_docs/python/tf/Variable>`_).
    name:
      String, the name of the layer. Layers with the same name will
      share weights, but to avoid mistakes we require ``reuse=True`` in such cases.
    use_sparse_grads:
      Boolean, if `True` do sparse mat mul with `embedding_lookup_sparse`, which will
      make gradients to weight matrix also sparse in backward pass. This can lead to non-trivial
      speed up at training time when input_size is large and optimizer handles sparse gradients
      correctly (eg. with SGD or LazyAdamOptimizer). If weight matrix is small, it's recommended
      to set this flag to `False`; for most use cases of FullSparse, however, weight matrix will
      be large, so it's better to set it to `True`
    num_partitions:
      Number of partitions to use for the weight variable. Defaults to 1.
    partition_axis:
      If num_partitions is specified, the partition axis for the weight variable
      Defaults to 0 (partition by row).
      Must be 0 (row) or 1 (column)
    use_binary_values:
      Assume all non zero values are 1. Defaults to False.
      This can improve training if used in conjunction with MDL.
    use_compression:
      Default False. Set True to enable data compression techniques for
      optimization of network traffic for distributed training.
  Returns:
    Outputs a ``tf.Tensor`` of size ``[batch_size x output_size]``.
  """
  # TODO - remove input_size warning.
  if input_size:
    raise ValueError('input_size is deprecated - it is now \
                      automatically inferred from your input.')

  dtype = None
  if isinstance(inputs, twml.SparseTensor):
    inputs = inputs.to_tf()
    dtype = inputs.dtype.base_dtype

  if isinstance(inputs, (list, tuple)):
    inputs = [inp.to_tf() if isinstance(inp, twml.SparseTensor) else inp for inp in inputs]
    dtype = inputs[0].dtype.base_dtype

  layer = FullSparse(output_size=output_size,
                     activation=activation,
                     trainable=trainable,
                     name=name,
                     weight_initializer=weight_initializer,
                     bias_initializer=bias_initializer,
                     weight_regularizer=weight_regularizer,
                     bias_regularizer=bias_regularizer,
                     dtype=dtype,
                     _scope=name,
                     _reuse=reuse,
                     use_sparse_grads=use_sparse_grads,
                     num_partitions=num_partitions,
                     partition_axis=partition_axis,
                     use_compression=use_compression,
                     use_binary_values=use_binary_values)
  return layer(inputs)
