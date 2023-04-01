import tensorflow.compat.v1 as tf
from twml.contrib.utils import masks, math_fns


def get_pair_loss(pairwise_label_scores, pairwise_predicted_scores,
                  params):
  """
  Paiwise learning-to-rank ranknet loss
  Check paper https://www.microsoft.com/en-us/research/publication/
  learning-to-rank-using-gradient-descent/
  for more information
  Args:
    pairwise_label_scores: a dense tensor of shape [n_data, n_data]
    pairwise_predicted_scores: a dense tensor of shape [n_data, n_data]
    n_data is the number of tweet candidates in a BatchPredictionRequest
    params: network parameters
  mask options: full_mask and diag_mask
  Returns:
    average loss over pairs defined by the masks
  """
  n_data = tf.shape(pairwise_label_scores)[0]
  if params.mask == "full_mask":
    # full_mask that only covers pairs that have different labels
    # (all pairwise_label_scores = 0.5: selfs and same labels are 0s)
    mask, pair_count = masks.full_mask(n_data, pairwise_label_scores)
  else:
    # diag_mask that covers all pairs
    # (only selfs/diags are 0s)
    mask, pair_count = masks.diag_mask(n_data, pairwise_label_scores)

  # pairwise sigmoid_cross_entropy_with_logits loss
  loss = tf.cond(tf.equal(pair_count, 0), lambda: 0.,
    lambda: _get_average_cross_entropy_loss(pairwise_label_scores,
      pairwise_predicted_scores, mask, pair_count))
  return loss


def get_lambda_pair_loss(pairwise_label_scores, pairwise_predicted_scores,
                  params, swapped_ndcg):
  """
  Paiwise learning-to-rank lambdarank loss
  faster than the previous gradient method
  Note: this loss depends on ranknet cross-entropy
  delta NDCG is applied to ranknet cross-entropy
  Hence, it is still a gradient descent method
  Check paper http://citeseerx.ist.psu.edu/viewdoc/
  download?doi=10.1.1.180.634&rep=rep1&type=pdf for more information
  for more information
  Args:
    pairwise_label_scores: a dense tensor of shape [n_data, n_data]
    pairwise_predicted_scores: a dense tensor of shape [n_data, n_data]
    n_data is the number of tweet candidates in a BatchPredictionRequest
    params: network parameters
    swapped_ndcg: swapped ndcg of shape [n_data, n_data]
    ndcg values when swapping each pair in the prediction ranking order
  mask options: full_mask and diag_mask
  Returns:
    average loss over pairs defined by the masks
  """
  n_data = tf.shape(pairwise_label_scores)[0]
  if params.mask == "full_mask":
    # full_mask that only covers pairs that have different labels
    # (all pairwise_label_scores = 0.5: selfs and same labels are 0s)
    mask, pair_count = masks.full_mask(n_data, pairwise_label_scores)
  else:
    # diag_mask that covers all pairs
    # (only selfs/diags are 0s)
    mask, pair_count = masks.diag_mask(n_data, pairwise_label_scores)

  # pairwise sigmoid_cross_entropy_with_logits loss
  loss = tf.cond(tf.equal(pair_count, 0), lambda: 0.,
    lambda: _get_average_cross_entropy_loss(pairwise_label_scores,
      pairwise_predicted_scores, mask, pair_count, swapped_ndcg))
  return loss


def _get_average_cross_entropy_loss(pairwise_label_scores, pairwise_predicted_scores,
                                    mask, pair_count, swapped_ndcg=None):
  """
  Average the loss for a batchPredictionRequest based on a desired number of pairs
  """
  loss = tf.nn.sigmoid_cross_entropy_with_logits(labels=pairwise_label_scores,
    logits=pairwise_predicted_scores)
  loss = mask * loss
  if swapped_ndcg is not None:
    loss = loss * swapped_ndcg
  loss = tf.reduce_sum(loss) / pair_count
  return loss


def get_listmle_loss(labels, predicted_scores):
  r"""
  listwise learning-to-rank listMLE loss
  Note: Simplified MLE formula is used in here (omit the proof in here)
  \sum_{s=1}^{n-1} (-predicted_scores + ln(\sum_{i=s}^n exp(predicted_scores)))
  n is tf.shape(predicted_scores)[0]
  Check paper http://icml2008.cs.helsinki.fi/papers/167.pdf for more information
  Args:
    labels: a dense tensor of shape [n_data, 1]
    n_data is the number of tweet candidates in a BatchPredictionRequest
    predicted_scores: a dense tensor of same shape and type as labels
  Returns:
    average loss
  """
  labels = tf.reshape(labels, [-1, 1])
  n_data = tf.shape(labels)[0]
  predicted_scores = tf.reshape(predicted_scores, [-1, 1])

  predicted_scores_ordered_by_labels = _get_ordered_predicted_scores(labels,
    predicted_scores, n_data)

  loss = (-1) * tf.reduce_sum(predicted_scores)
  # sum over 1 to n_data - 1
  temp = tf.gather(predicted_scores_ordered_by_labels, [n_data - 1])
  temp = tf.reshape(temp, [])
  loss = tf.add(loss, temp)

  exps = tf.exp(predicted_scores_ordered_by_labels)
  exp_sum = tf.reduce_sum(exps)
  # clip exp_sum for safer log
  loss = tf.add(loss, math_fns.safe_log(exp_sum))

  iteration = tf.constant(0)

  def _cond(iteration, loss, exp_sum, exp):
    return tf.less(iteration, n_data - 2)

  def _gen_loop_body():
    def loop_body(iteration, loss, exp_sum, exps):
      temp = tf.gather(exps, [iteration])
      temp = tf.reshape(temp, [])
      exp_sum = tf.subtract(exp_sum, temp)
      # clip exp_sum for safer log
      loss = tf.add(loss, math_fns.safe_log(exp_sum))
      return tf.add(iteration, 1), loss, exp_sum, exps
    return loop_body

  iteration, loss, exp_sum, exps = tf.while_loop(_cond, _gen_loop_body(),
    (iteration, loss, exp_sum, exps))
  loss = loss / tf.cast(n_data, dtype=tf.float32)
  return loss


def _get_ordered_predicted_scores(labels, predicted_scores, n_data):
  """
  Order predicted_scores based on sorted labels
  """
  sorted_labels, ordered_labels_indices = tf.nn.top_k(
    tf.transpose(labels), k=n_data)
  ordered_labels_indices = tf.transpose(ordered_labels_indices)
  predicted_scores_ordered_by_labels = tf.gather_nd(predicted_scores,
    ordered_labels_indices)
  return predicted_scores_ordered_by_labels


def get_attrank_loss(labels, predicted_scores, weights=None):
  """
  Modified listwise learning-to-rank AttRank loss
  Check paper https://arxiv.org/abs/1804.05936 for more information
  Note: there is an inconsistency between the paper statement and
  their public code
  Args:
    labels: a dense tensor of shape [n_data, 1]
    n_data is the number of tweet candidates in a BatchPredictionRequest
    predicted_scores: a dense tensor of same shape and type as labels
    weights: a dense tensor of the same shape as labels
  Returns:
    average loss
  """
  # The authors immeplemented the following, which is basically listnet
  # attention_labels = _get_attentions(labels)
  # attention_labels = tf.reshape(attention_labels, [1, -1])
  # predicted_scores = tf.reshape(predicted_scores, [1, -1])
  # loss = tf.reduce_mean(tf.nn.softmax_cross_entropy_with_logits(labels=attention_labels,
  #   logits=predicted_scores))

  # The paper proposed the following
  # attention_labels = _get_attentions(labels)
  # # However the following line is wrong based on their statement
  # # as _get_attentions can give 0 results when input < 0
  # # and the result cannot be used in _get_attrank_cross_entropy
  # # log(a_i^S)
  # # attention_predicted_scores = _get_attentions(predicted_scores)
  # loss = _get_attrank_cross_entropy(attention_labels, attention_predicted_scores)
  # # the range of attention_predicted_scores is [0, 1)
  # # this gives sigmoid [0.5, 0.732)
  # # hence, it is not good to use in sigmoid_cross_entropy_with_logits either

  # Implemented the following instead
  # _get_attentions is applied to labels
  # softmax is applied to predicted_scores
  reshaped_labels = tf.reshape(labels, [1, -1])
  attention_labels = _get_attentions(reshaped_labels)
  reshaped_predicted_scores = tf.reshape(predicted_scores, [1, -1])
  attention_predicted_scores = tf.nn.softmax(reshaped_predicted_scores)
  loss = _get_attrank_cross_entropy(attention_labels, attention_predicted_scores)
  return loss


def _get_attentions(raw_scores):
  """
  Used in attention weights in AttRank loss
  for a query/batch/batchPreidictionRequest
  (a rectified softmax function)
  """
  not_consider = tf.less_equal(raw_scores, 0)
  mask = tf.ones(tf.shape(raw_scores)) - tf.cast(not_consider, dtype=tf.float32)
  mask = tf.cast(mask, dtype=tf.float32)
  expon_labels = mask * tf.exp(raw_scores)

  expon_label_sum = tf.reduce_sum(expon_labels)
  # expon_label_sum is safe as a denominator
  attentions = math_fns.safe_div(expon_labels, expon_label_sum)
  return attentions


def _get_attrank_cross_entropy(labels, logits):
  # logits is not safe based on their satement
  # do not use this function directly elsewhere
  results = labels * math_fns.safe_log(logits) + (1 - labels) * math_fns.safe_log(1 - logits)
  results = (-1) * results
  results = tf.reduce_mean(results)
  return results


def get_listnet_loss(labels, predicted_scores, weights=None):
  """
  Listwise learning-to-rank listet loss
  Check paper https://www.microsoft.com/en-us/research/
  wp-content/uploads/2016/02/tr-2007-40.pdf
  for more information
  Args:
    labels: a dense tensor of shape [n_data, 1]
    n_data is the number of tweet candidates in a BatchPredictionRequest
    predicted_scores: a dense tensor of same shape and type as labels
    weights: a dense tensor of the same shape as labels
  Returns:
    average loss
  """
  # top one probability is the same as softmax
  labels_top_one_probs = _get_top_one_probs(labels)
  predicted_scores_top_one_probs = _get_top_one_probs(predicted_scores)

  if weights is None:
    loss = tf.reduce_mean(
      _get_listnet_cross_entropy(labels=labels_top_one_probs,
      logits=predicted_scores_top_one_probs))
    return loss

  loss = tf.reduce_mean(
    _get_listnet_cross_entropy(labels=labels_top_one_probs,
    logits=predicted_scores_top_one_probs) * weights) / tf.reduce_mean(weights)
  return loss


def _get_top_one_probs(labels):
  """
  Used in listnet top-one probabilities
  for a query/batch/batchPreidictionRequest
  (essentially a softmax function)
  """
  expon_labels = tf.exp(labels)
  expon_label_sum = tf.reduce_sum(expon_labels)
  # expon_label_sum is safe as a denominator
  attentions = expon_labels / expon_label_sum
  return attentions


def _get_listnet_cross_entropy(labels, logits):
  """
  Used in listnet
  cross entropy on top-one probabilities
  between ideal/label top-one probabilities
  and predicted/logits top-one probabilities
  for a query/batch/batchPreidictionRequest
  """
  # it is safe to use log on logits
  # that come from _get_top_one_probs
  # do not use this function directly elsewhere
  results = (-1) * labels * math_fns.safe_log(logits)
  return results


def get_pointwise_loss(labels, predicted_scores, weights=None):
  """
  Pointwise learning-to-rank pointwise loss
  Args:
    labels: a dense tensor of shape [n_data, 1]
    n_data is the number of tweet candidates in a BatchPredictionRequest
    predicted_scores: a dense tensor of same shape and type as labels
    weights: a dense tensor of the same shape as labels
  Returns:
    average loss
  """
  if weights is None:
    loss = tf.reduce_mean(
      tf.nn.sigmoid_cross_entropy_with_logits(labels=labels,
      logits=predicted_scores))
    return loss
  loss = tf.reduce_mean(tf.nn.sigmoid_cross_entropy_with_logits(labels=labels,
        logits=predicted_scores) * weights) / tf.reduce_mean(weights)
  return loss
