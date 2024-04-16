package com.tw ter.recos njector.edges

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.store.T etCreat onT  MHStore
 mport com.tw ter.fr gate.common.ut l.SnowflakeUt ls
 mport com.tw ter.recos. nternal.thr ftscala.{RecosUserT et nfo, T etType}
 mport com.tw ter.recos.ut l.Act on
 mport com.tw ter.recos njector.dec der.Recos njectorDec der
 mport com.tw ter.recos njector.dec der.Recos njectorDec derConstants
 mport com.tw ter.recos njector.ut l.T etCreateEventDeta ls
 mport com.tw ter.ut l.{Future, T  }

class T etEventToUserT etEnt yGraphBu lder(
  userT etEnt yEdgeBu lder: UserT etEnt yEdgeBu lder,
  t etCreat onStore: T etCreat onT  MHStore,
  dec der: Recos njectorDec der
)(
  overr de  mpl c  val statsRece ver: StatsRece ver)
    extends EventTo ssageBu lder[T etCreateEventDeta ls, UserT etEnt yEdge] {

  // T etCreat onStore counters
  pr vate val lastT etT  Not nMh = statsRece ver.counter("last_t et_t  _not_ n_mh")
  pr vate val t etCreat onStore nserts = statsRece ver.counter("t et_creat on_store_ nserts")

  pr vate val num nval dAct onCounter = statsRece ver.counter("num_ nval d_t et_act on")

  pr vate val numT etEdgesCounter = statsRece ver.counter("num_t et_edge")
  pr vate val numRet etEdgesCounter = statsRece ver.counter("num_ret et_edge")
  pr vate val numReplyEdgesCounter = statsRece ver.counter("num_reply_edge")
  pr vate val numQuoteEdgesCounter = statsRece ver.counter("num_quote_edge")
  pr vate val num s nt onedEdgesCounter = statsRece ver.counter("num_ s nt oned_edge")
  pr vate val num s d ataggedEdgesCounter = statsRece ver.counter("num_ s d atagged_edge")

  pr vate val num sDec der = statsRece ver.counter("num_dec der_enabled")
  pr vate val num sNotDec der = statsRece ver.counter("num_dec der_not_enabled")

  overr de def shouldProcessEvent(event: T etCreateEventDeta ls): Future[Boolean] = {
    val  sDec der = dec der. sAva lable(
      Recos njectorDec derConstants.T etEventTransfor rUserT etEnt yEdgesDec der
    )
     f ( sDec der) {
      num sDec der. ncr()
      Future(true)
    } else {
      num sNotDec der. ncr()
      Future(false)
    }
  }

  /**
   * Bu ld edges Reply event. Reply event em s 2 edges:
   * author -> Reply -> S ceT et d
   * author -> T et -> Reply d
   * Do not assoc ate ent  es  n reply t et to t  s ce t et
   */
  pr vate def bu ldReplyEdge(event: T etCreateEventDeta ls) = {
    val userT etEngage nt = event.userT etEngage nt
    val author d = userT etEngage nt.engageUser d

    val replyEdgeFut = event.s ceT etDeta ls
      .map { s ceT etDeta ls =>
        val s ceT et d = s ceT etDeta ls.t et. d
        val s ceT etEnt  esMapFut = userT etEnt yEdgeBu lder.getEnt  esMapAndUpdateCac (
          t et d = s ceT et d,
          t etDeta ls = So (s ceT etDeta ls)
        )

        s ceT etEnt  esMapFut.map { s ceT etEnt  esMap =>
          val replyEdge = UserT etEnt yEdge(
            s ceUser = author d,
            targetT et = s ceT et d,
            act on = Act on.Reply,
             tadata = So (userT etEngage nt.t et d),
            card nfo = So (s ceT etDeta ls.card nfo.toByte),
            ent  esMap = s ceT etEnt  esMap,
            t etDeta ls = So (s ceT etDeta ls)
          )
          numReplyEdgesCounter. ncr()
          So (replyEdge)
        }
      }.getOrElse(Future.None)

    val t etCreat onEdgeFut =
       f (dec der. sAva lable(Recos njectorDec derConstants.EnableEm T etEdgeFromReply)) {
        getAndUpdateLastT etCreat onT  (
          author d = author d,
          t et d = userT etEngage nt.t et d,
          t etType = T etType.Reply
        ).map { lastT etT   =>
          val edge = UserT etEnt yEdge(
            s ceUser = author d,
            targetT et = userT etEngage nt.t et d,
            act on = Act on.T et,
             tadata = lastT etT  ,
            card nfo = userT etEngage nt.t etDeta ls.map(_.card nfo.toByte),
            ent  esMap = None,
            t etDeta ls = userT etEngage nt.t etDeta ls
          )
          numT etEdgesCounter. ncr()
          So (edge)
        }
      } else {
        Future.None
      }

    Future.jo n(replyEdgeFut, t etCreat onEdgeFut).map {
      case (replyEdgeOpt, t etCreat onEdgeOpt) =>
        t etCreat onEdgeOpt.toSeq ++ replyEdgeOpt.toSeq
    }
  }

  /**
   * Bu ld a Ret et UTEG edge: author -> RT -> S ceT et d.
   */
  pr vate def bu ldRet etEdge(event: T etCreateEventDeta ls) = {
    val userT etEngage nt = event.userT etEngage nt
    val t et d = userT etEngage nt.t et d

    event.s ceT etDeta ls
      .map { s ceT etDeta ls =>
        val s ceT et d = s ceT etDeta ls.t et. d //  d of t  t et be ng Ret eted
        val s ceT etEnt  esMapFut = userT etEnt yEdgeBu lder.getEnt  esMapAndUpdateCac (
          t et d = s ceT et d,
          t etDeta ls = So (s ceT etDeta ls)
        )

        s ceT etEnt  esMapFut.map { s ceT etEnt  esMap =>
          val edge = UserT etEnt yEdge(
            s ceUser = userT etEngage nt.engageUser d,
            targetT et = s ceT et d,
            act on = Act on.Ret et,
             tadata = So (t et d), //  tadata  s t  t et d
            card nfo = So (s ceT etDeta ls.card nfo.toByte),
            ent  esMap = s ceT etEnt  esMap,
            t etDeta ls = So (s ceT etDeta ls)
          )
          numRet etEdgesCounter. ncr()
          Seq(edge)
        }
      }.getOrElse(Future.N l)
  }

  /**
   * Bu ld edges for a Quote event. Quote t et em s 2 edges:
   * 1. A quote soc al proof: author -> Quote -> S ceT et d
   * 2. A t et creat on edge: author -> T et -> QuoteT et d
   */
  pr vate def bu ldQuoteEdges(
    event: T etCreateEventDeta ls
  ): Future[Seq[UserT etEnt yEdge]] = {
    val userT etEngage nt = event.userT etEngage nt
    val t et d = userT etEngage nt.t et d
    val author d = userT etEngage nt.engageUser d

    // do not assoc ate ent  es  n quote t et to t  s ce t et,
    // but assoc ate ent  es to quote t et  n t et creat on event
    val quoteT etEdgeFut = event.s ceT etDeta ls
      .map { s ceT etDeta ls =>
        val s ceT et d = s ceT etDeta ls.t et. d //  d of t  t et be ng quoted
        val s ceT etEnt  esMapFut = userT etEnt yEdgeBu lder.getEnt  esMapAndUpdateCac (
          t et d = s ceT et d,
          t etDeta ls = event.s ceT etDeta ls
        )

        s ceT etEnt  esMapFut.map { s ceT etEnt  esMap =>
          val edge = UserT etEnt yEdge(
            s ceUser = author d,
            targetT et = s ceT et d,
            act on = Act on.Quote,
             tadata = So (t et d), //  tadata  s t et d
            card nfo = So (s ceT etDeta ls.card nfo.toByte), // card nfo of t  s ce t et
            ent  esMap = s ceT etEnt  esMap,
            t etDeta ls = So (s ceT etDeta ls)
          )
          numQuoteEdgesCounter. ncr()
          Seq(edge)
        }
      }.getOrElse(Future.N l)

    val t etCreat onEdgeFut = getAndUpdateLastT etCreat onT  (
      author d = author d,
      t et d = t et d,
      t etType = T etType.Quote
    ).map { lastT etT   =>
      val  tadata = lastT etT  
      val card nfo = userT etEngage nt.t etDeta ls.map(_.card nfo.toByte)
      val edge = UserT etEnt yEdge(
        s ceUser = author d,
        targetT et = t et d,
        act on = Act on.T et,
         tadata =  tadata,
        card nfo = card nfo,
        ent  esMap = None,
        t etDeta ls = userT etEngage nt.t etDeta ls
      )
      numT etEdgesCounter. ncr()
      Seq(edge)
    }

    Future.jo n(quoteT etEdgeFut, t etCreat onEdgeFut).map {
      case (quoteEdge, creat onEdge) =>
        quoteEdge ++ creat onEdge
    }
  }

  /**
   * Bu ld edges for a T et event. A T et em s 3 tyes edges:
   * 1. A t et creat on edge: author -> T et -> T et d
   * 2.  s nt oned edges:  nt onedUser d ->  s nt oned -> T et d
   * 3.  s d atagged edges:  d ataggedUser d ->  s d atagged -> T et d
   */
  pr vate def bu ldT etEdges(event: T etCreateEventDeta ls): Future[Seq[UserT etEnt yEdge]] = {
    val userT etEngage nt = event.userT etEngage nt
    val t etDeta ls = userT etEngage nt.t etDeta ls
    val t et d = userT etEngage nt.t et d
    val author d = userT etEngage nt.engageUser d

    val card nfo = t etDeta ls.map(_.card nfo.toByte)

    val ent  esMapFut = userT etEnt yEdgeBu lder.getEnt  esMapAndUpdateCac (
      t et d = t et d,
      t etDeta ls = t etDeta ls
    )

    val lastT etT  Fut = getAndUpdateLastT etCreat onT  (
      author d = author d,
      t et d = t et d,
      t etType = T etType.T et
    )

    Future.jo n(ent  esMapFut, lastT etT  Fut).map {
      case (ent  esMap, lastT etT  ) =>
        val t etCreat onEdge = UserT etEnt yEdge(
          s ceUser = author d,
          targetT et = t et d,
          act on = Act on.T et,
           tadata = lastT etT  ,
          card nfo = card nfo,
          ent  esMap = ent  esMap,
          t etDeta ls = userT etEngage nt.t etDeta ls
        )
        numT etEdgesCounter. ncr()

        val  s nt onedEdges = event.val d nt onUser ds
          .map(_.map {  nt onedUser d =>
            UserT etEnt yEdge(
              s ceUser =  nt onedUser d,
              targetT et = t et d,
              act on = Act on. s nt oned,
               tadata = So (t et d),
              card nfo = card nfo,
              ent  esMap = ent  esMap,
              t etDeta ls = userT etEngage nt.t etDeta ls
            )
          }).getOrElse(N l)
        num s nt onedEdgesCounter. ncr( s nt onedEdges.s ze)

        val  s d ataggedEdges = event.val d d atagUser ds
          .map(_.map {  d ataggedUser d =>
            UserT etEnt yEdge(
              s ceUser =  d ataggedUser d,
              targetT et = t et d,
              act on = Act on. s d aTagged,
               tadata = So (t et d),
              card nfo = card nfo,
              ent  esMap = ent  esMap,
              t etDeta ls = userT etEngage nt.t etDeta ls
            )
          }).getOrElse(N l)
        num s d ataggedEdgesCounter. ncr( s d ataggedEdges.s ze)

        Seq(t etCreat onEdge) ++  s nt onedEdges ++  s d ataggedEdges
    }
  }

  /**
   * For a g ven user, read t  user's last t   t eted from t  MH store, and
   * wr e t  new t et t    nto t  MH store before return ng.
   * Note t  funct on  s async, so t  MH wr e operat ons w ll cont nue to execute on  s own.
   * T  m ght create a read/wr e race cond  on, but  's expected.
   */
  pr vate def getAndUpdateLastT etCreat onT  (
    author d: Long,
    t et d: Long,
    t etType: T etType
  ): Future[Opt on[Long]] = {
    val newT et nfo = RecosUserT et nfo(
      author d,
      t et d,
      t etType,
      SnowflakeUt ls.t etCreat onT  (t et d).map(_. nM ll s).getOrElse(T  .now. nM ll s)
    )

    t etCreat onStore
      .get(author d)
      .map(_.map { prev ousT et nfoSeq =>
        val lastT etT   = prev ousT et nfoSeq
          .f lter( nfo =>  nfo.t etType == T etType.T et ||  nfo.t etType == T etType.Quote)
          .map(_.t etT  stamp)
          .sortBy(-_)
          . adOpt on // Fetch t  latest t   user T eted or Quoted
          .getOrElse(
            T  .Bottom. nM ll s
          ) // Last t et t   never recorded  n MH, default to oldest po nt  n t  

         f (lastT etT   == T  .Bottom. nM ll s) lastT etT  Not nMh. ncr()
        lastT etT  
      })
      .ensure {
        t etCreat onStore
          .put(author d, newT et nfo)
          .onSuccess(_ => t etCreat onStore nserts. ncr())
          .onFa lure { e =>
            statsRece ver.counter("wr e_fa led_w h_ex:" + e.getClass.getNa ). ncr()
          }
      }
  }

  overr de def bu ldEdges(event: T etCreateEventDeta ls): Future[Seq[UserT etEnt yEdge]] = {
    val userT etEngage nt = event.userT etEngage nt
    userT etEngage nt.act on match {
      case Act on.Reply =>
        bu ldReplyEdge(event)
      case Act on.Ret et =>
        bu ldRet etEdge(event)
      case Act on.T et =>
        bu ldT etEdges(event)
      case Act on.Quote =>
        bu ldQuoteEdges(event)
      case _ =>
        num nval dAct onCounter. ncr()
        Future.N l
    }

  }

  overr de def f lterEdges(
    event: T etCreateEventDeta ls,
    edges: Seq[UserT etEnt yEdge]
  ): Future[Seq[UserT etEnt yEdge]] = {
    Future(edges) // No f lter ng for now. Add more  f needed
  }
}
