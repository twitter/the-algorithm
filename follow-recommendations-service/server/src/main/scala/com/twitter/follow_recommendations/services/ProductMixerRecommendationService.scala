package com.tw ter.follow_recom ndat ons.serv ces

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport javax. nject. nject
 mport javax. nject.S ngleton
 mport com.tw ter.t  l nes.conf gap .Params
 mport com.tw ter.follow_recom ndat ons.common.ut ls.D splayLocat onProductConverterUt l
 mport com.tw ter.follow_recom ndat ons.conf gap .dec ders.Dec derParams
 mport com.tw ter.follow_recom ndat ons.logg ng.FrsLogger
 mport com.tw ter.follow_recom ndat ons.models.{DebugParams => FrsDebugParams}
 mport com.tw ter.follow_recom ndat ons.models.Recom ndat onRequest
 mport com.tw ter.follow_recom ndat ons.models.Recom ndat onResponse
 mport com.tw ter.follow_recom ndat ons.models.Request
 mport com.tw ter.product_m xer.core.model.marshall ng.request.{
  DebugParams => ProductM xerDebugParams
}
 mport com.tw ter.product_m xer.core.product.reg stry.ProductP pel neReg stry
 mport com.tw ter.product_m xer.core.p pel ne.product.ProductP pel neRequest
 mport com.tw ter.st ch.St ch

@S ngleton
class ProductM xerRecom ndat onServ ce @ nject() (
  productP pel neReg stry: ProductP pel neReg stry,
  resultLogger: FrsLogger,
  baseStats: StatsRece ver) {

  pr vate val stats = baseStats.scope("product_m xer_recos_serv ce_stats")
  pr vate val logg ngStats = stats.scope("logged")

  def get(request: Recom ndat onRequest, params: Params): St ch[Recom ndat onResponse] = {
     f (params(Dec derParams.EnableRecom ndat ons)) {
      val productM xerRequest = convertToProductM xerRequest(request)

      productP pel neReg stry
        .getProductP pel ne[Request, Recom ndat onResponse](productM xerRequest.product)
        .process(ProductP pel neRequest(productM xerRequest, params)).onSuccess { response =>
           f (resultLogger.shouldLog(request.debugParams)) {
            logg ngStats.counter(). ncr()
            resultLogger.logRecom ndat onResult(request, response)
          }
        }
    } else {
      St ch.value(Recom ndat onResponse(N l))
    }

  }

  def convertToProductM xerRequest(frsRequest: Recom ndat onRequest): Request = {
    Request(
      maxResults = frsRequest.maxResults,
      debugParams = convertToProductM xerDebugParams(frsRequest.debugParams),
      productContext = None,
      product =
        D splayLocat onProductConverterUt l.d splayLocat onToProduct(frsRequest.d splayLocat on),
      cl entContext = frsRequest.cl entContext,
      ser al zedRequestCursor = frsRequest.cursor,
      frsDebugParams = frsRequest.debugParams,
      d splayLocat on = frsRequest.d splayLocat on,
      excluded ds = frsRequest.excluded ds,
      fetchPromotedContent = frsRequest.fetchPromotedContent,
      userLocat onState = frsRequest.userLocat onState
    )
  }

  pr vate def convertToProductM xerDebugParams(
    frsDebugParams: Opt on[FrsDebugParams]
  ): Opt on[ProductM xerDebugParams] = {
    frsDebugParams.map { debugParams =>
      ProductM xerDebugParams(debugParams.featureOverr des, None)
    }
  }
}
