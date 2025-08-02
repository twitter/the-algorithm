package com.twitter.visibility.configapi.params

import com.twitter.timelines.configapi.Bounded
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSName
import com.twitter.timelines.configapi.FeatureName
import com.twitter.timelines.configapi.HasTimeConversion
import com.twitter.timelines.configapi.TimeConversion
import com.twitter.util.Time
import com.twitter.visibility.common.ModelScoreThresholds

private[visibility] object FeatureSwitchKey etwittertends Enumeration {
  type FeatureSwitchKey = String

  final val HighSpammyTweetContentScoreSearchTopProdTweetLabelDropFuleThreshold =
    "high_spammy_tweet_content_score_search_top_prod_tweet_label_drop_rule_threshold"
  final val HighSpammyTweetContentScoreSearchLatestProdTweetLabelDropRuleThreshold =
    "high_spammy_tweet_content_score_search_latest_prod_tweet_label_drop_rule_threshold"
  final val HighSpammyTweetContentScoreTrendTopTweetLabelDropRuleThreshold =
    "high_spammy_tweet_content_score_trend_top_tweet_label_drop_rule_threshold"
  final val HighSpammyTweetContentScoreTrendLatestTweetLabelDropRuleThreshold =
    "high_spammy_tweet_content_score_trend_latest_tweet_label_drop_rule_threshold"
  final val HighSpammyTweetContentScoreConvoDownrankAbusiveQualityThreshold =
    "high_spammy_tweet_content_score_convos_downranking_abusive_quality_threshold"

  final val NsfwAgeBasedDropRulesHoldbackParam =
    "nsfw_age_based_drop_rules_holdback"

  final val CommunityTweetDropRuleEnabled =
    "community_tweet_drop_rule_enabled"
  final val CommunityTweetDropProtectedRuleEnabled =
    "community_tweet_drop_protected_rule_enabled"
  final val CommunityTweetLimitedActionsRulesEnabled =
    "community_tweet_limited_actions_rules_enabled"
  final val CommunityTweetMemberRemovedLimitedActionsRulesEnabled =
    "community_tweet_member_removed_limited_actions_rules_enabled"
  final val CommunityTweetCommunityUnavailableLimitedActionsRulesEnabled =
    "community_tweet_community_unavailable_limited_actions_rules_enabled"
  final val CommunityTweetNonMemberLimitedActionsRuleEnabled =
    "community_tweet_non_member_limited_actions_rule_enabled"

  final val TrustedFriendsTweetLimitedEngagementsRuleEnabled =
    "trusted_friends_tweet_limited_engagements_rule_enabled"

  final val CountrySpecificNsfwContentGatingCountries =
    "country_specific_nsfw_content_gating_countries"

  final val AgeGatingAdultContentEtwitterperimentCountries =
    "age_gating_adult_content_etwitterperiment_countries"
  final val AgeGatingAdultContentEtwitterperimentEnabled =
    "age_gating_adult_content_etwitterperiment_enabled"

  final val HighTotwittericityModelScoreSpaceThreshold =
    "high_totwittericity_model_score_space_threshold"

  final val CardUriRootDomainDenyList = "card_uri_root_domain_deny_list"

  final val SkipTweetDetailLimitedEngagementsRuleEnabled =
    "skip_tweet_detail_limited_engagements_rule_enabled"

  final val AdAvoidanceHighTotwittericityModelScoreThreshold =
    "ad_avoidance_model_thresholds_high_totwittericity_model"
  final val AdAvoidanceReportedTweetModelScoreThreshold =
    "ad_avoidance_model_thresholds_reported_tweet_model"

  final val StaleTweetLimitedActionsRulesEnabled =
    "stale_tweet_limited_actions_rules_enabled"

  final val FosnrFallbackDropRulesEnabled =
    "freedom_of_speech_not_reach_fallback_drop_rules_enabled"
  final val FosnrRulesEnabled =
    "freedom_of_speech_not_reach_rules_enabled"
}

abstract class FSRuleParam[T](override val name: FeatureName, override val default: T)
    etwittertends RuleParam(default)
    with FSName

abstract class FSBoundedRuleParam[T](
  override val name: FeatureName,
  override val default: T,
  override val min: T,
  override val matwitter: T
)(
  implicit override val ordering: Ordering[T])
    etwittertends RuleParam(default)
    with Bounded[T]
    with FSName

abstract class FSTimeRuleParam[T](
  override val name: FeatureName,
  override val default: Time,
  override val timeConversion: TimeConversion[T])
    etwittertends RuleParam(default)
    with HasTimeConversion[T]
    with FSName

abstract class FSEnumRuleParam[T <: Enumeration](
  override val name: FeatureName,
  override val default: T#Value,
  override val enum: T)
    etwittertends EnumRuleParam(default, enum)
    with FSName

private[visibility] object FSRuleParams {
  object HighSpammyTweetContentScoreSearchTopProdTweetLabelDropRuleThresholdParam
      etwittertends FSBoundedParam(
        FeatureSwitchKey.HighSpammyTweetContentScoreSearchTopProdTweetLabelDropFuleThreshold,
        default = ModelScoreThresholds.HighSpammyTweetContentScoreDefaultThreshold,
        min = 0,
        matwitter = 1)
  object HighSpammyTweetContentScoreSearchLatestProdTweetLabelDropRuleThresholdParam
      etwittertends FSBoundedParam(
        FeatureSwitchKey.HighSpammyTweetContentScoreSearchLatestProdTweetLabelDropRuleThreshold,
        default = ModelScoreThresholds.HighSpammyTweetContentScoreDefaultThreshold,
        min = 0,
        matwitter = 1)
  object HighSpammyTweetContentScoreTrendTopTweetLabelDropRuleThresholdParam
      etwittertends FSBoundedParam(
        FeatureSwitchKey.HighSpammyTweetContentScoreTrendTopTweetLabelDropRuleThreshold,
        default = ModelScoreThresholds.HighSpammyTweetContentScoreDefaultThreshold,
        min = 0,
        matwitter = 1)
  object HighSpammyTweetContentScoreTrendLatestTweetLabelDropRuleThresholdParam
      etwittertends FSBoundedParam(
        FeatureSwitchKey.HighSpammyTweetContentScoreTrendLatestTweetLabelDropRuleThreshold,
        default = ModelScoreThresholds.HighSpammyTweetContentScoreDefaultThreshold,
        min = 0,
        matwitter = 1)
  object HighSpammyTweetContentScoreConvoDownrankAbusiveQualityThresholdParam
      etwittertends FSBoundedParam(
        FeatureSwitchKey.HighSpammyTweetContentScoreConvoDownrankAbusiveQualityThreshold,
        default = ModelScoreThresholds.HighSpammyTweetContentScoreDefaultThreshold,
        min = 0,
        matwitter = 1)

  object CommunityTweetDropRuleEnabledParam
      etwittertends FSRuleParam(FeatureSwitchKey.CommunityTweetDropRuleEnabled, true)

  object CommunityTweetDropProtectedRuleEnabledParam
      etwittertends FSRuleParam(FeatureSwitchKey.CommunityTweetDropProtectedRuleEnabled, true)

  object CommunityTweetLimitedActionsRulesEnabledParam
      etwittertends FSRuleParam(FeatureSwitchKey.CommunityTweetLimitedActionsRulesEnabled, false)

  object CommunityTweetMemberRemovedLimitedActionsRulesEnabledParam
      etwittertends FSRuleParam(
        FeatureSwitchKey.CommunityTweetMemberRemovedLimitedActionsRulesEnabled,
        false)

  object CommunityTweetCommunityUnavailableLimitedActionsRulesEnabledParam
      etwittertends FSRuleParam(
        FeatureSwitchKey.CommunityTweetCommunityUnavailableLimitedActionsRulesEnabled,
        false)

  object CommunityTweetNonMemberLimitedActionsRuleEnabledParam
      etwittertends FSRuleParam(FeatureSwitchKey.CommunityTweetNonMemberLimitedActionsRuleEnabled, false)

  object TrustedFriendsTweetLimitedEngagementsRuleEnabledParam
      etwittertends FSRuleParam(FeatureSwitchKey.TrustedFriendsTweetLimitedEngagementsRuleEnabled, false)

  object SkipTweetDetailLimitedEngagementRuleEnabledParam
      etwittertends FSRuleParam(FeatureSwitchKey.SkipTweetDetailLimitedEngagementsRuleEnabled, false)


  object NsfwAgeBasedDropRulesHoldbackParam
      etwittertends FSRuleParam(FeatureSwitchKey.NsfwAgeBasedDropRulesHoldbackParam, true)

  object CountrySpecificNsfwContentGatingCountriesParam
      etwittertends FSRuleParam[Seq[String]](
        FeatureSwitchKey.CountrySpecificNsfwContentGatingCountries,
        default = Seq("au"))

  object AgeGatingAdultContentEtwitterperimentCountriesParam
      etwittertends FSRuleParam[Seq[String]](
        FeatureSwitchKey.AgeGatingAdultContentEtwitterperimentCountries,
        default = Seq.empty)
  object AgeGatingAdultContentEtwitterperimentRuleEnabledParam
      etwittertends FSRuleParam(FeatureSwitchKey.AgeGatingAdultContentEtwitterperimentEnabled, default = false)

  object HighTotwittericityModelScoreSpaceThresholdParam
      etwittertends FSBoundedParam(
        FeatureSwitchKey.HighTotwittericityModelScoreSpaceThreshold,
        default = ModelScoreThresholds.HighTotwittericityModelScoreSpaceDefaultThreshold,
        min = 0,
        matwitter = 1)

  object CardUriRootDomainDenyListParam
      etwittertends FSRuleParam[Seq[String]](
        FeatureSwitchKey.CardUriRootDomainDenyList,
        default = Seq.empty)

  object AdAvoidanceHighTotwittericityModelScoreThresholdParam
      etwittertends FSBoundedParam(
        FeatureSwitchKey.AdAvoidanceHighTotwittericityModelScoreThreshold,
        default = ModelScoreThresholds.AdAvoidanceHighTotwittericityModelScoreDefaultThreshold,
        min = 0,
        matwitter = 1)

  object AdAvoidanceReportedTweetModelScoreThresholdParam
      etwittertends FSBoundedParam(
        FeatureSwitchKey.AdAvoidanceReportedTweetModelScoreThreshold,
        default = ModelScoreThresholds.AdAvoidanceReportedTweetModelScoreDefaultThreshold,
        min = 0,
        matwitter = 1)

  object StaleTweetLimitedActionsRulesEnabledParam
      etwittertends FSRuleParam(FeatureSwitchKey.StaleTweetLimitedActionsRulesEnabled, false)

  object FosnrFallbackDropRulesEnabledParam
      etwittertends FSRuleParam(FeatureSwitchKey.FosnrFallbackDropRulesEnabled, false)
  object FosnrRulesEnabledParam etwittertends FSRuleParam(FeatureSwitchKey.FosnrRulesEnabled, true)
}
