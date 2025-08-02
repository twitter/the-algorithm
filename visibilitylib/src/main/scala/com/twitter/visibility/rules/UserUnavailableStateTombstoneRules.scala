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
      etwittertends RuleWithConstantAction(Tombstone(epitaph), condition) {}

  abstract class UserUnavailableStateRetweetTombstoneRule(epitaph: Epitaph, condition: Condition)
      etwittertends RuleWithConstantAction(Tombstone(epitaph), And(Retweet, condition)) {}

  abstract class UserUnavailableStateInnerQuotedTweetTombstoneRule(
    epitaph: Epitaph,
    condition: Condition)
      etwittertends RuleWithConstantAction(Tombstone(epitaph), And(IsQuotedInnerTweet, condition))

  abstract class UserUnavailableStateInnerQuotedTweetInterstitialRule(
    reason: Reason,
    condition: Condition)
      etwittertends RuleWithConstantAction(Interstitial(reason), And(IsQuotedInnerTweet, condition))

  object SuspendedUserUnavailableTweetTombstoneRule
      etwittertends UserUnavailableStateTweetTombstoneRule(Epitaph.Suspended, SuspendedAuthor)

  object DeactivatedUserUnavailableTweetTombstoneRule
      etwittertends UserUnavailableStateTweetTombstoneRule(Epitaph.Deactivated, DeactivatedAuthor)

  object OffBoardedUserUnavailableTweetTombstoneRule
      etwittertends UserUnavailableStateTweetTombstoneRule(Epitaph.Offboarded, OffboardedAuthor)

  object ErasedUserUnavailableTweetTombstoneRule
      etwittertends UserUnavailableStateTweetTombstoneRule(Epitaph.Deactivated, ErasedAuthor)

  object ProtectedUserUnavailableTweetTombstoneRule
      etwittertends UserUnavailableStateTweetTombstoneRule(Epitaph.Protected, ProtectedAuthor)

  object AuthorBlocksViewerUserUnavailableTweetTombstoneRule
      etwittertends UserUnavailableStateTweetTombstoneRule(Epitaph.BlockedBy, AuthorBlocksViewer)

  object UserUnavailableTweetTombstoneRule
      etwittertends UserUnavailableStateTweetTombstoneRule(Epitaph.Unavailable, UnavailableAuthor)

  object SuspendedUserUnavailableRetweetTombstoneRule
      etwittertends UserUnavailableStateRetweetTombstoneRule(Epitaph.Suspended, SuspendedAuthor)

  object DeactivatedUserUnavailableRetweetTombstoneRule
      etwittertends UserUnavailableStateRetweetTombstoneRule(Epitaph.Deactivated, DeactivatedAuthor)

  object OffBoardedUserUnavailableRetweetTombstoneRule
      etwittertends UserUnavailableStateRetweetTombstoneRule(Epitaph.Offboarded, OffboardedAuthor)

  object ErasedUserUnavailableRetweetTombstoneRule
      etwittertends UserUnavailableStateRetweetTombstoneRule(Epitaph.Deactivated, ErasedAuthor)

  object ProtectedUserUnavailableRetweetTombstoneRule
      etwittertends UserUnavailableStateRetweetTombstoneRule(Epitaph.Protected, ProtectedAuthor)

  object AuthorBlocksViewerUserUnavailableRetweetTombstoneRule
      etwittertends UserUnavailableStateRetweetTombstoneRule(Epitaph.BlockedBy, AuthorBlocksViewer)

  object ViewerBlocksAuthorUserUnavailableRetweetTombstoneRule
      etwittertends UserUnavailableStateRetweetTombstoneRule(Epitaph.Unavailable, ViewerBlocksAuthor)

  object ViewerMutesAuthorUserUnavailableRetweetTombstoneRule
      etwittertends UserUnavailableStateRetweetTombstoneRule(Epitaph.Unavailable, ViewerMutesAuthor)

  object SuspendedUserUnavailableInnerQuotedTweetTombstoneRule
      etwittertends UserUnavailableStateInnerQuotedTweetTombstoneRule(Epitaph.Suspended, SuspendedAuthor)

  object DeactivatedUserUnavailableInnerQuotedTweetTombstoneRule
      etwittertends UserUnavailableStateInnerQuotedTweetTombstoneRule(
        Epitaph.Deactivated,
        DeactivatedAuthor)

  object OffBoardedUserUnavailableInnerQuotedTweetTombstoneRule
      etwittertends UserUnavailableStateInnerQuotedTweetTombstoneRule(
        Epitaph.Offboarded,
        OffboardedAuthor)

  object ErasedUserUnavailableInnerQuotedTweetTombstoneRule
      etwittertends UserUnavailableStateInnerQuotedTweetTombstoneRule(Epitaph.Deactivated, ErasedAuthor)

  object ProtectedUserUnavailableInnerQuotedTweetTombstoneRule
      etwittertends UserUnavailableStateInnerQuotedTweetTombstoneRule(Epitaph.Protected, ProtectedAuthor)

  object AuthorBlocksViewerUserUnavailableInnerQuotedTweetTombstoneRule
      etwittertends UserUnavailableStateInnerQuotedTweetTombstoneRule(
        Epitaph.BlockedBy,
        AuthorBlocksViewer)

  object ViewerBlocksAuthorUserUnavailableInnerQuotedTweetInterstitialRule
      etwittertends UserUnavailableStateInnerQuotedTweetInterstitialRule(
        Reason.ViewerBlocksAuthor,
        ViewerBlocksAuthor) {
    override def enabled: Seq[RuleParam[Boolean]] =
      Seq(EnableInnerQuotedTweetViewerBlocksAuthorInterstitialRuleParam)
  }

  object ViewerMutesAuthorUserUnavailableInnerQuotedTweetInterstitialRule
      etwittertends UserUnavailableStateInnerQuotedTweetInterstitialRule(
        Reason.ViewerMutesAuthor,
        ViewerMutesAuthor) {
    override def enabled: Seq[RuleParam[Boolean]] =
      Seq(EnableInnerQuotedTweetViewerMutesAuthorInterstitialRuleParam)
  }
}
