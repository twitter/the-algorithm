package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em. ssage

 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata. mageVar antMarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em. ssage. ssage mage
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class  ssage mageMarshaller @ nject() (
   mageVar antMarshaller:  mageVar antMarshaller) {

  def apply( ssage mage:  ssage mage): urt. ssage mage = {
    urt. ssage mage(
       mageVar ants =  ssage mage. mageVar ants.map( mageVar antMarshaller(_)),
      backgroundColor =  ssage mage.backgroundColor
    )
  }
}
