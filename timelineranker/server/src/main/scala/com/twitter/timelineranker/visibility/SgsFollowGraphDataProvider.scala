package com.tw ter.t  l neranker.v s b l y

 mport com.tw ter.f nagle.stats.Stat
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.t  l neranker.core.FollowGraphData
 mport com.tw ter.t  l neranker.core.FollowGraphDataFuture
 mport com.tw ter.t  l nes.cl ents.soc algraph.ScopedSoc alGraphCl entFactory
 mport com.tw ter.t  l nes.model._
 mport com.tw ter.t  l nes.ut l.Fa lOpenHandler
 mport com.tw ter.t  l nes.ut l.stats._
 mport com.tw ter.t  l nes.v s b l y._
 mport com.tw ter.ut l.Future

object SgsFollowGraphDataProv der {
  val EmptyUser dsSet: Set[User d] = Set.empty[User d]
  val EmptyUser dsSetFuture: Future[Set[User d]] = Future.value(EmptyUser dsSet)
  val EmptyUser dsSeq: Seq[User d] = Seq.empty[User d]
  val EmptyUser dsSeqFuture: Future[Seq[User d]] = Future.value(EmptyUser dsSeq)
  val EmptyV s b l yProf les: Map[User d, V s b l yProf le] = Map.empty[User d, V s b l yProf le]
  val EmptyV s b l yProf lesFuture: Future[Map[User d, V s b l yProf le]] =
    Future.value(EmptyV s b l yProf les)
}

object SgsFollowGraphDataF elds extends Enu rat on {
  val Follo dUser ds: Value = Value
  val MutuallyFollow ngUser ds: Value = Value
  val MutedUser ds: Value = Value
  val Ret etsMutedUser ds: Value = Value

  val None: ValueSet = SgsFollowGraphDataF elds.ValueSet()

  def throw f nval d(f elds: SgsFollowGraphDataF elds.ValueSet): Un  = {
     f (f elds.conta ns(MutuallyFollow ngUser ds) && !f elds.conta ns(Follo dUser ds)) {
      throw new  llegalArgu ntExcept on(
        "MutuallyFollow ngUser ds f eld requ res Follo dUser ds f eld to be def ned."
      )
    }
  }
}

/**
 * Prov des  nformat on on t  follow graph of a g ven user.
 */
class SgsFollowGraphDataProv der(
  soc alGraphCl entFactory: ScopedSoc alGraphCl entFactory,
  v s b l yProf leHydratorFactory: V s b l yProf leHydratorFactory,
  f eldsToFetch: SgsFollowGraphDataF elds.ValueSet,
  scope: RequestScope,
  statsRece ver: StatsRece ver)
    extends FollowGraphDataProv der
    w h RequestStats {

  SgsFollowGraphDataF elds.throw f nval d(f eldsToFetch)

  pr vate[t ] val stats = scope.stats("followGraphDataProv der", statsRece ver)
  pr vate[t ] val scopedStatsRece ver = stats.scopedStatsRece ver

  pr vate[t ] val follow ngScope = scopedStatsRece ver.scope("follow ng")
  pr vate[t ] val follow ngLatencyStat = follow ngScope.stat(LatencyMs)
  pr vate[t ] val follow ngS zeStat = follow ngScope.stat(S ze)
  pr vate[t ] val follow ngTruncatedCounter = follow ngScope.counter("numTruncated")

  pr vate[t ] val mutuallyFollow ngScope = scopedStatsRece ver.scope("mutuallyFollow ng")
  pr vate[t ] val mutuallyFollow ngLatencyStat = mutuallyFollow ngScope.stat(LatencyMs)
  pr vate[t ] val mutuallyFollow ngS zeStat = mutuallyFollow ngScope.stat(S ze)

  pr vate[t ] val v s b l yScope = scopedStatsRece ver.scope("v s b l y")
  pr vate[t ] val v s b l yLatencyStat = v s b l yScope.stat(LatencyMs)
  pr vate[t ] val mutedStat = v s b l yScope.stat("muted")
  pr vate[t ] val ret etsMutedStat = v s b l yScope.stat("ret etsMuted")

  pr vate[t ] val soc alGraphCl ent = soc alGraphCl entFactory.scope(scope)
  pr vate[t ] val v s b l yProf leHydrator =
    createV s b l yProf leHydrator(v s b l yProf leHydratorFactory, scope, f eldsToFetch)

  pr vate[t ] val fa lOpenScope = scopedStatsRece ver.scope("fa lOpen")
  pr vate[t ] val mutuallyFollow ngHandler =
    new Fa lOpenHandler(fa lOpenScope, "mutuallyFollow ng")

  pr vate[t ] val obta nV s b l yProf les = f eldsToFetch.conta ns(
    SgsFollowGraphDataF elds.MutedUser ds
  ) || f eldsToFetch.conta ns(SgsFollowGraphDataF elds.Ret etsMutedUser ds)

  /**
   * Gets follow graph data for t  g ven user.
   *
   * @param user d user whose follow graph deta ls are to be obta ned.
   * @param maxFollow ngCount Max mum number of follo d user  Ds to fetch.
   *           f t  g ven user follows more than t se many users,
   *          t n t  most recent maxFollow ngCount users are returned.
   */
  def get(
    user d: User d,
    maxFollow ngCount:  nt
  ): Future[FollowGraphData] = {
    getAsync(
      user d,
      maxFollow ngCount
    ).get()
  }

  def getAsync(
    user d: User d,
    maxFollow ngCount:  nt
  ): FollowGraphDataFuture = {

    stats.statRequest()
    val follo dUser dsFuture =
       f (f eldsToFetch.conta ns(SgsFollowGraphDataF elds.Follo dUser ds)) {
        getFollow ng(user d, maxFollow ngCount)
      } else {
        SgsFollowGraphDataProv der.EmptyUser dsSeqFuture
      }

    val mutuallyFollow ngUser dsFuture =
       f (f eldsToFetch.conta ns(SgsFollowGraphDataF elds.MutuallyFollow ngUser ds)) {
        follo dUser dsFuture.flatMap { follo dUser ds =>
          getMutuallyFollow ngUser ds(user d, follo dUser ds)
        }
      } else {
        SgsFollowGraphDataProv der.EmptyUser dsSetFuture
      }

    val v s b l yProf lesFuture =  f (obta nV s b l yProf les) {
      follo dUser dsFuture.flatMap { follo dUser ds =>
        getV s b l yProf les(user d, follo dUser ds)
      }
    } else {
      SgsFollowGraphDataProv der.EmptyV s b l yProf lesFuture
    }

    val mutedUser dsFuture =  f (f eldsToFetch.conta ns(SgsFollowGraphDataF elds.MutedUser ds)) {
      getMutedUsers(v s b l yProf lesFuture).map { mutedUser ds =>
        mutedStat.add(mutedUser ds.s ze)
        mutedUser ds
      }
    } else {
      SgsFollowGraphDataProv der.EmptyUser dsSetFuture
    }

    val ret etsMutedUser dsFuture =
       f (f eldsToFetch.conta ns(SgsFollowGraphDataF elds.Ret etsMutedUser ds)) {
        getRet etsMutedUsers(v s b l yProf lesFuture).map { ret etsMutedUser ds =>
          ret etsMutedStat.add(ret etsMutedUser ds.s ze)
          ret etsMutedUser ds
        }
      } else {
        SgsFollowGraphDataProv der.EmptyUser dsSetFuture
      }

    FollowGraphDataFuture(
      user d,
      follo dUser dsFuture,
      mutuallyFollow ngUser dsFuture,
      mutedUser dsFuture,
      ret etsMutedUser dsFuture
    )
  }

  pr vate[t ] def getV s b l yProf les(
    user d: User d,
    follow ng ds: Seq[User d]
  ): Future[Map[User d, V s b l yProf le]] = {
    Stat.t  Future(v s b l yLatencyStat) {
      v s b l yProf leHydrator(So (user d), Future.value(follow ng ds.toSeq))
    }
  }

  def getFollow ng(user d: User d, maxFollow ngCount:  nt): Future[Seq[User d]] = {
    Stat.t  Future(follow ngLatencyStat) {
      //   fetch 1 more than t  l m  so that   can dec de  f   ended up
      // truncat ng t  follow ngs.
      val follow ng dsFuture = soc alGraphCl ent.getFollow ng(user d, So (maxFollow ngCount + 1))
      follow ng dsFuture.map { follow ng ds =>
        follow ngS zeStat.add(follow ng ds.length)
         f (follow ng ds.length > maxFollow ngCount) {
          follow ngTruncatedCounter. ncr()
          follow ng ds.take(maxFollow ngCount)
        } else {
          follow ng ds
        }
      }
    }
  }

  def getMutuallyFollow ngUser ds(
    user d: User d,
    follow ng ds: Seq[User d]
  ): Future[Set[User d]] = {
    Stat.t  Future(mutuallyFollow ngLatencyStat) {
      mutuallyFollow ngHandler {
        val mutuallyFollow ng dsFuture =
          soc alGraphCl ent.getFollowOverlap(follow ng ds.toSeq, user d)
        mutuallyFollow ng dsFuture.map { mutuallyFollow ng ds =>
          mutuallyFollow ngS zeStat.add(mutuallyFollow ng ds.s ze)
        }
        mutuallyFollow ng dsFuture
      } { e: Throwable => SgsFollowGraphDataProv der.EmptyUser dsSetFuture }
    }
  }

  pr vate[t ] def getRet etsMutedUsers(
    v s b l yProf lesFuture: Future[Map[User d, V s b l yProf le]]
  ): Future[Set[User d]] = {
    //  f t  hydrator  s not able to fetch ret ets-muted status,   default to true.
    getUsersMatch ngV s b l yPred cate(
      v s b l yProf lesFuture,
      (v s b l yProf le: V s b l yProf le) => v s b l yProf le.areRet etsMuted.getOrElse(true)
    )
  }

  pr vate[t ] def getMutedUsers(
    v s b l yProf lesFuture: Future[Map[User d, V s b l yProf le]]
  ): Future[Set[User d]] = {
    //  f t  hydrator  s not able to fetch muted status,   default to true.
    getUsersMatch ngV s b l yPred cate(
      v s b l yProf lesFuture,
      (v s b l yProf le: V s b l yProf le) => v s b l yProf le. sMuted.getOrElse(true)
    )
  }

  pr vate[t ] def getUsersMatch ngV s b l yPred cate(
    v s b l yProf lesFuture: Future[Map[User d, V s b l yProf le]],
    pred cate: (V s b l yProf le => Boolean)
  ): Future[Set[User d]] = {
    v s b l yProf lesFuture.map { v s b l yProf les =>
      v s b l yProf les
        .f lter {
          case (_, v s b l yProf le) =>
            pred cate(v s b l yProf le)
        }
        .collect { case (user d, _) => user d }
        .toSet
    }
  }

  pr vate[t ] def createV s b l yProf leHydrator(
    factory: V s b l yProf leHydratorFactory,
    scope: RequestScope,
    f eldsToFetch: SgsFollowGraphDataF elds.ValueSet
  ): V s b l yProf leHydrator = {
    val hydrat onProf leRequest = Hydrat onProf leRequest(
      getMuted = f eldsToFetch.conta ns(SgsFollowGraphDataF elds.MutedUser ds),
      getRet etsMuted = f eldsToFetch.conta ns(SgsFollowGraphDataF elds.Ret etsMutedUser ds)
    )
    factory(hydrat onProf leRequest, scope)
  }
}

class ScopedSgsFollowGraphDataProv derFactory(
  soc alGraphCl entFactory: ScopedSoc alGraphCl entFactory,
  v s b l yProf leHydratorFactory: V s b l yProf leHydratorFactory,
  f eldsToFetch: SgsFollowGraphDataF elds.ValueSet,
  statsRece ver: StatsRece ver)
    extends ScopedFactory[SgsFollowGraphDataProv der] {

  overr de def scope(scope: RequestScope): SgsFollowGraphDataProv der = {
    new SgsFollowGraphDataProv der(
      soc alGraphCl entFactory,
      v s b l yProf leHydratorFactory,
      f eldsToFetch,
      scope,
      statsRece ver
    )
  }
}
