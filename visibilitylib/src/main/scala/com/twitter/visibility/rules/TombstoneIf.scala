package com.tw ter.v s b l y.rules

 mport com.tw ter.v s b l y.rules.Cond  on.And
 mport com.tw ter.v s b l y.rules.Cond  on. sFocalT et
 mport com.tw ter.v s b l y.rules.Cond  on.Not

object Tombstone f {

  object Author sProtected
      extends RuleW hConstantAct on(
        Tombstone(Ep aph.Protected),
        And(
          Cond  on.LoggedOutOrV e rNotFollow ngAuthor,
          Cond  on.ProtectedAuthor
        )
      )

  object Reply sModeratedByRootAuthor
      extends RuleW hConstantAct on(
        Tombstone(Ep aph.Moderated),
        And(
          Not( sFocalT et),
          Cond  on.Moderated
        )
      )

  object V e r sBlockedByAuthor
      extends OnlyW nNotAuthorV e rRule(
        Tombstone(Ep aph.BlockedBy),
        Cond  on.AuthorBlocksV e r
      )

  object Author sDeact vated
      extends RuleW hConstantAct on(
        Tombstone(Ep aph.Deact vated),
        Cond  on.Deact vatedAuthor
      )

  object Author sSuspended
      extends RuleW hConstantAct on(
        Tombstone(Ep aph.Suspended),
        Cond  on.SuspendedAuthor
      )
}
