package com.twitter.follow_recommendations.common.rankers.ml_ranker.scoring

import com.twitter.cortetwitter.deepbird.thriftjava.DeepbirdPredictionService
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.follow_recommendations.common.constants.GuiceNamedConstants
import com.twitter.follow_recommendations.common.rankers.common.RankerId
import com.twitter.ml.api.Feature
import javatwitter.inject.Inject
import javatwitter.inject.Named
import javatwitter.inject.Singleton

// This is a standard DeepbirdV2 ML Ranker scoring config that should be etwittertended by all ML scorers
//
// Only modify this trait when adding new fields to DeepbirdV2 scorers which
trait DeepbirdProdScorer etwittertends DeepbirdScorer {
  override val batchSize = 20
}

// Feature.Continuous("prediction") is specific to ClemNet architecture, we can change it to be more informative in the netwittert iteration
trait PostNutwitterV1DeepbirdProdScorer etwittertends DeepbirdProdScorer {
  override val predictionFeature: Feature.Continuous =
    new Feature.Continuous("prediction")
}

// The current, primary PostNUtwitter DeepbirdV2 scorer used in production
@Singleton
class PostnutwitterDeepbirdProdScorer @Inject() (
  @Named(GuiceNamedConstants.WTF_PROD_DEEPBIRDV2_CLIENT)
  override val deepbirdClient: DeepbirdPredictionService.ServiceToClient,
  override val baseStats: StatsReceiver)
    etwittertends PostNutwitterV1DeepbirdProdScorer {
  override val id = RankerId.PostNutwitterProdRanker
  override val modelName = "PostNUtwitter14531GafClemNetWarmStart"
}
