package com.tw ter.fr gate.pushserv ce.model.ntab

 mport com.tw ter.fr gate.common.base.T etAuthorDeta ls
 mport com.tw ter.fr gate.common.base.T etCand date
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.take.Not f cat onServ ceSender
 mport com.tw ter.not f cat onserv ce.thr ftscala.D splayTextEnt y
 mport com.tw ter.ut l.Future

tra  F1F rstDegreeT etNTabRequestHydrator extends T etNTabRequestHydrator {
  self: PushCand date w h T etCand date w h T etAuthorDeta ls =>

  overr de val d splayTextEnt  esFut: Future[Seq[D splayTextEnt y]] =
    Not f cat onServ ceSender.getD splayTextEnt yFromUser(t etAuthor, "author", true).map(_.toSeq)

  overr de lazy val facep leUsersFut: Future[Seq[Long]] = sender dFut.map(Seq(_))

}
