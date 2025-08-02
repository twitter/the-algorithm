package com.twitter.home_mitwitterer.product.scored_tweets.scorer

import com.twitter.home_mitwitterer.model.HomeFeatures.ScoreFeature
import com.twitter.home_mitwitterer.product.scored_tweets.model.ScoredTweetsQuery
import com.twitter.product_mitwitterer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mitwitterer.core.functional_component.scorer.Scorer
import com.twitter.product_mitwitterer.core.model.common.CandidateWithFeatures
import com.twitter.product_mitwitterer.core.model.common.identifier.ScorerIdentifier
import com.twitter.stitch.Stitch

/**
 * Apply various heuristics to the model score
 */
object HeuristicScorer etwittertends Scorer[ScoredTweetsQuery, TweetCandidate] {

  override val identifier: ScorerIdentifier = ScorerIdentifier("Heuristic")

  override val features: Set[Feature[_, _]] = Set(ScoreFeature)

  override def apply(
    query: ScoredTweetsQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = {
    val rescorers = Seq(
      RescoreOutOfNetwork,
      RescoreReplies,
      RescoreBlueVerified,
      RescoreCreators,
      RescoreMTLNormalization,
      RescoreAuthorDiversity(AuthorDiversityDiscountProvider(candidates)),
      RescoreFeedbackFatigue(query)
    )

    val updatedScores = candidates.map { candidate =>
      val score = candidate.features.getOrElse(ScoreFeature, None)
      val scaleFactor = rescorers.map(_(query, candidate)).product
      val updatedScore = score.map(_ * scaleFactor)
      FeatureMapBuilder().add(ScoreFeature, updatedScore).build()
    }

    Stitch.value(updatedScores)
  }
}
