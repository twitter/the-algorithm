package com.twitter.tweet_mixer.functional_component.filter

import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.model.common.presentation.CandidatePipelines

trait ShouldIgnoreCandidatePipelinesFilter {
  def candidatePipelinesToExclude: Set[CandidatePipelineIdentifier]

  def shouldIgnore(candidate: CandidateWithFeatures[TweetCandidate]): Boolean = {
    candidate.features
      .get(CandidatePipelines)
      .exists(pipeline => candidatePipelinesToExclude.contains(pipeline))
  }
}
