package com.twitter.home_mixer.functional_component.feature_hydrator

import breeze.stats.distributions.Beta
import breeze.stats.distributions.Rand
import com.github.nscala_time.time.Imports.LocalDate
import com.twitter.home_mixer.model.PredictedScoreFeature
import com.twitter.home_mixer.param.HomeGlobalParams.Scoring.AddNoiseInWeightsPerLabel
import com.twitter.home_mixer.param.HomeGlobalParams.Scoring.EnableDailyFrozenNoisyWeights
import com.twitter.home_mixer.param.HomeGlobalParams.Scoring.NoisyWeightAlphaParam
import com.twitter.home_mixer.param.HomeGlobalParams.Scoring.NoisyWeightBetaParam
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import java.lang.{Long => JLong}
import java.util.Objects
import javax.inject.Inject
import javax.inject.Singleton
import com.twitter.ml.api.thriftscala.GeneralTensor
import com.twitter.ml.api.thriftscala.DoubleTensor

@Singleton
class HeavyRankerWeightsQueryFeatureHydrator @Inject() ()
    extends QueryFeatureHydrator[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("HeavyRankerWeights")

  private def doubleToGeneralTensor(value: Double): GeneralTensor = {
    val tensor = DoubleTensor(doubles = List(value), shape = Some(List(1L)))
    GeneralTensor.DoubleTensor(tensor)
  }

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    val builder = FeatureMapBuilder()
    // Ensure that for the same userId and start of day, we get the same sequence of random numbers
    // Let's us freeze the weight for one day and observe UAS impact
    val startOfDay = LocalDate.now().toDateTimeAtStartOfDay.toInstant.getMillis
    val seed =
      Objects.hash(JLong.valueOf(query.getRequiredUserId), JLong.valueOf(startOfDay)).toLong
    val alpha = query.params(NoisyWeightAlphaParam)
    val beta = query.params(NoisyWeightBetaParam)
    val betaDist = new Beta(alpha, beta)
    if (query.params(EnableDailyFrozenNoisyWeights)) Rand.generator.setSeed(seed)
    for (predictedScoreFeature <- PredictedScoreFeature.PredictedScoreFeatures) {
      val presetWeight = query.params(predictedScoreFeature.modelWeightParam)
      val weight = if (query.params(AddNoiseInWeightsPerLabel)) {
        // Apply noise to each weight
        presetWeight * (1 + betaDist.draw())
      } else {
        presetWeight
      }
      builder.add(predictedScoreFeature.weightQueryFeature, Some(weight))
      for (biasQueryFeature <- predictedScoreFeature.biasQueryFeature;
        modelBiasParam <- predictedScoreFeature.modelBiasParam) {
        builder.add(biasQueryFeature, Some(query.params(modelBiasParam)))
      }
      for (debiasQueryFeature <- predictedScoreFeature.debiasQueryFeature;
        modelDebiasParam <- predictedScoreFeature.modelDebiasParam) {
        val debiasValue = query.params(modelDebiasParam)
        builder.add(debiasQueryFeature, Some(doubleToGeneralTensor((debiasValue))))
      }
    }
    Stitch.value(builder.build())
  }

  override val features: Set[Feature[_, _]] = {
    val weightFeatures = PredictedScoreFeature.PredictedScoreFeatureSet.map(_.weightQueryFeature)
    val biasFeatures = PredictedScoreFeature.PredictedScoreFeatureSet.flatMap(_.biasQueryFeature)
    val debiasFeatures = PredictedScoreFeature.PredictedScoreFeatureSet
      .flatMap(_.debiasQueryFeature)
    (weightFeatures ++ biasFeatures ++ debiasFeatures).toSet
  }
}
