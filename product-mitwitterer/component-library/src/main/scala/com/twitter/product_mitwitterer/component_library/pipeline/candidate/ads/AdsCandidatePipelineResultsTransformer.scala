package com.twitter.product_mitwitterer.component_library.pipeline.candidate.ads

import com.twitter.adserver.thriftscala.AdImpression
import com.twitter.product_mitwitterer.component_library.model.candidate.ads.AdsCandidate
import com.twitter.product_mitwitterer.component_library.model.candidate.ads.AdsTweetCandidate
import com.twitter.product_mitwitterer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.PipelineFailure
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.UnetwitterpectedCandidateResult

object AdsCandidatePipelineResultsTransformer
    etwittertends CandidatePipelineResultsTransformer[AdImpression, AdsCandidate] {

  override def transform(sourceResult: AdImpression): AdsCandidate =
    (sourceResult.nativeRtbCreative, sourceResult.promotedTweetId) match {
      case (None, Some(promotedTweetId)) =>
        AdsTweetCandidate(
          id = promotedTweetId,
          adImpression = sourceResult
        )
      case (Some(_), None) =>
        throw unsupportedAdImpressionPipelineFailure(
          impression = sourceResult,
          reason = "Received ad impression with rtbCreative")
      case (Some(_), Some(_)) =>
        throw unsupportedAdImpressionPipelineFailure(
          impression = sourceResult,
          reason = "Received ad impression with both rtbCreative and promoted tweetId")
      case (None, None) =>
        throw unsupportedAdImpressionPipelineFailure(
          impression = sourceResult,
          reason = "Received ad impression with neither rtbCreative nor promoted tweetId")
    }

  private def unsupportedAdImpressionPipelineFailure(impression: AdImpression, reason: String) =
    PipelineFailure(
      UnetwitterpectedCandidateResult,
      reason =
        s"Unsupported AdImpression ($reason). impressionString: ${impression.impressionString}")
}
