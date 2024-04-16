package com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.AddEntr esT  l ne nstruct on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neEntry
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

/**
 *  erates over all t  [[T  l neEntry]] passed   and creates `addEntry` entr es  n t  URT for
 * any entr es wh ch are not p nned and not replaceable(cursors are replaceable)
 *
 * T   s because p nned entr es always show up  n t  `p nEntry` sect on, and replaceable entr es
 * w ll show up  n t  `replaceEntry` sect on.
 */
case class AddEntr esW hP nnedAndReplace nstruct onBu lder[Query <: P pel neQuery](
  overr de val  nclude nstruct on:  nclude nstruct on[Query] = Always nclude)
    extends Urt nstruct onBu lder[Query, AddEntr esT  l ne nstruct on] {

  overr de def bu ld(
    query: Query,
    entr es: Seq[T  l neEntry]
  ): Seq[AddEntr esT  l ne nstruct on] = {
     f ( nclude nstruct on(query, entr es)) {
      val entr esToAdd = entr es
        .f lterNot(_. sP nned.getOrElse(false))
        .f lter(_.entry dToReplace. sEmpty)
       f (entr esToAdd.nonEmpty) Seq(AddEntr esT  l ne nstruct on(entr esToAdd))
      else Seq.empty
    } else
      Seq.empty
  }
}
