package com.tw ter.product_m xer.core.funct onal_component.selector

 mport com.tw ter.product_m xer.core.funct onal_component.common.Cand dateScope
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

/** Selects so  `rema n ngCand dates` and add t m to t  `result` */
tra  Selector[-Query <: P pel neQuery] {

  /**
   * Spec f es wh ch [[com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls.s ce]]s
   * t  [[Selector]] w ll apply to.
   *
   * @note    s up to each [[Selector]]  mple ntat on to correctly handle t  behav or
   */
  def p pel neScope: Cand dateScope

  /** Selects so  `rema n ngCand dates` and add t m to t  `result` */
  def apply(
    query: Query,
    rema n ngCand dates: Seq[Cand dateW hDeta ls],
    result: Seq[Cand dateW hDeta ls]
  ): SelectorResult
}
