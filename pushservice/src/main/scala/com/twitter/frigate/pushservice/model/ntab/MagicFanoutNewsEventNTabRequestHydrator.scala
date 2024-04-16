package com.tw ter.fr gate.pushserv ce.model.ntab

 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.model.Mag cFanoutEventHydratedCand date
 mport com.tw ter.not f cat onserv ce.thr ftscala.D splayTextEnt y
 mport com.tw ter.not f cat onserv ce.thr ftscala.TextValue
 mport com.tw ter.ut l.Future

tra  Mag cFanoutNewsEventNTabRequestHydrator extends EventNTabRequestHydrator {
  self: PushCand date w h Mag cFanoutEventHydratedCand date =>
  overr de lazy val tapThroughFut: Future[Str ng] = Future.value(s" /events/$event d")
  overr de lazy val d splayTextEnt  esFut: Future[Seq[D splayTextEnt y]] =
    eventT leFut.map { eventT le =>
      Seq(D splayTextEnt y(na  = "t le", value = TextValue.Text(eventT le)))
    }
}
