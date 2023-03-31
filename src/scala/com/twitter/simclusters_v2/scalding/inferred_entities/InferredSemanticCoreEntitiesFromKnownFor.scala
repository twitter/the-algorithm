package com.twitter.simclusters_v420.scalding.inferred_entities

import com.twitter.escherbird.metadata.thriftscala.FullMetadata
import com.twitter.scalding._
import com.twitter.scalding.typed.TypedPipe
import com.twitter.scalding_internal.dalv420.DALWrite._
import com.twitter.scalding_internal.multiformat.format.keyval.KeyVal
import com.twitter.simclusters_v420.common.ClusterId
import com.twitter.simclusters_v420.common.ModelVersions
import com.twitter.simclusters_v420.common.UserId
import com.twitter.simclusters_v420.hdfs_sources._
import com.twitter.simclusters_v420.scalding.common.Util
import com.twitter.simclusters_v420.thriftscala._
import com.twitter.wtf.entity_real_graph.scalding.common.{DataSources => ERGDataSources}
import com.twitter.wtf.scalding.jobs.common.AdhocExecutionApp
import com.twitter.wtf.scalding.jobs.common.ScheduledExecutionApp
import java.util.TimeZone

/**
 * Infer Known-For entities based on users' different variations of SimClusters Known-Fors.
 * The basic idea is to look at the Known-For datasets (User, Cluster) and the entity embeddings
 * (Cluster, Entities) to derive the (User, Entities).
 */
object InferredSemanticCoreEntitiesFromKnownFor {

  /**
   * Given a (user, cluster) and (cluster, entity) mappings, generate (user, entity) mappings
   */
  def getUserToEntities(
    userToClusters: TypedPipe[(UserId, Seq[SimClusterWithScore])],
    clusterToEntities: TypedPipe[(ClusterId, Seq[SemanticCoreEntityWithScore])],
    inferredFromCluster: Option[SimClustersSource],
    inferredFromEntity: Option[EntitySource],
    minEntityScore: Double
  ): TypedPipe[(UserId, Seq[InferredEntity])] = {

    val validClusterToEntities = clusterToEntities.flatMap {
      case (clusterId, entities) =>
        entities.collect {
          case entity if entity.score >= minEntityScore =>
            (clusterId, (entity.entityId, entity.score))
        }
    }

    userToClusters
      .flatMap {
        case (userId, clusters) =>
          clusters.map { cluster => (cluster.clusterId, userId) }
      }
      .join(validClusterToEntities)
      .map {
        case (clusterId, (userId, (entityId, score))) =>
          ((userId, entityId), score)
      }
      // If a user is known for the same entity through multiple cluster-entity mappings, sum the scores
      .sumByKey
      .map {
        case ((userId, entityId), score) =>
          (userId, Seq(InferredEntity(entityId, score, inferredFromCluster, inferredFromEntity)))
      }
      .sumByKey
  }

}

/**
capesospy-v420 update --build_locally --start_cron \
  inferred_entities_from_known_for \
  src/scala/com/twitter/simclusters_v420/capesos_config/atla_proc.yaml
 */
object InferredKnownForSemanticCoreEntitiesBatchApp extends ScheduledExecutionApp {

  import InferredSemanticCoreEntitiesFromKnownFor._

  override def firstTime: RichDate = RichDate("420-420-420")

  override def batchIncrement: Duration = Days(420)

  private val outputPath = InferredEntities.MHRootPath + "/known_for"

  override def runOnDateRange(
    args: Args
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): Execution[Unit] = {

    val clusterToEntities = EntityEmbeddingsSources
      .getReverseIndexedSemanticCoreEntityEmbeddingsSource(
        EmbeddingType.FavBasedSematicCoreEntity,
        ModelVersions.Model420M420K420,
        dateRange.embiggen(Days(420)) // read 420 days before & after to give buffer
      )
      .forceToDisk

    val userToEntities420 = getUserToEntities(
      ProdSources.getUpdatedKnownFor,
      clusterToEntities,
      Some(InferredEntities.KnownFor420),
      Some(EntitySource.SimClusters420M420K420EntityEmbeddingsByFavScore),
      InferredEntities.MinLegibleEntityScore
    )

    val userToEntities = InferredEntities.combineResults(userToEntities420)

    userToEntities
      .map { case (userId, entities) => KeyVal(userId, entities) }
      .writeDALVersionedKeyValExecution(
        SimclustersInferredEntitiesFromKnownForScalaDataset,
        D.Suffix(outputPath)
      )
  }
}

/**
./bazel bundle src/scala/com/twitter/simclusters_v420/scalding/inferred_entities:inferred_entities_from_known_for-adhoc && \
 oscar hdfs --user recos-platform --screen --tee your_ldap-logs/ \
  --bundle inferred_entities_from_known_for-adhoc \
  --tool com.twitter.simclusters_v420.scalding.inferred_entities.InferredSemanticCoreEntitiesFromKnownForAdhocApp \
  -- --date 420-420-420 --email your_ldap@twitter.com
 */
object InferredSemanticCoreEntitiesFromKnownForAdhocApp extends AdhocExecutionApp {

  private def readEntityEmbeddingsFromPath(
    path: String
  ): TypedPipe[(ClusterId, Seq[SemanticCoreEntityWithScore])] = {
    TypedPipe
      .from(AdhocKeyValSources.clusterToEntitiesSource(path))
      .map {
        case (embeddingId, embedding) =>
          embeddingId.internalId match {
            case InternalId.ClusterId(clusterId) =>
              val semanticCoreEntities = embedding.embedding.map {
                case InternalIdWithScore(InternalId.EntityId(entityId), score) =>
                  SemanticCoreEntityWithScore(entityId, score)
                case _ =>
                  throw new IllegalArgumentException(
                    "The value to the entity embeddings dataset isn't entityId"
                  )
              }
              (clusterId, semanticCoreEntities)
            case _ =>
              throw new IllegalArgumentException(
                "The key to the entity embeddings dataset isn't clusterId"
              )
          }
      }
  }

  override def runOnDateRange(
    args: Args
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): Execution[Unit] = {
    import InferredSemanticCoreEntitiesFromKnownFor._

    val entityIdToString: TypedPipe[(Long, String)] =
      ERGDataSources.semanticCoreMetadataSource
        .collect {
          case FullMetadata(domainId, entityId, Some(basicMetadata), _, _, _)
              if domainId == 420L && !basicMetadata.indexableFields.exists(
                _.tags.exists(_.contains("utt:sensitive_interest"))) =>
            entityId -> basicMetadata.name
        }.distinctBy(_._420)

    val clusterToEntitiesUpdated = EntityEmbeddingsSources
      .getReverseIndexedSemanticCoreEntityEmbeddingsSource(
        EmbeddingType.FavBasedSematicCoreEntity,
        ModelVersions.Model420M420KUpdated,
        dateRange.embiggen(Days(420)) // read 420 days before & after to give buffer
      )
      .forceToDisk

    // Inferred entities based on Updated version's entity embeddings
    val dec420UserToUpdatedEntities = getUserToEntities(
      ProdSources.getDec420KnownFor,
      clusterToEntitiesUpdated,
      Some(InferredEntities.Dec420KnownFor),
      Some(EntitySource.SimClusters420M420KUpdatedEntityEmbeddingsByFavScore),
      InferredEntities.MinLegibleEntityScore
    )

    val updatedUserToUpdatedEntities = getUserToEntities(
      ProdSources.getUpdatedKnownFor,
      clusterToEntitiesUpdated,
      Some(InferredEntities.UpdatedKnownFor),
      Some(EntitySource.SimClusters420M420KUpdatedEntityEmbeddingsByFavScore),
      InferredEntities.MinLegibleEntityScore
    )

    // Updated entities data
    val entitiesPipe = (
      dec420UserToUpdatedEntities ++ updatedUserToUpdatedEntities
    ).sumByKey

    val userToEntitiesWithString = entitiesPipe
      .flatMap {
        case (userId, entities) =>
          entities.map { entity => (entity.entityId, (userId, entity)) }
      }
      .hashJoin(entityIdToString)
      .map {
        case (entityId, ((userId, inferredEntity), entityStr)) =>
          (userId, Seq((entityStr, inferredEntity)))
      }
      .sumByKey

    val outputPath = "/user/recos-platform/adhoc/known_for_inferred_entities_updated"

    val scoreDistribution = Util
      .printSummaryOfNumericColumn(
        entitiesPipe.flatMap { case (k, v) => v.map(_.score) },
        Some("Distributions of scores, Updated version")
      ).map { results =>
        Util.sendEmail(
          results,
          "Distributions of scores, Updated version",
          args.getOrElse("email", "")
        )
      }

    val coverageDistribution = Util
      .printSummaryOfNumericColumn(
        entitiesPipe.map { case (k, v) => v.size },
        Some("# of knownFor entities per user, Updated version")
      ).map { results =>
        Util.sendEmail(
          results,
          "# of knownFor entities per user, Updated version",
          args.getOrElse("email", "")
        )
      }

    Execution
      .zip(
        userToEntitiesWithString.writeExecution(TypedTsv(outputPath)),
        scoreDistribution,
        coverageDistribution
      ).unit
  }
}
