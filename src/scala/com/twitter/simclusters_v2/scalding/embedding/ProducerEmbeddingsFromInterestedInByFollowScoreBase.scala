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

/**
 * Base class for Follow based producer embeddings. Helps reuse the code for different model versions
 */
trait ProducerEmbeddingsFromInterestedInByFollowScoreBase extends ScheduledExecutionApp {
  import ProducerEmbeddingsFromInterestedIn.*
  import ProducerEmbeddingsFromInterestedInBatchAppUtil.*

  val producerTopKEmbeddingsByFollowScorePathPrefix: String =
    "/producer_top_k_simcluster_embeddings_by_follow_score_"
  val clusterTopKProducersByFollowScorePathPrefix: String =
    "/simcluster_embedding_top_k_producers_by_follow_score_"
  val minNumFollowers: Int = minNumFollowersForProducer

  def modelVersion: ModelVersion

  def producerTopKSimclusterEmbeddingsByFollowScoreDataset: KeyValDALDataset[
    KeyVal[Long, TopSimClustersWithScore]
  ]

  def simclusterEmbeddingTopKProducersByFollowScoreDataset: KeyValDALDataset[
    KeyVal[PersistedFullClusterId, TopProducersWithScore]
  ]

  def getInterestedInFn: (DateRange, TimeZone) => TypedPipe[(Long, ClustersUserIsInterestedIn)]

  override def runOnDateRange(
    args: Args
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): Execution[Unit] = {

    val producerTopKEmbeddingsByFollowScorePath: String =
      rootPath + producerTopKEmbeddingsByFollowScorePathPrefix + ModelVersions
        .toKnownForModelVersion(modelVersion)

    val clusterTopKProducersByFollowScorePath: String =
      rootPath + clusterTopKProducersByFollowScorePathPrefix + ModelVersions
        .toKnownForModelVersion(modelVersion)

    val producerClusterEmbeddingByFollowScore = getProducerClusterEmbedding(
      getInterestedInFn(dateRange.embiggen(Days(5)), timeZone),
      DataSources.userUserNormalizedGraphSource,
      DataSources.userNormsAndCounts,
      userToProducerFollowScore,
      userToClusterFollowScore, // Follow score
      _.followerCount.exists(_ > minNumFollowers),
      numReducersForMatrixMultiplication,
      modelVersion,
      cosineSimilarityThreshold
    ).forceToDisk

    writeOutput(
      producerClusterEmbeddingByFollowScore,
      producerTopKSimclusterEmbeddingsByFollowScoreDataset,
      simclusterEmbeddingTopKProducersByFollowScoreDataset,
      producerTopKEmbeddingsByFollowScorePath,
      clusterTopKProducersByFollowScorePath,
      modelVersion
    )
  }
}
















