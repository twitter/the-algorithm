package com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata

 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Cl entEventDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

tra  BaseCl entEventDeta lsBu lder[-Query <: P pel neQuery, -Cand date <: Un versalNoun[Any]] {

  /**
   * @return a [[Cl entEventDeta ls]] for t  prov ded [[Cand date]]
   * @see [[Cl entEventDeta ls]]
   */
  def apply(
    query: Query,
    cand date: Cand date,
    cand dateFeatures: FeatureMap
  ): Opt on[Cl entEventDeta ls]
}
