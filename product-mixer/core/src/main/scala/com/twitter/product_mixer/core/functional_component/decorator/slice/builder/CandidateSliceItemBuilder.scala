package com.tw ter.product_m xer.core.funct onal_component.decorator.sl ce.bu lder

 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.marshall ng.response.sl ce.Sl ce em
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

tra  Cand dateSl ce emBu lder[
  -Query <: P pel neQuery,
  -Bu lder nput <: Un versalNoun[Any],
  Bu lderOutput <: Sl ce em] {

  def apply(query: Query, cand date: Bu lder nput, featureMap: FeatureMap): Bu lderOutput
}
