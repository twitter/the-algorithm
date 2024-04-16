package com.tw ter.product_m xer.component_l brary.selector

 mport com.tw ter.product_m xer.core.funct onal_component.common.Cand dateScope
 mport com.tw ter.product_m xer.core.funct onal_component.selector.Selector
 mport com.tw ter.product_m xer.core.funct onal_component.selector.SelectorResult
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.t  l nes.conf gap .Param

tra   ncludeSelector[-Query <: P pel neQuery] {
  def apply(
    query: Query,
    rema n ngCand dates: Seq[Cand dateW hDeta ls],
    result: Seq[Cand dateW hDeta ls]
  ): Boolean
}

/**
 * Run [[selector]]  f [[ ncludeSelector]] resolves to true, else no-op t  selector
 */
case class SelectCond  onally[-Query <: P pel neQuery](
  selector: Selector[Query],
   ncludeSelector:  ncludeSelector[Query])
    extends Selector[Query] {

  overr de val p pel neScope: Cand dateScope = selector.p pel neScope

  overr de def apply(
    query: Query,
    rema n ngCand dates: Seq[Cand dateW hDeta ls],
    result: Seq[Cand dateW hDeta ls]
  ): SelectorResult = {
     f ( ncludeSelector(query, rema n ngCand dates, result)) {
      selector(query, rema n ngCand dates, result)
    } else SelectorResult(rema n ngCand dates = rema n ngCand dates, result = result)
  }
}

object SelectCond  onally {

  /**
   * Wrap each [[Selector]]  n `selectors`  n an [[ ncludeSelector]] w h ` ncludeSelector` as t  [[SelectCond  onally. ncludeSelector]]
   */
  def apply[Query <: P pel neQuery](
    selectors: Seq[Selector[Query]],
     ncludeSelector:  ncludeSelector[Query]
  ): Seq[Selector[Query]] =
    selectors.map(SelectCond  onally(_,  ncludeSelector))

  /**
   * A [[SelectCond  onally]] based on a [[Param]]
   */
  def paramGated[Query <: P pel neQuery](
    selector: Selector[Query],
    enabledParam: Param[Boolean],
  ): SelectCond  onally[Query] =
    SelectCond  onally(selector, (query, _, _) => query.params(enabledParam))

  /**
   * Wrap each [[Selector]]  n `selectors`  n a [[SelectCond  onally]] based on a [[Param]]
   */
  def paramGated[Query <: P pel neQuery](
    selectors: Seq[Selector[Query]],
    enabledParam: Param[Boolean],
  ): Seq[Selector[Query]] =
    selectors.map(SelectCond  onally.paramGated(_, enabledParam))

  /**
   * A [[SelectCond  onally]] based on an  nverted [[Param]]
   */
  def paramNotGated[Query <: P pel neQuery](
    selector: Selector[Query],
    enabledParamTo nvert: Param[Boolean],
  ): SelectCond  onally[Query] =
    SelectCond  onally(selector, (query, _, _) => !query.params(enabledParamTo nvert))

  /**
   * Wrap each [[Selector]]  n `selectors`  n a [[SelectCond  onally]] based on an  nverted [[Param]]
   */
  def paramNotGated[Query <: P pel neQuery](
    selectors: Seq[Selector[Query]],
    enabledParamTo nvert: Param[Boolean],
  ): Seq[Selector[Query]] =
    selectors.map(SelectCond  onally.paramNotGated(_, enabledParamTo nvert))
}
