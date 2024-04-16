package com.tw ter.product_m xer.component_l brary.premarshaller.urt.bu lder

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.AddToModuleT  l ne nstruct on
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neEntry
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neModule
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

case class AddToModule nstruct onBu lder[Query <: P pel neQuery](
  overr de val  nclude nstruct on:  nclude nstruct on[Query] = Always nclude)
    extends Urt nstruct onBu lder[Query, AddToModuleT  l ne nstruct on] {

  overr de def bu ld(
    query: Query,
    entr es: Seq[T  l neEntry]
  ): Seq[AddToModuleT  l ne nstruct on] = {
     f ( nclude nstruct on(query, entr es)) {
      val moduleEntr es = entr es.collect {
        case module: T  l neModule => module
      }
       f (moduleEntr es.nonEmpty) {
        assert(moduleEntr es.s ze == 1, "Currently   only support append ng to one module")
        moduleEntr es. adOpt on.map { moduleEntry =>
          AddToModuleT  l ne nstruct on(
            module ems = moduleEntry. ems,
            moduleEntry d = moduleEntry.entry dent f er,
            // Currently conf gur ng module emEntry d and prepend f elds are not supported.
            module emEntry d = None,
            prepend = None
          )
        }
      }.toSeq
      else Seq.empty
    } else {
      Seq.empty
    }
  }
}
