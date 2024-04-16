package com.tw ter.users gnalserv ce.s gnals

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.soc algraph.thr ftscala.Relat onsh pType
 mport com.tw ter.soc algraph.thr ftscala.Soc alGraphServ ce
 mport com.tw ter.tw stly.common.User d
 mport com.tw ter.users gnalserv ce.base.BaseS gnalFetc r
 mport com.tw ter.users gnalserv ce.base.Query
 mport com.tw ter.users gnalserv ce.s gnals.common.SGSUt ls
 mport com.tw ter.users gnalserv ce.thr ftscala.S gnal
 mport com.tw ter.users gnalserv ce.thr ftscala.S gnalType
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.T  r
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
case class AccountBlocksFetc r @ nject() (
  sgsCl ent: Soc alGraphServ ce. thodPerEndpo nt,
  t  r: T  r,
  stats: StatsRece ver)
    extends BaseS gnalFetc r {

  overr de type RawS gnalType = S gnal
  overr de val na : Str ng = t .getClass.getCanon calNa 
  overr de val statsRece ver: StatsRece ver = stats.scope(t .na )

  overr de def getRawS gnals(
    user d: User d
  ): Future[Opt on[Seq[RawS gnalType]]] = {
    SGSUt ls.getSGSRawS gnals(user d, sgsCl ent, Relat onsh pType.Block ng, S gnalType.AccountBlock)
  }

  overr de def process(
    query: Query,
    rawS gnals: Future[Opt on[Seq[RawS gnalType]]]
  ): Future[Opt on[Seq[S gnal]]] = {
    rawS gnals.map(_.map(_.take(query.maxResults.getOrElse( nt.MaxValue))))
  }
}
