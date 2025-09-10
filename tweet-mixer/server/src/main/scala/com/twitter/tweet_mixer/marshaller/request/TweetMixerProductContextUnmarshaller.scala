package com.twitter.tweet_mixer.marshaller.request

import com.twitter.product_mixer.core.model.marshalling.request.ProductContext
import com.twitter.tweet_mixer.product.home_recommended_tweets.marshaller.request.HomeRecommendedTweetsProductContextUnmarshaller
import com.twitter.tweet_mixer.product.notifications_recommended_tweets.marshaller.request.NotificationsRecommendedTweetsProductContextUnmarshaller
import com.twitter.tweet_mixer.product.imv_recommended_tweets.marshaller.request.IMVRecommendedTweetsProductContextUnmarshaller
import com.twitter.tweet_mixer.product.imv_related_tweets.marshaller.request.IMVRelatedTweetsProductContextUnmarshaller
import com.twitter.tweet_mixer.product.rux_related_tweets.marshaller.request.RUXRelatedTweetsProductContextUnmarshaller
import com.twitter.tweet_mixer.product.debugger_tweets.marshaller.request.DebuggerTweetsProductContextUnmarshaller
import com.twitter.tweet_mixer.product.video_recommended_tweets.marshaller.request.VideoRecommendedTweetsProductContextUnmarshaller
import com.twitter.tweet_mixer.product.topic_tweets.marshaller.request.TopicTweetsProductContextUnmarshaller
import com.twitter.tweet_mixer.product.logged_out_video_recommended_tweets.marshaller.request.LoggedOutVideoRecommendedTweetsProductContextUnmarshaller
import com.twitter.tweet_mixer.{thriftscala => t}

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TweetMixerProductContextUnmarshaller @Inject() (
  homeRecommendedTweetsProductContextUnmarshaller: HomeRecommendedTweetsProductContextUnmarshaller,
  notificationsRecommendedTweetsProductContextUnmarshaller: NotificationsRecommendedTweetsProductContextUnmarshaller,
  imvRecommendedTweetsProductContextUnmarshaller: IMVRecommendedTweetsProductContextUnmarshaller,
  imvRelatedTweetsProductContextUnmarshaller: IMVRelatedTweetsProductContextUnmarshaller,
  ruxRelatedTweetsProductContextUnmarshaller: RUXRelatedTweetsProductContextUnmarshaller,
  videoRecommendedTweetsProductContextUnmarshaller: VideoRecommendedTweetsProductContextUnmarshaller,
  loggedOutVideoRecommendedTweetsProductContextUnmarshaller: LoggedOutVideoRecommendedTweetsProductContextUnmarshaller,
  topicTweetsProductContextUnmarshaller: TopicTweetsProductContextUnmarshaller,
  debuggerTweetsProductContextUnmarshaller: DebuggerTweetsProductContextUnmarshaller) {

  def apply(productContext: t.ProductContext): ProductContext = productContext match {
    case t.ProductContext.HomeRecommendedTweetsProductContext(context) =>
      homeRecommendedTweetsProductContextUnmarshaller(context)
    case t.ProductContext.NotificationsRecommendedTweetsProductContext(context) =>
      notificationsRecommendedTweetsProductContextUnmarshaller(context)
    case t.ProductContext.ImvRecommendedTweetsProductContext(context) =>
      imvRecommendedTweetsProductContextUnmarshaller(context)
    case t.ProductContext.ImvRelatedTweetsProductContext(context) =>
      imvRelatedTweetsProductContextUnmarshaller(context)
    case t.ProductContext.RuxRelatedTweetsProductContext(context) =>
      ruxRelatedTweetsProductContextUnmarshaller(context)
    case t.ProductContext.DebuggerTweetsProductContext(context) =>
      debuggerTweetsProductContextUnmarshaller(context)
    case t.ProductContext.VideoRecommendedTweetsProductContext(context) =>
      videoRecommendedTweetsProductContextUnmarshaller(context)
    case t.ProductContext.LoggedOutVideoRecommendedTweetsProductContext(context) =>
      loggedOutVideoRecommendedTweetsProductContextUnmarshaller(context)
    case t.ProductContext.TopicTweetsProductContext(context) =>
      topicTweetsProductContextUnmarshaller(context)
    case t.ProductContext.UnknownUnionField(field) =>
      throw new UnsupportedOperationException(s"Unknown display context: ${field.field.name}")
  }
}
