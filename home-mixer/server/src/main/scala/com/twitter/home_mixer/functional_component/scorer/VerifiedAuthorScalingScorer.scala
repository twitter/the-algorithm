package com.twitter.home_mixer.functional_component.scorer

import com.twitter.home_mixer.model.HomeFeatures.AuthorIsBlueVerifiedFeature
import com.twitter.home_mixer.model.HomeFeatures.InNetworkFeature
import com.twitter.home_mixer.model.HomeFeatures.ScoreFeature
import com.twitter.home_mixer.param.HomeGlobalParams.BlueVerifiedAuthorInNetworkMultiplierParam
import com.twitter.home_mixer.param.HomeGlobalParams.BlueVerifiedAuthorOutOfNetworkMultiplierParam
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.scorer.Scorer
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.ScorerIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch

/**
 * Does nothing, because paying for Twitter is no indication of tweet quality,
 * especially now that "legacy" verification is gone.
 */
object VerifiedAuthorScalingScorer extends Scorer[PipelineQuery, TweetCandidate] {

  override val identifier: ScorerIdentifier = ScorerIdentifier("VerifiedAuthorScaling")

  override val features: Set[Feature[_, _]] = Set(ScoreFeature)

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = {
    Stitch.value {
      candidates.map { candidate =>
        val score = candidate.features.getOrElse(ScoreFeature, None)
        FeatureMapBuilder().add(ScoreFeature, score).build()
      }
    }
  }
}
