package com.tw ter.follow_recom ndat ons.common.cand date_s ces.stp

 mport com.tw ter.follow_recom ndat ons.common.cl ents.soc algraph.RecentEdgesQuery
 mport com.tw ter.follow_recom ndat ons.common.cl ents.soc algraph.Soc alGraphCl ent
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.models.HasRecentFollo dUser ds
 mport com.tw ter. rm .model.Algor hm
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.product_m xer.core.model.marshall ng.request.HasCl entContext
 mport com.tw ter.soc algraph.thr ftscala.Relat onsh pType
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.generated.cl ent.onboard ng.userrecs.StrongT ePred ct onFeaturesOnUserCl entColumn
 mport javax. nject.S ngleton
 mport javax. nject. nject

/**
 * Returns mutual follows.   f rst gets mutual follows from recent 100 follows and follo rs, and t n un ons t 
 * w h mutual follows from STP features dataset.
 */
@S ngleton
class MutualFollowStrongT ePred ct onS ce @ nject() (
  sgsCl ent: Soc alGraphCl ent,
  strongT ePred ct onFeaturesOnUserCl entColumn: StrongT ePred ct onFeaturesOnUserCl entColumn)
    extends Cand dateS ce[HasCl entContext w h HasRecentFollo dUser ds, Cand dateUser] {
  val  dent f er: Cand dateS ce dent f er =
    MutualFollowStrongT ePred ct onS ce. dent f er

  overr de def apply(
    target: HasCl entContext w h HasRecentFollo dUser ds
  ): St ch[Seq[Cand dateUser]] = {
    target.getOpt onalUser d match {
      case So (user d) =>
        val newFollow ngs = target.recentFollo dUser ds
          .getOrElse(N l)
          .take(MutualFollowStrongT ePred ct onS ce.NumOfRecentFollow ngs)
        val newFollo rsSt ch =
          sgsCl ent
            .getRecentEdges(RecentEdgesQuery(user d, Seq(Relat onsh pType.Follo dBy))).map(
              _.take(MutualFollowStrongT ePred ct onS ce.NumOfRecentFollo rs))
        val mutualFollowsSt ch =
          strongT ePred ct onFeaturesOnUserCl entColumn.fetc r
            .fetch(user d).map(_.v.flatMap(_.topMutualFollows.map(_.map(_.user d))).getOrElse(N l))

        St ch.jo n(newFollo rsSt ch, mutualFollowsSt ch).map {
          case (newFollo rs, mutualFollows) => {
            (newFollow ngs. ntersect(newFollo rs) ++ mutualFollows).d st nct
              .map( d => Cand dateUser( d, So (Cand dateUser.DefaultCand dateScore)))
          }
        }
      case _ => St ch.N l
    }
  }
}

object MutualFollowStrongT ePred ct onS ce {
  val  dent f er: Cand dateS ce dent f er = Cand dateS ce dent f er(
    Algor hm.MutualFollowSTP.toStr ng)
  val NumOfRecentFollow ngs = 100
  val NumOfRecentFollo rs = 100
}
