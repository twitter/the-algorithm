package com.twitter.visibility.rules

import com.twitter.visibility.configapi.params.FSRuleParams.HighToxicityModelScoreSpaceThresholdParam
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
import com.twitter.visibility.models.SpaceSafetyLabelType.HighToxicityModelScore
import com.twitter.visibility.models.SpaceSafetyLabelType.ViolenceHighRecall
import com.twitter.visibility.models.UserLabelValue
import com.twitter.visibility.rules.Condition._
import com.twitter.visibility.rules.Reason.Nsfw
import com.twitter.visibility.rules.Reason.Unspecified

object SpaceRules {

  abstract class SpaceHasLabelRule(
    action: Action,
    safetyLabelType: SpaceSafetyLabelType)
      extends RuleWithConstantAction(action, And(SpaceHasLabel(safetyLabelType), NonAuthorViewer))

  abstract class SpaceHasLabelAndNonFollowerRule(
    action: Action,
    safetyLabelType: SpaceSafetyLabelType)
      extends RuleWithConstantAction(
        action,
        And(SpaceHasLabel(safetyLabelType), LoggedOutOrViewerNotFollowingAuthor))

  abstract class AnySpaceHostOrAdminHasLabelRule(
    action: Action,
    userLabel: UserLabelValue)
      extends WhenAuthorUserLabelPresentRule(action, userLabel)

  abstract class AnySpaceHostOrAdminHasLabelAndNonFollowerRule(
    action: Action,
    userLabel: UserLabelValue)
      extends ConditionWithUserLabelRule(action, LoggedOutOrViewerNotFollowingAuthor, userLabel)


  object SpaceDoNotAmplifyAllUsersDropRule
      extends SpaceHasLabelRule(
        Drop(Unspecified),
        DoNotAmplify,
      )

  object SpaceDoNotAmplifyNonFollowerDropRule
      extends SpaceHasLabelAndNonFollowerRule(
        Drop(Unspecified),
        DoNotAmplify,
      )

  object SpaceCoordHarmfulActivityHighRecallAllUsersDropRule
      extends SpaceHasLabelRule(
        Drop(Unspecified),
        CoordinatedHarmfulActivityHighRecall,
      )

  object SpaceCoordHarmfulActivityHighRecallNonFollowerDropRule
      extends SpaceHasLabelAndNonFollowerRule(
        Drop(Unspecified),
        CoordinatedHarmfulActivityHighRecall,
      )

  object SpaceUntrustedUrlAllUsersDropRule
      extends SpaceHasLabelRule(
        Drop(Unspecified),
        UntrustedUrl,
      )

  object SpaceUntrustedUrlNonFollowerDropRule
      extends SpaceHasLabelAndNonFollowerRule(
        Drop(Unspecified),
        UntrustedUrl,
      )

  object SpaceMisleadingHighRecallNonFollowerDropRule
      extends SpaceHasLabelAndNonFollowerRule(
        Drop(Unspecified),
        MisleadingHighRecall,
      )

  object SpaceNsfwHighPrecisionAllUsersInterstitialRule
      extends SpaceHasLabelRule(
        Interstitial(Nsfw),
        NsfwHighPrecision,
      )

  object SpaceNsfwHighPrecisionAllUsersDropRule
      extends SpaceHasLabelRule(
        Drop(Nsfw),
        NsfwHighPrecision,
      )

  object SpaceNsfwHighPrecisionNonFollowerDropRule
      extends SpaceHasLabelAndNonFollowerRule(
        Drop(Nsfw),
        NsfwHighPrecision,
      )

  object SpaceNsfwHighPrecisionSafeSearchNonFollowerDropRule
      extends RuleWithConstantAction(
        Drop(Nsfw),
        And(
          SpaceHasLabel(NsfwHighPrecision),
          NonAuthorViewer,
          LoggedOutOrViewerOptInFiltering,
          Not(ViewerDoesFollowAuthor),
        ),
      )

  object SpaceNsfwHighRecallAllUsersDropRule
      extends SpaceHasLabelRule(
        Drop(Nsfw),
        NsfwHighRecall,
      )

  object SpaceNsfwHighRecallNonFollowerDropRule
      extends SpaceHasLabelAndNonFollowerRule(
        Drop(Nsfw),
        NsfwHighRecall,
      )

  object SpaceNsfwHighRecallSafeSearchNonFollowerDropRule
      extends RuleWithConstantAction(
        Drop(Nsfw),
        And(
          SpaceHasLabel(NsfwHighRecall),
          NonAuthorViewer,
          LoggedOutOrViewerOptInFiltering,
          Not(ViewerDoesFollowAuthor),
        ),
      )

  object SpaceHatefulHighRecallAllUsersDropRule
      extends SpaceHasLabelRule(
        Drop(Unspecified),
        HatefulHighRecall,
      )

  object SpaceViolenceHighRecallAllUsersDropRule
      extends SpaceHasLabelRule(
        Drop(Unspecified),
        ViolenceHighRecall,
      )

  object SpaceHighToxicityScoreNonFollowerDropRule
      extends RuleWithConstantAction(
        Drop(Unspecified),
        And(
          SpaceHasLabelWithScoreAboveThresholdWithParam(
            HighToxicityModelScore,
            HighToxicityModelScoreSpaceThresholdParam
          ),
          NonAuthorViewer,
          LoggedOutOrViewerNotFollowingAuthor,
        )
      )
      with ExperimentalRule


  object ViewerHasMatchingMutedKeywordInSpaceTitleForNotificationsRule
      extends OnlyWhenNotAuthorViewerRule(
        Drop(Reason.MutedKeyword),
        Condition.ViewerHasMatchingKeywordInSpaceTitleForNotifications
      ) {
    override def enabled: Seq[RuleParam[Boolean]] = Seq(
      EnableMutedKeywordFilteringSpaceTitleNotificationsRuleParam)

  }


  object UserAbusiveNonFollowerDropRule
      extends AnySpaceHostOrAdminHasLabelAndNonFollowerRule(
        Drop(Unspecified),
        Abusive
      )

  object UserBlinkWorstAllUsersDropRule
      extends AnySpaceHostOrAdminHasLabelRule(
        Drop(Unspecified),
        BlinkWorst
      )

  object UserNsfwNearPerfectNonFollowerDropRule
      extends AnySpaceHostOrAdminHasLabelAndNonFollowerRule(
        Drop(Nsfw),
        NsfwNearPerfect
      )

  object UserNsfwHighPrecisionNonFollowerDropRule
      extends AnySpaceHostOrAdminHasLabelAndNonFollowerRule(
        Drop(Nsfw),
        UserLabelValue.NsfwHighPrecision
      )

  object UserNsfwAvatarImageNonFollowerDropRule
      extends AnySpaceHostOrAdminHasLabelAndNonFollowerRule(
        Drop(Nsfw),
        NsfwAvatarImage
      )

  object UserNsfwBannerImageNonFollowerDropRule
      extends AnySpaceHostOrAdminHasLabelAndNonFollowerRule(
        Drop(Nsfw),
        NsfwBannerImage
      )
}
