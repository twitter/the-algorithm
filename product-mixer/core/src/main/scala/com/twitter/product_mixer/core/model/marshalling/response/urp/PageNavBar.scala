package com.tw ter.product_m xer.core.model.marshall ng.response.urp

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Cl entEvent nfo
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.HasCl entEvent nfo

sealed tra  PageNavBar

case class Top cPageNavBar(
  top c d: Str ng,
  overr de val cl entEvent nfo: Opt on[Cl entEvent nfo] = None)
    extends PageNavBar
    w h HasCl entEvent nfo

case class T leNavBar(
  t le: Str ng,
  subt le: Opt on[Str ng] = None,
  overr de val cl entEvent nfo: Opt on[Cl entEvent nfo] = None)
    extends PageNavBar
    w h HasCl entEvent nfo
