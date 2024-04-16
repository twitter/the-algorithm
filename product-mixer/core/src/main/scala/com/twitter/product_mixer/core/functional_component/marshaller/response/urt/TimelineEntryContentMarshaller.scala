package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neEntry
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l ne em
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neModule
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neOperat on
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class T  l neEntryContentMarshaller @ nject() (
  t  l ne emMarshaller: T  l ne emMarshaller,
  t  l neModuleMarshaller: T  l neModuleMarshaller,
  t  l neOperat onMarshaller: T  l neOperat onMarshaller) {

  def apply(entry: T  l neEntry): urt.T  l neEntryContent = entry match {
    case  em: T  l ne em =>
      urt.T  l neEntryContent. em(t  l ne emMarshaller( em))
    case module: T  l neModule =>
      urt.T  l neEntryContent.T  l neModule(t  l neModuleMarshaller(module))
    case operat on: T  l neOperat on =>
      urt.T  l neEntryContent.Operat on(t  l neOperat onMarshaller(operat on))
  }
}
