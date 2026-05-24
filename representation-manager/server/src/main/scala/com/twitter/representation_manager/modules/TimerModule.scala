package com.twitter.representation_manager.modules

import com.google.inject.Provides
import com.twitter.finagle.util.DefaultTimer
import com.twitter.inject.TwitterModule
import com.twitter.util.Timer
import javax.inject.Singleton

object TimerModule extends TwitterModule {
  @Singleton
  @Provides
  def providesTimer: Timer = DefaultTimer
}
