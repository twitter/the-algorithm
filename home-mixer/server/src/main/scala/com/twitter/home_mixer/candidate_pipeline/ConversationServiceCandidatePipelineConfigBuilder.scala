package com.tw ter.ho _m xer.cand date_p pel ne

 mport com.tw ter.ho _m xer.funct onal_component.feature_hydrator.Na sFeatureHydrator
 mport com.tw ter.ho _m xer.funct onal_component.feature_hydrator.T etyp eFeatureHydrator
 mport com.tw ter.ho _m xer.funct onal_component.f lter. nval dSubscr pt onT etF lter
 mport com.tw ter.product_m xer.component_l brary.cand date_s ce.t etconvosvc.Conversat onServ ceCand dateS ce
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.Cand dateDecorator
 mport com.tw ter.product_m xer.core.funct onal_component.gate.BaseGate
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Conversat onServ ceCand dateP pel neConf gBu lder[Query <: P pel neQuery] @ nject() (
  conversat onServ ceCand dateS ce: Conversat onServ ceCand dateS ce,
  t etyp eFeatureHydrator: T etyp eFeatureHydrator,
   nval dSubscr pt onT etF lter:  nval dSubscr pt onT etF lter,
  na sFeatureHydrator: Na sFeatureHydrator) {

  def bu ld(
    gates: Seq[BaseGate[Query]] = Seq.empty,
    decorator: Opt on[Cand dateDecorator[Query, T etCand date]] = None
  ): Conversat onServ ceCand dateP pel neConf g[Query] = {
    new Conversat onServ ceCand dateP pel neConf g(
      conversat onServ ceCand dateS ce,
      t etyp eFeatureHydrator,
      na sFeatureHydrator,
       nval dSubscr pt onT etF lter,
      gates,
      decorator
    )
  }
}
