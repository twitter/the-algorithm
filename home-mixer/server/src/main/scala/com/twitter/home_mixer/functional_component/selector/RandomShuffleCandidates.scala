package com.twitter.home_mixer.functional_component.selector

import com.twitter.product_mixer.core.functional_component.common.AllPipelines
import com.twitter.product_mixer.core.functional_component.common.CandidateScope
import com.twitter.product_mixer.core.functional_component.selector.Selector
import com.twitter.product_mixer.core.functional_component.selector.SelectorResult
import com.twitter.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import java.util.concurrent.ThreadLocalRandom
import scala.collection.mutable.ArrayBuffer

object RandomShuffleCandidates extends Selector[PipelineQuery] {

  override def apply(
    query: PipelineQuery,
    remainingCandidates: Seq[CandidateWithDetails],
    result: Seq[CandidateWithDetails]
  ): SelectorResult = {
    // Use ThreadLocalRandom to avoid lock contention in concurrent applications
    val shuffledRemainingCandidates = threadLocalShuffle(remainingCandidates)
    SelectorResult(remainingCandidates = shuffledRemainingCandidates, result = result)
  }

  override def pipelineScope: CandidateScope = AllPipelines

  /**
   * Fisher-Yates shuffle using ThreadLocalRandom for better concurrent performance.
   * This avoids the lock contention issue with scala.util.Random.shuffle
   */
  private def threadLocalShuffle[T](seq: Seq[T]): Seq[T] = {
    val buffer = ArrayBuffer(seq: _*)
    val random = ThreadLocalRandom.current()
    var i = buffer.length - 1
    while (i > 0) {
      val j = random.nextInt(i + 1)
      val tmp = buffer(i)
      buffer(i) = buffer(j)
      buffer(j) = tmp
      i -= 1
    }
    buffer.toSeq
  }
}
