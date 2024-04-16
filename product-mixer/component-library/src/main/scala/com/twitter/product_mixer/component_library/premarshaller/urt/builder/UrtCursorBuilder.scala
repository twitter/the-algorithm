package com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder

 mport com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder.UrtCursorBu lder.DefaultSort ndex
 mport com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder.UrtCursorBu lder.NextPageTopCursorEntryOffset
 mport com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder.UrtCursorBu lder.UrtEntryOffset
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neEntry
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.operat on.BottomCursor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.operat on.Cursor em
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.operat on.CursorOperat on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.operat on.CursorType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.operat on.GapCursor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.operat on.TopCursor
 mport com.tw ter.product_m xer.core.p pel ne.HasP pel neCursor
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.p pel ne.UrtP pel neCursor
 mport com.tw ter.product_m xer.core.ut l.Sort ndexBu lder

object UrtCursorBu lder {
  val NextPageTopCursorEntryOffset = 1L
  val UrtEntryOffset = 1L
  val DefaultSort ndex = (query: P pel neQuery) => Sort ndexBu lder.t  To d(query.queryT  )
}

tra  UrtCursorBu lder[-Query <: P pel neQuery] {

  val  ncludeOperat on:  nclude nstruct on[Query] = Always nclude

  def cursorType: CursorType
  def cursorValue(query: Query, entr es: Seq[T  l neEntry]): Str ng

  /**
   *  dent f er of an *ex st ng* t  l ne cursor that t  new cursor would replace,  f t  cursor
   *  s returned  n a `ReplaceEntry` t  l ne  nstruct on.
   *
   * Note:
   *   - T   d  s used to populate t  `entry dToReplace` f eld on t  URT T  l neEntry
   *     generated. More deta ls at [[CursorOperat on.entry dToReplace]].
   *   - As a convent on,   use t  sort ndex of t  cursor for  s  d/entry d f elds. So t 
   *     ` dToReplace` should represent t  sort ndex of t  ex st ng cursor to be replaced.
   */
  def  dToReplace(query: Query): Opt on[Long] = None

  def cursorSort ndex(query: Query, entr es: Seq[T  l neEntry]): Long =
    (query, cursorType) match {
      case (query: P pel neQuery w h HasP pel neCursor[_], TopCursor) =>
        topCursorSort ndex(query, entr es)
      case (query: P pel neQuery w h HasP pel neCursor[_], BottomCursor | GapCursor) =>
        bottomCursorSort ndex(query, entr es)
      case _ =>
        throw new UnsupportedOperat onExcept on(
          "Automat c sort  ndex support l m ed to top and bottom cursors")
    }

  def bu ld(query: Query, entr es: Seq[T  l neEntry]): Opt on[CursorOperat on] = {
     f ( ncludeOperat on(query, entr es)) {
      val sort ndex = cursorSort ndex(query, entr es)

      val cursorOperat on = CursorOperat on(
         d = sort ndex,
        sort ndex = So (sort ndex),
        value = cursorValue(query, entr es),
        cursorType = cursorType,
        d splayTreat nt = None,
         dToReplace =  dToReplace(query),
      )

      So (cursorOperat on)
    } else None
  }

  /**
   * Bu ld t  top cursor sort  ndex wh ch handles t  follow ng cases:
   * 1. W n t re  s at least one non-cursor entry, use t  f rst entry's sort  ndex + UrtEntryOffset
   * 2. W n t re are no non-cursor entr es, and  n  alSort ndex  s not set wh ch  nd cates that
   *       s t  f rst page, use DefaultSort ndex + UrtEntryOffset
   * 3. W n t re are no non-cursor entr es, and  n  alSort ndex  s set wh ch  nd cates that    s
   *    not t  f rst page, use t  query. n  alSort ndex from t  passed- n cursor + UrtEntryOffset
   */
  protected def topCursorSort ndex(
    query: P pel neQuery w h HasP pel neCursor[_],
    entr es: Seq[T  l neEntry]
  ): Long = {
    val nonCursorEntr es = entr es.f lter {
      case _: CursorOperat on => false
      case _: Cursor em => false
      case _ => true
    }

    lazy val  n  alSort ndex =
      UrtP pel neCursor.getCursor n  alSort ndex(query).getOrElse(DefaultSort ndex(query))

    nonCursorEntr es. adOpt on.flatMap(_.sort ndex).getOrElse( n  alSort ndex) + UrtEntryOffset
  }

  /**
   * Spec f es t  po nt at wh ch t  next page's entr es' sort  nd ces w ll start count ng.
   *
   * Note that  n t  case of URT, t  next page's entr es' does not  nclude t  top cursor. As
   * such, t  value of  n  alSort ndex passed back  n t  cursor  s typ cally t  bottom cursor's
   * sort  ndex - 2. Subtract ng 2 leaves room for t  next page's top cursor, wh ch w ll have a
   * sort  ndex of top entry + 1.
   */
  protected def nextBottom n  alSort ndex(
    query: P pel neQuery w h HasP pel neCursor[_],
    entr es: Seq[T  l neEntry]
  ): Long = {
    bottomCursorSort ndex(query, entr es) - NextPageTopCursorEntryOffset - UrtEntryOffset
  }

  /**
   * Bu ld t  bottom cursor sort  ndex wh ch handles t  follow ng cases:
   * 1. W n t re  s at least one non-cursor entry, use t  last entry's sort  ndex - UrtEntryOffset
   * 2. W n t re are no non-cursor entr es, and  n  alSort ndex  s not set wh ch  nd cates that
   *       s t  f rst page, use DefaultSort ndex
   * 3. W n t re are no non-cursor entr es, and  n  alSort ndex  s set wh ch  nd cates that    s
   *    not t  f rst page, use t  query. n  alSort ndex from t  passed- n cursor
   */
  protected def bottomCursorSort ndex(
    query: P pel neQuery w h HasP pel neCursor[_],
    entr es: Seq[T  l neEntry]
  ): Long = {
    val nonCursorEntr es = entr es.f lter {
      case _: CursorOperat on => false
      case _: Cursor em => false
      case _ => true
    }

    lazy val  n  alSort ndex =
      UrtP pel neCursor.getCursor n  alSort ndex(query).getOrElse(DefaultSort ndex(query))

    nonCursorEntr es.lastOpt on
      .flatMap(_.sort ndex).map(_ - UrtEntryOffset).getOrElse( n  alSort ndex)
  }
}
