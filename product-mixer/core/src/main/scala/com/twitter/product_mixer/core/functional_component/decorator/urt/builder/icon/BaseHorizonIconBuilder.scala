package com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. con

 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. con.Hor zon con
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

tra  BaseHor zon conBu lder[-Query <: P pel neQuery, -Cand date <: Un versalNoun[Any]] {

  def apply(
    query: Query,
    cand dates: Seq[Cand dateW hFeatures[Cand date]]
  ): Opt on[Hor zon con]
}
