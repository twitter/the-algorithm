package com.tw ter.follow_recom ndat ons.modules

 mport com.tw ter.follow_recom ndat ons.products.ProdProductReg stry
 mport com.tw ter.follow_recom ndat ons.products.common.ProductReg stry
 mport com.tw ter. nject.Tw terModule
 mport javax. nject.S ngleton

object ProductReg stryModule extends Tw terModule {
  overr de protected def conf gure(): Un  = {
    b nd[ProductReg stry].to[ProdProductReg stry]. n[S ngleton]
  }
}
