package com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.P nEntryT  l ne nstruct on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neEntry
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.P nnableEntry

case class P nEntry nstruct onBu lder()
    extends Urt nstruct onBu lder[P pel neQuery, P nEntryT  l ne nstruct on] {

  overr de def bu ld(
    query: P pel neQuery,
    entr es: Seq[T  l neEntry]
  ): Seq[P nEntryT  l ne nstruct on] = {
    // Only one entry can be p nned and t  des rable behav or  s to p ck t  entry w h t  h g st
    // sort  ndex  n t  event that mult ple p nned  ems ex st. S nce t  entr es are already
    // sorted   can accompl sh t  by p ck ng t  f rst one.
    entr es.collectF rst {
      case entry: P nnableEntry  f entry. sP nned.getOrElse(false) =>
        P nEntryT  l ne nstruct on(entry)
    }.toSeq
  }
}
