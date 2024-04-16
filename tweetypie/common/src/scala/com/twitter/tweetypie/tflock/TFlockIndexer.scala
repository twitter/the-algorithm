/** Copyr ght 2010 Tw ter,  nc. */
package com.tw ter.t etyp e
package tflock

 mport com.tw ter.f nagle.stats.Counter
 mport com.tw ter.flockdb.cl ent._
 mport com.tw ter.flockdb.cl ent.thr ftscala.Pr or y
 mport com.tw ter.snowflake. d.Snowflake d
 mport com.tw ter.t etyp e.serverut l.StoredCard
 mport com.tw ter.t etyp e.thr ftscala._
 mport com.tw ter.ut l.Future
 mport scala.collect on.mutable.L stBuffer

object TFlock ndexer {

  /**
   * Pr ntable na s for so  edge types currently def ned  n [[com.tw ter.flockdb.cl ent]].
   * Used to def ned stats counters for add ng edges.
   */
  val graphNa s: Map[ nt, Str ng] =
    Map(
      CardT etsGraph. d -> "card_t ets",
      Conversat onGraph. d -> "conversat on",
      D rectedAtUser dGraph. d -> "d rected_at_user_ d",
       nv edUsersGraph. d -> " nv ed_users",
       d aT  l neGraph. d -> " d a_t  l ne",
       nt onsGraph. d -> " nt ons",
      NarrowcastSentT etsGraph. d -> "narrowcast_sent_t ets",
      NullcastedT etsGraph. d -> "nullcasted_t ets",
      QuotersGraph. d -> "quoters",
      QuotesGraph. d -> "quotes",
      QuoteT ets ndexGraph. d -> "quote_t ets_ ndex",
      Repl esToT etsGraph. d -> "repl es_to_t ets",
      Ret etsBy Graph. d -> "ret ets_by_ ",
      Ret etsGraph. d -> "ret ets",
      Ret etsOf Graph. d -> "ret ets_of_ ",
      Ret etS ceGraph. d -> "ret et_s ce",
      T etsRet etedGraph. d -> "t ets_ret eted",
      UserT  l neGraph. d -> "user_t  l ne",
      CreatorSubscr pt onT  l neGraph. d -> "creator_subscr pt on_t  l ne",
      CreatorSubscr pt on d aT  l neGraph. d -> "creator_subscr pt on_ mage_t  l ne",
    )

  /**
   * On edge delet on, edges are e  r arch ved permanently or reta ned for 3 months, based on
   * t  retent on pol cy  n t  above confluence page.
   *
   * T se two retent on pol c es correspond to t  two delet on techn ques: arch ve and remove.
   *   call removeEdges for edges w h a short retent on pol cy and arch veEdges for edges w h
   * a permanent retent on pol cy.
   */
  val graphsW hRemovedEdges: Seq[ nt] =
    Seq(
      CardT etsGraph. d,
      CuratedT  l neGraph. d,
      CuratedT etsGraph. d,
      D rectedAtUser dGraph. d,
       d aT  l neGraph. d,
      MutedConversat onsGraph. d,
      QuotersGraph. d,
      QuotesGraph. d,
      QuoteT ets ndexGraph. d,
      ReportedT etsGraph. d,
      Ret etsOf Graph. d,
      Ret etS ceGraph. d,
      SoftL kesGraph. d,
      T etsRet etedGraph. d,
      CreatorSubscr pt onT  l neGraph. d,
      CreatorSubscr pt on d aT  l neGraph. d,
    )

  /**
   * T se edges should be left  n place w n bounced t ets are deleted.
   * T se edges are removed dur ng hard delet on.
   *
   * T   s done so external teams (t  l nes) can execute on t se edges for
   * tombstone feature.
   */
  val bounceDeleteGraph ds: Set[ nt] =
    Set(
      UserT  l neGraph. d,
      Conversat onGraph. d
    )

  def makeCounters(stats: StatsRece ver, operat on: Str ng): Map[ nt, Counter] = {
    TFlock ndexer.graphNa s
      .mapValues(stats.scope(_).counter(operat on))
      .w hDefaultValue(stats.scope("unknown").counter(operat on))
  }
}

/**
 * @param background ndex ngPr or y spec f es t  queue to use for
 *   background  ndex ng operat ons. T   s useful for mak ng t 
 *   effects of background  ndex ng operat ons (such as delet ng edges
 *   for deleted T ets) ava lable sooner  n test ng scenar os
 *   (end-to-end tests or develop nt  nstances).    s set to
 *   Pr or y.Low  n product on to reduce t  load on h gh pr or y
 *   queues that   use for prom nently user-v s ble operat ons.
 */
class TFlock ndexer(
  tflock: TFlockCl ent,
  has d a: T et => Boolean,
  background ndex ngPr or y: Pr or y,
  stats: StatsRece ver)
    extends T et ndexer {
  pr vate[t ] val FutureN l = Future.N l

  pr vate[t ] val arch veCounters = TFlock ndexer.makeCounters(stats, "arch ve")
  pr vate[t ] val removeCounters = TFlock ndexer.makeCounters(stats, "remove")
  pr vate[t ] val  nsertCounters = TFlock ndexer.makeCounters(stats, " nsert")
  pr vate[t ] val negateCounters = TFlock ndexer.makeCounters(stats, "negate")

  pr vate[t ] val foreground ndex ngPr or y: Pr or y = Pr or y.H gh

  overr de def create ndex(t et: T et): Future[Un ] =
    createEdges(t et,  sUndelete = false)

  overr de def undelete ndex(t et: T et): Future[Un ] =
    createEdges(t et,  sUndelete = true)

  pr vate[t ] case class Part  onedEdges(
    longRetent on: Seq[ExecuteEdge[StatusGraph]] = N l,
    shortRetent on: Seq[ExecuteEdge[StatusGraph]] = N l,
    negate: Seq[ExecuteEdge[StatusGraph]] = N l,
     gnore: Seq[ExecuteEdge[StatusGraph]] = N l)

  pr vate[t ] def part  onEdgesForDelete(
    edges: Seq[ExecuteEdge[StatusGraph]],
     sBounceDelete: Boolean
  ) =
    edges.foldLeft(Part  onedEdges()) {
      // Two dependees of UserT  l neGraph edge states to sat sfy: t  l nes & safety tools.
      // T  l nes show bounce-deleted t ets as tombstones; regular deletes are not shown.
      //   -  .e. t  l ne ds = UserT  l neGraph(Normal || Negat ve)
      // Safety tools show deleted t ets to author zed  nternal rev ew agents
      //   -  .e. deleted ds = UserT  l neGraph(Removed || Negat ve)
      case (part  onedEdges, edge)  f  sBounceDelete && edge.graph d == UserT  l neGraph. d =>
        part  onedEdges.copy(negate = edge +: part  onedEdges.negate)

      case (part  onedEdges, edge)  f  sBounceDelete && edge.graph d == Conversat onGraph. d =>
        // Bounce-deleted t ets rema n rendered as tombstones  n conversat ons, so do not mod fy
        // t  Conversat onGraph edge state
        part  onedEdges.copy( gnore = edge +: part  onedEdges. gnore)

      case (part  onedEdges, edge)
           f TFlock ndexer.graphsW hRemovedEdges.conta ns(edge.graph d) =>
        part  onedEdges.copy(shortRetent on = edge +: part  onedEdges.shortRetent on)

      case (part  onedEdges, edge) =>
        part  onedEdges.copy(longRetent on = edge +: part  onedEdges.longRetent on)
    }

  overr de def delete ndex(t et: T et,  sBounceDelete: Boolean): Future[Un ] =
    for {
      edges <- getEdges(t et,  sCreate = false,  sDelete = true,  sUndelete = false)
      part  onedEdges = part  onEdgesForDelete(edges,  sBounceDelete)
      () <-
        Future
          .jo n(
            tflock
              .arch veEdges(part  onedEdges.longRetent on, background ndex ngPr or y)
              .onSuccess(_ =>
                part  onedEdges.longRetent on.foreach(e => arch veCounters(e.graph d). ncr())),
            tflock
              .removeEdges(part  onedEdges.shortRetent on, background ndex ngPr or y)
              .onSuccess(_ =>
                part  onedEdges.shortRetent on.foreach(e => removeCounters(e.graph d). ncr())),
            tflock
              .negateEdges(part  onedEdges.negate, background ndex ngPr or y)
              .onSuccess(_ =>
                part  onedEdges.negate.foreach(e => negateCounters(e.graph d). ncr()))
          )
          .un 
    } y eld ()

  /**
   * T  operat on  s called w n a user  s put  nto or taken out of
   * a state  n wh ch t  r ret ets should no longer be v s ble
   * (e.g. suspended or ROPO).
   */
  overr de def setRet etV s b l y(ret et d: T et d, setV s ble: Boolean): Future[Un ] = {
    val ret etEdge = Seq(ExecuteEdge(ret et d, Ret etsGraph, None, Reverse))

     f (setV s ble) {
      tflock
        . nsertEdges(ret etEdge, background ndex ngPr or y)
        .onSuccess(_ =>  nsertCounters(Ret etsGraph. d). ncr())
    } else {
      tflock
        .arch veEdges(ret etEdge, background ndex ngPr or y)
        .onSuccess(_ => arch veCounters(Ret etsGraph. d). ncr())
    }
  }

  pr vate[t ] def createEdges(t et: T et,  sUndelete: Boolean): Future[Un ] =
    for {
      edges <- getEdges(t et = t et,  sCreate = true,  sDelete = false,  sUndelete =  sUndelete)
      () <- tflock. nsertEdges(edges, foreground ndex ngPr or y)
    } y eld {
      // Count all t  edges  've successfully added:
      edges.foreach(e =>  nsertCounters(e.graph d). ncr())
    }

  pr vate[t ] def addRTEdges(
    t et: T et,
    share: Share,
     sCreate: Boolean,
    edges: L stBuffer[ExecuteEdge[StatusGraph]],
    futureEdges: L stBuffer[Future[Seq[ExecuteEdge[StatusGraph]]]]
  ): Un  = {

    edges += Ret etsOf Graph.edge(share.s ceUser d, t et. d)
    edges += Ret etsBy Graph.edge(getUser d(t et), t et. d)
    edges += Ret etsGraph.edge(share.s ceStatus d, t et. d)

     f ( sCreate) {
      edges += ExecuteEdge(
        s ce d = getUser d(t et),
        graph = Ret etS ceGraph,
        dest nat on ds = So (Seq(share.s ceStatus d)),
        d rect on = Forward,
        pos  on = So (Snowflake d(t et. d).t  . nM ll s)
      )
      edges.append(T etsRet etedGraph.edge(share.s ceUser d, share.s ceStatus d))
    } else {
      edges += Ret etS ceGraph.edge(getUser d(t et), share.s ceStatus d)

      //  f t   s t  last ret et   need to remove   from t  s ce user's
      // t ets ret eted graph
      futureEdges.append(
        tflock.count(Ret etsGraph.from(share.s ceStatus d)).flatMap { count =>
           f (count <= 1) {
            tflock.selectAll(Ret etsGraph.from(share.s ceStatus d)).map { t ets =>
               f (t ets.s ze <= 1)
                Seq(T etsRet etedGraph.edge(share.s ceUser d, share.s ceStatus d))
              else
                N l
            }
          } else {
            FutureN l
          }
        }
      )
    }
  }

  pr vate[t ] def addReplyEdges(
    t et: T et,
    edges: L stBuffer[ExecuteEdge[StatusGraph]]
  ): Un  = {
    getReply(t et).foreach { reply =>
      reply. nReplyToStatus d.flatMap {  nReplyToStatus d =>
        edges += Repl esToT etsGraph.edge( nReplyToStatus d, t et. d)

        // only  ndex conversat on d  f t   s a reply to anot r t et
        T etLenses.conversat on d.get(t et).map { conversat on d =>
          edges += Conversat onGraph.edge(conversat on d, t et. d)
        }
      }
    }
  }

  pr vate[t ] def addD rectedAtEdges(
    t et: T et,
    edges: L stBuffer[ExecuteEdge[StatusGraph]]
  ): Un  = {
    T etLenses.d rectedAtUser.get(t et).foreach { d rectedAtUser =>
      edges += D rectedAtUser dGraph.edge(d rectedAtUser.user d, t et. d)
    }
  }

  pr vate[t ] def add nt onEdges(
    t et: T et,
    edges: L stBuffer[ExecuteEdge[StatusGraph]]
  ): Un  = {
    get nt ons(t et)
      .flatMap(_.user d).foreach {  nt on =>
        edges +=  nt onsGraph.edge( nt on, t et. d)
      }
  }

  pr vate[t ] def addQTEdges(
    t et: T et,
    edges: L stBuffer[ExecuteEdge[StatusGraph]],
    futureEdges: L stBuffer[Future[Seq[ExecuteEdge[StatusGraph]]]],
     sCreate: Boolean
  ): Un  = {
    val user d = getUser d(t et)

    t et.quotedT et.foreach { quotedT et =>
      // Regardless of t et creates/deletes,   add t  correspond ng edges to t 
      // follow ng two graphs. Note that  're handl ng t  case for
      // t  QuotersGraph sl ghtly d fferently  n t  t et delete case.
      edges.append(QuotesGraph.edge(quotedT et.user d, t et. d))
      edges.append(QuoteT ets ndexGraph.edge(quotedT et.t et d, t et. d))
       f ( sCreate) {
        // As  nt oned above, for t et creates   go a ad and add an edge
        // to t  QuotersGraph w hout any add  onal c cks.
        edges.append(QuotersGraph.edge(quotedT et.t et d, user d))
      } else {
        // For t et deletes,   only add an edge to be deleted from t 
        // QuotersGraph  f t  t et ng user  sn't quot ng t  t et anymore
        //  .e.  f a user has quoted a t et mult ple t  s,   only delete
        // an edge from t  QuotersGraph  f t y've deleted all t  quotes,
        // ot rw se an edge should ex st by def n  on of what t  QuotersGraph
        // represents.

        // Note: T re can be a potent al edge case  re due to a race cond  on
        //  n t  follow ng scenar o.
        //  )   A quotes a t et T tw ce result ng  n t ets T1 and T2.
        //   )  T re should ex st edges  n t  QuotersGraph from T -> A and T1 <-> T, T2 <-> T  n
        //      t  QuoteT ets ndexGraph, but one of t  edges haven't been wr ten
        //      to t  QuoteT ets ndex graph  n TFlock yet.
        //    )  n t  scenar o,   shouldn't really be delet ng an edge as  're do ng below.
        // T  approach that  're tak ng below  s a "best effort" approach s m lar to what  
        // currently do for RTs.

        // F nd all t  quotes of t  quoted t et from t  quot ng user
        val quotesFromQuot ngUser = QuoteT ets ndexGraph
          .from(quotedT et.t et d)
          . ntersect(UserT  l neGraph.from(user d))
        futureEdges.append(
          tflock
            .count(quotesFromQuot ngUser).flatMap { count =>
              //  f t   s t  last quote of t  quoted t et from t  quot ng user,
              //   go a ad and delete t  edge from t  QuotersGraph.
               f (count <= 1) {
                tflock.selectAll(quotesFromQuot ngUser).map { t ets =>
                   f (t ets.s ze <= 1) {
                    Seq(QuotersGraph.edge(quotedT et.t et d, user d))
                  } else {
                    N l
                  }
                }
              } else {
                FutureN l
              }
            }
        )
      }
    }
  }

  pr vate[t ] def addCardEdges(
    t et: T et,
    edges: L stBuffer[ExecuteEdge[StatusGraph]]
  ): Un  = {
    // Note that   are  ndex ng only t  TOO "stored" cards
    // (cardUr =card://<card d>). Rest of t  cards are  gnored  re.
    t et.cardReference
      .collect {
        case StoredCard( d) =>
          edges.append(CardT etsGraph.edge( d, t et. d))
      }.getOrElse(())
  }

  // Note: on undelete, t   thod restores all arch ved edges,  nclud ng those that may have
  // been arch ved pr or to t  delete. T   s  ncorrect behav or but  n pract ce rarely
  // causes problems, as undeletes are so rare.
  pr vate[t ] def addEdgesForDeleteOrUndelete(
    t et: T et,
    edges: L stBuffer[ExecuteEdge[StatusGraph]]
  ): Un  = {
    edges.appendAll(
      Seq(
         nt onsGraph.edges(t et. d, None, Reverse),
        Repl esToT etsGraph.edges(t et. d, None)
      )
    )

    // W n   delete or undelete a conversat on control root T et   want to arch ve or restore
    // all t  edges  n  nv edUsersGraph from t  T et  d.
     f (hasConversat onControl(t et) &&  sConversat onRoot(t et)) {
      edges.append( nv edUsersGraph.edges(t et. d, None))
    }
  }

  pr vate[t ] def addS mpleEdges(
    t et: T et,
    edges: L stBuffer[ExecuteEdge[StatusGraph]]
  ): Un  = {
     f (T etLenses.nullcast.get(t et)) {
      edges.append(NullcastedT etsGraph.edge(getUser d(t et), t et. d))
    } else  f (T etLenses.narrowcast.get(t et). sDef ned) {
      edges.append(NarrowcastSentT etsGraph.edge(getUser d(t et), t et. d))
    } else {
      edges.append(UserT  l neGraph.edge(getUser d(t et), t et. d))

       f (has d a(t et))
        edges.append( d aT  l neGraph.edge(getUser d(t et), t et. d))

      //  ndex root creator subscr pt on t ets.
      //  gnore repl es because those are not necessar ly v s ble to a user who subscr bes to t et author
      val  sRootT et: Boolean = t et.coreData match {
        case So (c) => c.reply. sEmpty && c.share. sEmpty
        case None => true
      }

       f (t et.exclus veT etControl. sDef ned &&  sRootT et) {
        edges.append(CreatorSubscr pt onT  l neGraph.edge(getUser d(t et), t et. d))

         f (has d a(t et))
          edges.append(CreatorSubscr pt on d aT  l neGraph.edge(getUser d(t et), t et. d))
      }
    }
  }

  /**
   *  ssues edges for each  nt on of user  n a conversat on-controlled t et. T  way  nv edUsers
   * graph accumulates complete set of  ds for @ nt on- nv ed users, by conversat on  d.
   */
  pr vate def  nv edUsersEdgesForCreate(
    t et: T et,
    edges: L stBuffer[ExecuteEdge[StatusGraph]]
  ): Un  = {
    val conversat on d: Long = getConversat on d(t et).getOrElse(t et. d)
    val  nt ons: Seq[User d] = get nt ons(t et).flatMap(_.user d)
    edges.appendAll( nt ons.map(user d =>  nv edUsersGraph.edge(conversat on d, user d)))
  }

  /**
   *  ssues edges of  nv eUsersGraph that ought to be deleted for a conversat on controlled reply.
   * T se are  nt ons of users  n t  g ven t et, only  f t  user was not  nt oned elsew re
   *  n t  conversat on. T  way for a conversat on,  nv edUsersGraph would always hold a set
   * of all users  nv ed to t  conversat on, and an edge  s removed only after t  last  nt on of
   * a user  s deleted.
   */
  pr vate def  nv edUsersEdgesForDelete(
    t et: T et,
    futureEdges: L stBuffer[Future[Seq[ExecuteEdge[StatusGraph]]]]
  ): Un  = {
    getConversat on d(t et).foreach { conversat on d: Long =>
      val  nt ons: Seq[User d] = get nt ons(t et).flatMap(_.user d)
       nt ons.foreach { user d =>
        val t et dsW h nConversat on = Conversat onGraph.from(conversat on d)
        val t et dsThat nt onUser =  nt onsGraph.from(user d)
        futureEdges.append(
          tflock
            .selectAll(
              query = t et dsThat nt onUser. ntersect(t et dsW h nConversat on),
              l m  = So (2), // Just need to know  f    s >1 or <=1, so 2 are enough.
              pageS ze = None // Prov de default, ot rw se Mock o compla ns
            ).map { t et ds: Seq[Long] =>
               f (t et ds.s ze <= 1) {
                Seq( nv edUsersGraph.edge(conversat on d, user d))
              } else {
                N l
              }
            }
        )
      }
    }
  }

  pr vate def has nv eV a nt on(t et: T et): Boolean = {
    t et.conversat onControl match {
      case So (Conversat onControl.By nv at on(controls)) =>
        controls. nv eV a nt on.getOrElse(false)
      case So (Conversat onControl.Commun y(controls)) =>
        controls. nv eV a nt on.getOrElse(false)
      case So (Conversat onControl.Follo rs(follo rs)) =>
        follo rs. nv eV a nt on.getOrElse(false)
      case _ =>
        false
    }
  }

  pr vate def hasConversat onControl(t et: T et): Boolean =
    t et.conversat onControl. sDef ned

  //  f a T et has a Conversat onControl,   must have a Conversat on d assoc ated w h   so  
  // can compare t  Conversat on d w h t  current T et  D to determ ne  f  's t  root of t 
  // conversat on. See Conversat on dHydrator for more deta ls
  pr vate def  sConversat onRoot(t et: T et): Boolean =
    getConversat on d(t et).get == t et. d

  pr vate def add nv edUsersEdges(
    t et: T et,
     sCreate: Boolean,
     sUndelete: Boolean,
    edges: L stBuffer[ExecuteEdge[StatusGraph]],
    futureEdges: L stBuffer[Future[Seq[ExecuteEdge[StatusGraph]]]]
  ): Un  = {
     f (hasConversat onControl(t et)) {
       f ( sCreate) {
         f ( sConversat onRoot(t et) && ! sUndelete) {
          // For root T ets, only add edges for or g nal creates, not for undeletes.
          // Undeletes are handled by addEdgesForDeleteOrUndelete.
           nv edUsersEdgesForCreate(t et, edges)
        }
         f (! sConversat onRoot(t et) && has nv eV a nt on(t et)) {
          // For repl es, only add edges w n t  conversat on control  s  n  nv eV a nt on mode.
           nv edUsersEdgesForCreate(t et, edges)
        }
      } else {
         f (! sConversat onRoot(t et)) {
           nv edUsersEdgesForDelete(t et, futureEdges)
        }
      }
    }
  }

  pr vate[t ] def getEdges(
    t et: T et,
     sCreate: Boolean,
     sDelete: Boolean,
     sUndelete: Boolean
  ): Future[Seq[ExecuteEdge[StatusGraph]]] = {
    val edges = L stBuffer[ExecuteEdge[StatusGraph]]()
    val futureEdges = L stBuffer[Future[Seq[ExecuteEdge[StatusGraph]]]]()

    addS mpleEdges(t et, edges)
    getShare(t et) match {
      case So (share) => addRTEdges(t et, share,  sCreate, edges, futureEdges)
      case _ =>
        add nv edUsersEdges(t et,  sCreate,  sUndelete, edges, futureEdges)
        addReplyEdges(t et, edges)
        addD rectedAtEdges(t et, edges)
        add nt onEdges(t et, edges)
        addQTEdges(t et, edges, futureEdges,  sCreate)
        addCardEdges(t et, edges)
         f ( sDelete ||  sUndelete) {
          addEdgesForDeleteOrUndelete(t et, edges)
        }
    }

    Future
      .collect(futureEdges)
      .map { moreEdges => (edges ++= moreEdges.flatten).toL st }
  }
}
