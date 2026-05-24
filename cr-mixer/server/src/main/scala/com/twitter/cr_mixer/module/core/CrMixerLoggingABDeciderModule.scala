package com.twitter.cr_mixer.module.core

import com.google.inject.Provides
import com.twitter.abdecider.LoggingABDecider
import com.twitter.cr_mixer.featureswitch.CrMixerLoggingABDecider
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.inject.TwitterModule
import javax.inject.Singleton

object CrMixerLoggingABDeciderModule extends TwitterModule {

  @Provides
  @Singleton
  def provideABDecider(
    loggingABDecider: LoggingABDecider,
    statsReceiver: StatsReceiver
  ): CrMixerLoggingABDecider = {
    CrMixerLoggingABDecider(loggingABDecider, statsReceiver)
  }
}
