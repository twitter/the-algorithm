package com.tw ter.product_m xer.component_l brary.premarshaller.sl ce.bu lder

 mport com.tw ter.product_m xer.core.model.marshall ng.response.sl ce.Cursor em
 mport com.tw ter.product_m xer.core.model.marshall ng.response.sl ce.CursorType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.sl ce.Sl ce em
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

tra  Sl ceCursorBu lder[-Query <: P pel neQuery] {

  val  ncludeOperat on: Should nclude[Query] = Always nclude

  def cursorValue(query: Query,  ems: Seq[Sl ce em]): Str ng
  def cursorType: CursorType

  def bu ld(query: Query, entr es: Seq[Sl ce em]): Opt on[Cursor em] = {
     f ( ncludeOperat on(query, entr es)) {
      So (
        Cursor em(
          cursorType = cursorType,
          value = cursorValue(query, entr es)
        ))
    } else None
  }
}
