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
 * Scales scores of tweets whose author is Blue Verified by the provided scale factor
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
        val updatedScore = getUpdatedScore(score, candidate, query)
        FeatureMapBuilder().add(ScoreFeature, updatedScore).build()
      }
    }
  }

  /**
   * We should only be applying this multiplier if the author of the candidate is Blue Verified.
   * We also treat In-Network vs Out-of-Network differently.
   */
  private def getUpdatedScore(
    score: Option[Double],
    candidate: CandidateWithFeatures[TweetCandidate],
    query: PipelineQuery
  ): Option[Double] = {
    val isAuthorBlueVerified = candidate.features.getOrElse(AuthorIsBlueVerifiedFeature, false)

    if (isAuthorBlueVerified) {
      val isCandidateInNetwork = candidate.features.getOrElse(InNetworkFeature, false)

      val scaleFactor =
        if (isCandidateInNetwork) query.params(BlueVerifiedAuthorInNetworkMultiplierParam)
        else query.params(BlueVerifiedAuthorOutOfNetworkMultiplierParam)

      score.map(_ * scaleFactor)
    } else score
  }
}
