# pylint: disable=no-member, arguments-differ, attribute-defined-outside-init, unused-argument
"""
Implementing Full Sparse Layer, allow specify use_binary_value in call() to
overide default action.
"""

from twml.layers import FullSparse as defaultFullSparse
from twml.layers.full_sparse import sparse_dense_matmul

import tensorflow.compat.v1 as tf


class FullSparse(defaultFullSparse):
  def call(self, inputs, use_binary_values=None, **kwargs):  # pylint: disable=unused-argument
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

    if use_binary_values is not None:
      default_use_binary_values = use_binary_values
    else:
      default_use_binary_values = self.use_binary_values

    if isinstance(default_use_binary_values, (list, tuple)):
      raise ValueError(
        "use_binary_values can not be %s when inputs is %s"
        % (type(default_use_binary_values), type(inputs))
      )

    outputs = sparse_dense_matmul(
      inputs,
      self.weight,
      self.use_sparse_grads,
      default_use_binary_values,
      name="sparse_mm",
      partition_axis=self.partition_axis,
      num_partitions=self.num_partitions,
      compress_ids=self._use_compression,
      cast_indices_dtype=self._cast_indices_dtype,
    )

    if self.bias is not None:
      outputs = tf.nn.bias_add(outputs, self.bias)

    if self.activation is not None:
      return self.activation(outputs)  # pylint: disable=not-callable
    return outputs
