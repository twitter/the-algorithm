package com.twitter.simclusters_v2.scalding.embedding

import com.twitter.dal.client.dataset.KeyValDALDataset
import com.twitter.scalding.*
import com.twitter.scalding_internal.dalv2.DALWrite.*
import com.twitter.scalding_internal.multiformat.format.keyval.KeyVal
import com.twitter.simclusters_v2.common.ModelVersions
import com.twitter.simclusters_v2.hdfs_sources.*
import com.twitter.simclusters_v2.scalding.embedding.common.EmbeddingUtil.*
import com.twitter.simclusters_v2.scalding.embedding.common.SimClustersEmbeddingJob
import com.twitter.simclusters_v2.thriftscala.*
import com.twitter.wtf.scalding.jobs.common.{AdhocExecutionApp, ScheduledExecutionApp}

import java.util.TimeZone

object ProducerEmbeddingsFromInterestedInBatchAppUtil {
  import ProducerEmbeddingsFromInterestedIn.*

  val user = System.getenv("USER")

  val rootPath: String = s"/user/$user/manhattan_sequence_files"

  // Helps speed up the multiplication step which can get very big
  val numReducersForMatrixMultiplication: Int = 12000

  /**
   * Given the producer x cluster matrix, key by producer / cluster individually, and write output
   * to individual DAL datasets
   */
  def writeOutput(
    producerClusterEmbedding: TypedPipe[((ClusterId, UserId), Double)],
    producerTopKEmbeddingsDataset: KeyValDALDataset[KeyVal[Long, TopSimClustersWithScore]],
    clusterTopKProducersDataset: KeyValDALDataset[
      KeyVal[PersistedFullClusterId, TopProducersWithScore]
    ],
    producerTopKEmbeddingsPath: String,
    clusterTopKProducersPath: String,
    modelVersion: ModelVersion
  ): Execution[Unit] = {
    val keyedByProducer =
      toSimClusterEmbedding(producerClusterEmbedding, topKClustersToKeep, modelVersion)
        .map { case (userId, clusters) => KeyVal(userId, clusters) }
        .writeDALVersionedKeyValExecution(
          producerTopKEmbeddingsDataset,
          D.Suffix(producerTopKEmbeddingsPath)
        )

    val keyedBySimCluster = fromSimClusterEmbedding(
      producerClusterEmbedding,
      topKUsersToKeep,
      modelVersion
    ).map {
        case (clusterId, topProducers) => KeyVal(clusterId, topProducersToThrift(topProducers))
      }
      .writeDALVersionedKeyValExecution(
        clusterTopKProducersDataset,
        D.Suffix(clusterTopKProducersPath)
      )

    Execution.zip(keyedByProducer, keyedBySimCluster).unit
  }
}




















