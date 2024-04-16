package com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. em.top c

 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.top c.Top cD splayType

tra  BaseTop cD splayTypeBu lder[-Query <: P pel neQuery, -Cand date <: Un versalNoun[Any]] {

  def apply(
    query: P pel neQuery,
    cand date: Cand date,
    cand dateFeatures: FeatureMap
  ): Opt on[Top cD splayType]
}
