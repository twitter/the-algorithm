package com.twitter.follow_recommendations.common.rankers.ml_ranker.scoring

import com.twitter.cortex.deepbird.thriftjava.DeepbirdPredictionService
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.follow_recommendations.common.constants.GuiceNamedConstants
import com.twitter.follow_recommendations.common.rankers.common.RankerId
import com.twitter.ml.api.Feature
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

// This is a standard DeepbirdV2 ML Ranker scoring config that should be extended by all ML scorers
//
// Only modify this trait when adding new fields to DeepbirdV2 scorers which
trait DeepbirdProdScorer extends DeepbirdScorer {
  override val batchSize = 20
}

// Feature.Continuous("prediction") is specific to ClemNet architecture, we can change it to be more informative in the next iteration
trait PostNuxV1DeepbirdProdScorer extends DeepbirdProdScorer {
  override val predictionFeature: Feature.Continuous =
    new Feature.Continuous("prediction")
}

// The current, primary PostNUX DeepbirdV2 scorer used in production
@Singleton
class PostnuxDeepbirdProdScorer @Inject() (
  @Named(GuiceNamedConstants.WTF_PROD_DEEPBIRDV2_CLIENT)
  override val deepbirdClient: DeepbirdPredictionService.ServiceToClient,
  override val baseStats: StatsReceiver)
    extends PostNuxV1DeepbirdProdScorer {
  override val id = RankerId.PostNuxProdRanker
  override val modelName = "PostNUX14531GafClemNetWarmStart"
}
