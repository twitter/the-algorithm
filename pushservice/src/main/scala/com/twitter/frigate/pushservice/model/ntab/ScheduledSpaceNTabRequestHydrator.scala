package com.tw ter.fr gate.pushserv ce.model.ntab

 mport com.tw ter.fr gate.common.base.SpaceCand date
 mport com.tw ter.fr gate.common.ut l.MrNtabCopyObjects
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.model.Sc duledSpaceSpeakerPushCand date
 mport com.tw ter.fr gate.pushserv ce.model.Sc duledSpaceSubscr berPushCand date
 mport com.tw ter.fr gate.pushserv ce.params.PushFeatureSw chParams
 mport com.tw ter.fr gate.pushserv ce.take.Not f cat onServ ceSender
 mport com.tw ter.fr gate.thr ftscala.SpaceNot f cat onType
 mport com.tw ter.g zmoduck.thr ftscala.User
 mport com.tw ter.not f cat onserv ce.thr ftscala._
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.T  

tra  Sc duledSpaceSpeakerNTabRequestHydrator extends Sc duledSpaceNTabRequestHydrator {
  self: PushCand date w h Sc duledSpaceSpeakerPushCand date =>

  overr de def refreshableType: Opt on[Str ng] = {
    fr gateNot f cat on.spaceNot f cat on.flatMap { spaceNot f cat on =>
      spaceNot f cat on.spaceNot f cat onType.flatMap {
        case SpaceNot f cat onType.PreSpaceBroadcast =>
          MrNtabCopyObjects.Sc duledSpaceSpeakerSoon.refreshableType
        case SpaceNot f cat onType.AtSpaceBroadcast =>
          MrNtabCopyObjects.Sc duledSpaceSpeakerNow.refreshableType
        case _ =>
          throw new  llegalStateExcept on(s"Unexpected SpaceNot f cat onType")
      }
    }
  }

  overr de lazy val facep leUsersFut: Future[Seq[Long]] = Future.N l

  overr de val soc alProofD splayText: Opt on[D splayText] = So (D splayText())
}

tra  Sc duledSpaceSubscr berNTabRequestHydrator extends Sc duledSpaceNTabRequestHydrator {
  self: PushCand date w h Sc duledSpaceSubscr berPushCand date =>

  overr de lazy val facep leUsersFut: Future[Seq[Long]] = {
    host d match {
      case So (spaceHost d) => Future.value(Seq(spaceHost d))
      case _ =>
        Future.except on(
          new  llegalStateExcept on(
            "Unable to get host  d for Sc duledSpaceSubscr berNTabRequestHydrator"))
    }
  }

  overr de val soc alProofD splayText: Opt on[D splayText] = None
}

tra  Sc duledSpaceNTabRequestHydrator extends NTabRequestHydrator {
  self: PushCand date w h SpaceCand date =>

  def hydratedHost: Opt on[User]

  overr de lazy val sender dFut: Future[Long] = {
    host d match {
      case So (spaceHost d) => Future.value(spaceHost d)
      case _ => throw new  llegalStateExcept on(s"No Space Host  d")
    }
  }

  overr de lazy val tapThroughFut: Future[Str ng] = Future.value(s" /spaces/$space d")

  overr de lazy val d splayTextEnt  esFut: Future[Seq[D splayTextEnt y]] =
    Not f cat onServ ceSender
      .getD splayTextEnt yFromUser(
        Future.value(hydratedHost),
        f eldNa  = "space_host_na ",
         sBold = true
      ).map(_.toSeq)

  overr de val storyContext: Opt on[StoryContext] = None

  overr de val  nl neCard: Opt on[ nl neCard] = None

  overr de lazy val ntabRequest: Future[Opt on[CreateGener cNot f cat onRequest]] = {
    Future.jo n(sender dFut, d splayTextEnt  esFut, facep leUsersFut, tapThroughFut).map {
      case (sender d, d splayTextEnt  es, facep leUsers, tapThrough) =>
        val exp ryT  M ll s =  f (target.params(PushFeatureSw chParams.EnableSpacesTtlForNtab)) {
          So (
            (T  .now + target.params(
              PushFeatureSw chParams.SpaceNot f cat onsTTLDurat onForNTab)). nM ll s)
        } else None

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
            refreshableType = refreshableType,
            exp ryT  M ll s = exp ryT  M ll s
          ))
    }
  }
}
