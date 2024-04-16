package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.str ngcenter

 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseStr
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.str ngcenter.BaseStr ngCenterPlaceholderBu lder
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.str ngcenter.cl ent.Str ngCenter
 mport com.tw ter.str ngcenter.cl ent.core.ExternalStr ng

case class StrStat c(
  text: Str ng)
    extends BaseStr[P pel neQuery, Un versalNoun[Any]] {
  def apply(
    query: P pel neQuery,
    cand date: Un versalNoun[Any],
    cand dateFeatures: FeatureMap
  ): Str ng = text
}

case class Str[-Query <: P pel neQuery, -Cand date <: Un versalNoun[Any]](
  text: ExternalStr ng,
  str ngCenter: Str ngCenter,
  str ngCenterPlaceholderBu lder: Opt on[BaseStr ngCenterPlaceholderBu lder[Query, Cand date]] =
    None)
    extends BaseStr[Query, Cand date] {

  def apply(query: Query, cand date: Cand date, cand dateFeatures: FeatureMap): Str ng = {
    val placeholderMapOpt =
      str ngCenterPlaceholderBu lder.map(_.apply(query, cand date, cand dateFeatures))
    str ngCenter.prepare(
      externalStr ng = text,
      placeholders = placeholderMapOpt.getOrElse(Map.empty[Str ng, Any])
    )
  }
}
