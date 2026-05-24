package com.twitter.visibility.rules

import com.twitter.visibility.configapi.params.RuleParam
import com.twitter.visibility.configapi.params.RuleParams.EnableInnerQuotedTweetViewerBlocksAuthorInterstitialRuleParam
import com.twitter.visibility.configapi.params.RuleParams.EnableInnerQuotedTweetViewerMutesAuthorInterstitialRuleParam
import com.twitter.visibility.rules.Condition.And
import com.twitter.visibility.rules.Condition.AuthorBlocksViewer
import com.twitter.visibility.rules.Condition.DeactivatedAuthor
import com.twitter.visibility.rules.Condition.ErasedAuthor
import com.twitter.visibility.rules.Condition.IsQuotedInnerTweet
import com.twitter.visibility.rules.Condition.OffboardedAuthor
import com.twitter.visibility.rules.Condition.ProtectedAuthor
import com.twitter.visibility.rules.Condition.Retweet
import com.twitter.visibility.rules.Condition.SuspendedAuthor
import com.twitter.visibility.rules.Condition.UnavailableAuthor
import com.twitter.visibility.rules.Condition.ViewerBlocksAuthor
import com.twitter.visibility.rules.Condition.ViewerMutesAuthor

object UserUnavailableStateTombstoneRules {
  abstract class UserUnavailableStateTweetTombstoneRule(epitaph: Epitaph, condition: Condition)
      extends RuleWithConstantAction(Tombstone(epitaph), condition) {}

  abstract class UserUnavailableStateRetweetTombstoneRule(epitaph: Epitaph, condition: Condition)
      extends RuleWithConstantAction(Tombstone(epitaph), And(Retweet, condition)) {}

  abstract class UserUnavailableStateInnerQuotedTweetTombstoneRule(
    epitaph: Epitaph,
    condition: Condition)
      extends RuleWithConstantAction(Tombstone(epitaph), And(IsQuotedInnerTweet, condition))

  abstract class UserUnavailableStateInnerQuotedTweetInterstitialRule(
    reason: Reason,
    condition: Condition)
      extends RuleWithConstantAction(Interstitial(reason), And(IsQuotedInnerTweet, condition))

  object SuspendedUserUnavailableTweetTombstoneRule
      extends UserUnavailableStateTweetTombstoneRule(Epitaph.Suspended, SuspendedAuthor)

  object DeactivatedUserUnavailableTweetTombstoneRule
      extends UserUnavailableStateTweetTombstoneRule(Epitaph.Deactivated, DeactivatedAuthor)

  object OffBoardedUserUnavailableTweetTombstoneRule
      extends UserUnavailableStateTweetTombstoneRule(Epitaph.Offboarded, OffboardedAuthor)

  object ErasedUserUnavailableTweetTombstoneRule
      extends UserUnavailableStateTweetTombstoneRule(Epitaph.Deactivated, ErasedAuthor)

  object ProtectedUserUnavailableTweetTombstoneRule
      extends UserUnavailableStateTweetTombstoneRule(Epitaph.Protected, ProtectedAuthor)

  object AuthorBlocksViewerUserUnavailableTweetTombstoneRule
      extends UserUnavailableStateTweetTombstoneRule(Epitaph.BlockedBy, AuthorBlocksViewer)

  object UserUnavailableTweetTombstoneRule
      extends UserUnavailableStateTweetTombstoneRule(Epitaph.Unavailable, UnavailableAuthor)

  object SuspendedUserUnavailableRetweetTombstoneRule
      extends UserUnavailableStateRetweetTombstoneRule(Epitaph.Suspended, SuspendedAuthor)

  object DeactivatedUserUnavailableRetweetTombstoneRule
      extends UserUnavailableStateRetweetTombstoneRule(Epitaph.Deactivated, DeactivatedAuthor)

  object OffBoardedUserUnavailableRetweetTombstoneRule
      extends UserUnavailableStateRetweetTombstoneRule(Epitaph.Offboarded, OffboardedAuthor)

  object ErasedUserUnavailableRetweetTombstoneRule
      extends UserUnavailableStateRetweetTombstoneRule(Epitaph.Deactivated, ErasedAuthor)

  object ProtectedUserUnavailableRetweetTombstoneRule
      extends UserUnavailableStateRetweetTombstoneRule(Epitaph.Protected, ProtectedAuthor)

  object AuthorBlocksViewerUserUnavailableRetweetTombstoneRule
      extends UserUnavailableStateRetweetTombstoneRule(Epitaph.BlockedBy, AuthorBlocksViewer)

  object ViewerBlocksAuthorUserUnavailableRetweetTombstoneRule
      extends UserUnavailableStateRetweetTombstoneRule(Epitaph.Unavailable, ViewerBlocksAuthor)

  object ViewerMutesAuthorUserUnavailableRetweetTombstoneRule
      extends UserUnavailableStateRetweetTombstoneRule(Epitaph.Unavailable, ViewerMutesAuthor)

  object SuspendedUserUnavailableInnerQuotedTweetTombstoneRule
      extends UserUnavailableStateInnerQuotedTweetTombstoneRule(Epitaph.Suspended, SuspendedAuthor)

  object DeactivatedUserUnavailableInnerQuotedTweetTombstoneRule
      extends UserUnavailableStateInnerQuotedTweetTombstoneRule(
        Epitaph.Deactivated,
        DeactivatedAuthor)

  object OffBoardedUserUnavailableInnerQuotedTweetTombstoneRule
      extends UserUnavailableStateInnerQuotedTweetTombstoneRule(
        Epitaph.Offboarded,
        OffboardedAuthor)

  object ErasedUserUnavailableInnerQuotedTweetTombstoneRule
      extends UserUnavailableStateInnerQuotedTweetTombstoneRule(Epitaph.Deactivated, ErasedAuthor)

  object ProtectedUserUnavailableInnerQuotedTweetTombstoneRule
      extends UserUnavailableStateInnerQuotedTweetTombstoneRule(Epitaph.Protected, ProtectedAuthor)

  object AuthorBlocksViewerUserUnavailableInnerQuotedTweetTombstoneRule
      extends UserUnavailableStateInnerQuotedTweetTombstoneRule(
        Epitaph.BlockedBy,
        AuthorBlocksViewer)

  object ViewerBlocksAuthorUserUnavailableInnerQuotedTweetInterstitialRule
      extends UserUnavailableStateInnerQuotedTweetInterstitialRule(
        Reason.ViewerBlocksAuthor,
        ViewerBlocksAuthor) {
    override def enabled: Seq[RuleParam[Boolean]] =
      Seq(EnableInnerQuotedTweetViewerBlocksAuthorInterstitialRuleParam)
  }

  object ViewerMutesAuthorUserUnavailableInnerQuotedTweetInterstitialRule
      extends UserUnavailableStateInnerQuotedTweetInterstitialRule(
        Reason.ViewerMutesAuthor,
        ViewerMutesAuthor) {
    override def enabled: Seq[RuleParam[Boolean]] =
      Seq(EnableInnerQuotedTweetViewerMutesAuthorInterstitialRuleParam)
  }
}
