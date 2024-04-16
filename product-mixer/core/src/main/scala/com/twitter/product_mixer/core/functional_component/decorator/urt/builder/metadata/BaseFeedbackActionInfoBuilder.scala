package com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata

 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.FeedbackAct on nfo
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

tra  BaseFeedbackAct on nfoBu lder[-Query <: P pel neQuery, -Cand date <: Un versalNoun[Any]] {

  def apply(
    query: Query,
    cand date: Cand date,
    cand dateFeatures: FeatureMap
  ): Opt on[FeedbackAct on nfo]
}
