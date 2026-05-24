package com.twitter.home_mixer.functional_component.feature_hydrator.adapters.simclusters_features

import com.twitter.ml.api.Feature
import com.twitter.ml.api.FeatureContext
import com.twitter.ml.api.RichDataRecord
import com.twitter.simclusters_v2.thriftscala.SimClustersEmbedding
import com.twitter.timelines.prediction.common.adapters.TimelinesMutatingAdapterBase
import com.twitter.timelines.prediction.features.simcluster.SimclusterFeatures
import java.util
import java.util.Collections

object SimclustersFeaturesAdapter
    extends TimelinesMutatingAdapterBase[Option[SimClustersEmbedding]] {

  val SimclustersSparseTweetEmbeddingsFeature: Feature.SparseContinuous =
    SimclusterFeatures.SIMCLUSTER_TWEET_CLUSTER_SCORES

  override val getFeatureContext: FeatureContext = new FeatureContext(
    SimclustersSparseTweetEmbeddingsFeature)

  override val commonFeatures: Set[Feature[_]] = Set.empty

  override def setFeatures(
    simClustersEmbedding: Option[SimClustersEmbedding],
    richDataRecord: RichDataRecord
  ): Unit = {
    val simclustersWithScoresMap = simClustersEmbedding match {
      case Some(value) =>
        val result = new util.HashMap[String, java.lang.Double](value.embedding.size, 1.0f)
        value.embedding.foreach { simclusterWithScore =>
          result.put(simclusterWithScore.clusterId.toString, simclusterWithScore.score)
        }
        result
      case None =>
        Collections.emptyMap[String, java.lang.Double]()
    }
    richDataRecord.setFeatureValue(
      SimclustersSparseTweetEmbeddingsFeature,
      simclustersWithScoresMap
    )
  }
}
