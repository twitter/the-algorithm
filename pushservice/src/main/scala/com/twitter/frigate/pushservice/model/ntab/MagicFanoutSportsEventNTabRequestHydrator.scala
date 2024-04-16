package com.tw ter.fr gate.pushserv ce.model.ntab

 mport com.tw ter.fr gate.common.base.Mag cFanoutSportsEventCand date
 mport com.tw ter.fr gate.common.base.Mag cFanoutSportsScore nformat on
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.model.Mag cFanoutEventHydratedCand date
 mport com.tw ter.fr gate.pushserv ce.params.{PushFeatureSw chParams => FS}
 mport com.tw ter.not f cat onserv ce.thr ftscala.CreateGener cNot f cat onRequest
 mport com.tw ter.not f cat onserv ce.thr ftscala.D splayText
 mport com.tw ter.not f cat onserv ce.thr ftscala.D splayTextEnt y
 mport com.tw ter.not f cat onserv ce.thr ftscala.Gener cType
 mport com.tw ter.not f cat onserv ce.thr ftscala.TextValue
 mport com.tw ter.not f cat onserv ce.thr ftscala.TapThroughAct on
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.T  

tra  Mag cFanoutSportsEventNTabRequestHydrator extends EventNTabRequestHydrator {
  self: PushCand date
    w h Mag cFanoutEventHydratedCand date
    w h Mag cFanoutSportsEventCand date
    w h Mag cFanoutSportsScore nformat on =>

  lazy val stats = self.statsRece ver.scope("Mag cFanoutSportsEventNtabHydrator")
  lazy val  nNetworkOnlyCounter = stats.counter(" n_network_only")
  lazy val faceP lesEnabledCounter = stats.counter("face_p les_enabled")
  lazy val faceP lesD sabledCounter = stats.counter("face_p les_d sabled")
  lazy val f lterPeopleWhoDontFollow Counter = stats.counter("pepole_who_dont_follow_ _counter")

  overr de lazy val tapThroughFut: Future[Str ng] = {
    Future.value(s" /events/$event d")
  }
  overr de lazy val d splayTextEnt  esFut: Future[Seq[D splayTextEnt y]] =
    eventT leFut.map { eventT le =>
      Seq(D splayTextEnt y(na  = "t le", value = TextValue.Text(eventT le)))
    }

  overr de lazy val facep leUsersFut: Future[Seq[Long]] =
     f (target.params(FS.EnableNTabFaceP leForSportsEventNot f cat ons)) {
      Future
        .jo n(
          target.not f cat onsFromOnlyPeople Follow,
          target.f lterNot f cat onsFromPeopleThatDontFollow ,
          awayTeam nfo,
          ho Team nfo).map {
          case ( nNetworkOnly, f lterPeopleWhoDontFollow , away, ho )
               f !( nNetworkOnly || f lterPeopleWhoDontFollow ) =>
            val awayTeam d = away.flatMap(_.tw terUser d)
            val ho Team d = ho .flatMap(_.tw terUser d)
            faceP lesEnabledCounter. ncr
            Seq(awayTeam d, ho Team d).flatten
          case ( nNetworkOnly, f lterPeopleWhoDontFollow , _, _) =>
            faceP lesD sabledCounter. ncr
             f ( nNetworkOnly)  nNetworkOnlyCounter. ncr
             f (f lterPeopleWhoDontFollow ) f lterPeopleWhoDontFollow Counter. ncr
            Seq.empty[Long]
        }
    } else Future.N l

  pr vate lazy val sportsNtabRequest: Future[Opt on[CreateGener cNot f cat onRequest]] = {
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
     f (target.params(FS.EnableNTabEntr esForSportsEventNot f cat ons)) {
      self.target. tory.flatMap { push tory =>
        val prevEvent toryEx sts = push tory.sorted tory.ex sts {
          case (_, not f cat on) =>
            not f cat on.mag cFanoutEventNot f cat on.ex sts(_.event d == self.event d)
        }
         f (prevEvent toryEx sts) {
          Future.None
        } else sportsNtabRequest
      }
    } else Future.None
  }
}
