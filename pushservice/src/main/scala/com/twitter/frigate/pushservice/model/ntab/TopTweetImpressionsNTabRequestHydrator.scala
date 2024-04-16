package com.tw ter.fr gate.pushserv ce.model.ntab

 mport com.tw ter.fr gate.common.base.TopT et mpress onsCand date
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.not f cat onserv ce.thr ftscala.D splayText
 mport com.tw ter.not f cat onserv ce.thr ftscala.D splayTextEnt y
 mport com.tw ter.not f cat onserv ce.thr ftscala. nl neCard
 mport com.tw ter.not f cat onserv ce.thr ftscala.StoryContext
 mport com.tw ter.not f cat onserv ce.thr ftscala.StoryContextValue
 mport com.tw ter.not f cat onserv ce.thr ftscala.TextValue
 mport com.tw ter.ut l.Future

tra  TopT et mpress onsNTabRequestHydrator extends NTabRequestHydrator {
  self: PushCand date w h TopT et mpress onsCand date =>

  overr de lazy val tapThroughFut: Future[Str ng] =
    Future.value(s"${target.target d}/status/$t et d")

  overr de val sender dFut: Future[Long] = Future.value(0L)

  overr de val facep leUsersFut: Future[Seq[Long]] = Future.N l

  overr de val storyContext: Opt on[StoryContext] =
    So (StoryContext(altText = "", value = So (StoryContextValue.T ets(Seq(t et d)))))

  overr de val  nl neCard: Opt on[ nl neCard] = None

  overr de lazy val d splayTextEnt  esFut: Future[Seq[D splayTextEnt y]] = {
    Future.value(
      Seq(
        D splayTextEnt y(na  = "num_ mpress ons", value = TextValue.Number(self. mpress onsCount))
      )
    )
  }

  overr de def soc alProofD splayText: Opt on[D splayText] = None
}
