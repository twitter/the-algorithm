package com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata

 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Url
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap

tra  BaseUrlBu lder[-Query <: P pel neQuery, -Cand date <: Un versalNoun[Any]] {

  def apply(query: Query, cand date: Cand date, cand dateFeatures: FeatureMap): Url
}
