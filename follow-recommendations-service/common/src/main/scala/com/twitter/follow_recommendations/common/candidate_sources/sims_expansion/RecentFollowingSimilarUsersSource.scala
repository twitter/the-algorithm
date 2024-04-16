package com.tw ter.follow_recom ndat ons.common.cand date_s ces.s ms_expans on

 mport com.google. nject.S ngleton
 mport com.tw ter.follow_recom ndat ons.common.cand date_s ces.s ms.Sw ch ngS msS ce
 mport com.tw ter.follow_recom ndat ons.common.models.Cand dateUser
 mport com.tw ter.follow_recom ndat ons.common.models.HasRecentFollo dUser ds
 mport com.tw ter. rm .model.Algor hm
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.conf gap .HasParams
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.cl ents.soc algraph.Soc alGraphCl ent
 mport com.tw ter.product_m xer.core.model.marshall ng.request.HasCl entContext
 mport javax. nject. nject

object RecentFollow ngS m larUsersS ce {

  val  dent f er = Cand dateS ce dent f er(Algor hm.NewFollow ngS m larUser.toStr ng)
}

@S ngleton
class RecentFollow ngS m larUsersS ce @ nject() (
  soc alGraph: Soc alGraphCl ent,
  sw ch ngS msS ce: Sw ch ngS msS ce,
  statsRece ver: StatsRece ver)
    extends S msExpans onBasedCand dateS ce[
      HasParams w h HasRecentFollo dUser ds w h HasCl entContext
    ](sw ch ngS msS ce) {

  val  dent f er = RecentFollow ngS m larUsersS ce. dent f er
  pr vate val stats = statsRece ver.scope( dent f er.na )
  pr vate val maxResultsStats = stats.scope("max_results")
  pr vate val cal bratedScoreCounter = stats.counter("cal brated_scores_counter")

  overr de def f rstDegreeNodes(
    request: HasParams w h HasRecentFollo dUser ds w h HasCl entContext
  ): St ch[Seq[Cand dateUser]] = {
     f (request.params(RecentFollow ngS m larUsersParams.T  stamp ntegrated)) {
      val recentFollo dUser dsW hT  St ch =
        soc alGraph.getRecentFollo dUser dsW hT  (request.cl entContext.user d.get)

      recentFollo dUser dsW hT  St ch.map { results =>
        val f rst_degree_nodes = results
          .sortBy(-_.t   nMs).take(
            request.params(RecentFollow ngS m larUsersParams.MaxF rstDegreeNodes))
        val max_t  stamp = f rst_degree_nodes. ad.t   nMs
        f rst_degree_nodes.map {
          case user dW hT   =>
            Cand dateUser(
              user dW hT  .user d,
              score = So (user dW hT  .t   nMs.toDouble / max_t  stamp))
        }
      }
    } else {
      St ch.value(
        request.recentFollo dUser ds
          .getOrElse(N l).take(
            request.params(RecentFollow ngS m larUsersParams.MaxF rstDegreeNodes)).map(
            Cand dateUser(_, score = So (1.0)))
      )
    }
  }

  overr de def maxSecondaryDegreeNodes(
    req: HasParams w h HasRecentFollo dUser ds w h HasCl entContext
  ):  nt = {
    req.params(RecentFollow ngS m larUsersParams.MaxSecondaryDegreeExpans onPerNode)
  }

  overr de def maxResults(
    req: HasParams w h HasRecentFollo dUser ds w h HasCl entContext
  ):  nt = {
    val f rstDegreeNodes = req.params(RecentFollow ngS m larUsersParams.MaxF rstDegreeNodes)
    val maxResultsNum = req.params(RecentFollow ngS m larUsersParams.MaxResults)
    maxResultsStats
      .stat(
        s"RecentFollow ngS m larUsersS ce_f rstDegreeNodes_${f rstDegreeNodes}_maxResults_${maxResultsNum}")
      .add(1)
    maxResultsNum
  }

  overr de def scoreCand date(s ceScore: Double, s m larToScore: Double): Double = {
    s ceScore * s m larToScore
  }

  overr de def cal brateD v sor(
    req: HasParams w h HasRecentFollo dUser ds w h HasCl entContext
  ): Double = {
    req.params(DBV2S msExpans onParams.RecentFollow ngS m larUsersDBV2Cal brateD v sor)
  }

  overr de def cal brateScore(
    cand dateScore: Double,
    req: HasParams w h HasRecentFollo dUser ds w h HasCl entContext
  ): Double = {
    cal bratedScoreCounter. ncr()
    cand dateScore / cal brateD v sor(req)
  }
}
