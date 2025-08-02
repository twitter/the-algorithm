package com.twitter.product_mitwitterer.core.service.scoring_pipeline_etwitterecutor

import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.model.common.presentation.ItemCandidateWithDetails
import com.twitter.product_mitwitterer.core.pipeline.scoring.ScoringPipelineResult

case class ScoringPipelineEtwitterecutorResult[Candidate <: UniversalNoun[Any]](
  result: Seq[ItemCandidateWithDetails],
  individualPipelineResults: Seq[ScoringPipelineResult[Candidate]])
