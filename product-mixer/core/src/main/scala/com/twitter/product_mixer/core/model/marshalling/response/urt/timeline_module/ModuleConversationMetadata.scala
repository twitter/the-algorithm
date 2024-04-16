package com.tw ter.product_m xer.core.model.marshall ng.response.urt.t  l ne_module

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Soc alContext

case class ModuleConversat on tadata(
  allT et ds: Opt on[Seq[Long]],
  soc alContext: Opt on[Soc alContext],
  enableDedupl cat on: Opt on[Boolean])
