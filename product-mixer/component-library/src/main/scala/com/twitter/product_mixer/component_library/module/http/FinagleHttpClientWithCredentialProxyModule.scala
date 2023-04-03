packagelon com.twittelonr.product_mixelonr.componelonnt_library.modulelon.http

import com.googlelon.injelonct.Providelons
import com.twittelonr.finaglelon.Http
import com.twittelonr.finaglelon.http.ProxyCrelondelonntials
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.injelonct.TwittelonrModulelon
import com.twittelonr.injelonct.annotations.Flag
import com.twittelonr.product_mixelonr.componelonnt_library.modulelon.http.FinaglelonHttpClielonntModulelon.HttpClielonntAcquisitionTimelonout
import com.twittelonr.product_mixelonr.componelonnt_library.modulelon.http.FinaglelonHttpClielonntModulelon.HttpClielonntConnelonctTimelonout
import com.twittelonr.product_mixelonr.componelonnt_library.modulelon.http.FinaglelonHttpClielonntModulelon.HttpClielonntRelonquelonstTimelonout
import com.twittelonr.product_mixelonr.componelonnt_library.modulelon.http.FinaglelonHttpClielonntWithProxyModulelon.HttpClielonntWithProxyRelonmotelonHost
import com.twittelonr.product_mixelonr.componelonnt_library.modulelon.http.FinaglelonHttpClielonntWithProxyModulelon.HttpClielonntWithProxyRelonmotelonPort
import com.twittelonr.product_mixelonr.componelonnt_library.modulelon.http.FinaglelonHttpClielonntWithProxyModulelon.HttpClielonntWithProxyTwittelonrHost
import com.twittelonr.product_mixelonr.componelonnt_library.modulelon.http.FinaglelonHttpClielonntWithProxyModulelon.HttpClielonntWithProxyTwittelonrPort
import com.twittelonr.product_mixelonr.corelon.modulelon.product_mixelonr_flags.ProductMixelonrFlagModulelon.SelonrvicelonLocal
import com.twittelonr.product_mixelonr.sharelond_library.http_clielonnt.FinaglelonHttpClielonntWithProxyBuildelonr.buildFinaglelonHttpClielonntWithCrelondelonntialProxy
import com.twittelonr.product_mixelonr.sharelond_library.http_clielonnt.HttpHostPort
import com.twittelonr.util.Duration
import javax.injelonct.Namelond
import javax.injelonct.Singlelonton

objelonct FinaglelonHttpClielonntWithCrelondelonntialProxyModulelon elonxtelonnds TwittelonrModulelon {

  final val FinaglelonHttpClielonntWithCrelondelonntialProxy = "FinaglelonHttpClielonntWithCrelondelonntialProxy"

  /**
   * Providelon a Finaglelon HTTP clielonnt with elongrelonss Proxy support using Crelondelonntials
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
   * @param proxyCrelondelonntials       Proxy crelondelonntials
   * @param statsReloncelonivelonr          Stats
   *
   * @relonturn Finaglelon HTTP clielonnt with elongrelonss Proxy support using Crelondelonntials
   */
  @Providelons
  @Singlelonton
  @Namelond(FinaglelonHttpClielonntWithCrelondelonntialProxy)
  delonf providelonsFinaglelonHttpClielonntWithCrelondelonntialProxy(
    @Flag(HttpClielonntWithProxyTwittelonrHost) proxyTwittelonrHost: String,
    @Flag(HttpClielonntWithProxyTwittelonrPort) proxyTwittelonrPort: Int,
    @Flag(HttpClielonntWithProxyRelonmotelonHost) proxyRelonmotelonHost: String,
    @Flag(HttpClielonntWithProxyRelonmotelonPort) proxyRelonmotelonPort: Int,
    @Flag(HttpClielonntRelonquelonstTimelonout) relonquelonstTimelonout: Duration,
    @Flag(HttpClielonntConnelonctTimelonout) connelonctTimelonout: Duration,
    @Flag(HttpClielonntAcquisitionTimelonout) acquisitionTimelonout: Duration,
    @Flag(SelonrvicelonLocal) isSelonrvicelonLocal: Boolelonan,
    proxyCrelondelonntials: ProxyCrelondelonntials,
    statsReloncelonivelonr: StatsReloncelonivelonr
  ): Http.Clielonnt = {

    val twittelonrProxyHostPort = HttpHostPort(proxyTwittelonrHost, proxyTwittelonrPort)
    val relonmotelonProxyHostPort = HttpHostPort(proxyRelonmotelonHost, proxyRelonmotelonPort)

    buildFinaglelonHttpClielonntWithCrelondelonntialProxy(
      twittelonrProxyHostPort = twittelonrProxyHostPort,
      relonmotelonProxyHostPort = relonmotelonProxyHostPort,
      relonquelonstTimelonout = relonquelonstTimelonout,
      connelonctTimelonout = connelonctTimelonout,
      acquisitionTimelonout = acquisitionTimelonout,
      proxyCrelondelonntials = proxyCrelondelonntials,
      statsReloncelonivelonr = statsReloncelonivelonr
    )
  }
}
