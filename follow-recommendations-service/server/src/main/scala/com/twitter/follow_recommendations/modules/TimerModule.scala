package com.twitter.follow_recommendations.modules

import com.google.inject.Provides
import com.google.inject.Singleton
import com.twitter.finagle.memcached.ZookeeperStateMonitor.DefaultTimer
import com.twitter.inject.TwitterModule
import com.twitter.util.Timer

object TimerModule extends TwitterModule {
  @Provides
  @Singleton
  def providesTimer: Timer = DefaultTimer
}
