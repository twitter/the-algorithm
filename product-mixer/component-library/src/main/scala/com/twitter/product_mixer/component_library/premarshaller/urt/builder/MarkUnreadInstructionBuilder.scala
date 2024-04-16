package com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.MarkEntr esUnread nstruct on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neEntry
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.MarkUnreadableEntry

/**
 * Bu ld a MarkUnreadEntr es  nstruct on
 *
 * Note that t   mple ntat on currently supports top-level entr es, but not module  em entr es.
 */
case class MarkUnread nstruct onBu lder[Query <: P pel neQuery](
  overr de val  nclude nstruct on:  nclude nstruct on[Query] = Always nclude)
    extends Urt nstruct onBu lder[Query, MarkEntr esUnread nstruct on] {

  overr de def bu ld(
    query: Query,
    entr es: Seq[T  l neEntry]
  ): Seq[MarkEntr esUnread nstruct on] = {
     f ( nclude nstruct on(query, entr es)) {
      val f lteredEntr es = entr es.collect {
        case entry: MarkUnreadableEntry  f entry. sMarkUnread.conta ns(true) =>
          entry.entry dent f er
      }
       f (f lteredEntr es.nonEmpty) Seq(MarkEntr esUnread nstruct on(f lteredEntr es))
      else Seq.empty
    } else {
      Seq.empty
    }
  }
}
