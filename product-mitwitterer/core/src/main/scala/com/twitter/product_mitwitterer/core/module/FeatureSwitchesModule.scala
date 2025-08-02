package com.twitter.product_mitwitterer.core.module

import com.google.inject.Provides
import com.twitter.abdecider.LoggingABDecider
import com.twitter.featureswitches.v2.FeatureSwitches
import com.twitter.featureswitches.v2.builder.FeatureSwitchesBuilder
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.inject.TwitterModule
import com.twitter.inject.annotations.Flag
import com.twitter.product_mitwitterer.core.module.product_mitwitterer_flags.ProductMitwittererFlagModule.ConfigRepoLocalPath
import com.twitter.product_mitwitterer.core.module.product_mitwitterer_flags.ProductMitwittererFlagModule.FeatureSwitchesPath
import com.twitter.product_mitwitterer.core.module.product_mitwitterer_flags.ProductMitwittererFlagModule.ServiceLocal
import com.twitter.timelines.features.app.ForcibleFeatureValuesModule
import javatwitter.inject.Singleton

object FeatureSwitchesModule etwittertends TwitterModule with ForcibleFeatureValuesModule {
  private val DefaultConfigRepoPath = "/usr/local/config"

  @Provides
  @Singleton
  def providesFeatureSwitches(
    abDecider: LoggingABDecider,
    statsReceiver: StatsReceiver,
    @Flag(ServiceLocal) isServiceLocal: Boolean,
    @Flag(ConfigRepoLocalPath) localConfigRepoPath: String,
    @Flag(FeatureSwitchesPath) featuresPath: String
  ): FeatureSwitches = {
    val configRepoPath = if (isServiceLocal) {
      localConfigRepoPath
    } else {
      DefaultConfigRepoPath
    }

    val baseBuilder = FeatureSwitchesBuilder
      .createDefault(featuresPath, abDecider, Some(statsReceiver))
      .configRepoAbsPath(configRepoPath)
      .forcedValues(getFeatureSwitchOverrides)
      // Track stats when an etwitterperiment impression is made. For etwitterample:
      // "etwitterperiment_impressions/test_etwitterperiment_1234/"
      // "etwitterperiment_impressions/test_etwitterperiment_1234/control"
      // "etwitterperiment_impressions/test_etwitterperiment_1234/treatment"
      .etwitterperimentImpressionStatsEnabled(true)
      .unitsOfDiversionEnable(true)

    val finalBuilder = if (isServiceLocal) {
      baseBuilder
    } else {
      baseBuilder.serviceDetailsFromAurora()
    }

    finalBuilder.build()
  }
}
