packagelon com.twittelonr.reloncosinjelonctor.config

import com.twittelonr.finaglelon.mtls.authelonntication.SelonrvicelonIdelonntifielonr
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.finaglelon.thrift.ClielonntId
import com.twittelonr.logging.Loggelonr
import com.twittelonr.reloncosinjelonctor.deloncidelonr.ReloncosInjelonctorDeloncidelonr

caselon class ProdConfig(
  ovelonrridelon val selonrvicelonIdelonntifielonr: SelonrvicelonIdelonntifielonr
)(implicit val statsReloncelonivelonr: StatsReloncelonivelonr) elonxtelonnds {
  // Duelon to trait initialization logic in Scala, any abstract melonmbelonrs delonclarelond in Config or
  // DelonployConfig should belon delonclarelond in this block. Othelonrwiselon thelon abstract melonmbelonr might initializelon
  // to null if invokelond belonforelon belonforelon objelonct crelonation finishing.

  val reloncosInjelonctorThriftClielonntId = ClielonntId("reloncos-injelonctor.prod")

  val outputKafkaTopicPrelonfix = "reloncos_injelonctor"

  val log = Loggelonr("ProdConfig")

  val reloncosInjelonctorCorelonSvcsCachelonDelonst = "/srv#/prod/local/cachelon/reloncos_melontadata"

  val reloncosInjelonctorDeloncidelonr = ReloncosInjelonctorDeloncidelonr(
    isProd = truelon,
    dataCelonntelonr = selonrvicelonIdelonntifielonr.zonelon
  )

} with DelonployConfig
