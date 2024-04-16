package com.tw ter.v s b l y.bu lder.users

 mport com.tw ter.f nagle.stats.Counter
 mport com.tw ter.g zmoduck.thr ftscala.Perspect ve
 mport com.tw ter.g zmoduck.thr ftscala.User
 mport com.tw ter.st ch.St ch
 mport com.tw ter.v s b l y.common.User d

case object V e rVerbsAuthor {
  def apply(
    author d: User d,
    v e r dOpt: Opt on[User d],
    relat onsh p: (User d, User d) => St ch[Boolean],
    relat onsh pCounter: Counter
  ): St ch[Boolean] = {
    relat onsh pCounter. ncr()

    v e r dOpt match {
      case So (v e r d) => relat onsh p(v e r d, author d)
      case _ => St ch.False
    }
  }

  def apply(
    author: User,
    v e r d: Opt on[User d],
    c ckPerspect ve: Perspect ve => Opt on[Boolean],
    relat onsh p: (User d, User d) => St ch[Boolean],
    relat onsh pCounter: Counter
  ): St ch[Boolean] = {
    author.perspect ve match {
      case So (perspect ve) =>
        c ckPerspect ve(perspect ve) match {
          case So (status) =>
            relat onsh pCounter. ncr()
            St ch.value(status)
          case None =>
            V e rVerbsAuthor(author. d, v e r d, relat onsh p, relat onsh pCounter)
        }
      case None => V e rVerbsAuthor(author. d, v e r d, relat onsh p, relat onsh pCounter)
    }
  }
}

case object AuthorVerbsV e r {

  def apply(
    author d: User d,
    v e r dOpt: Opt on[User d],
    relat onsh p: (User d, User d) => St ch[Boolean],
    relat onsh pCounter: Counter
  ): St ch[Boolean] = {
    relat onsh pCounter. ncr()

    v e r dOpt match {
      case So (v e r d) => relat onsh p(author d, v e r d)
      case _ => St ch.False
    }
  }
  def apply(
    author: User,
    v e r d: Opt on[User d],
    c ckPerspect ve: Perspect ve => Opt on[Boolean],
    relat onsh p: (User d, User d) => St ch[Boolean],
    relat onsh pCounter: Counter
  ): St ch[Boolean] = {
    author.perspect ve match {
      case So (perspect ve) =>
        c ckPerspect ve(perspect ve) match {
          case So (status) =>
            relat onsh pCounter. ncr()
            St ch.value(status)
          case None =>
            AuthorVerbsV e r(author. d, v e r d, relat onsh p, relat onsh pCounter)
        }
      case None => AuthorVerbsV e r(author. d, v e r d, relat onsh p, relat onsh pCounter)
    }
  }
}
