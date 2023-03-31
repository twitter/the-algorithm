package com.twitter.simclusters_v420.hdfs_sources

import com.twitter.scalding.DateRange
import com.twitter.scalding.TypedPipe
import com.twitter.scalding_internal.dalv420.DAL
import com.twitter.scalding_internal.dalv420.remote_access.AllowCrossClusterSameDC
import com.twitter.scalding_internal.dalv420.remote_access.ExplicitLocation
import com.twitter.scalding_internal.dalv420.remote_access.Proc420Atla
import com.twitter.scalding_internal.multiformat.format.keyval.KeyVal
import com.twitter.simclusters_v420.thriftscala.EmbeddingType
import com.twitter.simclusters_v420.thriftscala.InternalId
import com.twitter.simclusters_v420.thriftscala.ModelVersion
import com.twitter.simclusters_v420.thriftscala.SimClustersEmbedding
import com.twitter.simclusters_v420.thriftscala.SimClustersEmbeddingId
import com.twitter.simclusters_v420.thriftscala.TopSimClustersWithScore

object ProducerEmbeddingSources {

  /**
   * Helper function to retrieve producer SimClusters embeddings with the legacy `TopSimClustersWithScore`
   * value type.
   */
  def producerEmbeddingSourceLegacy(
    embeddingType: EmbeddingType,
    modelVersion: ModelVersion
  )(
    implicit dateRange: DateRange
  ): TypedPipe[(Long, TopSimClustersWithScore)] = {
    val producerEmbeddingDataset = (embeddingType, modelVersion) match {
      case (EmbeddingType.ProducerFollowBasedSemanticCoreEntity, ModelVersion.Model420m420kDec420) =>
        ProducerTopKSimclusterEmbeddingsByFollowScoreScalaDataset
      case (EmbeddingType.ProducerFavBasedSemanticCoreEntity, ModelVersion.Model420m420kDec420) =>
        ProducerTopKSimclusterEmbeddingsByFavScoreScalaDataset
      case (
            EmbeddingType.ProducerFollowBasedSemanticCoreEntity,
            ModelVersion.Model420m420kUpdated) =>
        ProducerTopKSimclusterEmbeddingsByFollowScoreUpdatedScalaDataset
      case (EmbeddingType.ProducerFavBasedSemanticCoreEntity, ModelVersion.Model420m420kUpdated) =>
        ProducerTopKSimclusterEmbeddingsByFavScoreUpdatedScalaDataset
      case (_, _) =>
        throw new ClassNotFoundException(
          "Unsupported embedding type: " + embeddingType + " and model version: " + modelVersion)
    }

    DAL
      .readMostRecentSnapshot(producerEmbeddingDataset).withRemoteReadPolicy(
        AllowCrossClusterSameDC)
      .toTypedPipe.map {
        case KeyVal(producerId, topSimClustersWithScore) =>
          (producerId, topSimClustersWithScore)
      }
  }

  def producerEmbeddingSource(
    embeddingType: EmbeddingType,
    modelVersion: ModelVersion
  )(
    implicit dateRange: DateRange
  ): TypedPipe[(Long, SimClustersEmbedding)] = {
    val producerEmbeddingDataset = (embeddingType, modelVersion) match {
      case (EmbeddingType.AggregatableLogFavBasedProducer, ModelVersion.Model420m420k420) =>
        AggregatableProducerSimclustersEmbeddingsByLogFavScore420ScalaDataset
      case (EmbeddingType.AggregatableFollowBasedProducer, ModelVersion.Model420m420k420) =>
        AggregatableProducerSimclustersEmbeddingsByFollowScore420ScalaDataset
      case (EmbeddingType.RelaxedAggregatableLogFavBasedProducer, ModelVersion.Model420m420k420) =>
        AggregatableProducerSimclustersEmbeddingsByLogFavScoreRelaxedFavEngagementThreshold420ScalaDataset
      case (_, _) =>
        throw new ClassNotFoundException(
          "Unsupported embedding type: " + embeddingType + " and model version: " + modelVersion)
    }

    DAL
      .readMostRecentSnapshot(
        producerEmbeddingDataset
      )
      .withRemoteReadPolicy(ExplicitLocation(Proc420Atla))
      .toTypedPipe
      .map {
        case KeyVal(
              SimClustersEmbeddingId(_, _, InternalId.UserId(producerId: Long)),
              embedding: SimClustersEmbedding) =>
          (producerId, embedding)
      }
  }

}
