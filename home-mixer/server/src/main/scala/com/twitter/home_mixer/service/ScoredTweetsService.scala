package com.tw ter.ho _m xer.serv ce

 mport com.tw ter.ho _m xer.{thr ftscala => t}
 mport com.tw ter.product_m xer.core.model.marshall ng.request.Request
 mport com.tw ter.product_m xer.core.p pel ne.product.ProductP pel neRequest
 mport com.tw ter.product_m xer.core.product.reg stry.ProductP pel neReg stry
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.conf gap .Params
 mport javax. nject. nject
 mport javax. nject.S ngleton
 mport scala.reflect.runt  .un verse._

@S ngleton
class ScoredT etsServ ce @ nject() (productP pel neReg stry: ProductP pel neReg stry) {

  def getScoredT etsResponse[RequestType <: Request](
    request: RequestType,
    params: Params
  )(
     mpl c  requestTypeTag: TypeTag[RequestType]
  ): St ch[t.ScoredT etsResponse] = productP pel neReg stry
    .getProductP pel ne[RequestType, t.ScoredT etsResponse](request.product)
    .process(ProductP pel neRequest(request, params))
}
