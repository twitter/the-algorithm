package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata. mageD splayType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata. con
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.FullW dth
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata. conSmall
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class  mageD splayTypeMarshaller @ nject() () {

  def apply( mageD splayType:  mageD splayType): urt. mageD splayType =
     mageD splayType match {
      case  con => urt. mageD splayType. con
      case FullW dth => urt. mageD splayType.FullW dth
      case  conSmall => urt. mageD splayType. conSmall
    }
}
