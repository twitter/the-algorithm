package com.tw ter.product_m xer.core.model.common.presentat on.urt

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.Module emTreeD splay

/*
 * Tree state declar ng  em’s parent relat onsh p w h any ot r  ems  n
 * t  module, any d splay  ndentat on  nformat on, and/or collapsed d splay state.
 */
tra  W h emTreeD splay { self: BaseUrt emPresentat on =>
  def treeD splay: Opt on[Module emTreeD splay]
}
