package com.twitter.follow_recommendations.common.utils

object CollectionUtil {

  /**
   * Transposes a sequence of sequences. As opposed to the Scala collection library version
   * of transpose, the sequences do not have to have the same length.
   *
   * Example:
   * transpose(immutable.Seq(immutable.Seq(1,2,3), immutable.Seq(4,5), immutable.Seq(6,7)))
   *   => immutable.Seq(immutable.Seq(1, 4, 6), immutable.Seq(2, 5, 7), immutable.Seq(3))
   *
   * @param seq a sequence of sequences
   * @tparam A the type of elements in the seq
   * @return the transposed sequence of sequences
   */
  def transposeLazy[A](seq: Seq[Seq[A]]): Stream[Seq[A]] =
    seq.filter(_.nonEmpty) match {
      case Nil => Stream.empty
      case ys => ys.map(_.head) #:: transposeLazy(ys.map(_.tail))
    }
}
