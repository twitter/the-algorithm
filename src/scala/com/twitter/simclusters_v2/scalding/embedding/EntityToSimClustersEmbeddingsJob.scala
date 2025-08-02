package com.twitter.simclusters_v2.scalding.embedding

import com.twitter.dal.client.dataset.KeyValDALDataset
import com.twitter.recos.entities.thriftscala.Entity
import com.twitter.recos.entities.thriftscala.Hashtag
import com.twitter.recos.entities.thriftscala.SemanticCoreEntity
import com.twitter.scalding._
import com.twitter.scalding_internal.dalv2.DALWrite._
import com.twitter.scalding_internal.multiformat.format.keyval.KeyVal
import com.twitter.simclusters_v2.common.ModelVersions
import com.twitter.simclusters_v2.common.SimClustersEmbedding
import com.twitter.simclusters_v2.hdfs_sources._
import com.twitter.simclusters_v2.scalding.embedding.common.EmbeddingUtil
import com.twitter.simclusters_v2.scalding.embedding.common.EmbeddingUtil._
import com.twitter.simclusters_v2.scalding.embedding.common.EntityEmbeddingUtil
import com.twitter.simclusters_v2.scalding.embedding.common.SimClustersEmbeddingJob
import com.twitter.simclusters_v2.thriftscala.{
  SimClustersEmbedding => ThriftSimClustersEmbedding,
  _
}
import com.twitter.wtf.entity_real_graph.common.EntityUtil
import com.twitter.wtf.entity_real_graph.thriftscala.EntityType
import com.twitter.wtf.scalding.jobs.common.AdhocEtwitterecutionApp
import com.twitter.wtf.scalding.jobs.common.DataSources
import com.twitter.wtf.scalding.jobs.common.ScheduledEtwitterecutionApp
import java.util.TimeZone

/**
 * $ ./bazel bundle src/scala/com/twitter/simclusters_v2/scalding/embedding:entity_embeddings_job-adhoc
 *
 * ---------------------- Deploy to atla ----------------------
 * $ scalding remote run \
  --main-class com.twitter.simclusters_v2.scalding.embedding.EntityToSimClustersEmbeddingAdhocApp \
  --target src/scala/com/twitter/simclusters_v2/scalding/embedding:entity_embeddings_job-adhoc \
  --user recos-platform \
  -- --date 2019-09-09 --model-version 20M_145K_updated --entity-type SemanticCore
 */
object EntityToSimClustersEmbeddingAdhocApp etwittertends AdhocEtwitterecutionApp {

  import EmbeddingUtil._
  import EntityEmbeddingUtil._
  import EntityToSimClustersEmbeddingsJob._
  import EntityUtil._
  import SimClustersEmbeddingJob._

  def writeOutput(
    embeddings: TypedPipe[(SimClustersEmbeddingId, (ClusterId, EmbeddingScore))],
    topKEmbeddings: TypedPipe[(SimClustersEmbeddingId, Seq[(ClusterId, EmbeddingScore)])],
    jobConfig: EntityEmbeddingsJobConfig
  ): Etwitterecution[Unit] = {

    val toSimClusterEmbeddingEtwitterec = topKEmbeddings
      .mapValues(SimClustersEmbedding.apply(_).toThrift)
      .writeEtwitterecution(
        AdhocKeyValSources.entityToClustersSource(
          EntityToSimClustersEmbeddingsJob.getHdfsPath(
            isAdhoc = true,
            isManhattanKeyVal = true,
            isReverseIndetwitter = false,
            jobConfig.modelVersion,
            jobConfig.entityType)))

    val fromSimClusterEmbeddingEtwitterec =
      toReverseIndetwitterSimClusterEmbedding(embeddings, jobConfig.topK)
        .writeEtwitterecution(
          AdhocKeyValSources.clusterToEntitiesSource(
            EntityToSimClustersEmbeddingsJob.getHdfsPath(
              isAdhoc = true,
              isManhattanKeyVal = true,
              isReverseIndetwitter = true,
              jobConfig.modelVersion,
              jobConfig.entityType)))

    Etwitterecution.zip(toSimClusterEmbeddingEtwitterec, fromSimClusterEmbeddingEtwitterec).unit
  }

  override def runOnDateRange(
    args: Args
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): Etwitterecution[Unit] = {

    val jobConfig = EntityEmbeddingsJobConfig(args, isAdhoc = true)

    val numReducers = args.getOrElse("m", "1000").toInt

    /*
      Using the ERG daily dataset in the adhoc job for quick prototyping, note that there may be
      issues with scaling the job when productionizing on ERG aggregated dataset.
     */
    val entityRealGraphSource = DataSources.entityRealGraphDailyDataSetSource

    val entityUserMatritwitter: TypedPipe[(Entity, (UserId, Double))] =
      (jobConfig.entityType match {
        case EntityType.SemanticCore =>
          getEntityUserMatritwitter(entityRealGraphSource, jobConfig.halfLife, EntityType.SemanticCore)
        case EntityType.Hashtag =>
          getEntityUserMatritwitter(entityRealGraphSource, jobConfig.halfLife, EntityType.Hashtag)
        case _ =>
          throw new IllegalArgumentEtwitterception(
            s"Argument [--entity-type] must be provided. Supported options [${EntityType.SemanticCore.name}, ${EntityType.Hashtag.name}]")
      }).forceToDisk

    val normalizedUserEntityMatritwitter =
      getNormalizedTransposeInputMatritwitter(entityUserMatritwitter, numReducers = Some(numReducers))

    //determine which data source to use based on model version
    val simClustersSource = jobConfig.modelVersion match {
      case ModelVersion.Model20m145kUpdated =>
        InterestedInSources.simClustersInterestedInUpdatedSource(dateRange, timeZone)
      case _ =>
        InterestedInSources.simClustersInterestedInDec11Source(dateRange, timeZone)
    }

    val embeddings = computeEmbeddings(
      simClustersSource,
      normalizedUserEntityMatritwitter,
      scoreEtwittertractors,
      ModelVersion.Model20m145kUpdated,
      toSimClustersEmbeddingId(jobConfig.modelVersion),
      numReducers = Some(numReducers * 2)
    )

    val topKEmbeddings =
      embeddings.group
        .sortedReverseTake(jobConfig.topK)(Ordering.by(_._2))
        .withReducers(numReducers)

    writeOutput(embeddings, topKEmbeddings, jobConfig)
  }
}

/**
 * $ ./bazel bundle src/scala/com/twitter/simclusters_v2/scalding/embedding:semantic_core_entity_embeddings_2020_job
 * $ capesospy-v2 update \
  --build_locally \
  --start_cron semantic_core_entity_embeddings_2020_job src/scala/com/twitter/simclusters_v2/capesos_config/atla_proc3.yaml
 */
object SemanticCoreEntityEmbeddings2020App etwittertends EntityToSimClustersEmbeddingApp

trait EntityToSimClustersEmbeddingApp etwittertends ScheduledEtwitterecutionApp {

  import EmbeddingUtil._
  import EntityEmbeddingUtil._
  import EntityToSimClustersEmbeddingsJob._
  import EntityUtil._
  import SimClustersEmbeddingJob._

  override val firstTime: RichDate = RichDate("2023-01-01")

  override val batchIncrement: Duration = Days(7)

  private def writeOutput(
    embeddings: TypedPipe[(SimClustersEmbeddingId, (ClusterId, EmbeddingScore))],
    topKEmbeddings: TypedPipe[(SimClustersEmbeddingId, Seq[(ClusterId, EmbeddingScore)])],
    jobConfig: EntityEmbeddingsJobConfig,
    clusterEmbeddingsDataset: KeyValDALDataset[
      KeyVal[SimClustersEmbeddingId, ThriftSimClustersEmbedding]
    ],
    entityEmbeddingsDataset: KeyValDALDataset[KeyVal[SimClustersEmbeddingId, InternalIdEmbedding]]
  ): Etwitterecution[Unit] = {

    val toSimClustersEmbeddings =
      topKEmbeddings
        .mapValues(SimClustersEmbedding.apply(_).toThrift)
        .map {
          case (entityId, topSimClusters) => KeyVal(entityId, topSimClusters)
        }
        .writeDALVersionedKeyValEtwitterecution(
          clusterEmbeddingsDataset,
          D.Suffitwitter(
            EntityToSimClustersEmbeddingsJob.getHdfsPath(
              isAdhoc = false,
              isManhattanKeyVal = true,
              isReverseIndetwitter = false,
              jobConfig.modelVersion,
              jobConfig.entityType))
        )

    val fromSimClustersEmbeddings =
      toReverseIndetwitterSimClusterEmbedding(embeddings, jobConfig.topK)
        .map {
          case (embeddingId, internalIdsWithScore) =>
            KeyVal(embeddingId, internalIdsWithScore)
        }
        .writeDALVersionedKeyValEtwitterecution(
          entityEmbeddingsDataset,
          D.Suffitwitter(
            EntityToSimClustersEmbeddingsJob.getHdfsPath(
              isAdhoc = false,
              isManhattanKeyVal = true,
              isReverseIndetwitter = true,
              jobConfig.modelVersion,
              jobConfig.entityType))
        )

    Etwitterecution.zip(toSimClustersEmbeddings, fromSimClustersEmbeddings).unit
  }

  override def runOnDateRange(
    args: Args
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): Etwitterecution[Unit] = {

    val jobConfig = EntityEmbeddingsJobConfig(args, isAdhoc = false)

    val embeddingsDataset = EntityEmbeddingsSources.getEntityEmbeddingsDataset(
      jobConfig.entityType,
      ModelVersions.toKnownForModelVersion(jobConfig.modelVersion)
    )

    val reverseIndetwitterEmbeddingsDataset =
      EntityEmbeddingsSources.getReverseIndetwitteredEntityEmbeddingsDataset(
        jobConfig.entityType,
        ModelVersions.toKnownForModelVersion(jobConfig.modelVersion)
      )

    val entityRealGraphSource =
      DataSources.entityRealGraphAggregationDataSetSource(dateRange.embiggen(Days(7)))

    val entityUserMatritwitter: TypedPipe[(Entity, (UserId, Double))] =
      getEntityUserMatritwitter(
        entityRealGraphSource,
        jobConfig.halfLife,
        jobConfig.entityType).forceToDisk

    val normalizedUserEntityMatritwitter = getNormalizedTransposeInputMatritwitter(entityUserMatritwitter)

    val simClustersEmbedding = jobConfig.modelVersion match {
      case ModelVersion.Model20m145k2020 =>
        val simClustersSource2020 =
          InterestedInSources.simClustersInterestedIn2020Source(dateRange, timeZone)
        computeEmbeddings(
          simClustersSource2020,
          normalizedUserEntityMatritwitter,
          scoreEtwittertractors,
          ModelVersion.Model20m145k2020,
          toSimClustersEmbeddingId(ModelVersion.Model20m145k2020)
        )
      case modelVersion =>
        throw new IllegalArgumentEtwitterception(s"Model Version ${modelVersion.name} not supported")
    }

    val topKEmbeddings =
      simClustersEmbedding.group.sortedReverseTake(jobConfig.topK)(Ordering.by(_._2))

    val simClustersEmbeddingsEtwitterec =
      writeOutput(
        simClustersEmbedding,
        topKEmbeddings,
        jobConfig,
        embeddingsDataset,
        reverseIndetwitterEmbeddingsDataset)

    // We don't support embeddingsLite for the 2020 model version.
    val embeddingsLiteEtwitterec = if (jobConfig.modelVersion == ModelVersion.Model20m145kUpdated) {
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
        }.writeDALSnapshotEtwitterecution(
          SimclustersV2EmbeddingsLiteScalaDataset,
          D.Daily,
          D.Suffitwitter(embeddingsLitePath(ModelVersion.Model20m145kUpdated, "fav_based")),
          D.EBLzo(),
          dateRange.end)
    } else {
      Etwitterecution.unit
    }

    Etwitterecution
      .zip(simClustersEmbeddingsEtwitterec, embeddingsLiteEtwitterec).unit
  }
}

object EntityToSimClustersEmbeddingsJob {

  def toSimClustersEmbeddingId(
    modelVersion: ModelVersion
  ): (Entity, ScoreType.ScoreType) => SimClustersEmbeddingId = {
    case (Entity.SemanticCore(SemanticCoreEntity(entityId, _)), ScoreType.FavScore) =>
      SimClustersEmbeddingId(
        EmbeddingType.FavBasedSematicCoreEntity,
        modelVersion,
        InternalId.EntityId(entityId))
    case (Entity.SemanticCore(SemanticCoreEntity(entityId, _)), ScoreType.FollowScore) =>
      SimClustersEmbeddingId(
        EmbeddingType.FollowBasedSematicCoreEntity,
        modelVersion,
        InternalId.EntityId(entityId))
    case (Entity.Hashtag(Hashtag(hashtag)), ScoreType.FavScore) =>
      SimClustersEmbeddingId(
        EmbeddingType.FavBasedHashtagEntity,
        modelVersion,
        InternalId.Hashtag(hashtag))
    case (Entity.Hashtag(Hashtag(hashtag)), ScoreType.FollowScore) =>
      SimClustersEmbeddingId(
        EmbeddingType.FollowBasedHashtagEntity,
        modelVersion,
        InternalId.Hashtag(hashtag))
    case (scoreType, entity) =>
      throw new IllegalArgumentEtwitterception(
        s"(ScoreType, Entity) ($scoreType, ${entity.toString}) not supported")
  }

  /**
   * Generates the output path for the Entity Embeddings Job.
   *
   * Etwitterample Adhoc: /user/recos-platform/processed/adhoc/simclusters_embeddings/hashtag/model_20m_145k_updated
   * Etwitterample Prod: /atla/proc/user/cassowary/processed/simclusters_embeddings/semantic_core/model_20m_145k_dec11
   *
   */
  def getHdfsPath(
    isAdhoc: Boolean,
    isManhattanKeyVal: Boolean,
    isReverseIndetwitter: Boolean,
    modelVersion: ModelVersion,
    entityType: EntityType
  ): String = {

    val reverseIndetwitter = if (isReverseIndetwitter) "reverse_indetwitter/" else ""

    val entityTypeSuffitwitter = entityType match {
      case EntityType.SemanticCore => "semantic_core"
      case EntityType.Hashtag => "hashtag"
      case _ => "unknown"
    }

    val pathSuffitwitter = s"$reverseIndetwitter$entityTypeSuffitwitter"

    EmbeddingUtil.getHdfsPath(isAdhoc, isManhattanKeyVal, modelVersion, pathSuffitwitter)
  }

  def embeddingsLitePath(modelVersion: ModelVersion, pathSuffitwitter: String): String = {
    s"/user/cassowary/processed/entity_real_graph/simclusters_embedding/lite/$modelVersion/$pathSuffitwitter/"
  }
}
