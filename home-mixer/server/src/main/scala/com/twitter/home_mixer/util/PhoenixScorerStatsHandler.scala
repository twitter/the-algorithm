package com.twitter.home_mixer.util

import com.twitter.finagle.stats.BroadcastStatsReceiver
import com.twitter.finagle.stats.Counter
import com.twitter.finagle.stats.NullStatsReceiver
import com.twitter.finagle.stats.Stat
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.model.PhoenixPredictedScoreFeature
import com.twitter.home_mixer.model.PhoenixPredictedScoreFeature.PhoenixPredictedScoreFeatures
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.servo.util.Memoize

case class PhoenixModelStats(broadcastStatsReceiver: StatsReceiver) {
  private val StatsReadabilityMultiplier = 1000
  private val PredictedScoreStatName = f"predictedScore${StatsReadabilityMultiplier}x"
  private val MissingScoreStatName = "missingScore"
  private val ValidScoreStatName = "validScore"
  private val NullStat = NullStatsReceiver.stat("NullStat")
  private val NullCounter = NullStatsReceiver.counter("NullCounter")

  val failuresStat: Stat = broadcastStatsReceiver.stat("failures")
  val responsesStat: Stat = broadcastStatsReceiver.stat("responses")
  val invalidResponsesCounter: Counter = broadcastStatsReceiver.counter("invalidResponses")
  val scoreStat: Stat = broadcastStatsReceiver.stat(f"score${StatsReadabilityMultiplier}x")
  val negativeFilterCounter: Counter = broadcastStatsReceiver.counter("negativeFiltered")

  private val predictedScoreStats: Map[PhoenixPredictedScoreFeature, Stat] =
    PhoenixPredictedScoreFeatures.map { scoreFeature =>
      (scoreFeature, broadcastStatsReceiver.stat(scoreFeature.featureName, PredictedScoreStatName))
    }.toMap

  private val validScoreCounters: Map[PhoenixPredictedScoreFeature, Counter] =
    PhoenixPredictedScoreFeatures.map { scoreFeature =>
      (scoreFeature, broadcastStatsReceiver.counter(scoreFeature.featureName, ValidScoreStatName))
    }.toMap

  private val missingScoreCounters: Map[PhoenixPredictedScoreFeature, Counter] =
    PhoenixPredictedScoreFeatures.map { scoreFeature =>
      (scoreFeature, broadcastStatsReceiver.counter(scoreFeature.featureName, MissingScoreStatName))
    }.toMap

  def getPredictedScoreStat(scoreFeature: PhoenixPredictedScoreFeature): Stat =
    predictedScoreStats.getOrElse(scoreFeature, NullStat)

  def getValidScoreCounter(scoreFeature: PhoenixPredictedScoreFeature): Counter =
    validScoreCounters.getOrElse(scoreFeature, NullCounter)

  def getMissingScoreCounter(scoreFeature: PhoenixPredictedScoreFeature): Counter =
    missingScoreCounters.getOrElse(scoreFeature, NullCounter)

  def trackPredictedScoreStats(
    predictedScoreFeature: PhoenixPredictedScoreFeature,
    predictedScoreOpt: Option[Double]
  ): Unit = {
    predictedScoreOpt match {
      case Some(predictedScore) =>
        getPredictedScoreStat(predictedScoreFeature)
          .add((predictedScore * StatsReadabilityMultiplier).toFloat)
        getValidScoreCounter(predictedScoreFeature).incr()
      case None =>
        getMissingScoreCounter(predictedScoreFeature).incr()
    }
  }
}

class PhoenixScorerStatsHandler(statsReceiver: StatsReceiver, scope: String) {
  private val scopedStatsReceiver = statsReceiver.scope(scope)

  // Memoize stats object so they are not created per request
  private val statsPerModel = Memoize[(String, String, String), PhoenixModelStats] {
    case (product, clientId, cluster) =>
      // Collect stats overall, per product, and per client
      val broadcastStatsReceiver: StatsReceiver = BroadcastStatsReceiver(
        Seq(
          scopedStatsReceiver,
          scopedStatsReceiver.scope(product),
          scopedStatsReceiver.scope(product).scope(cluster),
          scopedStatsReceiver.scope(clientId).scope(product)
        )
      )
      PhoenixModelStats(broadcastStatsReceiver)
  }

  def getModelStats(query: PipelineQuery, cluster: String): PhoenixModelStats = {
    val clientId = query.clientContext.appId.getOrElse(0L).toString
    statsPerModel((query.product.identifier.toString, clientId, cluster))
  }
}
