package com.tw ter.product_m xer.component_l brary.s de_effect

 mport com.tw ter.product_m xer.component_l brary.s de_effect.ParamGatedP pel neResultS deEffect. dent f erPref x
 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.Alert
 mport com.tw ter.product_m xer.core.funct onal_component.s de_effect.ExecuteSynchronously
 mport com.tw ter.product_m xer.core.funct onal_component.s de_effect.Fa lOpen
 mport com.tw ter.product_m xer.core.funct onal_component.s de_effect.P pel neResultS deEffect
 mport com.tw ter.product_m xer.core.model.common.Cond  onally
 mport com.tw ter.product_m xer.core.model.common. dent f er.S deEffect dent f er
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.marshall ng.HasMarshall ng
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.conf gap .Param

/**
 * A [[P pel neResultS deEffect]] w h [[Cond  onally]] based on a [[Param]]
 *
 * @param enabledParam t  param to turn t  f lter on and off
 * @param s deEffect t  underly ng s de effect to run w n `enabledParam`  s true
 * @tparam Query T  doma n model for t  query or request
 */
sealed case class ParamGatedP pel neResultS deEffect[
  -Query <: P pel neQuery,
  ResultType <: HasMarshall ng
] pr vate (
  enabledParam: Param[Boolean],
  s deEffect: P pel neResultS deEffect[Query, ResultType])
    extends P pel neResultS deEffect[Query, ResultType]
    w h P pel neResultS deEffect.Cond  onally[Query, ResultType] {
  overr de val  dent f er: S deEffect dent f er = S deEffect dent f er(
     dent f erPref x + s deEffect. dent f er.na )
  overr de val alerts: Seq[Alert] = s deEffect.alerts
  overr de def only f(
    query: Query,
    selectedCand dates: Seq[Cand dateW hDeta ls],
    rema n ngCand dates: Seq[Cand dateW hDeta ls],
    droppedCand dates: Seq[Cand dateW hDeta ls],
    response: ResultType
  ): Boolean =
    Cond  onally.and(
      P pel neResultS deEffect
        . nputs(query, selectedCand dates, rema n ngCand dates, droppedCand dates, response),
      s deEffect,
      query.params(enabledParam))
  overr de def apply( nputs: P pel neResultS deEffect. nputs[Query, ResultType]): St ch[Un ] =
    s deEffect.apply( nputs)
}

object ParamGatedP pel neResultS deEffect {

  val  dent f erPref x = "ParamGated"

  /**
   * A [[P pel neResultS deEffect]] w h [[Cond  onally]] based on a [[Param]]
   *
   * @param enabledParam t  param to turn t  f lter on and off
   * @param s deEffect t  underly ng s de effect to run w n `enabledParam`  s true
   * @tparam Query T  doma n model for t  query or request
   */
  def apply[Query <: P pel neQuery, ResultType <: HasMarshall ng](
    enabledParam: Param[Boolean],
    s deEffect: P pel neResultS deEffect[Query, ResultType]
  ): ParamGatedP pel neResultS deEffect[Query, ResultType] = {
    s deEffect match {
      case _: Fa lOpen =>
        new ParamGatedP pel neResultS deEffect(enabledParam, s deEffect)
          w h ExecuteSynchronously
          w h Fa lOpen
      case _: ExecuteSynchronously =>
        new ParamGatedP pel neResultS deEffect(enabledParam, s deEffect) w h ExecuteSynchronously
      case _ =>
        new ParamGatedP pel neResultS deEffect(enabledParam, s deEffect)
    }
  }
}
