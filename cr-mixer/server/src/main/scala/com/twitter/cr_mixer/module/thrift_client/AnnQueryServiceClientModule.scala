package com.tw ter.cr_m xer.module.thr ft_cl ent

 mport com.google. nject.Prov des
 mport com.tw ter.ann.common.thr ftscala.AnnQueryServ ce
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.convers ons.PercentOps._
 mport com.tw ter.cr_m xer.conf g.T  outConf g
 mport com.tw ter.f nagle.Thr ftMux
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.mtls.cl ent.MtlsStackCl ent._
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f nagle.thr ft.Cl ent d
 mport com.tw ter. nject.Tw terModule
 mport javax. nject.Na d
 mport javax. nject.S ngleton

object AnnQueryServ ceCl entModule extends Tw terModule {
  f nal val DebuggerDemoAnnServ ceCl entNa  = "DebuggerDemoAnnServ ceCl ent"

  @Prov des
  @S ngleton
  @Na d(DebuggerDemoAnnServ ceCl entNa )
  def debuggerDemoAnnServ ceCl ent(
    serv ce dent f er: Serv ce dent f er,
    cl ent d: Cl ent d,
    statsRece ver: StatsRece ver,
    t  outConf g: T  outConf g,
  ): AnnQueryServ ce. thodPerEndpo nt = {
    // T  ANN  s bu lt from t  embedd ngs  n src/scala/com/tw ter/wtf/beam/bq_embedd ng_export/sql/MlfExper  ntalT etEmbedd ngScalaDataset.sql
    // Change t  above sql  f   want to bu ld t   ndex from a d ff embedd ng
    val dest = "/s/cassowary/mlf-exper  ntal-ann-serv ce"
    val label = "exper  ntal-ann"
    bu ldCl ent(serv ce dent f er, cl ent d, t  outConf g, statsRece ver, dest, label)
  }

  f nal val TwH NUuaAnnServ ceCl entNa  = "TwH NUuaAnnServ ceCl ent"
  @Prov des
  @S ngleton
  @Na d(TwH NUuaAnnServ ceCl entNa )
  def twh nUuaAnnServ ceCl ent(
    serv ce dent f er: Serv ce dent f er,
    cl ent d: Cl ent d,
    statsRece ver: StatsRece ver,
    t  outConf g: T  outConf g,
  ): AnnQueryServ ce. thodPerEndpo nt = {
    val dest = "/s/cassowary/twh n-uua-ann-serv ce"
    val label = "twh n_uua_ann"

    bu ldCl ent(serv ce dent f er, cl ent d, t  outConf g, statsRece ver, dest, label)
  }

  f nal val TwH NRegularUpdateAnnServ ceCl entNa  = "TwH NRegularUpdateAnnServ ceCl ent"
  @Prov des
  @S ngleton
  @Na d(TwH NRegularUpdateAnnServ ceCl entNa )
  def twH NRegularUpdateAnnServ ceCl ent(
    serv ce dent f er: Serv ce dent f er,
    cl ent d: Cl ent d,
    statsRece ver: StatsRece ver,
    t  outConf g: T  outConf g,
  ): AnnQueryServ ce. thodPerEndpo nt = {
    val dest = "/s/cassowary/twh n-regular-update-ann-serv ce"
    val label = "twh n_regular_update"

    bu ldCl ent(serv ce dent f er, cl ent d, t  outConf g, statsRece ver, dest, label)
  }

  f nal val TwoTo rFavAnnServ ceCl entNa  = "TwoTo rFavAnnServ ceCl ent"
  @Prov des
  @S ngleton
  @Na d(TwoTo rFavAnnServ ceCl entNa )
  def twoTo rFavAnnServ ceCl ent(
    serv ce dent f er: Serv ce dent f er,
    cl ent d: Cl ent d,
    statsRece ver: StatsRece ver,
    t  outConf g: T  outConf g,
  ): AnnQueryServ ce. thodPerEndpo nt = {
    val dest = "/s/cassowary/t et-rec-two-to r-fav-ann"
    val label = "t et_rec_two_to r_fav_ann"

    bu ldCl ent(serv ce dent f er, cl ent d, t  outConf g, statsRece ver, dest, label)
  }

  pr vate def bu ldCl ent(
    serv ce dent f er: Serv ce dent f er,
    cl ent d: Cl ent d,
    t  outConf g: T  outConf g,
    statsRece ver: StatsRece ver,
    dest: Str ng,
    label: Str ng
  ): AnnQueryServ ce. thodPerEndpo nt = {
    val thr ftCl ent = Thr ftMux.cl ent
      .w hMutualTls(serv ce dent f er)
      .w hCl ent d(cl ent d)
      .w hLabel(label)
      .w hStatsRece ver(statsRece ver)
      .w hTransport.connectT  out(500.m ll seconds)
      .w hSess on.acqu s  onT  out(500.m ll seconds)
      . thodBu lder(dest)
      .w hT  outPerRequest(t  outConf g.annServ ceCl entT  out)
      .w hRetryD sabled
      . dempotent(5.percent)
      .serv cePerEndpo nt[AnnQueryServ ce.Serv cePerEndpo nt]

    Thr ftMux.Cl ent. thodPerEndpo nt(thr ftCl ent)
  }
}
