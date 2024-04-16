package com.tw ter.ho _m xer.funct onal_component.decorator.urt.bu lder

 mport com.tw ter.product_m xer.core.product.gu ce.scope.ProductScoped
 mport com.tw ter.str ngcenter.cl ent.ExternalStr ngReg stry
 mport javax. nject. nject
 mport javax. nject.Prov der
 mport javax. nject.S ngleton

@S ngleton
class FeedbackStr ngs @ nject() (
  @ProductScoped externalStr ngReg stryProv der: Prov der[ExternalStr ngReg stry]) {
  pr vate val externalStr ngReg stry = externalStr ngReg stryProv der.get()

  val seeLessOftenFeedbackStr ng =
    externalStr ngReg stry.createProdStr ng("Feedback.seeLessOften")
  val seeLessOftenConf rmat onFeedbackStr ng =
    externalStr ngReg stry.createProdStr ng("Feedback.seeLessOftenConf rmat on")
}
