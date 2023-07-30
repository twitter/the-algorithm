package com.X.home_mixer.product.list_recommended_users.feature_hydrator

import com.X.home_mixer.model.request.HasListId
import com.X.home_mixer.product.list_recommended_users.model.ListRecommendedUsersFeatures.IsSGSValidUserFeature
import com.X.product_mixer.component_library.model.candidate.UserCandidate
import com.X.product_mixer.core.feature.Feature
import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.X.product_mixer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.X.product_mixer.core.model.common.CandidateWithFeatures
import com.X.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.socialgraph.{thriftscala => sg}
import com.X.stitch.Stitch
import com.X.stitch.socialgraph.SocialGraph

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IsSGSValidUserFeatureHydrator @Inject() (socialGraph: SocialGraph)
    extends BulkCandidateFeatureHydrator[PipelineQuery with HasListId, UserCandidate] {

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
      context = Some(sg.LookupContext(performUnion = Some(true)))
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
