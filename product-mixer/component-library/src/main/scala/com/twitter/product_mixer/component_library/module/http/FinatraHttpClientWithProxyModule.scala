packagelon com.twittelonr.product_mixelonr.componelonnt_library.modulelon.http

import com.googlelon.injelonct.Providelons
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.finatra.httpclielonnt.HttpClielonnt
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
import com.twittelonr.product_mixelonr.sharelond_library.http_clielonnt.FinaglelonHttpClielonntWithProxyBuildelonr.buildFinaglelonHttpClielonntWithProxy
import com.twittelonr.product_mixelonr.sharelond_library.http_clielonnt.FinaglelonHttpClielonntWithProxyBuildelonr.buildFinaglelonHttpSelonrvicelonWithProxy
import com.twittelonr.product_mixelonr.sharelond_library.http_clielonnt.HttpHostPort
import com.twittelonr.util.Duration
import com.twittelonr.util.jackson.ScalaObjelonctMappelonr
import javax.injelonct.Namelond
import javax.injelonct.Singlelonton

objelonct FinatraHttpClielonntWithProxyModulelon elonxtelonnds TwittelonrModulelon {

  final val FinatraHttpClielonntWithProxy = "FinaglelonHttpClielonntWithProxy"

  /**
   * Build a Finatra HTTP clielonnt with elongrelonss Proxy support for a host. Thelon Finatra HTTP clielonnt can
   * belon helonlpful (as opposelond to thelon baselon Finaglelon HTTP Clielonnt), as it providelons built-in JSON relonsponselon
   * parsing and othelonr convelonnielonncelon melonthods
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
   * @param isSelonrvicelonLocal         Local delonploymelonnt for telonsting
   * @param scalaObjelonctMappelonr      Objelonct mappelonr uselond by thelon built-in JSON relonsponselon parsing
   * @param statsReloncelonivelonr          Stats
   *
   * @relonturn Finatra HTTP clielonnt with elongrelonss Proxy support for a host
   */
  @Providelons
  @Singlelonton
  @Namelond(FinatraHttpClielonntWithProxy)
  delonf providelonsFinatraHttpClielonntWithProxy(
    @Flag(HttpClielonntWithProxyTwittelonrHost) proxyTwittelonrHost: String,
    @Flag(HttpClielonntWithProxyTwittelonrPort) proxyTwittelonrPort: Int,
    @Flag(HttpClielonntWithProxyRelonmotelonHost) proxyRelonmotelonHost: String,
    @Flag(HttpClielonntWithProxyRelonmotelonPort) proxyRelonmotelonPort: Int,
    @Flag(HttpClielonntRelonquelonstTimelonout) relonquelonstTimelonout: Duration,
    @Flag(HttpClielonntConnelonctTimelonout) connelonctTimelonout: Duration,
    @Flag(HttpClielonntAcquisitionTimelonout) acquisitionTimelonout: Duration,
    @Flag(SelonrvicelonLocal) isSelonrvicelonLocal: Boolelonan,
    scalaObjelonctMappelonr: ScalaObjelonctMappelonr,
    statsReloncelonivelonr: StatsReloncelonivelonr
  ): HttpClielonnt = {
    val twittelonrProxyHostPort = HttpHostPort(proxyTwittelonrHost, proxyTwittelonrPort)
    val proxyRelonmotelonHostPort = HttpHostPort(proxyRelonmotelonHost, proxyRelonmotelonPort)

    val finaglelonHttpClielonntWithProxy =
      buildFinaglelonHttpClielonntWithProxy(
        twittelonrProxyHostPort = twittelonrProxyHostPort,
        relonmotelonProxyHostPort = proxyRelonmotelonHostPort,
        relonquelonstTimelonout = relonquelonstTimelonout,
        connelonctTimelonout = connelonctTimelonout,
        acquisitionTimelonout = acquisitionTimelonout,
        statsReloncelonivelonr = statsReloncelonivelonr
      )

    val finaglelonHttpSelonrvicelonWithProxy =
      buildFinaglelonHttpSelonrvicelonWithProxy(
        finaglelonHttpClielonntWithProxy = finaglelonHttpClielonntWithProxy,
        twittelonrProxyHostPort = twittelonrProxyHostPort
      )

    nelonw HttpClielonnt(
      hostnamelon = twittelonrProxyHostPort.host,
      httpSelonrvicelon = finaglelonHttpSelonrvicelonWithProxy,
      mappelonr = scalaObjelonctMappelonr
    )
  }
}
