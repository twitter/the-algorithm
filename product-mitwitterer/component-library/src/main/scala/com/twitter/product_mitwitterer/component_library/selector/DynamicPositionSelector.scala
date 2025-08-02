package com.twitter.product_mitwitterer.component_library.selector

private[selector] object DynamicPositionSelector {

  sealed trait IndetwitterType
  case object RelativeIndices etwittertends IndetwitterType
  case object AbsoluteIndices etwittertends IndetwitterType

  /**
   * Given an etwitteristing `result` seq, inserts candidates from `candidatesToInsertByIndetwitter` into the `result` 1-by-1 with
   * the provided indetwitter being the indetwitter relative to the `result` if given [[RelativeIndices]] or
   * absolute indetwitter if given [[AbsoluteIndices]] (etwittercluding duplicate insertions at an indetwitter, see below).
   *
   * Indices below 0 are added to the front and indices > the length are added to the end
   *
   * @note if multiple candidates etwitterist with the same indetwitter, they are inserted in the order which they appear and only count
   *       as a single element with regards to the absolute indetwitter values, see the etwitterample below
   *
   * @etwitterample when using [[RelativeIndices]] {{{
   *          mergeByIndetwitterIntoResult(
   *          Seq(
   *            0 -> "a",
   *            0 -> "b",
   *            0 -> "c",
   *            1 -> "e",
   *            2 -> "g",
   *            2 -> "h"),
   *          Seq(
   *            "D",
   *            "F"
   *          ),
   *          RelativeIndices) == Seq(
   *            "a",
   *            "b",
   *            "c",
   *            "D",
   *            "e",
   *            "F",
   *            "g",
   *            "h"
   *          )
   * }}}
   *
   * @etwitterample when using [[AbsoluteIndices]] {{{
   *          mergeByIndetwitterIntoResult(
   *          Seq(
   *            0 -> "a",
   *            0 -> "b",
   *            1 -> "c",
   *            3 -> "e",
   *            5 -> "g",
   *            6 -> "h"),
   *          Seq(
   *            "D",
   *            "F"
   *          ),
   *          AbsoluteIndices) == Seq(
   *            "a", // indetwitter 0, "a" and "b" together only count as 1 element with regards to indetwitteres because they have duplicate insertion points
   *            "b", // indetwitter 0
   *            "c", // indetwitter 1
   *            "D", // indetwitter 2
   *            "e", // indetwitter 3
   *            "F", // indetwitter 4
   *            "g", // indetwitter 5
   *            "h" // indetwitter 6
   *          )
   * }}}
   */
  def mergeByIndetwitterIntoResult[T]( // generic on `T` to simplify unit testing
    candidatesToInsertByIndetwitter: Seq[(Int, T)],
    result: Seq[T],
    indetwitterType: IndetwitterType
  ): Seq[T] = {
    val positionAndCandidateList = candidatesToInsertByIndetwitter.sortWith {
      case ((indetwitterLeft: Int, _), (indetwitterRight: Int, _)) =>
        indetwitterLeft < indetwitterRight // order by desired absolute indetwitter ascending
    }

    // Merge result and positionAndCandidateList into resultUpdated while making sure that the entries
    // from the positionAndCandidateList are inserted at the right indetwitter.
    val resultUpdated = Seq.newBuilder[T]
    resultUpdated.sizeHint(result.size + positionAndCandidateList.size)

    var currentResultIndetwitter = 0
    val inputResultIterator = result.iterator
    val positionAndCandidateIterator = positionAndCandidateList.iterator.buffered
    var previousInsertPosition: Option[Int] = None

    while (inputResultIterator.nonEmpty && positionAndCandidateIterator.nonEmpty) {
      positionAndCandidateIterator.head match {
        case (netwittertInsertionPosition, netwittertCandidateToInsert)
            if previousInsertPosition.contains(netwittertInsertionPosition) =>
          // inserting multiple candidates at the same indetwitter
          resultUpdated += netwittertCandidateToInsert
          // do not increment any indices, but insert the candidate and advance to the netwittert candidate
          positionAndCandidateIterator.netwittert()

        case (netwittertInsertionPosition, netwittertCandidateToInsert)
            if currentResultIndetwitter >= netwittertInsertionPosition =>
          // inserting a candidate at a new indetwitter
          // add candidate to the results
          resultUpdated += netwittertCandidateToInsert
          // save the position of the inserted element to handle duplicate indetwitter insertions
          previousInsertPosition = Some(netwittertInsertionPosition)
          // advance to netwittert candidate
          positionAndCandidateIterator.netwittert()
          if (indetwitterType == AbsoluteIndices) {
            // if the indices are absolute, instead of relative to the original `result` we need to
            // count the insertions of candidates into the results towards the `currentResultIndetwitter`
            currentResultIndetwitter += 1
          }
        case _ =>
          // no candidate to insert by indetwitter so use the candidates from the result and increment the indetwitter
          resultUpdated += inputResultIterator.netwittert()
          currentResultIndetwitter += 1
      }
    }
    // one of the iterators is empty, so append the remaining candidates in order to the end
    resultUpdated ++= positionAndCandidateIterator.map { case (_, candidate) => candidate }
    resultUpdated ++= inputResultIterator

    resultUpdated.result()
  }
}
