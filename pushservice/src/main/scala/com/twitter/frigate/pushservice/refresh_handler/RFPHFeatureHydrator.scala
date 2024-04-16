package com.tw ter.fr gate.pushserv ce.refresh_handler

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base.Cand dateDeta ls
 mport com.tw ter.fr gate.common.base.FeatureMap
 mport com.tw ter.fr gate.data_p pel ne.features_common.MrRequestContextForFeatureStore
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.ml.Hydrat onContextBu lder
 mport com.tw ter.fr gate.pushserv ce.params.PushParams
 mport com.tw ter.fr gate.pushserv ce.ut l.MrUserStateUt l
 mport com.tw ter.nrel. avyranker.FeatureHydrator
 mport com.tw ter.ut l.Future

class RFPHFeatureHydrator(
  featureHydrator: FeatureHydrator
)(
   mpl c  globalStats: StatsRece ver) {

   mpl c  val statsRece ver: StatsRece ver =
    globalStats.scope("RefreshForPushHandler")

  //stat for feature hydrat on
  pr vate val featureHydrat onEnabledCounter = statsRece ver.counter("featureHydrat onEnabled")
  pr vate val mrUserStateStat = statsRece ver.scope("mr_user_state")

  pr vate def hydrateFromRelevanceHydrator(
    cand dateDeta ls: Seq[Cand dateDeta ls[PushCand date]],
    mrRequestContextForFeatureStore: MrRequestContextForFeatureStore
  ): Future[Un ] = {
    val pushCand dates = cand dateDeta ls.map(_.cand date)
    val cand datesAndContextsFut = Future.collect(pushCand dates.map { pc =>
      val contextFut = Hydrat onContextBu lder.bu ld(pc)
      contextFut.map { ctx => (pc, ctx) }
    })
    cand datesAndContextsFut.flatMap { cand datesAndContexts =>
      val contexts = cand datesAndContexts.map(_._2)
      val resultsFut = featureHydrator.hydrateCand date(contexts, mrRequestContextForFeatureStore)
      resultsFut.map { hydrat onResult =>
        cand datesAndContexts.foreach {
          case (pushCand date, context) =>
            val resultFeatures = hydrat onResult.getOrElse(context, FeatureMap())
            pushCand date. rgeFeatures(resultFeatures)
        }
      }
    }
  }

  def cand dateFeatureHydrat on(
    cand dateDeta ls: Seq[Cand dateDeta ls[PushCand date]],
    mrRequestContextForFeatureStore: MrRequestContextForFeatureStore
  ): Future[Seq[Cand dateDeta ls[PushCand date]]] = {
    cand dateDeta ls. adOpt on match {
      case So (cand) =>
        val target = cand.cand date.target
        MrUserStateUt l.updateMrUserStateStats(target)(mrUserStateStat)
         f (target.params(PushParams.D sableAllRelevanceParam)) {
          Future.value(cand dateDeta ls)
        } else {
          featureHydrat onEnabledCounter. ncr()
          for {
            _ <- hydrateFromRelevanceHydrator(cand dateDeta ls, mrRequestContextForFeatureStore)
          } y eld {
            cand dateDeta ls
          }
        }
      case _ => Future.N l
    }
  }
}
