package com.tw ter.fr gate.pushserv ce.model.ntab

 mport com.tw ter.fr gate.pushserv ce.model.Subscr bedSearchT etPushCand date
 mport com.tw ter.fr gate.pushserv ce.take.Not f cat onServ ceSender
 mport com.tw ter.not f cat onserv ce.thr ftscala.D splayText
 mport com.tw ter.not f cat onserv ce.thr ftscala.D splayTextEnt y
 mport com.tw ter.not f cat onserv ce.thr ftscala.TextValue
 mport com.tw ter.ut l.Future

tra  Subscr bedSearchT etNtabRequestHydrator extends T etNTabRequestHydrator {
  self: Subscr bedSearchT etPushCand date =>
  overr de def d splayTextEnt  esFut: Future[Seq[D splayTextEnt y]] = Not f cat onServ ceSender
    .getD splayTextEnt yFromUser(t etAuthor, "t etAuthor",  sBold = true).map(_.toSeq)

  overr de def soc alProofD splayText: Opt on[D splayText] = {
    So (D splayText(values = Seq(D splayTextEnt y("search_query", TextValue.Text(searchTerm)))))
  }

  overr de lazy val facep leUsersFut: Future[Seq[Long]] = sender dFut.map(Seq(_))

  overr de lazy val tapThroughFut: Future[Str ng] =
    Future.value(self.ntabLand ngUrl)
}
