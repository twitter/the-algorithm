package com.tw ter.un f ed_user_act ons.adapter.cl ent_event

 mport com.tw ter.cl entapp.thr ftscala.EventNa space
 mport com.tw ter.cl entapp.thr ftscala.LogEvent
 mport com.tw ter.cl entapp.thr ftscala.{ em => LogEvent em}
 mport com.tw ter.suggests.controller_data.ho _t ets.thr ftscala.Ho T etsControllerDataAl ases.V1Al as
 mport com.tw ter.un f ed_user_act ons.thr ftscala._

object ProductSurfaceUt ls {

  def getProductSurface(eventNa space: Opt on[EventNa space]): Opt on[ProductSurface] = {
    (
      eventNa space.flatMap(_.page),
      eventNa space.flatMap(_.sect on),
      eventNa space.flatMap(_.ele nt)) match {
      case (So ("ho ") | So ("ho _latest"), _, _) => So (ProductSurface.Ho T  l ne)
      case (So ("ntab"), _, _) => So (ProductSurface.Not f cat onTab)
      case (So (page), So (sect on), _)  f  sPushNot f cat on(page, sect on) =>
        So (ProductSurface.PushNot f cat on)
      case (So ("search"), _, _) => So (ProductSurface.SearchResultsPage)
      case (_, _, So ("typea ad")) => So (ProductSurface.SearchTypea ad)
      case _ => None
    }
  }

  pr vate def  sPushNot f cat on(page: Str ng, sect on: Str ng): Boolean = {
    Seq[Str ng]("not f cat on", "toasts").conta ns(page) ||
    (page == "app" && sect on == "push")
  }

  def getProductSurface nfo(
    productSurface: Opt on[ProductSurface],
    ce em: LogEvent em,
    logEvent: LogEvent
  ): Opt on[ProductSurface nfo] = {
    productSurface match {
      case So (ProductSurface.Ho T  l ne) => createHo T  l ne nfo(ce em)
      case So (ProductSurface.Not f cat onTab) => createNot f cat onTab nfo(ce em)
      case So (ProductSurface.PushNot f cat on) => createPushNot f cat on nfo(logEvent)
      case So (ProductSurface.SearchResultsPage) => createSearchResultPage nfo(ce em, logEvent)
      case So (ProductSurface.SearchTypea ad) => createSearchTypea ad nfo(ce em, logEvent)
      case _ => None
    }
  }

  pr vate def createPushNot f cat on nfo(logEvent: LogEvent): Opt on[ProductSurface nfo] =
    Not f cat onCl entEventUt ls.getNot f cat on dForPushNot f cat on(logEvent) match {
      case So (not f cat on d) =>
        So (
          ProductSurface nfo.PushNot f cat on nfo(
            PushNot f cat on nfo(not f cat on d = not f cat on d)))
      case _ => None
    }

  pr vate def createNot f cat onTab nfo(ce em: LogEvent em): Opt on[ProductSurface nfo] =
    Not f cat onCl entEventUt ls.getNot f cat on dForNot f cat onTab(ce em) match {
      case So (not f cat on d) =>
        So (
          ProductSurface nfo.Not f cat onTab nfo(
            Not f cat onTab nfo(not f cat on d = not f cat on d)))
      case _ => None
    }

  pr vate def createHo T  l ne nfo(ce em: LogEvent em): Opt on[ProductSurface nfo] = {
    def suggestType: Opt on[Str ng] = Ho  nfoUt ls.getSuggestType(ce em)
    def controllerData: Opt on[V1Al as] = Ho  nfoUt ls.getHo T etControllerDataV1(ce em)

     f (suggestType. sDef ned || controllerData. sDef ned) {
      So (
        ProductSurface nfo.Ho T  l ne nfo(
          Ho T  l ne nfo(
            suggest onType = suggestType,
             njectedPos  on = controllerData.flatMap(_. njectedPos  on)
          )))
    } else None
  }

  pr vate def createSearchResultPage nfo(
    ce em: LogEvent em,
    logEvent: LogEvent
  ): Opt on[ProductSurface nfo] = {
    val search nfoUt l = new Search nfoUt ls(ce em)
    search nfoUt l.getQueryOptFrom em(logEvent).map { query =>
      ProductSurface nfo.SearchResultsPage nfo(
        SearchResultsPage nfo(
          query = query,
          queryS ce = search nfoUt l.getQueryS ceOptFromControllerDataFrom em,
           emPos  on = ce em.pos  on,
          t etResultS ces = search nfoUt l.getT etResultS ces,
          userResultS ces = search nfoUt l.getUserResultS ces,
          queryF lterType = search nfoUt l.getQueryF lterType(logEvent)
        ))
    }
  }

  pr vate def createSearchTypea ad nfo(
    ce em: LogEvent em,
    logEvent: LogEvent
  ): Opt on[ProductSurface nfo] = {
    logEvent.searchDeta ls.flatMap(_.query).map { query =>
      ProductSurface nfo.SearchTypea ad nfo(
        SearchTypea ad nfo(
          query = query,
           emPos  on = ce em.pos  on
        )
      )
    }
  }
}
