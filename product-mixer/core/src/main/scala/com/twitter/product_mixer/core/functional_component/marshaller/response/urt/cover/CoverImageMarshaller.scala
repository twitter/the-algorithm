package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.cover

 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata. mageAn mat onTypeMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata. mageD splayTypeMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata. mageVar antMarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.cover.Cover mage
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Cover mageMarshaller @ nject() (
   mageVar antMarshaller:  mageVar antMarshaller,
   mageD splayTypeMarshaller:  mageD splayTypeMarshaller,
   mageAn mat onTypeMarshaller:  mageAn mat onTypeMarshaller) {

  def apply(cover mage: Cover mage): urt.Cover mage =
    urt.Cover mage(
       mage =  mageVar antMarshaller(cover mage. mageVar ant),
       mageD splayType =  mageD splayTypeMarshaller(cover mage. mageD splayType),
       mageAn mat onType = cover mage. mageAn mat onType.map( mageAn mat onTypeMarshaller(_))
    )
}
