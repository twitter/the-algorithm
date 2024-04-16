package com.tw ter.follow_recom ndat ons.common.cand date_s ces.s ms_expans on

 mport com.google. nject.S ngleton
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.s ms.Sw ch ngS msS ce
 mport com.tw ter.follow_recom ndat ons.common.cl ents.real_t  _real_graph.RealT  RealGraphCl ent
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter. rm .model.Algor hm
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.product_m xer.core.model.marshall ng.request.HasCl entContext
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.conf gap .HasParams

 mport javax. nject. nject

@S ngleton
class RecentStrongEngage ntD rectFollowS m larUsersS ce @ nject() (
  realT  RealGraphCl ent: RealT  RealGraphCl ent,
  sw ch ngS msS ce: Sw ch ngS msS ce)
    extends S msExpans onBasedCand dateS ce[HasCl entContext w h HasParams](
      sw ch ngS msS ce) {

  val  dent f er = RecentStrongEngage ntD rectFollowS m larUsersS ce. dent f er

  overr de def f rstDegreeNodes(
    request: HasCl entContext w h HasParams
  ): St ch[Seq[Cand dateUser]] = request.getOpt onalUser d
    .map { user d =>
      realT  RealGraphCl ent
        .getUsersRecentlyEngagedW h(
          user d,
          RealT  RealGraphCl ent.StrongEngage ntScoreMap,
           ncludeD rectFollowCand dates = true,
           ncludeNonD rectFollowCand dates = false
        ).map(_.take(RecentStrongEngage ntD rectFollowS m larUsersS ce.MaxF rstDegreeNodes))
    }.getOrElse(St ch.N l)

  overr de def maxSecondaryDegreeNodes(request: HasCl entContext w h HasParams):  nt =  nt.MaxValue

  overr de def maxResults(request: HasCl entContext w h HasParams):  nt =
    RecentStrongEngage ntD rectFollowS m larUsersS ce.MaxResults

  overr de def scoreCand date(s ceScore: Double, s m larToScore: Double): Double = {
    s ceScore * s m larToScore
  }

  overr de def cal brateD v sor(req: HasCl entContext w h HasParams): Double = 1.0d
}

object RecentStrongEngage ntD rectFollowS m larUsersS ce {
  val  dent f er = Cand dateS ce dent f er(Algor hm.RecentStrongEngage ntS m larUser.toStr ng)
  val MaxF rstDegreeNodes = 10
  val MaxResults = 200
}
