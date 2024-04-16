package com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.AddEntr esT  l ne nstruct on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.Cover
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neEntry
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

/**
 * Bu ld AddEntr es  nstruct on w h spec al handl ng for Covers.
 *
 * Cover Entr es should be collected and transfor d  nto ShowCover  nstruct ons. T se should be
 * f ltered out of t  AddEntr es  nstruct on.   avo d do ng t  as part of t  regular
 * AddEntr es nstruct onBu lder because covers are used only used w n us ng a Fl p P pel ne and
 * detect ng cover entr es takes l near t  .
 */
case class AddEntr esW hShowCover nstruct onBu lder[Query <: P pel neQuery](
  overr de val  nclude nstruct on:  nclude nstruct on[Query] = Always nclude)
    extends Urt nstruct onBu lder[Query, AddEntr esT  l ne nstruct on] {
  overr de def bu ld(
    query: Query,
    entr es: Seq[T  l neEntry]
  ): Seq[AddEntr esT  l ne nstruct on] = {
     f ( nclude nstruct on(query, entr es)) {
      val entr esToAdd = entr es.f lterNot(_. s nstanceOf[Cover])
       f (entr esToAdd.nonEmpty) Seq(AddEntr esT  l ne nstruct on(entr esToAdd)) else Seq.empty
    } else
      Seq.empty
  }
}
