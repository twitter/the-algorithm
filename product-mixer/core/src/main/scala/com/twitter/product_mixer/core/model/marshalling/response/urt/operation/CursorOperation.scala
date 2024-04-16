package com.tw ter.product_m xer.core.model.marshall ng.response.urt.operat on

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.operat on.CursorOperat on.CursorEntryNa space
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.EntryNa space
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neEntry
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neOperat on

object CursorOperat on {
  val CursorEntryNa space = EntryNa space("cursor")

  pr vate def entry dent f er(cursorType: CursorType,  dent f er: Long): Str ng =
    s"$CursorEntryNa space-${cursorType.entryNa space.toStr ng}-$ dent f er"
}

case class CursorOperat on(
  overr de val  d: Long,
  overr de val sort ndex: Opt on[Long],
  value: Str ng,
  cursorType: CursorType,
  d splayTreat nt: Opt on[CursorD splayTreat nt],
   dToReplace: Opt on[Long])
    extends T  l neOperat on {
  overr de val entryNa space: EntryNa space = CursorEntryNa space

  overr de lazy val entry dent f er: Str ng = CursorOperat on.entry dent f er(cursorType,  d)

  overr de def entry dToReplace: Opt on[Str ng] =
     dToReplace.map(CursorOperat on.entry dent f er(cursorType, _))

  overr de def w hSort ndex(sort ndex: Long): T  l neEntry = copy(sort ndex = So (sort ndex))
}
