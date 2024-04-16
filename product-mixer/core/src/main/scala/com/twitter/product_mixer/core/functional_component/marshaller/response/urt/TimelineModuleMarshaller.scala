package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt

 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.Cl entEvent nfoMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata.Feedback nfoMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.t  l ne_module.ModuleD splayTypeMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.t  l ne_module.ModuleFooterMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.t  l ne_module.Module aderMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.t  l ne_module.Module tadataMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.t  l ne_module.ModuleShowMoreBehav orMarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l neModule
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class T  l neModuleMarshaller @ nject() (
  module emMarshaller: Module emMarshaller,
  moduleD splayTypeMarshaller: ModuleD splayTypeMarshaller,
  module aderMarshaller: Module aderMarshaller,
  moduleFooterMarshaller: ModuleFooterMarshaller,
  cl entEvent nfoMarshaller: Cl entEvent nfoMarshaller,
  feedback nfoMarshaller: Feedback nfoMarshaller,
  module tadataMarshaller: Module tadataMarshaller,
  moduleShowMoreBehav orMarshaller: ModuleShowMoreBehav orMarshaller) {

  def apply(t  l neModule: T  l neModule): urt.T  l neModule = urt.T  l neModule(
     ems = t  l neModule. ems.map(module emMarshaller(_, t  l neModule.entry dent f er)),
    d splayType = moduleD splayTypeMarshaller(t  l neModule.d splayType),
     ader = t  l neModule. ader.map(module aderMarshaller(_)),
    footer = t  l neModule.footer.map(moduleFooterMarshaller(_)),
    cl entEvent nfo = t  l neModule.cl entEvent nfo.map(cl entEvent nfoMarshaller(_)),
    feedback nfo = t  l neModule.feedbackAct on nfo.map(feedback nfoMarshaller(_)),
     tadata = t  l neModule. tadata.map(module tadataMarshaller(_)),
    showMoreBehav or = t  l neModule.showMoreBehav or.map(moduleShowMoreBehav orMarshaller(_))
  )
}
