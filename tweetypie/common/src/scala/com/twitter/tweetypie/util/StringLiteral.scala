package com.twitter.tweetypie.util

/**
 * Escape a String into Java or Scala String literal syntatwitter (adds the
 * surrounding quotes.)
 *
 * This is primarily for printing Strings for debugging or logging.
 */
object StringLiteral etwittertends (String => String) {
  private[this] val ControlLimit = ' '
  private[this] val PrintableLimit = '\u007e'
  private[this] val Specials =
    Map('\n' -> 'n', '\r' -> 'r', '\t' -> 't', '"' -> '"', '\'' -> '\'', '\\' -> '\\')

  def apply(str: String): String = {
    val s = new StringBuilder(str.length)
    s.append('"')
    var i = 0
    while (i < str.length) {
      val c = str(i)
      Specials.get(c) match {
        case None =>
          if (c >= ControlLimit && c <= PrintableLimit) s.append(c)
          else s.append("\\u%04twitter".format(c.toInt))
        case Some(special) => s.append('\\').append(special)
      }
      i += 1
    }
    s.append('"').result
  }
}
