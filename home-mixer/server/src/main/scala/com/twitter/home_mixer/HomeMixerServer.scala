package com.tw ter.ho _m xer

 mport com.google. nject.Module
 mport com.tw ter.f nagle.F lter
 mport com.tw ter.f natra.annotat ons.DarkTraff cF lterType
 mport com.tw ter.f natra.http.HttpServer
 mport com.tw ter.f natra.http.rout ng.HttpRouter
 mport com.tw ter.f natra.mtls.http.{Mtls => HttpMtls}
 mport com.tw ter.f natra.mtls.thr ftmux.Mtls
 mport com.tw ter.f natra.mtls.thr ftmux.modules.MtlsThr ft bFormsModule
 mport com.tw ter.f natra.thr ft.Thr ftServer
 mport com.tw ter.f natra.thr ft.f lters._
 mport com.tw ter.f natra.thr ft.rout ng.Thr ftRouter
 mport com.tw ter.ho _m xer.controller.Ho Thr ftController
 mport com.tw ter.ho _m xer.federated.Ho M xerColumn
 mport com.tw ter.ho _m xer.module._
 mport com.tw ter.ho _m xer.param.GlobalParamConf gModule
 mport com.tw ter.ho _m xer.product.Ho M xerProductModule
 mport com.tw ter.ho _m xer.{thr ftscala => st}
 mport com.tw ter.product_m xer.component_l brary.module.AccountRecom ndat onsM xerModule
 mport com.tw ter.product_m xer.component_l brary.module.DarkTraff cF lterModule
 mport com.tw ter.product_m xer.component_l brary.module.Earlyb rdModule
 mport com.tw ter.product_m xer.component_l brary.module.ExploreRankerCl entModule
 mport com.tw ter.product_m xer.component_l brary.module.G zmoduckCl entModule
 mport com.tw ter.product_m xer.component_l brary.module.Onboard ngTaskServ ceModule
 mport com.tw ter.product_m xer.component_l brary.module.Soc alGraphServ ceModule
 mport com.tw ter.product_m xer.component_l brary.module.T  l neRankerCl entModule
 mport com.tw ter.product_m xer.component_l brary.module.T  l neScorerCl entModule
 mport com.tw ter.product_m xer.component_l brary.module.T  l neServ ceCl entModule
 mport com.tw ter.product_m xer.component_l brary.module.T et mpress onStoreModule
 mport com.tw ter.product_m xer.component_l brary.module.T etM xerCl entModule
 mport com.tw ter.product_m xer.component_l brary.module.UserSess onStoreModule
 mport com.tw ter.product_m xer.core.controllers.ProductM xerController
 mport com.tw ter.product_m xer.core.module.Logg ngThrowableExcept onMapper
 mport com.tw ter.product_m xer.core.module.ProductM xerModule
 mport com.tw ter.product_m xer.core.module.str ngcenter.ProductScopeStr ngCenterModule
 mport com.tw ter.strato.fed.StratoFed
 mport com.tw ter.strato.fed.server.StratoFedServer

object Ho M xerServerMa n extends Ho M xerServer

class Ho M xerServer
    extends StratoFedServer
    w h Thr ftServer
    w h Mtls
    w h HttpServer
    w h HttpMtls {
  overr de val na  = "ho -m xer-server"

  overr de val modules: Seq[Module] = Seq(
    AccountRecom ndat onsM xerModule,
    Advert serBrandSafetySett ngsStoreModule,
    BlenderCl entModule,
    Cl entSent mpress onsPubl s rModule,
    Conversat onServ ceModule,
    Earlyb rdModule,
    ExploreRankerCl entModule,
    Feedback toryCl entModule,
    G zmoduckCl entModule,
    GlobalParamConf gModule,
    Ho AdsCand dateS ceModule,
    Ho M xerFlagsModule,
    Ho M xerProductModule,
    Ho M xerRes cesModule,
     mpress onBloomF lterModule,
     nject on toryCl entModule,
    ManhattanCl entsModule,
    ManhattanFeatureRepos oryModule,
    ManhattanT et mpress onStoreModule,
     mcac dFeatureRepos oryModule,
    Nav ModelCl entModule,
    Onboard ngTaskServ ceModule,
    Opt m zedStratoCl entModule,
    PeopleD scoveryServ ceModule,
    ProductM xerModule,
    RealGraph nNetworkScoresModule,
    Realt  AggregateFeatureRepos oryModule,
    ScoredT ets mcac Module,
    Scr beEventPubl s rModule,
    S mClustersRecentEngage ntsCl entModule,
    Soc alGraphServ ceModule,
    StaleT etsCac Module,
    Thr ftFeatureRepos oryModule,
    T  l neRankerCl entModule,
    T  l neScorerCl entModule,
    T  l neServ ceCl entModule,
    T  l nesPers stenceStoreCl entModule,
    Top cSoc alProofCl entModule,
    T et mpress onStoreModule,
    T etM xerCl entModule,
    T etyp eCl entModule,
    T etyp eStat cEnt  esCac Cl entModule,
    UserSess onStoreModule,
    new DarkTraff cF lterModule[st.Ho M xer.ReqRepServ cePerEndpo nt](),
    new MtlsThr ft bFormsModule[st.Ho M xer. thodPerEndpo nt](t ),
    new ProductScopeStr ngCenterModule()
  )

  overr de def conf gureThr ft(router: Thr ftRouter): Un  = {
    router
      .f lter[Logg ngMDCF lter]
      .f lter[Trace dMDCF lter]
      .f lter[Thr ftMDCF lter]
      .f lter[StatsF lter]
      .f lter[AccessLogg ngF lter]
      .f lter[Except onMapp ngF lter]
      .f lter[F lter.TypeAgnost c, DarkTraff cF lterType]
      .except onMapper[Logg ngThrowableExcept onMapper]
      .except onMapper[P pel neFa lureExcept onMapper]
      .add[Ho Thr ftController]
  }

  overr de def conf gureHttp(router: HttpRouter): Un  =
    router.add(
      ProductM xerController[st.Ho M xer. thodPerEndpo nt](
        t . njector,
        st.Ho M xer.ExecuteP pel ne))

  overr de val dest: Str ng = "/s/ho -m xer/ho -m xer:strato"

  overr de val columns: Seq[Class[_ <: StratoFed.Column]] =
    Seq(classOf[Ho M xerColumn])

  overr de protected def warmup(): Un  = {
    handle[Ho M xerThr ftServerWarmupHandler]()
    handle[Ho M xerHttpServerWarmupHandler]()
  }
}
