package com.tw ter.graph_feature_serv ce.server

 mport com.google. nject.Module
 mport com.tw ter.f natra.dec der.modules.Dec derModule
 mport com.tw ter.f natra.mtls.thr ftmux.Mtls
 mport com.tw ter.f natra.thr ft.Thr ftServer
 mport com.tw ter.f natra.thr ft.f lters.{
  AccessLogg ngF lter,
  Logg ngMDCF lter,
  StatsF lter,
  Thr ftMDCF lter,
  Trace dMDCF lter
}
 mport com.tw ter.f natra.mtls.thr ftmux.modules.MtlsThr ft bFormsModule
 mport com.tw ter.f natra.thr ft.rout ng.Thr ftRouter
 mport com.tw ter.graph_feature_serv ce.server.controllers.ServerController
 mport com.tw ter.graph_feature_serv ce.server.handlers.ServerWarmupHandler
 mport com.tw ter.graph_feature_serv ce.server.modules.{
  Get ntersect onStoreModule,
  GraphFeatureServ ceWorkerCl entsModule,
  ServerFlagsModule
}
 mport com.tw ter.graph_feature_serv ce.thr ftscala
 mport com.tw ter. nject.thr ft.modules.Thr ftCl ent dModule

object Ma n extends ServerMa n

class ServerMa n extends Thr ftServer w h Mtls {

  overr de val na  = "graph_feature_serv ce-server"

  overr de val modules: Seq[Module] = {
    Seq(
      ServerFlagsModule,
      Dec derModule,
      Thr ftCl ent dModule,
      GraphFeatureServ ceWorkerCl entsModule,
      Get ntersect onStoreModule,
      new MtlsThr ft bFormsModule[thr ftscala.Server. thodPerEndpo nt](t )
    )
  }

  overr de def conf gureThr ft(router: Thr ftRouter): Un  = {
    router
      .f lter[Logg ngMDCF lter]
      .f lter[Trace dMDCF lter]
      .f lter[Thr ftMDCF lter]
      .f lter[AccessLogg ngF lter]
      .f lter[StatsF lter]
      .add[ServerController]
  }

  overr de protected def warmup(): Un  = {
    handle[ServerWarmupHandler]()
  }
}
