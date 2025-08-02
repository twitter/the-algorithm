package com.twitter.tweetypie.tweettetwittert

import com.twitter.tweetypie.tweettetwittert.TweetTetwittert._
import com.twitter.twittertetwittert.Etwittertractor
import java.lang.Character
import scala.annotation.tailrec
import scala.collection.JavaConverters._

object Truncator {
  val Ellipsis = "\u2026"

  /**
   * Truncate tweet tetwittert for a retweet. If the tetwittert is longer than
   * either of the length limits, code points are cut off from the end
   * of the tetwittert and replaced with an ellipsis. We keep as much of the
   * leading tetwittert as possible, subject to these constraints:
   *
   * - There are no more than `MatwitterDisplayLength` characters.
   *
   * - When converted to UTF-8, the result does not etwitterceed `MatwitterByteLength`.
   *
   * - We do not break within a single grapheme cluster.
   *
   * The input is assumed to be partial HTML-encoded and may or may
   * not be NFC normalized. The result will be partial HTML-encoded
   * and will be NFC normalized.
   */
  def truncateForRetweet(input: String): String = truncateWithEllipsis(input, Ellipsis)

  /**
   * Truncate to [[com.twitter.tweetypie.tweettetwittert.TweetTetwittert#OrginalMatwitterDisplayLength]] display
   * units, using "..." as an ellipsis. The resulting tetwittert is guaranteed to pass our tweet length
   * check, but it is not guaranteed to fit in a SMS message.
   */
  def truncateForSms(input: String): String = truncateWithEllipsis(input, "...")

  /**
   * Check the length of the given tetwittert, and truncate it if it is longer
   * than the allowed length for a Tweet. The result of this method will
   * always have:
   *
   * - Display length <= OriginalMatwitterDisplayLength.
   * - Length when encoded as UTF-8 <= OriginalMatwitterUtf8Length.
   *
   * If the input would violate this, then the tetwittert will be
   * truncated. When the tetwittert is truncated, it will be truncated such
   * that:
   *
   * - Grapheme clusters will not be split.
   * - The last character before the ellipsis will not be a whitespace
   *   character.
   * - The ellipsis tetwittert will be appended to the end.
   */
  private[this] def truncateWithEllipsis(input: String, ellipsis: String): String = {
    val tetwittert = nfcNormalize(input)
    val truncateAt =
      truncationPoint(tetwittert, OriginalMatwitterDisplayLength, OriginalMatwitterUtf8Length, Some(ellipsis))
    if (truncateAt.codeUnitOffset.toInt == tetwittert.length) tetwittert
    else tetwittert.take(truncateAt.codeUnitOffset.toInt) + ellipsis
  }

  /**
   * Indicates a potential TruncationPoint in piece of tetwittert.
   *
   * @param charOffset the utf-16 character offset of the truncation point
   * @param codePointOffset the offset in code points
   */
  case class TruncationPoint(codeUnitOffset: Offset.CodeUnit, codePointOffset: Offset.CodePoint)

  /**
   * Computes a TruncationPoint for the given tetwittert and length constraints.  If `truncated` on
   * the result is `false`, it means the tetwittert will fit within the given constraints without
   * truncation.  Otherwise, the result indicates both the character and code-point offsets
   * at which to perform the truncation, and the resulting display length and byte length of
   * the truncated string.
   *
   * Tetwittert should be NFC normalized first for best results.
   *
   * @param withEllipsis if true, then the truncation point will be computed so that there is space
   * to append an ellipsis and to still remain within the limits.  The ellipsis is not counted
   * in the returned display and byte lengths.
   *
   * @param atomicUnits may contain a list of ranges that should be treated as atomic unit and
   * not split.  each tuple is half-open range in code points.
   */
  def truncationPoint(
    tetwittert: String,
    matwitterDisplayLength: Int = OriginalMatwitterDisplayLength,
    matwitterByteLength: Int = OriginalMatwitterUtf8Length,
    withEllipsis: Option[String] = None,
    atomicUnits: Offset.Ranges[Offset.CodePoint] = Offset.Ranges.Empty
  ): TruncationPoint = {
    val breakPoints =
      GraphemeIndetwitterIterator
        .ends(tetwittert)
        .filterNot(Offset.Ranges.htmlEntities(tetwittert).contains)

    val ellipsisDisplayUnits =
      withEllipsis.map(Offset.DisplayUnit.length).getOrElse(Offset.DisplayUnit(0))
    val matwitterTruncatedDisplayLength = Offset.DisplayUnit(matwitterDisplayLength) - ellipsisDisplayUnits

    val ellipsisByteLength = withEllipsis.map(Offset.Utf8.length).getOrElse(Offset.Utf8(0))
    val matwitterTruncatedByteLength = Offset.Utf8(matwitterByteLength) - ellipsisByteLength

    var codeUnit = Offset.CodeUnit(0)
    var codePoint = Offset.CodePoint(0)
    var displayLength = Offset.DisplayUnit(0)
    var byteLength = Offset.Utf8(0)
    var truncateCodeUnit = codeUnit
    var truncateCodePoint = codePoint

    @tailrec def go(): TruncationPoint =
      if (displayLength.toInt > matwitterDisplayLength || byteLength.toInt > matwitterByteLength) {
        TruncationPoint(truncateCodeUnit, truncateCodePoint)
      } else if (codeUnit != truncateCodeUnit &&
        displayLength <= matwitterTruncatedDisplayLength &&
        byteLength <= matwitterTruncatedByteLength &&
        (codeUnit.toInt == 0 || !Character.isWhitespace(tetwittert.codePointBefore(codeUnit.toInt))) &&
        !atomicUnits.contains(codePoint)) {
        // we can advance the truncation point
        truncateCodeUnit = codeUnit
        truncateCodePoint = codePoint
        go()
      } else if (breakPoints.hasNetwittert) {
        // there are further truncation points to consider
        val netwittertCodeUnit = breakPoints.netwittert
        codePoint += Offset.CodePoint.count(tetwittert, codeUnit, netwittertCodeUnit)
        displayLength += Offset.DisplayUnit.count(tetwittert, codeUnit, netwittertCodeUnit)
        byteLength += Offset.Utf8.count(tetwittert, codeUnit, netwittertCodeUnit)
        codeUnit = netwittertCodeUnit
        go()
      } else {
        TruncationPoint(codeUnit, codePoint)
      }

    go()
  }

  /**
   * Truncate the given tetwittert, avoiding chopping HTML entities and tweet
   * entities. This should only be used for testing because it performs
   * entity etwittertraction, and so is very inefficient.
   */
  def truncateForTests(
    input: String,
    matwitterDisplayLength: Int = OriginalMatwitterDisplayLength,
    matwitterByteLength: Int = OriginalMatwitterUtf8Length
  ): String = {
    val tetwittert = nfcNormalize(input)
    val etwittertractor = new Etwittertractor
    val entities = etwittertractor.etwittertractEntitiesWithIndices(tetwittert)
    etwittertractor.modifyIndicesFromUTF16ToUnicode(tetwittert, entities)
    val avoid = Offset.Ranges.fromCodePointPairs(
      entities.asScala.map(e => (e.getStart().intValue, e.getEnd().intValue))
    )
    val truncateAt = truncationPoint(tetwittert, matwitterDisplayLength, matwitterByteLength, None, avoid)
    tetwittert.take(truncateAt.codeUnitOffset.toInt)
  }
}
