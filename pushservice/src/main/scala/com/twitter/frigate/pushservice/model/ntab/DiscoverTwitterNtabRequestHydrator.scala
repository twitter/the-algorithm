package com.tw ter.fr gate.pushserv ce.model.ntab

 mport com.tw ter.fr gate.common.rec_types.RecTypes
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.params.PushConstants
 mport com.tw ter.fr gate.thr ftscala.{CommonRecom ndat onType => CRT}
 mport com.tw ter.not f cat onserv ce.thr ftscala._
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.T  

tra  D scoverTw terNtabRequestHydrator extends NTabRequestHydrator {
  self: PushCand date =>

  overr de val sender dFut: Future[Long] = Future.value(0L)

  overr de val tapThroughFut: Future[Str ng] =
    commonRecType match {
      case CRT.AddressBookUploadPush => Future.value(PushConstants.AddressBookUploadTapThrough)
      case CRT. nterestP ckerPush => Future.value(PushConstants. nterestP ckerTapThrough)
      case CRT.CompleteOnboard ngPush =>
        Future.value(PushConstants.CompleteOnboard ng nterestAddressTapThrough)
      case _ =>
        Future.value(PushConstants.ConnectTabPushTapThrough)
    }

  overr de val d splayTextEnt  esFut: Future[Seq[D splayTextEnt y]] = Future.N l

  overr de val facep leUsersFut: Future[Seq[Long]] = Future.N l

  overr de val storyContext: Opt on[StoryContext] = None

  overr de val  nl neCard: Opt on[ nl neCard] = None

  overr de val soc alProofD splayText: Opt on[D splayText] = So (D splayText())

  overr de lazy val ntabRequest: Future[Opt on[CreateGener cNot f cat onRequest]] =
     f (self.commonRecType == CRT.ConnectTabPush || RecTypes. sOnboard ngFlowType(
        self.commonRecType)) {
      Future.jo n(sender dFut, d splayTextEnt  esFut, facep leUsersFut, tapThroughFut).map {
        case (sender d, d splayTextEnt  es, facep leUsers, tapThrough) =>
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
              refreshableType = refreshableType
            ))
      }
    } else Future.None
}
