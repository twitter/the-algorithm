package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urp

 mport com.tw ter.pages.render.{thr ftscala => urp}
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.Cl entEvent nfoMarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urp.Top cPage ader
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Top cPage aderMarshaller @ nject() (
  top cPage aderFacep leMarshaller: Top cPage aderFacep leMarshaller,
  cl entEvent nfoMarshaller: Cl entEvent nfoMarshaller,
  top cPage aderD splayTypeMarshaller: Top cPage aderD splayTypeMarshaller) {

  def apply(top cPage ader: Top cPage ader): urp.Top cPage ader =
    urp.Top cPage ader(
      top c d = top cPage ader.top c d,
      facep le = top cPage ader.facep le.map(top cPage aderFacep leMarshaller(_)),
      cl entEvent nfo = top cPage ader.cl entEvent nfo.map(cl entEvent nfoMarshaller(_)),
      land ngContext = top cPage ader.land ngContext,
      d splayType = top cPage ader.d splayType
        .map(top cPage aderD splayTypeMarshaller(_)).getOrElse(
          urp.Top cPage aderD splayType.Bas c)
    )
}
