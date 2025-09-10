package com.twitter.tweet_mixer.product.home_recommended_tweets

import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.common.access_policy.AccessPolicy
import com.twitter.product_mixer.core.functional_component.common.alert.NotificationGroup
import com.twitter.product_mixer.core.model.common.identifier.ComponentIdentifier
import com.twitter.product_mixer.core.model.common.identifier.ProductPipelineIdentifier
import com.twitter.product_mixer.core.model.marshalling.request.Product
import com.twitter.product_mixer.core.pipeline.PipelineConfig
import com.twitter.product_mixer.core.pipeline.product.ProductPipelineConfig
import com.twitter.product_mixer.core.product.ProductParamConfig
import com.twitter.timelines.configapi.Params
import com.twitter.tweet_mixer.feature.PredictionRequestIdFeature
import com.twitter.tweet_mixer.model.request.HomeRecommendedTweetsProduct
import com.twitter.tweet_mixer.model.request.TweetMixerRequest
import com.twitter.tweet_mixer.model.response.TweetMixerResponse
import com.twitter.tweet_mixer.product.home_recommended_tweets.model.request.HomeRecommendedTweetsProductContext
import com.twitter.tweet_mixer.product.home_recommended_tweets.model.request.HomeRecommendedTweetsQuery
import com.twitter.tweet_mixer.product.home_recommended_tweets.param.HomeRecommendedTweetsParamConfig
import com.twitter.tweet_mixer.service.TweetMixerAccessPolicy.DefaultTweetMixerAccessPolicy
import com.twitter.tweet_mixer.service.TweetMixerAccessPolicy.HomeDebugAccessPolicy
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.AllHours
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.ForYouGroupMap
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.defaultEmptyResponseRateAlert
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.defaultLatencyAlert
import com.twitter.tweet_mixer.service.TweetMixerNotificationConfig.defaultSuccessRateAlert
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRecommendedTweetsProductPipelineConfig @Inject() (
  homeRecommendedTweetsParamConfig: HomeRecommendedTweetsParamConfig,
  homeRecommendedTweetsRecommendationPipelineConfig: HomeRecommendedTweetsRecommendationPipelineConfig)
    extends ProductPipelineConfig[
      TweetMixerRequest,
      HomeRecommendedTweetsQuery,
      TweetMixerResponse
    ] {

  private val ProductPipelineName = "HomeRecommendedTweets"

  override val identifier: ProductPipelineIdentifier =
    ProductPipelineIdentifier(ProductPipelineName)

  override val product: Product = HomeRecommendedTweetsProduct

  override val paramConfig: ProductParamConfig = homeRecommendedTweetsParamConfig

  override def pipelineQueryTransformer(
    request: TweetMixerRequest,
    params: Params
  ): HomeRecommendedTweetsQuery = {
    val (excludedIds, getRandomTweets, predictionRequestId) = request.productContext match {
      case Some(
            HomeRecommendedTweetsProductContext(
              excludedIds,
              getRandomTweets,
              predictionRequestId
            )
          ) =>
        (excludedIds, getRandomTweets, predictionRequestId)
      case _ => (Set.empty[Long], false, None)
    }

    val featureMap = FeatureMapBuilder()
      .add(PredictionRequestIdFeature, predictionRequestId)
      .build()

    HomeRecommendedTweetsQuery(
      params = params,
      clientContext = request.clientContext,
      excludedIds = excludedIds,
      requestedMaxResults = request.maxResults,
      features = Some(featureMap),
      debugOptions = request.debugParams.flatMap(_.debugOptions),
      getRandomTweets = getRandomTweets
    )
  }

  implicit val notificationGroupMap: Map[String, NotificationGroup] = ForYouGroupMap

  override val pipelines: Seq[PipelineConfig] =
    Seq(homeRecommendedTweetsRecommendationPipelineConfig)

  override def pipelineSelector(query: HomeRecommendedTweetsQuery): ComponentIdentifier =
    homeRecommendedTweetsRecommendationPipelineConfig.identifier

  override val debugAccessPolicies: Set[AccessPolicy] =
    DefaultTweetMixerAccessPolicy ++ HomeDebugAccessPolicy

  override val alerts = Seq(
    defaultSuccessRateAlert(),
    defaultEmptyResponseRateAlert(
      warnThreshold = 10,
      criticalThreshold = 20,
      notificationType = AllHours
    ),
    defaultLatencyAlert()
  )
}
