package com.tw ter.cr_m xer.module.thr ft_cl ent

 mport com.google. nject.Prov des
 mport com.tw ter.convers ons.PercentOps._
 mport com.tw ter.cr_m xer.model.ModuleNa s
 mport com.tw ter.cr_m xer.conf g.T  outConf g
 mport com.tw ter.f nagle.Thr ftMux
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.mtls.cl ent.MtlsStackCl ent._
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f nagle.thr ft.Cl ent d
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.s mclustersann.{thr ftscala => t}
 mport javax. nject.Na d
 mport javax. nject.S ngleton

object S mClustersAnnServ ceCl entModule extends Tw terModule {

  @Prov des
  @S ngleton
  @Na d(ModuleNa s.ProdS mClustersANNServ ceCl entNa )
  def prov desProdS mClustersANNServ ceCl ent(
    serv ce dent f er: Serv ce dent f er,
    cl ent d: Cl ent d,
    t  outConf g: T  outConf g,
    statsRece ver: StatsRece ver,
  ): t.S mClustersANNServ ce. thodPerEndpo nt = {
    val label = "s mclusters-ann-server"
    val dest = "/s/s mclusters-ann/s mclusters-ann"

    bu ldCl ent(serv ce dent f er, cl ent d, t  outConf g, statsRece ver, dest, label)
  }

  @Prov des
  @S ngleton
  @Na d(ModuleNa s.Exper  ntalS mClustersANNServ ceCl entNa )
  def prov desExper  ntalS mClustersANNServ ceCl ent(
    serv ce dent f er: Serv ce dent f er,
    cl ent d: Cl ent d,
    t  outConf g: T  outConf g,
    statsRece ver: StatsRece ver,
  ): t.S mClustersANNServ ce. thodPerEndpo nt = {
    val label = "s mclusters-ann-exper  ntal-server"
    val dest = "/s/s mclusters-ann/s mclusters-ann-exper  ntal"

    bu ldCl ent(serv ce dent f er, cl ent d, t  outConf g, statsRece ver, dest, label)
  }

  @Prov des
  @S ngleton
  @Na d(ModuleNa s.S mClustersANNServ ceCl entNa 1)
  def prov desS mClustersANNServ ceCl ent1(
    serv ce dent f er: Serv ce dent f er,
    cl ent d: Cl ent d,
    t  outConf g: T  outConf g,
    statsRece ver: StatsRece ver,
  ): t.S mClustersANNServ ce. thodPerEndpo nt = {
    val label = "s mclusters-ann-server-1"
    val dest = "/s/s mclusters-ann/s mclusters-ann-1"

    bu ldCl ent(serv ce dent f er, cl ent d, t  outConf g, statsRece ver, dest, label)
  }

  @Prov des
  @S ngleton
  @Na d(ModuleNa s.S mClustersANNServ ceCl entNa 2)
  def prov desS mClustersANNServ ceCl ent2(
    serv ce dent f er: Serv ce dent f er,
    cl ent d: Cl ent d,
    t  outConf g: T  outConf g,
    statsRece ver: StatsRece ver,
  ): t.S mClustersANNServ ce. thodPerEndpo nt = {
    val label = "s mclusters-ann-server-2"
    val dest = "/s/s mclusters-ann/s mclusters-ann-2"

    bu ldCl ent(serv ce dent f er, cl ent d, t  outConf g, statsRece ver, dest, label)
  }

  @Prov des
  @S ngleton
  @Na d(ModuleNa s.S mClustersANNServ ceCl entNa 3)
  def prov desS mClustersANNServ ceCl ent3(
    serv ce dent f er: Serv ce dent f er,
    cl ent d: Cl ent d,
    t  outConf g: T  outConf g,
    statsRece ver: StatsRece ver,
  ): t.S mClustersANNServ ce. thodPerEndpo nt = {
    val label = "s mclusters-ann-server-3"
    val dest = "/s/s mclusters-ann/s mclusters-ann-3"

    bu ldCl ent(serv ce dent f er, cl ent d, t  outConf g, statsRece ver, dest, label)
  }

  @Prov des
  @S ngleton
  @Na d(ModuleNa s.S mClustersANNServ ceCl entNa 5)
  def prov desS mClustersANNServ ceCl ent5(
    serv ce dent f er: Serv ce dent f er,
    cl ent d: Cl ent d,
    t  outConf g: T  outConf g,
    statsRece ver: StatsRece ver,
  ): t.S mClustersANNServ ce. thodPerEndpo nt = {
    val label = "s mclusters-ann-server-5"
    val dest = "/s/s mclusters-ann/s mclusters-ann-5"

    bu ldCl ent(serv ce dent f er, cl ent d, t  outConf g, statsRece ver, dest, label)
  }

  @Prov des
  @S ngleton
  @Na d(ModuleNa s.S mClustersANNServ ceCl entNa 4)
  def prov desS mClustersANNServ ceCl ent4(
    serv ce dent f er: Serv ce dent f er,
    cl ent d: Cl ent d,
    t  outConf g: T  outConf g,
    statsRece ver: StatsRece ver,
  ): t.S mClustersANNServ ce. thodPerEndpo nt = {
    val label = "s mclusters-ann-server-4"
    val dest = "/s/s mclusters-ann/s mclusters-ann-4"

    bu ldCl ent(serv ce dent f er, cl ent d, t  outConf g, statsRece ver, dest, label)
  }
  pr vate def bu ldCl ent(
    serv ce dent f er: Serv ce dent f er,
    cl ent d: Cl ent d,
    t  outConf g: T  outConf g,
    statsRece ver: StatsRece ver,
    dest: Str ng,
    label: Str ng
  ): t.S mClustersANNServ ce. thodPerEndpo nt = {
    val stats = statsRece ver.scope("clnt")

    val thr ftCl ent = Thr ftMux.cl ent
      .w hMutualTls(serv ce dent f er)
      .w hCl ent d(cl ent d)
      .w hLabel(label)
      .w hStatsRece ver(stats)
      . thodBu lder(dest)
      . dempotent(5.percent)
      .w hT  outPerRequest(t  outConf g.annServ ceCl entT  out)
      .w hRetryD sabled
      .serv cePerEndpo nt[t.S mClustersANNServ ce.Serv cePerEndpo nt]

    Thr ftMux.Cl ent. thodPerEndpo nt(thr ftCl ent)
  }

}
