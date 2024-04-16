package com.tw ter.ho _m xer.marshaller.t  l nes

 mport com.tw ter.product_m xer.component_l brary.model.cursor.UrtUnorderedExclude dsCursor
 mport com.tw ter.t  l nes.serv ce.{thr ftscala => t}
 mport com.tw ter.ut l.T  

object Recom ndedUsersCursorUnmarshaller {

  def apply(requestCursor: t.RequestCursor): Opt on[UrtUnorderedExclude dsCursor] = {
    requestCursor match {
      case t.RequestCursor.Recom ndedUsersCursor(cursor) =>
        So (
          UrtUnorderedExclude dsCursor(
             n  alSort ndex = cursor.m nSort ndex.getOrElse(T  .now. nM ll seconds),
            excluded ds = cursor.prev ouslyRecom ndedUser ds
          ))
      case _ => None
    }
  }
}
