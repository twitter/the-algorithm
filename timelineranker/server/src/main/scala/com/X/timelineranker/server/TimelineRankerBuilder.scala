package com.X.timelineranker.server

import com.X.concurrent.AsyncSemaphore
import com.X.finagle.Filter
import com.X.finagle.ServiceFactory
import com.X.finagle.thrift.filter.ThriftForwardingWarmUpFilter
import com.X.finagle.thrift.ClientIdRequiredFilter
import com.X.timelineranker.config.RuntimeConfiguration
import com.X.timelineranker.config.TimelineRankerConstants
import com.X.timelineranker.decider.DeciderKey
import com.X.timelineranker.entity_tweets.EntityTweetsRepositoryBuilder
import com.X.timelineranker.observe.DebugObserverBuilder
import com.X.timelineranker.parameters.ConfigBuilder
import com.X.timelineranker.parameters.util.RecapQueryParamInitializer
import com.X.timelineranker.recap_author.RecapAuthorRepositoryBuilder
import com.X.timelineranker.recap_hydration.RecapHydrationRepositoryBuilder
import com.X.timelineranker.in_network_tweets.InNetworkTweetRepositoryBuilder
import com.X.timelineranker.repository._
import com.X.timelineranker.thriftscala.TimelineRanker$FinagleService
import com.X.timelineranker.uteg_liked_by_tweets.UtegLikedByTweetsRepositoryBuilder
import com.X.timelines.filter.DarkTrafficFilterBuilder
import com.X.timelines.observe.ServiceObserver
import com.X.timelines.util.DeciderableRequestSemaphoreFilter
import org.apache.thrift.protocol.TBinaryProtocol
import org.apache.thrift.protocol.TCompactProtocol
import org.apache.thrift.protocol.TProtocolFactory

class TimelineRankerBuilder(config: RuntimeConfiguration) {

  private[this] val underlyingClients = config.underlyingClients

  private[this] val configBuilder =
    new ConfigBuilder(config.deciderGateBuilder, config.statsReceiver)
  private[this] val debugObserverBuilder = new DebugObserverBuilder(config.whitelist)
  private[this] val serviceObserver = new ServiceObserver(config.statsReceiver)
  private[this] val routingRepository = RoutingTimelineRepositoryBuilder(config, configBuilder)
  private[this] val inNetworkTweetRepository =
    new InNetworkTweetRepositoryBuilder(config, configBuilder).apply()
  private[this] val recapHydrationRepository =
    new RecapHydrationRepositoryBuilder(config, configBuilder).apply()
  private[this] val recapAuthorRepository = new RecapAuthorRepositoryBuilder(config).apply()
  private[this] val entityTweetsRepository =
    new EntityTweetsRepositoryBuilder(config, configBuilder).apply()
  private[this] val utegLikedByTweetsRepository =
    new UtegLikedByTweetsRepositoryBuilder(config, configBuilder).apply()

  private[this] val queryParamInitializer = new RecapQueryParamInitializer(
    config = configBuilder.rootConfig,
    runtimeConfig = config
  )

  val timelineRanker: TimelineRanker = new TimelineRanker(
    routingRepository = routingRepository,
    inNetworkTweetRepository = inNetworkTweetRepository,
    recapHydrationRepository = recapHydrationRepository,
    recapAuthorRepository = recapAuthorRepository,
    entityTweetsRepository = entityTweetsRepository,
    utegLikedByTweetsRepository = utegLikedByTweetsRepository,
    serviceObserver = serviceObserver,
    abdecider = Some(config.abdecider),
    clientRequestAuthorizer = config.clientRequestAuthorizer,
    debugObserver = debugObserverBuilder.observer,
    queryParamInitializer = queryParamInitializer,
    statsReceiver = config.statsReceiver
  )

  private[this] def mkServiceFactory(
    protocolFactory: TProtocolFactory
  ): ServiceFactory[Array[Byte], Array[Byte]] = {
    val clientIdFilter = new ClientIdRequiredFilter[Array[Byte], Array[Byte]](
      config.statsReceiver.scope("service").scope("filter")
    )

    // Limits the total number of concurrent requests handled by the TimelineRanker
    val maxConcurrencyFilter = {
      val asyncSemaphore = new AsyncSemaphore(
        initialPermits = config.maxConcurrency,
        maxWaiters = 0
      )
      val enableLimiting = config.deciderGateBuilder.linearGate(
        DeciderKey.EnableMaxConcurrencyLimiting
      )

      new DeciderableRequestSemaphoreFilter(
        enableFilter = enableLimiting,
        semaphore = asyncSemaphore,
        statsReceiver = config.statsReceiver
      )
    }

    // Forwards a percentage of traffic via the DarkTrafficFilter to the TimelineRanker proxy, which in turn can be
    // used to forward dark traffic to staged instances
    val darkTrafficFilter = DarkTrafficFilterBuilder(
      config.deciderGateBuilder,
      DeciderKey.EnableRoutingToRankerDevProxy,
      TimelineRankerConstants.ClientPrefix,
      underlyingClients.darkTrafficProxy,
      config.statsReceiver
    )

    val warmupForwardingFilter = if (config.isProd) {
      new ThriftForwardingWarmUpFilter(
        Warmup.WarmupForwardingTime,
        underlyingClients.timelineRankerForwardingClient.service,
        config.statsReceiver.scope("warmupForwardingFilter"),
        isBypassClient = { _.name.startsWith("timelineranker.") }
      )
    } else Filter.identity[Array[Byte], Array[Byte]]

    val serviceFilterChain = clientIdFilter
      .andThen(maxConcurrencyFilter)
      .andThen(warmupForwardingFilter)
      .andThen(darkTrafficFilter)
      .andThen(serviceObserver.thriftExceptionFilter)

    val finagleService =
      new TimelineRanker$FinagleService(timelineRanker, protocolFactory)

    ServiceFactory.const(serviceFilterChain andThen finagleService)
  }

  val serviceFactory: ServiceFactory[Array[Byte], Array[Byte]] =
    mkServiceFactory(new TBinaryProtocol.Factory())

  val compactProtocolServiceFactory: ServiceFactory[Array[Byte], Array[Byte]] =
    mkServiceFactory(new TCompactProtocol.Factory())
}
