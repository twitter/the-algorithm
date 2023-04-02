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
 * Scheduled production job which generates topic embeddings per locale based on Entity Real Graph.
 *
 * V2 Uses the log transform of the ERG favScores and the SimCluster InterestedIn scores.
 *
 * $ ./bazel bundle src/scala/com/twitter/simclusters_v2/scalding/embedding:locale_entity_simclusters_embedding_v2
 * $ capesospy-v2 update \
  --build_locally \
  --start_cron locale_entity_simclusters_embedding_v2 src/scala/com/twitter/simclusters_v2/capesos_config/atla_proc3.yaml
 */
object LocaleEntitySimClustersEmbeddingV2ScheduledApp
    extends LocaleEntitySimClustersEmbeddingV2Job
    with ScheduledExecutionApp {

  override val firstTime: RichDate = RichDate("2020-04-08")

  override val batchIncrement: Duration = Days(1)

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
          KeyVal(
            SimClustersEmbeddingId(
              EmbeddingType.LogFavBasedLocaleSemanticCoreEntity,
              ModelVersion.Model20m145kUpdated,
              InternalId.LocaleEntityId(LocaleEntityId(entityId, lang))
            ),
            SimClustersEmbedding(clustersWithScores).toThrift
          )
      }
      .writeDALVersionedKeyValExecution(
        EntityEmbeddingsSources.LogFavSemanticCorePerLanguageSimClustersEmbeddingsDataset,
        D.Suffix(
          EmbeddingUtil.getHdfsPath(
            isAdhoc = false,
            isManhattanKeyVal = true,
            ModelVersion.Model20m145kUpdated,
            pathSuffix = "log_fav_erg_based_embeddings"))
      )
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
          KeyVal(
            SimClustersEmbeddingId(
              EmbeddingType.LogFavBasedLocaleSemanticCoreEntity,
              ModelVersion.Model20m145kUpdated,
              InternalId.ClusterId(clusterId)
            ),
            InternalIdEmbedding(nounsWithScore.map {
              case ((entityId, lang), score) =>
                InternalIdWithScore(
                  InternalId.LocaleEntityId(LocaleEntityId(entityId, lang)),
                  score)
            })
          )
      }
      .writeDALVersionedKeyValExecution(
        EntityEmbeddingsSources.LogFavReverseIndexSemanticCorePerLanguageSimClustersEmbeddingsDataset,
        D.Suffix(
          EmbeddingUtil.getHdfsPath(
            isAdhoc = false,
            isManhattanKeyVal = true,
            ModelVersion.Model20m145kUpdated,
            pathSuffix = "reverse_index_log_fav_erg_based_embeddings"))
      )
  }
}




