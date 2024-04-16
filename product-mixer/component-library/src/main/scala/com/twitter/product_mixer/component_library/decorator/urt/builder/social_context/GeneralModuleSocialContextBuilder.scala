package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.soc al_context

 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseModuleStr
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.soc al_context.BaseModuleSoc alContextBu lder
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.GeneralContext
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.GeneralContextType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Url
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

/**
 * T  class works t  sa  as [[GeneralSoc alContextBu lder]] but passes a l st of cand dates
 *  nto [[BaseModuleStr]] w n render ng t  str ng.
 */
case class GeneralModuleSoc alContextBu lder[
  -Query <: P pel neQuery,
  -Cand date <: Un versalNoun[Any]
](
  textBu lder: BaseModuleStr[Query, Cand date],
  contextType: GeneralContextType,
  url: Opt on[Str ng] = None,
  context mageUrls: Opt on[L st[Str ng]] = None,
  land ngUrl: Opt on[Url] = None)
    extends BaseModuleSoc alContextBu lder[Query, Cand date] {

  def apply(
    query: Query,
    cand dates: Seq[Cand dateW hFeatures[Cand date]]
  ): Opt on[GeneralContext] =
    So (
      GeneralContext(
        text = textBu lder(query, cand dates),
        contextType = contextType,
        url = url,
        context mageUrls = context mageUrls,
        land ngUrl = land ngUrl))
}
