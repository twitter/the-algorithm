package com.ExTwitter.follow_recommendations.models.failures

import com.ExTwitter.product_mixer.core.pipeline.pipeline_failure.CandidateSourceTimeout
import com.ExTwitter.product_mixer.core.pipeline.pipeline_failure.PipelineFailure

object TimeoutPipelineFailure {
  def apply(candidateSourceName: String): PipelineFailure = {
    PipelineFailure(
      CandidateSourceTimeout,
      s"Candidate Source $candidateSourceName timed out before returning candidates")
  }
}
