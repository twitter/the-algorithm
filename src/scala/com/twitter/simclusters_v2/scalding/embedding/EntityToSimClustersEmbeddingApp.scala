package com.twitter.simclusters_v2.scalding.embedding

import com.twitter.dal.client.dataset.KeyValDALDataset
import com.twitter.recos.entities.thriftscala.{Entity, Hashtag, SemanticCoreEntity}
import com.twitter.scalding.*
import com.twitter.scalding_internal.dalv2.DALWrite.*
import com.twitter.scalding_internal.multiformat.format.keyval.KeyVal
import com.twitter.simclusters_v2.common.{ModelVersions, SimClustersEmbedding}
import com.twitter.simclusters_v2.hdfs_sources.*
import com.twitter.simclusters_v2.scalding.embedding.common.EmbeddingUtil.*
import com.twitter.simclusters_v2.scalding.embedding.common.{EmbeddingUtil, EntityEmbeddingUtil, SimClustersEmbeddingJob}
import com.twitter.simclusters_v2.thriftscala.{SimClustersEmbedding as ThriftSimClustersEmbedding, *}
import com.twitter.wtf.entity_real_graph.common.EntityUtil
import com.twitter.wtf.entity_real_graph.thriftscala.EntityType
import com.twitter.wtf.scalding.jobs.common.{AdhocExecutionApp, DataSources, ScheduledExecutionApp}

import java.util.TimeZone


trait EntityToSimClustersEmbeddingApp extends ScheduledExecutionApp {

  import EmbeddingUtil.*
  import EntityEmbeddingUtil.*
  import EntityToSimClustersEmbeddingsJob.*
  import SimClustersEmbeddingJob.*

  override val firstTime: RichDate = RichDate("2023-01-01")

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
      ModelVersions.toKnownForModelVersion(jobConfig.modelVersion)
    )

    val reverseIndexEmbeddingsDataset =
      EntityEmbeddingsSources.getReverseIndexedEntityEmbeddingsDataset(
        jobConfig.entityType,
        ModelVersions.toKnownForModelVersion(jobConfig.modelVersion)
      )

    val entityRealGraphSource =
      DataSources.entityRealGraphAggregationDataSetSource(dateRange.embiggen(Days(7)))

    val entityUserMatrix: TypedPipe[(Entity, (UserId, Double))] =
      getEntityUserMatrix(
        entityRealGraphSource,
        jobConfig.halfLife,
        jobConfig.entityType).forceToDisk

    val normalizedUserEntityMatrix = getNormalizedTransposeInputMatrix(entityUserMatrix)

    val simClustersEmbedding = jobConfig.modelVersion match {
      case ModelVersion.Model20m145k2020 =>
        val simClustersSource2020 =
          InterestedInSources.simClustersInterestedIn2020Source(dateRange, timeZone)
        computeEmbeddings(
          simClustersSource2020,
          normalizedUserEntityMatrix,
          scoreExtractors,
          ModelVersion.Model20m145k2020,
          toSimClustersEmbeddingId(ModelVersion.Model20m145k2020)
        )
      case modelVersion =>
        throw new IllegalArgumentException(s"Model Version ${modelVersion.name} not supported")
    }

    val topKEmbeddings =
      simClustersEmbedding.group.sortedReverseTake(jobConfig.topK)(Ordering.by(_._2))

    val simClustersEmbeddingsExec =
      writeOutput(
        simClustersEmbedding,
        topKEmbeddings,
        jobConfig,
        embeddingsDataset,
        reverseIndexEmbeddingsDataset)

    // We don't support embeddingsLite for the 2020 model version.
    val embeddingsLiteExec = if (jobConfig.modelVersion == ModelVersion.Model20m145kUpdated) {
      topKEmbeddings
        .collect {
          case (
                SimClustersEmbeddingId(
                  EmbeddingType.FavBasedSematicCoreEntity,
                  ModelVersion.Model20m145kUpdated,
                  InternalId.EntityId(entityId)),
                clustersWithScores) =>
            entityId -> clustersWithScores
        }
        .flatMap {
          case (entityId, clustersWithScores) =>
            clustersWithScores.map {
              case (clusterId, score) => EmbeddingsLite(entityId, clusterId, score)
            }
          case _ => Nil
        }.writeDALSnapshotExecution(
          SimclustersV2EmbeddingsLiteScalaDataset,
          D.Daily,
          D.Suffix(embeddingsLitePath(ModelVersion.Model20m145kUpdated, "fav_based")),
          D.EBLzo(),
          dateRange.end)
    } else {
      Execution.unit
    }

    Execution
      .zip(simClustersEmbeddingsExec, embeddingsLiteExec).unit
  }

  private def writeOutput(
    embeddings: TypedPipe[(SimClustersEmbeddingId, (ClusterId, EmbeddingScore))],
    topKEmbeddings: TypedPipe[(SimClustersEmbeddingId, Seq[(ClusterId, EmbeddingScore)])],
    jobConfig: EntityEmbeddingsJobConfig,
    clusterEmbeddingsDataset: KeyValDALDataset[
      KeyVal[SimClustersEmbeddingId, ThriftSimClustersEmbedding]
    ],
    entityEmbeddingsDataset: KeyValDALDataset[KeyVal[SimClustersEmbeddingId, InternalIdEmbedding]]
  ): Execution[Unit] = {

    val toSimClustersEmbeddings =
      topKEmbeddings
        .mapValues(SimClustersEmbedding.apply(_).toThrift)
        .map {
          case (entityId, topSimClusters) => KeyVal(entityId, topSimClusters)
        }
        .writeDALVersionedKeyValExecution(
          clusterEmbeddingsDataset,
          D.Suffix(
            EntityToSimClustersEmbeddingsJob.getHdfsPath(
              isAdhoc = false,
              isManhattanKeyVal = true,
              isReverseIndex = false,
              jobConfig.modelVersion,
              jobConfig.entityType))
        )

    val fromSimClustersEmbeddings =
      toReverseIndexSimClusterEmbedding(embeddings, jobConfig.topK)
        .map {
          case (embeddingId, internalIdsWithScore) =>
            KeyVal(embeddingId, internalIdsWithScore)
        }
        .writeDALVersionedKeyValExecution(
          entityEmbeddingsDataset,
          D.Suffix(
            EntityToSimClustersEmbeddingsJob.getHdfsPath(
              isAdhoc = false,
              isManhattanKeyVal = true,
              isReverseIndex = true,
              jobConfig.modelVersion,
              jobConfig.entityType))
        )

    Execution.zip(toSimClustersEmbeddings, fromSimClustersEmbeddings).unit
  }
}


