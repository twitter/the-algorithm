package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urp

 mport com.tw ter.pages.render.{thr ftscala => urp}
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urp.Seg ntedT  l nesPageBody
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Seg ntedT  l nesMarshaller @ nject() (
  seg ntedT  l neMarshaller: Seg ntedT  l neMarshaller) {

  def apply(seg ntedT  l nesPageBody: Seg ntedT  l nesPageBody): urp.Seg ntedT  l nes =
    urp.Seg ntedT  l nes(
       n  alT  l ne = seg ntedT  l neMarshaller(seg ntedT  l nesPageBody. n  alT  l ne),
      t  l nes = seg ntedT  l nesPageBody.t  l nes.map(seg ntedT  l neMarshaller(_))
    )
}
