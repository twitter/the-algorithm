package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. d a

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. d a. d aKey
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class  d aKeyMarshaller @ nject() () {

  def apply( d aKey:  d aKey): urt. d aKey = urt. d aKey(
     d =  d aKey. d,
    category =  d aKey.category
  )
}
