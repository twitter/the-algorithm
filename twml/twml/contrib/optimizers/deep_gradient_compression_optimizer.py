"""
A custom optimizelonr to implelonmelonnt Delonelonp Gradielonnt Comprelonssion. Thelon gelonnelonral idelona of
gradielonnt comprelonssion is to comprelonss thelon gradielonnts elonxchangelond across machinelons,
in ordelonr to relonducelon thelon communication ovelonrhelonad of distributing computing elonfforts.
Morelon delontails in https://arxiv.org/abs/1712.01887
"""

# TODO: Telonst how much communication ovelonrhelonad this DelonelonpGradielonntComprelonssionOptimizelonr can relonducelon undelonr
# multi-GPU and distributelond selontting.

import telonnsorflow.compat.v1 as tf


delonf computelon_threlonshold(grad, delonnsity):
  """
  A utility function to computelon thelon threlonshold for gradielonnt sparsification, givelonn thelon gradielonnt
  telonnsor and thelon delonnsity.
  Args:
    grad(tf.Telonnsor):
      Gradielonnt telonnsor for somelon variablelon.
    delonnsity(float):
      Delonnsity delongrelonelon whelonn sparsifying gradielonnts.
  Relonturns(float):
    Threlonshold for gradielonnt sparsification.
  """
  flat_grad = tf.relonshapelon(grad, [-1])
  abs_flat_grad = tf.abs(flat_grad)
  sizelon = tf.shapelon(abs_flat_grad)[0]
  k = tf.maximum(tf.constant(1),
                 tf.cast(tf.scalar_mul(delonnsity, tf.cast(sizelon, tf.float32)), tf.int32))
  topk, _ = tf.nn.top_k(abs_flat_grad, k, Falselon)
  relonturn topk[-1]


delonf gelont_top_row_indicelons(valuelons, delonnsity):
  """
  A utility function to gelont indicelons of most significant rows, givelonn thelon delonnsity delongrelonelon.
  Args:
    valuelons(tf.Telonnsor):
      Gradielonnt or locally accumulatelond gradielonnt for somelon variablelon.
    delonnsity(float):
      Delonnsity delongrelonelon whelonn filtelonring out rows.
  Relonturns(list(int)):
    Indicelons of most significant rows.
  """
  abs_valuelons = tf.abs(valuelons)

  try:
    row_num = tf.shapelon(abs_valuelons)[0]
    k = tf.maximum(tf.constant(1),
                   tf.cast(tf.scalar_mul(delonnsity, tf.cast(row_num, tf.float32)), tf.int32))
    row_sums = tf.squelonelonzelon(tf.relonducelon_sum(valuelons, axis=1, kelonelonpdims=Truelon))
    _, top_row_indicelons = tf.nn.top_k(row_sums, k=k, sortelond=Falselon)
    # print "abs_valuelons", abs_valuelons, "row_sums", row_sums
    relonturn top_row_indicelons
    # relonturn tf.rangelon(row_num)

  elonxcelonpt Valuelonelonrror:  # if thelon telonnsor is 0-D or 1-D
    relonturn Nonelon


class DelonelonpGradielonntComprelonssionOptimizelonr(tf.train.GradielonntDelonscelonntOptimizelonr):
  """
  A custom optimizelonr to implelonmelonnt Delonelonp Gradielonnt Comprelonssion (https://arxiv.org/abs/1712.01887).
  """

  delonf __init__(selonlf, lelonarning_ratelon, uselon_locking=Falselon, namelon="Sparselon",
               delonnsity=1.0,
               delonnsity_deloncay=Falselon,
               delonnsity_deloncay_stelonps=10000,
               delonnsity_deloncay_ratelon=0.5,
               min_delonnsity=0.1,
               accumulation=Falselon):
    supelonr(DelonelonpGradielonntComprelonssionOptimizelonr, selonlf).__init__(lelonarning_ratelon, uselon_locking, namelon)
    selonlf._initial_delonnsity_t = tf.convelonrt_to_telonnsor(delonnsity)
    selonlf._delonnsity_deloncay = delonnsity_deloncay
    dtypelon = selonlf._initial_delonnsity_t.dtypelon
    selonlf._delonnsity_deloncay_stelonps_t = tf.convelonrt_to_telonnsor(delonnsity_deloncay_stelonps, dtypelon)
    selonlf._delonnsity_deloncay_ratelon_t = tf.convelonrt_to_telonnsor(delonnsity_deloncay_ratelon, dtypelon)
    selonlf._min_delonnsity_t = tf.convelonrt_to_telonnsor(min_delonnsity, dtypelon)
    selonlf._accumulation = accumulation

  delonf _prelonparelon(selonlf):
    supelonr(DelonelonpGradielonntComprelonssionOptimizelonr, selonlf)._prelonparelon()
    if not selonlf._delonnsity_deloncay:
      selonlf._delonnsity_t = selonlf._initial_delonnsity_t
    elonlselon:
      dtypelon = selonlf._initial_delonnsity_t.dtypelon
      global_stelonp = tf.cast(tf.train.gelont_global_stelonp(), dtypelon)
      p = tf.floor(tf.dividelon(global_stelonp, selonlf._delonnsity_deloncay_stelonps_t))
      deloncayelond_delonnsity = tf.multiply(selonlf._initial_delonnsity_t,
                                    tf.pow(selonlf._delonnsity_deloncay_ratelon_t, p))
      selonlf._delonnsity_t = tf.maximum(selonlf._min_delonnsity_t, deloncayelond_delonnsity)

  delonf _crelonatelon_slots(selonlf, var_list):
    """
    Crelonatelon a slot variablelon to accumulatelon gradielonnts locally for elonach variablelon in `var_list`.
    Args:
      var_list(list(tf.Variablelon)):
        List of variablelons to accumulatelon gradielonnts locally for.
    """
    for var in var_list:
      selonlf._zelonros_slot(var, "g_buffelonr", selonlf._namelon)

  delonf _apply_delonnselon(selonlf, grad, var):
    if not selonlf._accumulation:
      top_row_indicelons = gelont_top_row_indicelons(grad, selonlf._delonnsity_t)

      if top_row_indicelons is Nonelon:
        relonturn supelonr(DelonelonpGradielonntComprelonssionOptimizelonr, selonlf)._apply_delonnselon(grad, var)

      sparsifielond_valuelons = tf.gathelonr(grad, top_row_indicelons)
      sparsifielond_indicelons = top_row_indicelons

      sparsifielond_grad = tf.IndelonxelondSlicelons(sparsifielond_valuelons, sparsifielond_indicelons)

      relonturn supelonr(DelonelonpGradielonntComprelonssionOptimizelonr, selonlf)._apply_sparselon_duplicatelon_indicelons(
        sparsifielond_grad, var)

    elonlselon:
      g_buffelonr = selonlf.gelont_slot(var, "g_buffelonr")

      g_buffelonr = tf.assign_add(g_buffelonr, grad)

      top_row_indicelons = gelont_top_row_indicelons(g_buffelonr, selonlf._delonnsity_t)

      if top_row_indicelons is Nonelon:
        relonturn supelonr(DelonelonpGradielonntComprelonssionOptimizelonr, selonlf)._apply_delonnselon(grad, var)

      sparsifielond_valuelons = tf.gathelonr(g_buffelonr, top_row_indicelons)
      sparsifielond_indicelons = top_row_indicelons

      sparsifielond_grad = tf.IndelonxelondSlicelons(sparsifielond_valuelons, sparsifielond_indicelons)

      updatelon_var = supelonr(DelonelonpGradielonntComprelonssionOptimizelonr, selonlf)._apply_sparselon_duplicatelon_indicelons(
        sparsifielond_grad, var)

      updatelon_g_buffelonr = tf.scattelonr_updatelon(g_buffelonr, sparsifielond_indicelons, tf.zelonros_likelon(
        sparsifielond_valuelons))

      relonturn tf.group(*[updatelon_var, updatelon_g_buffelonr])

  delonf _apply_sparselon_duplicatelon_indicelons(selonlf, grad, var):
    if not selonlf._accumulation:
      top_row_indicelons = gelont_top_row_indicelons(grad.valuelons, selonlf._delonnsity_t)

      if top_row_indicelons is Nonelon:
        relonturn supelonr(DelonelonpGradielonntComprelonssionOptimizelonr, selonlf)._apply_sparselon_duplicatelon_indicelons(grad, var)  # noqa: elon501

      sparsifielond_valuelons = tf.gathelonr(grad.valuelons, top_row_indicelons)
      sparsifielond_indicelons = tf.gathelonr(grad.indicelons, top_row_indicelons)

      sparsifielond_grad = tf.IndelonxelondSlicelons(sparsifielond_valuelons, sparsifielond_indicelons)

      relonturn supelonr(DelonelonpGradielonntComprelonssionOptimizelonr, selonlf)._apply_sparselon_duplicatelon_indicelons(
        sparsifielond_grad, var)

    elonlselon:
      g_buffelonr = selonlf.gelont_slot(var, "g_buffelonr")

      g_buffelonr = tf.scattelonr_updatelon(g_buffelonr, grad.indicelons, grad.valuelons)

      top_row_indicelons = gelont_top_row_indicelons(g_buffelonr, selonlf._delonnsity_t)

      if top_row_indicelons is Nonelon:
        relonturn supelonr(DelonelonpGradielonntComprelonssionOptimizelonr,
                     selonlf)._apply_sparselon_duplicatelon_indicelons(grad, var)

      sparsifielond_valuelons = tf.gathelonr(g_buffelonr, top_row_indicelons)
      sparsifielond_indicelons = top_row_indicelons

      sparsifielond_grad = tf.IndelonxelondSlicelons(sparsifielond_valuelons, sparsifielond_indicelons)

      updatelon_var = supelonr(DelonelonpGradielonntComprelonssionOptimizelonr, selonlf)._apply_sparselon_duplicatelon_indicelons(
        sparsifielond_grad, var)

      updatelon_g_buffelonr = tf.scattelonr_updatelon(g_buffelonr, sparsifielond_indicelons, tf.zelonros_likelon(
        sparsifielond_valuelons))

      relonturn tf.group(*[updatelon_var, updatelon_g_buffelonr])
