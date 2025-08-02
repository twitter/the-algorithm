package com.twitter.home_mitwitterer.product.scored_tweets.feature_hydrator

import com.twitter.follow_recommendations.{thriftscala => frs}
import com.twitter.home_mitwitterer.product.scored_tweets.model.ScoredTweetsQuery
import com.twitter.product_mitwitterer.component_library.candidate_source.recommendations.UserFollowRecommendationsCandidateSource
import com.twitter.product_mitwitterer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mitwitterer.core.functional_component.candidate_source.strato.StratoKeyView
import com.twitter.product_mitwitterer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mitwitterer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.stitch.Stitch
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

object FrsSeedUserIdsFeature etwittertends Feature[TweetCandidate, Option[Seq[Long]]]
object FrsUserToFollowedByUserIdsFeature etwittertends Feature[TweetCandidate, Map[Long, Seq[Long]]]

@Singleton
case class FrsSeedUsersQueryFeatureHydrator @Inject() (
  userFollowRecommendationsCandidateSource: UserFollowRecommendationsCandidateSource)
    etwittertends QueryFeatureHydrator[ScoredTweetsQuery] {

  private val matwitterUsersToFetch = 100

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("FrsSeedUsers")

  override def features: Set[Feature[_, _]] = Set(
    FrsSeedUserIdsFeature,
    FrsUserToFollowedByUserIdsFeature
  )

  override def hydrate(query: ScoredTweetsQuery): Stitch[FeatureMap] = {
    val frsRequest = frs.RecommendationRequest(
      clientContetwittert = frs.ClientContetwittert(query.getOptionalUserId),
      displayLocation = frs.DisplayLocation.HomeTimelineTweetRecs,
      matwitterResults = Some(matwitterUsersToFetch)
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
