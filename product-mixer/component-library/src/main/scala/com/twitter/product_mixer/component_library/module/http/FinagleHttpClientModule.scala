package com.tw ter.product_m xer.component_l brary.module.http

 mport com.google. nject.Prov des
 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.Http
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter. nject.annotat ons.Flag
 mport com.tw ter.product_m xer.shared_l brary.http_cl ent.F nagleHttpCl entBu lder.bu ldF nagleHttpCl entMutualTls
 mport com.tw ter.ut l.Durat on
 mport javax. nject.Na d
 mport javax. nject.S ngleton

object F nagleHttpCl entModule extends Tw terModule {

  f nal val HttpCl entRequestT  out = "http_cl ent.request_t  out"
  f nal val HttpCl entConnectT  out = "http_cl ent.connect_t  out"
  f nal val HttpCl entAcqu s  onT  out = "http_cl ent.acqu s  on_t  out"

  flag[Durat on](
    na  = HttpCl entRequestT  out,
    default = 200.m ll seconds,
     lp = "HTTP cl ent request t  out")

  flag[Durat on](
    na  = HttpCl entConnectT  out,
    default = 500.m ll seconds,
     lp = "HTTP cl ent transport connect t  out")

  flag[Durat on](
    na  = HttpCl entAcqu s  onT  out,
    default = 500.m ll seconds,
     lp = "HTTP cl ent sess on acqu s  on t  out")

  f nal val F nagleHttpCl entModule = "F nagleHttpCl entModule"

  /**
   * Prov des a F nagle HTTP cl ent w h S2S Auth / Mutual TLS
   *
   * Note that t  t  outs conf gured  n t  module are  ant to be a reasonable start ng po nt
   * only. To furt r tun ng t  sett ngs, e  r overr de t  flags or create local copy of t  module.
   *
   * @param requestT  out     HTTP cl ent request t  out
   * @param connectT  out     HTTP cl ent transport connect t  out
   * @param acqu s  onT  out HTTP cl ent sess on acqu s  on t  out
   * @param serv ce dent f er  Serv ce  D used to S2S Auth
   * @param statsRece ver      Stats
   *
   * @return F nagle HTTP Cl ent w h S2S Auth / Mutual TLS
   */
  @Prov des
  @S ngleton
  @Na d(F nagleHttpCl entModule)
  def prov desF nagleHttpCl ent(
    @Flag(HttpCl entRequestT  out) requestT  out: Durat on,
    @Flag(HttpCl entConnectT  out) connectT  out: Durat on,
    @Flag(HttpCl entAcqu s  onT  out) acqu s  onT  out: Durat on,
    serv ce dent f er: Serv ce dent f er,
    statsRece ver: StatsRece ver
  ): Http.Cl ent =
    bu ldF nagleHttpCl entMutualTls(
      requestT  out = requestT  out,
      connectT  out = connectT  out,
      acqu s  onT  out = acqu s  onT  out,
      serv ce dent f er = serv ce dent f er,
      statsRece ver = statsRece ver
    )
}
