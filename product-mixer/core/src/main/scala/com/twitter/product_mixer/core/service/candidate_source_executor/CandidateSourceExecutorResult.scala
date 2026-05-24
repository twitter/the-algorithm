package com.twitter.product_mixer.core.service.candidate_source_executor

import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.model.common.UniversalNoun
import com.twitter.product_mixer.core.service.ExecutorResult

case class CandidateSourceExecutorResult[Candidate <: UniversalNoun[Any]](
  candidates: Seq[FetchedCandidateWithFeatures[Candidate]],
  candidateSourceFeatureMap: FeatureMap)
    extends ExecutorResult
