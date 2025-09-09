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
import com.twitter.wtf.scalding.jobs.common.AdhocExecutionApp
import com.twitter.wtf.scalding.jobs.common.DataSources
import com.twitter.wtf.scalding.jobs.common.ScheduledExecutionApp
import java.util.TimeZone

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
      throw new IllegalArgumentException(
        s"(ScoreType, Entity) ($scoreType, ${entity.toString}) not supported")
  }

  /**
   * Generates the output path for the Entity Embeddings Job.
   *
   * Example Adhoc: /user/recos-platform/processed/adhoc/simclusters_embeddings/hashtag/model_20m_145k_updated
   * Example Prod: /atla/proc/user/cassowary/processed/simclusters_embeddings/semantic_core/model_20m_145k_dec11
   *
   */
  def getHdfsPath(
    isAdhoc: Boolean,
    isManhattanKeyVal: Boolean,
    isReverseIndex: Boolean,
    modelVersion: ModelVersion,
    entityType: EntityType
  ): String = {

    val reverseIndex = if (isReverseIndex) "reverse_index/" else ""

    val entityTypeSuffix = entityType match {
      case EntityType.SemanticCore => "semantic_core"
      case EntityType.Hashtag => "hashtag"
      case _ => "unknown"
    }

    val pathSuffix = s"$reverseIndex$entityTypeSuffix"

    EmbeddingUtil.getHdfsPath(isAdhoc, isManhattanKeyVal, modelVersion, pathSuffix)
  }

  def embeddingsLitePath(modelVersion: ModelVersion, pathSuffix: String): String = {
    s"/user/cassowary/processed/entity_real_graph/simclusters_embedding/lite/$modelVersion/$pathSuffix/"
  }
}
