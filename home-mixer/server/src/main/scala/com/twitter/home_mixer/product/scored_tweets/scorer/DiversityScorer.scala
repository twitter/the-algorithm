package com.twitter.home_mixer.product.scored_tweets.scorer

import com.twitter.home_mixer.model.HomeFeatures.ScoreFeature
import com.twitter.home_mixer.product.scored_tweets.model.ScoredTweetsQuery
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.scorer.Scorer
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.ScorerIdentifier
import com.twitter.stitch.Stitch

/**
 * Discounts scores of each consecutive tweet (ordered by score desc) from the
 * same entity (e.g. author, engager, topic) based on the discount factor provided
 */

case class DiversityScorer(diversityDiscountProvider: DiversityDiscountProvider)
    extends Scorer[ScoredTweetsQuery, TweetCandidate] {

  override val identifier: ScorerIdentifier = ScorerIdentifier("Diversity")

  override val features: Set[Feature[_, _]] = Set(ScoreFeature)

  override def apply(
    query: ScoredTweetsQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = {
    val candidateIdScoreMap = candidates
      .groupBy(diversityDiscountProvider.entityId)
      .flatMap {
        case (entityIdOpt, entityCandidates) =>
          val candidateScores = entityCandidates
            .map { candidate =>
              val score = candidate.features.getOrElse(ScoreFeature, None).getOrElse(0.0)
              (candidate.candidate.id, score)
            }.sortBy(_._2)(Ordering.Double.reverse)

          if (entityIdOpt.isDefined) {
            candidateScores.zipWithIndex.map {
              case ((candidateId, score), index) =>
                candidateId -> diversityDiscountProvider.discount(score, index)
            }
          } else candidateScores
      }

    Stitch.value {
      candidates.map { candidate =>
        val score = candidateIdScoreMap.getOrElse(candidate.candidate.id, 0.0)
        FeatureMapBuilder()
          .add(ScoreFeature, Some(score))
          .build()
      }
    }
  }
}
