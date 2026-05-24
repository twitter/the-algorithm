package com.twitter.home_mixer.product.for_you.feature_hydrator

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.model.HomeFeatures.TimelineServiceTweetsFeature
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.{FeatureMap, FeatureMapBuilder}
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.strato.generated.client.tweetypie.federated.ApiCountsOnTweetClientColumn
import com.twitter.strato.client.Client
import javax.inject.{Inject, Singleton}
import com.twitter.util.logging.Logging
import javax.inject.Named
import com.twitter.home_mixer.param.HomeMixerInjectionNames.BatchedStratoClientWithLongTimeout

case class TweetEngagementCounts(
  favoriteCount: Option[Long],
  replyCount: Option[Long],
  retweetCount: Option[Long],
  quoteCount: Option[Long],
  bookmarkCount: Option[Long]
)

object TweetEngagementCountsFeature extends Feature[PipelineQuery, Map[Long, TweetEngagementCounts]]


@Singleton
class TweetEngagementsFeatureHydrator @Inject() (
  @Named(BatchedStratoClientWithLongTimeout) stratoClient: Client,
  stats: StatsReceiver)
  extends QueryFeatureHydrator[PipelineQuery]
  with Logging {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("TweetEngagements")

  override val features: Set[Feature[_, _]] =
    Set(TweetEngagementCountsFeature)

  private val engagementCountsFoundCounter =
    stats.counter("TweetEngagementsFound")
  private val engagementCountsNotFoundCounter =
    stats.counter("TweetEngagementsNotFound")

  private def fetchEngagementCountsFromStrato(tweetId: Long): Stitch[Option[TweetEngagementCounts]] = {
    val fetcher = new ApiCountsOnTweetClientColumn(stratoClient).fetcher
    fetcher
      .fetch(tweetId)
      .map(_.v)
      .map {
        case Some(result) =>
          engagementCountsFoundCounter.incr()
          Some(TweetEngagementCounts(
            favoriteCount = result.favoriteCount,
            replyCount = result.replyCount,
            retweetCount = result.retweetCount,
            quoteCount = result.quoteCount,
            bookmarkCount = result.bookmarkCount
          ))
        case None =>
          engagementCountsNotFoundCounter.incr()
          None
      }
  }

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    val tweetIds: Seq[Long] = query.features.map(_.getOrElse(TimelineServiceTweetsFeature, Seq.empty[Long])).getOrElse(Seq.empty)

    if (tweetIds.isEmpty)
      return Stitch.value(
        FeatureMapBuilder()
          .add(TweetEngagementCountsFeature, Map.empty[Long, TweetEngagementCounts])
          .build()
      )

    val fetchedEngagementCounts: Stitch[Map[Long, TweetEngagementCounts]] =
      Stitch
        .collect(tweetIds.map { id =>
          fetchEngagementCountsFromStrato(id).map(_.map(counts => id -> counts))
        })
        .map(_.flatten.toMap)

    fetchedEngagementCounts.map { found =>
      FeatureMapBuilder()
        .add(TweetEngagementCountsFeature, found)
        .build()
    }
  }
}
