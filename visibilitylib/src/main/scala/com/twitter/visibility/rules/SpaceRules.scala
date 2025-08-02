package com.twitter.visibility.rules

import com.twitter.visibility.configapi.params.FSRuleParams.HighTotwittericityModelScoreSpaceThresholdParam
import com.twitter.visibility.configapi.params.RuleParam
import com.twitter.visibility.configapi.params.RuleParams.EnableMutedKeywordFilteringSpaceTitleNotificationsRuleParam
import com.twitter.visibility.models.SpaceSafetyLabelType.CoordinatedHarmfulActivityHighRecall
import com.twitter.visibility.models.SpaceSafetyLabelType.DoNotAmplify
import com.twitter.visibility.models.SpaceSafetyLabelType.MisleadingHighRecall
import com.twitter.visibility.models.SpaceSafetyLabelType.NsfwHighPrecision
import com.twitter.visibility.models.SpaceSafetyLabelType.NsfwHighRecall
import com.twitter.visibility.models.SpaceSafetyLabelType.UntrustedUrl
import com.twitter.visibility.models.UserLabelValue.Abusive
import com.twitter.visibility.models.UserLabelValue.BlinkWorst
import com.twitter.visibility.models.UserLabelValue.DelayedRemediation
import com.twitter.visibility.models.UserLabelValue.NsfwAvatarImage
import com.twitter.visibility.models.UserLabelValue.NsfwBannerImage
import com.twitter.visibility.models.UserLabelValue.NsfwNearPerfect
import com.twitter.visibility.models.SpaceSafetyLabelType
import com.twitter.visibility.models.SpaceSafetyLabelType.HatefulHighRecall
import com.twitter.visibility.models.SpaceSafetyLabelType.HighTotwittericityModelScore
import com.twitter.visibility.models.SpaceSafetyLabelType.ViolenceHighRecall
import com.twitter.visibility.models.UserLabelValue
import com.twitter.visibility.rules.Condition._
import com.twitter.visibility.rules.Reason.Nsfw
import com.twitter.visibility.rules.Reason.Unspecified

object SpaceRules {

  abstract class SpaceHasLabelRule(
    action: Action,
    safetyLabelType: SpaceSafetyLabelType)
      etwittertends RuleWithConstantAction(action, And(SpaceHasLabel(safetyLabelType), NonAuthorViewer))

  abstract class SpaceHasLabelAndNonFollowerRule(
    action: Action,
    safetyLabelType: SpaceSafetyLabelType)
      etwittertends RuleWithConstantAction(
        action,
        And(SpaceHasLabel(safetyLabelType), LoggedOutOrViewerNotFollowingAuthor))

  abstract class AnySpaceHostOrAdminHasLabelRule(
    action: Action,
    userLabel: UserLabelValue)
      etwittertends WhenAuthorUserLabelPresentRule(action, userLabel)

  abstract class AnySpaceHostOrAdminHasLabelAndNonFollowerRule(
    action: Action,
    userLabel: UserLabelValue)
      etwittertends ConditionWithUserLabelRule(action, LoggedOutOrViewerNotFollowingAuthor, userLabel)


  object SpaceDoNotAmplifyAllUsersDropRule
      etwittertends SpaceHasLabelRule(
        Drop(Unspecified),
        DoNotAmplify,
      )

  object SpaceDoNotAmplifyNonFollowerDropRule
      etwittertends SpaceHasLabelAndNonFollowerRule(
        Drop(Unspecified),
        DoNotAmplify,
      )

  object SpaceCoordHarmfulActivityHighRecallAllUsersDropRule
      etwittertends SpaceHasLabelRule(
        Drop(Unspecified),
        CoordinatedHarmfulActivityHighRecall,
      )

  object SpaceCoordHarmfulActivityHighRecallNonFollowerDropRule
      etwittertends SpaceHasLabelAndNonFollowerRule(
        Drop(Unspecified),
        CoordinatedHarmfulActivityHighRecall,
      )

  object SpaceUntrustedUrlAllUsersDropRule
      etwittertends SpaceHasLabelRule(
        Drop(Unspecified),
        UntrustedUrl,
      )

  object SpaceUntrustedUrlNonFollowerDropRule
      etwittertends SpaceHasLabelAndNonFollowerRule(
        Drop(Unspecified),
        UntrustedUrl,
      )

  object SpaceMisleadingHighRecallNonFollowerDropRule
      etwittertends SpaceHasLabelAndNonFollowerRule(
        Drop(Unspecified),
        MisleadingHighRecall,
      )

  object SpaceNsfwHighPrecisionAllUsersInterstitialRule
      etwittertends SpaceHasLabelRule(
        Interstitial(Nsfw),
        NsfwHighPrecision,
      )

  object SpaceNsfwHighPrecisionAllUsersDropRule
      etwittertends SpaceHasLabelRule(
        Drop(Nsfw),
        NsfwHighPrecision,
      )

  object SpaceNsfwHighPrecisionNonFollowerDropRule
      etwittertends SpaceHasLabelAndNonFollowerRule(
        Drop(Nsfw),
        NsfwHighPrecision,
      )

  object SpaceNsfwHighPrecisionSafeSearchNonFollowerDropRule
      etwittertends RuleWithConstantAction(
        Drop(Nsfw),
        And(
          SpaceHasLabel(NsfwHighPrecision),
          NonAuthorViewer,
          LoggedOutOrViewerOptInFiltering,
          Not(ViewerDoesFollowAuthor),
        ),
      )

  object SpaceNsfwHighRecallAllUsersDropRule
      etwittertends SpaceHasLabelRule(
        Drop(Nsfw),
        NsfwHighRecall,
      )

  object SpaceNsfwHighRecallNonFollowerDropRule
      etwittertends SpaceHasLabelAndNonFollowerRule(
        Drop(Nsfw),
        NsfwHighRecall,
      )

  object SpaceNsfwHighRecallSafeSearchNonFollowerDropRule
      etwittertends RuleWithConstantAction(
        Drop(Nsfw),
        And(
          SpaceHasLabel(NsfwHighRecall),
          NonAuthorViewer,
          LoggedOutOrViewerOptInFiltering,
          Not(ViewerDoesFollowAuthor),
        ),
      )

  object SpaceHatefulHighRecallAllUsersDropRule
      etwittertends SpaceHasLabelRule(
        Drop(Unspecified),
        HatefulHighRecall,
      )

  object SpaceViolenceHighRecallAllUsersDropRule
      etwittertends SpaceHasLabelRule(
        Drop(Unspecified),
        ViolenceHighRecall,
      )

  object SpaceHighTotwittericityScoreNonFollowerDropRule
      etwittertends RuleWithConstantAction(
        Drop(Unspecified),
        And(
          SpaceHasLabelWithScoreAboveThresholdWithParam(
            HighTotwittericityModelScore,
            HighTotwittericityModelScoreSpaceThresholdParam
          ),
          NonAuthorViewer,
          LoggedOutOrViewerNotFollowingAuthor,
        )
      )
      with EtwitterperimentalRule


  object ViewerHasMatchingMutedKeywordInSpaceTitleForNotificationsRule
      etwittertends OnlyWhenNotAuthorViewerRule(
        Drop(Reason.MutedKeyword),
        Condition.ViewerHasMatchingKeywordInSpaceTitleForNotifications
      ) {
    override def enabled: Seq[RuleParam[Boolean]] = Seq(
      EnableMutedKeywordFilteringSpaceTitleNotificationsRuleParam)

  }


  object UserAbusiveNonFollowerDropRule
      etwittertends AnySpaceHostOrAdminHasLabelAndNonFollowerRule(
        Drop(Unspecified),
        Abusive
      )

  object UserBlinkWorstAllUsersDropRule
      etwittertends AnySpaceHostOrAdminHasLabelRule(
        Drop(Unspecified),
        BlinkWorst
      )

  object UserNsfwNearPerfectNonFollowerDropRule
      etwittertends AnySpaceHostOrAdminHasLabelAndNonFollowerRule(
        Drop(Nsfw),
        NsfwNearPerfect
      )

  object UserNsfwHighPrecisionNonFollowerDropRule
      etwittertends AnySpaceHostOrAdminHasLabelAndNonFollowerRule(
        Drop(Nsfw),
        UserLabelValue.NsfwHighPrecision
      )

  object UserNsfwAvatarImageNonFollowerDropRule
      etwittertends AnySpaceHostOrAdminHasLabelAndNonFollowerRule(
        Drop(Nsfw),
        NsfwAvatarImage
      )

  object UserNsfwBannerImageNonFollowerDropRule
      etwittertends AnySpaceHostOrAdminHasLabelAndNonFollowerRule(
        Drop(Nsfw),
        NsfwBannerImage
      )
}
