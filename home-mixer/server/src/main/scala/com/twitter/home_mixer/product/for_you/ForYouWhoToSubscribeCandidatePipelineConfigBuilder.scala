package com.tw ter.ho _m xer.product.for_ 

 mport com.tw ter.ho _m xer.funct onal_component.decorator.urt.bu lder.Ho WhoToSubscr beFeedbackAct on nfoBu lder
 mport com.tw ter.ho _m xer.funct onal_component.gate.D sm ssFat gueGate
 mport com.tw ter.ho _m xer.funct onal_component.gate.T  l nesPers stenceStoreLast nject onGate
 mport com.tw ter.ho _m xer.model.Ho Features.D sm ss nfoFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Pers stenceEntr esFeature
 mport com.tw ter.ho _m xer.product.for_ .model.For Query
 mport com.tw ter.ho _m xer.product.for_ .param.For Param.EnableWhoToSubscr beCand dateP pel neParam
 mport com.tw ter.ho _m xer.product.for_ .param.For Param.WhoToSubscr beD splayType dParam
 mport com.tw ter.ho _m xer.product.for_ .param.For Param.WhoToSubscr beM n nject on ntervalParam
 mport com.tw ter.ho _m xer.serv ce.Ho M xerAlertConf g
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.t  l ne_module.ParamWhoToFollowModuleD splayTypeBu lder
 mport com.tw ter.product_m xer.component_l brary.p pel ne.cand date.who_to_subscr be_module.WhoToSubscr beCand dateP pel neConf g
 mport com.tw ter.product_m xer.component_l brary.p pel ne.cand date.who_to_subscr be_module.WhoToSubscr beCand dateP pel neConf gBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.gate.Gate
 mport com.tw ter.t  l neserv ce.model.r ch.Ent y dType
 mport com.tw ter.t  l neserv ce.suggests.thr ftscala.SuggestType
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class For WhoToSubscr beCand dateP pel neConf gBu lder @ nject() (
  whoToSubscr beCand dateP pel neConf gBu lder: WhoToSubscr beCand dateP pel neConf gBu lder,
  ho WhoToSubscr beFeedbackAct on nfoBu lder: Ho WhoToSubscr beFeedbackAct on nfoBu lder) {

  def bu ld(): WhoToSubscr beCand dateP pel neConf g[For Query] = {
    val gates: Seq[Gate[For Query]] = Seq(
      T  l nesPers stenceStoreLast nject onGate(
        WhoToSubscr beM n nject on ntervalParam,
        Pers stenceEntr esFeature,
        Ent y dType.WhoToSubscr be
      ),
      D sm ssFat gueGate(SuggestType.WhoToSubscr be, D sm ss nfoFeature)
    )

    whoToSubscr beCand dateP pel neConf gBu lder.bu ld[For Query](
       dent f er = WhoToSubscr beCand dateP pel neConf g. dent f er,
      supportedCl entParam = So (EnableWhoToSubscr beCand dateP pel neParam),
      alerts = alerts,
      gates = gates,
      moduleD splayTypeBu lder =
        ParamWhoToFollowModuleD splayTypeBu lder(WhoToSubscr beD splayType dParam),
      feedbackAct on nfoBu lder = So (ho WhoToSubscr beFeedbackAct on nfoBu lder)
    )
  }

  pr vate val alerts = Seq(
    Ho M xerAlertConf g.Bus nessH s.defaultSuccessRateAlert(70),
    Ho M xerAlertConf g.Bus nessH s.defaultEmptyResponseRateAlert()
  )
}
