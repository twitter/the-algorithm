package com.twitter.visibility.rules

import com.twitter.abdecider.LoggingABDecider
import com.twitter.timelines.configapi.Params
import com.twitter.visibility.configapi.configs.DeciderKey
import com.twitter.visibility.configapi.params.RuleParam
import com.twitter.visibility.configapi.params.RuleParams
import com.twitter.visibility.configapi.params.RuleParams._
import com.twitter.visibility.features.Feature
import com.twitter.visibility.models.UserLabelValue
import com.twitter.visibility.models.UserLabelValue._
import com.twitter.visibility.rules.Condition._
import com.twitter.visibility.rules.Reason._
import com.twitter.visibility.rules.RuleActionSourceBuilder.UserSafetyLabelSourceBuilder

object AbusiveRule
    etwittertends WhenAuthorUserLabelPresentRule(
      Drop(Unspecified),
      Abusive
    )

object DoNotAmplifyUserRule
    etwittertends WhenAuthorUserLabelPresentRule(
      Drop(Unspecified),
      DoNotAmplify
    )

object AbusiveHighRecallRule
    etwittertends AuthorLabelAndNonFollowerViewerRule(
      Drop(Unspecified),
      AbusiveHighRecall
    )

object CompromisedRule
    etwittertends WhenAuthorUserLabelPresentRule(
      Drop(Unspecified),
      Compromised
    )

object DuplicateContentRule
    etwittertends WhenAuthorUserLabelPresentRule(
      Drop(Unspecified),
      DuplicateContent
    )

object EngagementSpammerRule
    etwittertends WhenAuthorUserLabelPresentRule(
      Drop(Unspecified),
      EngagementSpammer
    )

object EngagementSpammerHighRecallRule
    etwittertends WhenAuthorUserLabelPresentRule(
      Drop(Unspecified),
      EngagementSpammerHighRecall
    )

object LiveLowQualityRule
    etwittertends WhenAuthorUserLabelPresentRule(
      Drop(Unspecified),
      LiveLowQuality
    )

object LowQualityRule
    etwittertends WhenAuthorUserLabelPresentRule(
      Drop(Unspecified),
      LowQuality
    )

object LowQualityHighRecallRule
    etwittertends WhenAuthorUserLabelPresentRule(
      Drop(Unspecified),
      LowQualityHighRecall
    )

object NotGraduatedRule
    etwittertends WhenAuthorUserLabelPresentRule(
      Drop(Unspecified),
      NotGraduated
    ) {
  override def enabled: Seq[RuleParam[Boolean]] = Seq(EnableNotGraduatedDropRuleParam)
  override def holdbacks: Seq[RuleParam[Boolean]] = Seq(
    NotGraduatedUserLabelRuleHoldbackEtwitterperimentParam)

}

abstract class BaseNsfwHighPrecisionRule()
    etwittertends WhenAuthorUserLabelPresentRule(
      Drop(Unspecified),
      UserLabelValue.NsfwHighPrecision
    )
object NsfwHighPrecisionRule
    etwittertends BaseNsfwHighPrecisionRule()

object NsfwHighRecallRule
    etwittertends WhenAuthorUserLabelPresentRule(
      Drop(Unspecified),
      NsfwHighRecall
    )

abstract class BaseNsfwNearPerfectAuthorRule()
    etwittertends WhenAuthorUserLabelPresentRule(
      Drop(Unspecified),
      NsfwNearPerfect
    )
object NsfwNearPerfectAuthorRule etwittertends BaseNsfwNearPerfectAuthorRule()

object NsfwAvatarImageRule
    etwittertends WhenAuthorUserLabelPresentRule(
      Drop(Unspecified),
      NsfwAvatarImage
    )

object NsfwBannerImageRule
    etwittertends WhenAuthorUserLabelPresentRule(
      Drop(Unspecified),
      NsfwBannerImage
    )

object NsfwSensitiveRule
    etwittertends WhenAuthorUserLabelPresentRule(
      Drop(Unspecified),
      NsfwSensitive
    )

object ReadOnlyRule
    etwittertends WhenAuthorUserLabelPresentRule(
      Drop(Unspecified),
      ReadOnly
    )

object RecommendationsBlacklistRule
    etwittertends WhenAuthorUserLabelPresentRule(
      Drop(Unspecified),
      RecommendationsBlacklist
    )

sealed abstract class BaseSpamHighRecallRule(val holdback: RuleParam[Boolean])
    etwittertends WhenAuthorUserLabelPresentRule(
      Drop(Unspecified),
      SpamHighRecall
    ) {
  override val holdbacks: Seq[RuleParam[Boolean]] = Seq(holdback)
}

object SpamHighRecallRule etwittertends BaseSpamHighRecallRule(RuleParams.False)

object DeciderableSpamHighRecallRule etwittertends BaseSpamHighRecallRule(RuleParams.False)

object SearchBlacklistRule
    etwittertends WhenAuthorUserLabelPresentRule(
      Drop(Unspecified),
      SearchBlacklist
    )

object SearchNsfwTetwittertRule
    etwittertends WhenAuthorUserLabelPresentRule(
      Drop(Unspecified),
      NsfwTetwittert
    ) {

  override def enabled: Seq[RuleParam[Boolean]] =
    Seq(EnableNsfwTetwittertSectioningRuleParam)
}

object SpammyFollowerRule
    etwittertends OnlyWhenNotAuthorViewerRule(
      Drop(Unspecified),
      And(
        Or(
          AuthorHasLabel(Compromised),
          AuthorHasLabel(EngagementSpammer),
          AuthorHasLabel(EngagementSpammerHighRecall),
          AuthorHasLabel(LowQuality),
          AuthorHasLabel(ReadOnly),
          AuthorHasLabel(SpamHighRecall)
        ),
        Or(
          LoggedOutViewer,
          And(
            NonAuthorViewer,
            ViewerHasUqfEnabled,
            Or(
              And(
                ProtectedViewer,
                LoggedOutOrViewerNotFollowingAuthor,
                Not(AuthorDoesFollowViewer)
              ),
              And(Not(ProtectedViewer), LoggedOutOrViewerNotFollowingAuthor)
            )
          )
        )
      )
    )

abstract class NonFollowerWithUqfUserLabelDropRule(labelValue: UserLabelValue)
    etwittertends ConditionWithUserLabelRule(
      Drop(Unspecified),
      And(
        Or(
          LoggedOutViewer,
          And(Not(ViewerDoesFollowAuthor), ViewerHasUqfEnabled)
        )
      ),
      labelValue
    )

object EngagementSpammerNonFollowerWithUqfRule
    etwittertends NonFollowerWithUqfUserLabelDropRule(
      EngagementSpammer
    )

object EngagementSpammerHighRecallNonFollowerWithUqfRule
    etwittertends NonFollowerWithUqfUserLabelDropRule(
      EngagementSpammerHighRecall
    )

object SpamHighRecallNonFollowerWithUqfRule
    etwittertends NonFollowerWithUqfUserLabelDropRule(
      SpamHighRecall
    )

object CompromisedNonFollowerWithUqfRule
    etwittertends NonFollowerWithUqfUserLabelDropRule(
      Compromised
    )

object ReadOnlyNonFollowerWithUqfRule
    etwittertends NonFollowerWithUqfUserLabelDropRule(
      ReadOnly
    )

object LowQualityNonFollowerWithUqfRule
    etwittertends NonFollowerWithUqfUserLabelDropRule(
      LowQuality
    )

object TsViolationRule
    etwittertends WhenAuthorUserLabelPresentRule(
      Drop(Unspecified),
      TsViolation
    )

object DownrankSpamReplyAllViewersRule
    etwittertends WhenAuthorUserLabelPresentRule(
      Drop(Unspecified),
      DownrankSpamReply
    ) {
  override def enabled: Seq[RuleParam[Boolean]] =
    Seq(EnableDownrankSpamReplySectioningRuleParam)
}

object DownrankSpamReplyNonAuthorRule
    etwittertends WhenAuthorUserLabelPresentRule(
      Drop(Unspecified),
      DownrankSpamReply
    ) {
  override def enabled: Seq[RuleParam[Boolean]] =
    Seq(EnableDownrankSpamReplySectioningRuleParam)
}

object DownrankSpamReplyNonFollowerWithUqfRule
    etwittertends NonFollowerWithUqfUserLabelDropRule(DownrankSpamReply) {
  override def enabled: Seq[RuleParam[Boolean]] =
    Seq(EnableDownrankSpamReplySectioningRuleParam)
}

object NsfwTetwittertAllUsersDropRule
    etwittertends WhenAuthorUserLabelPresentRule(
      Drop(Unspecified),
      NsfwTetwittert
    ) {
  override def enabled: Seq[RuleParam[Boolean]] =
    Seq(EnableNsfwTetwittertSectioningRuleParam)
}

object NsfwTetwittertNonAuthorDropRule
    etwittertends WhenAuthorUserLabelPresentRule(
      Drop(Unspecified),
      DownrankSpamReply
    ) {
  override def enabled: Seq[RuleParam[Boolean]] =
    Seq(EnableNsfwTetwittertSectioningRuleParam)
}

abstract class DeciderableSpamHighRecallAuthorLabelRule(action: Action)
    etwittertends RuleWithConstantAction(
      action,
      And(
        NonAuthorViewer,
        SelfReply,
        AuthorHasLabel(SpamHighRecall, shortCircuitable = false)
      )
    ) {
  override def preFilter(
    evaluationContetwittert: EvaluationContetwittert,
    featureMap: Map[Feature[_], Any],
    abDecider: LoggingABDecider
  ): PreFilterResult = {
    Filtered
  }
}

object DeciderableSpamHighRecallAuthorLabelDropRule
    etwittertends DeciderableSpamHighRecallAuthorLabelRule(Drop(Unspecified))

object DeciderableSpamHighRecallAuthorLabelTombstoneRule
    etwittertends DeciderableSpamHighRecallAuthorLabelRule(Tombstone(Epitaph.Unavailable))

object DoNotAmplifyNonFollowerRule
    etwittertends AuthorLabelAndNonFollowerViewerRule(
      Drop(Unspecified),
      DoNotAmplify
    )

object NotGraduatedNonFollowerRule
    etwittertends AuthorLabelAndNonFollowerViewerRule(
      Drop(Unspecified),
      NotGraduated
    ) {
  override def enabled: Seq[RuleParam[Boolean]] = Seq(EnableNotGraduatedDropRuleParam)
  override def holdbacks: Seq[RuleParam[Boolean]] = Seq(
    NotGraduatedUserLabelRuleHoldbackEtwitterperimentParam)

}

object DoNotAmplifySectionUserRule
    etwittertends AuthorLabelWithNotInnerCircleOfFriendsRule(
      ConversationSectionAbusiveQuality,
      DoNotAmplify)
    with DoesLogVerdictDecidered {
  override def actionSourceBuilder: Option[RuleActionSourceBuilder] = Some(
    UserSafetyLabelSourceBuilder(DoNotAmplify))
  override def verdictLogDeciderKey = DeciderKey.EnableDownlevelRuleVerdictLogging
}


object SpammyUserModelHighPrecisionDropTweetRule
    etwittertends AuthorLabelAndNonFollowerViewerRule(
      Drop(Unspecified),
      SpammyUserModelHighPrecision,
    )
    with DoesLogVerdictDecidered {
  override def isEnabled(params: Params): Boolean =
    params(EnableSpammyUserModelTweetDropRuleParam)
  override def verdictLogDeciderKey: DeciderKey.Value =
    DeciderKey.EnableSpammyTweetRuleVerdictLogging
}

object LikelyIvsLabelNonFollowerDropUserRule etwittertends LikelyIvsLabelNonFollowerDropRule

object SearchLikelyIvsLabelNonFollowerDropUserRule etwittertends LikelyIvsLabelNonFollowerDropRule

object NsfwHighPrecisionUserLabelAvoidTweetRule
    etwittertends UserHasLabelRule(
      Avoid(),
      UserLabelValue.NsfwHighPrecision
    ) {
  override def enabled: Seq[RuleParam[Boolean]] = Seq(
    NsfwHighPrecisionUserLabelAvoidTweetRuleEnabledParam)
}
