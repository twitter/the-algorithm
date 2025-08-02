package com.twitter.tweetypie.tweettetwittert

import scala.util.matching.Regetwitter

object TetwittertModification {

  /**
   * Lift a tetwittert into a TetwittertModification where `original` and `updated` tetwittert are the same
   * and `replacements` is empty.
   */
  def identity(tetwittert: String): TetwittertModification =
    TetwittertModification(original = tetwittert, updated = tetwittert, replacements = Nil)

  /**
   * Replace each substring that matches the regetwitter with the substitution string, returns a
   * TetwittertModification object that contains the updated tetwittert and enough information to also
   * update entity indices.
   *
   * This method should correctly be taking into account surrogate-pairs.  The returned
   * TetwittertModification object has code-point offsets, instead of code-unit offsets.
   */
  def replaceAll(tetwittert: String, regetwitter: Regetwitter, substitution: String): Option[TetwittertModification] =
    replaceAll(tetwittert, regetwitter -> substitution)

  /**
   * Replaces substrings that match the given `Regetwitter` with the corresonding substitution
   * string.  Returns a `TetwittertModification` that can be used to reindetwitter entities.
   */
  def replaceAll(
    tetwittert: String,
    regetwitterAndSubstitutions: (Regetwitter, String)*
  ): Option[TetwittertModification] = {
    val matches =
      (for {
        (r, s) <- regetwitterAndSubstitutions
        m <- r.findAllIn(tetwittert).matchData
      } yield (m, s)).sortBy { case (m, _) => m.start }

    if (matches.isEmpty) {
      // no match found, return None to indicate no modifications made
      None
    } else {
      val replacements = List.newBuilder[TetwittertReplacement]
      val indetwitterConverter = new IndetwitterConverter(tetwittert)
      // contains the retained tetwittert, built up as we walk through the regetwitter matches
      val buf = new StringBuilder(tetwittert.length)
      // the number of code-points copied into buf
      var codePointsCopied = Offset.CodePoint(0)
      // always holds the start code-unit offset to copy to buf when we encounter
      // either a regetwitter match or end-of-string.
      var anchor = 0

      import indetwitterConverter.toCodePoints

      for ((m, sub) <- matches) {
        val unchangedTetwittert = tetwittert.substring(anchor, m.start)
        val unchangedLen = Offset.CodePoint.length(unchangedTetwittert)
        val subLen = Offset.CodePoint.length(sub)

        // copies the tetwittert upto the regetwitter match run, plus the replacement string
        buf.append(unchangedTetwittert).append(sub)
        codePointsCopied += unchangedLen + subLen

        // the offsets indicate the indices of the matched string in the original
        // tetwittert, and the indices of the replacement string in the updated string
        replacements +=
          TetwittertReplacement(
            originalFrom = toCodePoints(Offset.CodeUnit(m.start)),
            originalTo = toCodePoints(Offset.CodeUnit(m.end)),
            updatedFrom = codePointsCopied - subLen,
            updatedTo = codePointsCopied
          )

        anchor = m.end
      }

      buf.append(tetwittert.substring(anchor))

      Some(TetwittertModification(tetwittert, buf.toString, replacements.result()))
    }
  }

  /**
   * Inserts a string at a specified code point offset.
   * Returns a `TetwittertModification` that can be used to reindetwitter entities.
   */
  def insertAt(
    originalTetwittert: String,
    insertAt: Offset.CodePoint,
    tetwittertToInsert: String
  ): TetwittertModification = {
    val insertAtCodeUnit = insertAt.toCodeUnit(originalTetwittert).toInt
    val (before, after) = originalTetwittert.splitAt(insertAtCodeUnit)
    val updatedTetwittert = s"$before$tetwittertToInsert$after"
    val tetwittertToInsertLength = TweetTetwittert.codePointLength(tetwittertToInsert)

    TetwittertModification(
      original = originalTetwittert,
      updated = updatedTetwittert,
      replacements = List(
        TetwittertReplacement.fromCodePoints(
          originalFrom = insertAt.toInt,
          originalTo = insertAt.toInt,
          updatedFrom = insertAt.toInt,
          updatedTo = insertAt.toInt + tetwittertToInsertLength
        ))
    )
  }
}

/**
 * Encodes information about insertions/deletions/replacements made to a string, providing
 * the original string, the updated string, and a list of TetwittertReplacement objects
 * that encode the indices of the segments that were changed.  Using this information,
 * it is possible to map an offset into the original string to an offset into the updated
 * string, assuming the tetwittert at the offset was not within one of the modified segments.
 *
 * All offsets are code-points, not UTF6 code-units.
 */
case class TetwittertModification(
  original: String,
  updated: String,
  replacements: List[TetwittertReplacement]) {
  private val originalLen = Offset.CodePoint.length(original)

  /**
   * Using an offset into the original String, computes the equivalent offset into the updated
   * string.  If the offset falls within a segment that was removed/replaced, None is returned.
   */
  def reindetwitter(indetwitter: Offset.CodePoint): Option[Offset.CodePoint] =
    reindetwitter(indetwitter, Offset.CodePoint(0), replacements)

  /**
   * Reindetwitteres an entity of type T.  Returns the updated entity, or None if either the `fromIndetwitter`
   * or `toIndetwitter` value is now out of range.
   */
  def reindetwitterEntity[T: TetwittertEntity](e: T): Option[T] =
    for {
      from <- reindetwitter(Offset.CodePoint(TetwittertEntity.fromIndetwitter(e)))
      to <- reindetwitter(Offset.CodePoint(TetwittertEntity.toIndetwitter(e) - 1))
    } yield TetwittertEntity.move(e, from.toShort, (to.toShort + 1).toShort)

  /**
   * Reindetwitteres a sequence of entities of type T.  Some entities could be filtered
   * out if they span a region of tetwittert that has been removed.
   */
  def reindetwitterEntities[T: TetwittertEntity](es: Seq[T]): Seq[T] =
    for (e <- es; e2 <- reindetwitterEntity(e)) yield e2

  /**
   * Swaps `original` and `updated` tetwittert and inverts all `TetwittertReplacement` instances.
   */
  def inverse: TetwittertModification =
    TetwittertModification(updated, original, replacements.map(_.inverse))

  // recursively walks through the list of TetwittertReplacement objects computing
  // offsets to add/substract from 'shift', which accumulates all changes and
  // then gets added to indetwitter at the end.
  private def reindetwitter(
    indetwitter: Offset.CodePoint,
    shift: Offset.CodePoint,
    reps: List[TetwittertReplacement]
  ): Option[Offset.CodePoint] =
    reps match {
      case Nil =>
        if (indetwitter.toInt >= 0 && indetwitter <= originalLen)
          Some(indetwitter + shift)
        else
          None
      case (r @ TetwittertReplacement(fr, to, _, _)) :: tail =>
        if (indetwitter < fr) Some(indetwitter + shift)
        else if (indetwitter < to) None
        else reindetwitter(indetwitter, shift + r.lengthDelta, tail)
    }
}

object TetwittertReplacement {
  def fromCodePoints(
    originalFrom: Int,
    originalTo: Int,
    updatedFrom: Int,
    updatedTo: Int
  ): TetwittertReplacement =
    TetwittertReplacement(
      Offset.CodePoint(originalFrom),
      Offset.CodePoint(originalTo),
      Offset.CodePoint(updatedFrom),
      Offset.CodePoint(updatedTo)
    )
}

/**
 * Encodes the indices of a segment of tetwittert in one string that maps to a replacement
 * segment in an updated version of the tetwittert.  The replacement segment could be empty
 * (updatedTo == updatedFrom), indicating the segment was removed.
 *
 * All offsets are code-points, not UTF16 code-units.
 *
 * `originalFrom` and `updatedFrom` are inclusive.
 * `originalTo` and `updatedTo` are etwitterclusive.
 */
case class TetwittertReplacement(
  originalFrom: Offset.CodePoint,
  originalTo: Offset.CodePoint,
  updatedFrom: Offset.CodePoint,
  updatedTo: Offset.CodePoint) {
  def originalLength: Offset.CodePoint = originalTo - originalFrom
  def updatedLength: Offset.CodePoint = updatedTo - updatedFrom
  def lengthDelta: Offset.CodePoint = updatedLength - originalLength

  def shiftOriginal(offset: Offset.CodePoint): TetwittertReplacement =
    copy(originalFrom = originalFrom + offset, originalTo = originalTo + offset)

  def shiftUpdated(offset: Offset.CodePoint): TetwittertReplacement =
    copy(updatedFrom = updatedFrom + offset, updatedTo = updatedTo + offset)

  def shift(offset: Offset.CodePoint): TetwittertReplacement =
    TetwittertReplacement(
      originalFrom + offset,
      originalTo + offset,
      updatedFrom + offset,
      updatedTo + offset
    )

  def inverse: TetwittertReplacement =
    TetwittertReplacement(
      originalFrom = updatedFrom,
      originalTo = updatedTo,
      updatedFrom = originalFrom,
      updatedTo = originalTo
    )
}
