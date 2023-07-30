package com.X.timelineranker.model

import com.X.timelineranker.{thriftscala => thrift}
import com.X.timelines.model.tweet.HydratedTweet
import com.X.tweetypie.{thriftscala => tweetypie}

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
