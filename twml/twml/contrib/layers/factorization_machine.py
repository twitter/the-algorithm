# pylint: disable=no-member, arguments-differ, attribute-defined-outside-init, unused-argument
"""
Implementing factorization Layer
"""

from twitter.deepbird.sparse.sparse_ops import _pad_empty_outputs

import tensorflow.compat.v1 as tf
import twml
from twml.layers.layer import Layer


class FactorizationMachine(Layer):
  """factorization machine layer class.
  This layer implements the factorization machine operation.
  The paper is "Factorization Machines" by Steffen Rendle.
  TDD: go/tf-fm-tdd

  Arguments:
    num_latent_variables:
      num of latent variables
      The number of parameter in this layer is num_latent_variables x n where n is number of
      input features.
    weight_initializer:
      Initializer function for the weight matrix.
      This argument defaults to zeros_initializer().
      This is valid when the FullSparse is the first layer of
      parameters but should be changed otherwise.
    weight_regularizer:
      Regularizer function for the weight matrix.
      Ensure to add tf.losses.get_regularization_loss() to your loss for this to take effect.
    activation:
      Activation function (callable). Set it to None to maintain a linear activation.
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
    use_binary_values:
      Assume all non zero values are 1. Defaults to False.
      This can improve training if used in conjunction with MDL.
      This parameter can also be a list of binary values if `inputs` passed to `call` a list.
  """

  def __init__(self,
    num_latent_variables=10,
    weight_initializer=None,
    activation=None,
    trainable=True,
    name=None,
    use_sparse_grads=True,
    use_binary_values=False,
    weight_regularizer=None,
    substract_self_cross=True,
    **kwargs):
    super(FactorizationMachine, self).__init__(trainable=trainable, name=name, **kwargs)

    if weight_initializer is None:
      weight_initializer = tf.zeros_initializer()
    self.weight_initializer = weight_initializer
    self.num_latent_variables = num_latent_variables
    self.activation = activation
    self.use_sparse_grads = use_sparse_grads
    self.use_binary_values = use_binary_values
    self.weight_regularizer = weight_regularizer
    self.substract_self_cross = substract_self_cross

  def build(self, input_shape):
    """
    creates``weight`` Variable of shape``[input_size, num_latent_variables]``.

    """

    shape = [input_shape[1], self.num_latent_variables]

    # There is a 2GB limitation for each tensor because of protobuf.
    # 2**30 is 1GB. 2 * (2**30) is 2GB.
    dtype = tf.as_dtype(self.dtype)
    requested_size = input_shape[1] * self.num_latent_variables * dtype.size
    if (requested_size >= 2**31):
      raise ValueError("Weight tensor can not be larger than 2GB. " %
                       "Requested Dimensions(%d, %d) of type %s (%d bytes total)"
                       (input_shape[1], self.num_latent_variables, dtype.name))

    if not callable(self.weight_initializer):
      shape = None

    # dense tensor
    self.weight = self.add_variable(
      'weight',
      initializer=self.weight_initializer,
      regularizer=self.weight_regularizer,
      shape=shape,
      dtype=self.dtype,
      trainable=True,
    )

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
        A SparseTensor
    Returns:
      - If `inputs` is `SparseTensor`, then returns a number with cross info
    """
    # The following are given:
    # - inputs is a sparse tensor, we call it sp_x.
    # - The dense_v tensor is a dense matrix, whose row i
    #   corresponds to the vector V_i.
    #   weights has shape [num_features, k]
    sp_x = inputs
    if isinstance(inputs, twml.SparseTensor):
      sp_x = inputs.to_tf()
    elif not isinstance(sp_x, tf.SparseTensor):
      raise TypeError("The sp_x must be of type tf.SparseTensor or twml.SparseTensor")

    indices = sp_x.indices[:, 1]
    batch_ids = sp_x.indices[:, 0]
    values = tf.reshape(sp_x.values, [-1, 1], name=self.name)
    if self.use_sparse_grads:
      v = tf.nn.embedding_lookup(self.weight, indices)
      # if (self.use_binary_values):
      #   values = tf.ones(tf.shape(values), dtype=values.dtype)
      v_times_x = v * values
      # First term: Sum_k  [Sum_i (v_ik * x_i)]^2
      all_crosses = tf.segment_sum(v_times_x, batch_ids, name=self.name)
      all_crosses_squared = tf.reduce_sum((all_crosses * all_crosses), 1)

      if self.substract_self_cross:
        # Second term: Sum_k Sum_i [ (v_ik * x_i)^2 ]
        v_times_x_2 = v_times_x**2
        self_crosses = tf.reduce_sum(tf.segment_sum(v_times_x_2, batch_ids, name=self.name), 1)
        outputs = all_crosses_squared - self_crosses
      else:
        outputs = all_crosses_squared
    else:
      # need to check if prediction is faster with code below
      crossTerm = tf.reduce_sum((tf.sparse_tensor_dense_matmul(sp_x, self.weight)**2), 1)

      if self.substract_self_cross:
        # compute self-cross term
        self_crossTerm = tf.reduce_sum(tf.segment_sum((tf.gather(self.weight, indices) * values)**2, batch_ids), 1)
        outputs = crossTerm - self_crossTerm
      else:
        outputs = crossTerm

    if self.activation is not None:
      outputs = self.activation(outputs)

    outputs = tf.reshape(outputs, [-1, 1], name=self.name)
    outputs = _pad_empty_outputs(outputs, tf.cast(sp_x.dense_shape[0], tf.int32))
    # set more explicit and static shape to avoid shape inference error
    # valueError: The last dimension of the inputs to `Dense` should be defined. Found `None`
    outputs.set_shape([None, 1])
    return outputs
