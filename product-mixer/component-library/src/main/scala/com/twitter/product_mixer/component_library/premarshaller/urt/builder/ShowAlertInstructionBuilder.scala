package com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.ShowAlert
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.ShowAlert nstruct on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neEntry
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

case class ShowAlert nstruct onBu lder[Query <: P pel neQuery](
  overr de val  nclude nstruct on:  nclude nstruct on[Query] = Always nclude)
    extends Urt nstruct onBu lder[Query, ShowAlert nstruct on] {

  overr de def bu ld(
    query: Query,
    entr es: Seq[T  l neEntry]
  ): Seq[ShowAlert nstruct on] = {
     f ( nclude nstruct on(query, entr es)) {
      // Currently only one Alert  s supported per response
      entr es.collectF rst {
        case alertEntry: ShowAlert => ShowAlert nstruct on(alertEntry)
      }.toSeq
    } else Seq.empty
  }
}
