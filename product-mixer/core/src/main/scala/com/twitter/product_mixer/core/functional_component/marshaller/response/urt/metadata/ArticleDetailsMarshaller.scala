package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Art cleDeta ls
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Art cleDeta lsMarshaller @ nject() () {

  def apply(art cleDeta ls: Art cleDeta ls): urt.Art cleDeta ls = urt.Art cleDeta ls(
    art clePos  on = art cleDeta ls.art clePos  on,
    shareCount = art cleDeta ls.shareCount
  )
}
