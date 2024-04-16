package com.tw ter.fr gate.pushserv ce.send_handler.generator

 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.RawCand date
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.Target
 mport com.tw ter.fr gate.pushserv ce.conf g.Conf g
 mport com.tw ter.fr gate.pushserv ce.except on.UnsupportedCrtExcept on
 mport com.tw ter.fr gate.thr ftscala.Fr gateNot f cat on
 mport com.tw ter.fr gate.thr ftscala.{CommonRecom ndat onType => CRT}
 mport com.tw ter.ut l.Future

object PushRequestToCand date {
  f nal def generatePushCand date(
    fr gateNot f cat on: Fr gateNot f cat on,
    target: Target
  )(
     mpl c  conf g: Conf g
  ): Future[RawCand date] = {

    val cand dateGenerator: (Target, Fr gateNot f cat on) => Future[RawCand date] = {
      fr gateNot f cat on.commonRecom ndat onType match {
        case CRT.Mag cFanoutNewsEvent => Mag cFanoutNewsEventCand dateGenerator.getCand date
        case CRT.Sc duledSpaceSubscr ber => Sc duledSpaceSubscr berCand dateGenerator.getCand date
        case CRT.Sc duledSpaceSpeaker => Sc duledSpaceSpeakerCand dateGenerator.getCand date
        case CRT.Mag cFanoutSportsEvent =>
          Mag cFanoutSportsEventCand dateGenerator.getCand date(
            _,
            _,
            conf g.basketballGa ScoreStore,
            conf g.baseballGa ScoreStore,
            conf g.cr cketMatchScoreStore,
            conf g.soccerMatchScoreStore,
            conf g.nflGa ScoreStore,
            conf g.semant cCore gadataStore
          )
        case CRT.Mag cFanoutProductLaunch =>
          Mag cFanoutProductLaunchCand dateGenerator.getCand date
        case CRT.NewCreator =>
          Mag cFanoutCreatorEventCand dateGenerator.getCand date
        case CRT.CreatorSubscr ber =>
          Mag cFanoutCreatorEventCand dateGenerator.getCand date
        case _ =>
          throw new UnsupportedCrtExcept on(
            "UnsupportedCrtExcept on for SendHandler: " + fr gateNot f cat on.commonRecom ndat onType)
      }
    }

    cand dateGenerator(target, fr gateNot f cat on)
  }
}
