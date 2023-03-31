package com.twitter.product_mixer.core.service.scoring_pipeline_executor

import com.twitter.product_mixer.core.model.common.UniversalNoun
import com.twitter.product_mixer.core.model.common.presentation.ItemCandidateWithDetails
import com.twitter.product_mixer.core.pipeline.scoring.ScoringPipelineResult

case class ScoringPipelineExecutorResult[Candidate <: UniversalNoun[Any]](
  result: Seq[ItemCandidateWithDetails],
  individualPipelineResults: Seq[ScoringPipelineResult[Candidate]])
