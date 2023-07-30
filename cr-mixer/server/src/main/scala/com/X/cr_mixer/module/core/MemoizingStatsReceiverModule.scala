package com.X.cr_mixer.module.core

import com.X.finagle.stats.LoadedStatsReceiver
import com.X.finagle.stats.StatsReceiver
import com.X.inject.XModule
import com.X.servo.util.MemoizingStatsReceiver

object MemoizingStatsReceiverModule extends XModule {
  override def configure(): Unit = {
    bind[StatsReceiver].toInstance(new MemoizingStatsReceiver(LoadedStatsReceiver))
  }
}
