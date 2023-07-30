package com.X.product_mixer.core.product

import com.X.finagle.stats.StatsReceiver
import com.X.product_mixer.core.functional_component.configapi.registry.ParamConfigBuilder
import com.X.servo.decider.DeciderGateBuilder
import com.X.timelines.configapi.FeatureSwitchOverrideUtil
import com.X.timelines.configapi.OptionalOverride
import com.X.timelines.configapi.decider.DeciderUtils

trait ProductParamConfigBuilder extends ParamConfigBuilder {
  productParamConfig: ProductParamConfig =>

  override def build(
    deciderGateBuilder: DeciderGateBuilder,
    statsReceiver: StatsReceiver
  ): Seq[OptionalOverride[_]] = {
    DeciderUtils.getBooleanDeciderOverrides(deciderGateBuilder, EnabledDeciderParam) ++
      FeatureSwitchOverrideUtil.getBooleanFSOverrides(SupportedClientParam) ++
      super.build(deciderGateBuilder, statsReceiver)
  }
}
