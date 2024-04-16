package com.tw ter.follow_recom ndat ons.models

 mport com.tw ter.follow_recom ndat ons.common.models.DebugOpt ons
 mport com.tw ter.follow_recom ndat ons.common.models.DebugOpt ons.fromDebugParamsThr ft
 mport com.tw ter.follow_recom ndat ons.logg ng.{thr ftscala => offl ne}
 mport com.tw ter.follow_recom ndat ons.{thr ftscala => t}
 mport com.tw ter.t  l nes.conf gap .{FeatureValue => Conf gAp FeatureValue}

case class DebugParams(
  featureOverr des: Opt on[Map[Str ng, Conf gAp FeatureValue]],
  debugOpt ons: Opt on[DebugOpt ons])

object DebugParams {
  def fromThr ft(thr ft: t.DebugParams): DebugParams = DebugParams(
    featureOverr des = thr ft.featureOverr des.map { map =>
      map.mapValues(FeatureValue.fromThr ft).toMap
    },
    debugOpt ons = So (
      fromDebugParamsThr ft(thr ft)
    )
  )
  def toOffl neThr ft(model: DebugParams): offl ne.Offl neDebugParams =
    offl ne.Offl neDebugParams(random zat onSeed = model.debugOpt ons.flatMap(_.random zat onSeed))
}

tra  HasFrsDebugParams {
  def frsDebugParams: Opt on[DebugParams]
}
