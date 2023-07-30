package com.X.timelineranker.parameters.entity_tweets

import com.X.servo.decider.DeciderGateBuilder
import com.X.servo.decider.DeciderKeyName
import com.X.timelineranker.decider.DeciderKey
import com.X.timelineranker.parameters.entity_tweets.EntityTweetsParams._
import com.X.timelines.configapi.decider.DeciderUtils
import com.X.timelines.configapi.BaseConfig
import com.X.timelines.configapi.BaseConfigBuilder
import com.X.timelines.configapi.FeatureSwitchOverrideUtil
import com.X.timelines.configapi.Param

object EntityTweetsProduction {
  val deciderByParam: Map[Param[_], DeciderKeyName] = Map[Param[_], DeciderKeyName](
    EnableContentFeaturesHydrationParam -> DeciderKey.EntityTweetsEnableContentFeaturesHydration
  )
}

case class EntityTweetsProduction(deciderGateBuilder: DeciderGateBuilder) {

  val booleanDeciderOverrides = DeciderUtils.getBooleanDeciderOverrides(
    deciderGateBuilder,
    EnableContentFeaturesHydrationParam
  )

  val booleanFeatureSwitchOverrides = FeatureSwitchOverrideUtil.getBooleanFSOverrides(
    EnableTokensInContentFeaturesHydrationParam,
    EnableTweetTextInContentFeaturesHydrationParam,
    EnableConversationControlInContentFeaturesHydrationParam,
    EnableTweetMediaHydrationParam
  )

  val intFeatureSwitchOverrides = FeatureSwitchOverrideUtil.getBoundedIntFSOverrides(
    MaxFollowedUsersParam
  )

  val config: BaseConfig = new BaseConfigBuilder()
    .set(booleanDeciderOverrides: _*)
    .set(booleanFeatureSwitchOverrides: _*)
    .set(intFeatureSwitchOverrides: _*)
    .build()
}
