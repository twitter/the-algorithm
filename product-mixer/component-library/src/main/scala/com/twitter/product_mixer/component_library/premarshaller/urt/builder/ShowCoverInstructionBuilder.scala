package com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.ShowCover nstruct on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.Cover
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neEntry
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

case class ShowCover nstruct onBu lder[Query <: P pel neQuery](
  overr de val  nclude nstruct on:  nclude nstruct on[Query] = Always nclude)
    extends Urt nstruct onBu lder[Query, ShowCover nstruct on] {
  overr de def bu ld(
    query: Query,
    entr es: Seq[T  l neEntry]
  ): Seq[ShowCover nstruct on] = {
     f ( nclude nstruct on(query, entr es)) {
      // Currently only one cover  s supported per response
      entr es.collectF rst {
        case coverEntry: Cover => ShowCover nstruct on(coverEntry)
      }.toSeq
    } else {
      Seq.empty
    }
  }
}
