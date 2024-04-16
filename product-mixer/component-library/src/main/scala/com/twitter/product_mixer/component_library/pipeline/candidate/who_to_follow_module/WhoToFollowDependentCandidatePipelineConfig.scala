package com.tw ter.product_m xer.component_l brary.p pel ne.cand date.who_to_follow_module

 mport com.tw ter.peopled scovery.ap .{thr ftscala => t}
 mport com.tw ter.product_m xer.component_l brary.cand date_s ce.people_d scovery.PeopleD scoveryCand dateS ce
 mport com.tw ter.product_m xer.component_l brary.model.cand date.UserCand date
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.BaseCand dateS ce
 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.Alert
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.Cand dateDecorator
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder. tadata.BaseFeedbackAct on nfoBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.t  l ne_module.BaseModuleD splayTypeBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lter
 mport com.tw ter.product_m xer.core.funct onal_component.gate.BaseGate
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateFeatureTransfor r
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateP pel neQueryTransfor r
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateP pel neResultsTransfor r
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.p pel ne.cand date.DependentCand dateP pel neConf g
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport com.tw ter.t  l nes.conf gap .Param
 mport com.tw ter.t  l nes.conf gap .dec der.Dec derParam

class WhoToFollowDependentCand dateP pel neConf g[Query <: P pel neQuery](
  overr de val  dent f er: Cand dateP pel ne dent f er,
  overr de val enabledDec derParam: Opt on[Dec derParam[Boolean]],
  overr de val supportedCl entParam: Opt on[FSParam[Boolean]],
  overr de val alerts: Seq[Alert],
  overr de val gates: Seq[BaseGate[Query]],
  whoToFollowCand dateS ce: PeopleD scoveryCand dateS ce,
  overr de val f lters: Seq[F lter[Query, UserCand date]],
  moduleD splayTypeBu lder: BaseModuleD splayTypeBu lder[Query, UserCand date],
  feedbackAct on nfoBu lder: Opt on[
    BaseFeedbackAct on nfoBu lder[P pel neQuery, UserCand date]
  ],
  d splayLocat onParam: Param[Str ng],
  supportedLa tsParam: Param[Seq[Str ng]],
  la tVers onParam: Param[ nt],
  excludedUser dsFeature: Opt on[Feature[P pel neQuery, Seq[Long]]])
    extends DependentCand dateP pel neConf g[
      Query,
      t.GetModuleRequest,
      t.Recom ndedUser,
      UserCand date
    ] {

  overr de val cand dateS ce: BaseCand dateS ce[t.GetModuleRequest, t.Recom ndedUser] =
    whoToFollowCand dateS ce

  overr de val queryTransfor r: Cand dateP pel neQueryTransfor r[
    P pel neQuery,
    t.GetModuleRequest
  ] = WhoToFollowCand dateP pel neQueryTransfor r(
    d splayLocat onParam = d splayLocat onParam,
    supportedLa tsParam = supportedLa tsParam,
    la tVers onParam = la tVers onParam,
    excludedUser dsFeature = excludedUser dsFeature
  )

  overr de val featuresFromCand dateS ceTransfor rs: Seq[
    Cand dateFeatureTransfor r[t.Recom ndedUser]
  ] = Seq(WhoToFollowResponseFeatureTransfor r)

  overr de val resultTransfor r: Cand dateP pel neResultsTransfor r[
    t.Recom ndedUser,
    UserCand date
  ] = { user => UserCand date(user.user d) }

  overr de val decorator: Opt on[Cand dateDecorator[Query, UserCand date]] =
    So (
      WhoToFollowCand dateDecorator(
        moduleD splayTypeBu lder,
        feedbackAct on nfoBu lder
      ))
}
