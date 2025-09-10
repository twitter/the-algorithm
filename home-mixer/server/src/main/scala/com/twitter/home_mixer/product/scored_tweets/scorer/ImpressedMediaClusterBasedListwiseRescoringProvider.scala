package com.twitter.home_mixer.product.scored_tweets.scorer

import com.twitter.finagle.stats.DefaultStatsReceiver
import com.twitter.home_mixer.functional_component.feature_hydrator.ImpressedMediaClusterIds
import com.twitter.home_mixer.model.HomeFeatures.TweetMediaClusterIdsFeature
import com.twitter.home_mixer.param.HomeGlobalParams.Scoring.ImpressedMediaClusterBasedRescoringParam
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.FeatureHydration.EnableMediaClusterDecayParam
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.FeatureHydration.EnableMediaClusterFeatureHydrationParam
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.pipeline.PipelineQuery

object ImpressedMediaClusterBasedListwiseRescoringProvider {

  /**
   * Impressed Media cluster-Id based
   * Rescore the list of candidates that have previously impressed mediaCluster Ids.
   */
  private val impressedMediaStats =
    DefaultStatsReceiver.scope("ImpressedMediaClusterBasedListwiseRescoringProvider")
  private val percentRescoredCandidatesStat =
    impressedMediaStats.stat("percent_rescored_candidates_x10")

  private def impressedMediaClusterIds(query: PipelineQuery) =
    query.features.map(_.getOrElse(ImpressedMediaClusterIds, Seq[Long]())).getOrElse(Seq[Long]())

  private def mediaClusterId(candidate: CandidateWithFeatures[TweetCandidate]) =
    candidate.features.getOrElse(TweetMediaClusterIdsFeature, Map[Long, Long]()).values.toSeq

  private def rescoringFactor(
    clusterIds: Seq[Long],
    impressedMediaClusterIdsToCountMap: Map[Long, Int],
    decayFactor: Double
  ): Double = {
    val decayFactors = clusterIds.map { clusterId =>
      impressedMediaClusterIdsToCountMap.get(clusterId) match {
        case Some(count) => math.pow(1.0 - decayFactor, count)
        case None => 1.0
      }
    }
    decayFactors.product
  }

  private def onlyIf(query: PipelineQuery): Boolean = {
    (query.params(ImpressedMediaClusterBasedRescoringParam) > 0.0) &&
    query.params(EnableMediaClusterDecayParam) &&
    query.params(EnableMediaClusterFeatureHydrationParam)
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

    val impressedMediaClusterIdsToCountMap = impressedMediaClusterIds(query)
      .groupBy(identity)
      .mapValues(_.size)

    val decayFactor = query.params(ImpressedMediaClusterBasedRescoringParam)

    val rescoringFactors = candidates.map { candidate =>
      val mediaClusterIds = mediaClusterId(candidate)
      val rescoreFactor =
        rescoringFactor(mediaClusterIds, impressedMediaClusterIdsToCountMap, decayFactor)
      candidate.candidate.id -> rescoreFactor
    }.toMap

    // Update Rescored candidates stats by number of candidates with rescoreFactor != 1
    percentRescoredCandidatesStat.add(
      (rescoringFactors.count(_._2 != 1.0) * 1000.0 / (candidates.size + 0.01)).toFloat)

    rescoringFactors
  }
}
