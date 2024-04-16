package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urp

 mport com.tw ter.pages.render.{thr ftscala => urp}
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.TransportMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.T  l neScr beConf gMarshaller
 mport com.tw ter.product_m xer.core.model.common. dent f er.TransportMarshaller dent f er
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urp.Page
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class UrpTransportMarshaller @ nject() (
  pageBodyMarshaller: PageBodyMarshaller,
  t  l neScr beConf gMarshaller: T  l neScr beConf gMarshaller,
  page aderMarshaller: Page aderMarshaller,
  pageNavBarMarshaller: PageNavBarMarshaller)
    extends TransportMarshaller[Page, urp.Page] {

  overr de val  dent f er: TransportMarshaller dent f er =
    TransportMarshaller dent f er("Un f edR chPage")

  overr de def apply(page: Page): urp.Page = urp.Page(
     d = page. d,
    pageBody = pageBodyMarshaller(page.pageBody),
    scr beConf g = page.scr beConf g.map(t  l neScr beConf gMarshaller(_)),
    page ader = page.page ader.map(page aderMarshaller(_)),
    pageNavBar = page.pageNavBar.map(pageNavBarMarshaller(_))
  )
}
