package com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder

 mport com.tw ter.product_m xer.component_l brary.model.cursor.UrtUnorderedBloomF lterCursor
 mport com.tw ter.product_m xer.component_l brary.premarshaller.cursor.UrtCursorSer al zer
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neEntry
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.operat on.BottomCursor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.operat on.CursorType
 mport com.tw ter.product_m xer.core.p pel ne.HasP pel neCursor
 mport com.tw ter.product_m xer.core.p pel ne.P pel neCursorSer al zer
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.search.common.ut l.bloomf lter.Adapt veLong ntBloomF lterBu lder

/**
 * Bu lds [[UrtUnorderedBloomF lterCursor]]  n t  Bottom pos  on
 *
 * @param  dSelector Spec f es t  entry from wh ch to der ve t  ` d` f eld
 * @param ser al zer Converts t  cursor to an encoded str ng
 */
case class UnorderedBloomF lterBottomCursorBu lder(
   dSelector: Part alFunct on[Un versalNoun[_], Long],
  ser al zer: P pel neCursorSer al zer[UrtUnorderedBloomF lterCursor] = UrtCursorSer al zer)
    extends UrtCursorBu lder[
      P pel neQuery w h HasP pel neCursor[UrtUnorderedBloomF lterCursor]
    ] {

  overr de val cursorType: CursorType = BottomCursor

  overr de def cursorValue(
    query: P pel neQuery w h HasP pel neCursor[UrtUnorderedBloomF lterCursor],
    entr es: Seq[T  l neEntry]
  ): Str ng = {
    val bloomF lter = query.p pel neCursor.map(_.long ntBloomF lter)
    val  ds = entr es.collect( dSelector)

    val cursor = UrtUnorderedBloomF lterCursor(
       n  alSort ndex = nextBottom n  alSort ndex(query, entr es),
      long ntBloomF lter = Adapt veLong ntBloomF lterBu lder.bu ld( ds, bloomF lter)
    )

    ser al zer.ser al zeCursor(cursor)
  }
}
