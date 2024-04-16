package com.tw ter.product_m xer.core.funct onal_component.feature_hydrator

 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common.SupportsCond  onally
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch

/**
 * Hydrate features for a spec f c cand date
 * e.g.  f t  cand date  s a T et t n a feature could be w t r  's  s marked as sens  ve
 *
 * @note  f   want to cond  onally run a [[BaseCand dateFeatureHydrator]]   can use t  m x n [[com.tw ter.product_m xer.core.model.common.Cond  onally]]
 *       or to gate on a [[com.tw ter.t  l nes.conf gap .Param]]   can use
 *       [[com.tw ter.product_m xer.component_l brary.feature_hydrator.cand date.param_gated.ParamGatedCand dateFeatureHydrator]] or
 *       [[com.tw ter.product_m xer.component_l brary.feature_hydrator.cand date.param_gated.ParamGatedBulkCand dateFeatureHydrator]]
 */
sealed tra  BaseCand dateFeatureHydrator[
  -Query <: P pel neQuery,
  -Result <: Un versalNoun[Any],
  FeatureType <: Feature[_, _]]
    extends FeatureHydrator[FeatureType]
    w h SupportsCond  onally[Query]

/**
 * A cand date feature hydrator that prov des an  mple ntat on for hydrat ng a s ngle cand date
 * at t  t  . Product M xer core takes care of hydrat ng all y  cand dates for   by
 * call ng t  for each cand date. T   s useful for St ch-po red downstream AP s (such
 * as Strato, G zmoduck, etc) w re t  AP  takes a s ngle cand date/key and St ch handles
 * batch ng for  .
 *
 * @note Any except ons that are thrown or returned as [[St ch.except on]] w ll be added to t 
 *       [[FeatureMap]] for *all* [[Feature]]s  ntended to be hydrated.
 *       Access ng a fa led Feature w ll throw  f us ng [[FeatureMap.get]] for Features that aren't
 *       [[com.tw ter.product_m xer.core.feature.FeatureW hDefaultOnFa lure]]
 *
 * @tparam Query T  query type
 * @tparam Result T  Cand date type
 */
tra  Cand dateFeatureHydrator[-Query <: P pel neQuery, -Result <: Un versalNoun[Any]]
    extends BaseCand dateFeatureHydrator[Query, Result, Feature[_, _]] {

  overr de val  dent f er: FeatureHydrator dent f er

  /** Hydrates a [[FeatureMap]] for a s ngle cand date */
  def apply(query: Query, cand date: Result, ex st ngFeatures: FeatureMap): St ch[FeatureMap]
}

/**
 * Hydrate features for a l st of cand dates
 * e.g. for a l st of T et cand dates, a feature could be t  v s b l y reason w t r to show or not show each T et
 */
tra  BaseBulkCand dateFeatureHydrator[
  -Query <: P pel neQuery,
  -Result <: Un versalNoun[Any],
  FeatureType <: Feature[_, _]]
    extends BaseCand dateFeatureHydrator[Query, Result, FeatureType] {

  /**
   * Hydrates a set of [[FeatureMap]]s for t  bulk l st of cand dates. Every  nput cand date must
   * have correspond ng entry  n t  returned seq w h a feature map.
   */
  def apply(
    query: Query,
    cand dates: Seq[Cand dateW hFeatures[Result]]
  ): St ch[Seq[FeatureMap]]
}

/**
 * A cand date feature hydrator that allows a user to bulk hydrate features for all cand dates
 * at once. T   s useful for downstream AP s that take a l st of cand dates  n one go such
 * as feature store or scorers.
 *
 * @note Any except ons that are thrown or returned as [[St ch.except on]] w ll be added to t 
 *       [[FeatureMap]] for *all* [[Feature]]s of *all* cand dates  ntended to be hydrated.
 *       An alternat ve to throw ng an except on  s per-cand date fa lure handl ng (e.g. add ng
 *       a fa led [[Feature]] w h `addFa lure`, a Try w h `add`, or an opt onal value w h `add`
 *       us ng [[FeatureMapBu lder]]).
 *       Access ng a fa led Feature w ll throw  f us ng [[FeatureMap.get]] for Features that aren't
 *       [[com.tw ter.product_m xer.core.feature.FeatureW hDefaultOnFa lure]].
 *
 * @tparam Query T  query type
 * @tparam Result T  Cand date type
 */
tra  BulkCand dateFeatureHydrator[-Query <: P pel neQuery, Cand date <: Un versalNoun[Any]]
    extends BaseBulkCand dateFeatureHydrator[Query, Cand date, Feature[_, _]] {
  overr de val  dent f er: FeatureHydrator dent f er

  overr de def apply(
    query: Query,
    cand dates: Seq[Cand dateW hFeatures[Cand date]]
  ): St ch[Seq[FeatureMap]]
}
