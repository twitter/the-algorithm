package com.tw ter.product_m xer.component_l brary.module.http

 mport com.google. nject.Prov des
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f natra.httpcl ent.HttpCl ent
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter. nject.annotat ons.Flag
 mport com.tw ter.product_m xer.component_l brary.module.http.F nagleHttpCl entModule.HttpCl entAcqu s  onT  out
 mport com.tw ter.product_m xer.component_l brary.module.http.F nagleHttpCl entModule.HttpCl entConnectT  out
 mport com.tw ter.product_m xer.component_l brary.module.http.F nagleHttpCl entModule.HttpCl entRequestT  out
 mport com.tw ter.product_m xer.shared_l brary.http_cl ent.F nagleHttpCl entBu lder.bu ldF nagleHttpCl entMutualTls
 mport com.tw ter.product_m xer.shared_l brary.http_cl ent.HttpHostPort
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.jackson.ScalaObjectMapper
 mport javax. nject.Na d
 mport javax. nject.S ngleton

object F natraHttpCl entModule extends Tw terModule {

  f nal val HttpCl entHost = "http_cl ent.host"
  f nal val HttpCl entPort = "http_cl ent.port"

  flag[Str ng](HttpCl entHost, "Host that t  cl ent w ll connect to")

  flag[ nt](HttpCl entPort, 443, "Port that t  cl ent w ll connect to")

  f nal val F natraHttpCl ent = "F natraHttpCl ent"

  /**
   * Bu ld a F natra HTTP cl ent for a host. T  F natra HTTP cl ent can be  lpful (as opposed to
   * t  base F nagle HTTP Cl ent), as   prov des bu lt- n JSON response pars ng and ot r
   * conven ence  thods
   *
   * Note that t  t  outs conf gured  n t  module are  ant to be a reasonable start ng po nt
   * only. To furt r tun ng t  sett ngs, e  r overr de t  flags or create local copy of t  module.
   *
   * @param requestT  out     HTTP cl ent request t  out
   * @param connectT  out     HTTP cl ent transport connect t  out
   * @param acqu s  onT  out HTTP cl ent sess on acqu s  on t  out
   * @param host               Host to bu ld F natra cl ent
   * @param port               Port to bu ld F natra cl ent
   * @param scalaObjectMapper  Object mapper used by t  bu lt- n JSON response pars ng
   * @param serv ce dent f er  Serv ce  D used to S2S Auth
   * @param statsRece ver      Stats
   *
   * @return F natra HTTP cl ent
   */
  @Prov des
  @S ngleton
  @Na d(F natraHttpCl ent)
  def prov desF natraHttpCl ent(
    @Flag(HttpCl entRequestT  out) requestT  out: Durat on,
    @Flag(HttpCl entConnectT  out) connectT  out: Durat on,
    @Flag(HttpCl entAcqu s  onT  out) acqu s  onT  out: Durat on,
    @Flag(HttpCl entHost) host: Str ng,
    @Flag(HttpCl entPort) port:  nt,
    scalaObjectMapper: ScalaObjectMapper,
    serv ce dent f er: Serv ce dent f er,
    statsRece ver: StatsRece ver
  ): HttpCl ent = {
    val f nagleHttpCl ent = bu ldF nagleHttpCl entMutualTls(
      requestT  out = requestT  out,
      connectT  out = connectT  out,
      acqu s  onT  out = acqu s  onT  out,
      serv ce dent f er = serv ce dent f er,
      statsRece ver = statsRece ver
    )

    val hostPort = HttpHostPort(host, port)
    val f nagleHttpServ ce = f nagleHttpCl ent.newServ ce(hostPort.toStr ng)

    new HttpCl ent(
      hostna  = hostPort.host,
      httpServ ce = f nagleHttpServ ce,
      mapper = scalaObjectMapper
    )
  }
}
