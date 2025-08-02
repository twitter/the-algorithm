package com.twitter.visibility.configapi

import com.twitter.abdecider.LoggingABDecider
import com.twitter.decider.Decider
import com.twitter.featureswitches.v2.FeatureSwitches
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.logging.Logger
import com.twitter.servo.util.MemoizingStatsReceiver
import com.twitter.timelines.configapi.Params
import com.twitter.visibility.models.SafetyLevel
import com.twitter.visibility.models.UnitOfDiversion
import com.twitter.visibility.models.ViewerContetwittert

object VisibilityParams {
  def apply(
    log: Logger,
    statsReceiver: StatsReceiver,
    decider: Decider,
    abDecider: LoggingABDecider,
    featureSwitches: FeatureSwitches
  ): VisibilityParams =
    new VisibilityParams(log, statsReceiver, decider, abDecider, featureSwitches)
}

class VisibilityParams(
  log: Logger,
  statsReceiver: StatsReceiver,
  decider: Decider,
  abDecider: LoggingABDecider,
  featureSwitches: FeatureSwitches) {

  private[this] val contetwittertFactory = new VisibilityRequestContetwittertFactory(
    abDecider,
    featureSwitches
  )

  private[this] val configBuilder = ConfigBuilder(statsReceiver.scope("config"), decider, log)

  private[this] val paramStats: MemoizingStatsReceiver = new MemoizingStatsReceiver(
    statsReceiver.scope("configapi_params"))

  def apply(
    viewerContetwittert: ViewerContetwittert,
    safetyLevel: SafetyLevel,
    unitsOfDiversion: Seq[UnitOfDiversion] = Seq.empty
  ): Params = {
    val config = configBuilder.build(safetyLevel)
    val requestContetwittert = contetwittertFactory(viewerContetwittert, safetyLevel, unitsOfDiversion)
    config.apply(requestContetwittert, paramStats)
  }

  def memoized(
    viewerContetwittert: ViewerContetwittert,
    safetyLevel: SafetyLevel,
    unitsOfDiversion: Seq[UnitOfDiversion] = Seq.empty
  ): Params = {
    val config = configBuilder.buildMemoized(safetyLevel)
    val requestContetwittert = contetwittertFactory(viewerContetwittert, safetyLevel, unitsOfDiversion)
    config.apply(requestContetwittert, paramStats)
  }
}
