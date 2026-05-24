package com.twitter.home_mixer.functional_component.filter

import com.twitter.home_mixer.functional_component.feature_hydrator.adapters.transformer_embeddings.UserHistoryEventsFeatures
import com.twitter.home_mixer.functional_component.feature_hydrator.adapters.transformer_embeddings.VideoUserHistoryEventsFeatures
import com.twitter.home_mixer.functional_component.feature_hydrator.user_history.UserHistoryEventsFeature
import com.twitter.home_mixer.model.HomeFeatures.DedupClusterId88Feature
import com.twitter.home_mixer.model.HomeFeatures.DedupClusterIdFeature
import com.twitter.home_mixer.param.HomeGlobalParams.DedupHistoricalEventsTimeWindowParam
import com.twitter.home_mixer.param.HomeGlobalParams.EnableClusterBased88DedupFilter
import com.twitter.home_mixer.param.HomeGlobalParams.EnableClusterBasedDedupFilter
import com.twitter.home_mixer.param.HomeGlobalParams.EnableNoClusterFilter
import scala.collection.convert.ImplicitConversions._
import com.twitter.ml.api.DataRecord
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.filter.FilterResult
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
object ClusterBasedDedupFilter
    extends Filter[PipelineQuery, TweetCandidate]
    with Filter.Conditionally[PipelineQuery, TweetCandidate] {

  override val identifier: FilterIdentifier = FilterIdentifier("ClusterBasedDedup")

  override def onlyIf(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Boolean = {
    query.params(EnableClusterBasedDedupFilter)
  }

  private def getViewedClusterIds(query: PipelineQuery): Set[Long] = {
    val currentTimeMs = System.currentTimeMillis()
    val dedupHistoricalEventsTimeWindow = query.params(DedupHistoricalEventsTimeWindowParam)

    val dataRecord = query.features.get
      .getOrElse(UserHistoryEventsFeature, new DataRecord())

    val clusterIds = dataRecord.getTensors match {
      case tensors if tensors != null =>
        val clusterIdsTensor =
          tensors.get(VideoUserHistoryEventsFeatures.VideoCluster95IdsFeature.getFeatureId)
        val actionTimestampTensor =
          tensors.get(UserHistoryEventsFeatures.ActionTimestampsFeature.getFeatureId)

        if (clusterIdsTensor != null && clusterIdsTensor.isSet() &&
          actionTimestampTensor != null && actionTimestampTensor.isSet()) {
          val actionTimestampsBuffer = actionTimestampTensor.getInt64Tensor.longs
            .map(_.longValue())
          val clusterIdsBuffer = clusterIdsTensor.getInt64Tensor.longs.map(_.longValue())

          clusterIdsBuffer
            .zip(actionTimestampsBuffer)
            .collect {
              case (clusterId, timestamp)
                  if timestamp > 0 && (currentTimeMs - timestamp) <= dedupHistoricalEventsTimeWindow =>
                clusterId
            }
            .toSet
        } else {
          Set.empty[Long]
        }
      case _ =>
        Set.empty[Long]
    }
    clusterIds
  }

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[FilterResult[TweetCandidate]] = {

    val historicalClusters = getViewedClusterIds(query) // Still based on 0.95 threshold
    val enable88Dedup = query.params(EnableClusterBased88DedupFilter)
    val enableNoClusterFilter = query.params(EnableNoClusterFilter)

    val (kept, removed) = candidates.foldLeft(
      (
        Seq[CandidateWithFeatures[TweetCandidate]](), // kept accumulator
        Seq[CandidateWithFeatures[TweetCandidate]](), // removed accumulator
        Set[Long](), // seen cluster IDs for 0.95 threshold
        Set[Long]() // seen cluster IDs for 0.88 threshold
      )
    ) {
      case ((keptAcc, removedAcc, seenCluster95Ids, seenCluster88Ids), candidate) =>
        val clusterId95 = candidate.features
          .getOrElse(DedupClusterIdFeature, None) // 0.95 threshold
        val clusterId88 = candidate.features
          .getOrElse(DedupClusterId88Feature, None) // 0.88 threshold

        (clusterId95, clusterId88) match {
          case (Some(c95), _) if historicalClusters.contains(c95) =>
            // Historical match at 0.95 threshold, remove it
            (keptAcc, removedAcc :+ candidate, seenCluster95Ids, seenCluster88Ids)
          case (Some(c95), _) if seenCluster95Ids.contains(c95) =>
            // Duplicate at 0.95 threshold (non-historical), remove it
            (keptAcc, removedAcc :+ candidate, seenCluster95Ids, seenCluster88Ids)
          case (Some(c95), Some(c88)) if enable88Dedup && seenCluster88Ids.contains(c88) =>
            // Unique at 0.95 but duplicate at 0.88 threshold, remove it
            (keptAcc, removedAcc :+ candidate, seenCluster95Ids + c95, seenCluster88Ids)
          case (Some(c95), Some(c88)) =>
            // First occurrence at both levels, keep it
            val updatedSeen88Ids = if (enable88Dedup) seenCluster88Ids + c88 else seenCluster88Ids
            (keptAcc :+ candidate, removedAcc, seenCluster95Ids + c95, updatedSeen88Ids)
          case (Some(c95), None) =>
            // Only 0.95 cluster ID, keep it if not seen
            (keptAcc :+ candidate, removedAcc, seenCluster95Ids + c95, seenCluster88Ids)
          case (None, Some(c88)) if enable88Dedup && seenCluster88Ids.contains(c88) =>
            // Only 0.88 cluster ID, remove if duplicate
            (keptAcc, removedAcc :+ candidate, seenCluster95Ids, seenCluster88Ids)
          case (None, Some(c88)) =>
            // Only 0.88 cluster ID, keep if not seen
            val updatedSeen88Ids = if (enable88Dedup) seenCluster88Ids + c88 else seenCluster88Ids
            (keptAcc :+ candidate, removedAcc, seenCluster95Ids, updatedSeen88Ids)
          case (None, None) =>
            // No cluster IDs, keep or remove based on enableNoClusterFilter
            if (enableNoClusterFilter) {
              (keptAcc, removedAcc :+ candidate, seenCluster95Ids, seenCluster88Ids) // Remove it
            } else {
              (keptAcc :+ candidate, removedAcc, seenCluster95Ids, seenCluster88Ids) // Keep it
            }
        }
    } match {
      case (keptCandidates, removedCandidates, _, _) =>
        (keptCandidates, removedCandidates)
    }

    Stitch.value(
      FilterResult(
        kept = kept.map(_.candidate),
        removed = removed.map(_.candidate)
      )
    )
  }
}
