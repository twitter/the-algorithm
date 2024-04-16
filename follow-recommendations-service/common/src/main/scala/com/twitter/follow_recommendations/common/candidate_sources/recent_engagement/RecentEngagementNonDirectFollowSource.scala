package com.tw ter.follow_recom ndat ons.common.cand date_s ces.recent_engage nt

 mport com.tw ter.follow_recom ndat ons.common.cl ents.real_t  _real_graph.RealT  RealGraphCl ent
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter. rm .model.Algor hm
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.st ch.St ch
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class RecentEngage ntNonD rectFollowS ce @ nject() (
  realT  RealGraphCl ent: RealT  RealGraphCl ent)
    extends Cand dateS ce[Long, Cand dateUser] {

  val  dent f er: Cand dateS ce dent f er =
    RecentEngage ntNonD rectFollowS ce. dent f er

  /**
   * Generate a l st of cand dates for t  target us ng Realt  GraphCl ent
   * and RecentEngage ntStore.
   */
  overr de def apply(targetUser d: Long): St ch[Seq[Cand dateUser]] = {
    realT  RealGraphCl ent
      .getUsersRecentlyEngagedW h(
        user d = targetUser d,
        engage ntScoreMap = RealT  RealGraphCl ent.Engage ntScoreMap,
         ncludeD rectFollowCand dates = false,
         ncludeNonD rectFollowCand dates = true
      )
      .map(_.map(_.w hCand dateS ce( dent f er)).sortBy(-_.score.getOrElse(0.0)))
  }
}

object RecentEngage ntNonD rectFollowS ce {
  val  dent f er = Cand dateS ce dent f er(Algor hm.RecentEngage ntNonD rectFollow.toStr ng)
}
