import numpy as np
import tensorflow.compat.v1 as tf


TWML_INIT_FEED_KEY = "TWML_INIT_FEED_COLLECTION"


class PartitionConstant(tf.keras.initializers.Constant):
  """A constant initializer that supports partitions"""

  def __call__(self, shape, dtype=None, partition_info=None):
    if partition_info is not None:
      if not isinstance(self.value, np.ndarray):
        raise ValueError(
          "Currently, PartitionConstant only supports "
          "partitioning on np.ndarrays. Got {}".format(type(self.value).__name__))
      offsets = partition_info.var_offset
      indices = tuple([slice(offset, offset + size) for offset, size in zip(offsets, shape)])
      subset = self.value[indices]
      return subset
    else:
      return self.value


partition_constant_initializer = PartitionConstant


class PlaceholderInitializer(tf.keras.initializers.Initializer):
  """A placeholder initializer that supports partitions"""

  def __init__(self, shape, dtype):
    self.dtype = dtype
    self.value = tf.placeholder(dtype=dtype, shape=shape)

  def __call__(self, shape, dtype=None, partition_info=None):
    if partition_info is not None:
      if self.dtype != dtype:
        raise ValueError("dtype does not match placeholder dtype")
      offsets = partition_info.var_offset
      indices = tuple([slice(offset, offset + size) for offset, size in zip(offsets, shape)])
      subset = self.value[indices]
      return subset
    else:
      return self.value


def get_init_feed_dict():
  """Get the init feed dictionary to be used when running the init op."""
  # Get the reference to the collection.
  init_feed_collection = tf.get_collection(TWML_INIT_FEED_KEY)
  init_feed_dict = {}
  for d in init_feed_collection:
    init_feed_dict.update(d)
  return init_feed_dict


def clear_init_feed_collection():
  """Clear the init feed collection."""
  init_feed_collection = tf.get_collection_ref(TWML_INIT_FEED_KEY)
  while init_feed_collection:
    init_feed_collection.pop()
