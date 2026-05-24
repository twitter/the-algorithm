package com.twitter.home_mixer.product.scored_tweets.scorer

import com.twitter.finagle.stats.DefaultStatsReceiver
import com.twitter.home_mixer.functional_component.feature_hydrator.ImpressedImageClusterIds
import com.twitter.home_mixer.model.HomeFeatures.ClipImageClusterIdsFeature
import com.twitter.home_mixer.param.HomeGlobalParams.Scoring.ImpressedImageClusterBasedRescoringParam
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.FeatureHydration.EnableImageClusterDecayParam
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.FeatureHydration.EnableImageClusterFeatureHydrationParam
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.pipeline.PipelineQuery

object ImpressedImageClusterBasedListwiseRescoringProvider {

  /**
   * Impressed Image cluster-Id based
   * Rescore the list of candidates that have previously impressed imageCluster Ids.
   */
  private val impressedImageStats =
    DefaultStatsReceiver.scope("ImpressedImageClusterBasedListwiseRescoringProvider")
  private val percentRescoredCandidatesStat =
    impressedImageStats.stat("percent_rescored_candidates_x10")

  private def impressedImageClusterIds(query: PipelineQuery) =
    query.features.map(_.getOrElse(ImpressedImageClusterIds, Seq[Long]())).getOrElse(Seq[Long]())

  private def imageClusterId(
    candidate: CandidateWithFeatures[TweetCandidate]
  ) = candidate.features.getOrElse(ClipImageClusterIdsFeature, Map[Long, Long]()).values.toSeq

  private def rescoringFactor(
    clusterIds: Seq[Long],
    impressedImageClusterIdsToCountMap: Map[Long, Int],
    decayFactor: Double
  ): Double = {
    val decayFactors = clusterIds.map { clusterId =>
      impressedImageClusterIdsToCountMap.get(clusterId) match {
        case Some(count) => math.pow(1.0 - decayFactor, count)
        case None => 1.0
      }
    }
    decayFactors.product
  }

  private def onlyIf(query: PipelineQuery): Boolean = {
    (query.params(ImpressedImageClusterBasedRescoringParam) > 0.0) &&
    query.params(EnableImageClusterDecayParam) &&
    query.params(EnableImageClusterFeatureHydrationParam)
  }

  def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Map[Long, Double] = {
    if (!onlyIf(query)) {
      return candidates.map { candidate =>
        candidate.candidate.id -> 1.0
      }.toMap
    }

    val impressedImageClusterIdsToCountMap = impressedImageClusterIds(query)
      .groupBy(identity)
      .mapValues(_.size)

    val decayFactor = query.params(ImpressedImageClusterBasedRescoringParam)

    val rescoringFactors = candidates.map { candidate =>
      val imageClusterIds = imageClusterId(candidate)
      val rescoreFactor =
        rescoringFactor(imageClusterIds, impressedImageClusterIdsToCountMap, decayFactor)
      candidate.candidate.id -> rescoreFactor
    }.toMap

    // Update Rescored candidates stats by number of candidates with rescoreFactor != 1
    percentRescoredCandidatesStat.add(
      (rescoringFactors.count(_._2 != 1.0) * 1000.0 / (candidates.size + 0.01)).toFloat)

    rescoringFactors
  }
}
