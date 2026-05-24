import tensorflow.compat.v1 as tf


def get_pairwise_scores(tensor_input):
  """
  This is so far used in pariwise learning-to-rank

  Arguments:
    tensor_input: a dense `Tensor` of shape [n_data, 1]
      n_data is the number of teet candidates

  Returns:
    pairwise scores: a dense `Tensor` of shape [n_data, n_data].
  """
  return tensor_input - tf.transpose(tensor_input)


def get_pairwise_label_scores(labels):
  """
  This is so far used in pariwise learning-to-rank
  Args:
    labels: a dense `Tensor` of shape [n_data, 1]
      n_data is the number of teet candidates
  Returns:
    pairwise label scores: a dense `Tensor` of shape [n_data, n_data].
      each value is within [0, 1]
  """
  # raw pairwise label scores/differences
  pairwise_label_scores = get_pairwise_scores(labels)
  # sanity check to make sure values in differences_ij are [-1, 1]
  differences_ij = tf.maximum(tf.minimum(1.0, pairwise_label_scores), -1.0)
  # values in pairwise_label_scores are within [0, 1] for cross entropy
  return (1.0 / 2.0) * (1.0 + differences_ij)
