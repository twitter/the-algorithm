package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.t  l ne_module

 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.t  l ne_module.BaseModule aderD splayTypeBu lder
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.t  l ne_module.Class c
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.t  l ne_module.Module aderD splayType
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

case class Module aderD splayTypeBu lder[
  -Query <: P pel neQuery,
  -Cand date <: Un versalNoun[Any]
](
  module aderD splayType: Module aderD splayType = Class c)
    extends BaseModule aderD splayTypeBu lder[Query, Cand date] {

  overr de def apply(
    query: Query,
    cand dates: Seq[Cand dateW hFeatures[Cand date]]
  ): Module aderD splayType = module aderD splayType

}
