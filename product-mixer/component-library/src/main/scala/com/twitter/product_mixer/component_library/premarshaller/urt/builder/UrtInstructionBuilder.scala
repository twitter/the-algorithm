package com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neEntry
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l ne nstruct on
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

tra  Urt nstruct onBu lder[-Query <: P pel neQuery, + nstruct on <: T  l ne nstruct on] {

  def  nclude nstruct on:  nclude nstruct on[Query] = Always nclude

  def bu ld(
    query: Query,
    entr es: Seq[T  l neEntry]
  ): Seq[ nstruct on]
}
