package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.contextual_ref

 mport com.tw ter.product_m xer.component_l brary.model.cand date.BaseT etCand date
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.contextual_ref.ContextualT etRef
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.contextual_ref.T etHydrat onContext

case class ContextualT etRefBu lder[-Cand date <: BaseT etCand date](
  t etHydrat onContext: T etHydrat onContext) {

  def apply(cand date: Cand date): Opt on[ContextualT etRef] =
    So (ContextualT etRef(cand date. d, So (t etHydrat onContext)))
}
