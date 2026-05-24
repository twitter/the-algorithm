package com.twitter.home_mixer.product.scored_tweets

import com.twitter.home_mixer.model.HomeFeatures.ServedAuthorIdsFeature
import com.twitter.home_mixer.model.HomeFeatures.ServedTweetIdsFeature
import com.twitter.home_mixer.model.HomeFeatures.SignupCountryFeature
import com.twitter.home_mixer.model.HomeFeatures.SignupSourceFeature
import com.twitter.home_mixer.model.HomeFeatures.TimelineServiceTweetsFeature
import com.twitter.home_mixer.model.HomeFeatures.UserFollowersCountFeature
import com.twitter.home_mixer.model.HomeFeatures.ViewerAllowsForYouRecommendationsFeature
import com.twitter.home_mixer.model.request.HomeMixerRequest
import com.twitter.home_mixer.model.request.ScoredTweetsProduct
import com.twitter.home_mixer.model.request.ScoredTweetsProductContext
import com.twitter.home_mixer.product.scored_tweets.model.ScoredTweetsQuery
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParamConfig
import com.twitter.home_mixer.service.HomeMixerAccessPolicy.DefaultHomeMixerAccessPolicy
import com.twitter.home_mixer.{thriftscala => t}
import com.twitter.product_mixer.component_library.premarshaller.cursor.UrtCursorSerializer
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

    val featureMap = FeatureMapBuilder()
      .add(ServedTweetIdsFeature, context.servedTweetIds.getOrElse(Seq.empty))
      .add(TimelineServiceTweetsFeature, context.backfillTweetIds.getOrElse(Seq.empty))
      .add(SignupCountryFeature, context.signupCountryCode)
      .add(ViewerAllowsForYouRecommendationsFeature, context.allowForYouRecommendations)
      .add(SignupSourceFeature, context.signupSource)
      .add(ServedAuthorIdsFeature, context.servedAuthorIds.getOrElse(Map.empty[Long, Seq[Long]]))
      .add(UserFollowersCountFeature, context.followerCount)
      .build()

    ScoredTweetsQuery(
      params = params,
      clientContext = request.clientContext,
      pipelineCursor =
        request.serializedRequestCursor.flatMap(UrtCursorSerializer.deserializeOrderedCursor),
      requestedMaxResults = request.maxResults,
      debugOptions = request.debugParams.flatMap(_.debugOptions),
      features = Some(featureMap),
      deviceContext = context.deviceContext,
      seenTweetIds = context.seenTweetIds,
      qualityFactorStatus = None,
      product = product
    )
  }

  override val pipelines: Seq[PipelineConfig] = Seq(scoredTweetsRecommendationPipelineConfig)

  override def pipelineSelector(query: ScoredTweetsQuery): ComponentIdentifier =
    scoredTweetsRecommendationPipelineConfig.identifier

  override val debugAccessPolicies: Set[AccessPolicy] = DefaultHomeMixerAccessPolicy
}
