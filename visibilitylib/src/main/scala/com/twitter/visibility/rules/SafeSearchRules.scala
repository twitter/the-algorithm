package com.twitter.visibility.rules

import com.twitter.visibility.configapi.params.RuleParam
import com.twitter.visibility.configapi.params.RuleParams.EnableDownrankSpamReplySectioningRuleParam
import com.twitter.visibility.configapi.params.RuleParams.EnableNotGraduatedSearchDropRuleParam
import com.twitter.visibility.configapi.params.RuleParams.NotGraduatedUserLabelRuleHoldbackExperimentParam
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
import com.twitter.visibility.rules.Reason.Unspecified
import com.twitter.visibility.rules.RuleActionSourceBuilder.TweetSafetyLabelSourceBuilder

case object SafeSearchTweetRules {

  object SafeSearchAbusiveTweetLabelRule
      extends NonAuthorViewerOptInFilteringWithTweetLabelRule(
        Drop(Unspecified),
        TweetSafetyLabelType.Abusive
      )
      with DoesLogVerdict {
    override def actionSourceBuilder: Option[RuleActionSourceBuilder] = Some(
      TweetSafetyLabelSourceBuilder(TweetSafetyLabelType.Abusive))
  }

  object SafeSearchGoreAndViolenceTweetLabelRule
      extends ConditionWithTweetLabelRule(
        Drop(Unspecified),
        And(
          NonAuthorViewer,
          TweetComposedBefore(TweetSafetyLabelType.GoreAndViolence.DeprecatedAt),
          LoggedOutOrViewerOptInFiltering
        ),
        TweetSafetyLabelType.GoreAndViolence
      )

  object SafeSearchUntrustedUrlTweetLabelRule
      extends NonAuthorViewerOptInFilteringWithTweetLabelRule(
        Drop(Unspecified),
        TweetSafetyLabelType.UntrustedUrl
      )

  object SafeSearchDownrankSpamReplyTweetLabelRule
      extends NonAuthorViewerOptInFilteringWithTweetLabelRule(
        Drop(Unspecified),
        TweetSafetyLabelType.DownrankSpamReply
      ) {
    override def enabled: Seq[RuleParam[Boolean]] =
      Seq(EnableDownrankSpamReplySectioningRuleParam)
  }

  object SafeSearchDownrankSpamReplyAuthorLabelRule
      extends ViewerOptInFilteringOnSearchUserLabelRule(
        Drop(Unspecified),
        UserLabelValue.DownrankSpamReply
      ) {
    override def enabled: Seq[RuleParam[Boolean]] =
      Seq(EnableDownrankSpamReplySectioningRuleParam)
  }

  object SafeSearchAutomationNonFollowerTweetLabelRule
      extends NonFollowerViewerOptInFilteringWithTweetLabelRule(
        Drop(Unspecified),
        TweetSafetyLabelType.Automation
      )

  object SafeSearchDuplicateMentionNonFollowerTweetLabelRule
      extends NonFollowerViewerOptInFilteringWithTweetLabelRule(
        Drop(Unspecified),
        TweetSafetyLabelType.DuplicateMention
      )

  object SafeSearchBystanderAbusiveTweetLabelRule
      extends NonAuthorViewerOptInFilteringWithTweetLabelRule(
        Drop(Unspecified),
        TweetSafetyLabelType.BystanderAbusive
      )
}

case object UnsafeSearchTweetRules {
}

case object SafeSearchUserRules {

  object SafeSearchAbusiveUserLabelRule
      extends ViewerOptInFilteringOnSearchUserLabelRule(
        Drop(Unspecified),
        UserLabelValue.Abusive
      )

  object SafeSearchAbusiveHighRecallUserLabelRule
      extends ViewerOptInFilteringOnSearchUserLabelRule(
        Drop(Unspecified),
        UserLabelValue.AbusiveHighRecall,
        LoggedOutOrViewerNotFollowingAuthor
      )

  object SafeSearchCompromisedUserLabelRule
      extends ViewerOptInFilteringOnSearchUserLabelRule(
        Drop(Unspecified),
        UserLabelValue.Compromised
      )

  object SafeSearchDuplicateContentUserLabelRule
      extends ViewerOptInFilteringOnSearchUserLabelRule(
        Drop(Unspecified),
        UserLabelValue.DuplicateContent
      )

  object SafeSearchLowQualityUserLabelRule
      extends ViewerOptInFilteringOnSearchUserLabelRule(
        Drop(Unspecified),
        UserLabelValue.LowQuality
      )

  object SafeSearchReadOnlyUserLabelRule
      extends ViewerOptInFilteringOnSearchUserLabelRule(
        Drop(Unspecified),
        UserLabelValue.ReadOnly
      )

  object SafeSearchSpamHighRecallUserLabelRule
      extends ViewerOptInFilteringOnSearchUserLabelRule(
        Drop(Unspecified),
        UserLabelValue.SpamHighRecall
      )

  object SafeSearchSearchBlacklistUserLabelRule
      extends ViewerOptInFilteringOnSearchUserLabelRule(
        Drop(Unspecified),
        UserLabelValue.SearchBlacklist
      )

  object SafeSearchDoNotAmplifyNonFollowersUserLabelRule
      extends ViewerOptInFilteringOnSearchUserLabelRule(
        Drop(Unspecified),
        UserLabelValue.DoNotAmplify,
        prerequisiteCondition = Not(ViewerDoesFollowAuthor)
      )

  object SafeSearchNotGraduatedNonFollowersUserLabelRule
      extends ViewerOptInFilteringOnSearchUserLabelRule(
        Drop(Unspecified),
        UserLabelValue.NotGraduated,
        prerequisiteCondition = Not(ViewerDoesFollowAuthor)
      ) {
    override def enabled: Seq[RuleParam[Boolean]] =
      Seq(EnableNotGraduatedSearchDropRuleParam)

    override def holdbacks: Seq[RuleParam[Boolean]] =
      Seq(NotGraduatedUserLabelRuleHoldbackExperimentParam)

  }
}
