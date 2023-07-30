package com.X.simclusters_v2.common.ml

import com.X.ml.api.Feature.Continuous
import com.X.ml.api.Feature.SparseContinuous
import com.X.ml.api._
import com.X.ml.api.util.FDsl._
import com.X.simclusters_v2.common.SimClustersEmbedding

class SimClustersEmbeddingAdapter(embeddingFeature: SparseContinuous)
    extends IRecordOneToOneAdapter[SimClustersEmbedding] {

  override def getFeatureContext: FeatureContext = new FeatureContext(embeddingFeature)

  override def adaptToDataRecord(embedding: SimClustersEmbedding): DataRecord = {
    val embeddingMap = embedding.embedding.map {
      case (clusterId, score) =>
        (clusterId.toString, score)
    }.toMap

    new DataRecord().setFeatureValue(embeddingFeature, embeddingMap)
  }
}

class NormalizedSimClustersEmbeddingAdapter(
  embeddingFeature: SparseContinuous,
  normFeature: Continuous)
    extends IRecordOneToOneAdapter[SimClustersEmbedding] {

  override def getFeatureContext: FeatureContext = new FeatureContext(embeddingFeature, normFeature)

  override def adaptToDataRecord(embedding: SimClustersEmbedding): DataRecord = {

    val normalizedEmbedding = Map(
      embedding.sortedClusterIds.map(_.toString).zip(embedding.normalizedSortedScores): _*)

    val dataRecord = new DataRecord().setFeatureValue(embeddingFeature, normalizedEmbedding)
    dataRecord.setFeatureValue(normFeature, embedding.l2norm)
  }
}
