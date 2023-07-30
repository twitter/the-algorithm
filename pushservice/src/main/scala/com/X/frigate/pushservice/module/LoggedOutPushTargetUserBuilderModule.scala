package com.X.frigate.pushservice.module

import com.google.inject.Provides
import com.google.inject.Singleton
import com.X.decider.Decider
import com.X.finagle.stats.StatsReceiver
import com.X.frigate.pushservice.target.LoggedOutPushTargetUserBuilder
import com.X.frigate.pushservice.config.DeployConfig
import com.X.inject.XModule

object LoggedOutPushTargetUserBuilderModule extends XModule {

  @Provides
  @Singleton
  def providesLoggedOutPushTargetUserBuilder(
    decider: Decider,
    config: DeployConfig,
    statsReceiver: StatsReceiver
  ): LoggedOutPushTargetUserBuilder = {
    LoggedOutPushTargetUserBuilder(
      historyStore = config.loggedOutHistoryStore,
      inputDecider = decider,
      inputAbDecider = config.abDecider,
      loggedOutPushInfoStore = config.loggedOutPushInfoStore
    )(statsReceiver)
  }
}
