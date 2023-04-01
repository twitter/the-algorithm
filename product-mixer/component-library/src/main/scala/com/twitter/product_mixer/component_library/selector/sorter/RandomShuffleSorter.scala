package com.twitter.product_mixer.component_library.selector.sorter

import com.twitter.product_mixer.core.model.common.presentation.CandidateWithDetails
import scala.util.Random

/**
 * Randomly shuffles candidates using the provided [[random]]
 *
 * @example `UpdateSortResults(RandomShuffleSorter())`
 * @param random used to set the seed and for ease of testing, in most cases leaving it as the default is fine.
 */
case class RandomShuffleSorter(random: Random = new Random(0)) extends SorterProvider with Sorter {

  override def sort[Candidate <: CandidateWithDetails](candidates: Seq[Candidate]): Seq[Candidate] =
    random.shuffle(candidates)
}
