package com.tw ter.product_m xer.component_l brary.selector

 mport com.tw ter.product_m xer.component_l brary.selector. nsert ntoModule.ModuleAnd ndex
 mport com.tw ter.product_m xer.component_l brary.selector. nsert ntoModule.ModuleW h emsToAddAndOt rCand dates
 mport com.tw ter.product_m xer.core.funct onal_component.common.Cand dateScope
 mport com.tw ter.product_m xer.core.funct onal_component.common.Spec f cP pel nes
 mport com.tw ter.product_m xer.core.funct onal_component.selector.Selector
 mport com.tw ter.product_m xer.core.funct onal_component.selector.SelectorResult
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.t  l nes.conf gap .Param

/**
 *  nsert all cand dates from [[cand dateP pel ne]] at a 0- ndexed f xed pos  on  nto a module from
 * [[targetModuleCand dateP pel ne]].  f t  results conta n mult ple modules from t  target cand date
 * p pel ne, t n t  cand dates w ll be  nserted  nto t  f rst module.  f t  target module's
 *  ems are a shorter length than t  requested pos  on, t n t  cand dates w ll be appended
 * to t  results.
 *
 * @note t  w ll throw an [[UnsupportedOperat onExcept on]]  f t  [[cand dateP pel ne]] conta ns any modules.
 *
 * @note t  updates t  module  n t  `rema n ngCand dates`
 */
case class  nsertF xedPos  on ntoModuleCand dates(
  cand dateP pel ne: Cand dateP pel ne dent f er,
  targetModuleCand dateP pel ne: Cand dateP pel ne dent f er,
  pos  onParam: Param[ nt])
    extends Selector[P pel neQuery] {

  overr de val p pel neScope: Cand dateScope =
    Spec f cP pel nes(cand dateP pel ne, targetModuleCand dateP pel ne)

  overr de def apply(
    query: P pel neQuery,
    rema n ngCand dates: Seq[Cand dateW hDeta ls],
    result: Seq[Cand dateW hDeta ls]
  ): SelectorResult = {

    val pos  on = query.params(pos  onParam)
    assert(pos  on >= 0, "Pos  on must be equal to or greater than zero")

    val ModuleW h emsToAddAndOt rCand dates(
      moduleToUpdateAnd ndex,
       emsTo nsert ntoModule,
      ot rCand dates) =
       nsert ntoModule.moduleToUpdate(
        cand dateP pel ne,
        targetModuleCand dateP pel ne,
        rema n ngCand dates)

    val updatedRema n ngCand dates = moduleToUpdateAnd ndex match {
      case None => rema n ngCand dates
      case _  f  emsTo nsert ntoModule. sEmpty => rema n ngCand dates
      case So (ModuleAnd ndex(moduleToUpdate,  ndexOfModule nOt rCand dates)) =>
        val updatedModule ems =
           f (pos  on < moduleToUpdate.cand dates.length) {
            val (left, r ght) = moduleToUpdate.cand dates.spl At(pos  on)
            left ++  emsTo nsert ntoModule ++ r ght
          } else {
            moduleToUpdate.cand dates ++  emsTo nsert ntoModule
          }
        val updatedModule = moduleToUpdate.copy(cand dates = updatedModule ems)
        ot rCand dates.updated( ndexOfModule nOt rCand dates, updatedModule)
    }

    SelectorResult(rema n ngCand dates = updatedRema n ngCand dates, result = result)
  }
}
