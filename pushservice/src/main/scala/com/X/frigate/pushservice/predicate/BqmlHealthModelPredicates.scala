package com.X.frigate.pushservice.predicate

import com.X.abuse.detection.scoring.thriftscala.TweetScoringRequest
import com.X.abuse.detection.scoring.thriftscala.TweetScoringResponse
import com.X.finagle.stats.StatsReceiver
import com.X.frigate.common.base._
import com.X.frigate.common.rec_types.RecTypes
import com.X.frigate.pushservice.model.PushTypes.PushCandidate
import com.X.frigate.pushservice.ml.HealthFeatureGetter
import com.X.frigate.pushservice.params.PushFeatureSwitchParams
import com.X.hermit.predicate.NamedPredicate
import com.X.hermit.predicate.Predicate
import com.X.frigate.pushservice.ml.PushMLModelScorer
import com.X.frigate.pushservice.params.PushMLModel
import com.X.util.Future
import com.X.frigate.pushservice.util.CandidateUtil
import com.X.frigate.thriftscala.UserMediaRepresentation
import com.X.hss.api.thriftscala.UserHealthSignalResponse
import com.X.storehaus.ReadableStore

object BqmlHealthModelPredicates {

  def healthModelOonPredicate(
    bqmlHealthModelScorer: PushMLModelScorer,
    producerMediaRepresentationStore: ReadableStore[Long, UserMediaRepresentation],
    userHealthScoreStore: ReadableStore[Long, UserHealthSignalResponse],
    tweetHealthScoreStore: ReadableStore[TweetScoringRequest, TweetScoringResponse]
  )(
    implicit stats: StatsReceiver
  ): NamedPredicate[
    PushCandidate with TweetCandidate with RecommendationType with TweetAuthor
  ] = {
    val name = "bqml_health_model_based_predicate"
    val scopedStatsReceiver = stats.scope(name)

    val allCandidatesCounter = scopedStatsReceiver.counter("all_candidates")
    val oonCandidatesCounter = scopedStatsReceiver.counter("oon_candidates")
    val filteredOonCandidatesCounter =
      scopedStatsReceiver.counter("filtered_oon_candidates")
    val emptyScoreCandidatesCounter = scopedStatsReceiver.counter("empty_score_candidates")
    val healthScoreStat = scopedStatsReceiver.stat("health_model_dist")

    Predicate
      .fromAsync { candidate: PushCandidate with TweetCandidate with RecommendationType =>
        val target = candidate.target
        val isOonCandidate = RecTypes.isOutOfNetworkTweetRecType(candidate.commonRecType) ||
          RecTypes.outOfNetworkTopicTweetTypes.contains(candidate.commonRecType)

        lazy val enableBqmlHealthModelPredicateParam =
          target.params(PushFeatureSwitchParams.EnableBqmlHealthModelPredicateParam)
        lazy val enableBqmlHealthModelPredictionForInNetworkCandidates =
          target.params(
            PushFeatureSwitchParams.EnableBqmlHealthModelPredictionForInNetworkCandidatesParam)
        lazy val bqmlHealthModelPredicateFilterThresholdParam =
          target.params(PushFeatureSwitchParams.BqmlHealthModelPredicateFilterThresholdParam)
        lazy val healthModelId = target.params(PushFeatureSwitchParams.BqmlHealthModelTypeParam)
        lazy val enableBqmlHealthModelScoreHistogramParam =
          target.params(PushFeatureSwitchParams.EnableBqmlHealthModelScoreHistogramParam)
        val healthModelScoreFeature = "bqml_health_model_score"

        val histogramBinSize = 0.05
        lazy val healthCandidateScoreHistogramCounters =
          bqmlHealthModelScorer.getScoreHistogramCounters(
            scopedStatsReceiver,
            "health_score_histogram",
            histogramBinSize)

        candidate match {
          case candidate: PushCandidate with TweetAuthor with TweetAuthorDetails
              if enableBqmlHealthModelPredicateParam && (isOonCandidate || enableBqmlHealthModelPredictionForInNetworkCandidates) =>
            HealthFeatureGetter
              .getFeatures(
                candidate,
                producerMediaRepresentationStore,
                userHealthScoreStore,
                Some(tweetHealthScoreStore))
              .flatMap { healthFeatures =>
                allCandidatesCounter.incr()
                candidate.mergeFeatures(healthFeatures)

                val healthModelScoreFutOpt =
                  if (candidate.numericFeatures.contains(healthModelScoreFeature)) {
                    Future.value(candidate.numericFeatures.get(healthModelScoreFeature))
                  } else
                    bqmlHealthModelScorer.singlePredicationForModelVersion(
                      healthModelId,
                      candidate
                    )

                candidate.populateQualityModelScore(
                  PushMLModel.HealthNsfwProbability,
                  healthModelId,
                  healthModelScoreFutOpt
                )

                healthModelScoreFutOpt.map {
                  case Some(healthModelScore) =>
                    healthScoreStat.add((healthModelScore * 10000).toFloat)
                    if (enableBqmlHealthModelScoreHistogramParam) {
                      healthCandidateScoreHistogramCounters(
                        math.ceil(healthModelScore / histogramBinSize).toInt).incr()
                    }

                    if (CandidateUtil.shouldApplyHealthQualityFilters(
                        candidate) && isOonCandidate) {
                      oonCandidatesCounter.incr()
                      val threshold = bqmlHealthModelPredicateFilterThresholdParam
                      candidate.cachePredicateInfo(
                        name,
                        healthModelScore,
                        threshold,
                        healthModelScore > threshold)
                      if (healthModelScore > threshold) {
                        filteredOonCandidatesCounter.incr()
                        false
                      } else true
                    } else true
                  case _ =>
                    emptyScoreCandidatesCounter.incr()
                    true
                }
              }
          case _ => Future.True
        }
      }
      .withStats(stats.scope(name))
      .withName(name)
  }
}
