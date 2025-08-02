package com.twitter.visibility.configapi.params

import com.twitter.timelines.configapi.EnumParam
import com.twitter.timelines.configapi.Param

abstract class RuleParam[T](override val default: T) etwittertends Param(default) {
  override val statName: String = s"RuleParam/${this.getClass.getSimpleName}"
}

abstract class EnumRuleParam[T <: Enumeration](override val default: T#Value, override val enum: T)
    etwittertends EnumParam(default, enum) {
  override val statName: String = s"RuleParam/${this.getClass.getSimpleName}"
}

private[visibility] object RuleParams {
  object True etwittertends RuleParam(true)
  object False etwittertends RuleParam(false)

  object TestHoldbackParam etwittertends RuleParam(true)

  object TweetConversationControlEnabledParam etwittertends RuleParam(default = false)

  object EnableLimitRepliesFollowersConversationRule etwittertends RuleParam(default = false)

  object CommunityTweetsEnabledParam etwittertends RuleParam(default = false)

  object DropCommunityTweetWithUndefinedCommunityRuleEnabledParam etwittertends RuleParam(default = false)

  object EnableHighPSpammyTweetScoreSearchTweetLabelDropRuleParam etwittertends RuleParam(false)

  object EnableSmyteSpamTweetRuleParam etwittertends RuleParam(false)

  object EnableHighSpammyTweetContentScoreSearchLatestTweetLabelDropRuleParam
      etwittertends RuleParam(false)

  object EnableHighSpammyTweetContentScoreSearchTopTweetLabelDropRuleParam etwittertends RuleParam(false)

  object NotGraduatedUserLabelRuleHoldbackEtwitterperimentParam etwittertends RuleParam(default = false)

  object EnableGoreAndViolenceTopicHighRecallTweetLabelRule etwittertends RuleParam(default = false)

  object EnableBlinkBadDownrankingRuleParam etwittertends RuleParam(false)
  object EnableBlinkWorstDownrankingRuleParam etwittertends RuleParam(false)

  object EnableHighSpammyTweetContentScoreTrendsTopTweetLabelDropRuleParam
      etwittertends RuleParam(default = false)

  object EnableHighSpammyTweetContentScoreTrendsLatestTweetLabelDropRuleParam
      etwittertends RuleParam(default = false)

  object EnableCopypastaSpamDownrankConvosAbusiveQualityRule etwittertends RuleParam(default = false)
  object EnableCopypastaSpamSearchDropRule etwittertends RuleParam(default = false)

  object EnableSpammyUserModelTweetDropRuleParam etwittertends RuleParam(default = false)

  object EnableAvoidNsfwRulesParam etwittertends RuleParam(false)

  object EnableReportedTweetInterstitialRule etwittertends RuleParam(default = false)

  object EnableReportedTweetInterstitialSearchRule etwittertends RuleParam(default = false)

  object EnableDropEtwitterclusiveTweetContentRule etwittertends RuleParam(default = false)

  object EnableDropEtwitterclusiveTweetContentRuleFailClosed etwittertends RuleParam(default = false)

  object EnableTombstoneEtwitterclusiveQtProfileTimelineParam etwittertends RuleParam(default = false)

  object EnableDropAllEtwitterclusiveTweetsRuleParam etwittertends RuleParam(false)
  object EnableDropAllEtwitterclusiveTweetsRuleFailClosedParam etwittertends RuleParam(false)

  object EnableDownrankSpamReplySectioningRuleParam etwittertends RuleParam(default = false)
  object EnableNsfwTetwittertSectioningRuleParam etwittertends RuleParam(default = false)

  object EnableSearchIpiSafeSearchWithoutUserInQueryDropRule etwittertends RuleParam(false)

  object PromotedTweetHealthEnforcementHoldback etwittertends RuleParam(default = true)
  object EnableTimelineHomePromotedTweetHealthEnforcementRules etwittertends RuleParam(default = false)

  object EnableMutedKeywordFilteringSpaceTitleNotificationsRuleParam etwittertends RuleParam(false)

  object EnableDropTweetsWithGeoRestrictedMediaRuleParam etwittertends RuleParam(default = false)

  object EnableDropAllTrustedFriendsTweetsRuleParam etwittertends RuleParam(false)
  object EnableDropTrustedFriendsTweetContentRuleParam etwittertends RuleParam(false)

  object EnableDropAllCollabInvitationTweetsRuleParam etwittertends RuleParam(false)

  object EnableNsfwTetwittertHighPrecisionDropRuleParam etwittertends RuleParam(false)

  object EnableLikelyIvsUserLabelDropRule etwittertends RuleParam(false)

  object EnableCardUriRootDomainCardDenylistRule etwittertends RuleParam(false)
  object EnableCommunityNonMemberPollCardRule etwittertends RuleParam(false)
  object EnableCommunityNonMemberPollCardRuleFailClosed etwittertends RuleParam(false)

  object EnableEtwitterperimentalNudgeEnabledParam etwittertends RuleParam(false)

  object NsfwHighPrecisionUserLabelAvoidTweetRuleEnabledParam etwittertends RuleParam(default = false)

  object EnableNewAdAvoidanceRulesParam etwittertends RuleParam(false)

  object EnableNsfaHighRecallAdAvoidanceParam etwittertends RuleParam(false)

  object EnableNsfaKeywordsHighPrecisionAdAvoidanceParam etwittertends RuleParam(false)

  object EnableStaleTweetDropRuleParam etwittertends RuleParam(false)
  object EnableStaleTweetDropRuleFailClosedParam etwittertends RuleParam(false)

  object EnableDeleteStateTweetRulesParam etwittertends RuleParam(default = false)

  object EnableSpacesSharingNsfwDropRulesParam etwittertends RuleParam(default = true)

  object EnableViewerIsSoftUserDropRuleParam etwittertends RuleParam(default = false)

  object EnablePdnaQuotedTweetTombstoneRuleParam etwittertends RuleParam(default = true)
  object EnableSpamQuotedTweetTombstoneRuleParam etwittertends RuleParam(default = true)

  object EnableNsfwHpQuotedTweetDropRuleParam etwittertends RuleParam(default = false)
  object EnableNsfwHpQuotedTweetTombstoneRuleParam etwittertends RuleParam(default = false)

  object EnableInnerQuotedTweetViewerBlocksAuthorInterstitialRuleParam
      etwittertends RuleParam(default = false)
  object EnableInnerQuotedTweetViewerMutesAuthorInterstitialRuleParam
      etwittertends RuleParam(default = false)


  object EnableNewSensitiveMediaSettingsInterstitialsHomeTimelineRulesParam etwittertends RuleParam(false)

  object EnableNewSensitiveMediaSettingsInterstitialsConversationRulesParam etwittertends RuleParam(false)

  object EnableNewSensitiveMediaSettingsInterstitialsProfileTimelineRulesParam
      etwittertends RuleParam(false)

  object EnableNewSensitiveMediaSettingsInterstitialsTweetDetailRulesParam etwittertends RuleParam(false)

  object EnableLegacySensitiveMediaHomeTimelineRulesParam etwittertends RuleParam(true)

  object EnableLegacySensitiveMediaConversationRulesParam etwittertends RuleParam(true)

  object EnableLegacySensitiveMediaProfileTimelineRulesParam etwittertends RuleParam(true)

  object EnableLegacySensitiveMediaTweetDetailRulesParam etwittertends RuleParam(true)

  object EnableLegacySensitiveMediaDirectMessagesRulesParam etwittertends RuleParam(true)

  object EnableTotwittericReplyFilteringConversationRulesParam etwittertends RuleParam(false)
  object EnableTotwittericReplyFilteringNotificationsRulesParam etwittertends RuleParam(false)

  object EnableSearchQueryMatchesTweetAuthorConditionParam etwittertends RuleParam(default = false)

  object EnableSearchBasicBlockMuteRulesParam etwittertends RuleParam(default = false)

  object EnableAbusiveBehaviorDropRuleParam etwittertends RuleParam(default = false)
  object EnableAbusiveBehaviorInterstitialRuleParam etwittertends RuleParam(default = false)
  object EnableAbusiveBehaviorLimitedEngagementsRuleParam etwittertends RuleParam(default = false)

  object EnableNotGraduatedDownrankConvosAbusiveQualityRuleParam etwittertends RuleParam(default = false)
  object EnableNotGraduatedSearchDropRuleParam etwittertends RuleParam(default = false)
  object EnableNotGraduatedDropRuleParam etwittertends RuleParam(default = false)

  object EnableFosnrRuleParam etwittertends RuleParam(default = false)

  object EnableAuthorBlocksViewerDropRuleParam etwittertends RuleParam(default = false)
}
