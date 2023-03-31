package com.twitter.product_mixer.component_library.selector.sorter

import com.twitter.product_mixer.core.model.common.presentation.CandidateWithDetails

/**
 * Reverse candidates.
 *
 * @example `UpdateSortResults(ReverseSorter())`
 */
object ReverseSorter extends SorterProvider with Sorter {

  override def sort[Candidate <: CandidateWithDetails](candidates: Seq[Candidate]): Seq[Candidate] =
    candidates.reverse
}
