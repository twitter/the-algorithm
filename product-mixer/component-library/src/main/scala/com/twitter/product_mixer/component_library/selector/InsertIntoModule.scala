package com.tw ter.product_m xer.component_l brary.selector

 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.common.presentat on. emCand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.common.presentat on.ModuleCand dateW hDeta ls
 mport scala.collect on. mmutable.Queue

pr vate[selector] object  nsert ntoModule {
  case class ModuleAnd ndex(
    moduleTo nsert nto: ModuleCand dateW hDeta ls,
     ndexOfModule nOt rCand dates:  nt)

  case class ModuleW h emsToAddAndOt rCand dates(
    moduleToUpdateAnd ndex: Opt on[ModuleAnd ndex],
     emsTo nsert ntoModule: Queue[ emCand dateW hDeta ls],
    ot rCand dates: Queue[Cand dateW hDeta ls])

  /**
   * G ven a Seq of `cand dates`, returns t  f rst module w h  's  ndex that matc s t 
   * `targetModuleCand dateP pel ne` w h all t  [[ emCand dateW hDeta ls]] that match t 
   * `cand dateP pel ne` added to t  ` emsTo nsert` and t  rema n ng cand dates,  nclud ng t 
   * module,  n t  `ot rCand dates`
   */
  def moduleToUpdate(
    cand dateP pel ne: Cand dateP pel ne dent f er,
    targetModuleCand dateP pel ne: Cand dateP pel ne dent f er,
    cand dates: Seq[Cand dateW hDeta ls]
  ): ModuleW h emsToAddAndOt rCand dates = {
    cand dates.foldLeft[ModuleW h emsToAddAndOt rCand dates](
      ModuleW h emsToAddAndOt rCand dates(None, Queue.empty, Queue.empty)) {
      case (
            state @ ModuleW h emsToAddAndOt rCand dates(_,  emsTo nsert ntoModule, _),
            selected em:  emCand dateW hDeta ls)  f selected em.s ce == cand dateP pel ne =>
        state.copy( emsTo nsert ntoModule =  emsTo nsert ntoModule :+ selected em)

      case (
            state @ ModuleW h emsToAddAndOt rCand dates(None, _, ot rCand dates),
            module: ModuleCand dateW hDeta ls)  f module.s ce == targetModuleCand dateP pel ne =>
        val  nsert on ndex = ot rCand dates.length
        val moduleAnd ndex = So (
          ModuleAnd ndex(
            moduleTo nsert nto = module,
             ndexOfModule nOt rCand dates =  nsert on ndex))
        val ot rCand datesW hModuleAppended = ot rCand dates :+ module
        state.copy(
          moduleToUpdateAnd ndex = moduleAnd ndex,
          ot rCand dates = ot rCand datesW hModuleAppended)

      case (_,  nval dModule: ModuleCand dateW hDeta ls)
           f  nval dModule.s ce == cand dateP pel ne =>
        /**
         * wh le not exactly an  llegal state,  s most l kely an  ncorrectly conf gured cand date p pel ne
         * that returned a module  nstead of return ng t  cand dates t  module conta ns. S nce   can't
         * nest a module  ns de of a module,   can e  r throw or  gnore   and   choose to  gnore  
         * to catch a potent al bug a custo r may do acc dentally.
         */
        throw new UnsupportedOperat onExcept on(
          s"Expected t  cand dateP pel ne $cand dateP pel ne to conta n  ems to put  nto t  module from t  targetModuleCand dateP pel ne $targetModuleCand dateP pel ne, but not conta n modules  self. " +
            s"T  can occur  f y  $cand dateP pel ne was  ncorrectly conf gured and returns a module w n    ntended to return t  cand dates t  module conta ned."
        )

      case (
            state @ ModuleW h emsToAddAndOt rCand dates(_, _, ot rCand dates),
            unselectedCand date) =>
        state.copy(ot rCand dates = ot rCand dates :+ unselectedCand date)
    }
  }

}
