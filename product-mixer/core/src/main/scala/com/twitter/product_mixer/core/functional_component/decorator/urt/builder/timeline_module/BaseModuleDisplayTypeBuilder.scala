package com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.t  l ne_module

 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.t  l ne_module.ModuleD splayType
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

tra  BaseModuleD splayTypeBu lder[-Query <: P pel neQuery, -Cand date <: Un versalNoun[Any]] {

  def apply(
    query: Query,
    cand dates: Seq[Cand dateW hFeatures[Cand date]]
  ): ModuleD splayType
}
