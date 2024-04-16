package com.tw ter.product_m xer.component_l brary.p pel ne.cand date.who_to_follow_module

 mport com.tw ter.account_recom ndat ons_m xer.{thr ftscala => t}
 mport com.tw ter.product_m xer.component_l brary.cand date_s ce.account_recom ndat ons_m xer.AccountRecom ndat onsM xerCand dateS ce
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

class WhoToFollowArmDependentCand dateP pel neConf g[Query <: P pel neQuery](
  overr de val  dent f er: Cand dateP pel ne dent f er,
  overr de val enabledDec derParam: Opt on[Dec derParam[Boolean]],
  overr de val supportedCl entParam: Opt on[FSParam[Boolean]],
  overr de val alerts: Seq[Alert],
  overr de val gates: Seq[BaseGate[Query]],
  accountRecom ndat onsM xerCand dateS ce: AccountRecom ndat onsM xerCand dateS ce,
  overr de val f lters: Seq[F lter[Query, UserCand date]],
  moduleD splayTypeBu lder: BaseModuleD splayTypeBu lder[Query, UserCand date],
  feedbackAct on nfoBu lder: Opt on[
    BaseFeedbackAct on nfoBu lder[P pel neQuery, UserCand date]
  ],
  d splayLocat onParam: Param[Str ng],
  excludedUser dsFeature: Opt on[Feature[P pel neQuery, Seq[Long]]],
  prof leUser dFeature: Opt on[Feature[P pel neQuery, Long]])
    extends DependentCand dateP pel neConf g[
      Query,
      t.AccountRecom ndat onsM xerRequest,
      t.Recom ndedUser,
      UserCand date
    ] {

  overr de val cand dateS ce: BaseCand dateS ce[
    t.AccountRecom ndat onsM xerRequest,
    t.Recom ndedUser
  ] =
    accountRecom ndat onsM xerCand dateS ce

  overr de val queryTransfor r: Cand dateP pel neQueryTransfor r[
    P pel neQuery,
    t.AccountRecom ndat onsM xerRequest
  ] = WhoToFollowArmCand dateP pel neQueryTransfor r(
    d splayLocat onParam = d splayLocat onParam,
    excludedUser dsFeature = excludedUser dsFeature,
    prof leUser dFeature = prof leUser dFeature
  )

  overr de val featuresFromCand dateS ceTransfor rs: Seq[
    Cand dateFeatureTransfor r[t.Recom ndedUser]
  ] = Seq(WhoToFollowArmResponseFeatureTransfor r)

  overr de val resultTransfor r: Cand dateP pel neResultsTransfor r[
    t.Recom ndedUser,
    UserCand date
  ] = { user => UserCand date(user.user d) }

  overr de val decorator: Opt on[Cand dateDecorator[Query, UserCand date]] =
    So (
      WhoToFollowArmCand dateDecorator(
        moduleD splayTypeBu lder,
        feedbackAct on nfoBu lder
      ))
}
