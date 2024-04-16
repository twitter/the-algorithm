package com.tw ter.product_m xer.component_l brary.selector

 mport com.tw ter.product_m xer.core.funct onal_component.common.Cand dateScope
 mport com.tw ter.product_m xer.core.funct onal_component.common.Spec f cP pel ne
 mport com.tw ter.product_m xer.core.funct onal_component.common.Spec f cP pel nes
 mport com.tw ter.product_m xer.core.funct onal_component.selector.Selector
 mport com.tw ter.product_m xer.core.funct onal_component.selector.SelectorResult
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.t  l nes.conf gap .Param

tra  MaxSelector[-Query <: P pel neQuery] {
  def apply(
    query: Query,
    rema n ngCand dates: Seq[Cand dateW hDeta ls],
    result: Seq[Cand dateW hDeta ls]
  ):  nt
}

object DropMaxCand dates {

  /**
   * A [[DropMaxCand dates]] Selector based on a [[Param]] appl ed to a s ngle cand date p pel ne
   */
  def apply[Query <: P pel neQuery](
    cand dateP pel ne: Cand dateP pel ne dent f er,
    maxSelect onsParam: Param[ nt]
  ) = new DropMaxCand dates[Query](
    Spec f cP pel ne(cand dateP pel ne),
    (query, _, _) => query.params(maxSelect onsParam))

  /**
   * A [[DropMaxCand dates]] Selector based on a [[Param]] w h mult ple cand date p pel nes
   */
  def apply[Query <: P pel neQuery](
    cand dateP pel nes: Set[Cand dateP pel ne dent f er],
    maxSelect onsParam: Param[ nt]
  ) = new DropMaxCand dates[Query](
    Spec f cP pel nes(cand dateP pel nes),
    (query, _, _) => query.params(maxSelect onsParam))

  /**
   * A [[DropMaxCand dates]] Selector based on a [[Param]] that appl es to a [[Cand dateScope]]
   */
  def apply[Query <: P pel neQuery](
    p pel neScope: Cand dateScope,
    maxSelect onsParam: Param[ nt]
  ) = new DropMaxCand dates[Query](p pel neScope, (query, _, _) => query.params(maxSelect onsParam))
}

/**
 * L m  t  number of  em and module (not  ems  ns de modules) cand dates from t 
 * spec f ed p pel nes based on t  value prov ded by t  [[MaxSelector]]
 *
 * For example,  f value from t  [[MaxSelector]]  s 3, and a cand dateP pel ne returned 10  ems
 *  n t  cand date pool, t n t se  ems w ll be reduced to t  f rst 3  ems. Note that to
 * update t  order ng of t  cand dates, an UpdateCand dateOrder ngSelector may be used pr or to
 * us ng t  Selector.
 *
 * Anot r example,  f t  [[MaxSelector]] value  s 3, and a cand dateP pel ne returned 10 modules
 *  n t  cand date pool, t n t se w ll be reduced to t  f rst 3 modules. T   ems  ns de t 
 * modeles w ll not be affected by t  selector. To control t  number of  ems  ns de modules see
 * [[DropMaxModule emCand dates]].
 */
case class DropMaxCand dates[-Query <: P pel neQuery](
  overr de val p pel neScope: Cand dateScope,
  maxSelector: MaxSelector[Query])
    extends Selector[Query] {

  overr de def apply(
    query: Query,
    rema n ngCand dates: Seq[Cand dateW hDeta ls],
    result: Seq[Cand dateW hDeta ls]
  ): SelectorResult = {
    val maxSelect ons = maxSelector(query, rema n ngCand dates, result)
    assert(maxSelect ons > 0, "Max select ons must be greater than zero")

    val rema n ngCand datesL m ed =
      DropSelector.takeUnt l(maxSelect ons, rema n ngCand dates, p pel neScope)

    SelectorResult(rema n ngCand dates = rema n ngCand datesL m ed, result = result)
  }
}
