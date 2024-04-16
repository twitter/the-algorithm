package com.tw ter.ho _m xer.funct onal_component.decorator.bu lder

 mport com.tw ter.ho _m xer.model.Ho Features.AncestorsFeature
 mport com.tw ter.product_m xer.component_l brary.model.cand date.BaseT etCand date
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.t  l ne_module.BaseModule tadataBu lder
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.t  l ne_module.ModuleConversat on tadata
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.t  l ne_module.Module tadata
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

case class Ho Conversat onModule tadataBu lder[
  -Query <: P pel neQuery,
  -Cand date <: BaseT etCand date
]() extends BaseModule tadataBu lder[Query, Cand date] {

  overr de def apply(
    query: Query,
    cand dates: Seq[Cand dateW hFeatures[Cand date]]
  ): Module tadata = Module tadata(
    ads tadata = None,
    conversat on tadata = So (
      ModuleConversat on tadata(
        allT et ds = So ((cand dates.last.cand date. d +:
          cand dates.last.features.getOrElse(AncestorsFeature, Seq.empty).map(_.t et d)).reverse),
        soc alContext = None,
        enableDedupl cat on = So (true)
      )),
    gr dCarousel tadata = None
  )
}
