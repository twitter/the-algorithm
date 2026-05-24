package com.twitter.home_mixer.product.scored_tweets.response_transformer

import com.twitter.home_mixer.model.HomeFeatures.DebugStringFeature
import com.twitter.home_mixer.model.HomeFeatures.ServedTypeFeature
import com.twitter.home_mixer.product.scored_tweets.candidate_source.ContentExplorationCandidateResponse
import com.twitter.home_mixer.{thriftscala => hmt}
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.model.common.identifier.TransformerIdentifier

object ScoredTweetsContentExplorationResponseFeatureTransformer
    extends CandidateFeatureTransformer[ContentExplorationCandidateResponse] {

  override val identifier: TransformerIdentifier =
    TransformerIdentifier("ScoredTweetsContentExploration")

  override val features: Set[Feature[_, _]] = Set(
    ServedTypeFeature,
    DebugStringFeature
  )

  override def transform(candidate: ContentExplorationCandidateResponse): FeatureMap = {
    val servedType = candidate.tier match {
      case "tier1" => hmt.ServedType.ForYouContentExploration
      case "tier2" => hmt.ServedType.ForYouContentExplorationTier2
      case _ => hmt.ServedType.ForYouContentExploration
    }
    FeatureMapBuilder()
      .add(ServedTypeFeature, servedType)
      .add(DebugStringFeature, Some(s"Content Exploration: ${candidate.tier}"))
      .build()
  }
}
