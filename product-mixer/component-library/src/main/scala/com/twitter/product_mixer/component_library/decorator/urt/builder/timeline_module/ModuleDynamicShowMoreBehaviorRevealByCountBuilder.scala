package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.t  l ne_module

 mport com.tw ter.product_m xer.core.funct onal_component.decorator.urt.bu lder.t  l ne_module.BaseModuleShowMoreBehav orBu lder
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.t  l ne_module.ModuleShowMoreBehav or
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.t  l ne_module.ModuleShowMoreBehav orRevealByCount
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

case class ModuleDynam cShowMoreBehav orRevealByCountBu lder(
   n  al emsCount:  nt,
  showMore emsCount:  nt)
    extends BaseModuleShowMoreBehav orBu lder[P pel neQuery, Un versalNoun[Any]] {

  overr de def apply(
    query: P pel neQuery,
    cand date: Seq[Cand dateW hFeatures[Un versalNoun[Any]]]
  ): ModuleShowMoreBehav or = ModuleShowMoreBehav orRevealByCount(
     n  al emsCount =  n  al emsCount,
    showMore emsCount = showMore emsCount
  )
}
