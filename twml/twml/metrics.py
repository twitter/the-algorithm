"""
T  module conta ns custom tensorflow  tr cs used at Tw ter.
 s components conform to convent ons used by t  ``tf. tr cs`` module.

"""

from collect ons  mport OrderedD ct
from functools  mport part al

 mport numpy as np
 mport tensorboard as tb
 mport tensorflow.compat.v1 as tf


CLAMP_EPS LON = 0.00001


def total_  ght_ tr c(
    labels,
    pred ct ons,
      ghts=None,
     tr cs_collect ons=None,
    updates_collect ons=None,
    na =None):
  w h tf.var able_scope(na , 'total_  ght', (labels, pred ct ons,   ghts)):
    total_  ght = _ tr c_var able(na ='total_  ght', shape=[], dtype=tf.float64)

     f   ghts  s None:
        ghts = tf.cast(tf.s ze(labels), total_  ght.dtype, na ="default_  ght")
    else:
        ghts = tf.cast(  ghts, total_  ght.dtype)

    # add up t    ghts to get total   ght of t  eval set
    update_total_  ght = tf.ass gn_add(total_  ght, tf.reduce_sum(  ghts), na ="update_op")

    value_op = tf. dent y(total_  ght)
    update_op = tf. dent y(update_total_  ght)

     f  tr cs_collect ons:
      tf.add_to_collect ons( tr cs_collect ons, value_op)

     f updates_collect ons:
      tf.add_to_collect ons(updates_collect ons, update_op)

    return value_op, update_op


def num_samples_ tr c(
    labels,
    pred ct ons,
      ghts=None,
     tr cs_collect ons=None,
    updates_collect ons=None,
    na =None):
  w h tf.var able_scope(na , 'num_samples', (labels, pred ct ons,   ghts)):
    num_samples = _ tr c_var able(na ='num_samples', shape=[], dtype=tf.float64)
    update_num_samples = tf.ass gn_add(num_samples, tf.cast(tf.s ze(labels), num_samples.dtype), na ="update_op")

    value_op = tf. dent y(num_samples)
    update_op = tf. dent y(update_num_samples)

     f  tr cs_collect ons:
      tf.add_to_collect ons( tr cs_collect ons, value_op)

     f updates_collect ons:
      tf.add_to_collect ons(updates_collect ons, update_op)

    return value_op, update_op


def ctr(labels, pred ct ons,
          ghts=None,
         tr cs_collect ons=None,
        updates_collect ons=None,
        na =None):
  # pyl nt: d sable=unused-argu nt
  """
  Compute t    ghted average pos  ve sample rat o based on labels
  ( .e.   ghted average percentage of pos  ve labels).
  T  na  `ctr` (cl ck-through-rate)  s from legacy.

  Args:
    labels: t  ground truth value.
    pred ct ons: t  pred cted values, whose shape must match labels.  gnored for CTR computat on.
      ghts: opt onal   ghts, whose shape must match labels .   ght  s 1  f not set.
     tr cs_collect ons: opt onal l st of collect ons to add t   tr c  nto.
    updates_collect ons: opt onal l st of collect ons to add t  assoc ated update_op  nto.
    na : an opt onal var able_scope na .

  Return:
    ctr: A `Tensor` represent ng pos  ve sample rat o.
    update_op: A update operat on used to accumulate data  nto t   tr c.
  """
  return tf. tr cs. an(
    values=labels,
      ghts=  ghts,
     tr cs_collect ons= tr cs_collect ons,
    updates_collect ons=updates_collect ons,
    na =na )


def pred cted_ctr(labels, pred ct ons,
                    ghts=None,
                   tr cs_collect ons=None,
                  updates_collect ons=None,
                  na =None):
  # pyl nt: d sable=unused-argu nt
  """
  Compute t    ghted average pos  ve rat o based on pred ct ons,
  ( .e.   ghted averaged pred cted pos  ve probab l y).
  T  na  `ctr` (cl ck-through-rate)  s from legacy.

  Args:
    labels: t  ground truth value.
    pred ct ons: t  pred cted values, whose shape must match labels.  gnored for CTR computat on.
      ghts: opt onal   ghts, whose shape must match labels .   ght  s 1  f not set.
     tr cs_collect ons: opt onal l st of collect ons to add t   tr c  nto.
    updates_collect ons: opt onal l st of collect ons to add t  assoc ated update_op  nto.
    na : an opt onal var able_scope na .

  Return:
    pred cted_ctr: A `Tensor` represent ng t  pred cted pos  ve rat o.
    update_op: A update operat on used to accumulate data  nto t   tr c.
  """
  return tf. tr cs. an(
    values=pred ct ons,
      ghts=  ghts,
     tr cs_collect ons= tr cs_collect ons,
    updates_collect ons=updates_collect ons,
    na =na )


def pred ct on_std_dev(labels, pred ct ons,
                         ghts=None,
                        tr cs_collect ons=None,
                       updates_collect ons=None,
                       na =None):
  """
  Compute t    ghted standard dev at on of t  pred ct ons.
  Note - t   s not a conf dence  nterval  tr c.

  Args:
    labels: t  ground truth value.
    pred ct ons: t  pred cted values, whose shape must match labels.  gnored for CTR computat on.
      ghts: opt onal   ghts, whose shape must match labels .   ght  s 1  f not set.
     tr cs_collect ons: opt onal l st of collect ons to add t   tr c  nto.
    updates_collect ons: opt onal l st of collect ons to add t  assoc ated update_op  nto.
    na : an opt onal var able_scope na .

  Return:
     tr c value: A `Tensor` represent ng t  value of t   tr c on t  data accumulated so far.
    update_op: A update operat on used to accumulate data  nto t   tr c.
  """
  w h tf.var able_scope(na , 'pred_std_dev', (labels, pred ct ons,   ghts)):
    labels = tf.cast(labels, tf.float64)
    pred ct ons = tf.cast(pred ct ons, tf.float64)

     f   ghts  s None:
        ghts = tf.ones(shape=tf.shape(labels), dtype=tf.float64, na ="default_  ght")
    else:
        ghts = tf.cast(  ghts, tf.float64)

    # State kept dur ng stream ng of examples
    total_  ghted_preds = _ tr c_var able(
        na ='total_  ghted_preds', shape=[], dtype=tf.float64)
    total_  ghted_preds_sq = _ tr c_var able(
        na ='total_  ghted_preds_sq', shape=[], dtype=tf.float64)
    total_  ghts = _ tr c_var able(
        na ='total_  ghts', shape=[], dtype=tf.float64)

    # Update state
    update_total_  ghted_preds = tf.ass gn_add(total_  ghted_preds, tf.reduce_sum(  ghts * pred ct ons))
    update_total_  ghted_preds_sq = tf.ass gn_add(total_  ghted_preds_sq, tf.reduce_sum(  ghts * pred ct ons * pred ct ons))
    update_total_  ghts = tf.ass gn_add(total_  ghts, tf.reduce_sum(  ghts))

    # Compute output
    def compute_output(tot_w, tot_wp, tot_wpp):
      return tf.math.sqrt(tot_wpp / tot_w - (tot_wp / tot_w) ** 2)
    std_dev_est = compute_output(total_  ghts, total_  ghted_preds, total_  ghted_preds_sq)
    update_std_dev_est = compute_output(update_total_  ghts, update_total_  ghted_preds, update_total_  ghted_preds_sq)

     f  tr cs_collect ons:
      tf.add_to_collect ons( tr cs_collect ons, std_dev_est)

     f updates_collect ons:
      tf.add_to_collect ons(updates_collect ons, update_std_dev_est)

    return std_dev_est, update_std_dev_est


def _get_arce_pred ct ons(pred ct ons,   ghts, label_  ghted, labels,
                         up_  ght, deprecated_rce,
                         total_pos  ve, update_total_pos  ve):
  """
  Returns t  ARCE pred ct ons, total_pos  ve, update_total_pos  ve and   ghts
  used by t  rest of t  twml. tr cs.rce  tr c computat on.
  """
  pred ct ons_  ghted = tf.mult ply(pred ct ons,   ghts, na ="  ghted_preds")
  label_  ghted_comp = tf.subtract(tf.reduce_sum(  ghts), tf.reduce_sum(label_  ghted))
  pred_  ght_comp = tf.subtract(tf.reduce_sum(  ghts), tf.reduce_sum(pred ct ons_  ghted))
  normal zer_comp = label_  ghted_comp / pred_  ght_comp

   f up_  ght  s False:
    total_pos  ve_un  ghted = _ tr c_var able(
      na ='total_pos  ve_un  ghted', shape=[], dtype=tf.float32)

    update_total_pos  ve_un  ghted = tf.ass gn_add(
      total_pos  ve_un  ghted, tf.reduce_sum(labels),
      na ="total_pos  ve_un  ghted_update")

     f deprecated_rce:
      normal zer = tf.reduce_sum(labels) / tf.reduce_sum(label_  ghted)
    else:
      # sum of labels / sum of   ghted labels
      normal zer = update_total_pos  ve_un  ghted / update_total_pos  ve

    label_comp = tf.subtract(tf.to_float(tf.s ze(labels)), tf.reduce_sum(labels))
    normal zer_comp = label_comp / label_  ghted_comp

    # note that up_  ght=True changes t se for t  rest of t  twml. tr c.rce computat on
      ghts = tf.ones(shape=tf.shape(labels), dtype=tf.float32, na ="default_  ght")
    total_pos  ve = total_pos  ve_un  ghted
    update_total_pos  ve = update_total_pos  ve_un  ghted
  else:
     f deprecated_rce:
      normal zer = tf.reduce_sum(label_  ghted) / tf.reduce_sum(pred ct ons_  ghted)
    else:
      # normal zer used for NRCE (and ARCE w h up_  ght=True)
      total_pred ct on = _ tr c_var able(na ='total_pred ct on', shape=[], dtype=tf.float32)

      # update t  var able hold ng t  sum of   ghted pred ct ons
      update_total_pred ct on = tf.ass gn_add(
        total_pred ct on, tf.reduce_sum(pred ct ons_  ghted), na ="total_pred ct on_update")

      # t  used to be tf.reduce_sum(label_  ghted) / tf.reduce_sum(pred ct ons_  ghted)
      # but    asure normal zer over batch was too fla d an approx mat on.
      normal zer = update_total_pos  ve / update_total_pred ct on

  pred_comp = tf.subtract(tf.ones(shape=tf.shape(labels), dtype=tf.float32), pred ct ons)
  pred_comp_norm = tf.mult ply(pred_comp, normal zer_comp, na ="normal zed_pred ct ons_comp")
  pred_num = tf.mult ply(pred ct ons, normal zer, na ="normal zed_pred_nu rator")
  pred_denom = tf.add(pred_num, pred_comp_norm, na ="normal zed_pred_denom nator")
  pred ct ons = pred_num / pred_denom

  return pred ct ons, total_pos  ve, update_total_pos  ve,   ghts


def rce(labels, pred ct ons,
          ghts=None,
        normal ze=False,
        arce=False,
        up_  ght=True,
         tr cs_collect ons=None,
        updates_collect ons=None,
        na =None,
        deprecated_rce=False):
  """
  Compute t  relat ve cross entropy (RCE).
  T  RCE  s a relat ve  asure nt compared to t  basel ne model's performance.
  T  basel ne model always pred cts average cl ck-through-rate (CTR).
  T  RCE  asures,  n percentage, how much better t  pred ct ons are, compared
  to t  basel ne model,  n terms of cross entropy loss.

  y = label; p = pred ct on;
  b nary cross entropy = y * log(p) + (1-y) * log(1-p)

  Args:
    labels:
      t  ground true value.
    pred ct ons:
      t  pred cted values, whose shape must match labels.
      ghts:
      opt onal   ghts, whose shape must match labels .   ght  s 1  f not set.
    normal ze:
       f set to true, produce NRCEs used at Tw ter. (normal ze preds by   ghts f rst)
      NOTE:  f   don't understand what NRCE  s, please don't use  .
    arce:
       f set to true, produces `ARCE <http://go/arce>`_.
      T  can only be act vated  f `normal ze=True`.
    up_  ght:
       f set to true, produces arce  n t  up_  ghted space (cons ders CTR after up_  ght ng
      data), wh le False g ves arce  n t  or g nal space (only cons ders CTR before up_  ght ng).
       n t  actual vers on, t  flag can only be act vated  f arce  s True.
      Not ce that t  actual vers on of NRCE corresponds to up_  ght=True.
     tr cs_collect ons:
      opt onal l st of collect ons to add t   tr c  nto.
    updates_collect ons:
      opt onal l st of collect ons to add t  assoc ated update_op  nto.
    na :
      an opt onal var able_scope na .
    deprecated_rce:
      enables t  prev ous NRCE/ARCE calculat ons wh ch calculated so  label  tr cs
      on t  batch  nstead of on all batc s seen so far. Note that t  older  tr c
      calculat on  s less stable, espec ally for smaller batch s zes.   should probably
      never have to set t  to True.

  Return:
    rce_value:
      A ``Tensor`` represent ng t  RCE.
    update_op:
      A update operat on used to accumulate data  nto t   tr c.

  .. note:: Must have at least 1 pos  ve and 1 negat ve sample accumulated,
     or RCE w ll co  out as NaN.
  """
  w h tf.var able_scope(na , 'rce', (labels, pred ct ons,   ghts)):
    labels = tf.to_float(labels, na ="label_to_float")
    pred ct ons = tf.to_float(pred ct ons, na ="pred ct ons_to_float")

     f   ghts  s None:
        ghts = tf.ones(shape=tf.shape(labels), dtype=tf.float32, na ="default_  ght")
    else:
        ghts = tf.to_float(  ghts, na ="  ght_to_float")

    total_pos  ve = _ tr c_var able(na ='total_pos  ve', shape=[], dtype=tf.float32)
    total_loss = _ tr c_var able(na ='total_loss', shape=[], dtype=tf.float32)
    total_  ght = _ tr c_var able(na ='total_  ght', shape=[], dtype=tf.float32)

    label_  ghted = tf.mult ply(labels,   ghts, na ="  ghted_label")

    update_total_pos  ve = tf.ass gn_add(
      total_pos  ve, tf.reduce_sum(label_  ghted), na ="total_pos_update")

     f arce:
       f normal ze  s False:
        ra se ValueError('T  conf gurat on of para ters  s not actually allo d')

      pred ct ons, total_pos  ve, update_total_pos  ve,   ghts = _get_arce_pred ct ons(
        pred ct ons=pred ct ons,   ghts=  ghts, deprecated_rce=deprecated_rce,
        label_  ghted=label_  ghted, labels=labels, up_  ght=up_  ght,
        total_pos  ve=total_pos  ve, update_total_pos  ve=update_total_pos  ve)

    el f normal ze:
      pred ct ons_  ghted = tf.mult ply(pred ct ons,   ghts, na ="  ghted_preds")

       f deprecated_rce:
        normal zer = tf.reduce_sum(label_  ghted) / tf.reduce_sum(pred ct ons_  ghted)
      else:
        total_pred ct on = _ tr c_var able(na ='total_pred ct on', shape=[], dtype=tf.float32)

        # update t  var able hold ng t  sum of   ghted pred ct ons
        update_total_pred ct on = tf.ass gn_add(
          total_pred ct on, tf.reduce_sum(pred ct ons_  ghted), na ="total_pred ct on_update")

        # t  used to be tf.reduce_sum(label_  ghted) / tf.reduce_sum(pred ct ons_  ghted)
        # but    asure normal zer over batch was too fla d an approx mat on.
        normal zer = update_total_pos  ve / update_total_pred ct on

      # NRCE
      pred ct ons = tf.mult ply(pred ct ons, normal zer, na ="normal zed_pred ct ons")

    # clamp pred ct ons to keep log(p) stable
    cl p_p = tf.cl p_by_value(pred ct ons, CLAMP_EPS LON, 1.0 - CLAMP_EPS LON, na ="cl p_p")
    logloss = _b nary_cross_entropy(pred=cl p_p, target=labels, na ="logloss")

    logloss_  ghted = tf.mult ply(logloss,   ghts, na ="  ghted_logloss")

    update_total_loss = tf.ass gn_add(
      total_loss, tf.reduce_sum(logloss_  ghted), na ="total_loss_update")
    update_total_  ght = tf.ass gn_add(
      total_  ght, tf.reduce_sum(  ghts), na ="total_  ght_update")

    #  tr c value retr eval subgraph
    ctr1 = tf.trued v(total_pos  ve, total_  ght, na ="ctr")
    # Note:   don't have to keep runn ng averages for comput ng basel ne CE. Because t  pred ct on
    #  s constant for every sample,   can s mpl fy   to t  formula below.
    basel ne_ce = _b nary_cross_entropy(pred=ctr1, target=ctr1, na ="basel ne_ce")
    pred_ce = tf.trued v(total_loss, total_  ght, na ="pred_ce")

    rce_t = tf.mult ply(
      1.0 - tf.trued v(pred_ce, basel ne_ce),
      100,
      na ="rce")

    #  tr c update subgraph
    ctr2 = tf.trued v(update_total_pos  ve, update_total_  ght, na ="ctr_update")
    # Note:   don't have to keep runn ng averages for comput ng basel ne CE. Because t  pred ct on
    #  s constant for every sample,   can s mpl fy   to t  formula below.
    basel ne_ce2 = _b nary_cross_entropy(pred=ctr2, target=ctr2, na ="basel ne_ce_update")
    pred_ce2 = tf.trued v(update_total_loss, update_total_  ght, na ="pred_ce_update")

    update_op = tf.mult ply(
      1.0 - tf.trued v(pred_ce2, basel ne_ce2),
      100,
      na ="update_op")

     f  tr cs_collect ons:
      tf.add_to_collect ons( tr cs_collect ons, rce_t)

     f updates_collect ons:
      tf.add_to_collect ons(updates_collect ons, update_op)

    return rce_t, update_op


def ce(p_true, p_est=None):
   f p_est  s None:
    p_est = p_true
  return _b nary_cross_entropy(pred=p_est, target=p_true, na =None)


def rce_transform(outputs, labels,   ghts):
  '''
  Construct an OrderedD ct of quant  es to aggregate over eval batc s
  outputs, labels,   ghts are TensorFlow tensors, and are assu d to
    be of shape [N] for batch_s ze = N
  Each entry  n t  output OrderedD ct should also be of shape [N]
  '''
  out_vals = OrderedD ct()
  out_vals['  ghted_loss'] =   ghts * ce(p_true=labels, p_est=outputs)
  out_vals['  ghted_labels'] = labels *   ghts
  out_vals['  ght'] =   ghts
  return out_vals


def rce_ tr c(aggregates):
  '''
   nput ``aggregates``  s an OrderedD ct w h t  sa  keys as those created
    by rce_transform(). T  d ct values are t  aggregates (reduce_sum)
    of t  values produced by rce_transform(), and should be scalars.
  output  s t  value of RCE
  '''
  # cummulat ve   ghted loss of model pred ct ons
  total_  ghted_loss = aggregates['  ghted_loss']
  total_  ghted_labels = aggregates['  ghted_labels']
  total_  ght = aggregates['  ght']

  model_average_loss = total_  ghted_loss / total_  ght
  basel ne_average_loss = ce(total_  ghted_labels / total_  ght)
  return 100.0 * (1 - model_average_loss / basel ne_average_loss)


def  tr c_std_err(labels, pred ct ons,
                     ghts=None,
                   transform=rce_transform,  tr c=rce_ tr c,
                    tr cs_collect ons=None,
                   updates_collect ons=None,
                   na ='rce_std_err'):
  """
  Compute t    ghted standard error of t  RCE  tr c on t  eval set.
  T  can be used for conf dence  ntervals and unpa red hypot s s tests.

  Args:
    labels: t  ground truth value.
    pred ct ons: t  pred cted values, whose shape must match labels.
      ghts: opt onal   ghts, whose shape must match labels .   ght  s 1  f not set.
    transform: a funct on of t  follow ng form:

      .. code-block:: python

        def transform(outputs, labels,   ghts):
          out_vals = OrderedD ct()
          ...
          return out_vals

      w re outputs, labels, and   ghts are all tensors of shape [eval_batch_s ze].
      T  returned OrderedD ct() should have values that are tensors of shape  [eval_batch_s ze].
      T se w ll be aggregated across many batc s  n t  eval dataset, to produce
      one scalar value per key of out_vals.
     tr c: a funct on of t  follow ng form

      .. code-block:: python

        def  tr c(aggregates):
          ...
          return  tr c_value

      w re aggregates  s an OrderedD ct() hav ng t  sa  keys created by transform().
      Each of t  correspond ng d ct values  s t  reduce_sum of t  values produced by
      transform(), and  s a TF scalar. T  return value should be a scalar represent ng
      t  value of t  des red  tr c.
     tr cs_collect ons: opt onal l st of collect ons to add t   tr c  nto.
    updates_collect ons: opt onal l st of collect ons to add t  assoc ated update_op  nto.
    na : an opt onal var able_scope na .

  Return:
     tr c value: A `Tensor` represent ng t  value of t   tr c on t  data accumulated so far.
    update_op: A update operat on used to accumulate data  nto t   tr c.
  """
  w h tf.var able_scope(na , ' tr c_std_err', (labels, pred ct ons,   ghts)):
    labels = tf.cast(labels, tf.float64)
    pred ct ons = tf.cast(pred ct ons, tf.float64)

     f   ghts  s None:
        ghts = tf.ones_l ke(labels, dtype=tf.float64, na ="default_  ght")
    else:
        ghts = tf.cast(  ghts, tf.float64)

    labels = tf.reshape(labels, [-1])
    pred ct ons = tf.reshape(pred ct ons, [-1])
    pred ct ons = tf.cl p_by_value(pred ct ons, CLAMP_EPS LON, 1.0 - CLAMP_EPS LON, na ="cl p_p")
      ghts = tf.reshape(  ghts, [-1])

    # f rst apply t  suppl ed transform funct on to t  output, label,   ght data
    # returns an OrderedD ct of 1xN tensors for N  nput samples
    # for each sample, compute f = transform(pred, l, w)
    transfor d = transform(pred ct ons, labels,   ghts)

    #   track 3 types of aggregate  nformat on
    # 1. total number of samples
    # 2. aggregated transfor d samples (mo nt1),  .e. sum(f)
    # 3. aggregated crosses of transfor d samples (mo nt2),  .e. sum(f*f^T)

    # count total number of samples
    sample_count = _ tr c_var able(
        na ='sample_count', shape=[], dtype=tf. nt64)
    update_sample_count = tf.ass gn_add(sample_count, tf.s ze(labels, out_type=sample_count.dtype))

    # compose t  ordered d ct  nto a s ngle vector
    # so f can be treated as a s ngle column vector rat r than a collect on of scalars
    N = len(transfor d)
    transfor d_vec = tf.stack(l st(transfor d.values()), ax s=1)

    # compute and update transfor d samples (1st order stat st cs)
    #  .e. accumulate f  nto F as F += sum(f)
    aggregates_1 = _ tr c_var able(
        na ='aggregates_1', shape=[N], dtype=tf.float64)
    update_aggregates_1 = tf.ass gn_add(aggregates_1, tf.reduce_sum(transfor d_vec, ax s=0))

    # compute and update crossed transfor d samples (2nd order stat st cs)
    #  .e. accumulate f*f^T  nto F2 as F2 += sum(f*transpose(f))
    aggregates_2 = _ tr c_var able(
        na ='aggregates_2', shape=[N, N], dtype=tf.float64)
    mo nt_2_temp = (
      tf.reshape(transfor d_vec, shape=[-1, N, 1])
      * tf.reshape(transfor d_vec, shape=[-1, 1, N])
    )
    update_aggregates_2 = tf.ass gn_add(aggregates_2, tf.reduce_sum(mo nt_2_temp, ax s=0))

    def compute_output(agg_1, agg_2, samp_cnt):
      # decompose t  aggregates back  nto a d ct to pass to t  user-suppl ed  tr c fn
      aggregates_d ct = OrderedD ct()
      for  , key  n enu rate(transfor d.keys()):
        aggregates_d ct[key] = agg_1[ ]

       tr c_value =  tr c(aggregates_d ct)

      # der vat ve of  tr c w h respect to t  1st order aggregates
      #  .e. d M(agg1) / d agg1
       tr c_pr   = tf.grad ents( tr c_value, agg_1, stop_grad ents=agg_1)

      # est mated covar ance of agg_1
      # cov(F) = sum(f*f^T) - (sum(f) * sum(f)^T) / N
      #     = agg_2 - (agg_1 * agg_1^T) / N
      N_covar ance_est mate = agg_2 - (
        tf.reshape(agg_1, shape=[-1, 1])
        @ tf.reshape(agg_1, shape=[1, -1])
        / tf.cast(samp_cnt, dtype=tf.float64)
      )

      # push N_covar ance_est mate through a l near zat on of  tr c around agg_1
      #  tr c var = transpose(d M(agg1) / d agg1) * cov(F) * (d M(agg1) / d agg1)
       tr c_var ance = (
        tf.reshape( tr c_pr  , shape=[1, -1])
        @ N_covar ance_est mate
        @ tf.reshape( tr c_pr  , shape=[-1, 1])
      )
      # result should be a s ngle ele nt, but t  matmul  s 2D
       tr c_var ance =  tr c_var ance[0][0]
       tr c_stderr = tf.sqrt( tr c_var ance)
      return  tr c_stderr

     tr c_stderr = compute_output(aggregates_1, aggregates_2, sample_count)
    update_ tr c_stderr = compute_output(update_aggregates_1, update_aggregates_2, update_sample_count)

     f  tr cs_collect ons:
      tf.add_to_collect ons( tr cs_collect ons,  tr c_stderr)

     f updates_collect ons:
      tf.add_to_collect ons(updates_collect ons, update_ tr c_stderr)

    return  tr c_stderr, update_ tr c_stderr


def lolly_nrce(labels, pred ct ons,
                 ghts=None,
                tr cs_collect ons=None,
               updates_collect ons=None,
               na =None):
  """
  Compute t  Lolly NRCE.

  Note: As t  NRCE calculat on uses Taylor expans on,   beco s  naccurate w n t  ctr  s large,
  espec ally w n t  adjusted ctr goes above 1.0.

  Calculat on:

  ::

    NRCE: lolly NRCE
    BCE: basel ne cross entropy
    NCE: normal zed cross entropy
    CE: cross entropy
    y_ : label of example  
    p_ : pred ct on of example  
    y: ctr
    p: average pred ct on
    a: normal zer

    Assu s any p_  and a * p_   s w h n [0, 1)
    NRCE = (1 - NCE / BCE) * 100
    BCE = - sum_ (y_  * log(y) + (1 - y_ ) * log(1 - y))
        = - (y * log(y) + (1 - y) * log(1 - y))
    a = y / p
    CE = - sum_ (y_  * log(p_ ) + (1 - y_ ) * log(1 - p_ ))
    NCE = - sum_ (y_  * log(a * p_ ) + (1 - y_ ) * log(1 - a * p_ ))
        = - sum_ (y_  * log(p_ ) + (1 - y_ ) * log(1 - p_ ))
          - sum_ (y_  * log(a))
          + sum_ ((1 - y_ ) * log(1 - p_ ))
          - sum_ ((1 - y_ ) * log(1 - a * p_ ))
        ~= CE - sum_ (y_ ) * log(a)
          + sum_ ((1 - y_ ) * (- sum_{j=1~5}(p_ ^j / j)))
          - sum_ ((1 - y_ ) * (- sum_{j=1~5}(a^j * p_ ^j / j)))
          # Takes 5  ems from t  Taylor expans on, can be  ncreased  f needed
          # Error for each example  s O(p_ ^6)
        = CE - sum_ (y_ ) * log(a)
          - sum_{j=1~5}(sum_ ((1 - y_ ) * p_ ^j) / j)
          + sum_{j=1~5}(sum_ ((1 - y_ ) * p_ ^j) * a^j / j)
        = CE - sum_ (y_ ) * log(a)
          + sum_{j=1~5}(sum_ ((1 - y_ ) * p_ ^j) * (a^j - 1) / j)

  Thus   keep track of CE, sum_ (y_ ), sum_ ((1 - y_ ) * p_ ^j) for j=1~5.
    also keep track of p and y by sum_ (y_ ), sum_ (p_ ), sum_ (1) so that
    can get a at t  end, wh ch leads to t  NRCE.

  NRCE uses ctr and average pctr to normal ze t  pctrs.
    removes t   mpact of pred ct on error from RCE.
  Usually NRCE  s h g r as t  pred ct on error  mpact on RCE  s negat ve.
  Remov ng pred ct on error  n   model can make RCE closer to NRCE and thus  mprove RCE.

   n Lolly NRCE   use ctr and average pctr of t  whole dataset.
    thus remove t  dataset level error  n NRCE calculat on.
   n t  case, w n   want to  mprove RCE to t  level of NRCE,
     s ach evable as dataset level pred ct on error  s easy to remove by cal brat on.
  Lolly NRCE  s thus a good est mate about t  potent al ga n by add ng cal brat on.

   n DBv2 NRCE,   use per-batch ctr and average pctr.   remove t  batch level error.
  T  error  s d ff cult to remove by model ng  mprove nt,
  at least not by s mple cal brat on.
    thus cannot  nd cate t  sa  opportun y as t  Lolly NRCE does.

  Args:
    labels:
      t  ground true value.
    pred ct ons:
      t  pred cted values, whose shape must match labels.
      ghts:
      opt onal   ghts, whose shape must match labels .   ght  s 1  f not set.
     tr cs_collect ons:
      opt onal l st of collect ons to add t   tr c  nto.
    updates_collect ons:
      opt onal l st of collect ons to add t  assoc ated update_op  nto.
    na :
      an opt onal var able_scope na .

  Return:
    rce_value:
      A ``Tensor`` represent ng t  RCE.
    update_op:
      A update operat on used to accumulate data  nto t   tr c.

  Note: Must have at least 1 pos  ve and 1 negat ve sample accumulated,
        or NRCE w ll co  out as NaN.
  """
  w h tf.var able_scope(na , "lolly_nrce", (labels, pred ct ons,   ghts)):
    labels = tf.to_float(labels, na ="label_to_float")
    pred ct ons = tf.to_float(pred ct ons, na ="pred ct ons_to_float")

     f   ghts  s None:
        ghts = tf.ones(shape=tf.shape(labels), dtype=tf.float32, na ="default_  ght")
    else:
        ghts = tf.to_float(  ghts, na ="  ght_to_float")

    pos  ve_  ghts = tf.mult ply(labels,   ghts, na ="pos  ve_  ghts")

    # clamp pred ct ons to keep log(p) stable
    cl p_pred ct ons = tf.cl p_by_value(
      pred ct ons,
      CLAMP_EPS LON,
      1.0 - CLAMP_EPS LON,
      na ="cl p_pred ct ons")
      ghted_pred ct ons = tf.mult ply(
      pred ct ons,   ghts,
      na ="  ghted_pred ct ons")

    logloss = _b nary_cross_entropy(pred=cl p_pred ct ons, target=labels, na ="logloss")
      ghted_logloss = tf.mult ply(logloss,   ghts, na ="  ghted_logloss")

    negat ves = tf.subtract(
      tf.ones(shape=tf.shape(labels), dtype=tf.float32),
      labels,
      na ="negat ves")
    negat ve_pred ct ons = tf.mult ply(
      pred ct ons,
      negat ves,
      na ="negat ve_pred ct ons")
      ghted_negat ve_pred ct ons = tf.mult ply(
      negat ve_pred ct ons,   ghts,
      na ="  ghted_negat ve_pred ct ons")
    negat ve_squared_pred ct ons = tf.mult ply(
      negat ve_pred ct ons,
      negat ve_pred ct ons,
      na ="negat ve_squared_pred ct ons")
      ghted_negat ve_squared_pred ct ons = tf.mult ply(
      negat ve_squared_pred ct ons,   ghts,
      na ="  ghted_negat ve_squared_pred ct ons")
    negat ve_cubed_pred ct ons = tf.mult ply(
      negat ve_squared_pred ct ons,
      negat ve_pred ct ons,
      na ="negat ve_cubed_pred ct ons")
      ghted_negat ve_cubed_pred ct ons = tf.mult ply(
      negat ve_cubed_pred ct ons,   ghts,
      na ="  ghted_negat ve_cubed_pred ct ons")
    negat ve_quart c_pred ct ons = tf.mult ply(
      negat ve_cubed_pred ct ons,
      negat ve_pred ct ons,
      na ="negat ve_quart c_pred ct ons")
      ghted_negat ve_quart c_pred ct ons = tf.mult ply(
      negat ve_quart c_pred ct ons,   ghts,
      na ="  ghted_negat ve_quart c_pred ct ons")
    negat ve_qu nt c_pred ct ons = tf.mult ply(
      negat ve_quart c_pred ct ons,
      negat ve_pred ct ons,
      na ="negat ve_qu nt c_pred ct ons")
      ghted_negat ve_qu nt c_pred ct ons = tf.mult ply(
      negat ve_qu nt c_pred ct ons,   ghts,
      na ="  ghted_negat ve_qu nt c_pred ct ons")

    # Tracked stats
    total_pos  ve = _ tr c_var able(na ="total_pos  ve", shape=[], dtype=tf.float32)
    total_  ght = _ tr c_var able(na ="total_  ght", shape=[], dtype=tf.float32)

    total_pred ct on = _ tr c_var able(na ="total_pred ct on", shape=[], dtype=tf.float32)

    total_negat ve_pred ct on = _ tr c_var able(
      na ="total_negat ve_pred ct on",
      shape=[], dtype=tf.float32)
    total_negat ve_squared_pred ct on = _ tr c_var able(
      na ="total_negat ve_squared_pred ct on",
      shape=[], dtype=tf.float32)
    total_negat ve_cubed_pred ct on = _ tr c_var able(
      na ="total_negat ve_cubed_pred ct on",
      shape=[], dtype=tf.float32)
    total_negat ve_quart c_pred ct on = _ tr c_var able(
      na ="total_negat ve_quart c_pred ct on",
      shape=[], dtype=tf.float32)
    total_negat ve_qu nt c_pred ct on = _ tr c_var able(
      na ="total_negat ve_qu nt c_pred ct on",
      shape=[], dtype=tf.float32)

    total_loss = _ tr c_var able(na ="total_loss", shape=[], dtype=tf.float32)

    # Update tracked stats
    update_total_pos  ve = tf.ass gn_add(
      total_pos  ve, tf.reduce_sum(pos  ve_  ghts), na ="total_pos  ve_update")
    update_total_  ght = tf.ass gn_add(
      total_  ght, tf.reduce_sum(  ghts), na ="total_  ght_update")
    update_total_pred ct on = tf.ass gn_add(
      total_pred ct on, tf.reduce_sum(  ghted_pred ct ons), na ="total_pred ct on_update")
    update_total_negat ve_pred ct on = tf.ass gn_add(
      total_negat ve_pred ct on,
      tf.reduce_sum(  ghted_negat ve_pred ct ons), na ="total_negat ve_pred ct on_update")
    update_total_negat ve_squared_pred ct on = tf.ass gn_add(
      total_negat ve_squared_pred ct on,
      tf.reduce_sum(  ghted_negat ve_squared_pred ct ons),
      na ="total_negat ve_squared_pred ct on_update")
    update_total_negat ve_cubed_pred ct on = tf.ass gn_add(
      total_negat ve_cubed_pred ct on,
      tf.reduce_sum(  ghted_negat ve_cubed_pred ct ons),
      na ="total_negat ve_cubed_pred ct on_update")
    update_total_negat ve_quart c_pred ct on = tf.ass gn_add(
      total_negat ve_quart c_pred ct on,
      tf.reduce_sum(  ghted_negat ve_quart c_pred ct ons),
      na ="total_negat ve_quart c_pred ct on_update")
    update_total_negat ve_qu nt c_pred ct on = tf.ass gn_add(
      total_negat ve_qu nt c_pred ct on,
      tf.reduce_sum(  ghted_negat ve_qu nt c_pred ct ons),
      na ="total_negat ve_qu nt c_pred ct on_update")
    update_total_loss = tf.ass gn_add(
      total_loss, tf.reduce_sum(  ghted_logloss), na ="total_loss_update")

    #  tr c value retr eval subgraph
    # ctr of t  batch
    pos  ve_rate = tf.trued v(total_pos  ve, total_  ght, na ="pos  ve_rate")
    # Note:   don't have to keep runn ng averages for comput ng basel ne CE. Because t  pred ct on
    #  s constant for every sample,   can s mpl fy   to t  formula below.
    basel ne_loss = _b nary_cross_entropy(
      pred=pos  ve_rate,
      target=pos  ve_rate,
      na ="basel ne_loss")

    # normal z ng rat o for nrce
    # calculated us ng total ctr and pctr so t  last batch has t  dataset ctr and pctr
    normal zer = tf.trued v(total_pos  ve, total_pred ct on, na ="normal zer")
    # Taylor expans on to calculate nl = - sum(y * log(p * a) + (1 - y) * log (1 - p * a))
    # log(1 - p * a) = -sum_{ =1~+ nf}(a^  * x^  /  )
    # log(1 - p) = -sum_{ =1~+ nf}(a^  * x^  /  )
    normal zed_loss = (
      total_loss -
      total_pos  ve * tf.log(normal zer) +
      total_negat ve_pred ct on * (normal zer - 1) +
      total_negat ve_squared_pred ct on * (normal zer * normal zer - 1) / 2 +
      total_negat ve_cubed_pred ct on *
      (normal zer * normal zer * normal zer - 1) / 3 +
      total_negat ve_quart c_pred ct on *
      (normal zer * normal zer * normal zer * normal zer - 1) / 4 +
      total_negat ve_qu nt c_pred ct on *
      (normal zer * normal zer * normal zer * normal zer * normal zer - 1) / 5)

    # average normal zed loss
    avg_loss = tf.trued v(normal zed_loss, total_  ght, na ="avg_loss")

    nrce_t = tf.mult ply(
      1.0 - tf.trued v(avg_loss, basel ne_loss),
      100,
      na ="lolly_nrce")

    #  tr c update subgraph
    update_pos  ve_rate = tf.trued v(
      update_total_pos  ve,
      update_total_  ght,
      na ="update_pos  ve_rate")
    # Note:   don't have to keep runn ng averages for comput ng basel ne CE. Because t  pred ct on
    #  s constant for every sample,   can s mpl fy   to t  formula below.
    update_basel ne_loss = _b nary_cross_entropy(
      pred=update_pos  ve_rate,
      target=update_pos  ve_rate,
      na ="update_basel ne_loss")

    update_normal zer = tf.trued v(
      update_total_pos  ve,
      update_total_pred ct on,
      na ="update_normal zer")
    update_normal zed_loss = (
      update_total_loss -
      update_total_pos  ve * tf.log(update_normal zer) +
      update_total_negat ve_pred ct on *
      (update_normal zer - 1) +
      update_total_negat ve_squared_pred ct on *
      (update_normal zer * update_normal zer - 1) / 2 +
      update_total_negat ve_cubed_pred ct on *
      (update_normal zer * update_normal zer * update_normal zer - 1) / 3 +
      update_total_negat ve_quart c_pred ct on *
      (update_normal zer * update_normal zer * update_normal zer *
       update_normal zer - 1) / 4 +
      update_total_negat ve_qu nt c_pred ct on *
      (update_normal zer * update_normal zer * update_normal zer *
       update_normal zer * update_normal zer - 1) / 5)

    update_avg_loss = tf.trued v(
      update_normal zed_loss,
      update_total_  ght,
      na ="update_avg_loss")

    update_op = tf.mult ply(
      1.0 - tf.trued v(update_avg_loss, update_basel ne_loss),
      100,
      na ="update_op")

     f  tr cs_collect ons:
      tf.add_to_collect ons( tr cs_collect ons, nrce_t)

     f updates_collect ons:
      tf.add_to_collect ons(updates_collect ons, update_op)

    return nrce_t, update_op


def _b nary_cross_entropy(pred, target, na ):
  return - tf.add(
    target * tf.log(pred),
    (1.0 - target) * tf.log(1.0 - pred),
    na =na )


# Cop ed from  tr cs_ mpl.py w h m nor mod f cat ons.
# https://g hub.com/tensorflow/tensorflow/blob/v1.5.0/tensorflow/python/ops/ tr cs_ mpl.py#L39
def _ tr c_var able(shape, dtype, val date_shape=True, na =None):
  """Create var able  n `GraphKeys.(LOCAL|METR C_VAR ABLES`) collect ons."""

  return tf.Var able(
    lambda: tf.zeros(shape, dtype),
    tra nable=False,
    collect ons=[tf.GraphKeys.LOCAL_VAR ABLES, tf.GraphKeys.METR C_VAR ABLES],
    val date_shape=val date_shape,
    na =na )

PERCENT LES = np.l nspace(0, 1, 101, dtype=np.float32)

#  tr c_na : ( tr c, requ res thresholded output)
SUPPORTED_B NARY_CLASS_METR CS = {
  # TWML  tr cs
  'total_  ght': (total_  ght_ tr c, False),
  'num_samples': (num_samples_ tr c, False),
  'rce': (rce, False),
  'rce_std_err': (part al( tr c_std_err, transform=rce_transform,  tr c=rce_ tr c, na ='rce_std_err'), False),
  'nrce': (part al(rce, normal ze=True), False),
  'lolly_nrce': (lolly_nrce, False),
  'arce': (part al(rce, normal ze=True, arce=True), False),
  'arce_or g nal': (part al(rce, normal ze=True, arce=True, up_  ght=False), False),
  # CTR  asures pos  ve sample rat o. T  term nology  s  n r ed from Ads.
  'ctr': (ctr, False),
  # pred cted CTR  asures pred cted pos  ve rat o.
  'pred cted_ctr': (pred cted_ctr, False),
  'pred_std_dev': (pred ct on_std_dev, False),
  # thresholded  tr cs
  'accuracy': (tf. tr cs.accuracy, True),
  'prec s on': (tf. tr cs.prec s on, True),
  'recall': (tf. tr cs.recall, True),

  'false_pos  ves': (tf. tr cs.false_pos  ves, True),
  'false_negat ves': (tf. tr cs.false_negat ves, True),
  'true_pos  ves': (tf. tr cs.true_pos  ves, True),
  'true_negat ves': (tf. tr cs.true_negat ves, True),

  'prec s on_at_percent les': (part al(tf. tr cs.prec s on_at_thresholds, thresholds=PERCENT LES), False),
  'recall_at_percent les': (part al(tf. tr cs.recall_at_thresholds, thresholds=PERCENT LES), False),
  'false_pos  ves_at_percent les': (part al(tf. tr cs.false_pos  ves_at_thresholds, thresholds=PERCENT LES), False),
  'false_negat ves_at_percent les': (part al(tf. tr cs.false_negat ves_at_thresholds, thresholds=PERCENT LES), False),
  'true_pos  ves_at_percent les': (part al(tf. tr cs.true_pos  ves_at_thresholds, thresholds=PERCENT LES), False),
  'true_negat ves_at_percent les': (part al(tf. tr cs.true_negat ves_at_thresholds, thresholds=PERCENT LES), False),

  # tensorflow  tr cs
  'roc_auc': (part al(tf. tr cs.auc, curve='ROC',
    summat on_ thod='careful_ nterpolat on'), False),
  'pr_auc': (part al(tf. tr cs.auc, curve='PR',
    summat on_ thod='careful_ nterpolat on'), False),

  # tensorboard curves
  'pr_curve': (tb.summary.v1.pr_curve_stream ng_op, False),

  # deprecated  tr cs
  'deprecated_nrce': (part al(rce, normal ze=True, deprecated_rce=True), False),
  'deprecated_arce': (part al(rce, normal ze=True, arce=True, deprecated_rce=True), False),
  'deprecated_arce_or g nal': (part al(rce, normal ze=True, arce=True,
                                     up_  ght=False, deprecated_rce=True), False)
}

# default  tr cs prov ded by get_b nary_class_ tr c_fn
DEFAULT_B NARY_CLASS_METR CS = ['total_  ght', 'num_samples', 'rce', 'rce_std_err',
                                'nrce', 'arce', 'ctr', 'pred cted_ctr', 'pred_std_dev',
                                'accuracy', 'prec s on', 'recall', 'roc_auc', 'pr_auc']


def get_b nary_class_ tr c_fn( tr cs=None):
  """
  Returns a funct on hav ng s gnature:

  .. code-block:: python

    def get_eval_ tr c_ops(graph_output, labels,   ghts):
      ...
      return eval_ tr c_ops

  w re t  returned eval_ tr c_ops  s a d ct of common evaluat on  tr c
  Ops for b nary class f cat on. See `tf.est mator.Est matorSpec
  <https://www.tensorflow.org/ap _docs/python/tf/est mator/Est matorSpec>`_
  for a descr pt on of eval_ tr c_ops. T  graph_output  s a t  result
  d ct returned by bu ld_graph. Labels and   ghts are tf.Tensors.

  T  follow ng graph_output keys are recogn zed:
    output:
      t  raw pred ct ons bet en 0 and 1. Requ red.
    threshold:
      A value bet en 0 and 1 used to threshold t  output  nto a hard_output.
      Defaults to 0.5 w n threshold and hard_output are m ss ng.
      E  r threshold or hard_output can be prov ded, but not both.
    hard_output:
      A thresholded output. E  r threshold or hard_output can be prov ded, but not both.

  Args:
     tr cs (l st of Str ng):
      a l st of  tr cs of  nterest. E.g. ['ctr', 'accuracy', 'rce']
      Ele nt  n t  l st can be a str ng from follow ng supported  tr cs, or can be a tuple
      w h three  ems:  tr c na ,  tr c funct on, bool for thresholded output.

      T se  tr cs are evaluated and reported to tensorboard *dur ng t  eval phases only*.
      Supported  tr cs:

      - ctr (sa  as pos  ve sample rat o.)
      - rce (cross entropy loss compared to t  basel ne model of always pred ct ng ctr)
      - nrce (normal zed rce, do not use t  one  f   do not understand what    s)
      - `arce <http://go/arce>`_ (a more recent proposed  mprov nt over NRCE)
      - arce_or g nal
      - lolly_nrce (NRCE as    s computed  n Lolly, w h Taylor expans on)
      - pr_auc
      - roc_auc
      - accuracy (percentage of pred ct ons that are correct)
      - prec s on (true pos  ves) / (true pos  ves + false pos  ves)
      - recall (true pos  ves) / (true pos  ves + false negat ves)
      - pr_curve (prec s on-recall curve)
      - deprecated_arce (ARCE as   was calculated before a stab l y f x)
      - deprecated_nrce (NRCE as   was calculated before a stab l y f x)

      Example of  tr cs l st w h m xture of str ng and tuple:
       tr cs = [
        'rce','nrce',
        'roc_auc',  # default roc_auc  tr c
        (
          'roc_auc_500',  # g ve t   tr c a na 
          part al(tf. tr cs.auc, curve='ROC', summat on_ thod='careful_ nterpolat on', num_thresholds=500),  # t   tr c fn
          False,  # w t r t   tr c requ res thresholded output
        )]

      NOTE: W n pred ct ng rare events roc_auc can be underest mated.  ncreas ng num_threshold
      can reduce t  underest mat on. See go/roc-auc-p fall for more deta ls.

      NOTE: accuracy / prec s on / recall apply to b nary class f cat on problems only.
       .e. a pred ct on  s only cons dered correct  f   matc s t  label. E.g.  f t  label
       s 1.0, and t  pred ct on  s 0.99,   does not get cred .   f   want to use
      prec s on / recall / accuracy  tr cs w h soft pred ct ons,  'll need to threshold
      y  pred ct ons  nto hard 0/1 labels.

      W n  tr cs  s None (t  default),   defaults to:
      [rce, nrce, arce, ctr, pred cted_ctr, accuracy, prec s on, recall, prauc, roc_auc],
  """
  # pyl nt: d sable=d ct-keys-not- erat ng
   f  tr cs  s None:
    # remove expens ve  tr cs by default for faster eval
     tr cs = l st(DEFAULT_B NARY_CLASS_METR CS)

  def get_eval_ tr c_ops(graph_output, labels,   ghts):
    """
    graph_output:
      d ct that  s returned by bu ld_graph g ven  nput features.
    labels:
      target labels assoc ated to batch.
      ghts:
        ghts of t  samples..
    """

    eval_ tr c_ops = OrderedD ct()

    preds = graph_output['output']

    threshold = graph_output['threshold']  f 'threshold'  n graph_output else 0.5

    hard_preds = graph_output.get('hard_output')
     f hard_preds  s None:
      hard_preds = tf.greater_equal(preds, threshold)

    # add  tr cs to eval_ tr c_ops d ct
    for  tr c  n  tr cs:
       f  s nstance( tr c, tuple) and len( tr c) == 3:
         tr c_na ,  tr c_factory, requ res_threshold =  tr c
         tr c_na  =  tr c_na .lo r()
      el f  s nstance( tr c, str):
         tr c_na  =  tr c.lo r()  #  tr c na  are case  nsens  ve.
         tr c_factory, requ res_threshold = SUPPORTED_B NARY_CLASS_METR CS.get( tr c_na )
      else:
        ra se ValueError(" tr c should be e  r str ng or tuple of length 3.")

       f  tr c_na   n eval_ tr c_ops:
        # avo d add ng dupl cate  tr cs.
        cont nue

       f  tr c_factory:
        value_op, update_op =  tr c_factory(
          labels=labels,
          pred ct ons=(hard_preds  f requ res_threshold else preds),
            ghts=  ghts, na = tr c_na )
        eval_ tr c_ops[ tr c_na ] = (value_op, update_op)
      else:
        ra se ValueError('Cannot f nd t   tr c na d ' +  tr c_na )

    return eval_ tr c_ops

  return get_eval_ tr c_ops


def get_mult _b nary_class_ tr c_fn( tr cs, classes=None, class_d m=1):
  """
  Returns a funct on hav ng s gnature:

  .. code-block:: python

    def get_eval_ tr c_ops(graph_output, labels,   ghts):
      ...
      return eval_ tr c_ops

  w re t  returned eval_ tr c_ops  s a d ct of common evaluat on  tr c
  Ops for concatenated b nary class f cat ons. See `tf.est mator.Est matorSpec
  <https://www.tensorflow.org/ap _docs/python/tf/est mator/Est matorSpec>`_
  for a descr pt on of eval_ tr c_ops. T  graph_output  s a t  result
  d ct returned by bu ld_graph. Labels and   ghts are tf.Tensors.

   n mult ple b nary class f cat on problems, t 
  ``pred ct ons`` (that  s, ``graph_output['output']``)
  are expected to have shape ``batch_s ze x n_classes``,
  w re ``n_classes``  s t  number of b nary class f cat on.
  B nary class f cat on at output[ ]  s expected to d scr m nate bet en ``classes[ ]`` (1)
  and NOT ``classes[ ]`` (0). T  labels should be of t  sa  shape as ``graph_output``
  w h b nary values (0 or 1). T    ghts can be of s ze ``batch_s ze`` or
  ``batch_s ze x n_classes``. T  ``class_d m`` conta n separate probab l  es,
  and need to have separate  tr cs.

  T  follow ng graph_output keys are recogn zed:
    output:
      t  raw pred ct ons bet en 0 and 1. Requ red.
    threshold:
      A value bet en 0 and 1 used to threshold t  output  nto a hard_output.
      Defaults to 0.5 w n threshold and hard_output are m ss ng.
      E  r threshold or hard_output can be prov ded, but not both.
    hard_output:
      A thresholded output. E  r threshold or hard_output can be prov ded, but not both.

  Args:
     tr cs (l st of  tr cs):
      a l st of  tr cs of  nterest. E.g. ['ctr', 'accuracy', 'rce']
      Ele nt  n t  l st can be a str ng from follow ng supported  tr cs, or can be a tuple
      w h three  ems:  tr c na ,  tr c funct on, bool for thresholded output.

      T se  tr cs are evaluated and reported to tensorboard *dur ng t  eval phases only*.
      Supported  tr cs:

      - ctr (sa  as pos  ve sample rat o.)
      - rce (cross entropy loss compared to t  basel ne model of always pred ct ng ctr)
      - nrce (normal zed rce, do not use t  one  f   do not understand what    s)
      - pr_auc
      - roc_auc
      - accuracy (percentage of pred ct ons that are correct)
      - prec s on (true pos  ves) / (true pos  ves + false pos  ves)
      - recall (true pos  ves) / (true pos  ves + false negat ves)
      - pr_curve (prec s on-recall curve)

      Example of  tr cs l st w h m xture of str ng and tuple:
       tr cs = [
        'rce','nrce',
        'roc_auc',  # default roc_auc  tr c
        (
          'roc_auc_500',  # g ve t   tr c a na 
          part al(tf. tr cs.auc, curve='ROC', summat on_ thod='careful_ nterpolat on', num_thresholds=500),  # t   tr c fn
          False,  # w t r t   tr c requ res thresholded output
        )]

      NOTE: W n pred ct on on rare events, roc_auc can be underest mated.  ncrease num_threshold
      can reduce t  underest mat on. See go/roc-auc-p fall for more deta ls.

      NOTE: accuracy / prec s on / recall apply to b nary class f cat on problems only.
       .e. a pred ct on  s only cons dered correct  f   matc s t  label. E.g.  f t  label
       s 1.0, and t  pred ct on  s 0.99,   does not get cred .   f   want to use
      prec s on / recall / accuracy  tr cs w h soft pred ct ons,  'll need to threshold
      y  pred ct ons  nto hard 0/1 labels.

      W n  tr cs  s None (t  default),   defaults to:
      [rce, nrce, arce, ctr, pred cted_ctr, accuracy, prec s on, recall, prauc, roc_auc],

    classes (l st of str ngs):
       n case of mult ple b nary class models, t  na s for each class or label.
      T se are used to d splay  tr cs on tensorboard.
       f t se are not spec f ed, t   ndex  n t  class or label d  ns on  s used, and  'll
      get  tr cs on tensorboard na d l ke: accuracy_0, accuracy_1, etc.

    class_d m (number):
      D  ns on of t  classes  n pred ct ons. Defaults to 1, that  s, batch_s ze x n_classes.
  """
  # pyl nt: d sable= nval d-na ,d ct-keys-not- erat ng
   f  tr cs  s None:
    # remove expens ve  tr cs by default for faster eval
     tr cs = l st(DEFAULT_B NARY_CLASS_METR CS)

  def get_eval_ tr c_ops(graph_output, labels,   ghts):
    """
    graph_output:
      d ct that  s returned by bu ld_graph g ven  nput features.
    labels:
      target labels assoc ated to batch.
      ghts:
        ghts of t  samples..
    """

    eval_ tr c_ops = OrderedD ct()

    preds = graph_output['output']

    threshold = graph_output['threshold']  f 'threshold'  n graph_output else 0.5

    hard_preds = graph_output.get('hard_output')
     f hard_preds  s None:
      hard_preds = tf.greater_equal(preds, threshold)

    shape = labels.get_shape()
    # bas c san y c ck: mult _ tr c d  ns on must ex st
    assert len(shape) > class_d m, "D  ns on spec f ed by class_d m does not ex st."

    num_labels = shape[class_d m]
    #  f   are do ng mult -class / mult -label  tr c, t  number of classes / labels must
    # be know at graph construct on t  .  T  d  ns on cannot have s ze None.
    assert num_labels  s not None, "T  mult - tr c d  ns on cannot be None."
    assert classes  s None or len(classes) == num_labels, (
      "Number of classes must match t  number of labels")

      ghts_shape =   ghts.get_shape()  f   ghts  s not None else None
     f   ghts_shape  s None:
      num_  ghts = None
    el f len(  ghts_shape) > 1:
      num_  ghts =   ghts_shape[class_d m]
    else:
      num_  ghts = 1

    for    n range(num_labels):

      # add  tr cs to eval_ tr c_ops d ct
      for  tr c  n  tr cs:
         f  s nstance( tr c, tuple) and len( tr c) == 3:
           tr c_na ,  tr c_factory, requ res_threshold =  tr c
           tr c_na  =  tr c_na .lo r()
        el f  s nstance( tr c, str):
           tr c_na  =  tr c.lo r()  #  tr c na  are case  nsens  ve.
           tr c_factory, requ res_threshold = SUPPORTED_B NARY_CLASS_METR CS.get( tr c_na )
        else:
          ra se ValueError(" tr c should be e  r str ng or tuple of length 3.")

        class_ tr c_na  =  tr c_na  + "_" + (classes[ ]  f classes  s not None else str( ))

         f class_ tr c_na   n eval_ tr c_ops:
          # avo d add ng dupl cate  tr cs.
          cont nue

        class_labels = tf.gat r(labels,  nd ces=[ ], ax s=class_d m)
        class_preds = tf.gat r(preds,  nd ces=[ ], ax s=class_d m)
        class_hard_preds = tf.gat r(hard_preds,  nd ces=[ ], ax s=class_d m)

         f num_  ghts  s None:
          class_  ghts = None
        el f num_  ghts == num_labels:
          class_  ghts = tf.gat r(  ghts,  nd ces=[ ], ax s=class_d m)
        el f num_  ghts == 1:
          class_  ghts =   ghts
        else:
          ra se ValueError("num_  ghts (%d) and num_labels (%d) do not match"
                           % (num_  ghts, num_labels))

         f  tr c_factory:
          value_op, update_op =  tr c_factory(
            labels=class_labels,
            pred ct ons=(class_hard_preds  f requ res_threshold else class_preds),
              ghts=class_  ghts, na =class_ tr c_na )
          eval_ tr c_ops[class_ tr c_na ] = (value_op, update_op)
        else:
          ra se ValueError('Cannot f nd t   tr c na d ' +  tr c_na )

    return eval_ tr c_ops

  return get_eval_ tr c_ops


def _get_uncal brated_ tr c_fn(cal brated_ tr c_fn, keep_  ght=True):
  """
  Returns a funct on hav ng s gnature:

  .. code-block:: python

    def get_eval_ tr c_ops(graph_output, labels,   ghts):
      ...
      return eval_ tr c_ops

  w re t  returned eval_ tr c_ops  s a d ct of common evaluat on  tr c
  Ops w h uncal brated output.

  T  follow ng graph_output keys are recogn zed:
    uncal brated_output:
      t  uncal brated raw pred ct ons bet en 0 and 1. Requ red.
    output:
      t  cal brated pred ct ons bet en 0 and 1.
    threshold:
      A value bet en 0 and 1 used to threshold t  output  nto a hard_output.
      Defaults to 0.5 w n threshold and hard_output are m ss ng.
      E  r threshold or hard_output can be prov ded, but not both.
    hard_output:
      A thresholded output. E  r threshold or hard_output can be prov ded, but not both.

  Args:
    cal brated_ tr c_fn:  tr cs funct on w h cal brat on and   ght.
    keep_  ght: Bool  nd cat ng w t r   keep   ght.
  """
   tr c_scope = 'uncal brated'  f keep_  ght else 'un  ghted'

  def get_eval_ tr c_ops(graph_output, labels,   ghts):
    """
    graph_output:
      d ct that  s returned by bu ld_graph g ven  nput features.
    labels:
      target labels assoc ated to batch.
      ghts:
        ghts of t  samples..
    """
    w h tf.var able_scope( tr c_scope):
       f 'uncal brated_output' not  n graph_output:
        ra se Except on("M ss ng uncal brated_output  n graph_output!")
      un_cal brated_  ghts =   ghts  f keep_  ght else tf.ones_l ke(  ghts)
      uncal brated_output = {
        'output': graph_output['uncal brated_output'],
        'threshold': graph_output.get('threshold', 0.5),
        'hard_output': graph_output.get('hard_output'),
        **{k: v for k, v  n graph_output. ems()  f k not  n ['output', 'threshold', 'hard_output']}
      }

      eval_ tr cs_ops = cal brated_ tr c_fn(uncal brated_output, labels, un_cal brated_  ghts)

      rena d_ tr cs_ops = {f'{ tr c_scope}_{k}': v for k, v  n eval_ tr cs_ops. ems()}
      return rena d_ tr cs_ops

  return get_eval_ tr c_ops


def get_mult _b nary_class_uncal brated_ tr c_fn(
   tr cs, classes=None, class_d m=1, keep_  ght=True):
  """
  Returns a funct on hav ng s gnature:

  .. code-block:: python

    def get_eval_ tr c_ops(graph_output, labels,   ghts):
      ...
      return eval_ tr c_ops

  w re t  returned eval_ tr c_ops  s a d ct of common evaluat on  tr c
  Ops for concatenated b nary class f cat ons w hout cal brat on.

  Note: 'uncal brated_output'  s requ red key  n graph_output.

  T  ma n use case for t  funct on  s:

  1) To calculated roc-auc for rare event.
  Cal brated pred ct on score for rare events w ll be concentrated near zero. As a result,
  t  roc-auc can be ser ously underest mated w h current  mple ntat on  n tf. tr c.auc.
  S nce roc-auc  s  nvar ant aga nst cal brat on,   can d rectly use uncal brated score for roc-auc.
  For more deta ls, please refer to: go/roc-auc- nvar ance.

  2) To set keep_  ght=False and get un  ghted and uncal brated  tr cs.
  T   s useful to eval how t  model  s f ted to  s actual tra n ng data, s nce
  often t   t  model  s tra ned w hout   ght.

  Args:
     tr cs (l st of Str ng):
      a l st of  tr cs of  nterest. E.g. ['ctr', 'accuracy', 'rce']
      Ele nt  n t  l st can be a str ng from supported  tr cs, or can be a tuple
      w h three  ems:  tr c na ,  tr c funct on, bool for thresholded output.
      T se  tr cs are evaluated and reported to tensorboard *dur ng t  eval phases only*.

      W n  tr cs  s None (t  default),   defaults to:
      [rce, nrce, arce, ctr, pred cted_ctr, accuracy, prec s on, recall, prauc, roc_auc],

    classes (l st of str ngs):
       n case of mult ple b nary class models, t  na s for each class or label.
      T se are used to d splay  tr cs on tensorboard.
       f t se are not spec f ed, t   ndex  n t  class or label d  ns on  s used, and  'll
      get  tr cs on tensorboard na d l ke: accuracy_0, accuracy_1, etc.

    class_d m (number):
      D  ns on of t  classes  n pred ct ons. Defaults to 1, that  s, batch_s ze x n_classes.

    keep_  ght (bool):
      W t r to keep   ghts for t   tr c.
  """

  cal brated_ tr c_fn = get_mult _b nary_class_ tr c_fn(
     tr cs, classes=classes, class_d m=class_d m)
  return _get_uncal brated_ tr c_fn(cal brated_ tr c_fn, keep_  ght=keep_  ght)


def comb ne_ tr c_fns(*fn_l st):
  """
  Comb ne mult ple  tr c funct ons.
  For example,   can comb ne  tr cs funct on generated by
  get_mult _b nary_class_ tr c_fn and get_mult _b nary_class_uncal brated_ tr c_fn.

  Args:
    *fn_l st: Mult ple  tr c funct ons to be comb ned

  Returns:
    Comb ned  tr c funct on.
  """
  def comb ned_ tr c_ops(*args, **kwargs):
    eval_ tr c_ops = OrderedD ct()
    for fn  n fn_l st:
      eval_ tr c_ops.update(fn(*args, **kwargs))
    return eval_ tr c_ops
  return comb ned_ tr c_ops
