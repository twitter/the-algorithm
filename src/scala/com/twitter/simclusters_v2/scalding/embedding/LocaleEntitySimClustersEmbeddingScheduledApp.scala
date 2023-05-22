package com.twitter.simclusters_v2.scalding.embedding

import com.twitter.dal.client.dataset.KeyValDALDataset
import com.twitter.recos.entities.thriftscala.{Entity, Hashtag, SemanticCoreEntity}
import com.twitter.scalding.*
import com.twitter.scalding_internal.dalv2.DALWrite.*
import com.twitter.scalding_internal.multiformat.format.keyval.KeyVal
import com.twitter.simclusters_v2.common.{ModelVersions, SimClustersEmbedding}
import com.twitter.simclusters_v2.hdfs_sources.{AdhocKeyValSources, EntityEmbeddingsSources, InterestedInSources}
import com.twitter.simclusters_v2.hdfs_sources.presto_hdfs_sources.*
import com.twitter.simclusters_v2.scalding.embedding.LocaleEntitySimClustersEmbeddingsJob.*
import com.twitter.simclusters_v2.scalding.embedding.common.EmbeddingUtil.*
import com.twitter.simclusters_v2.scalding.embedding.common.EntityEmbeddingUtil.*
import com.twitter.simclusters_v2.scalding.embedding.common.{EmbeddingUtil, ExternalDataSources}
import com.twitter.simclusters_v2.scalding.embedding.common.SimClustersEmbeddingJob.*
import com.twitter.simclusters_v2.thriftscala.{SimClustersEmbedding as ThriftSimClustersEmbedding, *}
import com.twitter.wtf.entity_real_graph.common.EntityUtil
import com.twitter.wtf.entity_real_graph.thriftscala.{Edge, EntityType}
import com.twitter.wtf.scalding.jobs.common.{AdhocExecutionApp, DataSources, ScheduledExecutionApp}

import java.util.TimeZone


/**
 * $ ./bazel bundle src/scala/com/twitter/simclusters_v2/scalding/embedding:semantic_core_entity_embeddings_per_language_job
 * $ capesospy-v2 update \
  --build_locally \
  --start_cron semantic_core_entity_embeddings_per_language_job src/scala/com/twitter/simclusters_v2/capesos_config/atla_proc3.yaml
 */
object LocaleEntitySimClustersEmbeddingScheduledApp extends ScheduledExecutionApp {

  // Import implicits

  import EmbeddingUtil.*

  override val firstTime: RichDate = RichDate("2019-10-22")

  override val batchIncrement: Duration = Days(7)

  override def runOnDateRange(
    args: Args
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): Execution[Unit] = {

    val jobConfig = EntityEmbeddingsJobConfig(args, isAdhoc = false)

    val embeddingsDataset = EntityEmbeddingsSources.getEntityEmbeddingsDataset(
      jobConfig.entityType,
      ModelVersions.toKnownForModelVersion(jobConfig.modelVersion),
      isEmbeddingsPerLocale = true
    )

    val reverseIndexEmbeddingsDataset =
      EntityEmbeddingsSources.getReverseIndexedEntityEmbeddingsDataset(
        jobConfig.entityType,
        ModelVersions.toKnownForModelVersion(jobConfig.modelVersion),
        isEmbeddingsPerLocale = true
      )

    val userEntityMatrix: TypedPipe[(UserId, (Entity, Double))] =
      getUserEntityMatrix(
        jobConfig,
        DataSources.entityRealGraphAggregationDataSetSource(dateRange.embiggen(Days(7))),
        Some(ExternalDataSources.uttEntitiesSource())
      ).forceToDisk

    //determine which data source to use based on model version
    val simClustersSource = jobConfig.modelVersion match {
      case ModelVersion.Model20m145kUpdated =>
        InterestedInSources.simClustersInterestedInUpdatedSource(dateRange, timeZone)
      case modelVersion =>
        throw new IllegalArgumentException(
          s"SimClusters model version not supported ${modelVersion.name}")
    }

    val entityPerLanguage = userEntityMatrix.join(ExternalDataSources.userSource).map {
      case (userId, ((entity, score), (_, language))) =>
        ((entity, language), (userId, score))
    }

    val normalizedUserEntityMatrix =
      getNormalizedTransposeInputMatrix(entityPerLanguage, numReducers = Some(3000))

    val simClustersEmbedding = jobConfig.modelVersion match {
      case ModelVersion.Model20m145kUpdated =>
        computeEmbeddings(
          simClustersSource,
          normalizedUserEntityMatrix,
          scoreExtractors,
          ModelVersion.Model20m145kUpdated,
          toSimClustersEmbeddingId(ModelVersion.Model20m145kUpdated),
          numReducers = Some(8000)
        )
      case modelVersion =>
        throw new IllegalArgumentException(
          s"SimClusters model version not supported ${modelVersion.name}")
    }

    val topKEmbeddings =
      simClustersEmbedding.group.sortedReverseTake(jobConfig.topK)(Ordering.by(_._2))

    writeOutput(
      simClustersEmbedding,
      topKEmbeddings,
      jobConfig,
      embeddingsDataset,
      reverseIndexEmbeddingsDataset)
  }

  private def writeOutput(
    embeddings: TypedPipe[(SimClustersEmbeddingId, (ClusterId, EmbeddingScore))],
    topKEmbeddings: TypedPipe[(SimClustersEmbeddingId, Seq[(ClusterId, EmbeddingScore)])],
    jobConfig: EntityEmbeddingsJobConfig,
    clusterEmbeddingsDataset: KeyValDALDataset[
      KeyVal[SimClustersEmbeddingId, ThriftSimClustersEmbedding]
    ],
    entityEmbeddingsDataset: KeyValDALDataset[KeyVal[SimClustersEmbeddingId, InternalIdEmbedding]]
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone
  ): Execution[Unit] = {

    val thriftSimClustersEmbedding = topKEmbeddings
      .mapValues(SimClustersEmbedding.apply(_).toThrift)

    val writeSimClustersEmbeddingKeyValDataset =
      thriftSimClustersEmbedding
        .map {
          case (entityId, topSimClusters) => KeyVal(entityId, topSimClusters)
        }
        .writeDALVersionedKeyValExecution(
          clusterEmbeddingsDataset,
          D.Suffix(
            LocaleEntitySimClustersEmbeddingsJob.getHdfsPath(
              isAdhoc = false,
              isManhattanKeyVal = true,
              isReverseIndex = false,
              isLogFav = false,
              jobConfig.modelVersion,
              jobConfig.entityType))
        )

    val writeSimClustersEmbeddingDataset = thriftSimClustersEmbedding
      .map {
        case (embeddingId, embedding) => SimClustersEmbeddingWithId(embeddingId, embedding)
      }
      .writeDALSnapshotExecution(
        SemanticCorePerLanguageSimclustersEmbeddingsPrestoScalaDataset,
        D.Daily,
        D.Suffix(
          LocaleEntitySimClustersEmbeddingsJob.getHdfsPath(
            isAdhoc = false,
            isManhattanKeyVal = false,
            isReverseIndex = false,
            isLogFav = false,
            jobConfig.modelVersion,
            jobConfig.entityType)),
        D.EBLzo(),
        dateRange.end
      )

    val thriftReversedSimclustersEmbeddings =
      toReverseIndexSimClusterEmbedding(embeddings, jobConfig.topK)

    val writeReverseSimClustersEmbeddingKeyValDataset =
      thriftReversedSimclustersEmbeddings
        .map {
          case (embeddingId, internalIdsWithScore) =>
            KeyVal(embeddingId, internalIdsWithScore)
        }
        .writeDALVersionedKeyValExecution(
          entityEmbeddingsDataset,
          D.Suffix(
            LocaleEntitySimClustersEmbeddingsJob.getHdfsPath(
              isAdhoc = false,
              isManhattanKeyVal = true,
              isReverseIndex = true,
              isLogFav = false,
              jobConfig.modelVersion,
              jobConfig.entityType))
        )

    val writeReverseSimClustersEmbeddingDataset =
      thriftReversedSimclustersEmbeddings
        .map {
          case (embeddingId, embedding) => InternalIdEmbeddingWithId(embeddingId, embedding)
        }.writeDALSnapshotExecution(
          ReverseIndexSemanticCorePerLanguageSimclustersEmbeddingsPrestoScalaDataset,
          D.Daily,
          D.Suffix(
            LocaleEntitySimClustersEmbeddingsJob.getHdfsPath(
              isAdhoc = false,
              isManhattanKeyVal = false,
              isReverseIndex = true,
              isLogFav = false,
              jobConfig.modelVersion,
              jobConfig.entityType)),
          D.EBLzo(),
          dateRange.end
        )

    Execution
      .zip(
        writeSimClustersEmbeddingDataset,
        writeSimClustersEmbeddingKeyValDataset,
        writeReverseSimClustersEmbeddingDataset,
        writeReverseSimClustersEmbeddingKeyValDataset
      ).unit
  }
}


