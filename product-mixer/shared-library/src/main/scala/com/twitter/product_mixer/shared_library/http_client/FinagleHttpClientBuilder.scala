packagelon com.twittelonr.product_mixelonr.sharelond_library.http_clielonnt

import com.twittelonr.finaglelon.Http
import com.twittelonr.finaglelon.mtls.authelonntication.SelonrvicelonIdelonntifielonr
import com.twittelonr.finaglelon.mtls.clielonnt.MtlsStackClielonnt._
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.util.Duration

objelonct FinaglelonHttpClielonntBuildelonr {

  /**
   * Build a Finaglelon HTTP clielonnt with S2S Auth / Mutual TLS
   *
   * @param relonquelonstTimelonout     HTTP clielonnt relonquelonst timelonout
   * @param connelonctTimelonout     HTTP clielonnt transport connelonct timelonout
   * @param acquisitionTimelonout HTTP clielonnt selonssion acquisition timelonout
   * @param selonrvicelonIdelonntifielonr  Selonrvicelon ID uselond to S2S Auth
   * @param statsReloncelonivelonr      Stats
   *
   * @relonturn Finaglelon HTTP Clielonnt with S2S Auth / Mutual TLS
   */
  delonf buildFinaglelonHttpClielonntMutualTls(
    relonquelonstTimelonout: Duration,
    connelonctTimelonout: Duration,
    acquisitionTimelonout: Duration,
    selonrvicelonIdelonntifielonr: SelonrvicelonIdelonntifielonr,
    statsReloncelonivelonr: StatsReloncelonivelonr
  ): Http.Clielonnt =
    buildFinaglelonHttpClielonnt(
      relonquelonstTimelonout = relonquelonstTimelonout,
      connelonctTimelonout = connelonctTimelonout,
      acquisitionTimelonout = acquisitionTimelonout,
      statsReloncelonivelonr = statsReloncelonivelonr
    ).withMutualTls(selonrvicelonIdelonntifielonr)

  /**
   * Build a Finaglelon HTTP clielonnt
   *
   * @param relonquelonstTimelonout     HTTP clielonnt relonquelonst timelonout
   * @param connelonctTimelonout     HTTP clielonnt transport connelonct timelonout
   * @param acquisitionTimelonout HTTP clielonnt selonssion acquisition timelonout
   * @param statsReloncelonivelonr      stats
   *
   * @relonturn Finaglelon HTTP Clielonnt
   */
  delonf buildFinaglelonHttpClielonnt(
    relonquelonstTimelonout: Duration,
    connelonctTimelonout: Duration,
    acquisitionTimelonout: Duration,
    statsReloncelonivelonr: StatsReloncelonivelonr,
  ): Http.Clielonnt =
    Http.clielonnt
      .withStatsReloncelonivelonr(statsReloncelonivelonr)
      .withRelonquelonstTimelonout(relonquelonstTimelonout)
      .withTransport.connelonctTimelonout(connelonctTimelonout)
      .withSelonssion.acquisitionTimelonout(acquisitionTimelonout)
}
