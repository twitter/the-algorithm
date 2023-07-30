package com.X.timelineranker.parameters.uteg_liked_by_tweets

import com.X.servo.decider.DeciderGateBuilder
import com.X.servo.decider.DeciderKeyName
import com.X.timelineranker.decider.DeciderKey
import com.X.timelineranker.parameters.uteg_liked_by_tweets.UtegLikedByTweetsParams._
import com.X.timelineranker.parameters.util.ConfigHelper
import com.X.timelines.configapi.BaseConfig
import com.X.timelines.configapi.BaseConfigBuilder
import com.X.timelines.configapi.FSBoundedParam
import com.X.timelines.configapi.FSParam
import com.X.timelines.configapi.FeatureSwitchOverrideUtil
import com.X.timelines.configapi.OptionalOverride
import com.X.timelines.configapi.Param

object UtegLikedByTweetsProduction {
  val deciderByParam: Map[Param[_], DeciderKeyName] = Map[Param[_], DeciderKeyName](
    EnableContentFeaturesHydrationParam -> DeciderKey.UtegLikedByTweetsEnableContentFeaturesHydration
  )

  val booleanDeciderParams: Seq[EnableContentFeaturesHydrationParam.type] = Seq(
    EnableContentFeaturesHydrationParam
  )

  val intParams: Seq[Param[Int]] = Seq(
    DefaultUTEGInNetworkCount,
    DefaultMaxTweetCount,
    DefaultUTEGOutOfNetworkCount,
    MinNumFavoritedByUserIdsParam
  )

  val booleanFeatureSwitchParams: Seq[FSParam[Boolean]] = Seq(
    UTEGRecommendationsFilter.EnableParam,
    UTEGRecommendationsFilter.ExcludeQuoteTweetParam,
    UTEGRecommendationsFilter.ExcludeReplyParam,
    UTEGRecommendationsFilter.ExcludeRetweetParam,
    UTEGRecommendationsFilter.ExcludeTweetParam,
    EnableTokensInContentFeaturesHydrationParam,
    EnableConversationControlInContentFeaturesHydrationParam,
    UTEGRecommendationsFilter.ExcludeRecommendedRepliesToNonFollowedUsersParam,
    EnableTweetTextInContentFeaturesHydrationParam,
    EnableTweetMediaHydrationParam,
    UtegLikedByTweetsParams.IncludeRandomTweetParam,
    UtegLikedByTweetsParams.IncludeSingleRandomTweetParam,
    UtegLikedByTweetsParams.EnableRelevanceSearchParam
  )
  val boundedDoubleFeatureSwitchParams: Seq[FSBoundedParam[Double]] = Seq(
    EarlybirdScoreMultiplierParam,
    UtegLikedByTweetsParams.ProbabilityRandomTweetParam
  )
  val boundedIntFeatureSwitchParams: Seq[FSBoundedParam[Int]] = Seq(
    UtegLikedByTweetsParams.NumAdditionalRepliesParam
  )

}

class UtegLikedByTweetsProduction(deciderGateBuilder: DeciderGateBuilder) {
  val configHelper: ConfigHelper =
    new ConfigHelper(UtegLikedByTweetsProduction.deciderByParam, deciderGateBuilder)
  val booleanDeciderOverrides: Seq[OptionalOverride[Boolean]] =
    configHelper.createDeciderBasedBooleanOverrides(
      UtegLikedByTweetsProduction.booleanDeciderParams)
  val boundedDoubleFeatureSwitchOverrides: Seq[OptionalOverride[Double]] =
    FeatureSwitchOverrideUtil.getBoundedDoubleFSOverrides(
      UtegLikedByTweetsProduction.boundedDoubleFeatureSwitchParams: _*)
  val booleanFeatureSwitchOverrides: Seq[OptionalOverride[Boolean]] =
    FeatureSwitchOverrideUtil.getBooleanFSOverrides(
      UtegLikedByTweetsProduction.booleanFeatureSwitchParams: _*)
  val boundedIntFeaturesSwitchOverrides: Seq[OptionalOverride[Int]] =
    FeatureSwitchOverrideUtil.getBoundedIntFSOverrides(
      UtegLikedByTweetsProduction.boundedIntFeatureSwitchParams: _*)

  val config: BaseConfig = new BaseConfigBuilder()
    .set(
      booleanDeciderOverrides: _*
    )
    .set(
      boundedDoubleFeatureSwitchOverrides: _*
    )
    .set(
      booleanFeatureSwitchOverrides: _*
    )
    .set(
      boundedIntFeaturesSwitchOverrides: _*
    )
    .build(UtegLikedByTweetsProduction.getClass.getSimpleName)
}
