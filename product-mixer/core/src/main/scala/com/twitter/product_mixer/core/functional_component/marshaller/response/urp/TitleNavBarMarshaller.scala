package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urp

 mport com.tw ter.pages.render.{thr ftscala => urp}
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.Cl entEvent nfoMarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urp.T leNavBar
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class T leNavBarMarshaller @ nject() (
  cl entEvent nfoMarshaller: Cl entEvent nfoMarshaller) {

  def apply(t leNavBar: T leNavBar): urp.T leNavBar =
    urp.T leNavBar(
      t le = t leNavBar.t le,
      subt le = t leNavBar.subt le,
      cl entEvent nfo = t leNavBar.cl entEvent nfo.map(cl entEvent nfoMarshaller(_))
    )
}
