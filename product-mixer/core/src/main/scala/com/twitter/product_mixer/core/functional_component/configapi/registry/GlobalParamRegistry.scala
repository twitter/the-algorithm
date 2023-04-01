package com.twitter.product_mixer.core.functional_component.configapi.registry

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.servo.decider.DeciderGateBuilder
import com.twitter.timelines.configapi.BaseConfigBuilder
import com.twitter.timelines.configapi.Config
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GlobalParamRegistry @Inject() (
  globalParamConfig: GlobalParamConfig,
  deciderGateBuilder: DeciderGateBuilder,
  statsReceiver: StatsReceiver) {

  def build(): Config = {
    val globalConfigs = globalParamConfig.build(deciderGateBuilder, statsReceiver)

    BaseConfigBuilder(globalConfigs).build("GlobalParamRegistry")
  }
}
