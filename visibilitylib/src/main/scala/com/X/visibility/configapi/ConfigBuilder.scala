package com.X.visibility.configapi

import com.X.decider.Decider
import com.X.finagle.stats.StatsReceiver
import com.X.logging.Logger
import com.X.servo.decider.DeciderGateBuilder
import com.X.timelines.configapi.CompositeConfig
import com.X.timelines.configapi.Config
import com.X.util.Memoize
import com.X.visibility.configapi.configs.VisibilityDeciders
import com.X.visibility.configapi.configs.VisibilityExperimentsConfig
import com.X.visibility.configapi.configs.VisibilityFeatureSwitches
import com.X.visibility.models.SafetyLevel

object ConfigBuilder {

  def apply(statsReceiver: StatsReceiver, decider: Decider, logger: Logger): ConfigBuilder = {
    val deciderGateBuilder: DeciderGateBuilder =
      new DeciderGateBuilder(decider)

    new ConfigBuilder(
      deciderGateBuilder,
      statsReceiver,
      logger
    )
  }
}

class ConfigBuilder(
  deciderGateBuilder: DeciderGateBuilder,
  statsReceiver: StatsReceiver,
  logger: Logger) {

  def buildMemoized: SafetyLevel => Config = Memoize(build)

  def build(safetyLevel: SafetyLevel): Config = {
    new CompositeConfig(
      VisibilityExperimentsConfig.config(safetyLevel) :+
        VisibilityDeciders.config(deciderGateBuilder, logger, statsReceiver, safetyLevel) :+
        VisibilityFeatureSwitches.config(statsReceiver, logger)
    )
  }
}
