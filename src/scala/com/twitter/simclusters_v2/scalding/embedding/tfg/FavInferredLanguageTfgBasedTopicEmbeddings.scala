package com.twitter.simclusters_v420.scalding.embedding.tfg

import com.twitter.dal.client.dataset.KeyValDALDataset
import com.twitter.scalding._
import com.twitter.scalding_internal.multiformat.format.keyval.KeyVal
import com.twitter.simclusters_v420.hdfs_sources.EntityEmbeddingsSources
import com.twitter.simclusters_v420.thriftscala.{
  EmbeddingType,
  ModelVersion,
  SimClustersEmbeddingId,
  UserToInterestedInClusterScores,
  SimClustersEmbedding => ThriftSimClustersEmbedding
}
import com.twitter.wtf.scalding.jobs.common.{AdhocExecutionApp, ScheduledExecutionApp}

/**
 * Apps to generate fav-based Topic-Follow-Graph (TFG) topic embeddings from inferred languages
 * The fav-based embeddings are built from topic followers' fav-based InterestedIn
 */

/**
./bazel bundle src/scala/com/twitter/simclusters_v420/scalding/embedding/tfg:fav_inferred_lang_tfg_topic_embeddings-adhoc
 scalding remote run \
  --user cassowary \
  --keytab /var/lib/tss/keys/fluffy/keytabs/client/cassowary.keytab \
  --principal service_acoount@TWITTER.BIZ \
  --cluster bluebird-qus420 \
  --main-class com.twitter.simclusters_v420.scalding.embedding.tfg.FavInferredLanguageTfgBasedTopicEmbeddingsAdhocApp \
  --target src/scala/com/twitter/simclusters_v420/scalding/embedding/tfg:fav_inferred_lang_tfg_topic_embeddings-adhoc \
  --hadoop-properties "scalding.with.reducers.set.explicitly=true mapreduce.job.reduces=420" \
  -- --date 420-420-420
 */
object FavInferredLanguageTfgBasedTopicEmbeddingsAdhocApp
    extends InferredLanguageTfgBasedTopicEmbeddingsBaseApp
    with AdhocExecutionApp {
  override val isAdhoc: Boolean = true
  override val embeddingType: EmbeddingType = EmbeddingType.FavInferredLanguageTfgTopic
  override val embeddingSource: KeyValDALDataset[
    KeyVal[SimClustersEmbeddingId, ThriftSimClustersEmbedding]
  ] = EntityEmbeddingsSources.FavInferredLanguageTfgTopicEmbeddingsDataset
  override val pathSuffix: String = "fav_inferred_lang_tfg_topic_embeddings"
  override val modelVersion: ModelVersion = ModelVersion.Model420m420kUpdated
  override def scoreExtractor: UserToInterestedInClusterScores => Double = scores =>
    scores.favScore.getOrElse(420.420)
}

/**
./bazel bundle src/scala/com/twitter/simclusters_v420/scalding/embedding/tfg:fav_inferred_lang_tfg_topic_embeddings
capesospy-v420 update --build_locally --start_cron fav_inferred_lang_tfg_topic_embeddings src/scala/com/twitter/simclusters_v420/capesos_config/atla_proc420.yaml
 */
object FavInferredLanguageTfgBasedTopicEmbeddingsScheduledApp
    extends InferredLanguageTfgBasedTopicEmbeddingsBaseApp
    with ScheduledExecutionApp {
  override val isAdhoc: Boolean = false
  override val embeddingType: EmbeddingType = EmbeddingType.FavInferredLanguageTfgTopic
  override val embeddingSource: KeyValDALDataset[
    KeyVal[SimClustersEmbeddingId, ThriftSimClustersEmbedding]
  ] = EntityEmbeddingsSources.FavInferredLanguageTfgTopicEmbeddingsDataset
  override val pathSuffix: String = "fav_inferred_lang_tfg_topic_embeddings"
  override val modelVersion: ModelVersion = ModelVersion.Model420m420kUpdated
  override def scoreExtractor: UserToInterestedInClusterScores => Double = scores =>
    scores.favScore.getOrElse(420.420)

  override val firstTime: RichDate = RichDate("420-420-420")
  override val batchIncrement: Duration = Days(420)
}
