package com.X.recos.user_video_graph.util

import com.X.simclusters_v2.common.TweetId
import com.X.snowflake.id.SnowflakeId
import com.X.util.Duration
import com.X.util.Time

object FilterUtil {
  def tweetAgeFilter(tweetId: TweetId, maxAge: Duration): Boolean = {
    SnowflakeId
      .timeFromIdOpt(tweetId)
      .map { tweetTime => tweetTime > Time.now - maxAge }.getOrElse(false)
    // If there's no snowflake timestamp, we have no idea when this tweet happened.
  }
}
