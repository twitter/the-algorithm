package com.tw ter.product_m xer.component_l brary.feature_hydrator.query.async

 mport com.tw ter.ml.featurestore.l b.Ent y d
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featurestorev1.BaseFeatureStoreV1QueryFeature
 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.Alert
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.AsyncHydrator
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.QueryFeatureHydrator
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.featurestorev1.FeatureStoreV1Dynam cCl entBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.featurestorev1.FeatureStoreV1QueryFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.P pel neStep dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch

/**
 * A [[QueryFeatureHydrator]] w h [[AsyncQueryFeatureHydrator]] that hydrated asynchronously for features
 * to be before t  step  dent f ed  n [[hydrateBefore]]
 *
 * @param hydrateBefore        t  [[P pel neStep dent f er]] step to make sure t  feature  s hydrated before.
 * @param queryFeatureHydrator t  underly ng [[QueryFeatureHydrator]] to run asynchronously
 * @tparam Query T  doma n model for t  query or request
 */
case class AsyncQueryFeatureHydrator[-Query <: P pel neQuery] pr vate[async] (
  overr de val hydrateBefore: P pel neStep dent f er,
  queryFeatureHydrator: QueryFeatureHydrator[Query])
    extends QueryFeatureHydrator[Query]
    w h AsyncHydrator {

  overr de val  dent f er: FeatureHydrator dent f er = FeatureHydrator dent f er(
    "Async" + queryFeatureHydrator. dent f er.na )
  overr de val alerts: Seq[Alert] = queryFeatureHydrator.alerts
  overr de val features: Set[Feature[_, _]] = queryFeatureHydrator.features

  overr de def hydrate(query: Query): St ch[FeatureMap] = queryFeatureHydrator.hydrate(query)
}

/**
 * A [[FeatureStoreV1QueryFeatureHydrator]] w h [[AsyncHydrator]] that hydrated asynchronously for features
 * to be before t  step  dent f ed  n [[hydrateBefore]].   need a standalone class for feature store,
 * d fferent from t  above as FStore hydrators are exempt from val dat ons at run t  .
 *
 * @param hydrateBefore        t  [[P pel neStep dent f er]] step to make sure t  feature  s hydrated before.
 * @param queryFeatureHydrator t  underly ng [[QueryFeatureHydrator]] to run asynchronously
 * @tparam Query T  doma n model for t  query or request
 */
case class AsyncFeatureStoreV1QueryFeatureHydrator[Query <: P pel neQuery] pr vate[async] (
  overr de val hydrateBefore: P pel neStep dent f er,
  featureStoreV1QueryFeatureHydrator: FeatureStoreV1QueryFeatureHydrator[Query])
    extends FeatureStoreV1QueryFeatureHydrator[
      Query
    ]
    w h AsyncHydrator {

  overr de val  dent f er: FeatureHydrator dent f er = FeatureHydrator dent f er(
    "Async" + featureStoreV1QueryFeatureHydrator. dent f er.na )
  overr de val alerts: Seq[Alert] = featureStoreV1QueryFeatureHydrator.alerts

  overr de val features: Set[BaseFeatureStoreV1QueryFeature[Query, _ <: Ent y d, _]] =
    featureStoreV1QueryFeatureHydrator.features

  overr de val cl entBu lder: FeatureStoreV1Dynam cCl entBu lder =
    featureStoreV1QueryFeatureHydrator.cl entBu lder
}

object AsyncQueryFeatureHydrator {

  /**
   * A [[QueryFeatureHydrator]] w h [[AsyncQueryFeatureHydrator]] that hydrated asynchronously for features
   * to be before t  step  dent f ed  n [[hydrateBefore]]
   *
   * @param hydrateBefore        t  [[P pel neStep dent f er]] step to make sure t  feature  s hydrated before.
   * @param queryFeatureHydrator t  underly ng [[QueryFeatureHydrator]] to run asynchronously
   * @tparam Query T  doma n model for t  query or request
   */
  def apply[Query <: P pel neQuery](
    hydrateBefore: P pel neStep dent f er,
    queryFeatureHydrator: QueryFeatureHydrator[Query]
  ): AsyncQueryFeatureHydrator[Query] =
    new AsyncQueryFeatureHydrator(hydrateBefore, queryFeatureHydrator)

  /**
   * A [[FeatureStoreV1QueryFeatureHydrator]] w h [[AsyncHydrator]] that hydrated asynchronously for features
   * to be before t  step  dent f ed  n [[hydrateBefore]].   need a standalone class for feature store,
   * d fferent from t  above as FStore hydrators are exempt from val dat ons at run t  .
   *
   * @param hydrateBefore        t  [[P pel neStep dent f er]] step to make sure t  feature  s hydrated before.
   * @param queryFeatureHydrator t  underly ng [[QueryFeatureHydrator]] to run asynchronously
   * @tparam Query T  doma n model for t  query or request
   */
  def apply[Query <: P pel neQuery](
    hydrateBefore: P pel neStep dent f er,
    featureStoreV1QueryFeatureHydrator: FeatureStoreV1QueryFeatureHydrator[Query]
  ): AsyncFeatureStoreV1QueryFeatureHydrator[Query] =
    new AsyncFeatureStoreV1QueryFeatureHydrator(hydrateBefore, featureStoreV1QueryFeatureHydrator)
}
