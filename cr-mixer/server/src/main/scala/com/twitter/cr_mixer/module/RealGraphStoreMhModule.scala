package com.tw ter.cr_m xer.module

 mport com.google. nject.Prov des
 mport com.google. nject.S ngleton
 mport com.google. nject.na .Na d
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.s mclusters_v2.common.User d
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.cr_m xer.model.ModuleNa s
 mport com.tw ter.cr_m xer.param.dec der.CrM xerDec der
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f nagle. mcac d.{Cl ent =>  mcac dCl ent}
 mport com.tw ter.storage.cl ent.manhattan.kv.ManhattanKVCl entMtlsParams
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.storehaus_ nternal.manhattan.Apollo
 mport com.tw ter.storehaus_ nternal.manhattan.ManhattanRO
 mport com.tw ter.storehaus_ nternal.manhattan.ManhattanROConf g
 mport com.tw ter.storehaus_ nternal.ut l.Appl cat on D
 mport com.tw ter.storehaus_ nternal.ut l.DatasetNa 
 mport com.tw ter.storehaus_ nternal.ut l.HDFSPath
 mport com.tw ter.b ject on.scrooge.B naryScalaCodec
 mport com.tw ter.cr_m xer.param.dec der.Dec derKey
 mport com.tw ter. rm .store.common.Dec derableReadableStore
 mport com.tw ter. rm .store.common.Observed mcac dReadableStore
 mport com.tw ter.wtf.cand date.thr ftscala.Cand dateSeq

object RealGraphStoreMhModule extends Tw terModule {

  @Prov des
  @S ngleton
  @Na d(ModuleNa s.RealGraph nStore)
  def prov desRealGraphStoreMh(
    dec der: CrM xerDec der,
    statsRece ver: StatsRece ver,
    manhattanKVCl entMtlsParams: ManhattanKVCl entMtlsParams,
    @Na d(ModuleNa s.Un f edCac ) crM xerUn f edCac Cl ent:  mcac dCl ent,
  ): ReadableStore[User d, Cand dateSeq] = {

     mpl c  val valueCodec = new B naryScalaCodec(Cand dateSeq)
    val underly ngStore = ManhattanRO
      .getReadableStoreW hMtls[User d, Cand dateSeq](
        ManhattanROConf g(
          HDFSPath(""),
          Appl cat on D("cr_m xer_apollo"),
          DatasetNa ("real_graph_scores_apollo"),
          Apollo),
        manhattanKVCl entMtlsParams
      )

    val  mCac dStore = Observed mcac dReadableStore
      .fromCac Cl ent(
        back ngStore = underly ngStore,
        cac Cl ent = crM xerUn f edCac Cl ent,
        ttl = 24.h s,
      )(
        value nject on = valueCodec,
        statsRece ver = statsRece ver.scope(" mCac dUserRealGraphMh"),
        keyToStr ng = { k: User d => s"uRGraph/$k" }
      )

    Dec derableReadableStore(
       mCac dStore,
      dec der.dec derGateBu lder. dGate(Dec derKey.enableRealGraphMhStoreDec derKey),
      statsRece ver.scope("RealGraphMh")
    )
  }
}
