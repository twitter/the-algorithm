package com.twitter.tweet_mixer.product

import com.twitter.inject.Injector
import com.twitter.product_mixer.core.product.guice.ProductScope
import com.twitter.product_mixer.core.product.registry.ProductPipelineRegistryConfig
import com.twitter.tweet_mixer.model.request.DebuggerTweetsProduct
import com.twitter.tweet_mixer.model.request.HomeRecommendedTweetsProduct
import com.twitter.tweet_mixer.model.request.IMVRecommendedTweetsProduct
import com.twitter.tweet_mixer.model.request.IMVRelatedTweetsProduct
import com.twitter.tweet_mixer.model.request.LoggedOutVideoRecommendedTweetsProduct
import com.twitter.tweet_mixer.model.request.NotificationsRecommendedTweetsProduct
import com.twitter.tweet_mixer.model.request.RUXRelatedTweetsProduct
import com.twitter.tweet_mixer.model.request.TopicTweetsProduct
import com.twitter.tweet_mixer.model.request.VideoRecommendedTweetsProduct
import com.twitter.tweet_mixer.product.home_recommended_tweets.HomeRecommendedTweetsProductPipelineConfig
import com.twitter.tweet_mixer.product.imv_recommended_tweets.IMVRecommendedTweetsProductPipelineConfig
import com.twitter.tweet_mixer.product.topic_tweets.TopicTweetsProductPipelineConfig
import com.twitter.tweet_mixer.product.imv_related_tweets.IMVRelatedTweetsProductPipelineConfig
import com.twitter.tweet_mixer.product.logged_out_video_recommended_tweets.LoggedOutVideoRecommendedTweetsProductPipelineConfig
import com.twitter.tweet_mixer.product.notifications_recommended_tweets.NotificationsRecommendedTweetsProductPipelineConfig
import com.twitter.tweet_mixer.product.rux_related_tweets.RUXRelatedTweetsProductPipelineConfig
import com.twitter.tweet_mixer.product.debugger_tweets.DebuggerTweetsProductPipelineConfig
import com.twitter.tweet_mixer.product.video_recommended_tweets.VideoRecommendedTweetsProductPipelineConfig

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TweetMixerProductPipelineRegistryConfig @Inject() (
  injector: Injector,
  productScope: ProductScope)
    extends ProductPipelineRegistryConfig {

  private val homeRecommendedTweetsProductPipelineConfig =
    productScope.let(HomeRecommendedTweetsProduct) {
      injector.instance[HomeRecommendedTweetsProductPipelineConfig]
    }

  private val notificationsRecommendedTweetsProductPipelineConfig =
    productScope.let(NotificationsRecommendedTweetsProduct) {
      injector.instance[NotificationsRecommendedTweetsProductPipelineConfig]
    }

  private val imvRecommendedTweetsProductPipelineConfig =
    productScope.let(IMVRecommendedTweetsProduct) {
      injector.instance[IMVRecommendedTweetsProductPipelineConfig]
    }

  private val imvRelatedTweetsProductPipelineConfig =
    productScope.let(IMVRelatedTweetsProduct) {
      injector.instance[IMVRelatedTweetsProductPipelineConfig]
    }

  private val ruxRelatedTweetsProductPipelineConfig =
    productScope.let(RUXRelatedTweetsProduct) {
      injector.instance[RUXRelatedTweetsProductPipelineConfig]
    }

  private val debuggerTweetsProductPipelineConfig =
    productScope.let(DebuggerTweetsProduct) {
      injector.instance[DebuggerTweetsProductPipelineConfig]
    }

  private val videoRecommendedTweetsProductPipelineConfig =
    productScope.let(VideoRecommendedTweetsProduct) {
      injector.instance[VideoRecommendedTweetsProductPipelineConfig]
    }

  private val loggedOutVideoRecommendedTweetsProductPipelineConfig =
    productScope.let(LoggedOutVideoRecommendedTweetsProduct) {
      injector.instance[LoggedOutVideoRecommendedTweetsProductPipelineConfig]
    }

  private val topicTweetsProductPipelineConfig =
    productScope.let(TopicTweetsProduct) {
      injector.instance[TopicTweetsProductPipelineConfig]
    }

  override val productPipelineConfigs = Seq(
    homeRecommendedTweetsProductPipelineConfig,
    notificationsRecommendedTweetsProductPipelineConfig,
    imvRecommendedTweetsProductPipelineConfig,
    imvRelatedTweetsProductPipelineConfig,
    ruxRelatedTweetsProductPipelineConfig,
    videoRecommendedTweetsProductPipelineConfig,
    loggedOutVideoRecommendedTweetsProductPipelineConfig,
    topicTweetsProductPipelineConfig,
    debuggerTweetsProductPipelineConfig
  )
}
