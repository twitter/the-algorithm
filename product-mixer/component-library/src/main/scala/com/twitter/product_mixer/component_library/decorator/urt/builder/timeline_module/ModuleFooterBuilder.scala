package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.t  l ne_module

 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.t  l ne_module.ModuleFooter
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseStr
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseUrlBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.t  l ne_module.BaseModuleFooterBu lder
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures

case class ModuleFooterBu lder[-Query <: P pel neQuery, -Cand date <: Un versalNoun[Any]](
  textBu lder: BaseStr[Query, Cand date],
  urlBu lder: Opt on[BaseUrlBu lder[Query, Cand date]])
    extends BaseModuleFooterBu lder[Query, Cand date] {

  overr de def apply(
    query: Query,
    cand dates: Seq[Cand dateW hFeatures[Cand date]]
  ): Opt on[ModuleFooter] = {
    cand dates. adOpt on.map { cand date =>
      ModuleFooter(
        text = textBu lder(query, cand date.cand date, cand date.features),
        land ngUrl = urlBu lder.map(_.apply(query, cand date.cand date, cand date.features))
      )
    }
  }
}
