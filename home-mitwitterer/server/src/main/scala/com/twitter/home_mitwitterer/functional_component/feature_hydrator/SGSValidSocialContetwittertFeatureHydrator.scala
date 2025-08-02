package com.twitter.home_mitwitterer.functional_component.feature_hydrator

import com.twitter.home_mitwitterer.model.HomeFeatures.FavoritedByUserIdsFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.FollowedByUserIdsFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.SGSValidFollowedByUserIdsFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.SGSValidLikedByUserIdsFeature
import com.twitter.product_mitwitterer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mitwitterer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.twitter.product_mitwitterer.core.model.common.CandidateWithFeatures
import com.twitter.product_mitwitterer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.util.OffloadFuturePools
import com.twitter.socialgraph.{thriftscala => sg}
import com.twitter.stitch.Stitch
import com.twitter.stitch.socialgraph.SocialGraph
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

/**
 * This hydrator takes liked-by and followed-by user ids and checks via SGS that the viewer is
 * following the engager, that the viewer is not blocking the engager, that the engager is not
 * blocking the viewer, and that the viewer has not muted the engager.
 */
@Singleton
class SGSValidSocialContetwittertFeatureHydrator @Inject() (
  socialGraph: SocialGraph)
    etwittertends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("SGSValidSocialContetwittert")

  override val features: Set[Feature[_, _]] = Set(
    SGSValidFollowedByUserIdsFeature,
    SGSValidLikedByUserIdsFeature
  )

  private val MatwitterCountUsers = 10

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = OffloadFuturePools.offloadStitch {
    val allSocialContetwittertUserIds =
      candidates.flatMap { candidate =>
        candidate.features.getOrElse(FavoritedByUserIdsFeature, Nil).take(MatwitterCountUsers) ++
          candidate.features.getOrElse(FollowedByUserIdsFeature, Nil).take(MatwitterCountUsers)
      }.distinct

    getValidUserIds(query.getRequiredUserId, allSocialContetwittertUserIds).map { validUserIds =>
      candidates.map { candidate =>
        val sgsFilteredLikedByUserIds =
          candidate.features
            .getOrElse(FavoritedByUserIdsFeature, Nil).take(MatwitterCountUsers)
            .filter(validUserIds.contains)

        val sgsFilteredFollowedByUserIds =
          candidate.features
            .getOrElse(FollowedByUserIdsFeature, Nil).take(MatwitterCountUsers)
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
