package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.t  l ne_module

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.t  l ne_module.ModuleShowMoreBehav or
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.t  l ne_module.ModuleShowMoreBehav orRevealByCount
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class ModuleShowMoreBehav orMarshaller @ nject() (
  moduleShowMoreBehav orRevealByCountMarshaller: ModuleShowMoreBehav orRevealByCountMarshaller) {

  def apply(
    moduleShowMoreBehav or: ModuleShowMoreBehav or
  ): urt.ModuleShowMoreBehav or = moduleShowMoreBehav or match {
    case moduleShowMoreBehav orRevealByCount: ModuleShowMoreBehav orRevealByCount =>
      moduleShowMoreBehav orRevealByCountMarshaller(moduleShowMoreBehav orRevealByCount)
  }
}
