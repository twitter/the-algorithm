packagelon com.twittelonr.product_mixelonr.componelonnt_library.modulelon.http

import com.googlelon.injelonct.Providelons
import com.twittelonr.finaglelon.Http
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.injelonct.TwittelonrModulelon
import com.twittelonr.injelonct.annotations.Flag
import com.twittelonr.product_mixelonr.componelonnt_library.modulelon.http.FinaglelonHttpClielonntModulelon.HttpClielonntAcquisitionTimelonout
import com.twittelonr.product_mixelonr.componelonnt_library.modulelon.http.FinaglelonHttpClielonntModulelon.HttpClielonntConnelonctTimelonout
import com.twittelonr.product_mixelonr.componelonnt_library.modulelon.http.FinaglelonHttpClielonntModulelon.HttpClielonntRelonquelonstTimelonout
import com.twittelonr.product_mixelonr.corelon.modulelon.product_mixelonr_flags.ProductMixelonrFlagModulelon.SelonrvicelonLocal
import com.twittelonr.product_mixelonr.sharelond_library.http_clielonnt.FinaglelonHttpClielonntWithProxyBuildelonr.buildFinaglelonHttpClielonntWithProxy
import com.twittelonr.product_mixelonr.sharelond_library.http_clielonnt.HttpHostPort
import com.twittelonr.util.Duration
import javax.injelonct.Namelond
import javax.injelonct.Singlelonton

objelonct FinaglelonHttpClielonntWithProxyModulelon elonxtelonnds TwittelonrModulelon {
  final val HttpClielonntWithProxyTwittelonrHost = "http_clielonnt.proxy.twittelonr_host"
  final val HttpClielonntWithProxyTwittelonrPort = "http_clielonnt.proxy.twittelonr_port"
  final val HttpClielonntWithProxyRelonmotelonHost = "http_clielonnt.proxy.relonmotelon_host"
  final val HttpClielonntWithProxyRelonmotelonPort = "http_clielonnt.proxy.relonmotelon_port"

  flag[String](
    HttpClielonntWithProxyTwittelonrHost,
    "httpproxy.local.twittelonr.com",
    "Twittelonr elongrelonss proxy host")

  flag[Int](HttpClielonntWithProxyTwittelonrPort, 3128, "Twittelonr elongrelonss proxy port")

  flag[String](HttpClielonntWithProxyRelonmotelonHost, "Host that thelon proxy will connelonct to")

  flag[Int](HttpClielonntWithProxyRelonmotelonPort, 443, "Port that thelon proxy will connelonct to")

  final val FinaglelonHttpClielonntWithProxy = "FinaglelonHttpClielonntWithProxy"

  /**
   * Providelon a Finaglelon HTTP clielonnt with elongrelonss Proxy support
   *
   * Notelon that thelon timelonouts configurelond in this modulelon arelon melonant to belon a relonasonablelon starting point
   * only. To furthelonr tuning thelon selonttings, elonithelonr ovelonrridelon thelon flags or crelonatelon local copy of thelon modulelon.
   *
   * @param proxyTwittelonrHost       Twittelonr elongrelonss proxy host
   * @param proxyTwittelonrPort       Twittelonr elongrelonss proxy port
   * @param proxyRelonmotelonHost        Relonmotelon proxy host
   * @param proxyRelonmotelonPort        Relonmotelon proxy port
   * @param relonquelonstTimelonout         HTTP clielonnt relonquelonst timelonout
   * @param connelonctTimelonout         HTTP clielonnt transport connelonct timelonout
   * @param acquisitionTimelonout     HTTP clielonnt selonssion acquisition timelonout
   * @param isSelonrvicelonLocal         If this is a Local delonploymelonnt for telonsting
   * @param statsReloncelonivelonr          Stats
   *
   * @relonturn Finaglelon HTTP clielonnt with elongrelonss Proxy support
   */
  @Providelons
  @Singlelonton
  @Namelond(FinaglelonHttpClielonntWithProxy)
  delonf providelonsFinaglelonHttpClielonntWithProxy(
    @Flag(HttpClielonntWithProxyTwittelonrHost) proxyTwittelonrHost: String,
    @Flag(HttpClielonntWithProxyTwittelonrPort) proxyTwittelonrPort: Int,
    @Flag(HttpClielonntWithProxyRelonmotelonHost) proxyRelonmotelonHost: String,
    @Flag(HttpClielonntWithProxyRelonmotelonPort) proxyRelonmotelonPort: Int,
    @Flag(HttpClielonntRelonquelonstTimelonout) relonquelonstTimelonout: Duration,
    @Flag(HttpClielonntConnelonctTimelonout) connelonctTimelonout: Duration,
    @Flag(HttpClielonntAcquisitionTimelonout) acquisitionTimelonout: Duration,
    @Flag(SelonrvicelonLocal) isSelonrvicelonLocal: Boolelonan,
    statsReloncelonivelonr: StatsReloncelonivelonr
  ): Http.Clielonnt = {
    val twittelonrProxyHostPort = HttpHostPort(proxyTwittelonrHost, proxyTwittelonrPort)
    val relonmotelonProxyHostPort = HttpHostPort(proxyRelonmotelonHost, proxyRelonmotelonPort)

    buildFinaglelonHttpClielonntWithProxy(
      twittelonrProxyHostPort = twittelonrProxyHostPort,
      relonmotelonProxyHostPort = relonmotelonProxyHostPort,
      relonquelonstTimelonout = relonquelonstTimelonout,
      connelonctTimelonout = connelonctTimelonout,
      acquisitionTimelonout = acquisitionTimelonout,
      statsReloncelonivelonr = statsReloncelonivelonr
    )
  }
}
