package com.tw ter.product_m xer.component_l brary.feature_hydrator.cand date.param_gated.featurestorev1

 mport com.tw ter.ml.featurestore.l b.Ent y d
 mport com.tw ter.product_m xer.component_l brary.feature_hydrator.cand date.param_gated.featurestorev1.ParamGatedFeatureStoreV1Cand dateFeatureHydrator. dent f erPref x
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featurestorev1.BaseFeatureStoreV1Cand dateFeature
 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.Alert
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.featurestorev1.FeatureStoreV1Cand dateFeatureHydrator
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.featurestorev1.FeatureStoreV1Dynam cCl entBu lder
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common.Cond  onally
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.conf gap .Param

/**
 * A [[FeatureStoreV1Cand dateFeatureHydrator]] w h [[Cond  onally]] based on a [[Param]]
 *
 * @param enabledParam t  param to turn t  [[FeatureStoreV1Cand dateFeatureHydrator]] on and off
 * @param cand dateFeatureHydrator t  underly ng [[FeatureStoreV1Cand dateFeatureHydrator]] to run w n `enabledParam`  s true
 * @tparam Query T  doma n model for t  query or request
 * @tparam Cand date T  type of t  cand dates
 */
case class ParamGatedFeatureStoreV1Cand dateFeatureHydrator[
  Query <: P pel neQuery,
  Cand date <: Un versalNoun[Any]
](
  enabledParam: Param[Boolean],
  cand dateFeatureHydrator: FeatureStoreV1Cand dateFeatureHydrator[Query, Cand date])
    extends FeatureStoreV1Cand dateFeatureHydrator[Query, Cand date]
    w h Cond  onally[Query] {

  overr de val  dent f er: FeatureHydrator dent f er = FeatureHydrator dent f er(
     dent f erPref x + cand dateFeatureHydrator. dent f er.na )

  overr de val alerts: Seq[Alert] = cand dateFeatureHydrator.alerts

  overr de val features: Set[
    BaseFeatureStoreV1Cand dateFeature[Query, Cand date, _ <: Ent y d, _]
  ] = cand dateFeatureHydrator.features

  overr de val cl entBu lder: FeatureStoreV1Dynam cCl entBu lder =
    cand dateFeatureHydrator.cl entBu lder

  overr de def only f(query: Query): Boolean =
    Cond  onally.and(query, cand dateFeatureHydrator, query.params(enabledParam))

  overr de def apply(
    query: Query,
    cand dates: Seq[Cand dateW hFeatures[Cand date]]
  ): St ch[Seq[FeatureMap]] = cand dateFeatureHydrator(query, cand dates)
}

object ParamGatedFeatureStoreV1Cand dateFeatureHydrator {
  val  dent f erPref x = "ParamGated"
}
