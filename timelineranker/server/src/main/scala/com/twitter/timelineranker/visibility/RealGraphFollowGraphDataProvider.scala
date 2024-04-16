package com.tw ter.t  l neranker.v s b l y

 mport com.tw ter.f nagle.stats.Stat
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.servo.repos ory.KeyValueRepos ory
 mport com.tw ter.servo.ut l.Gate
 mport com.tw ter.t  l neranker.core.FollowGraphData
 mport com.tw ter.t  l neranker.core.FollowGraphDataFuture
 mport com.tw ter.t  l nes.cl ents.soc algraph.Soc alGraphCl ent
 mport com.tw ter.t  l nes.model.User d
 mport com.tw ter.t  l nes.ut l.Fa lOpenHandler
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.Stopwatch
 mport com.tw ter.wtf.cand date.thr ftscala.Cand dateSeq

object RealGraphFollowGraphDataProv der {
  val EmptyRealGraphResponse = Cand dateSeq(N l)
}

/**
 * Wraps an underly ng FollowGraphDataProv der (wh ch  n pract ce w ll usually be a
 * [[SgsFollowGraphDataProv der]]) and supple nts t  l st of follow ngs prov ded by t 
 * underly ng prov der w h add  onal follow ngs fetc d from RealGraph  f   looks l ke t 
 * underly ng prov der d d not get t  full l st of t  user's follow ngs.
 *
 * F rst c cks w t r t  s ze of t  underly ng follow ng l st  s >= t  max requested follow ng
 * count, wh ch  mpl es that t re  re add  onal follow ngs beyond t  max requested count.  f so,
 * fetc s t  full set of follow ngs from RealGraph (go/realgraph), wh ch w ll be at most 2000.
 *
 * Because t  RealGraph dataset  s not realt   and thus can potent ally  nclude stale follow ngs,
 * t  prov der conf rms that t  follow ngs fetc d from RealGraph are val d us ng SGS's
 * getFollowOverlap  thod, and t n  rges t  val d RealGraph follow ngs w h t  underly ng
 * follow ngs.
 *
 * Note that t  supple nt ng  s expected to be very rare as most users do not have more than
 * t  max follow ngs   fetch from SGS. Also note that t  class  s ma nly  ntended for use
 *  n t  ho  t  l ne mater al zat on path, w h t  goal of prevent ng a case w re users
 * who follow a very large number of accounts may not see T ets from t  r earl er follows  f  
 * used SGS-based follow fetch ng alone.
 */
class RealGraphFollowGraphDataProv der(
  underly ng: FollowGraphDataProv der,
  realGraphCl ent: KeyValueRepos ory[Seq[User d], User d, Cand dateSeq],
  soc alGraphCl ent: Soc alGraphCl ent,
  supple ntFollowsW hRealGraphGate: Gate[User d],
  statsRece ver: StatsRece ver)
    extends FollowGraphDataProv der {
   mport RealGraphFollowGraphDataProv der._

  pr vate[t ] val scopedStatsRece ver = statsRece ver.scope("realGraphFollowGraphDataProv der")
  pr vate[t ] val requestCounter = scopedStatsRece ver.counter("requests")
  pr vate[t ] val atMaxCounter = scopedStatsRece ver.counter("followsAtMax")
  pr vate[t ] val totalLatencyStat = scopedStatsRece ver.stat("totalLatencyW nSupple nt ng")
  pr vate[t ] val supple ntLatencyStat = scopedStatsRece ver.stat("supple ntFollowsLatency")
  pr vate[t ] val realGraphResponseS zeStat = scopedStatsRece ver.stat("realGraphFollows")
  pr vate[t ] val realGraphEmptyCounter = scopedStatsRece ver.counter("realGraphEmpty")
  pr vate[t ] val nonOverlapp ngS zeStat = scopedStatsRece ver.stat("nonOverlapp ngFollows")

  pr vate[t ] val fa lOpenHandler = new Fa lOpenHandler(scopedStatsRece ver)

  overr de def get(user d: User d, maxFollow ngCount:  nt): Future[FollowGraphData] = {
    getAsync(user d, maxFollow ngCount).get()
  }

  overr de def getAsync(user d: User d, maxFollow ngCount:  nt): FollowGraphDataFuture = {
    val startT   = Stopwatch.t  M ll s()
    val underly ngResult = underly ng.getAsync(user d, maxFollow ngCount)
     f (supple ntFollowsW hRealGraphGate(user d)) {
      val supple ntedFollows = underly ngResult.follo dUser dsFuture.flatMap { sgsFollows =>
        supple ntFollowsW hRealGraph(user d, maxFollow ngCount, sgsFollows, startT  )
      }
      underly ngResult.copy(follo dUser dsFuture = supple ntedFollows)
    } else {
      underly ngResult
    }
  }

  overr de def getFollow ng(user d: User d, maxFollow ngCount:  nt): Future[Seq[User d]] = {
    val startT   = Stopwatch.t  M ll s()
    val underly ngFollows = underly ng.getFollow ng(user d, maxFollow ngCount)
     f (supple ntFollowsW hRealGraphGate(user d)) {
      underly ng.getFollow ng(user d, maxFollow ngCount).flatMap { sgsFollows =>
        supple ntFollowsW hRealGraph(user d, maxFollow ngCount, sgsFollows, startT  )
      }
    } else {
      underly ngFollows
    }
  }

  pr vate[t ] def supple ntFollowsW hRealGraph(
    user d: User d,
    maxFollow ngCount:  nt,
    sgsFollows: Seq[Long],
    startT  : Long
  ): Future[Seq[User d]] = {
    requestCounter. ncr()
     f (sgsFollows.s ze >= maxFollow ngCount) {
      atMaxCounter. ncr()
      val supple ntedFollowsFuture = realGraphCl ent(Seq(user d))
        .map(_.getOrElse(user d, EmptyRealGraphResponse))
        .map(_.cand dates.map(_.user d))
        .flatMap {
          case realGraphFollows  f realGraphFollows.nonEmpty =>
            realGraphResponseS zeStat.add(realGraphFollows.s ze)
            // F lter out "stale" follows from realgraph by c ck ng t m aga nst SGS
            val ver f edRealGraphFollows =
              soc alGraphCl ent.getFollowOverlap(user d, realGraphFollows)
            ver f edRealGraphFollows.map { follows =>
              val comb nedFollows = (sgsFollows ++ follows).d st nct
              val add  onalFollows = comb nedFollows.s ze - sgsFollows.s ze
               f (add  onalFollows > 0) nonOverlapp ngS zeStat.add(add  onalFollows)
              comb nedFollows
            }
          case _ =>
            realGraphEmptyCounter. ncr()
            Future.value(sgsFollows)
        }
        .onSuccess { _ => totalLatencyStat.add(Stopwatch.t  M ll s() - startT  ) }

      Stat.t  Future(supple ntLatencyStat) {
        fa lOpenHandler(supple ntedFollowsFuture) { _ => Future.value(sgsFollows) }
      }
    } else {
      Future.value(sgsFollows)
    }
  }

  overr de def getMutuallyFollow ngUser ds(
    user d: User d,
    follow ng ds: Seq[User d]
  ): Future[Set[User d]] = {
    underly ng.getMutuallyFollow ngUser ds(user d, follow ng ds)
  }
}
