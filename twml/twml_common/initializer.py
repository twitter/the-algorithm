 mport tensorflow.compat.v1 as tf


class Part  on n  al zer(tf.keras. n  al zers. n  al zer):
  """Requ red to  n  al ze part  oned   ght w h numpy array for tests"""

  def __ n __(self, np_array):
    self.np_array = np_array

  def __call__(self, shape, dtype=None, part  on_ nfo=None):
    offset = part  on_ nfo.var_offset
     x0,  x1 = offset[0], offset[0] + shape[0]
     y0,  y1 = offset[1], offset[1] + shape[1]
    return self.np_array[ x0: x1,  y0: y1]
