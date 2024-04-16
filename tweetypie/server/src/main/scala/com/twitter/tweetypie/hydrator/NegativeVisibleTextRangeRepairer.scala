package com.tw ter.t etyp e
package hydrator

 mport com.tw ter.t etyp e.thr ftscala.TextRange

/**
 * So  t ets w h v s bleTextRange may have from ndex > to ndex,  n wh ch case set from ndex
 * to to ndex.
 */
object Negat veV s bleTextRangeRepa rer {
  pr vate val mutat on =
    Mutat on[Opt on[TextRange]] {
      case So (TextRange(from, to))  f from > to => So (So (TextRange(to, to)))
      case _ => None
    }

  pr vate[t etyp e] val t etMutat on = T etLenses.v s bleTextRange.mutat on(mutat on)
}
