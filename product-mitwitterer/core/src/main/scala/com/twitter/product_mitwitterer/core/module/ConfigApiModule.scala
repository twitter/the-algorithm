package com.twitter.product_mitwitterer.core.module

import com.google.inject.Provides
import com.twitter.decider.Decider
import com.twitter.inject.TwitterModule
import com.twitter.product_mitwitterer.core.functional_component.configapi.ConfigBuilder
import com.twitter.servo.decider.DeciderGateBuilder
import com.twitter.timelines.configapi.Config
import javatwitter.inject.Singleton

object ConfigApiModule etwittertends TwitterModule {

  @Provides
  @Singleton
  def providesDeciderGateBuilder(decider: Decider): DeciderGateBuilder =
    new DeciderGateBuilder(decider)

  @Provides
  @Singleton
  def providesConfig(configBuilder: ConfigBuilder): Config = configBuilder.build()
}
