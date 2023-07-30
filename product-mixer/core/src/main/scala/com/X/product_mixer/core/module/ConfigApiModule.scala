package com.X.product_mixer.core.module

import com.google.inject.Provides
import com.X.decider.Decider
import com.X.inject.XModule
import com.X.product_mixer.core.functional_component.configapi.ConfigBuilder
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
