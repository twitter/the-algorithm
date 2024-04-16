package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.conversat ons

 mport com.tw ter.product_m xer.component_l brary.model.cand date.BaseT etCand date
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.t  l ne_module.BaseModule tadataBu lder
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.t  l ne_module.ModuleConversat on tadata
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.t  l ne_module.Module tadata
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

case class Conversat onModule tadataBu lder[
  Query <: P pel neQuery,
  Cand date <: BaseT etCand date
](
  ancestor dsFeature: Feature[_, Seq[Long]],
  all dsOrder ng: Order ng[Long])
    extends BaseModule tadataBu lder[Query, Cand date] {

  overr de def apply(
    query: Query,
    cand dates: Seq[Cand dateW hFeatures[Cand date]]
  ): Module tadata = {

    val ancestors = cand dates.last.features.getOrElse(ancestor dsFeature, Seq.empty)
    val sortedAllT et ds = (cand dates.last.cand date. d +: ancestors).sorted(all dsOrder ng)

    Module tadata(
      ads tadata = None,
      conversat on tadata = So (
        ModuleConversat on tadata(
          allT et ds = So (sortedAllT et ds),
          soc alContext = None,
          enableDedupl cat on = So (true)
        )),
      gr dCarousel tadata = None
    )
  }
}
