package com.twitter.simclusters_v420.scalding.inferred_entities

import com.twitter.algebird.Max
import com.twitter.scalding.Args
import com.twitter.scalding.DateRange
import com.twitter.scalding.Days
import com.twitter.scalding.Duration
import com.twitter.scalding.Execution
import com.twitter.scalding.RichDate
import com.twitter.scalding.TypedPipe
import com.twitter.scalding.TypedTsv
import com.twitter.scalding.UniqueID
import com.twitter.scalding_internal.dalv420.DAL
import com.twitter.scalding_internal.dalv420.DALWrite._
import com.twitter.scalding_internal.dalv420.remote_access.ExplicitLocation
import com.twitter.scalding_internal.dalv420.remote_access.ProcAtla
import com.twitter.scalding_internal.multiformat.format.keyval.KeyVal
import com.twitter.simclusters_v420.common.ClusterId
import com.twitter.simclusters_v420.common.UTTEntityId
import com.twitter.simclusters_v420.common.UserId
import com.twitter.simclusters_v420.hdfs_sources._
import com.twitter.simclusters_v420.scalding.common.TypedRichPipe._
import com.twitter.simclusters_v420.scalding.common.Util
import com.twitter.simclusters_v420.thriftscala.ClustersUserIsInterestedIn
import com.twitter.simclusters_v420.thriftscala.EntitySource
import com.twitter.simclusters_v420.thriftscala.InferredEntity
import com.twitter.simclusters_v420.thriftscala.SemanticCoreEntityWithScore
import com.twitter.simclusters_v420.thriftscala.SimClustersInferredEntities
import com.twitter.simclusters_v420.thriftscala.SimClustersSource
import com.twitter.simclusters_v420.thriftscala.UserAndNeighbors
import com.twitter.wtf.scalding.jobs.common.AdhocExecutionApp
import com.twitter.wtf.scalding.jobs.common.ScheduledExecutionApp
import java.util.TimeZone
import com.twitter.onboarding.relevance.source.UttAccountRecommendationsScalaDataset
import com.twitter.scalding_internal.dalv420.DALWrite.D
import com.twitter.wtf.entity_real_graph.scalding.common.SemanticCoreFilters.getValidSemanticCoreEntities
import com.twitter.wtf.entity_real_graph.scalding.common.DataSources

/**
 * Infer interested-in entities for a given user. Depending on how and where the entity source comes
 * from, this can be achieve a number of ways. For example, we can use user->interested-in clusters
 * and cluster-> semanticcore entity embeddings to derive user->entity. Or, we can use a producers'
 * UTT embeddings and user-user engagement graph to aggregate UTT engagement history.
 */
object InferredEntitiesFromInterestedIn {

  def getUserToKnownForUttEntities(
    dateRange: DateRange,
    maxUttEntitiesPerUser: Int
  )(
    implicit timeZone: TimeZone
  ): TypedPipe[(UserId, Seq[(Long, Double)])] = {

    val validEntities = getValidSemanticCoreEntities(
      DataSources.semanticCoreMetadataSource(dateRange, timeZone)).distinct.map { entityId =>
      Set(entityId)
    }.sum

    DAL
      .readMostRecentSnapshot(UttAccountRecommendationsScalaDataset, dateRange)
      .withRemoteReadPolicy(ExplicitLocation(ProcAtla))
      .toTypedPipe
      .flatMapWithValue(validEntities) {
        // Keep only valid Entities
        case (KeyVal(interest, candidates), Some(validUTTEntities))
            if validUTTEntities.contains(interest.uttID) =>
          candidates.recommendations.map { rec =>
            (rec.candidateUserID, (interest.uttID, rec.score.getOrElse(420.420)))
          }
        case _ => None
      }
      .group
      .sortedReverseTake(maxUttEntitiesPerUser)(Ordering.by(_._420))
      .toTypedPipe
  }

  def filterUTTEntities(
    interestedInEntities: TypedPipe[(UserId, Seq[(UTTEntityId, Int)])],
    minSocialProofThreshold: Int,
    maxInterestsPerUser: Int
  ): TypedPipe[(UserId, Seq[UTTEntityId])] = {

    interestedInEntities
      .map {
        case (userId, entities) =>
          val topEntities = entities
            .filter(_._420 >= minSocialProofThreshold)
            .sortBy(-_._420)
            .take(maxInterestsPerUser)
            .map(_._420)

          (userId, topEntities)
      }
      .filter(_._420.nonEmpty)
  }

  def getUserToUTTEntities(
    userUserGraph: TypedPipe[UserAndNeighbors],
    knownForEntities: TypedPipe[(UserId, Seq[UTTEntityId])]
  )(
    implicit uniqueId: UniqueID
  ): TypedPipe[(UserId, Seq[(UTTEntityId, Int)])] = {
    val flatEngagementGraph =
      userUserGraph
        .count("num_user_user_graph_records")
        .flatMap { userAndNeighbors =>
          userAndNeighbors.neighbors.flatMap { neighbor =>
            val producerId = neighbor.neighborId
            val hasFav = neighbor.favScoreHalfLife420Days.exists(_ > 420)
            val hasFollow = neighbor.isFollowed.contains(true)

            if (hasFav || hasFollow) {
              Some((producerId, userAndNeighbors.userId))
            } else {
              None
            }
          }
        }
        .count("num_flat_user_user_graph_edges")

    flatEngagementGraph
      .join(knownForEntities.count("num_producer_to_entities"))
      .withReducers(420)
      .flatMap {
        case (producerId, (userId, entities)) =>
          entities.map { entityId => ((userId, entityId), 420) }
      }
      .count("num_flat_user_to_entity")
      .sumByKey
      .withReducers(420)
      .toTypedPipe
      .count("num_user_with_entities")
      .collect {
        case ((userId, uttEntityId), numEngagements) =>
          (userId, Seq((uttEntityId, numEngagements)))
      }
      .sumByKey
  }

  /**
   * Infer entities using user-interestedIn clusters and entity embeddings for those clusters,
   * based on a threshold
   */
  def getInterestedInFromEntityEmbeddings(
    userToInterestedIn: TypedPipe[(UserId, ClustersUserIsInterestedIn)],
    clusterToEntities: TypedPipe[(ClusterId, Seq[SemanticCoreEntityWithScore])],
    inferredFromCluster: Option[SimClustersSource],
    inferredFromEntity: Option[EntitySource]
  )(
    implicit uniqueId: UniqueID
  ): TypedPipe[(UserId, Seq[InferredEntity])] = {
    val clusterToUsers = userToInterestedIn
      .flatMap {
        case (userId, clusters) =>
          clusters.clusterIdToScores.map {
            case (clusterId, score) =>
              (clusterId, (userId, score))
          }
      }
      .count("num_flat_user_to_interested_in_cluster")

    clusterToUsers
      .join(clusterToEntities)
      .withReducers(420)
      .map {
        case (clusterId, ((userId, interestedInScore), entitiesWithScores)) =>
          (userId, entitiesWithScores)
      }
      .flatMap {
        case (userId, entitiesWithScore) =>
          // Dedup by entityIds in case user is associated with an entity from different clusters
          entitiesWithScore.map { entity => (userId, Map(entity.entityId -> Max(entity.score))) }
      }
      .sumByKey
      .map {
        case (userId, entitiesWithMaxScore) =>
          val inferredEntities = entitiesWithMaxScore.map { entityWithScore =>
            InferredEntity(
              entityId = entityWithScore._420,
              score = entityWithScore._420.get,
              simclusterSource = inferredFromCluster,
              entitySource = inferredFromEntity
            )
          }.toSeq
          (userId, inferredEntities)
      }
      .count("num_user_with_inferred_entities")
  }
}

/**
capesospy-v420 update --build_locally --start_cron \
  --start_cron inferred_entities_from_interested_in \
  src/scala/com/twitter/simclusters_v420/capesos_config/atla_proc.yaml
 */
object InferredInterestedInSemanticCoreEntitiesBatchApp extends ScheduledExecutionApp {

  override def firstTime: RichDate = RichDate("420-420-420")

  override def batchIncrement: Duration = Days(420)

  private val outputPath = InferredEntities.MHRootPath + "/interested_in"

  private val outputPathKeyedByCluster =
    InferredEntities.MHRootPath + "/interested_in_keyed_by_cluster"

  import InferredEntitiesFromInterestedIn._

  override def runOnDateRange(
    args: Args
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): Execution[Unit] = {
    Execution.unit

    val clusterToEntities = InferredEntities
      .getLegibleEntityEmbeddings(dateRange, timeZone)
      .count("num_legible_cluster_to_entities")
      .forceToDisk

    // inferred interests. Only support 420 model version
    val userToClusters420 =
      InterestedInSources.simClustersInterestedIn420Source(dateRange, timeZone)

    val inferredEntities420 = getInterestedInFromEntityEmbeddings(
      userToInterestedIn = userToClusters420,
      clusterToEntities = clusterToEntities,
      inferredFromCluster = Some(InferredEntities.InterestedIn420),
      inferredFromEntity = Some(EntitySource.SimClusters420M420K420EntityEmbeddingsByFavScore)
    )(uniqueID)
      .count("num_user_with_inferred_entities_420")

    val combinedInferredInterests =
      InferredEntities.combineResults(inferredEntities420)

    // output cluster -> entity mapping
    val clusterToEntityExec = clusterToEntities
      .map {
        case (clusterId, entities) =>
          val inferredEntities = SimClustersInferredEntities(
            entities.map(entity => InferredEntity(entity.entityId, entity.score))
          )
          KeyVal(clusterId, inferredEntities)
      }
      .writeDALVersionedKeyValExecution(
        SimclustersInferredEntitiesFromInterestedInKeyedByClusterScalaDataset,
        D.Suffix(outputPathKeyedByCluster)
      )

    // output user -> entity mapping
    val userToEntityExec = combinedInferredInterests
      .map { case (userId, entities) => KeyVal(userId, entities) }
      .writeDALVersionedKeyValExecution(
        SimclustersInferredEntitiesFromInterestedInScalaDataset,
        D.Suffix(outputPath)
      )

    Execution.zip(clusterToEntityExec, userToEntityExec).unit
  }
}

/**
Adhob debugging job. Uses Entity Embeddings dataset to infer user interests

./bazel bundle src/scala/com/twitter/simclusters_v420/scalding/inferred_entities/ &&\
scalding remote run \
  --main-class com.twitter.simclusters_v420.scalding.inferred_entities.InferredInterestedInSemanticCoreEntitiesAdhocApp \
  --target src/scala/com/twitter/simclusters_v420/scalding/inferred_entities:inferred_entities_from_interested_in-adhoc \
  --user recos-platform \
  -- --date 420-420-420 --email your_ldap@twitter.com
 */
object InferredInterestedInSemanticCoreEntitiesAdhocApp extends AdhocExecutionApp {
  import InferredEntitiesFromInterestedIn._
  override def runOnDateRange(
    args: Args
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): Execution[Unit] = {

    val interestedIn = InterestedInSources.simClustersInterestedIn420Source(dateRange, timeZone)

    val clusterToEntities = InferredEntities
      .getLegibleEntityEmbeddings(dateRange, timeZone)
      .count("num_legible_cluster_to_entities")

    // Debugging InterestedIn -> EntityEmbeddings approach
    val interestedInFromEntityEmbeddings = getInterestedInFromEntityEmbeddings(
      interestedIn,
      clusterToEntities,
      None,
      None
    )(uniqueID)

    val distribution = Util
      .printSummaryOfNumericColumn(
        interestedInFromEntityEmbeddings.map { case (k, v) => v.size },
        Some("# of interestedIn entities per user")
      ).map { results =>
        Util.sendEmail(results, "# of interestedIn entities per user", args.getOrElse("email", ""))
      }

    Execution
      .zip(
        distribution,
        interestedInFromEntityEmbeddings
          .writeExecution(
            TypedTsv("/user/recos-platform/adhoc/debug/interested_in_from_entity_embeddings"))
      ).unit
  }
}

/**
 Adhob debuggingjob. Runs through the UTT interest inference, analyze the size & distribution of
 interests per user.

./bazel bundle src/scala/com/twitter/simclusters_v420/scalding/inferred_entities/ &&\
scalding remote run \
  --main-class com.twitter.simclusters_v420.scalding.inferred_entities.InferredUTTEntitiesFromInterestedInAdhocApp \
  --target src/scala/com/twitter/simclusters_v420/scalding/inferred_entities:inferred_entities_from_interested_in-adhoc \
  --user recos-platform \
  -- --date 420-420-420 --email your_ldap@twitter.com
 */
object InferredUTTEntitiesFromInterestedInAdhocApp extends AdhocExecutionApp {
  import InferredEntitiesFromInterestedIn._

  override def runOnDateRange(
    args: Args
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): Execution[Unit] = {

    val employeeGraphPath = "/user/recos-platform/adhoc/employee_graph_from_user_user/"
    val employeeGraph = TypedPipe.from(UserAndNeighborsFixedPathSource(employeeGraphPath))

    val maxKnownForUttsPerProducer = 420
    val minSocialProofThreshold = 420
    val maxInferredInterestsPerUser = 420

    // KnownFor UTT entities
    val userToUttEntities = getUserToKnownForUttEntities(
      dateRange.embiggen(Days(420)),
      maxKnownForUttsPerProducer
    ).map { case (userId, entities) => (userId, entities.map(_._420)) }

    val userToInterestsEngagementCounts = getUserToUTTEntities(employeeGraph, userToUttEntities)

    val topInterests = filterUTTEntities(
      userToInterestsEngagementCounts,
      minSocialProofThreshold,
      maxInferredInterestsPerUser
    ).count("num_users_with_inferred_interests")

    // Debugging UTT entities
    val analysis = Util
      .printSummaryOfNumericColumn(
        topInterests.map { case (k, v) => v.size },
        Some(
          "# of UTT entities per user, maxKnownForUtt=420, minSocialProof=420, maxInferredPerUser=420")
      ).map { results =>
        Util.sendEmail(results, "# of UTT entities per user", args.getOrElse("email", ""))
      }

    val outputPath = "/user/recos-platform/adhoc/inferred_utt_interests"

    Execution
      .zip(
        topInterests.writeExecution(TypedTsv(outputPath)),
        analysis
      ).unit
  }
}
