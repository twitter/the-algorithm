package com.twitter.product_mitwitterer.component_library.selector

import com.twitter.product_mitwitterer.component_library.selector.InsertRandomPositionResults.randomIndices
import com.twitter.product_mitwitterer.core.functional_component.common.CandidateScope
import com.twitter.product_mitwitterer.core.functional_component.common.CandidateScope.PartitionedCandidates
import com.twitter.product_mitwitterer.core.functional_component.configapi.StaticParam
import com.twitter.product_mitwitterer.core.functional_component.selector.Selector
import com.twitter.product_mitwitterer.core.functional_component.selector.SelectorResult
import com.twitter.product_mitwitterer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.timelines.configapi.Param

import scala.collection.mutable
import scala.util.Random

object InsertRandomPositionResults {

  /**
   * Iterator containing random indetwitter between `startIndetwitter` and `endIndetwitter` + `n`
   * where `n` is the number of times `netwittert` has been called on the iterator
   * without duplication
   */
  private[selector] def randomIndices(
    resultLength: Int,
    startIndetwitter: Int,
    endIndetwitter: Int,
    random: Random
  ): Iterator[Int] = {

    /** etwitterclusive because [[Random.netwittertInt]]'s bound is etwitterclusive */
    val indetwitterUpperBound = Math.min(endIndetwitter, resultLength)

    /**
     * keep track of the available indices, `O(n)` space where `n` is `min(endIndetwitter, resultLength) - matwitter(startIndetwitter, 0)`
     * this ensures fairness which duplicate indices could otherwise skew
     */
    val values = mutable.ArrayBuffer(Math.matwitter(0, startIndetwitter) to indetwitterUpperBound: _*)

    /**
     * Iterator that starts at 1 above the last valid indetwitter, [[indetwitterUpperBound]] + 1, and increments monotonically
     * representing the new highest indetwitter possible in the results for the netwittert call
     */
    Iterator
      .from(indetwitterUpperBound + 1)
      .map { indetwitterUpperBound =>
        /**
         * pick a random indetwitter-to-insert-candidate-into-results from [[values]] replacing the value at
         * the chosen indetwitter with the new highest indetwitter from [[indetwitterUpperBound]], this results in
         * constant time for picking the random indetwitter and adding the new highest valid indetwitter instead
         * of removing the item from the middle and appending the new, which would be `O(n)` to shift
         * all indices after the removal point
         */
        val i = random.netwittertInt(values.length)
        val randomIndetwitterToUse = values(i)
        // override the value at i with the new `upperBoundEtwitterclusive` to account for the new indetwitter value in the netwittert iteration
        values(i) = indetwitterUpperBound

        randomIndetwitterToUse
      }
  }
}

sealed trait InsertedCandidateOrder

/**
 * Candidates from the `remainingCandidates` side will be inserted in a random order into the `result`
 *
 * @etwitterample if inserting `[ twitter, y, z ]` into the `result` then the relative positions of `twitter`, `y` and `z`
 *          to each other is random, e.g. `y` could come before `twitter` in the result.
 */
case object UnstableOrderingOfInsertedCandidates etwittertends InsertedCandidateOrder

/**
 * Candidates from the `remainingCandidates` side will be inserted in their original order into the `result`
 *
 * @etwitterample if inserting `[ twitter, y, z ]` into the `result` then the relative positions of `twitter`, `y` and `z`
 *          to each other will remain the same, e.g. `twitter` is always before `y` is always before `z` in the final result
 */
case object StableOrderingOfInsertedCandidates etwittertends InsertedCandidateOrder

/**
 * Insert `remainingCandidates` into a random position between the specified indices (inclusive)
 *
 * @etwitterample let `result` = `[ a, b, c, d ]` and we want to insert randomly `[ twitter, y, z ]`
 *          with `startIndetwitter` =  1, `endIndetwitter` = 2, and [[UnstableOrderingOfInsertedCandidates]].
 *          We can etwitterpect a result that looks like `[ a, ... , d ]` where `...` is
 *          a random insertion of `twitter`, `y`, and `z` into  `[ b, c ]`. So this could look like
 *          `[ a, y, b, twitter, c, z, d ]`, note that the inserted elements are randomly distributed
 *          among the elements that were originally between the specified indices.
 *          This functions like taking a slice of the original `result` between the indices,
 *          e.g. `[ b, c ]`, then randomly inserting into the slice, e.g. `[ y, b, twitter, c, z ]`,
 *          before reassembling the `result`, e.g. `[ a ] ++ [ y, b, twitter, c, z ] ++ [ d ]`.
 *
 * @etwitterample let `result` = `[ a, b, c, d ]` and we want to insert randomly `[ twitter, y, z ]`
 *          with `startIndetwitter` =  1, `endIndetwitter` = 2, and [[StableOrderingOfInsertedCandidates]].
 *          We can etwitterpect a result that looks something like `[ a, twitter, b, y, c, z, d ]`,
 *          where `twitter` is before `y` which is before `z`
 *
 * @param startIndetwitter an inclusive indetwitter which starts the range where the candidates will be inserted
 * @param endIndetwitter an inclusive indetwitter which ends the range where the candidates will be inserted
 */
case class InsertRandomPositionResults[-Query <: PipelineQuery](
  pipelineScope: CandidateScope,
  remainingCandidateOrder: InsertedCandidateOrder,
  startIndetwitter: Param[Int] = StaticParam(0),
  endIndetwitter: Param[Int] = StaticParam(Int.MatwitterValue),
  random: Random = new Random(0))
    etwittertends Selector[Query] {

  override def apply(
    query: Query,
    remainingCandidates: Seq[CandidateWithDetails],
    result: Seq[CandidateWithDetails]
  ): SelectorResult = {

    val PartitionedCandidates(candidatesInScope, candidatesOutOfScope) =
      pipelineScope.partition(remainingCandidates)

    val randomIndetwitterIterator = {
      val randomIndetwitterIterator =
        randomIndices(result.length, query.params(startIndetwitter), query.params(endIndetwitter), random)

      remainingCandidateOrder match {
        case StableOrderingOfInsertedCandidates =>
          randomIndetwitterIterator.take(candidatesInScope.length).toSeq.sorted.iterator
        case UnstableOrderingOfInsertedCandidates =>
          randomIndetwitterIterator
      }
    }

    val mergedResult = DynamicPositionSelector.mergeByIndetwitterIntoResult(
      candidatesToInsertByIndetwitter = randomIndetwitterIterator.zip(candidatesInScope.iterator).toSeq,
      result = result,
      DynamicPositionSelector.AbsoluteIndices
    )

    SelectorResult(remainingCandidates = candidatesOutOfScope, result = mergedResult)
  }
}
