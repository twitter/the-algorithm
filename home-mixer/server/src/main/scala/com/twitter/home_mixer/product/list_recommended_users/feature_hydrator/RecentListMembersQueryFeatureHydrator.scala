package com.twitter.home_mixer.product.list_recommended_users.feature_hydrator

import com.twitter.home_mixer.model.request.HasListId
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.socialgraph.{thriftscala => sg}
import com.twitter.stitch.Stitch
import com.twitter.stitch.socialgraph.SocialGraph

import javax.inject.Inject
import javax.inject.Singleton

case object RecentListMembersFeature extends FeatureWithDefaultOnFailure[PipelineQuery, Seq[Long]] {
  override val defaultValue: Seq[Long] = Seq.empty
}

@Singleton
class RecentListMembersQueryFeatureHydrator @Inject() (socialGraph: SocialGraph)
    extends QueryFeatureHydrator[PipelineQuery with HasListId] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("RecentListMembers")

  override val features: Set[Feature[_, _]] = Set(RecentListMembersFeature)

  private val MaxRecentMembers = 10

  override def hydrate(query: PipelineQuery with HasListId): Stitch[FeatureMap] = {
    val request = sg.IdsRequest(
      relationships = Seq(sg
        .SrcRelationship(query.listId, sg.RelationshipType.ListHasMember, hasRelationship = true)),
      pageRequest = Some(sg.PageRequest(selectAll = Some(true), count = Some(MaxRecentMembers)))
    )
    socialGraph.ids(request).map(_.ids).map { listMembers =>
      FeatureMapBuilder().add(RecentListMembersFeature, listMembers).build()
    }
  }
}
