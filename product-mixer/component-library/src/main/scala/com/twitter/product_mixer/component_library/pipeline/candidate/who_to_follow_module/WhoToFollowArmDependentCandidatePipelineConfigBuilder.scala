package com.tw ter.product_m xer.component_l brary.p pel ne.cand date.who_to_follow_module

 mport com.tw ter.product_m xer.component_l brary.cand date_s ce.account_recom ndat ons_m xer.AccountRecom ndat onsM xerCand dateS ce
 mport com.tw ter.product_m xer.component_l brary.model.cand date.UserCand date
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.Alert
 mport com.tw ter.product_m xer.core.funct onal_component.conf gap .Stat cParam
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseFeedbackAct on nfoBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.t  l ne_module.BaseModuleD splayTypeBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lter
 mport com.tw ter.product_m xer.core.funct onal_component.gate.BaseGate
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport com.tw ter.t  l nes.conf gap .Param
 mport com.tw ter.t  l nes.conf gap .dec der.Dec derParam
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class WhoToFollowArmDependentCand dateP pel neConf gBu lder @ nject() (
  accountRecom ndat onsM xerCand dateS ce: AccountRecom ndat onsM xerCand dateS ce) {

  /**
   * Bu ld a WhoToFollowArmDependentCand dateP pel neConf g
   *
   *
   * To create a regular Cand dateP pel neConf g  nstead see [[WhoToFollowArmCand dateP pel neConf gBu lder]].
   *
   * @note  f  njected classes are needed to populate para ters  n t   thod, cons der creat ng a
   *       ProductWhoToFollowCand dateP pel neConf gBu lder w h a s ngle `def bu ld()`  thod. That
   *       product-spec f c bu lder class can t n  nject everyth ng   needs ( nclud ng t  class),
   *       and delegate to t  class's bu ld()  thod w h n  s own bu ld()  thod.
   */
  def bu ld[Query <: P pel neQuery](
    moduleD splayTypeBu lder: BaseModuleD splayTypeBu lder[Query, UserCand date],
     dent f er: Cand dateP pel ne dent f er = WhoToFollowArmCand dateP pel neConf g. dent f er,
    enabledDec derParam: Opt on[Dec derParam[Boolean]] = None,
    supportedCl entParam: Opt on[FSParam[Boolean]] = None,
    alerts: Seq[Alert] = Seq.empty,
    gates: Seq[BaseGate[Query]] = Seq.empty,
    f lters: Seq[F lter[Query, UserCand date]] = Seq.empty,
    feedbackAct on nfoBu lder: Opt on[BaseFeedbackAct on nfoBu lder[
      P pel neQuery,
      UserCand date
    ]] = None,
    d splayLocat onParam: Param[Str ng] =
      Stat cParam(WhoToFollowArmCand dateP pel neQueryTransfor r.Ho D splayLocat on),
    excludedUser dsFeature: Opt on[Feature[P pel neQuery, Seq[Long]]],
    prof leUser dFeature: Opt on[Feature[P pel neQuery, Long]]
  ): WhoToFollowArmDependentCand dateP pel neConf g[Query] =
    new WhoToFollowArmDependentCand dateP pel neConf g(
       dent f er =  dent f er,
      enabledDec derParam = enabledDec derParam,
      supportedCl entParam = supportedCl entParam,
      alerts = alerts,
      gates = gates,
      accountRecom ndat onsM xerCand dateS ce = accountRecom ndat onsM xerCand dateS ce,
      f lters = f lters,
      moduleD splayTypeBu lder = moduleD splayTypeBu lder,
      feedbackAct on nfoBu lder = feedbackAct on nfoBu lder,
      d splayLocat onParam = d splayLocat onParam,
      excludedUser dsFeature = excludedUser dsFeature,
      prof leUser dFeature = prof leUser dFeature
    )
}
