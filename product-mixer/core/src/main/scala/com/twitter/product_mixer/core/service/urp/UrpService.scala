package com.tw ter.product_m xer.core.serv ce.urp

 mport com.tw ter.pages.render.{thr ftscala => urp}
 mport com.tw ter.product_m xer.core.model.marshall ng.request.Request
 mport com.tw ter.product_m xer.core.p pel ne.product.ProductP pel neRequest
 mport com.tw ter.product_m xer.core.product.reg stry.ProductP pel neReg stry
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.conf gap .Params

 mport javax. nject. nject
 mport javax. nject.S ngleton
 mport scala.reflect.runt  .un verse.TypeTag

@S ngleton
class UrpServ ce @ nject() (productP pel neReg stry: ProductP pel neReg stry) {

  def getUrpResponse[RequestType <: Request](
    request: RequestType,
    params: Params
  )(
     mpl c  requestTypeTag: TypeTag[RequestType]
  ): St ch[urp.Page] =
    productP pel neReg stry
      .getProductP pel ne[RequestType, urp.Page](request.product)
      .process(ProductP pel neRequest(request, params))
}
