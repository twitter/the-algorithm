package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urp

 mport com.tw ter.pages.render.{thr ftscala => urp}
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.UrlMarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urp.Top cPage aderFacep le
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Top cPage aderFacep leMarshaller @ nject() (
  urlMarshaller: UrlMarshaller) {

  def apply(top cPage aderFacep le: Top cPage aderFacep le): urp.Top cPage aderFacep le =
    urp.Top cPage aderFacep le(
      user ds = top cPage aderFacep le.user ds,
      facep leUrl = top cPage aderFacep le.facep leUrl.map(urlMarshaller(_))
    )
}
