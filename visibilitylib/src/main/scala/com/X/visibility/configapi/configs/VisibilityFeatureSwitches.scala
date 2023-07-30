package com.X.visibility.configapi.configs

import com.X.finagle.stats.StatsReceiver
import com.X.logging.Logger
import com.X.timelines.configapi._
import com.X.util.Time
import com.X.visibility.configapi.params.FSEnumRuleParam
import com.X.visibility.configapi.params.FSRuleParams._

private[visibility] object VisibilityFeatureSwitches {

  val booleanFsOverrides: Seq[OptionalOverride[Boolean]] =
    FeatureSwitchOverrideUtil.getBooleanFSOverrides(
      AgeGatingAdultContentExperimentRuleEnabledParam,
      CommunityTweetCommunityUnavailableLimitedActionsRulesEnabledParam,
      CommunityTweetDropProtectedRuleEnabledParam,
      CommunityTweetDropRuleEnabledParam,
      CommunityTweetLimitedActionsRulesEnabledParam,
      CommunityTweetMemberRemovedLimitedActionsRulesEnabledParam,
      CommunityTweetNonMemberLimitedActionsRuleEnabledParam,
      NsfwAgeBasedDropRulesHoldbackParam,
      SkipTweetDetailLimitedEngagementRuleEnabledParam,
      StaleTweetLimitedActionsRulesEnabledParam,
      TrustedFriendsTweetLimitedEngagementsRuleEnabledParam,
      FosnrFallbackDropRulesEnabledParam,
      FosnrRulesEnabledParam
    )

  val doubleFsOverrides: Seq[OptionalOverride[Double]] =
    FeatureSwitchOverrideUtil.getBoundedDoubleFSOverrides(
      HighSpammyTweetContentScoreSearchTopProdTweetLabelDropRuleThresholdParam,
      HighSpammyTweetContentScoreSearchLatestProdTweetLabelDropRuleThresholdParam,
      HighSpammyTweetContentScoreTrendTopTweetLabelDropRuleThresholdParam,
      HighSpammyTweetContentScoreTrendLatestTweetLabelDropRuleThresholdParam,
      HighSpammyTweetContentScoreConvoDownrankAbusiveQualityThresholdParam,
      HighToxicityModelScoreSpaceThresholdParam,
      AdAvoidanceHighToxicityModelScoreThresholdParam,
      AdAvoidanceReportedTweetModelScoreThresholdParam,
    )

  val timeFsOverrides: Seq[OptionalOverride[Time]] =
    FeatureSwitchOverrideUtil.getTimeFromStringFSOverrides()

  val stringSeqFeatureSwitchOverrides: Seq[OptionalOverride[Seq[String]]] =
    FeatureSwitchOverrideUtil.getStringSeqFSOverrides(
      CountrySpecificNsfwContentGatingCountriesParam,
      AgeGatingAdultContentExperimentCountriesParam,
      CardUriRootDomainDenyListParam
    )

  val enumFsParams: Seq[FSEnumRuleParam[_ <: Enumeration]] = Seq()

  val mkOptionalEnumFsOverrides: (StatsReceiver, Logger) => Seq[OptionalOverride[_]] = {
    (statsReceiver: StatsReceiver, logger: Logger) =>
      FeatureSwitchOverrideUtil.getEnumFSOverrides(
        statsReceiver,
        logger,
        enumFsParams: _*
      )
  }

  def overrides(statsReceiver: StatsReceiver, logger: Logger): Seq[OptionalOverride[_]] = {
    val enumOverrides = mkOptionalEnumFsOverrides(statsReceiver, logger)
    booleanFsOverrides ++
      doubleFsOverrides ++
      timeFsOverrides ++
      stringSeqFeatureSwitchOverrides ++
      enumOverrides
  }

  def config(statsReceiver: StatsReceiver, logger: Logger): BaseConfig =
    BaseConfigBuilder(overrides(statsReceiver.scope("features_switches"), logger))
      .build("VisibilityFeatureSwitches")
}
