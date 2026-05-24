package com.twitter.tweet_mixer.module

import com.twitter.conversions.DurationOps._
import com.google.inject.Provides
import com.twitter.inject.TwitterModule
import com.twitter.tweet_mixer.config.TimeoutConfig
import javax.inject.Singleton

object TimeoutConfigModule extends TwitterModule {
  @Provides
  @Singleton
  def provideTimeoutBudget(): TimeoutConfig = {
    TimeoutConfig(
      thriftAnnServiceClientTimeout = 50.milliseconds,
      thriftSANNServiceClientTimeout = 50.milliseconds,
      thriftTweetypieClientTimeout = 50.milliseconds,
      thriftUserTweetGraphClientTimeout = 50.milliseconds,
      thriftUserVideoGraphClientTimeout = 50.milliseconds,
      candidateSourceTimeout = 100.milliseconds,
      userStateStoreTimeout = 12.milliseconds
    )
  }
}
