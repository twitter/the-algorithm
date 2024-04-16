package com.tw ter.ho _m xer.product.scored_t ets.selector

 mport com.tw ter.ho _m xer.model.Ho Features.Author dFeature
 mport com.tw ter.ho _m xer.model.Ho Features. nNetworkFeature
 mport com.tw ter.ho _m xer.model.Ho Features.ScoreFeature
 mport com.tw ter.ho _m xer.model.Ho Features.SuggestTypeFeature
 mport com.tw ter.product_m xer.core.funct onal_component.common.Cand dateScope
 mport com.tw ter.product_m xer.core.funct onal_component.selector.Selector
 mport com.tw ter.product_m xer.core.funct onal_component.selector.SelectorResult
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

case class KeepBestOutOfNetworkCand datePerAuthorPerSuggestType(
  overr de val p pel neScope: Cand dateScope)
    extends Selector[P pel neQuery] {

  overr de def apply(
    query: P pel neQuery,
    rema n ngCand dates: Seq[Cand dateW hDeta ls],
    result: Seq[Cand dateW hDeta ls]
  ): SelectorResult = {
    val (selectedCand dates, ot rCand dates) =
      rema n ngCand dates.part  on(cand date =>
        p pel neScope.conta ns(cand date) && !cand date.features.getOrElse( nNetworkFeature, true))

    val f lteredCand dates = selectedCand dates
      .groupBy { cand date =>
        (
          cand date.features.getOrElse(Author dFeature, None),
          cand date.features.getOrElse(SuggestTypeFeature, None)
        )
      }
      .values.map(_.maxBy(_.features.getOrElse(ScoreFeature, None)))
      .toSeq

    val updatedCand dates = ot rCand dates ++ f lteredCand dates
    SelectorResult(rema n ngCand dates = updatedCand dates, result = result)
  }
}
