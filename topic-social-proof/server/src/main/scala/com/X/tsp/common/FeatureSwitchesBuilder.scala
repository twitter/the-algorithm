package com.X.tsp.common

import com.X.abdecider.LoggingABDecider
import com.X.featureswitches.v2.FeatureSwitches
import com.X.featureswitches.v2.builder.{FeatureSwitchesBuilder => FsBuilder}
import com.X.featureswitches.v2.experimentation.NullBucketImpressor
import com.X.finagle.stats.StatsReceiver
import com.X.util.Duration

case class FeatureSwitchesBuilder(
  statsReceiver: StatsReceiver,
  abDecider: LoggingABDecider,
  featuresDirectory: String,
  addServiceDetailsFromAurora: Boolean,
  configRepoDirectory: String = "/usr/local/config",
  fastRefresh: Boolean = false,
  impressExperiments: Boolean = true) {

  def build(): FeatureSwitches = {
    val featureSwitches = FsBuilder()
      .abDecider(abDecider)
      .statsReceiver(statsReceiver)
      .configRepoAbsPath(configRepoDirectory)
      .featuresDirectory(featuresDirectory)
      .limitToReferencedExperiments(shouldLimit = true)
      .experimentImpressionStatsEnabled(true)

    if (!impressExperiments) featureSwitches.experimentBucketImpressor(NullBucketImpressor)
    if (addServiceDetailsFromAurora) featureSwitches.serviceDetailsFromAurora()
    if (fastRefresh) featureSwitches.refreshPeriod(Duration.fromSeconds(10))

    featureSwitches.build()
  }
}
