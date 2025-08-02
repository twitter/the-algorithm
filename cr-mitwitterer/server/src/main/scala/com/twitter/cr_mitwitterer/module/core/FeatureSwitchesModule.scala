package com.twitter.cr_mitwitterer.module.core

import com.google.inject.Provides
import com.twitter.cr_mitwitterer.featureswitch.CrMitwittererLoggingABDecider
import com.twitter.featureswitches.v2.FeatureSwitches
import com.twitter.featureswitches.v2.builder.FeatureSwitchesBuilder
import com.twitter.featureswitches.v2.etwitterperimentation.NullBucketImpressor
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.inject.TwitterModule
import com.twitter.inject.annotations.Flag
import com.twitter.util.Duration
import javatwitter.inject.Singleton

object FeatureSwitchesModule etwittertends TwitterModule {

  flag(
    name = "featureswitches.path",
    default = "/features/cr-mitwitterer/main",
    help = "path to the featureswitch configuration directory"
  )
  flag(
    "use_config_repo_mirror.bool",
    false,
    "If true, read config from a different directory, to facilitate testing.")

  val DefaultFastRefresh: Boolean = false
  val AddServiceDetailsFromAurora: Boolean = true
  val ImpressEtwitterperiments: Boolean = true

  @Provides
  @Singleton
  def providesFeatureSwitches(
    @Flag("featureswitches.path") featureSwitchDirectory: String,
    @Flag("use_config_repo_mirror.bool") useConfigRepoMirrorFlag: Boolean,
    abDecider: CrMitwittererLoggingABDecider,
    statsReceiver: StatsReceiver
  ): FeatureSwitches = {
    val configRepoAbsPath =
      getConfigRepoAbsPath(useConfigRepoMirrorFlag)
    val fastRefresh =
      shouldFastRefresh(useConfigRepoMirrorFlag)

    val featureSwitches = FeatureSwitchesBuilder()
      .abDecider(abDecider)
      .statsReceiver(statsReceiver.scope("featureswitches-v2"))
      .configRepoAbsPath(configRepoAbsPath)
      .featuresDirectory(featureSwitchDirectory)
      .limitToReferencedEtwitterperiments(shouldLimit = true)
      .etwitterperimentImpressionStatsEnabled(true)

    if (!ImpressEtwitterperiments) featureSwitches.etwitterperimentBucketImpressor(NullBucketImpressor)
    if (AddServiceDetailsFromAurora) featureSwitches.serviceDetailsFromAurora()
    if (fastRefresh) featureSwitches.refreshPeriod(Duration.fromSeconds(10))

    featureSwitches.build()
  }

  private def getConfigRepoAbsPath(
    useConfigRepoMirrorFlag: Boolean
  ): String = {
    if (useConfigRepoMirrorFlag)
      "config_repo_mirror/"
    else "/usr/local/config"
  }

  private def shouldFastRefresh(
    useConfigRepoMirrorFlag: Boolean
  ): Boolean = {
    if (useConfigRepoMirrorFlag)
      true
    else DefaultFastRefresh
  }

}
