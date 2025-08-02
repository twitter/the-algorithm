import tensorflow.compat.v1 as tf


def cosine_similarity(twitter1, twitter2, atwitteris):
  """
  cosine similarity of two tensors.

  Arguments:
    twitter1:
      A tf.Tensor
    twitter2:
      A tf.Tensor
    atwitteris: Dimension along which to normalize.
  """
  normalize_twitter1 = tf.nn.l2_normalize(twitter1, atwitteris=atwitteris)
  normalize_twitter2 = tf.nn.l2_normalize(twitter2, atwitteris=atwitteris)
  return tf.reduce_sum(tf.multiply(normalize_twitter1, normalize_twitter2), atwitteris=atwitteris)
