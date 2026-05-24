package com.twitter.tweet_mixer.marshaller.response

import com.twitter.tweet_mixer.model.response.TweetMixerProductResponse
import com.twitter.tweet_mixer.product.home_recommended_tweets.marshaller.response.HomeRecommendedTweetsProductResponseMarshaller
import com.twitter.tweet_mixer.product.home_recommended_tweets.model.response.HomeRecommendedTweetsProductResponse
import com.twitter.tweet_mixer.product.imv_recommended_tweets.marshaller.response.IMVRecommendedTweetsProductResponseMarshaller
import com.twitter.tweet_mixer.product.imv_recommended_tweets.model.response.IMVRecommendedTweetsProductResponse
import com.twitter.tweet_mixer.product.imv_related_tweets.marshaller.response.IMVRelatedTweetsProductResponseMarshaller
import com.twitter.tweet_mixer.product.imv_related_tweets.model.response.IMVRelatedTweetsProductResponse
import com.twitter.tweet_mixer.product.logged_out_video_recommended_tweets.marshaller.response.LoggedOutVideoRecommendedTweetsProductResponseMarshaller
import com.twitter.tweet_mixer.product.logged_out_video_recommended_tweets.model.response.LoggedOutVideoRecommendedTweetsProductResponse
import com.twitter.tweet_mixer.product.notifications_recommended_tweets.marshaller.response.NotificationsRecommendedTweetsProductResponseMarshaller
import com.twitter.tweet_mixer.product.notifications_recommended_tweets.model.response.NotificationsRecommendedTweetsProductResponse
import com.twitter.tweet_mixer.product.rux_related_tweets.marshaller.response.RUXRelatedTweetsProductResponseMarshaller
import com.twitter.tweet_mixer.product.rux_related_tweets.model.response.RUXRelatedTweetsProductResponse
import com.twitter.tweet_mixer.product.debugger_tweets.marshaller.response.DebuggerTweetsProductResponseMarshaller
import com.twitter.tweet_mixer.product.debugger_tweets.model.response.DebuggerTweetsProductResponse
import com.twitter.tweet_mixer.product.topic_tweets.marshaller.response.TopicTweetsProductResponseMarshaller
import com.twitter.tweet_mixer.product.topic_tweets.model.response.TopicsTweetsProductResponse
import com.twitter.tweet_mixer.product.video_recommended_tweets.marshaller.response.VideoRecommendedTweetsProductResponseMarshaller
import com.twitter.tweet_mixer.product.video_recommended_tweets.model.response.VideoRecommendedTweetsProductResponse
import com.twitter.tweet_mixer.{thriftscala => t}
import javax.inject.Inject
import javax.inject.Singleton

/**
 * When adding a new Product, you will need to marshal the domain Product Response into Thrift.
 *
 * @see [[HomeRecommendedTweetsProductResponseMarshaller]] for an example.
 */
@Singleton
class TweetMixerProductResponseMarshaller @Inject() (
  homeRecommendedTweetsProductResponseMarshaller: HomeRecommendedTweetsProductResponseMarshaller,
  notificationsRecommendedTweetsProductResponseMarshaller: NotificationsRecommendedTweetsProductResponseMarshaller,
  imvRecommendedTweetsProductResponseMarshaller: IMVRecommendedTweetsProductResponseMarshaller,
  imvRelatedTweetsProductResponseMarshaller: IMVRelatedTweetsProductResponseMarshaller,
  ruxRelatedTweetsProductResponseMarshaller: RUXRelatedTweetsProductResponseMarshaller,
  videoRecommendedTweetsProductResponseMarshaller: VideoRecommendedTweetsProductResponseMarshaller,
  loggedOutVideoRecommendedTweetsProductResponseMarshaller: LoggedOutVideoRecommendedTweetsProductResponseMarshaller,
  topicTweetsProductResponseMarshaller: TopicTweetsProductResponseMarshaller,
  debuggerTweetsProductResponseMarshaller: DebuggerTweetsProductResponseMarshaller) {

  def apply(
    tweetMixerProductResponse: TweetMixerProductResponse
  ): t.TweetMixerRecommendationResponse =
    tweetMixerProductResponse match {
      case productResponse: HomeRecommendedTweetsProductResponse =>
        homeRecommendedTweetsProductResponseMarshaller(productResponse)

      case productResponse: NotificationsRecommendedTweetsProductResponse =>
        notificationsRecommendedTweetsProductResponseMarshaller(productResponse)

      case productResponse: IMVRecommendedTweetsProductResponse =>
        imvRecommendedTweetsProductResponseMarshaller(productResponse)

      case productResponse: IMVRelatedTweetsProductResponse =>
        imvRelatedTweetsProductResponseMarshaller(productResponse)

      case productResponse: RUXRelatedTweetsProductResponse =>
        ruxRelatedTweetsProductResponseMarshaller(productResponse)

      case productResponse: DebuggerTweetsProductResponse =>
        debuggerTweetsProductResponseMarshaller(productResponse)

      case productResponse: VideoRecommendedTweetsProductResponse =>
        videoRecommendedTweetsProductResponseMarshaller(productResponse)

      case productResponse: LoggedOutVideoRecommendedTweetsProductResponse =>
        loggedOutVideoRecommendedTweetsProductResponseMarshaller(productResponse)

      case productResponse: TopicsTweetsProductResponse =>
        topicTweetsProductResponseMarshaller(productResponse)

      case _ =>
        throw new UnsupportedOperationException(
          s"Unknown product response: $tweetMixerProductResponse"
        )
    }
}
