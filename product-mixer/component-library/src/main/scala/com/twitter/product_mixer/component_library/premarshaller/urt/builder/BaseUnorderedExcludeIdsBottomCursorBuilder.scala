package com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder

 mport com.tw ter.product_m xer.component_l brary.model.cursor.UrtUnorderedExclude dsCursor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neEntry
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.operat on.BottomCursor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.operat on.CursorType
 mport com.tw ter.product_m xer.core.p pel ne.HasP pel neCursor
 mport com.tw ter.product_m xer.core.p pel ne.P pel neCursorSer al zer
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.t  l nes.conf gap .Param

tra  BaseUnorderedExclude dsBottomCursorBu lder
    extends UrtCursorBu lder[
      P pel neQuery w h HasP pel neCursor[UrtUnorderedExclude dsCursor]
    ] {

  def excluded dsMaxLengthParam: Param[ nt]

  def excludeEntr esCollector(entr es: Seq[T  l neEntry]): Seq[Long]

  def ser al zer: P pel neCursorSer al zer[UrtUnorderedExclude dsCursor]

  overr de val cursorType: CursorType = BottomCursor

  overr de def cursorValue(
    query: P pel neQuery w h HasP pel neCursor[UrtUnorderedExclude dsCursor],
    entr es: Seq[T  l neEntry]
  ): Str ng = {
    val excluded dsMaxLength = query.params(excluded dsMaxLengthParam)
    assert(excluded dsMaxLength > 0, "Excluded  Ds max length must be greater than zero")

    val newEntry ds = excludeEntr esCollector(entr es)
    assert(
      newEntry ds.length < excluded dsMaxLength,
      "New entry  Ds length must be smaller than excluded  Ds max length")

    val excluded ds = query.p pel neCursor
      .map(_.excluded ds ++ newEntry ds)
      .getOrElse(newEntry ds)
      .takeR ght(excluded dsMaxLength)

    val cursor = UrtUnorderedExclude dsCursor(
       n  alSort ndex = nextBottom n  alSort ndex(query, entr es),
      excluded ds = excluded ds
    )

    ser al zer.ser al zeCursor(cursor)
  }
}
