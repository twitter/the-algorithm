package com.tw ter.s mclustersann

 mport com.google. nject.Module
 mport com.tw ter.f natra.dec der.modules.Dec derModule
 mport com.tw ter.f natra.mtls.thr ftmux.Mtls
 mport com.tw ter.f natra.thr ft.Thr ftServer
 mport com.tw ter.f natra.thr ft.f lters._
 mport com.tw ter.f natra.thr ft.rout ng.Thr ftRouter
 mport com.tw ter. nject.thr ft.modules.Thr ftCl ent dModule
 mport com.tw ter.relevance_platform.common.except ons._
 mport com.tw ter.s mclustersann.controllers.S mClustersANNController
 mport com.tw ter.s mclustersann.except ons. nval dRequestForS mClustersAnnVar antExcept onMapper
 mport com.tw ter.s mclustersann.modules._
 mport com.tw ter.s mclustersann.thr ftscala.S mClustersANNServ ce
 mport com.tw ter.f nagle.F lter
 mport com.tw ter.f natra.annotat ons.DarkTraff cF lterType
 mport com.tw ter. nject.annotat ons.Flags
 mport com.tw ter.relevance_platform.common.f lters.DarkTraff cF lterModule
 mport com.tw ter.relevance_platform.common.f lters.Cl entStatsF lter
 mport com.tw ter.s mclustersann.common.FlagNa s.D sableWarmup

object S mClustersAnnServerMa n extends S mClustersAnnServer

class S mClustersAnnServer extends Thr ftServer w h Mtls {
  flag(
    na  = D sableWarmup,
    default = false,
     lp = " f true, no warmup w ll be run."
  )

  overr de val na  = "s mclusters-ann-server"

  overr de val modules: Seq[Module] = Seq(
    Cac Module,
    Serv ceNa MapperModule,
    ClusterConf gMapperModule,
    ClusterConf gModule,
    ClusterT et ndexProv derModule,
    Dec derModule,
    Embedd ngStoreModule,
    FlagsModule,
    FuturePoolProv der,
    RateL m erModule,
    S mClustersANNCand dateS ceModule,
    StratoCl entProv derModule,
    Thr ftCl ent dModule,
    new CustomMtlsThr ft bFormsModule[S mClustersANNServ ce. thodPerEndpo nt](t ),
    new DarkTraff cF lterModule[S mClustersANNServ ce.ReqRepServ cePerEndpo nt]()
  )

  def conf gureThr ft(router: Thr ftRouter): Un  = {
    router
      .f lter[Logg ngMDCF lter]
      .f lter[Trace dMDCF lter]
      .f lter[Thr ftMDCF lter]
      .f lter[Cl entStatsF lter]
      .f lter[Except onMapp ngF lter]
      .f lter[F lter.TypeAgnost c, DarkTraff cF lterType]
      .except onMapper[ nval dRequestForS mClustersAnnVar antExcept onMapper]
      .except onMapper[Deadl neExceededExcept onMapper]
      .except onMapper[UnhandledExcept onMapper]
      .add[S mClustersANNController]
  }

  overr de protected def warmup(): Un  = {
     f (! njector. nstance[Boolean](Flags.na d(D sableWarmup))) {
      handle[S mclustersAnnWarmupHandler]()
    }
  }
}
