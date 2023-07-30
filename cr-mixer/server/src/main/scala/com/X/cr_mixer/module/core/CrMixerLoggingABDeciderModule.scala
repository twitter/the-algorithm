package com.X.cr_mixer.module.core

import com.google.inject.Provides
import com.X.abdecider.LoggingABDecider
import com.X.cr_mixer.featureswitch.CrMixerLoggingABDecider
import com.X.finagle.stats.StatsReceiver
import com.X.inject.XModule
import javax.inject.Singleton

object CrMixerLoggingABDeciderModule extends XModule {

  @Provides
  @Singleton
  def provideABDecider(
    loggingABDecider: LoggingABDecider,
    statsReceiver: StatsReceiver
  ): CrMixerLoggingABDecider = {
    CrMixerLoggingABDecider(loggingABDecider, statsReceiver)
  }
}
