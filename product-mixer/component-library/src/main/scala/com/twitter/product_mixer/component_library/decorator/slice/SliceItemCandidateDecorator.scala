package com.tw ter.product_m xer.component_l brary.decorator.sl ce

 mport com.tw ter.product_m xer.component_l brary.model.cand date.CursorCand date
 mport com.tw ter.product_m xer.component_l brary.model.presentat on.sl ce.Sl ce emPresentat on
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.common. dent f er.Decorator dent f er
 mport com.tw ter.product_m xer.core.model.marshall ng.response.sl ce.Cursor em
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.Cand dateDecorator
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.Decorat on
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.sl ce.bu lder.Cand dateSl ce emBu lder
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.st ch.St ch

/**
 * Adds a [[Decorat on]] for all `cand dates` that are [[CursorCand date]]s
 *
 * @note Only [[CursorCand date]]s get decorated  n [[Sl ce emCand dateDecorator]]
 *       because t  [[com.tw ter.product_m xer.component_l brary.premarshaller.sl ce.Sl ceDoma nMarshaller]]
 *       handles t  undecorated non-[[CursorCand date]] `cand dates` d rectly.
 */
case class Sl ce emCand dateDecorator[Query <: P pel neQuery, Cand date <: Un versalNoun[Any]](
  cursorBu lder: Cand dateSl ce emBu lder[Query, CursorCand date, Cursor em],
  overr de val  dent f er: Decorator dent f er = Decorator dent f er("Sl ce emCand date"))
    extends Cand dateDecorator[Query, Cand date] {

  overr de def apply(
    query: Query,
    cand dates: Seq[Cand dateW hFeatures[Cand date]]
  ): St ch[Seq[Decorat on]] = {
    val cursorPresentat ons = cand dates.collect {
      case Cand dateW hFeatures(cand date: CursorCand date, features) =>
        val cursor em = cursorBu lder(query, cand date, features)
        val presentat on = Sl ce emPresentat on(sl ce em = cursor em)

        Decorat on(cand date, presentat on)
    }

    St ch.value(cursorPresentat ons)
  }
}
