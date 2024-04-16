package com.tw ter.follow_recom ndat ons.common.cand date_s ces.salsa

 mport com.tw ter.follow_recom ndat ons.common.cl ents.real_t  _real_graph.RealT  RealGraphCl ent
 mport com.tw ter. rm .model.Algor hm
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.st ch.St ch
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class RecentEngage ntD rectFollowSalsaExpans onS ce @ nject() (
  realT  RealGraphCl ent: RealT  RealGraphCl ent,
  salsaExpander: SalsaExpander)
    extends SalsaExpans onBasedCand dateS ce[Long](salsaExpander) {

  overr de val  dent f er: Cand dateS ce dent f er =
    RecentEngage ntD rectFollowSalsaExpans onS ce. dent f er

  overr de def f rstDegreeNodes(target: Long): St ch[Seq[Long]] = realT  RealGraphCl ent
    .getUsersRecentlyEngagedW h(
      target,
      RealT  RealGraphCl ent.Engage ntScoreMap,
       ncludeD rectFollowCand dates = true,
       ncludeNonD rectFollowCand dates = false
    ).map { recentlyFollo d =>
      recentlyFollo d
        .take(RecentEngage ntD rectFollowSalsaExpans onS ce.NumF rstDegreeNodesToRetr eve)
        .map(_. d)
    }

  overr de def maxResults(target: Long):  nt =
    RecentEngage ntD rectFollowSalsaExpans onS ce.OutputS ze
}

object RecentEngage ntD rectFollowSalsaExpans onS ce {
  val  dent f er: Cand dateS ce dent f er = Cand dateS ce dent f er(
    Algor hm.RecentEngage ntSarusOcCur.toStr ng)
  val NumF rstDegreeNodesToRetr eve = 10
  val OutputS ze = 200
}
