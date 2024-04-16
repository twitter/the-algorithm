package com.tw ter.ho _m xer.module

 mport com.google. nject.Prov des
 mport com.google. nject.na .Na d
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle. mcac d.{Cl ent =>  mcac dCl ent}
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.hash ng.KeyHas r
 mport com.tw ter.ho _m xer.param.Ho M xer nject onNa s.StaleT etsCac 
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.product_m xer.shared_l brary. mcac d_cl ent. mcac dCl entBu lder
 mport javax. nject.S ngleton

object StaleT etsCac Module extends Tw terModule {

  @S ngleton
  @Prov des
  @Na d(StaleT etsCac )
  def prov desCac (
    serv ce dent f er: Serv ce dent f er,
    statsRece ver: StatsRece ver
  ):  mcac dCl ent = {
     mcac dCl entBu lder.bu ld mcac dCl ent(
      destNa  = "/srv#/prod/local/cac /stalet etscac :t mcac s",
      numTr es = 3,
      numConnect ons = 1,
      requestT  out = 200.m ll seconds,
      globalT  out = 500.m ll seconds,
      connectT  out = 200.m ll seconds,
      acqu s  onT  out = 200.m ll seconds,
      serv ce dent f er = serv ce dent f er,
      statsRece ver = statsRece ver,
      fa lureAccrualPol cy = None,
      keyHas r = So (KeyHas r.FNV1_32)
    )
  }
}
