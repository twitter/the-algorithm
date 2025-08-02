package com.twitter.frigate.pushservice.refresh_handler

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.frigate.common.base.CandidateDetails
import com.twitter.frigate.common.base.FeatureMap
import com.twitter.frigate.data_pipeline.features_common.MrRequestContetwittertForFeatureStore
import com.twitter.frigate.pushservice.model.PushTypes.PushCandidate
import com.twitter.frigate.pushservice.ml.HydrationContetwittertBuilder
import com.twitter.frigate.pushservice.params.PushParams
import com.twitter.frigate.pushservice.util.MrUserStateUtil
import com.twitter.nrel.heavyranker.FeatureHydrator
import com.twitter.util.Future

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
    mrRequestContetwittertForFeatureStore: MrRequestContetwittertForFeatureStore
  ): Future[Unit] = {
    val pushCandidates = candidateDetails.map(_.candidate)
    val candidatesAndContetwittertsFut = Future.collect(pushCandidates.map { pc =>
      val contetwittertFut = HydrationContetwittertBuilder.build(pc)
      contetwittertFut.map { cttwitter => (pc, cttwitter) }
    })
    candidatesAndContetwittertsFut.flatMap { candidatesAndContetwitterts =>
      val contetwitterts = candidatesAndContetwitterts.map(_._2)
      val resultsFut = featureHydrator.hydrateCandidate(contetwitterts, mrRequestContetwittertForFeatureStore)
      resultsFut.map { hydrationResult =>
        candidatesAndContetwitterts.foreach {
          case (pushCandidate, contetwittert) =>
            val resultFeatures = hydrationResult.getOrElse(contetwittert, FeatureMap())
            pushCandidate.mergeFeatures(resultFeatures)
        }
      }
    }
  }

  def candidateFeatureHydration(
    candidateDetails: Seq[CandidateDetails[PushCandidate]],
    mrRequestContetwittertForFeatureStore: MrRequestContetwittertForFeatureStore
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
            _ <- hydrateFromRelevanceHydrator(candidateDetails, mrRequestContetwittertForFeatureStore)
          } yield {
            candidateDetails
          }
        }
      case _ => Future.Nil
    }
  }
}
