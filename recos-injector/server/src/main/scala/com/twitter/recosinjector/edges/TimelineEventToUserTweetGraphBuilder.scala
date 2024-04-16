package com.tw ter.recos njector.edges

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.recos.ut l.Act on
 mport com.tw ter.recos njector.ut l.T etFavor eEventDeta ls
 mport com.tw ter.ut l.Future

class T  l neEventToUserT etGraphBu lder(
  userT etEnt yEdgeBu lder: UserT etEnt yEdgeBu lder
)(
  overr de  mpl c  val statsRece ver: StatsRece ver)
    extends EventTo ssageBu lder[T etFavor eEventDeta ls, UserT etEnt yEdge] {

  overr de def shouldProcessEvent(event: T etFavor eEventDeta ls): Future[Boolean] = {
    Future(true)
  }

  overr de def bu ldEdges(deta ls: T etFavor eEventDeta ls): Future[Seq[UserT etEnt yEdge]] = {
    val engage nt = deta ls.userT etEngage nt

    engage nt.act on match {
      case Act on.Favor e =>
        val t etDeta ls = engage nt.t etDeta ls

        val ent  esMapFut = userT etEnt yEdgeBu lder.getEnt  esMapAndUpdateCac (
          t et d = engage nt.t et d,
          t etDeta ls = t etDeta ls
        )

        ent  esMapFut
          .map { ent  esMap =>
            UserT etEnt yEdge(
              s ceUser = engage nt.engageUser d,
              targetT et = engage nt.t et d,
              act on = engage nt.act on,
               tadata = engage nt.engage ntT  M ll s,
              card nfo = engage nt.t etDeta ls.map(_.card nfo.toByte),
              ent  esMap = ent  esMap,
              t etDeta ls = t etDeta ls
            )
          }
          .map(Seq(_))

      case _ => Future.N l
    }
  }

  overr de def f lterEdges(
    event: T etFavor eEventDeta ls,
    edges: Seq[UserT etEnt yEdge]
  ): Future[Seq[UserT etEnt yEdge]] = {
    Future(edges)
  }
}
