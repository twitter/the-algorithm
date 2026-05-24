package com.twitter.timelineranker.parameters.revchron

import com.twitter.servo.decider.DeciderGateBuilder
import com.twitter.timelines.configapi._

object ReverseChronProduction {
  val intFeatureSwitchParams = Seq(ReverseChronParams.MaxFollowedUsersParam)
  val booleanFeatureSwitchParams = Seq(
    ReverseChronParams.ReturnEmptyWhenOverMaxFollowsParam,
    ReverseChronParams.DirectedAtNarrowcastingViaSearchParam,
    ReverseChronParams.PostFilteringBasedOnSearchMetadataEnabledParam
  )
}

class ReverseChronProduction(deciderGateBuilder: DeciderGateBuilder) {
  val intOverrides = FeatureSwitchOverrideUtil.getBoundedIntFSOverrides(
    ReverseChronProduction.intFeatureSwitchParams: _*
  )

  val booleanOverrides = FeatureSwitchOverrideUtil.getBooleanFSOverrides(
    ReverseChronProduction.booleanFeatureSwitchParams: _*
  )

  val config: BaseConfig = new BaseConfigBuilder()
    .set(intOverrides: _*)
    .set(booleanOverrides: _*)
    .build(ReverseChronProduction.getClass.getSimpleName)
}
