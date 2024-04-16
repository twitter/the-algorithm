package com.tw ter.ho _m xer.funct onal_component.s de_effect

 mport com.tw ter.ho _m xer.model.Ho Features.Follow ngLastNonPoll ngT  Feature
 mport com.tw ter.ho _m xer.model.Ho Features.NonPoll ngT  sFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Poll ngFeature
 mport com.tw ter.ho _m xer.model.request.Dev ceContext
 mport com.tw ter.ho _m xer.model.request.HasDev ceContext
 mport com.tw ter.ho _m xer.model.request.Follow ngProduct
 mport com.tw ter.ho _m xer.serv ce.Ho M xerAlertConf g
 mport com.tw ter.product_m xer.component_l brary.s de_effect.UserSess onStoreUpdateS deEffect
 mport com.tw ter.product_m xer.core.model.common. dent f er.S deEffect dent f er
 mport com.tw ter.product_m xer.core.model.marshall ng.HasMarshall ng
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.t  l neserv ce.model.ut l.F nagleRequestContext
 mport com.tw ter.user_sess on_store.ReadWr eUserSess onStore
 mport com.tw ter.user_sess on_store.Wr eRequest
 mport com.tw ter.user_sess on_store.thr ftscala.NonPoll ngT  stamps
 mport com.tw ter.user_sess on_store.thr ftscala.UserSess onF eld
 mport com.tw ter.ut l.T  

 mport javax. nject. nject
 mport javax. nject.S ngleton

/**
 * S de effect that updates t  User Sess on Store (Manhattan) w h t  t  stamps of non poll ng requests.
 */
@S ngleton
class UpdateLastNonPoll ngT  S deEffect[
  Query <: P pel neQuery w h HasDev ceContext,
  ResponseType <: HasMarshall ng] @ nject() (
  overr de val userSess onStore: ReadWr eUserSess onStore)
    extends UserSess onStoreUpdateS deEffect[
      Wr eRequest,
      Query,
      ResponseType
    ] {
  pr vate val MaxNonPoll ngT  s = 10

  overr de val  dent f er: S deEffect dent f er = S deEffect dent f er("UpdateLastNonPoll ngT  ")

  /**
   * W n t  request  s non poll ng and  s not a background fetch request, update
   * t  l st of non poll ng t  stamps w h t  t  stamp of t  current request
   */
  overr de def bu ldWr eRequest(query: Query): Opt on[Wr eRequest] = {
    val  sBackgroundFetch = query.dev ceContext
      .ex sts(_.requestContextValue.conta ns(Dev ceContext.RequestContext.BackgroundFetch))

     f (!query.features.ex sts(_.getOrElse(Poll ngFeature, false)) && ! sBackgroundFetch) {
      val f elds = Seq(UserSess onF eld.NonPoll ngT  stamps(makeLastNonPoll ngT  stamps(query)))
      So (Wr eRequest(query.getRequ redUser d, f elds))
    } else None
  }

  overr de val alerts = Seq(
    Ho M xerAlertConf g.Bus nessH s.defaultSuccessRateAlert(99.96)
  )

  pr vate def makeLastNonPoll ngT  stamps(query: Query): NonPoll ngT  stamps = {
    val pr orNonPoll ngT  stamps =
      query.features.map(_.getOrElse(NonPoll ngT  sFeature, Seq.empty)).toSeq.flatten

    val lastNonPoll ngT  Ms =
      F nagleRequestContext.default.requestStartT  .get.getOrElse(T  .now). nM ll s

    val follow ngLastNonPoll ngT   = query.features
      .flatMap(features => features.getOrElse(Follow ngLastNonPoll ngT  Feature, None))
      .map(_. nM ll s)

    NonPoll ngT  stamps(
      nonPoll ngT  stampsMs =
        (lastNonPoll ngT  Ms +: pr orNonPoll ngT  stamps).take(MaxNonPoll ngT  s),
      mostRecentHo LatestNonPoll ngT  stampMs =
         f (query.product == Follow ngProduct) So (lastNonPoll ngT  Ms)
        else follow ngLastNonPoll ngT  
    )
  }
}
