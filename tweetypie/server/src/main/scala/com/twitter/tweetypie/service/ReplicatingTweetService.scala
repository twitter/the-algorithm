package com.tw ter.t etyp e
package serv ce

 mport com.tw ter.t etyp e.thr ftscala._
 mport com.tw ter.servo.forked.Forked
 mport com.tw ter.t etyp e.serv ce.Repl cat ngT etServ ce.GatedRepl cat onCl ent

/**
 * Wraps an underly ng Thr ftT etServ ce, transform ng external requests to repl cated requests.
 */
object Repl cat ngT etServ ce {
  // Can be used to assoc ate repl cat on cl ent w h a gate that determ nes
  //  f a repl cat on request should be perfor d.
  case class GatedRepl cat onCl ent(cl ent: Thr ftT etServ ce, gate: Gate[Un ]) {
    def execute(executor: Forked.Executor, act on: Thr ftT etServ ce => Un ): Un  = {
       f (gate()) executor { () => act on(cl ent) }
    }
  }
}

class Repl cat ngT etServ ce(
  protected val underly ng: Thr ftT etServ ce,
  repl cat onTargets: Seq[GatedRepl cat onCl ent],
  executor: Forked.Executor,
) extends T etServ ceProxy {
  pr vate[t ] def repl cateRead(act on: Thr ftT etServ ce => Un ): Un  =
    repl cat onTargets.foreach(_.execute(executor, act on))

  overr de def getT etCounts(request: GetT etCountsRequest): Future[Seq[GetT etCountsResult]] = {
    repl cateRead(_.repl catedGetT etCounts(request))
    underly ng.getT etCounts(request)
  }

  overr de def getT etF elds(request: GetT etF eldsRequest): Future[Seq[GetT etF eldsResult]] = {
     f (!request.opt ons.doNotCac ) {
      repl cateRead(_.repl catedGetT etF elds(request))
    }
    underly ng.getT etF elds(request)
  }

  overr de def getT ets(request: GetT etsRequest): Future[Seq[GetT etResult]] = {
     f (!request.opt ons.ex sts(_.doNotCac )) {
      repl cateRead(_.repl catedGetT ets(request))
    }
    underly ng.getT ets(request)
  }
}
