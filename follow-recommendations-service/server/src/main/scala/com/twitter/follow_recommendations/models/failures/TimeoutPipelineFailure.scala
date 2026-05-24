package com.twitter.follow_recommendations.models.failures

import com.twitter.product_mixer.core.pipeline.pipeline_failure.CandidateSourceTimeout
import com.twitter.product_mixer.core.pipeline.pipeline_failure.PipelineFailure

object TimeoutPipelineFailure {
  def apply(candidateSourceName: String): PipelineFailure = {
    PipelineFailure(
      CandidateSourceTimeout,
      s"Candidate Source $candidateSourceName timed out before returning candidates")
  }
}
