package com.X.home_mixer.product.for_you.side_effect

import com.X.home_mixer.model.HomeFeatures.IsReadFromCacheFeature
import com.X.home_mixer.model.HomeFeatures.PredictionRequestIdFeature
import com.X.home_mixer.model.HomeFeatures.ServedIdFeature
import com.X.home_mixer.model.HomeFeatures.ServedRequestIdFeature
import com.X.home_mixer.model.HomeFeatures.StreamToKafkaFeature
import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.X.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.X.product_mixer.core.model.common.presentation.ItemCandidateWithDetails
import com.X.product_mixer.core.model.common.presentation.ModuleCandidateWithDetails
import com.X.product_mixer.core.pipeline.PipelineQuery

object ServedCandidateKafkaSideEffect {

  def extractCandidates(
    query: PipelineQuery,
    selectedCandidates: Seq[CandidateWithDetails],
    sourceIdentifiers: Set[CandidatePipelineIdentifier]
  ): Seq[ItemCandidateWithDetails] = {
    val servedRequestIdOpt =
      query.features.getOrElse(FeatureMap.empty).getOrElse(ServedRequestIdFeature, None)

    selectedCandidates.iterator
      .filter(candidate => sourceIdentifiers.contains(candidate.source))
      .flatMap {
        case item: ItemCandidateWithDetails => Seq(item)
        case module: ModuleCandidateWithDetails => module.candidates
      }
      .filter(candidate => candidate.features.getOrElse(StreamToKafkaFeature, false))
      .map { candidate =>
        val servedId =
          if (candidate.features.getOrElse(IsReadFromCacheFeature, false) &&
            servedRequestIdOpt.nonEmpty)
            servedRequestIdOpt
          else
            candidate.features.getOrElse(PredictionRequestIdFeature, None)

        candidate.copy(features = candidate.features + (ServedIdFeature, servedId))
      }.toSeq
      // deduplicate by (tweetId, userId, servedId)
      .groupBy { candidate =>
        (
          candidate.candidateIdLong,
          query.getRequiredUserId,
          candidate.features.getOrElse(ServedIdFeature, None))
      }.values.map(_.head).toSeq
  }
}
