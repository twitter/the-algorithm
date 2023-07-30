package com.X.cr_mixer.util

import com.X.cr_mixer.model.CandidateGenerationInfo
import com.X.cr_mixer.model.SourceInfo
import com.X.cr_mixer.thriftscala.CandidateGenerationKey
import com.X.cr_mixer.thriftscala.SimilarityEngine
import com.X.cr_mixer.thriftscala.SourceType
import com.X.simclusters_v2.common.UserId
import com.X.simclusters_v2.thriftscala.InternalId
import com.X.util.Time

object CandidateGenerationKeyUtil {
  private val PlaceholderUserId = 0L // this default value will not be used

  private val DefaultSourceInfo: SourceInfo = SourceInfo(
    sourceType = SourceType.RequestUserId,
    sourceEventTime = None,
    internalId = InternalId.UserId(PlaceholderUserId)
  )

  def toThrift(
    candidateGenerationInfo: CandidateGenerationInfo,
    requestUserId: UserId
  ): CandidateGenerationKey = {
    CandidateGenerationKey(
      sourceType = candidateGenerationInfo.sourceInfoOpt.getOrElse(DefaultSourceInfo).sourceType,
      sourceEventTime = candidateGenerationInfo.sourceInfoOpt
        .getOrElse(DefaultSourceInfo).sourceEventTime.getOrElse(Time.fromMilliseconds(0L)).inMillis,
      id = candidateGenerationInfo.sourceInfoOpt
        .map(_.internalId).getOrElse(InternalId.UserId(requestUserId)),
      modelId = candidateGenerationInfo.similarityEngineInfo.modelId.getOrElse(""),
      similarityEngineType =
        Some(candidateGenerationInfo.similarityEngineInfo.similarityEngineType),
      contributingSimilarityEngine =
        Some(candidateGenerationInfo.contributingSimilarityEngines.map(se =>
          SimilarityEngine(se.similarityEngineType, se.modelId, se.score)))
    )
  }
}
