package com.twitter.recos.user_video_graph

import com.twitter.finagle.thrift.ClientId
import com.twitter.finagle.tracing.Trace
import com.twitter.finagle.tracing.TraceId
import com.twitter.recos.decider.EndpointLoadShedder
import com.twitter.recos.user_video_graph.thriftscala._
import com.twitter.util.Duration
import com.twitter.util.Future
import com.twitter.util.Timer
import scala.concurrent.duration.MILLISECONDS
import com.twitter.logging.Logger
import com.twitter.recos.user_tweet_graph.relatedTweetHandlers.ConsumersBasedRelatedTweetsHandler
import com.twitter.recos.user_video_graph.relatedTweetHandlers.ProducerBasedRelatedTweetsHandler
import com.twitter.recos.user_video_graph.relatedTweetHandlers.TweetBasedRelatedTweetsHandler

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
