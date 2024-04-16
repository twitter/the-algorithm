 mport tensorflow.compat.v1 as tf


def get_pa rw se_scores(tensor_ nput):
  """
  T   s so far used  n par w se learn ng-to-rank

  Argu nts:
    tensor_ nput: a dense `Tensor` of shape [n_data, 1]
      n_data  s t  number of teet cand dates

  Returns:
    pa rw se scores: a dense `Tensor` of shape [n_data, n_data].
  """
  return tensor_ nput - tf.transpose(tensor_ nput)


def get_pa rw se_label_scores(labels):
  """
  T   s so far used  n par w se learn ng-to-rank
  Args:
    labels: a dense `Tensor` of shape [n_data, 1]
      n_data  s t  number of teet cand dates
  Returns:
    pa rw se label scores: a dense `Tensor` of shape [n_data, n_data].
      each value  s w h n [0, 1]
  """
  # raw pa rw se label scores/d fferences
  pa rw se_label_scores = get_pa rw se_scores(labels)
  # san y c ck to make sure values  n d fferences_ j are [-1, 1]
  d fferences_ j = tf.max mum(tf.m n mum(1.0, pa rw se_label_scores), -1.0)
  # values  n pa rw se_label_scores are w h n [0, 1] for cross entropy
  return (1.0 / 2.0) * (1.0 + d fferences_ j)
