package com.X.interaction_graph.scio.agg_direct_interactions

import com.spotify.scio.ScioContext
import com.spotify.scio.values.SCollection
import com.X.beam.job.ServiceIdentifierOptions
import com.X.cde.scio.dal_read.SourceUtil
import com.X.timelineservice.thriftscala.ContextualizedFavoriteEvent
import com.X.twadoop.user.gen.thriftscala.CombinedUser
import com.X.tweetsource.common.thriftscala.UnhydratedFlatTweet
import com.X.tweetypie.thriftscala.TweetMediaTagEvent
import com.X.usersource.snapshot.combined.UsersourceScalaDataset
import com.X.util.Duration
import org.joda.time.Interval
import twadoop_config.configuration.log_categories.group.timeline.TimelineServiceFavoritesScalaDataset
import twadoop_config.configuration.log_categories.group.tweetypie.TweetypieMediaTagEventsScalaDataset
import tweetsource.common.UnhydratedFlatScalaDataset

case class InteractionGraphAggDirectInteractionsSource(
  pipelineOptions: InteractionGraphAggDirectInteractionsOption
)(
  implicit sc: ScioContext) {
  val dalEnvironment: String = pipelineOptions
    .as(classOf[ServiceIdentifierOptions])
    .getEnvironment()

  def readFavorites(dateInterval: Interval): SCollection[ContextualizedFavoriteEvent] =
    SourceUtil.readDALDataset[ContextualizedFavoriteEvent](
      dataset = TimelineServiceFavoritesScalaDataset,
      interval = dateInterval,
      dalEnvironment = dalEnvironment
    )

  def readPhotoTags(dateInterval: Interval): SCollection[TweetMediaTagEvent] =
    SourceUtil.readDALDataset[TweetMediaTagEvent](
      dataset = TweetypieMediaTagEventsScalaDataset,
      interval = dateInterval,
      dalEnvironment = dalEnvironment)

  def readTweetSource(dateInterval: Interval): SCollection[UnhydratedFlatTweet] =
    SourceUtil.readDALDataset[UnhydratedFlatTweet](
      dataset = UnhydratedFlatScalaDataset,
      interval = dateInterval,
      dalEnvironment = dalEnvironment)

  def readCombinedUsers(): SCollection[CombinedUser] =
    SourceUtil.readMostRecentSnapshotNoOlderThanDALDataset[CombinedUser](
      dataset = UsersourceScalaDataset,
      noOlderThan = Duration.fromDays(5),
      dalEnvironment = dalEnvironment
    )
}
