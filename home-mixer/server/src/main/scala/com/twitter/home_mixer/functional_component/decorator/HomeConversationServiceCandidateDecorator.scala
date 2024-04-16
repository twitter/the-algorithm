package com.tw ter.ho _m xer.funct onal_component.decorator

 mport com.tw ter.ho _m xer.funct onal_component.decorator.bu lder.Ho Conversat onModule tadataBu lder
 mport com.tw ter.ho _m xer.funct onal_component.decorator.bu lder.Ho T  l nesScore nfoBu lder
 mport com.tw ter.ho _m xer.funct onal_component.decorator.urt.bu lder.Ho FeedbackAct on nfoBu lder
 mport com.tw ter.ho _m xer.model.Ho Features.Conversat onModuleFocalT et dFeature
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.Urt emCand dateDecorator
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.UrtMult pleModulesDecorator
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.t et.T etCand dateUrt emBu lder
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. tadata.Cl entEvent nfoBu lder
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.t  l ne_module.Stat cModuleD splayTypeBu lder
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.t  l ne_module.T  l neModuleBu lder
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.EntryNa space
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.t  l ne_module.Vert calConversat on
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.t  l nes. nject on.scr be. nject onScr beUt l
 mport com.tw ter.t  l neserv ce.suggests.{thr ftscala => st}

object Ho Conversat onServ ceCand dateDecorator {

  pr vate val Conversat onModuleNa space = EntryNa space("ho -conversat on")

  def apply(
    ho FeedbackAct on nfoBu lder: Ho FeedbackAct on nfoBu lder
  ): So [UrtMult pleModulesDecorator[P pel neQuery, T etCand date, Long]] = {
    val suggestType = st.SuggestType.RankedOrgan cT et
    val component =  nject onScr beUt l.scr beComponent(suggestType).get
    val cl entEvent nfoBu lder = Cl entEvent nfoBu lder(component)
    val t et emBu lder = T etCand dateUrt emBu lder(
      cl entEvent nfoBu lder = cl entEvent nfoBu lder,
      t  l nesScore nfoBu lder = So (Ho T  l nesScore nfoBu lder),
      feedbackAct on nfoBu lder = So (ho FeedbackAct on nfoBu lder)
    )

    val moduleBu lder = T  l neModuleBu lder(
      entryNa space = Conversat onModuleNa space,
      cl entEvent nfoBu lder = cl entEvent nfoBu lder,
      d splayTypeBu lder = Stat cModuleD splayTypeBu lder(Vert calConversat on),
       tadataBu lder = So (Ho Conversat onModule tadataBu lder())
    )

    So (
      UrtMult pleModulesDecorator(
        urt emCand dateDecorator = Urt emCand dateDecorator(t et emBu lder),
        moduleBu lder = moduleBu lder,
        groupByKey = (_, _, cand dateFeatures) =>
          cand dateFeatures.getOrElse(Conversat onModuleFocalT et dFeature, None)
      ))
  }
}
