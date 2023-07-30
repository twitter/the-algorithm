package com.X.timelineranker.config

import com.X.cortex_tweet_annotate.thriftscala.CortexTweetQueryService
import com.X.finagle.Service
import com.X.finagle.mtls.authentication.ServiceIdentifier
import com.X.finagle.service.RetryPolicy
import com.X.finagle.stats.StatsReceiver
import com.X.finagle.thrift.ClientId
import com.X.finagle.thrift.ThriftClientRequest
import com.X.gizmoduck.thriftscala.{UserService => Gizmoduck}
import com.X.manhattan.v1.thriftscala.{ManhattanCoordinator => ManhattanV1}
import com.X.merlin.thriftscala.UserRolesService
import com.X.recos.user_tweet_entity_graph.thriftscala.UserTweetEntityGraph
import com.X.search.earlybird.thriftscala.EarlybirdService
import com.X.socialgraph.thriftscala.SocialGraphService
import com.X.storehaus.Store
import com.X.strato.client.{Client => StratoClient}
import com.X.timelineranker.recap.model.ContentFeatures
import com.X.timelineranker.thriftscala.TimelineRanker
import com.X.timelines.config.ConfigUtils
import com.X.timelines.config.TimelinesUnderlyingClientConfiguration
import com.X.timelines.model.TweetId
import com.X.timelineservice.thriftscala.TimelineService
import com.X.tweetypie.thriftscala.{TweetService => TweetyPie}
import com.X.util.Duration
import com.X.util.Try
import org.apache.thrift.protocol.TCompactProtocol

abstract class UnderlyingClientConfiguration(
  flags: TimelineRankerFlags,
  val statsReceiver: StatsReceiver)
    extends TimelinesUnderlyingClientConfiguration
    with ConfigUtils {

  lazy val thriftClientId: ClientId = timelineRankerClientId()
  override lazy val serviceIdentifier: ServiceIdentifier = flags.getServiceIdentifier

  def timelineRankerClientId(scope: Option[String] = None): ClientId = {
    clientIdWithScopeOpt("timelineranker", flags.getEnv, scope)
  }

  def createEarlybirdClient(
    scope: String,
    requestTimeout: Duration,
    timeout: Duration,
    retryPolicy: RetryPolicy[Try[Nothing]]
  ): EarlybirdService.MethodPerEndpoint = {
    val scopedName = s"earlybird/$scope"

    methodPerEndpointClient[
      EarlybirdService.ServicePerEndpoint,
      EarlybirdService.MethodPerEndpoint](
      thriftMuxClientBuilder(
        scopedName,
        protocolFactoryOption = Some(new TCompactProtocol.Factory),
        requireMutualTls = true)
        .dest("/s/earlybird-root-superroot/root-superroot")
        .timeout(timeout)
        .requestTimeout(requestTimeout)
        .retryPolicy(retryPolicy)
    )
  }

  def createEarlybirdRealtimeCgClient(
    scope: String,
    requestTimeout: Duration,
    timeout: Duration,
    retryPolicy: RetryPolicy[Try[Nothing]]
  ): EarlybirdService.MethodPerEndpoint = {
    val scopedName = s"earlybird/$scope"

    methodPerEndpointClient[
      EarlybirdService.ServicePerEndpoint,
      EarlybirdService.MethodPerEndpoint](
      thriftMuxClientBuilder(
        scopedName,
        protocolFactoryOption = Some(new TCompactProtocol.Factory),
        requireMutualTls = true)
        .dest("/s/earlybird-rootrealtimecg/root-realtime_cg")
        .timeout(timeout)
        .requestTimeout(requestTimeout)
        .retryPolicy(retryPolicy)
    )
  }

  def cortexTweetQueryServiceClient: CortexTweetQueryService.MethodPerEndpoint
  def gizmoduckClient: Gizmoduck.MethodPerEndpoint
  def manhattanStarbuckClient: ManhattanV1.MethodPerEndpoint
  def sgsClient: SocialGraphService.MethodPerEndpoint
  def timelineRankerForwardingClient: TimelineRanker.FinagledClient
  def timelineServiceClient: TimelineService.MethodPerEndpoint
  def tweetyPieHighQoSClient: TweetyPie.MethodPerEndpoint
  def tweetyPieLowQoSClient: TweetyPie.MethodPerEndpoint
  def userRolesServiceClient: UserRolesService.MethodPerEndpoint
  def contentFeaturesCache: Store[TweetId, ContentFeatures]
  def userTweetEntityGraphClient: UserTweetEntityGraph.FinagledClient
  def stratoClient: StratoClient

  def darkTrafficProxy: Option[Service[ThriftClientRequest, Array[Byte]]] = {
    if (flags.darkTrafficDestination.trim.nonEmpty) {
      Some(darkTrafficClient(flags.darkTrafficDestination))
    } else {
      None
    }
  }

}
