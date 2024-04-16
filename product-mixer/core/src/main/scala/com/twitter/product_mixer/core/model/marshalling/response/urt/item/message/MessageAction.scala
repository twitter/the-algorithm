package com.tw ter.product_m xer.core.model.marshall ng.response.urt. em. ssage

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Callback
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Cl entEvent nfo

case class  ssageAct on(
  d sm ssOnCl ck: Boolean,
  url: Opt on[Str ng],
  cl entEvent nfo: Opt on[Cl entEvent nfo],
  onCl ckCallbacks: Opt on[Seq[Callback]])
