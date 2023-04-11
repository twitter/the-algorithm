package com.twitter.visibility.configapi.params

import com.twitter.timelines.configapi.Bounded
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSName
import com.twitter.timelines.configapi.FeatureName
import com.twitter.timelines.configapi.HasTimeConversion
import com.twitter.timelines.configapi.TimeConversion
import com.twitter.util.Time
import com.twitter.visibility.common.ModelScoreThresholds

private[visibility] object FeatureSwitchKey extends Enumeration {
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

  final val AgeGatingAdultContentExperimentCountries =
    "age_gating_adult_content_experiment_countries"
  final val AgeGatingAdultContentExperimentEnabled =
    "age_gating_adult_content_experiment_enabled"

  final val HighToxicityModelScoreSpaceThreshold =
    "high_toxicity_model_score_space_threshold"

  final val CardUriRootDomainDenyList = "card_uri_root_domain_deny_list"

  final val SkipTweetDetailLimitedEngagementsRuleEnabled =
    "skip_tweet_detail_limited_engagements_rule_enabled"

  final val AdAvoidanceHighToxicityModelScoreThreshold =
    "ad_avoidance_model_thresholds_high_toxicity_model"
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
    extends RuleParam(default)
    with FSName

abstract class FSBoundedRuleParam[T](
  override val name: FeatureName,
  override val default: T,
  override val min: T,
  override val max: T
)(
  implicit override val ordering: Ordering[T])
    extends RuleParam(default)
    with Bounded[T]
    with FSName

abstract class FSTimeRuleParam[T](
  override val name: FeatureName,
  override val default: Time,
  override val timeConversion: TimeConversion[T])
    extends RuleParam(default)
    with HasTimeConversion[T]
    with FSName

abstract class FSEnumRuleParam[T <: Enumeration](
  override val name: FeatureName,
  override val default: T#Value,
  override val enum: T)
    extends EnumRuleParam(default, enum)
    with FSName

private[visibility] object FSRuleParams {
  object HighSpammyTweetContentScoreSearchTopProdTweetLabelDropRuleThresholdParam
      extends FSBoundedParam(
        FeatureSwitchKey.HighSpammyTweetContentScoreSearchTopProdTweetLabelDropFuleThreshold,
        default = ModelScoreThresholds.HighSpammyTweetContentScoreDefaultThreshold,
        min = 0,
        max = 1)
  object HighSpammyTweetContentScoreSearchLatestProdTweetLabelDropRuleThresholdParam
      extends FSBoundedParam(
        FeatureSwitchKey.HighSpammyTweetContentScoreSearchLatestProdTweetLabelDropRuleThreshold,
        default = ModelScoreThresholds.HighSpammyTweetContentScoreDefaultThreshold,
        min = 0,
        max = 1)
  object HighSpammyTweetContentScoreTrendTopTweetLabelDropRuleThresholdParam
      extends FSBoundedParam(
        FeatureSwitchKey.HighSpammyTweetContentScoreTrendTopTweetLabelDropRuleThreshold,
        default = ModelScoreThresholds.HighSpammyTweetContentScoreDefaultThreshold,
        min = 0,
        max = 1)
  object HighSpammyTweetContentScoreTrendLatestTweetLabelDropRuleThresholdParam
      extends FSBoundedParam(
        FeatureSwitchKey.HighSpammyTweetContentScoreTrendLatestTweetLabelDropRuleThreshold,
        default = ModelScoreThresholds.HighSpammyTweetContentScoreDefaultThreshold,
        min = 0,
        max = 1)
  object HighSpammyTweetContentScoreConvoDownrankAbusiveQualityThresholdParam
      extends FSBoundedParam(
        FeatureSwitchKey.HighSpammyTweetContentScoreConvoDownrankAbusiveQualityThreshold,
        default = ModelScoreThresholds.HighSpammyTweetContentScoreDefaultThreshold,
        min = 0,
        max = 1)

  object CommunityTweetDropRuleEnabledParam
      extends FSRuleParam(FeatureSwitchKey.CommunityTweetDropRuleEnabled, true)

  object CommunityTweetDropProtectedRuleEnabledParam
      extends FSRuleParam(FeatureSwitchKey.CommunityTweetDropProtectedRuleEnabled, true)

  object CommunityTweetLimitedActionsRulesEnabledParam
      extends FSRuleParam(FeatureSwitchKey.CommunityTweetLimitedActionsRulesEnabled, false)

  object CommunityTweetMemberRemovedLimitedActionsRulesEnabledParam
      extends FSRuleParam(
        FeatureSwitchKey.CommunityTweetMemberRemovedLimitedActionsRulesEnabled,
        false)

  object CommunityTweetCommunityUnavailableLimitedActionsRulesEnabledParam
      extends FSRuleParam(
        FeatureSwitchKey.CommunityTweetCommunityUnavailableLimitedActionsRulesEnabled,
        false)

  object CommunityTweetNonMemberLimitedActionsRuleEnabledParam
      extends FSRuleParam(FeatureSwitchKey.CommunityTweetNonMemberLimitedActionsRuleEnabled, false)

  object TrustedFriendsTweetLimitedEngagementsRuleEnabledParam
      extends FSRuleParam(FeatureSwitchKey.TrustedFriendsTweetLimitedEngagementsRuleEnabled, false)

  object SkipTweetDetailLimitedEngagementRuleEnabledParam
      extends FSRuleParam(FeatureSwitchKey.SkipTweetDetailLimitedEngagementsRuleEnabled, false)


  object NsfwAgeBasedDropRulesHoldbackParam
      extends FSRuleParam(FeatureSwitchKey.NsfwAgeBasedDropRulesHoldbackParam, true)

  object CountrySpecificNsfwContentGatingCountriesParam
      extends FSRuleParam[Seq[String]](
        FeatureSwitchKey.CountrySpecificNsfwContentGatingCountries,
        default = Seq("au"))

  object AgeGatingAdultContentExperimentCountriesParam
      extends FSRuleParam[Seq[String]](
        FeatureSwitchKey.AgeGatingAdultContentExperimentCountries,
        default = Seq.empty)
  object AgeGatingAdultContentExperimentRuleEnabledParam
      extends FSRuleParam(FeatureSwitchKey.AgeGatingAdultContentExperimentEnabled, default = false)

  object HighToxicityModelScoreSpaceThresholdParam
      extends FSBoundedParam(
        FeatureSwitchKey.HighToxicityModelScoreSpaceThreshold,
        default = ModelScoreThresholds.HighToxicityModelScoreSpaceDefaultThreshold,
        min = 0,
        max = 1)

  object CardUriRootDomainDenyListParam
      extends FSRuleParam[Seq[String]](
        FeatureSwitchKey.CardUriRootDomainDenyList,
        default = Seq.empty)

  object AdAvoidanceHighToxicityModelScoreThresholdParam
      extends FSBoundedParam(
        FeatureSwitchKey.AdAvoidanceHighToxicityModelScoreThreshold,
        default = ModelScoreThresholds.AdAvoidanceHighToxicityModelScoreDefaultThreshold,
        min = 0,
        max = 1)

  object AdAvoidanceReportedTweetModelScoreThresholdParam
      extends FSBoundedParam(
        FeatureSwitchKey.AdAvoidanceReportedTweetModelScoreThreshold,
        default = ModelScoreThresholds.AdAvoidanceReportedTweetModelScoreDefaultThreshold,
        min = 0,
        max = 1)

  object StaleTweetLimitedActionsRulesEnabledParam
      extends FSRuleParam(FeatureSwitchKey.StaleTweetLimitedActionsRulesEnabled, false)

  object FosnrFallbackDropRulesEnabledParam
      extends FSRuleParam(FeatureSwitchKey.FosnrFallbackDropRulesEnabled, false)
  object FosnrRulesEnabledParam extends FSRuleParam(FeatureSwitchKey.FosnrRulesEnabled, true)
}
