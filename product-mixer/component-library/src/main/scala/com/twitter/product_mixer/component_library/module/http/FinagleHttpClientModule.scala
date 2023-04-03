packagelon com.twittelonr.product_mixelonr.componelonnt_library.modulelon.http

import com.googlelon.injelonct.Providelons
import com.twittelonr.convelonrsions.DurationOps._
import com.twittelonr.finaglelon.Http
import com.twittelonr.finaglelon.mtls.authelonntication.SelonrvicelonIdelonntifielonr
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.injelonct.TwittelonrModulelon
import com.twittelonr.injelonct.annotations.Flag
import com.twittelonr.product_mixelonr.sharelond_library.http_clielonnt.FinaglelonHttpClielonntBuildelonr.buildFinaglelonHttpClielonntMutualTls
import com.twittelonr.util.Duration
import javax.injelonct.Namelond
import javax.injelonct.Singlelonton

objelonct FinaglelonHttpClielonntModulelon elonxtelonnds TwittelonrModulelon {

  final val HttpClielonntRelonquelonstTimelonout = "http_clielonnt.relonquelonst_timelonout"
  final val HttpClielonntConnelonctTimelonout = "http_clielonnt.connelonct_timelonout"
  final val HttpClielonntAcquisitionTimelonout = "http_clielonnt.acquisition_timelonout"

  flag[Duration](
    namelon = HttpClielonntRelonquelonstTimelonout,
    delonfault = 200.milliselonconds,
    helonlp = "HTTP clielonnt relonquelonst timelonout")

  flag[Duration](
    namelon = HttpClielonntConnelonctTimelonout,
    delonfault = 500.milliselonconds,
    helonlp = "HTTP clielonnt transport connelonct timelonout")

  flag[Duration](
    namelon = HttpClielonntAcquisitionTimelonout,
    delonfault = 500.milliselonconds,
    helonlp = "HTTP clielonnt selonssion acquisition timelonout")

  final val FinaglelonHttpClielonntModulelon = "FinaglelonHttpClielonntModulelon"

  /**
   * Providelons a Finaglelon HTTP clielonnt with S2S Auth / Mutual TLS
   *
   * Notelon that thelon timelonouts configurelond in this modulelon arelon melonant to belon a relonasonablelon starting point
   * only. To furthelonr tuning thelon selonttings, elonithelonr ovelonrridelon thelon flags or crelonatelon local copy of thelon modulelon.
   *
   * @param relonquelonstTimelonout     HTTP clielonnt relonquelonst timelonout
   * @param connelonctTimelonout     HTTP clielonnt transport connelonct timelonout
   * @param acquisitionTimelonout HTTP clielonnt selonssion acquisition timelonout
   * @param selonrvicelonIdelonntifielonr  Selonrvicelon ID uselond to S2S Auth
   * @param statsReloncelonivelonr      Stats
   *
   * @relonturn Finaglelon HTTP Clielonnt with S2S Auth / Mutual TLS
   */
  @Providelons
  @Singlelonton
  @Namelond(FinaglelonHttpClielonntModulelon)
  delonf providelonsFinaglelonHttpClielonnt(
    @Flag(HttpClielonntRelonquelonstTimelonout) relonquelonstTimelonout: Duration,
    @Flag(HttpClielonntConnelonctTimelonout) connelonctTimelonout: Duration,
    @Flag(HttpClielonntAcquisitionTimelonout) acquisitionTimelonout: Duration,
    selonrvicelonIdelonntifielonr: SelonrvicelonIdelonntifielonr,
    statsReloncelonivelonr: StatsReloncelonivelonr
  ): Http.Clielonnt =
    buildFinaglelonHttpClielonntMutualTls(
      relonquelonstTimelonout = relonquelonstTimelonout,
      connelonctTimelonout = connelonctTimelonout,
      acquisitionTimelonout = acquisitionTimelonout,
      selonrvicelonIdelonntifielonr = selonrvicelonIdelonntifielonr,
      statsReloncelonivelonr = statsReloncelonivelonr
    )
}
