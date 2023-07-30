package com.X.recos.user_tweet_entity_graph

import com.X.abdecider.ABDeciderFactory
import com.X.abdecider.LoggingABDecider
import com.X.app.Flag
import com.X.conversions.DurationOps._
import com.X.finagle.ThriftMux
import com.X.finagle.http.HttpMuxer
import com.X.finagle.mtls.authentication.ServiceIdentifier
import com.X.finagle.mtls.server.MtlsStackServer._
import com.X.finagle.mux.transport.OpportunisticTls
import com.X.finagle.thrift.ClientId
import com.X.finatra.kafka.consumers.FinagleKafkaConsumerBuilder
import com.X.finatra.kafka.domain.KafkaGroupId
import com.X.finatra.kafka.domain.SeekStrategy
import com.X.finatra.kafka.serde.ScalaSerdes
import com.X.frigate.common.util.ElfOwlFilter
import com.X.frigate.common.util.ElfOwlFilter.ByLdapGroup
import com.X.graphjet.bipartite.NodeMetadataLeftIndexedPowerLawMultiSegmentBipartiteGraph
import com.X.logging._
import com.X.recos.decider.UserTweetEntityGraphDecider
import com.X.recos.graph_common.FinagleStatsReceiverWrapper
import com.X.recos.graph_common.NodeMetadataLeftIndexedPowerLawMultiSegmentBipartiteGraphBuilder
import com.X.recos.internal.thriftscala.RecosHoseMessage
import com.X.recos.model.Constants
import com.X.recos.user_tweet_entity_graph.RecosConfig._
import com.X.server.logging.{Logging => JDK14Logging}
import com.X.server.Deciderable
import com.X.server.XServer
import com.X.thriftwebforms.MethodOptions
import com.X.thriftwebforms.XServerThriftWebForms
import com.X.util.Await
import com.X.util.Duration
import java.net.InetSocketAddress
import java.util.concurrent.TimeUnit
import org.apache.kafka.clients.CommonClientConfigs
import org.apache.kafka.common.config.SaslConfigs
import org.apache.kafka.common.config.SslConfigs
import org.apache.kafka.common.security.auth.SecurityProtocol
import org.apache.kafka.common.serialization.StringDeserializer

object Main extends XServer with JDK14Logging with Deciderable {
  profile =>

  val shardId: Flag[Int] = flag("shardId", 0, "Shard ID")
  val servicePort: Flag[InetSocketAddress] =
    flag("service.port", new InetSocketAddress(10143), "Thrift service port")
  val logDir: Flag[String] = flag("logdir", "recos", "Logging directory")
  val numShards: Flag[Int] = flag("numShards", 1, "Number of shards for this service")
  val truststoreLocation: Flag[String] =
    flag[String]("truststore_location", "", "Truststore file location")
  val hoseName: Flag[String] =
    flag("hosename", "recos_injector_user_user", "the kafka stream used for incoming edges")

  val dataCenter: Flag[String] = flag("service.cluster", "atla", "Data Center")
  val serviceRole: Flag[String] = flag("service.role", "Service Role")
  val serviceEnv: Flag[String] = flag("service.env", "Service Env")
  val serviceName: Flag[String] = flag("service.name", "Service Name")

  private val maxNumSegments =
    flag("maxNumSegments", graphBuilderConfig.maxNumSegments, "the number of segments in the graph")

  private val statsReceiverWrapper = FinagleStatsReceiverWrapper(statsReceiver)

  lazy val clientId = ClientId(s"usertweetentitygraph.${serviceEnv()}")

  private val shutdownTimeout = flag(
    "service.shutdownTimeout",
    5.seconds,
    "Maximum amount of time to wait for pending requests to complete on shutdown"
  )

  // ********* logging **********

  lazy val loggingLevel: Level = Level.INFO
  lazy val recosLogPath: String = logDir() + "/recos.log"
  lazy val graphLogPath: String = logDir() + "/graph.log"
  lazy val accessLogPath: String = logDir() + "/access.log"

  override def loggerFactories: List[LoggerFactory] =
    List(
      LoggerFactory(
        level = Some(loggingLevel),
        handlers = QueueingHandler(
          handler = FileHandler(
            filename = recosLogPath,
            level = Some(loggingLevel),
            rollPolicy = Policy.Hourly,
            rotateCount = 6,
            formatter = new Formatter
          )
        ) :: Nil
      ),
      LoggerFactory(
        node = "graph",
        useParents = false,
        level = Some(loggingLevel),
        handlers = QueueingHandler(
          handler = FileHandler(
            filename = graphLogPath,
            level = Some(loggingLevel),
            rollPolicy = Policy.Hourly,
            rotateCount = 6,
            formatter = new Formatter
          )
        ) :: Nil
      ),
      LoggerFactory(
        node = "access",
        useParents = false,
        level = Some(loggingLevel),
        handlers = QueueingHandler(
          handler = FileHandler(
            filename = accessLogPath,
            level = Some(loggingLevel),
            rollPolicy = Policy.Hourly,
            rotateCount = 6,
            formatter = new Formatter
          )
        ) :: Nil
      ),
      LoggerFactory(
        node = "client_event",
        level = Some(loggingLevel),
        useParents = false,
        handlers = QueueingHandler(
          maxQueueSize = 10000,
          handler = ScribeHandler(
            category = "client_event",
            formatter = BareFormatter
          )
        ) :: Nil
      )
    )
  // ******** Decider *************

  val graphDecider: UserTweetEntityGraphDecider = UserTweetEntityGraphDecider()

  // ********* ABdecider **********

  val abDeciderYmlPath: String = "/usr/local/config/abdecider/abdecider.yml"

  val scribeLogger: Option[Logger] = Some(Logger.get("client_event"))

  val abDecider: LoggingABDecider =
    ABDeciderFactory(
      abDeciderYmlPath = abDeciderYmlPath,
      scribeLogger = scribeLogger,
      environment = Some("production")
    ).buildWithLogging()

  // ********* Recos service **********

  private def getKafkaBuilder() = {
    FinagleKafkaConsumerBuilder[String, RecosHoseMessage]()
      .dest("/s/kafka/recommendations:kafka-tls")
      .groupId(KafkaGroupId(f"user_tweet_entity_graph-${shardId()}%06d"))
      .keyDeserializer(new StringDeserializer)
      .valueDeserializer(ScalaSerdes.Thrift[RecosHoseMessage].deserializer)
      .seekStrategy(SeekStrategy.REWIND)
      .rewindDuration(20.hours)
      .withConfig(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, SecurityProtocol.SASL_SSL.toString)
      .withConfig(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, truststoreLocation())
      .withConfig(SaslConfigs.SASL_MECHANISM, SaslConfigs.GSSAPI_MECHANISM)
      .withConfig(SaslConfigs.SASL_KERBEROS_SERVICE_NAME, "kafka")
      .withConfig(SaslConfigs.SASL_KERBEROS_SERVER_NAME, "kafka")
  }
  def main(): Unit = {
    log.info("building graph with maxNumSegments = " + profile.maxNumSegments())
    val graph = NodeMetadataLeftIndexedPowerLawMultiSegmentBipartiteGraphBuilder(
      graphBuilderConfig.copy(maxNumSegments = profile.maxNumSegments()),
      statsReceiverWrapper
    )

    val kafkaConfigBuilder = getKafkaBuilder()

    val graphWriter =
      UserTweetEntityGraphWriter(
        shardId().toString,
        serviceEnv(),
        hoseName(),
        128, // keep the original setting.
        kafkaConfigBuilder,
        clientId.name,
        statsReceiver,
      )
    graphWriter.initHose(graph)

    val tweetRecsRunner = new TweetRecommendationsRunner(
      graph,
      Constants.salsaRunnerConfig,
      statsReceiverWrapper
    )

    val tweetSocialProofRunner = new TweetSocialProofRunner(
      graph,
      Constants.salsaRunnerConfig,
      statsReceiver
    )

    val entitySocialProofRunner = new EntitySocialProofRunner(
      graph,
      Constants.salsaRunnerConfig,
      statsReceiver
    )

    val recommendationHandler = new RecommendationHandler(tweetRecsRunner, statsReceiver)

    /*
     * Old social proof handler retained to support old tweet social proof endpoint.
     * Future clients should utilize the findRecommendationSocialProofs endpoint which will use
     * the more broad "SocialProofHandler"
     */
    val tweetSocialProofHandler = new TweetSocialProofHandler(
      tweetSocialProofRunner,
      graphDecider,
      statsReceiver
    )
    val socialProofHandler = new SocialProofHandler(
      tweetSocialProofRunner,
      entitySocialProofRunner,
      graphDecider,
      statsReceiver
    )
    val userTweetEntityGraph = new UserTweetEntityGraph(
      recommendationHandler,
      tweetSocialProofHandler,
      socialProofHandler
    ) with LoggingUserTweetEntityGraph

    // For MutualTLS
    val serviceIdentifier = ServiceIdentifier(
      role = serviceRole(),
      service = serviceName(),
      environment = serviceEnv(),
      zone = dataCenter()
    )
    log.info(s"ServiceIdentifier = ${serviceIdentifier.toString}")

    val thriftServer = ThriftMux.server
      .withOpportunisticTls(OpportunisticTls.Required)
      .withMutualTls(serviceIdentifier)
      .serveIface(servicePort(), userTweetEntityGraph)

    log.info("clientid: " + clientId.toString)
    log.info("servicePort: " + servicePort().toString)

    log.info("adding shutdown hook")
    onExit {
      graphWriter.shutdown()
      thriftServer.close(shutdownTimeout().fromNow)
    }
    log.info("added shutdown hook")

    // Wait on the thriftServer so that shutdownTimeout is respected.
    Await.result(thriftServer)
  }
}
