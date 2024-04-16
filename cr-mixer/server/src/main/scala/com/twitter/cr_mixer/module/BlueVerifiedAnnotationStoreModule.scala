package com.tw ter.cr_m xer.module

 mport com.google. nject.Prov des
 mport com.google. nject.S ngleton
 mport com.google. nject.na .Na d
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.cr_m xer.model.ModuleNa s
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.data_p pel ne.scald ng.thr ftscala.BlueVer f edAnnotat onsV2
 mport com.tw ter.storage.cl ent.manhattan.kv.ManhattanKVCl entMtlsParams
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.storehaus_ nternal.manhattan.At na
 mport com.tw ter.storehaus_ nternal.manhattan.ManhattanRO
 mport com.tw ter.storehaus_ nternal.manhattan.ManhattanROConf g
 mport com.tw ter.storehaus_ nternal.ut l.Appl cat on D
 mport com.tw ter.storehaus_ nternal.ut l.DatasetNa 
 mport com.tw ter.storehaus_ nternal.ut l.HDFSPath
 mport com.tw ter.b ject on.scrooge.B naryScalaCodec
 mport com.tw ter. rm .store.common.ObservedCac dReadableStore

object BlueVer f edAnnotat onStoreModule extends Tw terModule {

  @Prov des
  @S ngleton
  @Na d(ModuleNa s.BlueVer f edAnnotat onStore)
  def prov desBlueVer f edAnnotat onStore(
    statsRece ver: StatsRece ver,
    manhattanKVCl entMtlsParams: ManhattanKVCl entMtlsParams,
  ): ReadableStore[Str ng, BlueVer f edAnnotat onsV2] = {

     mpl c  val valueCodec = new B naryScalaCodec(BlueVer f edAnnotat onsV2)

    val underly ngStore = ManhattanRO
      .getReadableStoreW hMtls[Str ng, BlueVer f edAnnotat onsV2](
        ManhattanROConf g(
          HDFSPath(""),
          Appl cat on D("content_recom nder_at na"),
          DatasetNa ("blue_ver f ed_annotat ons"),
          At na),
        manhattanKVCl entMtlsParams
      )

    ObservedCac dReadableStore.from(
      underly ngStore,
      ttl = 24.h s,
      maxKeys = 100000,
      w ndowS ze = 10000L,
      cac Na  = "blue_ver f ed_annotat on_cac "
    )(statsRece ver.scope(" n moryCac dBlueVer f edAnnotat onStore"))
  }
}
