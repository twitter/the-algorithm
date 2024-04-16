package com.tw ter.users gnalserv ce
package base

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.users gnalserv ce.thr ftscala.S gnal
 mport com.tw ter.ut l.Future
 mport com.tw ter.tw stly.common.User d
 mport com.tw ter.users gnalserv ce.thr ftscala.S gnalType
 mport com.tw ter.fr gate.common.base.Stats
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.users gnalserv ce.thr ftscala.Cl ent dent f er
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.T  r
 mport java. o.Ser al zable

case class Query(
  user d: User d,
  s gnalType: S gnalType,
  maxResults: Opt on[ nt],
  cl ent d: Cl ent dent f er = Cl ent dent f er.Unknown)

/**
 * A tra  that def nes a standard  nterface for t  s gnal fetc r
 *
 * Extends t  only w n all ot r tra s extend ng BaseS gnalFetc r do not apply to
 * y  use case.
 */
tra  BaseS gnalFetc r extends ReadableStore[Query, Seq[S gnal]] {
   mport BaseS gnalFetc r._

  /**
   * T  RawS gnalType  s t  output type of `getRawS gnals` and t   nput type of `process`.
   * Overr de   as y  own raw s gnal type to ma nta n  ta data wh ch can be used  n t 
   * step of `process`.
   * Note that t  RawS gnalType  s an  nter d ate data type  ntended to be small to avo d
   * b g data chunks be ng passed over funct ons or be ng  mcac d.
   */
  type RawS gnalType <: Ser al zable

  def na : Str ng
  def statsRece ver: StatsRece ver
  def t  r: T  r

  /**
   * T  funct on  s called by t  top level class to fetch s gnals.   executes t  p pel ne to
   * fetch raw s gnals, process and transform t  s gnals. Except ons and t  out control are
   * handled  re.
   * @param query
   * @return Future[Opt on[Seq[S gnal]]]
   */
  overr de def get(query: Query): Future[Opt on[Seq[S gnal]]] = {
    val cl entStatsRece ver = statsRece ver.scope(query.cl ent d.na ).scope(query.s gnalType.na )
    Stats
      .track ems(cl entStatsRece ver) {
        val rawS gnals = getRawS gnals(query.user d)
        val s gnals = process(query, rawS gnals)
        s gnals
      }.ra seW h n(T  out)(t  r).handle {
        case e =>
          cl entStatsRece ver.scope("Fetc rExcept ons").counter(e.getClass.getCanon calNa ). ncr()
          EmptyResponse
      }
  }

  /**
   * Overr de t  funct on to def ne how to fetch t  raw s gnals from any store
   * Note that t  RawS gnalType  s an  nter d ate data type  ntended to be small to avo d
   * b g data chunks be ng passed over funct ons or be ng  mcac d.
   * @param user d
   * @return Future[Opt on[Seq[RawS gnalType]]]
   */
  def getRawS gnals(user d: User d): Future[Opt on[Seq[RawS gnalType]]]

  /**
   * Overr de t  funct on to def ne how to process t  raw s gnals and transform t m to s gnals.
   * @param query
   * @param rawS gnals
   * @return Future[Opt on[Seq[S gnal]]]
   */
  def process(
    query: Query,
    rawS gnals: Future[Opt on[Seq[RawS gnalType]]]
  ): Future[Opt on[Seq[S gnal]]]
}

object BaseS gnalFetc r {
  val T  out: Durat on = 20.m ll seconds
  val EmptyResponse: Opt on[Seq[S gnal]] = So (Seq.empty[S gnal])
}
