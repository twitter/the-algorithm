package com.twitter.product_mixer.component_library.selector.sorter

import com.twitter.product_mixer.core.model.common.presentation.CandidateWithDetails
import java.util.concurrent.ThreadLocalRandom
import scala.collection.mutable.ArrayBuffer

/**
 * Randomly shuffles candidates using ThreadLocalRandom for better concurrent performance.
 * 
 * This implementation avoids the lock contention issue with scala.util.Random by using
 * ThreadLocalRandom, which provides each thread with its own random number generator.
 *
 * @example `UpdateSortResults(RandomShuffleSorter())`
 * @param seed optional seed for reproducible shuffles (useful for testing)
 */
case class RandomShuffleSorter(seed: Option[Long] = None) extends SorterProvider with Sorter {

  override def sort[Candidate <: CandidateWithDetails](candidates: Seq[Candidate]): Seq[Candidate] =
    threadLocalShuffle(candidates)

  /**
   * Fisher-Yates shuffle using ThreadLocalRandom for better concurrent performance.
   * If a seed is provided, uses a seeded java.util.Random for reproducibility.
   */
  private def threadLocalShuffle[T](seq: Seq[T]): Seq[T] = {
    val buffer = ArrayBuffer(seq: _*)
    val random = seed match {
      case Some(s) => new java.util.Random(s)
      case None => ThreadLocalRandom.current()
    }
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
