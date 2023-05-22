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
import com.twitter.scalding_internal.dalv2.DALWrite.*
import com.twitter.scalding_internal.dalv2.remote_access.ExplicitLocation
import com.twitter.scalding_internal.dalv2.remote_access.ProcAtla
import com.twitter.scalding_internal.multiformat.format.keyval.KeyVal
import com.twitter.simclusters_v2.common.ModelVersions
import com.twitter.simclusters_v2.common.SimClustersEmbedding
import com.twitter.simclusters_v2.hdfs_sources.AdhocKeyValSources
import com.twitter.simclusters_v2.hdfs_sources.ProducerEmbeddingSources
import com.twitter.simclusters_v2.hdfs_sources.SemanticCoreEmbeddingsFromProducerScalaDataset
import com.twitter.simclusters_v2.scalding.embedding.common.EmbeddingUtil.*
import com.twitter.simclusters_v2.thriftscala
import com.twitter.simclusters_v2.thriftscala.EmbeddingType
import com.twitter.simclusters_v2.thriftscala.InternalId
import com.twitter.simclusters_v2.thriftscala.ModelVersion
import com.twitter.simclusters_v2.thriftscala.SimClusterWithScore
import com.twitter.simclusters_v2.thriftscala.SimClustersEmbeddingId
import com.twitter.simclusters_v2.thriftscala.TopSimClustersWithScore
import com.twitter.wtf.scalding.jobs.common.AdhocExecutionApp
import com.twitter.wtf.scalding.jobs.common.ScheduledExecutionApp
import com.twitter.wtf.scalding.jobs.common.StatsUtil.*

import java.util.TimeZone

/*
  $ ./bazel bundle src/scala/com/twitter/simclusters_v2/scalding/embedding:entity_embedding_from_producer_embedding-adhoc

  $ scalding remote run \
  --main-class com.twitter.simclusters_v2.scalding.embedding.EntityEmbeddingFromProducerEmbeddingAdhocJob \
  --target src/scala/com/twitter/simclusters_v2/scalding/embedding:entity_embedding_from_producer_embedding-adhoc \
  --user recos-platform \
  -- --date 2019-10-23 --model_version 20M_145K_updated
 */
object EntityEmbeddingFromProducerEmbeddingAdhocJob extends AdhocExecutionApp {
  override def runOnDateRange(
                               args: Args
                             )(
                               implicit dateRange: DateRange,
                               timeZone: TimeZone,
                               uniqueID: UniqueID
                             ): Execution[Unit] = {
    // step 1: read in (entity, producer) pairs and remove duplicates
    val topK = args.getOrElse("top_k", "100").toInt

    val modelVersion = ModelVersions.toModelVersion(
      args.getOrElse("model_version", ModelVersions.Model20M145KUpdated))

    val entityKnownForProducers =
      EntityEmbeddingFromProducerEmbeddingJob
        .getNormalizedEntityProducerMatrix(dateRange.embiggen(Days(7)))
        .count("num unique entity producer pairs").map {
        case (entityId, producerId, score) => (producerId, (entityId, score))
      }

    // step 2: read in producer to simclusters embeddings

    val producersEmbeddingsFollowBased =
      ProducerEmbeddingSources.producerEmbeddingSourceLegacy(
        EmbeddingType.ProducerFollowBasedSemanticCoreEntity,
        modelVersion)(dateRange.embiggen(Days(7)))

    val producersEmbeddingsFavBased =
      ProducerEmbeddingSources.producerEmbeddingSourceLegacy(
        EmbeddingType.ProducerFavBasedSemanticCoreEntity,
        modelVersion)(dateRange.embiggen(Days(7)))

    // step 3: join producer embedding with entity, producer pairs and reformat result into format [SimClustersEmbeddingId, SimClustersEmbedding]
    val producerBasedEntityEmbeddingsFollowBased =
      EntityEmbeddingFromProducerEmbeddingJob
        .computeEmbedding(
          producersEmbeddingsFollowBased,
          entityKnownForProducers,
          topK,
          modelVersion,
          EmbeddingType.ProducerFollowBasedSemanticCoreEntity).toTypedPipe.count(
        "follow_based_entity_count")

    val producerBasedEntityEmbeddingsFavBased =
      EntityEmbeddingFromProducerEmbeddingJob
        .computeEmbedding(
          producersEmbeddingsFavBased,
          entityKnownForProducers,
          topK,
          modelVersion,
          EmbeddingType.ProducerFavBasedSemanticCoreEntity).toTypedPipe.count(
        "fav_based_entity_count")

    val producerBasedEntityEmbeddings =
      producerBasedEntityEmbeddingsFollowBased ++ producerBasedEntityEmbeddingsFavBased

    // step 4 write results to file
    producerBasedEntityEmbeddings
      .count("total_count").writeExecution(
      AdhocKeyValSources.entityToClustersSource(
        getHdfsPath(isAdhoc = true, isManhattanKeyVal = true, modelVersion, "producer")))
  }

}