import tensorflow.compat.v1 as tf
from tensorflow.python.ops import array_ops, math_ops


# Copied from metrics_impl.py
# https://github.com/tensorflow/tensorflow/blob/master/tensorflow/python/ops/metrics_impl.py#L216
def safe_div(numerator, denominator, name=None):
  """
  Example usage: calculating NDCG = DCG / IDCG to handle cases when
  IDCG = 0 returns 0 instead of Infinity 
  Do not use this dividing funciton unless it makes sense to your problem
  Divides two tensors element-wise, returns 0 if the denominator is <= 0.
  Args:
    numerator: a real `Tensor`.
    denominator: a real `Tensor`, with dtype matching `numerator`.
    name: Name for the returned op.
  Returns:
    0 if `denominator` <= 0, else `numerator` / `denominator`
  """
  t = math_ops.truediv(numerator, denominator)
  zero = array_ops.zeros_like(t, dtype=denominator.dtype)
  condition = math_ops.greater(denominator, zero)
  zero = math_ops.cast(zero, t.dtype)
  return array_ops.where(condition, t, zero, name=name)


def cal_ndcg(label_scores, predicted_scores, top_k_int=1):
  """
  Calculate NDCG score for top_k_int ranking positions
  Args:
    label_scores: a real `Tensor`.
    predicted_scores: a real `Tensor`, with dtype matching label_scores
    top_k_int: An int or an int `Tensor`.
  Returns:
    a `Tensor` that holds DCG / IDCG.
  """
  sorted_labels, predicted_order = _get_ranking_orders(
    label_scores, predicted_scores, top_k_int=top_k_int)

  predicted_relevance = _get_relevance_scores(predicted_order)
  sorted_relevance = _get_relevance_scores(sorted_labels)

  cg_discount = _get_cg_discount(top_k_int)

  dcg = _dcg_idcg(predicted_relevance, cg_discount)
  idcg = _dcg_idcg(sorted_relevance, cg_discount)
  # the ndcg score of the batch
  # idcg is 0 if label_scores are all 0
  ndcg = safe_div(dcg, idcg, 'one_ndcg')
  return ndcg


def cal_swapped_ndcg(label_scores, predicted_scores, top_k_int):
  """
  Calculate swapped NDCG score in Lambda Rank for full/top k ranking positions
  Args:
    label_scores: a real `Tensor`.
    predicted_scores: a real `Tensor`, with dtype matching label_scores
    top_k_int: An int or an int `Tensor`. 
  Returns:
    a `Tensor` that holds swapped NDCG by .
  """
  sorted_labels, predicted_order = _get_ranking_orders(
    label_scores, predicted_scores, top_k_int=top_k_int)

  predicted_relevance = _get_relevance_scores(predicted_order)
  sorted_relevance = _get_relevance_scores(sorted_labels)

  cg_discount = _get_cg_discount(top_k_int)

  # cg_discount is safe as a denominator
  dcg_k = predicted_relevance / cg_discount
  dcg = tf.reduce_sum(dcg_k)

  idcg_k = sorted_relevance / cg_discount
  idcg = tf.reduce_sum(idcg_k)

  ndcg = safe_div(dcg, idcg, 'ndcg_in_lambdarank_training')

  # remove the gain from label i then add the gain from label j
  tiled_ij = tf.tile(dcg_k, [1, top_k_int])
  new_ij = (predicted_relevance / tf.transpose(cg_discount))

  tiled_ji = tf.tile(tf.transpose(dcg_k), [top_k_int, 1])
  new_ji = tf.transpose(predicted_relevance) / cg_discount

  # if swap i and j, remove the stale cg for i, then add the new cg for i,
  # remove the stale cg for j, and then add the new cg for j
  new_dcg = dcg - tiled_ij + new_ij - tiled_ji + new_ji

  new_ndcg = safe_div(new_dcg, idcg, 'new_ndcg_in_lambdarank_training')
  swapped_ndcg = tf.abs(ndcg - new_ndcg)
  return swapped_ndcg


def _dcg_idcg(relevance_scores, cg_discount):
  """
  Calculate DCG scores for top_k_int ranking positions
  Args:
    relevance_scores: a real `Tensor`.
    cg_discount: a real `Tensor`, with dtype matching relevance_scores
  Returns:
    a `Tensor` that holds \\sum_{i=1}^k \frac{relevance_scores_k}{cg_discount}  
  """
  # cg_discount is safe
  dcg_k = relevance_scores / cg_discount
  return tf.reduce_sum(dcg_k)


def _get_ranking_orders(label_scores, predicted_scores, top_k_int=1):
  """
  Calculate DCG scores for top_k_int ranking positions
  Args:
    label_scores: a real `Tensor`.
    predicted_scores: a real `Tensor`, with dtype matching label_scores
    top_k_int: an integer or an int `Tensor`.
  Returns:
    two `Tensors` that hold sorted_labels: the ground truth relevance socres
    and predicted_order: relevance socres based on sorted predicted_scores
  """
  # sort predictions_scores and label_scores
  # size [batch_size/num of DataRecords, 1]
  label_scores = tf.reshape(label_scores, [-1, 1])
  predicted_scores = tf.reshape(predicted_scores, [-1, 1])
  # sorted_labels contians the relevance scores of the correct order
  sorted_labels, ordered_labels_indices = tf.nn.top_k(
    tf.transpose(label_scores), k=top_k_int)
  sorted_labels = tf.transpose(sorted_labels)
  # sort predicitons and use the indices to obtain the relevance scores of the predicted order
  sorted_predictions, ordered_predictions_indices = tf.nn.top_k(
    tf.transpose(predicted_scores), k=top_k_int)
  ordered_predictions_indices_for_labels = tf.transpose(ordered_predictions_indices)
  # predicted_order contians the relevance scores of the predicted order
  predicted_order = tf.gather_nd(label_scores, ordered_predictions_indices_for_labels)
  return sorted_labels, predicted_order


def _get_cg_discount(top_k_int=1):
  r"""
  Calculate discounted gain factor for ranking position till top_k_int
  Args:
    top_k_int: An int or an int `Tensor`.
  Returns:
    a `Tensor` that holds \log_{2}(i + 1), i \in [1, k] 
  """
  log_2 = tf.log(tf.constant(2.0, dtype=tf.float32))
  # top_k_range needs to start from 1 to top_k_int
  top_k_range = tf.range(top_k_int) + 1
  top_k_range = tf.reshape(top_k_range, [-1, 1])
  # cast top_k_range to float
  top_k_range = tf.cast(top_k_range, dtype=tf.float32)
  cg_discount = tf.log(top_k_range + 1.0) / log_2
  return cg_discount


def _get_relevance_scores(scores):
  return 2 ** scores - 1


def safe_log(raw_scores, name=None):
  """
  Calculate log of a tensor, handling cases that
  raw_scores are close to 0s
  Args:
    raw_scores: An float `Tensor`.
  Returns:
    A float `Tensor` that hols the safe log base e of input
  """
  epsilon = 1E-8
  clipped_raw_scores = tf.maximum(raw_scores, epsilon)
  return tf.log(clipped_raw_scores)
