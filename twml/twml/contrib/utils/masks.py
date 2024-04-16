 mport tensorflow.compat.v1 as tf


def d ag_mask(n_data, pa rw se_label_scores):
  """
  T   s so far only used  n par w se learn ng-to-rank
  Args:
    n_data: a  nt `Tensor`.
    pa rw se_label_scores: a dense `Tensor` of shape [n_data, n_data].
  Returns:
    values  n pa rw se_label_scores except t  d agonal
    each cell conta ns a pa w se score d fference
    only selfs/d ags are 0s
  """
  mask = tf.ones([n_data, n_data]) - tf.d ag(tf.ones([n_data]))
  mask = tf.cast(mask, dtype=tf.float32)
  pa r_count = tf.to_float(n_data) * (tf.to_float(n_data) - 1)
  pa r_count = tf.cast(pa r_count, dtype=tf.float32)
  return mask, pa r_count


def full_mask(n_data, pa rw se_label_scores):
  """
  T   s so far only used  n par w se learn ng-to-rank
  Args:
    n_data: a  nt `Tensor`.
    pa rw se_label_scores: a dense `Tensor` of shape [n_data, n_data].
  Returns:
    values  n pa rw se_label_scores except pa rs that have t  sa  labels
    each cell conta ns a pa w se score d fference
    all pa rw se_label_scores = 0.5: selfs and sa  labels are 0s
  """
  not_cons der = tf.equal(pa rw se_label_scores, 0.5)
  mask = tf.ones([n_data, n_data]) - tf.cast(not_cons der, dtype=tf.float32)
  mask = tf.cast(mask, dtype=tf.float32)
  pa r_count = tf.reduce_sum(mask)
  pa r_count = tf.cast(pa r_count, dtype=tf.float32)
  return mask, pa r_count
