package com.tw ter.product_m xer.core.product.reg stry

 mport com.tw ter.product_m xer.core.model.marshall ng.request.Request
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.p pel ne.product.ProductP pel neConf g

tra  ProductP pel neReg stryConf g {
  def productP pel neConf gs: Seq[ProductP pel neConf g[_ <: Request, _ <: P pel neQuery, _]]
}
