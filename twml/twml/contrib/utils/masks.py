import tensorflow.compat.v1 as tf


def diag_mask(n_data, pairwise_label_scores):
  """
  This is so far only used in pariwise learning-to-rank
  Args:
    n_data: a int `Tensor`.
    pairwise_label_scores: a dense `Tensor` of shape [n_data, n_data].
  Returns:
    values in pairwise_label_scores except the diagonal
    each cell contains a paiwise score difference
    only selfs/diags are 0s
  """
  mask = tf.ones([n_data, n_data]) - tf.diag(tf.ones([n_data]))
  mask = tf.cast(mask, dtype=tf.float32)
  pair_count = tf.to_float(n_data) * (tf.to_float(n_data) - 1)
  pair_count = tf.cast(pair_count, dtype=tf.float32)
  return mask, pair_count


def full_mask(n_data, pairwise_label_scores):
  """
  This is so far only used in pariwise learning-to-rank
  Args:
    n_data: a int `Tensor`.
    pairwise_label_scores: a dense `Tensor` of shape [n_data, n_data].
  Returns:
    values in pairwise_label_scores except pairs that have the same labels
    each cell contains a paiwise score difference
    all pairwise_label_scores = 0.5: selfs and same labels are 0s
  """
  not_consider = tf.equal(pairwise_label_scores, 0.5)
  mask = tf.ones([n_data, n_data]) - tf.cast(not_consider, dtype=tf.float32)
  mask = tf.cast(mask, dtype=tf.float32)
  pair_count = tf.reduce_sum(mask)
  pair_count = tf.cast(pair_count, dtype=tf.float32)
  return mask, pair_count
