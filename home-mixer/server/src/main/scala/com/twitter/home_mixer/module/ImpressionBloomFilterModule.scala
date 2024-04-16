package com.tw ter.ho _m xer.module

 mport com.google. nject.Prov des
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter. nject.annotat ons.Flag
 mport com.tw ter.storage.cl ent.manhattan.kv.Guarantee
 mport com.tw ter.storehaus_ nternal.manhattan.ManhattanClusters
 mport com.tw ter.t  l nes.cl ents.manhattan.store._
 mport com.tw ter.t  l nes. mpress onbloomf lter.{thr ftscala => blm}
 mport com.tw ter.t  l nes. mpress onstore. mpress onbloomf lter. mpress onBloomF lterManhattanKeyValueDescr ptor
 mport com.tw ter.ut l.Durat on
 mport javax. nject.S ngleton

object  mpress onBloomF lterModule extends Tw terModule {

  pr vate val ProdApp d = " mpress on_bloom_f lter_store"
  pr vate val ProdDataset = " mpress on_bloom_f lter"
  pr vate val Stag ngApp d = " mpress on_bloom_f lter_store_stag ng"
  pr vate val Stag ngDataset = " mpress on_bloom_f lter_stag ng"
  pr vate val Cl entStatsScope = "t etBloomF lter mpress onManhattanCl ent"
  pr vate val DefaultTTL = 7.days
  pr vate f nal val T  out = "mh_ mpress on_store_bloom_f lter.t  out"

  flag[Durat on](T  out, 150.m ll s, "T  out per request")

  @Prov des
  @S ngleton
  def prov des mpress onBloomF lter(
    @Flag(T  out) t  out: Durat on,
    serv ce dent f er: Serv ce dent f er,
    statsRece ver: StatsRece ver
  ): ManhattanStoreCl ent[blm. mpress onBloomF lterKey, blm. mpress onBloomF lterSeq] = {
    val (app d, dataset) = serv ce dent f er.env ron nt.toLo rCase match {
      case "prod" => (ProdApp d, ProdDataset)
      case _ => (Stag ngApp d, Stag ngDataset)
    }

     mpl c  val manhattanKeyValueDescr ptor:  mpress onBloomF lterManhattanKeyValueDescr ptor =
       mpress onBloomF lterManhattanKeyValueDescr ptor(
        dataset = dataset,
        ttl = DefaultTTL
      )

    ManhattanStoreCl entBu lder.bu ldManhattanCl ent(
      serv ce dent f er = serv ce dent f er,
      cluster = ManhattanClusters.nash,
      app d = app d,
      defaultMaxT  out = t  out,
      maxRetryCount = 2,
      defaultGuarantee = So (Guarantee.SoftDcRead Wr es),
       sReadOnly = false,
      statsScope = Cl entStatsScope,
      statsRece ver = statsRece ver
    )
  }
}
