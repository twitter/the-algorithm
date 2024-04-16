package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. d a

 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata. mageVar antMarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. d a.Broadcast d
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. d a. mage
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. d a. d aEnt y
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. d a.T et d a
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class  d aEnt yMarshaller @ nject() (
  t et d aMarshaller: T et d aMarshaller,
  broadcast dMarshaller: Broadcast dMarshaller,
   mageVar antMarshaller:  mageVar antMarshaller) {

  def apply( d aEnt y:  d aEnt y): urt. d aEnt y =  d aEnt y match {
    case t et d a: T et d a => urt. d aEnt y.T et d a(t et d aMarshaller(t et d a))
    case broadcast d: Broadcast d => urt. d aEnt y.Broadcast d(broadcast dMarshaller(broadcast d))
    case  mage:  mage => urt. d aEnt y. mage( mageVar antMarshaller( mage. mage))
  }
}
