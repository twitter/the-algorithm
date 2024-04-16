package com.tw ter.cr_m xer.module

 mport com.google. nject.Prov des
 mport com.google. nject.S ngleton
 mport com.tw ter.app.Flag
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.cr_m xer.model.ModuleNa s
 mport com.tw ter.f nagle. mcac d.Cl ent
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.storehaus_ nternal. mcac . mcac Store
 mport com.tw ter.storehaus_ nternal.ut l.Cl entNa 
 mport com.tw ter.storehaus_ nternal.ut l.ZkEndPo nt
 mport javax. nject.Na d

object Un f edCac Cl ent extends Tw terModule {

  pr vate val T ME_OUT = 20.m ll seconds

  val crM xerUn f edCac Dest: Flag[Str ng] = flag[Str ng](
    na  = "crM xer.un f edCac Dest",
    default = "/s/cac /content_recom nder_un f ed_v2",
     lp = "W ly path to Content Recom nder un f ed cac "
  )

  val t etRecom ndat onResultsCac Dest: Flag[Str ng] = flag[Str ng](
    na  = "t etRecom ndat onResults.Cac Dest",
    default = "/s/cac /t et_recom ndat on_results",
     lp = "W ly path to CrM xer getT etRecom ndat ons() results cac "
  )

  val earlyb rdT etsCac Dest: Flag[Str ng] = flag[Str ng](
    na  = "earlyb rdT ets.Cac Dest",
    default = "/s/cac /crm xer_earlyb rd_t ets",
     lp = "W ly path to CrM xer Earlyb rd Recency Based S m lar y Eng ne result cac "
  )

  @Prov des
  @S ngleton
  @Na d(ModuleNa s.Un f edCac )
  def prov deUn f edCac Cl ent(
    serv ce dent f er: Serv ce dent f er,
    statsRece ver: StatsRece ver,
  ): Cl ent =
     mcac Store. mcac dCl ent(
      na  = Cl entNa (" mcac -content-recom nder-un f ed"),
      dest = ZkEndPo nt(crM xerUn f edCac Dest()),
      statsRece ver = statsRece ver.scope("cac _cl ent"),
      serv ce dent f er = serv ce dent f er,
      t  out = T ME_OUT
    )

  @Prov des
  @S ngleton
  @Na d(ModuleNa s.T etRecom ndat onResultsCac )
  def prov desT etRecom ndat onResultsCac (
    serv ce dent f er: Serv ce dent f er,
    statsRece ver: StatsRece ver,
  ): Cl ent =
     mcac Store. mcac dCl ent(
      na  = Cl entNa (" mcac -t et-recom ndat on-results"),
      dest = ZkEndPo nt(t etRecom ndat onResultsCac Dest()),
      statsRece ver = statsRece ver.scope("cac _cl ent"),
      serv ce dent f er = serv ce dent f er,
      t  out = T ME_OUT
    )

  @Prov des
  @S ngleton
  @Na d(ModuleNa s.Earlyb rdT etsCac )
  def prov desEarlyb rdT etsCac (
    serv ce dent f er: Serv ce dent f er,
    statsRece ver: StatsRece ver,
  ): Cl ent =
     mcac Store. mcac dCl ent(
      na  = Cl entNa (" mcac -crm xer-earlyb rd-t ets"),
      dest = ZkEndPo nt(earlyb rdT etsCac Dest()),
      statsRece ver = statsRece ver.scope("cac _cl ent"),
      serv ce dent f er = serv ce dent f er,
      t  out = T ME_OUT
    )
}
