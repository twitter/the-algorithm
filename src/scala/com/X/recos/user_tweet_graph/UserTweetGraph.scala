package com.X.recos.user_tweet_graph

import com.X.finagle.thrift.ClientId
import com.X.finagle.tracing.Trace
import com.X.finagle.tracing.TraceId
import com.X.recos.decider.EndpointLoadShedder
import com.X.recos.recos_common.thriftscala._
import com.X.recos.user_tweet_graph.thriftscala._
import com.X.util.Duration
import com.X.util.Future
import com.X.util.Timer
import scala.concurrent.duration.MILLISECONDS
import com.X.logging.Logger
import com.X.recos.user_tweet_graph.relatedTweetHandlers.TweetBasedRelatedTweetsHandler
import com.X.recos.user_tweet_graph.relatedTweetHandlers.ProducerBasedRelatedTweetsHandler
import com.X.recos.user_tweet_graph.relatedTweetHandlers.ConsumersBasedRelatedTweetsHandler
import com.X.simclusters_v2.common.TweetId
import com.X.simclusters_v2.common.UserId

object UserTweetGraph {
  def traceId: TraceId = Trace.id
  def clientId: Option[ClientId] = ClientId.current
}

class UserTweetGraph(
  tweetBasedRelatedTweetsHandler: TweetBasedRelatedTweetsHandler,
  producerBasedRelatedTweetsHandler: ProducerBasedRelatedTweetsHandler,
  consumersBasedRelatedTweetsHandler: ConsumersBasedRelatedTweetsHandler,
  endpointLoadShedder: EndpointLoadShedder
)(
  implicit timer: Timer)
    extends thriftscala.UserTweetGraph.MethodPerEndpoint {

  private val defaultTimeout: Duration = Duration(50, MILLISECONDS)
  private val EmptyResponse = Future.value(RelatedTweetResponse())
  private val EmptyFeatureResponse = Future.value(UserTweetFeatureResponse())

  private val log = Logger()

  override def recommendTweets(request: RecommendTweetRequest): Future[RecommendTweetResponse] =
    Future.value(RecommendTweetResponse())

  override def getLeftNodeEdges(request: GetRecentEdgesRequest): Future[GetRecentEdgesResponse] =
    Future.value(GetRecentEdgesResponse())

  override def getRightNode(tweet: Long): Future[NodeInfo] = Future.value(NodeInfo())

  // deprecated
  override def relatedTweets(request: RelatedTweetRequest): Future[RelatedTweetResponse] =
    EmptyResponse

  override def tweetBasedRelatedTweets(
    request: TweetBasedRelatedTweetRequest
  ): Future[RelatedTweetResponse] =
    endpointLoadShedder("tweetBasedRelatedTweets") {
      tweetBasedRelatedTweetsHandler(request).raiseWithin(defaultTimeout)
    }.rescue {
      case EndpointLoadShedder.LoadSheddingException =>
        EmptyResponse
      case e =>
        log.info("user-tweet-graph_tweetBasedRelatedTweets" + e)
        EmptyResponse
    }

  override def producerBasedRelatedTweets(
    request: ProducerBasedRelatedTweetRequest
  ): Future[RelatedTweetResponse] =
    endpointLoadShedder("producerBasedRelatedTweets") {
      producerBasedRelatedTweetsHandler(request).raiseWithin(defaultTimeout)
    }.rescue {
      case EndpointLoadShedder.LoadSheddingException =>
        EmptyResponse
      case e =>
        log.info("user-tweet-graph_producerBasedRelatedTweets" + e)
        EmptyResponse
    }

  override def consumersBasedRelatedTweets(
    request: ConsumersBasedRelatedTweetRequest
  ): Future[RelatedTweetResponse] =
    endpointLoadShedder("consumersBasedRelatedTweets") {
      consumersBasedRelatedTweetsHandler(request).raiseWithin(defaultTimeout)
    }.rescue {
      case EndpointLoadShedder.LoadSheddingException =>
        EmptyResponse
      case e =>
        log.info("user-tweet-graph_consumersBasedRelatedTweets" + e)
        EmptyResponse
    }

  // deprecated
  override def userTweetFeatures(
    userId: UserId,
    tweetId: TweetId
  ): Future[UserTweetFeatureResponse] =
    EmptyFeatureResponse

}
