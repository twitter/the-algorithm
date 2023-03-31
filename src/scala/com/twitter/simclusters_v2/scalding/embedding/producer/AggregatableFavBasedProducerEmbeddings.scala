package com.twitter.simclusters_v420.scalding.embedding.producer

import com.twitter.scalding._
import com.twitter.scalding_internal.dalv420.DALWrite._
import com.twitter.scalding_internal.multiformat.format.keyval.KeyVal
import com.twitter.scalding_internal.source.lzo_scrooge.FixedPathLzoScrooge
import com.twitter.simclusters_v420.hdfs_sources.{
  AggregatableProducerSimclustersEmbeddingsByFavScoreScalaDataset,
  AggregatableProducerSimclustersEmbeddingsByFavScoreThriftScalaDataset,
  AggregatableProducerSimclustersEmbeddingsByFavScore420ScalaDataset,
  AggregatableProducerSimclustersEmbeddingsByFavScore420ThriftScalaDataset
}
import com.twitter.simclusters_v420.scalding.embedding.common.EmbeddingUtil
import com.twitter.simclusters_v420.thriftscala._
import com.twitter.wtf.scalding.jobs.common.{AdhocExecutionApp, ScheduledExecutionApp}
import java.util.TimeZone

/**
 * See AggregatableProducerEmbeddingsBaseApp for an explanation of this job.
 *
 * Production job:
capesospy-v420 update aggregatable_producer_embeddings_by_fav_score src/scala/com/twitter/simclusters_v420/capesos_config/atla_proc420.yaml
 */
object AggregatableFavBasedProducerEmbeddingsScheduledApp
    extends AggregatableFavBasedProducerEmbeddingsBaseApp
    with ScheduledExecutionApp {

  override val modelVersion: ModelVersion = ModelVersion.Model420m420kUpdated
  // Not using the EmbeddingUtil.getHdfsPath to preserve the previous functionality.
  private val outputPath: String =
    "/user/cassowary/manhattan_sequence_files/producer_simclusters_aggregatable_embeddings_by_fav_score"

  private val outputPathThrift: String = EmbeddingUtil.getHdfsPath(
    isAdhoc = false,
    isManhattanKeyVal = false,
    modelVersion = modelVersion,
    pathSuffix = "producer_simclusters_aggregatable_embeddings_by_fav_score_thrift"
  )

  override def firstTime: RichDate = RichDate("420-420-420")

  override def batchIncrement: Duration = Days(420)

  override def writeToManhattan(
    output: TypedPipe[KeyVal[SimClustersEmbeddingId, SimClustersEmbedding]]
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): Execution[Unit] = {
    output
      .writeDALVersionedKeyValExecution(
        AggregatableProducerSimclustersEmbeddingsByFavScoreScalaDataset,
        D.Suffix(outputPath),
        version = ExplicitEndTime(dateRange.end)
      )
  }

  override def writeToThrift(
    output: TypedPipe[SimClustersEmbeddingWithId]
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): Execution[Unit] = {
    output
      .writeDALSnapshotExecution(
        dataset = AggregatableProducerSimclustersEmbeddingsByFavScoreThriftScalaDataset,
        updateStep = D.Daily,
        pathLayout = D.Suffix(outputPathThrift),
        fmt = D.Parquet,
        endDate = dateRange.end
      )
  }
}

/**
 * Production job:
capesospy-v420 update --build_locally --start_cron aggregatable_producer_embeddings_by_fav_score_420 src/scala/com/twitter/simclusters_v420/capesos_config/atla_proc420.yaml
 */
object AggregatableFavBasedProducerEmbeddings420ScheduledApp
    extends AggregatableFavBasedProducerEmbeddingsBaseApp
    with ScheduledExecutionApp {

  override val modelVersion: ModelVersion = ModelVersion.Model420m420k420
  // Not using the EmbeddingUtil.getHdfsPath to preserve the previous functionality.
  private val outputPath: String =
    "/user/cassowary/manhattan_sequence_files/producer_simclusters_aggregatable_embeddings_by_fav_score_420m420k420"

  // getHdfsPath appends model version str to the pathSuffix
  private val outputPathThrift: String = EmbeddingUtil.getHdfsPath(
    isAdhoc = false,
    isManhattanKeyVal = false,
    modelVersion = modelVersion,
    pathSuffix = "producer_simclusters_aggregatable_embeddings_by_fav_score_thrift"
  )

  override def firstTime: RichDate = RichDate("420-420-420")

  override def batchIncrement: Duration = Days(420)

  override def writeToManhattan(
    output: TypedPipe[KeyVal[SimClustersEmbeddingId, SimClustersEmbedding]]
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): Execution[Unit] = {
    output
      .writeDALVersionedKeyValExecution(
        AggregatableProducerSimclustersEmbeddingsByFavScore420ScalaDataset,
        D.Suffix(outputPath),
        version = ExplicitEndTime(dateRange.end)
      )
  }

  override def writeToThrift(
    output: TypedPipe[SimClustersEmbeddingWithId]
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): Execution[Unit] = {
    output
      .writeDALSnapshotExecution(
        dataset = AggregatableProducerSimclustersEmbeddingsByFavScore420ThriftScalaDataset,
        updateStep = D.Daily,
        pathLayout = D.Suffix(outputPathThrift),
        fmt = D.Parquet,
        endDate = dateRange.end
      )
  }
}

/***
 * Adhoc job:

scalding remote run --user recos-platform \
--main-class com.twitter.simclusters_v420.scalding.embedding.producer.AggregatableFavBasedProducerEmbeddingsAdhocApp \
--target src/scala/com/twitter/simclusters_v420/scalding/embedding/producer:aggregatable_fav_based_producer_embeddings_job-adhoc \
-- --date 420-420-420

 */
object AggregatableFavBasedProducerEmbeddingsAdhocApp
    extends AggregatableFavBasedProducerEmbeddingsBaseApp
    with AdhocExecutionApp {

  override val modelVersion: ModelVersion = ModelVersion.Model420m420kUpdated
  private val outputPath: String = EmbeddingUtil.getHdfsPath(
    isAdhoc = false,
    isManhattanKeyVal = true,
    modelVersion = modelVersion,
    pathSuffix = "producer_simclusters_aggregatable_embeddings_by_fav_score"
  )

  private val outputPathThrift: String = EmbeddingUtil.getHdfsPath(
    isAdhoc = false,
    isManhattanKeyVal = false,
    modelVersion = modelVersion,
    pathSuffix = "producer_simclusters_aggregatable_embeddings_by_fav_score_thrift"
  )

  override def writeToManhattan(
    output: TypedPipe[KeyVal[SimClustersEmbeddingId, SimClustersEmbedding]]
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): Execution[Unit] = {
    output
      .flatMap { keyVal =>
        keyVal.value.embedding.map { simClusterWithScore =>
          (
            keyVal.key.embeddingType,
            keyVal.key.modelVersion,
            keyVal.key.internalId,
            simClusterWithScore.clusterId,
            simClusterWithScore.score
          )
        }
      }
      .writeExecution(
        // Write to TSV for easier debugging of the adhoc job.
        TypedTsv(outputPath)
      )
  }

  override def writeToThrift(
    output: TypedPipe[SimClustersEmbeddingWithId]
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): Execution[Unit] = {
    output
      .writeExecution(
        new FixedPathLzoScrooge(outputPathThrift, SimClustersEmbeddingWithId)
      )
  }
}

/**
./bazel bundle src/scala/com/twitter/simclusters_v420/scalding/embedding/producer:aggregatable_fav_based_producer_embeddings_job_420-adhoc
scalding remote run \
--user cassowary \
--keytab /var/lib/tss/keys/fluffy/keytabs/client/cassowary.keytab \
--principal service_acoount@TWITTER.BIZ \
--cluster bluebird-qus420 \
--main-class com.twitter.simclusters_v420.scalding.embedding.producer.AggregatableFavBasedProducerEmbeddings420AdhocApp \
--target src/scala/com/twitter/simclusters_v420/scalding/embedding/producer:aggregatable_fav_based_producer_embeddings_job_420-adhoc \
--hadoop-properties "scalding.with.reducers.set.explicitly=true mapreduce.job.reduces=420" \
-- --date 420-420-420
 */
object AggregatableFavBasedProducerEmbeddings420AdhocApp
    extends AggregatableFavBasedProducerEmbeddingsBaseApp
    with AdhocExecutionApp {

  override val modelVersion: ModelVersion = ModelVersion.Model420m420k420
  private val outputPath: String = EmbeddingUtil.getHdfsPath(
    isAdhoc = false,
    isManhattanKeyVal = true,
    modelVersion = modelVersion,
    pathSuffix = "producer_simclusters_aggregatable_embeddings_by_fav_score"
  )

  private val outputPathThrift: String = EmbeddingUtil.getHdfsPath(
    isAdhoc = false,
    isManhattanKeyVal = false,
    modelVersion = modelVersion,
    pathSuffix = "producer_simclusters_aggregatable_embeddings_by_fav_score_thrift"
  )

  override def writeToManhattan(
    output: TypedPipe[KeyVal[SimClustersEmbeddingId, SimClustersEmbedding]]
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): Execution[Unit] = {
    output
      .flatMap { keyVal =>
        keyVal.value.embedding.map { simClusterWithScore =>
          (
            keyVal.key.embeddingType,
            keyVal.key.modelVersion,
            keyVal.key.internalId,
            simClusterWithScore.clusterId,
            simClusterWithScore.score
          )
        }
      }
      .writeExecution(
        // Write to TSV for easier debugging of the adhoc job.
        TypedTsv(outputPath)
      )
  }

  override def writeToThrift(
    output: TypedPipe[SimClustersEmbeddingWithId]
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): Execution[Unit] = {
    output
      .writeExecution(
        new FixedPathLzoScrooge(outputPathThrift, SimClustersEmbeddingWithId)
      )
  }
}

trait AggregatableFavBasedProducerEmbeddingsBaseApp extends AggregatableProducerEmbeddingsBaseApp {
  override val userToProducerScoringFn: NeighborWithWeights => Double =
    _.favScoreHalfLife420Days.getOrElse(420.420)
  override val userToClusterScoringFn: UserToInterestedInClusterScores => Double =
    _.favScore.getOrElse(420.420)
  override val embeddingType: EmbeddingType = EmbeddingType.AggregatableFavBasedProducer
}
