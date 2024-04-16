package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.T  l nesDeta ls
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class T  l nesDeta lsMarshaller @ nject() () {

  def apply(t  l nesDeta ls: T  l nesDeta ls): urt.T  l nesDeta ls = urt.T  l nesDeta ls(
     nject onType = t  l nesDeta ls. nject onType,
    controllerData = t  l nesDeta ls.controllerData,
    s ceData = t  l nesDeta ls.s ceData
  )
}
