package com.twitter.home_mixer.product.for_you.feature_hydrator

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.gizmoduck.{thriftscala => gt}
import com.twitter.home_mixer.model.HomeFeatures.{
  TLSOriginalTweetsWithConfirmedAuthorFeature,
  TweetAuthorFollowersFeature
}
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.{FeatureMap, FeatureMapBuilder}
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.util.OffloadFuturePools
import com.twitter.stitch.Stitch
import javax.inject.{Inject, Singleton}
import com.twitter.util.logging.Logging

@Singleton
class TweetAuthorFollowersFeatureHydrator @Inject() (
  gizmoduck: gt.UserService.MethodPerEndpoint,
  stats: StatsReceiver)
  extends QueryFeatureHydrator[PipelineQuery]
  with Logging {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("TweetAuthorFollowers")

  override val features: Set[Feature[_, _]] =
    Set(TweetAuthorFollowersFeature)

  private val queryFields: Set[gt.QueryFields] = Set(
    gt.QueryFields.Counts
  )

  private val lookupContext = gt.LookupContext(isRequestSheddable = Some(true))

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    val originals: Seq[(Long, Long)] = query.features.map(_.getOrElse(TLSOriginalTweetsWithConfirmedAuthorFeature, Seq())).getOrElse(Seq.empty)

    if (originals.isEmpty) {
      return Stitch.value(
        FeatureMapBuilder()
          .add(TweetAuthorFollowersFeature, Map.empty[Long, Option[Long]])
          .build()
      )
    }

    val authorIds = originals.map(_._2).distinct

    OffloadFuturePools.offloadFuture {
      gizmoduck.get(lookupContext, authorIds, queryFields).map { gizmoduckResponse =>
        val hydratedUsersMap = gizmoduckResponse.collect {
          case userResult if userResult.user.isDefined =>
            val user = userResult.user.get
            user.id -> user
        }.toMap.mapValues(user => user.counts.map(_.followers))

        val tweetAuthorFollowersMap = originals.map { case (tweetId, authorId) =>
          tweetId -> hydratedUsersMap.getOrElse(authorId, None)
        }.toMap

        FeatureMapBuilder()
          .add(TweetAuthorFollowersFeature, tweetAuthorFollowersMap)
          .build()
      }
    }
  }
} 