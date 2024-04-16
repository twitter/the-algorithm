package com.tw ter.graph_feature_serv ce.worker.handlers

 mport com.tw ter.f natra.thr ft.rout ng.Thr ftWarmup
 mport com.tw ter. nject.Logg ng
 mport com.tw ter. nject.ut ls.Handler
 mport javax. nject.{ nject, S ngleton}

@S ngleton
class WorkerWarmupHandler @ nject() (warmup: Thr ftWarmup) extends Handler w h Logg ng {

  overr de def handle(): Un  = {
     nfo("Warmup Done!")
  }
}
