package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.t  l ne_module

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.t  l ne_module.ModuleShowMoreBehav orRevealByCount
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class ModuleShowMoreBehav orRevealByCountMarshaller @ nject() () {

  def apply(
    moduleShowMoreBehav orRevealByCount: ModuleShowMoreBehav orRevealByCount
  ): urt.ModuleShowMoreBehav or =
    urt.ModuleShowMoreBehav or.RevealByCount(
      urt.ModuleShowMoreBehav orRevealByCount(
         n  al emsCount = moduleShowMoreBehav orRevealByCount. n  al emsCount,
        showMore emsCount = moduleShowMoreBehav orRevealByCount.showMore emsCount
      )
    )
}
