package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. em.aud o_space

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.aud o_space.Aud oSpace em
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Aud oSpace emMarshaller @ nject() () {

  def apply(aud oSpace em: Aud oSpace em): urt.T  l ne emContent =
    urt.T  l ne emContent.Aud oSpace(
      urt.Aud oSpace(
         d = aud oSpace em. d
      )
    )
}
