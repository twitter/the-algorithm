package com.tw ter.t  l neranker.repos ory

 mport com.tw ter.t  l nes.v s b l y.model.V s b l yRule

object Repos oryBu lder {
  val V s b l yRules: Set[V s b l yRule.Value] = Set(
    V s b l yRule.Blocked,
    V s b l yRule.BlockedBy,
    V s b l yRule.Muted,
    V s b l yRule.Protected,
    V s b l yRule.AccountStatus
  )
}

tra  Repos oryBu lder {
  val V s b l yRules: Set[V s b l yRule.Value] = Repos oryBu lder.V s b l yRules
}
