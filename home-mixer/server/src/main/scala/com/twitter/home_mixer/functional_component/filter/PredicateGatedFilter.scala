package com.tw ter.ho _m xer.funct onal_component.f lter

 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.Alert
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lter
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lterResult
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common.Cond  onally
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.common. dent f er.F lter dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch

tra  F lterPred cate[-Query <: P pel neQuery] {
  def apply(query: Query): Boolean
}

/**
 * A [[F lter]] w h [[Cond  onally]] based on a [[F lterPred cate]]
 *
 * @param pred cate t  pred cate to turn t  f lter on and off
 * @param f lter t  underly ng f lter to run w n `pred cate`  s true
 * @tparam Query T  doma n model for t  query or request
 * @tparam Cand date T  type of t  cand dates
 */
case class Pred cateGatedF lter[-Query <: P pel neQuery, Cand date <: Un versalNoun[Any]](
  pred cate: F lterPred cate[Query],
  f lter: F lter[Query, Cand date])
    extends F lter[Query, Cand date]
    w h F lter.Cond  onally[Query, Cand date] {

  overr de val  dent f er: F lter dent f er = F lter dent f er(
    Pred cateGatedF lter. dent f erPref x + f lter. dent f er.na )

  overr de val alerts: Seq[Alert] = f lter.alerts

  overr de def only f(query: Query, cand dates: Seq[Cand dateW hFeatures[Cand date]]): Boolean =
    Cond  onally.and(F lter. nput(query, cand dates), f lter, pred cate(query))

  overr de def apply(
    query: Query,
    cand dates: Seq[Cand dateW hFeatures[Cand date]]
  ): St ch[F lterResult[Cand date]] = f lter.apply(query, cand dates)
}

object Pred cateGatedF lter {
  val  dent f erPref x = "Pred cateGated"
}
