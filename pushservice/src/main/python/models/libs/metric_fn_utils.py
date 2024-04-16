"""
Ut lt es for construct ng a  tr c_fn for mag c recs.
"""

from twml.contr b. tr cs. tr cs  mport (
  get_dual_b nary_tasks_ tr c_fn,
  get_nu r c_ tr c_fn,
  get_part al_mult _b nary_class_ tr c_fn,
  get_s ngle_b nary_task_ tr c_fn,
)

from .model_ut ls  mport generate_d sl ked_mask

 mport tensorflow.compat.v1 as tf


METR C_BOOK = {
  "OONC": ["OONC"],
  "OONC_Engage nt": ["OONC", "Engage nt"],
  "Sent": ["Sent"],
  " avyRankPos  on": [" avyRankPos  on"],
  " avyRankProbab l y": [" avyRankProbab l y"],
}

USER_AGE_FEATURE_NAME = "accountAge"
NEW_USER_AGE_CUTOFF = 0


def remove_padd ng_and_flatten(tensor, val d_batch_s ze):
  """Remove t  padd ng of t   nput padded tensor g ven t  val d batch s ze tensor,
    t n flatten t  output w h respect to t  f rst d  ns on.
  Args:
    tensor: A tensor of s ze [META_BATCH_S ZE, BATCH_S ZE, FEATURE_D M].
    val d_batch_s ze: A tensor of s ze [META_BATCH_S ZE], w h each ele nt  nd cat ng
      t  effect ve batch s ze of t  BATCH_S ZE d  ns on.

  Returns:
    A tesnor of s ze [tf.reduce_sum(val d_batch_s ze), FEATURE_D M].
  """
  unpadded_ragged_tensor = tf.RaggedTensor.from_tensor(tensor=tensor, lengths=val d_batch_s ze)

  return unpadded_ragged_tensor.flat_values


def safe_mask(values, mask):
  """Mask values  f poss ble.

  Boolean mask  nputed values  f and only  f values  s a tensor of t  sa  d  ns on as mask (or can be broadcasted to that d  ns on).

  Args:
      values (Any or Tensor):  nput tensor to mask. D m 0 should be s ze N.
      mask (boolean tensor): A boolean tensor of s ze N.

  Returns Values or Values masked.
  """
   f values  s None:
    return values
   f not tf. s_tensor(values):
    return values
  values_shape = values.get_shape()
   f not values_shape or len(values_shape) == 0:
    return values
   f not mask.get_shape(). s_compat ble_w h(values_shape[0]):
    return values
  return tf.boolean_mask(values, mask)


def add_new_user_ tr cs( tr c_fn):
  """W ll strat fy t   tr c_fn by add ng new user  tr cs.

  G ven an  nput  tr c_fn, double every  tr c: One w ll be t  or gnal and t  ot r w ll only  nclude those for new users.

  Args:
       tr c_fn (python funct on): Base twml  tr c_fn.

  Returns a  tr c_fn w h new user  tr cs  ncluded.
  """

  def  tr c_fn_w h_new_users(graph_output, labels,   ghts):
     f USER_AGE_FEATURE_NAME not  n graph_output:
      ra se ValueError(
        " n order to get  tr cs strat f ed by user age, {na } feature should be added to model graph output. Ho ver, only t  follow ng output keys  re found: {keys}.".format(
          na =USER_AGE_FEATURE_NAME, keys=graph_output.keys()
        )
      )

     tr c_ops =  tr c_fn(graph_output, labels,   ghts)

     s_new = tf.reshape(
      tf.math.less_equal(
        tf.cast(graph_output[USER_AGE_FEATURE_NAME], tf. nt64),
        tf.cast(NEW_USER_AGE_CUTOFF, tf. nt64),
      ),
      [-1],
    )

    labels = safe_mask(labels,  s_new)
      ghts = safe_mask(  ghts,  s_new)
    graph_output = {key: safe_mask(values,  s_new) for key, values  n graph_output. ems()}

    new_user_ tr c_ops =  tr c_fn(graph_output, labels,   ghts)
    new_user_ tr c_ops = {na  + "_new_users": ops for na , ops  n new_user_ tr c_ops. ems()}
     tr c_ops.update(new_user_ tr c_ops)
    return  tr c_ops

  return  tr c_fn_w h_new_users


def get_ ta_learn_s ngle_b nary_task_ tr c_fn(
   tr cs, classna s, top_k=(5, 5, 5), use_top_k=False
):
  """Wrapper funct on to use t   tr c_fn w h  ta learn ng evaluat on sc  .

  Args:
     tr cs: A l st of str ng represent ng  tr c na s.
    classna s: A l st of str ng repsent ng class na s,  n case of mult ple b nary class models,
      t  na s for each class or label.
    top_k: A tuple of  nt to spec fy top K  tr cs.
    use_top_k: A boolean value  nd cat ng of top K of  tr cs  s used.

  Returns:
    A custom zed  tr c_fn funct on.
  """

  def get_eval_ tr c_ops(graph_output, labels,   ghts):
    """T  op func of t  eval_ tr cs. Compar ng w h normal vers on,
      t  d fference  s   flatten t  output, label, and   ghts.

    Args:
      graph_output: A d ct of tensors.
      labels: A tensor of  nt32 be t  value of e  r 0 or 1.
        ghts: A tensor of float32 to  nd cate t  per record   ght.

    Returns:
      A d ct of  tr c na s and values.
    """
     tr c_op_  ghted = get_part al_mult _b nary_class_ tr c_fn(
       tr cs, predcols=0, classes=classna s
    )
    classna s_un  ghted = ["un  ghted_" + classna  for classna   n classna s]
     tr c_op_un  ghted = get_part al_mult _b nary_class_ tr c_fn(
       tr cs, predcols=0, classes=classna s_un  ghted
    )

    val d_batch_s ze = graph_output["val d_batch_s ze"]
    graph_output["output"] = remove_padd ng_and_flatten(graph_output["output"], val d_batch_s ze)
    labels = remove_padd ng_and_flatten(labels, val d_batch_s ze)
      ghts = remove_padd ng_and_flatten(  ghts, val d_batch_s ze)

    tf.ensure_shape(graph_output["output"], [None, 1])
    tf.ensure_shape(labels, [None, 1])
    tf.ensure_shape(  ghts, [None, 1])

     tr cs_  ghted =  tr c_op_  ghted(graph_output, labels,   ghts)
     tr cs_un  ghted =  tr c_op_un  ghted(graph_output, labels, None)
     tr cs_  ghted.update( tr cs_un  ghted)

     f use_top_k:
       tr c_op_nu r c = get_nu r c_ tr c_fn( tr cs=None, topK=top_k, predcol=0, labelcol=1)
       tr cs_nu r c =  tr c_op_nu r c(graph_output, labels,   ghts)
       tr cs_  ghted.update( tr cs_nu r c)
    return  tr cs_  ghted

  return get_eval_ tr c_ops


def get_ ta_learn_dual_b nary_tasks_ tr c_fn(
   tr cs, classna s, top_k=(5, 5, 5), use_top_k=False
):
  """Wrapper funct on to use t   tr c_fn w h  ta learn ng evaluat on sc  .

  Args:
     tr cs: A l st of str ng represent ng  tr c na s.
    classna s: A l st of str ng repsent ng class na s,  n case of mult ple b nary class models,
      t  na s for each class or label.
    top_k: A tuple of  nt to spec fy top K  tr cs.
    use_top_k: A boolean value  nd cat ng of top K of  tr cs  s used.

  Returns:
    A custom zed  tr c_fn funct on.
  """

  def get_eval_ tr c_ops(graph_output, labels,   ghts):
    """T  op func of t  eval_ tr cs. Compar ng w h normal vers on,
      t  d fference  s   flatten t  output, label, and   ghts.

    Args:
      graph_output: A d ct of tensors.
      labels: A tensor of  nt32 be t  value of e  r 0 or 1.
        ghts: A tensor of float32 to  nd cate t  per record   ght.

    Returns:
      A d ct of  tr c na s and values.
    """
     tr c_op_  ghted = get_part al_mult _b nary_class_ tr c_fn(
       tr cs, predcols=[0, 1], classes=classna s
    )
    classna s_un  ghted = ["un  ghted_" + classna  for classna   n classna s]
     tr c_op_un  ghted = get_part al_mult _b nary_class_ tr c_fn(
       tr cs, predcols=[0, 1], classes=classna s_un  ghted
    )

    val d_batch_s ze = graph_output["val d_batch_s ze"]
    graph_output["output"] = remove_padd ng_and_flatten(graph_output["output"], val d_batch_s ze)
    labels = remove_padd ng_and_flatten(labels, val d_batch_s ze)
      ghts = remove_padd ng_and_flatten(  ghts, val d_batch_s ze)

    tf.ensure_shape(graph_output["output"], [None, 2])
    tf.ensure_shape(labels, [None, 2])
    tf.ensure_shape(  ghts, [None, 1])

     tr cs_  ghted =  tr c_op_  ghted(graph_output, labels,   ghts)
     tr cs_un  ghted =  tr c_op_un  ghted(graph_output, labels, None)
     tr cs_  ghted.update( tr cs_un  ghted)

     f use_top_k:
       tr c_op_nu r c = get_nu r c_ tr c_fn( tr cs=None, topK=top_k, predcol=2, labelcol=2)
       tr cs_nu r c =  tr c_op_nu r c(graph_output, labels,   ghts)
       tr cs_  ghted.update( tr cs_nu r c)
    return  tr cs_  ghted

  return get_eval_ tr c_ops


def get_ tr c_fn(task_na , use_strat fy_ tr cs, use_ ta_batch=False):
  """W ll retr eve t   tr c_fn for mag c recs.

  Args:
    task_na  (str ng): Wh ch task  s be ng used for t  model.
    use_strat fy_ tr cs (boolean): Should   add strat f ed  tr cs (new user  tr cs).
    use_ ta_batch (boolean):  f t  output/label/  ghts are passed  n 3D shape  nstead of
    2D shape.

  Returns:
    A  tr c_fn funct on to pass  n twml Tra ner.
  """
   f task_na  not  n METR C_BOOK:
    ra se ValueError(
      "Task na  of {task_na } not recogn zed. Unable to retr eve  tr cs.".format(
        task_na =task_na 
      )
    )
  class_na s = METR C_BOOK[task_na ]
   f use_ ta_batch:
    get_n_b nary_task_ tr c_fn = (
      get_ ta_learn_s ngle_b nary_task_ tr c_fn
       f len(class_na s) == 1
      else get_ ta_learn_dual_b nary_tasks_ tr c_fn
    )
  else:
    get_n_b nary_task_ tr c_fn = (
      get_s ngle_b nary_task_ tr c_fn  f len(class_na s) == 1 else get_dual_b nary_tasks_ tr c_fn
    )

   tr c_fn = get_n_b nary_task_ tr c_fn( tr cs=None, classna s=METR C_BOOK[task_na ])

   f use_strat fy_ tr cs:
     tr c_fn = add_new_user_ tr cs( tr c_fn)

  return  tr c_fn


def fl p_d sl ked_labels( tr c_fn):
  """T  funct on returns an adapted  tr c_fn wh ch fl ps t  labels of t  OONCed evaluat on data to 0  f    s d sl ked.
  Args:
     tr c_fn: A  tr c_fn funct on to pass  n twml Tra ner.

  Returns:
    _adapted_ tr c_fn: A custom zed  tr c_fn funct on w h d sl ked OONC labels fl pped.
  """

  def _adapted_ tr c_fn(graph_output, labels,   ghts):
    """A custom zed  tr c_fn funct on w h d sl ked OONC labels fl pped.

    Args:
      graph_output: A d ct of tensors.
      labels: labels of tra n ng samples, wh ch  s a 2D tensor of shape batch_s ze x 3: [OONCs, engage nts, d sl kes]
        ghts: A tensor of float32 to  nd cate t  per record   ght.

    Returns:
      A d ct of  tr c na s and values.
    """
    #   want to mult ply t  label of t  observat on by 0 only w n    s d sl ked
    d sl ked_mask = generate_d sl ked_mask(labels)

    # Extract OONC and engage nt labels only.
    labels = tf.reshape(labels[:, 0:2], shape=[-1, 2])

    # Labels w ll be set to 0  f    s d sl ked.
    adapted_labels = labels * tf.cast(tf.log cal_not(d sl ked_mask), dtype=labels.dtype)

    return  tr c_fn(graph_output, adapted_labels,   ghts)

  return _adapted_ tr c_fn
