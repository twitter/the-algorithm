package com.tw ter.users gnalserv ce.base

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base.Stats
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.users gnalserv ce.thr ftscala.S gnal
 mport com.tw ter.users gnalserv ce.thr ftscala.S gnalType
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.T  r

/**
 * Comb ne a BaseS gnalFetc r w h a map of negat ve s gnalFetc rs. F lter out t  negat ve
 * s gnals from t  s gnals from BaseS gnalFetc r.
 */
case class F lteredS gnalFetc rController(
  back ngS gnalFetc r: BaseS gnalFetc r,
  or g nS gnalType: S gnalType,
  stats: StatsRece ver,
  t  r: T  r,
  f lterS gnalFetc rs: Map[S gnalType, BaseS gnalFetc r] =
    Map.empty[S gnalType, BaseS gnalFetc r])
    extends ReadableStore[Query, Seq[S gnal]] {
  val statsRece ver: StatsRece ver = stats.scope(t .getClass.getCanon calNa )

  overr de def get(query: Query): Future[Opt on[Seq[S gnal]]] = {
    val cl entStatsRece ver = statsRece ver.scope(query.s gnalType.na ).scope(query.cl ent d.na )
    Stats
      .track ems(cl entStatsRece ver) {
        val back ngS gnals =
          back ngS gnalFetc r.get(Query(query.user d, or g nS gnalType, None, query.cl ent d))
        val f lteredS gnals = f lter(query, back ngS gnals)
        f lteredS gnals
      }.ra seW h n(BaseS gnalFetc r.T  out)(t  r).handle {
        case e =>
          cl entStatsRece ver.scope("Fetc rExcept ons").counter(e.getClass.getCanon calNa ). ncr()
          BaseS gnalFetc r.EmptyResponse
      }
  }

  def f lter(
    query: Query,
    rawS gnals: Future[Opt on[Seq[S gnal]]]
  ): Future[Opt on[Seq[S gnal]]] = {
    Stats
      .track ems(statsRece ver) {
        val or g nS gnals = rawS gnals.map(_.getOrElse(Seq.empty[S gnal]))
        val f lterS gnals =
          Future
            .collect {
              f lterS gnalFetc rs.map {
                case (s gnalType, s gnalFetc r) =>
                  s gnalFetc r
                    .get(Query(query.user d, s gnalType, None, query.cl ent d))
                    .map(_.getOrElse(Seq.empty))
              }.toSeq
            }.map(_.flatten.toSet)
        val f lterS gnalsSet = f lterS gnals
          .map(_.flatMap(_.target nternal d))

        val or g nS gnalsW h d =
          or g nS gnals.map(_.map(s gnal => (s gnal, s gnal.target nternal d)))
        Future.jo n(or g nS gnalsW h d, f lterS gnalsSet).map {
          case (or g nS gnalsW h d, f lterS gnalsSet) =>
            So (
              or g nS gnalsW h d
                .collect {
                  case (s gnal,  nternal dOpt)
                       f  nternal dOpt.nonEmpty && !f lterS gnalsSet.conta ns( nternal dOpt.get) =>
                    s gnal
                }.take(query.maxResults.getOrElse( nt.MaxValue)))
        }
      }
  }

}
