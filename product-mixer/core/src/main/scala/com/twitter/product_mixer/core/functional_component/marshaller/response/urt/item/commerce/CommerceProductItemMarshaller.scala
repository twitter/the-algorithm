package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.com rce

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.com rce.Com rceProduct em
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Com rceProduct emMarshaller @ nject() () {

  def apply(com rceProduct em: Com rceProduct em): urt.T  l ne emContent =
    urt.T  l ne emContent.Com rceProduct(urt.Com rceProduct(com rceProduct em. d))
}
