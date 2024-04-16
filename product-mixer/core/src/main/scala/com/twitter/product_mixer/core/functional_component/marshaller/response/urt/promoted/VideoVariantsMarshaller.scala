package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.promoted

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.promoted.V deoVar ant
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject.S ngleton

@S ngleton
class V deoVar antsMarshaller {
  def apply(v deoVar ants: Seq[V deoVar ant]): Seq[urt.V deoVar ant] = {
    v deoVar ants.map(v deoVar ant =>
      urt.V deoVar ant(
        url = v deoVar ant.url,
        contentType = v deoVar ant.contentType,
        b rate = v deoVar ant.b rate
      ))
  }
}
