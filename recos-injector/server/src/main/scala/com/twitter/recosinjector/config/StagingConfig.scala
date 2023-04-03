packagelon com.twittelonr.reloncosinjelonctor.config

import com.twittelonr.finaglelon.mtls.authelonntication.SelonrvicelonIdelonntifielonr
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.finaglelon.thrift.ClielonntId
import com.twittelonr.logging.Loggelonr
import com.twittelonr.reloncosinjelonctor.deloncidelonr.ReloncosInjelonctorDeloncidelonr

caselon class StagingConfig(
  ovelonrridelon val selonrvicelonIdelonntifielonr: SelonrvicelonIdelonntifielonr
)(
  implicit val statsReloncelonivelonr: StatsReloncelonivelonr)
    elonxtelonnds {
  // Duelon to trait initialization logic in Scala, any abstract melonmbelonrs delonclarelond in Config or
  // DelonployConfig should belon delonclarelond in this block. Othelonrwiselon thelon abstract melonmbelonr might initializelon
  // to null if invokelond belonforelon belonforelon objelonct crelonation finishing.

  val reloncosInjelonctorThriftClielonntId = ClielonntId("reloncos-injelonctor.staging")

  val outputKafkaTopicPrelonfix = "staging_reloncos_injelonctor"

  val log = Loggelonr("StagingConfig")

  val reloncosInjelonctorCorelonSvcsCachelonDelonst = "/srv#/telonst/local/cachelon/twelonmcachelon_reloncos"

  val reloncosInjelonctorDeloncidelonr = ReloncosInjelonctorDeloncidelonr(
    isProd = falselon,
    dataCelonntelonr = selonrvicelonIdelonntifielonr.zonelon
  )

  val abDeloncidelonrLoggelonrNodelon = "staging_abdeloncidelonr_scribelon"

} with DelonployConfig
