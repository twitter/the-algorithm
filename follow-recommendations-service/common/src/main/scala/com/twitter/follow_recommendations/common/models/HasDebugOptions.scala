package com.tw ter.follow_recom ndat ons.common.models

 mport com.tw ter.follow_recom ndat ons.thr ftscala.DebugParams

case class DebugOpt ons(
  random zat onSeed: Opt on[Long] = None,
  fetchDebug nfo: Boolean = false,
  doNotLog: Boolean = false)

object DebugOpt ons {
  def fromDebugParamsThr ft(debugParams: DebugParams): DebugOpt ons = {
    DebugOpt ons(
      debugParams.random zat onSeed,
      debugParams. ncludeDebug nfo nResults.getOrElse(false),
      debugParams.doNotLog.getOrElse(false)
    )
  }
}

tra  HasDebugOpt ons {
  def debugOpt ons: Opt on[DebugOpt ons]

  def getRandom zat onSeed: Opt on[Long] = debugOpt ons.flatMap(_.random zat onSeed)

  def fetchDebug nfo: Opt on[Boolean] = debugOpt ons.map(_.fetchDebug nfo)
}

tra  HasFrsDebugOpt ons {
  def frsDebugOpt ons: Opt on[DebugOpt ons]
}
