package com.tw ter.product_m xer.component_l brary.premarshaller.sl ce.bu lder

 mport com.tw ter.product_m xer.core.model.marshall ng.response.sl ce.Cursor em
 mport com.tw ter.product_m xer.core.model.marshall ng.response.sl ce.NextCursor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.sl ce.GapCursor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.sl ce.Prev ousCursor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.sl ce.Sl ce
 mport com.tw ter.product_m xer.core.model.marshall ng.response.sl ce.Sl ce nfo
 mport com.tw ter.product_m xer.core.model.marshall ng.response.sl ce.Sl ce em
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lure
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.UnexpectedCand date nMarshaller

tra  Sl ceBu lder[-Query <: P pel neQuery] {
  def cursorBu lders: Seq[Sl ceCursorBu lder[Query]]
  def cursorUpdaters: Seq[Sl ceCursorUpdater[Query]]

  pr vate def conta nsGapCursor( ems: Seq[Sl ce em]): Boolean =
     ems.collectF rst { case Cursor em(_, GapCursor) => () }.nonEmpty

  f nal def bu ldSl ce(query: Query,  ems: Seq[Sl ce em]): Sl ce = {
    val bu ltCursors = cursorBu lders.flatMap(_.bu ld(query,  ems))

    //  erate over t  cursorUpdaters  n t  order t y  re def ned. Note that each updater w ll
    // be passed t   ems updated by t  prev ous cursorUpdater.
    val updated ems = cursorUpdaters.foldLeft( ems) { ( ems, cursorUpdater) =>
      cursorUpdater.update(query,  ems)
    } ++ bu ltCursors

    val (cursors, nonCursor ems) = updated ems.part  on(_. s nstanceOf[Cursor em])
    val nextCursor = cursors.collectF rst {
      case cursor @ Cursor em(_, NextCursor) => cursor.value
    }
    val prev ousCursor = cursors.collectF rst {
      case cursor @ Cursor em(_, Prev ousCursor) => cursor.value
    }

    /**
     *  dent fy w t r a [[GapCursor]]  s present and g ve as much deta l to po nt to w re   ca  from
     * S nce t   s already a fatal error case for t  request,  s okay to be a l tle expens ve to get
     * t  best error  ssage poss ble for debug purposes.
     */
     f (conta nsGapCursor(cursors)) {
      val errorDeta ls =
         f (conta nsGapCursor(bu ltCursors)) {
          "T   ans one of y  `cursorBu lders` returned a GapCursor."
        } else  f (conta nsGapCursor( ems)) {
          "T   ans one of y  `Cand dateDecorator`s decorated a Cand date w h a GapCursor."
        } else {
          "T   ans one of y  `cursorUpdaters` returned a GapCursor."
        }
      throw P pel neFa lure(
        UnexpectedCand date nMarshaller,
        s"Sl ceBu lder does not support GapCursors but one was g ven. $errorDeta ls"
      )
    }

    Sl ce(
       ems = nonCursor ems,
      sl ce nfo = Sl ce nfo(prev ousCursor = prev ousCursor, nextCursor = nextCursor))
  }
}
