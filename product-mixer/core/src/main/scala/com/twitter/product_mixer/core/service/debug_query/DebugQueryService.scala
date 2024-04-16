package com.tw ter.product_m xer.core.serv ce.debug_query

 mport com.fasterxml.jackson.datab nd.Ser al zat onFeature
 mport com.tw ter.f nagle.Serv ce
 mport com.tw ter.f nagle.context.Contexts
 mport com.tw ter.f nagle.trac ng.Trace.traceLocal
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.transport.Transport
 mport com.tw ter.product_m xer.core.funct onal_component.conf gap .ParamsBu lder
 mport com.tw ter.product_m xer.core.model.common. dent f er.Component dent f erStack
 mport com.tw ter.product_m xer.core.model.marshall ng.request.Product
 mport com.tw ter.product_m xer.core.model.marshall ng.request.Request
 mport com.tw ter.product_m xer.core.p pel ne.product.ProductP pel ne
 mport com.tw ter.product_m xer.core.p pel ne.product.ProductP pel neRequest
 mport com.tw ter.product_m xer.core.product.reg stry.ProductP pel neReg stry
 mport com.tw ter.product_m xer.core.{thr ftscala => t}
 mport com.tw ter.scrooge.Thr ftStruct
 mport com.tw ter.scrooge.{Request => ScroogeRequest}
 mport com.tw ter.scrooge.{Response => ScroogeResponse}
 mport com.tw ter.st ch.St ch
 mport com.tw ter.turntable.context.TurntableRequestContextKey
 mport com.tw ter.ut l.jackson.ScalaObjectMapper
 mport javax. nject. nject
 mport javax. nject.S ngleton
 mport scala.reflect.runt  .un verse.TypeTag

/**
 * Returns t  complete execut on log for a p pel ne query. T se endpo nts are  ntended for
 * debugg ng (pr mar ly through Turntable).
 */
@S ngleton
class DebugQueryServ ce @ nject() (
  productP pel neReg stry: ProductP pel neReg stry,
  paramsBu lder: ParamsBu lder,
  author zat onServ ce: Author zat onServ ce) {

  pr vate val mapper =
    ScalaObjectMapper.bu lder
      .w hAdd  onalJacksonModules(Seq(ParamsSer al zerModule))
      .w hSer al zat onConf g(
        Map(
          // T se are cop ed from t  default ser al zat on conf g.
          Ser al zat onFeature.WR TE_DATES_AS_T MESTAMPS -> false,
          Ser al zat onFeature.WR TE_ENUMS_US NG_TO_STR NG -> true,
          // Generally   want to be defens ve w n ser al z ng s nce   don't control everyth ng that's
          // ser al zed. T   ssue also ca  up w n try ng to ser al ze Un  as part of sync s de effects.
          Ser al zat onFeature.FA L_ON_EMPTY_BEANS -> false,
        ))
      // T  default  mple ntat on represents numbers as JSON Numbers ( .e. Double w h 53 b  prec s on
      // wh ch leads to Snowflake  Ds be ng cropped  n t  case of t ets.
      .w hNumbersAsStr ngs(true)
      .objectMapper

  def apply[
    Thr ftRequest <: Thr ftStruct w h Product1[M xerServ ceRequest],
    M xerServ ceRequest <: Thr ftStruct,
    M xerRequest <: Request
  ](
    unmarshaller: M xerServ ceRequest => M xerRequest
  )(
     mpl c  requestTypeTag: TypeTag[M xerRequest]
  ): Serv ce[ScroogeRequest[Thr ftRequest], ScroogeResponse[t.P pel neExecut onResult]] = {
    (thr ftRequest: ScroogeRequest[Thr ftRequest]) =>
      {

        val request = unmarshaller(thr ftRequest.args._1)
        val params = paramsBu lder.bu ld(
          cl entContext = request.cl entContext,
          product = request.product,
          featureOverr des = request.debugParams.flatMap(_.featureOverr des).getOrElse(Map.empty)
        )

        val productP pel ne = productP pel neReg stry
          .getProductP pel ne[M xerRequest, Any](request.product)
        ver fyRequestAuthor zat on(request.product, productP pel ne)
        Contexts.broadcast.letClear(TurntableRequestContextKey) {
          St ch
            .run(productP pel ne
              .arrow(ProductP pel neRequest(request, params)).map { deta ledResult =>
                // Ser al zat on can be slow so a trace  s useful both for opt m zat on by t  Prom x
                // team and to g ve v s b l y to custo rs.
                val ser al zedJSON =
                  traceLocal("ser al ze_debug_response")(mapper.wr eValueAsStr ng(deta ledResult))
                t.P pel neExecut onResult(ser al zedJSON)
              })
            .map(ScroogeResponse(_))
        }
      }
  }

  pr vate def ver fyRequestAuthor zat on(
    product: Product,
    productP pel ne: ProductP pel ne[_, _]
  ): Un  = {
    val serv ce dent f er = Serv ce dent f er.fromCert f cate(Transport.peerCert f cate)
    val requestContext = Contexts.broadcast
      .get(TurntableRequestContextKey).getOrElse(throw M ss ngTurntableRequestContextExcept on)

    val componentStack = Component dent f erStack(productP pel ne. dent f er, product. dent f er)
    author zat onServ ce.ver fyRequestAuthor zat on(
      componentStack,
      serv ce dent f er,
      productP pel ne.debugAccessPol c es,
      requestContext)
  }
}

object M ss ngTurntableRequestContextExcept on
    extends Except on("Request  s m ss ng turntable request context")
