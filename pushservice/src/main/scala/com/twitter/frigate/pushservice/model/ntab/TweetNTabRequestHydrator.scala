package com.tw ter.fr gate.pushserv ce.model.ntab

 mport com.tw ter.fr gate.common.base.T etAuthorDeta ls
 mport com.tw ter.fr gate.common.base.T etCand date
 mport com.tw ter.fr gate.pushserv ce.except on.T etNTabRequestHydratorExcept on
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.params.PushFeatureSw chParams
 mport com.tw ter.not f cat onserv ce.thr ftscala. nl neCard
 mport com.tw ter.not f cat onserv ce.thr ftscala.StoryContext
 mport com.tw ter.not f cat onserv ce.thr ftscala.StoryContextValue
 mport com.tw ter.fr gate.pushserv ce.ut l.Ema lLand ngPageExper  ntUt l
 mport com.tw ter.not f cat onserv ce.thr ftscala._
 mport com.tw ter.ut l.Future

tra  T etNTabRequestHydrator extends NTabRequestHydrator {
  self: PushCand date w h T etCand date w h T etAuthorDeta ls =>

  overr de def sender dFut: Future[Long] =
    t etAuthor.map {
      case So (author) => author. d
      case _ =>
        throw new T etNTabRequestHydratorExcept on(
          s"Unable to obta n Author  D for: $commonRecType")
    }

  overr de def storyContext: Opt on[StoryContext] = So (
    StoryContext(
      altText = "",
      value = So (StoryContextValue.T ets(Seq(t et d))),
      deta ls = None
    ))

  overr de def  nl neCard: Opt on[ nl neCard] = So ( nl neCard.T etCard(T etCard(t et d)))

  overr de lazy val tapThroughFut: Future[Str ng] = {
    Future.jo n(t etAuthor, target.dev ce nfo).map {
      case (So (author), So (dev ce nfo)) =>
        val enableRuxLand ngPage = dev ce nfo. sRuxLand ngPageEl g ble && target.params(
          PushFeatureSw chParams.EnableNTabRuxLand ngPage)
        val authorProf le = author.prof le.getOrElse(
          throw new T etNTabRequestHydratorExcept on(
            s"Unable to obta n author prof le for: ${author. d}"))
         f (enableRuxLand ngPage) {
          Ema lLand ngPageExper  ntUt l.createNTabRuxLand ngUR (authorProf le.screenNa , t et d)
        } else {
          s"${authorProf le.screenNa }/status/${t et d.toStr ng}"
        }
      case _ =>
        throw new T etNTabRequestHydratorExcept on(
          s"Unable to obta n author and target deta ls to generate tap through for T et: $t et d")
    }
  }

  overr de def soc alProofD splayText: Opt on[D splayText] = None
}
