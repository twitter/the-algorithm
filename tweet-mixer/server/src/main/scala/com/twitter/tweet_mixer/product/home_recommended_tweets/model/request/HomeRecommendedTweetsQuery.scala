package com.twitter.tweet_mixer.product.home_recommended_tweets.model.request

import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.model.marshalling.request.ClientContext
import com.twitter.product_mixer.core.model.marshalling.request.DebugOptions
import com.twitter.product_mixer.core.model.marshalling.request.HasExcludedIds
import com.twitter.product_mixer.core.model.marshalling.request.Product
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.timelines.configapi.Params
import com.twitter.tweet_mixer.model.request.HomeRecommendedTweetsProduct

case class HomeRecommendedTweetsQuery(
  override val params: Params,
  override val clientContext: ClientContext,
  override val excludedIds: Set[Long],
  override val requestedMaxResults: Option[Int],
  override val features: Option[FeatureMap],
  override val debugOptions: Option[DebugOptions],
  val getRandomTweets: Boolean)
    extends PipelineQuery
    with HasExcludedIds {
  override val product: Product = HomeRecommendedTweetsProduct

  override def withFeatureMap(features: FeatureMap): HomeRecommendedTweetsQuery =
    copy(features = Some(features))
}
