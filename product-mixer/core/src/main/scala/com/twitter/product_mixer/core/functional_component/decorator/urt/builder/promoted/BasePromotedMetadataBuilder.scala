package com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.promoted

 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted.Promoted tadata
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

tra  BasePromoted tadataBu lder[-Query <: P pel neQuery, -Cand date <: Un versalNoun[Any]] {

  def apply(
    query: Query,
    cand date: Cand date,
    cand dateFeatures: FeatureMap
  ): Opt on[Promoted tadata]
}
