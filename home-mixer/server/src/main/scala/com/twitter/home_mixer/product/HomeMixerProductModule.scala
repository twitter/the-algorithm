package com.tw ter.ho _m xer.product

 mport com.tw ter. nject.Tw terModule
 mport com.tw ter.product_m xer.core.product.reg stry.ProductP pel neReg stryConf g

object Ho M xerProductModule extends Tw terModule {

  overr de def conf gure(): Un  = {
    b nd[ProductP pel neReg stryConf g].to[Ho ProductP pel neReg stryConf g]
  }
}
