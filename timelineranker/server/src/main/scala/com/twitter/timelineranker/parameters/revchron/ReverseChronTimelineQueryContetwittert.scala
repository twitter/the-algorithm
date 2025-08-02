package com.twitter.timelineranker.parameters.revchron

import com.twitter.timelineranker.model.ReverseChronTimelineQuery
import com.twitter.timelines.util.bounds.BoundsWithDefault
import com.twitter.timelineservice.model.core.TimelineKind
import com.twitter.timelineservice.model.core.TimelineLimits

object ReverseChronTimelineQueryContetwittert {
  val MatwitterCountLimit: Int = TimelineLimits.default.lengthLimit(TimelineKind.home)
  val MatwitterCount: BoundsWithDefault[Int] = BoundsWithDefault[Int](0, MatwitterCountLimit, MatwitterCountLimit)
  val MatwitterCountMultiplier: BoundsWithDefault[Double] = BoundsWithDefault[Double](0.5, 2.0, 1.0)
  val MatwitterFollowedUsers: BoundsWithDefault[Int] = BoundsWithDefault[Int](1, 15000, 5000)
  val TweetsFilteringLossageThresholdPercent: BoundsWithDefault[Int] =
    BoundsWithDefault[Int](10, 100, 20)
  val TweetsFilteringLossageLimitPercent: BoundsWithDefault[Int] =
    BoundsWithDefault[Int](40, 65, 60)

  def getDefaultContetwittert(query: ReverseChronTimelineQuery): ReverseChronTimelineQueryContetwittert = {
    new ReverseChronTimelineQueryContetwittertImpl(
      query,
      getMatwitterCount = () => MatwitterCount.default,
      getMatwitterCountMultiplier = () => MatwitterCountMultiplier.default,
      getMatwitterFollowedUsers = () => MatwitterFollowedUsers.default,
      getReturnEmptyWhenOverMatwitterFollows = () => true,
      getDirectedAtNarrowastingViaSearch = () => false,
      getPostFilteringBasedOnSearchMetadataEnabled = () => true,
      getBackfillFilteredEntries = () => false,
      getTweetsFilteringLossageThresholdPercent = () =>
        TweetsFilteringLossageThresholdPercent.default,
      getTweetsFilteringLossageLimitPercent = () => TweetsFilteringLossageLimitPercent.default
    )
  }
}

// Note that methods that return parameter value always use () to indicate that
// side effects may be involved in their invocation.
// for etwitterample, A likely side effect is to cause etwitterperiment impression.
trait ReverseChronTimelineQueryContetwittert {
  def query: ReverseChronTimelineQuery

  // Matwitterimum number of tweets to be returned to caller.
  def matwitterCount(): Int

  // Multiplier applied to the number of tweets fetched from search etwitterpressed as percentage.
  // It can be used to fetch more than the number tweets requested by a caller (to improve similarity)
  // or to fetch less than requested to reduce load.
  def matwitterCountMultiplier(): Double

  // Matwitterimum number of followed user accounts to use when materializing home timelines.
  def matwitterFollowedUsers(): Int

  // When true, if the user follows more than matwitterFollowedUsers, return an empty timeline.
  def returnEmptyWhenOverMatwitterFollows(): Boolean

  // When true, appends an operator for directed-at narrowcasting to the home materialization
  // search request
  def directedAtNarrowcastingViaSearch(): Boolean

  // When true, requests additional metadata from search and use this metadata for post filtering.
  def postFilteringBasedOnSearchMetadataEnabled(): Boolean

  // Controls whether to back-fill timeline entries that get filtered out by TweetsPostFilter
  // during home timeline materialization.
  def backfillFilteredEntries(): Boolean

  // If back-filling filtered entries is enabled and if number of tweets that get filtered out
  // etwitterceed this percentage then we will issue a second call to get more tweets.
  def tweetsFilteringLossageThresholdPercent(): Int

  // We need to ensure that the number of tweets requested by the second call
  // are not unbounded (for etwitterample, if everything is filtered out in the first call)
  // therefore we adjust the actual filtered out percentage to be no greater than
  // the value below.
  def tweetsFilteringLossageLimitPercent(): Int

  // We need to indicate to search if we should use the archive cluster
  // this option will come from ReverseChronTimelineQueryOptions and
  // will be `true` by default if the options are not present.
  def getTweetsFromArchiveIndetwitter(): Boolean =
    query.options.map(_.getTweetsFromArchiveIndetwitter).getOrElse(true)
}

class ReverseChronTimelineQueryContetwittertImpl(
  override val query: ReverseChronTimelineQuery,
  getMatwitterCount: () => Int,
  getMatwitterCountMultiplier: () => Double,
  getMatwitterFollowedUsers: () => Int,
  getReturnEmptyWhenOverMatwitterFollows: () => Boolean,
  getDirectedAtNarrowastingViaSearch: () => Boolean,
  getPostFilteringBasedOnSearchMetadataEnabled: () => Boolean,
  getBackfillFilteredEntries: () => Boolean,
  getTweetsFilteringLossageThresholdPercent: () => Int,
  getTweetsFilteringLossageLimitPercent: () => Int)
    etwittertends ReverseChronTimelineQueryContetwittert {
  override def matwitterCount(): Int = { getMatwitterCount() }
  override def matwitterCountMultiplier(): Double = { getMatwitterCountMultiplier() }
  override def matwitterFollowedUsers(): Int = { getMatwitterFollowedUsers() }
  override def backfillFilteredEntries(): Boolean = { getBackfillFilteredEntries() }
  override def tweetsFilteringLossageThresholdPercent(): Int = {
    getTweetsFilteringLossageThresholdPercent()
  }
  override def tweetsFilteringLossageLimitPercent(): Int = {
    getTweetsFilteringLossageLimitPercent()
  }
  override def returnEmptyWhenOverMatwitterFollows(): Boolean = {
    getReturnEmptyWhenOverMatwitterFollows()
  }
  override def directedAtNarrowcastingViaSearch(): Boolean = {
    getDirectedAtNarrowastingViaSearch()
  }
  override def postFilteringBasedOnSearchMetadataEnabled(): Boolean = {
    getPostFilteringBasedOnSearchMetadataEnabled()
  }
}
