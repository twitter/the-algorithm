package com.tw ter.product_m xer.component_l brary.selector

 mport com.tw ter.product_m xer.component_l brary.model.cand date.CursorCand date
 mport com.tw ter.product_m xer.core.funct onal_component.common.Cand dateScope
 mport com.tw ter.product_m xer.core.funct onal_component.common.Spec f cP pel nes
 mport com.tw ter.product_m xer.core.funct onal_component.selector.Selector
 mport com.tw ter.product_m xer.core.funct onal_component.selector.SelectorResult
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.common.presentat on.ModuleCand dateW hDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.t  l nes.conf gap .Param

/**
 * Drop t  module from t  `result`  f   doesn't conta n enough  em cand dates.
 *
 * For example, for a g ven module,  f m nResultsParam  s 3, and t  results conta n 2  ems,
 * t n that module w ll be ent rely dropped from t  results.
 */
case class DropModuleTooFewModule emResults(
  cand dateP pel ne: Cand dateP pel ne dent f er,
  m nModule emsParam: Param[ nt])
    extends Selector[P pel neQuery] {

  overr de val p pel neScope: Cand dateScope = Spec f cP pel nes(cand dateP pel ne)

  overr de def apply(
    query: P pel neQuery,
    rema n ngCand dates: Seq[Cand dateW hDeta ls],
    result: Seq[Cand dateW hDeta ls]
  ): SelectorResult = {
    val m nModule emSelect ons = query.params(m nModule emsParam)
    assert(m nModule emSelect ons > 0, "M n results must be greater than zero")

    val updatedResults = result.f lter {
      case module: ModuleCand dateW hDeta ls
           f p pel neScope.conta ns(module) && module.cand dates.count { cand dateW hDeta ls =>
            !cand dateW hDeta ls.cand date. s nstanceOf[CursorCand date]
          } < m nModule emSelect ons =>
        false
      case _ => true
    }

    SelectorResult(rema n ngCand dates = rema n ngCand dates, result = updatedResults)
  }
}
