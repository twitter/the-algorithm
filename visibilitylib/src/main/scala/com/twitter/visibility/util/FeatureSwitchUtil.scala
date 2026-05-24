package com.twitter.visibility.util

import com.twitter.abdecider.ABDecider
import com.twitter.featureswitches.v2.FeatureSwitches
import com.twitter.featureswitches.v2.builder.FeatureSwitchesBuilder
import com.twitter.finagle.stats.StatsReceiver

object FeatureSwitchUtil {
  private val LibraryFeaturesConfigPath = "/features/visibility/main"
  private val LimitedActionsFeaturesConfigPath = "/features/visibility-limited-actions/main"

  def mkVisibilityLibraryFeatureSwitches(
    abDecider: ABDecider,
    statsReceiver: StatsReceiver
  ): FeatureSwitches =
    FeatureSwitchesBuilder
      .createDefault(LibraryFeaturesConfigPath, abDecider, Some(statsReceiver)).build()

  def mkLimitedActionsFeatureSwitches(statsReceiver: StatsReceiver): FeatureSwitches =
    FeatureSwitchesBuilder
      .createWithNoExperiments(LimitedActionsFeaturesConfigPath, Some(statsReceiver)).build()
}
