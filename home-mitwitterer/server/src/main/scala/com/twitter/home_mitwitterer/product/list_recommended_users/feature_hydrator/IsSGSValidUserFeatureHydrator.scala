package com.twitter.home_mitwitterer.product.list_recommended_users.feature_hydrator

import com.twitter.home_mitwitterer.model.request.HasListId
import com.twitter.home_mitwitterer.product.list_recommended_users.model.ListRecommendedUsersFeatures.IsSGSValidUserFeature
import com.twitter.product_mitwitterer.component_library.model.candidate.UserCandidate
import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mitwitterer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.twitter.product_mitwitterer.core.model.common.CandidateWithFeatures
import com.twitter.product_mitwitterer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.socialgraph.{thriftscala => sg}
import com.twitter.stitch.Stitch
import com.twitter.stitch.socialgraph.SocialGraph

import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class IsSGSValidUserFeatureHydrator @Inject() (socialGraph: SocialGraph)
    etwittertends BulkCandidateFeatureHydrator[PipelineQuery with HasListId, UserCandidate] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("IsSGSValidUser")

  override def features: Set[Feature[_, _]] = Set(IsSGSValidUserFeature)

  override def apply(
    query: PipelineQuery with HasListId,
    candidates: Seq[CandidateWithFeatures[UserCandidate]]
  ): Stitch[Seq[FeatureMap]] = {
    val sourceId = query.getRequiredUserId
    val targetUserIds = candidates.map(_.candidate.id)
    val request = sg.IdsRequest(
      relationships = Seq(
        sg.SrcRelationship(
          source = sourceId,
          relationshipType = sg.RelationshipType.Blocking,
          hasRelationship = true,
          targets = Some(targetUserIds)),
        sg.SrcRelationship(
          source = sourceId,
          relationshipType = sg.RelationshipType.BlockedBy,
          hasRelationship = true,
          targets = Some(targetUserIds)),
        sg.SrcRelationship(
          source = sourceId,
          relationshipType = sg.RelationshipType.Muting,
          hasRelationship = true,
          targets = Some(targetUserIds))
      ),
      pageRequest = Some(sg.PageRequest(selectAll = Some(true))),
      contetwittert = Some(sg.LookupContetwittert(performUnion = Some(true)))
    )

    socialGraph.ids(request).map(_.ids).map(_.toSet).map { hasRelationshipUserIds =>
      candidates.map { candidate =>
        FeatureMapBuilder()
          .add(IsSGSValidUserFeature, !hasRelationshipUserIds.contains(candidate.candidate.id))
          .build()
      }
    }
  }
}
