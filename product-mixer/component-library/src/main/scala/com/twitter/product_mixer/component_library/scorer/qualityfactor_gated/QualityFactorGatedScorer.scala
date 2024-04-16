package com.tw ter.product_m xer.component_l brary.scorer.qual yfactor_gated

 mport com.tw ter.product_m xer.component_l brary.scorer.qual yfactor_gated.Qual yFactorGatedScorer. dent f erPref x
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.Alert
 mport com.tw ter.product_m xer.core.funct onal_component.scorer.Scorer
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common.Cond  onally
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.common. dent f er.Component dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.Scorer dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.qual y_factor.HasQual yFactorStatus
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.conf gap .Param

/**
 * A [[scorer]] w h [[Cond  onally]] based on qual y factor value and threshold
 *
 * @param qual yFactorThreshold qul aty factor threshold that turn off t  scorer
 * @param p pel ne dent f er  dent f er of t  p pel ne that qual y factor  s based on
 * @param scorer t  underly ng [[scorer]] to run w n `enabledParam`  s true
 * @tparam Query T  doma n model for t  query or request
 * @tparam Result T  type of t  cand dates
 */
case class Qual yFactorGatedScorer[
  -Query <: P pel neQuery w h HasQual yFactorStatus,
  Result <: Un versalNoun[Any]
](
  p pel ne dent f er: Component dent f er,
  qual yFactorThresholdParam: Param[Double],
  scorer: Scorer[Query, Result])
    extends Scorer[Query, Result]
    w h Cond  onally[Query] {

  overr de val  dent f er: Scorer dent f er = Scorer dent f er(
     dent f erPref x + scorer. dent f er.na )

  overr de val alerts: Seq[Alert] = scorer.alerts

  overr de val features: Set[Feature[_, _]] = scorer.features

  overr de def only f(query: Query): Boolean =
    Cond  onally.and(
      query,
      scorer,
      query.getQual yFactorCurrentValue(p pel ne dent f er) >= query.params(
        qual yFactorThresholdParam))

  overr de def apply(
    query: Query,
    cand dates: Seq[Cand dateW hFeatures[Result]]
  ): St ch[Seq[FeatureMap]] = scorer(query, cand dates)
}

object Qual yFactorGatedScorer {
  val  dent f erPref x = "Qual yFactorGated"
}
