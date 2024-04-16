package com.tw ter.product_m xer.core.serv ce.sl ce

 mport com.tw ter.product_m xer.core.model.marshall ng.request.Request
 mport com.tw ter.product_m xer.core.p pel ne.product.ProductP pel neRequest
 mport com.tw ter.product_m xer.core.product.reg stry.ProductP pel neReg stry
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.graphql.thr ftscala.Sl ceResult
 mport com.tw ter.t  l nes.conf gap .Params

 mport javax. nject. nject
 mport javax. nject.S ngleton
 mport scala.reflect.runt  .un verse.TypeTag

/**
 * Look up and execute Sl ce products  n t  [[ProductP pel neReg stry]]
 */
@S ngleton
class Sl ceServ ce @ nject() (productP pel neReg stry: ProductP pel neReg stry) {

  def getSl ceResponse[RequestType <: Request](
    request: RequestType,
    params: Params
  )(
     mpl c  requestTypeTag: TypeTag[RequestType]
  ): St ch[Sl ceResult] =
    productP pel neReg stry
      .getProductP pel ne[RequestType, Sl ceResult](request.product)
      .process(ProductP pel neRequest(request, params))
}
