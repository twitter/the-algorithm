package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.t  l ne_module

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.t  l ne_module.Class c
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.t  l ne_module.Class cNoD v der
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.t  l ne_module.ContextEmphas s
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.t  l ne_module.Module aderD splayType
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Module aderD splayTypeMarshaller @ nject() () {

  def apply(d splayType: Module aderD splayType): urt.Module aderD splayType =
    d splayType match {
      case Class c => urt.Module aderD splayType.Class c
      case ContextEmphas s => urt.Module aderD splayType.ContextEmphas s
      case Class cNoD v der => urt.Module aderD splayType.Class cNoD v der
    }

}
