"""
Implementing partition Layer
"""


from .layer import Layer

import tensorflow.compat.v1 as tf


class Partition(Layer):
  """
  This layer implements:

  .. code-block:: python

    tf.dynamic_partition(input_vals, partition_ids, self.partitions)

  Input:
    partitions:
      the number of partitions which we will divide the hashmap keys/bvalues

  Output:
    A layer that performs partitioning
   """

  def __init__(self, partitions=2, **kwargs):
    self.partitions = partitions
    super(Partition, self).__init__(**kwargs)

  def compute_output_shape(self, input_shape):
    """Computes the output shape of the layer given the input shape.

    Args:
      input_shape: A (possibly nested tuple of) `TensorShape`.  It need not
        be fully defined (e.g. the batch size may be unknown).

    Raises NotImplementedError.

    """
    raise NotImplementedError

  def call(self, partition_ids, input_vals, input_keys, **kwargs):
    """This layer is responsible for partitioning the values/keys of a hashmap

    Arguments:
      partition_ids:
        Tensor that is equivalent to boolean (int32).
      input_vals:
        Tensor that represents the values of the hashmap(float).
      input_keys:
        Tensor that represents the keys of the hashmap(float)

    Returns:
      The output of the partition layer, which is a list of lists which looks
      something like:

      .. code-block:: python

        [[vals_0, vals_1], [keys_0, keys_1], [indices_0, indices_1]]

      where:
        vals_x:
          values of the hashmap for partition x
        keys_x:
          keys of the hashmap for partition x
        indices_x:
          indices of the hashmap for partition x
    """
    partioned_val = tf.dynamic_partition(input_vals, partition_ids, self.partitions)
    partioned_keys = tf.dynamic_partition(input_keys, partition_ids, self.partitions)
    partioned_indices = tf.dynamic_partition(tf.range(tf.shape(partition_ids)[0]),
                                             tf.cast(partition_ids, tf.int32), self.partitions)
    return [partioned_val, partioned_keys, partioned_indices]
