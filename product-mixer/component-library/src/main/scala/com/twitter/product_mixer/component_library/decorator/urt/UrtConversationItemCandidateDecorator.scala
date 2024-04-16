package com.tw ter.product_m xer.component_l brary.decorator.urt

 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.t et.T etCand dateUrt emBu lder
 mport com.tw ter.product_m xer.component_l brary.model.cand date.BaseT etCand date
 mport com.tw ter.product_m xer.component_l brary.model.presentat on.urt.Conversat onModule em
 mport com.tw ter.product_m xer.component_l brary.model.presentat on.urt.Urt emPresentat on
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.Cand dateDecorator
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.Decorat on
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common. dent f er.Decorator dent f er
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.Module emTreeD splay
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch

case class UrtConversat on emCand dateDecorator[
  Query <: P pel neQuery,
  Cand date <: BaseT etCand date
](
  t etCand dateUrt emBu lder: T etCand dateUrt emBu lder[Query, Cand date],
  overr de val  dent f er: Decorator dent f er = Decorator dent f er("UrtConversat on em"))
    extends Cand dateDecorator[Query, Cand date] {

  overr de def apply(
    query: Query,
    cand dates: Seq[Cand dateW hFeatures[Cand date]]
  ): St ch[Seq[Decorat on]] = {
    val cand datePresentat ons = cand dates.v ew.z pW h ndex.map {
      case (cand date,  ndex) =>
        val  emPresentat on = new Urt emPresentat on(
          t  l ne em = t etCand dateUrt emBu lder(
            p pel neQuery = query,
            t etCand date = cand date.cand date,
            cand dateFeatures = cand date.features)
        ) w h Conversat onModule em {
          overr de val treeD splay: Opt on[Module emTreeD splay] = None
          overr de val d spensable: Boolean =  ndex < cand dates.length - 1
        }

        Decorat on(cand date.cand date,  emPresentat on)
    }

    St ch.value(cand datePresentat ons)
  }
}
