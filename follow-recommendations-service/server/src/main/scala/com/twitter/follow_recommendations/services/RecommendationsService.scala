package com.tw ter.follow_recom ndat ons.serv ces

 mport com.tw ter.follow_recom ndat ons.conf gap .dec ders.Dec derParams
 mport com.tw ter.follow_recom ndat ons.logg ng.FrsLogger
 mport com.tw ter.follow_recom ndat ons.models.Recom ndat onRequest
 mport com.tw ter.follow_recom ndat ons.models.Recom ndat onResponse
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.conf gap .Params
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Recom ndat onsServ ce @ nject() (
  productRecom nderServ ce: ProductRecom nderServ ce,
  resultLogger: FrsLogger) {
  def get(request: Recom ndat onRequest, params: Params): St ch[Recom ndat onResponse] = {
     f (params(Dec derParams.EnableRecom ndat ons)) {
      productRecom nderServ ce
        .getRecom ndat ons(request, params).map(Recom ndat onResponse).onSuccess { response =>
           f (resultLogger.shouldLog(request.debugParams)) {
            resultLogger.logRecom ndat onResult(request, response)
          }
        }
    } else {
      St ch.value(Recom ndat onResponse(N l))
    }
  }
}
