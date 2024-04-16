package com.tw ter.product_m xer.component_l brary.scorer.param_gated

 mport com.tw ter.product_m xer.component_l brary.scorer.param_gated.ParamGatedScorer. dent f erPref x
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.Alert
 mport com.tw ter.product_m xer.core.funct onal_component.scorer.Scorer
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common.Cond  onally
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.common. dent f er.Scorer dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.conf gap .Param

/**
 * A [[scorer]] w h [[Cond  onally]] based on a [[Param]]
 *
 * @param enabledParam t  param to turn t  [[scorer]] on and off
 * @param scorer t  underly ng [[scorer]] to run w n `enabledParam`  s true
 * @tparam Query T  doma n model for t  query or request
 * @tparam Result T  type of t  cand dates
 */
case class ParamGatedScorer[-Query <: P pel neQuery, Result <: Un versalNoun[Any]](
  enabledParam: Param[Boolean],
  scorer: Scorer[Query, Result])
    extends Scorer[Query, Result]
    w h Cond  onally[Query] {
  overr de val  dent f er: Scorer dent f er = Scorer dent f er(
     dent f erPref x + scorer. dent f er.na )
  overr de val alerts: Seq[Alert] = scorer.alerts
  overr de val features: Set[Feature[_, _]] = scorer.features
  overr de def only f(query: Query): Boolean =
    Cond  onally.and(query, scorer, query.params(enabledParam))
  overr de def apply(
    query: Query,
    cand dates: Seq[Cand dateW hFeatures[Result]]
  ): St ch[Seq[FeatureMap]] = scorer(query, cand dates)
}

object ParamGatedScorer {
  val  dent f erPref x = "ParamGated"
}
