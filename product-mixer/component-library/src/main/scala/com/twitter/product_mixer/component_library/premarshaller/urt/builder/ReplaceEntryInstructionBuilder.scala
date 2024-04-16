package com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.ReplaceEntryT  l ne nstruct on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neEntry
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.operat on.CursorOperat on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.operat on.CursorType
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

/**
 * Selects one or more [[T  l neEntry]]  nstance from t   nput t  l ne entr es.
 *
 * @tparam Query T  doma n model for t  [[P pel neQuery]] used as  nput.
 */
tra  Entr esToReplace[-Query <: P pel neQuery] {
  def apply(query: Query, entr es: Seq[T  l neEntry]): Seq[T  l neEntry]
}

/**
 * Selects all entr es w h a non-empty val d entry dToReplace.
 *
 * @note t  w ll result  n mult ple [[ReplaceEntryT  l ne nstruct on]]s
 */
case object ReplaceAllEntr es extends Entr esToReplace[P pel neQuery] {
  def apply(query: P pel neQuery, entr es: Seq[T  l neEntry]): Seq[T  l neEntry] =
    entr es.f lter(_.entry dToReplace. sDef ned)
}

/**
 * Selects a replaceable URT [[CursorOperat on]] from t  t  l ne entr es, that matc s t 
 *  nput cursorType.
 */
case class ReplaceUrtCursor(cursorType: CursorType) extends Entr esToReplace[P pel neQuery] {
  overr de def apply(query: P pel neQuery, entr es: Seq[T  l neEntry]): Seq[T  l neEntry] =
    entr es.collectF rst {
      case cursorOperat on: CursorOperat on
           f cursorOperat on.cursorType == cursorType && cursorOperat on.entry dToReplace. sDef ned =>
        cursorOperat on
    }.toSeq
}

/**
 * Create a ReplaceEntry  nstruct on
 *
 * @param entr esToReplace   each replace  nstruct on can conta n only one entry. Users spec fy wh ch
 *                           entry to replace us ng [[Entr esToReplace]].  f mult ple entr es are
 *                           spec f ed, mult ple [[ReplaceEntryT  l ne nstruct on]]s w ll be created.
 * @param  nclude nstruct on w t r t   nstruct on should be  ncluded  n t  response
 */
case class ReplaceEntry nstruct onBu lder[Query <: P pel neQuery](
  entr esToReplace: Entr esToReplace[Query],
  overr de val  nclude nstruct on:  nclude nstruct on[Query] = Always nclude)
    extends Urt nstruct onBu lder[Query, ReplaceEntryT  l ne nstruct on] {

  overr de def bu ld(
    query: Query,
    entr es: Seq[T  l neEntry]
  ): Seq[ReplaceEntryT  l ne nstruct on] = {
     f ( nclude nstruct on(query, entr es))
      entr esToReplace(query, entr es).map(ReplaceEntryT  l ne nstruct on)
    else
      Seq.empty
  }
}
