package com.twitter.visibility.rules

import com.twitter.visibility.common.actions.LimitedEngagementReason
import com.twitter.visibility.configapi.params.FSRuleParams.AdAvoidanceHighTotwittericityModelScoreThresholdParam
import com.twitter.visibility.configapi.params.FSRuleParams.AdAvoidanceReportedTweetModelScoreThresholdParam
import com.twitter.visibility.configapi.params.FSRuleParams.CommunityTweetCommunityUnavailableLimitedActionsRulesEnabledParam
import com.twitter.visibility.configapi.params.FSRuleParams.CommunityTweetDropProtectedRuleEnabledParam
import com.twitter.visibility.configapi.params.FSRuleParams.CommunityTweetDropRuleEnabledParam
import com.twitter.visibility.configapi.params.FSRuleParams.CommunityTweetLimitedActionsRulesEnabledParam
import com.twitter.visibility.configapi.params.FSRuleParams.CommunityTweetMemberRemovedLimitedActionsRulesEnabledParam
import com.twitter.visibility.configapi.params.FSRuleParams.CommunityTweetNonMemberLimitedActionsRuleEnabledParam
import com.twitter.visibility.configapi.params.FSRuleParams.StaleTweetLimitedActionsRulesEnabledParam
import com.twitter.visibility.configapi.params.FSRuleParams.TrustedFriendsTweetLimitedEngagementsRuleEnabledParam
import com.twitter.visibility.configapi.params.RuleParam
import com.twitter.visibility.configapi.params.RuleParams
import com.twitter.visibility.configapi.params.RuleParams._
import com.twitter.visibility.features.{TweetDeleteReason => FeatureTweetDeleteReason}
import com.twitter.visibility.models.TweetDeleteReason
import com.twitter.visibility.models.TweetSafetyLabelType
import com.twitter.visibility.rules.Condition.ViewerIsEtwitterclusiveTweetAuthor
import com.twitter.visibility.rules.Condition._
import com.twitter.visibility.rules.Reason.CommunityTweetAuthorRemoved
import com.twitter.visibility.rules.Reason.CommunityTweetHidden
import com.twitter.visibility.rules.Reason.Nsfw
import com.twitter.visibility.rules.Reason.StaleTweet
import com.twitter.visibility.rules.Reason.Unspecified
import com.twitter.visibility.rules.RuleActionSourceBuilder.TweetSafetyLabelSourceBuilder

abstract class TweetHasLabelRule(action: Action, tweetSafetyLabelType: TweetSafetyLabelType)
    etwittertends RuleWithConstantAction(action, TweetHasLabel(tweetSafetyLabelType)) {
  override def actionSourceBuilder: Option[RuleActionSourceBuilder] = Some(
    TweetSafetyLabelSourceBuilder(tweetSafetyLabelType))
}

abstract class ConditionWithTweetLabelRule(
  action: Action,
  condition: Condition,
  tweetSafetyLabelType: TweetSafetyLabelType)
    etwittertends RuleWithConstantAction(action, And(TweetHasLabel(tweetSafetyLabelType), condition)) {
  override def actionSourceBuilder: Option[RuleActionSourceBuilder] = Some(
    TweetSafetyLabelSourceBuilder(tweetSafetyLabelType))
}

abstract class NonAuthorWithTweetLabelRule(
  action: Action,
  tweetSafetyLabelType: TweetSafetyLabelType)
    etwittertends ConditionWithTweetLabelRule(action, NonAuthorViewer, tweetSafetyLabelType) {
  override def actionSourceBuilder: Option[RuleActionSourceBuilder] = Some(
    TweetSafetyLabelSourceBuilder(tweetSafetyLabelType))
}

abstract class NonFollowerWithTweetLabelRule(
  action: Action,
  tweetSafetyLabelType: TweetSafetyLabelType)
    etwittertends ConditionWithTweetLabelRule(
      action,
      LoggedOutOrViewerNotFollowingAuthor,
      tweetSafetyLabelType
    )

abstract class NonAuthorAndNonFollowerWithTweetLabelRule(
  action: Action,
  tweetSafetyLabelType: TweetSafetyLabelType)
    etwittertends ConditionWithTweetLabelRule(
      action,
      And(NonAuthorViewer, LoggedOutOrViewerNotFollowingAuthor),
      tweetSafetyLabelType
    )

abstract class NonFollowerWithUqfTweetLabelRule(
  action: Action,
  tweetSafetyLabelType: TweetSafetyLabelType)
    etwittertends ConditionWithTweetLabelRule(
      action,
      Or(
        LoggedOutViewer,
        And(
          NonAuthorViewer,
          Not(ViewerDoesFollowAuthor),
          ViewerHasUqfEnabled
        )
      ),
      tweetSafetyLabelType
    )

abstract class ViewerWithUqfTweetLabelRule(action: Action, labelValue: TweetSafetyLabelType)
    etwittertends ConditionWithTweetLabelRule(action, ViewerHasUqfEnabled, labelValue)

case object ConversationControlRules {

  abstract class ConversationControlBaseRule(condition: Condition)
      etwittertends RuleWithConstantAction(
        LimitedEngagements(LimitedEngagementReason.ConversationControl),
        condition) {
    override def enabled: Seq[RuleParam[Boolean]] = Seq(TweetConversationControlEnabledParam)
  }

  object LimitRepliesCommunityConversationRule
      etwittertends ConversationControlBaseRule(
        And(
          TweetIsCommunityConversation,
          Not(
            Or(
              LoggedOutViewer,
              Retweet,
              ViewerIsTweetConversationRootAuthor,
              ViewerIsInvitedToTweetConversation,
              ConversationRootAuthorDoesFollowViewer
            ))
        )
      )

  object LimitRepliesFollowersConversationRule
      etwittertends ConversationControlBaseRule(
        And(
          TweetIsFollowersConversation,
          Not(
            Or(
              LoggedOutViewer,
              Retweet,
              ViewerIsTweetConversationRootAuthor,
              ViewerIsInvitedToTweetConversation,
              ViewerDoesFollowConversationRootAuthor
            ))
        )
      )

  object LimitRepliesByInvitationConversationRule
      etwittertends ConversationControlBaseRule(
        And(
          TweetIsByInvitationConversation,
          Not(
            Or(
              LoggedOutViewer,
              Retweet,
              ViewerIsTweetConversationRootAuthor,
              ViewerIsInvitedToTweetConversation
            ))
        )
      )

}

abstract class NonAuthorViewerOptInFilteringWithTweetLabelRule(
  action: Action,
  tweetSafetyLabelType: TweetSafetyLabelType)
    etwittertends ConditionWithTweetLabelRule(
      action,
      And(NonAuthorViewer, LoggedOutOrViewerOptInFiltering),
      tweetSafetyLabelType)

abstract class NonFollowerViewerOptInFilteringWithTweetLabelRule(
  action: Action,
  tweetSafetyLabelType: TweetSafetyLabelType)
    etwittertends ConditionWithTweetLabelRule(
      action,
      And(LoggedOutOrViewerNotFollowingAuthor, LoggedOutOrViewerOptInFiltering),
      tweetSafetyLabelType
    )

object TweetNsfwUserDropRule etwittertends RuleWithConstantAction(Drop(Nsfw), TweetHasNsfwUserAuthor)
object TweetNsfwAdminDropRule etwittertends RuleWithConstantAction(Drop(Nsfw), TweetHasNsfwAdminAuthor)

object NullcastedTweetRule
    etwittertends RuleWithConstantAction(
      Drop(Unspecified),
      And(Nullcast, Not(Retweet), Not(IsQuotedInnerTweet), Not(TweetIsCommunityTweet)))

object MutedRetweetsRule
    etwittertends RuleWithConstantAction(Drop(Unspecified), And(Retweet, ViewerMutesRetweetsFromAuthor))

abstract class FilterCommunityTweetsRule(override val action: Action)
    etwittertends RuleWithConstantAction(action, TweetIsCommunityTweet) {
  override def enabled: Seq[RuleParam[Boolean]] = Seq(CommunityTweetDropRuleEnabledParam)
}

object DropCommunityTweetsRule etwittertends FilterCommunityTweetsRule(Drop(CommunityTweetHidden))

object TombstoneCommunityTweetsRule
    etwittertends FilterCommunityTweetsRule(Tombstone(Epitaph.Unavailable))

abstract class FilterCommunityTweetCommunityNotVisibleRule(override val action: Action)
    etwittertends RuleWithConstantAction(
      action,
      And(
        NonAuthorViewer,
        TweetIsCommunityTweet,
        Not(CommunityTweetCommunityVisible),
      )) {
  override def enabled: Seq[RuleParam[Boolean]] =
    Seq(DropCommunityTweetWithUndefinedCommunityRuleEnabledParam)
}

object DropCommunityTweetCommunityNotVisibleRule
    etwittertends FilterCommunityTweetCommunityNotVisibleRule(Drop(CommunityTweetHidden))

object TombstoneCommunityTweetCommunityNotVisibleRule
    etwittertends FilterCommunityTweetCommunityNotVisibleRule(Tombstone(Epitaph.Unavailable))

abstract class FilterAllCommunityTweetsRule(override val action: Action)
    etwittertends RuleWithConstantAction(action, TweetIsCommunityTweet) {
  override def enabled: Seq[RuleParam[Boolean]] = Seq(CommunityTweetsEnabledParam)
}

object DropAllCommunityTweetsRule etwittertends FilterAllCommunityTweetsRule(Drop(Unspecified))

object TombstoneAllCommunityTweetsRule
    etwittertends FilterAllCommunityTweetsRule(Tombstone(Epitaph.Unavailable))

object DropOuterCommunityTweetsRule
    etwittertends RuleWithConstantAction(
      Drop(Unspecified),
      And(TweetIsCommunityTweet, Not(IsQuotedInnerTweet)))

object DropAllHiddenCommunityTweetsRule
    etwittertends RuleWithConstantAction(
      Drop(Unspecified),
      And(TweetIsCommunityTweet, CommunityTweetIsHidden))

abstract class FilterHiddenCommunityTweetsRule(override val action: Action)
    etwittertends RuleWithConstantAction(
      action,
      And(
        NonAuthorViewer,
        TweetIsCommunityTweet,
        CommunityTweetIsHidden,
        Not(ViewerIsCommunityModerator)
      ))

object DropHiddenCommunityTweetsRule
    etwittertends FilterHiddenCommunityTweetsRule(Drop(CommunityTweetHidden))

object TombstoneHiddenCommunityTweetsRule
    etwittertends FilterHiddenCommunityTweetsRule(Tombstone(Epitaph.CommunityTweetHidden))

object DropAllAuthorRemovedCommunityTweetsRule
    etwittertends RuleWithConstantAction(
      Drop(Unspecified),
      And(TweetIsCommunityTweet, CommunityTweetAuthorIsRemoved))

abstract class FilterAuthorRemovedCommunityTweetsRule(override val action: Action)
    etwittertends RuleWithConstantAction(
      action,
      And(
        NonAuthorViewer,
        TweetIsCommunityTweet,
        CommunityTweetAuthorIsRemoved,
        Not(ViewerIsCommunityModerator)
      ))

object DropAuthorRemovedCommunityTweetsRule
    etwittertends FilterAuthorRemovedCommunityTweetsRule(Drop(CommunityTweetAuthorRemoved))

object TombstoneAuthorRemovedCommunityTweetsRule
    etwittertends FilterAuthorRemovedCommunityTweetsRule(Tombstone(Epitaph.Unavailable))

abstract class FilterProtectedCommunityTweetsRule(override val action: Action)
    etwittertends RuleWithConstantAction(
      action,
      And(TweetIsCommunityTweet, ProtectedAuthor, NonAuthorViewer)) {
  override def enabled: Seq[RuleParam[Boolean]] = Seq(CommunityTweetDropProtectedRuleEnabledParam)
}

object DropProtectedCommunityTweetsRule
    etwittertends FilterProtectedCommunityTweetsRule(Drop(CommunityTweetHidden))

object TombstoneProtectedCommunityTweetsRule
    etwittertends FilterProtectedCommunityTweetsRule(Tombstone(Epitaph.Unavailable))

abstract class CommunityTweetCommunityUnavailableLimitedActionsRule(
  reason: LimitedEngagementReason,
  condition: CommunityTweetCommunityUnavailable,
) etwittertends RuleWithConstantAction(
      LimitedEngagements(reason),
      And(
        Not(NonAuthorViewer),
        TweetIsCommunityTweet,
        condition,
      )
    ) {
  override def enabled: Seq[RuleParam[Boolean]] = Seq(
    CommunityTweetCommunityUnavailableLimitedActionsRulesEnabledParam)
}

object CommunityTweetCommunityNotFoundLimitedActionsRule
    etwittertends CommunityTweetCommunityUnavailableLimitedActionsRule(
      LimitedEngagementReason.CommunityTweetCommunityNotFound,
      CommunityTweetCommunityNotFound,
    )

object CommunityTweetCommunityDeletedLimitedActionsRule
    etwittertends CommunityTweetCommunityUnavailableLimitedActionsRule(
      LimitedEngagementReason.CommunityTweetCommunityDeleted,
      CommunityTweetCommunityDeleted,
    )

object CommunityTweetCommunitySuspendedLimitedActionsRule
    etwittertends CommunityTweetCommunityUnavailableLimitedActionsRule(
      LimitedEngagementReason.CommunityTweetCommunitySuspended,
      CommunityTweetCommunitySuspended,
    )

abstract class CommunityTweetModeratedLimitedActionsRule(
  reason: LimitedEngagementReason,
  condition: CommunityTweetIsModerated,
  enabledParam: RuleParam[Boolean],
) etwittertends RuleWithConstantAction(
      LimitedEngagements(reason),
      And(
        TweetIsCommunityTweet,
        condition,
        Or(
          Not(NonAuthorViewer),
          ViewerIsCommunityModerator,
        )
      )) {
  override def enabled: Seq[RuleParam[Boolean]] = Seq(enabledParam)
}

object CommunityTweetMemberRemovedLimitedActionsRule
    etwittertends CommunityTweetModeratedLimitedActionsRule(
      LimitedEngagementReason.CommunityTweetMemberRemoved,
      CommunityTweetAuthorIsRemoved,
      CommunityTweetMemberRemovedLimitedActionsRulesEnabledParam,
    )

object CommunityTweetHiddenLimitedActionsRule
    etwittertends CommunityTweetModeratedLimitedActionsRule(
      LimitedEngagementReason.CommunityTweetHidden,
      CommunityTweetIsHidden,
      CommunityTweetLimitedActionsRulesEnabledParam,
    )

abstract class CommunityTweetLimitedActionsRule(
  reason: LimitedEngagementReason,
  condition: Condition,
) etwittertends RuleWithConstantAction(
      LimitedEngagements(reason),
      And(
        TweetIsCommunityTweet,
        condition
      )) {
  override def enabled: Seq[RuleParam[Boolean]] = Seq(CommunityTweetLimitedActionsRulesEnabledParam)
}

object CommunityTweetMemberLimitedActionsRule
    etwittertends CommunityTweetLimitedActionsRule(
      LimitedEngagementReason.CommunityTweetMember,
      ViewerIsCommunityMember,
    )

object CommunityTweetNonMemberLimitedActionsRule
    etwittertends CommunityTweetLimitedActionsRule(
      LimitedEngagementReason.CommunityTweetNonMember,
      Not(ViewerIsCommunityMember),
    ) {
  override def enabled: Seq[RuleParam[Boolean]] = Seq(
    CommunityTweetNonMemberLimitedActionsRuleEnabledParam)
}

object ReportedTweetInterstitialRule
    etwittertends RuleWithConstantAction(
      Interstitial(Reason.ViewerReportedTweet),
      And(
        NonAuthorViewer,
        Not(Retweet),
        ViewerReportsTweet
      )) {
  override def enabled: Seq[RuleParam[Boolean]] = Seq(EnableReportedTweetInterstitialRule)
}

object ReportedTweetInterstitialSearchRule
    etwittertends RuleWithConstantAction(
      Interstitial(Reason.ViewerReportedTweet),
      And(
        NonAuthorViewer,
        Not(Retweet),
        ViewerReportsTweet
      )) {
  override def enabled: Seq[RuleParam[Boolean]] = Seq(EnableReportedTweetInterstitialSearchRule)
}

abstract class FilterEtwitterclusiveTweetContentRule(
  action: Action,
  additionalCondition: Condition = Condition.True)
    etwittertends RuleWithConstantAction(
      action,
      And(
        additionalCondition,
        TweetIsEtwitterclusiveContent,
        Or(
          LoggedOutViewer,
          Not(
            Or(
              ViewerIsEtwitterclusiveTweetAuthor,
              ViewerSuperFollowsEtwitterclusiveTweetAuthor,
              And(
                Not(NonAuthorViewer),
                Not(Retweet)
              )
            )
          ),
        ),
      )
    ) {
  override def enabled: Seq[RuleParam[Boolean]] = Seq(EnableDropEtwitterclusiveTweetContentRule)
  override def enableFailClosed: Seq[RuleParam[Boolean]] = Seq(
    EnableDropEtwitterclusiveTweetContentRuleFailClosed)
}

object DropEtwitterclusiveTweetContentRule
    etwittertends FilterEtwitterclusiveTweetContentRule(Drop(Reason.EtwitterclusiveTweet))

object TombstoneEtwitterclusiveTweetContentRule
    etwittertends FilterEtwitterclusiveTweetContentRule(Tombstone(Epitaph.SuperFollowsContent))

object TombstoneEtwitterclusiveQuotedTweetContentRule
    etwittertends FilterEtwitterclusiveTweetContentRule(
      Tombstone(Epitaph.SuperFollowsContent),
      IsQuotedInnerTweet
    )

object DropAllEtwitterclusiveTweetsRule
    etwittertends RuleWithConstantAction(
      Drop(Reason.EtwitterclusiveTweet),
      TweetIsEtwitterclusiveContent
    ) {
  override def enabled: Seq[RuleParam[Boolean]] = Seq(EnableDropAllEtwitterclusiveTweetsRuleParam)
  override def enableFailClosed: Seq[RuleParam[Boolean]] = Seq(
    EnableDropAllEtwitterclusiveTweetsRuleFailClosedParam)
}

object DropTweetsWithGeoRestrictedMediaRule
    etwittertends RuleWithConstantAction(Drop(Unspecified), MediaRestrictedInViewerCountry) {
  override def enabled: Seq[RuleParam[Boolean]] = Seq(
    EnableDropTweetsWithGeoRestrictedMediaRuleParam)
}

object TrustedFriendsTweetLimitedEngagementsRule
    etwittertends RuleWithConstantAction(
      LimitedEngagements(LimitedEngagementReason.TrustedFriendsTweet),
      TweetIsTrustedFriendsContent
    ) {
  override def enabled: Seq[RuleParam[Boolean]] = Seq(
    TrustedFriendsTweetLimitedEngagementsRuleEnabledParam
  )
}

object DropAllTrustedFriendsTweetsRule
    etwittertends RuleWithConstantAction(
      Drop(Reason.TrustedFriendsTweet),
      TweetIsTrustedFriendsContent
    ) {
  override def enabled: Seq[RuleParam[Boolean]] = Seq(EnableDropAllTrustedFriendsTweetsRuleParam)
  override def enableFailClosed: Seq[RuleParam[Boolean]] = Seq(RuleParams.True)
}

object DropAllCollabInvitationTweetsRule
    etwittertends RuleWithConstantAction(
      Drop(Unspecified),
      TweetIsCollabInvitationContent
    ) {
  override def enabled: Seq[RuleParam[Boolean]] = Seq(EnableDropAllCollabInvitationTweetsRuleParam)
  override def enableFailClosed: Seq[RuleParam[Boolean]] = Seq(RuleParams.True)
}

abstract class FilterTrustedFriendsTweetContentRule(action: Action)
    etwittertends OnlyWhenNotAuthorViewerRule(
      action,
      And(
        TweetIsTrustedFriendsContent,
        Not(
          Or(
            ViewerIsTrustedFriendsTweetAuthor,
            ViewerIsTrustedFriend
          )
        )
      )
    ) {
  override def enabled: Seq[RuleParam[Boolean]] = Seq(EnableDropTrustedFriendsTweetContentRuleParam)
  override def enableFailClosed: Seq[RuleParam[Boolean]] = Seq(RuleParams.True)
}

object DropTrustedFriendsTweetContentRule
    etwittertends FilterTrustedFriendsTweetContentRule(Drop(Reason.TrustedFriendsTweet))

object TombstoneTrustedFriendsTweetContentRule
    etwittertends FilterTrustedFriendsTweetContentRule(Tombstone(Epitaph.Unavailable))

object TweetNsfwUserAdminAvoidRule
    etwittertends RuleWithConstantAction(
      Avoid(),
      Or(
        TweetHasNsfwUserAuthor,
        TweetHasNsfwAdminAuthor,
        NsfwUserAuthor,
        NsfwAdminAuthor
      )
    )

object AvoidHighTotwittericityModelScoreRule
    etwittertends RuleWithConstantAction(
      Avoid(),
      TweetHasLabelWithScoreAboveThresholdWithParam(
        TweetSafetyLabelType.HighTotwittericityScore,
        AdAvoidanceHighTotwittericityModelScoreThresholdParam)
    )

object AvoidReportedTweetModelScoreRule
    etwittertends RuleWithConstantAction(
      Avoid(),
      TweetHasLabelWithScoreAboveThresholdWithParam(
        TweetSafetyLabelType.HighPReportedTweetScore,
        AdAvoidanceReportedTweetModelScoreThresholdParam)
    )

object TombstoneDeletedOuterTweetRule
    etwittertends RuleWithConstantAction(
      Tombstone(Epitaph.Deleted),
      And(
        Equals(FeatureTweetDeleteReason, TweetDeleteReason.Deleted),
        Not(IsQuotedInnerTweet)
      )
    ) {
  override def enabled: Seq[RuleParam[Boolean]] = Seq(EnableDeleteStateTweetRulesParam)
}

object TombstoneDeletedTweetRule
    etwittertends RuleWithConstantAction(
      Tombstone(Epitaph.Deleted),
      And(
        Equals(FeatureTweetDeleteReason, TweetDeleteReason.Deleted),
      )
    ) {
  override def enabled: Seq[RuleParam[Boolean]] = Seq(EnableDeleteStateTweetRulesParam)
}

object TombstoneDeletedQuotedTweetRule
    etwittertends RuleWithConstantAction(
      Tombstone(Epitaph.Deleted),
      And(
        Equals(FeatureTweetDeleteReason, TweetDeleteReason.Deleted),
        IsQuotedInnerTweet
      )
    ) {
  override def enabled: Seq[RuleParam[Boolean]] = Seq(EnableDeleteStateTweetRulesParam)
}

object TombstoneBounceDeletedTweetRule
    etwittertends RuleWithConstantAction(
      Tombstone(Epitaph.BounceDeleted),
      Equals(FeatureTweetDeleteReason, TweetDeleteReason.BounceDeleted),
    ) {
  override def enabled: Seq[RuleParam[Boolean]] = Seq(EnableDeleteStateTweetRulesParam)
}

object TombstoneBounceDeletedOuterTweetRule
    etwittertends RuleWithConstantAction(
      Tombstone(Epitaph.BounceDeleted),
      And(
        Equals(FeatureTweetDeleteReason, TweetDeleteReason.BounceDeleted),
        Not(IsQuotedInnerTweet)
      )
    ) {
  override def enabled: Seq[RuleParam[Boolean]] = Seq(EnableDeleteStateTweetRulesParam)
}

object TombstoneBounceDeletedQuotedTweetRule
    etwittertends RuleWithConstantAction(
      Tombstone(Epitaph.BounceDeleted),
      And(
        Equals(FeatureTweetDeleteReason, TweetDeleteReason.BounceDeleted),
        IsQuotedInnerTweet
      )
    ) {
  override def enabled: Seq[RuleParam[Boolean]] = Seq(EnableDeleteStateTweetRulesParam)
}


object DropStaleTweetsRule
    etwittertends RuleWithConstantAction(
      Drop(StaleTweet),
      And(TweetIsStaleTweet, Not(IsQuotedInnerTweet), Not(Retweet), Not(IsSourceTweet))) {
  override def enabled: Seq[RuleParam[Boolean]] = Seq(EnableStaleTweetDropRuleParam)
  override def enableFailClosed: Seq[RuleParam[Boolean]] = Seq(
    EnableStaleTweetDropRuleFailClosedParam)
}

object StaleTweetLimitedActionsRule
    etwittertends RuleWithConstantAction(
      LimitedEngagements(LimitedEngagementReason.StaleTweet),
      TweetIsStaleTweet) {
  override def enabled: Seq[RuleParam[Boolean]] = Seq(StaleTweetLimitedActionsRulesEnabledParam)
}
