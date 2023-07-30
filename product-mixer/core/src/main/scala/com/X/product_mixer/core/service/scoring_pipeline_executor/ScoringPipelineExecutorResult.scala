package com.X.product_mixer.core.service.scoring_pipeline_executor

import com.X.product_mixer.core.model.common.UniversalNoun
import com.X.product_mixer.core.model.common.presentation.ItemCandidateWithDetails
import com.X.product_mixer.core.pipeline.scoring.ScoringPipelineResult

case class ScoringPipelineExecutorResult[Candidate <: UniversalNoun[Any]](
  result: Seq[ItemCandidateWithDetails],
  individualPipelineResults: Seq[ScoringPipelineResult[Candidate]])
