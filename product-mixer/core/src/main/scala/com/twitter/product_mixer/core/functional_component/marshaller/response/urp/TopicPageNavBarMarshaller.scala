package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urp

 mport com.tw ter.pages.render.{thr ftscala => urp}
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.Cl entEvent nfoMarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urp.Top cPageNavBar
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Top cPageNavBarMarshaller @ nject() (
  cl entEvent nfoMarshaller: Cl entEvent nfoMarshaller) {

  def apply(top cPageNavBar: Top cPageNavBar): urp.Top cPageNavBar =
    urp.Top cPageNavBar(
      top c d = top cPageNavBar.top c d,
      cl entEvent nfo = top cPageNavBar.cl entEvent nfo.map(cl entEvent nfoMarshaller(_))
    )
}
