package com.tw ter.product_m xer.core.model.marshall ng.response.urt.cover

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.button.ButtonStyle
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. con.Hor zon con
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Callback
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Cl entEvent nfo

case class CoverCta(
  text: Str ng,
  ctaBehav or: CoverCtaBehav or,
  callbacks: Opt on[L st[Callback]],
  cl entEvent nfo: Opt on[Cl entEvent nfo],
   con: Opt on[Hor zon con],
  buttonStyle: Opt on[ButtonStyle])
