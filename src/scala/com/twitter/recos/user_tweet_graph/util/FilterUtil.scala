package com.twitter.recos.user_tweet_graph.util

import com.twitter.simclusters_v2.common.TweetId
import com.twitter.snowflake.id.SnowflakeId
import com.twitter.util.Duration
import com.twitter.util.Time

object FilterUtil {
  def tweetAgeFilter(tweetId: TweetId, maxAge: Duration): Boolean = {
    SnowflakeId
      .timeFromIdOpt(tweetId)
      .map { tweetTime => tweetTime > Time.now - maxAge }.getOrElse(false)
    // If there's no snowflake timestamp, we have no idea when this tweet happened.
  }
}
