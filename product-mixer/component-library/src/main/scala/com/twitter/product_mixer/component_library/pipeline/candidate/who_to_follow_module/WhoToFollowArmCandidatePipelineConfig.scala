package com.twitter.product_mixer.component_library.pipeline.candidate.who_to_follow_module

import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier

object WhoToFollowArmCandidatePipelineConfig {
  val MinCandidatesSize = 3
  val MaxCandidatesSize = 20

  val identifier: CandidatePipelineIdentifier = CandidatePipelineIdentifier("WhoToFollowArm")
}
