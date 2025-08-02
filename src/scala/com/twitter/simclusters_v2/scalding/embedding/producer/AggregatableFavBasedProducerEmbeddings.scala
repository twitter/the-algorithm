package com.twitter.simclusters_v2.scalding.embedding.producer

import com.twitter.scalding._
import com.twitter.scalding_internal.dalv2.DALWrite._
import com.twitter.scalding_internal.multiformat.format.keyval.KeyVal
import com.twitter.scalding_internal.source.lzo_scrooge.FitwitteredPathLzoScrooge
import com.twitter.simclusters_v2.hdfs_sources.{
  AggregatableProducerSimclustersEmbeddingsByFavScoreScalaDataset,
  AggregatableProducerSimclustersEmbeddingsByFavScoreThriftScalaDataset,
  AggregatableProducerSimclustersEmbeddingsByFavScore2020ScalaDataset,
  AggregatableProducerSimclustersEmbeddingsByFavScore2020ThriftScalaDataset
}
import com.twitter.simclusters_v2.scalding.embedding.common.EmbeddingUtil
import com.twitter.simclusters_v2.thriftscala._
import com.twitter.wtf.scalding.jobs.common.{AdhocEtwitterecutionApp, ScheduledEtwitterecutionApp}
import java.util.TimeZone

/**
 * See AggregatableProducerEmbeddingsBaseApp for an etwitterplanation of this job.
 *
 * Production job:
capesospy-v2 update aggregatable_producer_embeddings_by_fav_score src/scala/com/twitter/simclusters_v2/capesos_config/atla_proc3.yaml
 */
object AggregatableFavBasedProducerEmbeddingsScheduledApp
    etwittertends AggregatableFavBasedProducerEmbeddingsBaseApp
    with ScheduledEtwitterecutionApp {

  override val modelVersion: ModelVersion = ModelVersion.Model20m145kUpdated
  // Not using the EmbeddingUtil.getHdfsPath to preserve the previous functionality.
  private val outputPath: String =
    "/user/cassowary/manhattan_sequence_files/producer_simclusters_aggregatable_embeddings_by_fav_score"

  private val outputPathThrift: String = EmbeddingUtil.getHdfsPath(
    isAdhoc = false,
    isManhattanKeyVal = false,
    modelVersion = modelVersion,
    pathSuffitwitter = "producer_simclusters_aggregatable_embeddings_by_fav_score_thrift"
  )

  override def firstTime: RichDate = RichDate("2020-05-11")

  override def batchIncrement: Duration = Days(7)

  override def writeToManhattan(
    output: TypedPipe[KeyVal[SimClustersEmbeddingId, SimClustersEmbedding]]
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): Etwitterecution[Unit] = {
    output
      .writeDALVersionedKeyValEtwitterecution(
        AggregatableProducerSimclustersEmbeddingsByFavScoreScalaDataset,
        D.Suffitwitter(outputPath),
        version = EtwitterplicitEndTime(dateRange.end)
      )
  }

  override def writeToThrift(
    output: TypedPipe[SimClustersEmbeddingWithId]
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): Etwitterecution[Unit] = {
    output
      .writeDALSnapshotEtwitterecution(
        dataset = AggregatableProducerSimclustersEmbeddingsByFavScoreThriftScalaDataset,
        updateStep = D.Daily,
        pathLayout = D.Suffitwitter(outputPathThrift),
        fmt = D.Parquet,
        endDate = dateRange.end
      )
  }
}

/**
 * Production job:
capesospy-v2 update --build_locally --start_cron aggregatable_producer_embeddings_by_fav_score_2020 src/scala/com/twitter/simclusters_v2/capesos_config/atla_proc3.yaml
 */
object AggregatableFavBasedProducerEmbeddings2020ScheduledApp
    etwittertends AggregatableFavBasedProducerEmbeddingsBaseApp
    with ScheduledEtwitterecutionApp {

  override val modelVersion: ModelVersion = ModelVersion.Model20m145k2020
  // Not using the EmbeddingUtil.getHdfsPath to preserve the previous functionality.
  private val outputPath: String =
    "/user/cassowary/manhattan_sequence_files/producer_simclusters_aggregatable_embeddings_by_fav_score_20m145k2020"

  // getHdfsPath appends model version str to the pathSuffitwitter
  private val outputPathThrift: String = EmbeddingUtil.getHdfsPath(
    isAdhoc = false,
    isManhattanKeyVal = false,
    modelVersion = modelVersion,
    pathSuffitwitter = "producer_simclusters_aggregatable_embeddings_by_fav_score_thrift"
  )

  override def firstTime: RichDate = RichDate("2021-03-04")

  override def batchIncrement: Duration = Days(7)

  override def writeToManhattan(
    output: TypedPipe[KeyVal[SimClustersEmbeddingId, SimClustersEmbedding]]
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): Etwitterecution[Unit] = {
    output
      .writeDALVersionedKeyValEtwitterecution(
        AggregatableProducerSimclustersEmbeddingsByFavScore2020ScalaDataset,
        D.Suffitwitter(outputPath),
        version = EtwitterplicitEndTime(dateRange.end)
      )
  }

  override def writeToThrift(
    output: TypedPipe[SimClustersEmbeddingWithId]
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): Etwitterecution[Unit] = {
    output
      .writeDALSnapshotEtwitterecution(
        dataset = AggregatableProducerSimclustersEmbeddingsByFavScore2020ThriftScalaDataset,
        updateStep = D.Daily,
        pathLayout = D.Suffitwitter(outputPathThrift),
        fmt = D.Parquet,
        endDate = dateRange.end
      )
  }
}

/***
 * Adhoc job:

scalding remote run --user recos-platform \
--main-class com.twitter.simclusters_v2.scalding.embedding.producer.AggregatableFavBasedProducerEmbeddingsAdhocApp \
--target src/scala/com/twitter/simclusters_v2/scalding/embedding/producer:aggregatable_fav_based_producer_embeddings_job-adhoc \
-- --date 2020-05-11

 */
object AggregatableFavBasedProducerEmbeddingsAdhocApp
    etwittertends AggregatableFavBasedProducerEmbeddingsBaseApp
    with AdhocEtwitterecutionApp {

  override val modelVersion: ModelVersion = ModelVersion.Model20m145kUpdated
  private val outputPath: String = EmbeddingUtil.getHdfsPath(
    isAdhoc = false,
    isManhattanKeyVal = true,
    modelVersion = modelVersion,
    pathSuffitwitter = "producer_simclusters_aggregatable_embeddings_by_fav_score"
  )

  private val outputPathThrift: String = EmbeddingUtil.getHdfsPath(
    isAdhoc = false,
    isManhattanKeyVal = false,
    modelVersion = modelVersion,
    pathSuffitwitter = "producer_simclusters_aggregatable_embeddings_by_fav_score_thrift"
  )

  override def writeToManhattan(
    output: TypedPipe[KeyVal[SimClustersEmbeddingId, SimClustersEmbedding]]
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): Etwitterecution[Unit] = {
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
      .writeEtwitterecution(
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
  ): Etwitterecution[Unit] = {
    output
      .writeEtwitterecution(
        new FitwitteredPathLzoScrooge(outputPathThrift, SimClustersEmbeddingWithId)
      )
  }
}

/**
./bazel bundle src/scala/com/twitter/simclusters_v2/scalding/embedding/producer:aggregatable_fav_based_producer_embeddings_job_2020-adhoc
scalding remote run \
--user cassowary \
--keytab /var/lib/tss/keys/fluffy/keytabs/client/cassowary.keytab \
--principal service_acoount@TWITTER.BIZ \
--cluster bluebird-qus1 \
--main-class com.twitter.simclusters_v2.scalding.embedding.producer.AggregatableFavBasedProducerEmbeddings2020AdhocApp \
--target src/scala/com/twitter/simclusters_v2/scalding/embedding/producer:aggregatable_fav_based_producer_embeddings_job_2020-adhoc \
--hadoop-properties "scalding.with.reducers.set.etwitterplicitly=true mapreduce.job.reduces=4000" \
-- --date 2020-06-28
 */
object AggregatableFavBasedProducerEmbeddings2020AdhocApp
    etwittertends AggregatableFavBasedProducerEmbeddingsBaseApp
    with AdhocEtwitterecutionApp {

  override val modelVersion: ModelVersion = ModelVersion.Model20m145k2020
  private val outputPath: String = EmbeddingUtil.getHdfsPath(
    isAdhoc = false,
    isManhattanKeyVal = true,
    modelVersion = modelVersion,
    pathSuffitwitter = "producer_simclusters_aggregatable_embeddings_by_fav_score"
  )

  private val outputPathThrift: String = EmbeddingUtil.getHdfsPath(
    isAdhoc = false,
    isManhattanKeyVal = false,
    modelVersion = modelVersion,
    pathSuffitwitter = "producer_simclusters_aggregatable_embeddings_by_fav_score_thrift"
  )

  override def writeToManhattan(
    output: TypedPipe[KeyVal[SimClustersEmbeddingId, SimClustersEmbedding]]
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): Etwitterecution[Unit] = {
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
      .writeEtwitterecution(
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
  ): Etwitterecution[Unit] = {
    output
      .writeEtwitterecution(
        new FitwitteredPathLzoScrooge(outputPathThrift, SimClustersEmbeddingWithId)
      )
  }
}

trait AggregatableFavBasedProducerEmbeddingsBaseApp etwittertends AggregatableProducerEmbeddingsBaseApp {
  override val userToProducerScoringFn: NeighborWithWeights => Double =
    _.favScoreHalfLife100Days.getOrElse(0.0)
  override val userToClusterScoringFn: UserToInterestedInClusterScores => Double =
    _.favScore.getOrElse(0.0)
  override val embeddingType: EmbeddingType = EmbeddingType.AggregatableFavBasedProducer
}
