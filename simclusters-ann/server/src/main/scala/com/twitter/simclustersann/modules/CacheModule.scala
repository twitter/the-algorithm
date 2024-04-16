package com.tw ter.s mclustersann.modules

 mport com.google. nject.Prov des
 mport com.tw ter.f nagle. mcac d.Cl ent
 mport javax. nject.S ngleton
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter. nject.annotat ons.Flag
 mport com.tw ter.s mclustersann.common.FlagNa s
 mport com.tw ter.storehaus_ nternal. mcac . mcac Store
 mport com.tw ter.storehaus_ nternal.ut l.Cl entNa 
 mport com.tw ter.storehaus_ nternal.ut l.ZkEndPo nt

object Cac Module extends Tw terModule {

  @S ngleton
  @Prov des
  def prov desCac (
    @Flag(FlagNa s.Cac Dest) cac Dest: Str ng,
    @Flag(FlagNa s.Cac T  out) cac T  out:  nt,
    serv ce dent f er: Serv ce dent f er,
    stats: StatsRece ver
  ): Cl ent =
     mcac Store. mcac dCl ent(
      na  = Cl entNa (" mcac _s mclusters_ann"),
      dest = ZkEndPo nt(cac Dest),
      t  out = cac T  out.m ll seconds,
      retr es = 0,
      statsRece ver = stats.scope("cac _cl ent"),
      serv ce dent f er = serv ce dent f er
    )
}
