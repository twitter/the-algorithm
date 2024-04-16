package com.tw ter.ho _m xer.marshaller.request

 mport com.tw ter.ho _m xer.model.request.Ho M xerRequest
 mport com.tw ter.ho _m xer.{thr ftscala => t}
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.request.Cl entContextUnmarshaller
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Ho M xerRequestUnmarshaller @ nject() (
  cl entContextUnmarshaller: Cl entContextUnmarshaller,
  ho ProductUnmarshaller: Ho M xerProductUnmarshaller,
  ho ProductContextUnmarshaller: Ho M xerProductContextUnmarshaller,
  ho DebugParamsUnmarshaller: Ho M xerDebugParamsUnmarshaller) {

  def apply(ho Request: t.Ho M xerRequest): Ho M xerRequest = {
    Ho M xerRequest(
      cl entContext = cl entContextUnmarshaller(ho Request.cl entContext),
      product = ho ProductUnmarshaller(ho Request.product),
      productContext = ho Request.productContext.map(ho ProductContextUnmarshaller(_)),
      // Avo d de-ser al z ng cursors  n t  request unmarshaller. T  unmarshaller should never
      // fa l, wh ch  s often a poss b l y w n try ng to de-ser al ze a cursor. Cursors can also
      // be product-spec f c and more appropr ately handled  n  nd v dual product p pel nes.
      ser al zedRequestCursor = ho Request.cursor,
      maxResults = ho Request.maxResults,
      debugParams = ho Request.debugParams.map(ho DebugParamsUnmarshaller(_)),
      ho RequestParam = false
    )
  }
}
