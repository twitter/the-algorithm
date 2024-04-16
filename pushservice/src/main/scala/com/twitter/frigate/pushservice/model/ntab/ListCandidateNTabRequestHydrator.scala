package com.tw ter.fr gate.pushserv ce.model.ntab

 mport com.tw ter.fr gate.pushserv ce.model.L stRecom ndat onPushCand date
 mport com.tw ter.not f cat onserv ce.thr ftscala.D splayText
 mport com.tw ter.not f cat onserv ce.thr ftscala.D splayTextEnt y
 mport com.tw ter.not f cat onserv ce.thr ftscala. nl neCard
 mport com.tw ter.not f cat onserv ce.thr ftscala.StoryContext
 mport com.tw ter.not f cat onserv ce.thr ftscala.TextValue
 mport com.tw ter.ut l.Future

tra  L stCand dateNTabRequestHydrator extends NTabRequestHydrator {

  self: L stRecom ndat onPushCand date =>

  overr de lazy val sender dFut: Future[Long] =
    l stOwner d.map(_.getOrElse(0L))

  overr de lazy val facep leUsersFut: Future[Seq[Long]] = Future.N l

  overr de lazy val storyContext: Opt on[StoryContext] = None

  overr de lazy val  nl neCard: Opt on[ nl neCard] = None

  overr de lazy val tapThroughFut: Future[Str ng] = Future.value(s" /l sts/${l st d}")

  overr de lazy val d splayTextEnt  esFut: Future[Seq[D splayTextEnt y]] = l stNa .map {
    l stNa Opt =>
      l stNa Opt.toSeq.map { na  =>
        D splayTextEnt y(na  = "t le", value = TextValue.Text(na ))
      }
  }

  overr de val soc alProofD splayText: Opt on[D splayText] = So (D splayText())
}
