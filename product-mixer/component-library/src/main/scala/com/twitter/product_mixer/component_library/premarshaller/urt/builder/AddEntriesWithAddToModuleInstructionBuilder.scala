package com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.AddEntr esT  l ne nstruct on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neEntry
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neModule
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

/**
 * Bu ld t  AddEntr es  nstruct on w h spec al handl ng for AddToModule entr es.
 *
 * Entr es wh ch are go ng to be added to a module are go ng to be added v a
 * AddToModule nstruct onBu lder, for ot r entr es  n t  sa  response (l ke cursor entr es)  
 * st ll need an AddEntr esT  l ne nstruct on wh ch  s go ng to be created by t  bu lder.
 */
case class AddEntr esW hAddToModule nstruct onBu lder[Query <: P pel neQuery](
  overr de val  nclude nstruct on:  nclude nstruct on[Query] = Always nclude)
    extends Urt nstruct onBu lder[Query, AddEntr esT  l ne nstruct on] {

  overr de def bu ld(
    query: Query,
    entr es: Seq[T  l neEntry]
  ): Seq[AddEntr esT  l ne nstruct on] = {
     f ( nclude nstruct on(query, entr es)) {
      val entr esToAdd = entr es.f lter {
        case _: T  l neModule => false
        case _ => true
      }
       f (entr esToAdd.nonEmpty) Seq(AddEntr esT  l ne nstruct on(entr esToAdd))
      else Seq.empty
    } else
      Seq.empty
  }
}
