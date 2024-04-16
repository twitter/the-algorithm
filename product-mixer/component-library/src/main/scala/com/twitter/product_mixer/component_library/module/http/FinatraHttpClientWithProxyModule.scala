package com.tw ter.product_m xer.component_l brary.module.http

 mport com.google. nject.Prov des
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f natra.httpcl ent.HttpCl ent
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter. nject.annotat ons.Flag
 mport com.tw ter.product_m xer.component_l brary.module.http.F nagleHttpCl entModule.HttpCl entAcqu s  onT  out
 mport com.tw ter.product_m xer.component_l brary.module.http.F nagleHttpCl entModule.HttpCl entConnectT  out
 mport com.tw ter.product_m xer.component_l brary.module.http.F nagleHttpCl entModule.HttpCl entRequestT  out
 mport com.tw ter.product_m xer.component_l brary.module.http.F nagleHttpCl entW hProxyModule.HttpCl entW hProxyRemoteHost
 mport com.tw ter.product_m xer.component_l brary.module.http.F nagleHttpCl entW hProxyModule.HttpCl entW hProxyRemotePort
 mport com.tw ter.product_m xer.component_l brary.module.http.F nagleHttpCl entW hProxyModule.HttpCl entW hProxyTw terHost
 mport com.tw ter.product_m xer.component_l brary.module.http.F nagleHttpCl entW hProxyModule.HttpCl entW hProxyTw terPort
 mport com.tw ter.product_m xer.core.module.product_m xer_flags.ProductM xerFlagModule.Serv ceLocal
 mport com.tw ter.product_m xer.shared_l brary.http_cl ent.F nagleHttpCl entW hProxyBu lder.bu ldF nagleHttpCl entW hProxy
 mport com.tw ter.product_m xer.shared_l brary.http_cl ent.F nagleHttpCl entW hProxyBu lder.bu ldF nagleHttpServ ceW hProxy
 mport com.tw ter.product_m xer.shared_l brary.http_cl ent.HttpHostPort
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.jackson.ScalaObjectMapper
 mport javax. nject.Na d
 mport javax. nject.S ngleton

object F natraHttpCl entW hProxyModule extends Tw terModule {

  f nal val F natraHttpCl entW hProxy = "F nagleHttpCl entW hProxy"

  /**
   * Bu ld a F natra HTTP cl ent w h Egress Proxy support for a host. T  F natra HTTP cl ent can
   * be  lpful (as opposed to t  base F nagle HTTP Cl ent), as   prov des bu lt- n JSON response
   * pars ng and ot r conven ence  thods
   *
   * Note that t  t  outs conf gured  n t  module are  ant to be a reasonable start ng po nt
   * only. To furt r tun ng t  sett ngs, e  r overr de t  flags or create local copy of t  module.
   *
   * @param proxyTw terHost       Tw ter egress proxy host
   * @param proxyTw terPort       Tw ter egress proxy port
   * @param proxyRemoteHost        Remote proxy host
   * @param proxyRemotePort        Remote proxy port
   * @param requestT  out         HTTP cl ent request t  out
   * @param connectT  out         HTTP cl ent transport connect t  out
   * @param acqu s  onT  out     HTTP cl ent sess on acqu s  on t  out
   * @param  sServ ceLocal         Local deploy nt for test ng
   * @param scalaObjectMapper      Object mapper used by t  bu lt- n JSON response pars ng
   * @param statsRece ver          Stats
   *
   * @return F natra HTTP cl ent w h Egress Proxy support for a host
   */
  @Prov des
  @S ngleton
  @Na d(F natraHttpCl entW hProxy)
  def prov desF natraHttpCl entW hProxy(
    @Flag(HttpCl entW hProxyTw terHost) proxyTw terHost: Str ng,
    @Flag(HttpCl entW hProxyTw terPort) proxyTw terPort:  nt,
    @Flag(HttpCl entW hProxyRemoteHost) proxyRemoteHost: Str ng,
    @Flag(HttpCl entW hProxyRemotePort) proxyRemotePort:  nt,
    @Flag(HttpCl entRequestT  out) requestT  out: Durat on,
    @Flag(HttpCl entConnectT  out) connectT  out: Durat on,
    @Flag(HttpCl entAcqu s  onT  out) acqu s  onT  out: Durat on,
    @Flag(Serv ceLocal)  sServ ceLocal: Boolean,
    scalaObjectMapper: ScalaObjectMapper,
    statsRece ver: StatsRece ver
  ): HttpCl ent = {
    val tw terProxyHostPort = HttpHostPort(proxyTw terHost, proxyTw terPort)
    val proxyRemoteHostPort = HttpHostPort(proxyRemoteHost, proxyRemotePort)

    val f nagleHttpCl entW hProxy =
      bu ldF nagleHttpCl entW hProxy(
        tw terProxyHostPort = tw terProxyHostPort,
        remoteProxyHostPort = proxyRemoteHostPort,
        requestT  out = requestT  out,
        connectT  out = connectT  out,
        acqu s  onT  out = acqu s  onT  out,
        statsRece ver = statsRece ver
      )

    val f nagleHttpServ ceW hProxy =
      bu ldF nagleHttpServ ceW hProxy(
        f nagleHttpCl entW hProxy = f nagleHttpCl entW hProxy,
        tw terProxyHostPort = tw terProxyHostPort
      )

    new HttpCl ent(
      hostna  = tw terProxyHostPort.host,
      httpServ ce = f nagleHttpServ ceW hProxy,
      mapper = scalaObjectMapper
    )
  }
}
