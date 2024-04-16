package com.tw ter.product_m xer.component_l brary.selector
 mport com.tw ter.product_m xer.component_l brary.model.cand date.RelevancePromptCand date
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.common.presentat on. emCand dateW hDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

/**
 * Compute a pos  on for  nsert ng a spec f c cand date  nto t  result sequence or g nally prov ded to t  Selector.
 *  f a `None`  s returned, t  Selector us ng t  would not  nsert that cand date  nto t  result.
 */
tra  Cand datePos  on nResults[-Query <: P pel neQuery] {
  def apply(
    query: Query,
    cand date: Cand dateW hDeta ls,
    result: Seq[Cand dateW hDeta ls]
  ): Opt on[ nt]
}

object PromptCand datePos  on nResults extends Cand datePos  on nResults[P pel neQuery] {
  overr de def apply(
    query: P pel neQuery,
    cand date: Cand dateW hDeta ls,
    result: Seq[Cand dateW hDeta ls]
  ): Opt on[ nt] = cand date match {
    case  emCand dateW hDeta ls(cand date, _, _) =>
      cand date match {
        case relevancePromptCand date: RelevancePromptCand date => relevancePromptCand date.pos  on
        case _ => None
      }
    // not support ng ModuleCand dateW hDeta ls r ght now as RelevancePromptCand date shouldn't be  n a module
    case _ => None
  }
}
