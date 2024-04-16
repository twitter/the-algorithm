package com.tw ter.product_m xer.core.model.marshall ng.response.urp

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Cl entEvent nfo
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.HasCl entEvent nfo

sealed tra  Page ader

case class Top cPage ader(
  top c d: Str ng,
  facep le: Opt on[Top cPage aderFacep le] = None,
  overr de val cl entEvent nfo: Opt on[Cl entEvent nfo] = None,
  land ngContext: Opt on[Str ng] = None,
  d splayType: Opt on[Top cPage aderD splayType] = So (Bas cTop cPage aderD splayType))
    extends Page ader
    w h HasCl entEvent nfo
