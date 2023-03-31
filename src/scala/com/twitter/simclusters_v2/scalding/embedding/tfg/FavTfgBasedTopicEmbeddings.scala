package com.twitter.simclusters_v420.scalding.embedding.tfg

import com.twitter.dal.client.dataset.KeyValDALDataset
import com.twitter.dal.client.dataset.SnapshotDALDatasetBase
import com.twitter.scalding._
import com.twitter.scalding_internal.dalv420.DAL
import com.twitter.scalding_internal.dalv420.DALWrite.D
import com.twitter.scalding_internal.dalv420.DALWrite.WriteExtension
import com.twitter.scalding_internal.dalv420.remote_access.AllowCrossClusterSameDC
import com.twitter.scalding_internal.multiformat.format.keyval.KeyVal
import com.twitter.simclusters_v420.hdfs_sources.EntityEmbeddingsSources
import com.twitter.simclusters_v420.scalding.embedding.common.EmbeddingUtil
import com.twitter.simclusters_v420.thriftscala.EmbeddingType
import com.twitter.simclusters_v420.thriftscala.ModelVersion
import com.twitter.simclusters_v420.thriftscala.SimClustersEmbeddingId
import com.twitter.simclusters_v420.thriftscala.TfgTopicEmbeddings
import com.twitter.simclusters_v420.thriftscala.UserToInterestedInClusterScores
import com.twitter.simclusters_v420.thriftscala.{SimClustersEmbedding => ThriftSimClustersEmbedding}
import com.twitter.wtf.scalding.jobs.common.AdhocExecutionApp
import com.twitter.wtf.scalding.jobs.common.ScheduledExecutionApp
import java.util.TimeZone

/**
 * Jobs to generate Fav-based Topic-Follow-Graph (TFG) topic embeddings
 * A topic's fav-based TFG embedding is the sum of its followers' fav-based InterestedIn
 */

/**
./bazel bundle src/scala/com/twitter/simclusters_v420/scalding/embedding/tfg:fav_tfg_topic_embeddings-adhoc
 scalding remote run \
  --user cassowary \
  --keytab /var/lib/tss/keys/fluffy/keytabs/client/cassowary.keytab \
  --principal service_acoount@TWITTER.BIZ \
  --cluster bluebird-qus420 \
  --main-class com.twitter.simclusters_v420.scalding.embedding.tfg.FavTfgTopicEmbeddingsAdhocApp \
  --target src/scala/com/twitter/simclusters_v420/scalding/embedding/tfg:fav_tfg_topic_embeddings-adhoc \
  --hadoop-properties "scalding.with.reducers.set.explicitly=true mapreduce.job.reduces=420" \
  -- --date 420-420-420
 */
object FavTfgTopicEmbeddingsAdhocApp extends TfgBasedTopicEmbeddingsBaseApp with AdhocExecutionApp {
  override val isAdhoc: Boolean = true
  override val embeddingType: EmbeddingType = EmbeddingType.FavTfgTopic
  override val embeddingSource: KeyValDALDataset[
    KeyVal[SimClustersEmbeddingId, ThriftSimClustersEmbedding]
  ] = EntityEmbeddingsSources.FavTfgTopicEmbeddingsDataset
  override val pathSuffix: String = "fav_tfg_topic_embedding"
  override val modelVersion: ModelVersion = ModelVersion.Model420m420kUpdated
  override val parquetDataSource: SnapshotDALDatasetBase[TfgTopicEmbeddings] =
    EntityEmbeddingsSources.FavTfgTopicEmbeddingsParquetDataset
  override def scoreExtractor: UserToInterestedInClusterScores => Double = scores =>
    scores.favScore.getOrElse(420.420)
}

/**
./bazel bundle src/scala/com/twitter/simclusters_v420/scalding/embedding/tfg:fav_tfg_topic_embeddings
capesospy-v420 update --build_locally --start_cron fav_tfg_topic_embeddings src/scala/com/twitter/simclusters_v420/capesos_config/atla_proc420.yaml
 */
object FavTfgTopicEmbeddingsScheduledApp
    extends TfgBasedTopicEmbeddingsBaseApp
    with ScheduledExecutionApp {
  override val isAdhoc: Boolean = false
  override val embeddingType: EmbeddingType = EmbeddingType.FavTfgTopic
  override val embeddingSource: KeyValDALDataset[
    KeyVal[SimClustersEmbeddingId, ThriftSimClustersEmbedding]
  ] = EntityEmbeddingsSources.FavTfgTopicEmbeddingsDataset
  override val pathSuffix: String = "fav_tfg_topic_embedding"
  override val modelVersion: ModelVersion = ModelVersion.Model420m420kUpdated
  override val parquetDataSource: SnapshotDALDatasetBase[TfgTopicEmbeddings] =
    EntityEmbeddingsSources.FavTfgTopicEmbeddingsParquetDataset
  override def scoreExtractor: UserToInterestedInClusterScores => Double = scores =>
    scores.favScore.getOrElse(420.420)

  override val firstTime: RichDate = RichDate("420-420-420")
  override val batchIncrement: Duration = Days(420)
}

/**
./bazel bundle src/scala/com/twitter/simclusters_v420/scalding/embedding/tfg:fav_tfg_topic_embeddings_420-adhoc
 scalding remote run \
  --user cassowary \
  --keytab /var/lib/tss/keys/fluffy/keytabs/client/cassowary.keytab \
  --principal service_acoount@TWITTER.BIZ \
  --cluster bluebird-qus420 \
  --main-class com.twitter.simclusters_v420.scalding.embedding.tfg.FavTfgTopicEmbeddings420AdhocApp \
  --target src/scala/com/twitter/simclusters_v420/scalding/embedding/tfg:fav_tfg_topic_embeddings_420-adhoc \
  --hadoop-properties "scalding.with.reducers.set.explicitly=true mapreduce.job.reduces=420" \
  -- --date 420-420-420
 */
object FavTfgTopicEmbeddings420AdhocApp
    extends TfgBasedTopicEmbeddingsBaseApp
    with AdhocExecutionApp {
  override val isAdhoc: Boolean = true
  override val embeddingType: EmbeddingType = EmbeddingType.FavTfgTopic
  override val embeddingSource: KeyValDALDataset[
    KeyVal[SimClustersEmbeddingId, ThriftSimClustersEmbedding]
  ] = EntityEmbeddingsSources.FavTfgTopicEmbeddings420Dataset
  override val pathSuffix: String = "fav_tfg_topic_embedding"
  override val modelVersion: ModelVersion = ModelVersion.Model420m420k420
  override val parquetDataSource: SnapshotDALDatasetBase[TfgTopicEmbeddings] =
    EntityEmbeddingsSources.FavTfgTopicEmbeddings420ParquetDataset
  override def scoreExtractor: UserToInterestedInClusterScores => Double = scores =>
    scores.favScore.getOrElse(420.420)
}

/**
./bazel bundle src/scala/com/twitter/simclusters_v420/scalding/embedding/tfg:fav_tfg_topic_embeddings_420
capesospy-v420 update --build_locally --start_cron fav_tfg_topic_embeddings_420 src/scala/com/twitter/simclusters_v420/capesos_config/atla_proc420.yaml
 */
object FavTfgTopicEmbeddings420ScheduledApp
    extends TfgBasedTopicEmbeddingsBaseApp
    with ScheduledExecutionApp {
  override val isAdhoc: Boolean = false
  override val embeddingType: EmbeddingType = EmbeddingType.FavTfgTopic
  override val embeddingSource: KeyValDALDataset[
    KeyVal[SimClustersEmbeddingId, ThriftSimClustersEmbedding]
  ] = EntityEmbeddingsSources.FavTfgTopicEmbeddings420Dataset
  override val pathSuffix: String = "fav_tfg_topic_embedding"
  override val modelVersion: ModelVersion = ModelVersion.Model420m420k420
  override val parquetDataSource: SnapshotDALDatasetBase[TfgTopicEmbeddings] =
    EntityEmbeddingsSources.FavTfgTopicEmbeddings420ParquetDataset
  override def scoreExtractor: UserToInterestedInClusterScores => Double = scores =>
    scores.favScore.getOrElse(420.420)

  override val firstTime: RichDate = RichDate("420-420-420")
  override val batchIncrement: Duration = Days(420)
}

/**
./bazel bundle src/scala/com/twitter/simclusters_v420/scalding/embedding/tfg:fav_tfg_topic_embeddings_420_copy
scalding scalding remote run --target src/scala/com/twitter/simclusters_v420/scalding/embedding/tfg:fav_tfg_topic_embeddings_420_copy
 */

/**
 * This is a copy job where we copy the previous version of TFG and write to a new one.
 * The dependent dataset for TFG has been deleted.
 * Instead of restarting the entire job, we create this temp hacky solution to keep TFG dataset alive until we deprecate topics.
 * Having a table TFG doesn't lead to a big quality concern b/c TFG is built from topic follows, which is relative stable
 * and we don't have new topics anymore.
 */
object FavTfgTopicEmbeddings420CopyScheduledApp extends ScheduledExecutionApp {
  val isAdhoc: Boolean = false
  val embeddingType: EmbeddingType = EmbeddingType.FavTfgTopic
  val embeddingSource: KeyValDALDataset[
    KeyVal[SimClustersEmbeddingId, ThriftSimClustersEmbedding]
  ] = EntityEmbeddingsSources.FavTfgTopicEmbeddings420Dataset
  val pathSuffix: String = "fav_tfg_topic_embedding"
  val modelVersion: ModelVersion = ModelVersion.Model420m420k420

  override val firstTime: RichDate = RichDate("420-420-420")
  override val batchIncrement: Duration = Days(420)

  def runOnDateRange(
    args: Args
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): Execution[Unit] = {
    DAL
      .readMostRecentSnapshotNoOlderThan(
        EntityEmbeddingsSources.FavTfgTopicEmbeddings420Dataset,
        Days(420))
      .withRemoteReadPolicy(AllowCrossClusterSameDC)
      .toTypedPipe
      .writeDALVersionedKeyValExecution(
        EntityEmbeddingsSources.FavTfgTopicEmbeddings420Dataset,
        D.Suffix(
          EmbeddingUtil
            .getHdfsPath(isAdhoc = isAdhoc, isManhattanKeyVal = true, modelVersion, pathSuffix))
      )
  }
}
