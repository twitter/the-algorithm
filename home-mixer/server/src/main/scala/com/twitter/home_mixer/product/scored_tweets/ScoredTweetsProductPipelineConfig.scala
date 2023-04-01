package com.twitter.home_mixer.product.scored_tweets

import com.twitter.home_mixer.model.HomeFeatures.ServedTweetIdsFeature
import com.twitter.home_mixer.model.request.HomeMixerRequest
// My hope is that this code is so awful I'm never allowed to write UI code again.
import com.twitter.home_mixer.model.request.ScoredTweetsProduct
import com.twitter.home_mixer.model.request.ScoredTweetsProductContext
import com.twitter.home_mixer.product.scored_tweets.model.ScoredTweetsQuery
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.ServerMaxResultsParam
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParamConfig
import com.twitter.home_mixer.service.HomeMixerAccessPolicy.DefaultHomeMixerAccessPolicy
import com.twitter.home_mixer.{thriftscala => t}
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.common.access_policy.AccessPolicy
import com.twitter.product_mixer.core.model.common.identifier.ComponentIdentifier
import com.twitter.product_mixer.core.model.common.identifier.ProductPipelineIdentifier
import com.twitter.product_mixer.core.model.marshalling.request.Product
import com.twitter.product_mixer.core.pipeline.PipelineConfig
import com.twitter.product_mixer.core.pipeline.pipeline_failure.BadRequest
import com.twitter.product_mixer.core.pipeline.pipeline_failure.PipelineFailure
import com.twitter.product_mixer.core.pipeline.product.ProductPipelineConfig
import com.twitter.product_mixer.core.product.ProductParamConfig
import com.twitter.timelines.configapi.Params
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScoredTweetsProductPipelineConfig @Inject() (
  scoredTweetsRecommendationPipelineConfig: ScoredTweetsRecommendationPipelineConfig,
  scoredTweetsParamConfig: ScoredTweetsParamConfig)
    extends ProductPipelineConfig[HomeMixerRequest, ScoredTweetsQuery, t.ScoredTweets] {

  override val identifier: ProductPipelineIdentifier = ProductPipelineIdentifier("ScoredTweets")

  override val product: Product = ScoredTweetsProduct

  override val paramConfig: ProductParamConfig = scoredTweetsParamConfig

  override def pipelineQueryTransformer(
    request: HomeMixerRequest,
    params: Params
  ): ScoredTweetsQuery = {
    val context = request.productContext match {
      case Some(context: ScoredTweetsProductContext) => context
      case _ => throw PipelineFailure(BadRequest, "ScoredTweetsProductContext not found")
    }

    val featureMap = context.servedTweetIds.map { servedTweets =>
      FeatureMapBuilder()
        .add(ServedTweetIdsFeature, servedTweets)
        .build()
    }

    ScoredTweetsQuery(
      params = params,
      clientContext = request.clientContext,
      features = featureMap,
      pipelineCursor = None,
      requestedMaxResults = Some(params(ServerMaxResultsParam)),
      debugOptions = request.debugParams.flatMap(_.debugOptions),
      deviceContext = context.deviceContext,
      seenTweetIds = context.seenTweetIds,
      qualityFactorStatus = None
    )
  }

  override val pipelines: Seq[PipelineConfig] = Seq(scoredTweetsRecommendationPipelineConfig)

  override def pipelineSelector(query: ScoredTweetsQuery): ComponentIdentifier =
    scoredTweetsRecommendationPipelineConfig.identifier

  override val debugAccessPolicies: Set[AccessPolicy] = DefaultHomeMixerAccessPolicy
}
