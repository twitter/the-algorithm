package com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. em.t et

 mport com.tw ter.product_m xer.component_l brary.model.cand date.BaseT etCand date
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.t et.T  l nesScore nfo
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

tra  BaseT  l nesScore nfoBu lder[-Query <: P pel neQuery, -Cand date <: BaseT etCand date] {

  def apply(
    query: Query,
    cand date: Cand date,
    cand dateFeatures: FeatureMap
  ): Opt on[T  l nesScore nfo]
}
