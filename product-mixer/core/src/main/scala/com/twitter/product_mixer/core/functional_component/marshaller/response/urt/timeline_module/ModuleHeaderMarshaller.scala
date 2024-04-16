package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.t  l ne_module

 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. con.Hor zon conMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata. mageVar antMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.Soc alContextMarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.t  l ne_module.Module ader
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Module aderMarshaller @ nject() (
  hor zon conMarshaller: Hor zon conMarshaller,
   mageVar antMarshaller:  mageVar antMarshaller,
  soc alContextMarshaller: Soc alContextMarshaller,
  module aderD splayTypeMarshaller: Module aderD splayTypeMarshaller) {

  def apply( ader: Module ader): urt.Module ader = urt.Module ader(
    text =  ader.text,
    st cky =  ader.st cky,
     con =  ader. con.map(hor zon conMarshaller(_)),
    custom con =  ader.custom con.map( mageVar antMarshaller(_)),
    soc alContext =  ader.soc alContext.map(soc alContextMarshaller(_)),
    d splayType = module aderD splayTypeMarshaller( ader.module aderD splayType)
  )
}
