package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.home_mixer.functional_component.decorator.builder.HomeTweetTypePredicates
import com.twitter.home_mixer.model.HomeFeatures.TweetTypeMetricsFeature
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.CandidateFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.suggests.controller_data.Home

object TweetTypeMetricsFeatureHydrator
    extends CandidateFeatureHydrator[PipelineQuery, TweetCandidate] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("TweetTypeMetrics")

  override val features: Set[Feature[_, _]] = Set(TweetTypeMetricsFeature)

  override def apply(
    query: PipelineQuery,
    candidate: TweetCandidate,
    existingFeatures: FeatureMap
  ): Stitch[FeatureMap] = {
    // Tweet type metrics are already available for cached tweets and shouldn't be overwritten
    val tweetTypeMetricsFeature = existingFeatures.getOrElse(TweetTypeMetricsFeature, None)
    val queryFeatures = query.features.getOrElse(FeatureMap.empty)

    val tweetTypesByteList = if (tweetTypeMetricsFeature.isEmpty) {
      val bitset = new java.util.BitSet()

      val trueTweetTypes = HomeTweetTypePredicates.PredicateMap.collect {
        // Not combining query and candidate features to reduce cost, instead running predicate separately
        case (predicateName, predicate)
            if (predicate(existingFeatures) | predicate(queryFeatures)) =>
          predicateName
      }.toSet

      Home.TweetTypeIdxMap.collect {
        case (tweetType, index) if trueTweetTypes.contains(tweetType) => bitset.set(index)
      }

      Some(bitset.toByteArray.toList)
    } else tweetTypeMetricsFeature

    Stitch.value(FeatureMapBuilder().add(TweetTypeMetricsFeature, tweetTypesByteList).build())
  }
}
