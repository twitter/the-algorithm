package com.tw ter.graph_feature_serv ce.worker.controllers

 mport com.tw ter.d scovery.common.stats.D scoveryStatsF lter
 mport com.tw ter.f nagle.Serv ce
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f natra.thr ft.Controller
 mport com.tw ter.graph_feature_serv ce.thr ftscala
 mport com.tw ter.graph_feature_serv ce.thr ftscala.Worker.Get ntersect on
 mport com.tw ter.graph_feature_serv ce.thr ftscala._
 mport com.tw ter.graph_feature_serv ce.worker.handlers._
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class WorkerController @ nject() (
  workerGet ntersect onHandler: WorkerGet ntersect onHandler
)(
   mpl c  statsRece ver: StatsRece ver)
    extends Controller(thr ftscala.Worker) {

  // use D scoveryStatsF lter to f lter out except ons out of   control
  pr vate val get ntersect onServ ce: Serv ce[
    Worker ntersect onRequest,
    Worker ntersect onResponse
  ] =
    new D scoveryStatsF lter[Worker ntersect onRequest, Worker ntersect onResponse](
      statsRece ver.scope("srv").scope("get_ ntersect on")
    ).andT n(Serv ce.mk(workerGet ntersect onHandler))

  val get ntersect on: Serv ce[Get ntersect on.Args, Worker ntersect onResponse] = { args =>
    get ntersect onServ ce(args.request).onFa lure { throwable =>
      logger.error(s"Fa lure to get  ntersect on for request $args.", throwable)
    }
  }

  handle(Get ntersect on) { get ntersect on }

}
