package com.tw ter.t etyp e
package handler

 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.repos ory.T etQuery
 mport com.tw ter.t etyp e.repos ory.T etRepos ory
 mport com.tw ter.t etyp e.store.QuotedT etTakedown
 mport com.tw ter.t etyp e.thr ftscala.QuotedT etTakedownRequest

/**
 * Create t  appropr ate QuotedT etTakedown.Event for a QuotedT etTakedown request.
 */
object QuotedT etTakedownEventBu lder {
  type Type = QuotedT etTakedownRequest => Future[Opt on[QuotedT etTakedown.Event]]

  val queryOpt ons: T etQuery.Opt ons =
    T etQuery.Opt ons(GetT etsHandler.Base nclude)

  def apply(t etRepo: T etRepos ory.Opt onal): Type =
    request =>
      St ch.run(
        t etRepo(request.quot ngT et d, queryOpt ons).map {
          _.map { quot ngT et =>
            QuotedT etTakedown.Event(
              quot ngT et d = request.quot ngT et d,
              quot ngUser d = getUser d(quot ngT et),
              quotedT et d = request.quotedT et d,
              quotedUser d = request.quotedUser d,
              takedownCountryCodes = request.takedownCountryCodes,
              takedownReasons = request.takedownReasons,
              t  stamp = T  .now
            )
          }
        }
      )
}
