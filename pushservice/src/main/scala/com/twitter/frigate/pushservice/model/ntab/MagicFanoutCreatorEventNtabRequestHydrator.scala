package com.tw ter.fr gate.pushserv ce.model.ntab

 mport com.tw ter.fr gate.mag c_events.thr ftscala.CreatorFanoutType
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.model.Mag cFanoutCreatorEventPushCand date
 mport com.tw ter.fr gate.pushserv ce.take.Not f cat onServ ceSender
 mport com.tw ter.not f cat onserv ce.thr ftscala.CreateGener cNot f cat onRequest
 mport com.tw ter.not f cat onserv ce.thr ftscala.D splayText
 mport com.tw ter.not f cat onserv ce.thr ftscala.D splayTextEnt y
 mport com.tw ter.not f cat onserv ce.thr ftscala.Gener cType
 mport com.tw ter.not f cat onserv ce.thr ftscala. nl neCard
 mport com.tw ter.not f cat onserv ce.thr ftscala.StoryContext
 mport com.tw ter.not f cat onserv ce.thr ftscala.TextValue
 mport com.tw ter.not f cat onserv ce.thr ftscala.TapThroughAct on
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.T  

tra  Mag cFanoutCreatorEventNtabRequestHydrator extends NTabRequestHydrator {
  self: PushCand date w h Mag cFanoutCreatorEventPushCand date =>

  overr de val sender dFut: Future[Long] = Future.value(creator d)

  overr de lazy val tapThroughFut: Future[Str ng] =
    Future.value(s"/${userProf le.screenNa }/superfollows/subscr be")

  lazy val opt onalT etCountEnt yFut: Future[Opt on[D splayTextEnt y]] = {
    creatorFanoutType match {
      case CreatorFanoutType.UserSubscr pt on =>
        numberOfT etsFut.map {
          _.flatMap {
            case numberOfT ets  f numberOfT ets >= 10 =>
              So (
                D splayTextEnt y(
                  na  = "t et_count",
                  emphas s = true,
                  value = TextValue.Text(numberOfT ets.toStr ng)))
            case _ => None
          }
        }
      case _ => Future.None
    }
  }

  overr de lazy val d splayTextEnt  esFut: Future[Seq[D splayTextEnt y]] =
    opt onalT etCountEnt yFut
      .map { t etCountOpt =>
        Seq(
          Not f cat onServ ceSender
            .getD splayTextEnt yFromUser(hydratedCreator, "d splay_na ",  sBold = true),
          t etCountOpt).flatten
      }

  overr de lazy val facep leUsersFut: Future[Seq[Long]] = Future.value(Seq(creator d))

  overr de val storyContext: Opt on[StoryContext] = None

  overr de val  nl neCard: Opt on[ nl neCard] = None

  lazy val refreshableTypeFut = {
    creatorFanoutType match {
      case CreatorFanoutType.UserSubscr pt on =>
        numberOfT etsFut.map {
          _.flatMap {
            case numberOfT ets  f numberOfT ets >= 10 =>
              So ("Mag cFanoutCreatorSubscr pt onW hT ets")
            case _ => super.refreshableType
          }
        }
      case _ => Future.value(super.refreshableType)
    }
  }

  overr de lazy val soc alProofD splayText: Opt on[D splayText] = {
    creatorFanoutType match {
      case CreatorFanoutType.UserSubscr pt on =>
        So (
          D splayText(values = Seq(
            D splayTextEnt y(na  = "handle", value = TextValue.Text(userProf le.screenNa )))))
      case CreatorFanoutType.NewCreator => None
      case _ => None
    }
  }

  overr de lazy val ntabRequest = {
    Future
      .jo n(
        sender dFut,
        d splayTextEnt  esFut,
        facep leUsersFut,
        tapThroughFut,
        refreshableTypeFut).map {
        case (sender d, d splayTextEnt  es, facep leUsers, tapThrough, refreshableTypeOpt) =>
          So (
            CreateGener cNot f cat onRequest(
              user d = target.target d,
              sender d = sender d,
              gener cType = Gener cType.RefreshableNot f cat on,
              d splayText = D splayText(values = d splayTextEnt  es),
              facep leUsers = facep leUsers,
              t  stampM ll s = T  .now. nM ll s,
              tapThroughAct on = So (TapThroughAct on(So (tapThrough))),
               mpress on d = So ( mpress on d),
              soc alProofText = soc alProofD splayText,
              context = storyContext,
               nl neCard =  nl neCard,
              refreshableType = refreshableTypeOpt
            ))
      }
  }
}
