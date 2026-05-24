package com.twitter.home_mixer.product.scored_tweets.feature_hydrator

import com.twitter.product_mixer.component_library.feature_hydrator.query.social_graph.SGSFollowedUsersFeature
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.socialgraph.{thriftscala => sg}
import com.twitter.stitch.Stitch
import com.twitter.stitch.socialgraph.{SocialGraph => SocialGraphStitchClient}
import javax.inject.Inject
import javax.inject.Singleton

object SGSMutuallyFollowedUsersFeature extends Feature[PipelineQuery, Seq[Long]]

@Singleton
case class SGSMutuallyFollowedUserHydrator @Inject() (
  socialGraphStitchClient: SocialGraphStitchClient)
    extends QueryFeatureHydrator[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("SGSMutuallyFollowedUsers")

  override val features: Set[Feature[_, _]] = Set(SGSMutuallyFollowedUsersFeature)

  private val SocialGraphLimit = 14999
  private val MaxFollowTargets = 1500
  private val DefaultFeatureMap = FeatureMapBuilder()
    .add(SGSMutuallyFollowedUsersFeature, Seq.empty)
    .build()

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {

    val sgsFollowedUserIds =
      query.features.map(_.getOrElse(SGSFollowedUsersFeature, Seq.empty)).toSeq.flatten

    if (sgsFollowedUserIds.nonEmpty) {
      val mutuallyFollowedRequest = sg.IdsRequest(
        relationships = Seq(
          sg.SrcRelationship(
            query.getRequiredUserId,
            sg.RelationshipType.FollowedBy,
            hasRelationship = true,
            targets = Some(sgsFollowedUserIds.take(MaxFollowTargets))
          ),
        ),
        pageRequest = Some(sg.PageRequest(count = Some(SocialGraphLimit)))
      )
      socialGraphStitchClient.ids(mutuallyFollowedRequest).map(_.ids).map { mutuallyFollowedUsers =>
        FeatureMapBuilder()
          .add(SGSMutuallyFollowedUsersFeature, mutuallyFollowedUsers)
          .build()
      }
    } else Stitch.value(DefaultFeatureMap)
  }
}
