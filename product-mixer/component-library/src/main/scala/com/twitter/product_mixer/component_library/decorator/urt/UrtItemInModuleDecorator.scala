package com.tw ter.product_m xer.component_l brary.decorator.urt

 mport com.tw ter.product_m xer.component_l brary.model.presentat on.urt.Urt emPresentat on
 mport com.tw ter.product_m xer.component_l brary.model.presentat on.urt.UrtModulePresentat on
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.common. dent f er.Decorator dent f er
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l ne em
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.Cand dateDecorator
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.Decorat on
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.t  l ne_module.BaseT  l neModuleBu lder
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.st ch.St ch

/**
 * Decorator that w ll apply t  prov ded [[urt emCand dateDecorator]] to all t  `cand dates` and apply
 * t  sa  [[UrtModulePresentat on]] from [[moduleBu lder]] to each Cand date.
 */
case class Urt em nModuleDecorator[
  Query <: P pel neQuery,
  Bu lder nput <: Un versalNoun[Any],
  Bu lderOutput <: T  l ne em
](
  urt emCand dateDecorator: Cand dateDecorator[Query, Bu lder nput],
  moduleBu lder: BaseT  l neModuleBu lder[Query, Bu lder nput],
  overr de val  dent f er: Decorator dent f er = Decorator dent f er("Urt em nModule"))
    extends Cand dateDecorator[Query, Bu lder nput] {

  overr de def apply(
    query: Query,
    cand dates: Seq[Cand dateW hFeatures[Bu lder nput]]
  ): St ch[Seq[Decorat on]] = {
     f (cand dates.nonEmpty) {
      val urt emCand datesW hDecorat on = urt emCand dateDecorator(query, cand dates)

      // Pass cand dates to support w n t  module  s constructed dynam cally based on t  l st
      val modulePresentat on =
        UrtModulePresentat on(moduleBu lder(query, cand dates))

      urt emCand datesW hDecorat on.map { cand dates =>
        cand dates.collect {
          case Decorat on(cand date, urt emPresentat on: Urt emPresentat on) =>
            Decorat on(
              cand date,
              urt emPresentat on.copy(modulePresentat on = So (modulePresentat on)))
        }
      }
    } else {
      St ch.N l
    }
  }
}
