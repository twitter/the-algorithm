package com.tw ter.fr gate.pushserv ce.model.ntab

 mport com.tw ter.fr gate.common.base.Soc alContextAct ons
 mport com.tw ter.fr gate.common.base.Soc alContextUserDeta ls
 mport com.tw ter.fr gate.common.base.T etAuthor
 mport com.tw ter.fr gate.common.base.T etAuthorDeta ls
 mport com.tw ter.fr gate.common.base.T etCand date
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.take.Not f cat onServ ceSender
 mport com.tw ter.not f cat onserv ce.thr ftscala.D splayTextEnt y
 mport com.tw ter.ut l.Future

tra  T etRet etNTabRequestHydrator extends T etNTabRequestHydrator w h NTabSoc alContext {
  self: PushCand date
    w h T etCand date
    w h T etAuthor
    w h T etAuthorDeta ls
    w h Soc alContextAct ons
    w h Soc alContextUserDeta ls =>

  overr de lazy val d splayTextEnt  esFut: Future[Seq[D splayTextEnt y]] = {
    Future
      .jo n(
        Not f cat onServ ceSender
          .getD splayTextEnt yFromUser(t etAuthor, "t etAuthorNa ",  sBold = false),
        Not f cat onServ ceSender
          .generateSoc alContextTextEnt  es(
            rankedNtabD splayNa sAnd ds(defaultToRecency = false),
            ot rCount)
      )
      .map {
        case (authorD splay, soc alContextD splay) =>
          soc alContextD splay ++ authorD splay
      }
  }

  overr de lazy val facep leUsersFut: Future[Seq[Long]] = Future.value(soc alContextUser ds)
}
