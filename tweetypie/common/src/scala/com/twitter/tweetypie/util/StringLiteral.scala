package com.tw ter.t etyp e.ut l

/**
 * Escape a Str ng  nto Java or Scala Str ng l eral syntax (adds t 
 * surround ng quotes.)
 *
 * T   s pr mar ly for pr nt ng Str ngs for debugg ng or logg ng.
 */
object Str ngL eral extends (Str ng => Str ng) {
  pr vate[t ] val ControlL m  = ' '
  pr vate[t ] val Pr ntableL m  = '\u007e'
  pr vate[t ] val Spec als =
    Map('\n' -> 'n', '\r' -> 'r', '\t' -> 't', '"' -> '"', '\'' -> '\'', '\\' -> '\\')

  def apply(str: Str ng): Str ng = {
    val s = new Str ngBu lder(str.length)
    s.append('"')
    var   = 0
    wh le (  < str.length) {
      val c = str( )
      Spec als.get(c) match {
        case None =>
           f (c >= ControlL m  && c <= Pr ntableL m ) s.append(c)
          else s.append("\\u%04x".format(c.to nt))
        case So (spec al) => s.append('\\').append(spec al)
      }
        += 1
    }
    s.append('"').result
  }
}
