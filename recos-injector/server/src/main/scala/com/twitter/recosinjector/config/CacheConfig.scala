package com.tw ter.recos njector.conf g

 mport com.tw ter.f nagle. mcac d.Cl ent
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.storehaus_ nternal. mcac . mcac Store
 mport com.tw ter.storehaus_ nternal.ut l.{Cl entNa , ZkEndPo nt}

tra  Cac Conf g {
   mpl c  def statsRece ver: StatsRece ver

  def serv ce dent f er: Serv ce dent f er

  def recos njectorCoreSvcsCac Dest: Str ng

  val recos njectorCoreSvcsCac Cl ent: Cl ent =  mcac Store. mcac dCl ent(
    na  = Cl entNa (" mcac -recos- njector"),
    dest = ZkEndPo nt(recos njectorCoreSvcsCac Dest),
    statsRece ver = statsRece ver,
    serv ce dent f er = serv ce dent f er
  )

}
