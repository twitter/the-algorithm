package com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.ClearCac T  l ne nstruct on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neEntry
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

case class ClearCac  nstruct onBu lder[Query <: P pel neQuery](
  overr de val  nclude nstruct on:  nclude nstruct on[Query] = Always nclude)
    extends Urt nstruct onBu lder[Query, ClearCac T  l ne nstruct on] {

  overr de def bu ld(
    query: Query,
    entr es: Seq[T  l neEntry]
  ): Seq[ClearCac T  l ne nstruct on] =
     f ( nclude nstruct on(query, entr es)) Seq(ClearCac T  l ne nstruct on()) else Seq.empty
}
