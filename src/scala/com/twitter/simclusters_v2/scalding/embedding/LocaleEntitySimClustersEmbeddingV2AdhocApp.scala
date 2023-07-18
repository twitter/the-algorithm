package com.twitter.simclusters_v2.scalding.embedding

import com.twitter.bijection.{Bufferable, Injection}
import com.twitter.recos.entities.thriftscala.{Entity, SemanticCoreEntity}
import com.twitter.scalding.*
import com.twitter.scalding_internal.dalv2.DALWrite.*
import com.twitter.scalding_internal.multiformat.format.keyval.KeyVal
import com.twitter.simclusters_v2.common.*
import com.twitter.simclusters_v2.hdfs_sources.{AdhocKeyValSources, EntityEmbeddingsSources}
import com.twitter.simclusters_v2.scalding.common.matrix.{SparseMatrix, SparseRowMatrix}
import com.twitter.simclusters_v2.scalding.embedding.common.EmbeddingUtil.ClusterId
import com.twitter.simclusters_v2.scalding.embedding.common.{EmbeddingUtil, ExternalDataSources, SimClustersEmbeddingBaseJob}
import com.twitter.simclusters_v2.thriftscala.*
import com.twitter.wtf.entity_real_graph.thriftscala.{Edge, FeatureName}
import com.twitter.wtf.scalding.jobs.common.{AdhocExecutionApp, DataSources, ScheduledExecutionApp}

import java.util.TimeZone

/**
 * $ ./bazel bundle src/scala/com/twitter/simclusters_v2/scalding/embedding:locale_entity_simclusters_embedding_v2-adhoc
 *
 * $ scalding remote run \
  --main-class com.twitter.simclusters_v2.scalding.embedding.LocaleEntitySimClustersEmbeddingV2AdhocApp \
  --target src/scala/com/twitter/simclusters_v2/scalding/embedding:locale_entity_simclusters_embedding_v2-adhoc \
  --user recos-platform --reducers 2000\
  -- --date 2020-04-06
 */
object LocaleEntitySimClustersEmbeddingV2AdhocApp
    extends LocaleEntitySimClustersEmbeddingV2Job
    with AdhocExecutionApp {

  override def writeNounToClustersIndex(
    output: TypedPipe[(LocaleEntity, Seq[(ClusterId, Double)])]
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): Execution[Unit] = {

    output
      .map {
        case ((entityId, lang), clustersWithScores) =>
          SimClustersEmbeddingId(
            EmbeddingType.LogFavBasedLocaleSemanticCoreEntity,
            ModelVersion.Model20m145kUpdated,
            InternalId.LocaleEntityId(LocaleEntityId(entityId, lang))
          ) -> SimClustersEmbedding(clustersWithScores).toThrift

      }.writeExecution(
        AdhocKeyValSources.entityToClustersSource(
          EmbeddingUtil.getHdfsPath(
            isAdhoc = true,
            isManhattanKeyVal = true,
            ModelVersion.Model20m145kUpdated,
            pathSuffix = "log_fav_erg_based_embeddings")))
  }

  override def writeClusterToNounsIndex(
    output: TypedPipe[(ClusterId, Seq[(LocaleEntity, Double)])]
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): Execution[Unit] = {

    output
      .map {
        case (clusterId, nounsWithScore) =>
          SimClustersEmbeddingId(
            EmbeddingType.LogFavBasedLocaleSemanticCoreEntity,
            ModelVersion.Model20m145kUpdated,
            InternalId.ClusterId(clusterId)
          ) ->
            InternalIdEmbedding(nounsWithScore.map {
              case ((entityId, lang), score) =>
                InternalIdWithScore(
                  InternalId.LocaleEntityId(LocaleEntityId(entityId, lang)),
                  score)
            })
      }
      .writeExecution(
        AdhocKeyValSources.clusterToEntitiesSource(
          EmbeddingUtil.getHdfsPath(
            isAdhoc = true,
            isManhattanKeyVal = true,
            ModelVersion.Model20m145kUpdated,
            pathSuffix = "reverse_index_log_fav_erg_based_embeddings")))
  }
}


