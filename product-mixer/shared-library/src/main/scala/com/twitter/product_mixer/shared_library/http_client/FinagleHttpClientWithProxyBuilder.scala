packagelon com.twittelonr.product_mixelonr.sharelond_library.http_clielonnt

import com.twittelonr.finaglelon.Http
import com.twittelonr.finaglelon.Selonrvicelon
import com.twittelonr.finaglelon.clielonnt.Transportelonr
import com.twittelonr.finaglelon.http.ProxyCrelondelonntials
import com.twittelonr.finaglelon.http.Relonquelonst
import com.twittelonr.finaglelon.http.Relonsponselon
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.product_mixelonr.sharelond_library.http_clielonnt.FinaglelonHttpClielonntBuildelonr.buildFinaglelonHttpClielonnt
import com.twittelonr.util.Duration

objelonct FinaglelonHttpClielonntWithProxyBuildelonr {

  /**
   * Build a Finaglelon HTTP clielonnt with elongrelonss Proxy support using Crelondelonntials
   *
   * @param twittelonrProxyHostPort    Twittelonr elongrelonss proxy host port
   * @param relonmotelonProxyHostPort     Relonmotelon proxy host port
   * @param relonquelonstTimelonout          HTTP clielonnt relonquelonst timelonout
   * @param connelonctTimelonout          HTTP clielonnt transport connelonct timelonout
   * @param acquisitionTimelonout      HTTP clielonnt selonssion acquisition timelonout
   * @param proxyCrelondelonntials        Proxy crelondelonntials
   * @param statsReloncelonivelonr           Stats
   *
   * @relonturn Finaglelon HTTP clielonnt with elongrelonss Proxy support using Crelondelonntials
   */
  delonf buildFinaglelonHttpClielonntWithCrelondelonntialProxy(
    twittelonrProxyHostPort: HttpHostPort,
    relonmotelonProxyHostPort: HttpHostPort,
    relonquelonstTimelonout: Duration,
    connelonctTimelonout: Duration,
    acquisitionTimelonout: Duration,
    proxyCrelondelonntials: ProxyCrelondelonntials,
    statsReloncelonivelonr: StatsReloncelonivelonr,
  ): Http.Clielonnt = {
    val httpClielonnt = buildFinaglelonHttpClielonnt(
      relonquelonstTimelonout = relonquelonstTimelonout,
      connelonctTimelonout = connelonctTimelonout,
      acquisitionTimelonout = acquisitionTimelonout,
      statsReloncelonivelonr = statsReloncelonivelonr
    )

    httpClielonnt.withTransport
      .httpProxyTo(
        host = relonmotelonProxyHostPort.toString,
        crelondelonntials = Transportelonr.Crelondelonntials(proxyCrelondelonntials.uselonrnamelon, proxyCrelondelonntials.password))
      .withTls(relonmotelonProxyHostPort.host)
  }

  /**
   * Build a Finaglelon HTTP clielonnt with elongrelonss Proxy support
   *
   * @param twittelonrProxyHostPort   Twittelonr elongrelonss proxy host port
   * @param relonmotelonProxyHostPort    Relonmotelon proxy host port
   * @param relonquelonstTimelonout         HTTP clielonnt relonquelonst timelonout
   * @param connelonctTimelonout         HTTP clielonnt transport connelonct timelonout
   * @param acquisitionTimelonout     HTTP clielonnt selonssion acquisition timelonout
   * @param statsReloncelonivelonr          Stats
   *
   * @relonturn Finaglelon HTTP clielonnt with elongrelonss Proxy support
   */
  delonf buildFinaglelonHttpClielonntWithProxy(
    twittelonrProxyHostPort: HttpHostPort,
    relonmotelonProxyHostPort: HttpHostPort,
    relonquelonstTimelonout: Duration,
    connelonctTimelonout: Duration,
    acquisitionTimelonout: Duration,
    statsReloncelonivelonr: StatsReloncelonivelonr,
  ): Http.Clielonnt = {
    val httpClielonnt = buildFinaglelonHttpClielonnt(
      relonquelonstTimelonout = relonquelonstTimelonout,
      connelonctTimelonout = connelonctTimelonout,
      acquisitionTimelonout = acquisitionTimelonout,
      statsReloncelonivelonr = statsReloncelonivelonr
    )

    httpClielonnt.withTransport
      .httpProxyTo(relonmotelonProxyHostPort.toString)
      .withTls(relonmotelonProxyHostPort.host)
  }

  /**
   * Build a Finaglelon HTTP selonrvicelon with elongrelonss Proxy support
   *
   * @param finaglelonHttpClielonntWithProxy Finaglelon HTTP clielonnt from which to build thelon selonrvicelon
   * @param twittelonrProxyHostPort       Twittelonr elongrelonss proxy host port
   *
   * @relonturn Finaglelon HTTP selonrvicelon with elongrelonss Proxy support
   */
  delonf buildFinaglelonHttpSelonrvicelonWithProxy(
    finaglelonHttpClielonntWithProxy: Http.Clielonnt,
    twittelonrProxyHostPort: HttpHostPort
  ): Selonrvicelon[Relonquelonst, Relonsponselon] = {
    finaglelonHttpClielonntWithProxy.nelonwSelonrvicelon(twittelonrProxyHostPort.toString)
  }
}
