package com.tw ter.cr_m xer.module.grpc_cl ent

 mport com.google. nject.Prov des
 mport com.tw ter.cr_m xer.conf g.T  outConf g
 mport com.tw ter.cr_m xer.model.ModuleNa s
 mport com.tw ter.f nagle.Http
 mport com.tw ter.f nagle.grpc.F nagleChannelBu lder
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.mtls.cl ent.MtlsStackCl ent.MtlsStackCl entSyntax
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.ut l.Durat on
 mport  o.grpc.ManagedChannel
 mport javax. nject.Na d
 mport javax. nject.S ngleton

object Nav GRPCCl entModule extends Tw terModule {

  val maxRetryAttempts = 3

  @Prov des
  @S ngleton
  @Na d(ModuleNa s.Ho Nav GRPCCl ent)
  def prov desHo Nav GRPCCl ent(
    serv ce dent f er: Serv ce dent f er,
    t  outConf g: T  outConf g,
    statsRece ver: StatsRece ver,
  ): ManagedChannel = {
    val label = "nav -wals-recom nded-t ets-ho -cl ent"
    val dest = "/s/ads-pred ct on/nav -wals-recom nded-t ets-ho "
    bu ldCl ent(serv ce dent f er, t  outConf g, statsRece ver, dest, label)
  }

  @Prov des
  @S ngleton
  @Na d(ModuleNa s.AdsFavedNav GRPCCl ent)
  def prov desAdsFavedNav GRPCCl ent(
    serv ce dent f er: Serv ce dent f er,
    t  outConf g: T  outConf g,
    statsRece ver: StatsRece ver,
  ): ManagedChannel = {
    val label = "nav -wals-ads-faved-t ets"
    val dest = "/s/ads-pred ct on/nav -wals-ads-faved-t ets"
    bu ldCl ent(serv ce dent f er, t  outConf g, statsRece ver, dest, label)
  }

  @Prov des
  @S ngleton
  @Na d(ModuleNa s.AdsMonet zableNav GRPCCl ent)
  def prov desAdsMonet zableNav GRPCCl ent(
    serv ce dent f er: Serv ce dent f er,
    t  outConf g: T  outConf g,
    statsRece ver: StatsRece ver,
  ): ManagedChannel = {
    val label = "nav -wals-ads-monet zable-t ets"
    val dest = "/s/ads-pred ct on/nav -wals-ads-monet zable-t ets"
    bu ldCl ent(serv ce dent f er, t  outConf g, statsRece ver, dest, label)
  }

  pr vate def bu ldCl ent(
    serv ce dent f er: Serv ce dent f er,
    t  outConf g: T  outConf g,
    statsRece ver: StatsRece ver,
    dest: Str ng,
    label: Str ng
  ): ManagedChannel = {

    val stats = statsRece ver.scope("clnt").scope(label)

    val cl ent = Http.cl ent
      .w hLabel(label)
      .w hMutualTls(serv ce dent f er)
      .w hRequestT  out(t  outConf g.nav RequestT  out)
      .w hTransport.connectT  out(Durat on.fromM ll seconds(10000))
      .w hSess on.acqu s  onT  out(Durat on.fromM ll seconds(20000))
      .w hStatsRece ver(stats)
      .w hHttpStats

    F nagleChannelBu lder
      .forTarget(dest)
      .overr deAuthor y("rustserv ng")
      .maxRetryAttempts(maxRetryAttempts)
      .enableRetryForStatus( o.grpc.Status.RESOURCE_EXHAUSTED)
      .enableRetryForStatus( o.grpc.Status.UNKNOWN)
      .enableUnsafeFullyBuffer ngMode()
      .httpCl ent(cl ent)
      .bu ld()

  }
}
