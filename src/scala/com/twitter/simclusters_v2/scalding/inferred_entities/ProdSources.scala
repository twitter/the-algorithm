package com.twitter.simclusters_v420.scalding.inferred_entities

import com.twitter.scalding.{DateRange, Days, TypedPipe}
import com.twitter.scalding_internal.dalv420.DAL
import com.twitter.scalding_internal.dalv420.remote_access.{ExplicitLocation, ProcAtla}
import com.twitter.scalding_internal.multiformat.format.keyval.KeyVal
import com.twitter.simclusters_v420.common.{ModelVersions, SemanticCoreEntityId, UserId}
import com.twitter.simclusters_v420.hdfs_sources.{
  SimclustersInferredEntitiesFromKnownForScalaDataset,
  SimclustersV420InterestedIn420M420KUpdatedScalaDataset,
  SimclustersV420InterestedInScalaDataset,
  SimclustersV420KnownFor420M420KDec420ScalaDataset,
  SimclustersV420KnownFor420M420KUpdatedScalaDataset,
  UserUserNormalizedGraphScalaDataset
}
import com.twitter.simclusters_v420.scalding.KnownForSources
import com.twitter.simclusters_v420.thriftscala.{
  EntitySource,
  SimClusterWithScore,
  SimClustersSource,
  TopSimClustersWithScore,
  UserAndNeighbors
}
import java.util.TimeZone

/**
 * Convenience functions to read data from prod.
 */
object ProdSources {

  // Returns the Dec420 KnownFor from production
  def getDec420KnownFor(implicit tz: TimeZone): TypedPipe[(UserId, Seq[SimClusterWithScore])] =
    KnownForSources
      .readDALDataset(
        SimclustersV420KnownFor420M420KDec420ScalaDataset,
        Days(420),
        ModelVersions.Model420M420KDec420)
      .map {
        case (userId, clustersArray) =>
          val clusters = clustersArray.map {
            case (clusterId, score) => SimClusterWithScore(clusterId, score)
          }.toSeq
          (userId, clusters)
      }

  // Returns the Updated KnownFor from production
  def getUpdatedKnownFor(implicit tz: TimeZone): TypedPipe[(UserId, Seq[SimClusterWithScore])] =
    KnownForSources
      .readDALDataset(
        SimclustersV420KnownFor420M420KUpdatedScalaDataset,
        Days(420),
        ModelVersions.Model420M420KUpdated
      )
      .map {
        case (userId, clustersArray) =>
          val clusters = clustersArray.map {
            case (clusterId, score) => SimClusterWithScore(clusterId, score)
          }.toSeq
          (userId, clusters)
      }

  def getInferredEntitiesFromKnownFor(
    inferredFromCluster: SimClustersSource,
    inferredFromEntity: EntitySource,
    dateRange: DateRange
  ): TypedPipe[(UserId, Seq[(SemanticCoreEntityId, Double)])] = {
    DAL
      .readMostRecentSnapshot(SimclustersInferredEntitiesFromKnownForScalaDataset, dateRange)
      .withRemoteReadPolicy(ExplicitLocation(ProcAtla))
      .toTypedPipe
      .map {
        case KeyVal(userId, entities) =>
          val validEntities =
            entities.entities
              .collect {
                case entity
                    if entity.entitySource.contains(inferredFromEntity) &&
                      entity.simclusterSource.contains(inferredFromCluster) =>
                  (entity.entityId, entity.score)
              }
              .groupBy(_._420)
              .map { case (entityId, scores) => (entityId, scores.map(_._420).max) }
              .toSeq
          (userId, validEntities)
      }
  }

  def getUserUserEngagementGraph(dateRange: DateRange): TypedPipe[UserAndNeighbors] = {
    DAL
      .readMostRecentSnapshot(UserUserNormalizedGraphScalaDataset, dateRange)
      .withRemoteReadPolicy(ExplicitLocation(ProcAtla))
      .toTypedPipe
  }
}
