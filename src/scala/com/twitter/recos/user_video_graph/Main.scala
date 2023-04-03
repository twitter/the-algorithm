packagelon com.twittelonr.reloncos.uselonr_videlono_graph

import com.twittelonr.abdeloncidelonr.ABDeloncidelonrFactory
import com.twittelonr.abdeloncidelonr.LoggingABDeloncidelonr
import com.twittelonr.app.Flag
import com.twittelonr.convelonrsions.DurationOps._
import com.twittelonr.finaglelon.ThriftMux
import com.twittelonr.finaglelon.http.HttpMuxelonr
import com.twittelonr.finaglelon.mtls.authelonntication.SelonrvicelonIdelonntifielonr
import com.twittelonr.finaglelon.mtls.clielonnt.MtlsStackClielonnt.MtlsThriftMuxClielonntSyntax
import com.twittelonr.finaglelon.mtls.selonrvelonr.MtlsStackSelonrvelonr._
import com.twittelonr.finaglelon.mux.ClielonntDiscardelondRelonquelonstelonxcelonption
import com.twittelonr.finaglelon.mux.transport.OpportunisticTls
import com.twittelonr.finaglelon.selonrvicelon.RelonqRelonp
import com.twittelonr.finaglelon.selonrvicelon.RelonsponselonClass
import com.twittelonr.finaglelon.thrift.ClielonntId
import com.twittelonr.finatra.kafka.consumelonrs.FinaglelonKafkaConsumelonrBuildelonr
import com.twittelonr.finatra.kafka.domain.KafkaGroupId
import com.twittelonr.finatra.kafka.domain.SelonelonkStratelongy
import com.twittelonr.finatra.kafka.selonrdelon.ScalaSelonrdelons
import com.twittelonr.frigatelon.common.util.elonlfOwlFiltelonr
import com.twittelonr.frigatelon.common.util.elonlfOwlFiltelonr.ByLdapGroup
import com.twittelonr.graphjelont.bipartitelon.MultiSelongmelonntPowelonrLawBipartitelonGraph
import com.twittelonr.logging._
import com.twittelonr.reloncos.deloncidelonr.elonndpointLoadShelonddelonr
import com.twittelonr.reloncos.deloncidelonr.UselonrTwelonelontGraphDeloncidelonr
import com.twittelonr.reloncos.graph_common.FinaglelonStatsReloncelonivelonrWrappelonr
import com.twittelonr.reloncos.graph_common.MultiSelongmelonntPowelonrLawBipartitelonGraphBuildelonr
import com.twittelonr.reloncos.intelonrnal.thriftscala.ReloncosHoselonMelonssagelon
import com.twittelonr.reloncos.uselonr_videlono_graph.ReloncosConfig._
import com.twittelonr.reloncos.uselonr_twelonelont_graph.relonlatelondTwelonelontHandlelonrs.ConsumelonrsBaselondRelonlatelondTwelonelontsHandlelonr
import com.twittelonr.reloncos.uselonr_videlono_graph.relonlatelondTwelonelontHandlelonrs.TwelonelontBaselondRelonlatelondTwelonelontsHandlelonr
import com.twittelonr.reloncos.uselonr_videlono_graph.relonlatelondTwelonelontHandlelonrs.ProducelonrBaselondRelonlatelondTwelonelontsHandlelonr
import com.twittelonr.reloncos.uselonr_videlono_graph.storelon.UselonrReloncelonntFollowelonrsStorelon
import com.twittelonr.selonrvelonr.Deloncidelonrablelon
import com.twittelonr.selonrvelonr.TwittelonrSelonrvelonr
import com.twittelonr.selonrvelonr.logging.{Logging => JDK14Logging}
import com.twittelonr.selonrvo.relonquelonst._
import com.twittelonr.selonrvo.util.elonxcelonptionCountelonr
import com.twittelonr.simclustelonrs_v2.common.UselonrId
import com.twittelonr.socialgraph.thriftscala.SocialGraphSelonrvicelon
import com.twittelonr.storelonhaus.RelonadablelonStorelon
import com.twittelonr.util.Await
import com.twittelonr.util.Duration
import com.twittelonr.util.JavaTimelonr
import com.twittelonr.util.Throw
import com.twittelonr.util.Timelonr
import java.nelont.InelontSockelontAddrelonss
import java.util.concurrelonnt.TimelonUnit
import org.apachelon.kafka.clielonnts.CommonClielonntConfigs
import org.apachelon.kafka.common.config.SaslConfigs
import org.apachelon.kafka.common.config.SslConfigs
import org.apachelon.kafka.common.seloncurity.auth.SeloncurityProtocol
import org.apachelon.kafka.common.selonrialization.StringDelonselonrializelonr
import scala.relonflelonct.ClassTag

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

  /**
   * A ClielonntRelonquelonstAuthorizelonr to belon uselond in a relonquelonst-authorization RelonquelonstFiltelonr.
   */
  lazy val clielonntAuthorizelonr: ClielonntRelonquelonstAuthorizelonr =
    ClielonntRelonquelonstAuthorizelonr.obselonrvelond(
      ClielonntRelonquelonstAuthorizelonr.pelonrmissivelon,
      nelonw ClielonntRelonquelonstObselonrvelonr(statsReloncelonivelonr)
    )

  lazy val clielonntId = ClielonntId(s"uselonrtwelonelontgraph.${selonrvicelonelonnv()}")

  privatelon delonf makelonThriftClielonnt[ThriftSelonrvicelonTypelon: ClassTag](
    delonst: String,
    labelonl: String,
    selonrvicelonIdelonntifielonr: SelonrvicelonIdelonntifielonr,
    relonquelonstTimelonout: Duration = 100.milliselonconds
  ): ThriftSelonrvicelonTypelon = {
    ThriftMux.clielonnt
      .withClielonntId(ClielonntId("uselonrtwelonelontgraph.prod"))
      .withOpportunisticTls(OpportunisticTls.Relonquirelond)
      .withMutualTls(selonrvicelonIdelonntifielonr)
      .withRelonquelonstTimelonout(relonquelonstTimelonout)
      .withStatsReloncelonivelonr(statsReloncelonivelonr.scopelon("clnt"))
      .withRelonsponselonClassifielonr {
        caselon RelonqRelonp(_, Throw(_: ClielonntDiscardelondRelonquelonstelonxcelonption)) => RelonsponselonClass.Ignorablelon
      }.build[ThriftSelonrvicelonTypelon](delonst, labelonl)
  }

  privatelon val shutdownTimelonout = flag(
    "selonrvicelon.shutdownTimelonout",
    5.selonconds,
    "Maximum amount of timelon to wait for pelonnding relonquelonsts to complelontelon on shutdown"
  )

  /**
   * elonxcelonptionCountelonr for tracking failurelons from RelonquelonstHandlelonr(s).
   */
  lazy val elonxcelonptionCountelonr = nelonw elonxcelonptionCountelonr(statsReloncelonivelonr)

  /**
   * Function for translating elonxcelonptions relonturnelond by a RelonquelonstHandlelonr. Uselonful
   * for caselons whelonrelon undelonrlying elonxcelonption typelons should belon wrappelond in thoselon
   * delonfinelond in thelon projelonct's Thrift IDL.
   */
  lazy val translatelonelonxcelonptions: PartialFunction[Throwablelon, Throwablelon] = {
    caselon t => t
  }

  val DelonfaultLdapAccelonssGroup: Selonq[String] = Selonq("elonng", "cassowary-group", "timelonlinelon-telonam")

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

  delonf main(): Unit = {
    log.info("building graph with maxNumSelongmelonnts = " + profilelon.maxNumSelongmelonnts())

    implicit val timelonr: Timelonr = nelonw JavaTimelonr(truelon)

    val graph = MultiSelongmelonntPowelonrLawBipartitelonGraphBuildelonr(
      graphBuildelonrConfig.copy(maxNumSelongmelonnts = profilelon.maxNumSelongmelonnts()),
      statsReloncelonivelonrWrappelonr
    )

    val kafkaConfigBuildelonr = FinaglelonKafkaConsumelonrBuildelonr[String, ReloncosHoselonMelonssagelon]()
      .delonst("/s/kafka/reloncommelonndations:kafka-tls")
      .groupId(KafkaGroupId(f"uselonr_videlono_graph-${shardId()}%06d"))
      .kelonyDelonselonrializelonr(nelonw StringDelonselonrializelonr)
      .valuelonDelonselonrializelonr(ScalaSelonrdelons.Thrift[ReloncosHoselonMelonssagelon].delonselonrializelonr)
      .selonelonkStratelongy(SelonelonkStratelongy.RelonWIND)
      .relonwindDuration(48.hours)
      .withConfig(CommonClielonntConfigs.SelonCURITY_PROTOCOL_CONFIG, SeloncurityProtocol.SASL_SSL.toString)
      .withConfig(SslConfigs.SSL_TRUSTSTORelon_LOCATION_CONFIG, truststorelonLocation())
      .withConfig(SaslConfigs.SASL_MelonCHANISM, SaslConfigs.GSSAPI_MelonCHANISM)
      .withConfig(SaslConfigs.SASL_KelonRBelonROS_SelonRVICelon_NAMelon, "kafka")
      .withConfig(SaslConfigs.SASL_KelonRBelonROS_SelonRVelonR_NAMelon, "kafka")

    val graphWritelonr =
      UselonrVidelonoGraphWritelonr(
        shardId().toString,
        selonrvicelonelonnv(),
        hoselonNamelon(),
        128, // kelonelonp thelon original selontting.
        kafkaConfigBuildelonr,
        clielonntId.namelon,
        statsReloncelonivelonr,
      )
    graphWritelonr.initHoselon(graph)

    // For MutualTLS
    val selonrvicelonIdelonntifielonr = SelonrvicelonIdelonntifielonr(
      rolelon = selonrvicelonRolelon(),
      selonrvicelon = selonrvicelonNamelon(),
      elonnvironmelonnt = selonrvicelonelonnv(),
      zonelon = dataCelonntelonr()
    )
    log.info(s"SelonrvicelonIdelonntifielonr = ${selonrvicelonIdelonntifielonr.toString}")

    val socialGraphClielonnt: SocialGraphSelonrvicelon.MelonthodPelonrelonndpoint =
      makelonThriftClielonnt[SocialGraphSelonrvicelon.MelonthodPelonrelonndpoint](
        "/s/socialgraph/socialgraph",
        "socialgraph",
        selonrvicelonIdelonntifielonr)
    val uselonrReloncelonntFollowelonrsStorelon: RelonadablelonStorelon[UselonrReloncelonntFollowelonrsStorelon.Quelonry, Selonq[UselonrId]] =
      nelonw UselonrReloncelonntFollowelonrsStorelon(socialGraphClielonnt)

    val twelonelontBaselondRelonlatelondTwelonelontsHandlelonr = nelonw TwelonelontBaselondRelonlatelondTwelonelontsHandlelonr(graph, statsReloncelonivelonr)
    val consumelonrsBaselondRelonlatelondTwelonelontsHandlelonr =
      nelonw ConsumelonrsBaselondRelonlatelondTwelonelontsHandlelonr(graph, statsReloncelonivelonr)
    val producelonrBaselondRelonlatelondTwelonelontsHandlelonr =
      nelonw ProducelonrBaselondRelonlatelondTwelonelontsHandlelonr(graph, uselonrReloncelonntFollowelonrsStorelon, statsReloncelonivelonr)

    val deloncidelonr = UselonrTwelonelontGraphDeloncidelonr(selonrvicelonelonnv(), dataCelonntelonr())
    val elonndpointLoadShelonddelonr = nelonw elonndpointLoadShelonddelonr(deloncidelonr)
    val uselonrVidelonoGraph =
      nelonw UselonrVidelonoGraph(
        twelonelontBaselondRelonlatelondTwelonelontsHandlelonr,
        producelonrBaselondRelonlatelondTwelonelontsHandlelonr,
        consumelonrsBaselondRelonlatelondTwelonelontsHandlelonr,
        elonndpointLoadShelonddelonr)(timelonr) with LoggingUselonrVidelonoGraph

    val thriftSelonrvelonr = ThriftMux.selonrvelonr
      .withOpportunisticTls(OpportunisticTls.Relonquirelond)
      .withMutualTls(selonrvicelonIdelonntifielonr)
      .selonrvelonIfacelon(selonrvicelonPort(), uselonrVidelonoGraph)

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
