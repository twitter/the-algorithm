package com.tw ter.product_m xer.core.funct onal_component.feature_hydrator

 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.model.common.SupportsCond  onally
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.P pel neStep dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch

/**
 * Hydrate features about t  query  self (not about t  cand dates)
 * e.g. features about t  user who  s mak ng t  request, what country t  request or g nated from, etc.
 *
 * @note [[BaseQueryFeatureHydrator]]s populate [[Feature]]s w h last-wr e-w ns semant cs for
 *       dupl cate [[Feature]]s, w re t  last hydrator to run that populates a [[Feature]] w ll
 *       overr de any prev ously run [[BaseQueryFeatureHydrator]]s values for that [[Feature]].
 *        n a [[com.tw ter.product_m xer.core.p pel ne.P pel neConf g P pel neConf g]] t   ans
 *       that t  r ght-most [[BaseQueryFeatureHydrator]] to populate a g ven [[Feature]] w ll be
 *       t  value that  s ava lable to use.
 *
 * @note  f   want to cond  onally run a [[BaseQueryFeatureHydrator]]   can use t  m x n [[com.tw ter.product_m xer.core.model.common.Cond  onally]]
 *       or to gate on a [[com.tw ter.t  l nes.conf gap .Param]]   can use [[com.tw ter.product_m xer.component_l brary.feature_hydrator.query.param_gated.ParamGatedQueryFeatureHydrator]]
 *
 * @note Any except ons that are thrown or returned as [[St ch.except on]] w ll be added to t 
 *       [[FeatureMap]] for t  [[Feature]]s that  re supposed to be hydrated.
 *       Access ng a fa led Feature w ll throw  f us ng [[FeatureMap.get]] for Features that aren't
 *       [[com.tw ter.product_m xer.core.feature.FeatureW hDefaultOnFa lure]]
 */
tra  BaseQueryFeatureHydrator[-Query <: P pel neQuery, FeatureType <: Feature[_, _]]
    extends FeatureHydrator[FeatureType]
    w h SupportsCond  onally[Query] {

  overr de val  dent f er: FeatureHydrator dent f er

  /** Hydrates a [[FeatureMap]] for a g ven [[Query]] */
  def hydrate(query: Query): St ch[FeatureMap]
}

tra  QueryFeatureHydrator[-Query <: P pel neQuery]
    extends BaseQueryFeatureHydrator[Query, Feature[_, _]]

/**
 * W n an [[AsyncHydrator]]  s run   w ll hydrate features  n t  background
 * and w ll make t m ava lable start ng at t  spec f ed po nt  n execut on.
 *
 * W n `hydrateBefore`  s reac d, any dupl cate [[Feature]]s that  re already hydrated w ll be
 * overr dden w h t  new value from t  [[AsyncHydrator]]
 *
 * @note [[AsyncHydrator]]s have t  sa  last-wr e-w ns semant cs for dupl cate [[Feature]]s
 *       as [[BaseQueryFeatureHydrator]] but w h so  nuance.  f [[AsyncHydrator]]s for t 
 *       sa  [[Feature]] have t  sa  `hydrateBefore` t n t  r ght-most [[AsyncHydrator]]s
 *       value takes precedence. S m larly, [[AsyncHydrator]]s always hydrate after any ot r
 *       [[BaseQueryFeatureHydrator]]. See t  examples for more deta l.
 * @example  f [[QueryFeatureHydrator]]s that populate t  sa  [[Feature]] are def ned  n a `P pel neConf g`
 *          such as `[ asyncHydratorForFeatureA, normalHydratorForFeatureA ]`, w re `asyncHydratorForFeatureA`
 *           s an [[AsyncHydrator]], w n `asyncHydratorForFeatureA` reac s  's `hydrateBefore`
 *          Step  n t  P pel ne, t  value for `FeatureA` from t  `asyncHydratorForFeatureA` w ll overr de
 *          t  ex st ng value from `normalHydratorForFeatureA`, even though  n t   n  al `P pel neConf g`
 *          t y are ordered d fferently.
 * @example  f [[AsyncHydrator]]s that populate t  sa  [[Feature]] are def ned  n a `P pel neConf g`
 *          such as `[ asyncHydratorForFeatureA1, asyncHydratorForFeatureA2 ]`, w re both [[AsyncHydrator]]s
 *          have t  sa  `hydrateBefore`, w n `hydrateBefore`  s reac d, t  value for `FeatureA` from
 *          `asyncHydratorForFeatureA2` w ll overr de t  value from `asyncHydratorForFeatureA1`.
 */
tra  AsyncHydrator {
  _: BaseQueryFeatureHydrator[_, _] =>

  /**
   * A [[P pel neStep dent f er]] from t  [[com.tw ter.product_m xer.core.p pel ne.P pel neConf g]] t   s used  n
   * by wh ch t  [[FeatureMap]] returned by t  [[AsyncHydrator]] w ll be completed.
   *
   * Access to t  [[Feature]]s from t  [[AsyncHydrator]] pr or to reach ng t  prov ded
   * [[P pel neStep dent f er]]s w ll result  n a [[com.tw ter.product_m xer.core.feature.featuremap.M ss ngFeatureExcept on]].
   *
   * @note  f [[P pel neStep dent f er]]  s a Step wh ch  s run  n parallel, t  [[Feature]]s w ll be ava lable for all t  parallel Steps.
   */
  def hydrateBefore: P pel neStep dent f er
}
