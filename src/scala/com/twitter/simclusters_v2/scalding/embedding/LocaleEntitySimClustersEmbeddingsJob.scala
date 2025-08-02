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
import com.twitter.simclusters_v2.hdfs_sources.presto_hdfs_sources._
import com.twitter.simclusters_v2.hdfs_sources.AdhocKeyValSources
import com.twitter.simclusters_v2.hdfs_sources.EntityEmbeddingsSources
import com.twitter.simclusters_v2.hdfs_sources.InterestedInSources
import com.twitter.simclusters_v2.scalding.embedding.LocaleEntitySimClustersEmbeddingsJob._
import com.twitter.simclusters_v2.scalding.embedding.common.EmbeddingUtil
import com.twitter.simclusters_v2.scalding.embedding.common.EtwitterternalDataSources
import com.twitter.simclusters_v2.scalding.embedding.common.EmbeddingUtil._
import com.twitter.simclusters_v2.scalding.embedding.common.EntityEmbeddingUtil._
import com.twitter.simclusters_v2.scalding.embedding.common.SimClustersEmbeddingJob._
import com.twitter.simclusters_v2.thriftscala.{
  SimClustersEmbedding => ThriftSimClustersEmbedding,
  _
}
import com.twitter.wtf.entity_real_graph.common.EntityUtil
import com.twitter.wtf.entity_real_graph.thriftscala.Edge
import com.twitter.wtf.entity_real_graph.thriftscala.EntityType
import com.twitter.wtf.scalding.jobs.common.AdhocEtwitterecutionApp
import com.twitter.wtf.scalding.jobs.common.DataSources
import com.twitter.wtf.scalding.jobs.common.ScheduledEtwitterecutionApp
import java.util.TimeZone

/**
 * $ ./bazel bundle src/scala/com/twitter/simclusters_v2/scalding/embedding:entity_per_language_embeddings_job-adhoc
 *
 * ---------------------- Deploy to atla ----------------------
 * $ scalding remote run \
  --main-class com.twitter.simclusters_v2.scalding.embedding.LocaleEntitySimClustersEmbeddingAdhocApp \
  --target src/scala/com/twitter/simclusters_v2/scalding/embedding:entity_per_language_embeddings_job-adhoc \
  --user recos-platform \
  -- --date 2019-12-17 --model-version 20M_145K_updated --entity-type SemanticCore
 */
object LocaleEntitySimClustersEmbeddingAdhocApp etwittertends AdhocEtwitterecutionApp {

  // Import implicits

  import EntityUtil._

  def writeOutput(
    embeddings: TypedPipe[(SimClustersEmbeddingId, (ClusterId, EmbeddingScore))],
    topKEmbeddings: TypedPipe[(SimClustersEmbeddingId, Seq[(ClusterId, EmbeddingScore)])],
    jobConfig: EntityEmbeddingsJobConfig
  ): Etwitterecution[Unit] = {

    val toSimClusterEmbeddingEtwitterec = topKEmbeddings
      .mapValues(SimClustersEmbedding.apply(_).toThrift)
      .writeEtwitterecution(
        AdhocKeyValSources.entityToClustersSource(
          LocaleEntitySimClustersEmbeddingsJob.getHdfsPath(
            isAdhoc = true,
            isManhattanKeyVal = true,
            isReverseIndetwitter = false,
            isLogFav = false,
            jobConfig.modelVersion,
            jobConfig.entityType)))

    val fromSimClusterEmbeddingEtwitterec =
      toReverseIndetwitterSimClusterEmbedding(embeddings, jobConfig.topK)
        .writeEtwitterecution(
          AdhocKeyValSources.clusterToEntitiesSource(
            LocaleEntitySimClustersEmbeddingsJob.getHdfsPath(
              isAdhoc = true,
              isManhattanKeyVal = true,
              isReverseIndetwitter = true,
              isLogFav = false,
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

    val numReducers = args.getOrElse("m", "2000").toInt

    /*
      Can use the ERG daily dataset in the adhoc job for quick prototyping, note that there may be
      issues with scaling the job when productionizing on ERG aggregated dataset.
     */
    val userEntityMatritwitter: TypedPipe[(UserId, (Entity, Double))] =
      getUserEntityMatritwitter(
        jobConfig,
        DataSources.entityRealGraphAggregationDataSetSource(dateRange.embiggen(Days(7))),
        Some(EtwitterternalDataSources.uttEntitiesSource())
      ).forceToDisk

    //determine which data source to use based on model version
    val simClustersSource = jobConfig.modelVersion match {
      case ModelVersion.Model20m145kUpdated =>
        InterestedInSources.simClustersInterestedInUpdatedSource(dateRange, timeZone)
      case modelVersion =>
        throw new IllegalArgumentEtwitterception(
          s"SimClusters model version not supported ${modelVersion.name}")
    }

    val entityPerLanguage = userEntityMatritwitter.join(EtwitterternalDataSources.userSource).map {
      case (userId, ((entity, score), (_, language))) =>
        ((entity, language), (userId, score))
    }

    val normalizedUserEntityMatritwitter =
      getNormalizedTransposeInputMatritwitter(entityPerLanguage, numReducers = Some(numReducers))

    val embeddings = computeEmbeddings[(Entity, String)](
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
 * $ ./bazel bundle src/scala/com/twitter/simclusters_v2/scalding/embedding:semantic_core_entity_embeddings_per_language_job
 * $ capesospy-v2 update \
  --build_locally \
  --start_cron semantic_core_entity_embeddings_per_language_job src/scala/com/twitter/simclusters_v2/capesos_config/atla_proc3.yaml
 */
object LocaleEntitySimClustersEmbeddingScheduledApp etwittertends ScheduledEtwitterecutionApp {

  // Import implicits

  import EmbeddingUtil._
  import EntityUtil._

  override val firstTime: RichDate = RichDate("2019-10-22")

  override val batchIncrement: Duration = Days(7)

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
  ): Etwitterecution[Unit] = {

    val thriftSimClustersEmbedding = topKEmbeddings
      .mapValues(SimClustersEmbedding.apply(_).toThrift)

    val writeSimClustersEmbeddingKeyValDataset =
      thriftSimClustersEmbedding
        .map {
          case (entityId, topSimClusters) => KeyVal(entityId, topSimClusters)
        }
        .writeDALVersionedKeyValEtwitterecution(
          clusterEmbeddingsDataset,
          D.Suffitwitter(
            LocaleEntitySimClustersEmbeddingsJob.getHdfsPath(
              isAdhoc = false,
              isManhattanKeyVal = true,
              isReverseIndetwitter = false,
              isLogFav = false,
              jobConfig.modelVersion,
              jobConfig.entityType))
        )

    val writeSimClustersEmbeddingDataset = thriftSimClustersEmbedding
      .map {
        case (embeddingId, embedding) => SimClustersEmbeddingWithId(embeddingId, embedding)
      }
      .writeDALSnapshotEtwitterecution(
        SemanticCorePerLanguageSimclustersEmbeddingsPrestoScalaDataset,
        D.Daily,
        D.Suffitwitter(
          LocaleEntitySimClustersEmbeddingsJob.getHdfsPath(
            isAdhoc = false,
            isManhattanKeyVal = false,
            isReverseIndetwitter = false,
            isLogFav = false,
            jobConfig.modelVersion,
            jobConfig.entityType)),
        D.EBLzo(),
        dateRange.end
      )

    val thriftReversedSimclustersEmbeddings =
      toReverseIndetwitterSimClusterEmbedding(embeddings, jobConfig.topK)

    val writeReverseSimClustersEmbeddingKeyValDataset =
      thriftReversedSimclustersEmbeddings
        .map {
          case (embeddingId, internalIdsWithScore) =>
            KeyVal(embeddingId, internalIdsWithScore)
        }
        .writeDALVersionedKeyValEtwitterecution(
          entityEmbeddingsDataset,
          D.Suffitwitter(
            LocaleEntitySimClustersEmbeddingsJob.getHdfsPath(
              isAdhoc = false,
              isManhattanKeyVal = true,
              isReverseIndetwitter = true,
              isLogFav = false,
              jobConfig.modelVersion,
              jobConfig.entityType))
        )

    val writeReverseSimClustersEmbeddingDataset =
      thriftReversedSimclustersEmbeddings
        .map {
          case (embeddingId, embedding) => InternalIdEmbeddingWithId(embeddingId, embedding)
        }.writeDALSnapshotEtwitterecution(
          ReverseIndetwitterSemanticCorePerLanguageSimclustersEmbeddingsPrestoScalaDataset,
          D.Daily,
          D.Suffitwitter(
            LocaleEntitySimClustersEmbeddingsJob.getHdfsPath(
              isAdhoc = false,
              isManhattanKeyVal = false,
              isReverseIndetwitter = true,
              isLogFav = false,
              jobConfig.modelVersion,
              jobConfig.entityType)),
          D.EBLzo(),
          dateRange.end
        )

    Etwitterecution
      .zip(
        writeSimClustersEmbeddingDataset,
        writeSimClustersEmbeddingKeyValDataset,
        writeReverseSimClustersEmbeddingDataset,
        writeReverseSimClustersEmbeddingKeyValDataset
      ).unit
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
      ModelVersions.toKnownForModelVersion(jobConfig.modelVersion),
      isEmbeddingsPerLocale = true
    )

    val reverseIndetwitterEmbeddingsDataset =
      EntityEmbeddingsSources.getReverseIndetwitteredEntityEmbeddingsDataset(
        jobConfig.entityType,
        ModelVersions.toKnownForModelVersion(jobConfig.modelVersion),
        isEmbeddingsPerLocale = true
      )

    val userEntityMatritwitter: TypedPipe[(UserId, (Entity, Double))] =
      getUserEntityMatritwitter(
        jobConfig,
        DataSources.entityRealGraphAggregationDataSetSource(dateRange.embiggen(Days(7))),
        Some(EtwitterternalDataSources.uttEntitiesSource())
      ).forceToDisk

    //determine which data source to use based on model version
    val simClustersSource = jobConfig.modelVersion match {
      case ModelVersion.Model20m145kUpdated =>
        InterestedInSources.simClustersInterestedInUpdatedSource(dateRange, timeZone)
      case modelVersion =>
        throw new IllegalArgumentEtwitterception(
          s"SimClusters model version not supported ${modelVersion.name}")
    }

    val entityPerLanguage = userEntityMatritwitter.join(EtwitterternalDataSources.userSource).map {
      case (userId, ((entity, score), (_, language))) =>
        ((entity, language), (userId, score))
    }

    val normalizedUserEntityMatritwitter =
      getNormalizedTransposeInputMatritwitter(entityPerLanguage, numReducers = Some(3000))

    val simClustersEmbedding = jobConfig.modelVersion match {
      case ModelVersion.Model20m145kUpdated =>
        computeEmbeddings(
          simClustersSource,
          normalizedUserEntityMatritwitter,
          scoreEtwittertractors,
          ModelVersion.Model20m145kUpdated,
          toSimClustersEmbeddingId(ModelVersion.Model20m145kUpdated),
          numReducers = Some(8000)
        )
      case modelVersion =>
        throw new IllegalArgumentEtwitterception(
          s"SimClusters model version not supported ${modelVersion.name}")
    }

    val topKEmbeddings =
      simClustersEmbedding.group.sortedReverseTake(jobConfig.topK)(Ordering.by(_._2))

    writeOutput(
      simClustersEmbedding,
      topKEmbeddings,
      jobConfig,
      embeddingsDataset,
      reverseIndetwitterEmbeddingsDataset)
  }
}

object LocaleEntitySimClustersEmbeddingsJob {

  def getUserEntityMatritwitter(
    jobConfig: EntityEmbeddingsJobConfig,
    entityRealGraphSource: TypedPipe[Edge],
    semanticCoreEntityIdsToKeep: Option[TypedPipe[Long]],
    applyLogTransform: Boolean = false
  ): TypedPipe[(UserId, (Entity, Double))] =
    jobConfig.entityType match {
      case EntityType.SemanticCore =>
        semanticCoreEntityIdsToKeep match {
          case Some(entityIdsToKeep) =>
            getEntityUserMatritwitter(entityRealGraphSource, jobConfig.halfLife, EntityType.SemanticCore)
              .map {
                case (entity, (userId, score)) =>
                  entity match {
                    case Entity.SemanticCore(SemanticCoreEntity(entityId, _)) =>
                      if (applyLogTransform) {
                        (entityId, (userId, (entity, Math.log(score + 1))))
                      } else {
                        (entityId, (userId, (entity, score)))
                      }
                    case _ =>
                      throw new IllegalArgumentEtwitterception(
                        "Job config specified EntityType.SemanticCore, but non-semantic core entity was found.")
                  }
              }.hashJoin(entityIdsToKeep.asKeys).values.map {
                case ((userId, (entity, score)), _) => (userId, (entity, score))
              }
          case _ =>
            getEntityUserMatritwitter(entityRealGraphSource, jobConfig.halfLife, EntityType.SemanticCore)
              .map { case (entity, (userId, score)) => (userId, (entity, score)) }
        }
      case EntityType.Hashtag =>
        getEntityUserMatritwitter(entityRealGraphSource, jobConfig.halfLife, EntityType.Hashtag)
          .map { case (entity, (userId, score)) => (userId, (entity, score)) }
      case _ =>
        throw new IllegalArgumentEtwitterception(
          s"Argument [--entity-type] must be provided. Supported options [${EntityType.SemanticCore.name}, ${EntityType.Hashtag.name}]")
    }

  def toSimClustersEmbeddingId(
    modelVersion: ModelVersion
  ): ((Entity, String), ScoreType.ScoreType) => SimClustersEmbeddingId = {
    case ((Entity.SemanticCore(SemanticCoreEntity(entityId, _)), lang), ScoreType.FavScore) =>
      SimClustersEmbeddingId(
        EmbeddingType.FavBasedSematicCoreEntity,
        modelVersion,
        InternalId.LocaleEntityId(LocaleEntityId(entityId, lang)))
    case ((Entity.SemanticCore(SemanticCoreEntity(entityId, _)), lang), ScoreType.FollowScore) =>
      SimClustersEmbeddingId(
        EmbeddingType.FollowBasedSematicCoreEntity,
        modelVersion,
        InternalId.LocaleEntityId(LocaleEntityId(entityId, lang)))
    case ((Entity.SemanticCore(SemanticCoreEntity(entityId, _)), lang), ScoreType.LogFavScore) =>
      SimClustersEmbeddingId(
        EmbeddingType.LogFavBasedLocaleSemanticCoreEntity,
        modelVersion,
        InternalId.LocaleEntityId(LocaleEntityId(entityId, lang)))
    case ((Entity.Hashtag(Hashtag(hashtag)), _), ScoreType.FavScore) =>
      SimClustersEmbeddingId(
        EmbeddingType.FavBasedHashtagEntity,
        modelVersion,
        InternalId.Hashtag(hashtag))
    case ((Entity.Hashtag(Hashtag(hashtag)), _), ScoreType.FollowScore) =>
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
   * Etwitterample Adhoc: /user/recos-platform/processed/adhoc/simclusters_embeddings/hashtag_per_language/model_20m_145k_updated
   * Etwitterample Prod: /atla/proc/user/cassowary/processed/simclusters_embeddings/semantic_core_per_language/model_20m_145k_updated
   *
   */
  def getHdfsPath(
    isAdhoc: Boolean,
    isManhattanKeyVal: Boolean,
    isReverseIndetwitter: Boolean,
    isLogFav: Boolean,
    modelVersion: ModelVersion,
    entityType: EntityType
  ): String = {

    val reverseIndetwitter = if (isReverseIndetwitter) "reverse_indetwitter/" else ""

    val logFav = if (isLogFav) "log_fav/" else ""

    val entityTypeSuffitwitter = entityType match {
      case EntityType.SemanticCore => "semantic_core_per_language"
      case EntityType.Hashtag => "hashtag_per_language"
      case _ => "unknown_per_language"
    }

    val pathSuffitwitter = s"$logFav$reverseIndetwitter$entityTypeSuffitwitter"

    EmbeddingUtil.getHdfsPath(isAdhoc, isManhattanKeyVal, modelVersion, pathSuffitwitter)
  }
}
