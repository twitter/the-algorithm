package com.tw ter.recos njector.event_processors

 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.recos.ut l.Act on
 mport com.tw ter.recos njector.cl ents.G zmoduck
 mport com.tw ter.recos njector.cl ents.T etyp e
 mport com.tw ter.recos njector.dec der.Recos njectorDec der
 mport com.tw ter.recos njector.dec der.Recos njectorDec derConstants
 mport com.tw ter.recos njector.edges.T  l neEventToUserT etEnt yGraphBu lder
 mport com.tw ter.recos njector.f lters.T etF lter
 mport com.tw ter.recos njector.f lters.UserF lter
 mport com.tw ter.recos njector.publ s rs.KafkaEventPubl s r
 mport com.tw ter.recos njector.ut l.T etDeta ls
 mport com.tw ter.recos njector.ut l.T etFavor eEventDeta ls
 mport com.tw ter.recos njector.ut l.UserT etEngage nt
 mport com.tw ter.scrooge.Thr ftStructCodec
 mport com.tw ter.t  l neserv ce.thr ftscala.Favor eEvent
 mport com.tw ter.t  l neserv ce.thr ftscala.Unfavor eEvent
 mport com.tw ter.t  l neserv ce.thr ftscala.{Event => T  l neEvent}
 mport com.tw ter.ut l.Future

/**
 * Processor for T  l ne events, such as Favor e (l k ng) t ets
 */
class T  l neEventProcessor(
  overr de val eventBusStreamNa : Str ng,
  overr de val thr ftStruct: Thr ftStructCodec[T  l neEvent],
  overr de val serv ce dent f er: Serv ce dent f er,
  kafkaEventPubl s r: KafkaEventPubl s r,
  userT etEnt yGraphTop c: Str ng,
  userT etEnt yGraph ssageBu lder: T  l neEventToUserT etEnt yGraphBu lder,
  dec der: Recos njectorDec der,
  g zmoduck: G zmoduck,
  t etyp e: T etyp e
)(
  overr de  mpl c  val statsRece ver: StatsRece ver)
    extends EventBusProcessor[T  l neEvent] {

  pr vate val processEventDec derCounter = statsRece ver.counter("num_process_t  l ne_event")
  pr vate val numFavor eEventCounter = statsRece ver.counter("num_favor e_event")
  pr vate val numUnFavor eEventCounter = statsRece ver.counter("num_unfavor e_event")
  pr vate val numNotFavor eEventCounter = statsRece ver.counter("num_not_favor e_event")

  pr vate val numSelfFavor eCounter = statsRece ver.counter("num_self_favor e_event")
  pr vate val numNullCastT etCounter = statsRece ver.counter("num_null_cast_t et")
  pr vate val numT etFa lSafetyLevelCounter = statsRece ver.counter("num_fa l_t etyp e_safety")
  pr vate val numFavor eUserUnsafeCounter = statsRece ver.counter("num_favor e_user_unsafe")
  pr vate val engageUserF lter = new UserF lter(g zmoduck)(statsRece ver.scope("engage_user"))
  pr vate val t etF lter = new T etF lter(t etyp e)

  pr vate val numProcessFavor e = statsRece ver.counter("num_process_favor e")
  pr vate val numNoProcessFavor e = statsRece ver.counter("num_no_process_favor e")

  pr vate def getFavor eEventDeta ls(
    favor eEvent: Favor eEvent
  ): T etFavor eEventDeta ls = {

    val engage nt = UserT etEngage nt(
      engageUser d = favor eEvent.user d,
      engageUser = favor eEvent.user,
      act on = Act on.Favor e,
      engage ntT  M ll s = So (favor eEvent.eventT  Ms),
      t et d = favor eEvent.t et d, // t  t et, or s ce t et  f target t et  s a ret et
      t etDeta ls = favor eEvent.t et.map(T etDeta ls) // t et always ex sts
    )
    T etFavor eEventDeta ls(userT etEngage nt = engage nt)
  }

  pr vate def getUnfavor eEventDeta ls(
    unfavor eEvent: Unfavor eEvent
  ): T etFavor eEventDeta ls = {
    val engage nt = UserT etEngage nt(
      engageUser d = unfavor eEvent.user d,
      engageUser = unfavor eEvent.user,
      act on = Act on.Unfavor e,
      engage ntT  M ll s = So (unfavor eEvent.eventT  Ms),
      t et d = unfavor eEvent.t et d, // t  t et, or s ce t et  f target t et  s a ret et
      t etDeta ls = unfavor eEvent.t et.map(T etDeta ls) // t et always ex sts
    )
    T etFavor eEventDeta ls(userT etEngage nt = engage nt)
  }

  pr vate def shouldProcessFavor eEvent(event: T etFavor eEventDeta ls): Future[Boolean] = {
    val engage nt = event.userT etEngage nt
    val engageUser d = engage nt.engageUser d
    val t et d = engage nt.t et d
    val author dOpt = engage nt.t etDeta ls.flatMap(_.author d)

    val  sSelfFavor e = author dOpt.conta ns(engageUser d)
    val  sNullCastT et = engage nt.t etDeta ls.forall(_. sNullCastT et)
    val  sEngageUserSafeFut = engageUserF lter.f lterByUser d(engageUser d)
    val  sT etPassSafetyFut = t etF lter.f lterForT etyp eSafetyLevel(t et d)

    Future.jo n( sEngageUserSafeFut,  sT etPassSafetyFut).map {
      case ( sEngageUserSafe,  sT etPassSafety) =>
         f ( sSelfFavor e) numSelfFavor eCounter. ncr()
         f ( sNullCastT et) numNullCastT etCounter. ncr()
         f (! sEngageUserSafe) numFavor eUserUnsafeCounter. ncr()
         f (! sT etPassSafety) numT etFa lSafetyLevelCounter. ncr()

        ! sSelfFavor e && ! sNullCastT et &&  sEngageUserSafe &&  sT etPassSafety
    }
  }

  pr vate def processFavor eEvent(favor eEvent: Favor eEvent): Future[Un ] = {
    val eventDeta ls = getFavor eEventDeta ls(favor eEvent)
    shouldProcessFavor eEvent(eventDeta ls).map {
      case true =>
        numProcessFavor e. ncr()
        // Convert t  event for UserT etEnt yGraph
        userT etEnt yGraph ssageBu lder.processEvent(eventDeta ls).map { edges =>
          edges.foreach { edge =>
            kafkaEventPubl s r.publ sh(edge.convertToRecosHose ssage, userT etEnt yGraphTop c)
          }
        }
      case false =>
        numNoProcessFavor e. ncr()
    }
  }

  pr vate def processUnFavor eEvent(unFavor eEvent: Unfavor eEvent): Future[Un ] = {
     f (dec der. sAva lable(Recos njectorDec derConstants.EnableUnfavor eEdge)) {
      val eventDeta ls = getUnfavor eEventDeta ls(unFavor eEvent)
      // Convert t  event for UserT etEnt yGraph
      userT etEnt yGraph ssageBu lder.processEvent(eventDeta ls).map { edges =>
        edges.foreach { edge =>
          kafkaEventPubl s r.publ sh(edge.convertToRecosHose ssage, userT etEnt yGraphTop c)
        }
      }
    } else {
      Future.Un 
    }
  }

  overr de def processEvent(event: T  l neEvent): Future[Un ] = {
    processEventDec derCounter. ncr()
    event match {
      case T  l neEvent.Favor e(favor eEvent: Favor eEvent) =>
        numFavor eEventCounter. ncr()
        processFavor eEvent(favor eEvent)
      case T  l neEvent.Unfavor e(unFavor eEvent: Unfavor eEvent) =>
        numUnFavor eEventCounter. ncr()
        processUnFavor eEvent(unFavor eEvent)
      case _ =>
        numNotFavor eEventCounter. ncr()
        Future.Un 
    }
  }
}
