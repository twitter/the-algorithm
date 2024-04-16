package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.t  l ne_module

 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.t  l ne_module.BaseModule aderBu lder
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.t  l ne_module.Module ader
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.t  l nes.conf gap .Param

case class ParamGatedModule aderBu lder[-Query <: P pel neQuery, -Cand date <: Un versalNoun[Any]](
  enableParam: Param[Boolean],
  enabledBu lder: BaseModule aderBu lder[Query, Cand date],
  defaultBu lder: Opt on[BaseModule aderBu lder[Query, Cand date]] = None)
    extends BaseModule aderBu lder[Query, Cand date] {

  def apply(
    query: Query,
    cand dates: Seq[Cand dateW hFeatures[Cand date]]
  ): Opt on[Module ader] = {
     f (query.params(enableParam)) {
      enabledBu lder(query, cand dates)
    } else {
      defaultBu lder.flatMap(_.apply(query, cand dates))
    }
  }
}
