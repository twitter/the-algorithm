package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.soc al_context

 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseStr
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.soc al_context.BaseSoc alContextBu lder
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.GeneralContext
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.GeneralContextType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Url
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

case class GeneralSoc alContextBu lder[-Query <: P pel neQuery, -Cand date <: Un versalNoun[Any]](
  textBu lder: BaseStr[Query, Cand date],
  contextType: GeneralContextType,
  url: Opt on[Str ng] = None,
  context mageUrls: Opt on[L st[Str ng]] = None,
  land ngUrl: Opt on[Url] = None)
    extends BaseSoc alContextBu lder[Query, Cand date] {

  def apply(
    query: Query,
    cand date: Cand date,
    cand dateFeatures: FeatureMap
  ): Opt on[GeneralContext] =
    So (
      GeneralContext(
        text = textBu lder(query, cand date, cand dateFeatures),
        contextType = contextType,
        url = url,
        context mageUrls = context mageUrls,
        land ngUrl = land ngUrl))
}
