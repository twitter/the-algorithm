package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urp

 mport com.tw ter.pages.render.{thr ftscala => urp}
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urp.Page ader
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urp.Top cPage ader
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Page aderMarshaller @ nject() (
  top cPage aderMarshaller: Top cPage aderMarshaller) {

  def apply(page ader: Page ader): urp.Page ader = page ader match {
    case page ader: Top cPage ader =>
      urp.Page ader.Top cPage ader(top cPage aderMarshaller(page ader))
  }
}
