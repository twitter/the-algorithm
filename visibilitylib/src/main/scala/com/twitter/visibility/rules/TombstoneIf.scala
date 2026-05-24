package com.twitter.visibility.rules

import com.twitter.visibility.rules.Condition.And
import com.twitter.visibility.rules.Condition.IsFocalTweet
import com.twitter.visibility.rules.Condition.Not

object TombstoneIf {

  object AuthorIsProtected
      extends RuleWithConstantAction(
        Tombstone(Epitaph.Protected),
        And(
          Condition.LoggedOutOrViewerNotFollowingAuthor,
          Condition.ProtectedAuthor
        )
      )

  object ReplyIsModeratedByRootAuthor
      extends RuleWithConstantAction(
        Tombstone(Epitaph.Moderated),
        And(
          Not(IsFocalTweet),
          Condition.Moderated
        )
      )

  object ViewerIsBlockedByAuthor
      extends OnlyWhenNotAuthorViewerRule(
        Tombstone(Epitaph.BlockedBy),
        Condition.AuthorBlocksViewer
      )

  object AuthorIsDeactivated
      extends RuleWithConstantAction(
        Tombstone(Epitaph.Deactivated),
        Condition.DeactivatedAuthor
      )

  object AuthorIsSuspended
      extends RuleWithConstantAction(
        Tombstone(Epitaph.Suspended),
        Condition.SuspendedAuthor
      )
}
