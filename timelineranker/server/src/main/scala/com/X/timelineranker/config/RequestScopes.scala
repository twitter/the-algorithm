package com.X.timelineranker.config

import com.X.timelines.util.stats.RequestScope

object RequestScopes {
  val HomeTimelineMaterialization: RequestScope = RequestScope("homeMaterialization")
  val InNetworkTweetSource: RequestScope = RequestScope("inNetworkTweet")
  val RecapHydrationSource: RequestScope = RequestScope("recapHydration")
  val RecapAuthorSource: RequestScope = RequestScope("recapAuthor")
  val ReverseChronHomeTimelineSource: RequestScope = RequestScope("reverseChronHomeTimelineSource")
  val EntityTweetsSource: RequestScope = RequestScope("entityTweets")
  val UtegLikedByTweetsSource: RequestScope = RequestScope("utegLikedByTweets")
}
