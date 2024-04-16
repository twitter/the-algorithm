package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. d a

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. d a.T et d a
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class T et d aMarshaller @ nject() () {

  def apply(t et d a: T et d a): urt.T et d a = urt.T et d a(
    t et d = t et d a.t et d,
    mo nt d = t et d a.mo nt d
  )
}
