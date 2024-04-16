package com.tw ter.follow_recom ndat ons.controllers

 mport com.tw ter.f natra.thr ft.Controller
 mport com.tw ter.follow_recom ndat ons.conf gap .ParamsFactory
 mport com.tw ter.follow_recom ndat ons.serv ces.ProductP pel neSelector
 mport com.tw ter.follow_recom ndat ons.serv ces.UserScor ngServ ce
 mport com.tw ter.follow_recom ndat ons.thr ftscala.FollowRecom ndat onsThr ftServ ce
 mport com.tw ter.follow_recom ndat ons.thr ftscala.FollowRecom ndat onsThr ftServ ce._
 mport com.tw ter.st ch.St ch
 mport javax. nject. nject

class Thr ftController @ nject() (
  userScor ngServ ce: UserScor ngServ ce,
  recom ndat onRequestBu lder: Recom ndat onRequestBu lder,
  scor ngUserRequestBu lder: Scor ngUserRequestBu lder,
  productP pel neSelector: ProductP pel neSelector,
  paramsFactory: ParamsFactory)
    extends Controller(FollowRecom ndat onsThr ftServ ce) {

  handle(GetRecom ndat ons) { args: GetRecom ndat ons.Args =>
    val st ch = recom ndat onRequestBu lder.fromThr ft(args.request).flatMap { request =>
      val params = paramsFactory(
        request.cl entContext,
        request.d splayLocat on,
        request.debugParams.flatMap(_.featureOverr des).getOrElse(Map.empty))
      productP pel neSelector.selectP pel ne(request, params).map(_.toThr ft)
    }
    St ch.run(st ch)
  }

  handle(ScoreUserCand dates) { args: ScoreUserCand dates.Args =>
    val st ch = scor ngUserRequestBu lder.fromThr ft(args.request).flatMap { request =>
      val params = paramsFactory(
        request.cl entContext,
        request.d splayLocat on,
        request.debugParams.flatMap(_.featureOverr des).getOrElse(Map.empty))
      userScor ngServ ce.get(request.copy(params = params)).map(_.toThr ft)
    }
    St ch.run(st ch)
  }
}
