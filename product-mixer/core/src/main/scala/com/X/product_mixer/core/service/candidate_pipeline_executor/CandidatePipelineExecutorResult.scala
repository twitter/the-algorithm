package com.X.product_mixer.core.service.candidate_pipeline_executor

import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.pipeline.candidate.CandidatePipelineResult

case class CandidatePipelineExecutorResult(
  candidatePipelineResults: Seq[CandidatePipelineResult],
  queryFeatureMap: FeatureMap)
