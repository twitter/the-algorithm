package com.tw ter.product_m xer.component_l brary.cand date_s ce.t etconvosvc

 mport com.tw ter.product_m xer.core.funct onal_component.common.Cand dateScope
 mport com.tw ter.product_m xer.core.funct onal_component.selector.Selector
 mport com.tw ter.product_m xer.core.funct onal_component.selector.SelectorResult
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.common.presentat on.ModuleCand dateW hDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

/**
 * Takes a conversat on module  em and truncates   to be at most t  focal t et, t  focal t et's
 *  n reply to t et and opt onally, t  root conversat on t et  f des red.
 * @param p pel neScope What p pel ne scopes to  nclude  n t .
 * @param  ncludeRootT et W t r to  nclude t  root t et at t  top of t  conversat on or not.
 * @tparam Query
 */
case class DropMaxConversat onModule emCand dates[-Query <: P pel neQuery](
  overr de val p pel neScope: Cand dateScope,
   ncludeRootT et: Boolean)
    extends Selector[Query] {
  overr de def apply(
    query: Query,
    rema n ngCand dates: Seq[Cand dateW hDeta ls],
    result: Seq[Cand dateW hDeta ls]
  ): SelectorResult = {
    val updatedCand dates = rema n ngCand dates.collect {
      case moduleCand date: ModuleCand dateW hDeta ls  f p pel neScope.conta ns(moduleCand date) =>
        updateConversat onModule(moduleCand date,  ncludeRootT et)
      case cand dates => cand dates
    }
    SelectorResult(rema n ngCand dates = updatedCand dates, result = result)
  }

  pr vate def updateConversat onModule(
    module: ModuleCand dateW hDeta ls,
     ncludeRootT et: Boolean
  ): ModuleCand dateW hDeta ls = {
    //  f t  thread  s only t  root t et & a focal t et reply ng to  , no truncat on can be done.
     f (module.cand dates.length <= 2) {
      module
    } else {
      //  f a thread  s more 3 or more t ets,   opt onally keep t  root t et  f des red, and take
      // t  focal t et t et and  s d rect ancestor (t  one   would have repl ed to) and return
      // those.
      val t etCand dates = module.cand dates
      val replyAndFocalT et = t etCand dates.takeR ght(2)
      val updatedConversat on =  f ( ncludeRootT et) {
        t etCand dates. adOpt on ++ replyAndFocalT et
      } else {
        replyAndFocalT et
      }
      module.copy(cand dates = updatedConversat on.toSeq)
    }
  }
}
