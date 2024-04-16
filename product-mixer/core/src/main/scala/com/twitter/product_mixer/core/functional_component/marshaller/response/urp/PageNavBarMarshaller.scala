package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urp

 mport com.tw ter.pages.render.{thr ftscala => urp}
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urp.PageNavBar
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urp.Top cPageNavBar
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urp.T leNavBar
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class PageNavBarMarshaller @ nject() (
  top cPageNavBarMarshaller: Top cPageNavBarMarshaller,
  t leNavBarMarshaller: T leNavBarMarshaller) {

  def apply(pageNavBar: PageNavBar): urp.PageNavBar = pageNavBar match {
    case pageNavBar: Top cPageNavBar =>
      urp.PageNavBar.Top cPageNavBar(top cPageNavBarMarshaller(pageNavBar))
    case pageNavBar: T leNavBar =>
      urp.PageNavBar.T leNavBar(t leNavBarMarshaller(pageNavBar))
  }
}
