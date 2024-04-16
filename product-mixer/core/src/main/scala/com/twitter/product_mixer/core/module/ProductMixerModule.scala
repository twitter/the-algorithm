package com.tw ter.product_m xer.core.module

 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.product_m xer.core.module.product_m xer_flags.ProductM xerFlagModule
 mport com.tw ter.f natra.dec der.modules.Dec derModule
 mport com.tw ter.f natra. nternat onal.modules.LanguagesModule
 mport com.tw ter.product_m xer.core.product.gu ce.ProductScopeModule
 mport com.tw ter.f natra.jackson.modules.ScalaObjectMapperModule
 mport com.tw ter. nject.thr ft.modules.Thr ftCl ent dModule

/**
 * ProductM xerModule prov des modules requ red by all Product M xer serv ces.
 *
 * @note  f y  serv ce calls Strato   w ll need to add t  [[StratoCl entModule]] y self.
 */
object ProductM xerModule extends Tw terModule {

  overr de val modules = Seq(
    ABDec derModule,
    Conf gAp Module,
    Dec derModule,
    FeatureSw c sModule,
    LanguagesModule,
    P pel neExecut onLoggerModule,
    ProductM xerFlagModule,
    new ProductScopeModule(),
    ScalaObjectMapperModule,
    Thr ftCl ent dModule,
  )
}
