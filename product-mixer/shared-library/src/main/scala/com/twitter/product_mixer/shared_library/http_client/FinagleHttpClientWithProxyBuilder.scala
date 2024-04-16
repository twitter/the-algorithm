package com.tw ter.product_m xer.shared_l brary.http_cl ent

 mport com.tw ter.f nagle.Http
 mport com.tw ter.f nagle.Serv ce
 mport com.tw ter.f nagle.cl ent.Transporter
 mport com.tw ter.f nagle.http.ProxyCredent als
 mport com.tw ter.f nagle.http.Request
 mport com.tw ter.f nagle.http.Response
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.product_m xer.shared_l brary.http_cl ent.F nagleHttpCl entBu lder.bu ldF nagleHttpCl ent
 mport com.tw ter.ut l.Durat on

object F nagleHttpCl entW hProxyBu lder {

  /**
   * Bu ld a F nagle HTTP cl ent w h Egress Proxy support us ng Credent als
   *
   * @param tw terProxyHostPort    Tw ter egress proxy host port
   * @param remoteProxyHostPort     Remote proxy host port
   * @param requestT  out          HTTP cl ent request t  out
   * @param connectT  out          HTTP cl ent transport connect t  out
   * @param acqu s  onT  out      HTTP cl ent sess on acqu s  on t  out
   * @param proxyCredent als        Proxy credent als
   * @param statsRece ver           Stats
   *
   * @return F nagle HTTP cl ent w h Egress Proxy support us ng Credent als
   */
  def bu ldF nagleHttpCl entW hCredent alProxy(
    tw terProxyHostPort: HttpHostPort,
    remoteProxyHostPort: HttpHostPort,
    requestT  out: Durat on,
    connectT  out: Durat on,
    acqu s  onT  out: Durat on,
    proxyCredent als: ProxyCredent als,
    statsRece ver: StatsRece ver,
  ): Http.Cl ent = {
    val httpCl ent = bu ldF nagleHttpCl ent(
      requestT  out = requestT  out,
      connectT  out = connectT  out,
      acqu s  onT  out = acqu s  onT  out,
      statsRece ver = statsRece ver
    )

    httpCl ent.w hTransport
      .httpProxyTo(
        host = remoteProxyHostPort.toStr ng,
        credent als = Transporter.Credent als(proxyCredent als.userna , proxyCredent als.password))
      .w hTls(remoteProxyHostPort.host)
  }

  /**
   * Bu ld a F nagle HTTP cl ent w h Egress Proxy support
   *
   * @param tw terProxyHostPort   Tw ter egress proxy host port
   * @param remoteProxyHostPort    Remote proxy host port
   * @param requestT  out         HTTP cl ent request t  out
   * @param connectT  out         HTTP cl ent transport connect t  out
   * @param acqu s  onT  out     HTTP cl ent sess on acqu s  on t  out
   * @param statsRece ver          Stats
   *
   * @return F nagle HTTP cl ent w h Egress Proxy support
   */
  def bu ldF nagleHttpCl entW hProxy(
    tw terProxyHostPort: HttpHostPort,
    remoteProxyHostPort: HttpHostPort,
    requestT  out: Durat on,
    connectT  out: Durat on,
    acqu s  onT  out: Durat on,
    statsRece ver: StatsRece ver,
  ): Http.Cl ent = {
    val httpCl ent = bu ldF nagleHttpCl ent(
      requestT  out = requestT  out,
      connectT  out = connectT  out,
      acqu s  onT  out = acqu s  onT  out,
      statsRece ver = statsRece ver
    )

    httpCl ent.w hTransport
      .httpProxyTo(remoteProxyHostPort.toStr ng)
      .w hTls(remoteProxyHostPort.host)
  }

  /**
   * Bu ld a F nagle HTTP serv ce w h Egress Proxy support
   *
   * @param f nagleHttpCl entW hProxy F nagle HTTP cl ent from wh ch to bu ld t  serv ce
   * @param tw terProxyHostPort       Tw ter egress proxy host port
   *
   * @return F nagle HTTP serv ce w h Egress Proxy support
   */
  def bu ldF nagleHttpServ ceW hProxy(
    f nagleHttpCl entW hProxy: Http.Cl ent,
    tw terProxyHostPort: HttpHostPort
  ): Serv ce[Request, Response] = {
    f nagleHttpCl entW hProxy.newServ ce(tw terProxyHostPort.toStr ng)
  }
}
