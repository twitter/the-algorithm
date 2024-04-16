package com.tw ter.follow_recom ndat ons.models

 mport com.tw ter.follow_recom ndat ons.common.models.D splayLocat on
 mport com.tw ter.product_m xer.core.model.marshall ng.request
 mport com.tw ter.product_m xer.core.model.marshall ng.request.Cl entContext
 mport com.tw ter.product_m xer.core.model.marshall ng.request.ProductContext
 mport com.tw ter.product_m xer.core.model.marshall ng.request.{Request => ProductM xerRequest}

case class Request(
  overr de val maxResults: Opt on[ nt],
  overr de val debugParams: Opt on[request.DebugParams],
  overr de val productContext: Opt on[ProductContext],
  overr de val product: request.Product,
  overr de val cl entContext: Cl entContext,
  overr de val ser al zedRequestCursor: Opt on[Str ng],
  overr de val frsDebugParams: Opt on[DebugParams],
  d splayLocat on: D splayLocat on,
  excluded ds: Opt on[Seq[Long]],
  fetchPromotedContent: Opt on[Boolean],
  userLocat onState: Opt on[Str ng] = None)
    extends ProductM xerRequest
    w h HasFrsDebugParams {}
