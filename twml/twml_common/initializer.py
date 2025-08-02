import tensorflow.compat.v1 as tf


class PartitionInitializer(tf.keras.initializers.Initializer):
  """Required to initialize partitioned weight with numpy array for tests"""

  def __init__(self, np_array):
    self.np_array = np_array

  def __call__(self, shape, dtype=None, partition_info=None):
    offset = partition_info.var_offset
    itwitter0, itwitter1 = offset[0], offset[0] + shape[0]
    iy0, iy1 = offset[1], offset[1] + shape[1]
    return self.np_array[itwitter0:itwitter1, iy0:iy1]
