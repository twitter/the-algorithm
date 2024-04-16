package com.tw ter.product_m xer.core.funct onal_component.scorer

 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.BaseBulkCand dateFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common.SupportsCond  onally
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.common. dent f er.Scorer dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch

/** Scores t  prov ded `cand dates` */
tra  Scorer[-Query <: P pel neQuery, -Cand date <: Un versalNoun[Any]]
    extends BaseBulkCand dateFeatureHydrator[Query, Cand date, Feature[_, _]]
    w h SupportsCond  onally[Query] {

  /** @see [[Scorer dent f er]] */
  overr de val  dent f er: Scorer dent f er

  /**
   * Features returned by t  Scorer
   */
  def features: Set[Feature[_, _]]

  /**
   * Scores t  prov ded `cand dates`
   *
   * @note t  returned Seq of [[FeatureMap]] must conta n all t   nput 'cand dates'
   * and be  n t  sa  order as t   nput 'cand dates'
   **/
  def apply(
    query: Query,
    cand dates: Seq[Cand dateW hFeatures[Cand date]]
  ): St ch[Seq[FeatureMap]]
}
