package com.tw ter.fr gate.pushserv ce.model.ntab

 mport com.tw ter.fr gate.common.base.Mag cFanoutProductLaunchCand date
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.params.PushFeatureSw chParams
 mport com.tw ter.not f cat onserv ce.thr ftscala._
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.T  

tra  Mag cFanoutProductLaunchNtabRequestHydrator extends NTabRequestHydrator {
  self: PushCand date w h Mag cFanoutProductLaunchCand date =>

  overr de val sender dFut: Future[Long] = Future.value(0L)

  overr de lazy val tapThroughFut: Future[Str ng] = Future.value(getProductLaunchTapThrough())

  overr de lazy val d splayTextEnt  esFut: Future[Seq[D splayTextEnt y]] = {
    Future.value(
      fr gateNot f cat on.mag cFanoutProductLaunchNot f cat on
        .flatMap {
          _.product nfo.flatMap {
            _.body.map { body =>
              Seq(
                D splayTextEnt y(na  = "body", value = TextValue.Text(body)),
              )
            }
          }
        }.getOrElse(N l))
  }

  overr de lazy val facep leUsersFut: Future[Seq[Long]] = {
    Future.value(
      fr gateNot f cat on.mag cFanoutProductLaunchNot f cat on
        .flatMap {
          _.product nfo.flatMap {
            _.facep leUsers
          }
        }.getOrElse(N l))
  }

  overr de val storyContext: Opt on[StoryContext] = None

  overr de val  nl neCard: Opt on[ nl neCard] = None

  overr de lazy val soc alProofD splayText: Opt on[D splayText] = {
    fr gateNot f cat on.mag cFanoutProductLaunchNot f cat on.flatMap {
      _.product nfo.flatMap {
        _.t le.map { t le =>
          D splayText(values =
            Seq(D splayTextEnt y(na  = "soc al_context", value = TextValue.Text(t le))))
        }
      }
    }
  }

  lazy val defaultTapThrough = target.params(PushFeatureSw chParams.ProductLaunchTapThrough)

  pr vate def getProductLaunchTapThrough(): Str ng = {
    fr gateNot f cat on.mag cFanoutProductLaunchNot f cat on match {
      case So (productLaunchNot f) =>
        productLaunchNot f.product nfo match {
          case So (product nfo) => product nfo.tapThrough.getOrElse(defaultTapThrough)
          case _ => defaultTapThrough
        }
      case _ => defaultTapThrough
    }
  }

  pr vate lazy val productLaunchNtabRequest: Future[Opt on[CreateGener cNot f cat onRequest]] = {
    Future
      .jo n(sender dFut, d splayTextEnt  esFut, facep leUsersFut, tapThroughFut)
      .map {
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
  }

  overr de lazy val ntabRequest: Future[Opt on[CreateGener cNot f cat onRequest]] = {
     f (target.params(PushFeatureSw chParams.EnableNTabEntr esForProductLaunchNot f cat ons)) {
      productLaunchNtabRequest
    } else Future.None
  }
}
