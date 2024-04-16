package com.tw ter.product_m xer.core.funct onal_component.conf gap 

 mport com.tw ter.product_m xer.core.funct onal_component.conf gap .reg stry.GlobalParamReg stry
 mport com.tw ter.product_m xer.core.product.reg stry.ProductParamReg stry
 mport com.tw ter.t  l nes.conf gap .Compos eConf g
 mport com.tw ter.t  l nes.conf gap .Conf g
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Conf gBu lder @ nject() (
  productParamReg stry: ProductParamReg stry,
  globalParamReg stry: GlobalParamReg stry) {

  def bu ld(): Conf g =
    new Compos eConf g(productParamReg stry.bu ld() ++ Seq(globalParamReg stry.bu ld()))
}
