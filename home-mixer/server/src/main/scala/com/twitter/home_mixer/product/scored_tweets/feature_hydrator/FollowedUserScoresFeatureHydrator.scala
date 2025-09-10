package com.twitter.home_mixer.product.scored_tweets.feature_hydrator

import com.twitter.home_mixer.model.HomeFeatures.RealGraphInNetworkScoresFeature
import com.twitter.product_mixer.component_library.feature_hydrator.query.social_graph.SGSFollowedUsersFeature
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import javax.inject.Inject
import javax.inject.Singleton

object FollowedUserScoresFeature extends Feature[PipelineQuery, Map[Long, Double]]

@Singleton
case class FollowedUserScoresFeatureHydrator @Inject() ()
    extends QueryFeatureHydrator[PipelineQuery] {

  private val UpdateFollowedUserThreshold = 1000
  private val MaxFollowedUsers = 1500
  private val DefaultAuthorScore = 2D

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("FollowedUserScores")

  override def features: Set[Feature[_, _]] = Set(FollowedUserScoresFeature)

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    val sgsFollowedUserIds =
      query.features.map(_.getOrElse(SGSFollowedUsersFeature, Seq.empty)).toSeq.flatten
    val sgsFollowedUserIdSet = sgsFollowedUserIds.toSet
    val authorScoreMap = query.features
      .map(_.getOrElse(RealGraphInNetworkScoresFeature, Map.empty[Long, Double]))
      .getOrElse(Map.empty)

    val filteredAuthorScoreMap = authorScoreMap.filter { entry =>
      sgsFollowedUserIdSet.contains(entry._1)
    }

    val updatedSgsFollowedUserIds = if (sgsFollowedUserIds.size >= UpdateFollowedUserThreshold) {
      filteredAuthorScoreMap.keySet ++
        sgsFollowedUserIds
          .filter(!filteredAuthorScoreMap.keySet.contains(_))
          .take(Math.max(0, MaxFollowedUsers - filteredAuthorScoreMap.size))
    } else sgsFollowedUserIds

    val existingAuthorIdScores = filteredAuthorScoreMap.values.toList.sorted
    val imputedScoreIndex =
      Math.min(existingAuthorIdScores.length - 1, (existingAuthorIdScores.length * 0.5f).toInt)
    val imputedScore =
      if (imputedScoreIndex >= 0) existingAuthorIdScores(imputedScoreIndex)
      else DefaultAuthorScore

    val updatedAuthorScoreMap = updatedSgsFollowedUserIds
      .map(_ -> imputedScore).toMap ++ filteredAuthorScoreMap

    Stitch.value {
      FeatureMapBuilder()
        .add(FollowedUserScoresFeature, updatedAuthorScoreMap)
        .build()
    }
  }
}
