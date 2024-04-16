package com.tw ter.ho _m xer.module

 mport com.google. nject.Prov des
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter. nject.annotat ons.Flag
 mport com.tw ter.t  l nem xer.cl ents.pers stence.T  l nePers stenceManhattanCl entBu lder
 mport com.tw ter.t  l nem xer.cl ents.pers stence.T  l nePers stenceManhattanCl entConf g
 mport com.tw ter.t  l nem xer.cl ents.pers stence.T  l neResponseBatc sCl ent
 mport com.tw ter.t  l nem xer.cl ents.pers stence.T  l neResponseV3
 mport com.tw ter.ut l.Durat on
 mport javax. nject.S ngleton

object T  l nesPers stenceStoreCl entModule extends Tw terModule {
  pr vate val Stag ngDataset = "t  l ne_response_batc s_v5_nonprod"
  pr vate val ProdDataset = "t  l ne_response_batc s_v5"
  pr vate f nal val T  out = "mh_pers stence_store.t  out"

  flag[Durat on](T  out, 300.m ll s, "T  out per request")

  @Prov des
  @S ngleton
  def prov desT  l nesPers stenceStoreCl ent(
    @Flag(T  out) t  out: Durat on,
     njectedServ ce dent f er: Serv ce dent f er,
    statsRece ver: StatsRece ver
  ): T  l neResponseBatc sCl ent[T  l neResponseV3] = {
    val t  l neResponseBatc sDataset =
       njectedServ ce dent f er.env ron nt.toLo rCase match {
        case "prod" => ProdDataset
        case _ => Stag ngDataset
      }

    val t  l neResponseBatc sConf g = new T  l nePers stenceManhattanCl entConf g {
      val dataset = t  l neResponseBatc sDataset
      val  sReadOnly = false
      val serv ce dent f er =  njectedServ ce dent f er
      overr de val defaultMaxT  out = t  out
      overr de val maxRetryCount = 2
    }

    T  l nePers stenceManhattanCl entBu lder.bu ldT  l neResponseV3Batc sCl ent(
      t  l neResponseBatc sConf g,
      statsRece ver
    )
  }
}
