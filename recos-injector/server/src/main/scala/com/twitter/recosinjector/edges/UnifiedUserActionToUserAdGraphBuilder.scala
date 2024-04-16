package com.tw ter.recos njector.edges

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.recos.ut l.Act on
 mport com.tw ter.recos njector.ut l.UuaEngage ntEventDeta ls
 mport com.tw ter.ut l.Future

class Un f edUserAct onToUserAdGraphBu lder(
  userT etEnt yEdgeBu lder: UserT etEnt yEdgeBu lder
)(
  overr de  mpl c  val statsRece ver: StatsRece ver)
    extends EventTo ssageBu lder[UuaEngage ntEventDeta ls, UserT etEnt yEdge] {

  overr de def shouldProcessEvent(event: UuaEngage ntEventDeta ls): Future[Boolean] = {
    event.userT etEngage nt.act on match {
      case Act on.Cl ck | Act on.V deoPlayback75 | Act on.Favor e => Future(true)
      case _ => Future(false)
    }
  }

  overr de def bu ldEdges(deta ls: UuaEngage ntEventDeta ls): Future[Seq[UserT etEnt yEdge]] = {
    val engage nt = deta ls.userT etEngage nt
    val t etDeta ls = engage nt.t etDeta ls

    Future.value(
      Seq(
        UserT etEnt yEdge(
          s ceUser = engage nt.engageUser d,
          targetT et = engage nt.t et d,
          act on = engage nt.act on,
           tadata = engage nt.engage ntT  M ll s,
          card nfo = engage nt.t etDeta ls.map(_.card nfo.toByte),
          ent  esMap = None,
          t etDeta ls = t etDeta ls
        )))
  }

  overr de def f lterEdges(
    event: UuaEngage ntEventDeta ls,
    edges: Seq[UserT etEnt yEdge]
  ): Future[Seq[UserT etEnt yEdge]] = {
    Future(edges)
  }
}
