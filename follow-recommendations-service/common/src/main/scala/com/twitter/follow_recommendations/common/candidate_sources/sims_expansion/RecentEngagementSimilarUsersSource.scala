package com.tw ter.follow_recom ndat ons.common.cand date_s ces.s ms_expans on

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.s ms.Sw ch ngS msS ce
 mport com.tw ter.follow_recom ndat ons.common.cl ents.real_t  _real_graph.RealT  RealGraphCl ent
 mport com.tw ter.follow_recom ndat ons.common.models.AccountProof
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.models.Reason
 mport com.tw ter.follow_recom ndat ons.common.models.S m larToProof
 mport com.tw ter. rm .model.Algor hm
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.product_m xer.core.model.marshall ng.request.HasCl entContext
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.conf gap .HasParams

 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class RecentEngage ntS m larUsersS ce @ nject() (
  realT  RealGraphCl ent: RealT  RealGraphCl ent,
  sw ch ngS msS ce: Sw ch ngS msS ce,
  statsRece ver: StatsRece ver)
    extends S msExpans onBasedCand dateS ce[HasCl entContext w h HasParams](
      sw ch ngS msS ce) {
  overr de def maxSecondaryDegreeNodes(req: HasCl entContext w h HasParams):  nt =  nt.MaxValue

  overr de def maxResults(req: HasCl entContext w h HasParams):  nt =
    RecentEngage ntS m larUsersS ce.MaxResults

  overr de val  dent f er: Cand dateS ce dent f er = RecentEngage ntS m larUsersS ce. dent f er
  pr vate val stats = statsRece ver.scope( dent f er.na )
  pr vate val cal bratedScoreCounter = stats.counter("cal brated_scores_counter")

  overr de def scoreCand date(s ceScore: Double, s m larToScore: Double): Double = {
    s ceScore * s m larToScore
  }

  overr de def cal brateD v sor(req: HasCl entContext w h HasParams): Double = {
    req.params(DBV2S msExpans onParams.RecentEngage ntS m larUsersDBV2Cal brateD v sor)
  }

  overr de def cal brateScore(
    cand dateScore: Double,
    req: HasCl entContext w h HasParams
  ): Double = {
    cal bratedScoreCounter. ncr()
    cand dateScore / cal brateD v sor(req)
  }

  /**
   * fetch f rst degree nodes g ven request
   */
  overr de def f rstDegreeNodes(
    target: HasCl entContext w h HasParams
  ): St ch[Seq[Cand dateUser]] = {
    target.getOpt onalUser d
      .map { user d =>
        realT  RealGraphCl ent
          .getUsersRecentlyEngagedW h(
            user d,
            RealT  RealGraphCl ent.Engage ntScoreMap,
             ncludeD rectFollowCand dates = true,
             ncludeNonD rectFollowCand dates = true
          ).map(_.sortBy(-_.score.getOrElse(0.0d))
            .take(RecentEngage ntS m larUsersS ce.MaxF rstDegreeNodes))
      }.getOrElse(St ch.N l)
  }

  overr de def aggregateAndScore(
    request: HasCl entContext w h HasParams,
    f rstDegreeToSecondDegreeNodesMap: Map[Cand dateUser, Seq[S m larUser]]
  ): St ch[Seq[Cand dateUser]] = {

    val  nputNodes = f rstDegreeToSecondDegreeNodesMap.keys.map(_. d).toSet
    val aggregator = request.params(RecentEngage ntS m larUsersParams.Aggregator) match {
      case S msExpans onS ceAggregator d.Max =>
        S msExpans onBasedCand dateS ce.ScoreAggregator.Max
      case S msExpans onS ceAggregator d.Sum =>
        S msExpans onBasedCand dateS ce.ScoreAggregator.Sum
      case S msExpans onS ceAggregator d.Mult Decay =>
        S msExpans onBasedCand dateS ce.ScoreAggregator.Mult Decay
    }

    val groupedCand dates = f rstDegreeToSecondDegreeNodesMap.values.flatten
      .f lterNot(c =>  nputNodes.conta ns(c.cand date d))
      .groupBy(_.cand date d)
      .map {
        case ( d, cand dates) =>
          // D fferent aggregators for f nal score
          val f nalScore = aggregator(cand dates.map(_.score).toSeq)
          val proofs = cand dates.map(_.s m larTo).toSet

          Cand dateUser(
             d =  d,
            score = So (f nalScore),
            reason =
              So (Reason(So (AccountProof(s m larToProof = So (S m larToProof(proofs.toSeq))))))
          ).w hCand dateS ce( dent f er)
      }
      .toSeq
      .sortBy(-_.score.getOrElse(0.0d))
      .take(maxResults(request))

    St ch.value(groupedCand dates)
  }
}

object RecentEngage ntS m larUsersS ce {
  val  dent f er = Cand dateS ce dent f er(Algor hm.RecentEngage ntS m larUser.toStr ng)
  val MaxF rstDegreeNodes = 10
  val MaxResults = 200
}
