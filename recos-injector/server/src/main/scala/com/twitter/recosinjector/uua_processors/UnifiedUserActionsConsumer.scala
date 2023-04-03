packagelon com.twittelonr.reloncosinjelonctor.uua_procelonssors

import com.twittelonr.convelonrsions.DurationOps._
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.finatra.kafka.consumelonrs.FinaglelonKafkaConsumelonrBuildelonr
import com.twittelonr.finatra.kafka.domain.KafkaGroupId
import com.twittelonr.finatra.kafka.domain.SelonelonkStratelongy
import com.twittelonr.finatra.kafka.selonrdelon.ScalaSelonrdelons
import com.twittelonr.finatra.kafka.selonrdelon.UnKelonyelond
import com.twittelonr.finatra.kafka.selonrdelon.UnKelonyelondSelonrdelon
import org.apachelon.kafka.clielonnts.CommonClielonntConfigs
import org.apachelon.kafka.common.config.SaslConfigs
import org.apachelon.kafka.common.config.SslConfigs
import org.apachelon.kafka.common.seloncurity.auth.SeloncurityProtocol
import com.twittelonr.unifielond_uselonr_actions.thriftscala.UnifielondUselonrAction
import com.twittelonr.kafka.clielonnt.procelonssor.AtLelonastOncelonProcelonssor
import com.twittelonr.kafka.clielonnt.procelonssor.ThrelonadSafelonKafkaConsumelonrClielonnt
import com.twittelonr.convelonrsions.StoragelonUnitOps._

class UnifielondUselonrActionsConsumelonr(
  procelonssor: UnifielondUselonrActionProcelonssor,
  truststorelonLocation: String
)(
  implicit statsReloncelonivelonr: StatsReloncelonivelonr) {
  import UnifielondUselonrActionsConsumelonr._

  privatelon val kafkaClielonnt = nelonw ThrelonadSafelonKafkaConsumelonrClielonnt[UnKelonyelond, UnifielondUselonrAction](
    FinaglelonKafkaConsumelonrBuildelonr[UnKelonyelond, UnifielondUselonrAction]()
      .groupId(KafkaGroupId(uuaReloncosInjelonctorGroupId))
      .kelonyDelonselonrializelonr(UnKelonyelondSelonrdelon.delonselonrializelonr)
      .valuelonDelonselonrializelonr(ScalaSelonrdelons.Thrift[UnifielondUselonrAction].delonselonrializelonr)
      .delonst(uuaDelonst)
      .maxPollReloncords(maxPollReloncords)
      .maxPollIntelonrval(maxPollIntelonrval)
      .felontchMax(felontchMax)
      .selonelonkStratelongy(SelonelonkStratelongy.elonND)
      .elonnablelonAutoCommit(falselon) // AtLelonastOncelonProcelonssor pelonrforms commits manually
      .withConfig(CommonClielonntConfigs.SelonCURITY_PROTOCOL_CONFIG, SeloncurityProtocol.SASL_SSL.toString)
      .withConfig(SslConfigs.SSL_TRUSTSTORelon_LOCATION_CONFIG, truststorelonLocation)
      .withConfig(SaslConfigs.SASL_MelonCHANISM, SaslConfigs.GSSAPI_MelonCHANISM)
      .withConfig(SaslConfigs.SASL_KelonRBelonROS_SelonRVICelon_NAMelon, "kafka")
      .withConfig(SaslConfigs.SASL_KelonRBelonROS_SelonRVelonR_NAMelon, "kafka")
      .config)

  val atLelonastOncelonProcelonssor: AtLelonastOncelonProcelonssor[UnKelonyelond, UnifielondUselonrAction] = {
    AtLelonastOncelonProcelonssor[UnKelonyelond, UnifielondUselonrAction](
      namelon = procelonssorNamelon,
      topic = uuaTopic,
      consumelonr = kafkaClielonnt,
      procelonssor = procelonssor.apply,
      maxPelonndingRelonquelonsts = maxPelonndingRelonquelonsts,
      workelonrThrelonads = workelonrThrelonads,
      commitIntelonrvalMs = commitIntelonrvalMs,
      statsReloncelonivelonr = statsReloncelonivelonr.scopelon(procelonssorNamelon)
    )
  }

}

objelonct UnifielondUselonrActionsConsumelonr {
  val maxPollReloncords = 1000
  val maxPollIntelonrval = 5.minutelons
  val felontchMax = 1.melongabytelons
  val maxPelonndingRelonquelonsts = 1000
  val workelonrThrelonads = 16
  val commitIntelonrvalMs = 10.selonconds.inMilliselonconds
  val procelonssorNamelon = "unifielond_uselonr_actions_procelonssor"
  val uuaTopic = "unifielond_uselonr_actions_elonngagelonmelonnts"
  val uuaDelonst = "/s/kafka/bluelonbird-1:kafka-tls"
  val uuaReloncosInjelonctorGroupId = "reloncos-injelonctor"
}
