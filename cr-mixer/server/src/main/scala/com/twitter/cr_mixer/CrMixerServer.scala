package com.tw ter.cr_m xer

 mport com.google. nject.Module
 mport com.tw ter.cr_m xer.controller.CrM xerThr ftController
 mport com.tw ter.cr_m xer.featuresw ch.Set mpressedBucketsLocalContextF lter
 mport com.tw ter.cr_m xer.module.Act vePromotedT etStoreModule
 mport com.tw ter.cr_m xer.module.CertoStratoStoreModule
 mport com.tw ter.cr_m xer.module.CrM xerParamConf gModule
 mport com.tw ter.cr_m xer.module.Embedd ngStoreModule
 mport com.tw ter.cr_m xer.module.FrsStoreModule
 mport com.tw ter.cr_m xer.module.MHMtlsParamsModule
 mport com.tw ter.cr_m xer.module.Offl neCand dateStoreModule
 mport com.tw ter.cr_m xer.module.RealGraphStoreMhModule
 mport com.tw ter.cr_m xer.module.RealGraphOonStoreModule
 mport com.tw ter.cr_m xer.module.Representat onManagerModule
 mport com.tw ter.cr_m xer.module.Representat onScorerModule
 mport com.tw ter.cr_m xer.module.T et nfoStoreModule
 mport com.tw ter.cr_m xer.module.T etRecentEngagedUserStoreModule
 mport com.tw ter.cr_m xer.module.T etRecom ndat onResultsStoreModule
 mport com.tw ter.cr_m xer.module.Tr pCand dateStoreModule
 mport com.tw ter.cr_m xer.module.Twh nCollabF lterStratoStoreModule
 mport com.tw ter.cr_m xer.module.UserS gnalServ ceColumnModule
 mport com.tw ter.cr_m xer.module.UserS gnalServ ceStoreModule
 mport com.tw ter.cr_m xer.module.UserStateStoreModule
 mport com.tw ter.cr_m xer.module.core.ABDec derModule
 mport com.tw ter.cr_m xer.module.core.CrM xerFlagModule
 mport com.tw ter.cr_m xer.module.core.CrM xerLogg ngABDec derModule
 mport com.tw ter.cr_m xer.module.core.FeatureContextBu lderModule
 mport com.tw ter.cr_m xer.module.core.FeatureSw c sModule
 mport com.tw ter.cr_m xer.module.core.KafkaProducerModule
 mport com.tw ter.cr_m xer.module.core.LoggerFactoryModule
 mport com.tw ter.cr_m xer.module.s m lar y_eng ne.Consu rEmbedd ngBasedTr pS m lar yEng neModule
 mport com.tw ter.cr_m xer.module.s m lar y_eng ne.Consu rEmbedd ngBasedTwH NS m lar yEng neModule
 mport com.tw ter.cr_m xer.module.s m lar y_eng ne.Consu rEmbedd ngBasedTwoTo rS m lar yEng neModule
 mport com.tw ter.cr_m xer.module.s m lar y_eng ne.Consu rsBasedUserAdGraphS m lar yEng neModule
 mport com.tw ter.cr_m xer.module.s m lar y_eng ne.Consu rsBasedUserV deoGraphS m lar yEng neModule
 mport com.tw ter.cr_m xer.module.s m lar y_eng ne.ProducerBasedUserAdGraphS m lar yEng neModule
 mport com.tw ter.cr_m xer.module.s m lar y_eng ne.ProducerBasedUserT etGraphS m lar yEng neModule
 mport com.tw ter.cr_m xer.module.s m lar y_eng ne.ProducerBasedUn f edS m lar yEng neModule
 mport com.tw ter.cr_m xer.module.s m lar y_eng ne.S mClustersANNS m lar yEng neModule
 mport com.tw ter.cr_m xer.module.s m lar y_eng ne.T etBasedUn f edS m lar yEng neModule
 mport com.tw ter.cr_m xer.module.s m lar y_eng ne.T etBasedQ gS m lar yEng neModule
 mport com.tw ter.cr_m xer.module.s m lar y_eng ne.T etBasedTwH NS mlar yEng neModule
 mport com.tw ter.cr_m xer.module.s m lar y_eng ne.T etBasedUserAdGraphS m lar yEng neModule
 mport com.tw ter.cr_m xer.module.s m lar y_eng ne.T etBasedUserT etGraphS m lar yEng neModule
 mport com.tw ter.cr_m xer.module.s m lar y_eng ne.T etBasedUserV deoGraphS m lar yEng neModule
 mport com.tw ter.cr_m xer.module.s m lar y_eng ne.Twh nCollabF lterLookupS m lar yEng neModule
 mport com.tw ter.cr_m xer.module.Consu rsBasedUserAdGraphStoreModule
 mport com.tw ter.cr_m xer.module.Consu rsBasedUserT etGraphStoreModule
 mport com.tw ter.cr_m xer.module.Consu rsBasedUserV deoGraphStoreModule
 mport com.tw ter.cr_m xer.module.D ffus onStoreModule
 mport com.tw ter.cr_m xer.module.Earlyb rdRecencyBasedCand dateStoreModule
 mport com.tw ter.cr_m xer.module.Tw ceClusters mbersStoreModule
 mport com.tw ter.cr_m xer.module.StrongT ePred ct onStoreModule
 mport com.tw ter.cr_m xer.module.thr ft_cl ent.AnnQueryServ ceCl entModule
 mport com.tw ter.cr_m xer.module.thr ft_cl ent.Earlyb rdSearchCl entModule
 mport com.tw ter.cr_m xer.module.thr ft_cl ent.FrsCl entModule
 mport com.tw ter.cr_m xer.module.thr ft_cl ent.Q gServ ceCl entModule
 mport com.tw ter.cr_m xer.module.thr ft_cl ent.S mClustersAnnServ ceCl entModule
 mport com.tw ter.cr_m xer.module.thr ft_cl ent.T etyP eCl entModule
 mport com.tw ter.cr_m xer.module.thr ft_cl ent.UserT etGraphCl entModule
 mport com.tw ter.cr_m xer.module.thr ft_cl ent.UserT etGraphPlusCl entModule
 mport com.tw ter.cr_m xer.module.thr ft_cl ent.UserV deoGraphCl entModule
 mport com.tw ter.cr_m xer.{thr ftscala => st}
 mport com.tw ter.f nagle.F lter
 mport com.tw ter.f natra.annotat ons.DarkTraff cF lterType
 mport com.tw ter.f natra.dec der.modules.Dec derModule
 mport com.tw ter.f natra.http.HttpServer
 mport com.tw ter.f natra.http.rout ng.HttpRouter
 mport com.tw ter.f natra.jackson.modules.ScalaObjectMapperModule
 mport com.tw ter.f natra.mtls.http.{Mtls => HttpMtls}
 mport com.tw ter.f natra.mtls.thr ftmux.Mtls
 mport com.tw ter.f natra.mtls.thr ftmux.modules.MtlsThr ft bFormsModule
 mport com.tw ter.f natra.thr ft.Thr ftServer
 mport com.tw ter.f natra.thr ft.f lters._
 mport com.tw ter.f natra.thr ft.rout ng.Thr ftRouter
 mport com.tw ter.hydra.common.model_conf g.{Conf gModule => HydraConf gModule}
 mport com.tw ter. nject.thr ft.modules.Thr ftCl ent dModule
 mport com.tw ter.product_m xer.core.module.Logg ngThrowableExcept onMapper
 mport com.tw ter.product_m xer.core.module.StratoCl entModule
 mport com.tw ter.product_m xer.core.module.product_m xer_flags.ProductM xerFlagModule
 mport com.tw ter.relevance_platform.common.f lters.Cl entStatsF lter
 mport com.tw ter.relevance_platform.common.f lters.DarkTraff cF lterModule
 mport com.tw ter.cr_m xer.module.S mClustersANNServ ceNa ToCl entMapper
 mport com.tw ter.cr_m xer.module.Sk StratoStoreModule
 mport com.tw ter.cr_m xer.module.BlueVer f edAnnotat onStoreModule
 mport com.tw ter.cr_m xer.module.core.T  outConf gModule
 mport com.tw ter.cr_m xer.module.grpc_cl ent.Nav GRPCCl entModule
 mport com.tw ter.cr_m xer.module.s m lar y_eng ne.CertoTop cT etS m lar yEng neModule
 mport com.tw ter.cr_m xer.module.s m lar y_eng ne.Consu rBasedWalsS m lar yEng neModule
 mport com.tw ter.cr_m xer.module.s m lar y_eng ne.D ffus onBasedS m lar yEng neModule
 mport com.tw ter.cr_m xer.module.s m lar y_eng ne.Earlyb rdS m lar yEng neModule
 mport com.tw ter.cr_m xer.module.s m lar y_eng ne.Sk Top cT etS m lar yEng neModule
 mport com.tw ter.cr_m xer.module.s m lar y_eng ne.UserT etEnt yGraphS m lar yEng neModule
 mport com.tw ter.cr_m xer.module.thr ft_cl ent.HydraPart  onCl entModule
 mport com.tw ter.cr_m xer.module.thr ft_cl ent.HydraRootCl entModule
 mport com.tw ter.cr_m xer.module.thr ft_cl ent.UserAdGraphCl entModule
 mport com.tw ter.cr_m xer.module.thr ft_cl ent.UserT etEnt yGraphCl entModule
 mport com.tw ter.thr ft bforms. thodOpt ons

object CrM xerServerMa n extends CrM xerServer

class CrM xerServer extends Thr ftServer w h Mtls w h HttpServer w h HttpMtls {
  overr de val na  = "cr-m xer-server"

  pr vate val coreModules = Seq(
    ABDec derModule,
    CrM xerFlagModule,
    CrM xerLogg ngABDec derModule,
    CrM xerParamConf gModule,
    new DarkTraff cF lterModule[st.CrM xer.ReqRepServ cePerEndpo nt](),
    Dec derModule,
    FeatureContextBu lderModule,
    FeatureSw c sModule,
    KafkaProducerModule,
    LoggerFactoryModule,
    MHMtlsParamsModule,
    ProductM xerFlagModule,
    ScalaObjectMapperModule,
    Thr ftCl ent dModule
  )

  pr vate val thr ftCl entModules = Seq(
    AnnQueryServ ceCl entModule,
    Earlyb rdSearchCl entModule,
    FrsCl entModule,
    HydraPart  onCl entModule,
    HydraRootCl entModule,
    Q gServ ceCl entModule,
    S mClustersAnnServ ceCl entModule,
    T etyP eCl entModule,
    UserAdGraphCl entModule,
    UserT etEnt yGraphCl entModule,
    UserT etGraphCl entModule,
    UserT etGraphPlusCl entModule,
    UserV deoGraphCl entModule,
  )

  pr vate val grpcCl entModules = Seq(
    Nav GRPCCl entModule
  )

  // Modules sorted alphabet cally, please keep t  order w n add ng a new module
  overr de val modules: Seq[Module] =
    coreModules ++ thr ftCl entModules ++ grpcCl entModules ++
      Seq(
        Act vePromotedT etStoreModule,
        CertoStratoStoreModule,
        CertoTop cT etS m lar yEng neModule,
        Consu rsBasedUserAdGraphS m lar yEng neModule,
        Consu rsBasedUserT etGraphStoreModule,
        Consu rsBasedUserV deoGraphS m lar yEng neModule,
        Consu rsBasedUserV deoGraphStoreModule,
        Consu rEmbedd ngBasedTr pS m lar yEng neModule,
        Consu rEmbedd ngBasedTwH NS m lar yEng neModule,
        Consu rEmbedd ngBasedTwoTo rS m lar yEng neModule,
        Consu rsBasedUserAdGraphStoreModule,
        Consu rBasedWalsS m lar yEng neModule,
        D ffus onStoreModule,
        Embedd ngStoreModule,
        Earlyb rdS m lar yEng neModule,
        Earlyb rdRecencyBasedCand dateStoreModule,
        FrsStoreModule,
        HydraConf gModule,
        Offl neCand dateStoreModule,
        ProducerBasedUn f edS m lar yEng neModule,
        ProducerBasedUserAdGraphS m lar yEng neModule,
        ProducerBasedUserT etGraphS m lar yEng neModule,
        RealGraphOonStoreModule,
        RealGraphStoreMhModule,
        Representat onManagerModule,
        Representat onScorerModule,
        S mClustersANNServ ceNa ToCl entMapper,
        S mClustersANNS m lar yEng neModule,
        Sk StratoStoreModule,
        Sk Top cT etS m lar yEng neModule,
        StratoCl entModule,
        StrongT ePred ct onStoreModule,
        T  outConf gModule,
        Tr pCand dateStoreModule,
        Tw ceClusters mbersStoreModule,
        T etBasedQ gS m lar yEng neModule,
        T etBasedTwH NS mlar yEng neModule,
        T etBasedUn f edS m lar yEng neModule,
        T etBasedUserAdGraphS m lar yEng neModule,
        T etBasedUserT etGraphS m lar yEng neModule,
        T etBasedUserV deoGraphS m lar yEng neModule,
        T et nfoStoreModule,
        T etRecentEngagedUserStoreModule,
        T etRecom ndat onResultsStoreModule,
        Twh nCollabF lterStratoStoreModule,
        Twh nCollabF lterLookupS m lar yEng neModule,
        UserS gnalServ ceColumnModule,
        UserS gnalServ ceStoreModule,
        UserStateStoreModule,
        UserT etEnt yGraphS m lar yEng neModule,
        D ffus onBasedS m lar yEng neModule,
        BlueVer f edAnnotat onStoreModule,
        new MtlsThr ft bFormsModule[st.CrM xer. thodPerEndpo nt](t ) {
          overr de protected def default thodAccess:  thodOpt ons.Access = {
             thodOpt ons.Access.ByLdapGroup(
              Seq(
                "cr-m xer-adm ns",
                "recosplat-sens  ve-data- d um",
                "recos-platform-adm ns",
              ))
          }
        }
      )

  def conf gureThr ft(router: Thr ftRouter): Un  = {
    router
      .f lter[Logg ngMDCF lter]
      .f lter[Trace dMDCF lter]
      .f lter[Thr ftMDCF lter]
      .f lter[Cl entStatsF lter]
      .f lter[AccessLogg ngF lter]
      .f lter[Set mpressedBucketsLocalContextF lter]
      .f lter[Except onMapp ngF lter]
      .f lter[F lter.TypeAgnost c, DarkTraff cF lterType]
      .except onMapper[Logg ngThrowableExcept onMapper]
      .add[CrM xerThr ftController]
  }

  overr de protected def warmup(): Un  = {
    handle[CrM xerThr ftServerWarmupHandler]()
    handle[CrM xerHttpServerWarmupHandler]()
  }
}
