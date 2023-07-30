package com.X.visibility.rules

import com.X.visibility.rules.Condition.And
import com.X.visibility.rules.Condition.IsFocalTweet
import com.X.visibility.rules.Condition.Not

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
