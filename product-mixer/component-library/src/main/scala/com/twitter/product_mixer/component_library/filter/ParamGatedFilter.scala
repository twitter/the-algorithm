package com.tw ter.product_m xer.component_l brary.f lter

 mport com.tw ter.product_m xer.component_l brary.f lter.ParamGatedF lter. dent f erPref x
 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.Alert
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lter
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lterResult
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common.Cond  onally
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.common. dent f er.F lter dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.conf gap .Param

/**
 * A [[F lter]] w h [[Cond  onally]] based on a [[Param]]
 *
 * @param enabledParam t  param to turn t  f lter on and off
 * @param f lter t  underly ng f lter to run w n `enabledParam`  s true
 * @tparam Query T  doma n model for t  query or request
 * @tparam Cand date T  type of t  cand dates
 */
case class ParamGatedF lter[-Query <: P pel neQuery, Cand date <: Un versalNoun[Any]](
  enabledParam: Param[Boolean],
  f lter: F lter[Query, Cand date])
    extends F lter[Query, Cand date]
    w h F lter.Cond  onally[Query, Cand date] {
  overr de val  dent f er: F lter dent f er = F lter dent f er(
     dent f erPref x + f lter. dent f er.na )
  overr de val alerts: Seq[Alert] = f lter.alerts
  overr de def only f(query: Query, cand dates: Seq[Cand dateW hFeatures[Cand date]]): Boolean =
    Cond  onally.and(F lter. nput(query, cand dates), f lter, query.params(enabledParam))
  overr de def apply(
    query: Query,
    cand dates: Seq[Cand dateW hFeatures[Cand date]]
  ): St ch[F lterResult[Cand date]] = f lter.apply(query, cand dates)
}

object ParamGatedF lter {
  val  dent f erPref x = "ParamGated"
}
