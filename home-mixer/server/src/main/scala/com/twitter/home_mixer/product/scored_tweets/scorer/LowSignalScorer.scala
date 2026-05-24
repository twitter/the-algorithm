package com.twitter.home_mixer.product.scored_tweets.scorer

import com.twitter.home_mixer.model.HomeFeatures.AuthorIdFeature
import com.twitter.home_mixer.model.HomeFeatures.ScoreFeature
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.scorer.Scorer
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.model.common.identifier.ScorerIdentifier
import com.twitter.product_mixer.core.model.common.presentation.CandidatePipelines
import com.twitter.product_mixer.core.model.common.presentation.CandidateSourcePosition
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import scala.collection.immutable.ListSet

/**
 * Assign scores based on candidate source positions, blending candidates from different sources
 */
object LowSignalScorer extends Scorer[PipelineQuery, TweetCandidate] {

  override val identifier: ScorerIdentifier = ScorerIdentifier("LowSignal")

  override val features: Set[Feature[_, _]] = Set(ScoreFeature)

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = {
    val candidatesByPipeline = candidates.groupBy {
      _.features.getOrElse(CandidatePipelines, ListSet.empty[CandidatePipelineIdentifier]).head
    }

    val candidateScoreMap = candidatesByPipeline
      .map {
        case (_, pipelineCandidates) =>
          val sortedCandidates = pipelineCandidates.sortBy(_.features.get(CandidateSourcePosition))
          deduplicateAuthors(sortedCandidates).zipWithIndex.map {
            case (candidate, index) => candidate.candidate.id -> index.toDouble
          }
      }.toSeq.flatten.toMap

    val maxScore = candidates.size.toDouble
    val updatedScores = candidates.map { candidate =>
      val score = maxScore - candidateScoreMap.getOrElse(candidate.candidate.id, maxScore)
      FeatureMap(ScoreFeature, Some(score))
    }
    Stitch.value(updatedScores)
  }

  def deduplicateAuthors(
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Seq[CandidateWithFeatures[TweetCandidate]] = {
    val seenAuthors = scala.collection.mutable.Set[Long]()
    candidates.collect {
      case c if seenAuthors.add(c.features.getOrElse(AuthorIdFeature, None).getOrElse(0L)) => c
    }
  }
}
