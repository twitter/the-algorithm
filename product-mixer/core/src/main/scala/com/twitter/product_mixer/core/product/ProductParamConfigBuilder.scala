package com.twitter.product_mixer.core.product

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.product_mixer.core.functional_component.configapi.registry.ParamConfigBuilder
import com.twitter.servo.decider.DeciderGateBuilder
import com.twitter.timelines.configapi.FeatureSwitchOverrideUtil
import com.twitter.timelines.configapi.OptionalOverride
import com.twitter.timelines.configapi.decider.DeciderUtils

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
