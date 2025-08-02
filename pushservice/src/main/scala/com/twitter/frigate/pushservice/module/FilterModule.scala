package com.twitter.frigate.pushservice.module

import com.google.inject.Provides
import javatwitter.inject.Singleton
import com.twitter.discovery.common.nackwarmupfilter.NackWarmupFilter
import com.twitter.inject.TwitterModule
import com.twitter.inject.annotations.Flag
import com.twitter.util.Duration

object FilterModule etwittertends TwitterModule {
  @Singleton
  @Provides
  def providesNackWarmupFilter(
    @Flag(FlagName.nackWarmupDuration) warmupDuration: Duration
  ): NackWarmupFilter = new NackWarmupFilter(warmupDuration)
}
