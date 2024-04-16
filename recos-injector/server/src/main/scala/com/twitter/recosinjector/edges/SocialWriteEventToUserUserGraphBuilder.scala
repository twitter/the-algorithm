package com.tw ter.recos njector.edges

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.recos.ut l.Act on
 mport com.tw ter.soc algraph.thr ftscala.{
  Act on => Soc alGraphAct on,
  FollowGraphEvent,
  FollowType,
  Wr eEvent
}
 mport com.tw ter.ut l.Future

/**
 * Converts a Wr eEvent to UserUserGraph's  ssages,  nclud ng  nt on and  d atag  ssages
 */
class Soc alWr eEventToUserUserGraphBu lder()(overr de  mpl c  val statsRece ver: StatsRece ver)
    extends EventTo ssageBu lder[Wr eEvent, UserUserEdge] {
  pr vate val followOrFr ct onlessFollowCounter =
    statsRece ver.counter("num_follow_or_fr ct onless")
  pr vate val notFollowOrFr ct onlessFollowCounter =
    statsRece ver.counter("num_not_follow_or_fr ct onless")
  pr vate val followEdgeCounter = statsRece ver.counter("num_follow_edge")

  /**
   * For now,   are only  nterested  n Follow events
   */
  overr de def shouldProcessEvent(event: Wr eEvent): Future[Boolean] = {
    event.act on match {
      case Soc alGraphAct on.Follow | Soc alGraphAct on.Fr ct onlessFollow =>
        followOrFr ct onlessFollowCounter. ncr()
        Future(true)
      case _ =>
        notFollowOrFr ct onlessFollowCounter. ncr()
        Future(false)
    }
  }

  /**
   * Determ ne w t r a Follow event  s val d/error free.
   */
  pr vate def  sVal dFollowEvent(followEvent: FollowGraphEvent): Boolean = {
    followEvent.followType match {
      case So (FollowType.NormalFollow) | So (FollowType.Fr ct onlessFollow) =>
        followEvent.result.val dat onError. sEmpty
      case _ =>
        false
    }
  }

  overr de def bu ldEdges(event: Wr eEvent): Future[Seq[UserUserEdge]] = {
    val userUserEdges = event.follow
      .map(_.collect {
        case followEvent  f  sVal dFollowEvent(followEvent) =>
          val s ceUser d = followEvent.result.request.s ce
          val targetUser d = followEvent.result.request.target
          followEdgeCounter. ncr()
          UserUserEdge(
            s ceUser d,
            targetUser d,
            Act on.Follow,
            So (System.currentT  M ll s())
          )
      }).getOrElse(N l)
    Future(userUserEdges)
  }

  overr de def f lterEdges(
    event: Wr eEvent,
    edges: Seq[UserUserEdge]
  ): Future[Seq[UserUserEdge]] = {
    Future(edges)
  }
}
