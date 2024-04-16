package com.tw ter.product_m xer.core.model.marshall ng.response.urt.t  l ne_module

object Module tadata {
  def  sConversat onModule(module tadata: Opt on[Module tadata]): Boolean =
    module tadata.map(_.conversat on tadata). sDef ned
}

case class Module tadata(
  ads tadata: Opt on[Ads tadata],
  conversat on tadata: Opt on[ModuleConversat on tadata],
  gr dCarousel tadata: Opt on[Gr dCarousel tadata])
