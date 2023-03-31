package com.twitter.timelineranker.model

import com.twitter.timelineranker.{thriftscala => thrift}
import com.twitter.timelines.model.tweet.HydratedTweet
import com.twitter.tweetypie.{thriftscala => tweetypie}

/**
 * Enables HydratedTweet entries to be included in a Timeline.
 */
class HydratedTweetEntry(tweet: tweetypie.Tweet) extends HydratedTweet(tweet) with TimelineEntry {

  def this(hydratedTweet: HydratedTweet) = this(hydratedTweet.tweet)

  override def toTimelineEntryThrift: thrift.TimelineEntry = {
    thrift.TimelineEntry.TweetypieTweet(tweet)
  }

  override def throwIfInvalid(): Unit = {
    // No validation performed.
  }
}
