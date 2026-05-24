# pylint: disable=useless-super-delegation
"""
Implementing Stitch Layer
"""


from .layer import Layer

import tensorflow.compat.v1 as tf


class Stitch(Layer):
  """
  This layer is responsible for stitching a partioned layer together.

  Output:
    A layer that performs stitching
  """

  def compute_output_shape(self, input_shape):
    """Computes the output shape of the layer given the input shape.

    Args:
      input_shape: A (possibly nested tuple of) `TensorShape`.  It need not
        be fully defined (e.g. the batch size may be unknown).

    Raises NotImplementedError.

    """
    raise NotImplementedError

  def call(self, partioned_val, partioned_keys,
           partioned_indices, **kwargs):  # pylint: disable=unused-argument, arguments-differ
    """
    This layer is responsible for stitching a partioned layer together.

    Input:
      partioned_val:
        a list of partioned Tensors which represent the vals of the hashmap
      partioned_keys:
        a list of partioned Tensors which represent the keys of the hashmap
      partioned_indices:
        a list of partioned Tensors which represent the indices of the hashmap
    Output:
      List which contains: [output_vals, output_keys]
        output_vals:
          Values of the HashMap (float)
        output_keys:
          Keys of HashMap (float)
    """
    indices = [tf.to_int32(index) for index in partioned_indices]
    concat_keys = tf.dynamic_stitch(indices, partioned_keys)
    concat_vals = tf.dynamic_stitch(indices, partioned_val)
    return [concat_vals, concat_keys]
