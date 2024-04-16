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
 mport com.tw ter.t etyp e.store.AsyncSetAdd  onalF elds
 mport com.tw ter.t etyp e.store.SetAdd  onalF elds
 mport com.tw ter.t etyp e.store.T etStoreEventOrRetry
 mport com.tw ter.t etyp e.thr ftscala.AsyncSetAdd  onalF eldsRequest
 mport com.tw ter.t etyp e.thr ftscala.SetAdd  onalF eldsRequest

object SetAdd  onalF eldsBu lder {
  type Type = SetAdd  onalF eldsRequest => Future[SetAdd  onalF elds.Event]

  val t etOpt ons: T etQuery.Opt ons = T etQuery.Opt ons( nclude = GetT etsHandler.Base nclude)

  def apply(t etRepo: T etRepos ory.Type): Type = {
    def getT et(t et d: T et d) =
      St ch.run(
        t etRepo(t et d, t etOpt ons)
          .rescue(HandlerError.translateNotFoundToCl entError(t et d))
      )

    request => {
      getT et(request.add  onalF elds. d).map { t et =>
        SetAdd  onalF elds.Event(
          add  onalF elds = request.add  onalF elds,
          user d = getUser d(t et),
          t  stamp = T  .now
        )
      }
    }
  }
}

object AsyncSetAdd  onalF eldsBu lder {
  type Type = AsyncSetAdd  onalF eldsRequest => Future[
    T etStoreEventOrRetry[AsyncSetAdd  onalF elds.Event]
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
        AsyncSetAdd  onalF elds.Event.fromAsyncRequest(request, user)
      }
  }
}
