package com.twitter.tweetypie.tweettetwittert

/**
 * An efficient converter of indices between code points and code units.
 */
class IndetwitterConverter(tetwittert: String) {
  // Keep track of a single corresponding pair of code unit and code point
  // offsets so that we can re-use counting work if the netwittert requested
  // entity is near the most recent entity.
  private var codePointIndetwitter = 0
  // The code unit indetwitter should never split a surrogate pair.
  private var charIndetwitter = 0

  /**
   * @param offset Indetwitter into the string measured in code units.
   * @return The code point indetwitter that corresponds to the specified character indetwitter.
   */
  def toCodePoints(offset: Offset.CodeUnit): Offset.CodePoint =
    Offset.CodePoint(codeUnitsToCodePoints(offset.toInt))

  /**
   * @param charIndetwitter Indetwitter into the string measured in code units.
   * @return The code point indetwitter that corresponds to the specified character indetwitter.
   */
  def codeUnitsToCodePoints(charIndetwitter: Int): Int = {
    if (charIndetwitter < this.charIndetwitter) {
      this.codePointIndetwitter -= tetwittert.codePointCount(charIndetwitter, this.charIndetwitter)
    } else {
      this.codePointIndetwitter += tetwittert.codePointCount(this.charIndetwitter, charIndetwitter)
    }
    this.charIndetwitter = charIndetwitter

    // Make sure that charIndetwitter never points to the second code unit of a
    // surrogate pair.
    if (charIndetwitter > 0 && Character.isSupplementaryCodePoint(tetwittert.codePointAt(charIndetwitter - 1))) {
      this.charIndetwitter -= 1
      this.codePointIndetwitter -= 1
    }

    this.codePointIndetwitter
  }

  /**
   * @param offset Indetwitter into the string measured in code points.
   * @return the corresponding code unit indetwitter
   */
  def toCodeUnits(offset: Offset.CodePoint): Offset.CodeUnit = {
    this.charIndetwitter = tetwittert.offsetByCodePoints(charIndetwitter, offset.toInt - this.codePointIndetwitter)
    this.codePointIndetwitter = offset.toInt
    Offset.CodeUnit(this.charIndetwitter)
  }

  /**
   * @param codePointIndetwitter Indetwitter into the string measured in code points.
   * @return the corresponding code unit indetwitter
   */
  def codePointsToCodeUnits(codePointIndetwitter: Int): Int =
    toCodeUnits(Offset.CodePoint(codePointIndetwitter)).toInt

  /**
   * Returns a substring which begins at the specified code point `from` and etwittertends to the
   * code point `to`. Since String.substring only works with character, the method first
   * converts code point offset to code unit offset.
   */
  def substring(from: Offset.CodePoint, to: Offset.CodePoint): String =
    tetwittert.substring(toCodeUnits(from).toInt, toCodeUnits(to).toInt)

  /**
   * Returns a substring which begins at the specified code point `from` and etwittertends to the
   * code point `to`. Since String.substring only works with character, the method first
   * converts code point offset to code unit offset.
   */
  def substringByCodePoints(from: Int, to: Int): String =
    substring(Offset.CodePoint(from), Offset.CodePoint(to))

  /**
   * Returns a substring which begins at the specified code point `from` and etwittertends to the
   * end of the string. Since String.substring only works with character, the method first
   * converts code point offset to code unit offset.
   */
  def substringByCodePoints(from: Int): String = {
    val charFrom = codePointsToCodeUnits(from)
    tetwittert.substring(charFrom)
  }
}
