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
 * Base class for Fav based producer embeddings. Helps reuse the code for different model versions
 */
trait ProducerEmbeddingsFromInterestedInByFavScoreBase extends ScheduledExecutionApp {
  import ProducerEmbeddingsFromInterestedIn.*
  import ProducerEmbeddingsFromInterestedInBatchAppUtil.*

  val producerTopKEmbeddingsByFavScorePathPrefix: String =
    "/producer_top_k_simcluster_embeddings_by_fav_score_"
  val clusterTopKProducersByFavScorePathPrefix: String =
    "/simcluster_embedding_top_k_producers_by_fav_score_"
  val minNumFavers: Int = minNumFaversForProducer

  def modelVersion: ModelVersion

  def producerTopKSimclusterEmbeddingsByFavScoreDataset: KeyValDALDataset[
    KeyVal[Long, TopSimClustersWithScore]
  ]

  def simclusterEmbeddingTopKProducersByFavScoreDataset: KeyValDALDataset[
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

    val producerTopKEmbeddingsByFavScorePathUpdated: String =
      rootPath + producerTopKEmbeddingsByFavScorePathPrefix + ModelVersions
        .toKnownForModelVersion(modelVersion)

    val clusterTopKProducersByFavScorePathUpdated: String =
      rootPath + clusterTopKProducersByFavScorePathPrefix + ModelVersions
        .toKnownForModelVersion(modelVersion)

    val producerClusterEmbeddingByFavScore = getProducerClusterEmbedding(
      getInterestedInFn(dateRange.embiggen(Days(5)), timeZone),
      DataSources.userUserNormalizedGraphSource,
      DataSources.userNormsAndCounts,
      userToProducerFavScore,
      userToClusterFavScore, // Fav score
      _.faverCount.exists(_ > minNumFavers),
      numReducersForMatrixMultiplication,
      modelVersion,
      cosineSimilarityThreshold
    ).forceToDisk

    writeOutput(
      producerClusterEmbeddingByFavScore,
      producerTopKSimclusterEmbeddingsByFavScoreDataset,
      simclusterEmbeddingTopKProducersByFavScoreDataset,
      producerTopKEmbeddingsByFavScorePathUpdated,
      clusterTopKProducersByFavScorePathUpdated,
      modelVersion
    )
  }
}


















