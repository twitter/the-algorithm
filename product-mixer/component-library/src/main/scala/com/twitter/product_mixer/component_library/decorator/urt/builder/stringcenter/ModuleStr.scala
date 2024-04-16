package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.str ngcenter

 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseModuleStr
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.str ngcenter.BaseModuleStr ngCenterPlaceholderBu lder
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.str ngcenter.cl ent.Str ngCenter
 mport com.tw ter.str ngcenter.cl ent.core.ExternalStr ng

/**
 * T  class works t  sa  as [[Str]] but passes  n a l st of cand dates to t 
 * [[BaseModuleStr ngCenterPlaceholderBu lder]] w n bu ld ng t  placeholders.
 */
case class ModuleStr[-Query <: P pel neQuery, -Cand date <: Un versalNoun[Any]](
  text: ExternalStr ng,
  str ngCenter: Str ngCenter,
  str ngCenterPlaceholderBu lder: Opt on[
    BaseModuleStr ngCenterPlaceholderBu lder[Query, Cand date]
  ] = None)
    extends BaseModuleStr[Query, Cand date] {

  def apply(query: Query, cand dates: Seq[Cand dateW hFeatures[Cand date]]): Str ng = {
    val placeholderMapOpt =
      str ngCenterPlaceholderBu lder.map(_.apply(query, cand dates))
    str ngCenter.prepare(
      externalStr ng = text,
      placeholders = placeholderMapOpt.getOrElse(Map.empty[Str ng, Any])
    )
  }
}
