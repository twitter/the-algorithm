package com.tw ter.recos njector.edges

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.recos.ut l.Act on
 mport com.tw ter.recos njector.ut l.T etCreateEventDeta ls
 mport com.tw ter.ut l.Future

/**
 * G ven a t et creat on event, parse for UserUserGraph edges. Spec f cally, w n a new t et  s
 * created, extract t  val d  nt oned and  d atagged users  n t  t et and create edges for t m
 */
class T etEventToUserUserGraphBu lder(
)(
  overr de  mpl c  val statsRece ver: StatsRece ver)
    extends EventTo ssageBu lder[T etCreateEventDeta ls, UserUserEdge] {
  pr vate val t etOrQuoteEventCounter = statsRece ver.counter("num_t et_or_quote_event")
  pr vate val nonT etOrQuoteEventCounter = statsRece ver.counter("num_non_t et_or_quote_event")
  pr vate val  nt onEdgeCounter = statsRece ver.counter("num_ nt on_edge")
  pr vate val  d atagEdgeCounter = statsRece ver.counter("num_ d atag_edge")

  overr de def shouldProcessEvent(event: T etCreateEventDeta ls): Future[Boolean] = {
    // For user  nteract ons, only new t ets and quotes are cons dered (no repl es or ret ets)
    event.userT etEngage nt.act on match {
      case Act on.T et | Act on.Quote =>
        t etOrQuoteEventCounter. ncr()
        Future(true)
      case _ =>
        nonT etOrQuoteEventCounter. ncr()
        Future(false)
    }
  }

  overr de def bu ldEdges(event: T etCreateEventDeta ls): Future[Seq[UserUserEdge]] = {
    val  nt onEdges = event.val d nt onUser ds
      .map(_.map {  nt onUser d =>
        UserUserEdge(
          s ceUser = event.userT etEngage nt.engageUser d,
          targetUser =  nt onUser d,
          act on = Act on. nt on,
           tadata = So (System.currentT  M ll s())
        )
      }).getOrElse(N l)

    val  d atagEdges = event.val d d atagUser ds
      .map(_.map {  d atagUser d =>
        UserUserEdge(
          s ceUser = event.userT etEngage nt.engageUser d,
          targetUser =  d atagUser d,
          act on = Act on. d aTag,
           tadata = So (System.currentT  M ll s())
        )
      }).getOrElse(N l)

     nt onEdgeCounter. ncr( nt onEdges.s ze)
     d atagEdgeCounter. ncr( d atagEdges.s ze)
    Future( nt onEdges ++  d atagEdges)
  }

  overr de def f lterEdges(
    event: T etCreateEventDeta ls,
    edges: Seq[UserUserEdge]
  ): Future[Seq[UserUserEdge]] = {
    Future(edges)
  }
}
