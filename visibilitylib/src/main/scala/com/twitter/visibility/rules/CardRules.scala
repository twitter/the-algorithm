package com.tw ter.v s b l y.rules

 mport com.tw ter.v s b l y.conf gap .params.FSRuleParams.CardUr RootDoma nDenyL stParam
 mport com.tw ter.v s b l y.conf gap .params.RuleParam
 mport com.tw ter.v s b l y.conf gap .params.RuleParams.EnableCardUr RootDoma nCardDenyl stRule
 mport com.tw ter.v s b l y.conf gap .params.RuleParams.EnableCommun yNon mberPollCardRule
 mport com.tw ter.v s b l y.conf gap .params.RuleParams.EnableCommun yNon mberPollCardRuleFa lClosed
 mport com.tw ter.v s b l y.rules.Cond  on.And
 mport com.tw ter.v s b l y.rules.Cond  on.CardUr HasRootDoma n
 mport com.tw ter.v s b l y.rules.Cond  on.Commun yT etCommun yV s ble
 mport com.tw ter.v s b l y.rules.Cond  on. sPollCard
 mport com.tw ter.v s b l y.rules.Cond  on.LoggedOutOrV e rNotFollow ngAuthor
 mport com.tw ter.v s b l y.rules.Cond  on.Not
 mport com.tw ter.v s b l y.rules.Cond  on.Or
 mport com.tw ter.v s b l y.rules.Cond  on.ProtectedAuthor
 mport com.tw ter.v s b l y.rules.Cond  on.T et sCommun yT et
 mport com.tw ter.v s b l y.rules.Cond  on.V e r sCommun y mber

object DropProtectedAuthorPollCardRule
    extends RuleW hConstantAct on(
      Drop(Reason.ProtectedAuthor),
      And(
         sPollCard,
        ProtectedAuthor,
        LoggedOutOrV e rNotFollow ngAuthor,
      )
    )

object DropCardUr RootDoma nDenyl stRule
    extends RuleW hConstantAct on(
      Drop(Reason.Unspec f ed),
      And(CardUr HasRootDoma n(CardUr RootDoma nDenyL stParam))
    ) {
  overr de def enabled: Seq[RuleParam[Boolean]] = Seq(EnableCardUr RootDoma nCardDenyl stRule)
}

object DropCommun yNon mberPollCardRule
    extends RuleW hConstantAct on(
      Drop(Reason.Commun yNotA mber),
      And(
         sPollCard,
        T et sCommun yT et,
        Or(
          Not(V e r sCommun y mber),
          Not(Commun yT etCommun yV s ble),
        )
      ),
    ) {
  overr de def enabled: Seq[RuleParam[Boolean]] = Seq(EnableCommun yNon mberPollCardRule)
  overr de def enableFa lClosed: Seq[RuleParam[Boolean]] = Seq(
    EnableCommun yNon mberPollCardRuleFa lClosed)
}
