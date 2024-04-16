package com.tw ter.product_m xer.core.funct onal_component.decorator

 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common.Component
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.common. dent f er.Component dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.Decorator dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch

/**
 * [[Cand dateDecorator]] generates a [[com.tw ter.product_m xer.core.model.common.presentat on.Un versalPresentat on]]
 * for Cand dates, wh ch encapsulate  nformat on about how to present t  cand date
 *
 * @see [[https://docb rd.tw ter.b z/product-m xer/funct onal-components.html#cand date-decorator]]
 * @see [[com.tw ter.product_m xer.core.model.common.presentat on.Un versalPresentat on]]
 */
tra  Cand dateDecorator[-Query <: P pel neQuery, -Cand date <: Un versalNoun[Any]]
    extends Component {

  overr de val  dent f er: Decorator dent f er = Cand dateDecorator.DefaultCand dateDecorator d

  /**
   * G ven a Seq of `Cand date`, returns a [[Decorat on]] for cand dates wh ch should be decorated
   *
   * `Cand date`s wh ch aren't decorated can be om ted from t  results
   */
  def apply(
    query: Query,
    cand dates: Seq[Cand dateW hFeatures[Cand date]]
  ): St ch[Seq[Decorat on]]
}

object Cand dateDecorator {
  pr vate[core] val DefaultCand dateDecorator d: Decorator dent f er =
    Decorator dent f er(Component dent f er.BasedOnParentComponent)

  /**
   * For use w n bu ld ng a [[Cand dateDecorator]]  n a [[com.tw ter.product_m xer.core.p pel ne.P pel neBu lder]]
   * to ensure that t   dent f er  s updated w h t  parent [[com.tw ter.product_m xer.core.p pel ne.P pel ne. dent f er]]
   */
  pr vate[core] def copyW hUpdated dent f er[
    Query <: P pel neQuery,
    Cand date <: Un versalNoun[Any]
  ](
    decorator: Cand dateDecorator[Query, Cand date],
    parent dent f er: Component dent f er
  ): Cand dateDecorator[Query, Cand date] = {
     f (decorator. dent f er == DefaultCand dateDecorator d) {
      new Cand dateDecorator[Query, Cand date] {
        overr de val  dent f er: Decorator dent f er = Decorator dent f er(parent dent f er.na )
        overr de def apply(
          query: Query,
          cand dates: Seq[Cand dateW hFeatures[Cand date]]
        ): St ch[Seq[Decorat on]] = decorator.apply(query, cand dates)
      }
    } else {
      decorator
    }
  }
}
