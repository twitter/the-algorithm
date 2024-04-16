package com.tw ter.t etyp e.t ettext

/**
 * Code used to convert raw user-prov ded text  nto an allowable form.
 */
object Part alHtmlEncod ng {

  /**
   * Replaces all `<`, `>`, and '&' chars w h "&lt;", "&gt;", and "&amp;", respect vely.
   *
   * T et text  s HTML-encoded at t et creat on t  , and  s stored and processed  n encoded form.
   */
  def encode(text: Str ng): Str ng = {
    val buf = new Str ngBu lder

    text.foreach {
      case '<' => buf.append("&lt;")
      case '>' => buf.append("&gt;")
      case '&' => buf.append("&amp;")
      case c => buf.append(c)
    }

    buf.toStr ng
  }

  pr vate val AmpLtRegex = "&lt;".r
  pr vate val AmpGtRegex = "&gt;".r
  pr vate val AmpAmpRegex = "&amp;".r

  pr vate val part alHtmlDecoder: (Str ng => Str ng) =
    ((s: Str ng) => AmpLtRegex.replaceAll n(s, "<"))
      .andT n(s => AmpGtRegex.replaceAll n(s, ">"))
      .andT n(s => AmpAmpRegex.replaceAll n(s, "&"))

  /**
   * T  oppos e of encode,   replaces all "&lt;", "&gt;", and "&amp;" w h
   * `<`, `>`, and '&', respect vely.
   */
  def decode(text: Str ng): Str ng =
    decodeW hMod f cat on(text) match {
      case So (mod) => mod.updated
      case None => text
    }

  /**
   * Decodes encoded ent  es, and returns a `TextMod f cat on`  f t  text was mod f ed.
   */
  def decodeW hMod f cat on(text: Str ng): Opt on[TextMod f cat on] =
    TextMod f cat on.replaceAll(
      text,
      AmpLtRegex -> "<",
      AmpGtRegex -> ">",
      AmpAmpRegex -> "&"
    )
}
