package com.tw ter.product_m xer.component_l brary.feature_hydrator.cand date.qual yfactor_gated

 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.Alert
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.Cand dateFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common.Cond  onally
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.common. dent f er.Component dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.qual y_factor.HasQual yFactorStatus
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.conf gap .Param

object Qual yFactorGatedCand dateFeatureHydrator {
  val  dent f erPref x = "QfGated"
}

/**
 * A [[Cand dateFeatureHydrator]] w h [[Cond  onally]] based on a qual yFactor threshold.
 * @param p pel ne dent f er  dent f er of t  p pel ne that assoc ated w h observed qual y factor
 * @param qual yFactor nclus veThreshold t   nclus ve threshold of qual y factor that value below   results  n
 *                                        t  underly ng hydrator be ng turned off
 * @param cand dateFeatureHydrator t  underly ng [[Cand dateFeatureHydrator]] to run w n qual y factor value
 *                                  s above t  g ven  nclus ve threshold
 * @tparam Query T  doma n model for t  query or request
 * @tparam Result T  type of t  cand dates
 */
case class Qual yFactorGatedCand dateFeatureHydrator[
  -Query <: P pel neQuery w h HasQual yFactorStatus,
  Result <: Un versalNoun[Any]
](
  p pel ne dent f er: Component dent f er,
  qual yFactor nclus veThreshold: Param[Double],
  cand dateFeatureHydrator: Cand dateFeatureHydrator[Query, Result])
    extends Cand dateFeatureHydrator[Query, Result]
    w h Cond  onally[Query] {
   mport Qual yFactorGatedCand dateFeatureHydrator._

  overr de val  dent f er: FeatureHydrator dent f er = FeatureHydrator dent f er(
     dent f erPref x + cand dateFeatureHydrator. dent f er.na )

  overr de val alerts: Seq[Alert] = cand dateFeatureHydrator.alerts

  overr de val features: Set[Feature[_, _]] = cand dateFeatureHydrator.features

  overr de def only f(query: Query): Boolean = Cond  onally.and(
    query,
    cand dateFeatureHydrator,
    query.getQual yFactorCurrentValue(p pel ne dent f er) >= query.params(
      qual yFactor nclus veThreshold))

  overr de def apply(
    query: Query,
    cand date: Result,
    ex st ngFeatures: FeatureMap
  ): St ch[FeatureMap] = cand dateFeatureHydrator.apply(query, cand date, ex st ngFeatures)
}
