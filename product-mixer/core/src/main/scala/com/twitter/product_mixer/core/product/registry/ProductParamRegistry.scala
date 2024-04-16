package com.tw ter.product_m xer.core.product.reg stry

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.servo.dec der.Dec derGateBu lder
 mport com.tw ter.t  l nes.conf gap .BaseConf gBu lder
 mport com.tw ter.t  l nes.conf gap .Conf g
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class ProductParamReg stry @ nject() (
  productP pel neReg stryConf g: ProductP pel neReg stryConf g,
  dec derGateBu lder: Dec derGateBu lder,
  statsRece ver: StatsRece ver) {

  def bu ld(): Seq[Conf g] = {
    val productConf gs = productP pel neReg stryConf g.productP pel neConf gs.map {
      productP pel neConf g =>
        BaseConf gBu lder(
          productP pel neConf g.paramConf g.bu ld(dec derGateBu lder, statsRece ver))
          .bu ld(productP pel neConf g.paramConf g.getClass.getS mpleNa )
    }

    productConf gs
  }
}
