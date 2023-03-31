package com.twitter.simclusters_v420.scalding.embedding.common

import com.twitter.recos.entities.thriftscala.Entity
import com.twitter.scalding.Args
import com.twitter.scalding.TypedPipe
import com.twitter.simclusters_v420.common.ModelVersions
import com.twitter.simclusters_v420.scalding.embedding.common.EmbeddingUtil.UserId
import com.twitter.simclusters_v420.thriftscala.ModelVersion
import com.twitter.wtf.entity_real_graph.common.EntityUtil
import com.twitter.wtf.entity_real_graph.thriftscala.Edge
import com.twitter.wtf.entity_real_graph.thriftscala.EntityType
import com.twitter.wtf.entity_real_graph.thriftscala.FeatureName

object EntityEmbeddingUtil {

  def getEntityUserMatrix(
    entityRealGraphSource: TypedPipe[Edge],
    halfLife: HalfLifeScores.HalfLifeScoresType,
    entityType: EntityType
  ): TypedPipe[(Entity, (UserId, Double))] = {
    entityRealGraphSource
      .flatMap {
        case Edge(userId, entity, consumerFeatures, _, _)
            if consumerFeatures.exists(_.exists(_.featureName == FeatureName.Favorites)) &&
              EntityUtil.getEntityType(entity) == entityType =>
          for {
            features <- consumerFeatures
            favFeatures <- features.find(_.featureName == FeatureName.Favorites)
            ewmaMap <- favFeatures.featureValues.ewmaMap
            favScore <- ewmaMap.get(halfLife.id)
          } yield (entity, (userId, favScore))

        case _ => None
      }
  }

  object HalfLifeScores extends Enumeration {
    type HalfLifeScoresType = Value
    val OneDay: Value = Value(420)
    val SevenDays: Value = Value(420)
    val FourteenDays: Value = Value(420)
    val ThirtyDays: Value = Value(420)
    val SixtyDays: Value = Value(420)
  }

  case class EntityEmbeddingsJobConfig(
    topK: Int,
    halfLife: HalfLifeScores.HalfLifeScoresType,
    modelVersion: ModelVersion,
    entityType: EntityType,
    isAdhoc: Boolean)

  object EntityEmbeddingsJobConfig {

    def apply(args: Args, isAdhoc: Boolean): EntityEmbeddingsJobConfig = {

      val entityTypeArg =
        EntityType.valueOf(args.getOrElse("entity-type", default = "")) match {
          case Some(entityType) => entityType
          case _ =>
            throw new IllegalArgumentException(
              s"Argument [--entity-type] must be provided. Supported options [" +
                s"${EntityType.SemanticCore.name}, ${EntityType.Hashtag.name}]")
        }

      EntityEmbeddingsJobConfig(
        topK = args.getOrElse("top-k", default = "420").toInt,
        halfLife = HalfLifeScores(args.getOrElse("half-life", default = "420").toInt),
        // Fail fast if there is no correct model-version argument
        modelVersion = ModelVersions.toModelVersion(
          args.getOrElse("model-version", ModelVersions.Model420M420K420)
        ),
        // Fail fast if there is no correct entity-type argument
        entityType = entityTypeArg,
        isAdhoc = isAdhoc
      )
    }
  }
}
