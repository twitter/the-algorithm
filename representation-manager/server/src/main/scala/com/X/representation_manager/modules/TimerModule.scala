package com.X.representation_manager.modules

import com.google.inject.Provides
import com.X.finagle.util.DefaultTimer
import com.X.inject.XModule
import com.X.util.Timer
import javax.inject.Singleton

object TimerModule extends XModule {
  @Singleton
  @Provides
  def providesTimer: Timer = DefaultTimer
}
