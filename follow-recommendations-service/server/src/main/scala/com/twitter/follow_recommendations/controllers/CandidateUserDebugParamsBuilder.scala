package com.tw ter.follow_recom ndat ons.controllers

 mport com.tw ter.follow_recom ndat ons.common.models._
 mport com.tw ter.follow_recom ndat ons.conf gap .ParamsFactory
 mport com.tw ter.follow_recom ndat ons.models.Cand dateUserDebugParams
 mport com.tw ter.follow_recom ndat ons.models.FeatureValue
 mport com.tw ter.follow_recom ndat ons.{thr ftscala => t}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Cand dateUserDebugParamsBu lder @ nject() (paramsFactory: ParamsFactory) {
  def fromThr ft(req: t.Scor ngUserRequest): Cand dateUserDebugParams = {
    val cl entContext = Cl entContextConverter.fromThr ft(req.cl entContext)
    val d splayLocat on = D splayLocat on.fromThr ft(req.d splayLocat on)

    Cand dateUserDebugParams(req.cand dates.map { cand date =>
      cand date.user d -> paramsFactory(
        cl entContext,
        d splayLocat on,
        cand date.featureOverr des
          .map(_.mapValues(FeatureValue.fromThr ft).toMap).getOrElse(Map.empty))
    }.toMap)
  }
}
