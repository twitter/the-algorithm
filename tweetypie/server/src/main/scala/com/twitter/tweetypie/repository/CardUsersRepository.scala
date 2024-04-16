package com.tw ter.t etyp e
package repos ory

 mport com.tw ter.expandodo.thr ftscala._
 mport com.tw ter.st ch.SeqGroup
 mport com.tw ter.st ch.St ch
 mport com.tw ter.st ch.compat.LegacySeqGroup
 mport com.tw ter.t etyp e.backends.Expandodo

object CardUsersRepos ory {
  type CardUr  = Str ng
  type Type = (CardUr , Context) => St ch[Opt on[Set[User d]]]

  case class Context(perspect veUser d: User d) extends AnyVal

  case class GetUsersGroup(perspect ve d: User d, getCardUsers: Expandodo.GetCardUsers)
      extends SeqGroup[CardUr , GetCardUsersResponse] {
    protected overr de def run(keys: Seq[CardUr ]): Future[Seq[Try[GetCardUsersResponse]]] =
      LegacySeqGroup.l ftToSeqTry(
        getCardUsers(
          GetCardUsersRequests(
            requests = keys.map(k => GetCardUsersRequest(k)),
            perspect veUser d = So (perspect ve d)
          )
        ).map(_.responses)
      )
  }

  def apply(getCardUsers: Expandodo.GetCardUsers): Type =
    (cardUr , ctx) =>
      St ch.call(cardUr , GetUsersGroup(ctx.perspect veUser d, getCardUsers)).map { resp =>
        val authorUser ds = resp.authorUser ds.map(_.toSet)
        val s eUser ds = resp.s eUser ds.map(_.toSet)

         f (authorUser ds. sEmpty) {
          s eUser ds
        } else  f (s eUser ds. sEmpty) {
          authorUser ds
        } else {
          So (authorUser ds.get ++ s eUser ds.get)
        }
      }
}
