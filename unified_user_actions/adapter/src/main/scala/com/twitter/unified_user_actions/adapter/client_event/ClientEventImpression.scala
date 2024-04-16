package com.tw ter.un f ed_user_act ons.adapter.cl ent_event

 mport com.tw ter.cl entapp.thr ftscala.EventNa space
 mport com.tw ter.cl entapp.thr ftscala.LogEvent
 mport com.tw ter.cl entapp.thr ftscala.{ em => LogEvent em}
 mport com.tw ter.logbase.thr ftscala.LogBase
 mport com.tw ter.un f ed_user_act ons.thr ftscala._
 mport com.tw ter.un f ed_user_act ons.thr ftscala. em.T et nfo

object Cl entEvent mpress on {
  object T etL nger mpress on extends BaseCl entEvent(Act onType.Cl entT etL nger mpress on) {
    overr de def getUua em(
      ce em: LogEvent em,
      logEvent: LogEvent
    ): Opt on[ em] = {
      for {
        act onT et d <- ce em. d
         mpress onDeta ls <- ce em. mpress onDeta ls
        l ngerStartT  stampMs <-  mpress onDeta ls.v s b l yStart
        l ngerEndT  stampMs <-  mpress onDeta ls.v s b l yEnd
      } y eld {
         em.T et nfo(
          Cl entEventCommonUt ls
            .getBas cT et nfo(act onT et d, ce em, logEvent.eventNa space)
            .copy(t etAct on nfo = So (
              T etAct on nfo.Cl entT etL nger mpress on(
                Cl entT etL nger mpress on(
                  l ngerStartT  stampMs = l ngerStartT  stampMs,
                  l ngerEndT  stampMs = l ngerEndT  stampMs
                )
              ))))
      }
    }
  }

  /**
   * To make par y w h  es ce's def n  on, render  mpress on for quoted T ets would em 
   * 2 events: 1 for t  quot ng T et and 1 for t  or g nal T et!!!
   */
  object T etRender mpress on extends BaseCl entEvent(Act onType.Cl entT etRender mpress on) {
    overr de def toUn f edUserAct on(logEvent: LogEvent): Seq[Un f edUserAct on] = {

      val logBase: Opt on[LogBase] = logEvent.logBase

      val raw = for {
        ed <- logEvent.eventDeta ls.toSeq
         ems <- ed. ems.toSeq
        ce em <-  ems
        eventT  stamp <- logBase.flatMap(getS ceT  stamp)
        uua em <- getUua em(ce em, logEvent)
         f  s emTypeVal d(ce em. emType)
      } y eld {
        val user dent f er: User dent f er = User dent f er(
          user d = logBase.flatMap(_.user d),
          guest dMarket ng = logBase.flatMap(_.guest dMarket ng))

        val productSurface: Opt on[ProductSurface] = ProductSurfaceUt ls
          .getProductSurface(logEvent.eventNa space)

        val event taData: Event tadata = Cl entEventCommonUt ls
          .getEvent tadata(
            eventT  stamp = eventT  stamp,
            logEvent = logEvent,
            ce em = ce em,
            productSurface = productSurface
          )

        Un f edUserAct on(
          user dent f er = user dent f er,
           em = uua em,
          act onType = Act onType.Cl entT etRender mpress on,
          event tadata = event taData,
          productSurface = productSurface,
          productSurface nfo =
            ProductSurfaceUt ls.getProductSurface nfo(productSurface, ce em, logEvent)
        )
      }

      raw.flatMap { e =>
        e. em match {
          case T et nfo(t) =>
            //  f    s an  mpress on toward quoted T et   em  2  mpress ons, 1 for quot ng T et
            // and 1 for t  or g nal T et.
             f (t.quotedT et d. sDef ned) {
              val or g nal em = t.copy(
                act onT et d = t.quotedT et d.get,
                act onT etAuthor nfo = t.quotedAuthor d.map( d => Author nfo(author d = So ( d))),
                quot ngT et d = So (t.act onT et d),
                quotedT et d = None,
                 nReplyToT et d = None,
                reply ngT et d = None,
                ret et ngT et d = None,
                ret etedT et d = None,
                quotedAuthor d = None,
                ret et ngAuthor d = None,
                 nReplyToAuthor d = None
              )
              val or g nal = e.copy( em = T et nfo(or g nal em))
              Seq(or g nal, e)
            } else Seq(e)
          case _ => N l
        }
      }
    }
  }

  object T etGallery mpress on extends BaseCl entEvent(Act onType.Cl entT etGallery mpress on)

  object T etDeta ls mpress on extends BaseCl entEvent(Act onType.Cl entT etDeta ls mpress on) {

    case class EventNa space nternal(
      cl ent: Str ng,
      page: Str ng,
      sect on: Str ng,
      component: Str ng,
      ele nt: Str ng,
      act on: Str ng)

    def  sT etDeta ls mpress on(eventNa spaceOpt: Opt on[EventNa space]): Boolean =
      eventNa spaceOpt.ex sts { eventNa space =>
        val eventNa space nternal = EventNa space nternal(
          cl ent = eventNa space.cl ent.getOrElse(""),
          page = eventNa space.page.getOrElse(""),
          sect on = eventNa space.sect on.getOrElse(""),
          component = eventNa space.component.getOrElse(""),
          ele nt = eventNa space.ele nt.getOrElse(""),
          act on = eventNa space.act on.getOrElse(""),
        )

         s phoneAppOrMacAppOr padAppCl entT etDeta ls mpress on(
          eventNa space nternal) ||  sAndro dAppCl entT etDeta ls mpress on(
          eventNa space nternal) ||  s bCl entT etDeta l mpress on(
          eventNa space nternal) ||  sT etDeckAppCl entT etDeta ls mpress on(
          eventNa space nternal) ||  sOt rAppCl entT etDeta ls mpress on(eventNa space nternal)
      }

    pr vate def  s bCl entT etDeta l mpress on(
      eventNa space: EventNa space nternal
    ): Boolean = {
      val eventNa SpaceStr =
        eventNa space.cl ent + ":" + eventNa space.page + ":" + eventNa space.sect on + ":" + eventNa space.component + ":" + eventNa space.ele nt + ":" + eventNa space.act on
      eventNa SpaceStr.equals gnoreCase("m5:t et::::show") || eventNa SpaceStr.equals gnoreCase(
        "m5:t et:land ng:::show") || eventNa SpaceStr
        .equals gnoreCase("m2:t et:::: mpress on") || eventNa SpaceStr.equals gnoreCase(
        "m2:t et::t et:: mpress on") || eventNa SpaceStr
        .equals gnoreCase("L eNat veWrapper:t et::::show") || eventNa SpaceStr.equals gnoreCase(
        "L eNat veWrapper:t et:land ng:::show")
    }

    pr vate def  sOt rAppCl entT etDeta ls mpress on(
      eventNa space: EventNa space nternal
    ): Boolean = {
      val excludedCl ents = Set(
        " b",
        "m5",
        "m2",
        "L eNat veWrapper",
        " phone",
        " pad",
        "mac",
        "andro d",
        "andro d_tablet",
        "deck")
      (!excludedCl ents.conta ns(eventNa space.cl ent)) && eventNa space.page
        .equals gnoreCase("t et") && eventNa space.sect on
        .equals gnoreCase("") && eventNa space.component
        .equals gnoreCase("t et") && eventNa space.ele nt
        .equals gnoreCase("") && eventNa space.act on.equals gnoreCase(" mpress on")
    }

    pr vate def  sT etDeckAppCl entT etDeta ls mpress on(
      eventNa space: EventNa space nternal
    ): Boolean =
      eventNa space.cl ent
        .equals gnoreCase("deck") && eventNa space.page
        .equals gnoreCase("t et") && eventNa space.sect on
        .equals gnoreCase("") && eventNa space.component
        .equals gnoreCase("t et") && eventNa space.ele nt
        .equals gnoreCase("") && eventNa space.act on.equals gnoreCase(" mpress on")

    pr vate def  sAndro dAppCl entT etDeta ls mpress on(
      eventNa space: EventNa space nternal
    ): Boolean =
      (eventNa space.cl ent
        .equals gnoreCase("andro d") || eventNa space.cl ent
        .equals gnoreCase("andro d_tablet")) && eventNa space.page
        .equals gnoreCase("t et") && eventNa space.sect on.equals gnoreCase(
        "") && (eventNa space.component
        .equals gnoreCase("t et") || eventNa space.component
        .matc s("^suggest.*_t et.*$") || eventNa space.component
        .equals gnoreCase("")) && eventNa space.ele nt
        .equals gnoreCase("") && eventNa space.act on.equals gnoreCase(" mpress on")

    pr vate def  s phoneAppOrMacAppOr padAppCl entT etDeta ls mpress on(
      eventNa space: EventNa space nternal
    ): Boolean =
      (eventNa space.cl ent
        .equals gnoreCase(" phone") || eventNa space.cl ent
        .equals gnoreCase(" pad") || eventNa space.cl ent
        .equals gnoreCase("mac")) && eventNa space.page.equals gnoreCase(
        "t et") && eventNa space.sect on
        .equals gnoreCase("") && (eventNa space.component
        .equals gnoreCase("t et") || eventNa space.component
        .matc s("^suggest.*_t et.*$")) && eventNa space.ele nt
        .equals gnoreCase("") && eventNa space.act on.equals gnoreCase(" mpress on")
  }
}
