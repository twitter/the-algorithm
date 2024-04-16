package com.tw ter.users gnalserv ce.s gnals

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.s mclusters_v2.common.User d
 mport com.tw ter.s mclusters_v2.thr ftscala. nternal d
 mport com.tw ter.strato.cl ent.Cl ent
 mport com.tw ter.strato.data.Conv
 mport com.tw ter.strato.thr ft.ScroogeConv
 mport com.tw ter.tw stly.thr ftscala.RecentNegat veEngagedT et
 mport com.tw ter.tw stly.thr ftscala.T etNegat veEngage ntType
 mport com.tw ter.tw stly.thr ftscala.UserRecentNegat veEngagedT ets
 mport com.tw ter.users gnalserv ce.base.Query
 mport com.tw ter.users gnalserv ce.base.StratoS gnalFetc r
 mport com.tw ter.users gnalserv ce.thr ftscala.S gnal
 mport com.tw ter.users gnalserv ce.thr ftscala.S gnalType
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.T  r
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
case class Negat veEngagedT etFetc r @ nject() (
  stratoCl ent: Cl ent,
  t  r: T  r,
  stats: StatsRece ver)
    extends StratoS gnalFetc r[(User d, Long), Un , UserRecentNegat veEngagedT ets] {

   mport Negat veEngagedT etFetc r._

  overr de type RawS gnalType = RecentNegat veEngagedT et
  overr de val na : Str ng = t .getClass.getCanon calNa 
  overr de val statsRece ver: StatsRece ver = stats.scope(na )

  overr de val stratoColumnPath: Str ng = stratoPath
  overr de val stratoV ew: Un  = None

  overr de protected val keyConv: Conv[(User d, Long)] = Conv.ofType
  overr de protected val v ewConv: Conv[Un ] = Conv.ofType
  overr de protected val valueConv: Conv[UserRecentNegat veEngagedT ets] =
    ScroogeConv.fromStruct[UserRecentNegat veEngagedT ets]

  overr de protected def toStratoKey(user d: User d): (User d, Long) = (user d, defaultVers on)

  overr de protected def toRawS gnals(
    stratoValue: UserRecentNegat veEngagedT ets
  ): Seq[RecentNegat veEngagedT et] = {
    stratoValue.recentNegat veEngagedT ets
  }

  overr de def process(
    query: Query,
    rawS gnals: Future[Opt on[Seq[RecentNegat veEngagedT et]]]
  ): Future[Opt on[Seq[S gnal]]] = {
    rawS gnals.map {
      _.map { s gnals =>
        s gnals
          .f lter(s gnal => negat veEngagedT etTypeF lter(query.s gnalType, s gnal))
          .map { s gnal =>
            S gnal(
              query.s gnalType,
              s gnal.engagedAt,
              So ( nternal d.T et d(s gnal.t et d))
            )
          }
          .groupBy(_.target nternal d) // groupBy  f t re's dupl cated author ds
          .mapValues(_.maxBy(_.t  stamp))
          .values
          .toSeq
          .sortBy(-_.t  stamp)
          .take(query.maxResults.getOrElse( nt.MaxValue))
      }
    }
  }
}

object Negat veEngagedT etFetc r {

  val stratoPath = "recom ndat ons/tw stly/userRecentNegat veEngagedT ets"
  pr vate val defaultVers on = 0L

  pr vate def negat veEngagedT etTypeF lter(
    s gnalType: S gnalType,
    s gnal: RecentNegat veEngagedT et
  ): Boolean = {
    s gnalType match {
      case S gnalType.T etDontL ke =>
        s gnal.engage ntType == T etNegat veEngage ntType.DontL ke
      case S gnalType.T etSeeFe r =>
        s gnal.engage ntType == T etNegat veEngage ntType.SeeFe r
      case S gnalType.T etReport =>
        s gnal.engage ntType == T etNegat veEngage ntType.ReportCl ck
      case S gnalType.Negat veEngagedT et d => true
      case _ => false
    }
  }

}
