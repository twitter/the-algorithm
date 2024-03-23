package com.ExTwitter.home_mixer.product.list_recommended_users.feature_hydrator

import com.ExTwitter.home_mixer.model.request.HasListId
import com.ExTwitter.home_mixer.product.list_recommended_users.model.ListRecommendedUsersFeatures.IsListMemberFeature
import com.ExTwitter.product_mixer.component_library.model.candidate.UserCandidate
import com.ExTwitter.product_mixer.core.feature.Feature
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMap
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.ExTwitter.product_mixer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.ExTwitter.product_mixer.core.model.common.CandidateWithFeatures
import com.ExTwitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery
import com.ExTwitter.socialgraph.{thriftscala => sg}
import com.ExTwitter.stitch.Stitch
import com.ExTwitter.stitch.socialgraph.SocialGraph

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IsListMemberFeatureHydrator @Inject() (socialGraph: SocialGraph)
    extends BulkCandidateFeatureHydrator[PipelineQuery with HasListId, UserCandidate] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("IsListMember")

  override val features: Set[Feature[_, _]] = Set(IsListMemberFeature)

  override def apply(
    query: PipelineQuery with HasListId,
    candidates: Seq[CandidateWithFeatures[UserCandidate]]
  ): Stitch[Seq[FeatureMap]] = {
    val userIds = candidates.map(_.candidate.id)
    val request = sg.IdsRequest(
      relationships = Seq(
        sg.SrcRelationship(
          source = query.listId,
          relationshipType = sg.RelationshipType.ListHasMember,
          hasRelationship = true,
          targets = Some(userIds))),
      pageRequest = Some(sg.PageRequest(selectAll = Some(true)))
    )

    socialGraph.ids(request).map(_.ids).map { listMembers =>
      val listMembersSet = listMembers.toSet
      candidates.map { candidate =>
        FeatureMapBuilder()
          .add(IsListMemberFeature, listMembersSet.contains(candidate.candidate.id))
          .build()
      }
    }
  }
}
