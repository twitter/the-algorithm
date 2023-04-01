import tensorflow.compat.v1 as tf


def cosine_similarity(x1, x2, axis):
  """
  cosine similarity of two tensors.

  Arguments:
    x1:
      A tf.Tensor
    x2:
      A tf.Tensor
    axis: Dimension along which to normalize.
  """
  normalize_x1 = tf.nn.l2_normalize(x1, axis=axis)
  normalize_x2 = tf.nn.l2_normalize(x2, axis=axis)
  return tf.reduce_sum(tf.multiply(normalize_x1, normalize_x2), axis=axis)
