package com.tw ter.product_m xer.core.model.marshall ng.response.urt.t  l ne_module

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. con.Hor zon con
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata. mageVar ant
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Soc alContext

case class Module ader(
  text: Str ng,
  st cky: Opt on[Boolean],
   con: Opt on[Hor zon con],
  custom con: Opt on[ mageVar ant],
  soc alContext: Opt on[Soc alContext],
  module aderD splayType: Module aderD splayType)
