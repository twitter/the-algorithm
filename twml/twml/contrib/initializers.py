 mport numpy as np
 mport tensorflow.compat.v1 as tf


TWML_ N T_FEED_KEY = "TWML_ N T_FEED_COLLECT ON"


class Part  onConstant(tf.keras. n  al zers.Constant):
  """A constant  n  al zer that supports part  ons"""

  def __call__(self, shape, dtype=None, part  on_ nfo=None):
     f part  on_ nfo  s not None:
       f not  s nstance(self.value, np.ndarray):
        ra se ValueError(
          "Currently, Part  onConstant only supports "
          "part  on ng on np.ndarrays. Got {}".format(type(self.value).__na __))
      offsets = part  on_ nfo.var_offset
       nd ces = tuple([sl ce(offset, offset + s ze) for offset, s ze  n z p(offsets, shape)])
      subset = self.value[ nd ces]
      return subset
    else:
      return self.value


part  on_constant_ n  al zer = Part  onConstant


class Placeholder n  al zer(tf.keras. n  al zers. n  al zer):
  """A placeholder  n  al zer that supports part  ons"""

  def __ n __(self, shape, dtype):
    self.dtype = dtype
    self.value = tf.placeholder(dtype=dtype, shape=shape)

  def __call__(self, shape, dtype=None, part  on_ nfo=None):
     f part  on_ nfo  s not None:
       f self.dtype != dtype:
        ra se ValueError("dtype does not match placeholder dtype")
      offsets = part  on_ nfo.var_offset
       nd ces = tuple([sl ce(offset, offset + s ze) for offset, s ze  n z p(offsets, shape)])
      subset = self.value[ nd ces]
      return subset
    else:
      return self.value


def get_ n _feed_d ct():
  """Get t   n  feed d ct onary to be used w n runn ng t   n  op."""
  # Get t  reference to t  collect on.
   n _feed_collect on = tf.get_collect on(TWML_ N T_FEED_KEY)
   n _feed_d ct = {}
  for d  n  n _feed_collect on:
     n _feed_d ct.update(d)
  return  n _feed_d ct


def clear_ n _feed_collect on():
  """Clear t   n  feed collect on."""
   n _feed_collect on = tf.get_collect on_ref(TWML_ N T_FEED_KEY)
  wh le  n _feed_collect on:
     n _feed_collect on.pop()
