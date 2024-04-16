package com.tw ter.un f ed_user_act ons.adapter.cl ent_event

 mport com.tw ter.cl entapp.thr ftscala.EventNa space
 mport com.tw ter.cl entapp.thr ftscala. em
 mport com.tw ter.cl entapp.thr ftscala. emType.User
 mport com.tw ter.cl entapp.thr ftscala.LogEvent
 mport com.tw ter.cl entapp.thr ftscala.{ em => LogEvent em}
 mport com.tw ter.un f ed_user_act ons.adapter.common.AdapterUt ls
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Author nfo
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Cl entEventNa space
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Event tadata
 mport com.tw ter.un f ed_user_act ons.thr ftscala.ProductSurface
 mport com.tw ter.un f ed_user_act ons.thr ftscala.S ceL neage
 mport com.tw ter.un f ed_user_act ons.thr ftscala.T etAuthorFollowCl ckS ce
 mport com.tw ter.un f ed_user_act ons.thr ftscala.T etAuthorUnfollowCl ckS ce
 mport com.tw ter.un f ed_user_act ons.thr ftscala.T et nfo

/**
 * Compr ses  lper  thods that:
 * 1. need not be overr dden by subclasses of `BaseCl entEvent`
 * 2. need not be  nvoked by  nstances of subclasses of `BaseCl entEvent`
 * 3. need to be access ble to subclasses of `BaseCl entEvent` and ot r ut ls
 */
object Cl entEventCommonUt ls {

  def getBas cT et nfo(
    act onT et d: Long,
    ce em: LogEvent em,
    ceNa spaceOpt: Opt on[EventNa space]
  ): T et nfo = T et nfo(
    act onT et d = act onT et d,
    act onT etTop cSoc alProof d = getTop c d(ce em, ceNa spaceOpt),
    ret et ngT et d = ce em.t etDeta ls.flatMap(_.ret et ngT et d),
    quotedT et d = ce em.t etDeta ls.flatMap(_.quotedT et d),
     nReplyToT et d = ce em.t etDeta ls.flatMap(_. nReplyToT et d),
    quot ngT et d = ce em.t etDeta ls.flatMap(_.quot ngT et d),
    // only set Author nfo w n author d  s present
    act onT etAuthor nfo = getAuthor nfo(ce em),
    ret et ngAuthor d = ce em.t etDeta ls.flatMap(_.ret etAuthor d),
    quotedAuthor d = ce em.t etDeta ls.flatMap(_.quotedAuthor d),
     nReplyToAuthor d = ce em.t etDeta ls.flatMap(_. nReplyToAuthor d),
    t etPos  on = ce em.pos  on,
    promoted d = ce em.promoted d
  )

  def getTop c d(
    ce em: LogEvent em,
    ceNa spaceOpt: Opt on[EventNa space] = None,
  ): Opt on[Long] =
    ceNa spaceOpt.flatMap {
      Top c dUt ls.getTop c d( em = ce em, _)
    }

  def getAuthor nfo(
    ce em: LogEvent em,
  ): Opt on[Author nfo] =
    ce em.t etDeta ls.flatMap(_.author d).map { author d =>
      Author nfo(
        author d = So (author d),
         sFollo dByAct ngUser = ce em. sV e rFollowsT etAuthor,
         sFollow ngAct ngUser = ce em. sT etAuthorFollowsV e r,
      )
    }

  def getEvent tadata(
    eventT  stamp: Long,
    logEvent: LogEvent,
    ce em: LogEvent em,
    productSurface: Opt on[ProductSurface] = None
  ): Event tadata = Event tadata(
    s ceT  stampMs = eventT  stamp,
    rece vedT  stampMs = AdapterUt ls.currentT  stampMs,
    s ceL neage = S ceL neage.Cl entEvents,
    // Cl ent U  language or from G zmoduck wh ch  s what user set  n Tw ter App.
    // Please see more at https://s cegraph.tw ter.b z/g .tw ter.b z/s ce/-/blob/f natra- nternal/ nternat onal/src/ma n/scala/com/tw ter/f natra/ nternat onal/Language dent f er.scala
    // T  format should be  SO 639-1.
    language = logEvent.logBase.flatMap(_.language).map(AdapterUt ls.normal zeLanguageCode),
    // Country code could be  P address (geoduck) or User reg strat on country (g zmoduck) and t  for r takes precedence.
    //   don’t know exactly wh ch one  s appl ed, unfortunately,
    // see https://s cegraph.tw ter.b z/g .tw ter.b z/s ce/-/blob/f natra- nternal/ nternat onal/src/ma n/scala/com/tw ter/f natra/ nternat onal/Country dent f er.scala
    // T  format should be  SO_3166-1_alpha-2.
    countryCode = logEvent.logBase.flatMap(_.country).map(AdapterUt ls.normal zeCountryCode),
    cl entApp d = logEvent.logBase.flatMap(_.cl entApp d),
    cl entVers on = logEvent.cl entVers on,
    cl entEventNa space = logEvent.eventNa space.map(en => toCl entEventNa space(en)),
    trace d = getTrace d(productSurface, ce em),
    requestJo n d = getRequestJo n d(productSurface, ce em),
    cl entEventTr ggeredOn = logEvent.eventDeta ls.flatMap(_.tr ggeredOn)
  )

  def toCl entEventNa space(eventNa space: EventNa space): Cl entEventNa space =
    Cl entEventNa space(
      page = eventNa space.page,
      sect on = eventNa space.sect on,
      component = eventNa space.component,
      ele nt = eventNa space.ele nt,
      act on = eventNa space.act on
    )

  /**
   * Get t  prof le d from  em. d, wh ch  emType = 'USER'.
   *
   * T  prof le d can be also be found  n t  event_deta ls.prof le_ d.
   * Ho ver, t   em. d  s more rel able than event_deta ls.prof le_ d,
   *  n part cular, 45% of t  cl ent events w h USER  ems have
   * Null for event_deta ls.prof le_ d wh le 0.13%  em. d  s Null.
   * As such,   only use  em. d to populate t  prof le_ d.
   */
  def getProf le dFromUser em( em:  em): Opt on[Long] =
     f ( em. emType.conta ns(User))
       em. d
    else None

  /**
   * Trace d  s go ng to be deprecated and replaced by requestJo n d.
   *
   * Get t  trace d from LogEvent em based on productSurface.
   *
   * T  trace d  s hydrated  n controller data from backend. D fferent product surfaces
   * populate d fferent controller data. Thus, t  product surface  s c cked f rst to dec de
   * wh ch controller data should be read to ge t  requestJo n d.
   */
  def getTrace d(productSurface: Opt on[ProductSurface], ce em: LogEvent em): Opt on[Long] =
    productSurface match {
      case So (ProductSurface.Ho T  l ne) => Ho  nfoUt ls.getTrace d(ce em)
      case So (ProductSurface.SearchResultsPage) => { new Search nfoUt ls(ce em) }.getTrace d
      case _ => None
    }

  /**
   * Get t  requestJo n d from LogEvent em based on productSurface.
   *
   * T  requestJo n d  s hydrated  n controller data from backend. D fferent product surfaces
   * populate d fferent controller data. Thus, t  product surface  s c cked f rst to dec de
   * wh ch controller data should be read to get t  requestJo n d.
   *
   * Support Ho  / Ho _latest / SearchResults for now, to add ot r surfaces based on requ re nt.
   */
  def getRequestJo n d(productSurface: Opt on[ProductSurface], ce em: LogEvent em): Opt on[Long] =
    productSurface match {
      case So (ProductSurface.Ho T  l ne) => Ho  nfoUt ls.getRequestJo n d(ce em)
      case So (ProductSurface.SearchResultsPage) => {
          new Search nfoUt ls(ce em)
        }.getRequestJo n d
      case _ => None
    }

  def getT etAuthorFollowS ce(
    eventNa space: Opt on[EventNa space]
  ): T etAuthorFollowCl ckS ce = {
    eventNa space
      .map(ns => (ns.ele nt, ns.act on)).map {
        case (So ("follow"), So ("cl ck")) => T etAuthorFollowCl ckS ce.Caret nu
        case (_, So ("follow")) => T etAuthorFollowCl ckS ce.Prof le mage
        case _ => T etAuthorFollowCl ckS ce.Unknown
      }.getOrElse(T etAuthorFollowCl ckS ce.Unknown)
  }

  def getT etAuthorUnfollowS ce(
    eventNa space: Opt on[EventNa space]
  ): T etAuthorUnfollowCl ckS ce = {
    eventNa space
      .map(ns => (ns.ele nt, ns.act on)).map {
        case (So ("unfollow"), So ("cl ck")) => T etAuthorUnfollowCl ckS ce.Caret nu
        case (_, So ("unfollow")) => T etAuthorUnfollowCl ckS ce.Prof le mage
        case _ => T etAuthorUnfollowCl ckS ce.Unknown
      }.getOrElse(T etAuthorUnfollowCl ckS ce.Unknown)
  }
}
