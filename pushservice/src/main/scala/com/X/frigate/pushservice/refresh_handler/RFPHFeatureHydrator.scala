package com.X.frigate.pushservice.refresh_handler

import com.X.finagle.stats.StatsReceiver
import com.X.frigate.common.base.CandidateDetails
import com.X.frigate.common.base.FeatureMap
import com.X.frigate.data_pipeline.features_common.MrRequestContextForFeatureStore
import com.X.frigate.pushservice.model.PushTypes.PushCandidate
import com.X.frigate.pushservice.ml.HydrationContextBuilder
import com.X.frigate.pushservice.params.PushParams
import com.X.frigate.pushservice.util.MrUserStateUtil
import com.X.nrel.heavyranker.FeatureHydrator
import com.X.util.Future

class RFPHFeatureHydrator(
  featureHydrator: FeatureHydrator
)(
  implicit globalStats: StatsReceiver) {

  implicit val statsReceiver: StatsReceiver =
    globalStats.scope("RefreshForPushHandler")

  //stat for feature hydration
  private val featureHydrationEnabledCounter = statsReceiver.counter("featureHydrationEnabled")
  private val mrUserStateStat = statsReceiver.scope("mr_user_state")

  private def hydrateFromRelevanceHydrator(
    candidateDetails: Seq[CandidateDetails[PushCandidate]],
    mrRequestContextForFeatureStore: MrRequestContextForFeatureStore
  ): Future[Unit] = {
    val pushCandidates = candidateDetails.map(_.candidate)
    val candidatesAndContextsFut = Future.collect(pushCandidates.map { pc =>
      val contextFut = HydrationContextBuilder.build(pc)
      contextFut.map { ctx => (pc, ctx) }
    })
    candidatesAndContextsFut.flatMap { candidatesAndContexts =>
      val contexts = candidatesAndContexts.map(_._2)
      val resultsFut = featureHydrator.hydrateCandidate(contexts, mrRequestContextForFeatureStore)
      resultsFut.map { hydrationResult =>
        candidatesAndContexts.foreach {
          case (pushCandidate, context) =>
            val resultFeatures = hydrationResult.getOrElse(context, FeatureMap())
            pushCandidate.mergeFeatures(resultFeatures)
        }
      }
    }
  }

  def candidateFeatureHydration(
    candidateDetails: Seq[CandidateDetails[PushCandidate]],
    mrRequestContextForFeatureStore: MrRequestContextForFeatureStore
  ): Future[Seq[CandidateDetails[PushCandidate]]] = {
    candidateDetails.headOption match {
      case Some(cand) =>
        val target = cand.candidate.target
        MrUserStateUtil.updateMrUserStateStats(target)(mrUserStateStat)
        if (target.params(PushParams.DisableAllRelevanceParam)) {
          Future.value(candidateDetails)
        } else {
          featureHydrationEnabledCounter.incr()
          for {
            _ <- hydrateFromRelevanceHydrator(candidateDetails, mrRequestContextForFeatureStore)
          } yield {
            candidateDetails
          }
        }
      case _ => Future.Nil
    }
  }
}
