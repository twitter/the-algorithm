package com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder

 mport com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder.UrtCursorUpdater.getCursorByType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neEntry
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.operat on.CursorOperat on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.operat on.CursorType
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

object UrtCursorUpdater {

  def getCursorByType(
    entr es: Seq[T  l neEntry],
    cursorType: CursorType
  ): Opt on[CursorOperat on] = {
    entr es.collectF rst {
      case cursor: CursorOperat on  f cursor.cursorType == cursorType => cursor
    }
  }
}

//  f a CursorCand date  s returned by a Cand date S ce, use t  tra  to update that Cursor as
// necessary (as opposed to bu ld ng a new cursor wh ch  s done w h t  UrtCursorBu lder)
tra  UrtCursorUpdater[-Query <: P pel neQuery] extends UrtCursorBu lder[Query] { self =>

  def getEx st ngCursor(entr es: Seq[T  l neEntry]): Opt on[CursorOperat on] = {
    getCursorByType(entr es, self.cursorType)
  }

  def update(query: Query, entr es: Seq[T  l neEntry]): Seq[T  l neEntry] = {
     f ( ncludeOperat on(query, entr es)) {
      getEx st ngCursor(entr es)
        .map { ex st ngCursor =>
          // Safe .get because  ncludeOperat on()  s shared  n t  context
          // bu ld()  thod creates a new CursorOperat on.   copy over t  ` dToReplace`
          // from t  ex st ng cursor.
          val newCursor =
            bu ld(query, entr es).get
              .copy( dToReplace = ex st ngCursor. dToReplace)

          entr es.f lterNot(_ == ex st ngCursor) :+ newCursor
        }.getOrElse(entr es)
    } else entr es
  }
}
