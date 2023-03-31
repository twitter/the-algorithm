package com.twitter.follow_recommendations.common.models

import com.twitter.follow_recommendations.thriftscala.DebugParams

case class DebugOptions(
  randomizationSeed: Option[Long] = None,
  fetchDebugInfo: Boolean = false,
  doNotLog: Boolean = false)

object DebugOptions {
  def fromDebugParamsThrift(debugParams: DebugParams): DebugOptions = {
    DebugOptions(
      debugParams.randomizationSeed,
      debugParams.includeDebugInfoInResults.getOrElse(false),
      debugParams.doNotLog.getOrElse(false)
    )
  }
}

trait HasDebugOptions {
  def debugOptions: Option[DebugOptions]

  def getRandomizationSeed: Option[Long] = debugOptions.flatMap(_.randomizationSeed)

  def fetchDebugInfo: Option[Boolean] = debugOptions.map(_.fetchDebugInfo)
}

trait HasFrsDebugOptions {
  def frsDebugOptions: Option[DebugOptions]
}
