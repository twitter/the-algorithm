package com.twitter.home_mixer.functional_component.selector

import com.twitter.product_mixer.core.functional_component.common.AllPipelines
import com.twitter.product_mixer.core.functional_component.common.CandidateScope
import com.twitter.product_mixer.core.functional_component.selector.Selector
import com.twitter.product_mixer.core.functional_component.selector.SelectorResult
import com.twitter.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import scala.util.Random

object RandomShuffleCandidates extends Selector[PipelineQuery] {

  override def apply(
    query: PipelineQuery,
    remainingCandidates: Seq[CandidateWithDetails],
    result: Seq[CandidateWithDetails]
  ): SelectorResult = {
    val shuffledRemainingCandidates = Random.shuffle(remainingCandidates)
    SelectorResult(remainingCandidates = shuffledRemainingCandidates, result = result)
  }

  override def pipelineScope: CandidateScope = AllPipelines
}
