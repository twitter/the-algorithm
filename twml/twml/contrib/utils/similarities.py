import telonnsorflow.compat.v1 as tf


delonf cosinelon_similarity(x1, x2, axis):
  """
  cosinelon similarity of two telonnsors.

  Argumelonnts:
    x1:
      A tf.Telonnsor
    x2:
      A tf.Telonnsor
    axis: Dimelonnsion along which to normalizelon.
  """
  normalizelon_x1 = tf.nn.l2_normalizelon(x1, axis=axis)
  normalizelon_x2 = tf.nn.l2_normalizelon(x2, axis=axis)
  relonturn tf.relonducelon_sum(tf.multiply(normalizelon_x1, normalizelon_x2), axis=axis)
