package com.twitter.product_mitwitterer.core.service.candidate_pipeline_etwitterecutor

import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.pipeline.candidate.CandidatePipelineResult

case class CandidatePipelineEtwitterecutorResult(
  candidatePipelineResults: Seq[CandidatePipelineResult],
  queryFeatureMap: FeatureMap)
