package com.X.home_mixer.functional_component.feature_hydrator

import com.X.home_mixer.model.HomeFeatures.FavoritedByUserIdsFeature
import com.X.home_mixer.model.HomeFeatures.FollowedByUserIdsFeature
import com.X.home_mixer.model.HomeFeatures.SGSValidFollowedByUserIdsFeature
import com.X.home_mixer.model.HomeFeatures.SGSValidLikedByUserIdsFeature
import com.X.product_mixer.component_library.model.candidate.TweetCandidate
import com.X.product_mixer.core.feature.Feature
import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.X.product_mixer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.X.product_mixer.core.model.common.CandidateWithFeatures
import com.X.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.product_mixer.core.util.OffloadFuturePools
import com.X.socialgraph.{thriftscala => sg}
import com.X.stitch.Stitch
import com.X.stitch.socialgraph.SocialGraph
import javax.inject.Inject
import javax.inject.Singleton

/**
 * This hydrator takes liked-by and followed-by user ids and checks via SGS that the viewer is
 * following the engager, that the viewer is not blocking the engager, that the engager is not
 * blocking the viewer, and that the viewer has not muted the engager.
 */
@Singleton
class SGSValidSocialContextFeatureHydrator @Inject() (
  socialGraph: SocialGraph)
    extends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("SGSValidSocialContext")

  override val features: Set[Feature[_, _]] = Set(
    SGSValidFollowedByUserIdsFeature,
    SGSValidLikedByUserIdsFeature
  )

  private val MaxCountUsers = 10

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = OffloadFuturePools.offloadStitch {
    val allSocialContextUserIds =
      candidates.flatMap { candidate =>
        candidate.features.getOrElse(FavoritedByUserIdsFeature, Nil).take(MaxCountUsers) ++
          candidate.features.getOrElse(FollowedByUserIdsFeature, Nil).take(MaxCountUsers)
      }.distinct

    getValidUserIds(query.getRequiredUserId, allSocialContextUserIds).map { validUserIds =>
      candidates.map { candidate =>
        val sgsFilteredLikedByUserIds =
          candidate.features
            .getOrElse(FavoritedByUserIdsFeature, Nil).take(MaxCountUsers)
            .filter(validUserIds.contains)

        val sgsFilteredFollowedByUserIds =
          candidate.features
            .getOrElse(FollowedByUserIdsFeature, Nil).take(MaxCountUsers)
            .filter(validUserIds.contains)

        FeatureMapBuilder()
          .add(SGSValidFollowedByUserIdsFeature, sgsFilteredFollowedByUserIds)
          .add(SGSValidLikedByUserIdsFeature, sgsFilteredLikedByUserIds)
          .build()
      }
    }
  }

  private def getValidUserIds(
    viewerId: Long,
    socialProofUserIds: Seq[Long]
  ): Stitch[Seq[Long]] = {
    if (socialProofUserIds.nonEmpty) {
      val request = sg.IdsRequest(
        relationships = Seq(
          sg.SrcRelationship(
            viewerId,
            sg.RelationshipType.Following,
            targets = Some(socialProofUserIds),
            hasRelationship = true),
          sg.SrcRelationship(
            viewerId,
            sg.RelationshipType.Blocking,
            targets = Some(socialProofUserIds),
            hasRelationship = false),
          sg.SrcRelationship(
            viewerId,
            sg.RelationshipType.BlockedBy,
            targets = Some(socialProofUserIds),
            hasRelationship = false),
          sg.SrcRelationship(
            viewerId,
            sg.RelationshipType.Muting,
            targets = Some(socialProofUserIds),
            hasRelationship = false)
        ),
        pageRequest = Some(sg.PageRequest(selectAll = Some(true)))
      )
      socialGraph.ids(request).map(_.ids)
    } else Stitch.Nil
  }
}
