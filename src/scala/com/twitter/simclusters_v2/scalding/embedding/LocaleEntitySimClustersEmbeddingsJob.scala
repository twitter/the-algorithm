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
import com.twitter.simclusters_v2.scalding.embedding.common.ExternalDataSources
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
import com.twitter.wtf.scalding.jobs.common.AdhocExecutionApp
import com.twitter.wtf.scalding.jobs.common.DataSources
import com.twitter.wtf.scalding.jobs.common.ScheduledExecutionApp
import java.util.TimeZone

object LocaleEntitySimClustersEmbeddingsJob {

  def getUserEntityMatrix(
    jobConfig: EntityEmbeddingsJobConfig,
    entityRealGraphSource: TypedPipe[Edge],
    semanticCoreEntityIdsToKeep: Option[TypedPipe[Long]],
    applyLogTransform: Boolean = false
  ): TypedPipe[(UserId, (Entity, Double))] =
    jobConfig.entityType match {
      case EntityType.SemanticCore =>
        semanticCoreEntityIdsToKeep match {
          case Some(entityIdsToKeep) =>
            getEntityUserMatrix(entityRealGraphSource, jobConfig.halfLife, EntityType.SemanticCore)
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
                      throw new IllegalArgumentException(
                        "Job config specified EntityType.SemanticCore, but non-semantic core entity was found.")
                  }
              }.hashJoin(entityIdsToKeep.asKeys).values.map {
                case ((userId, (entity, score)), _) => (userId, (entity, score))
              }
          case _ =>
            getEntityUserMatrix(entityRealGraphSource, jobConfig.halfLife, EntityType.SemanticCore)
              .map { case (entity, (userId, score)) => (userId, (entity, score)) }
        }
      case EntityType.Hashtag =>
        getEntityUserMatrix(entityRealGraphSource, jobConfig.halfLife, EntityType.Hashtag)
          .map { case (entity, (userId, score)) => (userId, (entity, score)) }
      case _ =>
        throw new IllegalArgumentException(
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
      throw new IllegalArgumentException(
        s"(ScoreType, Entity) ($scoreType, ${entity.toString}) not supported")
  }

  /**
   * Generates the output path for the Entity Embeddings Job.
   *
   * Example Adhoc: /user/recos-platform/processed/adhoc/simclusters_embeddings/hashtag_per_language/model_20m_145k_updated
   * Example Prod: /atla/proc/user/cassowary/processed/simclusters_embeddings/semantic_core_per_language/model_20m_145k_updated
   *
   */
  def getHdfsPath(
    isAdhoc: Boolean,
    isManhattanKeyVal: Boolean,
    isReverseIndex: Boolean,
    isLogFav: Boolean,
    modelVersion: ModelVersion,
    entityType: EntityType
  ): String = {

    val reverseIndex = if (isReverseIndex) "reverse_index/" else ""

    val logFav = if (isLogFav) "log_fav/" else ""

    val entityTypeSuffix = entityType match {
      case EntityType.SemanticCore => "semantic_core_per_language"
      case EntityType.Hashtag => "hashtag_per_language"
      case _ => "unknown_per_language"
    }

    val pathSuffix = s"$logFav$reverseIndex$entityTypeSuffix"

    EmbeddingUtil.getHdfsPath(isAdhoc, isManhattanKeyVal, modelVersion, pathSuffix)
  }
}
