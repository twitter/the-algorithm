package com.X.visibility.configapi

import com.X.abdecider.LoggingABDecider
import com.X.decider.Decider
import com.X.featureswitches.v2.FeatureSwitches
import com.X.finagle.stats.StatsReceiver
import com.X.logging.Logger
import com.X.servo.util.MemoizingStatsReceiver
import com.X.timelines.configapi.Params
import com.X.visibility.models.SafetyLevel
import com.X.visibility.models.UnitOfDiversion
import com.X.visibility.models.ViewerContext

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

  private[this] val contextFactory = new VisibilityRequestContextFactory(
    abDecider,
    featureSwitches
  )

  private[this] val configBuilder = ConfigBuilder(statsReceiver.scope("config"), decider, log)

  private[this] val paramStats: MemoizingStatsReceiver = new MemoizingStatsReceiver(
    statsReceiver.scope("configapi_params"))

  def apply(
    viewerContext: ViewerContext,
    safetyLevel: SafetyLevel,
    unitsOfDiversion: Seq[UnitOfDiversion] = Seq.empty
  ): Params = {
    val config = configBuilder.build(safetyLevel)
    val requestContext = contextFactory(viewerContext, safetyLevel, unitsOfDiversion)
    config.apply(requestContext, paramStats)
  }

  def memoized(
    viewerContext: ViewerContext,
    safetyLevel: SafetyLevel,
    unitsOfDiversion: Seq[UnitOfDiversion] = Seq.empty
  ): Params = {
    val config = configBuilder.buildMemoized(safetyLevel)
    val requestContext = contextFactory(viewerContext, safetyLevel, unitsOfDiversion)
    config.apply(requestContext, paramStats)
  }
}
