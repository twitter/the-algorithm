package com.tw ter.fr gate.pushserv ce.model.ntab

 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.not f cat onserv ce.thr ftscala.D splayText
 mport com.tw ter.not f cat onserv ce.thr ftscala. nl neCard
 mport com.tw ter.not f cat onserv ce.thr ftscala.StoryContext
 mport com.tw ter.ut l.Future

tra  EventNTabRequestHydrator extends NTabRequestHydrator {
  self: PushCand date =>

  overr de def sender dFut: Future[Long] = Future.value(0L)

  overr de def facep leUsersFut: Future[Seq[Long]] = Future.N l

  overr de val storyContext: Opt on[StoryContext] = None

  overr de val  nl neCard: Opt on[ nl neCard] = None

  overr de val soc alProofD splayText: Opt on[D splayText] = So (D splayText())
}
