package com.tw ter.un f ed_user_act ons.adapter.t etyp e_event

 mport com.tw ter.t etyp e.thr ftscala.QuotedT et
 mport com.tw ter.t etyp e.thr ftscala.Share
 mport com.tw ter.t etyp e.thr ftscala.T etDeleteEvent
 mport com.tw ter.t etyp e.thr ftscala.T etEventFlags
 mport com.tw ter.un f ed_user_act ons.adapter.common.AdapterUt ls
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Act onType
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Author nfo
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Event tadata
 mport com.tw ter.un f ed_user_act ons.thr ftscala. em
 mport com.tw ter.un f ed_user_act ons.thr ftscala.S ceL neage
 mport com.tw ter.un f ed_user_act ons.thr ftscala.T et nfo
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Un f edUserAct on
 mport com.tw ter.un f ed_user_act ons.thr ftscala.User dent f er

tra  BaseT etyp eT etEventDelete extends BaseT etyp eT etEvent[T etDeleteEvent] {
  type ExtractedEvent
  protected def act onType: Act onType

  def getUn f edUserAct on(
    t etDeleteEvent: T etDeleteEvent,
    t etEventFlags: T etEventFlags
  ): Opt on[Un f edUserAct on] =
    extract(t etDeleteEvent).map { extractedEvent =>
      Un f edUserAct on(
        user dent f er = getUser dent f er(t etDeleteEvent),
         em = get em(extractedEvent, t etDeleteEvent),
        act onType = act onType,
        event tadata = getEvent tadata(t etDeleteEvent, t etEventFlags)
      )
    }

  protected def extract(t etDeleteEvent: T etDeleteEvent): Opt on[ExtractedEvent]

  protected def get em(extractedEvent: ExtractedEvent, t etDeleteEvent: T etDeleteEvent):  em

  protected def getUser dent f er(t etDeleteEvent: T etDeleteEvent): User dent f er =
    User dent f er(user d = t etDeleteEvent.user.map(_. d))

  protected def getEvent tadata(
    t etDeleteEvent: T etDeleteEvent,
    flags: T etEventFlags
  ): Event tadata =
    Event tadata(
      s ceT  stampMs = flags.t  stampMs,
      rece vedT  stampMs = AdapterUt ls.currentT  stampMs,
      s ceL neage = S ceL neage.ServerT etyp eEvents,
      trace d = None, // Currently trace d  s not stored  n T etDeleteEvent.
      // UUA sets t  to None s nce t re  s no request level language  nfo.
      language = None,
      // UUA sets t  to be cons stent w h  ES ce. For t  def n  on,
      //  see https://s cegraph.tw ter.b z/g .tw ter.b z/s ce/-/blob/src/thr ft/com/tw ter/t etyp e/t et.thr ft?L1001.
      //  T  def n  on  re confl cts w h t   ntent on of UUA to log t  request country code
      //  rat r than t  s gnup / geo-tagg ng country.
      countryCode = t etDeleteEvent.t et.place.flatMap(_.countryCode),
      /* cl entAppl cat on d  s user's app  d  f t  delete  s  n  ated by a user,
       * or aud or's app  d  f t  delete  s  n  ated by an aud or */
      cl entApp d = t etDeleteEvent.aud .flatMap(_.cl entAppl cat on d),
      cl entVers on = None // Currently cl entVers on  s not stored  n T etDeleteEvent.
    )
}

object T etyp eDeleteEvent extends BaseT etyp eT etEventDelete {
  type ExtractedEvent = Long
  overr de protected val act onType: Act onType = Act onType.ServerT etDelete

  overr de protected def extract(t etDeleteEvent: T etDeleteEvent): Opt on[Long] = So (
    t etDeleteEvent.t et. d)

  protected def get em(
    t et d: Long,
    t etDeleteEvent: T etDeleteEvent
  ):  em =
     em.T et nfo(
      T et nfo(
        act onT et d = t et d,
        act onT etAuthor nfo =
          So (Author nfo(author d = t etDeleteEvent.t et.coreData.map(_.user d)))
      ))
}

object T etyp eUnret etEvent extends BaseT etyp eT etEventDelete {
  overr de protected val act onType: Act onType = Act onType.ServerT etUnret et

  overr de type ExtractedEvent = Share

  overr de protected def extract(t etDeleteEvent: T etDeleteEvent): Opt on[Share] =
    t etDeleteEvent.t et.coreData.flatMap(_.share)

  overr de protected def get em(share: Share, t etDeleteEvent: T etDeleteEvent):  em =
     em.T et nfo(
      T et nfo(
        act onT et d = share.s ceStatus d,
        act onT etAuthor nfo = So (Author nfo(author d = So (share.s ceUser d))),
        ret et ngT et d = So (t etDeleteEvent.t et. d)
      )
    )
}

object T etyp eUnreplyEvent extends BaseT etyp eT etEventDelete {
  case class Pred cateOutput(t et d: Long, user d: Long)

  overr de type ExtractedEvent = Pred cateOutput

  overr de protected val act onType: Act onType = Act onType.ServerT etUnreply

  overr de protected def extract(t etDeleteEvent: T etDeleteEvent): Opt on[Pred cateOutput] =
    t etDeleteEvent.t et.coreData
      .flatMap(_.reply).flatMap(r =>
        r. nReplyToStatus d.map(t et d => Pred cateOutput(t et d, r. nReplyToUser d)))

  overr de protected def get em(
    repl edT et: Pred cateOutput,
    t etDeleteEvent: T etDeleteEvent
  ):  em = {
     em.T et nfo(
      T et nfo(
        act onT et d = repl edT et.t et d,
        act onT etAuthor nfo = So (Author nfo(author d = So (repl edT et.user d))),
        reply ngT et d = So (t etDeleteEvent.t et. d)
      )
    )
  }
}

object T etyp eUnquoteEvent extends BaseT etyp eT etEventDelete {
  overr de protected val act onType: Act onType = Act onType.ServerT etUnquote

  type ExtractedEvent = QuotedT et

  overr de protected def extract(t etDeleteEvent: T etDeleteEvent): Opt on[QuotedT et] =
    t etDeleteEvent.t et.quotedT et

  overr de protected def get em(
    quotedT et: QuotedT et,
    t etDeleteEvent: T etDeleteEvent
  ):  em =
     em.T et nfo(
      T et nfo(
        act onT et d = quotedT et.t et d,
        act onT etAuthor nfo = So (Author nfo(author d = So (quotedT et.user d))),
        quot ngT et d = So (t etDeleteEvent.t et. d)
      )
    )
}
