package com.tw ter.product_m xer.component_l brary.feature_hydrator.query.param_gated.featurestorev1

 mport com.tw ter.ml.featurestore.l b.Ent y d
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featurestorev1.BaseFeatureStoreV1QueryFeature
 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.Alert
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.featurestorev1.FeatureStoreV1Dynam cCl entBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.featurestorev1.FeatureStoreV1QueryFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common.Cond  onally
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.conf gap .Param

/**
 * A [[QueryFeatureHydrator]] w h [[Cond  onally]] based on a [[Param]]
 *
 * @param enabledParam t  param to turn t  [[QueryFeatureHydrator]] on and off
 * @param queryFeatureHydrator t  underly ng [[QueryFeatureHydrator]] to run w n `enabledParam`  s true
 * @tparam Query T  doma n model for t  query or request
 * @tparam Result T  type of t  cand dates
 */
case class ParamGatedFeatureStoreV1QueryFeatureHydrator[
  Query <: P pel neQuery,
  Result <: Un versalNoun[Any]
](
  enabledParam: Param[Boolean],
  queryFeatureHydrator: FeatureStoreV1QueryFeatureHydrator[Query])
    extends FeatureStoreV1QueryFeatureHydrator[Query]
    w h Cond  onally[Query] {

  overr de val  dent f er: FeatureHydrator dent f er = FeatureHydrator dent f er(
    "ParamGated" + queryFeatureHydrator. dent f er.na )

  overr de val alerts: Seq[Alert] = queryFeatureHydrator.alerts

  overr de val features: Set[BaseFeatureStoreV1QueryFeature[Query, _ <: Ent y d, _]] =
    queryFeatureHydrator.features

  overr de val cl entBu lder: FeatureStoreV1Dynam cCl entBu lder =
    queryFeatureHydrator.cl entBu lder
  overr de def only f(query: Query): Boolean =
    Cond  onally.and(query, queryFeatureHydrator, query.params(enabledParam))

  overr de def hydrate(query: Query): St ch[FeatureMap] = queryFeatureHydrator.hydrate(query)
}
