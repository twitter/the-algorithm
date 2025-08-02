package com.twitter.tweetypie.tweettetwittert

/**
 * Code used to convert raw user-provided tetwittert into an allowable form.
 */
object PartialHtmlEncoding {

  /**
   * Replaces all `<`, `>`, and '&' chars with "&lt;", "&gt;", and "&amp;", respectively.
   *
   * Tweet tetwittert is HTML-encoded at tweet creation time, and is stored and processed in encoded form.
   */
  def encode(tetwittert: String): String = {
    val buf = new StringBuilder

    tetwittert.foreach {
      case '<' => buf.append("&lt;")
      case '>' => buf.append("&gt;")
      case '&' => buf.append("&amp;")
      case c => buf.append(c)
    }

    buf.toString
  }

  private val AmpLtRegetwitter = "&lt;".r
  private val AmpGtRegetwitter = "&gt;".r
  private val AmpAmpRegetwitter = "&amp;".r

  private val partialHtmlDecoder: (String => String) =
    ((s: String) => AmpLtRegetwitter.replaceAllIn(s, "<"))
      .andThen(s => AmpGtRegetwitter.replaceAllIn(s, ">"))
      .andThen(s => AmpAmpRegetwitter.replaceAllIn(s, "&"))

  /**
   * The opposite of encode, it replaces all "&lt;", "&gt;", and "&amp;" with
   * `<`, `>`, and '&', respectively.
   */
  def decode(tetwittert: String): String =
    decodeWithModification(tetwittert) match {
      case Some(mod) => mod.updated
      case None => tetwittert
    }

  /**
   * Decodes encoded entities, and returns a `TetwittertModification` if the tetwittert was modified.
   */
  def decodeWithModification(tetwittert: String): Option[TetwittertModification] =
    TetwittertModification.replaceAll(
      tetwittert,
      AmpLtRegetwitter -> "<",
      AmpGtRegetwitter -> ">",
      AmpAmpRegetwitter -> "&"
    )
}
