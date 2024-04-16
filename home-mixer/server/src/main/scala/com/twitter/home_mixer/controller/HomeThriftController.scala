package com.tw ter.ho _m xer.controller

 mport com.tw ter.f natra.thr ft.Controller
 mport com.tw ter.ho _m xer.marshaller.request.Ho M xerRequestUnmarshaller
 mport com.tw ter.ho _m xer.model.request.Ho M xerRequest
 mport com.tw ter.ho _m xer.serv ce.ScoredT etsServ ce
 mport com.tw ter.ho _m xer.{thr ftscala => t}
 mport com.tw ter.product_m xer.core.controllers.DebugTw terContext
 mport com.tw ter.product_m xer.core.funct onal_component.conf gap .ParamsBu lder
 mport com.tw ter.product_m xer.core.serv ce.debug_query.DebugQueryServ ce
 mport com.tw ter.product_m xer.core.serv ce.urt.UrtServ ce
 mport com.tw ter.snowflake. d.Snowflake d
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.conf gap .Params
 mport javax. nject. nject

class Ho Thr ftController @ nject() (
  ho RequestUnmarshaller: Ho M xerRequestUnmarshaller,
  urtServ ce: UrtServ ce,
  scoredT etsServ ce: ScoredT etsServ ce,
  paramsBu lder: ParamsBu lder)
    extends Controller(t.Ho M xer)
    w h DebugTw terContext {

  handle(t.Ho M xer.GetUrtResponse) { args: t.Ho M xer.GetUrtResponse.Args =>
    val request = ho RequestUnmarshaller(args.request)
    val params = bu ldParams(request)
    St ch.run(urtServ ce.getUrtResponse[Ho M xerRequest](request, params))
  }

  handle(t.Ho M xer.GetScoredT etsResponse) { args: t.Ho M xer.GetScoredT etsResponse.Args =>
    val request = ho RequestUnmarshaller(args.request)
    val params = bu ldParams(request)
    w hDebugTw terContext(request.cl entContext) {
      St ch.run(scoredT etsServ ce.getScoredT etsResponse[Ho M xerRequest](request, params))
    }
  }

  pr vate def bu ldParams(request: Ho M xerRequest): Params = {
    val userAgeOpt = request.cl entContext.user d.map { user d =>
      Snowflake d.t  From dOpt(user d).map(_.unt lNow. nDays).getOrElse( nt.MaxValue)
    }
    val fsCustomMap nput = userAgeOpt.map("account_age_ n_days" -> _).toMap
    paramsBu lder.bu ld(
      cl entContext = request.cl entContext,
      product = request.product,
      featureOverr des = request.debugParams.flatMap(_.featureOverr des).getOrElse(Map.empty),
      fsCustomMap nput = fsCustomMap nput
    )
  }
}
