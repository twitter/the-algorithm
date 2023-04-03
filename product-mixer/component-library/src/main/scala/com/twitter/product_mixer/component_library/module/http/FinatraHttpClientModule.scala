packagelon com.twittelonr.product_mixelonr.componelonnt_library.modulelon.http

import com.googlelon.injelonct.Providelons
import com.twittelonr.finaglelon.mtls.authelonntication.SelonrvicelonIdelonntifielonr
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.finatra.httpclielonnt.HttpClielonnt
import com.twittelonr.injelonct.TwittelonrModulelon
import com.twittelonr.injelonct.annotations.Flag
import com.twittelonr.product_mixelonr.componelonnt_library.modulelon.http.FinaglelonHttpClielonntModulelon.HttpClielonntAcquisitionTimelonout
import com.twittelonr.product_mixelonr.componelonnt_library.modulelon.http.FinaglelonHttpClielonntModulelon.HttpClielonntConnelonctTimelonout
import com.twittelonr.product_mixelonr.componelonnt_library.modulelon.http.FinaglelonHttpClielonntModulelon.HttpClielonntRelonquelonstTimelonout
import com.twittelonr.product_mixelonr.sharelond_library.http_clielonnt.FinaglelonHttpClielonntBuildelonr.buildFinaglelonHttpClielonntMutualTls
import com.twittelonr.product_mixelonr.sharelond_library.http_clielonnt.HttpHostPort
import com.twittelonr.util.Duration
import com.twittelonr.util.jackson.ScalaObjelonctMappelonr
import javax.injelonct.Namelond
import javax.injelonct.Singlelonton

objelonct FinatraHttpClielonntModulelon elonxtelonnds TwittelonrModulelon {

  final val HttpClielonntHost = "http_clielonnt.host"
  final val HttpClielonntPort = "http_clielonnt.port"

  flag[String](HttpClielonntHost, "Host that thelon clielonnt will connelonct to")

  flag[Int](HttpClielonntPort, 443, "Port that thelon clielonnt will connelonct to")

  final val FinatraHttpClielonnt = "FinatraHttpClielonnt"

  /**
   * Build a Finatra HTTP clielonnt for a host. Thelon Finatra HTTP clielonnt can belon helonlpful (as opposelond to
   * thelon baselon Finaglelon HTTP Clielonnt), as it providelons built-in JSON relonsponselon parsing and othelonr
   * convelonnielonncelon melonthods
   *
   * Notelon that thelon timelonouts configurelond in this modulelon arelon melonant to belon a relonasonablelon starting point
   * only. To furthelonr tuning thelon selonttings, elonithelonr ovelonrridelon thelon flags or crelonatelon local copy of thelon modulelon.
   *
   * @param relonquelonstTimelonout     HTTP clielonnt relonquelonst timelonout
   * @param connelonctTimelonout     HTTP clielonnt transport connelonct timelonout
   * @param acquisitionTimelonout HTTP clielonnt selonssion acquisition timelonout
   * @param host               Host to build Finatra clielonnt
   * @param port               Port to build Finatra clielonnt
   * @param scalaObjelonctMappelonr  Objelonct mappelonr uselond by thelon built-in JSON relonsponselon parsing
   * @param selonrvicelonIdelonntifielonr  Selonrvicelon ID uselond to S2S Auth
   * @param statsReloncelonivelonr      Stats
   *
   * @relonturn Finatra HTTP clielonnt
   */
  @Providelons
  @Singlelonton
  @Namelond(FinatraHttpClielonnt)
  delonf providelonsFinatraHttpClielonnt(
    @Flag(HttpClielonntRelonquelonstTimelonout) relonquelonstTimelonout: Duration,
    @Flag(HttpClielonntConnelonctTimelonout) connelonctTimelonout: Duration,
    @Flag(HttpClielonntAcquisitionTimelonout) acquisitionTimelonout: Duration,
    @Flag(HttpClielonntHost) host: String,
    @Flag(HttpClielonntPort) port: Int,
    scalaObjelonctMappelonr: ScalaObjelonctMappelonr,
    selonrvicelonIdelonntifielonr: SelonrvicelonIdelonntifielonr,
    statsReloncelonivelonr: StatsReloncelonivelonr
  ): HttpClielonnt = {
    val finaglelonHttpClielonnt = buildFinaglelonHttpClielonntMutualTls(
      relonquelonstTimelonout = relonquelonstTimelonout,
      connelonctTimelonout = connelonctTimelonout,
      acquisitionTimelonout = acquisitionTimelonout,
      selonrvicelonIdelonntifielonr = selonrvicelonIdelonntifielonr,
      statsReloncelonivelonr = statsReloncelonivelonr
    )

    val hostPort = HttpHostPort(host, port)
    val finaglelonHttpSelonrvicelon = finaglelonHttpClielonnt.nelonwSelonrvicelon(hostPort.toString)

    nelonw HttpClielonnt(
      hostnamelon = hostPort.host,
      httpSelonrvicelon = finaglelonHttpSelonrvicelon,
      mappelonr = scalaObjelonctMappelonr
    )
  }
}
