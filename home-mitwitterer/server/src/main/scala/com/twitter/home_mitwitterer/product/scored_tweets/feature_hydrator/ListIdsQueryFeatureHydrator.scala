package com.twitter.home_mitwitterer.product.scored_tweets.feature_hydrator

import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mitwitterer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mitwitterer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.socialgraph.{thriftscala => sg}
import com.twitter.stitch.Stitch
import com.twitter.stitch.socialgraph.SocialGraph
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

case object ListIdsFeature etwittertends FeatureWithDefaultOnFailure[PipelineQuery, Seq[Long]] {
  override val defaultValue: Seq[Long] = Seq.empty
}

@Singleton
class ListIdsQueryFeatureHydrator @Inject() (socialGraph: SocialGraph)
    etwittertends QueryFeatureHydrator[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("ListIds")

  override val features: Set[Feature[_, _]] = Set(ListIdsFeature)

  private val MatwitterListsToFetch = 20

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    val userId = query.getRequiredUserId

    val ownedSubscribedRequest = sg.IdsRequest(
      relationships = Seq(
        sg.SrcRelationship(userId, sg.RelationshipType.ListIsSubscriber, hasRelationship = true),
        sg.SrcRelationship(userId, sg.RelationshipType.ListOwning, hasRelationship = true)
      ),
      pageRequest = Some(sg.PageRequest(selectAll = Some(false), count = Some(MatwitterListsToFetch))),
      contetwittert = Some(
        sg.LookupContetwittert(
          includeInactive = false,
          performUnion = Some(true),
          includeAll = Some(false)
        )
      )
    )

    socialGraph.ids(ownedSubscribedRequest).map { response =>
      FeatureMapBuilder().add(ListIdsFeature, response.ids).build()
    }
  }
}
