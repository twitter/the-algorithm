package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt

 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.t  l ne_module.ModuleD splayTypeMarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.Module emTreeD splay
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Module emTreeD splayMarshaller @ nject() (
  moduleD splayTypeMarshaller: ModuleD splayTypeMarshaller) {

  def apply(module emTreeD splay: Module emTreeD splay): urt.Module emTreeD splay =
    urt.Module emTreeD splay(
      parentModule emEntry d = module emTreeD splay.parentModuleEntry em d,
       ndentFromParent = module emTreeD splay. ndentFromParent,
      d splayType = module emTreeD splay.d splayType.map(moduleD splayTypeMarshaller(_)),
       sAnchorCh ld = module emTreeD splay. sAnchorCh ld
    )
}
