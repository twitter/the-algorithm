package com.tw ter.recos njector.edges

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.recos.ut l.Act on
 mport com.tw ter.recos njector.ut l.UuaEngage ntEventDeta ls
 mport com.tw ter.ut l.Future

class Un f edUserAct onToUserV deoGraphBu lder(
  userT etEnt yEdgeBu lder: UserT etEnt yEdgeBu lder
)(
  overr de  mpl c  val statsRece ver: StatsRece ver)
    extends EventTo ssageBu lder[UuaEngage ntEventDeta ls, UserT etEnt yEdge] {

  pr vate val numV deoPlayback50EdgeCounter = statsRece ver.counter("num_v deo_playback50_edge")
  pr vate val numUnV deoPlayback50Counter = statsRece ver.counter("num_non_v deo_playback50_edge")

  overr de def shouldProcessEvent(event: UuaEngage ntEventDeta ls): Future[Boolean] = {
    event.userT etEngage nt.act on match {
      case Act on.V deoPlayback50 => Future(true)
      case _ => Future(false)
    }
  }

  overr de def bu ldEdges(deta ls: UuaEngage ntEventDeta ls): Future[Seq[UserT etEnt yEdge]] = {
    val engage nt = deta ls.userT etEngage nt
    val t etDeta ls = engage nt.t etDeta ls

    Future
      .value(
        UserT etEnt yEdge(
          s ceUser = engage nt.engageUser d,
          targetT et = engage nt.t et d,
          act on = engage nt.act on,
           tadata = engage nt.engage ntT  M ll s,
          card nfo = engage nt.t etDeta ls.map(_.card nfo.toByte),
          ent  esMap = None,
          t etDeta ls = t etDeta ls
        )
      ).map { edge =>
        edge match {
          case v deoPlayback50  f v deoPlayback50.act on == Act on.V deoPlayback50 =>
            numV deoPlayback50EdgeCounter. ncr()
          case _ =>
            numUnV deoPlayback50Counter. ncr()
        }
        Seq(edge)
      }
  }

  overr de def f lterEdges(
    event: UuaEngage ntEventDeta ls,
    edges: Seq[UserT etEnt yEdge]
  ): Future[Seq[UserT etEnt yEdge]] = {
    Future(edges)
  }
}
