package com.twitter.cr_mitwitterer.module.core

import com.google.inject.Provides
import com.twitter.abdecider.LoggingABDecider
import com.twitter.cr_mitwitterer.featureswitch.CrMitwittererLoggingABDecider
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.inject.TwitterModule
import javatwitter.inject.Singleton

object CrMitwittererLoggingABDeciderModule etwittertends TwitterModule {

  @Provides
  @Singleton
  def provideABDecider(
    loggingABDecider: LoggingABDecider,
    statsReceiver: StatsReceiver
  ): CrMitwittererLoggingABDecider = {
    CrMitwittererLoggingABDecider(loggingABDecider, statsReceiver)
  }
}
