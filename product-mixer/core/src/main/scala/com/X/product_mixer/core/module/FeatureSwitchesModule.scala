package com.X.product_mixer.core.module

import com.google.inject.Provides
import com.X.abdecider.LoggingABDecider
import com.X.featureswitches.v2.FeatureSwitches
import com.X.featureswitches.v2.builder.FeatureSwitchesBuilder
import com.X.finagle.stats.StatsReceiver
import com.X.inject.XModule
import com.X.inject.annotations.Flag
import com.X.product_mixer.core.module.product_mixer_flags.ProductMixerFlagModule.ConfigRepoLocalPath
import com.X.product_mixer.core.module.product_mixer_flags.ProductMixerFlagModule.FeatureSwitchesPath
import com.X.product_mixer.core.module.product_mixer_flags.ProductMixerFlagModule.ServiceLocal
import com.X.timelines.features.app.ForcibleFeatureValuesModule
import javax.inject.Singleton

object FeatureSwitchesModule extends XModule with ForcibleFeatureValuesModule {
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
      // Track stats when an experiment impression is made. For example:
      // "experiment_impressions/test_experiment_1234/"
      // "experiment_impressions/test_experiment_1234/control"
      // "experiment_impressions/test_experiment_1234/treatment"
      .experimentImpressionStatsEnabled(true)
      .unitsOfDiversionEnable(true)

    val finalBuilder = if (isServiceLocal) {
      baseBuilder
    } else {
      baseBuilder.serviceDetailsFromAurora()
    }

    finalBuilder.build()
  }
}
