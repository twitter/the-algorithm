package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.model.PredictedScoreFeature
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import javax.inject.Inject

/*
 * This Hydrator updates the model scoring weights with the optimizer weights.
 */

class OptimizerWeightsQueryFeatureHydrator @Inject() (
  statsReceiver: StatsReceiver)
    extends QueryFeatureHydrator[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("OptimizerWeights")

  private val EnabledPredictedScoreFeatures: Seq[PredictedScoreFeature] =
    PredictedScoreFeature.PredictedScoreFeatures

  override val features: Set[Feature[_, _]] = {
    EnabledPredictedScoreFeatures.map(_.weightQueryFeature).toSet
  }

  private val BIG_PRIME = 65537
  private def hashFn(userId: Long, seed: Long, numBuckets: Int): Int = {
    val x = userId % BIG_PRIME
    val y = seed % BIG_PRIME
    val h = (31 * x + 41 * y) * 53
    (h % BIG_PRIME).toInt % numBuckets
  }

  private val successCounter =
    statsReceiver.counter("success")
  private val failureCounter = statsReceiver.counter("failure")

  private def get_user_weights(
    userId: Long,
    optimizerParams: OptimizerParams
  ): Map[String, Double] = {
    val weightIndex =
      hashFn(userId, seed = optimizerParams.epochTimestamp, numBuckets = optimizerParams.nBuckets)
    val userWeights = optimizerParams.optimizerWeights(weightIndex)
    userWeights
  }

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    val optimizerParams = query.features
      .flatMap(_.getOrElse(HeartbeatOptimizerWeightsFeature, None))

    val featureMapBuilder = FeatureMapBuilder()

    optimizerParams match {
      case Some(optimizerParams) =>
        if (optimizerParams.optimizerWeights.size != optimizerParams.nBuckets) {
          failureCounter.incr(1)
          // Fall back to default weights when size mismatch
          for (predictedScoreFeature <- EnabledPredictedScoreFeatures) {
            val currentWeight = query.params(predictedScoreFeature.modelWeightParam)
            featureMapBuilder.add(predictedScoreFeature.weightQueryFeature, Some(currentWeight))
          }
        } else {
          val userWeights: Map[String, Double] =
            get_user_weights(query.getRequiredUserId, optimizerParams)

          for (predictedScoreFeature <- EnabledPredictedScoreFeatures) {
            val currentWeight = query.params(predictedScoreFeature.modelWeightParam)
            val weight_name = predictedScoreFeature.modelWeightParam.name
            val optimizerWeight =
              userWeights.getOrElse(weight_name, currentWeight)
            featureMapBuilder.add(predictedScoreFeature.weightQueryFeature, Some(optimizerWeight))
          }
          successCounter.incr(1)
        }

      case _ =>
        // When HeartbeatOptimizerWeightsFeature is None, use default weights
        for (predictedScoreFeature <- EnabledPredictedScoreFeatures) {
          val currentWeight = query.params(predictedScoreFeature.modelWeightParam)
          featureMapBuilder.add(predictedScoreFeature.weightQueryFeature, Some(currentWeight))
        }
        failureCounter.incr(1)
    }

    Stitch.value(featureMapBuilder.build())
  }
}
