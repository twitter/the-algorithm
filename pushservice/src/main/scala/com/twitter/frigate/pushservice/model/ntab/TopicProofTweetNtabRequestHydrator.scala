package com.tw ter.fr gate.pushserv ce.model.ntab

 mport com.tw ter.fr gate.pushserv ce.model.Top cProofT etPushCand date
 mport com.tw ter.fr gate.pushserv ce.except on.T etNTabRequestHydratorExcept on
 mport com.tw ter.fr gate.pushserv ce.except on.UttEnt yNotFoundExcept on
 mport com.tw ter.fr gate.pushserv ce.take.Not f cat onServ ceSender
 mport com.tw ter.not f cat onserv ce.thr ftscala.D splayText
 mport com.tw ter.not f cat onserv ce.thr ftscala.D splayTextEnt y
 mport com.tw ter.not f cat onserv ce.thr ftscala.StoryContext
 mport com.tw ter.not f cat onserv ce.thr ftscala.StoryContextValue
 mport com.tw ter.not f cat onserv ce.thr ftscala.TextValue
 mport com.tw ter.ut l.Future

tra  Top cProofT etNtabRequestHydrator extends NTabRequestHydrator {
  self: Top cProofT etPushCand date =>

  overr de def d splayTextEnt  esFut: Future[Seq[D splayTextEnt y]] = Not f cat onServ ceSender
    .getD splayTextEnt yFromUser(t etAuthor, "t etAuthorNa ", true)
    .map(_.toSeq)

  pr vate lazy val uttEnt y = local zedUttEnt y.getOrElse(
    throw new UttEnt yNotFoundExcept on(
      s"${getClass.getS mpleNa } UttEnt y m ss ng for $t et d")
  )

  overr de lazy val tapThroughFut: Future[Str ng] = {
    t etAuthor.map {
      case So (author) =>
        val authorProf le = author.prof le.getOrElse(
          throw new T etNTabRequestHydratorExcept on(
            s"Unable to obta n author prof le for: ${author. d}"))
        s"${authorProf le.screenNa }/status/${t et d.toStr ng}"
      case _ =>
        throw new T etNTabRequestHydratorExcept on(
          s"Unable to obta n author and target deta ls to generate tap through for T et: $t et d")
    }
  }

  overr de lazy val soc alProofD splayText: Opt on[D splayText] = {
    So (
      D splayText(values =
        Seq(D splayTextEnt y("top c_na ", TextValue.Text(uttEnt y.local zedNa ForD splay))))
    )
  }

  overr de lazy val facep leUsersFut: Future[Seq[Long]] = sender dFut.map(Seq(_))

  overr de val  nl neCard = None

  overr de def storyContext: Opt on[StoryContext] = So (
    StoryContext("", So (StoryContextValue.T ets(Seq(t et d)))))

  overr de def sender dFut: Future[Long] =
    t etAuthor.map {
      case So (author) => author. d
      case _ =>
        throw new T etNTabRequestHydratorExcept on(
          s"Unable to obta n Author  D for: $commonRecType")
    }
}
