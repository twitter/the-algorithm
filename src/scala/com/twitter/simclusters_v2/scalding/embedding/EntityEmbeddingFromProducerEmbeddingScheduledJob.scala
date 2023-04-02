package com.twitter.simclusters_v2.scalding.embedding

import com.twitter.onboarding.relevance.candidates.thriftscala.InterestBasedUserRecommendations
import com.twitter.onboarding.relevance.candidates.thriftscala.UTTInterest
import com.twitter.onboarding.relevance.source.UttAccountRecommendationsScalaDataset
import com.twitter.scalding.Args
import com.twitter.scalding.DateRange
import com.twitter.scalding.Days
import com.twitter.scalding.Duration
import com.twitter.scalding.Execution
import com.twitter.scalding.RichDate
import com.twitter.scalding.UniqueID
import com.twitter.scalding.typed.TypedPipe
import com.twitter.scalding.typed.UnsortedGrouped
import com.twitter.scalding_internal.dalv2.DAL
import com.twitter.scalding_internal.dalv2.DALWrite._
import com.twitter.scalding_internal.dalv2.remote_access.ExplicitLocation
import com.twitter.scalding_internal.dalv2.remote_access.ProcAtla
import com.twitter.scalding_internal.multiformat.format.keyval.KeyVal
import com.twitter.simclusters_v2.common.ModelVersions
import com.twitter.simclusters_v2.common.SimClustersEmbedding
import com.twitter.simclusters_v2.hdfs_sources.AdhocKeyValSources
import com.twitter.simclusters_v2.hdfs_sources.ProducerEmbeddingSources
import com.twitter.simclusters_v2.hdfs_sources.SemanticCoreEmbeddingsFromProducerScalaDataset
import com.twitter.simclusters_v2.scalding.embedding.common.EmbeddingUtil._
import com.twitter.simclusters_v2.thriftscala
import com.twitter.simclusters_v2.thriftscala.EmbeddingType
import com.twitter.simclusters_v2.thriftscala.InternalId
import com.twitter.simclusters_v2.thriftscala.ModelVersion
import com.twitter.simclusters_v2.thriftscala.SimClusterWithScore
import com.twitter.simclusters_v2.thriftscala.SimClustersEmbeddingId
import com.twitter.simclusters_v2.thriftscala.TopSimClustersWithScore
import com.twitter.wtf.scalding.jobs.common.AdhocExecutionApp
import com.twitter.wtf.scalding.jobs.common.ScheduledExecutionApp
import com.twitter.wtf.scalding.jobs.common.StatsUtil._
import java.util.TimeZone

/*
 $ ./bazel bundle src/scala/com/twitter/simclusters_v2/scalding/embedding:entity_embedding_from_producer_embedding_job
 $ capesospy-v2 update \
  --build_locally \
  --start_cron entity_embedding_from_producer_embedding_job src/scala/com/twitter/simclusters_v2/capesos_config/atla_proc3.yaml
 */
object EntityEmbeddingFromProducerEmbeddingScheduledJob extends ScheduledExecutionApp {
  override def firstTime: RichDate = RichDate("2019-10-16")

  override def batchIncrement: Duration = Days(7)

  override def runOnDateRange(
                               args: Args
                             )(
                               implicit dateRange: DateRange,
                               timeZone: TimeZone,
                               uniqueID: UniqueID
                             ): Execution[Unit] = {
    // parse args: modelVersion, topK
    val topK = args.getOrElse("top_k", "100").toInt
    // only support dec11 now since updated model is not productionized for producer embedding
    val modelVersion =
      ModelVersions.toModelVersion(
        args.getOrElse("model_version", ModelVersions.Model20M145KUpdated))

    val entityKnownForProducers =
      EntityEmbeddingFromProducerEmbeddingJob
        .getNormalizedEntityProducerMatrix(dateRange.embiggen(Days(7)))
        .count("num unique entity producer pairs").map {
        case (entityId, producerId, score) => (producerId, (entityId, score))
      }

    val favBasedEmbeddings = EntityEmbeddingFromProducerEmbeddingJob
      .computeEmbedding(
        ProducerEmbeddingSources.producerEmbeddingSourceLegacy(
          EmbeddingType.ProducerFavBasedSemanticCoreEntity,
          modelVersion)(dateRange.embiggen(Days(7))),
        entityKnownForProducers,
        topK,
        modelVersion,
        EmbeddingType.ProducerFavBasedSemanticCoreEntity
      ).toTypedPipe.count("follow_based_entity_count")

    val followBasedEmbeddings = EntityEmbeddingFromProducerEmbeddingJob
      .computeEmbedding(
        ProducerEmbeddingSources.producerEmbeddingSourceLegacy(
          EmbeddingType.ProducerFollowBasedSemanticCoreEntity,
          modelVersion)(dateRange.embiggen(Days(7))),
        entityKnownForProducers,
        topK,
        modelVersion,
        EmbeddingType.ProducerFollowBasedSemanticCoreEntity
      ).toTypedPipe.count("fav_based_entity_count")

    val embedding = favBasedEmbeddings ++ followBasedEmbeddings

    embedding
      .count("total_count")
      .map {
        case (embeddingId, embedding) => KeyVal(embeddingId, embedding)
      }.writeDALVersionedKeyValExecution(
      SemanticCoreEmbeddingsFromProducerScalaDataset,
      D.Suffix(getHdfsPath(isAdhoc = false, isManhattanKeyVal = true, modelVersion, "producer"))
    )

  }

}