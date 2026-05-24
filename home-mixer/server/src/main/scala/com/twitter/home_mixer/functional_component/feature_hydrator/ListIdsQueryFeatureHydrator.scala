package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.home_mixer.param.HomeGlobalParams.ListMandarinTweetsParams.ListMandarinTweetsEnable
import com.twitter.home_mixer.param.HomeGlobalParams.ListMandarinTweetsParams.ListMandarinTweetsLists
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.socialgraph.{thriftscala => sg}
import com.twitter.stitch.Stitch
import com.twitter.stitch.socialgraph.SocialGraph

import javax.inject.Inject
import javax.inject.Singleton

case object ListIdsFeature extends FeatureWithDefaultOnFailure[PipelineQuery, Seq[Long]] {
  override val defaultValue: Seq[Long] = Seq.empty
}

@Singleton
class ListIdsQueryFeatureHydrator @Inject() (socialGraph: SocialGraph)
    extends QueryFeatureHydrator[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("ListIds")

  override val features: Set[Feature[_, _]] = Set(ListIdsFeature)

  private val MaxListsToFetch = 20

  private def buildIdsRequest(userId: Long, relationshipType: sg.RelationshipType) = sg.IdsRequest(
    relationships = Seq(
      sg.SrcRelationship(userId, relationshipType, hasRelationship = true),
      sg.SrcRelationship(userId, sg.RelationshipType.ListMuting, hasRelationship = false)
    ),
    pageRequest = Some(sg.PageRequest(selectAll = Some(false), count = Some(MaxListsToFetch))),
    context = Some(sg.LookupContext(performUnion = Some(false), includeAll = Some(false)))
  )

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    val userId = query.getRequiredUserId

    val subscribedRequest = buildIdsRequest(userId, sg.RelationshipType.ListIsSubscriber)
    val ownedRequest = buildIdsRequest(userId, sg.RelationshipType.ListOwning)

    Stitch.join(socialGraph.ids(ownedRequest), socialGraph.ids(subscribedRequest)).map {
      case (ownedResponse, subscribedResponse) =>
        val recommendedListIds =
          if (query.params(ListMandarinTweetsEnable))
            query.params(ListMandarinTweetsLists)
          else
            Seq.empty

        val ids = (ownedResponse.ids ++ subscribedResponse.ids ++ recommendedListIds).distinct

        FeatureMapBuilder().add(ListIdsFeature, ids).build()
    }
  }
}
