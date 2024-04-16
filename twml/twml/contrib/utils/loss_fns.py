 mport tensorflow.compat.v1 as tf
from twml.contr b.ut ls  mport masks, math_fns


def get_pa r_loss(pa rw se_label_scores, pa rw se_pred cted_scores,
                  params):
  """
  Pa w se learn ng-to-rank ranknet loss
  C ck paper https://www.m crosoft.com/en-us/research/publ cat on/
  learn ng-to-rank-us ng-grad ent-descent/
  for more  nformat on
  Args:
    pa rw se_label_scores: a dense tensor of shape [n_data, n_data]
    pa rw se_pred cted_scores: a dense tensor of shape [n_data, n_data]
    n_data  s t  number of t et cand dates  n a BatchPred ct onRequest
    params: network para ters
  mask opt ons: full_mask and d ag_mask
  Returns:
    average loss over pa rs def ned by t  masks
  """
  n_data = tf.shape(pa rw se_label_scores)[0]
   f params.mask == "full_mask":
    # full_mask that only covers pa rs that have d fferent labels
    # (all pa rw se_label_scores = 0.5: selfs and sa  labels are 0s)
    mask, pa r_count = masks.full_mask(n_data, pa rw se_label_scores)
  else:
    # d ag_mask that covers all pa rs
    # (only selfs/d ags are 0s)
    mask, pa r_count = masks.d ag_mask(n_data, pa rw se_label_scores)

  # pa rw se s gmo d_cross_entropy_w h_log s loss
  loss = tf.cond(tf.equal(pa r_count, 0), lambda: 0.,
    lambda: _get_average_cross_entropy_loss(pa rw se_label_scores,
      pa rw se_pred cted_scores, mask, pa r_count))
  return loss


def get_lambda_pa r_loss(pa rw se_label_scores, pa rw se_pred cted_scores,
                  params, swapped_ndcg):
  """
  Pa w se learn ng-to-rank lambdarank loss
  faster than t  prev ous grad ent  thod
  Note: t  loss depends on ranknet cross-entropy
  delta NDCG  s appl ed to ranknet cross-entropy
   nce,    s st ll a grad ent descent  thod
  C ck paper http://c eseerx. st.psu.edu/v ewdoc/
  download?do =10.1.1.180.634&rep=rep1&type=pdf for more  nformat on
  for more  nformat on
  Args:
    pa rw se_label_scores: a dense tensor of shape [n_data, n_data]
    pa rw se_pred cted_scores: a dense tensor of shape [n_data, n_data]
    n_data  s t  number of t et cand dates  n a BatchPred ct onRequest
    params: network para ters
    swapped_ndcg: swapped ndcg of shape [n_data, n_data]
    ndcg values w n swapp ng each pa r  n t  pred ct on rank ng order
  mask opt ons: full_mask and d ag_mask
  Returns:
    average loss over pa rs def ned by t  masks
  """
  n_data = tf.shape(pa rw se_label_scores)[0]
   f params.mask == "full_mask":
    # full_mask that only covers pa rs that have d fferent labels
    # (all pa rw se_label_scores = 0.5: selfs and sa  labels are 0s)
    mask, pa r_count = masks.full_mask(n_data, pa rw se_label_scores)
  else:
    # d ag_mask that covers all pa rs
    # (only selfs/d ags are 0s)
    mask, pa r_count = masks.d ag_mask(n_data, pa rw se_label_scores)

  # pa rw se s gmo d_cross_entropy_w h_log s loss
  loss = tf.cond(tf.equal(pa r_count, 0), lambda: 0.,
    lambda: _get_average_cross_entropy_loss(pa rw se_label_scores,
      pa rw se_pred cted_scores, mask, pa r_count, swapped_ndcg))
  return loss


def _get_average_cross_entropy_loss(pa rw se_label_scores, pa rw se_pred cted_scores,
                                    mask, pa r_count, swapped_ndcg=None):
  """
  Average t  loss for a batchPred ct onRequest based on a des red number of pa rs
  """
  loss = tf.nn.s gmo d_cross_entropy_w h_log s(labels=pa rw se_label_scores,
    log s=pa rw se_pred cted_scores)
  loss = mask * loss
   f swapped_ndcg  s not None:
    loss = loss * swapped_ndcg
  loss = tf.reduce_sum(loss) / pa r_count
  return loss


def get_l stmle_loss(labels, pred cted_scores):
  r"""
  l stw se learn ng-to-rank l stMLE loss
  Note: S mpl f ed MLE formula  s used  n  re (om  t  proof  n  re)
  \sum_{s=1}^{n-1} (-pred cted_scores + ln(\sum_{ =s}^n exp(pred cted_scores)))
  n  s tf.shape(pred cted_scores)[0]
  C ck paper http:// cml2008.cs. ls nk .f /papers/167.pdf for more  nformat on
  Args:
    labels: a dense tensor of shape [n_data, 1]
    n_data  s t  number of t et cand dates  n a BatchPred ct onRequest
    pred cted_scores: a dense tensor of sa  shape and type as labels
  Returns:
    average loss
  """
  labels = tf.reshape(labels, [-1, 1])
  n_data = tf.shape(labels)[0]
  pred cted_scores = tf.reshape(pred cted_scores, [-1, 1])

  pred cted_scores_ordered_by_labels = _get_ordered_pred cted_scores(labels,
    pred cted_scores, n_data)

  loss = (-1) * tf.reduce_sum(pred cted_scores)
  # sum over 1 to n_data - 1
  temp = tf.gat r(pred cted_scores_ordered_by_labels, [n_data - 1])
  temp = tf.reshape(temp, [])
  loss = tf.add(loss, temp)

  exps = tf.exp(pred cted_scores_ordered_by_labels)
  exp_sum = tf.reduce_sum(exps)
  # cl p exp_sum for safer log
  loss = tf.add(loss, math_fns.safe_log(exp_sum))

   erat on = tf.constant(0)

  def _cond( erat on, loss, exp_sum, exp):
    return tf.less( erat on, n_data - 2)

  def _gen_loop_body():
    def loop_body( erat on, loss, exp_sum, exps):
      temp = tf.gat r(exps, [ erat on])
      temp = tf.reshape(temp, [])
      exp_sum = tf.subtract(exp_sum, temp)
      # cl p exp_sum for safer log
      loss = tf.add(loss, math_fns.safe_log(exp_sum))
      return tf.add( erat on, 1), loss, exp_sum, exps
    return loop_body

   erat on, loss, exp_sum, exps = tf.wh le_loop(_cond, _gen_loop_body(),
    ( erat on, loss, exp_sum, exps))
  loss = loss / tf.cast(n_data, dtype=tf.float32)
  return loss


def _get_ordered_pred cted_scores(labels, pred cted_scores, n_data):
  """
  Order pred cted_scores based on sorted labels
  """
  sorted_labels, ordered_labels_ nd ces = tf.nn.top_k(
    tf.transpose(labels), k=n_data)
  ordered_labels_ nd ces = tf.transpose(ordered_labels_ nd ces)
  pred cted_scores_ordered_by_labels = tf.gat r_nd(pred cted_scores,
    ordered_labels_ nd ces)
  return pred cted_scores_ordered_by_labels


def get_attrank_loss(labels, pred cted_scores,   ghts=None):
  """
  Mod f ed l stw se learn ng-to-rank AttRank loss
  C ck paper https://arx v.org/abs/1804.05936 for more  nformat on
  Note: t re  s an  ncons stency bet en t  paper state nt and
  t  r publ c code
  Args:
    labels: a dense tensor of shape [n_data, 1]
    n_data  s t  number of t et cand dates  n a BatchPred ct onRequest
    pred cted_scores: a dense tensor of sa  shape and type as labels
      ghts: a dense tensor of t  sa  shape as labels
  Returns:
    average loss
  """
  # T  authors  m ple nted t  follow ng, wh ch  s bas cally l stnet
  # attent on_labels = _get_attent ons(labels)
  # attent on_labels = tf.reshape(attent on_labels, [1, -1])
  # pred cted_scores = tf.reshape(pred cted_scores, [1, -1])
  # loss = tf.reduce_ an(tf.nn.softmax_cross_entropy_w h_log s(labels=attent on_labels,
  #   log s=pred cted_scores))

  # T  paper proposed t  follow ng
  # attent on_labels = _get_attent ons(labels)
  # # Ho ver t  follow ng l ne  s wrong based on t  r state nt
  # # as _get_attent ons can g ve 0 results w n  nput < 0
  # # and t  result cannot be used  n _get_attrank_cross_entropy
  # # log(a_ ^S)
  # # attent on_pred cted_scores = _get_attent ons(pred cted_scores)
  # loss = _get_attrank_cross_entropy(attent on_labels, attent on_pred cted_scores)
  # # t  range of attent on_pred cted_scores  s [0, 1)
  # # t  g ves s gmo d [0.5, 0.732)
  # #  nce,    s not good to use  n s gmo d_cross_entropy_w h_log s e  r

  #  mple nted t  follow ng  nstead
  # _get_attent ons  s appl ed to labels
  # softmax  s appl ed to pred cted_scores
  reshaped_labels = tf.reshape(labels, [1, -1])
  attent on_labels = _get_attent ons(reshaped_labels)
  reshaped_pred cted_scores = tf.reshape(pred cted_scores, [1, -1])
  attent on_pred cted_scores = tf.nn.softmax(reshaped_pred cted_scores)
  loss = _get_attrank_cross_entropy(attent on_labels, attent on_pred cted_scores)
  return loss


def _get_attent ons(raw_scores):
  """
  Used  n attent on   ghts  n AttRank loss
  for a query/batch/batchPre d ct onRequest
  (a rect f ed softmax funct on)
  """
  not_cons der = tf.less_equal(raw_scores, 0)
  mask = tf.ones(tf.shape(raw_scores)) - tf.cast(not_cons der, dtype=tf.float32)
  mask = tf.cast(mask, dtype=tf.float32)
  expon_labels = mask * tf.exp(raw_scores)

  expon_label_sum = tf.reduce_sum(expon_labels)
  # expon_label_sum  s safe as a denom nator
  attent ons = math_fns.safe_d v(expon_labels, expon_label_sum)
  return attent ons


def _get_attrank_cross_entropy(labels, log s):
  # log s  s not safe based on t  r sate nt
  # do not use t  funct on d rectly elsew re
  results = labels * math_fns.safe_log(log s) + (1 - labels) * math_fns.safe_log(1 - log s)
  results = (-1) * results
  results = tf.reduce_ an(results)
  return results


def get_l stnet_loss(labels, pred cted_scores,   ghts=None):
  """
  L stw se learn ng-to-rank l stet loss
  C ck paper https://www.m crosoft.com/en-us/research/
  wp-content/uploads/2016/02/tr-2007-40.pdf
  for more  nformat on
  Args:
    labels: a dense tensor of shape [n_data, 1]
    n_data  s t  number of t et cand dates  n a BatchPred ct onRequest
    pred cted_scores: a dense tensor of sa  shape and type as labels
      ghts: a dense tensor of t  sa  shape as labels
  Returns:
    average loss
  """
  # top one probab l y  s t  sa  as softmax
  labels_top_one_probs = _get_top_one_probs(labels)
  pred cted_scores_top_one_probs = _get_top_one_probs(pred cted_scores)

   f   ghts  s None:
    loss = tf.reduce_ an(
      _get_l stnet_cross_entropy(labels=labels_top_one_probs,
      log s=pred cted_scores_top_one_probs))
    return loss

  loss = tf.reduce_ an(
    _get_l stnet_cross_entropy(labels=labels_top_one_probs,
    log s=pred cted_scores_top_one_probs) *   ghts) / tf.reduce_ an(  ghts)
  return loss


def _get_top_one_probs(labels):
  """
  Used  n l stnet top-one probab l  es
  for a query/batch/batchPre d ct onRequest
  (essent ally a softmax funct on)
  """
  expon_labels = tf.exp(labels)
  expon_label_sum = tf.reduce_sum(expon_labels)
  # expon_label_sum  s safe as a denom nator
  attent ons = expon_labels / expon_label_sum
  return attent ons


def _get_l stnet_cross_entropy(labels, log s):
  """
  Used  n l stnet
  cross entropy on top-one probab l  es
  bet en  deal/label top-one probab l  es
  and pred cted/log s top-one probab l  es
  for a query/batch/batchPre d ct onRequest
  """
  #    s safe to use log on log s
  # that co  from _get_top_one_probs
  # do not use t  funct on d rectly elsew re
  results = (-1) * labels * math_fns.safe_log(log s)
  return results


def get_po ntw se_loss(labels, pred cted_scores,   ghts=None):
  """
  Po ntw se learn ng-to-rank po ntw se loss
  Args:
    labels: a dense tensor of shape [n_data, 1]
    n_data  s t  number of t et cand dates  n a BatchPred ct onRequest
    pred cted_scores: a dense tensor of sa  shape and type as labels
      ghts: a dense tensor of t  sa  shape as labels
  Returns:
    average loss
  """
   f   ghts  s None:
    loss = tf.reduce_ an(
      tf.nn.s gmo d_cross_entropy_w h_log s(labels=labels,
      log s=pred cted_scores))
    return loss
  loss = tf.reduce_ an(tf.nn.s gmo d_cross_entropy_w h_log s(labels=labels,
        log s=pred cted_scores) *   ghts) / tf.reduce_ an(  ghts)
  return loss
