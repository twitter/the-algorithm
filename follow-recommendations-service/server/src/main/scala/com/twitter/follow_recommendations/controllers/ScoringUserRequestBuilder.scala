package com.tw ter.follow_recom ndat ons.controllers

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.models.Cl entContextConverter
 mport com.tw ter.follow_recom ndat ons.common.models.DebugOpt ons
 mport com.tw ter.follow_recom ndat ons.common.models.D splayLocat on
 mport com.tw ter.follow_recom ndat ons.models.DebugParams
 mport com.tw ter.follow_recom ndat ons.models.Scor ngUserRequest
 mport com.tw ter.t  l nes.conf gap .Params
 mport javax. nject. nject
 mport javax. nject.S ngleton
 mport com.tw ter.follow_recom ndat ons.{thr ftscala => t}
 mport com.tw ter.g zmoduck.thr ftscala.UserType
 mport com.tw ter.st ch.St ch

@S ngleton
class Scor ngUserRequestBu lder @ nject() (
  requestBu lderUserFetc r: RequestBu lderUserFetc r,
  cand dateUserDebugParamsBu lder: Cand dateUserDebugParamsBu lder,
  statsRece ver: StatsRece ver) {
  pr vate val scopedStats = statsRece ver.scope(t .getClass.getS mpleNa )
  pr vate val  sSoftUserCounter = scopedStats.counter(" s_soft_user")

  def fromThr ft(req: t.Scor ngUserRequest): St ch[Scor ngUserRequest] = {
    requestBu lderUserFetc r.fetchUser(req.cl entContext.user d).map { userOpt =>
      val  sSoftUser = userOpt.ex sts(_.userType == UserType.Soft)
       f ( sSoftUser)  sSoftUserCounter. ncr()

      val cand dateUsersParamsMap = cand dateUserDebugParamsBu lder.fromThr ft(req)
      val cand dates = req.cand dates.map { cand date =>
        Cand dateUser
          .fromUserRecom ndat on(cand date).copy(params =
            cand dateUsersParamsMap.paramsMap.getOrElse(cand date.user d, Params. nval d))
      }

      Scor ngUserRequest(
        cl entContext = Cl entContextConverter.fromThr ft(req.cl entContext),
        d splayLocat on = D splayLocat on.fromThr ft(req.d splayLocat on),
        params = Params.Empty,
        debugOpt ons = req.debugParams.map(DebugOpt ons.fromDebugParamsThr ft),
        recentFollo dUser ds = None,
        recentFollo dByUser ds = None,
        wtf mpress ons = None,
        s m larToUser ds = N l,
        cand dates = cand dates,
        debugParams = req.debugParams.map(DebugParams.fromThr ft),
         sSoftUser =  sSoftUser
      )
    }
  }

}
