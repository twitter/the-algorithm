package com.twitter.home_mixer.functional_component.feature_hydrator.adapters.light_ranking_features

import com.twitter.home_mixer.thriftscala.ServedType
import com.twitter.ml.api.Feature
import com.twitter.ml.api.FeatureContext
import com.twitter.ml.api.RichDataRecord
import com.twitter.timelines.prediction.common.adapters.TimelinesMutatingAdapterBase
import com.twitter.timelines.prediction.features.common.TimelinesSharedFeatures
import java.lang.{Boolean => JBoolean}
import java.lang.{Long => JLong}
import java.lang.{String => JString}

case class LightRankingCandidateFeatures(
  isSelected: Boolean,
  isSelectedByHeavyRanker: Boolean,
  rankByHeavyRanker: Int,
  servedType: ServedType,
  candidateSourcePosition: Long,
)

/**
 * define light ranking features adapter to create a data record which includes many light ranking features
 * e.g. predictionRequestId, userId, tweetId to be used as joined key in batch pipeline.
 */
object LightRankingCandidateFeaturesAdapter
    extends TimelinesMutatingAdapterBase[LightRankingCandidateFeatures] {

  val featureContext = new FeatureContext(
    TimelinesSharedFeatures.IS_SELECTED,
    TimelinesSharedFeatures.IS_SELECTED_BY_HEAVY_RANKER,
    TimelinesSharedFeatures.RANK_BY_HEAVY_RANKER,
    TimelinesSharedFeatures.SERVED_TYPE_ID,
    TimelinesSharedFeatures.SERVED_TYPE,
    TimelinesSharedFeatures.CANDIDATE_SOURCE_POSITION,
  )

  override def getFeatureContext: FeatureContext = featureContext

  override val commonFeatures: Set[Feature[_]] = Set.empty

  override def setFeatures(
    lightRankingCandidateFeatures: LightRankingCandidateFeatures,
    richDataRecord: RichDataRecord
  ): Unit = {
    richDataRecord.setFeatureValue[JBoolean](
      TimelinesSharedFeatures.IS_SELECTED,
      lightRankingCandidateFeatures.isSelected)
    richDataRecord.setFeatureValue[JBoolean](
      TimelinesSharedFeatures.IS_SELECTED_BY_HEAVY_RANKER,
      lightRankingCandidateFeatures.isSelectedByHeavyRanker)
    richDataRecord.setFeatureValue[JLong](
      TimelinesSharedFeatures.RANK_BY_HEAVY_RANKER,
      lightRankingCandidateFeatures.rankByHeavyRanker)
    richDataRecord.setFeatureValue[JString](
      TimelinesSharedFeatures.SERVED_TYPE,
      lightRankingCandidateFeatures.servedType.name)
    richDataRecord.setFeatureValue[JLong](
      TimelinesSharedFeatures.SERVED_TYPE_ID,
      lightRankingCandidateFeatures.servedType.getValue())
    richDataRecord.setFeatureValue[JLong](
      TimelinesSharedFeatures.CANDIDATE_SOURCE_POSITION,
      lightRankingCandidateFeatures.candidateSourcePosition)
  }
}
