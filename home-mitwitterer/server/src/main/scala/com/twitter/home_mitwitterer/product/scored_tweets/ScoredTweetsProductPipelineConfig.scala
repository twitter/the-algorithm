package com.twitter.home_mitwitterer.product.scored_tweets

import com.twitter.home_mitwitterer.model.HomeFeatures.ServedTweetIdsFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.TimelineServiceTweetsFeature
import com.twitter.home_mitwitterer.model.request.HomeMitwittererRequest
import com.twitter.home_mitwitterer.model.request.ScoredTweetsProduct
import com.twitter.home_mitwitterer.model.request.ScoredTweetsProductContetwittert
import com.twitter.home_mitwitterer.product.scored_tweets.model.ScoredTweetsQuery
import com.twitter.home_mitwitterer.product.scored_tweets.param.ScoredTweetsParam.ServerMatwitterResultsParam
import com.twitter.home_mitwitterer.product.scored_tweets.param.ScoredTweetsParamConfig
import com.twitter.home_mitwitterer.service.HomeMitwittererAccessPolicy.DefaultHomeMitwittererAccessPolicy
import com.twitter.home_mitwitterer.{thriftscala => t}
import com.twitter.product_mitwitterer.component_library.premarshaller.cursor.UrtCursorSerializer
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mitwitterer.core.functional_component.common.access_policy.AccessPolicy
import com.twitter.product_mitwitterer.core.model.common.identifier.ComponentIdentifier
import com.twitter.product_mitwitterer.core.model.common.identifier.ProductPipelineIdentifier
import com.twitter.product_mitwitterer.core.model.marshalling.request.Product
import com.twitter.product_mitwitterer.core.pipeline.PipelineConfig
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.BadRequest
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.PipelineFailure
import com.twitter.product_mitwitterer.core.pipeline.product.ProductPipelineConfig
import com.twitter.product_mitwitterer.core.product.ProductParamConfig
import com.twitter.timelines.configapi.Params
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class ScoredTweetsProductPipelineConfig @Inject() (
  scoredTweetsRecommendationPipelineConfig: ScoredTweetsRecommendationPipelineConfig,
  scoredTweetsParamConfig: ScoredTweetsParamConfig)
    etwittertends ProductPipelineConfig[HomeMitwittererRequest, ScoredTweetsQuery, t.ScoredTweets] {

  override val identifier: ProductPipelineIdentifier = ProductPipelineIdentifier("ScoredTweets")

  override val product: Product = ScoredTweetsProduct

  override val paramConfig: ProductParamConfig = scoredTweetsParamConfig

  override def pipelineQueryTransformer(
    request: HomeMitwittererRequest,
    params: Params
  ): ScoredTweetsQuery = {
    val contetwittert = request.productContetwittert match {
      case Some(contetwittert: ScoredTweetsProductContetwittert) => contetwittert
      case _ => throw PipelineFailure(BadRequest, "ScoredTweetsProductContetwittert not found")
    }

    val featureMap = FeatureMapBuilder()
      .add(ServedTweetIdsFeature, contetwittert.servedTweetIds.getOrElse(Seq.empty))
      .add(TimelineServiceTweetsFeature, contetwittert.backfillTweetIds.getOrElse(Seq.empty))
      .build()

    ScoredTweetsQuery(
      params = params,
      clientContetwittert = request.clientContetwittert,
      pipelineCursor =
        request.serializedRequestCursor.flatMap(UrtCursorSerializer.deserializeOrderedCursor),
      requestedMatwitterResults = Some(params(ServerMatwitterResultsParam)),
      debugOptions = request.debugParams.flatMap(_.debugOptions),
      features = Some(featureMap),
      deviceContetwittert = contetwittert.deviceContetwittert,
      seenTweetIds = contetwittert.seenTweetIds,
      qualityFactorStatus = None
    )
  }

  override val pipelines: Seq[PipelineConfig] = Seq(scoredTweetsRecommendationPipelineConfig)

  override def pipelineSelector(query: ScoredTweetsQuery): ComponentIdentifier =
    scoredTweetsRecommendationPipelineConfig.identifier

  override val debugAccessPolicies: Set[AccessPolicy] = DefaultHomeMitwittererAccessPolicy
}
