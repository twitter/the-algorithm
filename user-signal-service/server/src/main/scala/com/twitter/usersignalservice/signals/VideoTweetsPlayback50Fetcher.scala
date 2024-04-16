package com.tw ter.users gnalserv ce.s gnals

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.tw stly.common.User d
 mport com.tw ter.tw stly.thr ftscala.UserRecentV deoV ewT ets
 mport com.tw ter.tw stly.thr ftscala.V deoV ewEngage ntType
 mport com.tw ter.users gnalserv ce.base.Query
 mport com.tw ter.users gnalserv ce.thr ftscala.S gnal
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.T  r
 mport com.tw ter.tw stly.thr ftscala.RecentV deoV ewT et
 mport com.tw ter.users gnalserv ce.thr ftscala.S gnalType
 mport com.tw ter.s mclusters_v2.thr ftscala. nternal d
 mport com.tw ter.strato.cl ent.Cl ent
 mport com.tw ter.strato.data.Conv
 mport com.tw ter.strato.thr ft.ScroogeConv
 mport com.tw ter.users gnalserv ce.base.StratoS gnalFetc r
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
case class V deoT etsPlayback50Fetc r @ nject() (
  stratoCl ent: Cl ent,
  t  r: T  r,
  stats: StatsRece ver)
    extends StratoS gnalFetc r[
      (User d, V deoV ewEngage ntType),
      Un ,
      UserRecentV deoV ewT ets
    ] {
   mport V deoT etsPlayback50Fetc r._

  overr de type RawS gnalType = RecentV deoV ewT et
  overr de def na : Str ng = t .getClass.getCanon calNa 
  overr de def statsRece ver: StatsRece ver = stats.scope(na )

  overr de val stratoColumnPath: Str ng = StratoColumn
  overr de val stratoV ew: Un  = None
  overr de protected val keyConv: Conv[(User d, V deoV ewEngage ntType)] = Conv.ofType
  overr de protected val v ewConv: Conv[Un ] = Conv.ofType
  overr de protected val valueConv: Conv[UserRecentV deoV ewT ets] =
    ScroogeConv.fromStruct[UserRecentV deoV ewT ets]

  overr de protected def toStratoKey(user d: User d): (User d, V deoV ewEngage ntType) =
    (user d, V deoV ewEngage ntType.V deoPlayback50)

  overr de protected def toRawS gnals(
    stratoValue: UserRecentV deoV ewT ets
  ): Seq[RecentV deoV ewT et] = stratoValue.recentEngagedT ets

  overr de def process(
    query: Query,
    rawS gnals: Future[Opt on[Seq[RecentV deoV ewT et]]]
  ): Future[Opt on[Seq[S gnal]]] = rawS gnals.map {
    _.map {
      _.f lter(v deoV ew =>
        !v deoV ew. sPromotedT et && v deoV ew.v deoDurat onSeconds >= M nV deoDurat onSeconds)
        .map { rawS gnal =>
          S gnal(
            S gnalType.V deoV ew90dPlayback50V1,
            rawS gnal.engagedAt,
            So ( nternal d.T et d(rawS gnal.t et d)))
        }.take(query.maxResults.getOrElse( nt.MaxValue))
    }
  }

}

object V deoT etsPlayback50Fetc r {
  pr vate val StratoColumn = "recom ndat ons/tw stly/userRecentV deoV ewT etEngage nts"
  pr vate val M nV deoDurat onSeconds = 10
}
