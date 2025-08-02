package com.twitter.visibility.rules

import com.twitter.visibility.common.ModelScoreThresholds
import com.twitter.visibility.common.actions.AvoidReason
import com.twitter.visibility.common.actions.AvoidReason.MightNotBeSuitableForAds
import com.twitter.visibility.common.actions.LimitedEngagementReason
import com.twitter.visibility.common.actions.TweetVisibilityNudgeReason
import com.twitter.visibility.configapi.configs.DeciderKey
import com.twitter.visibility.configapi.params.FSRuleParams.HighSpammyTweetContentScoreSearchLatestProdTweetLabelDropRuleThresholdParam
import com.twitter.visibility.configapi.params.FSRuleParams.HighSpammyTweetContentScoreSearchTopProdTweetLabelDropRuleThresholdParam
import com.twitter.visibility.configapi.params.FSRuleParams.HighSpammyTweetContentScoreTrendLatestTweetLabelDropRuleThresholdParam
import com.twitter.visibility.configapi.params.FSRuleParams.HighSpammyTweetContentScoreTrendTopTweetLabelDropRuleThresholdParam
import com.twitter.visibility.configapi.params.FSRuleParams.SkipTweetDetailLimitedEngagementRuleEnabledParam
import com.twitter.visibility.configapi.params.RuleParam
import com.twitter.visibility.configapi.params.RuleParams._
import com.twitter.visibility.models.TweetSafetyLabelType
import com.twitter.visibility.rules.Condition._
import com.twitter.visibility.rules.Condition.{True => TrueCondition}
import com.twitter.visibility.rules.Reason._
import com.twitter.visibility.rules.RuleActionSourceBuilder.TweetSafetyLabelSourceBuilder

object AbusiveTweetLabelRule
    etwittertends NonAuthorWithTweetLabelRule(
      Drop(Unspecified),
      TweetSafetyLabelType.Abusive
    )
    with DoesLogVerdict

object AbusiveNonFollowerTweetLabelRule
    etwittertends NonFollowerWithTweetLabelRule(
      Drop(Totwittericity),
      TweetSafetyLabelType.Abusive
    )

object AbusiveUqfNonFollowerTweetLabelRule
    etwittertends NonFollowerWithUqfTweetLabelRule(
      Drop(Totwittericity),
      TweetSafetyLabelType.Abusive
    )

object AbusiveHighRecallTweetLabelRule
    etwittertends NonAuthorWithTweetLabelRule(
      Drop(Unspecified),
      TweetSafetyLabelType.AbusiveHighRecall
    )

object AbusiveHighRecallNonFollowerTweetLabelRule
    etwittertends NonFollowerWithTweetLabelRule(
      Interstitial(PossiblyUndesirable),
      TweetSafetyLabelType.AbusiveHighRecall
    )

object AutomationTweetLabelRule
    etwittertends NonFollowerWithTweetLabelRule(
      Drop(Unspecified),
      TweetSafetyLabelType.Automation
    )

object BystanderAbusiveTweetLabelRule
    etwittertends NonAuthorWithTweetLabelRule(
      Drop(Unspecified),
      TweetSafetyLabelType.BystanderAbusive
    )

object BystanderAbusiveNonFollowerTweetLabelRule
    etwittertends NonFollowerWithTweetLabelRule(
      Drop(Unspecified),
      TweetSafetyLabelType.BystanderAbusive
    )

abstract class DuplicateContentTweetLabelRule(action: Action)
    etwittertends NonAuthorWithTweetLabelRule(
      action,
      TweetSafetyLabelType.DuplicateContent
    )

object DuplicateContentTweetLabelDropRule
    etwittertends DuplicateContentTweetLabelRule(Drop(TweetLabelDuplicateContent))

object DuplicateContentTweetLabelTombstoneRule
    etwittertends DuplicateContentTweetLabelRule(Tombstone(Epitaph.Unavailable))

object DuplicateMentionTweetLabelRule
    etwittertends NonFollowerWithTweetLabelRule(
      Drop(Unspecified),
      TweetSafetyLabelType.DuplicateMention
    )

object DuplicateMentionUqfTweetLabelRule
    etwittertends NonFollowerWithUqfTweetLabelRule(
      Drop(TweetLabelDuplicateMention),
      TweetSafetyLabelType.DuplicateMention
    )

object GoreAndViolenceTweetLabelRule
    etwittertends ConditionWithTweetLabelRule(
      Drop(Unspecified),
      And(
        NonAuthorViewer,
        TweetComposedBefore(TweetSafetyLabelType.GoreAndViolence.DeprecatedAt)
      ),
      TweetSafetyLabelType.GoreAndViolence
    )

object LiveLowQualityTweetLabelRule
    etwittertends NonAuthorWithTweetLabelRule(
      Drop(Unspecified),
      TweetSafetyLabelType.LiveLowQuality
    )

object LowQualityMentionTweetLabelRule
    etwittertends RuleWithConstantAction(
      Drop(LowQualityMention),
      And(
        TweetHasLabelForPerspectivalUser(TweetSafetyLabelType.LowQualityMention),
        ViewerHasUqfEnabled
      )
    )

abstract class NsfwCardImageTweetLabelBaseRule(
  override val action: Action,
  val additionalCondition: Condition = TrueCondition,
) etwittertends RuleWithConstantAction(
      action,
      And(
        additionalCondition,
        TweetHasLabel(TweetSafetyLabelType.NsfwCardImage)
      )
    )

object NsfwCardImageTweetLabelRule
    etwittertends NsfwCardImageTweetLabelBaseRule(
      action = Drop(Nsfw),
      additionalCondition = NonAuthorViewer,
    )

object NsfwCardImageAllUsersTweetLabelRule
    etwittertends NsfwCardImageTweetLabelBaseRule(
      action = Interstitial(Nsfw)
    )

object NsfwCardImageAvoidAllUsersTweetLabelRule
    etwittertends NsfwCardImageTweetLabelBaseRule(
      action = Avoid(Some(AvoidReason.ContainsNsfwMedia)),
    ) {
  override def enabled: Seq[RuleParam[Boolean]] = Seq(EnableAvoidNsfwRulesParam)

  override val fallbackActionBuilder: Option[ActionBuilder[_ <: Action]] = Some(
    new ConstantActionBuilder(Avoid(Some(MightNotBeSuitableForAds))))
}

object NsfwCardImageAvoidAdPlacementAllUsersTweetLabelRule
    etwittertends NsfwCardImageTweetLabelBaseRule(
      action = Avoid(Some(AvoidReason.ContainsNsfwMedia)),
    ) {
  override def enabled: Seq[RuleParam[Boolean]] = Seq(EnableAvoidNsfwRulesParam)
}

object SearchAvoidTweetNsfwAdminRule
    etwittertends RuleWithConstantAction(
      Avoid(Some(AvoidReason.ContainsNsfwMedia)),
      TweetHasNsfwAdminAuthor
    ) {
  override def enabled: Seq[RuleParam[Boolean]] = Seq(EnableAvoidNsfwRulesParam)
}

object SearchAvoidTweetNsfwUserRule
    etwittertends RuleWithConstantAction(
      Avoid(Some(AvoidReason.ContainsNsfwMedia)),
      TweetHasNsfwUserAuthor
    ) {
  override def enabled: Seq[RuleParam[Boolean]] = Seq(EnableAvoidNsfwRulesParam)
}

object NsfwCardImageAllUsersTweetLabelDropRule
    etwittertends NsfwCardImageTweetLabelBaseRule(
      action = Drop(Nsfw),
    )

object HighProactiveTosScoreTweetLabelDropRule
    etwittertends NonAuthorWithTweetLabelRule(
      Drop(Unspecified),
      TweetSafetyLabelType.HighProactiveTosScore
    )

object HighProactiveTosScoreTweetLabelDropSearchRule
    etwittertends NonAuthorAndNonFollowerWithTweetLabelRule(
      Drop(Unspecified),
      TweetSafetyLabelType.HighProactiveTosScore
    )

object NsfwHighPrecisionTweetLabelRule
    etwittertends NonAuthorWithTweetLabelRule(
      Drop(Nsfw),
      TweetSafetyLabelType.NsfwHighPrecision
    )

object NsfwHighPrecisionAllUsersTweetLabelDropRule
    etwittertends TweetHasLabelRule(
      Drop(Nsfw),
      TweetSafetyLabelType.NsfwHighPrecision
    )

object NsfwHighPrecisionInnerQuotedTweetLabelRule
    etwittertends ConditionWithTweetLabelRule(
      Drop(Nsfw),
      And(IsQuotedInnerTweet, NonAuthorViewer),
      TweetSafetyLabelType.NsfwHighPrecision
    ) {
  override def enabled: Seq[RuleParam[Boolean]] = Seq(EnableNsfwHpQuotedTweetDropRuleParam)
}

object NsfwHighPrecisionTombstoneInnerQuotedTweetLabelRule
    etwittertends ConditionWithTweetLabelRule(
      Tombstone(Epitaph.Unavailable),
      And(IsQuotedInnerTweet, NonAuthorViewer),
      TweetSafetyLabelType.NsfwHighPrecision
    ) {
  override def enabled: Seq[RuleParam[Boolean]] = Seq(EnableNsfwHpQuotedTweetTombstoneRuleParam)
}

object GoreAndViolenceHighPrecisionTweetLabelRule
    etwittertends NonAuthorWithTweetLabelRule(
      Drop(Nsfw),
      TweetSafetyLabelType.GoreAndViolenceHighPrecision
    )

object NsfwReportedHeuristicsTweetLabelRule
    etwittertends NonAuthorWithTweetLabelRule(
      Drop(Nsfw),
      TweetSafetyLabelType.NsfwReportedHeuristics
    )

object GoreAndViolenceReportedHeuristicsTweetLabelRule
    etwittertends NonAuthorWithTweetLabelRule(
      Drop(Nsfw),
      TweetSafetyLabelType.GoreAndViolenceReportedHeuristics
    )

object NsfwHighPrecisionInterstitialAllUsersTweetLabelRule
    etwittertends TweetHasLabelRule(
      Interstitial(Nsfw),
      TweetSafetyLabelType.NsfwHighPrecision
    )
    with DoesLogVerdict

object GoreAndViolenceHighPrecisionAvoidAllUsersTweetLabelRule
    etwittertends TweetHasLabelRule(
      Avoid(Some(AvoidReason.ContainsNsfwMedia)),
      TweetSafetyLabelType.GoreAndViolenceHighPrecision
    ) {
  override def enabled: Seq[RuleParam[Boolean]] = Seq(EnableAvoidNsfwRulesParam)

  override val fallbackActionBuilder: Option[ActionBuilder[_ <: Action]] = Some(
    new ConstantActionBuilder(Avoid(Some(MightNotBeSuitableForAds))))
}

object GoreAndViolenceHighPrecisionAllUsersTweetLabelRule
    etwittertends TweetHasLabelRule(
      Interstitial(Nsfw),
      TweetSafetyLabelType.GoreAndViolenceHighPrecision
    )
    with DoesLogVerdict {
  override def actionSourceBuilder: Option[RuleActionSourceBuilder] = Some(
    TweetSafetyLabelSourceBuilder(TweetSafetyLabelType.GoreAndViolenceHighPrecision)
  )
}

object NsfwReportedHeuristicsAvoidAllUsersTweetLabelRule
    etwittertends TweetHasLabelRule(
      Avoid(Some(AvoidReason.ContainsNsfwMedia)),
      TweetSafetyLabelType.NsfwReportedHeuristics
    ) {
  override def enabled: Seq[RuleParam[Boolean]] = Seq(EnableAvoidNsfwRulesParam)

  override val fallbackActionBuilder: Option[ActionBuilder[_ <: Action]] = Some(
    new ConstantActionBuilder(Avoid(Some(MightNotBeSuitableForAds))))
}

object NsfwReportedHeuristicsAvoidAdPlacementAllUsersTweetLabelRule
    etwittertends TweetHasLabelRule(
      Avoid(Some(AvoidReason.ContainsNsfwMedia)),
      TweetSafetyLabelType.NsfwReportedHeuristics
    ) {
  override def enabled: Seq[RuleParam[Boolean]] = Seq(EnableAvoidNsfwRulesParam)

  override val fallbackActionBuilder: Option[ActionBuilder[_ <: Action]] = Some(
    new ConstantActionBuilder(Avoid(Some(MightNotBeSuitableForAds))))
}

object NsfwReportedHeuristicsAllUsersTweetLabelRule
    etwittertends TweetHasLabelRule(
      Interstitial(Nsfw),
      TweetSafetyLabelType.NsfwReportedHeuristics
    )

object GoreAndViolenceReportedHeuristicsAllUsersTweetLabelRule
    etwittertends TweetHasLabelRule(
      Interstitial(Nsfw),
      TweetSafetyLabelType.GoreAndViolenceReportedHeuristics
    )

object GoreAndViolenceReportedHeuristicsAvoidAllUsersTweetLabelRule
    etwittertends TweetHasLabelRule(
      Avoid(Some(AvoidReason.ContainsNsfwMedia)),
      TweetSafetyLabelType.GoreAndViolenceReportedHeuristics
    ) {
  override def enabled: Seq[RuleParam[Boolean]] = Seq(EnableAvoidNsfwRulesParam)

  override val fallbackActionBuilder: Option[ActionBuilder[_ <: Action]] = Some(
    new ConstantActionBuilder(Avoid(Some(MightNotBeSuitableForAds))))
}

object GoreAndViolenceReportedHeuristicsAvoidAdPlacementAllUsersTweetLabelRule
    etwittertends TweetHasLabelRule(
      Avoid(Some(AvoidReason.ContainsNsfwMedia)),
      TweetSafetyLabelType.GoreAndViolenceReportedHeuristics
    ) {
  override def enabled: Seq[RuleParam[Boolean]] = Seq(EnableAvoidNsfwRulesParam)

  override val fallbackActionBuilder: Option[ActionBuilder[_ <: Action]] = Some(
    new ConstantActionBuilder(Avoid(Some(MightNotBeSuitableForAds))))
}

object GoreAndViolenceHighPrecisionAllUsersTweetLabelDropRule
    etwittertends TweetHasLabelRule(
      Drop(Nsfw),
      TweetSafetyLabelType.GoreAndViolenceHighPrecision
    )

object NsfwReportedHeuristicsAllUsersTweetLabelDropRule
    etwittertends TweetHasLabelRule(
      Drop(Nsfw),
      TweetSafetyLabelType.NsfwReportedHeuristics
    )

object GoreAndViolenceReportedHeuristicsAllUsersTweetLabelDropRule
    etwittertends TweetHasLabelRule(
      Drop(Nsfw),
      TweetSafetyLabelType.GoreAndViolenceReportedHeuristics
    )

object NsfwHighRecallTweetLabelRule
    etwittertends NonAuthorWithTweetLabelRule(
      Drop(Nsfw),
      TweetSafetyLabelType.NsfwHighRecall
    )

object NsfwHighRecallAllUsersTweetLabelDropRule
    etwittertends TweetHasLabelRule(
      Drop(Nsfw),
      TweetSafetyLabelType.NsfwHighRecall
    )

abstract class PdnaTweetLabelRule(
  override val action: Action,
  val additionalCondition: Condition)
    etwittertends ConditionWithTweetLabelRule(
      action,
      And(NonAuthorViewer, additionalCondition),
      TweetSafetyLabelType.Pdna
    )

object PdnaTweetLabelRule etwittertends PdnaTweetLabelRule(Drop(PdnaTweet), Condition.True)

object PdnaTweetLabelTombstoneRule
    etwittertends PdnaTweetLabelRule(Tombstone(Epitaph.Unavailable), Condition.True)

object PdnaQuotedTweetLabelTombstoneRule
    etwittertends PdnaTweetLabelRule(Tombstone(Epitaph.Unavailable), Condition.IsQuotedInnerTweet) {
  override def enabled: Seq[RuleParam[Boolean]] = Seq(EnablePdnaQuotedTweetTombstoneRuleParam)
}

object PdnaAllUsersTweetLabelRule
    etwittertends TweetHasLabelRule(
      Drop(Unspecified),
      TweetSafetyLabelType.Pdna
    )

object SearchBlacklistTweetLabelRule
    etwittertends NonAuthorWithTweetLabelRule(
      Drop(Unspecified),
      TweetSafetyLabelType.SearchBlacklist
    )

object SearchBlacklistHighRecallTweetLabelDropRule
    etwittertends NonAuthorAndNonFollowerWithTweetLabelRule(
      Drop(Unspecified),
      TweetSafetyLabelType.SearchBlacklistHighRecall
    )

abstract class SpamTweetLabelRule(
  override val action: Action,
  val additionalCondition: Condition)
    etwittertends ConditionWithTweetLabelRule(
      action,
      And(NonAuthorViewer, additionalCondition),
      TweetSafetyLabelType.Spam
    )
    with DoesLogVerdict

object SpamTweetLabelRule etwittertends SpamTweetLabelRule(Drop(TweetLabeledSpam), Condition.True)

object SpamTweetLabelTombstoneRule
    etwittertends SpamTweetLabelRule(Tombstone(Epitaph.Unavailable), Condition.True)

object SpamQuotedTweetLabelTombstoneRule
    etwittertends SpamTweetLabelRule(Tombstone(Epitaph.Unavailable), Condition.IsQuotedInnerTweet) {
  override def enabled: Seq[RuleParam[Boolean]] = Seq(EnableSpamQuotedTweetTombstoneRuleParam)
}

object SpamAllUsersTweetLabelRule
    etwittertends TweetHasLabelRule(
      Drop(Unspecified),
      TweetSafetyLabelType.Spam
    )

abstract class BounceTweetLabelRule(override val action: Action)
    etwittertends NonAuthorWithTweetLabelRule(
      action,
      TweetSafetyLabelType.Bounce
    )

object BounceTweetLabelRule etwittertends BounceTweetLabelRule(Drop(Bounce))

object BounceTweetLabelTombstoneRule etwittertends BounceTweetLabelRule(Tombstone(Epitaph.Bounced))

abstract class BounceOuterTweetLabelRule(override val action: Action)
    etwittertends ConditionWithTweetLabelRule(
      action,
      And(Not(Condition.IsQuotedInnerTweet), NonAuthorViewer),
      TweetSafetyLabelType.Bounce
    )

object BounceOuterTweetTombstoneRule etwittertends BounceOuterTweetLabelRule(Tombstone(Epitaph.Bounced))

object BounceQuotedTweetTombstoneRule
    etwittertends ConditionWithTweetLabelRule(
      Tombstone(Epitaph.Bounced),
      Condition.IsQuotedInnerTweet,
      TweetSafetyLabelType.Bounce
    )

object BounceAllUsersTweetLabelRule
    etwittertends TweetHasLabelRule(
      Drop(Bounce),
      TweetSafetyLabelType.Bounce
    )


abstract class SpamHighRecallTweetLabelRule(action: Action)
    etwittertends NonAuthorWithTweetLabelRule(
      action,
      TweetSafetyLabelType.SpamHighRecall
    )

object SpamHighRecallTweetLabelDropRule
    etwittertends SpamHighRecallTweetLabelRule(Drop(SpamHighRecallTweet))

object SpamHighRecallTweetLabelTombstoneRule
    etwittertends SpamHighRecallTweetLabelRule(Tombstone(Epitaph.Unavailable))

object UntrustedUrlAllViewersTweetLabelRule
    etwittertends TweetHasLabelRule(
      Drop(Unspecified),
      TweetSafetyLabelType.UntrustedUrl
    )

object DownrankSpamReplyAllViewersTweetLabelRule
    etwittertends TweetHasLabelRule(
      Drop(Unspecified),
      TweetSafetyLabelType.DownrankSpamReply
    ) {
  override def enabled: Seq[RuleParam[Boolean]] =
    Seq(EnableDownrankSpamReplySectioningRuleParam)
}

object UntrustedUrlTweetLabelRule
    etwittertends NonAuthorWithTweetLabelRule(
      Drop(Unspecified),
      TweetSafetyLabelType.UntrustedUrl
    )

object DownrankSpamReplyTweetLabelRule
    etwittertends NonAuthorWithTweetLabelRule(
      Drop(Unspecified),
      TweetSafetyLabelType.DownrankSpamReply
    ) {
  override def enabled: Seq[RuleParam[Boolean]] =
    Seq(EnableDownrankSpamReplySectioningRuleParam)
}

object UntrustedUrlUqfNonFollowerTweetLabelRule
    etwittertends NonFollowerWithUqfTweetLabelRule(
      Drop(UntrustedUrl),
      TweetSafetyLabelType.UntrustedUrl
    )

object DownrankSpamReplyUqfNonFollowerTweetLabelRule
    etwittertends NonFollowerWithUqfTweetLabelRule(
      Drop(SpamReplyDownRank),
      TweetSafetyLabelType.DownrankSpamReply
    ) {
  override def enabled: Seq[RuleParam[Boolean]] =
    Seq(EnableDownrankSpamReplySectioningRuleParam)
}

object NsfaHighRecallTweetLabelRule
    etwittertends RuleWithConstantAction(
      Drop(Unspecified),
      And(
        NonAuthorViewer,
        TweetHasLabel(TweetSafetyLabelType.NsfaHighRecall)
      )
    )

object NsfaHighRecallTweetLabelInterstitialRule
    etwittertends RuleWithConstantAction(
      Interstitial(Unspecified),
      And(
        NonAuthorViewer,
        TweetHasLabel(TweetSafetyLabelType.NsfaHighRecall)
      )
    )

object NsfwVideoTweetLabelDropRule
    etwittertends NonAuthorWithTweetLabelRule(
      Drop(Nsfw),
      TweetSafetyLabelType.NsfwVideo
    ) {
  override def enabled: Seq[RuleParam[Boolean]] = Seq(EnableNsfwTetwittertSectioningRuleParam)
}

object NsfwTetwittertTweetLabelDropRule
    etwittertends NonAuthorWithTweetLabelRule(
      Drop(Nsfw),
      TweetSafetyLabelType.NsfwTetwittert
    )

object NsfwVideoAllUsersTweetLabelDropRule
    etwittertends TweetHasLabelRule(
      Drop(Nsfw),
      TweetSafetyLabelType.NsfwVideo
    )

object NsfwTetwittertAllUsersTweetLabelDropRule
    etwittertends TweetHasLabelRule(
      Drop(Nsfw),
      TweetSafetyLabelType.NsfwTetwittert
    ) {
  override def enabled: Seq[RuleParam[Boolean]] = Seq(EnableNsfwTetwittertSectioningRuleParam)
}

abstract class BaseLowQualityTweetLabelRule(action: Action)
    etwittertends RuleWithConstantAction(
      action,
      And(
        TweetHasLabel(TweetSafetyLabelType.LowQuality),
        TweetComposedBefore(PublicInterest.PolicyConfig.LowQualityProtwitteryLabelStart),
        NonAuthorViewer
      )
    )
    with DoesLogVerdict {
  override def actionSourceBuilder: Option[RuleActionSourceBuilder] = Some(
    TweetSafetyLabelSourceBuilder(TweetSafetyLabelType.LowQuality))
}

object LowQualityTweetLabelDropRule etwittertends BaseLowQualityTweetLabelRule(Drop(LowQualityTweet))

object LowQualityTweetLabelTombstoneRule
    etwittertends BaseLowQualityTweetLabelRule(Tombstone(Epitaph.Unavailable))

abstract class SafetyCrisisLevelDropRule(level: Int, condition: Condition = TrueCondition)
    etwittertends ConditionWithTweetLabelRule(
      Drop(Unspecified),
      And(
        NonAuthorViewer,
        condition,
        TweetHasSafetyLabelWithScoreEqInt(TweetSafetyLabelType.SafetyCrisis, level)
      ),
      TweetSafetyLabelType.SafetyCrisis
    )

object SafetyCrisisAnyLevelDropRule
    etwittertends NonAuthorWithTweetLabelRule(
      Drop(Unspecified),
      TweetSafetyLabelType.SafetyCrisis
    )

object SafetyCrisisLevel2DropRule etwittertends SafetyCrisisLevelDropRule(2, Not(ViewerDoesFollowAuthor))

object SafetyCrisisLevel3DropRule etwittertends SafetyCrisisLevelDropRule(3, Not(ViewerDoesFollowAuthor))

object SafetyCrisisLevel4DropRule etwittertends SafetyCrisisLevelDropRule(4)

abstract class SafetyCrisisLevelSectionRule(level: Int)
    etwittertends ConditionWithNotInnerCircleOfFriendsRule(
      ConversationSectionAbusiveQuality,
      And(
        TweetHasLabel(TweetSafetyLabelType.SafetyCrisis),
        TweetHasSafetyLabelWithScoreEqInt(TweetSafetyLabelType.SafetyCrisis, level))
    ) {
  override def actionSourceBuilder: Option[RuleActionSourceBuilder] = Some(
    TweetSafetyLabelSourceBuilder(TweetSafetyLabelType.SafetyCrisis))
}

object SafetyCrisisLevel3SectionRule
    etwittertends SafetyCrisisLevelSectionRule(3)
    with DoesLogVerdictDecidered {
  override def verdictLogDeciderKey = DeciderKey.EnableDownlevelRuleVerdictLogging
}

object SafetyCrisisLevel4SectionRule
    etwittertends SafetyCrisisLevelSectionRule(4)
    with DoesLogVerdictDecidered {
  override def verdictLogDeciderKey = DeciderKey.EnableDownlevelRuleVerdictLogging
}

object DoNotAmplifyDropRule
    etwittertends NonFollowerWithTweetLabelRule(Drop(Unspecified), TweetSafetyLabelType.DoNotAmplify)

object DoNotAmplifyAllViewersDropRule
    etwittertends TweetHasLabelRule(Drop(Unspecified), TweetSafetyLabelType.DoNotAmplify)

object DoNotAmplifySectionRule
    etwittertends ConditionWithNotInnerCircleOfFriendsRule(
      ConversationSectionAbusiveQuality,
      TweetHasLabel(TweetSafetyLabelType.DoNotAmplify))

object HighPSpammyScoreAllViewerDropRule
    etwittertends TweetHasLabelRule(Drop(Unspecified), TweetSafetyLabelType.HighPSpammyTweetScore)

object HighPSpammyTweetScoreSearchTweetLabelDropRule
    etwittertends RuleWithConstantAction(
      action = Drop(Unspecified),
      condition = And(
        LoggedOutOrViewerNotFollowingAuthor,
        TweetHasLabelWithScoreAboveThreshold(
          TweetSafetyLabelType.HighPSpammyTweetScore,
          ModelScoreThresholds.HighPSpammyTweetScoreThreshold)
      )
    )
    with DoesLogVerdictDecidered {
  override def enabled: Seq[RuleParam[Boolean]] = Seq(
    EnableHighPSpammyTweetScoreSearchTweetLabelDropRuleParam)
  override def actionSourceBuilder: Option[RuleActionSourceBuilder] = Some(
    TweetSafetyLabelSourceBuilder(TweetSafetyLabelType.HighPSpammyTweetScore))
  override def verdictLogDeciderKey: DeciderKey.Value =
    DeciderKey.EnableSpammyTweetRuleVerdictLogging
}

object AdsManagerDenyListAllUsersTweetLabelRule
    etwittertends TweetHasLabelRule(
      Drop(Unspecified),
      TweetSafetyLabelType.AdsManagerDenyList
    )

abstract class SmyteSpamTweetLabelRule(action: Action)
    etwittertends NonAuthorWithTweetLabelRule(
      action,
      TweetSafetyLabelType.SmyteSpamTweet
    ) {
  override def enabled: Seq[RuleParam[Boolean]] = Seq(EnableSmyteSpamTweetRuleParam)
}

object SmyteSpamTweetLabelDropRule etwittertends SmyteSpamTweetLabelRule(Drop(TweetLabeledSpam))

object SmyteSpamTweetLabelTombstoneRule
    etwittertends SmyteSpamTweetLabelRule(Tombstone(Epitaph.Unavailable))

object SmyteSpamTweetLabelDropSearchRule etwittertends SmyteSpamTweetLabelRule(Drop(Unspecified))

object HighSpammyTweetContentScoreSearchLatestTweetLabelDropRule
    etwittertends RuleWithConstantAction(
      action = Drop(Unspecified),
      condition = And(
        Not(IsTweetInTweetLevelStcmHoldback),
        LoggedOutOrViewerNotFollowingAuthor,
        TweetHasLabelWithScoreAboveThresholdWithParam(
          TweetSafetyLabelType.HighSpammyTweetContentScore,
          HighSpammyTweetContentScoreSearchLatestProdTweetLabelDropRuleThresholdParam)
      )
    )
    with DoesLogVerdictDecidered {
  override def actionSourceBuilder: Option[RuleActionSourceBuilder] = Some(
    TweetSafetyLabelSourceBuilder(TweetSafetyLabelType.HighSpammyTweetContentScore))
  override def verdictLogDeciderKey: DeciderKey.Value =
    DeciderKey.EnableSpammyTweetRuleVerdictLogging
}

object HighSpammyTweetContentScoreSearchTopTweetLabelDropRule
    etwittertends RuleWithConstantAction(
      action = Drop(Unspecified),
      condition = And(
        Not(IsTweetInTweetLevelStcmHoldback),
        LoggedOutOrViewerNotFollowingAuthor,
        TweetHasLabelWithScoreAboveThresholdWithParam(
          TweetSafetyLabelType.HighSpammyTweetContentScore,
          HighSpammyTweetContentScoreSearchTopProdTweetLabelDropRuleThresholdParam)
      )
    )
    with DoesLogVerdictDecidered {
  override def actionSourceBuilder: Option[RuleActionSourceBuilder] = Some(
    TweetSafetyLabelSourceBuilder(TweetSafetyLabelType.HighSpammyTweetContentScore))
  override def verdictLogDeciderKey: DeciderKey.Value =
    DeciderKey.EnableSpammyTweetRuleVerdictLogging

}

object HighSpammyTweetContentScoreTrendsTopTweetLabelDropRule
    etwittertends RuleWithConstantAction(
      action = Drop(Unspecified),
      condition = And(
        Not(IsTweetInTweetLevelStcmHoldback),
        LoggedOutOrViewerNotFollowingAuthor,
        IsTrendClickSourceSearchResult,
        TweetHasLabelWithScoreAboveThresholdWithParam(
          TweetSafetyLabelType.HighSpammyTweetContentScore,
          HighSpammyTweetContentScoreTrendTopTweetLabelDropRuleThresholdParam)
      )
    )
    with DoesLogVerdictDecidered {
  override def actionSourceBuilder: Option[RuleActionSourceBuilder] = Some(
    TweetSafetyLabelSourceBuilder(TweetSafetyLabelType.HighSpammyTweetContentScore))
  override def verdictLogDeciderKey: DeciderKey.Value =
    DeciderKey.EnableSpammyTweetRuleVerdictLogging

}

object HighSpammyTweetContentScoreTrendsLatestTweetLabelDropRule
    etwittertends RuleWithConstantAction(
      action = Drop(Unspecified),
      condition = And(
        Not(IsTweetInTweetLevelStcmHoldback),
        LoggedOutOrViewerNotFollowingAuthor,
        IsTrendClickSourceSearchResult,
        TweetHasLabelWithScoreAboveThresholdWithParam(
          TweetSafetyLabelType.HighSpammyTweetContentScore,
          HighSpammyTweetContentScoreTrendLatestTweetLabelDropRuleThresholdParam)
      )
    )
    with DoesLogVerdictDecidered {
  override def actionSourceBuilder: Option[RuleActionSourceBuilder] = Some(
    TweetSafetyLabelSourceBuilder(TweetSafetyLabelType.HighSpammyTweetContentScore))
  override def verdictLogDeciderKey: DeciderKey.Value =
    DeciderKey.EnableSpammyTweetRuleVerdictLogging
}

object GoreAndViolenceTopicHighRecallTweetLabelRule
    etwittertends NonAuthorWithTweetLabelRule(
      Drop(Unspecified),
      TweetSafetyLabelType.GoreAndViolenceTopicHighRecall
    ) {
  override def enabled: Seq[RuleParam[Boolean]] = Seq(
    EnableGoreAndViolenceTopicHighRecallTweetLabelRule)
}

object CopypastaSpamAllViewersTweetLabelRule
    etwittertends TweetHasLabelRule(
      Drop(Unspecified),
      TweetSafetyLabelType.CopypastaSpam
    ) {
  override def enabled: Seq[RuleParam[Boolean]] =
    Seq(EnableCopypastaSpamSearchDropRule)
}

object CopypastaSpamAllViewersSearchTweetLabelRule
    etwittertends TweetHasLabelRule(
      Drop(Unspecified),
      TweetSafetyLabelType.CopypastaSpam
    ) {
  override def enabled: Seq[RuleParam[Boolean]] =
    Seq(EnableCopypastaSpamSearchDropRule)
}

object CopypastaSpamNonFollowerSearchTweetLabelRule
    etwittertends NonFollowerWithTweetLabelRule(
      Drop(Unspecified),
      TweetSafetyLabelType.CopypastaSpam
    ) {
  override def enabled: Seq[RuleParam[Boolean]] =
    Seq(EnableCopypastaSpamSearchDropRule)
}

object CopypastaSpamAbusiveQualityTweetLabelRule
    etwittertends ConditionWithNotInnerCircleOfFriendsRule(
      ConversationSectionAbusiveQuality,
      TweetHasLabel(TweetSafetyLabelType.CopypastaSpam)
    )
    with DoesLogVerdictDecidered {
  override def enabled: Seq[RuleParam[Boolean]] = Seq(
    EnableCopypastaSpamDownrankConvosAbusiveQualityRule)
  override def actionSourceBuilder: Option[RuleActionSourceBuilder] = Some(
    TweetSafetyLabelSourceBuilder(TweetSafetyLabelType.CopypastaSpam))
  override def verdictLogDeciderKey = DeciderKey.EnableDownlevelRuleVerdictLogging
}

object DynamicProductAdLimitedEngagementTweetLabelRule
    etwittertends TweetHasLabelRule(
      LimitedEngagements(LimitedEngagementReason.DynamicProductAd),
      TweetSafetyLabelType.DynamicProductAd)

object SkipTweetDetailLimitedEngagementTweetLabelRule
    etwittertends AlwaysActRule(LimitedEngagements(LimitedEngagementReason.SkipTweetDetail)) {
  override def enabled: Seq[RuleParam[Boolean]] = Seq(
    SkipTweetDetailLimitedEngagementRuleEnabledParam)
}

object DynamicProductAdDropTweetLabelRule
    etwittertends TweetHasLabelRule(Drop(Unspecified), TweetSafetyLabelType.DynamicProductAd)

object NsfwTetwittertHighPrecisionTweetLabelDropRule
    etwittertends RuleWithConstantAction(
      Drop(Reason.Nsfw),
      And(
        NonAuthorViewer,
        Or(
          TweetHasLabel(TweetSafetyLabelType.EtwitterperimentalSensitiveIllegal2),
          TweetHasLabel(TweetSafetyLabelType.NsfwTetwittertHighPrecision)
        )
      )
    )
    with DoesLogVerdict {
  override def enabled: Seq[RuleParam[Boolean]] = Seq(EnableNsfwTetwittertHighPrecisionDropRuleParam)
  override def actionSourceBuilder: Option[RuleActionSourceBuilder] = Some(
    TweetSafetyLabelSourceBuilder(TweetSafetyLabelType.NsfwTetwittertHighPrecision))
}


object EtwitterperimentalNudgeLabelRule
    etwittertends TweetHasLabelRule(
      TweetVisibilityNudge(TweetVisibilityNudgeReason.EtwitterperimentalNudgeSafetyLabelReason),
      TweetSafetyLabelType.EtwitterperimentalNudge) {
  override def enabled: Seq[RuleParam[Boolean]] = Seq(EnableEtwitterperimentalNudgeEnabledParam)
}

object NsfwTetwittertTweetLabelAvoidRule
    etwittertends RuleWithConstantAction(
      Avoid(),
      Or(
        TweetHasLabel(TweetSafetyLabelType.EtwitterperimentalSensitiveIllegal2),
        TweetHasLabel(TweetSafetyLabelType.NsfwTetwittertHighPrecision)
      )
    ) {
  override def actionSourceBuilder: Option[RuleActionSourceBuilder] = Some(
    TweetSafetyLabelSourceBuilder(TweetSafetyLabelType.NsfwTetwittertHighPrecision))
}

object DoNotAmplifyTweetLabelAvoidRule
    etwittertends TweetHasLabelRule(
      Avoid(),
      TweetSafetyLabelType.DoNotAmplify
    ) {
  override val fallbackActionBuilder: Option[ActionBuilder[_ <: Action]] = Some(
    new ConstantActionBuilder(Avoid(Some(MightNotBeSuitableForAds))))
}

object NsfaHighPrecisionTweetLabelAvoidRule
    etwittertends TweetHasLabelRule(
      Avoid(),
      TweetSafetyLabelType.NsfaHighPrecision
    ) {
  override val fallbackActionBuilder: Option[ActionBuilder[_ <: Action]] = Some(
    new ConstantActionBuilder(Avoid(Some(MightNotBeSuitableForAds))))
}

object NsfwHighPrecisionTweetLabelAvoidRule
    etwittertends TweetHasLabelRule(
      Avoid(Some(AvoidReason.ContainsNsfwMedia)),
      TweetSafetyLabelType.NsfwHighPrecision
    ) {
  override val fallbackActionBuilder: Option[ActionBuilder[_ <: Action]] = Some(
    new ConstantActionBuilder(Avoid(Some(MightNotBeSuitableForAds))))
}

object NsfwHighRecallTweetLabelAvoidRule
    etwittertends TweetHasLabelRule(
      Avoid(Some(AvoidReason.ContainsNsfwMedia)),
      TweetSafetyLabelType.NsfwHighRecall
    ) {
  override val fallbackActionBuilder: Option[ActionBuilder[_ <: Action]] = Some(
    new ConstantActionBuilder(Avoid(Some(MightNotBeSuitableForAds))))
}
