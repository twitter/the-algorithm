package com.tw ter.t etyp e
package hydrator

 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.core.T etData
 mport com.tw ter.t etyp e.core.ValueState
 mport com.tw ter.t etyp e.repos ory.T etQuery
 mport com.tw ter.t etyp e.thr ftscala.ent  es. mpl c s._
 mport com.tw ter.t etyp e.thr ftscala.TextRange
 mport com.tw ter.t etyp e.t ettext.Offset
 mport com.tw ter.t etyp e.t ettext.TextMod f cat on
 mport com.tw ter.t etyp e.t ettext.T etText
 mport com.tw ter.t etyp e.ut l.T etLenses

object NoteT etSuff xHydrator {

  val ELL PS S: Str ng = "\u2026"

  pr vate def addTextSuff x(t et: T et): T et = {
    val or g nalText = T etLenses.text(t et)
    val or g nalTextLength = T etText.codePo ntLength(or g nalText)

    val v s bleTextRange: TextRange =
      T etLenses
        .v s bleTextRange(t et)
        .getOrElse(TextRange(0, or g nalTextLength))

    val  nsertAtCodePo nt = Offset.CodePo nt(v s bleTextRange.to ndex)

    val textMod f cat on = TextMod f cat on. nsertAt(
      or g nalText,
       nsertAtCodePo nt,
      ELL PS S
    )

    val  d aEnt  es = T etLenses. d a(t et)
    val urlEnt  es = T etLenses.urls(t et)

    val mod f edText = textMod f cat on.updated
    val mod f ed d aEnt  es = textMod f cat on.re ndexEnt  es( d aEnt  es)
    val mod f edUrlEnt  es = textMod f cat on.re ndexEnt  es(urlEnt  es)
    val mod f edV s bleTextRange = v s bleTextRange.copy(to ndex =
      v s bleTextRange.to ndex + T etText.codePo ntLength(ELL PS S))

    val updatedT et =
      Lens.setAll(
        t et,
        T etLenses.text -> mod f edText,
        T etLenses.urls -> mod f edUrlEnt  es.sortBy(_.from ndex),
        T etLenses. d a -> mod f ed d aEnt  es.sortBy(_.from ndex),
        T etLenses.v s bleTextRange -> So (mod f edV s bleTextRange)
      )

    updatedT et
  }

  def apply(): T etDataValueHydrator = {
    ValueHydrator[T etData, T etQuery.Opt ons] { (td, _) =>
      val updatedT et = addTextSuff x(td.t et)
      St ch.value(ValueState.delta(td, td.copy(t et = updatedT et)))
    }.only f { (td, _) =>
      td.t et.noteT et. sDef ned &&
      td.t et.noteT et.flatMap(_. sExpandable).getOrElse(true)
    }
  }
}
