package com.tw ter.product_m xer.component_l brary.feature_hydrator.query.logged_ n_only

 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.Alert
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.QueryFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common.Cond  onally
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch

/**
 * A [[QueryFeatureHydrator]] w h [[Cond  onally]] to run only for logged  n users
 *
 * @param queryFeatureHydrator t  underly ng [[QueryFeatureHydrator]] to run w n query. sLoggedOut  s false
 * @tparam Query T  doma n model for t  query or request
 * @tparam Result T  type of t  cand dates
 */
case class Logged nOnlyQueryFeatureHydrator[-Query <: P pel neQuery, Result <: Un versalNoun[Any]](
  queryFeatureHydrator: QueryFeatureHydrator[Query])
    extends QueryFeatureHydrator[Query]
    w h Cond  onally[Query] {
  overr de val  dent f er: FeatureHydrator dent f er = FeatureHydrator dent f er(
    "Logged nOnly" + queryFeatureHydrator. dent f er.na )
  overr de val alerts: Seq[Alert] = queryFeatureHydrator.alerts
  overr de val features: Set[Feature[_, _]] = queryFeatureHydrator.features
  overr de def only f(query: Query): Boolean =
    Cond  onally.and(query, queryFeatureHydrator, !query. sLoggedOut)
  overr de def hydrate(query: Query): St ch[FeatureMap] = queryFeatureHydrator.hydrate(query)
}
