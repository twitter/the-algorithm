package com.X.timelineranker.core

import com.X.timelines.model.tweet.HydratedTweet

case class HydratedTweets(
  outerTweets: Seq[HydratedTweet],
  innerTweets: Seq[HydratedTweet] = Seq.empty)
