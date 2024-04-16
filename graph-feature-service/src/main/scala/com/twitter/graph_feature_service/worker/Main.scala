package com.tw ter.graph_feature_serv ce.worker

 mport com.google. nject.Module
 mport com.tw ter.f natra.dec der.modules.Dec derModule
 mport com.tw ter.f natra.g zmoduck.modules.T  rModule
 mport com.tw ter.f natra.mtls.thr ftmux.Mtls
 mport com.tw ter.f natra.thr ft.Thr ftServer
 mport com.tw ter.f natra.thr ft.f lters.{
  Logg ngMDCF lter,
  StatsF lter,
  Thr ftMDCF lter,
  Trace dMDCF lter
}
 mport com.tw ter.f natra.mtls.thr ftmux.modules.MtlsThr ft bFormsModule
 mport com.tw ter.f natra.thr ft.rout ng.Thr ftRouter
 mport com.tw ter.graph_feature_serv ce.thr ftscala
 mport com.tw ter.graph_feature_serv ce.worker.controllers.WorkerController
 mport com.tw ter.graph_feature_serv ce.worker.handlers.WorkerWarmupHandler
 mport com.tw ter.graph_feature_serv ce.worker.modules.{
  GraphConta nerProv derModule,
  WorkerFlagModule
}
 mport com.tw ter.graph_feature_serv ce.worker.ut l.GraphConta ner
 mport com.tw ter. nject.thr ft.modules.Thr ftCl ent dModule
 mport com.tw ter.ut l.Awa 

object Ma n extends WorkerMa n

class WorkerMa n extends Thr ftServer w h Mtls {

  overr de val na  = "graph_feature_serv ce-worker"

  overr de val modules: Seq[Module] = {
    Seq(
      WorkerFlagModule,
      Dec derModule,
      T  rModule,
      Thr ftCl ent dModule,
      GraphConta nerProv derModule,
      new MtlsThr ft bFormsModule[thr ftscala.Worker. thodPerEndpo nt](t )
    )
  }

  overr de def conf gureThr ft(router: Thr ftRouter): Un  = {
    router
      .f lter[Logg ngMDCF lter]
      .f lter[Trace dMDCF lter]
      .f lter[Thr ftMDCF lter]
      .f lter[StatsF lter]
      .add[WorkerController]
  }

  overr de protected def warmup(): Un  = {
    val graphConta ner =  njector. nstance[GraphConta ner]
    Awa .result(graphConta ner.warmup)
    handle[WorkerWarmupHandler]()
  }
}
