package com.twitter.home_mixer.product.for_you.selector

import com.twitter.product_mixer.core.functional_component.common.CandidateScope
import com.twitter.product_mixer.core.functional_component.common.CandidateScope.PartitionedCandidates
import com.twitter.product_mixer.core.functional_component.selector.Selector
import com.twitter.product_mixer.core.functional_component.selector.SelectorResult
import com.twitter.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.timelines.configapi.Param

trait MustDebunch {
  def apply(candidate: CandidateWithDetails): Boolean
}

/**
 * This selector rearranges the candidates to only allow bunches of size [[maxBunchSize]],
 * where a bunch is a consecutive sequence of candidates that meet [[mustDebunch]].
 */
case class DebunchCandidates(
  override val pipelineScope: CandidateScope,
  mustDebunch: MustDebunch,
  maxBunchSize: Param[Int])
    extends Selector[PipelineQuery] {

  override def apply(
    query: PipelineQuery,
    remainingCandidates: Seq[CandidateWithDetails],
    result: Seq[CandidateWithDetails]
  ): SelectorResult = {
    val PartitionedCandidates(selectedCandidates, otherCandidates) =
      pipelineScope.partition(remainingCandidates)
    val mutableCandidates = collection.mutable.ListBuffer(selectedCandidates: _*)

    var candidatePointer = 0
    var nonDebunchPointer = 0
    var bunchSize = 0
    var finalNonDebunch = -1

    while (candidatePointer < mutableCandidates.size) {
      if (mustDebunch(mutableCandidates(candidatePointer))) bunchSize += 1
      else {
        bunchSize = 0
        finalNonDebunch = candidatePointer
      }

      if (bunchSize > query.params(maxBunchSize)) {
        nonDebunchPointer = Math.max(candidatePointer, nonDebunchPointer)
        while (nonDebunchPointer < mutableCandidates.size &&
          mustDebunch(mutableCandidates(nonDebunchPointer))) {
          nonDebunchPointer += 1
        }
        if (nonDebunchPointer == mutableCandidates.size)
          candidatePointer = mutableCandidates.size
        else {
          val nextNonDebunch = mutableCandidates(nonDebunchPointer)
          mutableCandidates.remove(nonDebunchPointer)
          mutableCandidates.insert(candidatePointer, nextNonDebunch)
          bunchSize = 0
          finalNonDebunch = candidatePointer
        }
      }

      candidatePointer += 1
    }

    val debunchedCandidates = mutableCandidates.toList
    val updatedCandidates = otherCandidates ++ debunchedCandidates
    SelectorResult(remainingCandidates = updatedCandidates, result = result)
  }
}
