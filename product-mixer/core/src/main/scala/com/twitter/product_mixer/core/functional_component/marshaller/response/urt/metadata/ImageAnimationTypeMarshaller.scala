package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata. mageAn mat onType
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Bounce
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class  mageAn mat onTypeMarshaller @ nject() () {

  def apply( mageAn mat onType:  mageAn mat onType): urt. mageAn mat onType =
     mageAn mat onType match {
      case Bounce => urt. mageAn mat onType.Bounce
    }
}
