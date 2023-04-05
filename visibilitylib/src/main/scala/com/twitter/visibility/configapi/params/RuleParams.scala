package com.twitter.visibility.configapi.params

import com.twitter.timelines.configapi.EnumParam
import com.twitter.timelines.configapi.Param

abstract class RuleParam[T](override val default: T) extends Param(default) {
  override val statName: String = s"RuleParam/${this.getClass.getSimpleName}"
}

abstract class EnumRuleParam[T <: Enumeration](override val default: T#Value, override val enum: T)
    extends EnumParam(default, enum) {
  override val statName: String = s"RuleParam/${this.getClass.getSimpleName}"
}

private[visibility] object RuleParams {
  object True extends RuleParam(true)
  object False extends RuleParam(false)

  object TestHoldbackParam extends RuleParam(true)

  object TweetConversationControlEnabledParam extends RuleParam(default = false)

  object EnableLimitRepliesFollowersConversationRule extends RuleParam(default = false)

  object CommunityTweetsEnabledParam extends RuleParam(default = false)

  object DropCommunityTweetWithUndefinedCommunityRuleEnabledParam extends RuleParam(default = false)

  object EnableHighPSpammyTweetScoreSearchTweetLabelDropRuleParam extends RuleParam(false)

  object EnableSmyteSpamTweetRuleParam extends RuleParam(false)

  object EnableHighSpammyTweetContentScoreSearchLatestTweetLabelDropRuleParam
      extends RuleParam(false)

  object EnableHighSpammyTweetContentScoreSearchTopTweetLabelDropRuleParam extends RuleParam(false)

  object NotGraduatedUserLabelRuleHoldbackExperimentParam extends RuleParam(default = false)

  object EnableGoreAndViolenceTopicHighRecallTweetLabelRule extends RuleParam(default = false)

  object EnableBlinkBadDownrankingRuleParam extends RuleParam(false)
  object EnableBlinkWorstDownrankingRuleParam extends RuleParam(false)

  object EnableHighSpammyTweetContentScoreTrendsTopTweetLabelDropRuleParam
      extends RuleParam(default = false)

  object EnableHighSpammyTweetContentScoreTrendsLatestTweetLabelDropRuleParam
      extends RuleParam(default = false)

  object EnableCopypastaSpamDownrankConvosAbusiveQualityRule extends RuleParam(default = false)
  object EnableCopypastaSpamSearchDropRule extends RuleParam(default = false)

  object EnableSpammyUserModelTweetDropRuleParam extends RuleParam(default = false)

  object EnableAvoidNsfwRulesParam extends RuleParam(false)

  object EnableReportedTweetInterstitialRule extends RuleParam(default = false)

  object EnableReportedTweetInterstitialSearchRule extends RuleParam(default = false)

  object EnableDropExclusiveTweetContentRule extends RuleParam(default = false)

  object EnableDropExclusiveTweetContentRuleFailClosed extends RuleParam(default = false)

  object EnableTombstoneExclusiveQtProfileTimelineParam extends RuleParam(default = false)

  object EnableDropAllExclusiveTweetsRuleParam extends RuleParam(false)
  object EnableDropAllExclusiveTweetsRuleFailClosedParam extends RuleParam(false)

  object EnableDownrankSpamReplySectioningRuleParam extends RuleParam(default = false)
  object EnableNsfwTextSectioningRuleParam extends RuleParam(default = false)

  object EnableSearchIpiSafeSearchWithoutUserInQueryDropRule extends RuleParam(false)

  object PromotedTweetHealthEnforcementHoldback extends RuleParam(default = true)
  object EnableTimelineHomePromotedTweetHealthEnforcementRules extends RuleParam(default = false)

  object EnableMutedKeywordFilteringSpaceTitleNotificationsRuleParam extends RuleParam(false)

  object EnableDropTweetsWithGeoRestrictedMediaRuleParam extends RuleParam(default = false)

  object EnableDropAllTrustedFriendsTweetsRuleParam extends RuleParam(false)
  object EnableDropTrustedFriendsTweetContentRuleParam extends RuleParam(false)

  object EnableDropAllCollabInvitationTweetsRuleParam extends RuleParam(false)

  object EnableNsfwTextHighPrecisionDropRuleParam extends RuleParam(false)

  object EnableLikelyIvsUserLabelDropRule extends RuleParam(false)

  object EnableCardUriRootDomainCardDenylistRule extends RuleParam(false)
  object EnableCommunityNonMemberPollCardRule extends RuleParam(false)
  object EnableCommunityNonMemberPollCardRuleFailClosed extends RuleParam(false)

  object EnableExperimentalNudgeEnabledParam extends RuleParam(false)

  object NsfwHighPrecisionUserLabelAvoidTweetRuleEnabledParam extends RuleParam(default = false)

  object EnableNewAdAvoidanceRulesParam extends RuleParam(false)

  object EnableNsfaHighRecallAdAvoidanceParam extends RuleParam(false)

  object EnableNsfaKeywordsHighPrecisionAdAvoidanceParam extends RuleParam(false)

  object EnableStaleTweetDropRuleParam extends RuleParam(false)
  object EnableStaleTweetDropRuleFailClosedParam extends RuleParam(false)

  object EnableDeleteStateTweetRulesParam extends RuleParam(default = false)

  object EnableSpacesSharingNsfwDropRulesParam extends RuleParam(default = true)

  object EnableViewerIsSoftUserDropRuleParam extends RuleParam(default = false)

  object EnablePdnaQuotedTweetTombstoneRuleParam extends RuleParam(default = true)
  object EnableSpamQuotedTweetTombstoneRuleParam extends RuleParam(default = true)

  object EnableNsfwHpQuotedTweetDropRuleParam extends RuleParam(default = false)
  object EnableNsfwHpQuotedTweetTombstoneRuleParam extends RuleParam(default = false)

  object EnableInnerQuotedTweetViewerBlocksAuthorInterstitialRuleParam
      extends RuleParam(default = false)
  object EnableInnerQuotedTweetViewerMutesAuthorInterstitialRuleParam
      extends RuleParam(default = false)


  object EnableNewSensitiveMediaSettingsInterstitialsHomeTimelineRulesParam extends RuleParam(false)

  object EnableNewSensitiveMediaSettingsInterstitialsConversationRulesParam extends RuleParam(false)

  object EnableNewSensitiveMediaSettingsInterstitialsProfileTimelineRulesParam
      extends RuleParam(false)

  object EnableNewSensitiveMediaSettingsInterstitialsTweetDetailRulesParam extends RuleParam(false)

  object EnableLegacySensitiveMediaHomeTimelineRulesParam extends RuleParam(true)

  object EnableLegacySensitiveMediaConversationRulesParam extends RuleParam(true)

  object EnableLegacySensitiveMediaProfileTimelineRulesParam extends RuleParam(true)

  object EnableLegacySensitiveMediaTweetDetailRulesParam extends RuleParam(true)

  object EnableLegacySensitiveMediaDirectMessagesRulesParam extends RuleParam(true)

  object EnableToxicReplyFilteringConversationRulesParam extends RuleParam(false)
  object EnableToxicReplyFilteringNotificationsRulesParam extends RuleParam(false)

  object EnableSearchQueryMatchesTweetAuthorConditionParam extends RuleParam(default = false)

  object EnableSearchBasicBlockMuteRulesParam extends RuleParam(default = false)

  object EnableAbusiveBehaviorDropRuleParam extends RuleParam(default = false)
  object EnableAbusiveBehaviorInterstitialRuleParam extends RuleParam(default = false)
  object EnableAbusiveBehaviorLimitedEngagementsRuleParam extends RuleParam(default = false)

  object EnableNotGraduatedDownrankConvosAbusiveQualityRuleParam extends RuleParam(default = false)
  object EnableNotGraduatedSearchDropRuleParam extends RuleParam(default = false)
  object EnableNotGraduatedDropRuleParam extends RuleParam(default = false)

  object EnableFosnrRuleParam extends RuleParam(default = false)

  object EnableAuthorBlocksViewerDropRuleParam extends RuleParam(default = false)
}
