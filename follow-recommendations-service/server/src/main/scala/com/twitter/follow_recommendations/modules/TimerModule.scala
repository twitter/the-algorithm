package com.ExTwitter.follow_recommendations.modules

import com.google.inject.Provides
import com.google.inject.Singleton
import com.ExTwitter.finagle.memcached.ZookeeperStateMonitor.DefaultTimer
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.util.Timer

object TimerModule extends ExTwitterModule {
  @Provides
  @Singleton
  def providesTimer: Timer = DefaultTimer
}
