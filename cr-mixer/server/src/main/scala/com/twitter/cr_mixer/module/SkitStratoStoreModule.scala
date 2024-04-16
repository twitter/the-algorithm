package com.tw ter.cr_m xer.module

 mport com.google. nject.Prov des
 mport com.google. nject.S ngleton
 mport com.google. nject.na .Na d
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.cr_m xer.model.ModuleNa s
 mport com.tw ter.cr_m xer.s m lar y_eng ne.S m lar yEng ne.keyHas r
 mport com.tw ter.f nagle. mcac d.{Cl ent =>  mcac dCl ent}
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.store.strato.StratoFetchableStore
 mport com.tw ter. rm .store.common.ObservedCac dReadableStore
 mport com.tw ter. rm .store.common.Observed mcac dReadableStore
 mport com.tw ter. rm .store.common.ObservedReadableStore
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.relevance_platform.common. nject on.LZ4 nject on
 mport com.tw ter.relevance_platform.common. nject on.SeqObject nject on
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.strato.cl ent.Cl ent
 mport com.tw ter.top c_recos.thr ftscala.Top cTopT ets
 mport com.tw ter.top c_recos.thr ftscala.Top cT et
 mport com.tw ter.top c_recos.thr ftscala.Top cT etPart  onFlatKey

/**
 * Strato store that wraps t  top c top t ets p pel ne  ndexed from a Summ ngb rd job
 */
object Sk StratoStoreModule extends Tw terModule {

  val column = "recom ndat ons/top c_recos/top cTopT ets"

  @Prov des
  @S ngleton
  @Na d(ModuleNa s.Sk StratoStoreNa )
  def prov desSk StratoStore(
    @Na d(ModuleNa s.Un f edCac ) crM xerUn f edCac Cl ent:  mcac dCl ent,
    stratoCl ent: Cl ent,
    statsRece ver: StatsRece ver
  ): ReadableStore[Top cT etPart  onFlatKey, Seq[Top cT et]] = {
    val sk Store = ObservedReadableStore(
      StratoFetchableStore
        .w hUn V ew[Top cT etPart  onFlatKey, Top cTopT ets](stratoCl ent, column))(
      statsRece ver.scope(ModuleNa s.Sk StratoStoreNa )).mapValues { top cTopT ets =>
      top cTopT ets.topT ets
    }

    val  mCac dStore = Observed mcac dReadableStore
      .fromCac Cl ent(
        back ngStore = sk Store,
        cac Cl ent = crM xerUn f edCac Cl ent,
        ttl = 10.m nutes
      )(
        value nject on = LZ4 nject on.compose(SeqObject nject on[Top cT et]()),
        statsRece ver = statsRece ver.scope(" mcac d_sk _store"),
        keyToStr ng = { k => s"sk :${keyHas r.hashKey(k.toStr ng.getBytes)}" }
      )

    ObservedCac dReadableStore.from[Top cT etPart  onFlatKey, Seq[Top cT et]](
       mCac dStore,
      ttl = 5.m nutes,
      maxKeys = 100000, // ~150MB max
      cac Na  = "sk _ n_ mory_cac ",
      w ndowS ze = 10000L
    )(statsRece ver.scope("sk _ n_ mory_cac "))
  }
}
