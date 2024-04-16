package com.tw ter.product_m xer.component_l brary.premarshaller.sl ce.bu lder

 mport com.tw ter.product_m xer.component_l brary.premarshaller.sl ce.bu lder.Sl ceCursorUpdater.getCursorByType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.sl ce.Cursor em
 mport com.tw ter.product_m xer.core.model.marshall ng.response.sl ce.CursorType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.sl ce.Sl ce em
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

object Sl ceCursorUpdater {

  def getCursorByType(
     ems: Seq[Sl ce em],
    cursorType: CursorType
  ): Opt on[Cursor em] = {
     ems.collectF rst {
      case cursor: Cursor em  f cursor.cursorType == cursorType => cursor
    }
  }
}

/**
 *  f [[Sl ceCursorBu lder. ncludeOperat on]]  s true and a cursor does ex st  n t  ` ems`,
 * t  w ll run t  t  underly ng [[Sl ceCursorBu lder]] w h t  full ` ems`
 * ( nclud ng all cursors wh ch may be present) t n f lter out only t  or g nally
 * found [[Cursor em]] from t  results). T n append t  new cursor to t  end of t  results.
 *
 *  f   have mult ple cursors that need to be updated,   w ll need to have mult ple updaters.
 *
 *  f a CursorCand date  s returned by a Cand date S ce, use t  tra  to update t  Cursor
 * ( f necessary) and add   to t  end of t  cand dates l st.
 */
tra  Sl ceCursorUpdater[-Query <: P pel neQuery] extends Sl ceCursorBu lder[Query] { self =>

  def getEx st ngCursor( ems: Seq[Sl ce em]): Opt on[Cursor em] = {
    getCursorByType( ems, self.cursorType)
  }

  def update(query: Query,  ems: Seq[Sl ce em]): Seq[Sl ce em] = {
     f ( ncludeOperat on(query,  ems)) {
      getEx st ngCursor( ems)
        .map { ex st ngCursor =>
          // Safe get because  ncludeOperat on()  s shared  n t  context
          val newCursor = bu ld(query,  ems).get

           ems.f lterNot(_ == ex st ngCursor) :+ newCursor
        }.getOrElse( ems)
    } else  ems
  }
}

tra  Sl ceCursorUpdaterFromUnderly ngBu lder[-Query <: P pel neQuery]
    extends Sl ceCursorUpdater[Query] {
  def underly ng: Sl ceCursorBu lder[Query]
  overr de def cursorValue(
    query: Query,
    entr es: Seq[Sl ce em]
  ): Str ng = underly ng.cursorValue(query, entr es)
}
