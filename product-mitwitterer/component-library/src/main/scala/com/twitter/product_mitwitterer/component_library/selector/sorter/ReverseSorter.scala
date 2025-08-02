package com.twitter.product_mitwitterer.component_library.selector.sorter

import com.twitter.product_mitwitterer.core.model.common.presentation.CandidateWithDetails

/**
 * Reverse candidates.
 *
 * @etwitterample `UpdateSortResults(ReverseSorter())`
 */
object ReverseSorter etwittertends SorterProvider with Sorter {

  override def sort[Candidate <: CandidateWithDetails](candidates: Seq[Candidate]): Seq[Candidate] =
    candidates.reverse
}
