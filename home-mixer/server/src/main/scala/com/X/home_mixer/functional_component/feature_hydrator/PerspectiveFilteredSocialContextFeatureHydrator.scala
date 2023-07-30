package com.X.home_mixer.functional_component.feature_hydrator

import com.X.home_mixer.model.HomeFeatures.FavoritedByUserIdsFeature
import com.X.home_mixer.model.HomeFeatures.PerspectiveFilteredLikedByUserIdsFeature
import com.X.product_mixer.component_library.model.candidate.TweetCandidate
import com.X.product_mixer.core.feature.Feature
import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.X.product_mixer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.X.product_mixer.core.model.common.CandidateWithFeatures
import com.X.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.product_mixer.core.util.OffloadFuturePools
import com.X.stitch.Stitch
import com.X.stitch.timelineservice.TimelineService
import com.X.stitch.timelineservice.TimelineService.GetPerspectives
import com.X.timelineservice.thriftscala.PerspectiveType
import com.X.timelineservice.thriftscala.PerspectiveType.Favorited
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Filter out unlike edges from liked-by tweets
 * Useful if the likes come from a cache and because UTEG does not fully remove unlike edges.
 */
@Singleton
class PerspectiveFilteredSocialContextFeatureHydrator @Inject() (timelineService: TimelineService)
    extends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("PerspectiveFilteredSocialContext")

  override val features: Set[Feature[_, _]] = Set(PerspectiveFilteredLikedByUserIdsFeature)

  private val MaxCountUsers = 10
  private val favoritePerspectiveSet: Set[PerspectiveType] = Set(Favorited)

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = OffloadFuturePools.offloadStitch {
    val engagingUserIdtoTweetId = candidates.flatMap { candidate =>
      candidate.features
        .getOrElse(FavoritedByUserIdsFeature, Seq.empty).take(MaxCountUsers)
        .map(favoritedBy => favoritedBy -> candidate.candidate.id)
    }

    val queries = engagingUserIdtoTweetId.map {
      case (userId, tweetId) =>
        GetPerspectives.Query(userId = userId, tweetId = tweetId, types = favoritePerspectiveSet)
    }

    Stitch.collect(queries.map(timelineService.getPerspective)).map { perspectiveResults =>
      val validUserIdTweetIds: Set[(Long, Long)] =
        queries
          .zip(perspectiveResults)
          .collect { case (query, perspective) if perspective.favorited => query }
          .map(query => (query.userId, query.tweetId))
          .toSet

      candidates.map { candidate =>
        val perspectiveFilteredFavoritedByUserIds: Seq[Long] = candidate.features
          .getOrElse(FavoritedByUserIdsFeature, Seq.empty).take(MaxCountUsers)
          .filter { userId => validUserIdTweetIds.contains((userId, candidate.candidate.id)) }

        FeatureMapBuilder()
          .add(PerspectiveFilteredLikedByUserIdsFeature, perspectiveFilteredFavoritedByUserIds)
          .build()
      }
    }
  }
}
