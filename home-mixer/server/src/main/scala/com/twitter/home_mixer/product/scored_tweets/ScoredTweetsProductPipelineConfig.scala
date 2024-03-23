package com.ExTwitter.home_mixer.product.scored_tweets

import com.ExTwitter.home_mixer.model.HomeFeatures.ServedTweetIdsFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.TimelineServiceTweetsFeature
import com.ExTwitter.home_mixer.model.request.HomeMixerRequest
import com.ExTwitter.home_mixer.model.request.ScoredTweetsProduct
import com.ExTwitter.home_mixer.model.request.ScoredTweetsProductContext
import com.ExTwitter.home_mixer.product.scored_tweets.model.ScoredTweetsQuery
import com.ExTwitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.ServerMaxResultsParam
import com.ExTwitter.home_mixer.product.scored_tweets.param.ScoredTweetsParamConfig
import com.ExTwitter.home_mixer.service.HomeMixerAccessPolicy.DefaultHomeMixerAccessPolicy
import com.ExTwitter.home_mixer.{thriftscala => t}
import com.ExTwitter.product_mixer.component_library.premarshaller.cursor.UrtCursorSerializer
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.ExTwitter.product_mixer.core.functional_component.common.access_policy.AccessPolicy
import com.ExTwitter.product_mixer.core.model.common.identifier.ComponentIdentifier
import com.ExTwitter.product_mixer.core.model.common.identifier.ProductPipelineIdentifier
import com.ExTwitter.product_mixer.core.model.marshalling.request.Product
import com.ExTwitter.product_mixer.core.pipeline.PipelineConfig
import com.ExTwitter.product_mixer.core.pipeline.pipeline_failure.BadRequest
import com.ExTwitter.product_mixer.core.pipeline.pipeline_failure.PipelineFailure
import com.ExTwitter.product_mixer.core.pipeline.product.ProductPipelineConfig
import com.ExTwitter.product_mixer.core.product.ProductParamConfig
import com.ExTwitter.timelines.configapi.Params
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

    val featureMap = FeatureMapBuilder()
      .add(ServedTweetIdsFeature, context.servedTweetIds.getOrElse(Seq.empty))
      .add(TimelineServiceTweetsFeature, context.backfillTweetIds.getOrElse(Seq.empty))
      .build()

    ScoredTweetsQuery(
      params = params,
      clientContext = request.clientContext,
      pipelineCursor =
        request.serializedRequestCursor.flatMap(UrtCursorSerializer.deserializeOrderedCursor),
      requestedMaxResults = Some(params(ServerMaxResultsParam)),
      debugOptions = request.debugParams.flatMap(_.debugOptions),
      features = Some(featureMap),
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
