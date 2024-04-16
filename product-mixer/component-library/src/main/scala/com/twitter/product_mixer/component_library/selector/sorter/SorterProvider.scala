package com.tw ter.product_m xer.component_l brary.selector.sorter

 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

/**
 * Makes a [[Sorter]] to run for t  g ven  nput based on t 
 * [[P pel neQuery]], t  `rema n ngCand dates`, and t  `result`.
 *
 * @note t  should be used to choose bet en d fferent [[Sorter]]s,
 *        f   want to cond  onally sort wrap y  [[Sorter]] w h
 *       [[com.tw ter.product_m xer.component_l brary.selector.SelectCond  onally]]  nstead.
 */
tra  SorterProv der {

  /** Makes a [[Sorter]] for t  g ven  nputs */
  def sorter(
    query: P pel neQuery,
    rema n ngCand dates: Seq[Cand dateW hDeta ls],
    result: Seq[Cand dateW hDeta ls]
  ): Sorter
}

/**
 * Sorts t  cand dates
 *
 * All [[Sorter]]s also  mple nt [[SorterProv der]] to prov de t mselves for conven ence.
 */
tra  Sorter { self: SorterProv der =>

  /** Sorts t  `cand dates` */
  def sort[Cand date <: Cand dateW hDeta ls](cand dates: Seq[Cand date]): Seq[Cand date]

  /** Any [[Sorter]] can be used  n place of a [[SorterProv der]] to prov de  self */
  overr de f nal def sorter(
    query: P pel neQuery,
    rema n ngCand dates: Seq[Cand dateW hDeta ls],
    result: Seq[Cand dateW hDeta ls]
  ): Sorter = self
}
