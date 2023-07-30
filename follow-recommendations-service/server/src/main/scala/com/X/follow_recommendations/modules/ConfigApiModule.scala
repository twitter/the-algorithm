package com.X.follow_recommendations.modules

import com.google.inject.Provides
import com.X.decider.Decider
import com.X.follow_recommendations.configapi.ConfigBuilder
import com.X.inject.XModule
import com.X.servo.decider.DeciderGateBuilder
import com.X.timelines.configapi.Config
import javax.inject.Singleton

object ConfigApiModule extends XModule {
  @Provides
  @Singleton
  def providesDeciderGateBuilder(decider: Decider): DeciderGateBuilder =
    new DeciderGateBuilder(decider)

  @Provides
  @Singleton
  def providesConfig(configBuilder: ConfigBuilder): Config = configBuilder.build()
}
