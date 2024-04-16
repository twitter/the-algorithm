package com.tw ter.product_m xer.component_l brary.module.http

 mport com.google. nject.Prov des
 mport com.tw ter.f nagle.Http
 mport com.tw ter.f nagle.http.ProxyCredent als
 mport com.tw ter.f nagle.stats.StatsRece ver
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
 mport com.tw ter.product_m xer.shared_l brary.http_cl ent.F nagleHttpCl entW hProxyBu lder.bu ldF nagleHttpCl entW hCredent alProxy
 mport com.tw ter.product_m xer.shared_l brary.http_cl ent.HttpHostPort
 mport com.tw ter.ut l.Durat on
 mport javax. nject.Na d
 mport javax. nject.S ngleton

object F nagleHttpCl entW hCredent alProxyModule extends Tw terModule {

  f nal val F nagleHttpCl entW hCredent alProxy = "F nagleHttpCl entW hCredent alProxy"

  /**
   * Prov de a F nagle HTTP cl ent w h Egress Proxy support us ng Credent als
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
   * @param  sServ ceLocal          f t   s a Local deploy nt for test ng
   * @param proxyCredent als       Proxy credent als
   * @param statsRece ver          Stats
   *
   * @return F nagle HTTP cl ent w h Egress Proxy support us ng Credent als
   */
  @Prov des
  @S ngleton
  @Na d(F nagleHttpCl entW hCredent alProxy)
  def prov desF nagleHttpCl entW hCredent alProxy(
    @Flag(HttpCl entW hProxyTw terHost) proxyTw terHost: Str ng,
    @Flag(HttpCl entW hProxyTw terPort) proxyTw terPort:  nt,
    @Flag(HttpCl entW hProxyRemoteHost) proxyRemoteHost: Str ng,
    @Flag(HttpCl entW hProxyRemotePort) proxyRemotePort:  nt,
    @Flag(HttpCl entRequestT  out) requestT  out: Durat on,
    @Flag(HttpCl entConnectT  out) connectT  out: Durat on,
    @Flag(HttpCl entAcqu s  onT  out) acqu s  onT  out: Durat on,
    @Flag(Serv ceLocal)  sServ ceLocal: Boolean,
    proxyCredent als: ProxyCredent als,
    statsRece ver: StatsRece ver
  ): Http.Cl ent = {

    val tw terProxyHostPort = HttpHostPort(proxyTw terHost, proxyTw terPort)
    val remoteProxyHostPort = HttpHostPort(proxyRemoteHost, proxyRemotePort)

    bu ldF nagleHttpCl entW hCredent alProxy(
      tw terProxyHostPort = tw terProxyHostPort,
      remoteProxyHostPort = remoteProxyHostPort,
      requestT  out = requestT  out,
      connectT  out = connectT  out,
      acqu s  onT  out = acqu s  onT  out,
      proxyCredent als = proxyCredent als,
      statsRece ver = statsRece ver
    )
  }
}
