package com.tw ter.follow_recom ndat ons.common.cl ents.g zmoduck

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.base.StatsUt l
 mport com.tw ter.g zmoduck.thr ftscala.LookupContext
 mport com.tw ter.g zmoduck.thr ftscala.Perspect veEdge
 mport com.tw ter.g zmoduck.thr ftscala.QueryF elds
 mport com.tw ter.st ch.St ch
 mport com.tw ter.st ch.g zmoduck.G zmoduck
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class G zmoduckCl ent @ nject() (g zmoduckSt chCl ent: G zmoduck, statsRece ver: StatsRece ver) {
  val stats = statsRece ver.scope("g zmoduck_cl ent")
  val getBy dStats = stats.scope("get_by_ d")
  val getUserBy d = stats.scope("get_user_by_ d")

  def  sProtected(user d: Long): St ch[Boolean] = {
    // get latency  tr cs w h StatsUt l.prof leSt ch w n call ng .getBy d
    val response = StatsUt l.prof leSt ch(
      g zmoduckSt chCl ent.getBy d(user d, Set(QueryF elds.Safety)),
      getBy dStats
    )
    response.map { result =>
      result.user.flatMap(_.safety).map(_. sProtected).getOrElse(true)
    }
  }

  def getUserNa (user d: Long, forUser d: Long): St ch[Opt on[Str ng]] = {
    val queryF elds = G zmoduckCl ent.GetUserBy dUserNa QueryF elds
    val lookupContext = LookupContext(
      forUser d = So (forUser d),
      perspect veEdges = So (G zmoduckCl ent.DefaultPerspect veEdges)
    )
    // get latency  tr cs w h StatsUt l.prof leSt ch w n call ng .getUserBy d
    val response = StatsUt l.prof leSt ch(
      g zmoduckSt chCl ent.getUserBy d(user d, queryF elds, lookupContext),
      getUserBy d
    )
    response.map(_.prof le.map(_.na ))
  }
}

object G zmoduckCl ent {
  // S m lar to G zmoduckUserRepos ory.DefaultPerspect veEdges
  val DefaultPerspect veEdges: Set[Perspect veEdge] =
    Set(
      Perspect veEdge.Block ng,
      Perspect veEdge.BlockedBy,
      Perspect veEdge.Dev ceFollow ng,
      Perspect veEdge.FollowRequestSent,
      Perspect veEdge.Follow ng,
      Perspect veEdge.Follo dBy,
      Perspect veEdge.L fel neFollow ng,
      Perspect veEdge.L fel neFollo dBy,
      Perspect veEdge.Mut ng,
      Perspect veEdge.NoRet etsFrom
    )

  // From G zmoduckUserRepos ory.DefaultQueryF elds
  val GetUserBy dQueryF elds: Set[QueryF elds] = Set(
    QueryF elds.Account,
    QueryF elds.Counts,
    QueryF elds.ExtendedProf le,
    QueryF elds.Perspect ve,
    QueryF elds.Prof le,
    QueryF elds.Prof leDes gn,
    QueryF elds.Prof leLocat on,
    QueryF elds.Safety,
    QueryF elds.Roles,
    QueryF elds.Takedowns,
    QueryF elds.UrlEnt  es,
    QueryF elds.D rect ssageV ew,
    QueryF elds. d aV ew
  )

  val GetUserBy dUserNa QueryF elds: Set[QueryF elds] = Set(
    QueryF elds.Prof le
  )
}
