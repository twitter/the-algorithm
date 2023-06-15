package com.twitter.frigate.pushservice.module

import com.google.inject.Provides
import com.google.inject.Singleton
import com.twitter.decider.Decider
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.frigate.pushservice.target.LoggedOutPushTargetUserBuilder
import com.twitter.frigate.pushservice.config.DeployConfig
import com.twitter.inject.TwitterModule

object LoggedOutPushTargetUserBuilderModule extends TwitterModule {

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
