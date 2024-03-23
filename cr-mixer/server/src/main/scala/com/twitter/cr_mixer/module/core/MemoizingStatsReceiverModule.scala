package com.ExTwitter.cr_mixer.module.core

import com.ExTwitter.finagle.stats.LoadedStatsReceiver
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.servo.util.MemoizingStatsReceiver

object MemoizingStatsReceiverModule extends ExTwitterModule {
  override def configure(): Unit = {
    bind[StatsReceiver].toInstance(new MemoizingStatsReceiver(LoadedStatsReceiver))
  }
}
