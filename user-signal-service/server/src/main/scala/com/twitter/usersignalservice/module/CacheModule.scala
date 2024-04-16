package com.tw ter.users gnalserv ce.module

 mport com.google. nject.Prov des
 mport javax. nject.S ngleton
 mport com.tw ter.f nagle. mcac d.Cl ent
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.storehaus_ nternal. mcac . mcac Store
 mport com.tw ter.storehaus_ nternal.ut l.ZkEndPo nt
 mport com.tw ter.storehaus_ nternal.ut l.Cl entNa 

object Cac Module extends Tw terModule {
  pr vate val cac Dest =
    flag[Str ng](na  = "cac _module.dest",  lp = "Path to  mcac  serv ce")
  pr vate val t  out =
    flag[ nt](na  = " mcac .t  out",  lp = " mcac  cl ent t  out")

  @S ngleton
  @Prov des
  def prov desCac (
    serv ce dent f er: Serv ce dent f er,
    stats: StatsRece ver
  ): Cl ent =
     mcac Store. mcac dCl ent(
      na  = Cl entNa (" mcac _user_s gnal_serv ce"),
      dest = ZkEndPo nt(cac Dest()),
      t  out = t  out().m ll seconds,
      retr es = 0,
      statsRece ver = stats.scope(" mcac "),
      serv ce dent f er = serv ce dent f er
    )
}
