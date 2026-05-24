package com.twitter.home_mixer.util

import com.twitter.finagle.stats.BroadcastStatsReceiver
import com.twitter.finagle.stats.Counter
import com.twitter.finagle.stats.NullStatsReceiver
import com.twitter.finagle.stats.Stat
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.model.HomeFeatures.NaviClientConfigFeature
import com.twitter.home_mixer.model.PredictedScoreFeature
import com.twitter.home_mixer.model.PredictedScoreFeature.PredictedScoreFeatures
import com.twitter.home_mixer.param.HomeGlobalParams.Scoring.ModelIdParam
import com.twitter.home_mixer.param.HomeGlobalParams.Scoring.ModelNameParam
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.servo.util.Memoize

case class ModelStats(broadcastStatsReceiver: StatsReceiver) {
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

  private val predictedScoreStats: Map[PredictedScoreFeature, Stat] = PredictedScoreFeatures.map {
    scoreFeature =>
      (scoreFeature, broadcastStatsReceiver.stat(scoreFeature.statName, PredictedScoreStatName))
  }.toMap

  private val validScoreCounters: Map[PredictedScoreFeature, Counter] = PredictedScoreFeatures.map {
    scoreFeature =>
      (scoreFeature, broadcastStatsReceiver.counter(scoreFeature.statName, ValidScoreStatName))
  }.toMap

  private val missingScoreCounters: Map[PredictedScoreFeature, Counter] =
    PredictedScoreFeatures.map { scoreFeature =>
      (scoreFeature, broadcastStatsReceiver.counter(scoreFeature.statName, MissingScoreStatName))
    }.toMap

  def getPredictedScoreStat(scoreFeature: PredictedScoreFeature): Stat =
    predictedScoreStats.getOrElse(scoreFeature, NullStat)

  def getValidScoreCounter(scoreFeature: PredictedScoreFeature): Counter =
    validScoreCounters.getOrElse(scoreFeature, NullCounter)

  def getMissingScoreCounter(scoreFeature: PredictedScoreFeature): Counter =
    missingScoreCounters.getOrElse(scoreFeature, NullCounter)

  def trackPredictedScoreStats(
    predictedScoreFeature: PredictedScoreFeature,
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

class NaviScorerStatsHandler(statsReceiver: StatsReceiver, scope: String) {
  private val scopedStatsReceiver = statsReceiver.scope(scope)

  // Memoize stats object so they are not created per request
  private val statsPerModel = Memoize[(String, String, String, String), ModelStats] {
    case (product, modelId, modelName, clientId) =>
      // Collect stats overall, per product, per model, and per client
      val broadcastStatsReceiver: StatsReceiver = BroadcastStatsReceiver(
        Seq(
          scopedStatsReceiver,
          scopedStatsReceiver.scope(product),
          scopedStatsReceiver.scope(modelId).scope(product),
          scopedStatsReceiver.scope(modelName).scope(product),
          scopedStatsReceiver.scope(clientId).scope(product)
        )
      )
      ModelStats(broadcastStatsReceiver)
  }

  /** Retrieve ModelStats for the given query */
  def getModelStats(
    query: PipelineQuery
  ): ModelStats = {
    val modelId = query.params(ModelIdParam)
    val modelName = query.params(ModelNameParam)
    val modelNameStr = if (modelName.nonEmpty) modelName else "EMPTY_MODEL_NAME"

    val naviClientConfig =
      query.features.map(_.get(NaviClientConfigFeature)).get // Should always be present

    val clientId = query.clientContext.appId.getOrElse(0L).toString
    statsPerModel(
      (
        query.product.identifier.toString,
        modelId + naviClientConfig.clusterStr,
        modelNameStr + naviClientConfig.clusterStr,
        clientId))
  }
}
