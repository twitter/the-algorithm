package com.twitter.product_mixer.core.service.candidate_pipeline_executor

import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.pipeline.candidate.CandidatePipelineResult

case class CandidatePipelineExecutorResult(
  candidatePipelineResults: Seq[CandidatePipelineResult],
  queryFeatureMap: FeatureMap)
