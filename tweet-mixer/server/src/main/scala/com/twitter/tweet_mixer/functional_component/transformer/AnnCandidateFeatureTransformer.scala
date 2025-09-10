package com.twitter.tweet_mixer.functional_component.transformer

import com.twitter.ann.common.CosineDistance
import com.twitter.ann.common.NeighborWithDistanceWithSeed
import com.twitter.tweet_mixer.feature.FromInNetworkSourceFeature
import com.twitter.tweet_mixer.feature.ScoreFeature
import com.twitter.tweet_mixer.feature.SourceSignalFeature
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.model.common.identifier.TransformerIdentifier

object AnnCandidateFeatureTransformer
    extends CandidateFeatureTransformer[NeighborWithDistanceWithSeed[Long, Long, CosineDistance]] {
  override def features: Set[Feature[_, _]] =
    Set(ScoreFeature, SourceSignalFeature, FromInNetworkSourceFeature)

  override val identifier: TransformerIdentifier = TransformerIdentifier("AnnCandidate")

  override def transform(
    input: NeighborWithDistanceWithSeed[Long, Long, CosineDistance]
  ): FeatureMap =
    FeatureMap(
      SourceSignalFeature,
      input.seed,
      ScoreFeature,
      input.distance.distance.toDouble,
      FromInNetworkSourceFeature,
      false
    )
}
