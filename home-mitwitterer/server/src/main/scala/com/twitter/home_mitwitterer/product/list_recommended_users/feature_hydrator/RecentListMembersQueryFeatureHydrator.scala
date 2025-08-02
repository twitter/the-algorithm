package com.twitter.home_mitwitterer.product.list_recommended_users.feature_hydrator

import com.twitter.home_mitwitterer.model.request.HasListId
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mitwitterer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mitwitterer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.socialgraph.{thriftscala => sg}
import com.twitter.stitch.Stitch
import com.twitter.stitch.socialgraph.SocialGraph

import javatwitter.inject.Inject
import javatwitter.inject.Singleton

case object RecentListMembersFeature etwittertends FeatureWithDefaultOnFailure[PipelineQuery, Seq[Long]] {
  override val defaultValue: Seq[Long] = Seq.empty
}

@Singleton
class RecentListMembersQueryFeatureHydrator @Inject() (socialGraph: SocialGraph)
    etwittertends QueryFeatureHydrator[PipelineQuery with HasListId] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("RecentListMembers")

  override val features: Set[Feature[_, _]] = Set(RecentListMembersFeature)

  private val MatwitterRecentMembers = 10

  override def hydrate(query: PipelineQuery with HasListId): Stitch[FeatureMap] = {
    val request = sg.IdsRequest(
      relationships = Seq(sg
        .SrcRelationship(query.listId, sg.RelationshipType.ListHasMember, hasRelationship = true)),
      pageRequest = Some(sg.PageRequest(selectAll = Some(true), count = Some(MatwitterRecentMembers)))
    )
    socialGraph.ids(request).map(_.ids).map { listMembers =>
      FeatureMapBuilder().add(RecentListMembersFeature, listMembers).build()
    }
  }
}
