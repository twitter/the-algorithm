package com.tw ter.recos njector.event_processors

 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.ut l.SnowflakeUt ls
 mport com.tw ter.g zmoduck.thr ftscala.User
 mport com.tw ter.recos.ut l.Act on
 mport com.tw ter.recos.ut l.Act on.Act on
 mport com.tw ter.recos njector.cl ents.G zmoduck
 mport com.tw ter.recos njector.cl ents.Soc alGraph
 mport com.tw ter.recos njector.cl ents.T etyp e
 mport com.tw ter.recos njector.edges.T etEventToUserT etEnt yGraphBu lder
 mport com.tw ter.recos njector.edges.T etEventToUserUserGraphBu lder
 mport com.tw ter.recos njector.f lters.T etF lter
 mport com.tw ter.recos njector.f lters.UserF lter
 mport com.tw ter.recos njector.publ s rs.KafkaEventPubl s r
 mport com.tw ter.recos njector.ut l.T etCreateEventDeta ls
 mport com.tw ter.recos njector.ut l.T etDeta ls
 mport com.tw ter.recos njector.ut l.UserT etEngage nt
 mport com.tw ter.scrooge.Thr ftStructCodec
 mport com.tw ter.t etyp e.thr ftscala.T et
 mport com.tw ter.t etyp e.thr ftscala.T etCreateEvent
 mport com.tw ter.t etyp e.thr ftscala.T etEvent
 mport com.tw ter.t etyp e.thr ftscala.T etEventData
 mport com.tw ter.ut l.Future

/**
 * Event processor for t et_events EventBus stream from T etyp e. T  stream prov des all t 
 * key events related to a new t et, l ke Creat on, Ret et, Quote T et, and Reply ng.
 *   also carr es t  ent  es/ tadata  nformat on  n a t et,  nclud ng
 * @  nt on, HashTag,  d aTag, URL, etc.
 */
class T etEventProcessor(
  overr de val eventBusStreamNa : Str ng,
  overr de val thr ftStruct: Thr ftStructCodec[T etEvent],
  overr de val serv ce dent f er: Serv ce dent f er,
  userUserGraph ssageBu lder: T etEventToUserUserGraphBu lder,
  userUserGraphTop c: Str ng,
  userT etEnt yGraph ssageBu lder: T etEventToUserT etEnt yGraphBu lder,
  userT etEnt yGraphTop c: Str ng,
  kafkaEventPubl s r: KafkaEventPubl s r,
  soc alGraph: Soc alGraph,
  g zmoduck: G zmoduck,
  t etyp e: T etyp e
)(
  overr de  mpl c  val statsRece ver: StatsRece ver)
    extends EventBusProcessor[T etEvent] {

  pr vate val t etCreateEventCounter = statsRece ver.counter("num_t et_create_events")
  pr vate val nonT etCreateEventCounter = statsRece ver.counter("num_non_t et_create_events")

  pr vate val t etAct onStats = statsRece ver.scope("t et_act on")
  pr vate val numUrlCounter = statsRece ver.counter("num_t et_url")
  pr vate val num d aUrlCounter = statsRece ver.counter("num_t et_ d a_url")
  pr vate val numHashTagCounter = statsRece ver.counter("num_t et_hashtag")

  pr vate val num nt onsCounter = statsRece ver.counter("num_t et_ nt on")
  pr vate val num d atagCounter = statsRece ver.counter("num_t et_ d atag")
  pr vate val numVal d nt onsCounter = statsRece ver.counter("num_t et_val d_ nt on")
  pr vate val numVal d d atagCounter = statsRece ver.counter("num_t et_val d_ d atag")

  pr vate val numNullCastT etCounter = statsRece ver.counter("num_null_cast_t et")
  pr vate val numNullCastS ceT etCounter = statsRece ver.counter("num_null_cast_s ce_t et")
  pr vate val numT etFa lSafetyLevelCounter = statsRece ver.counter("num_fa l_t etyp e_safety")
  pr vate val numAuthorUnsafeCounter = statsRece ver.counter("num_author_unsafe")
  pr vate val numProcessT etCounter = statsRece ver.counter("num_process_t et")
  pr vate val numNoProcessT etCounter = statsRece ver.counter("num_no_process_t et")

  pr vate val selfRet etCounter = statsRece ver.counter("num_ret ets_self")

  pr vate val engageUserF lter = new UserF lter(g zmoduck)(statsRece ver.scope("author_user"))
  pr vate val t etF lter = new T etF lter(t etyp e)

  pr vate def trackT etCreateEventStats(deta ls: T etCreateEventDeta ls): Un  = {
    t etAct onStats.counter(deta ls.userT etEngage nt.act on.toStr ng). ncr()

    deta ls.userT etEngage nt.t etDeta ls.foreach { t etDeta ls =>
      t etDeta ls. nt onUser ds.foreach( nt on => num nt onsCounter. ncr( nt on.s ze))
      t etDeta ls. d atagUser ds.foreach( d atag => num d atagCounter. ncr( d atag.s ze))
      t etDeta ls.urls.foreach(urls => numUrlCounter. ncr(urls.s ze))
      t etDeta ls. d aUrls.foreach( d aUrls => num d aUrlCounter. ncr( d aUrls.s ze))
      t etDeta ls.hashtags.foreach(hashtags => numHashTagCounter. ncr(hashtags.s ze))
    }

    deta ls.val d nt onUser ds.foreach( nt ons => numVal d nt onsCounter. ncr( nt ons.s ze))
    deta ls.val d d atagUser ds.foreach( d atags => numVal d d atagCounter. ncr( d atags.s ze))
  }

  /**
   * G ven a created t et, return what type of t et    s,  .e. T et, Ret et, Quote, or Reply。
   * Ret et, Quote, or Reply are respons ve act ons to a s ce t et, so for t se t ets,
   *   also return t  t et  d and author of t  s ce t et (ex. t  t et be ng ret eted).
   */
  pr vate def getT etAct on(t etDeta ls: T etDeta ls): Act on = {
    (t etDeta ls.replyS ce d, t etDeta ls.ret etS ce d, t etDeta ls.quoteS ce d) match {
      case (So (_), _, _) =>
        Act on.Reply
      case (_, So (_), _) =>
        Act on.Ret et
      case (_, _, So (_)) =>
        Act on.Quote
      case _ =>
        Act on.T et
    }
  }

  /**
   * G ven a l st of  nt oned users and  d atagged users  n t  t et, return t  users who
   * actually follow t  s ce user.
   */
  pr vate def getFollo dBy ds(
    s ceUser d: Long,
     nt onUser ds: Opt on[Seq[Long]],
     d atagUser ds: Opt on[Seq[Long]]
  ): Future[Seq[Long]] = {
    val un queEnt yUser ds =
      ( nt onUser ds.getOrElse(N l) ++  d atagUser ds.getOrElse(N l)).d st nct
     f (un queEnt yUser ds. sEmpty) {
      Future.N l
    } else {
      soc alGraph.follo dByNotMutedBy(s ceUser d, un queEnt yUser ds)
    }
  }

  pr vate def getS ceT et(t etDeta ls: T etDeta ls): Future[Opt on[T et]] = {
    t etDeta ls.s ceT et d match {
      case So (s ceT et d) =>
        t etyp e.getT et(s ceT et d)
      case _ =>
        Future.None
    }
  }

  /**
   * Extract and return t  deta ls w n t  s ce user created a new t et.
   */
  pr vate def getT etDeta ls(
    t et: T et,
    engageUser: User
  ): Future[T etCreateEventDeta ls] = {
    val t etDeta ls = T etDeta ls(t et)

    val act on = getT etAct on(t etDeta ls)
    val t etCreat onT  M ll s = SnowflakeUt ls.t etCreat onT  (t et. d).map(_. nM ll seconds)
    val engageUser d = engageUser. d
    val userT etEngage nt = UserT etEngage nt(
      engageUser d = engageUser d,
      engageUser = So (engageUser),
      act on = act on,
      engage ntT  M ll s = t etCreat onT  M ll s,
      t et d = t et. d,
      t etDeta ls = So (t etDeta ls)
    )

    val s ceT etFut = getS ceT et(t etDeta ls)
    val follo dBy dsFut = getFollo dBy ds(
      engageUser d,
      t etDeta ls. nt onUser ds,
      t etDeta ls. d atagUser ds
    )

    Future.jo n(follo dBy dsFut, s ceT etFut).map {
      case (follo dBy ds, s ceT et) =>
        T etCreateEventDeta ls(
          userT etEngage nt = userT etEngage nt,
          val dEnt yUser ds = follo dBy ds,
          s ceT etDeta ls = s ceT et.map(T etDeta ls)
        )
    }
  }

  /**
   * Exclude any Ret ets of one's own t ets
   */
  pr vate def  sEventSelfRet et(t etEvent: T etCreateEventDeta ls): Boolean = {
    (t etEvent.userT etEngage nt.act on == Act on.Ret et) &&
    t etEvent.userT etEngage nt.t etDeta ls.ex sts(
      _.s ceT etUser d.conta ns(
        t etEvent.userT etEngage nt.engageUser d
      ))
  }

  pr vate def  sT etPassSafetyF lter(t etEvent: T etCreateEventDeta ls): Future[Boolean] = {
    t etEvent.userT etEngage nt.act on match {
      case Act on.Reply | Act on.Ret et | Act on.Quote =>
        t etEvent.userT etEngage nt.t etDeta ls
          .flatMap(_.s ceT et d).map { s ceT et d =>
            t etF lter.f lterForT etyp eSafetyLevel(s ceT et d)
          }.getOrElse(Future(false))
      case Act on.T et =>
        t etF lter.f lterForT etyp eSafetyLevel(t etEvent.userT etEngage nt.t et d)
    }
  }

  pr vate def shouldProcessT etEvent(event: T etCreateEventDeta ls): Future[Boolean] = {
    val engage nt = event.userT etEngage nt
    val engageUser d = engage nt.engageUser d

    val  sNullCastT et = engage nt.t etDeta ls.forall(_. sNullCastT et)
    val  sNullCastS ceT et = event.s ceT etDeta ls.ex sts(_. sNullCastT et)
    val  sSelfRet et =  sEventSelfRet et(event)
    val  sEngageUserSafeFut = engageUserF lter.f lterByUser d(engageUser d)
    val  sT etPassSafetyFut =  sT etPassSafetyF lter(event)

    Future.jo n( sEngageUserSafeFut,  sT etPassSafetyFut).map {
      case ( sEngageUserSafe,  sT etPassSafety) =>
         f ( sNullCastT et) numNullCastT etCounter. ncr()
         f ( sNullCastS ceT et) numNullCastS ceT etCounter. ncr()
         f (! sEngageUserSafe) numAuthorUnsafeCounter. ncr()
         f ( sSelfRet et) selfRet etCounter. ncr()
         f (! sT etPassSafety) numT etFa lSafetyLevelCounter. ncr()

        ! sNullCastT et &&
        ! sNullCastS ceT et &&
        ! sSelfRet et &&
         sEngageUserSafe &&
         sT etPassSafety
    }
  }

  overr de def processEvent(event: T etEvent): Future[Un ] = {
    event.data match {
      case T etEventData.T etCreateEvent(event: T etCreateEvent) =>
        getT etDeta ls(
          t et = event.t et,
          engageUser = event.user
        ).flatMap { eventW hDeta ls =>
          t etCreateEventCounter. ncr()

          shouldProcessT etEvent(eventW hDeta ls).map {
            case true =>
              numProcessT etCounter. ncr()
              trackT etCreateEventStats(eventW hDeta ls)
              // Convert t  event for UserUserGraph
              userUserGraph ssageBu lder.processEvent(eventW hDeta ls).map { edges =>
                edges.foreach { edge =>
                  kafkaEventPubl s r.publ sh(edge.convertToRecosHose ssage, userUserGraphTop c)
                }
              }
              // Convert t  event for UserT etEnt yGraph
              userT etEnt yGraph ssageBu lder.processEvent(eventW hDeta ls).map { edges =>
                edges.foreach { edge =>
                  kafkaEventPubl s r
                    .publ sh(edge.convertToRecosHose ssage, userT etEnt yGraphTop c)
                }
              }
            case false =>
              numNoProcessT etCounter. ncr()
          }
        }
      case _ =>
        nonT etCreateEventCounter. ncr()
        Future.Un 
    }
  }
}
