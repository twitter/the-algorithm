package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.t  l ne_module

 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.EntryNa space
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neModule
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.t  l ne_module.BaseModuleD splayTypeBu lder
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseCl entEvent nfoBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseFeedbackAct on nfoBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.t  l ne_module.BaseModuleFooterBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.t  l ne_module.BaseModule aderBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.t  l ne_module.BaseModule tadataBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.t  l ne_module.BaseModuleShowMoreBehav orBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.t  l ne_module.BaseT  l neModuleBu lder
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures

case class T  l neModuleBu lder[-Query <: P pel neQuery, -Cand date <: Un versalNoun[Any]](
  entryNa space: EntryNa space,
  d splayTypeBu lder: BaseModuleD splayTypeBu lder[Query, Cand date],
  cl entEvent nfoBu lder: BaseCl entEvent nfoBu lder[Query, Cand date],
  module dGenerat on: Module dGenerat on = Automat cUn queModule d(),
  feedbackAct on nfoBu lder: Opt on[
    BaseFeedbackAct on nfoBu lder[Query, Cand date]
  ] = None,
   aderBu lder: Opt on[BaseModule aderBu lder[Query, Cand date]] = None,
  footerBu lder: Opt on[BaseModuleFooterBu lder[Query, Cand date]] = None,
   tadataBu lder: Opt on[BaseModule tadataBu lder[Query, Cand date]] = None,
  showMoreBehav orBu lder: Opt on[BaseModuleShowMoreBehav orBu lder[Query, Cand date]] = None)
    extends BaseT  l neModuleBu lder[Query, Cand date] {

  overr de def apply(
    query: Query,
    cand dates: Seq[Cand dateW hFeatures[Cand date]]
  ): T  l neModule = {
    val f rstCand date = cand dates. ad
    T  l neModule(
       d = module dGenerat on.module d,
      // Sort  ndexes are automat cally set  n t  doma n marshaller phase
      sort ndex = None,
      entryNa space = entryNa space,
      // Modules should not need an ele nt by default; only  ems should
      cl entEvent nfo =
        cl entEvent nfoBu lder(query, f rstCand date.cand date, f rstCand date.features, None),
      feedbackAct on nfo = feedbackAct on nfoBu lder.flatMap(
        _.apply(query, f rstCand date.cand date, f rstCand date.features)),
       sP nned = None,
      //  ems are automat cally set  n t  doma n marshaller phase
       ems = Seq.empty,
      d splayType = d splayTypeBu lder(query, cand dates),
       ader =  aderBu lder.flatMap(_.apply(query, cand dates)),
      footer = footerBu lder.flatMap(_.apply(query, cand dates)),
       tadata =  tadataBu lder.map(_.apply(query, cand dates)),
      showMoreBehav or = showMoreBehav orBu lder.map(_.apply(query, cand dates))
    )
  }
}
