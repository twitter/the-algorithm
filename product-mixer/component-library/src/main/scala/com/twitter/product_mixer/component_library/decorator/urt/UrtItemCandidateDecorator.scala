package com.tw ter.product_m xer.component_l brary.decorator.urt

 mport com.tw ter.product_m xer.component_l brary.model.presentat on.urt.Urt emPresentat on
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.common. dent f er.Decorator dent f er
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l ne em
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.Cand dateDecorator
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.Decorat on
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.Cand dateUrtEntryBu lder
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.st ch.St ch

/**
 * Decorator that w ll apply t  prov ded [[Cand dateUrtEntryBu lder]] to each cand date  ndependently to make a [[T  l ne em]]
 */
case class Urt emCand dateDecorator[
  Query <: P pel neQuery,
  Bu lder nput <: Un versalNoun[Any],
  Bu lderOutput <: T  l ne em
](
  bu lder: Cand dateUrtEntryBu lder[Query, Bu lder nput, Bu lderOutput],
  overr de val  dent f er: Decorator dent f er = Decorator dent f er("Urt emCand date"))
    extends Cand dateDecorator[Query, Bu lder nput] {

  overr de def apply(
    query: Query,
    cand dates: Seq[Cand dateW hFeatures[Bu lder nput]]
  ): St ch[Seq[Decorat on]] = {
    val cand datePresentat ons = cand dates.map { cand date =>
      val  emPresentat on = Urt emPresentat on(
        t  l ne em = bu lder(query, cand date.cand date, cand date.features)
      )

      Decorat on(cand date.cand date,  emPresentat on)
    }

    St ch.value(cand datePresentat ons)
  }
}
