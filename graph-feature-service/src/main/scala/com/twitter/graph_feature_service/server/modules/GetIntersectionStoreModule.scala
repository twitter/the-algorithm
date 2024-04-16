package com.tw ter.graph_feature_serv ce.server.modules

 mport com.google. nject.Prov des
 mport com.tw ter.b ject on.scrooge.CompactScalaCodec
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.graph_feature_serv ce.common.Conf gs._
 mport com.tw ter.graph_feature_serv ce.server.stores.Get ntersect onStore
 mport com.tw ter.graph_feature_serv ce.server.stores.Get ntersect onStore.Get ntersect onQuery
 mport com.tw ter.graph_feature_serv ce.thr ftscala.Cac d ntersect onResult
 mport com.tw ter. rm .store.common.Observed mcac dReadableStore
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter. nject.annotat ons.Flag
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.storehaus_ nternal. mcac . mcac Store
 mport com.tw ter.storehaus_ nternal.ut l.{Cl entNa , ZkEndPo nt}
 mport com.tw ter.ut l.Durat on
 mport javax. nject.{Na d, S ngleton}

/**
 *  n  al ze t   mCac  based Get ntersect onStore.
 * T  Key of  mCac   s User d~Cand date d~FeatureTypes~ ntersect on dL m .
 */
object Get ntersect onStoreModule extends Tw terModule {

  pr vate[t ] val requestT  out: Durat on = 25.m ll s
  pr vate[t ] val retr es:  nt = 0

  @Prov des
  @Na d("ReadThroughGet ntersect onStore")
  @S ngleton
  def prov deReadThroughGet ntersect onStore(
    graphFeatureServ ceWorkerCl ents: GraphFeatureServ ceWorkerCl ents,
    serv ce dent f er: Serv ce dent f er,
    @Flag(ServerFlagNa s. mCac Cl entNa )  mCac Na : Str ng,
    @Flag(ServerFlagNa s. mCac Path)  mCac Path: Str ng
  )(
     mpl c  statsRece ver: StatsRece ver
  ): ReadableStore[Get ntersect onQuery, Cac d ntersect onResult] = {
    bu ld mcac Store(
      graphFeatureServ ceWorkerCl ents,
       mCac Na ,
       mCac Path,
      serv ce dent f er)
  }

  @Prov des
  @Na d("BypassCac Get ntersect onStore")
  @S ngleton
  def prov deReadOnlyGet ntersect onStore(
    graphFeatureServ ceWorkerCl ents: GraphFeatureServ ceWorkerCl ents,
  )(
     mpl c  statsRece ver: StatsRece ver
  ): ReadableStore[Get ntersect onQuery, Cac d ntersect onResult] = {
    // Bypass t   mcac .
    Get ntersect onStore(graphFeatureServ ceWorkerCl ents, statsRece ver)
  }

  pr vate[t ] def bu ld mcac Store(
    graphFeatureServ ceWorkerCl ents: GraphFeatureServ ceWorkerCl ents,
     mCac Na : Str ng,
     mCac Path: Str ng,
    serv ce dent f er: Serv ce dent f er,
  )(
     mpl c  statsRece ver: StatsRece ver
  ): ReadableStore[Get ntersect onQuery, Cac d ntersect onResult] = {
    val back ngStore = Get ntersect onStore(graphFeatureServ ceWorkerCl ents, statsRece ver)

    val cac Cl ent =  mcac Store. mcac dCl ent(
      na  = Cl entNa ( mCac Na ),
      dest = ZkEndPo nt( mCac Path),
      t  out = requestT  out,
      retr es = retr es,
      serv ce dent f er = serv ce dent f er,
      statsRece ver = statsRece ver
    )

    Observed mcac dReadableStore.fromCac Cl ent[Get ntersect onQuery, Cac d ntersect onResult](
      back ngStore = back ngStore,
      cac Cl ent = cac Cl ent,
      ttl =  mCac TTL
    )(
      value nject on = LZ4 nject on.compose(CompactScalaCodec(Cac d ntersect onResult)),
      statsRece ver = statsRece ver.scope(" m_cac "),
      keyToStr ng = { key =>
        s"L~${key.user d}~${key.cand date d}~${key.featureTypesStr ng}~${key. ntersect on dL m }"
      }
    )
  }
}
