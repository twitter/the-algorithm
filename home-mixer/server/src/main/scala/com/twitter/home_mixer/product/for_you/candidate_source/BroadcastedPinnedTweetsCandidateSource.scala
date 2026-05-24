package com.twitter.home_mixer.product.for_you.candidate_source

import com.twitter.home_mixer.product.for_you.model.ForYouQuery
import com.twitter.home_mixer.model.HomeFeatures.ImpressionBloomFilterFeature
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.stitch.Stitch
import com.twitter.stitch.timelineservice.TimelineService
import com.twitter.timelines.impressionstore.impressionbloomfilter.ImpressionBloomFilterItem
import com.twitter.timelineservice.{thriftscala => t}
import javax.inject.Inject
import javax.inject.Singleton

case class PinnedTweetCandidate(tweetId: Long, userId: Option[Long])

@Singleton
class BroadcastedPinnedTweetsCandidateSource @Inject() (
  timelineService: TimelineService)
    extends CandidateSource[
      ForYouQuery,
      PinnedTweetCandidate
    ] {

  override val identifier: CandidateSourceIdentifier =
    CandidateSourceIdentifier("BroadcastedPinnedTweets")

  private val MaxTimelineServiceTweets = 100
  private val MaxTweetsForHydration = 20

  override def apply(query: ForYouQuery): Stitch[Seq[PinnedTweetCandidate]] = {
    val userId = query.getRequiredUserId
    val timelineQueryOptions = t.TimelineQueryOptions(
      contextualUserId = Some(userId)
    )

    val timelineServiceQuery = t.TimelineQuery(
      timelineType = t.TimelineType.PinnedTweets,
      timelineId = userId,
      maxCount = MaxTimelineServiceTweets.toShort,
      cursor2 = None,
      options = Some(timelineQueryOptions),
      timelineId2 = Some(t.TimelineId(t.TimelineType.PinnedTweets, userId)),
    )

    timelineService
      .getTimeline(timelineServiceQuery).map { timeline =>
        timeline.entries.collect {
          case t.TimelineEntry.Tweet(tweet) =>
            PinnedTweetCandidate(tweet.statusId, tweet.userId)
        }
      }.map { candidates =>
        val bloomFilterSeq = query.features.map(_.get(ImpressionBloomFilterFeature)).get
        val bloomFilters =
          bloomFilterSeq.entries.map(ImpressionBloomFilterItem.fromThrift(_).bloomFilter)

        val seenTweetIds = query.seenTweetIds.getOrElse(Seq.empty).toSet

        candidates
          .filter { pinnedTweetCandidate =>
            !seenTweetIds.contains(pinnedTweetCandidate.tweetId) &&
            !bloomFilters.exists(filter => filter.mayContain(pinnedTweetCandidate.tweetId))
          }
          .take(MaxTweetsForHydration)
      }
  }
}
