package com.twitter.tweet_mixer.functional_component.hydrator

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

object SGSFollowedUsersFeature extends Feature[PipelineQuery, Seq[Long]]

@Singleton
case class SGSFollowedUsersQueryFeatureHydrator @Inject() (
  socialGraphStitchClient: SocialGraphStitchClient)
    extends QueryFeatureHydrator[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("SGSFollowedUsers")

  override val features: Set[Feature[_, _]] = Set(SGSFollowedUsersFeature)

  private val SocialGraphLimit = 2999

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    val userId = query.getRequiredUserId

    val request = sg.IdsRequest(
      relationships = Seq(
        sg.SrcRelationship(userId, sg.RelationshipType.Following, hasRelationship = true),
        sg.SrcRelationship(userId, sg.RelationshipType.Muting, hasRelationship = false)
      ),
      pageRequest = Some(sg.PageRequest(count = Some(SocialGraphLimit)))
    )

    socialGraphStitchClient
      .ids(request).map(_.ids)
      .map { followedUsers =>
        FeatureMapBuilder().add(SGSFollowedUsersFeature, followedUsers).build()
      }
  }
}
