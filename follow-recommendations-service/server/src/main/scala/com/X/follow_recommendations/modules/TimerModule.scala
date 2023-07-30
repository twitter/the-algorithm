package com.X.follow_recommendations.modules

import com.google.inject.Provides
import com.google.inject.Singleton
import com.X.finagle.memcached.ZookeeperStateMonitor.DefaultTimer
import com.X.inject.XModule
import com.X.util.Timer

object TimerModule extends XModule {
  @Provides
  @Singleton
  def providesTimer: Timer = DefaultTimer
}
