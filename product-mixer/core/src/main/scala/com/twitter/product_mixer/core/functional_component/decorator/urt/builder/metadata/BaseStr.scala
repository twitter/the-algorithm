package com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata

 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

tra  BaseStr[-Query <: P pel neQuery, -Cand date <: Un versalNoun[Any]] {

  def apply(query: Query, cand date: Cand date, cand dateFeatures: FeatureMap): Str ng
}
