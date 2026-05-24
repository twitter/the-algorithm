package com.twitter.visibility.rules

import com.twitter.visibility.rules.Condition.And
import com.twitter.visibility.rules.Condition.Not

object InterstitialIf {

  object ViewerMutedKeyword
      extends RuleWithConstantAction(
        Interstitial(Reason.MutedKeyword),
        And(
          Not(Condition.IsFocalTweet),
          Condition.ViewerHasMatchingKeywordForTweetReplies,
        )
      )

  object ViewerBlockedAuthor
      extends RuleWithConstantAction(
        Interstitial(Reason.ViewerBlocksAuthor),
        And(
          Not(Condition.IsFocalTweet),
          Condition.ViewerBlocksAuthor
        )
      )

  object ViewerHardMutedAuthor
      extends RuleWithConstantAction(
        Interstitial(Reason.ViewerHardMutedAuthor),
        And(
          Not(Condition.IsFocalTweet),
          Condition.ViewerMutesAuthor,
          Not(
            Condition.ViewerDoesFollowAuthor
          )
        )
      )

  object ViewerReportedAuthor
      extends RuleWithConstantAction(
        Interstitial(Reason.ViewerReportedAuthor),
        Condition.ViewerReportsAuthor
      )
}
