package com.tw ter.product_m xer.core.serv ce.urt

 mport com.fasterxml.jackson.datab nd.Ser al zat onFeature
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.UrtTransportMarshaller
 mport com.tw ter.product_m xer.core.model.marshall ng.request.Request
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lure
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.ProductD sabled
 mport com.tw ter.product_m xer.core.p pel ne.product.ProductP pel neRequest
 mport com.tw ter.product_m xer.core.product.reg stry.ProductP pel neReg stry
 mport com.tw ter.product_m xer.core.{thr ftscala => t}
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.conf gap .Params
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport com.tw ter.ut l.jackson.ScalaObjectMapper

 mport javax. nject. nject
 mport javax. nject.S ngleton
 mport scala.reflect.runt  .un verse.TypeTag

/**
 * Look up and execute products  n t  [[ProductP pel neReg stry]]
 */
@S ngleton
class UrtServ ce @ nject() (productP pel neReg stry: ProductP pel neReg stry) {

  def getUrtResponse[RequestType <: Request](
    request: RequestType,
    params: Params
  )(
     mpl c  requestTypeTag: TypeTag[RequestType]
  ): St ch[urt.T  l neResponse] =
    productP pel neReg stry
      .getProductP pel ne[RequestType, urt.T  l neResponse](request.product)
      .process(ProductP pel neRequest(request, params))
      .handle {
        // Detect ProductD sabled and convert   to T  l neUnava lable
        case p pel neFa lure: P pel neFa lure  f p pel neFa lure.category == ProductD sabled =>
          UrtTransportMarshaller.unava lable("")
      }

  /**
   * Get deta led p pel ne execut on as a ser al zed JSON Str ng
   */
  def getP pel neExecut onResult[RequestType <: Request](
    request: RequestType,
    params: Params
  )(
     mpl c  requestTypeTag: TypeTag[RequestType]
  ): St ch[t.P pel neExecut onResult] =
    productP pel neReg stry
      .getProductP pel ne[RequestType, urt.T  l neResponse](request.product)
      .arrow(ProductP pel neRequest(request, params)).map { deta ledResult =>
        val mapper = ScalaObjectMapper()
        // conf gure so that except on  s not thrown w never case class  s not ser al zable
        mapper.underly ng.conf gure(Ser al zat onFeature.FA L_ON_EMPTY_BEANS, false)
        val ser al zedJSON = mapper.wr ePrettyStr ng(deta ledResult)
        t.P pel neExecut onResult(ser al zedJSON)
      }

}
