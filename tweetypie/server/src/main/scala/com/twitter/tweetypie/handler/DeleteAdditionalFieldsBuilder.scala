package com.tw ter.t etyp e
package handler

 mport com.tw ter.st ch.NotFound
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.repos ory.T etQuery
 mport com.tw ter.t etyp e.repos ory.T etRepos ory
 mport com.tw ter.t etyp e.repos ory.UserKey
 mport com.tw ter.t etyp e.repos ory.UserQueryOpt ons
 mport com.tw ter.t etyp e.repos ory.UserRepos ory
 mport com.tw ter.t etyp e.repos ory.UserV s b l y
 mport com.tw ter.t etyp e.store.AsyncDeleteAdd  onalF elds
 mport com.tw ter.t etyp e.store.DeleteAdd  onalF elds
 mport com.tw ter.t etyp e.store.T etStoreEventOrRetry
 mport com.tw ter.t etyp e.thr ftscala.AsyncDeleteAdd  onalF eldsRequest
 mport com.tw ter.t etyp e.thr ftscala.DeleteAdd  onalF eldsRequest

object DeleteAdd  onalF eldsBu lder {
  type Type = DeleteAdd  onalF eldsRequest => Future[Seq[DeleteAdd  onalF elds.Event]]

  val t etQueryOpt ons = T etQuery.Opt ons( nclude = GetT etsHandler.Base nclude)

  def apply(t etRepo: T etRepos ory.Type): Type = {
    def getT et(t et d: T et d) =
      St ch.run(
        t etRepo(t et d, t etQueryOpt ons)
          .rescue(HandlerError.translateNotFoundToCl entError(t et d))
      )

    request => {
      Future.collect(
        request.t et ds.map { t et d =>
          getT et(t et d).map { t et =>
            DeleteAdd  onalF elds.Event(
              t et d = t et d,
              f eld ds = request.f eld ds,
              user d = getUser d(t et),
              t  stamp = T  .now
            )
          }
        }
      )
    }
  }
}

object AsyncDeleteAdd  onalF eldsBu lder {
  type Type = AsyncDeleteAdd  onalF eldsRequest => Future[
    T etStoreEventOrRetry[AsyncDeleteAdd  onalF elds.Event]
  ]

  val userQueryOpts: UserQueryOpt ons = UserQueryOpt ons(Set(UserF eld.Safety), UserV s b l y.All)

  def apply(userRepo: UserRepos ory.Type): Type = {
    def getUser(user d: User d): Future[User] =
      St ch.run(
        userRepo(UserKey.by d(user d), userQueryOpts)
          .rescue { case NotFound => St ch.except on(HandlerError.userNotFound(user d)) }
      )

    request =>
      getUser(request.user d).map { user =>
        AsyncDeleteAdd  onalF elds.Event.fromAsyncRequest(request, user)
      }
  }
}
