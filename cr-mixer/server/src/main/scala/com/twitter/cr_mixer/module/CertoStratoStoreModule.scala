package com.tw ter.cr_m xer.module

 mport com.google. nject.Prov des
 mport com.google. nject.S ngleton
 mport com.google. nject.na .Na d
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.cr_m xer.model.ModuleNa s
 mport com.tw ter.cr_m xer.s m lar y_eng ne.S m lar yEng ne.keyHas r
 mport com.tw ter.f nagle. mcac d.{Cl ent =>  mcac dCl ent}
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter. rm .store.common.ObservedCac dReadableStore
 mport com.tw ter. rm .store.common.Observed mcac dReadableStore
 mport com.tw ter. rm .store.common.ObservedReadableStore
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.relevance_platform.common. nject on.LZ4 nject on
 mport com.tw ter.relevance_platform.common. nject on.SeqObject nject on
 mport com.tw ter.s mclusters_v2.thr ftscala.Top c d
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.strato.cl ent.Cl ent
 mport com.tw ter.top c_recos.stores.CertoTop cTopKT etsStore
 mport com.tw ter.top c_recos.thr ftscala.T etW hScores

object CertoStratoStoreModule extends Tw terModule {

  @Prov des
  @S ngleton
  @Na d(ModuleNa s.CertoStratoStoreNa )
  def prov desCertoStratoStore(
    @Na d(ModuleNa s.Un f edCac ) crM xerUn f edCac Cl ent:  mcac dCl ent,
    stratoCl ent: Cl ent,
    statsRece ver: StatsRece ver
  ): ReadableStore[Top c d, Seq[T etW hScores]] = {
    val certoStore = ObservedReadableStore(CertoTop cTopKT etsStore.prodStore(stratoCl ent))(
      statsRece ver.scope(ModuleNa s.CertoStratoStoreNa )).mapValues { topKT etsW hScores =>
      topKT etsW hScores.topT etsByFollo rL2Normal zedCos neS m lar yScore
    }

    val  mCac dStore = Observed mcac dReadableStore
      .fromCac Cl ent(
        back ngStore = certoStore,
        cac Cl ent = crM xerUn f edCac Cl ent,
        ttl = 10.m nutes
      )(
        value nject on = LZ4 nject on.compose(SeqObject nject on[T etW hScores]()),
        statsRece ver = statsRece ver.scope(" mcac d_certo_store"),
        keyToStr ng = { k => s"certo:${keyHas r.hashKey(k.toStr ng.getBytes)}" }
      )

    ObservedCac dReadableStore.from[Top c d, Seq[T etW hScores]](
       mCac dStore,
      ttl = 5.m nutes,
      maxKeys = 100000, // ~150MB max
      cac Na  = "certo_ n_ mory_cac ",
      w ndowS ze = 10000L
    )(statsRece ver.scope("certo_ n_ mory_cac "))
  }
}
