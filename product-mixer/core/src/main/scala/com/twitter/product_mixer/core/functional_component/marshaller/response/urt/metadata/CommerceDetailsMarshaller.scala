package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Com rceDeta ls
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Com rceDeta lsMarshaller @ nject() () {

  def apply(com rceDeta ls: Com rceDeta ls): urt.Com rceDeta ls = urt.Com rceDeta ls(
    drop d = com rceDeta ls.drop d,
    shopV2 d = com rceDeta ls.shopV2 d,
    productKey = com rceDeta ls.productKey,
     rchant d = com rceDeta ls. rchant d,
    product ndex = com rceDeta ls.product ndex,
  )
}
