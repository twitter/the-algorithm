package com.tw ter.product_m xer.component_l brary.feature_hydrator.cand date.param_gated

 mport com.tw ter.product_m xer.component_l brary.feature_hydrator.cand date.param_gated.ParamGatedBulkCand dateFeatureHydrator. dent f erPref x
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.Alert
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.BulkCand dateFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common.Cond  onally
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.conf gap .Param

/**
 * A [[BulkCand dateFeatureHydrator]] w h [[Cond  onally]] based on a [[Param]]
 *
 * @param enabledParam t  param to turn t  [[BulkCand dateFeatureHydrator]] on and off
 * @param bulkCand dateFeatureHydrator t  underly ng [[BulkCand dateFeatureHydrator]] to run w n `enabledParam`  s true
 * @tparam Query T  doma n model for t  query or request
 * @tparam Result T  type of t  cand dates
 */
case class ParamGatedBulkCand dateFeatureHydrator[
  -Query <: P pel neQuery,
  Result <: Un versalNoun[Any]
](
  enabledParam: Param[Boolean],
  bulkCand dateFeatureHydrator: BulkCand dateFeatureHydrator[Query, Result])
    extends BulkCand dateFeatureHydrator[Query, Result]
    w h Cond  onally[Query] {

  overr de val  dent f er: FeatureHydrator dent f er = FeatureHydrator dent f er(
     dent f erPref x + bulkCand dateFeatureHydrator. dent f er.na )

  overr de val alerts: Seq[Alert] = bulkCand dateFeatureHydrator.alerts

  overr de val features: Set[Feature[_, _]] = bulkCand dateFeatureHydrator.features

  overr de def only f(query: Query): Boolean =
    Cond  onally.and(query, bulkCand dateFeatureHydrator, query.params(enabledParam))

  overr de def apply(
    query: Query,
    cand dates: Seq[Cand dateW hFeatures[Result]]
  ): St ch[Seq[FeatureMap]] = bulkCand dateFeatureHydrator(query, cand dates)
}

object ParamGatedBulkCand dateFeatureHydrator {
  val  dent f erPref x = "ParamGated"
}
