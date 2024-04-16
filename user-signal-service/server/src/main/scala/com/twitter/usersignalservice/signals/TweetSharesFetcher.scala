package com.tw ter.users gnalserv ce.s gnals

 mport com.tw ter.b ject on.Codec
 mport com.tw ter.b ject on.scrooge.B naryScalaCodec
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.onboard ng.relevance.t et_engage nt.thr ftscala.Engage nt dent f er
 mport com.tw ter.onboard ng.relevance.t et_engage nt.thr ftscala.T etEngage nt
 mport com.tw ter.onboard ng.relevance.t et_engage nt.thr ftscala.T etEngage nts
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal nject on.Long2B gEnd an
 mport com.tw ter.s mclusters_v2.thr ftscala. nternal d
 mport com.tw ter.storage.cl ent.manhattan.kv.ManhattanKVCl entMtlsParams
 mport com.tw ter.storehaus_ nternal.manhattan.Apollo
 mport com.tw ter.storehaus_ nternal.manhattan.ManhattanCluster
 mport com.tw ter.tw stly.common.User d
 mport com.tw ter.users gnalserv ce.base.ManhattanS gnalFetc r
 mport com.tw ter.users gnalserv ce.base.Query
 mport com.tw ter.users gnalserv ce.thr ftscala.S gnal
 mport com.tw ter.users gnalserv ce.thr ftscala.S gnalType
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.T  r
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
case class T etSharesFetc r @ nject() (
  manhattanKVCl entMtlsParams: ManhattanKVCl entMtlsParams,
  t  r: T  r,
  stats: StatsRece ver)
    extends ManhattanS gnalFetc r[Long, T etEngage nts] {

   mport T etSharesFetc r._

  overr de type RawS gnalType = T etEngage nt

  overr de def na : Str ng = t .getClass.getCanon calNa 

  overr de def statsRece ver: StatsRece ver = stats.scope(na )

  overr de protected def manhattanApp d: Str ng = MHApp d

  overr de protected def manhattanDatasetNa : Str ng = MHDatasetNa 

  overr de protected def manhattanCluster d: ManhattanCluster = Apollo

  overr de protected def manhattanKeyCodec: Codec[Long] = Long2B gEnd an

  overr de protected def manhattanRawS gnalCodec: Codec[T etEngage nts] = B naryScalaCodec(
    T etEngage nts)

  overr de protected def toManhattanKey(user d: User d): Long = user d

  overr de protected def toRawS gnals(
    manhattanValue: T etEngage nts
  ): Seq[T etEngage nt] = manhattanValue.t etEngage nts

  overr de def process(
    query: Query,
    rawS gnals: Future[Opt on[Seq[T etEngage nt]]]
  ): Future[Opt on[Seq[S gnal]]] = {
    rawS gnals.map {
      _.map {
        _.collect {
          case t etEngage nt  f (t etEngage nt.engage ntType == Engage nt dent f er.Share) =>
            S gnal(
              S gnalType.T etShareV1,
              t etEngage nt.t  stampMs,
              So ( nternal d.T et d(t etEngage nt.t et d)))
        }.sortBy(-_.t  stamp).take(query.maxResults.getOrElse( nt.MaxValue))
      }
    }
  }
}

object T etSharesFetc r {
  pr vate val MHApp d = "uss_prod_apollo"
  pr vate val MHDatasetNa  = "t et_share_engage nts"
}
