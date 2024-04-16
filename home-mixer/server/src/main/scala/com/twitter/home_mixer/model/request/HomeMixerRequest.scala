package com.tw ter.ho _m xer.model.request

 mport com.tw ter.product_m xer.core.model.marshall ng.request.Cl entContext
 mport com.tw ter.product_m xer.core.model.marshall ng.request.DebugParams
 mport com.tw ter.product_m xer.core.model.marshall ng.request.Product
 mport com.tw ter.product_m xer.core.model.marshall ng.request.ProductContext
 mport com.tw ter.product_m xer.core.model.marshall ng.request.Request

case class Ho M xerRequest(
  overr de val cl entContext: Cl entContext,
  overr de val product: Product,
  // Product-spec f c para ters should be placed  n t  Product Context
  overr de val productContext: Opt on[ProductContext],
  overr de val ser al zedRequestCursor: Opt on[Str ng],
  overr de val maxResults: Opt on[ nt],
  overr de val debugParams: Opt on[DebugParams],
  // Para ters that apply to all products can be promoted to t  request-level
  ho RequestParam: Boolean)
    extends Request
