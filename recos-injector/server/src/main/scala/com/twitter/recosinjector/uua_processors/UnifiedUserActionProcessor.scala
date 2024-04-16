package com.tw ter.recos njector.uua_processors

 mport org.apac .kafka.cl ents.consu r.Consu rRecord
 mport com.tw ter.f natra.kafka.serde.UnKeyed
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.recos.ut l.Act on
 mport com.tw ter.recos.ut l.Act on.Act on
 mport com.tw ter.recos njector.cl ents.G zmoduck
 mport com.tw ter.recos njector.cl ents.T etyp e
 mport com.tw ter.recos njector.edges.Un f edUserAct onToUserV deoGraphBu lder
 mport com.tw ter.recos njector.edges.Un f edUserAct onToUserAdGraphBu lder
 mport com.tw ter.recos njector.edges.Un f edUserAct onToUserT etGraphPlusBu lder
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Un f edUserAct on
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Act onType
 mport com.tw ter.un f ed_user_act ons.thr ftscala. em
 mport com.tw ter.recos njector.f lters.UserF lter
 mport com.tw ter.recos njector.publ s rs.KafkaEventPubl s r
 mport com.tw ter.recos njector.ut l.T etDeta ls
 mport com.tw ter.recos njector.ut l.UserT etEngage nt
 mport com.tw ter.recos njector.ut l.UuaEngage ntEventDeta ls
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Not f cat onContent
 mport com.tw ter.un f ed_user_act ons.thr ftscala.Not f cat on nfo
 mport com.tw ter.ut l.Future

class Un f edUserAct onProcessor(
  g zmoduck: G zmoduck,
  t etyp e: T etyp e,
  kafkaEventPubl s r: KafkaEventPubl s r,
  userV deoGraphTop c: Str ng,
  userV deoGraphBu lder: Un f edUserAct onToUserV deoGraphBu lder,
  userAdGraphTop c: Str ng,
  userAdGraphBu lder: Un f edUserAct onToUserAdGraphBu lder,
  userT etGraphPlusTop c: Str ng,
  userT etGraphPlusBu lder: Un f edUserAct onToUserT etGraphPlusBu lder
)(
   mpl c  statsRece ver: StatsRece ver) {

  val  ssagesProcessedCount = statsRece ver.counter(" ssages_processed")

  val eventsByTypeCounts = statsRece ver.scope("events_by_type")
  pr vate val numSelfEngageCounter = statsRece ver.counter("num_self_engage_event")
  pr vate val numT etFa lSafetyLevelCounter = statsRece ver.counter("num_fa l_t etyp e_safety")
  pr vate val numNullCastT etCounter = statsRece ver.counter("num_null_cast_t et")
  pr vate val numEngageUserUnsafeCounter = statsRece ver.counter("num_engage_user_unsafe")
  pr vate val engageUserF lter = new UserF lter(g zmoduck)(statsRece ver.scope("engage_user"))
  pr vate val numNoProcessT etCounter = statsRece ver.counter("num_no_process_t et")
  pr vate val numProcessT etCounter = statsRece ver.counter("num_process_t et")

  pr vate def getUuaEngage ntEventDeta ls(
    un f edUserAct on: Un f edUserAct on
  ): Opt on[Future[UuaEngage ntEventDeta ls]] = {
    val user dOpt = un f edUserAct on.user dent f er.user d
    val t et dOpt = un f edUserAct on. em match {
      case  em.T et nfo(t et nfo) => So (t et nfo.act onT et d)
      case  em.Not f cat on nfo(
            Not f cat on nfo(_, Not f cat onContent.T etNot f cat on(not f cat on))) =>
        So (not f cat on.t et d)
      case _ => None
    }
    val t  stamp = un f edUserAct on.event tadata.s ceT  stampMs
    val act on = getT etAct on(un f edUserAct on.act onType)

    t et dOpt
      .flatMap { t et d =>
        user dOpt.map { engageUser d =>
          val t etFut = t etyp e.getT et(t et d)
          t etFut.map { t etOpt =>
            val t etDeta lsOpt = t etOpt.map(T etDeta ls)
            val engage nt = UserT etEngage nt(
              engageUser d = engageUser d,
              act on = act on,
              engage ntT  M ll s = So (t  stamp),
              t et d = t et d,
              engageUser = None,
              t etDeta ls = t etDeta lsOpt
            )
            UuaEngage ntEventDeta ls(engage nt)
          }
        }
      }
  }

  pr vate def getT etAct on(act on: Act onType): Act on = {
    act on match {
      case Act onType.Cl entT etV deoPlayback50 => Act on.V deoPlayback50
      case Act onType.Cl entT etCl ck => Act on.Cl ck
      case Act onType.Cl entT etV deoPlayback75 => Act on.V deoPlayback75
      case Act onType.Cl entT etV deoQual yV ew => Act on.V deoQual yV ew
      case Act onType.ServerT etFav => Act on.Favor e
      case Act onType.ServerT etReply => Act on.Reply
      case Act onType.ServerT etRet et => Act on.Ret et
      case Act onType.Cl entT etQuote => Act on.Quote
      case Act onType.Cl entNot f cat onOpen => Act on.Not f cat onOpen
      case Act onType.Cl entT etEma lCl ck => Act on.Ema lCl ck
      case Act onType.Cl entT etShareV aBookmark => Act on.Share
      case Act onType.Cl entT etShareV aCopyL nk => Act on.Share
      case Act onType.Cl entT etSeeFe r => Act on.T etSeeFe r
      case Act onType.Cl entT etNotRelevant => Act on.T etNotRelevant
      case Act onType.Cl entT etNot nterested n => Act on.T etNot nterested n
      case Act onType.ServerT etReport => Act on.T etReport
      case Act onType.Cl entT etMuteAuthor => Act on.T etMuteAuthor
      case Act onType.Cl entT etBlockAuthor => Act on.T etBlockAuthor
      case _ => Act on.UnDef ned
    }
  }
  pr vate def shouldProcessT etEngage nt(
    event: UuaEngage ntEventDeta ls,
     sAdsUseCase: Boolean = false
  ): Future[Boolean] = {
    val engage nt = event.userT etEngage nt
    val engageUser d = engage nt.engageUser d
    val author dOpt = engage nt.t etDeta ls.flatMap(_.author d)

    val  sSelfEngage = author dOpt.conta ns(engageUser d)
    val  sNullCastT et = engage nt.t etDeta ls.forall(_. sNullCastT et)
    val  sEngageUserSafeFut = engageUserF lter.f lterByUser d(engageUser d)
    val  sT etPassSafety =
      engage nt.t etDeta ls. sDef ned // T etyp e can fetch a t et object successfully

     sEngageUserSafeFut.map {  sEngageUserSafe =>
       f ( sSelfEngage) numSelfEngageCounter. ncr()
       f ( sNullCastT et) numNullCastT etCounter. ncr()
       f (! sEngageUserSafe) numEngageUserUnsafeCounter. ncr()
       f (! sT etPassSafety) numT etFa lSafetyLevelCounter. ncr()

      ! sSelfEngage && (! sNullCastT et && ! sAdsUseCase ||  sNullCastT et &&  sAdsUseCase) &&  sEngageUserSafe &&  sT etPassSafety
    }
  }

  def apply(record: Consu rRecord[UnKeyed, Un f edUserAct on]): Future[Un ] = {

     ssagesProcessedCount. ncr()
    val un f edUserAct on = record.value
    eventsByTypeCounts.counter(un f edUserAct on.act onType.toStr ng). ncr()

    getT etAct on(un f edUserAct on.act onType) match {
      case Act on.UnDef ned =>
        numNoProcessT etCounter. ncr()
        Future.Un 
      case act on =>
        getUuaEngage ntEventDeta ls(un f edUserAct on)
          .map {
            _.flatMap { deta l =>
              // T  follow ng cases are set up spec f cally for an ads relevance demo.
              val act onForAds = Set(Act on.Cl ck, Act on.Favor e, Act on.V deoPlayback75)
               f (act onForAds.conta ns(act on))
                shouldProcessT etEngage nt(deta l,  sAdsUseCase = true).map {
                  case true =>
                    userAdGraphBu lder.processEvent(deta l).map { edges =>
                      edges.foreach { edge =>
                        kafkaEventPubl s r
                          .publ sh(edge.convertToRecosHose ssage, userAdGraphTop c)
                      }
                    }
                    numProcessT etCounter. ncr()
                  case _ =>
                }

              shouldProcessT etEngage nt(deta l).map {
                case true =>
                  userV deoGraphBu lder.processEvent(deta l).map { edges =>
                    edges.foreach { edge =>
                      kafkaEventPubl s r
                        .publ sh(edge.convertToRecosHose ssage, userV deoGraphTop c)
                    }
                  }

                  userT etGraphPlusBu lder.processEvent(deta l).map { edges =>
                    edges.foreach { edge =>
                      kafkaEventPubl s r
                        .publ sh(edge.convertToRecosHose ssage, userT etGraphPlusTop c)
                    }
                  }
                  numProcessT etCounter. ncr()
                case _ =>
              }
            }
          }.getOrElse(Future.Un )
    }
  }
}
