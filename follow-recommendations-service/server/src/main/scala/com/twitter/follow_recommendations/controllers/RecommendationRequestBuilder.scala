package com.tw ter.follow_recom ndat ons.controllers

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.models.Cl entContextConverter
 mport com.tw ter.follow_recom ndat ons.common.models.D splayLocat on
 mport com.tw ter.follow_recom ndat ons.models.DebugParams
 mport com.tw ter.follow_recom ndat ons.models.D splayContext
 mport com.tw ter.follow_recom ndat ons.models.Recom ndat onRequest
 mport com.tw ter.follow_recom ndat ons.{thr ftscala => t}
 mport com.tw ter.g zmoduck.thr ftscala.UserType
 mport com.tw ter.st ch.St ch
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Recom ndat onRequestBu lder @ nject() (
  requestBu lderUserFetc r: RequestBu lderUserFetc r,
  statsRece ver: StatsRece ver) {
  pr vate val scopedStats = statsRece ver.scope(t .getClass.getS mpleNa )
  pr vate val  sSoftUserCounter = scopedStats.counter(" s_soft_user")

  def fromThr ft(tRequest: t.Recom ndat onRequest): St ch[Recom ndat onRequest] = {
    requestBu lderUserFetc r.fetchUser(tRequest.cl entContext.user d).map { userOpt =>
      val  sSoftUser = userOpt.ex sts(_.userType == UserType.Soft)
       f ( sSoftUser)  sSoftUserCounter. ncr()
      Recom ndat onRequest(
        cl entContext = Cl entContextConverter.fromThr ft(tRequest.cl entContext),
        d splayLocat on = D splayLocat on.fromThr ft(tRequest.d splayLocat on),
        d splayContext = tRequest.d splayContext.map(D splayContext.fromThr ft),
        maxResults = tRequest.maxResults,
        cursor = tRequest.cursor,
        excluded ds = tRequest.excluded ds,
        fetchPromotedContent = tRequest.fetchPromotedContent,
        debugParams = tRequest.debugParams.map(DebugParams.fromThr ft),
        userLocat onState = tRequest.userLocat onState,
         sSoftUser =  sSoftUser
      )
    }

  }
}
