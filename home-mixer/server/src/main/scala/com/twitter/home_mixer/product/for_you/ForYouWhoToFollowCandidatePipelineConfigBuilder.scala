package com.tw ter.ho _m xer.product.for_ 

 mport com.tw ter.ho _m xer.funct onal_component.decorator.urt.bu lder.Ho WhoToFollowFeedbackAct on nfoBu lder
 mport com.tw ter.ho _m xer.funct onal_component.gate.D sm ssFat gueGate
 mport com.tw ter.ho _m xer.funct onal_component.gate.T  l nesPers stenceStoreLast nject onGate
 mport com.tw ter.ho _m xer.model.Ho Features.D sm ss nfoFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Pers stenceEntr esFeature
 mport com.tw ter.ho _m xer.model.Ho Features.WhoToFollowExcludedUser dsFeature
 mport com.tw ter.ho _m xer.product.for_ .model.For Query
 mport com.tw ter.ho _m xer.product.for_ .param.For Param.EnableWhoToFollowCand dateP pel neParam
 mport com.tw ter.ho _m xer.product.for_ .param.For Param.WhoToFollowD splayLocat onParam
 mport com.tw ter.ho _m xer.product.for_ .param.For Param.WhoToFollowD splayType dParam
 mport com.tw ter.ho _m xer.product.for_ .param.For Param.WhoToFollowM n nject on ntervalParam
 mport com.tw ter.ho _m xer.serv ce.Ho M xerAlertConf g
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.t  l ne_module.ParamWhoToFollowModuleD splayTypeBu lder
 mport com.tw ter.product_m xer.component_l brary.p pel ne.cand date.who_to_follow_module.WhoToFollowArmCand dateP pel neConf g
 mport com.tw ter.product_m xer.component_l brary.p pel ne.cand date.who_to_follow_module.WhoToFollowArmCand dateP pel neConf gBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.gate.Gate
 mport com.tw ter.t  l neserv ce.model.r ch.Ent y dType
 mport com.tw ter.t  l neserv ce.suggests.thr ftscala.SuggestType
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class For WhoToFollowCand dateP pel neConf gBu lder @ nject() (
  whoToFollowArmCand dateP pel neConf gBu lder: WhoToFollowArmCand dateP pel neConf gBu lder,
  ho WhoToFollowFeedbackAct on nfoBu lder: Ho WhoToFollowFeedbackAct on nfoBu lder) {

  def bu ld(): WhoToFollowArmCand dateP pel neConf g[For Query] = {
    val gates: Seq[Gate[For Query]] = Seq(
      T  l nesPers stenceStoreLast nject onGate(
        WhoToFollowM n nject on ntervalParam,
        Pers stenceEntr esFeature,
        Ent y dType.WhoToFollow
      ),
      D sm ssFat gueGate(SuggestType.WhoToFollow, D sm ss nfoFeature)
    )

    whoToFollowArmCand dateP pel neConf gBu lder.bu ld[For Query](
       dent f er = WhoToFollowArmCand dateP pel neConf g. dent f er,
      supportedCl entParam = So (EnableWhoToFollowCand dateP pel neParam),
      alerts = alerts,
      gates = gates,
      moduleD splayTypeBu lder =
        ParamWhoToFollowModuleD splayTypeBu lder(WhoToFollowD splayType dParam),
      feedbackAct on nfoBu lder = So (ho WhoToFollowFeedbackAct on nfoBu lder),
      excludedUser dsFeature = So (WhoToFollowExcludedUser dsFeature),
      d splayLocat onParam = WhoToFollowD splayLocat onParam
    )
  }

  pr vate val alerts = Seq(
    Ho M xerAlertConf g.Bus nessH s.defaultSuccessRateAlert(70),
    Ho M xerAlertConf g.Bus nessH s.defaultEmptyResponseRateAlert()
  )
}
