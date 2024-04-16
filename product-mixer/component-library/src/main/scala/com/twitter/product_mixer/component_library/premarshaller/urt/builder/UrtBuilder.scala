package com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.operat on.CursorOperat on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l ne
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neEntry
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l ne nstruct on
 mport com.tw ter.product_m xer.core.p pel ne.HasP pel neCursor
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.p pel ne.UrtP pel neCursor
 mport com.tw ter.product_m xer.core.ut l.Sort ndexBu lder

tra  UrtBu lder[-Query <: P pel neQuery, + nstruct on <: T  l ne nstruct on] {
  pr vate val T  l ne dSuff x = "-T  l ne"

  def  nstruct onBu lders: Seq[Urt nstruct onBu lder[Query,  nstruct on]]

  def cursorBu lders: Seq[UrtCursorBu lder[Query]]
  def cursorUpdaters: Seq[UrtCursorUpdater[Query]]

  def  tadataBu lder: Opt on[BaseUrt tadataBu lder[Query]]

  // T  l ne entry sort  ndexes w ll count down by t  value. Values h g r than 1 are useful to
  // leave room  n t  sequence for dynam cally  nject ng content  n bet en ex st ng entr es.
  def sort ndexStep:  nt = 1

  f nal def bu ldT  l ne(
    query: Query,
    entr es: Seq[T  l neEntry]
  ): T  l ne = {
    val  n  alSort ndex = get n  alSort ndex(query)

    // Set t  sort  ndexes of t  entr es before   pass t m to t  cursor bu lders, s nce many
    // cursor  mple ntat ons use t  sort  ndex of t  f rst/last entry as part of t  cursor value
    val sort ndexedEntr es = updateSort ndexes( n  alSort ndex, entr es)

    //  erate over t  cursorUpdaters  n t  order t y  re def ned. Note that each updater w ll
    // be passed t  t  l neEntr es updated by t  prev ous cursorUpdater.
    val updatedCursorEntr es: Seq[T  l neEntry] =
      cursorUpdaters.foldLeft(sort ndexedEntr es) { (t  l neEntr es, cursorUpdater) =>
        cursorUpdater.update(query, t  l neEntr es)
      }

    val allCursoredEntr es =
      updatedCursorEntr es ++ cursorBu lders.flatMap(_.bu ld(query, updatedCursorEntr es))

    val  nstruct ons: Seq[ nstruct on] =
       nstruct onBu lders.flatMap(_.bu ld(query, allCursoredEntr es))

    val  tadata =  tadataBu lder.map(_.bu ld(query, allCursoredEntr es))

    T  l ne(
       d = query.product. dent f er.toStr ng + T  l ne dSuff x,
       nstruct ons =  nstruct ons,
       tadata =  tadata
    )
  }

  f nal def get n  alSort ndex(query: Query): Long =
    query match {
      case cursorQuery: HasP pel neCursor[_] =>
        UrtP pel neCursor
          .getCursor n  alSort ndex(cursorQuery)
          .getOrElse(Sort ndexBu lder.t  To d(query.queryT  ))
      case _ => Sort ndexBu lder.t  To d(query.queryT  )
    }

  /**
   * Updates t  sort  ndexes  n t  t  l ne entr es start ng from t  g ven  n  al sort  ndex
   * value and decreas ng by t  value def ned  n t  sort  ndex step f eld
   *
   * @param  n  alSort ndex T   n  al value of t  sort  ndex
   * @param t  l neEntr es T  l ne entr es to update
   */
  f nal def updateSort ndexes(
     n  alSort ndex: Long,
    t  l neEntr es: Seq[T  l neEntry]
  ): Seq[T  l neEntry] = {
    val  ndexRange =
       n  alSort ndex to ( n  alSort ndex - (t  l neEntr es.s ze * sort ndexStep)) by -sort ndexStep

    // Sk p any ex st ng cursors because t  r sort  ndexes w ll be managed by t  r cursor updater.
    //  f t  cursors are not removed f rst, t n t  rema n ng entr es would have a gap everyw re
    // an ex st ng cursor was present.
    val (cursorEntr es, nonCursorEntr es) = t  l neEntr es.part  on {
      case _: CursorOperat on => true
      case _ => false
    }

    nonCursorEntr es.z p( ndexRange).map {
      case (entry,  ndex) =>
        entry.w hSort ndex( ndex)
    } ++ cursorEntr es
  }
}
