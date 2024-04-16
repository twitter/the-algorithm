package com.tw ter.un f ed_user_act ons.adapter.t etyp e_event

 mport com.tw ter.t etyp e.thr ftscala.QuotedT et
 mport com.tw ter.t etyp e.thr ftscala.Share
 mport com.tw ter.t etyp e.thr ftscala.T etCreateEvent
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

/**
 * Base class for T etyp e T etCreateEvent  nclud ng Quote, Reply, Ret et, and Create.
 */
tra  BaseT etyp eT etEventCreate extends BaseT etyp eT etEvent[T etCreateEvent] {
  type ExtractedEvent
  protected def act onType: Act onType

  /**
   *  T   s t  country code w re act onT et d  s sent from. For t  def n  ons,
   *  c ck https://s cegraph.tw ter.b z/g .tw ter.b z/s ce/-/blob/src/thr ft/com/tw ter/t etyp e/t et.thr ft?L1001.
   *
   *  UUA sets t  to be cons stent w h  ES ce to  et ex st ng use requ re nt.
   *
   *  For ServerT etReply/Ret et/Quote, t  geo-tagg ng country code  s not ava lable  n T etCreatEvent.
   *  Thus, user s gnup country  s p cked to  et a custo r use case.
   *
   *  T  def n  on  re confl cts w h t   ntent on of UUA to log t  request country code
   *  rat r than t  s gnup / geo-tagg ng country.
   *
   */
  protected def getCountryCode(tce: T etCreateEvent): Opt on[Str ng] = {
    tce.t et.place match {
      case So (p) => p.countryCode
      case _ => tce.user.safety.flatMap(_.s gnupCountryCode)
    }
  }

  protected def get em(
    extractedEvent: ExtractedEvent,
    t etCreateEvent: T etCreateEvent
  ):  em
  protected def extract(t etCreateEvent: T etCreateEvent): Opt on[ExtractedEvent]

  def getUn f edUserAct on(
    t etCreateEvent: T etCreateEvent,
    t etEventFlags: T etEventFlags
  ): Opt on[Un f edUserAct on] = {
    extract(t etCreateEvent).map { extractedEvent =>
      Un f edUserAct on(
        user dent f er = getUser dent f er(t etCreateEvent),
         em = get em(extractedEvent, t etCreateEvent),
        act onType = act onType,
        event tadata = getEvent tadata(t etCreateEvent, t etEventFlags),
        productSurface = None,
        productSurface nfo = None
      )
    }
  }

  protected def getUser dent f er(t etCreateEvent: T etCreateEvent): User dent f er =
    User dent f er(user d = So (t etCreateEvent.user. d))

  protected def getEvent tadata(
    t etCreateEvent: T etCreateEvent,
    flags: T etEventFlags
  ): Event tadata =
    Event tadata(
      s ceT  stampMs = flags.t  stampMs,
      rece vedT  stampMs = AdapterUt ls.currentT  stampMs,
      s ceL neage = S ceL neage.ServerT etyp eEvents,
      trace d = None, // Currently trace d  s not stored  n T etCreateEvent
      // UUA sets t  to None s nce t re  s no request level language  nfo.
      language = None,
      countryCode = getCountryCode(t etCreateEvent),
      cl entApp d = t etCreateEvent.t et.dev ceS ce.flatMap(_.cl entApp d),
      cl entVers on = None // Currently cl entVers on  s not stored  n T etCreateEvent
    )
}

/**
 * Get Un f edUserAct on from a t et Create.
 * Note t  Create  s generated w n t  t et  s not a Quote/Ret et/Reply.
 */
object T etyp eCreateEvent extends BaseT etyp eT etEventCreate {
  type ExtractedEvent = Long
  overr de protected val act onType: Act onType = Act onType.ServerT etCreate
  overr de protected def extract(t etCreateEvent: T etCreateEvent): Opt on[Long] =
    Opt on(t etCreateEvent.t et. d)

  protected def get em(
    t et d: Long,
    t etCreateEvent: T etCreateEvent
  ):  em =
     em.T et nfo(
      T et nfo(
        act onT et d = t et d,
        act onT etAuthor nfo = So (Author nfo(author d = So (t etCreateEvent.user. d)))
      ))
}

/**
 * Get Un f edUserAct on from a Reply.
 * Note t  Reply  s generated w n so one  s reply ng to a t et.
 */
object T etyp eReplyEvent extends BaseT etyp eT etEventCreate {
  case class Pred cateOutput(t et d: Long, user d: Long)
  overr de type ExtractedEvent = Pred cateOutput
  overr de protected val act onType: Act onType = Act onType.ServerT etReply
  overr de protected def extract(t etCreateEvent: T etCreateEvent): Opt on[Pred cateOutput] =
    t etCreateEvent.t et.coreData
      .flatMap(_.reply).flatMap(r =>
        r. nReplyToStatus d.map(t et d => Pred cateOutput(t et d, r. nReplyToUser d)))

  overr de protected def get em(
    repl edT et: Pred cateOutput,
    t etCreateEvent: T etCreateEvent
  ):  em = {
     em.T et nfo(
      T et nfo(
        act onT et d = repl edT et.t et d,
        act onT etAuthor nfo = So (Author nfo(author d = So (repl edT et.user d))),
        reply ngT et d = So (t etCreateEvent.t et. d)
      )
    )
  }
}

/**
 * Get Un f edUserAct on from a Quote.
 * Note t  Quote  s generated w n so one  s quot ng (ret et ng w h com nt) a t et.
 */
object T etyp eQuoteEvent extends BaseT etyp eT etEventCreate {
  overr de protected val act onType: Act onType = Act onType.ServerT etQuote
  type ExtractedEvent = QuotedT et
  overr de protected def extract(t etCreateEvent: T etCreateEvent): Opt on[QuotedT et] =
    t etCreateEvent.t et.quotedT et

  overr de protected def get em(
    quotedT et: QuotedT et,
    t etCreateEvent: T etCreateEvent
  ):  em =
     em.T et nfo(
      T et nfo(
        act onT et d = quotedT et.t et d,
        act onT etAuthor nfo = So (Author nfo(author d = So (quotedT et.user d))),
        quot ngT et d = So (t etCreateEvent.t et. d)
      )
    )
}

/**
 * Get Un f edUserAct on from a Ret et.
 * Note t  Ret et  s generated w n so one  s ret et ng (w hout com nt) a t et.
 */
object T etyp eRet etEvent extends BaseT etyp eT etEventCreate {
  overr de type ExtractedEvent = Share
  overr de protected val act onType: Act onType = Act onType.ServerT etRet et
  overr de protected def extract(t etCreateEvent: T etCreateEvent): Opt on[Share] =
    t etCreateEvent.t et.coreData.flatMap(_.share)

  overr de protected def get em(share: Share, t etCreateEvent: T etCreateEvent):  em =
     em.T et nfo(
      T et nfo(
        act onT et d = share.s ceStatus d,
        act onT etAuthor nfo = So (Author nfo(author d = So (share.s ceUser d))),
        ret et ngT et d = So (t etCreateEvent.t et. d)
      )
    )
}

/**
 * Get Un f edUserAct on from a T etEd .
 * Note t  Ed   s generated w n so one  s ed  ng t  r quote or default t et. T  ed  w ll
 * generate a new T et.
 */
object T etyp eEd Event extends BaseT etyp eT etEventCreate {
  overr de type ExtractedEvent = Long
  overr de protected def act onType: Act onType = Act onType.ServerT etEd 
  overr de protected def extract(t etCreateEvent: T etCreateEvent): Opt on[Long] =
    T etyp eEventUt ls.ed edT et dFromT et(t etCreateEvent.t et)

  overr de protected def get em(
    ed edT et d: Long,
    t etCreateEvent: T etCreateEvent
  ):  em =
     em.T et nfo(
      T et nfo(
        act onT et d = t etCreateEvent.t et. d,
        act onT etAuthor nfo = So (Author nfo(author d = So (t etCreateEvent.user. d))),
        ed edT et d = So (ed edT et d),
        quotedT et d = t etCreateEvent.t et.quotedT et.map(_.t et d)
      )
    )
}
