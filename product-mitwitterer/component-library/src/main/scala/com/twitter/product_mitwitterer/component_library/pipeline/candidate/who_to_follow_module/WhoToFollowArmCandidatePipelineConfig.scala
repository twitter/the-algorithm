package com.twitter.product_mitwitterer.component_library.pipeline.candidate.who_to_follow_module

import com.twitter.product_mitwitterer.core.model.common.identifier.CandidatePipelineIdentifier

object WhoToFollowArmCandidatePipelineConfig {
  val MinCandidatesSize = 3
  val MatwitterCandidatesSize = 20

  val identifier: CandidatePipelineIdentifier = CandidatePipelineIdentifier("WhoToFollowArm")
}
