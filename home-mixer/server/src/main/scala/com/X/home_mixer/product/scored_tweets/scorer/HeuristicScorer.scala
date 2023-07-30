package com.X.home_mixer.product.scored_tweets.scorer

import com.X.home_mixer.model.HomeFeatures.ScoreFeature
import com.X.home_mixer.product.scored_tweets.model.ScoredTweetsQuery
import com.X.product_mixer.component_library.model.candidate.TweetCandidate
import com.X.product_mixer.core.feature.Feature
import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.X.product_mixer.core.functional_component.scorer.Scorer
import com.X.product_mixer.core.model.common.CandidateWithFeatures
import com.X.product_mixer.core.model.common.identifier.ScorerIdentifier
import com.X.stitch.Stitch

/**
 * Apply various heuristics to the model score
 */
object HeuristicScorer extends Scorer[ScoredTweetsQuery, TweetCandidate] {

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
