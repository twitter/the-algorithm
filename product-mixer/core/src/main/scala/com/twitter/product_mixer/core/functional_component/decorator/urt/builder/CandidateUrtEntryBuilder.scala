package com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder

 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neEntry
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

tra  Cand dateUrtEntryBu lder[
  -Query <: P pel neQuery,
  -Bu lder nput <: Un versalNoun[Any],
  Bu lderOutput <: T  l neEntry] {

  def apply(query: Query, cand date: Bu lder nput, cand dateFeatures: FeatureMap): Bu lderOutput
}
