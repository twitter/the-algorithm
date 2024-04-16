package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.AddToModuleT  l ne nstruct on
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class AddToModule nstruct onMarshaller @ nject() (module emMarshaller: Module emMarshaller) {

  def apply( nstruct on: AddToModuleT  l ne nstruct on): urt.AddToModule = urt.AddToModule(
    module ems =  nstruct on.module ems.map(module emMarshaller(_,  nstruct on.moduleEntry d)),
    moduleEntry d =  nstruct on.moduleEntry d,
    module emEntry d =  nstruct on.module emEntry d,
    prepend =  nstruct on.prepend
  )
}
