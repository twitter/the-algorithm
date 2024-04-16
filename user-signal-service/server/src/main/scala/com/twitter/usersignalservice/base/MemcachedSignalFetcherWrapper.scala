package com.tw ter.users gnalserv ce
package base

 mport com.tw ter.f nagle. mcac d.{Cl ent =>  mcac dCl ent}
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.hash ng.KeyHas r
 mport com.tw ter. rm .store.common.Observed mcac dReadableStore
 mport com.tw ter.relevance_platform.common. nject on.LZ4 nject on
 mport com.tw ter.relevance_platform.common. nject on.SeqObject nject on
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.tw stly.common.User d
 mport com.tw ter.users gnalserv ce.thr ftscala.S gnal
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.T  r

/**
 * Use t  wrapper w n t  latency of t  s gnal fetc r  s too h gh (see BaseS gnalFetc r.T  out
 * ) and t  results from t  s gnal fetc r don't change often (e.g. results are generated from a
 * scald ng job sc duled each day).
 * @param  mcac dCl ent
 * @param baseS gnalFetc r
 * @param ttl
 * @param stats
 * @param t  r
 */
case class  mcac dS gnalFetc rWrapper(
   mcac dCl ent:  mcac dCl ent,
  baseS gnalFetc r: BaseS gnalFetc r,
  ttl: Durat on,
  stats: StatsRece ver,
  keyPref x: Str ng,
  t  r: T  r)
    extends BaseS gnalFetc r {
   mport  mcac dS gnalFetc rWrapper._
  overr de type RawS gnalType = baseS gnalFetc r.RawS gnalType

  overr de val na : Str ng = t .getClass.getCanon calNa 
  overr de val statsRece ver: StatsRece ver = stats.scope(na ).scope(baseS gnalFetc r.na )

  val underly ngStore: ReadableStore[User d, Seq[RawS gnalType]] = {
    val cac Underly ngStore = new ReadableStore[User d, Seq[RawS gnalType]] {
      overr de def get(user d: User d): Future[Opt on[Seq[RawS gnalType]]] =
        baseS gnalFetc r.getRawS gnals(user d)
    }
    Observed mcac dReadableStore.fromCac Cl ent(
      back ngStore = cac Underly ngStore,
      cac Cl ent =  mcac dCl ent,
      ttl = ttl)(
      value nject on = LZ4 nject on.compose(SeqObject nject on[RawS gnalType]()),
      statsRece ver = statsRece ver,
      keyToStr ng = { k: User d =>
        s"$keyPref x:${keyHas r.hashKey(k.toStr ng.getBytes)}"
      }
    )
  }

  overr de def getRawS gnals(user d: User d): Future[Opt on[Seq[RawS gnalType]]] =
    underly ngStore.get(user d)

  overr de def process(
    query: Query,
    rawS gnals: Future[Opt on[Seq[RawS gnalType]]]
  ): Future[Opt on[Seq[S gnal]]] = baseS gnalFetc r.process(query, rawS gnals)

}

object  mcac dS gnalFetc rWrapper {
  pr vate val keyHas r: KeyHas r = KeyHas r.FNV1A_64
}
