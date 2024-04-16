package com.tw ter.product_m xer.component_l brary.p pel ne.cand date.ads

 mport com.tw ter.product_m xer.component_l brary.model.query.ads.AdsQuery
 mport com.tw ter.product_m xer.core.funct onal_component.common.Cand dateScope
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

/**
 * Der ve an est mate of t  number of organ c  ems from t  query.  f   need a more prec se number,
 * cons der sw ch ng to [[AdsDependentCand dateP pel neConf g]]
 */
tra  Est mateNumOrgan c ems[Query <: P pel neQuery w h AdsQuery] {

  def apply(query: Query): Short
}

/**
 * Compute t  number of organ c  ems from t  query and set of prev ous cand dates.
 *
 * @note t  key d fference bet en [[CountNumOrgan c ems]] and [[Est mateNumOrgan c ems]]  s
 *       that for [[Est mateNumOrgan c ems]]   don't have any cand dates returned yet, so   can
 *       only guess as to t  number of organ c  ems  n t  result set.  n contrast,
 *       [[CountNumOrgan c ems]]  s used on dependant cand date p pel nes w re   can scan over
 *       t  cand date p pel nes result set to count t  number of organ c  ems.
 */
tra  CountNumOrgan c ems[-Query <: P pel neQuery w h AdsQuery] {

  def apply(query: Query, prev ousCand dates: Seq[Cand dateW hDeta ls]): Short
}

/**
 * Treat all prev ously retr eved cand dates as organ c
 */
case object CountAllCand dates extends CountNumOrgan c ems[P pel neQuery w h AdsQuery] {

  def apply(
    query: P pel neQuery w h AdsQuery,
    prev ousCand dates: Seq[Cand dateW hDeta ls]
  ): Short =
    prev ousCand dates.length.toShort
}

/**
 * Only count cand dates from a spec f c subset of p pel nes as organ c
 */
case class CountCand datesFromP pel nes(p pel nes: Cand dateScope)
    extends CountNumOrgan c ems[P pel neQuery w h AdsQuery] {

  def apply(
    query: P pel neQuery w h AdsQuery,
    prev ousCand dates: Seq[Cand dateW hDeta ls]
  ): Short =
    prev ousCand dates.count(p pel nes.conta ns).toShort
}
