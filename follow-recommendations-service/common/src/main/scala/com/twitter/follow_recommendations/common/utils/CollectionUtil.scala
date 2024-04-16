package com.tw ter.follow_recom ndat ons.common.ut ls

object Collect onUt l {

  /**
   * Transposes a sequence of sequences. As opposed to t  Scala collect on l brary vers on
   * of transpose, t  sequences do not have to have t  sa  length.
   *
   * Example:
   * transpose( mmutable.Seq( mmutable.Seq(1,2,3),  mmutable.Seq(4,5),  mmutable.Seq(6,7)))
   *   =>  mmutable.Seq( mmutable.Seq(1, 4, 6),  mmutable.Seq(2, 5, 7),  mmutable.Seq(3))
   *
   * @param seq a sequence of sequences
   * @tparam A t  type of ele nts  n t  seq
   * @return t  transposed sequence of sequences
   */
  def transposeLazy[A](seq: Seq[Seq[A]]): Stream[Seq[A]] =
    seq.f lter(_.nonEmpty) match {
      case N l => Stream.empty
      case ys => ys.map(_. ad) #:: transposeLazy(ys.map(_.ta l))
    }
}
