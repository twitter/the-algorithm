package com.X.product_mixer.core.functional_component.configapi.registry

import com.X.finagle.stats.StatsReceiver
import com.X.servo.decider.DeciderGateBuilder
import com.X.timelines.configapi.BaseConfigBuilder
import com.X.timelines.configapi.Config
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
