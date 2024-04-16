"""
Module conta n ng extra tensorflow  tr cs used at Tw ter.
T  module conforms to convent ons used by tf. tr cs.*.
 n part cular, each  tr c constructs two subgraphs: value_op and update_op:
  - T  value op  s used to fetch t  current  tr c value.
  - T  update_op  s used to accumulate  nto t   tr c.

Note: s m lar to tf. tr cs.*,  tr cs  n  re do not support mult -label learn ng.
  w ll have to wr e wrapper classes to create one  tr c per label.

Note: s m lar to tf. tr cs.*, batc s added  nto a  tr c v a  s update_op are cumulat ve!

"""

from collect ons  mport OrderedD ct
from functools  mport part al

 mport tensorflow.compat.v1 as tf
from tensorflow.python.eager  mport context
from tensorflow.python.fra work  mport dtypes, ops
from tensorflow.python.ops  mport array_ops, state_ops
 mport twml
from twml.contr b.ut ls  mport math_fns


def ndcg(labels, pred ct ons,
                   tr cs_collect ons=None,
                  updates_collect ons=None,
                  na =None,
                  top_k_ nt=1):
  # pyl nt: d sable=unused-argu nt
  """
  Compute full normal zed d scounted cumulat ve ga n (ndcg) based on pred ct ons
  ndcg = dcg_k/ dcg_k, k  s a cut off rank ng post on
  T re are a few var ants of ndcg
  T  dcg (d scounted cumulat ve ga n) formula used  n
  twml.contr b. tr cs.ndcg  s::

    \\sum_{ =1}^k \frac{2^{relevance\\_score} -1}{\\log_{2}(  + 1)}

  k  s t  length of  ems to be ranked  n a batch/query
  Not ce that w t r k w ll be replaced w h a f xed value requ res d scuss ons
  T  scores  n pred ct ons are transfor d to order and relevance scores to calculate ndcg
  A relevance score  ans how relevant a DataRecord  s to a part cular query

  Argu nts:
    labels: t  ground truth value.
    pred ct ons: t  pred cted values, whose shape must match labels.  gnored for CTR computat on.
     tr cs_collect ons: opt onal l st of collect ons to add t   tr c  nto.
    updates_collect ons: opt onal l st of collect ons to add t  assoc ated update_op  nto.
    na : an opt onal var able_scope na .

  Returns:
    ndcg: A `Tensor` represent ng t  ndcg score.
    update_op: A update operat on used to accumulate data  nto t   tr c.
  """
  w h tf.var able_scope(na , 'ndcg', (labels, pred ct ons)):
    label_scores = tf.to_float(labels, na ='label_to_float')
    pred cted_scores = tf.to_float(pred ct ons, na ='pred ct ons_to_float')

     f context.execut ng_eagerly():
      ra se Runt  Error('ndcg  s not supported w n eager execut on '
                         ' s enabled.')

    total_ndcg = _ tr c_var able([], dtypes.float32, na ='total_ndcg')
    count_query = _ tr c_var able([], dtypes.float32, na ='query_count')

    # actual ndcg cutoff pos  on top_k_ nt
    max_pred ct on_s ze = array_ops.s ze(pred cted_scores)
    top_k_ nt = tf.m n mum(max_pred ct on_s ze, top_k_ nt)
    # t  ndcg score of t  batch
    ndcg = math_fns.cal_ndcg(label_scores,
      pred cted_scores, top_k_ nt=top_k_ nt)
    # add ndcg of t  current batch to total_ndcg
    update_total_op = state_ops.ass gn_add(total_ndcg, ndcg)
    w h ops.control_dependenc es([ndcg]):
      # count_query stores t  number of quer es
      # count_query  ncreases by 1 for each batch/query
      update_count_op = state_ops.ass gn_add(count_query, 1)

     an_ndcg = math_fns.safe_d v(total_ndcg, count_query, ' an_ndcg')
    update_op = math_fns.safe_d v(update_total_op, update_count_op, 'update_ an_ndcg_op')

     f  tr cs_collect ons:
      ops.add_to_collect ons( tr cs_collect ons,  an_ndcg)

     f updates_collect ons:
      ops.add_to_collect ons(updates_collect ons, update_op)

    return  an_ndcg, update_op


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


# b nary  tr c_na : ( tr c, requ res thresholded output)
SUPPORTED_B NARY_CLASS_METR CS = {
  # TWML b nary  tr cs
  'rce': (twml. tr cs.rce, False),
  'nrce': (part al(twml. tr cs.rce, normal ze=True), False),
  # CTR  asures pos  ve sample rat o. T  term nology  s  n r ed from Ads.
  'ctr': (twml. tr cs.ctr, False),
  # pred cted CTR  asures pred cted pos  ve rat o.
  'pred cted_ctr': (twml. tr cs.pred cted_ctr, False),
  # thresholded  tr cs
  'accuracy': (tf. tr cs.accuracy, True),
  'prec s on': (tf. tr cs.prec s on, True),
  'recall': (tf. tr cs.recall, True),
  # tensorflow  tr cs
  'roc_auc': (part al(tf. tr cs.auc, curve='ROC'), False),
  'pr_auc': (part al(tf. tr cs.auc, curve='PR'), False),
}

# search  tr c_na :  tr c
SUPPORTED_SEARCH_METR CS = {
  # TWML search  tr cs
  # ndcg needs t  raw pred ct on scores to sort
  'ndcg': ndcg,
}


def get_search_ tr c_fn(b nary_ tr cs=None, search_ tr cs=None,
  ndcg_top_ks=[1, 3, 5, 10], use_b nary_ tr cs=False):
  """
  Returns a funct on hav ng s gnature:

  .. code-block:: python

    def get_eval_ tr c_ops(graph_output, labels,   ghts):
      ...
      return eval_ tr c_ops

  w re t  returned eval_ tr c_ops  s a d ct of common evaluat on  tr c
  Ops for rank ng. See `tf.est mator.Est matorSpec
  <https://www.tensorflow.org/ap _docs/python/tf/est mator/Est matorSpec>`_
  for a descr pt on of eval_ tr c_ops. T  graph_output  s a t  result
  d ct returned by bu ld_graph. Labels and   ghts are tf.Tensors.

  T  follow ng graph_output keys are recogn zed:
    output:
      t  raw pred ct ons. Requ red.
    threshold:
      Only used  n SUPPORTED_B NARY_CLASS_METR CS
       f t  lables are 0s and 1s
      A value bet en 0 and 1 used to threshold t  output  nto a hard_output.
      Defaults to 0.5 w n threshold and hard_output are m ss ng.
      E  r threshold or hard_output can be prov ded, but not both.
    hard_output:
      Only used  n SUPPORTED_B NARY_CLASS_METR CS
      A thresholded output. E  r threshold or hard_output can be prov ded, but not both.

  Argu nts:
    only used  n po ntw se learn ng-to-rank

    b nary_ tr cs (l st of Str ng):
      a l st of  tr cs of  nterest. E.g. ['ctr', 'accuracy', 'rce']
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

      NOTE: accuracy / prec s on / recall apply to b nary class f cat on problems only.
       .e. a pred ct on  s only cons dered correct  f   matc s t  label. E.g.  f t  label
       s 1.0, and t  pred ct on  s 0.99,   does not get cred .   f   want to use
      prec s on / recall / accuracy  tr cs w h soft pred ct ons,  'll need to threshold
      y  pred ct ons  nto hard 0/1 labels.

      W n b nary_ tr cs  s None (t  default),   defaults to all supported  tr cs

    search_ tr cs (l st of Str ng):
      a l st of  tr cs of  nterest. E.g. ['ndcg']
      T se  tr cs are evaluated and reported to tensorboard *dur ng t  eval phases only*.
      Supported  tr cs:
        - ndcg

      NOTE: ndcg works for rank ng-relatd problems.
      A batch conta ns all DataRecords that belong to t  sa  query
       f pa r_ n_batch_mode used  n scald ng -- a batch conta ns a pa r of DataRecords
      that belong to t  sa  query and have d fferent labels -- ndcg does not apply  n  re.

      W n search_ tr cs  s None (t  default),   defaults to all supported search  tr cs
      currently only 'ndcg'

    ndcg_top_ks (l st of  ntegers):
      T  cut-off rank ng post ons for a query
      W n ndcg_top_ks  s None or empty (t  default),   defaults to [1, 3, 5, 10]

    use_b nary_ tr cs:
      False (default)
      Only set   to true  n po ntw se learn ng-to-rank
  """
  # pyl nt: d sable=d ct-keys-not- erat ng

   f ndcg_top_ks  s None or not ndcg_top_ks:
    ndcg_top_ks = [1, 3, 5, 10]

   f search_ tr cs  s None:
    search_ tr cs = l st(SUPPORTED_SEARCH_METR CS.keys())

   f b nary_ tr cs  s None and use_b nary_ tr cs:
    # Added SUPPORTED_B NARY_CLASS_METR CS  n twml. t cs as  ll
    # t y are only used  n po ntw se lear ng-to-rank
    b nary_ tr cs = l st(SUPPORTED_B NARY_CLASS_METR CS.keys())

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
    # hard_preds  s a tensor
    # c ck hard_preds  s None and t n c ck  f    s empty
     f hard_preds  s None or tf.equal(tf.s ze(hard_preds), 0):
      hard_preds = tf.greater_equal(preds, threshold)

    # add search  tr cs to eval_ tr c_ops d ct
    for  tr c_na   n search_ tr cs:
       tr c_na  =  tr c_na .lo r()  #  tr c na  are case  nsens  ve.

       f  tr c_na   n eval_ tr c_ops:
        # avo d add ng dupl cate  tr cs.
        cont nue

      search_ tr c_factory = SUPPORTED_SEARCH_METR CS.get( tr c_na )
       f search_ tr c_factory:
         f  tr c_na  == 'ndcg':
          for top_k  n ndcg_top_ks:
            #  tr c na  w ll show as ndcg_1, ndcg_10, ...
             tr c_na _ndcg_top_k =  tr c_na  + '_' + str(top_k)
            top_k_ nt = tf.constant(top_k, dtype=tf. nt32)
            # Note: hav ng   ghts  n ndcg does not make much sense
            # Because ndcg already has pos  on   ghts/d scounts
            # Thus   ghts are not appl ed  n ndcg  tr c
            value_op, update_op = search_ tr c_factory(
              labels=labels,
              pred ct ons=preds,
              na = tr c_na _ndcg_top_k,
              top_k_ nt=top_k_ nt)
            eval_ tr c_ops[ tr c_na _ndcg_top_k] = (value_op, update_op)
      else:
        ra se ValueError('Cannot f nd t  search  tr c na d ' +  tr c_na )

     f use_b nary_ tr cs:
      # add b nary  tr cs to eval_ tr c_ops d ct
      for  tr c_na   n b nary_ tr cs:

         f  tr c_na   n eval_ tr c_ops:
          # avo d add ng dupl cate  tr cs.
          cont nue

         tr c_na  =  tr c_na .lo r()  #  tr c na  are case  nsens  ve.
        b nary_ tr c_factory, requ res_threshold = SUPPORTED_B NARY_CLASS_METR CS.get( tr c_na )
         f b nary_ tr c_factory:
          value_op, update_op = b nary_ tr c_factory(
            labels=labels,
            pred ct ons=(hard_preds  f requ res_threshold else preds),
              ghts=  ghts,
            na = tr c_na )
          eval_ tr c_ops[ tr c_na ] = (value_op, update_op)
        else:
          ra se ValueError('Cannot f nd t  b nary  tr c na d ' +  tr c_na )

    return eval_ tr c_ops

  return get_eval_ tr c_ops
