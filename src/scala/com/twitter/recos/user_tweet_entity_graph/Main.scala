packagelon com.twittelonr.reloncos.uselonr_twelonelont_elonntity_graph

import com.twittelonr.abdeloncidelonr.ABDeloncidelonrFactory
import com.twittelonr.abdeloncidelonr.LoggingABDeloncidelonr
import com.twittelonr.app.Flag
import com.twittelonr.convelonrsions.DurationOps._
import com.twittelonr.finaglelon.ThriftMux
import com.twittelonr.finaglelon.http.HttpMuxelonr
import com.twittelonr.finaglelon.mtls.authelonntication.SelonrvicelonIdelonntifielonr
import com.twittelonr.finaglelon.mtls.selonrvelonr.MtlsStackSelonrvelonr._
import com.twittelonr.finaglelon.mux.transport.OpportunisticTls
import com.twittelonr.finaglelon.thrift.ClielonntId
import com.twittelonr.finatra.kafka.consumelonrs.FinaglelonKafkaConsumelonrBuildelonr
import com.twittelonr.finatra.kafka.domain.KafkaGroupId
import com.twittelonr.finatra.kafka.domain.SelonelonkStratelongy
import com.twittelonr.finatra.kafka.selonrdelon.ScalaSelonrdelons
import com.twittelonr.frigatelon.common.util.elonlfOwlFiltelonr
import com.twittelonr.frigatelon.common.util.elonlfOwlFiltelonr.ByLdapGroup
import com.twittelonr.graphjelont.bipartitelon.NodelonMelontadataLelonftIndelonxelondPowelonrLawMultiSelongmelonntBipartitelonGraph
import com.twittelonr.logging._
import com.twittelonr.reloncos.deloncidelonr.UselonrTwelonelontelonntityGraphDeloncidelonr
import com.twittelonr.reloncos.graph_common.FinaglelonStatsReloncelonivelonrWrappelonr
import com.twittelonr.reloncos.graph_common.NodelonMelontadataLelonftIndelonxelondPowelonrLawMultiSelongmelonntBipartitelonGraphBuildelonr
import com.twittelonr.reloncos.intelonrnal.thriftscala.ReloncosHoselonMelonssagelon
import com.twittelonr.reloncos.modelonl.Constants
import com.twittelonr.reloncos.uselonr_twelonelont_elonntity_graph.ReloncosConfig._
import com.twittelonr.selonrvelonr.logging.{Logging => JDK14Logging}
import com.twittelonr.selonrvelonr.Deloncidelonrablelon
import com.twittelonr.selonrvelonr.TwittelonrSelonrvelonr
import com.twittelonr.thriftwelonbforms.MelonthodOptions
import com.twittelonr.thriftwelonbforms.TwittelonrSelonrvelonrThriftWelonbForms
import com.twittelonr.util.Await
import com.twittelonr.util.Duration
import java.nelont.InelontSockelontAddrelonss
import java.util.concurrelonnt.TimelonUnit
import org.apachelon.kafka.clielonnts.CommonClielonntConfigs
import org.apachelon.kafka.common.config.SaslConfigs
import org.apachelon.kafka.common.config.SslConfigs
import org.apachelon.kafka.common.seloncurity.auth.SeloncurityProtocol
import org.apachelon.kafka.common.selonrialization.StringDelonselonrializelonr

objelonct Main elonxtelonnds TwittelonrSelonrvelonr with JDK14Logging with Deloncidelonrablelon {
  profilelon =>

  val shardId: Flag[Int] = flag("shardId", 0, "Shard ID")
  val selonrvicelonPort: Flag[InelontSockelontAddrelonss] =
    flag("selonrvicelon.port", nelonw InelontSockelontAddrelonss(10143), "Thrift selonrvicelon port")
  val logDir: Flag[String] = flag("logdir", "reloncos", "Logging direlonctory")
  val numShards: Flag[Int] = flag("numShards", 1, "Numbelonr of shards for this selonrvicelon")
  val truststorelonLocation: Flag[String] =
    flag[String]("truststorelon_location", "", "Truststorelon filelon location")
  val hoselonNamelon: Flag[String] =
    flag("hoselonnamelon", "reloncos_injelonctor_uselonr_uselonr", "thelon kafka strelonam uselond for incoming elondgelons")

  val dataCelonntelonr: Flag[String] = flag("selonrvicelon.clustelonr", "atla", "Data Celonntelonr")
  val selonrvicelonRolelon: Flag[String] = flag("selonrvicelon.rolelon", "Selonrvicelon Rolelon")
  val selonrvicelonelonnv: Flag[String] = flag("selonrvicelon.elonnv", "Selonrvicelon elonnv")
  val selonrvicelonNamelon: Flag[String] = flag("selonrvicelon.namelon", "Selonrvicelon Namelon")

  privatelon val maxNumSelongmelonnts =
    flag("maxNumSelongmelonnts", graphBuildelonrConfig.maxNumSelongmelonnts, "thelon numbelonr of selongmelonnts in thelon graph")

  privatelon val statsReloncelonivelonrWrappelonr = FinaglelonStatsReloncelonivelonrWrappelonr(statsReloncelonivelonr)

  lazy val clielonntId = ClielonntId(s"uselonrtwelonelontelonntitygraph.${selonrvicelonelonnv()}")

  privatelon val shutdownTimelonout = flag(
    "selonrvicelon.shutdownTimelonout",
    5.selonconds,
    "Maximum amount of timelon to wait for pelonnding relonquelonsts to complelontelon on shutdown"
  )

  // ********* logging **********

  lazy val loggingLelonvelonl: Lelonvelonl = Lelonvelonl.INFO
  lazy val reloncosLogPath: String = logDir() + "/reloncos.log"
  lazy val graphLogPath: String = logDir() + "/graph.log"
  lazy val accelonssLogPath: String = logDir() + "/accelonss.log"

  ovelonrridelon delonf loggelonrFactorielons: List[LoggelonrFactory] =
    List(
      LoggelonrFactory(
        lelonvelonl = Somelon(loggingLelonvelonl),
        handlelonrs = QuelonueloningHandlelonr(
          handlelonr = FilelonHandlelonr(
            filelonnamelon = reloncosLogPath,
            lelonvelonl = Somelon(loggingLelonvelonl),
            rollPolicy = Policy.Hourly,
            rotatelonCount = 6,
            formattelonr = nelonw Formattelonr
          )
        ) :: Nil
      ),
      LoggelonrFactory(
        nodelon = "graph",
        uselonParelonnts = falselon,
        lelonvelonl = Somelon(loggingLelonvelonl),
        handlelonrs = QuelonueloningHandlelonr(
          handlelonr = FilelonHandlelonr(
            filelonnamelon = graphLogPath,
            lelonvelonl = Somelon(loggingLelonvelonl),
            rollPolicy = Policy.Hourly,
            rotatelonCount = 6,
            formattelonr = nelonw Formattelonr
          )
        ) :: Nil
      ),
      LoggelonrFactory(
        nodelon = "accelonss",
        uselonParelonnts = falselon,
        lelonvelonl = Somelon(loggingLelonvelonl),
        handlelonrs = QuelonueloningHandlelonr(
          handlelonr = FilelonHandlelonr(
            filelonnamelon = accelonssLogPath,
            lelonvelonl = Somelon(loggingLelonvelonl),
            rollPolicy = Policy.Hourly,
            rotatelonCount = 6,
            formattelonr = nelonw Formattelonr
          )
        ) :: Nil
      ),
      LoggelonrFactory(
        nodelon = "clielonnt_elonvelonnt",
        lelonvelonl = Somelon(loggingLelonvelonl),
        uselonParelonnts = falselon,
        handlelonrs = QuelonueloningHandlelonr(
          maxQuelonuelonSizelon = 10000,
          handlelonr = ScribelonHandlelonr(
            catelongory = "clielonnt_elonvelonnt",
            formattelonr = BarelonFormattelonr
          )
        ) :: Nil
      )
    )
  // ******** Deloncidelonr *************

  val graphDeloncidelonr: UselonrTwelonelontelonntityGraphDeloncidelonr = UselonrTwelonelontelonntityGraphDeloncidelonr()

  // ********* ABdeloncidelonr **********

  val abDeloncidelonrYmlPath: String = "/usr/local/config/abdeloncidelonr/abdeloncidelonr.yml"

  val scribelonLoggelonr: Option[Loggelonr] = Somelon(Loggelonr.gelont("clielonnt_elonvelonnt"))

  val abDeloncidelonr: LoggingABDeloncidelonr =
    ABDeloncidelonrFactory(
      abDeloncidelonrYmlPath = abDeloncidelonrYmlPath,
      scribelonLoggelonr = scribelonLoggelonr,
      elonnvironmelonnt = Somelon("production")
    ).buildWithLogging()

  // ********* Reloncos selonrvicelon **********

  privatelon delonf gelontKafkaBuildelonr() = {
    FinaglelonKafkaConsumelonrBuildelonr[String, ReloncosHoselonMelonssagelon]()
      .delonst("/s/kafka/reloncommelonndations:kafka-tls")
      .groupId(KafkaGroupId(f"uselonr_twelonelont_elonntity_graph-${shardId()}%06d"))
      .kelonyDelonselonrializelonr(nelonw StringDelonselonrializelonr)
      .valuelonDelonselonrializelonr(ScalaSelonrdelons.Thrift[ReloncosHoselonMelonssagelon].delonselonrializelonr)
      .selonelonkStratelongy(SelonelonkStratelongy.RelonWIND)
      .relonwindDuration(20.hours)
      .withConfig(CommonClielonntConfigs.SelonCURITY_PROTOCOL_CONFIG, SeloncurityProtocol.SASL_SSL.toString)
      .withConfig(SslConfigs.SSL_TRUSTSTORelon_LOCATION_CONFIG, truststorelonLocation())
      .withConfig(SaslConfigs.SASL_MelonCHANISM, SaslConfigs.GSSAPI_MelonCHANISM)
      .withConfig(SaslConfigs.SASL_KelonRBelonROS_SelonRVICelon_NAMelon, "kafka")
      .withConfig(SaslConfigs.SASL_KelonRBelonROS_SelonRVelonR_NAMelon, "kafka")
  }
  delonf main(): Unit = {
    log.info("building graph with maxNumSelongmelonnts = " + profilelon.maxNumSelongmelonnts())
    val graph = NodelonMelontadataLelonftIndelonxelondPowelonrLawMultiSelongmelonntBipartitelonGraphBuildelonr(
      graphBuildelonrConfig.copy(maxNumSelongmelonnts = profilelon.maxNumSelongmelonnts()),
      statsReloncelonivelonrWrappelonr
    )

    val kafkaConfigBuildelonr = gelontKafkaBuildelonr()

    val graphWritelonr =
      UselonrTwelonelontelonntityGraphWritelonr(
        shardId().toString,
        selonrvicelonelonnv(),
        hoselonNamelon(),
        128, // kelonelonp thelon original selontting.
        kafkaConfigBuildelonr,
        clielonntId.namelon,
        statsReloncelonivelonr,
      )
    graphWritelonr.initHoselon(graph)

    val twelonelontReloncsRunnelonr = nelonw TwelonelontReloncommelonndationsRunnelonr(
      graph,
      Constants.salsaRunnelonrConfig,
      statsReloncelonivelonrWrappelonr
    )

    val twelonelontSocialProofRunnelonr = nelonw TwelonelontSocialProofRunnelonr(
      graph,
      Constants.salsaRunnelonrConfig,
      statsReloncelonivelonr
    )

    val elonntitySocialProofRunnelonr = nelonw elonntitySocialProofRunnelonr(
      graph,
      Constants.salsaRunnelonrConfig,
      statsReloncelonivelonr
    )

    val reloncommelonndationHandlelonr = nelonw ReloncommelonndationHandlelonr(twelonelontReloncsRunnelonr, statsReloncelonivelonr)

    /*
     * Old social proof handlelonr relontainelond to support old twelonelont social proof elonndpoint.
     * Futurelon clielonnts should utilizelon thelon findReloncommelonndationSocialProofs elonndpoint which will uselon
     * thelon morelon broad "SocialProofHandlelonr"
     */
    val twelonelontSocialProofHandlelonr = nelonw TwelonelontSocialProofHandlelonr(
      twelonelontSocialProofRunnelonr,
      graphDeloncidelonr,
      statsReloncelonivelonr
    )
    val socialProofHandlelonr = nelonw SocialProofHandlelonr(
      twelonelontSocialProofRunnelonr,
      elonntitySocialProofRunnelonr,
      graphDeloncidelonr,
      statsReloncelonivelonr
    )
    val uselonrTwelonelontelonntityGraph = nelonw UselonrTwelonelontelonntityGraph(
      reloncommelonndationHandlelonr,
      twelonelontSocialProofHandlelonr,
      socialProofHandlelonr
    ) with LoggingUselonrTwelonelontelonntityGraph

    // For MutualTLS
    val selonrvicelonIdelonntifielonr = SelonrvicelonIdelonntifielonr(
      rolelon = selonrvicelonRolelon(),
      selonrvicelon = selonrvicelonNamelon(),
      elonnvironmelonnt = selonrvicelonelonnv(),
      zonelon = dataCelonntelonr()
    )
    log.info(s"SelonrvicelonIdelonntifielonr = ${selonrvicelonIdelonntifielonr.toString}")

    val thriftSelonrvelonr = ThriftMux.selonrvelonr
      .withOpportunisticTls(OpportunisticTls.Relonquirelond)
      .withMutualTls(selonrvicelonIdelonntifielonr)
      .selonrvelonIfacelon(selonrvicelonPort(), uselonrTwelonelontelonntityGraph)

    log.info("clielonntid: " + clielonntId.toString)
    log.info("selonrvicelonPort: " + selonrvicelonPort().toString)

    log.info("adding shutdown hook")
    onelonxit {
      graphWritelonr.shutdown()
      thriftSelonrvelonr.closelon(shutdownTimelonout().fromNow)
    }
    log.info("addelond shutdown hook")

    // Wait on thelon thriftSelonrvelonr so that shutdownTimelonout is relonspelonctelond.
    Await.relonsult(thriftSelonrvelonr)
  }
}
