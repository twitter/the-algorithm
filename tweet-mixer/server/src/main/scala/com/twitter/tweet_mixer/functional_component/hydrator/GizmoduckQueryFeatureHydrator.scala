package com.twitter.tweet_mixer.functional_component.hydrator

import com.twitter.gizmoduck.{thriftscala => gt}
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.Conditionally
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.stitch.gizmoduck.Gizmoduck
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.EnableGizmoduckQueryFeatureHydrator
import javax.inject.Inject
import javax.inject.Singleton

object UserFollowersCountFeature extends Feature[PipelineQuery, Option[Int]]

@Singleton
case class GizmoduckQueryFeatureHydrator @Inject() (gizmoduck: Gizmoduck)
    extends QueryFeatureHydrator[PipelineQuery]
    with Conditionally[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("Gizmoduck")

  override val features: Set[Feature[_, _]] = Set(UserFollowersCountFeature)

  private val queryFields: Set[gt.QueryFields] = Set(gt.QueryFields.Counts)

  override def onlyIf(query: PipelineQuery): Boolean =
    query.params(EnableGizmoduckQueryFeatureHydrator)

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    val userId = query.getRequiredUserId
    gizmoduck
      .getUserById(
        userId = userId,
        queryFields = queryFields,
        context = gt.LookupContext(forUserId = Some(userId))
      ).map { user =>
        FeatureMap(UserFollowersCountFeature, user.counts.map(_.followers.toInt))
      }.rescue {
        case _ => Stitch.value(FeatureMap(UserFollowersCountFeature, None))
      }
  }
}
