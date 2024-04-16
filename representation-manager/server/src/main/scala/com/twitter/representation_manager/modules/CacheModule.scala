package com.tw ter.representat on_manager.modules

 mport com.google. nject.Prov des
 mport com.tw ter.f nagle. mcac d.Cl ent
 mport javax. nject.S ngleton
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.storehaus_ nternal. mcac . mcac Store
 mport com.tw ter.storehaus_ nternal.ut l.Cl entNa 
 mport com.tw ter.storehaus_ nternal.ut l.ZkEndPo nt

object Cac Module extends Tw terModule {

  pr vate val cac Dest = flag[Str ng]("cac _module.dest", "Path to  mcac  serv ce")
  pr vate val t  out = flag[ nt](" mcac .t  out", " mcac  cl ent t  out")
  pr vate val retr es = flag[ nt](" mcac .retr es", " mcac  t  out retr es")

  @S ngleton
  @Prov des
  def prov desCac (
    serv ce dent f er: Serv ce dent f er,
    stats: StatsRece ver
  ): Cl ent =
     mcac Store. mcac dCl ent(
      na  = Cl entNa (" mcac _representat on_manager"),
      dest = ZkEndPo nt(cac Dest()),
      t  out = t  out().m ll seconds,
      retr es = retr es(),
      statsRece ver = stats.scope("cac _cl ent"),
      serv ce dent f er = serv ce dent f er
    )
}
