package com.twitter.visibility.rules

import com.twitter.visibility.rules.Condition.And
import com.twitter.visibility.rules.Condition.IsFocalTweet
import com.twitter.visibility.rules.Condition.Not

object TombstoneIf {

  object AuthorIsProtected
      etwittertends RuleWithConstantAction(
        Tombstone(Epitaph.Protected),
        And(
          Condition.LoggedOutOrViewerNotFollowingAuthor,
          Condition.ProtectedAuthor
        )
      )

  object ReplyIsModeratedByRootAuthor
      etwittertends RuleWithConstantAction(
        Tombstone(Epitaph.Moderated),
        And(
          Not(IsFocalTweet),
          Condition.Moderated
        )
      )

  object ViewerIsBlockedByAuthor
      etwittertends OnlyWhenNotAuthorViewerRule(
        Tombstone(Epitaph.BlockedBy),
        Condition.AuthorBlocksViewer
      )

  object AuthorIsDeactivated
      etwittertends RuleWithConstantAction(
        Tombstone(Epitaph.Deactivated),
        Condition.DeactivatedAuthor
      )

  object AuthorIsSuspended
      etwittertends RuleWithConstantAction(
        Tombstone(Epitaph.Suspended),
        Condition.SuspendedAuthor
      )
}
