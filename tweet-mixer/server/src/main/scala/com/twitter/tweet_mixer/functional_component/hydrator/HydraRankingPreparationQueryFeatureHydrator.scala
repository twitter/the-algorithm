package com.twitter.tweet_mixer.functional_component.hydrator

import com.twitter.hydra.root.thriftscala.HydraRootRecommendationResponse
import com.twitter.hydra.root.{thriftscala => t}
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.functional_component.marshaller.request.ClientContextMarshaller
import com.twitter.product_mixer.core.model.common.Conditionally
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.model.marshalling.request.ClientContext
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.EnableHydraScoringSideEffect
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.HydraModelName
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.HydraScoringPipelineEnabled
import javax.inject.Inject
import javax.inject.Singleton

object HydraRankingPreparedFeature extends FeatureWithDefaultOnFailure[PipelineQuery, Boolean] {
  override def defaultValue: Boolean = false
}

@Singleton
class HydraRankingPreparationQueryFeatureHydrator @Inject() (
  hydraRootService: t.HydraRoot.MethodPerEndpoint)
    extends QueryFeatureHydrator[PipelineQuery]
    with Conditionally[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("HydraRootService")

  override val features: Set[Feature[_, _]] = Set(HydraRankingPreparedFeature)

  private val succeededFeatureMap = FeatureMapBuilder()
    .add(HydraRankingPreparedFeature, true)
    .build()

  private val failedFeatureMap = FeatureMapBuilder()
    .add(HydraRankingPreparedFeature, false)
    .build()

  override def onlyIf(query: PipelineQuery): Boolean = {
    // Using same param as side effect for making sure, both ranking and preparation happens for same request
    query.params(EnableHydraScoringSideEffect) || query.params(HydraScoringPipelineEnabled)
  }

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    val userId = query.getRequiredUserId
    val clientContext = query.clientContext
    val modelName = query.params(HydraModelName)
    if (query.params(HydraScoringPipelineEnabled)) {
      syncHydraCall(userId, clientContext, modelName)
    } else if (query.params(EnableHydraScoringSideEffect)) {
      asyncHydraCall(userId, clientContext, modelName)
    } else {
      Stitch.value(failedFeatureMap) // Should never reach here as onlyIf will gate
    }
  }

  private def asyncHydraCall(
    userId: Long,
    clientContext: ClientContext,
    modelName: String
  ): Stitch[FeatureMap] = Stitch
    .value(succeededFeatureMap)
    .applyEffect { _ =>
      Stitch.async { getHydraResponse(userId, clientContext, modelName) }
    }

  private def syncHydraCall(
    userId: Long,
    clientContext: ClientContext,
    modelName: String
  ): Stitch[FeatureMap] = getHydraResponse(userId, clientContext, modelName).map {
    case HydraRootRecommendationResponse.HydraRootTweetRankingPreparationResponse(_) =>
      succeededFeatureMap
    case _ => failedFeatureMap
  }

  private def getHydraResponse(
    userId: Long,
    clientContext: ClientContext,
    modelName: String
  ): Stitch[HydraRootRecommendationResponse] = {
    val hydraRequest = t.HydraRootRequest(
      clientContext = ClientContextMarshaller(clientContext),
      product = t.Product.TweetRankingPreparation,
      productContext =
        Some(t.ProductContext.TweetRankingPreparation(t.TweetRankingPreparation(modelName))),
    )

    Stitch.callFuture(hydraRootService.getRecommendationResponse(hydraRequest))
  }
}
