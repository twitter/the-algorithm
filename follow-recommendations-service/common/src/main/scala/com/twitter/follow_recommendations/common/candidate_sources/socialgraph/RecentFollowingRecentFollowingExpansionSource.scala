package com.tw ter.follow_recom ndat ons.common.cand date_s ces.soc algraph

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.base.TwoHopExpans onCand dateS ce
 mport com.tw ter.follow_recom ndat ons.common.cl ents.soc algraph.RecentEdgesQuery
 mport com.tw ter.follow_recom ndat ons.common.cl ents.soc algraph.Soc alGraphCl ent
 mport com.tw ter.follow_recom ndat ons.common.models.AccountProof
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.models.FollowProof
 mport com.tw ter.follow_recom ndat ons.common.models.HasRecentFollo dUser ds
 mport com.tw ter.follow_recom ndat ons.common.models.Reason
 mport com.tw ter. rm .model.Algor hm
 mport com.tw ter. nject.Logg ng
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.soc algraph.thr ftscala.Relat onsh pType
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.conf gap .HasParams
 mport javax. nject. nject
 mport javax. nject.S ngleton

/**
 * T  cand date s ce  s a two hop expans on over t  follow graph. T  cand dates returned from t  s ce  s t  users that get follo d by t  target user's recent follow ngs.   w ll call Soc alGraph `n` + 1 t  s w re `n`  s t  number of recent follow ngs of t  target user to be cons dered.
 */
@S ngleton
class RecentFollow ngRecentFollow ngExpans onS ce @ nject() (
  soc alGraphCl ent: Soc alGraphCl ent,
  statsRece ver: StatsRece ver)
    extends TwoHopExpans onCand dateS ce[
      HasParams w h HasRecentFollo dUser ds,
      Long,
      Long,
      Cand dateUser
    ]
    w h Logg ng {

  overr de val  dent f er: Cand dateS ce dent f er =
    RecentFollow ngRecentFollow ngExpans onS ce. dent f er

  val stats = statsRece ver.scope( dent f er.na )

  overr de def f rstDegreeNodes(
    target: HasParams w h HasRecentFollo dUser ds
  ): St ch[Seq[Long]] = St ch.value(
    target.recentFollo dUser ds
      .getOrElse(N l).take(
        RecentFollow ngRecentFollow ngExpans onS ce.NumF rstDegreeNodesToRetr eve)
  )

  overr de def secondaryDegreeNodes(
    target: HasParams w h HasRecentFollo dUser ds,
    node: Long
  ): St ch[Seq[Long]] = soc alGraphCl ent
    .getRecentEdgesCac d(
      RecentEdgesQuery(
        node,
        Seq(Relat onsh pType.Follow ng),
        So (RecentFollow ngRecentFollow ngExpans onS ce.NumSecondDegreeNodesToRetr eve)),
      useCac dStratoColumn =
        target.params(RecentFollow ngRecentFollow ngExpans onS ceParams.CallSgsCac dColumn)
    ).map(
      _.take(RecentFollow ngRecentFollow ngExpans onS ce.NumSecondDegreeNodesToRetr eve)).rescue {
      case except on: Except on =>
        logger.warn(
          s"${t .getClass} fa ls to retr eve second degree nodes for f rst degree node $node",
          except on)
        stats.counter("second_degree_expans on_error"). ncr()
        St ch.N l
    }

  overr de def aggregateAndScore(
    target: HasParams w h HasRecentFollo dUser ds,
    f rstDegreeToSecondDegreeNodesMap: Map[Long, Seq[Long]]
  ): St ch[Seq[Cand dateUser]] = {
    val z pped = f rstDegreeToSecondDegreeNodesMap.toSeq.flatMap {
      case (f rstDegree d, secondDegree ds) =>
        secondDegree ds.map(secondDegree d => f rstDegree d -> secondDegree d)
    }
    val cand dateAndConnect ons = z pped
      .groupBy { case (_, secondDegree d) => secondDegree d }
      .mapValues { v => v.map { case (f rstDegree d, _) => f rstDegree d } }
      .toSeq
      .sortBy { case (_, connect ons) => -connect ons.s ze }
      .map {
        case (cand date d, connect ons) =>
          Cand dateUser(
             d = cand date d,
            score = So (Cand dateUser.DefaultCand dateScore),
            reason = So (
              Reason(
                So (AccountProof(followProof = So (FollowProof(connect ons, connect ons.s ze))))))
          ).w hCand dateS ce( dent f er)
      }
    St ch.value(cand dateAndConnect ons)
  }
}

object RecentFollow ngRecentFollow ngExpans onS ce {
  val  dent f er = Cand dateS ce dent f er(Algor hm.NewFollow ngNewFollow ngExpans on.toStr ng)

  val NumF rstDegreeNodesToRetr eve = 5
  val NumSecondDegreeNodesToRetr eve = 20
}
