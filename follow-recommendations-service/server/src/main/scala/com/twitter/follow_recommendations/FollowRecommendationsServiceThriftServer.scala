package com.tw ter.follow_recom ndat ons

 mport com.google. nject.Module
 mport com.tw ter.f nagle.Thr ftMux
 mport com.tw ter.f natra.dec der.modules.Dec derModule
 mport com.tw ter.f natra.http.HttpServer
 mport com.tw ter.f natra.http.rout ng.HttpRouter
 mport com.tw ter.f natra. nternat onal.modules. 18nFactoryModule
 mport com.tw ter.f natra. nternat onal.modules.LanguagesModule
 mport com.tw ter.f natra.jackson.modules.ScalaObjectMapperModule
 mport com.tw ter.f natra.mtls.http.{Mtls => HttpMtls}
 mport com.tw ter.f natra.mtls.thr ftmux.Mtls
 mport com.tw ter.f natra.thr ft.Thr ftServer
 mport com.tw ter.f natra.thr ft.f lters._
 mport com.tw ter.f nagle.thr ft.Protocols
 mport com.tw ter.f natra.thr ft.rout ng.Thr ftRouter
 mport com.tw ter.follow_recom ndat ons.common.cl ents.addressbook.AddressbookModule
 mport com.tw ter.follow_recom ndat ons.common.cl ents.adserver.AdserverModule
 mport com.tw ter.follow_recom ndat ons.common.cl ents.cac . mcac Module
 mport com.tw ter.follow_recom ndat ons.common.cl ents.deepb rdv2.DeepB rdV2Pred ct onServ ceCl entModule
 mport com.tw ter.follow_recom ndat ons.common.cl ents.ema l_storage_serv ce.Ema lStorageServ ceModule
 mport com.tw ter.follow_recom ndat ons.common.cl ents.geoduck.Locat onServ ceModule
 mport com.tw ter.follow_recom ndat ons.common.cl ents.g zmoduck.G zmoduckModule
 mport com.tw ter.follow_recom ndat ons.common.cl ents.graph_feature_serv ce.GraphFeatureStoreModule
 mport com.tw ter.follow_recom ndat ons.common.cl ents. mpress on_store. mpress onStoreModule
 mport com.tw ter.follow_recom ndat ons.common.cl ents.phone_storage_serv ce.PhoneStorageServ ceModule
 mport com.tw ter.follow_recom ndat ons.common.cl ents.soc algraph.Soc alGraphModule
 mport com.tw ter.follow_recom ndat ons.common.cl ents.strato.StratoCl entModule
 mport com.tw ter.follow_recom ndat ons.common.constants.Serv ceConstants._
 mport com.tw ter.follow_recom ndat ons.common.feature_hydrat on.s ces.Hydrat onS cesModule
 mport com.tw ter.follow_recom ndat ons.controllers.Thr ftController
 mport com.tw ter.follow_recom ndat ons.modules._
 mport com.tw ter.follow_recom ndat ons.serv ce.except ons.UnknownLogg ngExcept onMapper
 mport com.tw ter.follow_recom ndat ons.serv ces.FollowRecom ndat onsServ ceWarmupHandler
 mport com.tw ter.follow_recom ndat ons.thr ftscala.FollowRecom ndat onsThr ftServ ce
 mport com.tw ter.geoduck.serv ce.common.cl entmodules.ReverseGeocoderThr ftCl entModule
 mport com.tw ter. nject.thr ft.f lters.DarkTraff cF lter
 mport com.tw ter. nject.thr ft.modules.Thr ftCl ent dModule
 mport com.tw ter.product_m xer.core.controllers.ProductM xerController
 mport com.tw ter.product_m xer.core.module.P pel neExecut onLoggerModule
 mport com.tw ter.product_m xer.core.module.product_m xer_flags.ProductM xerFlagModule
 mport com.tw ter.product_m xer.core.module.str ngcenter.ProductScopeStr ngCenterModule
 mport com.tw ter.product_m xer.core.product.gu ce.ProductScopeModule

object FollowRecom ndat onsServ ceThr ftServerMa n extends FollowRecom ndat onsServ ceThr ftServer

class FollowRecom ndat onsServ ceThr ftServer
    extends Thr ftServer
    w h Mtls
    w h HttpServer
    w h HttpMtls {
  overr de val na : Str ng = "follow-recom ndat ons-serv ce-server"

  overr de val modules: Seq[Module] =
    Seq(
      ABDec derModule,
      AddressbookModule,
      AdserverModule,
      Conf gAp Module,
      Dec derModule,
      DeepB rdV2Pred ct onServ ceCl entModule,
      D ffyModule,
      Ema lStorageServ ceModule,
      FeaturesSw c sModule,
      FlagsModule,
      G zmoduckModule,
      GraphFeatureStoreModule,
      Hydrat onS cesModule,
       18nFactoryModule,
       mpress onStoreModule,
      LanguagesModule,
      Locat onServ ceModule,
       mcac Module,
      PhoneStorageServ ceModule,
      P pel neExecut onLoggerModule,
      ProductM xerFlagModule,
      ProductReg stryModule,
      new ProductScopeModule(),
      new ProductScopeStr ngCenterModule(),
      new ReverseGeocoderThr ftCl entModule,
      ScalaObjectMapperModule,
      ScorerModule,
      Scr beModule,
      Soc alGraphModule,
      StratoCl entModule,
      Thr ftCl ent dModule,
      T  rModule,
    )

  def conf gureThr ft(router: Thr ftRouter): Un  = {
    router
      .f lter[Logg ngMDCF lter]
      .f lter[Trace dMDCF lter]
      .f lter[Thr ftMDCF lter]
      .f lter[StatsF lter]
      .f lter[AccessLogg ngF lter]
      .f lter[Except onMapp ngF lter]
      .except onMapper[UnknownLogg ngExcept onMapper]
      .f lter[DarkTraff cF lter[FollowRecom ndat onsThr ftServ ce.ReqRepServ cePerEndpo nt]]
      .add[Thr ftController]
  }

  overr de def conf gureThr ftServer(server: Thr ftMux.Server): Thr ftMux.Server = {
    server.w hProtocolFactory(
      Protocols.b naryFactory(
        str ngLengthL m  = Str ngLengthL m ,
        conta nerLengthL m  = Conta nerLengthL m ))
  }

  overr de def conf gureHttp(router: HttpRouter): Un  = router.add(
    ProductM xerController[FollowRecom ndat onsThr ftServ ce. thodPerEndpo nt](
      t . njector,
      FollowRecom ndat onsThr ftServ ce.ExecuteP pel ne))

  overr de def warmup(): Un  = {
    handle[FollowRecom ndat onsServ ceWarmupHandler]()
  }
}
