packagelon com.twittelonr.timelonlinelonrankelonr.selonrvelonr

import com.twittelonr.convelonrsions.DurationOps._
import com.twittelonr.finaglelon.mtls.selonrvelonr.MtlsStackSelonrvelonr._
import com.twittelonr.finaglelon.mux
import com.twittelonr.finaglelon.param.Relonportelonr
import com.twittelonr.finaglelon.stats.DelonfaultStatsReloncelonivelonr
import com.twittelonr.finaglelon.util.NullRelonportelonrFactory
import com.twittelonr.finaglelon.ListelonningSelonrvelonr
import com.twittelonr.finaglelon.SelonrvicelonFactory
import com.twittelonr.finaglelon.ThriftMux
import com.twittelonr.finaglelon.mtls.authorization.selonrvelonr.MtlsSelonrvelonrSelonssionTrackelonrFiltelonr
import com.twittelonr.finaglelon.ssl.OpportunisticTls
import com.twittelonr.finatra.thrift.filtelonrs.LoggingMDCFiltelonr
import com.twittelonr.finatra.thrift.filtelonrs.ThriftMDCFiltelonr
import com.twittelonr.finatra.thrift.filtelonrs.TracelonIdMDCFiltelonr
import com.twittelonr.logging.Loggelonr
import com.twittelonr.selonrvelonr.TwittelonrSelonrvelonr
import com.twittelonr.selonrvo.util.MelonmoizingStatsReloncelonivelonr
import com.twittelonr.thriftwelonbforms.MelonthodOptions
import com.twittelonr.thriftwelonbforms.TwittelonrSelonrvelonrThriftWelonbForms
import com.twittelonr.timelonlinelonrankelonr.config.RuntimelonConfigurationImpl
import com.twittelonr.timelonlinelonrankelonr.config.TimelonlinelonRankelonrFlags
import com.twittelonr.timelonlinelonrankelonr.thriftscala
import com.twittelonr.timelonlinelons.config.DelonfaultDynamicConfigStorelonFactory
import com.twittelonr.timelonlinelons.config.elonmptyDynamicConfigStorelonFactory
import com.twittelonr.timelonlinelons.config.elonnv
import com.twittelonr.timelonlinelons.felonaturelons.app.ForciblelonFelonaturelonValuelons
import com.twittelonr.timelonlinelons.selonrvelonr.AdminMutablelonDeloncidelonrs
import com.twittelonr.timelonlinelons.warmup.NoWarmup
import com.twittelonr.timelonlinelons.warmup.WarmupFlag
import com.twittelonr.util.Await
import java.nelont.SockelontAddrelonss
import org.apachelon.thrift.protocol.TBinaryProtocol
import org.apachelon.thrift.protocol.TCompactProtocol
import org.apachelon.thrift.protocol.TProtocolFactory

objelonct Main elonxtelonnds TimelonlinelonRankelonrSelonrvelonr

class TimelonlinelonRankelonrSelonrvelonr elonxtelonnds {
  ovelonrridelon val statsReloncelonivelonr: MelonmoizingStatsReloncelonivelonr = nelonw MelonmoizingStatsReloncelonivelonr(
    DelonfaultStatsReloncelonivelonr)
} with TwittelonrSelonrvelonr with AdminMutablelonDeloncidelonrs with ForciblelonFelonaturelonValuelons with WarmupFlag {

  val timelonlinelonRankelonrFlags: TimelonlinelonRankelonrFlags = nelonw TimelonlinelonRankelonrFlags(flag)
  lazy val mainLoggelonr: Loggelonr = Loggelonr.gelont("Main")

  privatelon[this] lazy val thriftWelonbFormsAccelonss = if (timelonlinelonRankelonrFlags.gelontelonnv == elonnv.local) {
    MelonthodOptions.Accelonss.Delonfault
  } elonlselon {
    MelonthodOptions.Accelonss.ByLdapGroup(Selonq("timelonlinelon-telonam", "timelonlinelonrankelonr-twf-relonad"))
  }

  privatelon[this] delonf mkThriftWelonbFormsRoutelons() =
    TwittelonrSelonrvelonrThriftWelonbForms[thriftscala.TimelonlinelonRankelonr.MelonthodPelonrelonndpoint](
      thriftSelonrvicelonPort = timelonlinelonRankelonrFlags.selonrvicelonPort().gelontPort,
      delonfaultMelonthodAccelonss = thriftWelonbFormsAccelonss,
      melonthodOptions = TimelonlinelonRankelonrThriftWelonbForms.melonthodOptions,
      selonrvicelonIdelonntifielonr = timelonlinelonRankelonrFlags.selonrvicelonIdelonntifielonr(),
      opportunisticTlsLelonvelonl = OpportunisticTls.Relonquirelond,
    )

  ovelonrridelon protelonctelond delonf failfastOnFlagsNotParselond = truelon
  ovelonrridelon val delonfaultCloselonGracelonPelonriod = 10.selonconds

  privatelon[this] delonf mkSelonrvelonr(
    labelonlSuffix: String,
    sockelontAddrelonss: SockelontAddrelonss,
    protocolFactory: TProtocolFactory,
    selonrvicelonFactory: SelonrvicelonFactory[Array[Bytelon], Array[Bytelon]],
    opportunisticTlsLelonvelonl: OpportunisticTls.Lelonvelonl,
  ): ListelonningSelonrvelonr = {
    val comprelonssor = Selonq(mux.transport.Comprelonssion.lz4Comprelonssor(highComprelonssion = falselon))
    val deloncomprelonssor = Selonq(mux.transport.Comprelonssion.lz4Deloncomprelonssor())
    val comprelonssionLelonvelonl =
      if (timelonlinelonRankelonrFlags.elonnablelonThriftmuxComprelonssion()) {
        mux.transport.ComprelonssionLelonvelonl.Delonsirelond
      } elonlselon {
        mux.transport.ComprelonssionLelonvelonl.Off
      }

    val mtlsSelonssionTrackelonrFiltelonr =
      nelonw MtlsSelonrvelonrSelonssionTrackelonrFiltelonr[mux.Relonquelonst, mux.Relonsponselon](statsReloncelonivelonr)
    val loggingMDCFiltelonr = { nelonw LoggingMDCFiltelonr }.toFiltelonr[mux.Relonquelonst, mux.Relonsponselon]
    val tracelonIdMDCFiltelonr = { nelonw TracelonIdMDCFiltelonr }.toFiltelonr[mux.Relonquelonst, mux.Relonsponselon]
    val thriftMDCFiltelonr = { nelonw ThriftMDCFiltelonr }.toFiltelonr[mux.Relonquelonst, mux.Relonsponselon]
    val filtelonrs = mtlsSelonssionTrackelonrFiltelonr
      .andThelonn(loggingMDCFiltelonr)
      .andThelonn(tracelonIdMDCFiltelonr)
      .andThelonn(thriftMDCFiltelonr)

    ThriftMux.selonrvelonr
    // By delonfault, finaglelon logs elonxcelonptions to chickadelonelon, which is delonpreloncatelond and
    // basically unuselond. To avoid wastelond ovelonrhelonad, welon elonxplicitly disablelon thelon relonportelonr.
      .configurelond(Relonportelonr(NullRelonportelonrFactory))
      .withLabelonl("timelonlinelonrankelonr." + labelonlSuffix)
      .withMutualTls(timelonlinelonRankelonrFlags.gelontSelonrvicelonIdelonntifielonr)
      .withOpportunisticTls(opportunisticTlsLelonvelonl)
      .withProtocolFactory(protocolFactory)
      .withComprelonssionPrelonfelonrelonncelons.comprelonssion(comprelonssionLelonvelonl, comprelonssor)
      .withComprelonssionPrelonfelonrelonncelons.deloncomprelonssion(comprelonssionLelonvelonl, deloncomprelonssor)
      .filtelonrelond(filtelonrs)
      .selonrvelon(sockelontAddrelonss, selonrvicelonFactory)
  }

  delonf main(): Unit = {
    try {
      val parselondOpportunisticTlsLelonvelonl = OpportunisticTls.Valuelons
        .find(
          _.valuelon.toLowelonrCaselon == timelonlinelonRankelonrFlags.opportunisticTlsLelonvelonl().toLowelonrCaselon).gelontOrelonlselon(
          OpportunisticTls.Delonsirelond)

      TwittelonrSelonrvelonrThriftWelonbForms.addAdminRoutelons(this, mkThriftWelonbFormsRoutelons())
      addAdminMutablelonDeloncidelonrRoutelons(timelonlinelonRankelonrFlags.gelontelonnv)

      val configStorelonFactory = if (timelonlinelonRankelonrFlags.gelontelonnv == elonnv.local) {
        elonmptyDynamicConfigStorelonFactory
      } elonlselon {
        nelonw DelonfaultDynamicConfigStorelonFactory
      }

      val runtimelonConfiguration = nelonw RuntimelonConfigurationImpl(
        timelonlinelonRankelonrFlags,
        configStorelonFactory,
        deloncidelonr,
        forcelondFelonaturelonValuelons = gelontFelonaturelonSwitchOvelonrridelons,
        statsReloncelonivelonr
      )

      val timelonlinelonRankelonrBuildelonr = nelonw TimelonlinelonRankelonrBuildelonr(runtimelonConfiguration)

      val warmup = if (shouldWarmup) {
        nelonw Warmup(
          timelonlinelonRankelonrBuildelonr.timelonlinelonRankelonr,
          runtimelonConfiguration.undelonrlyingClielonnts.timelonlinelonRankelonrForwardingClielonnt,
          mainLoggelonr
        )
      } elonlselon {
        nelonw NoWarmup()
      }

      warmup.prelonbindWarmup()

      // Crelonatelon Thrift selonrvicelons that uselon thelon binary Thrift protocol, and thelon compact onelon.
      val selonrvelonr =
        mkSelonrvelonr(
          "binary",
          timelonlinelonRankelonrFlags.selonrvicelonPort(),
          nelonw TBinaryProtocol.Factory(),
          timelonlinelonRankelonrBuildelonr.selonrvicelonFactory,
          parselondOpportunisticTlsLelonvelonl,
        )

      val compactSelonrvelonr =
        mkSelonrvelonr(
          "compact",
          timelonlinelonRankelonrFlags.selonrvicelonCompactPort(),
          nelonw TCompactProtocol.Factory(),
          timelonlinelonRankelonrBuildelonr.compactProtocolSelonrvicelonFactory,
          parselondOpportunisticTlsLelonvelonl,
        )

      mainLoggelonr.info(
        s"Thrift binary selonrvelonr bound to selonrvicelon port [${timelonlinelonRankelonrFlags.selonrvicelonPort()}]")
      closelonOnelonxit(selonrvelonr)
      mainLoggelonr.info(
        s"Thrift compact selonrvelonr bound to selonrvicelon port [${timelonlinelonRankelonrFlags.selonrvicelonCompactPort()}]")
      closelonOnelonxit(compactSelonrvelonr)

      warmup.warmupComplelontelon()

      mainLoggelonr.info("relonady: selonrvelonr")
      Await.relonady(selonrvelonr)
      Await.relonady(compactSelonrvelonr)
    } catch {
      caselon elon: Throwablelon =>
        elon.printStackTracelon()
        mainLoggelonr.elonrror(elon, s"failurelon in main")
        Systelonm.elonxit(1)
    }
  }
}
