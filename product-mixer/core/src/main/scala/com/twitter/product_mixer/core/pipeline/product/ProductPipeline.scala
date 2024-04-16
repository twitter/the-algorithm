package com.tw ter.product_m xer.core.p pel ne.product

 mport com.tw ter.product_m xer.core.funct onal_component.common.access_pol cy.W hDebugAccessPol c es
 mport com.tw ter.product_m xer.core.model.common. dent f er.ProductP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.marshall ng.request.Request
 mport com.tw ter.product_m xer.core.p pel ne.P pel ne
 mport com.tw ter.st ch.Arrow

/**
 * A Product P pel ne
 *
 * T   s an abstract class, as   only construct t se v a t  [[ProductP pel neBu lder]].
 *
 * A [[ProductP pel ne]]  s capable of process ng a [[Request]] and return ng a response.
 *
 * @tparam RequestType t  doma n model for t  query or request
 * @tparam ResponseType t  f nal marshalled result type
 */
abstract class ProductP pel ne[RequestType <: Request, ResponseType] pr vate[product]
    extends P pel ne[ProductP pel neRequest[RequestType], ResponseType]
    w h W hDebugAccessPol c es {
  overr de pr vate[core] val conf g: ProductP pel neConf g[RequestType, _, ResponseType]
  overr de val arrow: Arrow[
    ProductP pel neRequest[RequestType],
    ProductP pel neResult[ResponseType]
  ]
  overr de val  dent f er: ProductP pel ne dent f er
}
