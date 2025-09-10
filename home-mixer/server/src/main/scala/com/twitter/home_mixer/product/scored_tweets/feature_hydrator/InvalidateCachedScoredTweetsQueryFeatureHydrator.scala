package com.twitter.home_mixer.product.scored_tweets.feature_hydrator

import com.twitter.home_mixer.model.HomeFeatures.CachedScoredTweetsFeature
import com.twitter.home_mixer.model.HomeFeatures.LastNonPollingTimeFeature
import com.twitter.home_mixer.model.HomeFeatures.UserLastExplicitSignalTimeFeature
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch

/**
 * Invalidates (flushes) cached ScoredTweets upon detecting explicit signal from user made since
 * previous request
 */
object InvalidateCachedScoredTweetsQueryFeatureHydrator
    extends QueryFeatureHydrator[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier(
    "InvalidateCachedScoredTweets")

  override val features: Set[Feature[_, _]] = Set(CachedScoredTweetsFeature)

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    val lastRequestTime =
      query.features.getOrElse(FeatureMap.empty).getOrElse(LastNonPollingTimeFeature, None)
    val lastExplicitSignalTime =
      query.features
        .getOrElse(FeatureMap.empty).getOrElse(UserLastExplicitSignalTimeFeature, None)
    val hasRecentExplicitSignal = lastExplicitSignalTime
      .flatMap { engagementTime =>
        lastRequestTime.map { requestTime => engagementTime > requestTime }
      }.getOrElse(false)

    val featureMap = if (hasRecentExplicitSignal) {
      FeatureMap(CachedScoredTweetsFeature, Seq.empty)
    } else {
      val cachedTweets =
        query.features.getOrElse(FeatureMap.empty).getOrElse(CachedScoredTweetsFeature, Seq.empty)
      FeatureMap(CachedScoredTweetsFeature, cachedTweets)
    }

    Stitch.value(featureMap)
  }
}
