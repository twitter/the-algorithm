package com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata

 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Cl entEvent nfo
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

tra  BaseCl entEvent nfoBu lder[-Query <: P pel neQuery, -Cand date <: Un versalNoun[Any]] {

  /**
   * @return a [[Cl entEvent nfo]] for t  prov ded [[Cand date]]
   * @see [[Cl entEvent nfo]]
   */
  def apply(
    query: Query,
    cand date: Cand date,
    cand dateFeatures: FeatureMap,
    ele nt: Opt on[Str ng]
  ): Opt on[Cl entEvent nfo]
}
