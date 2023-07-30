package com.X.recos.user_video_graph

import com.X.finagle.thrift.ClientId
import com.X.finagle.tracing.Trace
import com.X.finagle.tracing.TraceId
import com.X.recos.decider.EndpointLoadShedder
import com.X.recos.user_video_graph.thriftscala._
import com.X.util.Duration
import com.X.util.Future
import com.X.util.Timer
import scala.concurrent.duration.MILLISECONDS
import com.X.logging.Logger
import com.X.recos.user_tweet_graph.relatedTweetHandlers.ConsumersBasedRelatedTweetsHandler
import com.X.recos.user_video_graph.relatedTweetHandlers.ProducerBasedRelatedTweetsHandler
import com.X.recos.user_video_graph.relatedTweetHandlers.TweetBasedRelatedTweetsHandler

object UserVideoGraph {
  def traceId: TraceId = Trace.id
  def clientId: Option[ClientId] = ClientId.current
}

class UserVideoGraph(
  tweetBasedRelatedTweetsHandler: TweetBasedRelatedTweetsHandler,
  producerBasedRelatedTweetsHandler: ProducerBasedRelatedTweetsHandler,
  consumersBasedRelatedTweetsHandler: ConsumersBasedRelatedTweetsHandler,
  endpointLoadShedder: EndpointLoadShedder
)(
  implicit timer: Timer)
    extends thriftscala.UserVideoGraph.MethodPerEndpoint {

  private val defaultTimeout: Duration = Duration(50, MILLISECONDS)
  private val EmptyResponse = Future.value(RelatedTweetResponse())
  private val log = Logger()

  override def tweetBasedRelatedTweets(
    request: TweetBasedRelatedTweetRequest
  ): Future[RelatedTweetResponse] =
    endpointLoadShedder("videoGraphTweetBasedRelatedTweets") {
      tweetBasedRelatedTweetsHandler(request).raiseWithin(defaultTimeout)
    }.rescue {
      case EndpointLoadShedder.LoadSheddingException =>
        EmptyResponse
      case e =>
        log.info("user-video-graph_tweetBasedRelatedTweets" + e)
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
        log.info("user-video-graph_producerBasedRelatedTweets" + e)
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
        log.info("user-video-graph_consumersBasedRelatedTweets" + e)
        EmptyResponse
    }
}
