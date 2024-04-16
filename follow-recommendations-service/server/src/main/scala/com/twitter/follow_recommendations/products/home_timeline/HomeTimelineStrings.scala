package com.tw ter.follow_recom ndat ons.products.ho _t  l ne

 mport com.tw ter.product_m xer.core.product.gu ce.scope.ProductScoped
 mport com.tw ter.str ngcenter.cl ent.ExternalStr ngReg stry
 mport com.tw ter.str ngcenter.cl ent.core.ExternalStr ng
 mport javax. nject. nject
 mport javax. nject.Prov der
 mport javax. nject.S ngleton

@S ngleton
class Ho T  l neStr ngs @ nject() (
  @ProductScoped externalStr ngReg stryProv der: Prov der[ExternalStr ngReg stry]) {
  pr vate val externalStr ngReg stry = externalStr ngReg stryProv der.get()
  val whoToFollowFollo dByManyUserS ngleStr ng: ExternalStr ng =
    externalStr ngReg stry.createProdStr ng("WtfRecom ndat onContext.follo dByManyUserS ngle")
  val whoToFollowFollo dByManyUserDoubleStr ng: ExternalStr ng =
    externalStr ngReg stry.createProdStr ng("WtfRecom ndat onContext.follo dByManyUserDouble")
  val whoToFollowFollo dByManyUserMult pleStr ng: ExternalStr ng =
    externalStr ngReg stry.createProdStr ng("WtfRecom ndat onContext.follo dByManyUserMult ple")
  val whoToFollowPopular nCountryKey: ExternalStr ng =
    externalStr ngReg stry.createProdStr ng("WtfRecom ndat onContext.popular nCountry")
  val whoToFollowModuleT le: ExternalStr ng =
    externalStr ngReg stry.createProdStr ng("WhoToFollowModule.t le")
  val whoToFollowModuleFooter: ExternalStr ng =
    externalStr ngReg stry.createProdStr ng("WhoToFollowModule.p vot")
}
