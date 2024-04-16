package com.tw ter.v s b l y.bu lder.dms

 mport com.tw ter.convosvc.thr ftscala.Event
 mport com.tw ter.convosvc.thr ftscala.StoredDelete
 mport com.tw ter.convosvc.thr ftscala.StoredPerspect val ssage nfo
 mport com.tw ter.convosvc.thr ftscala.Perspect valSpamState
 mport com.tw ter.st ch.St ch
 mport com.tw ter.v s b l y.bu lder.FeatureMapBu lder
 mport com.tw ter.v s b l y.bu lder.users.AuthorFeatures
 mport com.tw ter.v s b l y.common.DmEvent d
 mport com.tw ter.v s b l y.common.dm_s ces.DmEventS ce
 mport com.tw ter.v s b l y.common.User d
 mport com.tw ter.convosvc.thr ftscala.EventType
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.st ch.NotFound
 mport com.tw ter.v s b l y.common.dm_s ces.DmConversat onS ce
 mport com.tw ter.v s b l y.features._

case class  nval dDmEventFeatureExcept on( ssage: Str ng) extends Except on( ssage)

class DmEventFeatures(
  dmEventS ce: DmEventS ce,
  dmConversat onS ce: DmConversat onS ce,
  authorFeatures: AuthorFeatures,
  dmConversat onFeatures: DmConversat onFeatures,
  statsRece ver: StatsRece ver) {
  pr vate[t ] val scopedStatsRece ver = statsRece ver.scope("dm_event_features")
  pr vate[t ] val requests = scopedStatsRece ver.counter("requests")

  def forDmEvent d(
    dmEvent d: DmEvent d,
    v e r d: User d
  ): FeatureMapBu lder => FeatureMapBu lder = {
    requests. ncr()

    val dmEventSt chRef: St ch[Opt on[Event]] =
      St ch.ref(dmEventS ce.getDmEvent(dmEvent d, v e r d))

    _.w hFeature(
      DmEvent s ssageCreateEvent,
       sDmEventType(dmEventSt chRef, EventType. ssageCreate))
      .w hFeature(
        Author sSuspended,
         ssageCreateEventHas nact ve n  at ngUser(
          dmEventSt chRef,
           n  at ngUser => authorFeatures.author sSuspended( n  at ngUser))
      )
      .w hFeature(
        Author sDeact vated,
         ssageCreateEventHas nact ve n  at ngUser(
          dmEventSt chRef,
           n  at ngUser => authorFeatures.author sDeact vated( n  at ngUser))
      )
      .w hFeature(
        Author sErased,
         ssageCreateEventHas nact ve n  at ngUser(
          dmEventSt chRef,
           n  at ngUser => authorFeatures.author sErased( n  at ngUser))
      )
      .w hFeature(
        DmEventOccurredBeforeLastClearedEvent,
        dmEventOccurredBeforeLastClearedEvent(dmEventSt chRef, dmEvent d, v e r d)
      )
      .w hFeature(
        DmEventOccurredBeforeJo nConversat onEvent,
        dmEventOccurredBeforeJo nConversat onEvent(dmEventSt chRef, dmEvent d, v e r d)
      )
      .w hFeature(
        V e r sDmConversat onPart c pant,
        dmEventV e r sDmConversat onPart c pant(dmEventSt chRef, v e r d)
      )
      .w hFeature(
        DmEvent sDeleted,
        dmEvent sDeleted(dmEventSt chRef, dmEvent d)
      )
      .w hFeature(
        DmEvent sH dden,
        dmEvent sH dden(dmEventSt chRef, dmEvent d)
      )
      .w hFeature(
        V e r sDmEvent n  at ngUser,
        v e r sDmEvent n  at ngUser(dmEventSt chRef, v e r d)
      )
      .w hFeature(
        DmEvent nOneToOneConversat onW hUnava lableUser,
        dmEvent nOneToOneConversat onW hUnava lableUser(dmEventSt chRef, v e r d)
      )
      .w hFeature(
        DmEvent sLast ssageReadUpdateEvent,
         sDmEventType(dmEventSt chRef, EventType.Last ssageReadUpdate)
      )
      .w hFeature(
        DmEvent sJo nConversat onEvent,
         sDmEventType(dmEventSt chRef, EventType.Jo nConversat on)
      )
      .w hFeature(
        DmEvent s lco  ssageCreateEvent,
         sDmEventType(dmEventSt chRef, EventType. lco  ssageCreate)
      )
      .w hFeature(
        DmEvent sTrustConversat onEvent,
         sDmEventType(dmEventSt chRef, EventType.TrustConversat on)
      )
      .w hFeature(
        DmEvent sCsFeedbackSubm ted,
         sDmEventType(dmEventSt chRef, EventType.CsFeedbackSubm ted)
      )
      .w hFeature(
        DmEvent sCsFeedbackD sm ssed,
         sDmEventType(dmEventSt chRef, EventType.CsFeedbackD sm ssed)
      )
      .w hFeature(
        DmEvent sConversat onCreateEvent,
         sDmEventType(dmEventSt chRef, EventType.Conversat onCreate)
      )
      .w hFeature(
        DmEvent nOneToOneConversat on,
        dmEvent nOneToOneConversat on(dmEventSt chRef, v e r d)
      )
      .w hFeature(
        DmEvent sPerspect valJo nConversat onEvent,
        dmEvent sPerspect valJo nConversat onEvent(dmEventSt chRef, dmEvent d, v e r d))

  }

  pr vate def  sDmEventType(
    dmEventOptSt ch: St ch[Opt on[Event]],
    eventType: EventType
  ): St ch[Boolean] =
    dmEventS ce.getEventType(dmEventOptSt ch).flatMap {
      case So (_: eventType.type) =>
        St ch.True
      case None =>
        St ch.except on( nval dDmEventFeatureExcept on(s"$eventType event type not found"))
      case _ =>
        St ch.False
    }

  pr vate def dmEvent sPerspect valJo nConversat onEvent(
    dmEventOptSt ch: St ch[Opt on[Event]],
    dmEvent d: DmEvent d,
    v e r d: User d
  ): St ch[Boolean] =
    St ch
      .jo n(
        dmEventS ce.getEventType(dmEventOptSt ch),
        dmEventS ce.getConversat on d(dmEventOptSt ch)).flatMap {
        case (So (EventType.Jo nConversat on), conversat on dOpt) =>
          conversat on dOpt match {
            case So (conversat on d) =>
              dmConversat onS ce
                .getPart c pantJo nConversat onEvent d(conversat on d, v e r d, v e r d)
                .flatMap {
                  case So (jo nConversat onEvent d) =>
                    St ch.value(jo nConversat onEvent d == dmEvent d)
                  case _ => St ch.False
                }
            case _ =>
              St ch.except on( nval dDmEventFeatureExcept on("Conversat on  d not found"))
          }
        case (None, _) =>
          St ch.except on( nval dDmEventFeatureExcept on("Event type not found"))
        case _ => St ch.False
      }

  pr vate def  ssageCreateEventHas nact ve n  at ngUser(
    dmEventOptSt ch: St ch[Opt on[Event]],
    cond  on: User d => St ch[Boolean],
  ): St ch[Boolean] =
    St ch
      .jo n(
        dmEventS ce.getEventType(dmEventOptSt ch),
        dmEventS ce.get n  at ngUser d(dmEventOptSt ch)).flatMap {
        case (So (EventType. ssageCreate), So (user d)) =>
          cond  on(user d).rescue {
            case NotFound =>
              St ch.except on( nval dDmEventFeatureExcept on(" n  at ng user not found"))
          }
        case (None, _) =>
          St ch.except on( nval dDmEventFeatureExcept on("DmEvent type  s m ss ng"))
        case (So (EventType. ssageCreate), _) =>
          St ch.except on( nval dDmEventFeatureExcept on(" n  at ng user  d  s m ss ng"))
        case _ => St ch.False
      }

  pr vate def dmEventOccurredBeforeLastClearedEvent(
    dmEventOptSt ch: St ch[Opt on[Event]],
    dmEvent d: DmEvent d,
    v e r d: User d
  ): St ch[Boolean] = {
    dmEventS ce.getConversat on d(dmEventOptSt ch).flatMap {
      case So (convo d) =>
        val lastClearedEvent dSt ch =
          dmConversat onS ce.getPart c pantLastClearedEvent d(convo d, v e r d, v e r d)
        lastClearedEvent dSt ch.flatMap {
          case So (lastClearedEvent d) => St ch(dmEvent d <= lastClearedEvent d)
          case _ =>
            St ch.False
        }
      case _ => St ch.False
    }
  }

  pr vate def dmEventOccurredBeforeJo nConversat onEvent(
    dmEventOptSt ch: St ch[Opt on[Event]],
    dmEvent d: DmEvent d,
    v e r d: User d
  ): St ch[Boolean] = {
    dmEventS ce.getConversat on d(dmEventOptSt ch).flatMap {
      case So (convo d) =>
        val jo nConversat onEvent dSt ch =
          dmConversat onS ce
            .getPart c pantJo nConversat onEvent d(convo d, v e r d, v e r d)
        jo nConversat onEvent dSt ch.flatMap {
          case So (jo nConversat onEvent d) => St ch(dmEvent d < jo nConversat onEvent d)
          case _ => St ch.False
        }
      case _ => St ch.False
    }
  }

  pr vate def dmEventV e r sDmConversat onPart c pant(
    dmEventOptSt ch: St ch[Opt on[Event]],
    v e r d: User d
  ): St ch[Boolean] = {
    dmEventS ce.getConversat on d(dmEventOptSt ch).flatMap {
      case So (convo d) =>
        dmConversat onFeatures.v e r sDmConversat onPart c pant(convo d, So (v e r d))
      case _ => St ch.True
    }
  }

  pr vate def dmEvent sDeleted(
    dmEventOptSt ch: St ch[Opt on[Event]],
    dmEvent d: DmEvent d
  ): St ch[Boolean] =
    dmEventS ce.getConversat on d(dmEventOptSt ch).flatMap {
      case So (convo d) =>
        dmConversat onS ce
          .getDelete nfo(convo d, dmEvent d).rescue {
            case e: java.lang. llegalArgu ntExcept on =>
              St ch.except on( nval dDmEventFeatureExcept on(" nval d conversat on  d"))
          }.flatMap {
            case So (StoredDelete(None)) => St ch.True
            case _ => St ch.False
          }
      case _ => St ch.False
    }

  pr vate def dmEvent sH dden(
    dmEventOptSt ch: St ch[Opt on[Event]],
    dmEvent d: DmEvent d
  ): St ch[Boolean] =
    dmEventS ce.getConversat on d(dmEventOptSt ch).flatMap {
      case So (convo d) =>
        dmConversat onS ce
          .getPerspect val ssage nfo(convo d, dmEvent d).rescue {
            case e: java.lang. llegalArgu ntExcept on =>
              St ch.except on( nval dDmEventFeatureExcept on(" nval d conversat on  d"))
          }.flatMap {
            case So (StoredPerspect val ssage nfo(So (h dden), _))  f h dden =>
              St ch.True
            case So (StoredPerspect val ssage nfo(_, So (spamState)))
                 f spamState == Perspect valSpamState.Spam =>
              St ch.True
            case _ => St ch.False
          }
      case _ => St ch.False
    }

  pr vate def v e r sDmEvent n  at ngUser(
    dmEventOptSt ch: St ch[Opt on[Event]],
    v e r d: User d
  ): St ch[Boolean] =
    St ch
      .jo n(
        dmEventS ce.getEventType(dmEventOptSt ch),
        dmEventS ce.get n  at ngUser d(dmEventOptSt ch)).flatMap {
        case (
              So (
                EventType.TrustConversat on | EventType.CsFeedbackSubm ted |
                EventType.CsFeedbackD sm ssed | EventType. lco  ssageCreate |
                EventType.Jo nConversat on),
              So (user d)) =>
          St ch(v e r d == user d)
        case (
              So (
                EventType.TrustConversat on | EventType.CsFeedbackSubm ted |
                EventType.CsFeedbackD sm ssed | EventType. lco  ssageCreate |
                EventType.Jo nConversat on),
              None) =>
          St ch.except on( nval dDmEventFeatureExcept on(" n  at ng user  d  s m ss ng"))
        case (None, _) =>
          St ch.except on( nval dDmEventFeatureExcept on("DmEvent type  s m ss ng"))
        case _ => St ch.True
      }

  pr vate def dmEvent nOneToOneConversat onW hUnava lableUser(
    dmEventOptSt ch: St ch[Opt on[Event]],
    v e r d: User d
  ): St ch[Boolean] =
    dmEventS ce.getConversat on d(dmEventOptSt ch).flatMap {
      case So (conversat on d) =>
        dmConversat onFeatures
          .dmConversat on sOneToOneConversat on(conversat on d, So (v e r d)).flatMap {
             sOneToOne =>
               f ( sOneToOne) {
                St ch
                  .jo n(
                    dmConversat onFeatures
                      .dmConversat onHasSuspendedPart c pant(conversat on d, So (v e r d)),
                    dmConversat onFeatures
                      .dmConversat onHasDeact vatedPart c pant(conversat on d, So (v e r d)),
                    dmConversat onFeatures
                      .dmConversat onHasErasedPart c pant(conversat on d, So (v e r d))
                  ).flatMap {
                    case (
                          convoPart c pant sSuspended,
                          convoPart c pant sDeact vated,
                          convoPart c pant sErased) =>
                      St ch.value(
                        convoPart c pant sSuspended || convoPart c pant sDeact vated || convoPart c pant sErased)
                  }
              } else {
                St ch.False
              }
          }
      case _ => St ch.False
    }

  pr vate def dmEvent nOneToOneConversat on(
    dmEventOptSt ch: St ch[Opt on[Event]],
    v e r d: User d
  ): St ch[Boolean] =
    dmEventS ce.getConversat on d(dmEventOptSt ch).flatMap {
      case So (conversat on d) =>
        dmConversat onFeatures
          .dmConversat on sOneToOneConversat on(conversat on d, So (v e r d))
      case _ => St ch.False
    }
}
