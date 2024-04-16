package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.t  l ne_module

 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.t  l ne_module.BaseModuleFooterBu lder
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.t  l ne_module.ModuleFooter
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.t  l nes.conf gap .Param

case class ParamGatedModuleFooterBu lder[-Query <: P pel neQuery, -Cand date <: Un versalNoun[Any]](
  enableParam: Param[Boolean],
  enabledBu lder: BaseModuleFooterBu lder[Query, Cand date],
  defaultBu lder: Opt on[BaseModuleFooterBu lder[Query, Cand date]] = None)
    extends BaseModuleFooterBu lder[Query, Cand date] {

  def apply(
    query: Query,
    cand dates: Seq[Cand dateW hFeatures[Cand date]]
  ): Opt on[ModuleFooter] = {
     f (query.params(enableParam)) {
      enabledBu lder(query, cand dates)
    } else {
      defaultBu lder.flatMap(_.apply(query, cand dates))
    }
  }
}
