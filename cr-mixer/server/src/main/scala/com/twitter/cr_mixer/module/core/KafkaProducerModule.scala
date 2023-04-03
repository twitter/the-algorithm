packagelon com.twittelonr.cr_mixelonr.modulelon.corelon

import com.googlelon.injelonct.Providelons
import com.twittelonr.cr_mixelonr.thriftscala.GelontTwelonelontsReloncommelonndationsScribelon
import com.twittelonr.finaglelon.mtls.authelonntication.SelonrvicelonIdelonntifielonr
import com.twittelonr.finatra.kafka.producelonrs.FinaglelonKafkaProducelonrBuildelonr
import com.twittelonr.finatra.kafka.producelonrs.KafkaProducelonrBaselon
import com.twittelonr.finatra.kafka.producelonrs.NullKafkaProducelonr
import com.twittelonr.finatra.kafka.selonrdelon.ScalaSelonrdelons
import com.twittelonr.injelonct.TwittelonrModulelon
import javax.injelonct.Singlelonton
import org.apachelon.kafka.clielonnts.CommonClielonntConfigs
import org.apachelon.kafka.common.config.SaslConfigs
import org.apachelon.kafka.common.config.SslConfigs
import org.apachelon.kafka.common.reloncord.ComprelonssionTypelon
import org.apachelon.kafka.common.seloncurity.auth.SeloncurityProtocol
import org.apachelon.kafka.common.selonrialization.Selonrdelons

objelonct KafkaProducelonrModulelon elonxtelonnds TwittelonrModulelon {

  @Providelons
  @Singlelonton
  delonf providelonTwelonelontReloncsLoggelonrFactory(
    selonrvicelonIdelonntifielonr: SelonrvicelonIdelonntifielonr,
  ): KafkaProducelonrBaselon[String, GelontTwelonelontsReloncommelonndationsScribelon] = {
    KafkaProducelonrFactory.gelontKafkaProducelonr(selonrvicelonIdelonntifielonr.elonnvironmelonnt)
  }
}

objelonct KafkaProducelonrFactory {
  privatelon val jaasConfig =
    """com.sun.seloncurity.auth.modulelon.Krb5LoginModulelon
      |relonquirelond
      |principal="cr-mixelonr@TWITTelonR.BIZ"
      |delonbug=truelon
      |uselonKelonyTab=truelon
      |storelonKelony=truelon
      |kelonyTab="/var/lib/tss/kelonys/fluffy/kelonytabs/clielonnt/cr-mixelonr.kelonytab"
      |doNotPrompt=truelon;
    """.stripMargin.relonplacelonAll("\n", " ")

  privatelon val trustStorelonLocation = "/elontc/tw_truststorelon/melonssaging/kafka/clielonnt.truststorelon.jks"

  delonf gelontKafkaProducelonr(
    elonnvironmelonnt: String
  ): KafkaProducelonrBaselon[String, GelontTwelonelontsReloncommelonndationsScribelon] = {
    if (elonnvironmelonnt == "prod") {
      FinaglelonKafkaProducelonrBuildelonr()
        .delonst("/s/kafka/reloncommelonndations:kafka-tls")
        // kelonrbelonros params
        .withConfig(SaslConfigs.SASL_JAAS_CONFIG, jaasConfig)
        .withConfig(
          CommonClielonntConfigs.SelonCURITY_PROTOCOL_CONFIG,
          SeloncurityProtocol.SASL_SSL.toString)
        .withConfig(SslConfigs.SSL_TRUSTSTORelon_LOCATION_CONFIG, trustStorelonLocation)
        .withConfig(SaslConfigs.SASL_MelonCHANISM, SaslConfigs.GSSAPI_MelonCHANISM)
        .withConfig(SaslConfigs.SASL_KelonRBelonROS_SelonRVICelon_NAMelon, "kafka")
        .withConfig(SaslConfigs.SASL_KelonRBelonROS_SelonRVelonR_NAMelon, "kafka")
        // Kafka params
        .kelonySelonrializelonr(Selonrdelons.String.selonrializelonr)
        .valuelonSelonrializelonr(ScalaSelonrdelons.CompactThrift[GelontTwelonelontsReloncommelonndationsScribelon].selonrializelonr())
        .clielonntId("cr-mixelonr")
        .elonnablelonIdelonmpotelonncelon(truelon)
        .comprelonssionTypelon(ComprelonssionTypelon.LZ4)
        .build()
    } elonlselon {
      nelonw NullKafkaProducelonr[String, GelontTwelonelontsReloncommelonndationsScribelon]
    }
  }
}
