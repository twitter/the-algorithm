 mport tensorflow.compat.v1 as tf
from tensorflow.python.ops  mport array_ops, math_ops


# Cop ed from  tr cs_ mpl.py
# https://g hub.com/tensorflow/tensorflow/blob/master/tensorflow/python/ops/ tr cs_ mpl.py#L216
def safe_d v(nu rator, denom nator, na =None):
  """
  Example usage: calculat ng NDCG = DCG /  DCG to handle cases w n
   DCG = 0 returns 0  nstead of  nf n y 
  Do not use t  d v d ng func on unless   makes sense to y  problem
  D v des two tensors ele nt-w se, returns 0  f t  denom nator  s <= 0.
  Args:
    nu rator: a real `Tensor`.
    denom nator: a real `Tensor`, w h dtype match ng `nu rator`.
    na : Na  for t  returned op.
  Returns:
    0  f `denom nator` <= 0, else `nu rator` / `denom nator`
  """
  t = math_ops.trued v(nu rator, denom nator)
  zero = array_ops.zeros_l ke(t, dtype=denom nator.dtype)
  cond  on = math_ops.greater(denom nator, zero)
  zero = math_ops.cast(zero, t.dtype)
  return array_ops.w re(cond  on, t, zero, na =na )


def cal_ndcg(label_scores, pred cted_scores, top_k_ nt=1):
  """
  Calculate NDCG score for top_k_ nt rank ng pos  ons
  Args:
    label_scores: a real `Tensor`.
    pred cted_scores: a real `Tensor`, w h dtype match ng label_scores
    top_k_ nt: An  nt or an  nt `Tensor`.
  Returns:
    a `Tensor` that holds DCG /  DCG.
  """
  sorted_labels, pred cted_order = _get_rank ng_orders(
    label_scores, pred cted_scores, top_k_ nt=top_k_ nt)

  pred cted_relevance = _get_relevance_scores(pred cted_order)
  sorted_relevance = _get_relevance_scores(sorted_labels)

  cg_d scount = _get_cg_d scount(top_k_ nt)

  dcg = _dcg_ dcg(pred cted_relevance, cg_d scount)
   dcg = _dcg_ dcg(sorted_relevance, cg_d scount)
  # t  ndcg score of t  batch
  #  dcg  s 0  f label_scores are all 0
  ndcg = safe_d v(dcg,  dcg, 'one_ndcg')
  return ndcg


def cal_swapped_ndcg(label_scores, pred cted_scores, top_k_ nt):
  """
  Calculate swapped NDCG score  n Lambda Rank for full/top k rank ng pos  ons
  Args:
    label_scores: a real `Tensor`.
    pred cted_scores: a real `Tensor`, w h dtype match ng label_scores
    top_k_ nt: An  nt or an  nt `Tensor`. 
  Returns:
    a `Tensor` that holds swapped NDCG by .
  """
  sorted_labels, pred cted_order = _get_rank ng_orders(
    label_scores, pred cted_scores, top_k_ nt=top_k_ nt)

  pred cted_relevance = _get_relevance_scores(pred cted_order)
  sorted_relevance = _get_relevance_scores(sorted_labels)

  cg_d scount = _get_cg_d scount(top_k_ nt)

  # cg_d scount  s safe as a denom nator
  dcg_k = pred cted_relevance / cg_d scount
  dcg = tf.reduce_sum(dcg_k)

   dcg_k = sorted_relevance / cg_d scount
   dcg = tf.reduce_sum( dcg_k)

  ndcg = safe_d v(dcg,  dcg, 'ndcg_ n_lambdarank_tra n ng')

  # remove t  ga n from label   t n add t  ga n from label j
  t led_ j = tf.t le(dcg_k, [1, top_k_ nt])
  new_ j = (pred cted_relevance / tf.transpose(cg_d scount))

  t led_j  = tf.t le(tf.transpose(dcg_k), [top_k_ nt, 1])
  new_j  = tf.transpose(pred cted_relevance) / cg_d scount

  #  f swap   and j, remove t  stale cg for  , t n add t  new cg for  ,
  # remove t  stale cg for j, and t n add t  new cg for j
  new_dcg = dcg - t led_ j + new_ j - t led_j  + new_j 

  new_ndcg = safe_d v(new_dcg,  dcg, 'new_ndcg_ n_lambdarank_tra n ng')
  swapped_ndcg = tf.abs(ndcg - new_ndcg)
  return swapped_ndcg


def _dcg_ dcg(relevance_scores, cg_d scount):
  """
  Calculate DCG scores for top_k_ nt rank ng pos  ons
  Args:
    relevance_scores: a real `Tensor`.
    cg_d scount: a real `Tensor`, w h dtype match ng relevance_scores
  Returns:
    a `Tensor` that holds \\sum_{ =1}^k \frac{relevance_scores_k}{cg_d scount}  
  """
  # cg_d scount  s safe
  dcg_k = relevance_scores / cg_d scount
  return tf.reduce_sum(dcg_k)


def _get_rank ng_orders(label_scores, pred cted_scores, top_k_ nt=1):
  """
  Calculate DCG scores for top_k_ nt rank ng pos  ons
  Args:
    label_scores: a real `Tensor`.
    pred cted_scores: a real `Tensor`, w h dtype match ng label_scores
    top_k_ nt: an  nteger or an  nt `Tensor`.
  Returns:
    two `Tensors` that hold sorted_labels: t  ground truth relevance socres
    and pred cted_order: relevance socres based on sorted pred cted_scores
  """
  # sort pred ct ons_scores and label_scores
  # s ze [batch_s ze/num of DataRecords, 1]
  label_scores = tf.reshape(label_scores, [-1, 1])
  pred cted_scores = tf.reshape(pred cted_scores, [-1, 1])
  # sorted_labels cont ans t  relevance scores of t  correct order
  sorted_labels, ordered_labels_ nd ces = tf.nn.top_k(
    tf.transpose(label_scores), k=top_k_ nt)
  sorted_labels = tf.transpose(sorted_labels)
  # sort pred c ons and use t   nd ces to obta n t  relevance scores of t  pred cted order
  sorted_pred ct ons, ordered_pred ct ons_ nd ces = tf.nn.top_k(
    tf.transpose(pred cted_scores), k=top_k_ nt)
  ordered_pred ct ons_ nd ces_for_labels = tf.transpose(ordered_pred ct ons_ nd ces)
  # pred cted_order cont ans t  relevance scores of t  pred cted order
  pred cted_order = tf.gat r_nd(label_scores, ordered_pred ct ons_ nd ces_for_labels)
  return sorted_labels, pred cted_order


def _get_cg_d scount(top_k_ nt=1):
  r"""
  Calculate d scounted ga n factor for rank ng pos  on t ll top_k_ nt
  Args:
    top_k_ nt: An  nt or an  nt `Tensor`.
  Returns:
    a `Tensor` that holds \log_{2}(  + 1),   \ n [1, k] 
  """
  log_2 = tf.log(tf.constant(2.0, dtype=tf.float32))
  # top_k_range needs to start from 1 to top_k_ nt
  top_k_range = tf.range(top_k_ nt) + 1
  top_k_range = tf.reshape(top_k_range, [-1, 1])
  # cast top_k_range to float
  top_k_range = tf.cast(top_k_range, dtype=tf.float32)
  cg_d scount = tf.log(top_k_range + 1.0) / log_2
  return cg_d scount


def _get_relevance_scores(scores):
  return 2 ** scores - 1


def safe_log(raw_scores, na =None):
  """
  Calculate log of a tensor, handl ng cases that
  raw_scores are close to 0s
  Args:
    raw_scores: An float `Tensor`.
  Returns:
    A float `Tensor` that hols t  safe log base e of  nput
  """
  eps lon = 1E-8
  cl pped_raw_scores = tf.max mum(raw_scores, eps lon)
  return tf.log(cl pped_raw_scores)
