package com.tw ter.v s b l y.rules

 mport com.tw ter.v s b l y.rules.Cond  on.And
 mport com.tw ter.v s b l y.rules.Cond  on.Not

object  nterst  al f {

  object V e rMutedKeyword
      extends RuleW hConstantAct on(
         nterst  al(Reason.MutedKeyword),
        And(
          Not(Cond  on. sFocalT et),
          Cond  on.V e rHasMatch ngKeywordForT etRepl es,
        )
      )

  object V e rBlockedAuthor
      extends RuleW hConstantAct on(
         nterst  al(Reason.V e rBlocksAuthor),
        And(
          Not(Cond  on. sFocalT et),
          Cond  on.V e rBlocksAuthor
        )
      )

  object V e rHardMutedAuthor
      extends RuleW hConstantAct on(
         nterst  al(Reason.V e rHardMutedAuthor),
        And(
          Not(Cond  on. sFocalT et),
          Cond  on.V e rMutesAuthor,
          Not(
            Cond  on.V e rDoesFollowAuthor
          )
        )
      )

  object V e rReportedAuthor
      extends RuleW hConstantAct on(
         nterst  al(Reason.V e rReportedAuthor),
        Cond  on.V e rReportsAuthor
      )
}
