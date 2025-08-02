package com.twitter.visibility.rules

import com.twitter.visibility.configapi.params.RuleParam
import com.twitter.visibility.configapi.params.RuleParams.EnableDownrankSpamReplySectioningRuleParam
import com.twitter.visibility.configapi.params.RuleParams.EnableNotGraduatedSearchDropRuleParam
import com.twitter.visibility.configapi.params.RuleParams.EnableNsfwTetwittertSectioningRuleParam
import com.twitter.visibility.configapi.params.RuleParams.NotGraduatedUserLabelRuleHoldbackEtwitterperimentParam
import com.twitter.visibility.models.TweetSafetyLabelType
import com.twitter.visibility.models.UserLabelValue
import com.twitter.visibility.rules.Condition.And
import com.twitter.visibility.rules.Condition.LoggedOutOrViewerNotFollowingAuthor
import com.twitter.visibility.rules.Condition.LoggedOutOrViewerOptInFiltering
import com.twitter.visibility.rules.Condition.NonAuthorViewer
import com.twitter.visibility.rules.Condition.Not
import com.twitter.visibility.rules.Condition.TweetComposedBefore
import com.twitter.visibility.rules.Condition.ViewerDoesFollowAuthor
import com.twitter.visibility.rules.Condition.ViewerOptInFilteringOnSearch
import com.twitter.visibility.rules.Reason.Nsfw
import com.twitter.visibility.rules.Reason.Unspecified
import com.twitter.visibility.rules.RuleActionSourceBuilder.TweetSafetyLabelSourceBuilder

case object SafeSearchTweetRules {

  object SafeSearchAbusiveTweetLabelRule
      etwittertends NonAuthorViewerOptInFilteringWithTweetLabelRule(
        Drop(Unspecified),
        TweetSafetyLabelType.Abusive
      )
      with DoesLogVerdict {
    override def actionSourceBuilder: Option[RuleActionSourceBuilder] = Some(
      TweetSafetyLabelSourceBuilder(TweetSafetyLabelType.Abusive))
  }

  object SafeSearchNsfwHighPrecisionTweetLabelRule
      etwittertends NonAuthorViewerOptInFilteringWithTweetLabelRule(
        Drop(Nsfw),
        TweetSafetyLabelType.NsfwHighPrecision
      )

  object SafeSearchGoreAndViolenceHighPrecisionTweetLabelRule
      etwittertends NonAuthorViewerOptInFilteringWithTweetLabelRule(
        Drop(Nsfw),
        TweetSafetyLabelType.GoreAndViolenceHighPrecision
      )

  object SafeSearchNsfwReportedHeuristicsTweetLabelRule
      etwittertends NonAuthorViewerOptInFilteringWithTweetLabelRule(
        Drop(Nsfw),
        TweetSafetyLabelType.NsfwReportedHeuristics
      )

  object SafeSearchGoreAndViolenceReportedHeuristicsTweetLabelRule
      etwittertends NonAuthorViewerOptInFilteringWithTweetLabelRule(
        Drop(Nsfw),
        TweetSafetyLabelType.GoreAndViolenceReportedHeuristics
      )

  object SafeSearchNsfwCardImageTweetLabelRule
      etwittertends NonAuthorViewerOptInFilteringWithTweetLabelRule(
        Drop(Nsfw),
        TweetSafetyLabelType.NsfwCardImage
      )

  object SafeSearchNsfwHighRecallTweetLabelRule
      etwittertends NonAuthorViewerOptInFilteringWithTweetLabelRule(
        Drop(Nsfw),
        TweetSafetyLabelType.NsfwHighRecall
      )

  object SafeSearchNsfwVideoTweetLabelRule
      etwittertends NonAuthorViewerOptInFilteringWithTweetLabelRule(
        Drop(Nsfw),
        TweetSafetyLabelType.NsfwVideo
      )

  object SafeSearchNsfwTetwittertTweetLabelRule
      etwittertends NonAuthorViewerOptInFilteringWithTweetLabelRule(
        Drop(Nsfw),
        TweetSafetyLabelType.NsfwTetwittert
      ) {
    override def enabled: Seq[RuleParam[Boolean]] = Seq(EnableNsfwTetwittertSectioningRuleParam)
  }

  object SafeSearchNsfwTetwittertAuthorLabelRule
      etwittertends ViewerOptInFilteringOnSearchUserLabelRule(
        Drop(Nsfw),
        UserLabelValue.DownrankSpamReply
      ) {
    override def enabled: Seq[RuleParam[Boolean]] = Seq(EnableNsfwTetwittertSectioningRuleParam)
  }

  object SafeSearchGoreAndViolenceTweetLabelRule
      etwittertends ConditionWithTweetLabelRule(
        Drop(Unspecified),
        And(
          NonAuthorViewer,
          TweetComposedBefore(TweetSafetyLabelType.GoreAndViolence.DeprecatedAt),
          LoggedOutOrViewerOptInFiltering
        ),
        TweetSafetyLabelType.GoreAndViolence
      )

  object SafeSearchUntrustedUrlTweetLabelRule
      etwittertends NonAuthorViewerOptInFilteringWithTweetLabelRule(
        Drop(Unspecified),
        TweetSafetyLabelType.UntrustedUrl
      )

  object SafeSearchDownrankSpamReplyTweetLabelRule
      etwittertends NonAuthorViewerOptInFilteringWithTweetLabelRule(
        Drop(Unspecified),
        TweetSafetyLabelType.DownrankSpamReply
      ) {
    override def enabled: Seq[RuleParam[Boolean]] =
      Seq(EnableDownrankSpamReplySectioningRuleParam)
  }

  object SafeSearchDownrankSpamReplyAuthorLabelRule
      etwittertends ViewerOptInFilteringOnSearchUserLabelRule(
        Drop(Unspecified),
        UserLabelValue.DownrankSpamReply
      ) {
    override def enabled: Seq[RuleParam[Boolean]] =
      Seq(EnableDownrankSpamReplySectioningRuleParam)
  }

  object SafeSearchAutomationNonFollowerTweetLabelRule
      etwittertends NonFollowerViewerOptInFilteringWithTweetLabelRule(
        Drop(Unspecified),
        TweetSafetyLabelType.Automation
      )

  object SafeSearchDuplicateMentionNonFollowerTweetLabelRule
      etwittertends NonFollowerViewerOptInFilteringWithTweetLabelRule(
        Drop(Unspecified),
        TweetSafetyLabelType.DuplicateMention
      )

  object SafeSearchBystanderAbusiveTweetLabelRule
      etwittertends NonAuthorViewerOptInFilteringWithTweetLabelRule(
        Drop(Unspecified),
        TweetSafetyLabelType.BystanderAbusive
      )
}

case object UnsafeSearchTweetRules {

  object UnsafeSearchNsfwHighPrecisionInterstitialAllUsersTweetLabelRule
      etwittertends ConditionWithTweetLabelRule(
        Interstitial(Nsfw),
        Not(ViewerOptInFilteringOnSearch),
        TweetSafetyLabelType.NsfwHighPrecision
      )

  object UnsafeSearchGoreAndViolenceHighPrecisionAllUsersTweetLabelRule
      etwittertends ConditionWithTweetLabelRule(
        Interstitial(Nsfw),
        Not(ViewerOptInFilteringOnSearch),
        TweetSafetyLabelType.GoreAndViolenceHighPrecision
      )

  object UnsafeSearchGoreAndViolenceHighPrecisionAllUsersTweetLabelDropRule
      etwittertends ConditionWithTweetLabelRule(
        Drop(Nsfw),
        Not(ViewerOptInFilteringOnSearch),
        TweetSafetyLabelType.GoreAndViolenceHighPrecision
      )

  object UnsafeSearchNsfwReportedHeuristicsAllUsersTweetLabelRule
      etwittertends ConditionWithTweetLabelRule(
        Interstitial(Nsfw),
        Not(ViewerOptInFilteringOnSearch),
        TweetSafetyLabelType.NsfwReportedHeuristics
      )

  object UnsafeSearchNsfwReportedHeuristicsAllUsersTweetLabelDropRule
      etwittertends ConditionWithTweetLabelRule(
        Drop(Nsfw),
        Not(ViewerOptInFilteringOnSearch),
        TweetSafetyLabelType.NsfwReportedHeuristics
      )

  object UnsafeSearchNsfwHighPrecisionAllUsersTweetLabelDropRule
      etwittertends ConditionWithTweetLabelRule(
        Drop(Nsfw),
        Not(ViewerOptInFilteringOnSearch),
        TweetSafetyLabelType.NsfwHighPrecision
      )

  object UnsafeSearchGoreAndViolenceReportedHeuristicsAllUsersTweetLabelRule
      etwittertends ConditionWithTweetLabelRule(
        Interstitial(Nsfw),
        Not(ViewerOptInFilteringOnSearch),
        TweetSafetyLabelType.GoreAndViolenceReportedHeuristics
      )

  object UnsafeSearchGoreAndViolenceReportedHeuristicsAllUsersTweetLabelDropRule
      etwittertends ConditionWithTweetLabelRule(
        Drop(Nsfw),
        Not(ViewerOptInFilteringOnSearch),
        TweetSafetyLabelType.GoreAndViolenceReportedHeuristics
      )

  object UnsafeSearchNsfwCardImageAllUsersTweetLabelRule
      etwittertends ConditionWithTweetLabelRule(
        Interstitial(Nsfw),
        Not(ViewerOptInFilteringOnSearch),
        TweetSafetyLabelType.NsfwCardImage
      )

  object UnsafeSearchNsfwCardImageAllUsersTweetLabelDropRule
      etwittertends ConditionWithTweetLabelRule(
        Drop(Nsfw),
        Not(ViewerOptInFilteringOnSearch),
        TweetSafetyLabelType.NsfwCardImage
      )

}

case object SafeSearchUserRules {

  object SafeSearchAbusiveUserLabelRule
      etwittertends ViewerOptInFilteringOnSearchUserLabelRule(
        Drop(Unspecified),
        UserLabelValue.Abusive
      )

  object SafeSearchAbusiveHighRecallUserLabelRule
      etwittertends ViewerOptInFilteringOnSearchUserLabelRule(
        Drop(Unspecified),
        UserLabelValue.AbusiveHighRecall,
        LoggedOutOrViewerNotFollowingAuthor
      )

  object SafeSearchHighRecallUserLabelRule
      etwittertends ViewerOptInFilteringOnSearchUserLabelRule(
        Drop(Nsfw),
        UserLabelValue.NsfwHighRecall,
        LoggedOutOrViewerNotFollowingAuthor
      )

  object SafeSearchCompromisedUserLabelRule
      etwittertends ViewerOptInFilteringOnSearchUserLabelRule(
        Drop(Unspecified),
        UserLabelValue.Compromised
      )

  object SafeSearchDuplicateContentUserLabelRule
      etwittertends ViewerOptInFilteringOnSearchUserLabelRule(
        Drop(Unspecified),
        UserLabelValue.DuplicateContent
      )

  object SafeSearchLowQualityUserLabelRule
      etwittertends ViewerOptInFilteringOnSearchUserLabelRule(
        Drop(Unspecified),
        UserLabelValue.LowQuality
      )

  object SafeSearchNsfwHighPrecisionUserLabelRule
      etwittertends ConditionWithUserLabelRule(
        Drop(Nsfw),
        LoggedOutOrViewerOptInFiltering,
        UserLabelValue.NsfwHighPrecision
      )

  object SafeSearchNsfwAvatarImageUserLabelRule
      etwittertends ViewerOptInFilteringOnSearchUserLabelRule(
        Drop(Nsfw),
        UserLabelValue.NsfwAvatarImage
      )

  object SafeSearchNsfwBannerImageUserLabelRule
      etwittertends ViewerOptInFilteringOnSearchUserLabelRule(
        Drop(Nsfw),
        UserLabelValue.NsfwBannerImage
      )

  object SafeSearchNsfwNearPerfectAuthorRule
      etwittertends ViewerOptInFilteringOnSearchUserLabelRule(
        Drop(Nsfw),
        UserLabelValue.NsfwNearPerfect
      )

  object SafeSearchReadOnlyUserLabelRule
      etwittertends ViewerOptInFilteringOnSearchUserLabelRule(
        Drop(Unspecified),
        UserLabelValue.ReadOnly
      )

  object SafeSearchSpamHighRecallUserLabelRule
      etwittertends ViewerOptInFilteringOnSearchUserLabelRule(
        Drop(Unspecified),
        UserLabelValue.SpamHighRecall
      )

  object SafeSearchSearchBlacklistUserLabelRule
      etwittertends ViewerOptInFilteringOnSearchUserLabelRule(
        Drop(Unspecified),
        UserLabelValue.SearchBlacklist
      )

  object SafeSearchNsfwTetwittertUserLabelRule
      etwittertends ViewerOptInFilteringOnSearchUserLabelRule(
        Drop(Unspecified),
        UserLabelValue.NsfwTetwittert
      ) {
    override def enabled: Seq[RuleParam[Boolean]] =
      Seq(EnableNsfwTetwittertSectioningRuleParam)
  }

  object SafeSearchDoNotAmplifyNonFollowersUserLabelRule
      etwittertends ViewerOptInFilteringOnSearchUserLabelRule(
        Drop(Unspecified),
        UserLabelValue.DoNotAmplify,
        prerequisiteCondition = Not(ViewerDoesFollowAuthor)
      )

  object SafeSearchNotGraduatedNonFollowersUserLabelRule
      etwittertends ViewerOptInFilteringOnSearchUserLabelRule(
        Drop(Unspecified),
        UserLabelValue.NotGraduated,
        prerequisiteCondition = Not(ViewerDoesFollowAuthor)
      ) {
    override def enabled: Seq[RuleParam[Boolean]] =
      Seq(EnableNotGraduatedSearchDropRuleParam)

    override def holdbacks: Seq[RuleParam[Boolean]] =
      Seq(NotGraduatedUserLabelRuleHoldbackEtwitterperimentParam)

  }
}
