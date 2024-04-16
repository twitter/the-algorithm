package com.tw ter.ho _m xer.product.follow ng

 mport com.tw ter.ho _m xer.funct onal_component.decorator.urt.bu lder.Ho WhoToFollowFeedbackAct on nfoBu lder
 mport com.tw ter.ho _m xer.funct onal_component.gate.D sm ssFat gueGate
 mport com.tw ter.ho _m xer.funct onal_component.gate.T  l nesPers stenceStoreLast nject onGate
 mport com.tw ter.ho _m xer.model.Ho Features.D sm ss nfoFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Pers stenceEntr esFeature
 mport com.tw ter.ho _m xer.model.Ho Features.WhoToFollowExcludedUser dsFeature
 mport com.tw ter.ho _m xer.product.follow ng.model.Follow ngQuery
 mport com.tw ter.ho _m xer.product.follow ng.param.Follow ngParam.EnableWhoToFollowCand dateP pel neParam
 mport com.tw ter.ho _m xer.product.follow ng.param.Follow ngParam.WhoToFollowD splayLocat onParam
 mport com.tw ter.ho _m xer.product.follow ng.param.Follow ngParam.WhoToFollowD splayType dParam
 mport com.tw ter.ho _m xer.product.follow ng.param.Follow ngParam.WhoToFollowM n nject on ntervalParam
 mport com.tw ter.ho _m xer.serv ce.Ho M xerAlertConf g
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.t  l ne_module.ParamWhoToFollowModuleD splayTypeBu lder
 mport com.tw ter.product_m xer.component_l brary.gate.NonEmptyCand datesGate
 mport com.tw ter.product_m xer.component_l brary.p pel ne.cand date.who_to_follow_module.WhoToFollowArmCand dateP pel neConf g
 mport com.tw ter.product_m xer.component_l brary.p pel ne.cand date.who_to_follow_module.WhoToFollowArmDependentCand dateP pel neConf g
 mport com.tw ter.product_m xer.component_l brary.p pel ne.cand date.who_to_follow_module.WhoToFollowArmDependentCand dateP pel neConf gBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.common.Cand dateScope
 mport com.tw ter.product_m xer.core.funct onal_component.conf gap .Stat cParam
 mport com.tw ter.product_m xer.core.funct onal_component.gate.BaseGate
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.t  l neserv ce.model.r ch.Ent y dType
 mport com.tw ter.t  l neserv ce.suggests.thr ftscala.SuggestType
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Follow ngWhoToFollowCand dateP pel neConf gBu lder @ nject() (
  whoToFollowArmDependentCand dateP pel neConf gBu lder: WhoToFollowArmDependentCand dateP pel neConf gBu lder,
  ho WhoToFollowFeedbackAct on nfoBu lder: Ho WhoToFollowFeedbackAct on nfoBu lder) {

  def bu ld(
    requ redNonEmptyP pel nes: Cand dateScope
  ): WhoToFollowArmDependentCand dateP pel neConf g[Follow ngQuery] = {
    val gates: Seq[BaseGate[P pel neQuery]] = Seq(
      T  l nesPers stenceStoreLast nject onGate(
        WhoToFollowM n nject on ntervalParam,
        Pers stenceEntr esFeature,
        Ent y dType.WhoToFollow
      ),
      D sm ssFat gueGate(SuggestType.WhoToFollow, D sm ss nfoFeature),
      NonEmptyCand datesGate(requ redNonEmptyP pel nes)
    )

    whoToFollowArmDependentCand dateP pel neConf gBu lder.bu ld[Follow ngQuery](
       dent f er = WhoToFollowArmCand dateP pel neConf g. dent f er,
      supportedCl entParam = So (EnableWhoToFollowCand dateP pel neParam),
      alerts = alerts,
      gates = gates,
      moduleD splayTypeBu lder =
        ParamWhoToFollowModuleD splayTypeBu lder(WhoToFollowD splayType dParam),
      feedbackAct on nfoBu lder = So (ho WhoToFollowFeedbackAct on nfoBu lder),
      d splayLocat onParam = Stat cParam(WhoToFollowD splayLocat onParam.default),
      excludedUser dsFeature = So (WhoToFollowExcludedUser dsFeature),
      prof leUser dFeature = None
    )
  }

  pr vate val alerts = Seq(
    Ho M xerAlertConf g.Bus nessH s.defaultSuccessRateAlert(70),
    Ho M xerAlertConf g.Bus nessH s.defaultEmptyResponseRateAlert()
  )
}
