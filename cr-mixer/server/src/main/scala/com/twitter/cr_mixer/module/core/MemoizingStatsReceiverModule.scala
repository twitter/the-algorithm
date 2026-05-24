package com.twitter.cr_mixer.module.core

import com.twitter.finagle.stats.LoadedStatsReceiver
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.inject.TwitterModule
import com.twitter.servo.util.MemoizingStatsReceiver

object MemoizingStatsReceiverModule extends TwitterModule {
  override def configure(): Unit = {
    bind[StatsReceiver].toInstance(new MemoizingStatsReceiver(LoadedStatsReceiver))
  }
}
