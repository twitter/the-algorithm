package com.tw ter.product_m xer.component_l brary.feature_hydrator.query.qual yfactor_gated

 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.Alert
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.QueryFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common.Cond  onally
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.common. dent f er.Component dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.qual y_factor.HasQual yFactorStatus
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.conf gap .Param

object Qual yFactorGatedQueryFeatureHydrator {
  val  dent f erPref x = "QfGated"
}

/**
 * A [[QueryFeatureHydrator]] w h [[Cond  onally]] based on a qual yFactor threshold.
 * @param p pel ne dent f er  dent f er of t  p pel ne that assoc ated w h observed qual y factor
 * @param qual yFactor nclus veThreshold t  threshold of t  qual y factor that results  n t  hydrator be ng turned off
 * @param queryFeatureHydrator t  underly ng [[QueryFeatureHydrator]] to run w n qual y factor value
 *                                  s above t  g ven  nclus ve threshold
 * @tparam Query T  doma n model for t  query or request
 * @tparam Result T  type of t  cand dates
 */
case class Qual yFactorGatedQueryFeatureHydrator[
  -Query <: P pel neQuery w h HasQual yFactorStatus,
  Result <: Un versalNoun[Any]
](
  p pel ne dent f er: Component dent f er,
  qual yFactor nclus veThreshold: Param[Double],
  queryFeatureHydrator: QueryFeatureHydrator[Query])
    extends QueryFeatureHydrator[Query]
    w h Cond  onally[Query] {
   mport Qual yFactorGatedQueryFeatureHydrator._

  overr de val  dent f er: FeatureHydrator dent f er = FeatureHydrator dent f er(
     dent f erPref x + queryFeatureHydrator. dent f er.na )

  overr de val alerts: Seq[Alert] = queryFeatureHydrator.alerts

  overr de val features: Set[Feature[_, _]] = queryFeatureHydrator.features

  overr de def only f(query: Query): Boolean = Cond  onally.and(
    query,
    queryFeatureHydrator,
    query.getQual yFactorCurrentValue(p pel ne dent f er) >= query.params(
      qual yFactor nclus veThreshold))

  overr de def hydrate(query: Query): St ch[FeatureMap] = queryFeatureHydrator.hydrate(query)
}
