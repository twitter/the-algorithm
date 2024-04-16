package com.tw ter.tsp.modules

 mport com.google. nject.Prov des
 mport com.google. nject.S ngleton
 mport com.tw ter.app.Flag
 mport com.tw ter.f nagle. mcac d.Cl ent
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.storehaus_ nternal. mcac . mcac Store
 mport com.tw ter.storehaus_ nternal.ut l.Cl entNa 
 mport com.tw ter.storehaus_ nternal.ut l.ZkEndPo nt

object Un f edCac Cl ent extends Tw terModule {
  val tspUn f edCac Dest: Flag[Str ng] = flag[Str ng](
    na  = "tsp.un f edCac Dest",
    default = "/srv#/prod/local/cac /top c_soc al_proof_un f ed",
     lp = "W ly path to top c soc al proof un f ed cac "
  )

  @Prov des
  @S ngleton
  def prov deUn f edCac Cl ent(
    serv ce dent f er: Serv ce dent f er,
    statsRece ver: StatsRece ver,
  ): Cl ent =
     mcac Store. mcac dCl ent(
      na  = Cl entNa ("top c-soc al-proof-un f ed- mcac "),
      dest = ZkEndPo nt(tspUn f edCac Dest()),
      statsRece ver = statsRece ver.scope("cac _cl ent"),
      serv ce dent f er = serv ce dent f er
    )
}
