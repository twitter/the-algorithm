package com.ExTwitter.cr_mixer.module.core

import com.google.inject.Provides
import com.ExTwitter.abdecider.LoggingABDecider
import com.ExTwitter.cr_mixer.featureswitch.CrMixerLoggingABDecider
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.inject.ExTwitterModule
import javax.inject.Singleton

object CrMixerLoggingABDeciderModule extends ExTwitterModule {

  @Provides
  @Singleton
  def provideABDecider(
    loggingABDecider: LoggingABDecider,
    statsReceiver: StatsReceiver
  ): CrMixerLoggingABDecider = {
    CrMixerLoggingABDecider(loggingABDecider, statsReceiver)
  }
}
