package com.tw ter.t etyp e.repos ory

 mport com.tw ter.aud ence_rewards.thr ftscala.HasSuperFollow ngRelat onsh pRequest
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.cl ent.Fetc r
 mport com.tw ter.strato.cl ent.{Cl ent => StratoCl ent}
 mport com.tw ter.t etyp e.Future
 mport com.tw ter.t etyp e.User d
 mport com.tw ter.t etyp e.core.T etCreateFa lure
 mport com.tw ter.t etyp e.thr ftscala.Exclus veT etControl
 mport com.tw ter.t etyp e.thr ftscala.T etCreateState

object StratoSuperFollowRelat onsRepos ory {
  type Type = (User d, User d) => St ch[Boolean]

  def apply(cl ent: StratoCl ent): Type = {

    val column = "aud encerewards/superFollows/hasSuperFollow ngRelat onsh pV2"

    val fetc r: Fetc r[HasSuperFollow ngRelat onsh pRequest, Un , Boolean] =
      cl ent.fetc r[HasSuperFollow ngRelat onsh pRequest, Boolean](column)

    (author d, v e r d) => {
      // Owner of an exclus ve t et cha n can respond to t  r own
      // t ets / repl es, desp e not super follow ng t mselves
       f (author d == v e r d) {
        St ch.True
      } else {
        val key = HasSuperFollow ngRelat onsh pRequest(author d, v e r d)
        // T  default relat on for t  column  s "m ss ng", aka None.
        // T  needs to be mapped to false s nce Super Follows are a sparse relat on.
        fetc r.fetch(key).map(_.v.getOrElse(false))
      }
    }
  }

  object Val date {
    def apply(
      exclus veT etControl: Opt on[Exclus veT etControl],
      user d: User d,
      superFollowRelat onsRepo: StratoSuperFollowRelat onsRepos ory.Type
    ): Future[Un ] = {
      St ch
        .run {
          exclus veT etControl.map(_.conversat onAuthor d) match {
            // Don't do exclus ve t et val dat on on non exclus ve t ets.
            case None =>
              St ch.value(true)

            case So (convoAuthor d) =>
              superFollowRelat onsRepo(user d, convoAuthor d)
          }
        }.map {
          case true => Future.Un 
          case false =>
            Future.except on(T etCreateFa lure.State(T etCreateState.S ceT etNotFound))
        }.flatten
    }
  }
}
