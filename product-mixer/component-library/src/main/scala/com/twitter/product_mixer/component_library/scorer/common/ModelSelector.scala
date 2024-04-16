package com.tw ter.product_m xer.component_l brary.scorer.common

 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.t  l nes.conf gap .Param

/**
 * Selector for choos ng wh ch Model  D/Na  to use w n call ng an underly ng ML Model Serv ce.
 */
tra  ModelSelector[-Query <: P pel neQuery] {
  def apply(query: Query): Opt on[Str ng]
}

/**
 * S mple Model  D Selector that chooses model based off of a Param object.
 * @param param Conf gAP  Param that dec des t  model  d.
 */
case class ParamModelSelector[Query <: P pel neQuery](param: Param[Str ng])
    extends ModelSelector[Query] {
  overr de def apply(query: Query): Opt on[Str ng] = So (query.params(param))
}

/**
 * Stat c Selector that chooses t  sa  model na  always
 * @param modelNa  T  model na  to use.
 */
case class Stat cModelSelector(modelNa : Str ng) extends ModelSelector[P pel neQuery] {
  overr de def apply(query: P pel neQuery): Opt on[Str ng] = So (modelNa )
}
