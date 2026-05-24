package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.follow_recommendations.{thriftscala => frs}
import com.twitter.product_mixer.component_library.candidate_source.recommendations.UserFollowRecommendationsCandidateSource
import com.twitter.product_mixer.component_library.candidate_source.recommendations.CachedUserFollowRecommendationsCandidateSource
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.candidate_source.strato.StratoKeyView
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import javax.inject.Inject
import javax.inject.Singleton

object FrsSeedUserIdsFeature extends Feature[TweetCandidate, Option[Seq[Long]]]
object FrsUserToFollowedByUserIdsFeature extends Feature[TweetCandidate, Map[Long, Seq[Long]]]

@Singleton
case class FrsSeedUsersQueryFeatureHydrator @Inject() (
  userFollowRecommendationsCandidateSource: UserFollowRecommendationsCandidateSource,
  cachedUserFollowRecommendationsCandidateSource: CachedUserFollowRecommendationsCandidateSource)
    extends QueryFeatureHydrator[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("FrsSeedUsers")

  private val maxUsersToFetch = 100

  override val features: Set[Feature[_, _]] = Set(
    FrsSeedUserIdsFeature,
    FrsUserToFollowedByUserIdsFeature
  )

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    val frsRequest = frs.RecommendationRequest(
      clientContext = frs.ClientContext(query.getOptionalUserId),
      displayLocation = frs.DisplayLocation.HomeTimelineTweetRecs,
      maxResults = Some(maxUsersToFetch)
    )

    userFollowRecommendationsCandidateSource(StratoKeyView(frsRequest, Unit))
      .map { userRecommendations: Seq[frs.UserRecommendation] =>
        val seedUserIds = userRecommendations.map(_.userId)
        val seedUserIdsSet = seedUserIds.toSet

        val userToFollowedByUserIds: Map[Long, Seq[Long]] = userRecommendations.flatMap {
          userRecommendation =>
            if (seedUserIdsSet.contains(userRecommendation.userId)) {
              val followProof =
                userRecommendation.reason.flatMap(_.accountProof).flatMap(_.followProof)
              val followedByUserIds = followProof.map(_.userIds).getOrElse(Seq.empty)
              Some(userRecommendation.userId -> followedByUserIds)
            } else {
              None
            }
        }.toMap

        FeatureMapBuilder()
          .add(FrsSeedUserIdsFeature, Some(seedUserIds))
          .add(FrsUserToFollowedByUserIdsFeature, userToFollowedByUserIds)
          .build()
      }
  }
}
