package com.tw ter.un f ed_user_act ons.adapter.tls_favs_event

 mport com.tw ter.f nagle.stats.NullStatsRece ver
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f natra.kafka.serde.UnKeyed
 mport com.tw ter.t  l neserv ce.thr ftscala._
 mport com.tw ter.un f ed_user_act ons.adapter.AbstractAdapter
 mport com.tw ter.un f ed_user_act ons.adapter.common.AdapterUt ls
 mport com.tw ter.un f ed_user_act ons.thr ftscala._

class TlsFavsAdapter
    extends AbstractAdapter[Contextual zedFavor eEvent, UnKeyed, Un f edUserAct on] {

   mport TlsFavsAdapter._

  overr de def adaptOneToKeyedMany(
     nput: Contextual zedFavor eEvent,
    statsRece ver: StatsRece ver = NullStatsRece ver
  ): Seq[(UnKeyed, Un f edUserAct on)] =
    adaptEvent( nput).map { e => (UnKeyed, e) }
}

object TlsFavsAdapter {

  def adaptEvent(e: Contextual zedFavor eEvent): Seq[Un f edUserAct on] =
    Opt on(e).flatMap { e =>
      e.event match {
        case Favor eEventUn on.Favor e(favor eEvent) =>
          So (
            Un f edUserAct on(
              user dent f er = getUser dent f er(Left(favor eEvent)),
               em = getFav em(favor eEvent),
              act onType = Act onType.ServerT etFav,
              event tadata = getEvent tadata(Left(favor eEvent), e.context),
              productSurface = None,
              productSurface nfo = None
            ))

        case Favor eEventUn on.Unfavor e(unfavor eEvent) =>
          So (
            Un f edUserAct on(
              user dent f er = getUser dent f er(R ght(unfavor eEvent)),
               em = getUnfav em(unfavor eEvent),
              act onType = Act onType.ServerT etUnfav,
              event tadata = getEvent tadata(R ght(unfavor eEvent), e.context),
              productSurface = None,
              productSurface nfo = None
            ))

        case _ => None
      }
    }.toSeq

  def getFav em(favor eEvent: Favor eEvent):  em =
     em.T et nfo(
      T et nfo(
        act onT et d = favor eEvent.t et d,
        act onT etAuthor nfo = So (Author nfo(author d = So (favor eEvent.t etUser d))),
        ret et ngT et d = favor eEvent.ret et d
      )
    )

  def getUnfav em(unfavor eEvent: Unfavor eEvent):  em =
     em.T et nfo(
      T et nfo(
        act onT et d = unfavor eEvent.t et d,
        act onT etAuthor nfo = So (Author nfo(author d = So (unfavor eEvent.t etUser d))),
        ret et ngT et d = unfavor eEvent.ret et d
      )
    )

  def getEvent tadata(
    event: E  r[Favor eEvent, Unfavor eEvent],
    context: LogEventContext
  ): Event tadata = {
    val s ceT  stampMs = event match {
      case Left(favor eEvent) => favor eEvent.eventT  Ms
      case R ght(unfavor eEvent) => unfavor eEvent.eventT  Ms
    }
    // Cl ent U  language, see more at http://go/languagepr or y. T  format should be  SO 639-1.
    val language = event match {
      case Left(favor eEvent) => favor eEvent.v e rContext.flatMap(_.requestLanguageCode)
      case R ght(unfavor eEvent) => unfavor eEvent.v e rContext.flatMap(_.requestLanguageCode)
    }
    // From t  request (user’s current locat on),
    // see https://s cegraph.tw ter.b z/g .tw ter.b z/s ce/-/blob/src/thr ft/com/tw ter/context/v e r.thr ft?L54
    // T  format should be  SO_3166-1_alpha-2.
    val countryCode = event match {
      case Left(favor eEvent) => favor eEvent.v e rContext.flatMap(_.requestCountryCode)
      case R ght(unfavor eEvent) => unfavor eEvent.v e rContext.flatMap(_.requestCountryCode)
    }
    Event tadata(
      s ceT  stampMs = s ceT  stampMs,
      rece vedT  stampMs = AdapterUt ls.currentT  stampMs,
      s ceL neage = S ceL neage.ServerTlsFavs,
      language = language.map(AdapterUt ls.normal zeLanguageCode),
      countryCode = countryCode.map(AdapterUt ls.normal zeCountryCode),
      trace d = So (context.trace d),
      cl entApp d = context.cl entAppl cat on d,
    )
  }

  // Get  d of t  user that took t  act on
  def getUser dent f er(event: E  r[Favor eEvent, Unfavor eEvent]): User dent f er =
    event match {
      case Left(favor eEvent) => User dent f er(user d = So (favor eEvent.user d))
      case R ght(unfavor eEvent) => User dent f er(user d = So (unfavor eEvent.user d))
    }
}
