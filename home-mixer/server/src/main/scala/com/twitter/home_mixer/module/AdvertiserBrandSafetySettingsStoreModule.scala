package com.tw ter.ho _m xer.module

 mport com.google. nject.Prov des

 mport com.tw ter.adserver.{thr ftscala => ads}
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.storage.cl ent.manhattan.kv.Guarantee
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.storehaus_ nternal.manhattan.ManhattanCluster
 mport com.tw ter.storehaus_ nternal.manhattan.ManhattanClusters
 mport com.tw ter.t  l nes.cl ents.ads.Advert serBrandSafetySett ngsStore
 mport com.tw ter.t  l nes.cl ents.manhattan.mhv3.ManhattanCl entBu lder
 mport com.tw ter.t  l nes.cl ents.manhattan.mhv3.ManhattanCl entConf gW hDataset
 mport com.tw ter.ut l.Durat on

 mport javax. nject.S ngleton

object Advert serBrandSafetySett ngsStoreModule extends Tw terModule {

  @Prov des
  @S ngleton
  def prov desAdvert serBrandSafetySett ngsStore(
     njectedServ ce dent f er: Serv ce dent f er,
    statsRece ver: StatsRece ver
  ): ReadableStore[Long, ads.Advert serBrandSafetySett ngs] = {
    val advert serBrandSafetySett ngsManhattanCl entConf g = new ManhattanCl entConf gW hDataset {
      overr de val cluster: ManhattanCluster = ManhattanClusters.apollo
      overr de val app d: Str ng = "brand_safety_apollo"
      overr de val dataset = "advert ser_brand_safety_sett ngs"
      overr de val statsScope: Str ng = "Advert serBrandSafetySett ngsManhattanCl ent"
      overr de val defaultGuarantee = Guarantee. ak
      overr de val defaultMaxT  out: Durat on = 100.m ll seconds
      overr de val maxRetryCount:  nt = 1
      overr de val  sReadOnly: Boolean = true
      overr de val serv ce dent f er: Serv ce dent f er =  njectedServ ce dent f er
    }

    val advert serBrandSafetySett ngsManhattanEndpo nt = ManhattanCl entBu lder
      .bu ldManhattanEndpo nt(advert serBrandSafetySett ngsManhattanCl entConf g, statsRece ver)

    val advert serBrandSafetySett ngsStore: ReadableStore[Long, ads.Advert serBrandSafetySett ngs] =
      Advert serBrandSafetySett ngsStore
        .cac d(
          advert serBrandSafetySett ngsManhattanEndpo nt,
          advert serBrandSafetySett ngsManhattanCl entConf g.dataset,
          ttl = 60.m nutes,
          maxKeys = 100000,
          w ndowS ze = 10L
        )(statsRece ver)

    advert serBrandSafetySett ngsStore
  }
}
