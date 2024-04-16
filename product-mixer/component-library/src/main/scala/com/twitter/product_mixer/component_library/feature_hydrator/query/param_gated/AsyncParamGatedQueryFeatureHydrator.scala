package com.tw ter.product_m xer.component_l brary.feature_hydrator.query.param_gated

 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.Alert
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.AsyncHydrator
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.QueryFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.P pel neStep dent f er
 mport com.tw ter.product_m xer.core.model.common.Cond  onally
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.conf gap .Param

/**
 * A [[QueryFeatureHydrator]] w h [[Cond  onally]] based on a [[Param]] that hydrates asynchronously for features
 * to be before t  step  dent f ed  n [[hydrateBefore]]
 *
 * @param enabledParam t  param to turn t  [[QueryFeatureHydrator]] on and off
 * @param hydrateBefore t  [[P pel neStep dent f er]] step to make sure t  feature  s hydrated before.
 * @param queryFeatureHydrator t  underly ng [[QueryFeatureHydrator]] to run w n `enabledParam`  s true
 * @tparam Query T  doma n model for t  query or request
 * @tparam Result T  type of t  cand dates
 */
case class AsyncParamGatedQueryFeatureHydrator[
  -Query <: P pel neQuery,
  Result <: Un versalNoun[Any]
](
  enabledParam: Param[Boolean],
  overr de val hydrateBefore: P pel neStep dent f er,
  queryFeatureHydrator: QueryFeatureHydrator[Query])
    extends QueryFeatureHydrator[Query]
    w h Cond  onally[Query]
    w h AsyncHydrator {

  overr de val  dent f er: FeatureHydrator dent f er = FeatureHydrator dent f er(
    "AsyncParamGated" + queryFeatureHydrator. dent f er.na )

  overr de val alerts: Seq[Alert] = queryFeatureHydrator.alerts

  overr de val features: Set[Feature[_, _]] = queryFeatureHydrator.features

  overr de def only f(query: Query): Boolean =
    Cond  onally.and(query, queryFeatureHydrator, query.params(enabledParam))

  overr de def hydrate(query: Query): St ch[FeatureMap] = queryFeatureHydrator.hydrate(query)
}
