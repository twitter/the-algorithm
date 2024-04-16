package com.tw ter.ho _m xer.funct onal_component.selector

 mport com.tw ter.product_m xer.component_l brary.model.presentat on.urt.UrtModulePresentat on
 mport com.tw ter.product_m xer.core.funct onal_component.common.Cand dateScope
 mport com.tw ter.product_m xer.core.funct onal_component.common.Cand dateScope.Part  onedCand dates
 mport com.tw ter.product_m xer.core.funct onal_component.selector.Selector
 mport com.tw ter.product_m xer.core.funct onal_component.selector.SelectorResult
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.common.presentat on.ModuleCand dateW hDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

/**
 * T  selector updates t   d of t  conversat on modules to be t   ad of t  module's  d.
 */
case class UpdateConversat onModule d(
  overr de val p pel neScope: Cand dateScope)
    extends Selector[P pel neQuery] {

  overr de def apply(
    query: P pel neQuery,
    rema n ngCand dates: Seq[Cand dateW hDeta ls],
    result: Seq[Cand dateW hDeta ls]
  ): SelectorResult = {
    val Part  onedCand dates(selectedCand dates, ot rCand dates) =
      p pel neScope.part  on(rema n ngCand dates)

    val updatedCand dates = selectedCand dates.map {
      case module @ ModuleCand dateW hDeta ls(cand dates, presentat onOpt, _) =>
        val updatedPresentat on = presentat onOpt.map {
          case urtModule @ UrtModulePresentat on(t  l neModule) =>
            urtModule.copy(t  l neModule =
              t  l neModule.copy( d = cand dates. ad.cand date dLong))
        }
        module.copy(presentat on = updatedPresentat on)
      case cand date => cand date
    }

    SelectorResult(rema n ngCand dates = updatedCand dates ++ ot rCand dates, result = result)
  }
}
