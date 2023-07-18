package com.twitter.simclusters_v2.scalding.embedding

import com.twitter.bijection.{Bufferable, Injection}
import com.twitter.recos.entities.thriftscala.{Entity, SemanticCoreEntity}
import com.twitter.scalding.{DateRange, Days, Duration, Execution, RichDate, TypedPipe, UniqueID}
import com.twitter.scalding_internal.dalv2.DALWrite._
import com.twitter.scalding_internal.multiformat.format.keyval.KeyVal
import com.twitter.simclusters_v2.common._
import com.twitter.simclusters_v2.hdfs_sources.{AdhocKeyValSources, EntityEmbeddingsSources}
import com.twitter.simclusters_v2.scalding.common.matrix.{SparseMatrix, SparseRowMatrix}
import com.twitter.simclusters_v2.scalding.embedding.common.EmbeddingUtil.ClusterId
import com.twitter.simclusters_v2.scalding.embedding.common.{
  EmbeddingUtil,
  ExternalDataSources,
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
import com.twitter.wtf.scalding.jobs.common.{AdhocExecutionApp, DataSources, ScheduledExecutionApp}
import java.util.TimeZone


trait LocaleEntitySimClustersEmbeddingV2Job extends SimClustersEmbeddingBaseJob[LocaleEntity] {

  override val numClustersPerNoun = 100

  override val numNounsPerClusters = 100

  override val thresholdForEmbeddingScores: Double = 0.001

  override val numReducersOpt: Option[Int] = Some(8000)

  private val DefaultERGHalfLifeInDays = 14

  private val MinInterestedInLogFavScore = 0.0

  implicit val inj: Injection[LocaleEntity, Array[Byte]] = Bufferable.injectionOf[LocaleEntity]

  override def prepareNounToUserMatrix(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): SparseMatrix[LocaleEntity, UserId, Double] = {

    val erg: TypedPipe[(SemanticCoreEntityId, (UserId, Double))] =
      DataSources.entityRealGraphAggregationDataSetSource(dateRange.embiggen(Days(7))).flatMap {
        case Edge(
              userId,
              Entity.SemanticCore(SemanticCoreEntity(entityId, _)),
              consumerFeatures,
              _,
              _) if consumerFeatures.exists(_.exists(_.featureName == FeatureName.Favorites)) =>
          for {
            features <- consumerFeatures
            favFeatures <- features.find(_.featureName == FeatureName.Favorites)
            ewmaMap <- favFeatures.featureValues.ewmaMap
            favScore <- ewmaMap.get(DefaultERGHalfLifeInDays)
          } yield (entityId, (userId, Math.log(favScore + 1)))

        case _ => None
      }

    SparseMatrix[LocaleEntity, UserId, Double](
      erg
        .hashJoin(ExternalDataSources.uttEntitiesSource().asKeys).map {
          case (entityId, ((userId, score), _)) => (userId, (entityId, score))
        }.join(ExternalDataSources.userSource).map {
          case (userId, ((entityId, score), (_, language))) =>
            ((entityId, language), userId, score)
        }
    )
  }

  override def prepareUserToClusterMatrix(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): SparseRowMatrix[UserId, ClusterId, Double] = {
    SparseRowMatrix(
      ExternalDataSources.simClustersInterestInLogFavSource(MinInterestedInLogFavScore),
      isSkinnyMatrix = true
    )
  }
}
