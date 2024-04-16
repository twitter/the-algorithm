package com.tw ter.product_m xer.component_l brary.module.http

 mport com.google. nject.Prov des
 mport com.tw ter.f nagle.Http
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter. nject.Tw terModule
 mport com.tw ter. nject.annotat ons.Flag
 mport com.tw ter.product_m xer.component_l brary.module.http.F nagleHttpCl entModule.HttpCl entAcqu s  onT  out
 mport com.tw ter.product_m xer.component_l brary.module.http.F nagleHttpCl entModule.HttpCl entConnectT  out
 mport com.tw ter.product_m xer.component_l brary.module.http.F nagleHttpCl entModule.HttpCl entRequestT  out
 mport com.tw ter.product_m xer.core.module.product_m xer_flags.ProductM xerFlagModule.Serv ceLocal
 mport com.tw ter.product_m xer.shared_l brary.http_cl ent.F nagleHttpCl entW hProxyBu lder.bu ldF nagleHttpCl entW hProxy
 mport com.tw ter.product_m xer.shared_l brary.http_cl ent.HttpHostPort
 mport com.tw ter.ut l.Durat on
 mport javax. nject.Na d
 mport javax. nject.S ngleton

object F nagleHttpCl entW hProxyModule extends Tw terModule {
  f nal val HttpCl entW hProxyTw terHost = "http_cl ent.proxy.tw ter_host"
  f nal val HttpCl entW hProxyTw terPort = "http_cl ent.proxy.tw ter_port"
  f nal val HttpCl entW hProxyRemoteHost = "http_cl ent.proxy.remote_host"
  f nal val HttpCl entW hProxyRemotePort = "http_cl ent.proxy.remote_port"

  flag[Str ng](
    HttpCl entW hProxyTw terHost,
    "httpproxy.local.tw ter.com",
    "Tw ter egress proxy host")

  flag[ nt](HttpCl entW hProxyTw terPort, 3128, "Tw ter egress proxy port")

  flag[Str ng](HttpCl entW hProxyRemoteHost, "Host that t  proxy w ll connect to")

  flag[ nt](HttpCl entW hProxyRemotePort, 443, "Port that t  proxy w ll connect to")

  f nal val F nagleHttpCl entW hProxy = "F nagleHttpCl entW hProxy"

  /**
   * Prov de a F nagle HTTP cl ent w h Egress Proxy support
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
   * @param statsRece ver          Stats
   *
   * @return F nagle HTTP cl ent w h Egress Proxy support
   */
  @Prov des
  @S ngleton
  @Na d(F nagleHttpCl entW hProxy)
  def prov desF nagleHttpCl entW hProxy(
    @Flag(HttpCl entW hProxyTw terHost) proxyTw terHost: Str ng,
    @Flag(HttpCl entW hProxyTw terPort) proxyTw terPort:  nt,
    @Flag(HttpCl entW hProxyRemoteHost) proxyRemoteHost: Str ng,
    @Flag(HttpCl entW hProxyRemotePort) proxyRemotePort:  nt,
    @Flag(HttpCl entRequestT  out) requestT  out: Durat on,
    @Flag(HttpCl entConnectT  out) connectT  out: Durat on,
    @Flag(HttpCl entAcqu s  onT  out) acqu s  onT  out: Durat on,
    @Flag(Serv ceLocal)  sServ ceLocal: Boolean,
    statsRece ver: StatsRece ver
  ): Http.Cl ent = {
    val tw terProxyHostPort = HttpHostPort(proxyTw terHost, proxyTw terPort)
    val remoteProxyHostPort = HttpHostPort(proxyRemoteHost, proxyRemotePort)

    bu ldF nagleHttpCl entW hProxy(
      tw terProxyHostPort = tw terProxyHostPort,
      remoteProxyHostPort = remoteProxyHostPort,
      requestT  out = requestT  out,
      connectT  out = connectT  out,
      acqu s  onT  out = acqu s  onT  out,
      statsRece ver = statsRece ver
    )
  }
}
