package com.tw ter.product_m xer.component_l brary.selector

 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.funct onal_component.common.Cand dateScope
 mport com.tw ter.product_m xer.core.funct onal_component.selector.Selector
 mport com.tw ter.product_m xer.core.funct onal_component.selector.SelectorResult
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

/**
 * A selector that appends all cand dates m ss ng a spec f c feature to t  results pool and keeps
 * t  rest  n t  rema n ng cand dates. T   s useful for backf ll scor ng cand dates w hout
 * a score from a prev ous scorer.
 * @param p pel neScope T  p pel ne scope to c ck
 * @param m ss ngFeature T  m ss ng feature to c ck for.
 */
case class  nsertAppendW houtFeatureResults(
  overr de val p pel neScope: Cand dateScope,
  m ss ngFeature: Feature[_, _])
    extends Selector[P pel neQuery] {

  overr de def apply(
    query: P pel neQuery,
    rema n ngCand dates: Seq[Cand dateW hDeta ls],
    result: Seq[Cand dateW hDeta ls]
  ): SelectorResult = {
    val (cand datesW hM ss ngFeature, cand datesW hFeature) = rema n ngCand dates.part  on {
      cand date =>
        p pel neScope.conta ns(cand date) && !cand date.features.getSuccessfulFeatures
          .conta ns(m ss ngFeature)
    }
    val updatedResults = result ++ cand datesW hM ss ngFeature
    SelectorResult(rema n ngCand dates = cand datesW hFeature, result = updatedResults)
  }
}
