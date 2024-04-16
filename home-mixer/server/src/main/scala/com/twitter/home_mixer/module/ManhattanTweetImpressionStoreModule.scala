package com.tw ter.ho _m xer.module

 mport com.google. nject.Prov des
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter. nject.annotat ons.Flag
 mport com.tw ter.storage.cl ent.manhattan.kv.Guarantee
 mport com.tw ter.storehaus_ nternal.manhattan.ManhattanClusters
 mport com.tw ter.t  l nes.cl ents.manhattan.mhv3.ManhattanCl entBu lder
 mport com.tw ter.t  l nes. mpress onstore.store.ManhattanT et mpress onStoreCl entConf g
 mport com.tw ter.t  l nes. mpress onstore.store.ManhattanT et mpress onStoreCl ent
 mport com.tw ter.ut l.Durat on
 mport javax. nject.S ngleton

object ManhattanT et mpress onStoreModule extends Tw terModule {

  pr vate val ProdApp d = "t  l nes_t et_ mpress on_store_v2"
  pr vate val ProdDataset = "t  l nes_t et_ mpress ons_v2"
  pr vate val Stag ngApp d = "t  l nes_t et_ mpress on_store_stag ng"
  pr vate val Stag ngDataset = "t  l nes_t et_ mpress ons_stag ng"
  pr vate val StatsScope = "manhattanT et mpress onStoreCl ent"
  pr vate val DefaultTTL = 2.days
  pr vate f nal val T  out = "mh_ mpress on_store.t  out"

  flag[Durat on](T  out, 150.m ll s, "T  out per request")

  @Prov des
  @S ngleton
  def prov desManhattanT et mpress onStoreCl ent(
    @Flag(T  out) t  out: Durat on,
    serv ce dent f er: Serv ce dent f er,
    statsRece ver: StatsRece ver
  ): ManhattanT et mpress onStoreCl ent = {

    val (app d, dataset) = serv ce dent f er.env ron nt.toLo rCase match {
      case "prod" => (ProdApp d, ProdDataset)
      case _ => (Stag ngApp d, Stag ngDataset)
    }

    val conf g = ManhattanT et mpress onStoreCl entConf g(
      cluster = ManhattanClusters.nash,
      app d = app d,
      dataset = dataset,
      statsScope = StatsScope,
      defaultGuarantee = Guarantee.SoftDcRead Wr es,
      defaultMaxT  out = t  out,
      maxRetryCount = 2,
       sReadOnly = false,
      serv ce dent f er = serv ce dent f er,
      ttl = DefaultTTL
    )

    val manhattanEndpo nt = ManhattanCl entBu lder.bu ldManhattanEndpo nt(conf g, statsRece ver)
    ManhattanT et mpress onStoreCl ent(conf g, manhattanEndpo nt, statsRece ver)
  }
}
