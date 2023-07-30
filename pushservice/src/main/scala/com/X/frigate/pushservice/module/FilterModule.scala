package com.X.frigate.pushservice.module

import com.google.inject.Provides
import javax.inject.Singleton
import com.X.discovery.common.nackwarmupfilter.NackWarmupFilter
import com.X.inject.XModule
import com.X.inject.annotations.Flag
import com.X.util.Duration

object FilterModule extends XModule {
  @Singleton
  @Provides
  def providesNackWarmupFilter(
    @Flag(FlagName.nackWarmupDuration) warmupDuration: Duration
  ): NackWarmupFilter = new NackWarmupFilter(warmupDuration)
}
