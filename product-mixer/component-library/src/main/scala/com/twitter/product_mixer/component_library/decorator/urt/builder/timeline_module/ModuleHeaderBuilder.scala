package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.t  l ne_module

 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. con.BaseHor zon conBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseStr
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.soc al_context.BaseModuleSoc alContextBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.t  l ne_module.BaseModule aderBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.t  l ne_module.BaseModule aderD splayTypeBu lder
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.t  l ne_module.Class c
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata. mageVar ant
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.t  l ne_module.Module ader
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

case class Module aderBu lder[-Query <: P pel neQuery, -Cand date <: Un versalNoun[Any]](
  textBu lder: BaseStr[Query, Cand date],
   sSt cky: Opt on[Boolean] = None,
  module ader conBu lder: Opt on[BaseHor zon conBu lder[Query, Cand date]] = None,
  custom con: Opt on[ mageVar ant] = None,
  moduleSoc alContextBu lder: Opt on[BaseModuleSoc alContextBu lder[Query, Cand date]] = None,
  module aderD splayTypeBu lder: BaseModule aderD splayTypeBu lder[Query, Cand date] =
    Module aderD splayTypeBu lder(Class c))
    extends BaseModule aderBu lder[Query, Cand date] {

  overr de def apply(
    query: Query,
    cand dates: Seq[Cand dateW hFeatures[Cand date]]
  ): Opt on[Module ader] = {
    val f rstCand date = cand dates. ad
    So (
      Module ader(
        text = textBu lder(query, f rstCand date.cand date, f rstCand date.features),
        st cky =  sSt cky,
        custom con = custom con,
        soc alContext = moduleSoc alContextBu lder.flatMap(_.apply(query, cand dates)),
         con = module ader conBu lder.flatMap(_.apply(query, cand dates)),
        module aderD splayType = module aderD splayTypeBu lder(query, cand dates),
      )
    )
  }
}
