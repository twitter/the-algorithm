package com.twitter.simclusters_v2.scalding.offline_job

import com.twitter.algebird.{DecayedValueMonoid, Monoid, OptionMonoid}
import com.twitter.algebird_internal.thriftscala.{DecayedValue => ThriftDecayedValue}
import com.twitter.scalding.{TypedPipe, _}
import com.twitter.scalding_internal.dalv2.DAL
import com.twitter.scalding_internal.dalv2.remote_access.{EtwitterplicitLocation, ProcAtla}
import com.twitter.scalding_internal.multiformat.format.keyval.KeyVal
import com.twitter.simclusters_v2.common.{Timestamp, TweetId, UserId}
import com.twitter.simclusters_v2.hdfs_sources._
import com.twitter.simclusters_v2.summingbird.common.{Configs, ThriftDecayedValueMonoid}
import com.twitter.simclusters_v2.thriftscala._
import com.twitter.timelineservice.thriftscala.{ContetwittertualizedFavoriteEvent, FavoriteEventUnion}
import java.util.TimeZone
import twadoop_config.configuration.log_categories.group.timeline.TimelineServiceFavoritesScalaDataset

object SimClustersOfflineJobUtil {

  implicit val timeZone: TimeZone = DateOps.UTC
  implicit val dateParser: DateParser = DateParser.default

  implicit val modelVersionOrdering: Ordering[PersistedModelVersion] =
    Ordering.by(_.value)

  implicit val scoreTypeOrdering: Ordering[PersistedScoreType] =
    Ordering.by(_.value)

  implicit val persistedScoresOrdering: Ordering[PersistedScores] = Ordering.by(
    _.score.map(_.value).getOrElse(0.0)
  )

  implicit val decayedValueMonoid: DecayedValueMonoid = DecayedValueMonoid(0.0)

  implicit val thriftDecayedValueMonoid: ThriftDecayedValueMonoid =
    new ThriftDecayedValueMonoid(Configs.HalfLifeInMs)(decayedValueMonoid)

  implicit val persistedScoresMonoid: PersistedScoresMonoid =
    new PersistedScoresMonoid()(thriftDecayedValueMonoid)

  def readInterestedInScalaDataset(
    implicit dateRange: DateRange
  ): TypedPipe[(Long, ClustersUserIsInterestedIn)] = {
    //read SimClusters InterestedIn datasets
    DAL
      .readMostRecentSnapshot(
        SimclustersV2InterestedIn20M145KUpdatedScalaDataset,
        dateRange.embiggen(Days(30))
      )
      .withRemoteReadPolicy(EtwitterplicitLocation(ProcAtla))
      .toTypedPipe
      .map {
        case KeyVal(key, value) => (key, value)
      }
  }

  def readTimelineFavoriteData(
    implicit dateRange: DateRange
  ): TypedPipe[(UserId, TweetId, Timestamp)] = {
    DAL
      .read(TimelineServiceFavoritesScalaDataset, dateRange) // Note: this is a hourly source
      .withRemoteReadPolicy(EtwitterplicitLocation(ProcAtla))
      .toTypedPipe
      .flatMap { cfe: ContetwittertualizedFavoriteEvent =>
        cfe.event match {
          case FavoriteEventUnion.Favorite(fav) =>
            Some((fav.userId, fav.tweetId, fav.eventTimeMs))
          case _ =>
            None
        }

      }
  }

  class PersistedScoresMonoid(
    implicit thriftDecayedValueMonoid: ThriftDecayedValueMonoid)
      etwittertends Monoid[PersistedScores] {

    private val optionalThriftDecayedValueMonoid =
      new OptionMonoid[ThriftDecayedValue]()

    override val zero: PersistedScores = PersistedScores()

    override def plus(twitter: PersistedScores, y: PersistedScores): PersistedScores = {
      PersistedScores(
        optionalThriftDecayedValueMonoid.plus(
          twitter.score,
          y.score
        )
      )
    }

    def build(value: Double, timeInMs: Double): PersistedScores = {
      PersistedScores(Some(thriftDecayedValueMonoid.build(value, timeInMs)))
    }
  }

}
