package com.twitter.simclusters_v2.scalding.embedding

import com.twitter.bijection.{Bufferable, Injection}
import com.twitter.recos.entities.thriftscala.{Entity, SemanticCoreEntity}
import com.twitter.scalding.{DateRange, Days, Duration, Etwitterecution, RichDate, TypedPipe, UniqueID}
import com.twitter.scalding_internal.dalv2.DALWrite._
import com.twitter.scalding_internal.multiformat.format.keyval.KeyVal
import com.twitter.simclusters_v2.common._
import com.twitter.simclusters_v2.hdfs_sources.{AdhocKeyValSources, EntityEmbeddingsSources}
import com.twitter.simclusters_v2.scalding.common.matritwitter.{SparseMatritwitter, SparseRowMatritwitter}
import com.twitter.simclusters_v2.scalding.embedding.common.EmbeddingUtil.ClusterId
import com.twitter.simclusters_v2.scalding.embedding.common.{
  EmbeddingUtil,
  EtwitterternalDataSources,
  SimClustersEmbeddingBaseJob
}
import com.twitter.simclusters_v2.thriftscala.{
  EmbeddingType,
  InternalId,
  InternalIdEmbedding,
  InternalIdWithScore,
  LocaleEntityId,
  ModelVersion,
  SimClustersEmbeddingId
}
import com.twitter.wtf.entity_real_graph.thriftscala.{Edge, FeatureName}
import com.twitter.wtf.scalding.jobs.common.{AdhocEtwitterecutionApp, DataSources, ScheduledEtwitterecutionApp}
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
    etwittertends LocaleEntitySimClustersEmbeddingV2Job
    with ScheduledEtwitterecutionApp {

  override val firstTime: RichDate = RichDate("2020-04-08")

  override val batchIncrement: Duration = Days(1)

  override def writeNounToClustersIndetwitter(
    output: TypedPipe[(LocaleEntity, Seq[(ClusterId, Double)])]
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): Etwitterecution[Unit] = {

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
      .writeDALVersionedKeyValEtwitterecution(
        EntityEmbeddingsSources.LogFavSemanticCorePerLanguageSimClustersEmbeddingsDataset,
        D.Suffitwitter(
          EmbeddingUtil.getHdfsPath(
            isAdhoc = false,
            isManhattanKeyVal = true,
            ModelVersion.Model20m145kUpdated,
            pathSuffitwitter = "log_fav_erg_based_embeddings"))
      )
  }

  override def writeClusterToNounsIndetwitter(
    output: TypedPipe[(ClusterId, Seq[(LocaleEntity, Double)])]
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): Etwitterecution[Unit] = {
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
      .writeDALVersionedKeyValEtwitterecution(
        EntityEmbeddingsSources.LogFavReverseIndetwitterSemanticCorePerLanguageSimClustersEmbeddingsDataset,
        D.Suffitwitter(
          EmbeddingUtil.getHdfsPath(
            isAdhoc = false,
            isManhattanKeyVal = true,
            ModelVersion.Model20m145kUpdated,
            pathSuffitwitter = "reverse_indetwitter_log_fav_erg_based_embeddings"))
      )
  }
}

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
    etwittertends LocaleEntitySimClustersEmbeddingV2Job
    with AdhocEtwitterecutionApp {

  override def writeNounToClustersIndetwitter(
    output: TypedPipe[(LocaleEntity, Seq[(ClusterId, Double)])]
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): Etwitterecution[Unit] = {

    output
      .map {
        case ((entityId, lang), clustersWithScores) =>
          SimClustersEmbeddingId(
            EmbeddingType.LogFavBasedLocaleSemanticCoreEntity,
            ModelVersion.Model20m145kUpdated,
            InternalId.LocaleEntityId(LocaleEntityId(entityId, lang))
          ) -> SimClustersEmbedding(clustersWithScores).toThrift

      }.writeEtwitterecution(
        AdhocKeyValSources.entityToClustersSource(
          EmbeddingUtil.getHdfsPath(
            isAdhoc = true,
            isManhattanKeyVal = true,
            ModelVersion.Model20m145kUpdated,
            pathSuffitwitter = "log_fav_erg_based_embeddings")))
  }

  override def writeClusterToNounsIndetwitter(
    output: TypedPipe[(ClusterId, Seq[(LocaleEntity, Double)])]
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): Etwitterecution[Unit] = {

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
      .writeEtwitterecution(
        AdhocKeyValSources.clusterToEntitiesSource(
          EmbeddingUtil.getHdfsPath(
            isAdhoc = true,
            isManhattanKeyVal = true,
            ModelVersion.Model20m145kUpdated,
            pathSuffitwitter = "reverse_indetwitter_log_fav_erg_based_embeddings")))
  }
}

trait LocaleEntitySimClustersEmbeddingV2Job etwittertends SimClustersEmbeddingBaseJob[LocaleEntity] {

  override val numClustersPerNoun = 100

  override val numNounsPerClusters = 100

  override val thresholdForEmbeddingScores: Double = 0.001

  override val numReducersOpt: Option[Int] = Some(8000)

  private val DefaultERGHalfLifeInDays = 14

  private val MinInterestedInLogFavScore = 0.0

  implicit val inj: Injection[LocaleEntity, Array[Byte]] = Bufferable.injectionOf[LocaleEntity]

  override def prepareNounToUserMatritwitter(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): SparseMatritwitter[LocaleEntity, UserId, Double] = {

    val erg: TypedPipe[(SemanticCoreEntityId, (UserId, Double))] =
      DataSources.entityRealGraphAggregationDataSetSource(dateRange.embiggen(Days(7))).flatMap {
        case Edge(
              userId,
              Entity.SemanticCore(SemanticCoreEntity(entityId, _)),
              consumerFeatures,
              _,
              _) if consumerFeatures.etwitterists(_.etwitterists(_.featureName == FeatureName.Favorites)) =>
          for {
            features <- consumerFeatures
            favFeatures <- features.find(_.featureName == FeatureName.Favorites)
            ewmaMap <- favFeatures.featureValues.ewmaMap
            favScore <- ewmaMap.get(DefaultERGHalfLifeInDays)
          } yield (entityId, (userId, Math.log(favScore + 1)))

        case _ => None
      }

    SparseMatritwitter[LocaleEntity, UserId, Double](
      erg
        .hashJoin(EtwitterternalDataSources.uttEntitiesSource().asKeys).map {
          case (entityId, ((userId, score), _)) => (userId, (entityId, score))
        }.join(EtwitterternalDataSources.userSource).map {
          case (userId, ((entityId, score), (_, language))) =>
            ((entityId, language), userId, score)
        }
    )
  }

  override def prepareUserToClusterMatritwitter(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): SparseRowMatritwitter[UserId, ClusterId, Double] = {
    SparseRowMatritwitter(
      EtwitterternalDataSources.simClustersInterestInLogFavSource(MinInterestedInLogFavScore),
      isSkinnyMatritwitter = true
    )
  }
}
