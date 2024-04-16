package com.tw ter.product_m xer.core.model.marshall ng.response.urt.operat on

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Cl entEvent nfo
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.FeedbackAct on nfo
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.operat on.CursorOperat on.CursorEntryNa space
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.EntryNa space
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neEntry
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l ne em

/**
 * Cursor em should only be used for Module cursors
 * For t  l ne cursors, see
 * [[com.tw ter.product_m xer.core.model.marshall ng.response.urt.operat on.CursorOperat on]]
 */
case class Cursor em(
  overr de val  d: Long,
  overr de val sort ndex: Opt on[Long],
  overr de val cl entEvent nfo: Opt on[Cl entEvent nfo],
  overr de val feedbackAct on nfo: Opt on[FeedbackAct on nfo],
  value: Str ng,
  cursorType: CursorType,
  d splayTreat nt: Opt on[CursorD splayTreat nt])
    extends T  l ne em {

  overr de val entryNa space: EntryNa space = CursorEntryNa space

  overr de lazy val entry dent f er: Str ng =
    s"$entryNa space-${cursorType.entryNa space}-$ d"

  overr de def w hSort ndex(sort ndex: Long): T  l neEntry = copy(sort ndex = So (sort ndex))
}
