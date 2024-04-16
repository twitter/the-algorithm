package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.promoted

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted.DcmUrlOverr deType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted.UnknownUrlOverr deType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted.UrlOverr deType
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class UrlOverr deTypeMarshaller @ nject() () {

  def apply(urlOverr deType: UrlOverr deType): urt.UrlOverr deType = urlOverr deType match {
    case UnknownUrlOverr deType => urt.UrlOverr deType.Unknown
    case DcmUrlOverr deType => urt.UrlOverr deType.Dcm
  }
}
