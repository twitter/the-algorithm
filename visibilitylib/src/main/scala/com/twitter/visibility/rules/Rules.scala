package com.twitter.visibility.rules

import com.twitter.visibility.configapi.params.RuleParam
import com.twitter.visibility.configapi.params.RuleParams
import com.twitter.visibility.configapi.params.RuleParams.EnableAuthorBlocksViewerDropRuleParam
import com.twitter.visibility.configapi.params.RuleParams.EnableInnerQuotedTweetViewerBlocksAuthorInterstitialRuleParam
import com.twitter.visibility.configapi.params.RuleParams.EnableInnerQuotedTweetViewerMutesAuthorInterstitialRuleParam
import com.twitter.visibility.configapi.params.RuleParams.EnableTimelineHomePromotedTweetHealthEnforcementRules
import com.twitter.visibility.configapi.params.RuleParams.EnableViewerIsSoftUserDropRuleParam
import com.twitter.visibility.configapi.params.RuleParams.PromotedTweetHealthEnforcementHoldback
import com.twitter.visibility.rules.Condition.And
import com.twitter.visibility.rules.Condition.IsQuotedInnerTweet
import com.twitter.visibility.rules.Condition.NonAuthorViewer
import com.twitter.visibility.rules.Condition.Not
import com.twitter.visibility.rules.Condition.Retweet
import com.twitter.visibility.rules.Condition.SoftViewer
import com.twitter.visibility.rules.Reason._

object DropAllRule
    etwittertends AlwaysActRule(
      Drop(Unspecified)
    )

object AllowAllRule
    etwittertends AlwaysActRule(
      Allow
    )

object TestRule
    etwittertends AlwaysActRule(
      Drop(Unspecified)
    )

object DeactivatedAuthorRule
    etwittertends OnlyWhenNotAuthorViewerRule(
      Drop(DeactivatedAuthor),
      Condition.DeactivatedAuthor
    )

object ErasedAuthorRule
    etwittertends OnlyWhenNotAuthorViewerRule(
      Drop(ErasedAuthor),
      Condition.ErasedAuthor
    )

object OffboardedAuthorRule
    etwittertends OnlyWhenNotAuthorViewerRule(
      Drop(OffboardedAuthor),
      Condition.OffboardedAuthor
    )

object DropNsfwUserAuthorRule
    etwittertends OnlyWhenNotAuthorViewerRule(
      Drop(Nsfw),
      Condition.NsfwUserAuthor
    )

object DropNsfwUserAuthorViewerOptInFilteringOnSearchRule
    etwittertends ViewerOptInFilteringOnSearchRule(
      Drop(Nsfw),
      Condition.NsfwUserAuthor
    )

object InterstitialNsfwUserAuthorRule
    etwittertends OnlyWhenNotAuthorViewerRule(
      Interstitial(Nsfw),
      Condition.NsfwUserAuthor
    )

object DropNsfwAdminAuthorRule
    etwittertends OnlyWhenNotAuthorViewerRule(
      Drop(Nsfw),
      Condition.NsfwAdminAuthor
    )

object DropNsfwAdminAuthorViewerOptInFilteringOnSearchRule
    etwittertends ViewerOptInFilteringOnSearchRule(
      Drop(Nsfw),
      Condition.NsfwAdminAuthor
    )

object InterstitialNsfwAdminAuthorRule
    etwittertends OnlyWhenNotAuthorViewerRule(
      Interstitial(Nsfw),
      Condition.NsfwAdminAuthor
    )

object ProtectedAuthorDropRule
    etwittertends RuleWithConstantAction(
      Drop(Reason.ProtectedAuthor),
      And(Condition.LoggedOutOrViewerNotFollowingAuthor, Condition.ProtectedAuthor)
    )

object ProtectedAuthorTombstoneRule
    etwittertends RuleWithConstantAction(
      Tombstone(Epitaph.Protected),
      And(Condition.LoggedOutOrViewerNotFollowingAuthor, Condition.ProtectedAuthor)
    )

object DropAllProtectedAuthorRule
    etwittertends RuleWithConstantAction(
      Drop(Reason.ProtectedAuthor),
      Condition.ProtectedAuthor
    ) {
  override def enableFailClosed: Seq[RuleParam[Boolean]] = Seq(RuleParams.True)
}

object ProtectedQuoteTweetAuthorRule
    etwittertends RuleWithConstantAction(
      Drop(Reason.ProtectedAuthor),
      And(Condition.OuterAuthorNotFollowingAuthor, Condition.ProtectedAuthor)
    )

object DropProtectedViewerIfPresentRule
    etwittertends RuleWithConstantAction(
      Drop(Reason.Unspecified),
      And(Condition.LoggedInViewer, Condition.ProtectedViewer)
    ) {
  override def enableFailClosed: Seq[RuleParam[Boolean]] = Seq(RuleParams.True)
}

object SuspendedAuthorRule
    etwittertends OnlyWhenNotAuthorViewerRule(
      Drop(SuspendedAuthor),
      Condition.SuspendedAuthor
    )

object SuspendedViewerRule
    etwittertends OnlyWhenNotAuthorViewerRule(
      Drop(Unspecified),
      Condition.SuspendedViewer
    )

object DeactivatedViewerRule
    etwittertends OnlyWhenNotAuthorViewerRule(
      Drop(Unspecified),
      Condition.DeactivatedViewer
    )

object ViewerIsUnmentionedRule
    etwittertends OnlyWhenNotAuthorViewerRule(
      Drop(Reason.ViewerIsUnmentioned),
      Condition.ViewerIsUnmentioned
    )

abstract class AuthorBlocksViewerRule(override val action: Action)
    etwittertends OnlyWhenNotAuthorViewerRule(
      action,
      Condition.AuthorBlocksViewer
    )

object AuthorBlocksViewerDropRule
    etwittertends AuthorBlocksViewerRule(
      Drop(Reason.AuthorBlocksViewer)
    )

object DeciderableAuthorBlocksViewerDropRule
    etwittertends AuthorBlocksViewerRule(
      Drop(Reason.AuthorBlocksViewer)
    ) {
  override def enabled: Seq[RuleParam[Boolean]] =
    Seq(EnableAuthorBlocksViewerDropRuleParam)
}

object AuthorBlocksViewerTombstoneRule
    etwittertends AuthorBlocksViewerRule(
      Tombstone(Epitaph.BlockedBy)
    )

object ViewerBlocksAuthorRule
    etwittertends OnlyWhenNotAuthorViewerRule(
      Drop(Reason.ViewerBlocksAuthor),
      Condition.ViewerBlocksAuthor
    )

object ViewerBlocksAuthorViewerOptInBlockingOnSearchRule
    etwittertends ViewerOptInBlockingOnSearchRule(
      Drop(Reason.ViewerBlocksAuthor),
      Condition.ViewerBlocksAuthor
    )

object ViewerMutesAuthorRule
    etwittertends OnlyWhenNotAuthorViewerRule(
      Drop(Reason.ViewerMutesAuthor),
      Condition.ViewerMutesAuthor
    )

object ViewerMutesAuthorViewerOptInBlockingOnSearchRule
    etwittertends ViewerOptInBlockingOnSearchRule(
      Drop(Reason.ViewerMutesAuthor),
      Condition.ViewerMutesAuthor
    )

object AuthorBlocksOuterAuthorRule
    etwittertends RuleWithConstantAction(
      Drop(Reason.AuthorBlocksViewer),
      And(Not(Condition.IsSelfQuote), Condition.AuthorBlocksOuterAuthor)
    )

object ViewerMutesAndDoesNotFollowAuthorRule
    etwittertends OnlyWhenNotAuthorViewerRule(
      Drop(Reason.ViewerHardMutedAuthor),
      And(Condition.ViewerMutesAuthor, Not(Condition.ViewerDoesFollowAuthor))
    )

object AuthorBlocksViewerUnspecifiedRule
    etwittertends OnlyWhenNotAuthorViewerRule(
      Drop(Reason.Unspecified),
      Condition.AuthorBlocksViewer
    )

object ViewerHasMatchingMutedKeywordForNotificationsRule
    etwittertends OnlyWhenNotAuthorViewerRule(
      Drop(Reason.MutedKeyword),
      Condition.ViewerHasMatchingKeywordForNotifications
    )

object ViewerHasMatchingMutedKeywordForHomeTimelineRule
    etwittertends OnlyWhenNotAuthorViewerRule(
      Drop(Reason.MutedKeyword),
      Condition.ViewerHasMatchingKeywordForHomeTimeline
    )

trait HasPromotedTweetHealthEnforcement etwittertends WithGate {
  override def holdbacks: Seq[RuleParam[Boolean]] = Seq(PromotedTweetHealthEnforcementHoldback)
  override def enabled: Seq[RuleParam[Boolean]] = Seq(
    EnableTimelineHomePromotedTweetHealthEnforcementRules)
}

object ViewerHasMatchingMutedKeywordForHomeTimelinePromotedTweetRule
    etwittertends OnlyWhenNotAuthorViewerRule(
      Drop(Reason.MutedKeyword),
      Condition.ViewerHasMatchingKeywordForHomeTimeline
    )
    with HasPromotedTweetHealthEnforcement

object ViewerHasMatchingMutedKeywordForTweetRepliesRule
    etwittertends OnlyWhenNotAuthorViewerRule(
      Drop(Reason.MutedKeyword),
      Condition.ViewerHasMatchingKeywordForTweetReplies
    )

object MutedKeywordForTweetRepliesInterstitialRule
    etwittertends OnlyWhenNotAuthorViewerRule(
      Interstitial(Reason.MutedKeyword),
      Condition.ViewerHasMatchingKeywordForTweetReplies
    )

object MutedKeywordForQuotedTweetTweetDetailInterstitialRule
    etwittertends OnlyWhenNotAuthorViewerRule(
      Interstitial(Reason.MutedKeyword),
      And(Condition.IsQuotedInnerTweet, Condition.ViewerHasMatchingKeywordForTweetReplies)
    )

object ViewerMutesAuthorInterstitialRule
    etwittertends OnlyWhenNotAuthorViewerRule(
      Interstitial(Reason.ViewerMutesAuthor),
      Condition.ViewerMutesAuthor
    )

object ViewerMutesAuthorInnerQuotedTweetInterstitialRule
    etwittertends OnlyWhenNotAuthorViewerRule(
      Interstitial(Reason.ViewerMutesAuthor),
      And(Condition.ViewerMutesAuthor, IsQuotedInnerTweet)
    ) {
  override def enabled: Seq[RuleParam[Boolean]] =
    Seq(EnableInnerQuotedTweetViewerMutesAuthorInterstitialRuleParam)
}

object ViewerMutesAuthorHomeTimelinePromotedTweetRule
    etwittertends OnlyWhenNotAuthorViewerRule(
      Drop(Reason.ViewerMutesAuthor),
      Condition.ViewerMutesAuthor
    )
    with HasPromotedTweetHealthEnforcement

object ViewerBlocksAuthorInterstitialRule
    etwittertends OnlyWhenNotAuthorViewerRule(
      Interstitial(Reason.ViewerBlocksAuthor),
      Condition.ViewerBlocksAuthor
    )

object ViewerBlocksAuthorInnerQuotedTweetInterstitialRule
    etwittertends OnlyWhenNotAuthorViewerRule(
      Interstitial(Reason.ViewerBlocksAuthor),
      And(Condition.ViewerBlocksAuthor, IsQuotedInnerTweet)
    ) {
  override def enabled: Seq[RuleParam[Boolean]] =
    Seq(EnableInnerQuotedTweetViewerBlocksAuthorInterstitialRuleParam)
}

object ViewerBlocksAuthorHomeTimelinePromotedTweetRule
    etwittertends OnlyWhenNotAuthorViewerRule(
      Drop(Reason.ViewerBlocksAuthor),
      Condition.ViewerBlocksAuthor
    )
    with HasPromotedTweetHealthEnforcement

object ViewerReportsAuthorInterstitialRule
    etwittertends OnlyWhenNotAuthorViewerRule(
      Interstitial(Reason.ViewerReportedAuthor),
      Condition.ViewerReportsAuthor
    )

object ViewerIsAuthorDropRule
    etwittertends RuleWithConstantAction(Drop(Unspecified), Not(NonAuthorViewer))

object ViewerIsNotAuthorDropRule etwittertends RuleWithConstantAction(Drop(Unspecified), NonAuthorViewer)

object RetweetDropRule etwittertends RuleWithConstantAction(Drop(Unspecified), Retweet)

object ViewerIsSoftUserDropRule etwittertends RuleWithConstantAction(Drop(ViewerIsSoftUser), SoftViewer) {

  override val enabled: Seq[RuleParam[Boolean]] = Seq(EnableViewerIsSoftUserDropRuleParam)
}
