package com.ExTwitter.cr_mixer.util

import com.ExTwitter.cr_mixer.model.CandidateGenerationInfo
import com.ExTwitter.cr_mixer.model.SourceInfo
import com.ExTwitter.cr_mixer.thriftscala.CandidateGenerationKey
import com.ExTwitter.cr_mixer.thriftscala.SimilarityEngine
import com.ExTwitter.cr_mixer.thriftscala.SourceType
import com.ExTwitter.simclusters_v2.common.UserId
import com.ExTwitter.simclusters_v2.thriftscala.InternalId
import com.ExTwitter.util.Time

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
