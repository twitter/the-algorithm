package com.ExTwitter.follow_recommendations.modules

import com.google.inject.Provides
import com.ExTwitter.decider.Decider
import com.ExTwitter.follow_recommendations.configapi.ConfigBuilder
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.servo.decider.DeciderGateBuilder
import com.ExTwitter.timelines.configapi.Config
import javax.inject.Singleton

object ConfigApiModule extends ExTwitterModule {
  @Provides
  @Singleton
  def providesDeciderGateBuilder(decider: Decider): DeciderGateBuilder =
    new DeciderGateBuilder(decider)

  @Provides
  @Singleton
  def providesConfig(configBuilder: ConfigBuilder): Config = configBuilder.build()
}
