package com.tw ter.t etyp e
package handler

 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.repos ory._
 mport com.tw ter.t etyp e.store.ScrubGeo
 mport com.tw ter.t etyp e.store.ScrubGeoUpdateUserT  stamp
 mport com.tw ter.t etyp e.thr ftscala.DeleteLocat onData
 mport com.tw ter.t etyp e.thr ftscala.GeoScrub

/**
 * Create t  appropr ate ScrubGeo.Event for a GeoScrub request.
 */
object ScrubGeoEventBu lder {
  val userQueryOpt ons: UserQueryOpt ons =
    UserQueryOpt ons(
      Set(UserF eld.Safety, UserF eld.Roles),
      UserV s b l y.All
    )

  pr vate def userLoader(
    stats: StatsRece ver,
    userRepo: UserRepos ory.Opt onal
  ): User d => Future[Opt on[User]] = {
    val userNotFoundCounter = stats.counter("user_not_found")
    user d =>
      St ch.run(
        userRepo(UserKey(user d), userQueryOpt ons)
          .onSuccess(userOpt =>  f (userOpt. sEmpty) userNotFoundCounter. ncr())
      )
  }

  object UpdateUserT  stamp {
    type Type = DeleteLocat onData => Future[ScrubGeoUpdateUserT  stamp.Event]

    def apply(
      stats: StatsRece ver,
      userRepo: UserRepos ory.Opt onal,
    ): Type = {
      val t  stampD ffStat = stats.stat("now_delta_ms")
      val loadUser = userLoader(stats, userRepo)
      request: DeleteLocat onData =>
        loadUser(request.user d).map { userOpt =>
          // delta bet en users request ng delet on and t  t     publ sh to T etEvents
          t  stampD ffStat.add((T  .now. nM ll s - request.t  stampMs).toFloat)
          ScrubGeoUpdateUserT  stamp.Event(
            user d = request.user d,
            t  stamp = T  .fromM ll seconds(request.t  stampMs),
            optUser = userOpt
          )
        }
    }
  }

  object ScrubT ets {
    type Type = GeoScrub => Future[ScrubGeo.Event]

    def apply(stats: StatsRece ver, userRepo: UserRepos ory.Opt onal): Type = {
      val loadUser = userLoader(stats, userRepo)
      geoScrub =>
        loadUser(geoScrub.user d).map { userOpt =>
          ScrubGeo.Event(
            t et dSet = geoScrub.status ds.toSet,
            user d = geoScrub.user d,
            enqueueMax = geoScrub.hoseb rdEnqueue,
            optUser = userOpt,
            t  stamp = T  .now
          )
        }
    }
  }
}
