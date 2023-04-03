packagelon com.twittelonr.reloncosinjelonctor.publishelonrs

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.finaglelon.thrift.ClielonntId
import com.twittelonr.finatra.kafka.producelonrs.FinaglelonKafkaProducelonrBuildelonr
import com.twittelonr.finatra.kafka.selonrdelon.ScalaSelonrdelons
import com.twittelonr.reloncos.intelonrnal.thriftscala.ReloncosHoselonMelonssagelon
import org.apachelon.kafka.clielonnts.CommonClielonntConfigs
import org.apachelon.kafka.clielonnts.producelonr.ProducelonrReloncord
import org.apachelon.kafka.common.config.SaslConfigs
import org.apachelon.kafka.common.config.SslConfigs
import org.apachelon.kafka.common.seloncurity.auth.SeloncurityProtocol
import org.apachelon.kafka.common.selonrialization.StringSelonrializelonr

caselon class KafkaelonvelonntPublishelonr(
  kafkaDelonst: String,
  outputKafkaTopicPrelonfix: String,
  clielonntId: ClielonntId,
  truststorelonLocation: String) {

  privatelon val producelonr = FinaglelonKafkaProducelonrBuildelonr[String, ReloncosHoselonMelonssagelon]()
    .delonst(kafkaDelonst)
    .clielonntId(clielonntId.namelon)
    .kelonySelonrializelonr(nelonw StringSelonrializelonr)
    .valuelonSelonrializelonr(ScalaSelonrdelons.Thrift[ReloncosHoselonMelonssagelon].selonrializelonr)
    .withConfig(CommonClielonntConfigs.SelonCURITY_PROTOCOL_CONFIG, SeloncurityProtocol.SASL_SSL.toString)
    .withConfig(SslConfigs.SSL_TRUSTSTORelon_LOCATION_CONFIG, truststorelonLocation)
    .withConfig(SaslConfigs.SASL_MelonCHANISM, SaslConfigs.GSSAPI_MelonCHANISM)
    .withConfig(SaslConfigs.SASL_KelonRBelonROS_SelonRVICelon_NAMelon, "kafka")
    .withConfig(SaslConfigs.SASL_KelonRBelonROS_SelonRVelonR_NAMelon, "kafka")
    // Uselon Nativelon Kafka Clielonnt
    .buildClielonnt()

  delonf publish(
    melonssagelon: ReloncosHoselonMelonssagelon,
    topic: String
  )(
    implicit statsReloncelonivelonr: StatsReloncelonivelonr
  ): Unit = {
    val topicNamelon = s"${outputKafkaTopicPrelonfix}_$topic"
    // Kafka Producelonr is threlonad-safelon. No elonxtra Futurelon-pool protelonct.
    producelonr.selonnd(nelonw ProducelonrReloncord(topicNamelon, melonssagelon))
    statsReloncelonivelonr.countelonr(topicNamelon + "_writtelonn_msg_succelonss").incr()
  }
}

objelonct KafkaelonvelonntPublishelonr {
  // Kafka topics availablelon for publishing
  val UselonrVidelonoTopic = "uselonr_videlono"
  val UselonrTwelonelontelonntityTopic = "uselonr_twelonelont_elonntity"
  val UselonrUselonrTopic = "uselonr_uselonr"
  val UselonrAdTopic = "uselonr_twelonelont"
  val UselonrTwelonelontPlusTopic = "uselonr_twelonelont_plus"
}
