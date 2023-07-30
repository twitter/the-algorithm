package com.X.recosinjector

import com.X.app.Flag
import com.X.finagle.http.HttpMuxer
import com.X.finagle.mtls.authentication.ServiceIdentifier
import com.X.finagle.stats.StatsReceiver
import com.X.frigate.common.util.ElfOwlFilter
import com.X.recosinjector.clients.Gizmoduck
import com.X.recosinjector.clients.RecosHoseEntitiesCache
import com.X.recosinjector.clients.SocialGraph
import com.X.recosinjector.clients.Tweetypie
import com.X.recosinjector.clients.UrlResolver
import com.X.recosinjector.config._
import com.X.recosinjector.edges.SocialWriteEventToUserUserGraphBuilder
import com.X.recosinjector.edges.TimelineEventToUserTweetEntityGraphBuilder
import com.X.recosinjector.edges.TweetEventToUserTweetEntityGraphBuilder
import com.X.recosinjector.edges.TweetEventToUserUserGraphBuilder
import com.X.recosinjector.edges.UnifiedUserActionToUserVideoGraphBuilder
import com.X.recosinjector.edges.UnifiedUserActionToUserAdGraphBuilder
import com.X.recosinjector.edges.UnifiedUserActionToUserTweetGraphPlusBuilder
import com.X.recosinjector.edges.UserTweetEntityEdgeBuilder
import com.X.recosinjector.event_processors.SocialWriteEventProcessor
import com.X.recosinjector.event_processors.TimelineEventProcessor
import com.X.recosinjector.event_processors.TweetEventProcessor
import com.X.recosinjector.publishers.KafkaEventPublisher
import com.X.recosinjector.uua_processors.UnifiedUserActionProcessor
import com.X.recosinjector.uua_processors.UnifiedUserActionsConsumer
import com.X.server.logging.{Logging => JDK14Logging}
import com.X.server.Deciderable
import com.X.server.XServer
import com.X.socialgraph.thriftscala.WriteEvent
import com.X.timelineservice.thriftscala.{Event => TimelineEvent}
import com.X.tweetypie.thriftscala.TweetEvent
import com.X.util.Await
import com.X.util.Duration
import java.util.concurrent.TimeUnit

object Main extends XServer with JDK14Logging with Deciderable { self =>

  implicit val stats: StatsReceiver = statsReceiver

  private val dataCenter: Flag[String] = flag("service.cluster", "atla", "Data Center")
  private val serviceRole: Flag[String] = flag("service.role", "Service Role")
  private val serviceEnv: Flag[String] = flag("service.env", "Service Env")
  private val serviceName: Flag[String] = flag("service.name", "Service Name")
  private val shardId = flag("shardId", 0, "Shard ID")
  private val numShards = flag("numShards", 1, "Number of shards for this service")
  private val truststoreLocation =
    flag[String]("truststore_location", "", "Truststore file location")

  def main(): Unit = {
    val serviceIdentifier = ServiceIdentifier(
      role = serviceRole(),
      service = serviceName(),
      environment = serviceEnv(),
      zone = dataCenter()
    )
    println("ServiceIdentifier = " + serviceIdentifier.toString)
    log.info("ServiceIdentifier = " + serviceIdentifier.toString)

    val shard = shardId()
    val numOfShards = numShards()
    val environment = serviceEnv()

    implicit val config: DeployConfig = {
      environment match {
        case "prod" => ProdConfig(serviceIdentifier)(stats)
        case "staging" | "devel" => StagingConfig(serviceIdentifier)
        case env => throw new Exception(s"Unknown environment $env")
      }
    }

    // Initialize the config and wait for initialization to finish
    Await.ready(config.init())

    log.info(
      "Starting Recos Injector: environment %s, clientId %s",
      environment,
      config.recosInjectorThriftClientId
    )
    log.info("Starting shard Id: %d of %d shards...".format(shard, numOfShards))

    // Client wrappers
    val cache = new RecosHoseEntitiesCache(config.recosInjectorCoreSvcsCacheClient)
    val gizmoduck = new Gizmoduck(config.userStore)
    val socialGraph = new SocialGraph(config.socialGraphIdStore)
    val tweetypie = new Tweetypie(config.tweetyPieStore)
    val urlResolver = new UrlResolver(config.urlInfoStore)

    // Edge builders
    val userTweetEntityEdgeBuilder = new UserTweetEntityEdgeBuilder(cache, urlResolver)

    // Publishers
    val kafkaEventPublisher = KafkaEventPublisher(
      "/s/kafka/recommendations:kafka-tls",
      config.outputKafkaTopicPrefix,
      config.recosInjectorThriftClientId,
      truststoreLocation())

    // Message Builders
    val socialWriteToUserUserMessageBuilder =
      new SocialWriteEventToUserUserGraphBuilder()(
        statsReceiver.scope("SocialWriteEventToUserUserGraphBuilder")
      )

    val timelineToUserTweetEntityMessageBuilder = new TimelineEventToUserTweetEntityGraphBuilder(
      userTweetEntityEdgeBuilder = userTweetEntityEdgeBuilder
    )(statsReceiver.scope("TimelineEventToUserTweetEntityGraphBuilder"))

    val tweetEventToUserTweetEntityGraphBuilder = new TweetEventToUserTweetEntityGraphBuilder(
      userTweetEntityEdgeBuilder = userTweetEntityEdgeBuilder,
      tweetCreationStore = config.tweetCreationStore,
      decider = config.recosInjectorDecider
    )(statsReceiver.scope("TweetEventToUserTweetEntityGraphBuilder"))

    val socialWriteEventProcessor = new SocialWriteEventProcessor(
      eventBusStreamName = s"recos_injector_social_write_event_$environment",
      thriftStruct = WriteEvent,
      serviceIdentifier = serviceIdentifier,
      kafkaEventPublisher = kafkaEventPublisher,
      userUserGraphTopic = KafkaEventPublisher.UserUserTopic,
      userUserGraphMessageBuilder = socialWriteToUserUserMessageBuilder
    )(statsReceiver.scope("SocialWriteEventProcessor"))

    val tweetToUserUserMessageBuilder = new TweetEventToUserUserGraphBuilder()(
      statsReceiver.scope("TweetEventToUserUserGraphBuilder")
    )

    val unifiedUserActionToUserVideoGraphBuilder = new UnifiedUserActionToUserVideoGraphBuilder(
      userTweetEntityEdgeBuilder = userTweetEntityEdgeBuilder
    )(statsReceiver.scope("UnifiedUserActionToUserVideoGraphBuilder"))

    val unifiedUserActionToUserAdGraphBuilder = new UnifiedUserActionToUserAdGraphBuilder(
      userTweetEntityEdgeBuilder = userTweetEntityEdgeBuilder
    )(statsReceiver.scope("UnifiedUserActionToUserAdGraphBuilder"))

    val unifiedUserActionToUserTweetGraphPlusBuilder =
      new UnifiedUserActionToUserTweetGraphPlusBuilder(
        userTweetEntityEdgeBuilder = userTweetEntityEdgeBuilder
      )(statsReceiver.scope("UnifiedUserActionToUserTweetGraphPlusBuilder"))

    // Processors
    val tweetEventProcessor = new TweetEventProcessor(
      eventBusStreamName = s"recos_injector_tweet_events_$environment",
      thriftStruct = TweetEvent,
      serviceIdentifier = serviceIdentifier,
      userUserGraphMessageBuilder = tweetToUserUserMessageBuilder,
      userUserGraphTopic = KafkaEventPublisher.UserUserTopic,
      userTweetEntityGraphMessageBuilder = tweetEventToUserTweetEntityGraphBuilder,
      userTweetEntityGraphTopic = KafkaEventPublisher.UserTweetEntityTopic,
      kafkaEventPublisher = kafkaEventPublisher,
      socialGraph = socialGraph,
      tweetypie = tweetypie,
      gizmoduck = gizmoduck
    )(statsReceiver.scope("TweetEventProcessor"))

    val timelineEventProcessor = new TimelineEventProcessor(
      eventBusStreamName = s"recos_injector_timeline_events_prototype_$environment",
      thriftStruct = TimelineEvent,
      serviceIdentifier = serviceIdentifier,
      kafkaEventPublisher = kafkaEventPublisher,
      userTweetEntityGraphTopic = KafkaEventPublisher.UserTweetEntityTopic,
      userTweetEntityGraphMessageBuilder = timelineToUserTweetEntityMessageBuilder,
      decider = config.recosInjectorDecider,
      gizmoduck = gizmoduck,
      tweetypie = tweetypie
    )(statsReceiver.scope("TimelineEventProcessor"))

    val eventBusProcessors = Seq(
      timelineEventProcessor,
      socialWriteEventProcessor,
      tweetEventProcessor
    )

    val uuaProcessor = new UnifiedUserActionProcessor(
      gizmoduck = gizmoduck,
      tweetypie = tweetypie,
      kafkaEventPublisher = kafkaEventPublisher,
      userVideoGraphTopic = KafkaEventPublisher.UserVideoTopic,
      userVideoGraphBuilder = unifiedUserActionToUserVideoGraphBuilder,
      userAdGraphTopic = KafkaEventPublisher.UserAdTopic,
      userAdGraphBuilder = unifiedUserActionToUserAdGraphBuilder,
      userTweetGraphPlusTopic = KafkaEventPublisher.UserTweetPlusTopic,
      userTweetGraphPlusBuilder = unifiedUserActionToUserTweetGraphPlusBuilder)(
      statsReceiver.scope("UnifiedUserActionProcessor"))

    val uuaConsumer = new UnifiedUserActionsConsumer(uuaProcessor, truststoreLocation())

    // Start-up init and graceful shutdown setup

    // wait a bit for services to be ready
    Thread.sleep(5000L)

    log.info("Starting the event processors")
    eventBusProcessors.foreach(_.start())

    log.info("Starting the uua processors")
    uuaConsumer.atLeastOnceProcessor.start()

    this.addAdminRoute(ElfOwlFilter.getPostbackRoute())

    onExit {
      log.info("Shutting down the event processors")
      eventBusProcessors.foreach(_.stop())
      log.info("Shutting down the uua processors")
      uuaConsumer.atLeastOnceProcessor.close()
      log.info("done exit")
    }

    // Wait on the thriftServer so that shutdownTimeout is respected.
    Await.result(adminHttpServer)
  }
}
