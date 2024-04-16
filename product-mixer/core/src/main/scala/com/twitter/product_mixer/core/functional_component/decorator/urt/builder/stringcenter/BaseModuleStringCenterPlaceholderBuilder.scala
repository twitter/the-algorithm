package com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.str ngcenter

 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

tra  BaseModuleStr ngCenterPlaceholderBu lder[
  -Query <: P pel neQuery,
  -Cand date <: Un versalNoun[Any]] {

  def apply(query: Query, cand dates: Seq[Cand dateW hFeatures[Cand date]]): Map[Str ng, Any]
}
