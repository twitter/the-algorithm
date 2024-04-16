package com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder

 mport com.tw ter.product_m xer.component_l brary.model.cursor.UrtPassThroughCursor
 mport com.tw ter.product_m xer.component_l brary.premarshaller.cursor.UrtCursorSer al zer
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neEntry
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.operat on.CursorType
 mport com.tw ter.product_m xer.core.p pel ne.HasP pel neCursor
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

case class PassThroughCursorBu lder[
  -Query <: P pel neQuery w h HasP pel neCursor[UrtPassThroughCursor]
](
  cursorFeature: Feature[Query, Str ng],
  overr de val cursorType: CursorType)
    extends UrtCursorBu lder[Query] {

  overr de val  ncludeOperat on:  nclude nstruct on[Query] = { (query, _) =>
    query.features.ex sts(_.getOrElse(cursorFeature, "").nonEmpty)
  }

  overr de def cursorValue(
    query: Query,
    entr es: Seq[T  l neEntry]
  ): Str ng =
    UrtCursorSer al zer.ser al zeCursor(
      UrtPassThroughCursor(
        cursorSort ndex(query, entr es),
        query.features.map(_.get(cursorFeature)).getOrElse(""),
        cursorType = So (cursorType)
      )
    )
}
