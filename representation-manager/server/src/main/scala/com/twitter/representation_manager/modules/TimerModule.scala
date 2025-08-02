package com.twitter.representation_manager.modules

import com.google.inject.Provides
import com.twitter.finagle.util.DefaultTimer
import com.twitter.inject.TwitterModule
import com.twitter.util.Timer
import javatwitter.inject.Singleton

object TimerModule etwittertends TwitterModule {
  @Singleton
  @Provides
  def providesTimer: Timer = DefaultTimer
}
