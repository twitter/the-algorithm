package com.X.timelineranker.parameters.in_network_tweets

import com.X.servo.decider.DeciderGateBuilder
import com.X.servo.decider.DeciderKeyName
import com.X.timelineranker.decider.DeciderKey
import com.X.timelineranker.parameters.in_network_tweets.InNetworkTweetParams._
import com.X.timelineranker.parameters.util.ConfigHelper
import com.X.timelines.configapi._
import com.X.servo.decider.DeciderKeyEnum

object InNetworkTweetProduction {
  val deciderByParam: Map[Param[_], DeciderKeyEnum#Value] = Map[Param[_], DeciderKeyName](
    EnableContentFeaturesHydrationParam -> DeciderKey.RecycledEnableContentFeaturesHydration,
    MaxCountMultiplierParam -> DeciderKey.RecycledMaxCountMultiplier
  )

  val doubleParams: Seq[MaxCountMultiplierParam.type] = Seq(
    MaxCountMultiplierParam
  )

  val booleanDeciderParams: Seq[EnableContentFeaturesHydrationParam.type] = Seq(
    EnableContentFeaturesHydrationParam
  )

  val booleanFeatureSwitchParams: Seq[FSParam[Boolean]] = Seq(
    EnableExcludeSourceTweetIdsQueryParam,
    EnableTokensInContentFeaturesHydrationParam,
    EnableReplyRootTweetHydrationParam,
    EnableTweetTextInContentFeaturesHydrationParam,
    EnableConversationControlInContentFeaturesHydrationParam,
    EnableTweetMediaHydrationParam,
    EnableEarlybirdReturnAllResultsParam,
    EnableEarlybirdRealtimeCgMigrationParam,
    RecycledMaxFollowedUsersEnableAntiDilutionParam
  )

  val boundedIntFeatureSwitchParams: Seq[FSBoundedParam[Int]] = Seq(
    MaxFollowedUsersParam,
    RelevanceOptionsMaxHitsToProcessParam
  )
}

class InNetworkTweetProduction(deciderGateBuilder: DeciderGateBuilder) {
  val configHelper: ConfigHelper =
    new ConfigHelper(InNetworkTweetProduction.deciderByParam, deciderGateBuilder)
  val doubleDeciderOverrides: Seq[OptionalOverride[Double]] =
    configHelper.createDeciderBasedOverrides(InNetworkTweetProduction.doubleParams)
  val booleanDeciderOverrides: Seq[OptionalOverride[Boolean]] =
    configHelper.createDeciderBasedBooleanOverrides(InNetworkTweetProduction.booleanDeciderParams)
  val boundedIntFeatureSwitchOverrides: Seq[OptionalOverride[Int]] =
    FeatureSwitchOverrideUtil.getBoundedIntFSOverrides(
      InNetworkTweetProduction.boundedIntFeatureSwitchParams: _*)
  val booleanFeatureSwitchOverrides: Seq[OptionalOverride[Boolean]] =
    FeatureSwitchOverrideUtil.getBooleanFSOverrides(
      InNetworkTweetProduction.booleanFeatureSwitchParams: _*)

  val config: BaseConfig = new BaseConfigBuilder()
    .set(
      booleanDeciderOverrides: _*
    )
    .set(
      doubleDeciderOverrides: _*
    )
    .set(
      boundedIntFeatureSwitchOverrides: _*
    )
    .set(
      booleanFeatureSwitchOverrides: _*
    )
    .build(InNetworkTweetProduction.getClass.getSimpleName)
}
